package uk.ac.rhul.project.benchmark;

import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.FailSetter;
import uk.ac.rhul.project.heursitics.Monotonic;
import uk.ac.rhul.project.userInterface.NewGameObserver;
import uk.ac.rhul.project.userInterface.SolveObserver;
import uk.ac.rhul.project.userInterface.View;

import java.io.File;
import java.io.IOException;

/**
 * View for playing a specific 2048 game in the terminal.
 */
public class SeededGameView implements View
{
    /**
     * Method used to start new game.
     */
    private NewGameObserver newGameObserver;

    /**
     * Method used to solve a game.
     */
    private SolveObserver solveObserver;

    /**
     * Configuration of the game to play.
     */
    private final GameConfiguration gameConfiguration;

    /**
     * Define new SeededGame view, using default configuration.
     * @param seed Seed of the game to play.
     */
    public SeededGameView(long seed)
    {
        this.gameConfiguration =
                new GameConfiguration(4, 4, BenchmarkerView::depth4_4,
                Integer.MAX_VALUE, new FailSetter(new Monotonic(), -Math.pow(10, 3)));

        gameConfiguration.setSeed(seed);
    }

    /**
     * Define new SeededGame view.
     * @param configuration Configuration of game to play.
     * @param seed Seed of game to play.
     */
    public SeededGameView(GameConfiguration configuration, long seed)
    {
        this.gameConfiguration = configuration;
        gameConfiguration.setSeed(seed);
    }

    /**
     * Set method used to start a new game.
     * @param handelNewGame method used to create a new game.
     */
    @Override
    public void addNewGameObserver(NewGameObserver handelNewGame)
    {
        this.newGameObserver = handelNewGame;
    }

    /**
     * Set method used to solve a game.
     * @param handelSolve method used to solve a game.
     */
    @Override
    public void addSolveObserver(SolveObserver handelSolve)
    {
        this.solveObserver = handelSolve;
    }

    /**
     * Set the game state from the same thread
     *
     * <p>
     *     This will display the game score and display the current game state as a grid.
     *     It is assumed that no value is longer then 5 chars.
     * </p>
     * @param state Current game state.
     */
    @Override
    public void setValues(GameState state)
    {
        System.out.println("Score: " + state.getScore());
        for (int i = 0; i < state.getHeight(); i++)
        {
            System.out.println();
            for (int j = 0; j < state.getWidth(); j++)
            {
                System.out.printf("%5d ", state.getGrid()[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Set the game state from any grid.
     * @param state The current game stae.
     */
    @Override
    public void updateGrid(GameState state)
    {
        this.setValues(state);
    }

    /**
     * Start running the game.
     * @param log Log file not used.
     */
    @Override
    public void startIfTerminal(File log) {
        this.newGameObserver.notifyObservers(gameConfiguration);
        this.solveObserver.notifyObserver(true);
    }
}
