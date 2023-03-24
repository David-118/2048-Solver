package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.io.*;

import java.nio.file.Path;
import java.util.Random;


/**
 * An expetimax tree for a n × m game of 2048,
 */
public class ExpectimaxTree
{
    /**
     * The current root node of the expetimax tree.
     */
    private Node currentRoot;

    /**
     * Depth function used to determine how deep a specfic tree should be.
     */
    private final DepthFunction depth;

    /**
     * The current number indefinite how many moves have been made in a game.
     */
    private int key;

    /**
     * The heuristic used to evaluate nodes in the tree.
     */
    private final Heuristic heuristic;

    /**
     * Number of 4 nodes that can be seen before pruning starts.
     */
    private final int count4;

    /**
     * Function used to dump text representation to format.
     * Defaults to do nothing.
     */
    private DmpTxt dmpTxtState = (int k) -> {};

    /**
     * Minimum depth of the tree (Should be at least the minimum depth of a given depth function)
     */
    public static final int BASELINE_DEPTH = 7;

    /**
     * Create an expectimax tree
     * @param initialState Initial State of the expectimax tree.
     * @param random       Random number generated used to pick a child at chance nodes.
     * @param depth        DepthFunction used for tree.
     * @param count4       Number of 4s seen before pruning children of node.
     * @param heuristic    Heuristic used  to evaluate tree.
     */
    public ExpectimaxTree(GameState initialState, Random random, DepthFunction depth, int count4, Heuristic heuristic)
    {
        this.depth = depth;
        this.heuristic = heuristic;
        this.currentRoot = new Node(initialState, NodeBehaviourMaximize::generate, random);
        this.count4 = count4;
        this.key = 0;
    }

    /**
     * Move to next move in game.
     * @return The new game state after a move has been made.
     * @throws EndOfGameException If the game is done.
     */
    public GameState makeMove() throws EndOfGameException
    {
        // Count nodes at bottom of the shallowest possible tree remaining.
        // Nodes where the game has ended before are not counted.
        int k = this.currentRoot.getBaselineCount(BASELINE_DEPTH - 2);
        int depth = this.depth.depth(k);
        this.currentRoot.generateChildren(depth, this.count4, 0);

        // Try writing to tree to file.
        try
        {
            dmpTxt(key);
        } catch (IOException e)
        {
            System.err.printf("Failed to write state %08d to html.\n", key);
            System.err.println(e.getMessage());
        }

        // Increment move counter.
        key++;

        // Move to next node in tree.
        this.currentRoot = this.currentRoot.nextNode(this.heuristic).nextNode(this.heuristic);
        return this.currentRoot.getGameState();
    }

    /**
     * Enable logging the trees to a specific folder.
     * <p style="color:red"><b>CAUTION</b>: Using this can quickly fill disk space for larger games,
     * and greatly slows down the performance.</p>
     * @param logDir Directory to put tree files
     */
    public void enableTreeLog(String logDir)
    {
        this.dmpTxtState = (int k) -> this._dmpTxt(k, logDir);
    }

    /**
     * Dump the tree to a text file named tree-{key}.tree if enableTreeLog has been called first.
     * @param key The number of moves so far in the game.
     * @throws IOException Unable to create or write to file.
     */
    public void dmpTxt(int key) throws IOException
    {
        this.dmpTxtState.dmpTxt(key);
    }

    /**
     * Dump tree to a text file irreverent of whether enableTreeLog has been called first.
     * @param key The number of moves so far in the game.
     * @param logDir Dir to write file to.
     * @throws IOException Unable to create or write to text file.
     */
    private void _dmpTxt(int key, String logDir) throws IOException
    {
        String txt =  currentRoot.toTxt(0, this.heuristic);

        File file = new File(Path.of(logDir, String.format("tree-%08d.tree", key)).toUri());

        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(txt);
        fileWriter.close();
    }
}
