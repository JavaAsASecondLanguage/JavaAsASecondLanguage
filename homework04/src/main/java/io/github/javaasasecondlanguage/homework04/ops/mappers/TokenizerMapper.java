package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

import java.util.ArrayList;
import java.util.List;

import static java.util.List.of;

/**
 * Splits text in the specified column into words, then creates a new record with each word.
 *
 * Split should happen on the following symbols: " ", ".", ",", "!", ";", "?", "'", ":"
 */
public class TokenizerMapper implements Mapper {

    private static final String SPLIT_PATTERN = "[\\s,\\.\\!\\;\\?\\'\\:\"]+";

    private final List<String> inputColumn = new ArrayList<>();
    private final String outputColumn;

    public TokenizerMapper(String inputColumn, String outputColumn) {
        this.inputColumn.add(inputColumn);
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
        final String text = inputRecord.getString(inputColumn.get(0));
	final Record rec = inputRecord.copyColumnsExcept(inputColumn);
        for (var x : text.split(SPLIT_PATTERN)) {
            final Record out = rec.copy();
            out.set(outputColumn, x);
            collector.collect(out);
        }
    }
}
