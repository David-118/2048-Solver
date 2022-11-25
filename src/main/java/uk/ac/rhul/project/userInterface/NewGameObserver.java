package uk.ac.rhul.project.userInterface;

/**
 * Interface for observer that makes new game.
 */
@FunctionalInterface
public interface NewGameObserver
{
    /**
     * Interface functions called when a new game is created.
     * @param height
     * @param width
     */
    void notifyObservers(int height, int width);
}
