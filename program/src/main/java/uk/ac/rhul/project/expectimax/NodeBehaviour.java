package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.concurrent.atomic.AtomicInteger;

interface NodeBehaviour
{
    Node nextNode(Heuristic heuristic) throws EndOfGameException;
    double applyHeuristic(Heuristic heuristic);

    String toTxt(int indent, Heuristic heuristic);

    int baseLineCount(int i);
}
