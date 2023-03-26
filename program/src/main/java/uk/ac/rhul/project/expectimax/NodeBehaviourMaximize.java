package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Makes a node behavior as a maximizing node.
 */
class NodeBehaviourMaximize implements NodeBehaviour {
    /**
     * Stores the nodes children.
     */
    private final Node[] children;

    /**
     * Make a maximizing node.
     * @param children The nodes children.
     */
    private NodeBehaviourMaximize(Node[] children) {
        this.children = children;
    }

    /**
     * Generate the node behaviour for a maximizing node.
     *
     * @param state   The state of the node.
     * @param random  Random  number generator passed down to child node.
     * @param depth   Depth of the subtree starting from this node.
     * @param count4  Number of '4s' before a node is pruned
     * @param layer   Layer in the tree currently on.
     * @return if the node children a maximizing node is returned <br>
     *         otherwise a leaf node behaviour is returned
     */
    public static NodeBehaviour generate(GameState state, Random random, int depth, int count4, int layer) {
        NodeBehaviour generated;
        List<GameState> childStates = state.getPossibleMoves();

        Node[] childNodes = new Node[childStates.size()];

        for (int i = 0; i < childNodes.length; i++) {
            childNodes[i] = new Node(childStates.get(i), NodeBehaviourChance::generate, random);
        }

        Arrays.stream(childNodes).parallel().forEach((Node child) -> child.generateChildren(depth, count4, layer));

        if (childNodes.length > 0) {
            generated = new NodeBehaviourMaximize(childNodes);
        } else {
            generated = new LeafNodeBehaviour(state);
        }
        return generated;
    }

    /**
     * Runs generateChildren on child nodes.
     *
     * @param state The state of the current game.
     * @param random The random number generating being used for the game.
     * @param depth Depth of the tree.
     * @param count4 Number of fours that have occoured so far.
     * @param layer Current layer of the tree.
     * @return this node behaviour.
     */
    public NodeBehaviour generated(GameState state, Random random, int depth, int count4, int layer) {
        Arrays.stream(this.children).parallel().forEach((Node child) -> {
            child.generateChildren(depth, count4, layer);
        });
        return this;
    }

    /**
     * Picks the best move, as evaluated by a heuristic.
     *
     * @param heuristic Heuristic to use in picking next node.
     * @return Highest scoring child node.
     */
    @Override
    public Node nextNode(Heuristic heuristic) {
        return Arrays.stream(this.children).unordered().parallel().max(
                        Comparator.comparingDouble((Node a) -> a.applyHeuristic(heuristic)))
                .orElseThrow(() -> {
                    throw new RuntimeException("Max node has no children");
                });
    }

    /**
     * Calcualtes score of the node based on children.
     *
     * @param heuristic Heuristic to apply.
     * @return The score of the highest scoring child.
     */
    @Override
    public double applyHeuristic(Heuristic heuristic) {
        return Arrays.stream(this.children).unordered().parallel()
                .mapToDouble((Node node) -> node.applyHeuristic(heuristic)).max()
                .orElseThrow(() -> {
                    throw new RuntimeException("Max node has no children");
                });

    }

    /**
     * Convert hte node behaviour and child nodes to a string.
     * @param indent Depth of this node in the tree.
     * @param heuristic Heuristic score for this Node.
     * @return 'M' + the child nodes string representation.
     */
    @Override
    public String toTxt(int indent, Heuristic heuristic) {
        StringBuilder childBuilder = new StringBuilder("M\n");
        for (Node node : this.children) {
            childBuilder.append(node.toTxt(indent + 1, heuristic));
        }
        return childBuilder.toString();
    }

    /**
     * Count the number of nodes i levels down.
     * @param i level down the tree.
     * @return number of nodes i levels down.
     */
    @Override
    public int baseLineCount(int i) {
        return Arrays.stream(this.children).mapToInt(c -> c.getBaselineCount(i)).sum();
    }
}
