package uk.ac.rhul.project.userInterface;

/**
 * Observer used to start solving a game.
 */
@FunctionalInterface
public interface SolveObserver
{
    /**
     * Interface for methods used as SolveObserver.
     */
    void notifyObserver();
}
