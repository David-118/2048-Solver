package uk.ac.rhul.project.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameModelTest
{
    private GameModel model_2x2;
    private GameModel model_classic;
    private GameModel model_rect1;
    private GameModel model_rect2;

    @BeforeEach
    void setup()
    {
        Random r = new Random(2022);
        model_2x2 = new GameModel(2, 2, r);
        model_classic = new GameModel(4, 4, r);
        model_rect1 = new GameModel(9, 3, r);
        model_rect2 = new GameModel(4, 5, r);
    }
    @Test
    void test_init()
    {
        this.model_2x2.init();
        assertEquals("[[0, 0], [2, 2]]", Arrays.deepToString(this.model_2x2.getGrid()));

        this.model_2x2.init();
        assertEquals("[[2, 0], [2, 0]]", Arrays.deepToString(this.model_2x2.getGrid()));

        this.model_classic.init();
        assertEquals("[[0, 0, 0, 0], [0, 2, 0, 0], [0, 0, 0, 2], [0, 0, 0, 0]]",
                Arrays.deepToString(this.model_classic.getGrid()));

        this.model_rect1.init();
        assertEquals("[[0, 0, 0], [0, 0, 0], [0, 0, 0], [0, 0, 0], [2, 0, 0]," +
                        " [0, 0, 0], [0, 0, 0], [0, 4, 0], [0, 0, 0]]",
                Arrays.deepToString(this.model_rect1.getGrid()));

        this.model_rect2.init();
        assertEquals("[[0, 4, 0, 0, 0], [2, 0, 0, 0, 0], [0, 0, 0, 0, 0], [0, 0, 0, 0, 0]]",
                Arrays.deepToString(this.model_rect2.getGrid()));
    }
}