package ProofOfConcept.DecisionTree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeNodeTest
{
    @Test
    void testCreateTree()
    {
        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode(1), new TreeNode(2)),
                new TreeNode(new TreeNode(3), new TreeNode(4), new TreeNode(5)),
                new TreeNode(new TreeNode(6)));

        assertEquals(3, root.getChildCount());
        assertEquals(2, root.getChild(0).getChildCount());
        assertEquals(3, root.getChild(1).getChildCount());
        assertEquals(1, root.getChild(2).getChildCount());
    }
}
