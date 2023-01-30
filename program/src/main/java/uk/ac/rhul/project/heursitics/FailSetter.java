package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

public class FailSetter extends FailWrapper
{
    private final double failScore;

    public FailSetter(Heuristic heuristic, double failScore)
    {
        super(heuristic);
        this.failScore = failScore;
    }


    @Override
    public double heuristic(GameState state)
    {
        if (this.isLoss(state)) return failScore;
        return this.applyChildHeuristic(state);
    }
}
