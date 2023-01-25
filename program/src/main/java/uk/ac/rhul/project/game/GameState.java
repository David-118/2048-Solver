package uk.ac.rhul.project.game;

import uk.ac.rhul.project.heursitics.Heuristic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Represents the state of the game.
 *
 * <p><br>Based on the original source [2]
 * Files:</p>
 * <ul>
 * <li>/js/game_manager.js</li>
 * <li>/js/grid.js</li>
 * </ul>
 */
public class GameState implements Cloneable
{
    /**
     * Probability of a 4 appearing in the gird.
     */
    private final static float PROB_OF_4 = 0.1f;

    /**
     * The numbers tiles created when a game is created.
     */
    private static final int INITIAL_CELL_COUNT = 2;

    /**
     * Height of the game's grid.
     */
    private int height;

    /**
     * Width of the game's grid.
     */
    private int width;

    /**
     * Random number generator used to add random cells.
     */
    private final Random random;

    /**
     * Represents the grid in 2048, stored as grid[rows][columns].
     */
    private int[][] grid;

    /**
     * Stores the current score of the game.
     */
    private int score;

    public float getProbability()
    {
        return probability;
    }

    public void setProbability(float probability)
    {
        this.probability = probability;
    }

    private float probability;


    /**
     * Create a game state.
     * @param rows The number of rows in the games grid.
     * @param cols The number of cols in the games grid.
     * @param random The random number generator used to place new tiles.
     */
    public GameState(GameConfiguration gameConfiguration, Random random)
    {
        this.height = gameConfiguration.getRows();
        this.width = gameConfiguration.getCols();
        this.grid = new int[height][width];
        this.random = random;
    }

    /**
     * Create a game state.
     * @param rows The number of rows in the games grid.
     * @param cols The number of cols in the games grid.
     */
    public GameState(GameConfiguration gameConfiguration)
    {
        this(gameConfiguration, new Random());
    }

    /**
     * Initialise new game of the same size as the previous, adds two random start tiles to the game.
     */
    public void init()
    {
        init(this.height, this.width);
    }

    /**
     * Initialise a new game with the specified size, adds two random start tiles to the game.
     * @param height New height of the game.
     * @param width New width of the game.
     */
    public void init(int height, int width)
    {
        this.height = height;
        this.width = width;
        this.score = 0;
        this.grid = new int[this.height][this.width];
        for (int i = 0; i < INITIAL_CELL_COUNT; i++)
        {
            this.addRandomCell();
        }
    }

    /**
     * Load a custom array into the 2048 game. This is partially useful in the testsuite.
     * @param grid The gird for the game
     */
    public void setGrid(int[][] grid)
    {
        this.grid = grid;
    }

    /**
     * Adds a random cell to the grid, used both when initialising
     * a new game, and after a move has been made successfully.
     */
    private void addRandomCell()
    {
        List<Point> cells = this.getFreeCells();
        int size = cells.size();

        // Select random cell from free cells in grid
        Point cell = cells.get(this.random.nextInt(size));

        // Set cell to 4 PROB_OF_4 of the time otherwise set to 2.
        int value = random.nextFloat() < PROB_OF_4 ? 4 : 2;
        this.grid[cell.x][cell.y] = value;
    }


    /**
     * Get a list of all the cells that have free cells.
     * <p>
     * Stored as a Point where:
     * </p>
     * <ul>
     *    <li>x refers to the row</li>
     *    <li>y refers to the column</li>
     * </ul>
     *
     * @return List of the positions were there is a free space.e
     */
    private List<Point> getFreeCells()
    {
        List<Point> freeCells = new ArrayList(height * width);
        for (int i = 0; i < this.height; i++)
        {
            for (int j = 0; j < this.width; j++)
            {
                if (this.grid[i][j] == 0)
                {
                    Point cell = new Point(i, j);
                    freeCells.add(cell);
                }
            }
        }

        return freeCells;
    }


    /**
     * Move tiles in a given direction,
     * @param dir The direction for the tiles to move.
     * @return True if any changes are made to the grid.
     */
    private boolean move(Direction dir)
    {
        boolean[][] merged = new boolean[this.height][this.width];
        boolean flag = false;

        for (int i : dir.getVerticalStream(this.height))
        {
            for (int j: dir.getHorizontalStream(this.width))
            {
                if (grid[i][j] != 0 && this.slideTile(i, j, dir, merged))
                {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * Generates all the possible moves.
     * @return An array of the possible moves.
     */
    public List<GameState> getPossibleMoves()
    {
        List<GameState> possibleMoves = new ArrayList<>(4);

        for(Direction dir: Direction.values())
        {
            GameState gameState = this.clone();
            if (gameState.move(dir))
            {
                gameState.probability = 1;
                possibleMoves.add(gameState);
            }
        }

        return possibleMoves;
    }

    /**
     * Generates all the possible states that can be generated from adding a random tile.
     * @return Array with the possible mutations.
     */
    public GameState[] getPossibleMutations()
    {
        List<Point> freeCells = this.getFreeCells();
        GameState[] states = new GameState[freeCells.size() * 2];

        final float CHANCE_OF_2 = (1f / freeCells.size()) * (1 - PROB_OF_4);
        final float CHANCE_OF_4 = (1f / freeCells.size()) * PROB_OF_4;

        for (int i = 0; i < freeCells.size(); i++)
        {
            GameState gameState1 = this.clone();
            GameState gameState2 = this.clone();

            gameState1.grid[freeCells.get(i).x][freeCells.get(i).y] = 2;
            gameState2.grid[freeCells.get(i).x][freeCells.get(i).y] = 4;

            gameState1.setProbability(CHANCE_OF_2);
            gameState2.setProbability(CHANCE_OF_4);

            states[2*i]= gameState1;
            states[2*i + 1] = gameState2;
        }

        return states;
    }

    /**
     * Slide a specific tile in a specific direction and merge is possible.
     * @param row The row the tile is on.
     * @param col The column the tile is on.
     * @param dir The direction to move the tile in.
     * @param merged Array that keeps track of which tiles hae been merged .
     * @return Returns true if the tile is moved or merged.
     */
    private boolean slideTile(final int row, final int col, Direction dir, boolean[][] merged)
    {
        int target_row = row;
        int target_col = col;

        // Calculate how far the tile can be moved (assuming no merge)
        while (nextCellInGrid(target_row, target_col, dir) && this.nextCellValue(target_row, target_col, dir) == 0)
        {
            target_row += dir.getRows();
            target_col += dir.getCols();
        }

        // Check for possible merge
        if (nextCellInGrid(target_row, target_col, dir) &&
                this.nextCellValue(target_row, target_col, dir) == this.grid[row][col] &&
                !merged[target_row][target_col] && !merged[target_row + dir.getRows()][target_col + dir.getCols()])
        {
            target_row += dir.getRows();
            target_col += dir.getCols();

            merged[target_row][target_col] = true;

            this.grid[target_row][target_col] <<= 1;
            this.score += this.grid[target_row][target_col];
        }  // move but no merge
        else if (target_row != row || target_col != col)
        {
            this.grid[target_row][target_col] = this.grid[row][col];
        }  // No move made
        else {
            return false;
        }

        // Remove previous cell
        this.grid[row][col] = 0;

        return true;
    }

    /**
     * Get the value of the  cell next to the position entered the specified direction.
     * @param row Row of the original cell.
     * @param col Column of the original cell.
     * @param dir Direction to look in
     * @return value of the cells neighbour.
     */
    private int nextCellValue(int row, int col, Direction dir)
    {
        return this.grid[row + dir.getRows()][col + dir.getCols()];
    }

    /**
     * Test if a cells neighbour is in the game's grid.
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @param dir The direction of the cells neighbour.
     * @return True if the neighbour is in the gird.
     */
    public boolean nextCellInGrid(int row, int col, Direction dir)
    {
        row += dir.getRows();
        col += dir.getCols();
        return 0 <= row && row < this.height && 0 <= col && col < this.width;
    }

    /**
     * Gets the games grid.
     * @return returns the array representing the grid.
     */
    public int[][] getGrid()
    {
        return this.grid;
    }


    /**
     * Gets the score of game.
     * @return The score of the game.
     */
    public int getScore()
    {
        return this.score;
    }

    /**
     * Creates a clone of the game.
     * @return clone of the game state.
     */
    @Override
    public GameState clone()
    {
        try
        {
            GameState clone = (GameState) super.clone();
            clone.grid = new int[this.height][this.width];

            for (int i = 0; i < this.height; i++)
            {
                clone.grid[i] = this.grid[i].clone();
            }

            return clone;
        } catch (CloneNotSupportedException e)
        {
            throw new RuntimeException(e);
        }
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    /**
     * Uses a heuristic function to score the game state.
     * @param heuristic The heuristic function.
     * @return heuristic(this)
     */
    public float applyHeuristic(Heuristic heuristic)
    {
        return heuristic.heuristic(this);
    }

    @Override
    public String toString()
    {
        return Arrays.deepToString(this.grid);
    }
}
