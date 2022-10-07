package ProofOfConcept.DecisionTree;

import java.lang.reflect.Array;

public class TreeNode
{
    private TreeNode[] children;
    private float score;


    // Create leaf node
    public TreeNode(float score)
    {
        this.children = new TreeNode[0];
        this.score = score;
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

    public float getScore()
    {
        return this.score;
    }
}
