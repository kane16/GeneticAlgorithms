package pl.guminski.ga.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import pl.guminski.ga.models.Individual;
import pl.guminski.ga.services.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    RoutingService routingService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ParametersService parametersService;

    @Autowired
    SimulationService simulationService;

    @Autowired
    DataExtractionService dataExtractionService;

    @FXML
    private JFXButton runSimulationButton;

    @FXML
    private JFXButton showSimulationTableOutputButton;

    @FXML
    private JFXButton showSimulationVisualisationOutputButton;

    @FXML
    private JFXComboBox<String> simulationComboBox;

    @FXML
    private JFXTextField PmField;

    @FXML
    private JFXTextField PxField;

    Parent selectedWindow;

    @FXML
    private JFXTextField popSizeField;

    @FXML
    private JFXTextField numOfGenField;

    @FXML
    private JFXButton showInitialPopulationButton;

    @FXML
    private Label progressStatus;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private BorderPane mainPane;

    @Autowired
    RankSelectionService rankSelectionService;

    @Autowired
    RouletteSelectionService rouletteSelectionService;

    @Autowired
    RandomSelectionService randomSelectionService;

    @Autowired
    GreedySelectionService greedySelectionService;

    public void initialize(){

        this.routingService = new RoutingService();

        ObservableList<String> scenarioNames = FXCollections.observableArrayList();

        scenarioNames.addAll(dataExtractionService.getAllFilesFromInputDataFolder());

        this.showSimulationTableOutputButton.setDisable(true);

        this.showSimulationVisualisationOutputButton.setDisable(true);

        runSimulationButton.setDisable(true);

        progressBar.setProgress(0);

        simulationComboBox.setItems(scenarioNames);

        showInitialPopulationButton.setDisable(true);

        simulationComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) ->
        {
            if(newValue != null){
                parametersService.setDataInputContainer(
                        dataExtractionService.getDataInputContainerFromFile(
                                dataExtractionService.getDataInputFile(newValue)));
                runSimulationButton.setDisable(false);
            }
        });
        popSizeField.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                parametersService.setPop_size(Integer.parseInt(newValue));
            }catch (Exception exc){

            }
        });
        PmField.setText(String.valueOf(parametersService.getPm()));
        PxField.setText(String.valueOf(parametersService.getPx()));
        popSizeField.setText(String.valueOf(parametersService.getPop_size()));

    }

    public void showPopulationScreen(){
        selectedWindow = routingService.openToolbarWindow("/views/ShowPopulationWindow.fxml", mainPane, applicationContext);
    }

    public void showSimulationOutputScreen(){
        selectedWindow = routingService.openToolbarWindow("/views/ShowOutputWindow.fxml", mainPane, applicationContext);
    }



    public void startSimulation(){
        mainPane.getChildren().remove(selectedWindow);
        showInitialPopulationButton.setDisable(true);
        showSimulationTableOutputButton.setDisable(true);
        showSimulationVisualisationOutputButton.setDisable(true);
        progressBar.progressProperty().unbind();
        progressStatus.textProperty().unbind();
        Task simulationTask = new Task() {
            @Override
            protected Object call() throws Exception {
                this.updateMessage("Populating model");
                this.updateProgress(5, 100);
                simulationService.populateModel();
                this.updateProgress(10, 100);
                this.updateMessage("Processing rank algorithm ...");
                List<Individual> rankAlgorithmBestIndividuals =
                        rankSelectionService.runAlgorithmAndFindBestSolutionInGeneration(simulationService.getPopulation());
                this.updateProgress(45, 100);
                this.updateMessage("Processing roulette algorithm...");
                List<Individual> rouletteAlgorithmBestIndividuals =
                        rouletteSelectionService.runAlgorithmAndFindBestSolutionInGeneration(simulationService.getPopulation());
                this.updateProgress(80, 100);
                this.updateMessage("Processing random algorithm...");
                Individual randomBestIndividual = randomSelectionService.generatePopulationAndFindBestFitness();
                this.updateProgress(90, 100);
                Individual greedyBestIndividual = greedySelectionService.getGreedyBestFitnessChromosome();
                this.updateProgress(100, 100);
                return null;
            }
        };
        progressStatus.textProperty().bind(simulationTask.messageProperty());
        progressBar.progressProperty().bind(simulationTask.progressProperty());
        simulationTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        progressStatus.textProperty().unbind();
                        progressStatus.setText("Processing done.");
                        showInitialPopulationButton.setDisable(false);
                        showSimulationTableOutputButton.setDisable(false);
                        showSimulationVisualisationOutputButton.setDisable(false);
                    }
                });
        Thread thread = new Thread(simulationTask);
        thread.setDaemon(true);
        thread.start();
    }

}
