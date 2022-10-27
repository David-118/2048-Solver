package uk.ac.rhul.project.game;

public class GameController
{
    private GameModel model;
    private GameView view;

    public GameController(GameModel model, GameView view)
    {
        this.model = model;
        this.view = view;
        this.view.addNewGameObserver(this::handelNewGame);
        this.view.addMoveObserver(this::handelMove);
        this.view.startNewGame();
    }

    private void handelNewGame()
    {
        this.model.init();
        this.view.setValues(this.model.getGrid());
    }

    public void handelMove(Direction dir)
    {
        this.model.move(dir);
        this.view.setValues(this.model.getGrid());
    }
}
