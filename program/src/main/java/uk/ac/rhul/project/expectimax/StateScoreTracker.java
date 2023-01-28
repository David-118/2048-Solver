package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;

public class StateScoreTracker
{
    int score = 0;
    GameState state;

    public void increment(int i)
    {
        this.score += i;
    }

    public int getScore()
    {
        return this.score;
    }

    public void setState(GameState state)
    {
        this.state = state;
    }

    public GameState getState()
    {
        return this.state;
    }
}
