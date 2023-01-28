package uk.ac.rhul.project.expectimax;

import javafx.util.Pair;
import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.*;
import java.util.stream.IntStream;

class NodeBehaviourMaximize implements NodeBehaviour
{
    private final Node[] children;
    private final int[] deltas;
    private final StateScoreTracker scoreTracker;

    public static NodeBehaviour generate(GameState state, Random random, int depth, StateScoreTracker stateScoreTracker)
    {
        NodeBehaviour generated;
        List<Pair<GameState, Integer>> childStates = state.getPossibleMoves();

        Node[] childNodes = new Node[childStates.size()];
        int[] deltas = new int[childStates.size()];

        for (int i = 0; i < childNodes.length; i++)
        {
            childNodes[i] = new Node(childStates.get(i).getKey(), NodeBehaviourChance::generate, random, stateScoreTracker);
            deltas[i] = childStates.get(i).getValue();
        }

        Arrays.stream(childNodes).parallel().forEach((Node child) -> child.generateChildren(depth));

        if (childNodes.length > 0)
        {
            generated = new NodeBehaviourMaximize(childNodes, deltas, stateScoreTracker);
        }
        else
        {
            generated = new LeafNodeBehaviour(state, stateScoreTracker);
        }
        return generated;
    }

    private NodeBehaviourMaximize(Node[] children, int[] deltas, StateScoreTracker stateScoreTracker)
    {
        this.children = children;
        this.deltas = deltas;
        this.scoreTracker = stateScoreTracker;
    }
    @Override
    public Node nextNode(Heuristic heuristic) throws EndOfGameException
    {
        Pair<Node, Integer> pair = IntStream.range(0, children.length).parallel().unordered()
                .mapToObj((int i) -> new Pair<>(this.children[i], this.deltas[i]))
                .max((Comparator.comparingDouble((Pair<Node, Integer> a) -> a.getKey().applyHeuristic(heuristic))))
                .orElseThrow(() -> {throw new RuntimeException("Max node has no children");});

        this.scoreTracker.increment(pair.getValue());

        return pair.getKey();
    }

    @Override
    public double applyHeuristic(Heuristic heuristic)
    {
        return Arrays.stream(this.children).unordered().parallel()
                .mapToDouble((Node node) -> node.applyHeuristic(heuristic)).max()
                .orElseThrow(() -> {throw new RuntimeException("Max node has no children");});

    }
}
