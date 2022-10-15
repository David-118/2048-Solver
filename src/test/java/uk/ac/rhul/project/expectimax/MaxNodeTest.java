package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxNodeTest
{
    @Test
    void test_getScore()
    {
        MaxNode a = new MaxNode(1f, new LeafNode(1, 10), new LeafNode(1, 8));
        MaxNode b = new MaxNode(0.5f, new LeafNode(1, 8), new LeafNode(1, 50));

        assertEquals(10, a.getScore());
        assertEquals(25, b.getScore());
    }
}