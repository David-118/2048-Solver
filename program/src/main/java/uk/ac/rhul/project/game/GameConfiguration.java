package uk.ac.rhul.project.game;

import uk.ac.rhul.project.expectimax.ExpectimaxTree;
import uk.ac.rhul.project.heursitics.Heuristic;
import uk.ac.rhul.project.userInterface.View;

import java.util.Locale;
import java.util.Random;

public class GameConfiguration
{

    private final int rows;
    private final int cols;
    private final int depth;
    private final Heuristic heuristic;

    private final int count4;

    private Random random;

    private Long seed = null;

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public int getDepth()
    {
        return depth;
    }

    public Heuristic getHeuristic()
    {
        return heuristic;
    }

    public Random getRandom()
    {
        return random;
    }

    public GameConfiguration(int rows, int cols, int depth, int count4, Heuristic heuristic)
    {
        this.rows = rows;
        this.cols = cols;
        this.depth = depth;
        this.heuristic = heuristic;
        this.count4 = count4;
        this.random = new Random();
    }


    public void setSeed(long seed)
    {
        this.random = new Random(seed);
    }

    public String getName()
    {
        return String.format("%d x %d game using %s (depth=%d)", rows, cols, heuristic.getName(), depth);
    }

    public int getCount4() {
        return count4;
    }
}
