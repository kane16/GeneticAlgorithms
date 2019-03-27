package pl.guminski.ga.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.guminski.ga.models.games.FutoshikiItem;
import pl.guminski.ga.models.games.SkyscraperItem;
import pl.guminski.ga.models.games.viewModels.Tile;
import pl.guminski.ga.services.futoshiki.CSPDataExtractorService;

import java.awt.*;
import java.io.FileNotFoundException;

@Controller
public class CSPController {

    @FXML
    JFXComboBox<String> gameNameCombo;

    @FXML
    JFXComboBox<String> scenarioNameCombo;

    @FXML
    StackPane rectangle;

    FutoshikiItem futoshikiItem;

    SkyscraperItem skyscraperItem;

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

    public void onScenarioChosen() throws FileNotFoundException {
        switch (gameNameCombo.getValue()){
            case "futoshiki":
                futoshikiItem = cspDataExtractorService.getFutoshikiItemFromFile(scenarioNameCombo.getValue());
                createFutoshikiBoard();
                break;
            case "skyscrapper":
                skyscraperItem = cspDataExtractorService.getScascraperItemFromFile(scenarioNameCombo.getValue());
                break;
        }
    }

    private void createFutoshikiBoard() {
        for(int i=0;i<futoshikiItem.maxNumber;i++){
            for(int j=0;j<futoshikiItem.maxNumber;j++){
                Tile tile = new Tile((int)rectangle.getWidth()/futoshikiItem.maxNumber,
                        (int)rectangle.getHeight()/futoshikiItem.maxNumber);
                tile.setTranslateX(j*(int)rectangle.getWidth()/futoshikiItem.maxNumber);
                tile.setTranslateY(i*(int)rectangle.getHeight()/futoshikiItem.maxNumber);
                tile.drawNumber("4");
                rectangle.getChildren().add(tile);
            }
        }
    }

}
