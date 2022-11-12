package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.userInterface.Heuristics;

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
    public float expectimax()
    {
        return this.getGameState().applyHeuristic(Heuristics::largestLower) * this.getWeight();
    }

    public void expectimax(int depth)
    {

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
