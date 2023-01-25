package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

/**
 * The functional interface that all heuristic functions should meat.
 */
public interface Heuristic
{
    /**
     * Computes the value of the heuristic.
     * @param state The game state to be evaluated.
     * @return A float value representing how good the state is.
     */
    double heuristic(GameState state);

    /**
     * Returns the name of heuristic function
     * @return name of the heuristic function
     */
    String getName();
}
