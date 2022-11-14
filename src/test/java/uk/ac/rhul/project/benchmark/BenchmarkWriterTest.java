package uk.ac.rhul.project.benchmark;

import org.junit.jupiter.api.Test;
import uk.ac.rhul.project.game.Direction;
import uk.ac.rhul.project.game.GameState;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BenchmarkWriterTest
{

    @Test
    void write()
    {
        BenchmarkWriter benchmarkWriter = new BenchmarkWriter();
        GameState state1 = new GameState(2, 2);
        state1.setGrid(new int[][]{{16, 8}, {4, 2}});

        GameState state2 = new GameState(4, 4, new Random(0));
        state2.init();
        state2.move(Direction.RIGHT);
        state2.move(Direction.DOWN);

        benchmarkWriter.add(new BenchmarkEntry("Heuristic 1", state1));
        benchmarkWriter.add(new BenchmarkEntry("Heuristic 2", state2));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try
        {
            benchmarkWriter.write(byteArrayOutputStream);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        assertEquals("heuristicName,maxTile,score\n" +
                "\"Heuristic 1\",16,0\n" +
                "\"Heuristic 2\",4,4\n", byteArrayOutputStream.toString(StandardCharsets.UTF_8));
    }
}