package uk.ac.rhul.project.benchmark;


import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Used to write BenchmarkEntries to a CSV file.
 *
 * <p>Written with aid from [9]</p>
 */
public class BenchmarkWriter {
    /**
     * A list containing all the entries in the benchmark.
     */
    private final List<BenchmarkEntry> entries;

    /**
     * A list containing all the entires that have not yet been written to the file.
     */
    private final List<BenchmarkEntry> buffer;

    /**
     * Schema for the csv
     */
    private final CsvSchema schema;

    /**
     * Maps attributes on to the schema
     */
    private final CsvMapper mapper;

    /**
     * Writes the csv to the file.
     */
    private SequenceWriter writer;

    /**
     * Define an instance of benchmark writer.
     */
    public BenchmarkWriter() {
        this.entries = new ArrayList<>();
        this.buffer = new ArrayList<>();
        this.mapper = new CsvMapper();
        this.schema = mapper.schemaFor(BenchmarkEntry.class).withHeader();
    }

    /**
     * Add an entry to the benchmarks to the buffer to be written to later.
     *
     * @param entry contains data on how good the final result of a game was.
     */
    public void add(BenchmarkEntry entry) {
        this.buffer.add(entry);

        int insertAt = Collections.binarySearch(this.entries, entry);

        // Get the index to insert item, if equal item not in array.
        if (insertAt < 0) insertAt = -insertAt - 1;

        this.entries.add(insertAt, entry);
    }

    /**
     * Write the CSV output from current buffer.
     *
     * @throws IOException Thrown if the output stream is not writable.
     */
    public void write() throws IOException {
        writer.write(this.buffer);
        this.buffer.clear();
    }

    /**
     * Get the median value of a data set
     * <b>Note:</b> this may sometimes be inaccurate for even length data.
     *
     * @return The median entry of the list (sorted by score)
     */
    public BenchmarkEntry median() {
        int count = entries.size();
        return entries.get(count / 2);
    }

    /**
     * Remove all entries from benchmark. Use before stating another benchmark.
     */
    public void flushEntries() {
        this.entries.clear();
    }

    /**
     * Close the CSV file.
     *
     * @throws IOException If the CSV file is unavailable.
     */
    public void close() throws IOException {
        writer.close();
    }

    /**
     * Set the output for the csv data.
     *
     * @param output Output stream for the csv (often a FileOutputStream)
     * @throws IOException Thrown wehen output is unavailable.
     */
    public void setOutput(OutputStream output) throws IOException {
        this.writer = mapper.writer(schema).writeValues(output);
    }
}
