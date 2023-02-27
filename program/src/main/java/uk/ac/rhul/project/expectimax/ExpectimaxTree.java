package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.io.*;

import java.util.Random;


public class ExpectimaxTree
{
    private Node currentRoot;
    private final int depth;

    private int key;
    private final Heuristic heuristic;

    public ExpectimaxTree(GameState initialState, Random random, int depth, Heuristic heuristic)
    {
        this.depth = depth;
        this.heuristic = heuristic;
        this.currentRoot = new Node(initialState, NodeBehaviourMaximize::generate, random);
        this.key = 0;
    }

    public GameState makeMove() throws EndOfGameException
    {
        this.currentRoot.generateChildren(this.depth);

        try
        {
            dmpTxt(key);
        } catch (IOException e)
        {
            System.err.printf("Failed to write state %08d to html.\n", key);
            System.err.println(e.getMessage());
        }
        key++;
        
        this.currentRoot = this.currentRoot.nextNode(this.heuristic).nextNode(this.heuristic);
        return this.currentRoot.getGameState();
    }

    public void dmpTxt(int key) throws IOException
    {
        String txt =  currentRoot.toTxt(0, this.heuristic);

        File folder = new File("trees");
        folder.delete(); folder.mkdir();
        File file = new File(String.format("trees/tree-%08d.tree", key));

        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(txt);
        fileWriter.close();
    }
}
