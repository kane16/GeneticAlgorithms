package pl.guminski.ga.models.games.viewModels;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends StackPane {

    private Text text = new Text();

    public Tile(int width, int height){
        Rectangle border = new Rectangle(width, height);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        setAlignment(Pos.CENTER);
        getChildren().addAll(border);
    }

    public void drawNumber(String number){
        text.setText(number);
    }

}
