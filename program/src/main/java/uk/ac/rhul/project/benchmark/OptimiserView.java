package uk.ac.rhul.project.benchmark;

import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.DynamicSnake;
import uk.ac.rhul.project.heursitics.FailRatio;
import uk.ac.rhul.project.userInterface.NewGameObserver;
import uk.ac.rhul.project.userInterface.SolveObserver;
import uk.ac.rhul.project.userInterface.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Clock;
import java.util.Arrays;

public class OptimiserView implements View
{
    private final int iterationCount;

    public static GameConfiguration makeGame(double n)
    {
        return new GameConfiguration(4, 4, 6, Integer.MAX_VALUE, new FailRatio(new DynamicSnake(4, 4), n));
    }


    private double optimalN;
    private BenchmarkEntry optimalMedian;

    private final BenchmarkEntry[] medianScores;
    private final double[] ns;

    private static final double minN = -1;

    private static final double maxN = 1;


    private NewGameObserver newGameObserver;
    private SolveObserver solveObserver;

    private final int count;
    private final BenchmarkWriter csvWriter;

    private int configIndex = 0;
    private int gameIndex = 0;

    private GameState currentState;

    public OptimiserView(int gameCount, int iterations)
    {
        this.count = gameCount;
        this.iterationCount = iterations;
        this.csvWriter = new BenchmarkWriter();
        this.medianScores = new BenchmarkEntry[iterations];
        this.ns = new double[iterations];
    }

    public void benchmark(OutputStream log) throws IOException
    {
        this.configIndex = 0;
        this.table();

        this.csvWriter.setOutput(log);
        for (double n = minN; n <= maxN; n += (maxN - minN) / (iterationCount - 1))
        {
            runGames(n);

            BenchmarkEntry median = csvWriter.median();

            if (optimalMedian == null || median.compareTo(optimalMedian) > 0)
            {
                optimalMedian = median;
                optimalN = n;
            }


            this.csvWriter.flushEntries();
            this.configIndex++;
        }
        this.csvWriter.close();
    }

    public void tableDivide(int scoreWidth, int tileWidth)
    {
        System.out.print("+-----+-");
        for (int j = 0; j < scoreWidth; j++) System.out.print("-");
        System.out.print("-+-");
        for (int j = 0; j < tileWidth; j++) System.out.print("-");
        System.out.println("-+");
    }
    private void table()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        int maxTile = 0;
        int maxScore = 0;

        for (int i = 0; i < this.iterationCount; i++)
        {
            if (medianScores[i] != null)
            {
                if (medianScores[i].maxTile > maxTile)
                {
                    maxTile = medianScores[i].maxTile;
                }

                if (medianScores[i].score > maxScore)
                {
                    maxScore = medianScores[i].score;
                }
            }
        }

        int scoreWidth = "Median Score".length();
        int tileWidth = "Median Max Tile".length();
        tableDivide(scoreWidth, tileWidth);
        System.out.println("|  N  | Median Score | Median Max Tile |");

        for (int i = 0; i < this.iterationCount; i++)
        {
            int score, tile;
            if (medianScores[i] == null)
            {
                score = 0; tile = 0;
            } else
            {
                score = medianScores[i].score;
                tile = medianScores[i].maxTile;
            }
            tableDivide(scoreWidth, tileWidth);
            System.out.printf("|% .1f | %" + scoreWidth + "d | %" + tileWidth + "d |\n", this.ns[i],
                    score, tile);
        }
        tableDivide(scoreWidth, tileWidth);
    }
    private void runGames(double n) throws IOException
    {
        for (int j  = 0; j < this.count; j++)
        {
            this.gameIndex = j;
            this.newGameObserver.notifyObservers(makeGame(n));
            long start = Clock.systemUTC().millis();
            this.solveObserver.notifyObserver(true);
            long end = Clock.systemUTC().millis();
            this.csvWriter.add(new BenchmarkEntry("n=" + n, this.currentState, end-start));

            BenchmarkEntry median = csvWriter.median();
            this.medianScores[configIndex] = median;
            this.ns[configIndex] = n;
            this.table();
            this.csvWriter.write();
        }
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
        String output = String.format("Value: %d/%d Game: %d/%d Current Score: %d",
                configIndex + 1, iterationCount, gameIndex + 1, count, state.getScore());

        System.out.print(output);
        for (int i = 0; i < output.length(); i++) System.out.print("\b");
    }

    @Override
    public void updateGrid(GameState state)
    {
        this.currentState = state;
        setValues(state);
    }

    public void startIfTerminal(File log) throws IOException
    {
        this.benchmark(new FileOutputStream(log));
    }
}

