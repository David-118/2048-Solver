
package uk.ac.rhul.project;

import uk.ac.rhul.project.userInterface.MainController;
import uk.ac.rhul.project.userInterface.MainModel;
import uk.ac.rhul.project.userInterface.MainView;

public class Driver
{


    public static void main(String[] args)
    {
        MainView view = MainView.getInstance();
        MainModel model = new MainModel(4, 4);
        new MainController(model, view);
   }
}
