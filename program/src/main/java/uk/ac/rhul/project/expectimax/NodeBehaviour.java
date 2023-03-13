package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

interface NodeBehaviour
{
    NodeBehaviour generated(GameState state, Random random, int depth, int count4, int layer);

    Node nextNode(Heuristic heuristic) throws EndOfGameException;
    double applyHeuristic(Heuristic heuristic);

    String toTxt(int indent, Heuristic heuristic);

    int baseLineCount(int i);
}
