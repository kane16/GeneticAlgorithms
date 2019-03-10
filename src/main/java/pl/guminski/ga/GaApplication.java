package pl.guminski.ga;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GaApplication extends Application {

    private ConfigurableApplicationContext context;
    private Parent rootNode;

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(GaApplication.class, getParameters().getRaw().toArray(new String[0]));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainToolbar.fxml"));
        loader.setControllerFactory(context::getBean);
        rootNode = loader.load();
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(true);
        Scene window = new Scene(rootNode, 1280, 768);
        window.getStylesheets().addAll(getClass().getResource("/styles/style.css").toExternalForm());
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - 1280) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - 768) / 2);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Rozwiązanie problemu podróżującego złodzieja");
        primaryStage.setScene(window);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        context.close();
    }

}
