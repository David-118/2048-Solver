package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.FailSetter;
import uk.ac.rhul.project.heursitics.Monotonic;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class ExpectimaxTreeTest {
    ExpectimaxTree tree;
    GameState inital;

    @BeforeEach
    void setup() {
        GameConfiguration conf = new GameConfiguration(4, 4, 4, Integer.MAX_VALUE,
                new FailSetter(new Monotonic(), -1000));
        conf.setSeed(0L);

        inital = new GameState(conf);
        inital.init();
        tree = new ExpectimaxTree(inital, conf.getRandom(), conf.getDepth(), Integer.MAX_VALUE, conf.getHeuristic());
    }
    @Test
    void makeMove() {
        try {
            assertEquals("[[0, 0, 0, 0], [2, 0, 0, 0], [2, 0, 4, 0], [0, 0, 0, 0]]",
                    Arrays.deepToString(tree.makeMove().getGrid()));

            assertEquals("[[0, 0, 0, 0], [2, 0, 0, 0], [0, 0, 0, 0], [4, 0, 4, 0]]",
                    Arrays.deepToString(tree.makeMove().getGrid()));
        } catch (EndOfGameException ex) {
            fail(ex);
        }

        try {
            while (true)
                tree.makeMove();
        } catch (EndOfGameException ex) {
            assertEquals("[[16, 2, 8, 2], [32, 4, 16, 64], [2, 64, 512, 4], [2048, 1024, 8, 2]]",
                    ex.getFinalState().toString());
        }

    }

    @Test
    void dmpTxt() {
        File dir = new File("trees-test");
        dir.mkdir();
        tree.enableTreeLog("trees-test");
        try {
            tree.makeMove();
            tree.dmpTxt(0);

            try (InputStream expected = getClass().getResourceAsStream("tree-00000000.tree");
                 InputStream real = new FileInputStream("trees-test/tree-00000000.tree")) {
                byte[] real_bytes = real.readAllBytes();
                byte[] expected_bytes = expected.readAllBytes();
                assertArrayEquals(real_bytes, expected_bytes);
            }
        } catch (EndOfGameException | IOException e) {
            fail(e);
        }

        File[] files = dir.listFiles();

        for (File file : files) {file.delete();}

        dir.delete();

    }
}

