package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@FunctionalInterface
interface NodeBehaviourGenerator
{
    NodeBehaviour generate(GameState state, Random random, int depth, int count4, int layer);
}
