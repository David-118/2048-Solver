package uk.ac.rhul.project.benchmark;

import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.*;
import uk.ac.rhul.project.userInterface.NewGameObserver;
import uk.ac.rhul.project.userInterface.SolveObserver;
import uk.ac.rhul.project.userInterface.View;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class BenchmarkerView implements View
{
    private static final GameConfiguration[] CONFIGERATIONS = new GameConfiguration[] {
            new GameConfiguration(4, 4, 6, new Monotonic()),
            new GameConfiguration(3, 3, 6, new Monotonic()),
            new GameConfiguration(2, 2, 6, new Monotonic()),
            new GameConfiguration(5, 5, 4, new Monotonic()),
    };

    private NewGameObserver newGameObserver;
    private SolveObserver solveObserver;

    private final int count;
    private final BenchmarkWriter csvWriter;

    private int configIndex = 0;
    private int gameIndex = 0;

    private GameState currentState;


    public BenchmarkerView(int count)
    {
        this.count = count;
        this.csvWriter = new BenchmarkWriter();
    }

    public void benchmark(OutputStream log) throws IOException
    {
        for (int i = 0; i < CONFIGERATIONS.length; i++)
        {
            this.configIndex = i;
            System.out.print("Starting with heuristic: ");
            System.out.println(CONFIGERATIONS[configIndex].getName());

            for (int j  = 0; j < this.count; j++)
            {
                this.gameIndex = j;
                System.out.printf("Starting game %d\n", gameIndex +1);
                this.newGameObserver.notifyObservers(CONFIGERATIONS[configIndex]);
                this.solveObserver.notifyObserver(true);
                this.csvWriter.add(new BenchmarkEntry(CONFIGERATIONS[configIndex].getName(), currentState));
	    }
        }
        this.csvWriter.write(log);
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
        System.out.printf("%s Game %d/%d: Score: %d \n %s\n",
                CONFIGERATIONS[this.configIndex].getName(), gameIndex + 1, count, state.getScore(),
                Arrays.deepToString(state.getGrid()));
    }

    @Override
    public void updateGrid(GameState state)
    {
        this.currentState = state;
        setValues(state);
    }
}
