package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.*;

class NodeBehaviourMaximize implements NodeBehaviour
{
    private final Node[] children;

    public static NodeBehaviour generate(GameState state, Random random, int depth,
                                         int abandonCount, double abandonThreshold)
    {
        NodeBehaviour generated;
        List<GameState> childStates = state.getPossibleMoves();

        if (state.applyHeuristic() < abandonThreshold) abandonCount--;

        Node[] childNodes = new Node[childStates.size()];

        for (int i = 0; i < childNodes.length; i++)
        {
            childNodes[i] = new Node(childStates.get(i), NodeBehaviourChance::generate, random);
        }


        int finalAbandonCount = abandonCount;
        Arrays.stream(childNodes).parallel().unordered().forEach((Node child) ->
                child.generateChildren(depth, finalAbandonCount, abandonThreshold));

        if (childNodes.length > 0)
        {
            generated = new NodeBehaviourMaximize(childNodes);
        }
        else
        {
            generated = new LeafNodeBehaviour(state);
        }
        return generated;
    }

    private NodeBehaviourMaximize(Node[] children)
    {
        this.children = children;
    }
    @Override
    public Node nextNode(Heuristic heuristic) throws EndOfGameException
    {
        return Arrays.stream(this.children).unordered().parallel().max(
                Comparator.comparingDouble((Node a) -> a.applyHeuristic(heuristic)))
                .orElseThrow(() -> {throw new RuntimeException("Max node has no children");});
    }

    @Override
    public double applyHeuristic(Heuristic heuristic)
    {
        return Arrays.stream(this.children).unordered().parallel()
                .mapToDouble((Node node) -> node.applyHeuristic(heuristic)).max()
                .orElseThrow(() -> {throw new RuntimeException("Max node has no children");});

    }

    @Override
    public Node[] getChildren()
    {
        return children;
    }
}
