package ProofOfConcept.Expectimax;

import ProofOfConcept.DecisionTree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpectimaxNodeTest
{
    private MaxNode maxNode;
    private MaxNode maxNode2;
    private ChanceNode chanceNode;
    private ChanceNode chanceNode2;

    private TreeNode testTree;

    @BeforeEach
    void setup()
    {
        maxNode = new MaxNode(1f,
                new TreeNode(1f, 0.5f),
                new TreeNode(1f, 17f));

        maxNode2 = new MaxNode(1f,
                new TreeNode(1f, 0.9f),
                new TreeNode(1f, 0.1f));

        chanceNode = new ChanceNode(1f,
                new TreeNode(0.8f, 10),
                new TreeNode(0.2f, 100));

        chanceNode2 = new ChanceNode(1f,
                new TreeNode(0.6f, 50),
                new TreeNode(0.4f, 100));

        this.testTree = new MaxNode(1f,
                new ChanceNode(1f,
                        new MaxNode(0.3f,
                                new ChanceNode(1f,
                                        new TreeNode(0.2f, 50f),
                                        new TreeNode(0.4f, 80f),
                                        new TreeNode(0.4f, 90f)),

                                new ChanceNode(1f,
                                        new TreeNode(0.9f, 10f),
                                        new TreeNode(0.1f, 100f)),

                                new ChanceNode(1f,
                                        new TreeNode(0.8f, 90f),
                                        new TreeNode(0.1f, 10f),
                                        new TreeNode(0.1f, 100f))),

                        new MaxNode(0.6f,
                                new ChanceNode(1f,
                                        new TreeNode(0.1f, 100f),
                                        new TreeNode(0.1f, 90f),
                                        new TreeNode(0.8f, 80f)),

                                new ChanceNode(1f,
                                        new TreeNode(0.9f, 10f),
                                        new TreeNode(0.1f, 100f))),

                        new MaxNode(0.1f,
                                new ChanceNode(1f,
                                        new TreeNode(0.1f, 10f),
                                        new TreeNode(0.2f, 100f),
                                        new TreeNode(0.7f, 90f)),

                                new ChanceNode(1f,
                                        new TreeNode(0.9f, 10f),
                                        new TreeNode(0.1f, 100f)))),
                new ChanceNode(1f,
                        new MaxNode(0.5f,
                                new ChanceNode(1f,
                                        new TreeNode(0.5f, 50f),
                                        new TreeNode(0.5f, 100f))),

                        new MaxNode(0.5f,
                                new ChanceNode(1f,
                                        new TreeNode(0.5f, 100f),
                                        new TreeNode(0.5f, 100f)))));




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

    @Test
    void testChanceNode()
    {
        assertEquals(14, chanceNode.getScore());
        assertEquals(35, chanceNode2.getScore());
    }

    @Test
    void testExpectimaxTree()
    {
        TreeNode node1 = this.testTree.getChild(0).getChild(0).getChild(0);
        assertEquals(26f, node1.getScore());

        TreeNode node2 = this.testTree.getChild(0).getChild(0).getChild(1);
        assertEquals(9.5f, node2.getScore());

        TreeNode node3 = this.testTree.getChild(0).getChild(0).getChild(2);
        assertEquals(27f + 2f / 3f, node3.getScore());

        TreeNode node4 = this.testTree.getChild(0).getChild(0);
        assertEquals(27f + 2f / 3f, node4.getScore());

        assertEquals(21.875f, this.testTree.getScore());
    }
}
