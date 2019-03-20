package pl.guminski.ga.controller;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.guminski.ga.services.ParametersService;
import pl.guminski.ga.services.PopulateTableService;
import pl.guminski.ga.services.SimulationService;

import java.io.IOException;

@Controller
public class PopulationWindowController {

    @Autowired
    ParametersService parametersService;

    @Autowired
    SimulationService simulationService;

    @Autowired
    PopulateTableService populateTableService;

    @FXML
    JFXTreeTableView treeTable;

    public void initialize() throws IOException {
        populateTableService.populateTable(treeTable, false);
    }

}
