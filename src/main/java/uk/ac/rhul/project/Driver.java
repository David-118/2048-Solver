
package uk.ac.rhul.project;

import javafx.util.Pair;
import uk.ac.rhul.project.benchmark.Benchmarker;
import uk.ac.rhul.project.userInterface.MainController;
import uk.ac.rhul.project.userInterface.MainModel;
import uk.ac.rhul.project.userInterface.MainView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Driver
{
    public static void main(String[] args)
    {
        MainView view = MainView.getInstance();
        MainModel model = new MainModel(4, 4);
        new MainController(model, view);
    }
}
