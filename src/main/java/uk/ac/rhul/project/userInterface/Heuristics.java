package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.Direction;
import uk.ac.rhul.project.game.GameState;

import java.util.Random;

/**
 * A class containing all the heuristic functions.
 */
public abstract class Heuristics
{
    /**
     * Sums up the value of all the cells in the state.
     * @param state The game state to be evaluated.
     * @return The sum of all the cells.
     */
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

    /**
     * Rewards game states that have larger values towards the bottom.
     * @param state The game state to be evaluated.
     * @return The sum of all the cells multiplied by the (index + 1) of the row they are in..
     */
    public static float largestLower(GameState state)
    {
        float sum = 0f;
        int[][] grid = state.getGrid();
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid.length; j++)
            {
                sum += grid[i][j] * (i+1);
            }
        }
        return sum;
    }

    /**
     * Rewards game states where the values are in zigzag pattern from the top left corner.
     * <p>Based on heuristic from [7]</p>
     * @param state The game state to be evaluated.
     * @return sum of each value in the gird multiplied by a weight matrix.
     */
    public static float snake_4_by_4(GameState state)
    {
        double[] powers = new double[7];

        for (int i = 0; i < 7; i++)
        {
            powers[i] = Math.pow(4, i);
        }

        int[][] weights = new int[][] {
                {15, 14, 13, 12},
                { 8,  9, 10, 11},
                { 7,  6,  5,  4},
                { 0,  1,  2,  3},
        };

        float sum = 0;
        int[][] grid = state.getGrid();

        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[0].length; j++)
            {
                sum += grid[i][j] * Math.pow(4, weights[i][j]);
            }
        }

        return sum;
    }


    /**
     * Rewards games where the largest cells are diagonal from the top left. Penalises when large number are next to
     * small neighbors.
     * <p>Based on heuristic from [8, grid.js:108]</p>
     * @param state The game state to be evaluated.
     * @return sum of  each cell multiplied by an internal weight.
     */
    public static float topLeftCornerProximity_4_by_4(GameState state)
    {
        int[][] weights = new int[][]{
                {6, 5, 4, 1},
                {5, 4, 1, 0},
                {4, 1, 0, -1},
                {1, 0, -1, -2},
        };

        float score = 0;
        float penalty = 0;

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                float cell = state.getGrid()[i][j];

                score += weights[i][j] * cell * cell;

                if (cell != 0)
                {
                    for (Direction dir : Direction.values())
                    {
                        if (state.nextCellInGrid(i, j, dir))
                        {
                            float neighbour = state.getGrid()[i + dir.getRows()][j + dir.getCols()];
                            if (neighbour != 0)
                            {
                                penalty += (Math.abs(neighbour - cell));
                            }
                        }
                    }
                }
            }
        }
        return score - penalty;
    }
}
