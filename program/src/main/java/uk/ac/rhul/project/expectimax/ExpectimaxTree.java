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

    public ExpectimaxTree(GameState initialState, Random random, int depth, Heuristic heuristic)
    {
        this.depth = depth;
        this.heuristic = heuristic;
        this.currentRoot = new Node(initialState, NodeBehaviourMaximize::generate, random);
    }

    public GameState makeMove() throws EndOfGameException
    {
        this.currentRoot.generateChildren(this.depth);
        this.currentRoot = this.currentRoot.nextNode(this.heuristic).nextNode(this.heuristic);
        return this.currentRoot.getGameState();
    }
}
