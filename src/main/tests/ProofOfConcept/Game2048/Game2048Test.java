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

    @Test
    void test_2_2_move()
    {
        this.game2_2.loadCustomGame(new int[][]{{2, 4}, {0, 0}});
        assertTrue(this.game2_2.move(DirectionVect.DOWN));
        assertEquals("Game2048{width=2, height=2, grid=[[0, 0], [2, 4]]}",this.game2_2.toString());

        assertTrue(this.game2_2.move(DirectionVect.UP));
        assertEquals("Game2048{width=2, height=2, grid=[[2, 4], [0, 0]]}",this.game2_2.toString());

        this.game2_2.loadCustomGame(new int[][]{{2, 0}, {4, 0}});
        assertTrue(this.game2_2.move(DirectionVect.RIGHT));
        assertEquals("Game2048{width=2, height=2, grid=[[0, 2], [0, 4]]}",this.game2_2.toString());

        assertTrue(this.game2_2.move(DirectionVect.LEFT));
        assertEquals("Game2048{width=2, height=2, grid=[[2, 0], [4, 0]]}",this.game2_2.toString());
    }

    @Test
    void test_2_2_move2()
    {
        this.game2_2.loadCustomGame(new int[][]{{2, 4}, {0, 0}});
        assertFalse(this.game2_2.move(DirectionVect.UP));
        assertEquals("Game2048{width=2, height=2, grid=[[2, 4], [0, 0]]}",this.game2_2.toString());

        assertTrue(this.game2_2.move(DirectionVect.DOWN));
        assertEquals("Game2048{width=2, height=2, grid=[[0, 0], [2, 4]]}",this.game2_2.toString());

        assertFalse(this.game2_2.move(DirectionVect.DOWN));
        assertEquals("Game2048{width=2, height=2, grid=[[0, 0], [2, 4]]}",this.game2_2.toString());
    }

    @Test
    void test_4_4_move()
    {
        this.game4_4.loadCustomGame(new int[][]{{2, 0, 0, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}});
        assertTrue(this.game4_4.move(DirectionVect.DOWN));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [2, 0, 0, 2]]}", this.game4_4.toString());

        assertTrue(this.game4_4.move(DirectionVect.UP));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[2, 0, 0, 2], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]}", this.game4_4.toString());


        this.game4_4.loadCustomGame(new int[][]{{2, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {2, 0, 0, 0}});
        assertTrue(this.game4_4.move(DirectionVect.RIGHT));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[0, 0, 0, 2], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 2]]}", this.game4_4.toString());

        assertTrue(this.game4_4.move(DirectionVect.LEFT));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[2, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [2, 0, 0, 0]]}", this.game4_4.toString());
    }

    @Test
    void test_4_4_move2()
    {
        this.game4_4.loadCustomGame(new int[][]{{4, 2, 0, 0}, {4, 0, 0, 2}, {2, 0, 4, 0}, {0, 0, 4, 2}});
        assertTrue(this.game4_4.move(DirectionVect.RIGHT));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[0, 0, 4, 2], [0, 0, 4, 2], [0, 0, 2, 4], [0, 0, 4, 2]]}", this.game4_4.toString());

        this.game4_4.loadCustomGame(new int[][]{{4, 2, 0, 0}, {4, 0, 0, 2}, {2, 0, 4, 0}, {0, 0, 4, 2}});
        assertTrue(this.game4_4.move(DirectionVect.LEFT));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[4, 2, 0, 0], [4, 2, 0, 0], [2, 4, 0, 0], [4, 2, 0, 0]]}", this.game4_4.toString());

        this.game4_4.loadCustomGame(new int[][]{{4, 4, 2, 0}, {2, 0, 0, 0}, {0, 0, 4, 4}, {0, 2, 0, 2}});
        assertTrue(this.game4_4.move(DirectionVect.DOWN));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[0, 0, 0, 0], [0, 0, 0, 0], [4, 4, 2, 4], [2, 2, 4, 2]]}", this.game4_4.toString());

        this.game4_4.loadCustomGame(new int[][]{{4, 4, 2, 0}, {2, 0, 0, 0}, {0, 0, 4, 4}, {0, 2, 0, 2}});
        assertTrue(this.game4_4.move(DirectionVect.UP));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[4, 4, 2, 4], [2, 2, 4, 2], [0, 0, 0, 0], [0, 0, 0, 0]]}", this.game4_4.toString());

    }

    @Test
    void test_4_4_move3()
    {
        this.game4_4.loadCustomGame(new int[][]{{2, 2, 0, 0}, {2, 2, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}});
        assertTrue(this.game4_4.move(DirectionVect.DOWN));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [4, 4, 0, 0]]}", this.game4_4.toString());

        assertTrue(this.game4_4.move(DirectionVect.RIGHT));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 8]]}", this.game4_4.toString());

        this.game4_4.loadCustomGame(new int[][]{{2, 2, 2, 2}, {2, 2, 2, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}});
        assertTrue(this.game4_4.move(DirectionVect.LEFT));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[4, 4, 0, 0], [4, 4, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]}", this.game4_4.toString());

        assertTrue(this.game4_4.move(DirectionVect.LEFT));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[8, 0, 0, 0], [8, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]}", this.game4_4.toString());

        assertTrue(this.game4_4.move(DirectionVect.UP));
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[16, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]}", this.game4_4.toString());
    }

    @Test
    void test_full_game()
    {
        this.game4_4.init();
        assertEquals(0, this.game4_4.getScore());

        if (this.game4_4.move(DirectionVect.DOWN))
            this.game4_4.addRndCell();

        assertEquals(4, this.game4_4.getScore());


        if (this.game4_4.move(DirectionVect.DOWN))
            this.game4_4.addRndCell();
        assertEquals(4, this.game4_4.getScore());


        if (this.game4_4.move(DirectionVect.RIGHT))
            this.game4_4.addRndCell();
        assertEquals(12, this.game4_4.getScore());


        if (this.game4_4.move(DirectionVect.RIGHT))
            this.game4_4.addRndCell();
        assertEquals(12, this.game4_4.getScore());


        if (this.game4_4.move(DirectionVect.DOWN))
            this.game4_4.addRndCell();
        assertEquals(16, this.game4_4.getScore());
    }

    @Test
    void test_merge_bug()
    {
        game4_4.loadCustomGame(new int[][]{{4,2,2,0}, {0,0,0,0},{0,0,0,0}, {0,0,0,0}});
        game4_4.move(DirectionVect.RIGHT);
        assertEquals("Game2048{width=4, height=4, " +
                "grid=[[0, 0, 4, 4], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]}", this.game4_4.toString());
    }

    @Test
    void test_rectangular()
    {
        Game2048 game_9_3 = new Game2048(9, 3, new Random(21102022));
        game_9_3.init();
        game_9_3.move(DirectionVect.RIGHT);
        assertEquals("Game2048{width=9, height=3, " +
                "grid=[[0, 0, 0, 0, 0, 0, 0, 0, 4], [0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0]]}",
                game_9_3.toString());

    }
}