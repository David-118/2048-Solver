package ProofOfConcept.Auto2048;

import ProofOfConcept.Game2048.DirectionVect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChanceNode extends Node2048
{

    @Override
    public Node2048[] getChildren()
    {

        List<Point> cells = this.getFreeCells();
        List<Node2048> children = new ArrayList<>(cells.size() * 2);
        for (Point cell:  cells)
        {
            Node2048 child1 = this.cloneAs(new MoveNode());

            child1.setCellValue(cell.x, cell.y, 2);

            children.add(child1);
        }
        Node2048[] nodes = children.toArray(new Node2048[0]);


        this.setChildren(nodes);

        this.nodeScore = 0;

        for (Node2048 node: nodes)
        {
            node.getChildren();
            this.nodeScore += node.nodeScore;
        }

        if (nodes.length > 0)
        {
            this.nodeScore /= nodes.length;
        } else
        {
            this.nodeScore = getScore();
        }


        return nodes;
    }

    @Override
    public Node2048 nextNode()
    {
        if (children.length > 0)
        {
            int next = rnd.nextInt(this.children.length);
            return this.children[next];
        } else
        {
            return null;
        }
    }
}
