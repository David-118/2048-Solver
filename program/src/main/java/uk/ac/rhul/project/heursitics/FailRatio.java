package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

/**
 * If the game state would fail multiply the heuristic score by a given value.
 */
public class FailRatio extends FailWrapper {
    /**
     * Ratio to multiply heuristic score by.
     */
    private final double ratio;

    /**
     * Create a new fail ratio.
     *
     * @param heuristic Heuristic to evaluate.
     * @param ratio     Ratio to multiply heuristic by if the state fails.
     */
    public FailRatio(Heuristic heuristic, double ratio) {
        super(heuristic);
        this.ratio = ratio;
    }

    /**
     * Apply the heuristic to a given game state.
     *
     * @param state The game state to be evaluated.
     * @return heuristic score of game state.
     */
    @Override
    public double heuristic(GameState state) {
        return this.applyChildHeuristic(state) * (this.isLoss(state) ? ratio : 1);
    }
}
