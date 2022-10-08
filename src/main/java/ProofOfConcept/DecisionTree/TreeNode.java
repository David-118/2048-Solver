package ProofOfConcept.DecisionTree;

import com.sun.source.tree.Tree;
import jdk.jshell.spi.ExecutionControl;

import java.lang.reflect.Array;

public class TreeNode
{
    private TreeNode[] children;
    private float score;

    // While nicer interface will be used a weight of 1 is the same as unweighted.
    private float weight;


    // Create leaf node
    public TreeNode(float weight, float score)
    {
        this.children = new TreeNode[0];
        this.score = score;
        this.weight = weight;
    }

    // Create branch node
    public TreeNode(float weight, TreeNode ... children)
    {
        this.children = children;
        this.weight = weight;
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

    protected  void setScore(float score)
    {
        this.score = score;
    }

    public TreeNode getNextNode()
    {
        return this;
    }

    public float getWeightedScore()
    {
        return this.score * this.weight;
    }
}
