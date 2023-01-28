package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.LargestLower;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ExpectimaxTreeTest
{
    @Test
    void testGame()
    {
        Random random = new Random(1);
        GameState state3x3 = new GameState(new GameConfiguration(3, 3, 4, new LargestLower()), random);
        state3x3.init();
        ExpectimaxTree expectimaxTree = new ExpectimaxTree(state3x3, random,4, new LargestLower());

        try {
            while (true)
            {
                expectimaxTree.makeMove();
            }
        }
        catch (EndOfGameException endOfGame)
        {
            int[][] grid = endOfGame.getFinalState().getGrid();
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 2; j++)
                {
                    assertNotEquals(grid[i][j], grid[i][j+1]);
                    assertNotEquals(grid[j][i], grid[j+1][i]);
                }
                for (int j = 0; j < 3; j++)
                {
                    assertNotEquals(0, grid[i][j]);
                }
            }
        }
    }
}