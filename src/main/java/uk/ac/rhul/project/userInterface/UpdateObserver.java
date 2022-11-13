package uk.ac.rhul.project.userInterface;

@FunctionalInterface
public interface UpdateObserver
{
    void notifyObservers(int[][] grid, int score);
}
