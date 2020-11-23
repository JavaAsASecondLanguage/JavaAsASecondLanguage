package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

import java.util.List;

/**
 * Splits text in the specified column into words, then creates a new record with each word.
 *
 * Split should happen on the following symbols: " ", ".", ",", "!", ";", "?", "'", ":"
 */
public class TokenizerMapper implements Mapper {

    private static final String SPLIT_PATTERN = "[\\s,\\.\\!\\;\\?\\'\\:\"]+";
    private final String inputColumn;
    private final String outputColumn;

    public TokenizerMapper(String inputColumn, String outputColumn) {
        this.inputColumn = inputColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
        if (inputRecord.isTerminal()) {
            collector.collect(inputRecord);
        } else {
            List.of(inputRecord.getString(inputColumn).split(SPLIT_PATTERN)).stream()
                    .map(token -> {
                        return inputRecord
                                .copyColumnsExcept(List.of(inputColumn))
                                .set(outputColumn, token);
                    })
                    .forEach(rec -> collector.collect(rec));
        }
    }
}
