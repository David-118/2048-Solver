package ProofOfConcept.Game2048;

import java.util.stream.IntStream;

public enum DirectionVect
{
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    DirectionVect(int i, int j)
    {
        this.i = i;
        this.j = j;
    }

    private final int i;
    private final int j;

    public int getI()
    {
        return i;
    }

    public int getJ()
    {
        return j;
    }

    public IntStream getVRange(int height)
    {
        return getStream( this == DOWN || this == UP, height);
    }


    public IntStream getHRange(int width)
    {
        return getStream(this == LEFT || this == RIGHT, width);
    }

    private IntStream getStream(boolean dirAxis, int max)
    {
        boolean reversed = this == DOWN || this == RIGHT;
        IntStream stream;

        if (dirAxis)
        {
            stream = IntStream.range(1, max);
            if (reversed) stream = stream.map(i -> max - i - 1);
        } else
        {
            stream = IntStream.range(0, max);
        }
        return stream;
    }
}
