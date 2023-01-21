package uk.ac.rhul.project.game;

import uk.ac.rhul.project.expectimax.ExpectimaxTree;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Random;

public class GameConfiguration
{

    private final int rows;
    private final int cols;
    private final int depth;
    private final Heuristic heuristic;

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


    public GameConfiguration(int rows, int cols, int depth, Heuristic heuristic)
    {
        this.rows = rows;
        this.cols = cols;
        this.depth = depth;
        this.heuristic = heuristic;
    }

    public String getName()
    {
        return String.format("%d x %d game using %s (depth=%d)", rows, cols, heuristic.getName(), depth);
    }
}
