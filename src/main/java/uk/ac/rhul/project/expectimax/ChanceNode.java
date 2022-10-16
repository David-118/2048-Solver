package uk.ac.rhul.project.expectimax;

public class ChanceNode implements Node
{
    private float weight;
    private Node[] children;

    public ChanceNode(float weight, Node ... children)
    {
        this.weight = weight;
        this.children = children;

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
        return this.children[0];
    }
}
