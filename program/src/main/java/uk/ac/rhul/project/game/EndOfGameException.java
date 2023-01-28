package uk.ac.rhul.project.game;

import uk.ac.rhul.project.expectimax.StateScoreTracker;

public class EndOfGameException extends Exception
{
    private StateScoreTracker state;
    public EndOfGameException(StateScoreTracker state)
    {
        this.state = state;
    }

    public StateScoreTracker getFinalState()
    {
        return state;
    }
}
