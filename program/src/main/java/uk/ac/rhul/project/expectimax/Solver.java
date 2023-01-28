package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.heursitics.Heuristic;
import uk.ac.rhul.project.userInterface.UpdateObserver;

import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Class used to solve a 2048 game, can be run inside a thread.
 */
public class Solver implements Runnable
{
    /**
     * Method to start solving the game.
     */

    private UpdateObserver updateObserver;
    private Heuristic heuristic;
    private ExpectimaxTree tree;
    private StateScoreTracker stateScoreTracker;

    private Random random;

    private int depth;

    public Solver(Random random)
    {
        this.random = random;
    }

    public void addUpdateObserver(UpdateObserver updateObserver)
    {
        this.updateObserver = updateObserver;
    }

    public void configureSolver(int depth, Heuristic heuristic)
    {
        this.depth = depth;
        this.heuristic = heuristic;
    }

    public void setGame(StateScoreTracker state)
    {
        this.tree = new ExpectimaxTree(state, this.random, depth, this.heuristic);
        this.stateScoreTracker = state;
    }

    public void run()
    {
        try
        {
            while (true)
            {
                this.tree.makeMove();
                updateObserver.notifyObservers(this.stateScoreTracker);
            }
        } catch (EndOfGameException end)
        {
            updateObserver.notifyObservers(end.getFinalState());
        }
    }

}
