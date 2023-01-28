package uk.ac.rhul.project.expectimax;

import javafx.util.Pair;
import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

class NodeBehaviourChance implements NodeBehaviour
{
    private final Node[] children;
    private final Random random;
    public static NodeBehaviour generate(GameState state, Random random, int depth, StateScoreTracker stateScoreTracker)
    {
        NodeBehaviour generated;
        List<Pair<GameState, Double>> childStates = state.getPossibleMutations();

        Node[] childNodes = new Node[childStates.size()];

        for (int i = 0; i < childNodes.length; i++)
        {
            childNodes[i] = new Node(childStates.get(i).getKey(), NodeBehaviourMaximize::generate, random, stateScoreTracker);
            childNodes[i].setWeight(childStates.get(i).getValue());
        }


        Arrays.stream(childNodes).parallel().forEach((Node child) -> child.generateChildren(depth));

        if (childNodes.length > 0)
        {
            generated = new NodeBehaviourChance(childNodes, random);
        }
        else
        {
            generated = new LeafNodeBehaviour(state, stateScoreTracker);
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
}
