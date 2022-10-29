package uk.ac.rhul.project;

import uk.ac.rhul.project.game.Direction;

@FunctionalInterface
public interface MoveObserver
{
    void notifyObservers(Direction dir);
}