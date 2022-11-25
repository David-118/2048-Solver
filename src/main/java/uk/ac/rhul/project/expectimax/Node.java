package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

/**
 * Represents a node in an expectimax tree
 */
public abstract class Node
{
    /**
     * The weight of the node
     */
    private final float weight;

    /**
     * The state the game is currently in for this tree node
     */
    private final GameState gameState;


    /**
     * Initialise the common aspects of all nodes.
     * @param gameState state of the game.
     * @param weight weighting for this node.
     */
    protected Node(GameState gameState, float weight)
    {
        this.gameState = gameState;
        this.weight = weight;
    }

    /**
     * Returns the score of the node
     * @param heuristic Heuristic used to calculate the value of any leaf nodes.
     * @return the score for the node.
     */
    public abstract float scoreNode(Heuristic heuristic);

    /**
     * Extends the depth of the tree.
     * @param depth New depth for the tree.
     * @param heuristic Heuristic used when evaluating leaf nodes.
     */
    public abstract void expectimax(int depth, Heuristic heuristic);

    /**
     * Returns the next root node for the tree.
     * @param heuristic Heuristic used to determine how good a state is.
     * @return The next node in the tree.
     */
    public abstract Node nextNode(Heuristic heuristic);
    public float getWeight()
    {
        return this.weight;
    }

    /**
     * Checks if a node is valid
     * @return True if the node is valid.
     */
    public abstract boolean validate();

    /**
     * Gets the current state of the game.
     * @return GameState associated with the node.
     */
    public GameState getGameState()
    {
        return this.gameState;
    }

    /**
     * Get all direct children of this node in an array.
     * @return Array of all the children to this node.
     */
    public abstract Node[] getChildren();

    /**
     * Common code checking if children are leaf nodes when creating a expectimax tree.
     * @param depth Depth of the tree.
     * @param heuristic Heuristic function used evaluate leaf nodes.
     * @param type The type of move that the node represents.
     */
    protected void expectimax(int depth, Heuristic heuristic, MoveType type)
    {
        for (int i = 0; i < this.getChildren().length; i++)
        {
            if (this.getChildren()[i] instanceof LeafNode)
            {
                this.getChildren()[i] = NodeFactory.createNode(type, this.getChildren()[i].getGameState(),
                        this.getChildren()[i].getWeight(), depth);
            } else
            {
                this.getChildren()[i].expectimax(depth - 1, heuristic);
            }
        }
    }
}
