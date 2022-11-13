package uk.ac.rhul.project.benchmark;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.ac.rhul.project.game.GameState;

@JsonPropertyOrder({"heuristicName", "maxTile", "score"})
public class BenchmarkEntry
{
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

    public String heuristicName;

    public int maxTile;

    public int score;

}
