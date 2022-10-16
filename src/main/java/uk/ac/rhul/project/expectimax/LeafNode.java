package uk.ac.rhul.project.expectimax;

public class LeafNode implements Node
{
    private float weight;
    private float score;

    public LeafNode(float weight, float score)
    {
        this.weight = weight;
        this.score = score;
    }

    @Override
    public float getScore()
    {
        return this.score * this.weight;
    }

    public Node nextNode()
    {
        return null;
    }
}
