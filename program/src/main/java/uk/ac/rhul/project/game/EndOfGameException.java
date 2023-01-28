package uk.ac.rhul.project.game;

public class EndOfGameException extends Exception
{
    private GameState state;
    public EndOfGameException(GameState state)
    {
        this.state = state;
    }

    public GameState getFinalState()
    {
        return state;
    }
}
