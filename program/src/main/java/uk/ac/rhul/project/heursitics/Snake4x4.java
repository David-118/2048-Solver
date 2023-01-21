package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

/**
 * Snake4x4 4x4 heurstic is based on heurstic found in [7]
 * Only works on a 4x4 grid
 */
public class Snake4x4 implements Heuristic
{
    /**
     * Rewards game states where the values are in zigzag pattern from the top left corner.
     * <p>Based on heuristic from [7]</p>
     * @param state The game state to be evaluated.
     * @return sum of each value in the gird multiplied by a weight matrix.
     */
    public float heuristic(GameState state)
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
     * Returns the name of the heuristic.
     * @return "Snake4x4 4x4"
     */
    @Override
    public String getName()
    {
        return "Snake (4x4)";
    }
}
