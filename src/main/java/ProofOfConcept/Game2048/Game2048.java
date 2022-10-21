package ProofOfConcept.Game2048;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game2048
{
    private int width;
    private int height;
    private long score;
    private Random rnd;
    private int[][] grid;

    public Game2048(int width, int height, Random rnd)
    {
        this.width = width;
        this.height = height;
        this.rnd = rnd;
    }

    /*
     * Starts a game with 2 random starting cells.
     * This function can be called again to restart a game.
     */
    public void init()
    {
        this.grid = new int[width][height];
        this.score = 0;
        addRndCell();
        addRndCell();
    }

    public void loadCustomGame(int[][] grid)
    {
        this.grid = grid;
    }

    /*
     * Populates random free cells in the grid with free cells.
     */
    public void addRndCell()
    {
        List<Point> freeCells = this.getFreeCells();
        int i = this.rnd.nextInt(freeCells.size());
        this.grid[freeCells.get(i).x][freeCells.get(i).y] = this.rnd.nextFloat() > 0.9 ? 4 : 2;
    }

    private List<Point> getFreeCells()
    {
        List<Point> cells = new ArrayList<Point>(width*height);
        for (int i = 0; i < this.height; i++)
        {
            for (int j = 0; j < this.width; j++)
            {
                if (this.grid[i][j] == 0)
                {
                    cells.add(new Point(i, j));
                }
            }
        }
        return cells;
    }

    public boolean move(DirectionVect dir)
    {
        boolean flag = false;
        for (int i: dir.getVRange(this.height).toArray())
        {
            for (int j: dir.getHRange(this.width).toArray())
            {
                if (grid[i][j] != 0)
                {
                    if (moveCell(i, j, dir))
                    {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }


    @Override
    public String  toString()
    {
        return "Game2048{" +
                "width=" + width +
                ", height=" + height +
                ", grid=" + Arrays.deepToString(grid) +
                '}';
    }

    private boolean  moveCell(final int row, final int col, DirectionVect dir)
    {
        int i = row; int j = col;

        while(inGrid(i + dir.getI(), j + dir.getJ()) &&
                grid[i + dir.getI()][j + dir.getJ()] == 0)
        {
            i += dir.getI();
            j += dir.getJ();
        }

        if (inGrid(i + dir.getI(), j + dir.getJ()) &&
                grid[row][col] == grid[i + dir.getI()][j + dir.getJ()])
        {
            this.grid[i + dir.getI()][j + dir.getJ()] <<= 1;
            this.grid[row][col] = 0;
            this.score += this.grid[i + dir.getI()][j + dir.getJ()];
            return true;
        }
        else if (!(i == row && j == col))
        {
            this.grid[i][j] = this.grid[row][col];
            this.grid[row][col] = 0;
            return true;
        }
        return false;

    }

    private boolean inGrid(int i, int j)
    {
        return 0 <= i && i < this.width && 0 <= j && j < this.height;
    }

    public long getScore()
    {
        return score;
    }
}
