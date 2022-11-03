package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.expectimax.Node;
import uk.ac.rhul.project.expectimax.NodeFactory;
import uk.ac.rhul.project.game.Direction;
import uk.ac.rhul.project.game.GameState;

import java.util.Random;
/*
 * Based on the origonal source [2]
 *  Files:
 *    /js/game_manager.js
 *    /js/grid.js
 */
public final class MainModel implements Model
{
    private GameState gameState;
    private Solver solver;
    private Random rnd;

    /*
     * Create a model of the game 2048
     * @parm rows: The height of the games grid
     * @parm cols: The width the games grid
     * @parm random: the random number generator used to test the project. Allows for testing with a known seed.
     */
    public MainModel(int rows, int cols, Random random)
    {
        NodeFactory.setRandom(random);
        this.gameState = new GameState(rows, cols, random, Heuristics::getRandom);
        this.solver = new Solver();
        this.rnd = random;
    }

    /*
     * Create a model of the game 2048
     * @parm rows: The height of the games grid
     * @parm cols: The width the games grid
     */
    public MainModel(int rows, int cols)
    {
        this(rows, cols, new Random());
    }

    /*
     * Start the game with random grids and score of 0
     */
    public void init()
    {
        this.gameState.init();
        this.initSolver();
    }

    public void init(int height, int width)
    {
        this.gameState = new GameState(height, width, this.rnd);
        this.gameState.init(height, width);
        this.initSolver();
    }

    private void initSolver()
    {
        Node node = NodeFactory.generateTree(gameState, 4);
        this.solver.setRoot(node);
    }

    public void move(Direction dir)
    {
        this.gameState.move(dir);
    }

    public int[][] getGrid()
    {
        return gameState.getGrid();
    }

    public int getScore()
    {
        return gameState.getScore();
    }

    @Override
    public void addUpdateObserver(UpdateObserver method)
    {
        this.solver.addUpdateObserver(method);
    }

    public void solve()
    {
        Thread thread = new Thread(solver);
        thread.start();
    }
}
