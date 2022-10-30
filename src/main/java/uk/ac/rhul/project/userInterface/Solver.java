package uk.ac.rhul.project.userInterface;

import javafx.application.Platform;
import uk.ac.rhul.project.game.Direction;
import uk.ac.rhul.project.game.GameState;

public class Solver implements Runnable
{
    private GameState gameState;
    private UpdateObserver updateValues;

    public Solver(GameState gameState)
    {
        this.gameState = gameState;
    }

    public void addUpdateObserver(UpdateObserver method)
    {
        this.updateValues = method;
    }

    public void run()
    {
        boolean running = true;

        while (running)
        {
            try
            {
                Thread.sleep(100);
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            for (Direction dir: Direction.values())
            {
                if (this.gameState.move(dir))
                {
                    Platform.runLater(() -> {
                        this.updateValues.notifyObservers(gameState.getGrid(), gameState.getScore());
                    });
                    break;
                } else if (dir == Direction.RIGHT)
                {
                    running = false;
                }
            }
        }
    }
}
