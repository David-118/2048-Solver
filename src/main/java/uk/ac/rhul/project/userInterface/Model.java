package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.Direction;

/**
 * The model used by a javafx interface.
 */
public interface Model
{
    void init();
    void init(int height, int width);
    int[][] getGrid();
    int getScore();
    void addUpdateObserver(UpdateObserver handelUpdate);
    public void solve();
}
