package uk.ac.rhul.project.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.heursitics.LargestLower;
import uk.ac.rhul.project.heursitics.SumCells;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateTest
{
    private GameState model_2x2;
    private GameState model_classic;
    private GameState model_rect1;
    private GameState model_rect2;

    private GameConfiguration gameConfig(int n, int m)
    {
        return new GameConfiguration(n, m, 0, null);
    }

    @BeforeEach
    void setup()
    {
        Random r = new Random(2022);
        model_2x2 = new GameState(gameConfig(2, 2), r);
        model_classic = new GameState(gameConfig(4, 4), r);
        model_rect1 = new GameState(gameConfig(9, 3), r);
        model_rect2 = new GameState(gameConfig(4, 5), r);
    }
    @Test
    void test_init()
    {
        this.model_2x2.init();
        assertEquals("[[0, 0], [2, 2]]", Arrays.deepToString(this.model_2x2.getGrid()));

        this.model_2x2.init();
        assertEquals("[[2, 0], [2, 0]]", Arrays.deepToString(this.model_2x2.getGrid()));

        this.model_classic.init();
        assertEquals("[[0, 0, 0, 0], [0, 2, 0, 0], [0, 0, 0, 2], [0, 0, 0, 0]]",
                Arrays.deepToString(this.model_classic.getGrid()));

        this.model_rect1.init();
        assertEquals("[[0, 0, 0], [0, 0, 0], [0, 0, 0], [0, 0, 0], [2, 0, 0]," +
                        " [0, 0, 0], [0, 0, 0], [0, 4, 0], [0, 0, 0]]",
                Arrays.deepToString(this.model_rect1.getGrid()));

        this.model_rect2.init();
        assertEquals("[[0, 4, 0, 0, 0], [2, 0, 0, 0, 0], [0, 0, 0, 0, 0], [0, 0, 0, 0, 0]]",
                Arrays.deepToString(this.model_rect2.getGrid()));
    }

    @Test
    void test_heuristic()
    {
        this.model_2x2.setGrid(new int[][]{{16, 8}, {4, 2}});

        assertEquals(30, this.model_2x2.applyHeuristic(new SumCells()));
        assertEquals(36, this.model_2x2.applyHeuristic(new LargestLower()));

        this.model_rect1.setGrid(new int[][]{
                {2, 4, 8}, {64, 32, 16}, {128, 256, 512},
                {2, 4, 8}, {64, 32, 16}, {128, 256, 512},
                {2, 4, 8}, {64, 32, 16}, {128, 256, 512},
        });

        assertEquals(3066, this.model_rect1.applyHeuristic(new SumCells()));
        assertEquals(17976, this.model_rect1.applyHeuristic(new LargestLower()));
    }
}