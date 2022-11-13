package uk.ac.rhul.project.benchmark;

import com.opencsv.bean.CsvBindByName;
import uk.ac.rhul.project.game.GameState;

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

        this.maxTitle = max;
        this.score = state.getScore();

        this.heuristicName = heuristicName;
    }

    @CsvBindByName(column = "Heuristic", required = true)
    private String heuristicName;

    @CsvBindByName(column = "Max Tile", required = true)
    private int maxTitle;

    @CsvBindByName(column = "Score", required = true)
    private int score;

}
