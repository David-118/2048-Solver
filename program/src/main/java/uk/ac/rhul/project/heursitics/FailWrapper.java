package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

/**
 * A heuristic which modifies the value of another heuristic based on if the game state looes.
 */
public abstract  class FailWrapper implements Heuristic
{
    /**
     * Heuristic to evaluate
     */
    private final Heuristic heuristic;

    /**
     * Create a new fail wrapper.
     * @param heuristic Heuristic to wrap.
     */

    public FailWrapper(Heuristic heuristic)
    {
        this.heuristic = heuristic;
    }

    /**
     * Apply the heuristic to a given game state.
     *
     * @param state The game state to be evaluated.
     * @return heuristic score of game state.
     */
    @Override
    public abstract double heuristic(GameState state);

    /**
     * Apply the wrapped heuristic to a game state.
     * @param state The gaem sate to be evaluated.
     * @return heuristic score of the game state.
     */
    protected double applyChildHeuristic(GameState state)
    {
        return heuristic.heuristic(state);
    }

    /**
     * Checks if the game state will lose.
     * @param state Game state to check.
     * @return true if game state is a loss.
     *
     */
    protected boolean isLoss(GameState state)
    {
        for (int i = 0; i < state.getHeight(); i++)
        {
            for (int j = 0; j < state.getWidth(); j++)
            {
                if (state.getGrid()[i][j] == 0) return false;
                if (i > 1 && state.getGrid()[i - 1][j] == state.getGrid()[i][j]) return false;
                if (j > 1 && state.getGrid()[i][j - 1] == state.getGrid()[i][j]) return false;
            }
        }
        return true;
    }

    /**
     * Returns name of heurstic
     * @return {wrapped heurstic name}(with Fail)
     */
    @Override
    public String getName()
    {
        return heuristic.getName() + "(With Fail)";
    }
}
