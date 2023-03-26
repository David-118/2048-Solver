package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Defines the behaviour of a Node.
 *
 * This defines behaviours related to a nodes children applying heuristics.
 */
interface NodeBehaviour {
    /**
     * Defines how to treat a node after it has been generated.
     * @param state The state of the current game.
     * @param random The random number generating being used for the game.
     * @param depth Depth of the tree.
     * @param count4 Number of fours that have occurred so far.
     * @param layer Current layer of the tree.
     * @return The node behaviour the tree should take, often simply this.
     */
    NodeBehaviour generated(GameState state, Random random, int depth, int count4, int layer);

    /**
     * Defines behaviour for how to get the next Node.
     * @param heuristic Heuristic to use in picking next node.
     * @return The next node.
     * @throws EndOfGameException if game ends.
     */
    Node nextNode(Heuristic heuristic) throws EndOfGameException;

    /**
     * Apply a herustic to a node or its children.
     * @param heuristic Heuristic to apply.
     * @return Heuristic score for this node.
     */
    double applyHeuristic(Heuristic heuristic);

    /**
     * Return the type of node as a letter, and its children.
     * @param indent Depth of this node in the tree.
     * @param heuristic Heuristic score for this Node.
     * @return String representation of the type of node and its children,
     */

    String toTxt(int indent, Heuristic heuristic);

    /**
     * Count the number of nodes i levels down the tree.
     * @param i level down the tree.
     * @return number of nodes i levels down the tree.
     */
    int baseLineCount(int i);
}
