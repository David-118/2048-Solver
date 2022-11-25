package uk.ac.rhul.project.userInterface;

/**
 * Observer used to update the game while the solver is playing the game.
 */
@FunctionalInterface
public interface UpdateObserver
{
    /**
     * Interface for methods to be used as an updateObserver.
     * @param grid 2D array represent the current state of the game.
     * @param score Score in the current game.
     */
    void notifyObservers(int[][] grid, int score);
}
