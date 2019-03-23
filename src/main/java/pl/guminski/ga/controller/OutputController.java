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
        populateTableService.populateTable(treeTable, true, new ArrayList<>());
        tableNames.setItems(observableTableNames);
        tableNames.setValue("All algorithms");
    }

}
