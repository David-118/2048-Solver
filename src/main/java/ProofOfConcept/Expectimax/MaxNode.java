package ProofOfConcept.Expectimax;

import ProofOfConcept.DecisionTree.TreeNode;

public class MaxNode extends TreeNode
{
    public MaxNode(float weight, TreeNode ... children)
    {
        super(1f, children);
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
            }
        }
        this.setScore(max);
    }
}
