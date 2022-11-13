package uk.ac.rhul.project.benchmark;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BenchmarkWriter
{
    List<BenchmarkEntry> entries;
    CsvSchema schema;
    CsvMapper mapper;

    public BenchmarkWriter()
    {
        entries = new ArrayList<>();
        this.mapper = new CsvMapper();
        this.schema = mapper.schemaFor(BenchmarkEntry.class).withHeader();
    }

    public void add(BenchmarkEntry entry)
    {
        this.entries.add(entry);
    }

    public void write(OutputStream output) throws JsonProcessingException, IOException
    {
        SequenceWriter writer = mapper.writer(schema).writeValues(output);
        writer.write(this.entries);
        writer.close();
    }
}
