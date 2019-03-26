package pl.guminski.ga.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.guminski.ga.services.futoshiki.CSPDataExtractorService;

@Controller
public class CSPController {

    @FXML
    JFXComboBox<String> gameNameCombo;

    @FXML
    JFXComboBox<String> scenarioNameCombo;

    @Autowired
    CSPDataExtractorService cspDataExtractorService;

    public void initialize(){
        ObservableList<String> gameNames = FXCollections.observableArrayList();
        gameNames.addAll("futoshiki", "skyscrapper");
        gameNameCombo.setItems(gameNames);
        scenarioNameCombo.setDisable(true);
    }

    public void onGameChosen(){
        ObservableList<String> scenarioNames = FXCollections.observableArrayList();
        scenarioNames.addAll(cspDataExtractorService.getAllFilesOfGameFromInputDataFolder(gameNameCombo.getValue()));
        scenarioNameCombo.setDisable(false);
        scenarioNameCombo.setItems(scenarioNames);
    }

}
