package pl.guminski.ga.services.geneticAlgorithm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RoutingService {

    public Parent openToolbarWindow(String resource, BorderPane mainPane, ApplicationContext applicationContext){
        try {
            Parent selectedWindow;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            loader.setControllerFactory(applicationContext::getBean);
            selectedWindow = loader.load();
            mainPane.setCenter(selectedWindow);
            return selectedWindow;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
