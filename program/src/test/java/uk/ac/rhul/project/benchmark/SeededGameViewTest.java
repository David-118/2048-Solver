package uk.ac.rhul.project.benchmark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.FailSetter;
import uk.ac.rhul.project.heursitics.Monotonic;
import uk.ac.rhul.project.userInterface.MainController;
import uk.ac.rhul.project.userInterface.MainModel;
import uk.ac.rhul.project.userInterface.Model;
import uk.ac.rhul.project.userInterface.View;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class SeededGameViewTest {
    View seededGameView;
    Model model;
    GameConfiguration conf = new GameConfiguration(4, 4, 4, Integer.MAX_VALUE, new FailSetter(new Monotonic(), -1000));

    ByteArrayOutputStream outStream;
    PrintStream out;
    @BeforeEach
    void setUp() {
        outStream = new ByteArrayOutputStream();
        out = new PrintStream(outStream);
        conf.setSeed(0);

        System.setOut(out);

        this.seededGameView = new SeededGameView(conf, 0L);
    }

    @Test
    void setValues() {
        GameState state = new GameState(conf);
        state.init();
        seededGameView.setValues(state);
        assertEquals("Score: 0\n\n    0     0     0     0 \n\n    2     0     0     0 \n\n" +
                "    0     0     0     2 \n\n    0     0     0     0 \n\n", outStream.toString());
    }

    @Test
    void startIfTerminal() {
        Model mainModel = new MainModel();
        new MainController(mainModel, seededGameView);
        try {
            seededGameView.startIfTerminal(null);
        } catch (IOException e) {
            fail(e);
        }
        try (InputStream expected = getClass().getResourceAsStream("expected_out")) {
            byte[] expected_bytes = expected.readAllBytes();
            byte[] real_bytes = outStream.toByteArray();
            assertArrayEquals(expected_bytes, real_bytes);
        } catch (IOException e) {
            fail(e);
        }
    }
}
