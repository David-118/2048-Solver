package uk.ac.rhul.project.expectimax;

import java.util.Random;

public class ChanceNode implements Node
{
    private float weight;
    private Node[] children;
    private Random random;

    public ChanceNode(float weight, Node ... children)
    {
        this.weight = weight;
        this.children = children;
    }

    public void setRandom(Random random)
    {
        this.random = random;
    }

    @Override
    public float getScore()
    {
        float sum = 0;
        for (int i = 0; i < children.length; i++)
        {
            sum += children[i].getScore();
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
}
