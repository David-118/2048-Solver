package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ChanceNodeTest
{
    private Node root;
    private Node chanceA;
    private Node chanceB;

    @BeforeEach
    void setup()
    {
        Random rnd = new Random(0);

        ChanceNode chanceA = new ChanceNode(1,
                new LeafNode(0.2f, 100f),
                new LeafNode(0.6f, 10f),
                new LeafNode(0.2f, 25f));
        chanceA.setRandom(rnd);

        ChanceNode chanceB = new ChanceNode(1,
                new LeafNode(0.8f, 100f),
                new LeafNode(0.2f, -800f));
        chanceB.setRandom(rnd);

        this.chanceA = chanceA;
        this.chanceB = chanceB;

        this.root = new MaxNode(1, chanceA, chanceB);
    }

    @Test
    void test_getScore()
    {
        assertEquals(10f + 1f/3f, this.chanceA.getScore());
        assertEquals(-40, this.chanceB.getScore());
    }

    @Test
    void test_fullTreeScore()
    {
        assertEquals(10f + 1f/3f, this.root.getScore());
    }

    @Test
    void test_nextNode()
    {
        assertEquals(6f, chanceA.nextNode().getScore());
        assertEquals(5f, chanceA.nextNode().getScore());
        assertEquals(6f, chanceA.nextNode().getScore());
        assertEquals(6f, chanceA.nextNode().getScore());
        assertEquals(6f, chanceA.nextNode().getScore());
        assertEquals(6f, chanceA.nextNode().getScore());
        assertEquals(6f, chanceA.nextNode().getScore());
        assertEquals(20f, chanceA.nextNode().getScore());
        assertEquals(80f, chanceB.nextNode().getScore());
        assertEquals(80f, chanceB.nextNode().getScore());
        assertEquals(80f, chanceB.nextNode().getScore());
        assertEquals(80f, chanceB.nextNode().getScore());
        assertEquals(80f, chanceB.nextNode().getScore());
        assertEquals(80f, chanceB.nextNode().getScore());
        assertEquals(-160f, chanceB.nextNode().getScore());
    }
}