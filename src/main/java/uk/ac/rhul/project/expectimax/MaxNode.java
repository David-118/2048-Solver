package uk.ac.rhul.project.expectimax;

public class MaxNode implements Node
{
    private float weight;
    private Node[] children;
    public MaxNode(float weight, Node ... children)
    {
        this.weight = weight;
        this.children = children;
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
}
