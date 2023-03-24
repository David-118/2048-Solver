package uk.ac.rhul.project.game;

import uk.ac.rhul.project.expectimax.DepthFunction;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Random;

/**
 * GameConfiguration represents the setup of a game.
 * This includes config for both algorithm and the
 * initial board.
 */
public class GameConfiguration {
    /**
     * Number of rows in a game state.
     */
    private final int rows;

    /**
     * Number of cols in a game sate.
     */
    private final int cols;

    /**
     * Function used to determine the depth of the expectation tree.
     */
    private final DepthFunction depth;

    /**
     * Heuristic used to evaluate the quality of a game state.
     */
    private final Heuristic heuristic;

    /**
     * Number of 4s to count before pruning.
     */
    private final int count4;

    /**
     * Seed of the random number generator (if null uses default seed).
     */
    private final Long seed = null;

    /**
     * Random number generator used to place random cells in the gmame state.
     */
    private Random random;

    /**
     * Create a GameConfiguration
     * @param rows      Number of rows in the game state.
     * @param cols      Number of cols in the game state.
     * @param depth     Constant depth of the expectimax tree.
     * @param count4    Number of 4s encounter before pruning.
     * @param heuristic Heuristic used to score game state.
     */
    public GameConfiguration(int rows, int cols, int depth, int count4, Heuristic heuristic) {
        this(rows, cols, (int k) -> depth, count4, heuristic);
    }

    /**
     * Create a GameConfiguration
     * @param rows      Number of rows in the game state.
     * @param cols      Number of cols in the game state.
     * @param depth     Depth Function used to evaluate the depth of an expectimax tree.
     * @param count4    Number of 4s encounter before pruning.
     * @param heuristic Heuristic used to score game state.
     */
    public GameConfiguration(int rows, int cols, DepthFunction depth, int count4, Heuristic heuristic) {
        this.rows = rows;
        this.cols = cols;
        this.depth = depth;
        this.heuristic = heuristic;
        this.count4 = count4;
        this.random = new Random();
    }

    /**
     * Get the number of rows.
     * @return number fof rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get the number of cols.
     * @return number of cols.
     */
    public int getCols() {
        return cols;
    }

    /**
     * Get the depth of the expectimax tree as a depth function
     * @return If Constant depth n returns (k) -> n <br>
     *         Otherwise returns depth function.
     */
    public DepthFunction getDepth() {
        return depth;
    }

    /**
     * Get the heuristic used to evaluate game sates.
     * @return Heuristic object used to evaluate game state.
     */
    public Heuristic getHeuristic() {
        return heuristic;
    }

    /**
     * Get the random number generator used add cells to the game state.
     * @return Random object (seeded with correct seed if provided)
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Set the seed for the random number generator.
     * @param seed The seed from the random number generator.
     */
    public void setSeed(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Print information about the game state. Only shows row, cols and heuristic.
     * @return String represenation of the game state.
     */
    public String getName() {
        return String.format("%d x %d game using %s", rows, cols, heuristic.getName());
    }

    /**
     * Get the number of '4's before a node is pruned.
     * @return number '4's before a node is pruned.
     */
    public int getCount4() {
        return count4;
    }
}
