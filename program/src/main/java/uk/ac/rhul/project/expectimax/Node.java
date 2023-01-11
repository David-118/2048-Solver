package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

class Node
{
    private NodeBehaviour behaviour;
    private final GameState gameState;

    protected Node(GameState gameState)
    {
        this.gameState = gameState;
        this.behaviour = new LeafNodeBehaviour(gameState);
    }

    public GameState getGameState()
    {
        return this.gameState;
    }

    public Node nextNode(Heuristic heuristic) throws EndOfGameException
    {
        return this.behaviour.nextNode(heuristic);
    }

    public float applyHeuristic(Heuristic heuristic)
    {
        return this.behaviour.applyHeuristic(heuristic);
    }

    public void generateChildren()
    {
        this.behaviour = NodeBehaviourMaximize.generate(this.gameState);
    }
}
