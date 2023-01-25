package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

class NodeBehaviourMaximize implements NodeBehaviour
{
    private final Node[] children;

    public static NodeBehaviour generate(GameState state, Random random, int depth)
    {
        NodeBehaviour generated;
        List<GameState> childStates = state.getPossibleMoves();

        Node[] childNodes = new Node[childStates.size()];

        for (int i = 0; i < childNodes.length; i++)
        {
            childNodes[i] = new Node(childStates.get(i), NodeBehaviourChance::generate, random);
        }

        Arrays.stream(childNodes).parallel().forEach((Node child) -> child.generateChildren(depth));

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
        return Arrays.stream(this.children).parallel().max((Node a, Node b) ->
                Float.compare(a.applyHeuristic(heuristic), b.applyHeuristic(heuristic)))
                .orElseThrow(() -> {throw new RuntimeException("Max node has no children");});
    }

    @Override
    public float applyHeuristic(Heuristic heuristic)
    {
        return (float) Arrays.stream(this.children).parallel()
                .mapToDouble((Node node) -> (double) node.applyHeuristic(heuristic)).max()
                .orElseThrow(() -> {throw new RuntimeException("Max node has no children");});

    }
}
