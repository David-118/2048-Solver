package uk.ac.rhul.project.expectimax;

public final class MaxNode implements Node
{
    private final float weight;
    private final Node[] children;

    @Override
    public float getWeight()
    {
        return weight;
    }

    public MaxNode(float weight, Node ... children) throws InvalidTreeException
    {
        this.weight = weight;
        this.children = children;

        if (!this.validate())
        {
            throw new InvalidTreeException("MaxNode requires children, but does not have any");
        }
    }

    @Override
    public float getScore()
    {
        float max = children[0].getScore();
        for (int i = 1; i < children.length; i++)
        {
            float score = children[i].getScore();
            if (score > max)
            {
                max = score;
            }
        }
        return max * weight;
    }

    public Node nextNode()
    {
        Node max = children[0];
        for (int i = 1; i < children.length; i++)
        {
            Node current = children[i];
            if (current.getScore() > max.getScore())
            {
                max = current;
            }
        }
        return max;
    }

    @Override
    public boolean validate()
    {
        return this.children.length > 0;
    }
}
