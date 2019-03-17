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
import pl.guminski.ga.services.DataExtractionService;
import pl.guminski.ga.services.ParametersService;
import pl.guminski.ga.services.RoutingService;
import pl.guminski.ga.services.SimulationService;

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

    public void startSimulation(){
        mainPane.getChildren().remove(selectedWindow);
        showInitialPopulationButton.setDisable(true);
        progressBar.progressProperty().unbind();
        progressStatus.textProperty().unbind();
        Task simulationTask = new Task() {
            @Override
            protected Object call() throws Exception {
                this.updateMessage("Populating model");
                this.updateProgress(5, 100);
                simulationService.populateModel();
                this.updateProgress(10, 100);
                this.updateMessage("Model populated");
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
                        progressStatus.setText("Model Populated");
                        showInitialPopulationButton.setDisable(false);
                    }
                });
        Thread thread = new Thread(simulationTask);
        thread.setDaemon(true);
        thread.start();
    }

}
