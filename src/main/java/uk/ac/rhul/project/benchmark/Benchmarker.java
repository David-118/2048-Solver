package uk.ac.rhul.project.benchmark;

import uk.ac.rhul.project.expectimax.Node;
import uk.ac.rhul.project.expectimax.NodeFactory;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.userInterface.Heuristic;
import uk.ac.rhul.project.userInterface.Heuristics;
import uk.ac.rhul.project.userInterface.Solver;

import java.io.OutputStream;

public class Benchmarker
{
    private static final String[] heuristic_names = new String[]{
            "sumCells",
            "largestLower",
            "snake_4_by_4",
            "topLeftCornerProximity_4_by_4"
    };

    private static final Heuristic[] heuristics = new Heuristic[]{
            Heuristics::sumCells,
            Heuristics::largestLower,
            Heuristics::snake_4_by_4,
            Heuristics::topLeftCornerProximity_4_by_4
    };
    private final int count;
    private final BenchmarkWriter csvWriter;

    public Benchmarker(int count)
    {
        this.count = count;
        this.csvWriter = new BenchmarkWriter();
    }

    public void benchmark(OutputStream log)
    {
        Solver solver = new Solver();
        for(int i = 0; i < heuristics.length; i++)
        {
            solver.setHeurstic(heuristics[i]);
            for (int j = 0; j < count; j++)
            {
                GameState startState = new GameState(4, 4);
                startState.init();
                Node root = NodeFactory.generateTree(startState, 6);
                solver.setRoot(root);

                solver.run();
                GameState endState = solver.getCurrentState();

                this.csvWriter.entries.add(new BenchmarkEntry(heuristic_names[i], startState));

                System.out.printf("%s (%d)\n", heuristic_names[i], j);
            }
        }
    }
}
