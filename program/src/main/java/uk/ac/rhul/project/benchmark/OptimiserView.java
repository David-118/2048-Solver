package uk.ac.rhul.project.benchmark;

import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.DynamicSnake;
import uk.ac.rhul.project.heursitics.FailSetter;
import uk.ac.rhul.project.userInterface.NewGameObserver;
import uk.ac.rhul.project.userInterface.SolveObserver;
import uk.ac.rhul.project.userInterface.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class OptimiserView implements View
{
    public static GameConfiguration quickGame(int n)
    {
        return new GameConfiguration(n, n, 4, new FailSetter(new DynamicSnake(n, n), Float.MIN_VALUE));
    }
    private static final GameConfiguration[] CONFIGURATIONS = new GameConfiguration[] {
            quickGame(2),
            quickGame(3),
            quickGame(4),
    };

    private NewGameObserver newGameObserver;
    private SolveObserver solveObserver;

    private final int count;
    private final BenchmarkWriter csvWriter;

    private int configIndex = 0;
    private int gameIndex = 0;

    private GameState currentState;


    public OptimiserView(int count)
    {
        this.count = count;
        this.csvWriter = new BenchmarkWriter();
    }

    public void benchmark(OutputStream log) throws IOException
    {
        this.csvWriter.setOutput(log);
        for (int i = 0; i < CONFIGURATIONS.length; i++)
        {
            this.configIndex = i;
            System.out.print("Starting with heuristic: ");
            System.out.println(CONFIGURATIONS[configIndex].getName());

            for (int j  = 0; j < this.count; j++)
            {
                this.gameIndex = j;
                System.out.printf("Starting game %d\n", gameIndex +1);
                this.newGameObserver.notifyObservers(CONFIGURATIONS[configIndex]);
                this.solveObserver.notifyObserver(true);
                this.csvWriter.add(new BenchmarkEntry(CONFIGURATIONS[configIndex].getName(), currentState));
                System.out.println(this.csvWriter.median());
                this.csvWriter.write();
            }
            this.csvWriter.flushEntries();
        }
        this.csvWriter.close();
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
        String score = Integer.toString(state.getScore());
        System.out.print(score);
        for (int i = 0; i < score.length(); i++) System.out.print("\b");
    }

    @Override
    public void updateGrid(GameState state)
    {
        this.currentState = state;
        setValues(state);
    }

    public void startIfTerminal(File log) throws IOException
    {
        log.createNewFile();
        this.benchmark(new FileOutputStream(log));
    }
}

