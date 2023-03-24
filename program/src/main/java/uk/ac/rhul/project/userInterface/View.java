package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.GameState;

import java.io.File;
import java.io.IOException;

/**
 * Interface for all views..
 */
public interface View
{
    /**
     * Set handel for when a new game is created.
     * @param handelNewGame handel for new game
     */
    void addNewGameObserver(NewGameObserver handelNewGame);

    /**
     * Set up handel for solving new game.
     * @param handelSolve handel for solving game.
     */
    void addSolveObserver(SolveObserver handelSolve);

    /**
     * Set the gamestate on the view from the same thread.
     * @param state current game state.
     */
    void setValues(GameState state);

    /**
     * SEt game sate from any thread.
     * @param state current game state.
     */
    void updateGrid(GameState state);

    /**
     * Start a terminal based view.
     * @param log where to log data (if appropriate)
     * @throws IOException unable to write to log file.
     */
    void startIfTerminal(File log) throws IOException;
}
