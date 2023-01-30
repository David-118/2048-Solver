
package uk.ac.rhul.project;

import uk.ac.rhul.project.benchmark.BenchmarkerView;
import uk.ac.rhul.project.benchmark.OptimiserView;
import uk.ac.rhul.project.userInterface.MainController;
import uk.ac.rhul.project.userInterface.MainModel;
import uk.ac.rhul.project.userInterface.MainView;
import uk.ac.rhul.project.userInterface.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Class that starts the whole program.
 */
public class Driver
{


    /**
     * Main function
     * @param args <p>The arguments supported are<ul>
     *             <li>-b [count]: Benchmark each known heuristic function with count number of games.</li>
     *             <li>--benchmark [count]: same as -b.</li>
     *             <li>-o [output]: output performance data to the csv file output</li>
     *             <li>--output [output]: same as -o</li>
     * </ul></p>
     */
    public static void main(String[] args)
    {
        String output = "";
        int size = 0;
        int i = 0;
        int iterations = 0;
        boolean benchmark = false;
        boolean optimise = false;
        while (i < args.length)
        {
            switch (args[i])
            {
                case "-b":
                case "--benchmark":
                    benchmark = true;
                    if (optimise) {
                        System.err.println("Benchmark and Optimise flags are incompatible");
                        System.exit(1);
                    }

                    if (i <= args.length - 2) {
                        try
                        {
                            size = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e)
                        {
                            System.err.println("Specify how many times to run each heuristic, and the number of iterations");
                            System.exit(1);
                        }
                    }
                    break;
                case "-s":
                case "--optimise":
                    optimise = true;
                    if (benchmark) {
                        System.err.println("Benchmark and Optimise flags are incompatible");
                        System.exit(1);
                    }

                    if (i <= args.length - 3) {
                        try
                        {
                            size = Integer.parseInt(args[++i]);
                            iterations = Integer.parseInt(args[++i]);
                            if (iterations < 2)
                            {
                                System.err.println("Iterations must be at least 2");
                                System.exit(1);
                            }
                        } catch (NumberFormatException e)
                        {
                            System.err.println("Specify how many times to run each heuristic, and the number of iterations");
                            System.exit(1);
                        }
                    }
                    break;
                case "-o":
                case "--output":
                    if (i <= args.length - 2) {
                        output = args[++i];
                    } else {
                        System.err.println("No output file specified");
                        System.exit(0);
                    }
                    break;
            }
            i++;
        }

        View view;

        if (benchmark)
        {
            view = new BenchmarkerView(size);
        } else if (optimise)
        {
            view = new OptimiserView(size, iterations);
        } else
        {
            view = MainView.getInstance();
        }

        MainModel model = new MainModel(4, 4);
        new MainController(model, view);

        if (output != "")
        {
            try
            {
                File file = new File(output);
                view.startIfTerminal(file);
            } catch (IOException e)
            {
                System.err.println("Failed to open file " + output);
                System.exit(0);
            }
        }
    }
}
