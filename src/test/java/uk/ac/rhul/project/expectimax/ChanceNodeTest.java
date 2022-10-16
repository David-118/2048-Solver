package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChanceNodeTest
{
    private Node root;
    private Node chanceA;
    private Node chanceB;

    @BeforeEach
    void setup()
    {
        this.chanceA = new ChanceNode(1,
                new LeafNode(0.2f, 100f),
                new LeafNode(0.6f, 10f),
                new LeafNode(0.2f, 25f));

        this.chanceB = new ChanceNode(1,
                new LeafNode(0.8f, 100f),
                new LeafNode(0.2f, -800f));

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
}