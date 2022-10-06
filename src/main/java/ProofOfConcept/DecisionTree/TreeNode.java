package ProofOfConcept.DecisionTree;

import java.lang.reflect.Array;

public class TreeNode
{
    private TreeNode[] children;

    // Create leaf node
    public TreeNode(int score)
    {
        this.children = new TreeNode[0];
    }

    // Create branch node
    public TreeNode(TreeNode ... children)
    {
        this.children = children;
    }

    public int getChildCount()
    {
        return this.children.length;
    }

    public TreeNode getChild(int i)
    {
        return this.children[i];
    }
}
