package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

/**
 * If the game state looes return a constant value instead of the typical heuristic score.
 */
public class FailSetter extends FailWrapper
{
    /**
     * Score to return in event of fail.
     */
    private final double failScore;

    /**
     * Create a new fail setter.
     * @param heuristic Heuristic to evaluate.
     * @param failScore Value to return if game state looses.
     */
    public FailSetter(Heuristic heuristic, double failScore)
    {
        super(heuristic);
        this.failScore = failScore;
    }


    /**
     * Evaluate heurstic of game state.
     * @param state The game state to be evaluated.
     * @return if loss: failScore
     *         otherwise: heuristic score of child.
     */
    @Override
    public double heuristic(GameState state)
    {
        if (this.isLoss(state)) return failScore;
        return this.applyChildHeuristic(state);
    }
}
