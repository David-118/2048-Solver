package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.heursitics.Heuristic;

public interface NodeBehaviour
{
    Node nextNode(Heuristic heuristic) throws EndOfGameException;
    double applyHeuristic(Heuristic heuristic);
}
