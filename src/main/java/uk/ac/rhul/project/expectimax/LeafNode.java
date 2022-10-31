package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;

public class LeafNode extends Node
{
    @Override
    public float getWeight()
    {
        return weight;
    }

    public LeafNode(GameState gameState, float weight)
    {
        super(gameState, weight);
    }

    @Override
    public float getScore()
    {
        return this.getScore() * this.getWeight();
    }

    public Node nextNode()
    {
        return null;
    }

    @Override
    public boolean validate()
    {
        return true;
    }

    @Override
    public Node[] getChildren()
    {
        return new Node[0];
    }
}
