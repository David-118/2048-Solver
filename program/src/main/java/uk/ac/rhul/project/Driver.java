package uk.ac.rhul.project;

import uk.ac.rhul.project.benchmark.BenchmarkerView;
import uk.ac.rhul.project.benchmark.OptimiserView;
import uk.ac.rhul.project.benchmark.SeededGameView;
import uk.ac.rhul.project.expectimax.ExpectimaxTree;
import uk.ac.rhul.project.game.EndOfGameException;
import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.FailSetter;
import uk.ac.rhul.project.heursitics.Monotonic;
import uk.ac.rhul.project.userInterface.*;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class that starts the whole program.
 */
public class Driver {
    /**
     * Main function
     *
     * @param args <p>The arguments supported are<ul>
     *             <li>-b [count]: Benchmark each known heuristic function with count number of games.</li>
     *             <li>--benchmark [count]: same as -b.</li>
     *             <li>-o [output]: output performance data to the csv file output</li>
     *             <li>--output [output]: same as -o</li>
     *             </ul></p>
     */
    public static void main(String[] args) {
        String output = "";
        String logDir = "";
        int size = 0;
        int i = 0;
        int iterations = 0;
        boolean benchmark = false;
        boolean optimise = false;
        boolean seeded = false;
        long seed = 0;
        boolean depthTest = false;
        int rows = 0;
        int cols = 0;
        long timeout = 0;
        int[][] grid = null;

        while (i < args.length) {
            switch (args[i]) {
                case "-b", "--benchmark" -> {
                    benchmark = true;
                    if (optimise) {
                        System.err.println("Benchmark and Optimise flags are incompatible");
                        System.exit(1);
                    }
                    if (i <= args.length - 2) {
                        try {
                            size = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.err.println("Specify how many times to run each heuristic, and the number of iterations");
                            System.exit(1);
                        }
                    }
                }
                case "-s", "--optimise" -> {
                    optimise = true;
                    if (benchmark) {
                        System.err.println("Benchmark and Optimise flags are incompatible");
                        System.exit(1);
                    }
                    if (i <= args.length - 3) {
                        try {
                            size = Integer.parseInt(args[++i]);
                            iterations = Integer.parseInt(args[++i]);
                            if (iterations < 2) {
                                System.err.println("Iterations must be at least 2");
                                System.exit(1);
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Specify how many times to run each heuristic, and the number of iterations");
                            System.exit(1);
                        }
                    }
                }
                case "-o", "--output" -> {
                    if (i <= args.length - 2) {
                        output = args[++i];
                    } else {
                        System.err.println("No output file specified");
                        System.exit(0);
                    }
                }
                case "-e", "--seed" -> {
                    if (i <= args.length - 2) {
                        seed = Long.parseLong(args[++i]);
                        seeded = true;
                    } else {
                        System.err.println("No seed specified");
                        System.exit(0);
                    }
                }
                case "-t", "--tree-log" -> {
                    if (i <= args.length - 2) {
                        logDir = args[++i];
                    } else {
                        System.err.println("Must specify location to save trees to.");
                        System.exit(0);
                    }
                }
                case "-d", "--depth-test" -> {
                    if (i <= args.length - 2) {
                        rows = Integer.parseInt(args[++i]);
                        cols = Integer.parseInt(args[++i]);
                        grid = new int[rows][cols];
                        if (i <= args.length - (rows*cols + 1)) {
                            for (int j = 0; j < rows; j++) {
                                for (int k = 0; k < rows; k++) {
                                    grid[j][k] = Integer.parseInt(args[++i]);
                                }
                            }
                            timeout = Long.parseLong(args[++i]);
                            depthTest = true;
                        } else {
                            System.err.println("depth-test requires args <width> <height> <val1> <val2> ... <val height*width> <timeout>");
                            System.exit(1);
                        }
                    } else {
                        System.err.println("depth-test requires args <width> <height> <val1> <val2> ... <val height*width> <timeout>");
                        System.exit(1);
                    }
                }
            }
            i++;
        }

        View view = null;

        if (depthTest) {
            testDepth(grid, timeout);
            System.exit(0);
        } else if (benchmark) {
            view = new BenchmarkerView(size);
        } else if (optimise) {
            view = new OptimiserView(size, iterations);
        } else if (seeded) {
            view = new SeededGameView(seed);
        } else {
            view = MainView.getInstance();
        }

        Model model;
        model = new MainModel();

        if (!logDir.equals("")) {
            model.enableTreeLog(logDir);
        }

        new MainController(model, view);

        if (!output.equals("") || seeded) {
            try {
                File file = new File(output);
                view.startIfTerminal(file);
            } catch (IOException e) {
                System.err.println("Failed to open file " + output);
                System.exit(0);
            }
        }
    }

    public static void testDepth(int[][] grid, long timeout) {
        int i = 2;
        while (true) {
            GameConfiguration conf = new GameConfiguration(grid.length, grid[0].length, i, Integer.MAX_VALUE, new FailSetter(new Monotonic(), -1000));
            GameState initial = new GameState(conf);
            initial.setGrid(grid);
            ExpectimaxTree expectimaxTree = new ExpectimaxTree(initial, conf.getRandom(), conf.getDepth(), conf.getCount4(), conf.getHeuristic());
            Thread tryMove = new Thread(() -> {
                try {
                    expectimaxTree.makeMove();
                } catch (EndOfGameException e) {
                    System.out.println("End of game state provided");
                    System.exit(1);
                }
            });
            int finalI = i;
            TimerTask exit = new TimerTask() {
                @Override
                public void run() {
                    System.out.println(initial.countFreeCells() + "," + (finalI - 1));
                    System.exit(0);
                }
            };

            Timer timeoutTimer = new Timer();

            tryMove.start();

            timeoutTimer.schedule(exit, timeout);

            try {
                tryMove.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            timeoutTimer.cancel();
            i++;
        }
    }
}
