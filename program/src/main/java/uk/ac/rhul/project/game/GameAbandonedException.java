package uk.ac.rhul.project.game;

public class GameAbandonedException extends EndOfGameException
{
    public GameAbandonedException(GameState state)
    {
        super(state);
    }
}

