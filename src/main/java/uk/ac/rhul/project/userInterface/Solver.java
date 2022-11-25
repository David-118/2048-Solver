package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.expectimax.Node;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import static java.lang.Thread.sleep;

/**
 * Class used to solve a 2048 game, can be run inside a thread.
 */
public class Solver implements Runnable
{
    /**
     * Root node of the tree.
     */
    private Node root;

    /**
     * Observer used to update the grid on the view live.
     */
    private UpdateObserver updateValues;

    /**
     * Heuristic used to evaluate each tree node.
     */
    private Heuristic heuristic;


    /**
     * Add an observer to update the game on the user interface.
     * @param method Method to call each time the game updates.
     */
    public void addUpdateObserver(UpdateObserver method)
    {
        this.updateValues = method;
    }

    /**
     * Set the heuristic function to be used by the Game Solver.
     * @param heuristic Method called to evaluate quality of a game state.
     */
    public void setHeuristic(Heuristic heuristic)
    {
        this.heuristic = heuristic;
    }

    /**
     * Set the root node of the expetimax tree.
     * @param node The root node of the tree.
     */
    public void setRoot(Node node)
    {
        this.root = node;
    }

    /**
     * Method to start solving the game.
     */
    public void run()
    {
        while (root != null)
        {
            Node nextRoot = root.nextNode(this.heuristic);


            if (nextRoot == null) break;
            root = nextRoot;

            root.expectimax(8, heuristic);

            this.updateValues.notifyObservers(root.getGameState());
        }
    }

    /**
     * Get the current state of the game.
     * @return the current game state, used for analysis after the game.
     */
    public GameState getState()
    {
        return this.root.getGameState();
    }
}
