package controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import static utilities.TTYInputFileDataReader.getAllFilesFromInputDataFolder;

public class MainController {

    @FXML
    JFXButton ShowSimulationTableOutput;

    @FXML
    JFXButton ShowSimulationVisualisationOutput;

    @FXML
    JFXComboBox<String> simulationComboBox;

    public void initialize(){

        ObservableList<String> scenarioNames = FXCollections.observableArrayList();

        scenarioNames.addAll(getAllFilesFromInputDataFolder());

        simulationComboBox.setItems(scenarioNames);

    }

    public void importSome(){
        System.out.println("Drukuje");
    }

}
