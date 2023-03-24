package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;


/**
 * Created heuristic based on nnenneo's heuristic in [10].
 * Has many factors of such as:
 * <ul>
 *     <li>sum values on the edge of the gird</li>
 *     <li>Monotony of the grid in vertical and horizontal directions</li>
 *     <li>Number of free cells in the grid</li>
 * </ul>
 * <p>
 * It is recommended to use it with the FailSetter, with a value of -1000.
 */
public class Monotonic implements Heuristic {
    /**
     * Apply the heuristic to game state.
     *
     * @param state The game state to be evaluated.
     * @return Heuristic score of the game state.
     */
    @Override
    public double heuristic(GameState state) {
        return sumEdges(state) - monotonicPenalty(state) + freeCells(state);
    }

    /**
     * Total the values on the edge of the game state.
     * @param state The game state to be evaluated.
     * @return Total from summing tiles on the edge (corners get counted twice)
     */
    public double sumEdges(GameState state) {
        float sum = 0;
        int height = state.getHeight(),
                width = state.getWidth();

        for (int i = 0; i < height; i++) {
            sum += state.getGrid()[i][0] + state.getGrid()[i][width - 1];
        }

        for (int j = 0; j < width; j++) {
            sum += state.getGrid()[0][j] + state.getGrid()[height - 1][j];
        }

        return sum;
    }

    /**
     * Returns a penalty for any rows and cols that are not completely monotonic in one direction.
     * @param state The game state to be evaluated.
     * @return penalty for the monotony of the grid.
     */
    public double monotonicPenalty(GameState state) {
        double monotonicity_left = 0;
        double monotonicity_right = 0;

        for (int i = 0; i < state.getHeight(); i++) {
            for (int j = 1; j < state.getWidth(); j++) {
                double leftmost = state.getGrid()[i][j - 1];
                double rightmost = state.getGrid()[i][j];
                if (leftmost > rightmost) {
                    monotonicity_left += leftmost - rightmost;
                } else {
                    monotonicity_right += rightmost - leftmost;
                }
            }
        }

        double monotonicity_upper = 0;
        double monotonicity_lower = 0;

        for (int j = 0; j < state.getWidth(); j++) {
            for (int i = 1; i < state.getHeight(); i++) {
                double uppermost = state.getGrid()[i - 1][j];
                double lowermost = state.getGrid()[i][j];
                if (uppermost > lowermost) {
                    monotonicity_upper += uppermost - lowermost;
                } else {
                    monotonicity_lower += lowermost - uppermost;
                }
            }
        }

        return Math.min(monotonicity_left, monotonicity_right) + Math.min(monotonicity_upper, monotonicity_lower);
    }

    /**
     * Counts the number of free cells in the grid.
     *
     * @param state Game sate being evaluated.
     * @return Number of free cells in the grid squared.
     */
    public double freeCells(GameState state) {
        float count = 0;
        for (int i = 0; i < state.getHeight(); i++) {
            for (int j = 0; j < state.getWidth(); j++) {
                if (state.getGrid()[i][j] == 0) count += 1D;
            }
        }
        return count * count;
    }

    /**
     * Returns the name of the heuristic.
     * @return Monotonic
     */
    @Override
    public String getName() {
        return "Monotonic";
    }
}
