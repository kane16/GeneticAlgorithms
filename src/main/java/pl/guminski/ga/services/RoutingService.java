package pl.guminski.ga.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RoutingService {

    public void openToolbarWindow(String resource, BorderPane mainPane, ApplicationContext applicationContext){
        try {
            Parent selectedWindow;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            loader.setControllerFactory(applicationContext::getBean);
            selectedWindow = loader.load();
            mainPane.setCenter(selectedWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
