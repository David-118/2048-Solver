package uk.ac.rhul.project.benchmark;

import uk.ac.rhul.project.expectimax.Node;
import uk.ac.rhul.project.expectimax.NodeFactory;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.userInterface.Heuristic;
import uk.ac.rhul.project.userInterface.Heuristics;
import uk.ac.rhul.project.userInterface.Solver;
import uk.ac.rhul.project.userInterface.UpdateObserver;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

public class Benchmarker
{
    private static final String[] heuristic_names = new String[]{
            "snake_4_by_4_proximity10",
            "snake_4_by_4_proximity100",
            "snake_4_by_4_proximity1000",
    };

    private static final Heuristic[] heuristics = new Heuristic[]{
            (GameState state) -> Heuristics.snake_4_by_4_withProximity(state, 10),
            (GameState state) -> Heuristics.snake_4_by_4_withProximity(state, 100),
            (GameState state) -> Heuristics.snake_4_by_4_withProximity(state, 1000),
    };
    private final int count;
    private final BenchmarkWriter csvWriter;

    public Benchmarker(int count)
    {
        this.count = count;
        this.csvWriter = new BenchmarkWriter();
        NodeFactory.setRandom(new Random());
    }

    public void benchmark(OutputStream log)
    {
        Solver solver = new Solver();
        solver.addUpdateObserver((grid, score) ->
        {
            System.out.println("Score: " + score);
            System.out.println(Arrays.deepToString(grid));
        });

        for(int i = 0; i < heuristics.length; i++)
        {
            solver.setHeurstic(heuristics[i]);
            for (int j = 0; j < count; j++)
            {
                System.out.printf("%s (%d)\n", heuristic_names[i], j);

                GameState startState = new GameState(4, 4);
                startState.init();

                solver.setRoot(NodeFactory.generateTree(startState, 6));

                solver.run();

                this.csvWriter.entries.add(new BenchmarkEntry(heuristic_names[i], solver.getState()));
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
