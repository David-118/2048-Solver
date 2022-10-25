package uk.ac.rhul.project.game;

import java.util.stream.IntStream;

public enum Direction
{
    UP(-1, 0, true),
    DOWN(1, 0, true),
    LEFT(0, -1, false),
    RIGHT(0, 1, false);

    private final int rows;
    private final int cols;
    private boolean vAxis;

    Direction(int rows, int cols, boolean vAxis)
    {
        this.rows = rows;
        this.cols = cols;
        this.vAxis = vAxis;
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public int[] getVerticalStream(int height)
    {
        return getStream(true, height);
    }

    public int[] getHorizontalStream(int width)
    {
        return getStream(false, width);
    }

    private int[] getStream(boolean vAxis, int max)
    {
        boolean reversed = this == DOWN || this == RIGHT;
        IntStream stream;

        if (this.vAxis == vAxis)
        {
            stream = IntStream.range(1, max);
            if (reversed)
            {
                stream = stream.map(i -> max - i - 1);
            }
        }
        else
        {
            stream = IntStream.range(0, max);
        }

        return stream.toArray();
    }
}
