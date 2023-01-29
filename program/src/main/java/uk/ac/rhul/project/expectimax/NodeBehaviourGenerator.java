package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;

import java.util.Random;

@FunctionalInterface
interface NodeBehaviourGenerator
{
    NodeBehaviour generate(GameState state, Random random, int depth,
                           int abandonCount, double abandonThreshold, int max4count);
}
