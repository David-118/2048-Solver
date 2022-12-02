package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;

import java.util.Random;

/**
 * NodeFactory is used to generate a tree of nodes from a GameState and depth.
 */
public abstract class NodeFactory
{
    /**
     * Random number generator used by all chance nodes created by this factory.
     */
    private static Random random;

    /**
     * Crates a tree from an initial 2048 state. Assumes that the first move is made by a player.
     * @param state State of the root node of this tree.
     * @param depth Maximum Depth of the tree
     * @return Returns a Node representing the expectimax tree starting at state.
     */
    public static Node generateTree(GameState state, int depth)
    {
        return generateTree(MoveType.PLAYER_MOVE, state, 1f, depth);
    }

    /**
     * Creates a tree node with no assumptions made.
     * @param type Player Move or randomly adding tile to the gird.
     * @param state Current state of the game.
     * @param weight Weight of the node.
     * @param depth Maximum depth of the tree.
     * @return Returns a Node representing the expectimax tree starting at state.
     */
    protected static Node generateTree(MoveType type, GameState state, float weight, int depth)
    {
        Node node = null;
        if (type == MoveType.PLAYER_MOVE)
        {
            GameState[] childStates = state.getPossibleMoves();
            if (childStates.length != 0 && depth > 0)
            {
                node = new MaxNode(state, weight, childStates, depth);
            }
        } else
        {
            GameState[] childStates = state.getPossibleMutations();
            if (childStates.length != 0 && depth > 0)
            {
                node = new ChanceNode(state, weight, childStates, depth, random);
            }
        }
        if (node == null)
        {
            node = new LeafNode(state, weight);
        }
        return node;
    }

    /**
     * Set the random number generator used by all chance nodes.
     * @param rnd The random number generator used by the tree.
     */
    public static void setRandom(Random rnd)
    {
        random = rnd;
    }
}
