package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.FailSetter;
import uk.ac.rhul.project.heursitics.Monotonic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
class NodeTest
{
    GameConfiguration conf;
    Node root;
    GameState initial;
    AtomicInteger counter;


    @BeforeEach
    void setUp()
    {
        this.conf = new GameConfiguration(4, 4, 6, Integer.MAX_VALUE, new FailSetter(new Monotonic(), -Math.pow(10, 3)));
        this.conf.setSeed(0L);
        this.initial = new GameState(conf);
        this.initial.init();
        this.initial.setProbability(1);
        this.root = new Node(initial, NodeBehaviourMaximize::generate, conf.getRandom());
        this.counter = new AtomicInteger(0);
    }

    @Test
    void getGameState()
    {
        assertEquals(this.initial, this.root.getGameState());
        assertEquals("[[0, 0, 0, 0], [2, 0, 0, 0], [0, 0, 0, 2], [0, 0, 0, 0]]",
                Arrays.deepToString(this.initial.getGrid()));
    }

    @Test
    void nextNode()
    {
        this.root.generateChildren(1, Integer.MAX_VALUE, counter,0);
        try
        {
            Node nxt = this.root.nextNode(this.conf.getHeuristic());
            assertEquals("[[2, 0, 0, 2], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]",
                    Arrays.deepToString(nxt.getGameState().getGrid()));

            nxt.generateChildren(1, Integer.MAX_VALUE, counter, 0);

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
        root.generateChildren(6, Integer.MAX_VALUE, counter, 0);

        assertEquals(165.649045D, root.applyHeuristic(conf.getHeuristic()), 0.0000005D);
    }

    @Test
    void generateChildren()
    {
        root.generateChildren(6, Integer.MAX_VALUE, counter, 0);
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

        initial.setProbability(0.5);
        root = new Node(initial, NodeBehaviourMaximize::generate, conf.getRandom());
        assertEquals(0.5D, root.getWeight());

        initial.setProbability(0.25);
        root = new Node(initial, NodeBehaviourMaximize::generate, conf.getRandom());
        assertEquals(0.25, root.getWeight());
    }

    @Test
    void toTxt()
    {
        assertEquals("#0#0#0#0#2#0#0#0#0#0#0#2#0#0#0#0#194.0#194.0L\n", this.root.toTxt(0, conf.getHeuristic()));
        assertEquals(" #0#0#0#0#2#0#0#0#0#0#0#2#0#0#0#0#194.0#194.0L\n", this.root.toTxt(1, conf.getHeuristic()));
        root.generateChildren(1, Integer.MAX_VALUE, counter, 0);
        assertEquals(
                "#0#0#0#0#2#0#0#0#0#0#0#2#0#0#0#0#194.0#202.0M\n" +
                " #2#0#0#2#0#0#0#0#0#0#0#0#0#0#0#0#202.0#202.0L\n" +
                " #0#0#0#0#0#0#0#0#0#0#0#0#2#0#0#2#202.0#202.0L\n" +
                " #0#0#0#0#2#0#0#0#2#0#0#0#0#0#0#0#198.0#198.0L\n" +
                " #0#0#0#0#0#0#0#2#0#0#0#2#0#0#0#0#198.0#198.0L\n",
                this.root.toTxt(0, conf.getHeuristic()));
    }


    @Test
    void testPruning() {
        root.generateChildren(4, 1, counter, 0);
        String real = root.toTxt(0, conf.getHeuristic());

        try (InputStream stream = getClass().getResourceAsStream("tree-00000000.pruned.tree")) {
            byte[] expected = stream.readAllBytes();
            assertEquals(new String(expected), real);
        } catch (IOException ex) {
            fail(ex);
        }
    }
    @Test
    void testToString()
    {
        root.generateChildren(1, Integer.MAX_VALUE, counter, 0);
        assertEquals("[[0, 0, 0, 0], [2, 0, 0, 0], [0, 0, 0, 2], [0, 0, 0, 0]]", this.root.toString());
        try
        {
            assertEquals("[[2, 0, 0, 2], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]", this.root.nextNode(conf.getHeuristic()).toString());
        } catch (EndOfGameException e)
        {
            fail(e);
        }
    }

    @Test
    void testCounter() {
        root.generateChildren(6, conf.getCount4(), this.counter, 0);
        assertEquals(43304, counter.get());
    }
}