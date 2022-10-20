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
    private void addRndCell()
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

    public void move(DirectionVect dir)
    {
        dir.getVRange(this.height).forEach(i -> {
            dir.getHRange(this.width).forEach(j -> {
                this.grid[i + dir.getI()][j + dir.getJ()] = this.grid[i][j];
                this.grid[i][j] = 0;
            });
        });
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
}
