package uk.ac.rhul.project.game;

import java.util.Random;
/*
 * Based on the origonal source [2]
 *  Files:
 *    /js/game_manager.js
 *    /js/grid.js
 */
public class GameModel
{
    private int height;
    private int width;
    private int[][] grid;
    private Random random;

    /*
     * Create a model of the game 2048
     * @parm rows: The height of the games grid
     * @parm cols: The width the games grid
     * @parm random: the random number generator used to test the project. Allows for testing with a known seed.
     */
    public GameModel(int rows, int cols, Random random)
    {
        this.height = rows;
        this.width = cols;
        this.grid = new int[rows][cols];
        this.random = random;
    }

    /*
     * Create a model of the game 2048
     * @parm rows: The height of the games grid
     * @parm cols: The width the games grid
     */
    public GameModel(int rows, int cols)
    {
        this(rows, cols, new Random());
    }
}
