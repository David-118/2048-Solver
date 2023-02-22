package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.GameState;

import java.io.File;
import java.io.IOException;

public interface View
{
    void addNewGameObserver(NewGameObserver handelNewGame);

    void addSolveObserver(SolveObserver handelSolve);

    void setValues(GameState state);

    void updateGrid(GameState state);

    void startIfTerminal(File log) throws IOException;
}
