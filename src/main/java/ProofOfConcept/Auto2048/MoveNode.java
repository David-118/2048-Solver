package ProofOfConcept.Auto2048;

import ProofOfConcept.Game2048.DirectionVect;

import java.util.ArrayList;
import java.util.List;

public class MoveNode extends Node2048
{
    Node2048 nextNodeI = null;
    @Override
    public Node2048[] expectimax()
    {
        List<Node2048> moves = new ArrayList<>(4);
        for (DirectionVect dir: DirectionVect.values())
        {
            Node2048 child = this.cloneAs(new ChanceNode());

            if (child.move(dir)) moves.add(child);

        }

        Node2048[] nodes = moves.toArray(new Node2048[0]);
        this.setChildren(nodes);

        this.nodeScore = Float.NEGATIVE_INFINITY;

        for (Node2048 node: nodes)
        {
            node.expectimax();
            if (node.nodeScore > this.nodeScore)
            {
                this.nextNodeI = node;
                this.nodeScore = node.nodeScore;
            }
        }

        if (nodes.length == 0)
        {
            this.nodeScore = getScore();
        }

        return nodes;
    }

    @Override
    public Node2048 nextNode()
    {
        return this.nextNodeI;
    }
}
