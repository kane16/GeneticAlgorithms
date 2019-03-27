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
import pl.guminski.ga.models.GA.Individual;
import pl.guminski.ga.services.geneticAlgorithm.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GASimulationController {

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

    @Autowired
    TournamentSelectionService tournamentSelectionService;

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

    @FXML
    private JFXTextField tourNumberField;

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
        PmField.textProperty().addListener(((observable, oldValue, newValue) ->
        {
            try{
                parametersService.setPm(Double.parseDouble(newValue));
            }catch (Exception exc){

            }
        }));
        PxField.textProperty().addListener(((observable, oldValue, newValue) -> {
            try{
                parametersService.setPx(Double.parseDouble(newValue));
            }catch (Exception exc){

            }
        }));
        numOfGenField.textProperty().addListener(((observable, oldValue, newValue) -> {
            try{
                parametersService.setGenerationNumber(Integer.parseInt(newValue));
            }catch (Exception exc){

            }
        }));
        tourNumberField.textProperty().addListener(((observable, oldValue, newValue) -> {
            try{
                parametersService.setTour(Integer.parseInt(newValue));
            }catch (Exception exc){

            }
        }));
        PmField.setText(String.valueOf(parametersService.getPm()));
        PxField.setText(String.valueOf(parametersService.getPx()));
        popSizeField.setText(String.valueOf(parametersService.getPop_size()));
        numOfGenField.setText(String.valueOf(parametersService.getGenerationNumber()));
        tourNumberField.setText(String.valueOf(parametersService.getTour()));
    }

    public void showPopulationScreen(){
        selectedWindow = routingService.openToolbarWindow("/views/ShowPopulationWindow.fxml", mainPane, applicationContext);
    }

    public void showSimulationOutputScreen(){
        selectedWindow = routingService.openToolbarWindow("/views/ShowOutputWindow.fxml", mainPane, applicationContext);
    }

    public void showSimulationVisualisationOutputScreen(){
        selectedWindow = routingService.openToolbarWindow("/views/Plots.fxml", mainPane, applicationContext);
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
                List<Individual> rankIndividuals = new ArrayList<>();
                List<Thread> threads = new ArrayList<>();
                this.updateMessage("Processing");
                for(int i=0 ; i<10 ;i++){
                    Task task = new Task() {
                        @Override
                        protected Object call() throws Exception {
                            rankIndividuals.addAll(
                                    rankSelectionService.runAlgorithmAndFindBestSolutionInGeneration(simulationService.getPopulation()));
                            return null;
                        }
                    };
                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    threads.add(thread);
                    thread.start();
                }
                List<Individual> tournamentIndividuals = new ArrayList<>();
                for(int i=0 ; i<10 ;i++){
                    Task task = new Task() {
                        @Override
                        protected Object call() throws Exception {
                            tournamentIndividuals.addAll(
                                    tournamentSelectionService.runAlgorithmAndFindBestSolutionInGeneration(simulationService.getPopulation()));
                            return null;
                        }
                    };
                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    threads.add(thread);
                    thread.start();
                }
                List<Individual> rouletteIndividuals = new ArrayList<>();
                for(int i=0 ; i<10 ;i++){
                    Task task = new Task() {
                        @Override
                        protected Object call() throws Exception {
                            rouletteIndividuals.addAll(
                                    rouletteSelectionService.runAlgorithmAndFindBestSolutionInGeneration(simulationService.getPopulation()));
                            return null;
                        }
                    };
                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    threads.add(thread);
                    thread.start();
                }
                List<Individual> randomIndividuals = new ArrayList<>();
                for(int i=0 ; i<10 ;i++){
                    Task task = new Task() {
                        @Override
                        protected Object call() throws Exception {
                            randomIndividuals.add(randomSelectionService.generatePopulationAndFindBestFitness());
                            return null;
                        }
                    };
                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    threads.add(thread);
                    thread.start();
                }
                List<Individual> greedyIndividuals = new ArrayList<>();
                for(int i=0 ; i<10 ;i++){
                    Task task = new Task() {
                        @Override
                        protected Object call() throws Exception {
                            greedyIndividuals.add(greedySelectionService.getGreedyBestFitnessChromosome());
                            return null;
                        }
                    };
                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    threads.add(thread);
                    thread.start();
                }
                while(threads.stream().anyMatch(Thread::isAlive)){
                    this.updateProgress(10+threads.stream().filter(thread -> !thread.isAlive())
                            .count()*2.25, 100);
                }
                this.updateProgress(100,100);
                simulationService.tournamentAlgorithmBestIndividuals = simulationService.getStatisticalIndividualFromList(tournamentIndividuals, false);
                simulationService.rankAlgorithmBestIndividuals = simulationService.getStatisticalIndividualFromList(rankIndividuals, false);
                simulationService.rouletteAlgorithmBestIndividuals = simulationService.getStatisticalIndividualFromList(rouletteIndividuals, false);
                simulationService.randomBestIndividual = simulationService.getStatisticalIndividualFromList(randomIndividuals, true).get(0);
                simulationService.greedyBestIndividual = simulationService.getStatisticalIndividualFromList(greedyIndividuals, true).get(0);
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
