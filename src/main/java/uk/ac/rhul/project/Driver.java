
package uk.ac.rhul.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import uk.ac.rhul.project.game.GameController;
import uk.ac.rhul.project.game.GameModel;
import uk.ac.rhul.project.game.GameView;

public class Driver
{


    public static void main(String[] args)
    {
        GameView view = GameView.getInstance();
        GameModel model = new GameModel(4, 4);
        new GameController(model, view);
    }
}
