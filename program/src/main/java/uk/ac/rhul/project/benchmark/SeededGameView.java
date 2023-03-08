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

public class SeededGameView implements View
{
    private NewGameObserver newGameObserver;
    private SolveObserver solveObserver;

    private final GameConfiguration gameConfiguration;

    public SeededGameView(long seed)
    {
        this.gameConfiguration =
                new GameConfiguration(4, 4, (int k) -> {
		    double l = Math.pow(15-k, 2) / 14D;
		    return (int) Math.floor(7 + (l * (3D / 14D)));
		},
                Integer.MAX_VALUE, new FailSetter(new Monotonic(), -Math.pow(10, 3)));

        gameConfiguration.setSeed(seed);
    }

    public SeededGameView(GameConfiguration configuration, long seed)
    {
        this.gameConfiguration = configuration;
        gameConfiguration.setSeed(seed);
    }
    @Override
    public void addNewGameObserver(NewGameObserver handelNewGame)
    {
        this.newGameObserver = handelNewGame;
    }

    @Override
    public void addSolveObserver(SolveObserver handelSolve)
    {
        this.solveObserver = handelSolve;
    }

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

    @Override
    public void updateGrid(GameState state)
    {
        this.setValues(state);
    }

    @Override
    public void startIfTerminal(File log) throws IOException
    {
        this.newGameObserver.notifyObservers(gameConfiguration);
        this.solveObserver.notifyObserver(true);
    }
}
