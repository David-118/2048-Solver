package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameAbandonedException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Random;

public class NodeBehaviourPruned implements NodeBehaviour
{
    private GameState state;
    public static NodeBehaviour generate(GameState state, Random random, int depth)
    {
        return new NodeBehaviourPruned(state);
    }

    public NodeBehaviourPruned(GameState state)
    {
        this.state = state;
    }

    @Override
    public Node nextNode(Heuristic heuristic) throws EndOfGameException
    {
        throw new GameAbandonedException(state);
    }

    @Override
    public double applyHeuristic(Heuristic heuristic)
    {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public Node[] getChildren()
    {
        return new Node[0];
    }
}
