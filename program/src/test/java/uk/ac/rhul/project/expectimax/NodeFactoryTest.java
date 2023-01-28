package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.SumCells;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NodeFactoryTest
{
    private NodeFactory factory;
    private GameState state;
    @BeforeEach
    void setUp()
    {
        Random random = new Random(1111);
        state = new GameState(new GameConfiguration(2, 2, 4, new SumCells()), random);
        state.init();
        state.setGrid(new int[][]{
                {2, 2},
                {0, 0},
        });

        StateScoreTracker tracker = new StateScoreTracker();
        tracker.setState(state);

        factory = new NodeFactory(random, tracker);
    }

    @Test
    public void test_createNewNode()
    {
        Node node = factory.createNode(state);
        assertEquals(state, node.getGameState());
    }
}