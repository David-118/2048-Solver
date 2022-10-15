package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeafNodeTest
{
    @Test
    void test_getScore()
    {
        LeafNode node1 = new LeafNode(1f, 1f);
        LeafNode node2 = new LeafNode(0.25f, 8f);

        assertEquals(1, node1.getScore());
        assertEquals(2, node2.getScore());
    }
}