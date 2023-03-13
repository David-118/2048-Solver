package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class NodeBehaviourMaximize implements NodeBehaviour
{
    private final Node[] children;

    public static NodeBehaviour generate(GameState state, Random random, int depth, int count4, int layer)
    {
        NodeBehaviour generated;
        List<GameState> childStates = state.getPossibleMoves();

        Node[] childNodes = new Node[childStates.size()];

        for (int i = 0; i < childNodes.length; i++)
        {
            childNodes[i] = new Node(childStates.get(i), NodeBehaviourChance::generate, random, ExpectimaxTree.BASELINE_DEPTH);
        }

        Arrays.stream(childNodes).parallel().forEach((Node child) -> child.generateChildren(depth, count4, layer));

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

    public NodeBehaviour generated(GameState state, Random random, int depth, int count4, int layer) {
        Arrays.stream(this.children).parallel().forEach((Node child) -> {
            child.generateChildren(depth, count4, layer);
        });
        return this;
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
    public String toTxt(int indent, Heuristic heuristic)
    {
        StringBuilder childBuilder = new StringBuilder("M\n");
        for (Node node: this.children)
        {
            childBuilder.append(node.toTxt(indent + 1, heuristic));
        }
        return childBuilder.toString();
    }

    @Override
    public int baseLineCount(int i) {
        return Arrays.stream(this.children).mapToInt(c -> c.getBaselineCount(i)).sum();
    }
}
