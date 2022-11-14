package uk.ac.rhul.project.userInterface;

import javafx.application.Platform;
import uk.ac.rhul.project.game.Direction;

/**
 * Controller links view to model
 */
public class MainController
{
    /**
     * Model for the user interface.
     */
    private final Model model;

    /**
     * View for the user to interface.
     */
    private final MainView view;

    /**
     * Create a controller with a given model and view.
     * @param model The model.
     * @param view The view.
     */
    public MainController(MainModel model, MainView view)
    {
        this.model = model;
        this.view = view;
        this.view.addNewGameObserver(this::handelNewGame);
        this.view.addMoveObserver(this::handelMove);
        this.view.addSolveObserver(this::handelSolve);
        this.model.addUpdateObserver(this::handelUpdate);
    }

    /**
     * Creates a new 2048 game.
     * @param height Height of the game.
     * @param width Width of the game
     */
    private void handelNewGame(int height, int width)
    {
        this.model.init(height, width);
        this.view.setValues(this.model.getGrid(), this.model.getScore());
    }

    /**
     * Makes a move in the 2048 model , and updates view.
     * @param dir
     */
    public void handelMove(Direction dir)
    {
        this.model.move(dir);
        this.view.setValues(this.model.getGrid(), this.model.getScore());
    }

    public void handelSolve()
    {
        this.model.solve();
    }

    public void handelUpdate(int[][] grid, int score)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                view.setValues(grid, score);
            }
        });
    }
}
