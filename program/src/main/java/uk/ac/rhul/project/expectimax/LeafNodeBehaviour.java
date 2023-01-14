package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

public class LeafNodeBehaviour implements NodeBehaviour
{
    private GameState state;
    public LeafNodeBehaviour(GameState state)
    {
        this.state = state;
    }
    @Override
    public Node nextNode(Heuristic heuristic) throws EndOfGameException
    {
        throw new EndOfGameException(this.state);
    }

    @Override
    public float applyHeuristic(Heuristic heuristic)
    {
        return state.applyHeuristic(heuristic);
    }
}
