package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

public class FailRatio extends FailWrapper
{
    private final double ratio;

    public FailRatio(Heuristic heuristic, double ratio)
    {
        super(heuristic);
        this.ratio = ratio;
    }

    @Override
    public double heuristic(GameState state)
    {
        return this.applyChildHeuristic(state) * (this.isLoss(state) ? ratio : 1);
    }
}
