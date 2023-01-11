package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class NodeBehaviourMaximize implements NodeBehaviour
{
    private final Node[] children;

    public static NodeBehaviour generate(GameState state)
    {
        NodeBehaviour generated;
        Stream<GameState> childStates = state.getPossibleMoves();

        Node[] childNodes = childStates.map(Node::new).toArray(Node[]::new);

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
        Optional<Float> score =
                Arrays.stream(this.children).parallel()
                        .map((Node node) -> node.applyHeuristic(heuristic)).max(Float::compareTo);

        if (score.isPresent())
        {
            return score.get();
        } else
        {
            throw new RuntimeException("Maximizing node has no children");
        }
    }
}
