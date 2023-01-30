package uk.ac.rhul.project.benchmark;


import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Used to write BenchmarkEntries to a CSV file.
 *
 * <p>Written with aid from [9]</p>
 */
public class BenchmarkWriter
{
    /**
     * A list containing all the entries to write to the csv file.
     */
   private final List<BenchmarkEntry> entries;
   private final List<BenchmarkEntry> buffer;

    private final CsvSchema schema;
    private final CsvMapper mapper;

    private SequenceWriter writer;

    /**
     * Define an instance of benchmark writer.
     */
    public BenchmarkWriter()
    {
        this.entries = new ArrayList<>();
        this.buffer = new ArrayList<>();
        this.mapper = new CsvMapper();
        this.schema = mapper.schemaFor(BenchmarkEntry.class).withHeader();
    }

    /**
     * Add an entry to the benchmarks, to be written at a later date.
     * @param entry contains data on how good the final result of a game was.
     */
    public void add(BenchmarkEntry entry)
    {
        this.buffer.add(entry);

        int insertAt = Collections.binarySearch(this.entries, entry);

        // Get the index to insert item, if equal item not in array.
        if (insertAt < 0) insertAt = -insertAt - 1;

        this.entries.add(insertAt, entry);
    }

    /**
     * Write the CSV output to an output stream.
     * @param output The stream to write the output to.;
     * @throws IOException Thrown if the output stream is not writable.
     */
    public void write() throws IOException
    {
        writer.write(this.buffer);
        this.buffer.clear();
    }

    public BenchmarkEntry median()
    {
        int count = entries.size();
        return  entries.get(count / 2);
    }

    public void flushEntries()
    {
        this.entries.clear();
    }

    public void close() throws IOException
    {
        writer.close();
    }

    public void setOutput(OutputStream output) throws IOException
    {
        this.writer = mapper.writer(schema).writeValues(output);
    }
}
