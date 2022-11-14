package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.userInterface.Heuristic;

/**
 * A node that has no children.
 */
public class LeafNode extends Node
{
    /**
     * Creates a leafNode
     * @param gameState The state the game is in.
     * @param weight The nodes weight.
     */
    protected LeafNode(GameState gameState, float weight)
    {
        super(gameState, weight);
    }

    /**
     * Get the score for this node using a given heuristic.
     * @param heuristic the heuristic to be used by this function
     * @return the value of the heuristic * the weight of this node.
     */
    @Override
    public float scoreNode(Heuristic heuristic)
    {
        return this.getGameState().applyHeuristic(heuristic) * this.getWeight();
    }

    /**
     * This method does nothing, as leaf node does not have any children.
     * @param depth Not Used
     * @param heuristic Not Used
     */
    public void expectimax(int depth, Heuristic heuristic)
    {

    }

    /**
     * By definition a lead node can not have a child to continue to.
     * @param heuristic Not used.
     * @return null.
     */
    public Node nextNode(Heuristic heuristic)
    {
        return null;
    }

    /**
     * Validate the node.
     * @return true. Invalid leaf nodes cannot be made.
     */
    @Override
    public boolean validate()
    {
        return true;
    }

    /**
     * Gets the children of node.
     * @return [], there are no children to this node.
     */
    @Override
    public Node[] getChildren()
    {
        return new Node[0];
    }
}
