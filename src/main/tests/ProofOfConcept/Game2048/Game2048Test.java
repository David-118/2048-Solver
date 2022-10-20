package ProofOfConcept.Game2048;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Game2048Test
{
    private Game2048 game2_2;
    private Game2048 game4_4;

    @BeforeEach
    public void setup()
    {
        this.game2_2 = new Game2048(2, 2, new Random(5));
        this.game4_4 = new Game2048(4, 4, new Random(5));
    }

    @Test
    public void testInit()
    {
        this.game2_2.init();
        assertEquals("Game2048{width=2, height=2, grid=[[0, 0], [2, 2]]}", this.game2_2.toString());

        this.game2_2.init();
        assertEquals("Game2048{width=2, height=2, grid=[[0, 4], [2, 0]]}", this.game2_2.toString());

        this.game2_2.init();
        assertEquals("Game2048{width=2, height=2, grid=[[0, 2], [0, 2]]}", this.game2_2.toString());

        this.game4_4.init();
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 2], [0, 0, 0, 2]]}", this.game4_4.toString());

        this.game4_4.init();
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[0, 0, 0, 0], [2, 0, 0, 4], [0, 0, 0, 0], [0, 0, 0, 0]]}", this.game4_4.toString());

        this.game4_4.init();
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[0, 0, 0, 0], [0, 0, 0, 2], [0, 0, 0, 0], [2, 0, 0, 0]]}", this.game4_4.toString());
    }
}