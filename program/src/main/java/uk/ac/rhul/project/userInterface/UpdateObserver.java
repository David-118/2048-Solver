package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.GameState;

/**
 * Observer used to update the game while the solver is playing the game.
 */
@FunctionalInterface
public interface UpdateObserver
{
    /**
     * Interface for methods to be used as an updateObserver.
     * @param gameState Current state of the game.
     */
    void notifyObservers(GameState gameState);
}
