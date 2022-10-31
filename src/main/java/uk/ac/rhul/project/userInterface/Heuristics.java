package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.GameState;

public abstract class Heuristics
{
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
}
