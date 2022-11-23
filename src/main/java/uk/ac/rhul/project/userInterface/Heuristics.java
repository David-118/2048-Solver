package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.Direction;
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
                sum += grid[i][j] * (i+1);
            }
        }
        return sum;
    }

    /*
     * Based on heuristic used by [7]
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

    public static float snake_4_by_4_withProximity(GameState state, float penaltyWeight)
    {
        float score = snake_4_by_4(state);
        float penalty = proximityPenalty(state);

        return score - penaltyWeight * penalty;
    }


    /*
     * Based on heuristic from [8, grid.js:108]
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
            }
        }
        return score - proximityPenalty(state);
    }

    public static float proximityPenalty(GameState state)
    {
        float penalty = 0;

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                float cell = state.getGrid()[i][j];

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

        return penalty;
    }
}
