package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.Direction;

public interface Model
{
    void init();
    void init(int height, int width);
    void move(Direction dir);
    int[][] getGrid();
    int getScore();
    void addUpdateObserver(UpdateObserver handelUpdate);
    public void solve();
}
