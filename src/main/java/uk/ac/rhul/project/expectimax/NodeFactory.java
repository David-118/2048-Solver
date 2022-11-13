package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;

import java.util.Random;

public class NodeFactory
{
    public static Random random;

    public static Node generateTree(GameState state, int depth)
    {
        return createNode(MoveType.PLAYER_MOVE, state, 1f, depth);
    }

    public static Node createNode(MoveType type, GameState state, float weight, int depth)
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

    public static void setRandom(Random rnd)
    {
        random = rnd;
    }
}
