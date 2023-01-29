package uk.ac.rhul.project.benchmark;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;
import java.util.ArrayList;
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

    private final CsvSchema schema;
    private final CsvMapper mapper;

    private SequenceWriter writer;

    /**
     * Define an instance of benchmark writer.
     */
    public BenchmarkWriter()
    {
        entries = new ArrayList<>();
        this.mapper = new CsvMapper();
        this.schema = mapper.schemaFor(BenchmarkEntry.class).withHeader();
    }

    /**
     * Add an entry to the benchmarks, to be written at a later date.
     * @param entry contains data on how good the final result of a game was.
     */
    public void add(BenchmarkEntry entry)
    {
        this.entries.add(entry);
    }

    /**
     * Write the CSV output to an output stream.
     * @param output The stream to write the output to.;
     * @throws IOException Thrown if the output stream is not writable.
     */
    public void write() throws IOException
    {
        writer.write(this.entries);
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
