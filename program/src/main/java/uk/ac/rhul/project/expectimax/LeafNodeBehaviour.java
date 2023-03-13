package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Arrays;
import java.util.Random;

public class LeafNodeBehaviour implements NodeBehaviour
{
    private final GameState state;

    public NodeBehaviour generated(GameState state, Random random, int depth, int count4, int layer) {
        return this;
    }
    public LeafNodeBehaviour(GameState state)
    {
        this.state = state;
    }
    @Override
    public Node nextNode(Heuristic heuristic) throws EndOfGameException
    {
        throw new EndOfGameException(this.state);
    }

    @Override
    public double applyHeuristic(Heuristic heuristic)
    {
        return state.applyHeuristic(heuristic);
    }

    @Override
    public String toTxt(int indent, Heuristic heuristic)
    {
        return "L\n";
    }

    @Override
    public int baseLineCount(int i) {
        return 0;
    }
}
