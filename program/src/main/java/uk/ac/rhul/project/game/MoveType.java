package uk.ac.rhul.project.game;

import uk.ac.rhul.project.expectimax.NodeBehaviourChance;
import uk.ac.rhul.project.expectimax.NodeBehaviourGenerator;
import uk.ac.rhul.project.expectimax.NodeBehaviourMaximize;

public enum MoveType
{
    PLAYER_MOVE(NodeBehaviourChance::generate),
    MUTATION(NodeBehaviourMaximize::generate);

    private final NodeBehaviourGenerator generator;
    MoveType(NodeBehaviourGenerator generator)
    {
        this.generator = generator;
    }

    public NodeBehaviourGenerator getBehaviorGenerator()
    {
        return generator;
    }
}
