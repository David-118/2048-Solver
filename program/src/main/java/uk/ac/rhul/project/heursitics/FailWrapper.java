package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

public class FailWrapper implements Heuristic
{
    private final Heuristic heuristic;
    private final double score;
    public FailWrapper(Heuristic heuristic, double score)
    {
        this.heuristic = heuristic;
        this.score = score;
    }

    @Override
    public double heuristic(GameState state)
    {
        if (loss(state)) return score;
        return heuristic.heuristic(state);
    }

    public boolean loss(GameState state)
    {
        for (int i = 0; i < state.getHeight(); i++)
        {
            for (int j = 0; j < state.getWidth(); j++)
            {
                if (state.getGrid()[i][j] == 0) return false;
                if (i > 1 && state.getGrid()[i - 1][j] == state.getGrid()[i][j]) return false;
                if (j > 1 && state.getGrid()[i][j - 1] == state.getGrid()[i][j]) return false;
            }
        }
        return true;
    }

    @Override
    public String getName()
    {
        return heuristic.getName() + "(With Fail)";
    }
}
