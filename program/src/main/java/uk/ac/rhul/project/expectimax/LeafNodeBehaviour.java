package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

public class LeafNodeBehaviour implements NodeBehaviour
{
    private GameState state;
    private StateScoreTracker stateScoreTracker;
    public LeafNodeBehaviour(GameState state, StateScoreTracker stateScoreTracker)
    {
        this.state = state;
        this.stateScoreTracker = stateScoreTracker;
    }
    @Override
    public Node nextNode(Heuristic heuristic) throws EndOfGameException
    {
        throw new EndOfGameException(this.stateScoreTracker);
    }

    @Override
    public double applyHeuristic(Heuristic heuristic)
    {
        return state.applyHeuristic(heuristic);
    }
}
