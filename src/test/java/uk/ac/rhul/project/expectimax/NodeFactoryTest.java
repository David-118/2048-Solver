package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.game.GameState;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NodeFactoryTest
{
    private GameState startState;
    private Node node;
    private Node node2;

    @BeforeEach
    void setup()
    {
        this.startState = new GameState(2, 2, new Random(0));
        this.startState.init();

        this.node = NodeFactory.generateTree(startState, 4);
        this.node2 = NodeFactory.generateTree(startState, 2);
    }


    @Test
    void test_generateTree()
    {
        assertEquals("[[0, 2], [2, 0]]", Arrays.deepToString(node.getGameState().getGrid()));

        assertEquals("[[2, 2], [0, 0]]", Arrays.deepToString(node.getChildren()[0].getGameState().getGrid()));
        assertEquals("[[0, 0], [2, 2]]", Arrays.deepToString(node.getChildren()[1].getGameState().getGrid()));
        assertEquals("[[2, 0], [2, 0]]", Arrays.deepToString(node.getChildren()[2].getGameState().getGrid()));
        assertEquals("[[0, 2], [0, 2]]", Arrays.deepToString(node.getChildren()[3].getGameState().getGrid()));

        assertEquals("[[2, 2], [2, 0]]",
                Arrays.deepToString(node.getChildren()[0].getChildren()[0].getGameState().getGrid()));
        assertEquals("[[2, 2], [4, 0]]",
                Arrays.deepToString(node.getChildren()[0].getChildren()[1].getGameState().getGrid()));
        assertEquals("[[2, 2], [0, 2]]",
                Arrays.deepToString(node.getChildren()[0].getChildren()[2].getGameState().getGrid()));
        assertEquals("[[2, 2], [0, 4]]",
                Arrays.deepToString(node.getChildren()[0].getChildren()[3].getGameState().getGrid()));

        assertEquals("[[4, 2], [0, 0]]",
                Arrays.deepToString(node.getChildren()[0].getChildren()[0].getChildren()[0].getGameState().getGrid()));
        assertEquals("[[0, 0], [4, 2]]",
                Arrays.deepToString(node.getChildren()[0].getChildren()[0].getChildren()[1].getGameState().getGrid()));
        assertEquals("[[4, 0], [2, 0]]",
                Arrays.deepToString(node.getChildren()[0].getChildren()[0].getChildren()[2].getGameState().getGrid()));
        assertEquals("[[0, 4], [0, 2]]",
                Arrays.deepToString(node.getChildren()[0].getChildren()[0].getChildren()[3].getGameState().getGrid()));

        assertEquals("[[4, 2], [2, 0]]",
                Arrays.deepToString(node.getChildren()[0].getChildren()[0]
                        .getChildren()[0].getChildren()[0].getGameState().getGrid()));
        assertEquals("[[4, 2], [4, 0]]",
                Arrays.deepToString(node.getChildren()[0].getChildren()[0]
                        .getChildren()[0].getChildren()[1].getGameState().getGrid()));
        assertEquals("[[4, 2], [0, 2]]",
                Arrays.deepToString(node.getChildren()[0].getChildren()[0]
                        .getChildren()[0].getChildren()[2].getGameState().getGrid()));
        assertEquals("[[4, 2], [0, 4]]",
                Arrays.deepToString(node.getChildren()[0].getChildren()[0]
                        .getChildren()[0].getChildren()[3].getGameState().getGrid()));
    }

    @Test
    void test_weights()
    {
        for (int i = 0; i < 4; i++)
        {
            assertEquals(1f, node.getChildren()[i].getWeight());

            if (i % 2 == 0)
            {
                assertEquals(0.45f, node.getChildren()[0].getChildren()[i].getWeight());
            } else
            {
                assertEquals(0.05f, node.getChildren()[0].getChildren()[i].getWeight());
            }
        }
    }

    @Test
    void test_heuristic()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j+=2)
            {
                assertEquals(6f, node2.getChildren()[i].getChildren()[j].getGameState().getHeuristic());
                assertEquals(8f, node2.getChildren()[i].getChildren()[j + 1].getGameState().getHeuristic());
            }
        }
    }

    @Test
    void test_expectimax()
    {
        for (int i = 0; i < 4; i++)
        {
            assertEquals(1.55f, this.node2.getChildren()[i].expectimax());
        }

        assertEquals(1.55f, this.node2.expectimax());

    }
}