package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.expectimax.StateScoreTracker;
import uk.ac.rhul.project.game.GameState;

public interface View
{
    void addNewGameObserver(NewGameObserver handelNewGame);

    void addSolveObserver(SolveObserver handelSolve);

    void setValues(StateScoreTracker state);

    void updateGrid(StateScoreTracker state);
}
