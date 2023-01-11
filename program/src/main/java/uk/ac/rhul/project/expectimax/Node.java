package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;

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
}
