package uk.ac.rhul.project.userInterface;

import javafx.application.Platform;
import uk.ac.rhul.project.expectimax.Node;
import uk.ac.rhul.project.expectimax.NodeFactory;
import uk.ac.rhul.project.game.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Solver implements Runnable
{
    private Node root;
    private UpdateObserver updateValues;

    private List<GameState> displayQueue;

    public Solver()
    {
        displayQueue = new LinkedList<>();
    }

    public void addUpdateObserver(UpdateObserver method)
    {
        this.updateValues = method;
    }

    public void setRoot(Node node)
    {
        this.root = node;
    }

    public void run()
    {
        while (root != null)
        {
            root = root.nextNode();



            if (root == null) break;


            root.expectimax(6);

            displayQueue.add(root.getGameState());


            Platform.runLater(() -> {
                GameState gameState = displayQueue.remove(0);
                this.updateValues.notifyObservers(gameState.getGrid(), gameState.getScore());
            });
        }
    }
}
