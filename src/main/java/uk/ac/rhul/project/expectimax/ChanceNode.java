package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Random;

/**
 * Chance node represents a state in 2048, were the next thing to happen will be a random tile being added to the grid.
 */
public class ChanceNode extends Node
{
    /**
     * Contains all the direct children of this node.
     */
    private Node[] children;

    /**
     * The random number generator used to pick what the next node will be.
     */
    private Random random;

    /**
     * The probability of the next tile being added being a 4
     * The probability of a 2 is 1 - PROB_OF_4
     */
    private final static float PROB_OF_4 = 0.1f;


    /**
     * Create a chance node
     * @param gameState the state this node represents.
     * @param weight the weight on this node, normally one for a chance node.
     * @param mutations the possible game states that can follow this state
     * @param depth maximum depth for the children of this node
     * @param random random number generator used to pick the next node
     */
    protected ChanceNode(GameState gameState, float weight, GameState[] mutations, int depth, Random random)
    {
        super(gameState, weight);
        this.random = random;

        this.children = new Node[mutations.length];

        final float CHANCE_OF_2 = (1f / (mutations.length / 2f)) * (1 - PROB_OF_4);
        final float CHANCE_OF_4 = (1f / (mutations.length / 2f)) * PROB_OF_4;

        for (int i = 0; i < mutations.length; i+=2)
        {
            this.children[i] = NodeFactory.createNode(MoveType.PLAYER_MOVE, mutations[i], CHANCE_OF_2, depth - 1);
            this.children[i + 1] = NodeFactory.createNode(MoveType.PLAYER_MOVE, mutations[i + 1], CHANCE_OF_4, depth - 1);
        }
    }

    /**
     * Extend the tree to a specified maximum depth from this node.
     * @param depth The depth to extend the tree to.
     * @param heuristic The heuristic function used to compute score for hte leaf nodes.
     */
    public void expectimax(int depth, Heuristic heuristic)
    {
        super.expectimax(depth - 1, heuristic, MoveType.PLAYER_MOVE);
    }

    /**
     * Calculate the score of this node using a certain heuristic
     * @param heuristic The heuristic function that will be applied to the child nodes in this tree
     * @return sum of all the weighted score of its children
     */
    @Override
    public float scoreNode(Heuristic heuristic)
    {
        float sum = 0;
        for (Node child : children)
        {
            sum += child.scoreNode(heuristic);
        }
        return sum;
    }

    /**
     * This method returns the next node to be processed from tree.
     * @param heuristic The heuristic is not used in the chance node implementation of nextNode
     * @return a random direct child of this node. A child nodes weight is the probability of it being selected.
     */
    public Node nextNode(Heuristic heuristic)
    {
        float rnd = random.nextFloat();
        float currentWeight = 0;

        for (int i = 0; i < this.children.length; i++)
        {
            currentWeight += this.children[i].getWeight();
            if (rnd <= currentWeight)
            {
                return this.children[i];
            }
        }
        System.out.println("Fail");
        return this.children[0]; // This should never happen if the weights are valid and add upto 1
    }

    /**
     * Check that node is valid.
     * @return True if the sum of its children's weights add up to 1.
     */
    @Override
    public boolean validate()
    {
        float sum = 0f;
        for (int i = 0; i < children.length; i++)
        {
            sum += children[i].getWeight();
        }

        return sum == 1f;
    }

    /**
     * Get the array of the nodes children.
     * @return An array that contains all the nodes children.
     */
    @Override
    public Node[] getChildren()
    {
        return this.children;
    }
}
