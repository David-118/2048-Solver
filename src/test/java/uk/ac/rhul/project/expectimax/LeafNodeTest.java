package uk.ac.rhul.project.expectimax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeafNodeTest
{
    LeafNode node1;
    LeafNode node2;

    @BeforeEach
    void setup()
    {
        node1 = new LeafNode(1f, 1f);
        node2 = new LeafNode(0.25f, 8f);
    }

    @Test
    void test_getScore()
    {
        assertEquals(1, node1.getScore());
        assertEquals(2, node2.getScore());
    }

    @Test
    void test_validate()
    {
        assertTrue(node1.validate());
        assertTrue(node2.validate());
    }
}