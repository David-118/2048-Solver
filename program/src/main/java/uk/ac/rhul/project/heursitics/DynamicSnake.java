package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

import java.util.Arrays;

public class DynamicSnake implements Heuristic
{
    double[][] powers;

    private final int rows, cols;

    public DynamicSnake(int row, int col)
    {
        this.powers = new double[row][col];
        this.rows = row; this.cols = col;

        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                int k = i%2==0 ? j : row - j - 1;
                this.powers[i][j] = Math.pow(4, col*i + k);
            }
        }
    }
    @Override
    public double heuristic(GameState state)
    {
        double sum = 0;
        int[][] grid = state.getGrid();
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[0].length; j++)
            {
                if (this.powers[i][j] != rows*cols - 1 - cols || grid[i][j] < grid[i+1][j])
                {
                    sum += grid[i][j] * this.powers[i][j];
                }
            }
        }
        return sum;
    }

    @Override
    public String getName()
    {
        return String.format("Dynamic Snake %dx%d", this.rows, this.cols);
    }
}
