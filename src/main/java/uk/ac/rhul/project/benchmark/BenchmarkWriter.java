package uk.ac.rhul.project.benchmark;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BenchmarkWriter
{
    List<BenchmarkEntry> entries;

    public BenchmarkWriter()
    {
        entries = new ArrayList<>();
    }

    public void add(BenchmarkEntry entry)
    {
        this.entries.add(entry);
    }

    public void write(OutputStream output) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException
    {
        Writer writer = new OutputStreamWriter(output);

        StatefulBeanToCsv<BenchmarkEntry>  entryToCsv= new StatefulBeanToCsvBuilder<BenchmarkEntry>(writer).build();

        entryToCsv.write(this.entries);
        writer.close();

    }
}
