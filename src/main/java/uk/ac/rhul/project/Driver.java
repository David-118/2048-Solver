
package uk.ac.rhul.project;

import uk.ac.rhul.project.userInterface.GameController;
import uk.ac.rhul.project.userInterface.GameModel;
import uk.ac.rhul.project.userInterface.GameView;

public class Driver
{


    public static void main(String[] args)
    {
        GameView view = GameView.getInstance();
        GameModel model = new GameModel(4, 4);
        new GameController(model, view);
   }
}
