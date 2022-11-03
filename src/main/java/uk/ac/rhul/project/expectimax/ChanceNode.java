package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;

import java.util.Random;

public class ChanceNode extends Node
{
    private Node[] children;
    private Random random;

    private GameState state;

    private final static float PROB_OF_4 = 0.1f;


    public ChanceNode(GameState gameState, float weight, GameState[] mutations, int depth, Random random)
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

    public void expectimax(int depth)
    {
        super.expectimax(depth - 1, MoveType.PLAYER_MOVE);
    }

    @Override
    public float expectimax()
    {
        float sum = 0;
        for (int i = 0; i < children.length; i++)
        {
            sum += children[i].expectimax();
        }
        return sum / children.length;
    }

    public Node nextNode()
    {
        float rnd = random.nextFloat();
        float cweight = 0;

        for (int i = 0; i < this.children.length; i++)
        {
            cweight += this.children[i].getWeight();
            if (rnd < cweight)
            {
                return this.children[i];
            }
        }
        return this.children[0]; // This should never happen if the weights are valid and add upto 1
    }

    @Override
    public float getWeight()
    {
        return this.weight;
    }

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

    @Override
    public Node[] getChildren()
    {
        return this.children;
    }
}
