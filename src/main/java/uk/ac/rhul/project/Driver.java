
package uk.ac.rhul.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import uk.ac.rhul.project.game.GameView;

public class Driver extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        GameView controller = new GameView();
        fxmlLoader.setController(controller);

        Scene root = fxmlLoader.load();

        primaryStage.setScene(root);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
