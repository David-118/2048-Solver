package uk.ac.rhul.project.benchmark;

import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.*;
import uk.ac.rhul.project.userInterface.NewGameObserver;
import uk.ac.rhul.project.userInterface.SolveObserver;
import uk.ac.rhul.project.userInterface.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Clock;
import java.util.Arrays;

/**
 * This view is designed to benchmark the provided games each n times.
 *
 * It prints basic information about the current game, which game of its type it is on, the score and current
 * game state to the terminal.
 *
 * Data is saved to a provided csv file at the end of the game.
 */
public class BenchmarkerView implements View {
    /**
     * The games to be played in a benchmark should be placed in this array before building.
     */
    private static final GameConfiguration[] CONFIGURATIONS = new GameConfiguration[]{
        new GameConfiguration(4, 4, 7, 3, new FailSetter(new Monotonic(), -1000)),
       new GameConfiguration(4, 4, 7, 2, new FailSetter(new Monotonic(), -1000)),
       new GameConfiguration(4, 4, 7, 1, new FailSetter(new Monotonic(), -1000))

    };

    /**
     * Number of each game to play
     */
    private final int count;

    /**
     * Writes data to the CSV file.
     */
    private final BenchmarkWriter csvWriter;

    /**
     * Method to call to create new game
     */
    private NewGameObserver newGameObserver;
    /**
     * Method to call for solving game.
     */
    private SolveObserver solveObserver;

    /**
     * The index of the current config in CONFIGURATIONS
     */
    private int configIndex = 0;

    /**
     * The index of the game currently being played. <br>
     * <i>E.g. first game in a series of 100 would be 0, last in 99.</i>
     */
    private int gameIndex = 0;

    /**
     * The state the game is currently in.
     */
    private GameState currentState;

    /**
     * Create a new benchmark view
     * @param count the number of times to play each configuration.
     */
    public BenchmarkerView(int count) {
        this.count = count;
        this.csvWriter = new BenchmarkWriter();
    }

    /**
     * Dynamic depth function for automatically varying the  depth of a 4x4 game.
     * <p>
     * This also works for a smaller games, however becomes impractical for larger games.
     * </p>
     * @param k Number of node at depth 5 in the tree.
     * @return The depth of the new tree. This will be at least 7.
     */
    public static int depth4_4(int k) {
        int i = k == 0 ? 7 : (int) Math.floor(21 * Math.pow(k, -0.065) - 3.75);
        return Math.max(i, 7);
    }

    /**
     * Start running the benchmarks.
     * @param log An output stream to write CSV data to.
     * @throws IOException If it is unable to write to the output.
     */
    public void benchmark(OutputStream log) throws IOException {
        this.csvWriter.setOutput(log);

        for (int i = 0; i < CONFIGURATIONS.length; i++) {
            this.configIndex = i;
            System.out.print("Starting with heuristic: ");
            System.out.println(CONFIGURATIONS[configIndex].getName());

            for (int j = 0; j < this.count; j++) {
                // Initialise Games
                this.gameIndex = j;
                CONFIGURATIONS[configIndex].setSeed(this.gameIndex);
                System.out.printf("Starting game %d\n", gameIndex + 1);

                // Start and Solve Game.
                this.newGameObserver.notifyObservers(CONFIGURATIONS[configIndex]);
                long start = Clock.systemUTC().millis();
                this.solveObserver.notifyObserver(true);
                long end = Clock.systemUTC().millis();

                // Log game
                this.csvWriter.add(new BenchmarkEntry(CONFIGURATIONS[configIndex].getName(), currentState, end - start));
                this.csvWriter.write();
            }
        }
        this.csvWriter.close();
    }

    /**
     * Set the method to be called to create a new game.
     * @param handelNewGame Method to create new game.
     */
    @Override
    public void addNewGameObserver(NewGameObserver handelNewGame) {
        this.newGameObserver = handelNewGame;
    }

    /**
     * Set the method to be called to solve a game.
     * @param handelSolve Method to solev a game.
     */
    @Override
    public void addSolveObserver(SolveObserver handelSolve) {
        this.solveObserver = handelSolve;
    }

    /**
     * Set state of the 2048 game from the same thread as the view.
     * @param state Current state of the game.
     */
    @Override
    public void setValues(GameState state) {
        System.out.printf("%s Game %d/%d: Score: %d \n %s\n",
                CONFIGURATIONS[this.configIndex].getName(), gameIndex + 1, count, state.getScore(),
                Arrays.deepToString(state.getGrid()));

    }

    /**
     * Set state of 2048 game from any thread.
     * @param state Curret state of the game
     */
    @Override
    public void updateGrid(GameState state) {
        this.currentState = state;
        setValues(state);
    }

    /**
     * Start running the benchmark.
     * @param log File to write CSV data to.
     * @throws IOException Thrown if file is unavailable for writing.
     */
    @Override
    public void startIfTerminal(File log) throws IOException {
        this.benchmark(new FileOutputStream(log));
    }
}
