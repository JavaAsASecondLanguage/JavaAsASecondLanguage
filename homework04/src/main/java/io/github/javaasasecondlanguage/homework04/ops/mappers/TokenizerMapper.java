package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

import java.util.LinkedList;

import static java.util.List.of;

/**
 * Splits text in the specified column into words, then creates a new record with each word.
 *
 * Split should happen on the following symbols: " ", ".", ",", "!", ";", "?", "'", ":"
 */
public class TokenizerMapper implements Mapper {
    private final String inputColumn;
    private final String outputColumn;

    private static final String SPLIT_PATTERN = "[\\s,\\.\\!\\;\\?\\'\\:\"]+";

    public TokenizerMapper(String inputColumn, String outputColumn) {
        this.inputColumn = inputColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
        for (String word : inputRecord.getData().get(this.inputColumn).toString().split(SPLIT_PATTERN)) {
            LinkedList<String> tmp = new LinkedList<String>();
            tmp.add(this.inputColumn);
            Record outputRecord = inputRecord.copyColumnsExcept(tmp);
            outputRecord.set(this.outputColumn, word);
            collector.collect(outputRecord);
        }
    }
}
