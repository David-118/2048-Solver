package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Arrays;
import java.util.Random;

class Node
{
    private NodeBehaviour behaviour;
    private final GameState gameState;
    private final Random random;
    private final NodeBehaviourGenerator behaviourGenerator;

    private final StateScoreTracker scoreTracker;

    private double weight;

    protected Node(GameState gameState, NodeBehaviourGenerator generator, Random random, StateScoreTracker scoreTracker)
    {
        this.random = random;
        this.gameState = gameState;
        this.behaviour = new LeafNodeBehaviour(gameState, scoreTracker);
        this.behaviourGenerator = generator;
        this.weight = 1;
        this.scoreTracker = scoreTracker;
    }

    public GameState getGameState()
    {
        return this.gameState;
    }

    public Node nextNode(Heuristic heuristic) throws EndOfGameException
    {
        Node nextNode = this.behaviour.nextNode(heuristic);
        this.scoreTracker.setState(nextNode.gameState);
        return nextNode;
    }

    public double applyHeuristic(Heuristic heuristic)
    {
        return this.behaviour.applyHeuristic(heuristic) * weight;
    }

    public void generateChildren(int depth)
    {
        if (depth > 0) this.behaviour =
                this.behaviourGenerator.generate(this.gameState, random, depth - 1, scoreTracker);
    }


    public double getWeight()
    {
        return weight;
    }

    @Override
    public String toString()
    {
        return this.gameState.toString();
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }
}
