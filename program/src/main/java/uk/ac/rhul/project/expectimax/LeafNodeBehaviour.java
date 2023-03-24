package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Random;

/**
 * Makes a node behaviour like a leaf node. Leaf nodes have no children.
 */
public class LeafNodeBehaviour implements NodeBehaviour {
    /**
     * Stores the current state of the game.
     */
    private final GameState state;

    /**
     * Generate a leaf node behaviour with a given game state.
     *
     * @param state The current game state.
     */
    public LeafNodeBehaviour(GameState state) {
        this.state = state;
    }

    /**
     * Generates the children of a leaf node, that has already been shown to not have children.
     *
     * @param state  Ignored
     * @param random Ignored
     * @param depth  Ignored
     * @param count4 Ignored
     * @param layer  Ignored
     * @return this instance of LeafNodeBehaviour.
     */
    public NodeBehaviour generated(GameState state, Random random, int depth, int count4, int layer) {
        return this;
    }

    /**
     * Throws EndOfGameException as there is never a next node in the tree.
     *
     * @param heuristic Ignored.
     * @return Never returns.
     * @throws EndOfGameException Always, as there is no next move it must instead end the game.
     */
    @Override
    public Node nextNode(Heuristic heuristic) throws EndOfGameException {
        throw new EndOfGameException(this.state);
    }

    /**
     * Applies a given heuristic to this node.
     *
     * @param heuristic Heuristic to apply
     * @return Heuristic score for this Node.
     */
    @Override
    public double applyHeuristic(Heuristic heuristic) {
        return state.applyHeuristic(heuristic);
    }

    /**
     * Returns the text representing the type of node this is (L) and its lack of children. Used for text representation
     * of trees.
     *
     * @param indent    Ignored (due to lack of children).
     * @param heuristic Ignored (due to lack of children).
     * @return Text representation of the tree.
     */
    @Override
    public String toTxt(int indent, Heuristic heuristic) {
        return "L\n";
    }

    /**
     * Returns the number of children i layers bellow a point.
     * @param i number of layers down to count.
     * @return 0 - there are no children.
     */
    @Override
    public int baseLineCount(int i) {
        return 0;
    }
}
