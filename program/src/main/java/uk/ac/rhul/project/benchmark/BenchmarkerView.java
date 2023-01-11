package uk.ac.rhul.project.benchmark;

import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.*;
import uk.ac.rhul.project.userInterface.NewGameObserver;
import uk.ac.rhul.project.userInterface.SolveObserver;
import uk.ac.rhul.project.userInterface.View;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkerView implements View
{
    private static final Heuristic[] HEURISTICS =
            new Heuristic[]{new SumCells(), new LargestLower(), new Snake(), new Diagonal()};

    private NewGameObserver newGameObserver;
    private SolveObserver solveObserver;

    private final int count;
    private final BenchmarkWriter csvWriter;

    private int heuristicIndex = 0;
    private int gameIndex = 0;

    private GameState currentState;


    public BenchmarkerView(int count)
    {
        this.count = count;
        this.csvWriter = new BenchmarkWriter();
    }

    public void benchmark(OutputStream log) throws IOException
    {
        for (int i  = 0; i < HEURISTICS.length;  i++)
        {
            this.heuristicIndex = i;
            System.out.print("Starting with heuristic: ");
            System.out.println(HEURISTICS[heuristicIndex].getName());

            for (int j  = 0; j < this.count; j++)
            {
                this.gameIndex = j;
                System.out.printf("Starting game %d\n", heuristicIndex +1);
                this.newGameObserver.notifyObservers(4, 4);
                this.solveObserver.notifyObserver(true, HEURISTICS[this.heuristicIndex]);
            }
            this.csvWriter.add(new BenchmarkEntry(HEURISTICS[heuristicIndex].getName(), currentState));
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
                HEURISTICS[this.heuristicIndex].getName(), gameIndex + 1, count, state.getScore(),
                Arrays.deepToString(state.getGrid()));
    }

    @Override
    public void updateGrid(GameState state)
    {
        this.currentState = state;
        setValues(state);
    }
}
