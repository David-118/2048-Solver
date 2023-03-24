package uk.ac.rhul.project.game;

import java.util.stream.IntStream;

/**
 * Represents the directions a 2048 move can occur in.
 */
public enum Direction {
    /**
     * Direction up on 2048 gird. (-1 row)
     */
    UP(-1, 0, true),
    /**
     * Direction down on 2048 grid (+1 row)
     */
    DOWN(1, 0, true),
    /**
     * Direction left on 2048 grid (-1 col)
     */
    LEFT(0, -1, false),

    /**
     * Direction right on 2048 grid (+1 col)
     */
    RIGHT(0, 1, false);

    /**
     * The amount a direction changes the first index in the array
     * <p>
     * i.e. +1 move down 1, -1 move down 1.
     * </p>
     */
    private final int rows;

    /**
     * The amount a direction changes the second index in the array
     * <p>
     * i.e. +1 move right 1, -1 move left 1.
     * </p>
     */
    private final int cols;

    /**
     * Is the movement on the vertical axis?
     */
    private final boolean vAxis;

    /**
     * Crate a direction.
     *
     * @param rows  change in first index of array.
     * @param cols  change in second index of array.
     * @param vAxis is the direction in the vertical axis?
     */
    Direction(int rows, int cols, boolean vAxis) {
        this.rows = rows;
        this.cols = cols;
        this.vAxis = vAxis;
    }

    /**
     * get the change in rows from this motion.
     *
     * @return change in rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * get the change in cols from this motion.
     *
     * @return change in cols.
     */
    public int getCols() {
        return cols;
    }

    /**
     * generates a stream which contains the order the rows should be iterated through when making this move.
     *
     * @param height The height of the 2048 game.
     * @return array of row to iterate through when making the movement.
     */
    public int[] getVerticalStream(int height) {
        return getStream(true, height);
    }

    /**
     * generates a stream which contains the order the columns should be iterated through when making this move.
     *
     * @param width The height of the 2048 game.
     * @return array of columns to iterate through when making the movement.
     */
    public int[] getHorizontalStream(int width) {
        return getStream(false, width);
    }

    /**
     * Generate a stream that can represent a row or column.
     *
     * @param vAxis Is stream in the vertical axis?
     * @param max   Width/Height of the 2048 game.
     * @return An integer array to be iterated through,
     */
    private int[] getStream(boolean vAxis, int max) {
        boolean reversed = this == DOWN || this == RIGHT;
        IntStream stream;

        if (this.vAxis == vAxis) {
            stream = IntStream.range(1, max);
            if (reversed) {
                stream = stream.map(i -> max - i - 1);
            }
        } else {
            stream = IntStream.range(0, max);
        }

        return stream.toArray();
    }
}
