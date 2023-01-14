package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Random;

class Node
{
    private NodeBehaviour behaviour;
    private final GameState gameState;
    private final Random random;
    private final NodeBehaviourGenerator behaviourGenerator;

    protected Node(GameState gameState, NodeBehaviourGenerator generator, Random random)
    {
        this.random = random;
        this.gameState = gameState;
        this.behaviour = new LeafNodeBehaviour(gameState);
        this.behaviourGenerator = generator;
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
        this.behaviour = this.behaviourGenerator.generate(this.gameState, random);
    }
}
