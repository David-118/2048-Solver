package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.expectimax.StateScoreTracker;
import uk.ac.rhul.project.game.GameConfiguration;

/**
 * The model used by a javafx interface.
 */
public interface Model
{
    void init(GameConfiguration configuration);
    StateScoreTracker getGrid();
    int getScore();
    void addUpdateObserver(UpdateObserver handelUpdate);
    void solve(boolean blocking);
}
