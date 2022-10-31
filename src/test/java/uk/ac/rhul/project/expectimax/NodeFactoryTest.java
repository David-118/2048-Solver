package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.game.GameState;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NodeFactoryTest
{
    @Test
    void test_generateTree()
    {
        GameState startState = new GameState(2, 2, new Random(0));
        startState.init();

        Node node = NodeFactory.generateTree(startState, 4);

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

    }
}