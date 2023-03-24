package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.expectimax.DepthFunction;
import uk.ac.rhul.project.expectimax.Solver;
import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Random;

/**
 * Creates a model for the user interface. Allows the user to solve a 2048 game.
 */
public final class MainModel implements Model {
    /**
     * Solver used to solve 2048 games.
     */
    private final Solver solver;

    /**
     * Dir to log trees to.
     */
    String logDir = "";

    /**
     * Current gameState.
     */
    private GameState gameState;

    /**
     * Random number generator used by games.
     */
    private Random rnd;

    /**
     * Make a new MainModel.
     */
    public MainModel() {
        this.solver = new Solver(new Random());
    }

    /**
     * Set up a game, and algorithm to solve it based on game configuration.
     * @param configuration Config for the game.
     */
    public void init(GameConfiguration configuration) {
        this.gameState = new GameState(configuration);
        this.gameState.init();
        this.rnd = configuration.getRandom();
        this.solver.setRandom(configuration.getRandom());
        this.initSolver(configuration.getDepth(), configuration.getCount4(), configuration.getHeuristic());
    }

    /**
     * Set the heuristic the solver is using and generate a tree, provide the root of the tree to the
     * solver.
     *
     * @param depth Depth function used to calculate size of the tree.
     * @param count4 Number of 4s before pruning.
     * @param heuristic Heuristic used to evaluate nodes.
     */
    private void initSolver(DepthFunction depth, int count4, Heuristic heuristic) {
        this.solver.configureSolver(depth, count4, heuristic);
        this.solver.setGame(this.gameState);
        if (!logDir.equals("")) this.solver.enableTreeLog(this.logDir);
    }

    /**
     * Get the grid from the 2048 game.
     *
     * @return current game state.
     */
    public GameState getGrid() {
        return gameState;
    }

    /**
     * Get the current score of the 2048 game.
     *
     * @return The score of the current game.
     */
    public int getScore() {
        return gameState.getScore();
    }

    /**
     * Maps views updateValues method to the Solver.
     */
    @Override
    public void addUpdateObserver(UpdateObserver handelUpdate) {
        this.solver.addUpdateObserver(handelUpdate);
    }

    /**
     * Start solving a game.
     * @param blocking if True: run game in main thread
     *                 otherwise: start new thread.
     */
    @Override
    public void solve(boolean blocking) {
        if (blocking) this.solver.run();
        else new Thread(this.solver).start();
    }

    /**
     * Enable logging trees to a directory.
     * @param logDir directory to log trees to.
     */
    public void enableTreeLog(String logDir) {
        this.logDir = logDir;
    }
}
