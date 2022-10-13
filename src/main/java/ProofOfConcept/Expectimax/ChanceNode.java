package ProofOfConcept.Expectimax;

import ProofOfConcept.DecisionTree.TreeNode;

public class ChanceNode extends TreeNode
{
    public ChanceNode(float weight, TreeNode ... children)
    {
        super(weight, children);
        float sum = 0f;

        for (int i = 0; i < children.length; i++)
        {
            sum += children[i].getWeightedScore();
        }

        this.setScore(sum / children.length);
    }
}
