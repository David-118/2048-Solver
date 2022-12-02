package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

/**
 * This node represents a 2048 node the next move is made by the player.
 */
public final class MaxNode extends Node
{
    /**
     * Array containing the nodes children.
     */
    private final Node[] children;

    /**
     * Create a MaxNode
     * @param gameState Current state of the game.
     * @param weight Weight of this node, normally the probability for the chance node.
     * @param moves Possible moves from the gameState.
     * @param depth Depth of the tree.
     */
    MaxNode(GameState gameState, float weight, GameState[] moves, int depth)
   {
       super(gameState, weight);

       children = new Node[moves.length];
       for (int i = 0; i < moves.length; i++)
       {
           children[i] = NodeFactory.generateTree(MoveType.GRID_MUTATION, moves[i], 1f, depth - 1);
       }
   }

    /**
     * Increase the depth of the expectimax tree from this node.
     * @param depth The new max depth of the tree from this node
     * @param heuristic The heuristic function used to calculate the score of the leaf nodes.
     */
    @Override
    public void extendTree(int depth, Heuristic heuristic)
    {
        super.extendTree(depth - 1, heuristic, MoveType.GRID_MUTATION);
    }

    /**
     * Calculate the score for this node
     * @param heuristic heuristic function used on any leaf nodes
     * @return the score of the child with the largest score.
     */
    @Override
    public float scoreNode(Heuristic heuristic)
    {
        float max = children[0].scoreNode(heuristic);
        for (int i = 1; i < children.length; i++)
        {
            float score = children[i].scoreNode(heuristic);
            if (score > max)
            {
                max = score;
            }
        }
        return max * this.getWeight();
    }

    /**
     * Gates the node representing the game after the player makes the optimal move
     * @param heuristic Heuristic used on leaf nodes in the tree
     * @return the direct child node with the largest child
     */
    public Node nextNode(Heuristic heuristic)
    {
        Node max = children[0];
        for (int i = 1; i < children.length; i++)
        {
            Node current = children[i];
            if (current.scoreNode(heuristic) > max.scoreNode(heuristic))
            {
                max = current;
            }
        }
        return max;
    }

    /**
     * The checks the node has children.
     * @return True if there are child nodes.
     */
    @Override
    public boolean validate()
    {
        return this.children.length > 0;
    }


    /**
     * Returns the children of this node.
     * @return Array of all direct children to this node.
     */
    @Override
    public Node[] getChildren()
    {
        return this.children;
    }
}
