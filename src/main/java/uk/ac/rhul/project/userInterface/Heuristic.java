package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.game.GameState;

@FunctionalInterface
public interface Heuristic
{
    float heuristic(GameState state);
}
