package uk.ac.rhul.project.benchmark;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.ac.rhul.project.game.GameState;

/**
 * This class is used from jackson csv.
 * <p>
 *   Its purpose it to collect data about the final state
 *   of a game, to be later logged to csv.
 * </p>
 * <p>
 *   Theoretically this can be logged to other formats
 *   supported by the jackson library.
 * </p>
 * <p>
 *   This class was written with the aid of [9].
 * </p>
 * <p>
 *   Attributes are public so that jackson can access them.
 * </p>
 */
@JsonPropertyOrder({"heuristicName", "maxTile", "score"})
public class BenchmarkEntry implements Comparable<BenchmarkEntry>
{
    /**
     * Create an instance of BenchmarkEntry.
     *
     * @param heuristicName The name of the heuristic used to generate the state.
     * @param state The final state of a 2048 game. Max tile and score is extracted form this.
     */
    public BenchmarkEntry(String heuristicName, GameState state)
    {
        int max = 0;
        for (int i = 0; i < state.getGrid().length; i++)
        {
            for (int j = 0; j < state.getGrid()[0].length; j++)
            {
                if (state.getGrid()[i][j] > max)
                {
                    max = state.getGrid()[i][j];
                }
            }
        }

        this.maxTile = max;
        this.score = state.getScore();

        this.heuristicName = heuristicName;
    }

    /**
     * The name the heuristic used to compute the game.
     */
    public final String heuristicName;

    /**
     * The largest tile achieved in the 2048 game.
     */
    public final int maxTile;

    /**
     * The score achieved in the 2048 game.
     */
    public final int score;

    @Override
    public String toString()
    {
        return String.format("score: %d, max-tile: %d", this.score, this.maxTile);
    }

    public int compareTo(BenchmarkEntry o)
    {
        return Integer.compare(this.score, o.score);
    }
}
