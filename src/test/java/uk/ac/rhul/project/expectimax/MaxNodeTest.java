package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxNodeTest
{
    private Node maxA;
    private Node maxB;
    private Node maxLeafA;
    private Node maxLeafB;

    @BeforeEach
    void setup() throws InvalidTreeException
    {
        maxLeafA = new LeafNode(1, 10);
        maxLeafB = new LeafNode(1, 50);
        maxA = new MaxNode(1f, maxLeafA, new LeafNode(1, 8));
        maxB = new MaxNode(0.5f, new LeafNode(1, 8), maxLeafB);
    }
    @Test
    void test_getScore()
    {
        assertEquals(10, maxA.getScore());
        assertEquals(25, maxB.getScore());
    }

    @Test
    void test_nextNode()
    {
        assertEquals(maxLeafA, maxA.nextNode());
        assertEquals(maxLeafB, maxB.nextNode());
    }

    @Test
    void test_valid()
    {
        assertTrue(maxA.validate());
        assertTrue(maxB.validate());
    }

    @Test
    void test_invalid()
    {
        assertThrows(InvalidTreeException.class,
                () -> new MaxNode(1f));
    }
}