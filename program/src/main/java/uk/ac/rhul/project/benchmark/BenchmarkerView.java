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

public class BenchmarkerView implements View {
    private static final GameConfiguration[] CONFIGURATIONS = new GameConfiguration[]{
            new GameConfiguration(2, 2, 7, 3, new SumCells()),
            new GameConfiguration(2, 2, 7, 3, new LargestLower()),
            new GameConfiguration(2, 2, 7, 3, new FailRatio(new DynamicSnake(2, 2), 0.8)),
            new GameConfiguration(2, 2, 7, 3, new FailSetter(new Monotonic(), -1000)),
            new GameConfiguration(3, 3, 7, 3, new SumCells()),
            new GameConfiguration(3, 3, 7, 3, new LargestLower()),
            new GameConfiguration(3, 3, 7, 3, new FailRatio(new DynamicSnake(3, 3), 0.8)),
            new GameConfiguration(3, 3, 7, 3zz, new FailSetter(new Monotonic(), -1000)),
    };
    private final int count;
    private final BenchmarkWriter csvWriter;
    private NewGameObserver newGameObserver;
    private SolveObserver solveObserver;
    private int configIndex = 0;
    private int gameIndex = 0;
    private GameState currentState;

    public BenchmarkerView(int count) {
        this.count = count;
        this.csvWriter = new BenchmarkWriter();
    }

    public static int depth4_4(int k) {
        int i = k == 0 ? 7 : (int) Math.floor(21 * Math.pow(k, -0.065) - 3.75);
        System.out.println(i + " " + k);
        return Math.max(i, 7);
    }

    public void benchmark(OutputStream log) throws IOException {
        this.csvWriter.setOutput(log);
        for (int i = 0; i < CONFIGURATIONS.length; i++) {
            this.configIndex = i;
            System.out.print("Starting with heuristic: ");
            System.out.println(CONFIGURATIONS[configIndex].getName());
            for (int j = 0; j < this.count; j++) {
                this.gameIndex = j;
                CONFIGURATIONS[configIndex].setSeed(this.gameIndex);
                System.out.printf("Starting game %d\n", gameIndex + 1);
                this.newGameObserver.notifyObservers(CONFIGURATIONS[configIndex]);
                long start = Clock.systemUTC().millis();
                this.solveObserver.notifyObserver(true);
                long end = Clock.systemUTC().millis();
                this.csvWriter.add(new BenchmarkEntry(CONFIGURATIONS[configIndex].getName(), currentState, end - start));
                this.csvWriter.write();
            }
        }
        this.csvWriter.close();
    }

    @Override
    public void addNewGameObserver(NewGameObserver handelNewGame) {
        this.newGameObserver = handelNewGame;
    }

    @Override
    public void addSolveObserver(SolveObserver handelSolve) {
        this.solveObserver = handelSolve;
    }

    @Override
    public void setValues(GameState state) {
        System.out.printf("%s Game %d/%d: Score: %d \n %s\n",
                CONFIGURATIONS[this.configIndex].getName(), gameIndex + 1, count, state.getScore(),
                Arrays.deepToString(state.getGrid()));

    }

    @Override
    public void updateGrid(GameState state) {
        this.currentState = state;
        setValues(state);
    }

    @Override
    public void startIfTerminal(File log) throws IOException {
        this.benchmark(new FileOutputStream(log));
    }
}
