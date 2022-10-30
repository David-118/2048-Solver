package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.Direction;

public interface Model
{
    void init();
    void move(Direction dir);
    int[][] getGrid();
    int getScore();

}
