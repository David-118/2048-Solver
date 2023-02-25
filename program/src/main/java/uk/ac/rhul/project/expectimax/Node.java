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

    private final double weight;

    protected Node(GameState gameState, NodeBehaviourGenerator generator, Random random)
    {
        this.random = random;
        this.gameState = gameState;
        this.behaviour = new LeafNodeBehaviour(gameState);
        this.behaviourGenerator = generator;
        this.weight = gameState.getProbability();
    }

    public GameState getGameState()
    {
        return this.gameState;
    }

    public Node nextNode(Heuristic heuristic) throws EndOfGameException
    {
        return this.behaviour.nextNode(heuristic);
    }

    public double applyHeuristic(Heuristic heuristic)
    {
        return this.behaviour.applyHeuristic(heuristic) * weight;
    }

    private double unweightedApplyHeuristic(Heuristic heuristic)
    {
        return this.behaviour.applyHeuristic(heuristic);
    }

    public void generateChildren(int depth)
    {
        if (depth > 0) this.behaviour = this.behaviourGenerator.generate(this.gameState, random, depth - 1);
    }


    public double getWeight()
    {
        return weight;
    }

    public String toTxt(int indent, Heuristic heuristic)
    {
        return " ".repeat(indent) + this.gameState.toTxt(heuristic) + "#" + this.unweightedApplyHeuristic(heuristic) +
                behaviour.toTxt(indent, heuristic);
    }

    @Override
    public String toString()
    {
        return this.gameState.toString();
    }
}
