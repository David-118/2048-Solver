package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

public class LargestRight implements Heuristic
{
    /**
     * Rewards game states that have larger values towards the right.
     * @param state The game state to be evaluated.
     * @return The sum of all the cells multiplied by the (index + 1) of the row they are in..
     */
    @Override
    public float heuristic(GameState state)
    {
        float sum = 0f;
        int[][] grid = state.getGrid();
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[0].length; j++)
            {
                sum += grid[i][j] * (j+1);
            }
        }
        return sum;
    }


    /**
     * Returns the name of the heuristic function.
     * @return "Largest Lower"
     */
    @Override
    public String getName()
    {
        return "Largest Right";
    }
}
