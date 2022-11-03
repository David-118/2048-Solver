package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.GameState;

import java.util.Random;

public abstract class Heuristics
{
    private static Random rnd = new Random();
    public static float sumCells(GameState state)
    {
        float sum = 0f;
        int[][] grid = state.getGrid();
        for (int[] row : grid)
        {
            for (int value: row)
            {
                sum += value;
            }
        }
        return sum;
    }

    public static float largestLower(GameState state)
    {
        float sum = 0f;
        int[][] grid = state.getGrid();
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid.length; j++)
            {
                sum += grid[i][j] ^ i;
            }
        }
        return sum;
    }

    public static float getScore(GameState state)
    {
        return state.getScore();
    }

    public static float getRandom(GameState state)
    {
        return rnd.nextFloat(1000);
    }
}
