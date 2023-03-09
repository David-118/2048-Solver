package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class NodeBehaviourChance implements NodeBehaviour
{
    private final Node[] children;
    private final Random random;
    public static NodeBehaviour generate(GameState state, Random random, int depth, int count4, int layer)
    {
        NodeBehaviour generated;
        GameState[] childStates = state.getPossibleMutations();

        Node[] childNodes = new Node[childStates.length];

        for (int i = 0; i < childNodes.length; i++)
        {
            childNodes[i] = new Node(childStates[i], NodeBehaviourMaximize::generate, random, ExpectimaxTree.BASELINE_DEPTH);
        }


        Arrays.stream(childNodes).parallel().forEach((Node child) -> {
            child.generateChildren(depth, count4, layer);
        });

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
        float prob = random.nextFloat();
        float current = 0;
        for (Node child : children)
        {
            current += child.getWeight();
            if (prob < current)
            {
                return child;
            } 
        }
        return this.children[0];
    }

    @Override
    public double applyHeuristic(Heuristic heuristic)
    {
        return Arrays.stream(this.children).unordered().parallel().mapToDouble((Node child) ->
                child.applyHeuristic(heuristic)).sum();
    }

    @Override
    public String toTxt(int indent, Heuristic heuristic)
    {
        StringBuilder childBuilder = new StringBuilder("C\n");
        for (Node node: this.children)
        {
            childBuilder.append(node.toTxt(indent + 1, heuristic));
        }
        return childBuilder.toString();
    }

    @Override
    public int baseLineCount(int i) {
        return Arrays.stream(this.children).mapToInt(c -> c.getBaselineCount((i))).sum();
    }
}
