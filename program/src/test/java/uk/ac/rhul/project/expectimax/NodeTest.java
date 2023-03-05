package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.FailSetter;
import uk.ac.rhul.project.heursitics.Monotonic;
import uk.ac.rhul.project.heursitics.SumCells;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
class NodeTest
{
    GameConfiguration conf;
    Node root;
    GameState inital;

    @BeforeEach
    void setUp()
    {
        this.conf = new GameConfiguration(4, 4, 6, new FailSetter(new Monotonic(), -Math.pow(10, 3)));
        this.conf.setSeed(0L);
        this.inital = new GameState(conf);
        this.inital.init();
        this.inital.setProbability(1);
        this.root = new Node(inital, NodeBehaviourMaximize::generate, conf.getRandom());
    }

    @Test
    void getGameState()
    {
        assertEquals(this.inital, this.root.getGameState());
        assertEquals("[[0, 0, 0, 0], [2, 0, 0, 0], [0, 0, 0, 2], [0, 0, 0, 0]]",
                Arrays.deepToString(this.inital.getGrid()));
    }

    @Test
    void nextNode()
    {
        this.root.generateChildren(1);
        try
        {
            Node nxt = this.root.nextNode(this.conf.getHeuristic());
            assertEquals("[[2, 0, 0, 2], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]",
                    Arrays.deepToString(nxt.getGameState().getGrid()));

            nxt.generateChildren(1);

            assertEquals("[[2, 0, 0, 2], [0, 0, 0, 0], [0, 0, 4, 0], [0, 0, 0, 0]]",
                    Arrays.deepToString(nxt.nextNode(this.conf.getHeuristic()).getGameState().getGrid()));
        } catch (EndOfGameException e)
        {
            fail(e);
        }
    }

    @Test
    void applyHeuristic()
    {
        assertEquals(194D, root.applyHeuristic(conf.getHeuristic()));
        root.generateChildren(6);

        assertEquals(165.649045D, root.applyHeuristic(conf.getHeuristic()), 0.0000005D);
    }

    @Test
    void generateChildren()
    {
        root.generateChildren(6);
        try
        {
            assertInstanceOf(Node.class,
                    root.nextNode(conf.getHeuristic())
                            .nextNode(conf.getHeuristic())
                            .nextNode(conf.getHeuristic()));
        }
        catch (EndOfGameException ex)
        {
            fail(ex);
        }
    }

    @Test
    void getWeight()
    {
        assertEquals(1.0D, root.getWeight());

        inital.setProbability(0.5);
        root = new Node(inital, NodeBehaviourMaximize::generate, conf.getRandom());
        assertEquals(0.5D, root.getWeight());

        inital.setProbability(0.25);
        root = new Node(inital, NodeBehaviourMaximize::generate, conf.getRandom());
        assertEquals(0.25, root.getWeight());
    }

    @Test
    void toTxt()
    {
        assertEquals("#0#0#0#0#2#0#0#0#0#0#0#2#0#0#0#0#194.0#194.0L\n", this.root.toTxt(0, conf.getHeuristic()));
        assertEquals(" #0#0#0#0#2#0#0#0#0#0#0#2#0#0#0#0#194.0#194.0L\n", this.root.toTxt(1, conf.getHeuristic()));
        root.generateChildren(1);
        assertEquals(
                "#0#0#0#0#2#0#0#0#0#0#0#2#0#0#0#0#194.0#202.0M\n" +
                " #2#0#0#2#0#0#0#0#0#0#0#0#0#0#0#0#202.0#202.0L\n" +
                " #0#0#0#0#0#0#0#0#0#0#0#0#2#0#0#2#202.0#202.0L\n" +
                " #0#0#0#0#2#0#0#0#2#0#0#0#0#0#0#0#198.0#198.0L\n" +
                " #0#0#0#0#0#0#0#2#0#0#0#2#0#0#0#0#198.0#198.0L\n",
                this.root.toTxt(0, conf.getHeuristic()));
    }

    @Test
    void testToString()
    {
        root.generateChildren(1);
        assertEquals("[[0, 0, 0, 0], [2, 0, 0, 0], [0, 0, 0, 2], [0, 0, 0, 0]]", this.root.toString());
        try
        {
            assertEquals("[[2, 0, 0, 2], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]", this.root.nextNode(conf.getHeuristic()).toString());
        } catch (EndOfGameException e)
        {
            fail(e);
        }
    }
}