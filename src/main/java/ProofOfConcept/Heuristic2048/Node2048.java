package ProofOfConcept.Heuristic2048;

import ProofOfConcept.Game2048.DirectionVect;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Node2048
{
    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    protected float nodeScore;
    private int width;
    private int height;
    private long score;
    protected Random rnd;
    private int[][] grid;

    protected Node2048[] children;

    public Node2048()
    {
        this(new Random());
    }

    public Node2048(Random rnd)
    {
        this.width = 4;
        this.height = 4;
        this.rnd = rnd;
    }

    protected Node2048 cloneAs(Node2048 as)
    {
        as.grid = new int[this.height][this.width];
        as.score = this.score;
        as.rnd = rnd;

        for (int i = 0; i < this.height; i++)
        {
            for (int j = 0; j < this.width; j++)
            {
                as.grid[i][j] = this.grid[i][j];
            }
        }

        return as;
    }

    protected void setChildren(Node2048[] children)
    {
        this.children = children;
    }

    /*
     * Starts a game with 2 random starting cells.
     * This function can be called again to restart a game.
     */
    public void init()
    {
        this.grid = new int[height][width];
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

    public abstract void expectimax(int maxDepth);

    public List<Point> getFreeCells()
    {
        List<Point> cells = new ArrayList<>(width*height);
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
        boolean[][] merged = new boolean[height][width];
        boolean flag = false;
        for (int i: dir.getVRange(this.height).toArray())
        {
            for (int j: dir.getHRange(this.width).toArray())
            {
                if (grid[i][j] != 0)
                {
                    if (moveCell(i, j, dir, merged))
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
        return Arrays.deepToString(grid);
    }

    private boolean  moveCell(final int row, final int col, DirectionVect dir, boolean[][] merged)
    {

        int i = row; int j = col;

        while(inGrid(i + dir.getI(), j + dir.getJ()) &&
                grid[i + dir.getI()][j + dir.getJ()] == 0)
        {
            i += dir.getI();
            j += dir.getJ();
        }

        if (inGrid(i + dir.getI(), j + dir.getJ()) &&
                grid[row][col] == grid[i + dir.getI()][j + dir.getJ()] &&
                !merged[i + dir.getI()][j + dir.getJ()])
        {
            this.grid[i + dir.getI()][j + dir.getJ()] <<= 1;
            merged[i + dir.getI()][j + dir.getJ()] = true;
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
        return 0 <= i && i < this.height && 0 <= j && j < this.width;
    }

    public long getScore()
    {
        return score;
    }

    public void print()
    {
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                System.out.printf("%5d", this.grid[i][j]);
            }
            System.out.println();
        }
    }

    protected void setCellValue(int i, int j, int val)
    {
        this.grid[i][j] = val;
    }

    public abstract Node2048 nextNode();

    public float heuristic()
    {
        float score = 0;
        for (int i = 0; i < this.getHeight(); i++)
        {
            for (int j = 0; j < this.getWidth(); j++)
            {
                score += grid[i][j] * i;
            }
        }

        return score;
    }
}
