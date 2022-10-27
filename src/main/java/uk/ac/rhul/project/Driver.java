
package uk.ac.rhul.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent gui = FXMLLoader.load(Driver.class.getResource("main.fxml"));
        Scene scene = new Scene(gui);

        primaryStage.setScene(scene);
        primaryStage.setTitle("2048 Solver");
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
