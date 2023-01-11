package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Diagonal;
import uk.ac.rhul.project.heursitics.LargestLower;
import uk.ac.rhul.project.heursitics.Snake;
import uk.ac.rhul.project.heursitics.SumCells;

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
    }

    @Test
    void nextNodeLeafNode()
    {
        assertThrows(EndOfGameException.class, root2x2::nextNode);
    }

    @Test
    void applyHeuristicLeafNode()
    {
        assertEquals(4f, state2x2.applyHeuristic(new SumCells()));
        assertEquals(6f, state2x2.applyHeuristic(new LargestLower()));
        assertEquals(1.5707E11, root4x4.applyHeuristic(new Snake()), 0.00005E11);
        assertEquals(122692f, root4x4.applyHeuristic(new Diagonal()));
    }
}