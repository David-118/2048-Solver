package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.Direction;
import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

/**
 * The model used by a javafx interface.
 */
public interface Model
{
    void init(GameConfiguration configuration);
    GameState getGrid();
    int getScore();
    void addUpdateObserver(UpdateObserver handelUpdate);
    void solve(boolean blocking);
}
