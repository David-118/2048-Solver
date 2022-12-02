package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

/**
 * Heuristic which sums up the value of every cell.
 * Works on any n x m game.
 */
public class SumCells implements Heuristic
{
    /**
     * Sums up the value of all the cells in the state.
     * @param state The game state to be evaluated.
     * @return The sum of all the cells.
     */
    @Override
    public float heuristic(GameState state)
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
     * Returns the name of the heuristic function.
     * @return "Sum Cells"
     */
    @Override
    public String getName()
    {
        return "Sum Cells";
    }
}
