package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Diagonal;
import uk.ac.rhul.project.heursitics.LargestLower;
import uk.ac.rhul.project.heursitics.Snake;
import uk.ac.rhul.project.heursitics.SumCells;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest
{
    Node root2x2;
    GameState state2x2;

    Node root4x4;
    GameState state4x4;
    @BeforeEach
    void setUp()
    {
        Random random = new Random(1);
        state2x2 = new GameState(2, 2, random);
        state2x2.init();

        state4x4 = new GameState(4, 4, random);
        state4x4.init();
        state4x4.setGrid(new int[][]{
                {128, 64, 32, 16},
                {  0,  2,  4,  8},
                {  0,  0,  2,  4},
                {  0,  0,  0,  2},
        });

        root2x2 = new Node(state2x2);
        root4x4 = new Node(state4x4);
    }

    @Test
    void getGameState()
    {
        assertEquals(state2x2, root2x2.getGameState());
        assertEquals(state4x4, root4x4.getGameState());
    }

    @Test
    void nextNodeLeafNode()
    {
        assertThrows(EndOfGameException.class, () -> this.root2x2.nextNode(new SumCells()));
        assertThrows(EndOfGameException.class, () -> this.root4x4.nextNode(new SumCells()));
    }

    @Test
    void applyHeuristicLeafNode()
    {
        assertEquals(4f, state2x2.applyHeuristic(new SumCells()));
        assertEquals(6f, state2x2.applyHeuristic(new LargestLower()));
        assertEquals(1.5707E11, root4x4.applyHeuristic(new Snake()), 0.00005E11);
        assertEquals(122692f, root4x4.applyHeuristic(new Diagonal()));
    }

    @Test
    void nextNodeMaxNode()
    {
        try
        {
            this.root4x4.generateChildren();
            assertEquals("[[0, 0, 0, 16], [0, 0, 32, 8], [0, 64, 4, 4], [128, 2, 2, 2]]",
                    Arrays.deepToString(root4x4.nextNode(new LargestLower()).getGameState().getGrid()));
        } catch (EndOfGameException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    void applyHeuristicMaxNode()
    {
        this.root2x2.generateChildren();
        assertEquals(8f, this.root2x2.applyHeuristic(new LargestLower()));
    }
}