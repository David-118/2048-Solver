package uk.ac.rhul.project.benchmark;

import uk.ac.rhul.project.expectimax.NodeFactory;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.userInterface.Heuristic;
import uk.ac.rhul.project.userInterface.Heuristics;
import uk.ac.rhul.project.userInterface.Solver;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

/**
 * This class runs a benchmark on all the heuristic functions
 * it is aware of. Currently, it runs off of a hard coded array
 * of heuristics, however I intend to change this in the future.
 *
 */
public class Benchmarker
{
    /**
     * An array of string representing what each heuristic will be
     * called in the csv file.
     */
    private static final String[] HEURISTIC_NAMES = new String[]{
            "sumCells",
            "largestLower",
            "snake_4_by_4",
            "topLeftCornerProximity_4_by_4"
    };

    /**
     * An array of Heuristic methods that correspond to the names
     * in HEURISTIC_NAMES.
     */
    private static final Heuristic[] HEURISTICS = new Heuristic[]{
            Heuristics::sumCells,
            Heuristics::largestLower,
            Heuristics::snake_4_by_4,
            Heuristics::topLeftCornerProximity_4_by_4
    };

    /**
     * The number of times each heuristic is iterated over
     */
    private final int count;
    private final BenchmarkWriter csvWriter;

    /**
     * Create a benchmarker to evaluate the performance of each heuristic function.
     * <p>Caution, this process can take multiple hours even with relatively small counts</p>
     * @param count how many times to preform each heuristic.
     */
    public Benchmarker(int count)
    {
        this.count = count;
        this.csvWriter = new BenchmarkWriter();
        NodeFactory.setRandom(new Random());
    }

    /**
     * Start the benchmarking process
     * <p>Warning: This does not log data until the process is complete at this point</p>
     * @param log The output stream that the csv data get written to.
     */
    public void benchmark(OutputStream log)
    {
        Solver solver = new Solver();
        solver.addUpdateObserver((grid, score) ->
        {
            System.out.println("Score: " + score);
            System.out.println(Arrays.deepToString(grid));
        });

        for(int i = 0; i < HEURISTICS.length; i++)
        {
            solver.setHeurstic(HEURISTICS[i]);
            for (int j = 0; j < count; j++)
            {
                System.out.printf("%s (%d)\n", HEURISTIC_NAMES[i], j);

                GameState startState = new GameState(4, 4);
                startState.init();

                solver.setRoot(NodeFactory.generateTree(startState, 6));

                solver.run();

                this.csvWriter.add(new BenchmarkEntry(HEURISTIC_NAMES[i], solver.getState()));
            }
        }
        try
        {
            this.csvWriter.write(log);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
