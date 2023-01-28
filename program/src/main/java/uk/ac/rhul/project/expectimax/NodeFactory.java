package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;

import java.util.Random;

public class NodeFactory
{
    private final Random random;
    private final StateScoreTracker stateScoreTracker;

    public NodeFactory(Random random, StateScoreTracker stateScoreTracker)
    {

        this.random = random;
        this.stateScoreTracker = stateScoreTracker;
    }
    public Node createNode(GameState state)
    {
        return  new Node(state, state.getMoveType().getBehaviorGenerator(), random, stateScoreTracker);
    }
}
