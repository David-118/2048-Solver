package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

class NodeBehaviourChance implements NodeBehaviour
{
    private final Node[] children;
    private final Random random;
    public static NodeBehaviour generate(GameState state, Random random, int depth)
    {
        NodeBehaviour generated;
        Stream<GameState> childStates = state.getPossibleMutations();

        Node[] childNodes = childStates.map((GameState childState) ->
                new Node(childState, NodeBehaviourMaximize::generate, random)).toArray(Node[]::new);

        Arrays.stream(childNodes).forEach((Node child) -> child.generateChildren(depth));

        if (childNodes.length > 0)
        {
            generated = new NodeBehaviourChance(childNodes, random);
        }
        else
        {
            generated = new LeafNodeBehaviour(state);
        }
        return generated;
    }

    private NodeBehaviourChance(Node[] children, Random random)
    {
        this.children = children;
        this.random = random;
    }

    @Override
    public Node nextNode(Heuristic heuristic) throws EndOfGameException
    {
        int index = random.nextInt(this.children.length);
        return this.children[index];
    }

    @Override
    public float applyHeuristic(Heuristic heuristic)
    {
        // Intend on migrating scores to double, for better java stream support in the future.
        return (float) Arrays.stream(this.children).mapToDouble((Node child) ->
                (double) child.applyHeuristic(heuristic)).average()
                .orElseThrow(() -> new RuntimeException("Chance node has no children"));
    }
}
