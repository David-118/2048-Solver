package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.Direction;

public class MainController
{
    private final Model model;
    private final MainView view;

    public MainController(MainModel model, MainView view)
    {
        this.model = model;
        this.view = view;
        this.view.addNewGameObserver(this::handelNewGame);
        this.view.addMoveObserver(this::handelMove);
        this.view.addSolveObserver(this::handelSolve);
    }

    private void handelNewGame(int height, int width)
    {
        this.model.init(height, width);
        this.view.setValues(this.model.getGrid(), this.model.getScore());
    }

    public void handelMove(Direction dir)
    {
        this.model.move(dir);
        this.view.setValues(this.model.getGrid(), this.model.getScore());
    }

    public void handelSolve()
    {
        System.out.println("Solve");
    }
}
