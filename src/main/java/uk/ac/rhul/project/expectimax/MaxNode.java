package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;


public final class MaxNode extends Node
{
    private final Node[] children;

    @Override
    public float getWeight()
    {
        return weight;
    }

   public MaxNode(GameState gameState, float weight, GameState[] moves, int depth)
   {
       super(gameState, weight);

       children = new Node[moves.length];
       for (int i = 0; i < moves.length; i++)
       {
           children[i] = NodeFactory.createNode(MoveType.GRID_MUTATION, moves[i], 1f, depth - 1);
       }
   }

    @Override
    public float getScore()
    {
        float max = children[0].getScore();
        for (int i = 1; i < children.length; i++)
        {
            float score = children[i].getScore();
            if (score > max)
            {
                max = score;
            }
        }
        return max * weight;
    }

    public Node nextNode()
    {
        Node max = children[0];
        for (int i = 1; i < children.length; i++)
        {
            Node current = children[i];
            if (current.getScore() > max.getScore())
            {
                max = current;
            }
        }
        return max;
    }

    @Override
    public boolean validate()
    {
        return this.children.length > 0;
    }

    @Override
    public Node[] getChildren()
    {
        return this.children;
    }
}
