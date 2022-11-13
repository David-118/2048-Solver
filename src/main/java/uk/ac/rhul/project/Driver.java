
package uk.ac.rhul.project;

import uk.ac.rhul.project.benchmark.Benchmarker;
import uk.ac.rhul.project.userInterface.MainController;
import uk.ac.rhul.project.userInterface.MainModel;
import uk.ac.rhul.project.userInterface.MainView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Driver
{


    public static void main(String[] args)
    {
        String output = "";
        boolean benchmark = false;
        int size = 0;
        int i = 0;
        while (i < args.length)
        {
            switch (args[i])
            {
                case "-b":
                case "--benchmark":
                    benchmark = true;
                    if (i <= args.length - 2) {
                        try
                        {
                            size = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e)
                        {
                            System.err.println("Specify how many time to run each heuristic");
                            System.exit(0);
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

        if (benchmark)
        {
            Benchmarker benchmarker = new Benchmarker(size);
            try
            {
                File file = new File(output);
                file.createNewFile();
                benchmarker.benchmark(new FileOutputStream(file));
            } catch (IOException e)
            {
                System.err.println("Failed to open file " + output);
                System.exit(0);
            }
        } else
        {
            MainView view = MainView.getInstance();
            MainModel model = new MainModel(4, 4);
            new MainController(model, view);
        }
    }
}
