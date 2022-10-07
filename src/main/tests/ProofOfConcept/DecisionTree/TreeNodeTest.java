package ProofOfConcept.DecisionTree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeNodeTest
{
    private TreeNode root;
    @BeforeEach
    void setup()
    {
        this.root = new TreeNode(1f,
                new TreeNode(1f, new TreeNode(0.5f, 1f), new TreeNode(0.5f, 2f)),
                new TreeNode(1f, new TreeNode(0.1f, 3f), new TreeNode(0.8f, 4f), new TreeNode(0.1f, 5)),
                new TreeNode(1f, new TreeNode(10f, 6f)));
    }

    @Test
    void testCreateTree()
    {
        assertEquals(3, root.getChildCount());
        assertEquals(2, root.getChild(0).getChildCount());
        assertEquals(3, root.getChild(1).getChildCount());
        assertEquals(1, root.getChild(2).getChildCount());
        assertEquals(0, root.getChild(0).getChild(0).getChildCount());
    }

    @Test
    void testGetScore()
    {
        assertEquals(1f, root.getChild(0).getChild(0).getScore());
        assertEquals(4f, root.getChild(1).getChild(1).getScore());
        assertEquals(6f, root.getChild(2).getChild(0).getScore());
    }

    @Test
    void testWeight()
    {
        assertEquals(0.5f, root.getChild(0).getChild(0).getWeightedScore());
        assertEquals(3.2f, root.getChild(1).getChild(1).getWeightedScore());
        assertEquals(60f, root.getChild(2).getChild(0).getWeightedScore());
    }
}
