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

    public abstract float expectimax();
    public abstract void expectimax(int depth);
    public abstract Node nextNode();
    public abstract float getWeight();
    public abstract boolean validate();
    public GameState getGameState()
    {
        return this.gameState;
    }
    public abstract Node[] getChildren();

    protected void expectimax(int depth, MoveType type)
    {
        for (int i = 0; i < this.getChildren().length; i++)
        {
            if (this.getChildren()[i] instanceof LeafNode)
            {
                MoveType other = type == MoveType.GRID_MUTATION ? MoveType.PLAYER_MOVE: MoveType.GRID_MUTATION;

                this.getChildren()[i] = NodeFactory.createNode(type, this.getChildren()[i].getGameState(),
                        this.getChildren()[i].getWeight(), depth);
            } else
            {
                this.getChildren()[i].expectimax(depth - 1);
            }
        }
    }
}
