package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.GameState;

/**
 * The functional interface that all heuristic functions should meat.
 */
@FunctionalInterface
public interface Heuristic
{
    /**
     * Interface for the heuristic function.
     * @param state The game state to be evaluated.
     * @return A float value representing how good the state is.
     */
    float heuristic(GameState state);
}
