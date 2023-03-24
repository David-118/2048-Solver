package uk.ac.rhul.project.game;

/**
 * Exception thrown to end a 2048 game, when the game is lost.
 */
public class EndOfGameException extends Exception {
    /**
     * The game state at the end of the game.
     */
    private final GameState state;

    /**
     * Create an end of game exception.
     * @param state The state the game ended in.
     */
    public EndOfGameException(GameState state) {
        this.state = state;
    }

    /**
     * Get the final state of the 2048 game.
     * @return the game state.
     */
    public GameState getFinalState() {
        return state;
    }
}
