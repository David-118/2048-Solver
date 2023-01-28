package uk.ac.rhul.project.game;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.heursitics.LargestLower;
import uk.ac.rhul.project.heursitics.SumCells;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateTest
{
    private GameState model_2x2;
    private GameState model_classic;
    private GameState model_rect1;
    private GameState model_rect2;

    @BeforeEach
    void setup()
    {
        Random r = new Random(2022);
        model_2x2 = new GameState(new GameConfiguration(2, 2, 0, null), r);
        model_classic = new GameState(new GameConfiguration(4, 4, 0, null), r);
        model_rect1 = new GameState(new GameConfiguration(9, 3, 0, null), r);
        model_rect2 = new GameState(new GameConfiguration(4, 5, 0, null), r);
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

    @Test
    void test_moveType()
    {
        this.model_2x2.init();
        assertEquals(model_2x2.getMoveType(), MoveType.MUTATION);
        for (Pair<GameState, Integer> move: this.model_2x2.getPossibleMoves())
        {
            assertEquals(move.getKey().getMoveType(), MoveType.PLAYER_MOVE);
            for (Pair<GameState, Double> mutation: move.getKey().getPossibleMutations())
            {
                assertEquals(mutation.getKey().getMoveType(), MoveType.MUTATION);
            }
        }
    }

    @Test
    void test_probabilities()
    {
        this.model_2x2.setGrid(new int[][]{{8, 0}, {0, 0}});
        List<Pair<GameState, Double>> mutations = this.model_2x2.getPossibleMutations();
        for (int i = 0; i < 6; i++)
        {
            assertEquals(mutations.get(i).getValue(), i % 2 == 0 ? (1d / 3) * 0.9d : (1d / 3) * 0.1d, 0.001);
        }

    }

    @Test
    void test_scoreDeltas()
    {
        this.model_classic.setGrid(new int[][]{
                {4, 4, 4, 4},
                {8, 4, 16, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });

        List<Pair<GameState, Integer>> move = model_classic.getPossibleMoves();
        int[] deltas = new int[]{8, 8, 16, 16};

        for (int i = 0; i < 4; i++)
        {
            assertEquals(deltas[i], move.get(i).getValue());
        }
    }
}