package uk.ac.rhul.project.userInterface;

import uk.ac.rhul.project.heursitics.*;

/**
 * Option that can be selected from gui to make hermeneutic.
 */
abstract class HeuristicOptions {
    /**
     * Name of the heuristic to appear in drop down.
     */
    private final String label;

    /**
     * Name of the heuristic option
     *
     * @param label Name of the heuristic to appear in drop down.
     */
    public HeuristicOptions(String label) {
        this.label = label;
    }

    /**
     * Make the heurstic.
     * @param rows Height of the game.
     * @param cols Width of the game.
     * @return the heustic.
     */
    public abstract Heuristic make(int rows, int cols);

    /**
     * gets the name of the heustic to appear in the drop down.
     * @return Label provided in constructor.
     */
    public String toString() {
        return label;
    }
}

/**
 * Option for SumCells Heuristic
 */
class SumCellsOption extends HeuristicOptions {
    /**
     * Make sum cells option.
     */
    public SumCellsOption() {
        super("Sum Cells");
    }

    /**
     * Make sum cells heuristic.
     * @param rows Height of the game.
     * @param cols Width of the game.
     * @return sum cells heuristic.
     */
    @Override
    public Heuristic make(int rows, int cols) {
        return new SumCells();
    }
}

/**
 * Largest Lower Heuristic Option
 */
class LargestLowerOption extends HeuristicOptions {
    /**
     * Make instance of LargestLowerOption.
     */
    public LargestLowerOption() {
        super("Largest Lower");
    }

    /**
     * Gets instance of LargestLower heuristic.
     * @param rows Height of the game.
     * @param cols Width of the game.
     * @return instance of LargestLower.
     */
    @Override
    public Heuristic make(int rows, int cols) {
        return new LargestLower();
    }
}

/**
 * Largest Right Heuristic Option
 */
class LargestRightOption extends HeuristicOptions {
    /**
     * Make instance of LargestRightOption.
     */
    public LargestRightOption() {
        super("Largest Right");
    }

    /**
     * Gets instance of LargestRight heuristic.
     * @param rows Height of the game.
     * @param cols Width of the game.
     * @return instance of LargestRight.
     */
    @Override
    public Heuristic make(int rows, int cols) {
        return new LargestRight();
    }
}

/**
 * Monotonic heuristic option, fail setter included.
 */
class MonotonicOption extends HeuristicOptions {
    /**
     * Make a new instance of MonotonicOption.
     */
    public MonotonicOption() {
        super("Monotonic");
    }

    /**
     * Make a Monotonic Heuristic in a fail setter (-1000).
     * @param rows Height of the game.
     * @param cols Width of the game.
     * @return FailSetter (failScore = -1000) wrapped around a Monotonic Heuristic.
     */
    @Override
    public Heuristic make(int rows, int cols) {
        return new FailSetter(new Monotonic(), -1000);
    }
}

/**
 * DynamicSnake Heuristic option, fail ratio included.
 */
class DynamicSnakeOption extends HeuristicOptions {
    /**
     * Make DynamicSnake Option.
     */
    public DynamicSnakeOption() {
        super("Dynamic Snake");
    }

    /**
     * Make a dynamic snake heuristic.
     * @param rows Height of the game.
     * @param cols Width of the game.
     * @return FailRatio (ratio=0.6) wrapped around DynamicSnake object size (row, col).
     */
    @Override
    public Heuristic make(int rows, int cols) {
        return new FailRatio(new DynamicSnake(rows, cols), 0.6);
    }
}

/**
 * Older Sanke4x4 Heuristic Option.
 */
class Snake4x4Option extends HeuristicOptions {
    /**
     * Make a Snake 4x4 Option
     */
    public Snake4x4Option() {
        super("Snake 4x4");
    }

    /**
     * Make a snake4x4 Heuristic.
     * @param rows Height of the game.
     * @param cols Width of the game.
     * @return Snake4x4 Heuristic, failRatio not included.
     */
    @Override
    public Heuristic make(int rows, int cols) {
        return new Snake4x4();
    }
}