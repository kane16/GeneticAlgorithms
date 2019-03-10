package pl.guminski.ga.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.guminski.ga.services.ParametersService;

import java.io.IOException;

@Controller
public class PopulationWindowController {

    @Autowired
    ParametersService parametersService;

    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainToolbar.fxml"));
        System.out.println(parametersService.getChromosome_size());
    }

}
