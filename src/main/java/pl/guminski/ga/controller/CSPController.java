package pl.guminski.ga.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.guminski.ga.models.games.FutoshikiItem;
import pl.guminski.ga.models.games.SkyscraperItem;
import pl.guminski.ga.models.games.viewModels.Tile;
import pl.guminski.ga.services.futoshiki.CSPDataExtractorService;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CSPController {

    @FXML
    JFXComboBox<String> gameNameCombo;

    @FXML
    JFXComboBox<String> scenarioNameCombo;

    @FXML
    AnchorPane rectangle;

    FutoshikiItem futoshikiItem;

    SkyscraperItem skyscraperItem;

    List<List<Tile>> skyscraperValues;

    List<List<Tile>> futoshikiValues;

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
                createSkyscraperBoard();
                break;
        }
    }

    private void createFutoshikiBoard() {
        rectangle.getChildren().clear();
        List<List<Tile>> values = new ArrayList<>();
        for(int i=0;i<futoshikiItem.maxNumber;i++){
            List<Tile> valuesRow = new ArrayList<>();
            for(int j=0;j<futoshikiItem.maxNumber;j++){
                valuesRow.add(addTileForFutoshiki(i, j));
            }
            values.add(valuesRow);
        }
        this.futoshikiValues = values;
    }

    public void createSkyscraperBoard(){
        rectangle.getChildren().clear();
        List<List<Tile>> values = new ArrayList<>();
        for(int i=0;i<skyscraperItem.size+2;i++){
            List<Tile> valuesRow = new ArrayList<>();
            for(int j=0;j<skyscraperItem.size+2;j++){
                if(i==0 && j>0 && j<skyscraperItem.size+1){
                    valuesRow.add(addVerticalTileForSkyscraper(i, j, skyscraperItem.size+2, skyscraperItem.topBound));
                }else if(i>0 && i<skyscraperItem.size+1 && j==0){
                    valuesRow.add(addHorizontalTileForSkyscraper(i, j, skyscraperItem.size + 2, skyscraperItem.leftBound));
                }else if(i>0 && i<skyscraperItem.size+1 && j==skyscraperItem.size+1){
                    valuesRow.add(addHorizontalTileForSkyscraper(i, j, skyscraperItem.size + 2, skyscraperItem.rightBound));
                }else if(i==skyscraperItem.size+1 && j>0 && j<skyscraperItem.size+1){
                    valuesRow.add(addVerticalTileForSkyscraper(i, j, skyscraperItem.size+2, skyscraperItem.bottomBound));
                }else {
                    Tile tile = new Tile((int) rectangle.getWidth() / (skyscraperItem.size + 2),
                            (int) rectangle.getHeight() / (skyscraperItem.size + 2));
                    tile.setTranslateX(j * (int) rectangle.getWidth() / (skyscraperItem.size + 2));
                    tile.setTranslateY(i * (int) rectangle.getHeight() / (skyscraperItem.size + 2));
                    tile.drawNumber("");
                    rectangle.getChildren().add(tile);
                    valuesRow.add(tile);
                }
            }
            values.add(valuesRow);
        }
        this.skyscraperValues = values;
    }

    private Tile addTileForFutoshiki(int i, int j) {
        Tile tile = new Tile((int) rectangle.getWidth() / futoshikiItem.maxNumber,
                (int) rectangle.getHeight() / futoshikiItem.maxNumber);
        tile.setTranslateX(j * (int) rectangle.getWidth() / futoshikiItem.maxNumber);
        tile.setTranslateY(i * (int) rectangle.getHeight() / futoshikiItem.maxNumber);
        tile.drawNumber(futoshikiItem.contentTable.get(i).get(j).toString());
        rectangle.getChildren().add(tile);
        return tile;
    }

    private Tile addVerticalTileForSkyscraper(int i, int j, int size, List<Integer> horizontalBound) {
        return AddTileForSkyscraper(i, j, size, horizontalBound, j-1);
    }

    private Tile addHorizontalTileForSkyscraper(int i, int j, int size, List<Integer> verticalBound){
        return AddTileForSkyscraper(i, j, size, verticalBound, i - 1);
    }

    private Tile AddTileForSkyscraper(int i, int j, int size, List<Integer> verticalBound, int itemIndex) {
        Tile tile = new Tile((int) rectangle.getWidth() / size,
                (int) rectangle.getHeight() / size);
        tile.setTranslateX(j * (int) rectangle.getWidth() / size);
        tile.setTranslateY(i * (int) rectangle.getHeight() / size);
        tile.drawNumber(verticalBound.get(itemIndex).toString());
        rectangle.getChildren().add(tile);
        return tile;
    }

}
