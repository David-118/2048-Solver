package ProofOfConcept.Expectimax;

import ProofOfConcept.DecisionTree.TreeNode;

public class MaxNode extends TreeNode
{
    private TreeNode optimalChild;

    public MaxNode(float weight, TreeNode ... children)
    {
        super(weight, children);
        this.calcScore();
    }

    private void calcScore()
    {
        int count = this.getChildCount();
        float max = Float.NEGATIVE_INFINITY;

        for (int i = 0; i < count; i++)
        {
            float childWeight = this.getChild(i).getWeightedScore();
            if (childWeight > max)
            {
                max = childWeight;
                this.optimalChild = this.getChild(i);
            }
        }
        this.setScore(max);
    }

    @Override
    public TreeNode getNextNode()
    {
        return this.optimalChild;
    }
}