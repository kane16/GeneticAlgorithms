package pl.guminski.ga.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import pl.guminski.ga.services.geneticAlgorithm.RoutingService;

@Controller
public class MainController {

    @FXML
    private JFXButton GASimulationButton;

    @FXML
    private JFXButton CSPSimulationButton;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    RoutingService routingService;

    public void onGAChosen(){
        routingService.openWindow("/views/GAToolbar.fxml", 1280, 768, true, GASimulationButton,
                applicationContext, "Travelling thief problem");
    }

    public void onCSPChosen(){
        routingService.openWindow("/views/CSPToolbar.fxml", 1280, 768, true, GASimulationButton,
                applicationContext, "Rules Satisfaction Problem");
    }

}
