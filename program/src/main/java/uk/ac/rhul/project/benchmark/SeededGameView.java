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
    public SeededGameView(Long seed)
    {
        this.gameConfiguration =
                new GameConfiguration(4, 4, 6, new FailSetter(new Monotonic(), -Math.pow(10, 3)));

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
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < state.getHeight(); i++)
        {
            System.out.println();
            for (int j = 0; j < state.getWidth(); j++)
            {
                System.out.printf("%5d ", state.getGrid()[i][j]);
            }
            System.out.println();

        }
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
