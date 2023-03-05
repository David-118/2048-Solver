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
    String logDir = "";

    public MainModel() {
        this.solver = new Solver(new Random());
    }

    public void init(GameConfiguration configuration)
    {
        this.gameState = new GameState(configuration);
        this.gameState.init();
        this.rnd = configuration.getRandom();
        this.solver.setRandom(configuration.getRandom());
        this.initSolver(configuration.getDepth(), configuration.getCount4(), configuration.getHeuristic());
    }

    /**
     * Set the heuristic the solver is using and generate a tree, provide the root of the tree to the
     * solver.
     */
    private void initSolver(int depth, int count4, Heuristic heuristic)
    {
        this.solver.configureSolver(depth, count4, heuristic);
        this.solver.setGame(this.gameState);
        if (!logDir.equals("")) this.solver.enableTreeLog(this.logDir);
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

    public void enableTreeLog(String logDir) {
        this.logDir = logDir;
    }
}
