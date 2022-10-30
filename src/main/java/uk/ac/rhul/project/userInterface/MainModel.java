package uk.ac.rhul.project.userInterface;

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

    /*
     * Create a model of the game 2048
     * @parm rows: The height of the games grid
     * @parm cols: The width the games grid
     * @parm random: the random number generator used to test the project. Allows for testing with a known seed.
     */
    public MainModel(int rows, int cols, Random random)
    {
        this.gameState = new GameState(rows, cols, random);
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
    }

    public void init(int height, int width)
    {
        this.gameState.init(height, width);
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
}
