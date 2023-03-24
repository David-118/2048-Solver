package uk.ac.rhul.project.expectimax;

import uk.ac.rhul.project.game.GameState;

import java.util.Random;

/**
 * Method used to generate a NodeBehvaiour
 */
@FunctionalInterface
interface NodeBehaviourGenerator {
    /**
     * Method used to generate a NodeBehvaiour
     * @param state   State of the current node.
     * @param random  Random number generator used to pick next node for chance nodes.
     * @param depth   Depth of the subtree.
     * @param count4  Number of '4's seen before pruning.
     * @param layer   Layer in the tree.
     * @return the new node behaviour.
     */
    NodeBehaviour generate(GameState state, Random random, int depth, int count4, int layer);
}
