package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.expectimax.Solver;
import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Heuristic;

import java.util.Random;
/**
 * Creates a model for the user interface. Allows the user to solve a 2048 game.
 */
public final class MainModel implements Model
{
    private GameState gameState;
    private Solver solver;
    private Random rnd;

    /**
     * Create a model of the game 2048
     * @param  rows The height of the games grid
     * @param cols The width the games grid
     * @param random the random number generator used to test the project. Allows for testing with a known seed.
     */
    public MainModel(int rows, int cols, Random random)
    {
        this.gameState = new GameState(new GameConfiguration(rows, cols, -1,null), random);
        this.solver = new Solver(random);
        this.rnd = random;
    }

    /**
     * Create a model of the game 2048
     * @param rows The height of the games grid
     * @param cols The width the games grid
     */
    public MainModel(int rows, int cols)
    {
        this(rows, cols, new Random());
    }


    /**
     * Start a game with a new size.
     * @param height The height of the new game.
     * @param width The width of the new game.
     */
    public void init(GameConfiguration configuration)
    {
        this.gameState = new GameState(configuration);
        this.gameState.init();
        this.initSolver(configuration.getDepth(), configuration.getHeuristic());
    }

    /**
     * Set the heuristic the solver is using and generate a tree, provide the root of the tree to the
     * solver.
     */
    private void initSolver(int depth, Heuristic heuristic)
    {
        this.solver.configureSolver(depth, heuristic);
        this.solver.setGame(this.gameState);
    }

    /**
     * Get the grid from the 2048 game.
     * @return a 2D array stored [rows, cols] representing the 2048 games.
     */
    public GameState getGrid()
    {
        return gameState;
    }

    /**
     * Get the current score of the 2048 game.
     * @return The score of the current game.
     */
    public int getScore()
    {
        return gameState.getScore();
    }

    @Override
    public void addUpdateObserver(UpdateObserver handelUpdate)
    {
        this.solver.addUpdateObserver(handelUpdate);
    }

    @Override
    public void solve(boolean blocking)
    {
        if (blocking) this.solver.run();
        else new Thread(this.solver).start();
    }
}
