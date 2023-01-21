package uk.ac.rhul.project.heursitics;

import uk.ac.rhul.project.game.GameState;

import java.util.Arrays;

public class DynamicSnake implements Heuristic
{
    int size;
    double[][] powers;

    public DynamicSnake(int size)
    {
        this.size = size;
        this.powers = new double[size][size];

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                int k = i%2==0 ? j : size - j - 1;
                this.powers[i][j] = Math.pow(4, size*i + k);
            }
        }
    }
    @Override
    public float heuristic(GameState state)
    {
        float sum = 0;
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
        return String.format("Dynamic Snake", size, size);
    }
}
