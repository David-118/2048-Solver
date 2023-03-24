package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;
import uk.ac.rhul.project.userInterface.UpdateObserver;

import java.util.Random;

/**
 * Class used to solve a 2048 game, can be run inside a thread.
 */
public class Solver implements Runnable {
    /**
     * Method to update the game.
     */
    private UpdateObserver updateObserver;

    /**
     * Heuristic used by the solver.
     */
    private Heuristic heuristic;

    /**
     * The current games tree.
     */
    private ExpectimaxTree tree;

    /**
     * Random number generator used to place random cells in the gird.
     */
    private Random random;

    /**
     * Function used to determine the depth of the tree is stored here.
     * If constant depth use a function that returns a constant.
     */
    private DepthFunction depth;

    /**
     * Number of 4s that have to be seen before pruning.
     */
    private int count4;

    /**
     * Create a Solver using a given random number generator.
     * @param random random number generator used to place cells in grid.
     */
    public Solver(Random random) {
        this.random = random;
    }

    /**
     * Set the random number generator used to place cells in grid.
     * @param random random number generator used to place cells in grid.
     */
    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * Set function called when the game state changes.
     * @param updateObserver function to call.
     */
    public void addUpdateObserver(UpdateObserver updateObserver) {
        this.updateObserver = updateObserver;
    }

    /**
     * Configure the main settings in the solver.
     * @param depth     Games depth function, if constant return constant value,
     * @param count4    Number of 4s uncounted before pruning.
     * @param heuristic Heuristic used to evaluate quality of game states.
     */
    public void configureSolver(DepthFunction depth, int count4, Heuristic heuristic) {
        this.depth = depth;
        this.count4 = count4;
        this.heuristic = heuristic;
    }

    /**
     * Set the initial game state.
     * @param state the entail game state.
     */
    public void setGame(GameState state) {
        this.tree = new ExpectimaxTree(state, this.random, this.depth, this.count4, this.heuristic);
    }

    /**
     * Start playing a game, until finished.
     */
    public void run() {
        try {
            while (true) {
                updateObserver.notifyObservers(this.tree.makeMove());
            }
        } catch (EndOfGameException end) {
            updateObserver.notifyObservers(end.getFinalState());
        }
    }

    /**
     * Enable logging trees to a directory.
     * @param logDir Directory to log to.
     */
    public void enableTreeLog(String logDir) {
        this.tree.enableTreeLog(logDir);
    }
}
