package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

public class LeafNodeBehaviour implements NodeBehaviour
{
    private GameState state;

    boolean gameOver;
    public LeafNodeBehaviour(GameState state)
    {
        this(state, false);
    }
    public LeafNodeBehaviour(GameState state, Boolean gameOver)
    {
        this.state = state;
        this.gameOver = gameOver;
    }
    @Override
    public Node nextNode(Heuristic heuristic) throws EndOfGameException
    {
        if (gameOver) throw new EndOfGameException(state);
        else throw new RuntimeException("Unexpected end of game");
    }

    @Override
    public double applyHeuristic(Heuristic heuristic)
    {
        return state.applyHeuristic(heuristic);
    }

    @Override
    public Node[] getChildren()
    {
        return new Node[0];
    }
}
