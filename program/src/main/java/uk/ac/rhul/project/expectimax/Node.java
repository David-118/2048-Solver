package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Random;

/**
 * Represents any node in a (n x m) 2048 expectimax tree.
 */
class Node {
    /**
     * The current state of the game.
     */
    private final GameState gameState;

    /**
     * Random number generater used to pick the child of for a chance node.
     */
    private final Random random;

    /**
     * Weight of this node.
     */
    private final double weight;

    /**
     * Behvaiour for the node can be set to a instance of:
     * <ul>
     *     <li>LeafNodeBehaviour</li>
     *     <li>NodeBehaviourChance</li>
     *     <li>NodeBehaviourMaximize</li>
     * </ul>
     */
    private NodeBehaviour behaviour;

    /**
     * Function used to generate a nodes children when generateChildren is called.
     */
    private NodeBehaviourGenerator behaviourGenerator;

    /**
     * Create a node for an expctimax tree
     *
     * @param gameState State of the node.
     * @param generator Function used to generate child nodes. Unless you know better use one of:
     *                  <ul>
     *                  <li>NodeBehaviourChance::generate</li>
     *                  <li>NodeBehaviourMaximize::generate</li>
     *                  </ul>
     * @param random    Random number generator used pick next node.
     */
    protected Node(GameState gameState, NodeBehaviourGenerator generator, Random random) {
        this.random = random;
        this.gameState = gameState;
        this.behaviour = new LeafNodeBehaviour(gameState);
        this.behaviourGenerator = generator;
        this.weight = gameState.getProbability();
    }

    /**
     * Get the game state of the current Node.
     * @return The game state of the current Node.
     */
    public GameState getGameState() {
        return this.gameState;
    }


    /**
     * Get the next Node in the tre.
     *
     * @param heuristic  Heuristic used to determine best move.
     * @return if Maximizing Node returns The Highest Score child (using heuristic)
     *         if Chance Node returns a random child.
     * @throws EndOfGameException If leafNode.
     */
    public Node nextNode(Heuristic heuristic) throws EndOfGameException {
        return this.behaviour.nextNode(heuristic);
    }

    /**
     * Apply heuristic to subtree starting from node.
     *
     * @param heuristic The heuristic score of this node.
     * @return Weight score of this node (using heuristic)
     */
    public double applyHeuristic(Heuristic heuristic) {
        return this.behaviour.applyHeuristic(heuristic) * weight;
    }

    /**
     * Apply heuristic to subtree starting from node.
     *
     * @param heuristic The heuristic score of this node.
     * @return Unweighted score of this node (using heuristic)
     */
    private double unweightedApplyHeuristic(Heuristic heuristic) {
        return this.behaviour.applyHeuristic(heuristic);
    }

    /**
     * Generate the children of this node. Once this has been called on a node it will simply call this method
     * on its child notes and return this existing behaviour.
     *
     * @param depth  Depth on the tree
     * @param count4 The number of 4s that can occur bowing pruning its children,
     * @param i Current layer of node.
     */
    public void generateChildren(int depth, int count4, int i) {
        if (this.gameState.cell() == 4) count4--;
        if (depth > 0 && count4 > 0) {
            this.behaviour = this.behaviourGenerator.generate(this.gameState, random, depth - 1, count4, i);

            // Stop a node from being regenerated.
            this.behaviourGenerator = this.behaviour::generated;
        }
    }

    /**
     * Get the weight of the node.
     * @return the weight of the node.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Returns a text representation of this node and its subtree. Used for generating text format of a tree.
     *
     * @param indent The number of spaces to place before root node.
     * @param heuristic Heurstic to calucalte scores with.
     * @return Text representation of this node and its subtree.
     */
    public String toTxt(int indent, Heuristic heuristic) {
        return " ".repeat(indent) + this.gameState.toTxt(heuristic) + "#" + this.unweightedApplyHeuristic(heuristic) + behaviour.toTxt(indent, heuristic);
    }

    /**
     * Converts nodes game state to a string.
     * @return gameState as string.
     */
    @Override
    public String toString() {
        return this.gameState.toString();
    }

    /**
     * Count the number of children i layers down the tree.
     *
     * @param i number of layers down the tree.
     * @return Number of children at the level i.
     */
    public int getBaselineCount(int i) {
        if (i == 0) {
            return 1;
        } else {
            return behaviour.baseLineCount(i - 1);
        }
    }
}
