package ProofOfConcept.Heuristic2048;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChanceNode extends Node2048
{

    @Override
    public void expectimax(int maxDepth)
    {
        if (maxDepth > 0)
        {

            if (this.children == null || this.children.length == 0)
            {
                List<Point> cells = this.getFreeCells();
                List<Node2048> children = new ArrayList<>(cells.size() * 2);
                for (Point cell : cells)
                {
                    Node2048 child1 = this.cloneAs(new MoveNode());
                    Node2048 child2 = this.cloneAs(new MoveNode());

                    child1.setCellValue(cell.x, cell.y, 2);
                    child2.setCellValue(cell.x, cell.y, 4);

                    children.add(child1);
                    children.add(child2);
                }

                Node2048[] nodes = children.toArray(new Node2048[0]);

                this.setChildren(nodes);
            }

            this.nodeScore = 0;

            for (Node2048 node : this.children)
            {
                node.expectimax(maxDepth - 1);
                this.nodeScore += node.nodeScore;
            }

            if (this.children.length > 0)
            {
                this.nodeScore /= this.children.length;
            } else
            {
                this.nodeScore = this.heuristic();
            }
        }

        this.nodeScore = heuristic();
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
