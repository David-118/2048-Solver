package uk.ac.rhul.project.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameState implements Cloneable
{
    private final static float PROB_OF_4 = 0.1f;
    private static final int INITIAL_CELL_COUNT = 2;
    private int height;
    private int width;
    private final Random random;
    private int[][] grid;
    private int score;


    public GameState(int rows, int cols, Random random)
    {
        this.height = rows;
        this.width = cols;
        this.grid = new int[rows][cols];
        this.random = random;
    }

    public GameState(int row, int cols)
    {
        this(row, cols, new Random());
    }

    public void init()
    {
        init(this.height, this.width);
    }

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
    public void setGrid(int[][] grid)
    {
        this.grid = grid;
    }

    /*
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


    /*
     * Get a list of all the cells that have free cells.
     * Stored as a Point where:
     *    x refers to the row
     *    y refers to the column
     */
    private List<Point> getFreeCells()
    {
        List<Point> freeCells = new ArrayList<>(width * height);
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

    public boolean move(Direction dir)
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

    public GameState[] getPossibleMoves()
    {
        List<GameState> gameStates = new ArrayList<>(4);

        for(Direction dir: Direction.values())
        {
            GameState gameState = this.clone();
            if (gameState.move(dir))
            {
                gameStates.add(gameState);
            }
        }

        return gameStates.toArray(new GameState[0]);
    }

    public GameState[] getPossibleMutations()
    {
        List<Point> freeCells = this.getFreeCells();
        List<GameState> gameStates = new ArrayList<>(freeCells.size() * 2);

        for (Point freeCell: freeCells)
        {
            GameState gameState1 = this.clone();
            GameState gameState2 = this.clone();

            gameState1.grid[freeCell.x][freeCell.y] = 2;
            gameState2.grid[freeCell.x][freeCell.y] = 4;

            gameStates.add(gameState1);
            gameStates.add(gameState2);
        }

        return gameStates.toArray(new GameState[0]);
    }

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

    private int nextCellValue(int row, int col, Direction dir)
    {
        return this.grid[row + dir.getRows()][col + dir.getCols()];
    }

    private boolean nextCellInGrid(int row, int col, Direction dir)
    {
        row += dir.getRows();
        col += dir.getCols();
        return 0 <= row && row < this.height && 0 <= col && col < this.width;
    }

    public int[][] getGrid()
    {
        return this.grid;
    }

    public int getScore()
    {
        return this.score;
    }

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
}
