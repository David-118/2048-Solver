package uk.ac.rhul.project.game;

public class GameController
{
    private final GameModel model;
    private final GameView view;

    public GameController(GameModel model, GameView view)
    {
        this.model = model;
        this.view = view;
        this.view.addNewGameObserver(this::handelNewGame);
        this.view.addMoveObserver(this::handelMove);
        this.view.startNewGame();
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
}
