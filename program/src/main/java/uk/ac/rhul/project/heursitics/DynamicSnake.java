package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

import java.util.Arrays;

public class DynamicSnake implements Heuristic
{
    double[][][] powers;

    private final int rows, cols;

    public DynamicSnake(int row, int col)
    {
        this.powers = new double[8][row][col];
        this.rows = row; this.cols = col;

        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                int k = i%2==0 ? j : row - j - 1;
                this.powers[0][i][j] = Math.pow(4, col*i + k);
                this.powers[1][i][col - 1 - j] = this.powers[0][i][j];
                this.powers[2][row - 1 - i][j] = this.powers[0][i][j];
                this.powers[3][row - 1 - i][j] = this.powers[1][i][j];

                int l = j%2==0 ? i : row - i - 1;

                this.powers[4][i][j] = Math.pow(4, row*j + l);
                this.powers[5][i][col - 1 - j] = this.powers[4][i][j];
                this.powers[6][row - 1 - i][j] = this.powers[4][i][j];
                this.powers[7][row - 1 - i][j] = this.powers[5][i][j];


            }
        }
    }
    @Override
    public double heuristic(GameState state)
    {
        double maxSum = 0;
        int[][] grid = state.getGrid();
        for (int k = 0; k < 8; k++)
        {
            double sum = 0;
            for (int i = 0; i < grid.length; i++)
            {
                for (int j = 0; j < grid[0].length; j++)
                {
                    sum += grid[i][j] * this.powers[k][i][j];
                }
            }
            if (sum > maxSum) maxSum = sum;
        }
        return maxSum;
    }

    @Override
    public String getName()
    {
        return String.format("Dynamic Snake %dx%d", this.rows, this.cols);
    }
}
