package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.game.GameState;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest
{
    Node root;
    GameState state;
    @BeforeEach
    void setUp()
    {
        Random random = new Random(1);
        state = new GameState(2, 2, random);
        state.init();

        root = new Node(state);
        System.out.println("init game state" + Arrays.deepToString(state.getGrid()));
    }

    @Test
    void getGameState()
    {
        assertEquals(state, root.getGameState());
    }
}