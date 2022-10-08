package ProofOfConcept.Expectimax;

import ProofOfConcept.DecisionTree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpectimaxNodeTest
{
    private MaxNode maxNode;
    private MaxNode maxNode2;

    @BeforeEach
    void setup()
    {
        maxNode = new MaxNode(1f,
                new TreeNode(1f, 0.5f),
                new TreeNode(1f, 17f));

        maxNode2 = new MaxNode(1f,
                new TreeNode(1f, 0.9f),
                new TreeNode(1f, 0.1f));
    }

    @Test
    void testMaxNode()
    {
        assertEquals(17f, maxNode.getScore());
        assertEquals(0.9f, maxNode2.getScore());
    }

    @Test
    void testNextNode()
    {
        assertEquals(maxNode.getChild(1), maxNode.getNextNode());
        assertEquals(maxNode2.getChild(0), maxNode2.getNextNode());
    }
}
