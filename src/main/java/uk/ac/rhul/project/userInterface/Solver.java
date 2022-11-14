package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.expectimax.Node;
import uk.ac.rhul.project.game.GameState;

import static java.lang.Thread.sleep;

public class Solver implements Runnable
{
    private Node root;
    private UpdateObserver updateValues;

    private Heuristic heuristic;


    public void addUpdateObserver(UpdateObserver method)
    {
        this.updateValues = method;
    }
    public void setHeurstic(Heuristic heuristic)
    {
        this.heuristic = heuristic;
    }

    public void setRoot(Node node)
    {
        this.root = node;
    }

    public void run()
    {
        while (root != null)
        {
            Node nextRoot = root.nextNode(this.heuristic);


            if (nextRoot == null) break;
            root = nextRoot;

            root.expectimax(8, heuristic);

            this.updateValues.notifyObservers(root.getGameState().getGrid(), root.getGameState().getScore());
        }
    }

    public GameState getState()
    {
        return this.root.getGameState();
    }
}
