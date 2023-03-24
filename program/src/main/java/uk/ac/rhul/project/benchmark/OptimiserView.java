package uk.ac.rhul.project.benchmark;

import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.DynamicSnake;
import uk.ac.rhul.project.heursitics.FailRatio;
import uk.ac.rhul.project.userInterface.NewGameObserver;
import uk.ac.rhul.project.userInterface.SolveObserver;
import uk.ac.rhul.project.userInterface.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Clock;

/**
 * OptimiserView is designed to calculate the optimal value for some heuristic. It will run a given game configuration
 * ranging a number (N) between two values.
 */
public class OptimiserView implements View {
    /**
     * Minimum value of N (Inclusive)
     */
    private static final double minN = -1;

    /**
     * Max Value of N (Inclusive).
     */
    private static final double maxN = 1;

    /**
     * Values of times to step N,
     * <p>E.g. If minN = -1 and maxN = 1 and iterationCount = 3 N will take value -1, 0, 1</p>
     */
    private final int iterationCount;

    /**
     * Median Scores for each value of N.
     */
    private final BenchmarkEntry[] medianScores;

    /**
     * Values of N tried so far.
     */
    private final double[] ns;

    /**
     * The number of times to run each game.
     */
    private final int count;

    /**
     * Writes data to csv file.
     */
    private final BenchmarkWriter csvWriter;

    /**
     * Optimal median value (so far).
     */
    private BenchmarkEntry optimalMedian;

    /**
     * Method used to start a new Game is stored here.
     */
    private NewGameObserver newGameObserver;

    /**
     * Method used to start solving a game is stored here.
     */
    private SolveObserver solveObserver;

    /**
     * Index of the current value of N to use.
     */
    private int configIndex = 0;

    /**
     * The index of the game currently being played. <br>
     * <i>E.g. first game in a series of 100 would be 0, last in 99.</i>
     */
    private int gameIndex = 0;

    /**
     * State of the current game.
     */
    private GameState currentState;

    /**
     * Create a new OptimierView
     * @param gameCount The number of games in each iteration
     * @param iterations Number of times to step N or total number of iterations.
     */
    public OptimiserView(int gameCount, int iterations) {
        this.count = gameCount;
        this.iterationCount = iterations;
        this.csvWriter = new BenchmarkWriter();
        this.medianScores = new BenchmarkEntry[iterations];
        this.ns = new double[iterations];
    }

    /**
     * Function used to make a game with given configeration
     * @param n the value to vary when making the heuristic.
     * @return The configuration of the current game.
     */
    public static GameConfiguration makeGame(double n) {
        return new GameConfiguration(4, 4, 6, Integer.MAX_VALUE, new FailRatio(new DynamicSnake(4, 4), n));
    }

    /**
     * Start calculating the optimal value
     * @param log Output stream for CSV data.
     * @throws IOException Output stream is unavailable.
     */
    public void benchmark(OutputStream log) throws IOException {
        this.configIndex = 0;
        this.table();

        this.csvWriter.setOutput(log);
        for (double n = minN; n <= maxN; n += (maxN - minN) / (iterationCount - 1)) {
            runGames(n);

            BenchmarkEntry median = csvWriter.median();

            if (optimalMedian == null || median.compareTo(optimalMedian) > 0) {
                optimalMedian = median;
            }


            this.csvWriter.flushEntries();
            this.configIndex++;
        }
        this.csvWriter.close();
    }

    /**
     * Draw vertical line in the table displayed.
     * @param scoreWidth Width of the score column
     * @param tileWidth  Width of the tile column
     */
    public void tableDivide(int scoreWidth, int tileWidth) {
        System.out.print("+-----+-");
        for (int j = 0; j < scoreWidth; j++) System.out.print("-");
        System.out.print("-+-");
        for (int j = 0; j < tileWidth; j++) System.out.print("-");
        System.out.println("-+");
    }

    /**
     * Update the table on the screen.
     */
    private void table() {
        // Clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        int maxTile = 0;
        int maxScore = 0;

        for (int i = 0; i < this.iterationCount; i++) {
            if (medianScores[i] != null) {
                if (medianScores[i].maxTile > maxTile) {
                    maxTile = medianScores[i].maxTile;
                }

                if (medianScores[i].score > maxScore) {
                    maxScore = medianScores[i].score;
                }
            }
        }

        // Draw table headers
        int scoreWidth = "Median Score".length();
        int tileWidth = "Median Max Tile".length();
        tableDivide(scoreWidth, tileWidth);
        System.out.println("|  N  | Median Score | Median Max Tile |");

        // Draw each table row
        for (int i = 0; i < this.iterationCount; i++) {
            int score, tile;
            if (medianScores[i] == null) {
                score = 0;
                tile = 0;
            } else {
                score = medianScores[i].score;
                tile = medianScores[i].maxTile;
            }
            tableDivide(scoreWidth, tileWidth);
            System.out.printf("|% .1f | %" + scoreWidth + "d | %" + tileWidth + "d |\n", this.ns[i],
                    score, tile);
        }
        tableDivide(scoreWidth, tileWidth);
    }

    /**
     * Run n games with a given configuration.
     * @param n Number of games to run.
     * @throws IOException Log file unavailable.
     */
    private void runGames(double n) throws IOException {
        for (int j = 0; j < this.count; j++) {
            // Run Game.
            this.gameIndex = j;
            this.newGameObserver.notifyObservers(makeGame(n));
            long start = Clock.systemUTC().millis();
            this.solveObserver.notifyObserver(true);
            long end = Clock.systemUTC().millis();

            // Log Game
            this.csvWriter.add(new BenchmarkEntry("n=" + n, this.currentState, end - start));

            // Update table
            BenchmarkEntry median = csvWriter.median();
            this.medianScores[configIndex] = median;
            this.ns[configIndex] = n;
            this.table();

            this.csvWriter.write();
        }
    }

    /**
     * Set method to create a new game.
     * @param handelNewGame Method to call when new game is made.
     */
    @Override
    public void addNewGameObserver(NewGameObserver handelNewGame) {
        this.newGameObserver = handelNewGame;
    }

    /**
     * Set method to solve a game.
     * @param handelSolve Method to call to solve a game.
     */
    @Override
    public void addSolveObserver(SolveObserver handelSolve) {
        this.solveObserver = handelSolve;
    }

    /**
     * Update game state from the same thread. Only outputs a status information about the current game.
     * @param state Current game state
     */
    @Override
    public void setValues(GameState state) {
        String output = String.format("Value: %d/%d Game: %d/%d Current Score: %d",
                configIndex + 1, iterationCount, gameIndex + 1, count, state.getScore());

        System.out.print(output);
        for (int i = 0; i < output.length(); i++) System.out.print("\b");
    }

    /**
     * Update game state from any thread.
     * @param state Current game state.
     */
    @Override
    public void updateGrid(GameState state) {
        this.currentState = state;
        setValues(state);
    }

    /**
     * Starts finding optimal value.
     * @param log File to log csv data to.
     * @throws IOException If file is unavailable.
     */
    public void startIfTerminal(File log) throws IOException {
        this.benchmark(new FileOutputStream(log));
    }
}

