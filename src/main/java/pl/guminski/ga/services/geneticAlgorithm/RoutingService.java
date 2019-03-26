package pl.guminski.ga.services.geneticAlgorithm;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
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

    public void openWindow(String resource, double width, double height, boolean isResizable, JFXButton button,
                           ApplicationContext applicationContext, String title) {
        try {
            Parent rootNode;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            loader.setControllerFactory(applicationContext::getBean);
            rootNode = loader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setResizable(true);
            Scene window = new Scene(rootNode, width, height);
            window.getStylesheets().addAll(getClass().getResource("/styles/style.css").toExternalForm());
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - width) / 2);
            stage.setY((primScreenBounds.getHeight() - height) / 2);
            stage.setResizable(isResizable);
            stage.setTitle(title);
            stage.setScene(window);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
