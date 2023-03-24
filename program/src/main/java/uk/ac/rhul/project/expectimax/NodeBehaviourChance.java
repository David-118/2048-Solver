package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Arrays;
import java.util.Random;

/**
 * The NodeBehaviourChance makes a Node act as a  like a chance node.
 */
class NodeBehaviourChance implements NodeBehaviour {
    /**
     * The children of the Node.
     */
    private final Node[] children;

    /**
     * Random number generator to pick the next node.
     */
    private final Random random;

    /**
     * Create new a NodeBehaviourChance
     * @param children Node's children.
     * @param random   Random number generator to pick next node.
     */
    private NodeBehaviourChance(Node[] children, Random random) {
        this.children = children;
        this.random = random;
    }

    /**
     * Generate the node behaviour for a chance node.
     *
     * @param state   The state of the node.
     * @param random  Random  number generater used to pick next node, and passed down to child node.
     * @param depth   Depth of the subtree starting from this node.
     * @param count4  Number of '4s' before a node is pruned
     * @param layer   Layer in the tree currently on.
     * @return if the node children a chance node is returned <br>
     *         otherwise a leaf node behaviour is returned
     */
    public static NodeBehaviour generate(GameState state, Random random, int depth, int count4, int layer) {
        NodeBehaviour generated;
        GameState[] childStates = state.getPossibleMutations();

        Node[] childNodes = new Node[childStates.length];

        for (int i = 0; i < childNodes.length; i++) {
            childNodes[i] = new Node(childStates[i], NodeBehaviourMaximize::generate, random);
        }


        Arrays.stream(childNodes).parallel().forEach((Node child) -> {
            child.generateChildren(depth, count4, layer);
        });

        if (childNodes.length > 0) {
            generated = new NodeBehaviourChance(childNodes, random);
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
     * Returns a random node.
     * @param heuristic Ignored.
     * @return the next node.
     */
    @Override
    public Node nextNode(Heuristic heuristic) {
        float prob = random.nextFloat();
        float current = 0;
        for (Node child : children) {
            current += child.getWeight();
            if (prob < current) {
                return child;
            }
        }
        return this.children[0];
    }

    /**
     * Applies heuristic to children and returns weighted average score.
     *
     * @param heuristic Heuristic to apply.
     * @return returns the score for the node.
     */
    @Override
    public double applyHeuristic(Heuristic heuristic) {
        return Arrays.stream(this.children).unordered().parallel().mapToDouble((Node child) -> child.applyHeuristic(heuristic)).sum();
    }

    /**
     * Create text representation for the node behaviour and the child nodes.
     * @param indent Depth of this node in the tree.
     * @param heuristic Heuristic score for this Node.
     * @return 'C' for chance node and text representation of child nodes.
     */
    @Override
    public String toTxt(int indent, Heuristic heuristic) {
        StringBuilder childBuilder = new StringBuilder("C\n");
        for (Node node : this.children) {
            childBuilder.append(node.toTxt(indent + 1, heuristic));
        }
        return childBuilder.toString();
    }

    /**
     * Count the number of children i levels down the tree.
     * @param i Number of layers down the tree.
     * @return Number of children i levels down the tree.
     */
    @Override
    public int baseLineCount(int i) {
        return Arrays.stream(this.children).mapToInt(c -> c.getBaselineCount((i))).sum();
    }
}
