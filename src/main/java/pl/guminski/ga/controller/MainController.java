package pl.guminski.ga.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import pl.guminski.ga.services.DataExtractionService;
import pl.guminski.ga.services.ParametersService;
import pl.guminski.ga.services.RoutingService;
import pl.guminski.ga.services.SimulationService;

import java.util.stream.Collectors;

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

        simulationComboBox.setItems(scenarioNames);

        showInitialPopulationButton.setDisable(true);

        progressBar.setProgress(0);

        simulationComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) ->
        {
            if(newValue != null){
                parametersService.setDataInputContainer(
                        dataExtractionService.getDataInputContainerFromFile(
                                dataExtractionService.getDataInputFile(newValue)));
                runSimulationButton.setDisable(false);
            }
        });

        PmField.setText(String.valueOf(parametersService.getPm()));
        PxField.setText(String.valueOf(parametersService.getPx()));
        popSizeField.setText(String.valueOf(parametersService.getPop_size()));

    }

    public void showPopulationScreen(){
        routingService.openToolbarWindow("/views/ShowPopulationWindow.fxml", mainPane, applicationContext);
    }

    public void startSimulation(){
        progressStatus.setText("Populating model");
        progressBar.indeterminateProperty().addListener(
                new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if(oldValue)
                            progressStatus.setText("Calculating time");
                        else progressStatus.setText("In progress");
                    }
                }
        );

        progressBar.progressProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        if(newValue.doubleValue() == 1.0){
                            progressStatus.setText("Simulation finished");
                            progressStatus.setTextFill(Color.GREEN);
                        }
                    }
                }
        );
        simulationService.populateModel();
        showInitialPopulationButton.setDisable(false);
    }

}
