package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.Direction;
import uk.ac.rhul.project.game.GameState;

/**
 * Diagonal4x4 is heurstic based on [8, grid.js:108]
 */
public class Diagonal4x4 implements Heuristic
{
    /**
     * Rewards games where the largest cells are diagonal from the top left. Penalises when large number are next to
     * small neighbors.
     * <p>Based on heuristic from [8, grid.js:108]</p>
     * @param state The game state to be evaluated.
     * @return sum of  each cell multiplied by an internal weight.
     */
    public float heuristic(GameState state)
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

    public String getName()
    {
        return "Diagonal (4 x 4)";
    }

}
