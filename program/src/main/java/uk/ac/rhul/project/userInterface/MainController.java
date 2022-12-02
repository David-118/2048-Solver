package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

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
    private final View view;

    /**
     * Create a controller with a given model and view.
     * @param model The model.
     * @param view The view.
     */
    public MainController(Model model, View view)
    {
        this.model = model;
        this.view = view;
        this.view.addNewGameObserver(this::handelNewGame);
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
        this.view.setValues(this.model.getGrid());
    }

    /**
     * Handel for solving a 2048 game.
     */
    public void handelSolve(boolean blocking, Heuristic heuristic)
    {
        this.model.solve(blocking, heuristic);
    }

    /**
     * Handel for updating 2048 game while solver is running.
     * @param state The current state of the game
     */
    public void handelUpdate(GameState state)
    {
        this.view.updateGrid(state);
    }
}
