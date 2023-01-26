package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;



/**
 * Created heuristic based on nnenneo's heuristic in [10].
 * Has many factors of such as:
 * <ul>
 *     <li>Losing state</li>
 *     <li></li>
 * </ul>
 */
public class Monotonic implements Heuristic
{
    @Override
    public double heuristic(GameState state)
    {
        return sumEdges(state) - monotonicPenalty(state) + freeCells(state);
    }

    public double sumEdges(GameState state)
    {
        float sum = 0;
        int height = state.getHeight(),
                width = state.getWidth();

        for (int i = 0; i < height; i++)
        {
            sum += state.getGrid()[i][0] + state.getGrid()[i][width - 1];
        }

        for (int j = 0; j < width; j++)
        {
            sum += state.getGrid()[0][j] + state.getGrid()[height - 1][j];
        }

        return sum;
    }

    public double monotonicPenalty(GameState state)
    {
        double monotonicity_left = 0;
        double monotonicity_right = 0;

        for (int i = 0; i < state.getHeight(); i++)
        {
            for (int j = 1; j < state.getWidth(); j++)
            {
                double leftmost = state.getGrid()[i][j-1];
                double rightmost = state.getGrid()[i][j];
                if (leftmost > rightmost)
                {
                    monotonicity_left += leftmost - rightmost;
                } else
                {
                    monotonicity_right += rightmost - leftmost;
                }
            }
        }

        double monotonicity_upper = 0;
        double monotonicity_lower = 0;

        for (int j = 0; j < state.getWidth(); j++)
        {
            for (int i = 1; i < state.getHeight(); i++)
            {
                double uppermost = state.getGrid()[i-1][j];
                double lowermost = state.getGrid()[i][j];
                if (uppermost > lowermost)
                {
                    monotonicity_upper += uppermost - lowermost;
                } else
                {
                    monotonicity_lower += lowermost - uppermost;
                }
            }
        }

        return Math.min(monotonicity_left, monotonicity_right) + Math.min(monotonicity_upper, monotonicity_lower);
    }

    public double freeCells(GameState state)
    {
        float count = 0;
        for (int i = 0; i < state.getHeight(); i++)
        {
            for (int j = 0; j < state.getWidth(); j++)
            {
                count += 1f;
            }
        }
        return count * count;
    }
    @Override
    public String getName()
    {
        return String.format("Monotonic");
    }
}
