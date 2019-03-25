package pl.guminski.ga.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.guminski.ga.services.ParametersService;
import pl.guminski.ga.services.PopulateTableService;
import pl.guminski.ga.services.SimulationService;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class OutputController {

    @Autowired
    ParametersService parametersService;

    @Autowired
    SimulationService simulationService;

    @Autowired
    PopulateTableService populateTableService;

    ObservableList<String> observableTableNames = FXCollections.observableArrayList();

    @FXML
    JFXComboBox tableNames;

    @FXML
    JFXTreeTableView treeTable;

    public void initialize() throws IOException {
        observableTableNames.addAll("All algorithms", "Rank", "Roulette", "Random", "Greedy");
        populateTableService.populateAllAlgorithmsTable(treeTable, "All algorithms");
        tableNames.setItems(observableTableNames);
        tableNames.setValue("All algorithms");
    }

    public void onChosenTable(){
        if(tableNames.getValue().equals("All algorithms")){
            populateTableService.populateAllAlgorithmsTable(treeTable, "All algorithms");
        }else if(tableNames.getValue().equals("Rank")){
            populateTableService.populateAllAlgorithmsTable(treeTable, "Rank");
        } else if (tableNames.getValue().equals("Roulette")) {
            populateTableService.populateAllAlgorithmsTable(treeTable, "Roulette");
        }else if(tableNames.getValue().equals("Random")){
            populateTableService.populateAllAlgorithmsTable(treeTable, "Random");
        }else if(tableNames.getValue().equals("Greedy")){
            populateTableService.populateAllAlgorithmsTable(treeTable, "Greedy");
        }
    }

}
