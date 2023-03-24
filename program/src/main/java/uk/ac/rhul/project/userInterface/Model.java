package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;

/**
 * The model used by a javafx interface.
 */
public interface Model
{
    /**
     * Set up a game, and algorithm to solve it based on game configuration.
     * @param configuration Config for the game.
     */
    void init(GameConfiguration configuration);

    /**
     * Get the grid from the 2048 game.
     *
     * @return current game state.
     */
    GameState getGrid();

    /**
     * Get the current score of the 2048 game.
     *
     * @return The score of the current game.
     */
    int getScore();

    /**
     * Sets the function to update view.
     * @param handelUpdate method used to handle updating the grid.
     */
    void addUpdateObserver(UpdateObserver handelUpdate);

    /**
     * Start solving a game.
     * @param blocking if True: run game in main thread
     *                 otherwise: start new thread.
     */
    void solve(boolean blocking);

    /**
     * Enable logging expectimax trees to a directory.
     * @param logDir Directory to save logs to.
     */
    void enableTreeLog(String logDir);
}
