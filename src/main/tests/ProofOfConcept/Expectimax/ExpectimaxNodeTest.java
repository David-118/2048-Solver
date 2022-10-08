package ProofOfConcept.Expectimax;

import ProofOfConcept.DecisionTree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpectimaxNodeTest
{
    @BeforeEach
    void setup()
    {

    }

    @Test
    void testMaxNode()
    {
        MaxNode maxNode = new MaxNode(1f,
                new TreeNode(1f, 0.5f),
                new TreeNode(1f, 17f));

        MaxNode maxNode2 = new MaxNode(1f,
                new TreeNode(1f, 0.9f),
                new TreeNode(1f, 0.1f));

        assertEquals(17f, maxNode.getScore());
        assertEquals(0.9f, maxNode2.getScore());
    }
}
