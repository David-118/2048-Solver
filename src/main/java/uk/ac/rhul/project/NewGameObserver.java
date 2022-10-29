package uk.ac.rhul.project;

@FunctionalInterface
public interface NewGameObserver
{
    void notifyObservers(int height, int width);
}
