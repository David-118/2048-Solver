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

        int max_power = (row - 3) + (col - 1) + 2 * col;

        for (int j = 0; j < col; j++)
        {
            for (int i = 0; i < rows - 2; i++)
            {
                this.powers[i][j] = Math.pow(4, i + col - j - 1);
            }
            this.powers[rows - 1][j] = Math.pow(4, max_power - 2*col + 1 + j);
            this.powers[rows - 1][j] = Math.pow(4, max_power - j);
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
