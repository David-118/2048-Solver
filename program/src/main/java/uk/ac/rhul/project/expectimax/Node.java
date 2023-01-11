package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

class Node
{
    private final GameState gameState;

    protected Node(GameState gameState)
    {
        this.gameState = gameState;
    }

    public GameState getGameState()
    {
        return this.gameState;
    }

    public Node nextNode() throws EndOfGameException
    {
        throw new EndOfGameException();
    }

    public float applyHeuristic(Heuristic heuristic)
    {
        return heuristic.heuristic(this.gameState);
    }
}
