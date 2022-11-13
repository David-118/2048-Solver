package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.userInterface.Heuristic;

public abstract class Node
{
    public final float weight;
    private final GameState gameState;

    public Node(GameState gameState, float weight)
    {
        this.gameState = gameState;
        this.weight = weight;
    }

    public abstract float expectimax(Heuristic heuristic);
    public abstract void expectimax(int depth, Heuristic heuristic);
    public abstract Node nextNode(Heuristic heuristic);
    public abstract float getWeight();
    public abstract boolean validate();
    public GameState getGameState()
    {
        return this.gameState;
    }
    public abstract Node[] getChildren();

    protected void expectimax(int depth, Heuristic heuristic, MoveType type)
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
                this.getChildren()[i].expectimax(depth - 1, heuristic);
            }
        }
    }
}
