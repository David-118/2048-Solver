package uk.ac.rhul.project.userInterface;

@FunctionalInterface
public interface NewGameObserver
{
    void notifyObservers(int height, int width);
}
