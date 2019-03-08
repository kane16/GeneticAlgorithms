package Visualisation;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainToolbar.fxml"));
        Scene window = new Scene(loader.load(), 1280, 768);
        window.getStylesheets().addAll(getClass().getResource("/styles/style.css").toExternalForm());
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - 1280) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - 768) / 2);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Rozwiązanie problemu podróżującego złodzieja");
        primaryStage.setScene(window);
        primaryStage.show();
    }
}
