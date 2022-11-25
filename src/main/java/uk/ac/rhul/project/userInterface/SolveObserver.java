package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.heursitics.Heuristic;

/**
 * Observer used to start solving a game.
 */
@FunctionalInterface
public interface SolveObserver
{
    /**
     * Interface for methods used as SolveObserver.
     * @param blocking, should this call block the main thread.
     */
    void notifyObserver(boolean blocking, Heuristic heuristic);
}
