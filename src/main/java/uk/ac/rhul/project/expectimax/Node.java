package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;

public abstract class Node
{
    public final float weight;
    private final GameState gameState;

    public Node(GameState gameState, float weight)
    {
        this.gameState = gameState;
        this.weight = weight;
    }

    public abstract float getScore();
    public abstract Node nextNode();
    public abstract float getWeight();
    public abstract boolean validate();
    public GameState getGameState()
    {
        return this.gameState;
    }
    public abstract Node[] getChildren();
}
