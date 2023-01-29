package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Random;

import static java.lang.Thread.sleep;

public class ExpectimaxTree
{
    private Node currentRoot;
    private final int depth;
    private final Heuristic heuristic;

    private final int max4Count = 2;

    public ExpectimaxTree(GameState initialState, Random random, int depth,
                          Heuristic heuristic)
    {
        this.depth = depth;
        this.heuristic = heuristic;
        this.currentRoot = new Node(initialState, NodeBehaviourMaximize::generate, random);
    }

    public GameState makeMove() throws EndOfGameException
    {
        return makeMove(Integer.MAX_VALUE, Double.NEGATIVE_INFINITY);
    }

    public GameState makeMove(int abandonCount, double abandonRatio) throws EndOfGameException
    {
        double abandonThreshold = this.currentRoot.directlyApplyHeuristic(this.heuristic) * abandonRatio;

        this.currentRoot.generateChildren(this.depth, abandonCount, abandonThreshold, this.max4Count);
        this.currentRoot = this.currentRoot.nextNode(this.heuristic).nextNode(this.heuristic);
        return this.currentRoot.getGameState();
    }
}
