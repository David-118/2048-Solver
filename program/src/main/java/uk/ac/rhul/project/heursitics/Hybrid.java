package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

public class Hybrid implements Heuristic
{
    double[][] powers;

    private final int rows, cols;

    public Hybrid(int row, int col)
    {
        this.powers = new double[row][col];
        this.rows = row; this.cols = col;

        int max_power = (rows - 1) + 2 * cols;

        for (int j = 0; j < col; j++)
        {
            for (int i = 0; i < rows - 1; i++)
            {
                this.powers[i][j] = Math.pow(4, i + j);
            }
            this.powers[rows - 1][col] = Math.pow(4, max_power - 1);
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
                sum += grid[i][j] * this.powers[i][j];
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
