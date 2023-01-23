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

        for (int i = 0; i < childNodes.length; i+=2)
        {
            childNodes[i].setWeight(0.9f);
            childNodes[i+1].setWeight(0.1f);
        }

        Arrays.stream(childNodes).parallel().forEach((Node child) -> child.generateChildren(depth));

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
        double prob = random.nextDouble() * (this.children.length / 2D);

        int index = (int) (Math.floor(prob) * 2);
        double node2or4 = prob % 1;
        if (node2or4 > 0.9) index += 1;
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
