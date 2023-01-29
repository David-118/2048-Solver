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
    private NodeBehaviourGenerator behaviourGenerator;

    private final double weight;

    protected Node(GameState gameState, NodeBehaviourGenerator generator, Random random)
    {
        this.random = random;
        this.gameState = gameState;
        this.behaviour = new LeafNodeBehaviour(gameState);
        this.behaviourGenerator = generator;
        this.weight = gameState.getProbability();
    }

    private NodeBehaviour generated(GameState state, Random random, int depth,
                                    int abandonCount, double abandonThreshold, int max4count)
    {
        Arrays.stream(this.behaviour.getChildren()).parallel().unordered().
                forEach((Node child) -> child.generateChildren(depth, abandonCount, abandonThreshold, max4count));

        return this.behaviour;
    }

    private NodeBehaviourGenerator afterGeneration = this::generated;

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

    public void generateChildren(int depth, int abandonCount, double abandonThreshold, int max4count)
    {
        if (abandonCount <= 0) this.abandon();
        if (depth > 0 && max4count > 0)
        {
            if (gameState.lastChangeAdded4()) max4count--;
            this.behaviour = this.behaviourGenerator.generate(this.gameState, random, depth - 1,
            abandonCount, abandonThreshold, max4count);
            this.behaviourGenerator = this.afterGeneration;
        }
    }

    public void generateChildren(int depth)
    {
        this.generateChildren(depth, Integer.MAX_VALUE, Double.NEGATIVE_INFINITY, Integer.MAX_VALUE);
    }

    public double getWeight()
    {
        return weight;
    }

    public void abandon()
    {
        this.behaviourGenerator = NodeBehaviourPruned::generate;
        this.afterGeneration = NodeBehaviourPruned::generate;
        this.generateChildren(1);
    }

    public void tempPrune()
    {

    }

    public double directlyApplyHeuristic(Heuristic heuristic)
    {
        return this.gameState.applyHeuristic(heuristic);
    }

    @Override
    public String toString()
    {
        return this.gameState.toString();
    }
}
