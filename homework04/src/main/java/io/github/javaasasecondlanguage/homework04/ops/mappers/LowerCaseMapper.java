package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

/**
 * Shifts selected column to lowercase.
 */
public class LowerCaseMapper implements Mapper {

    private final String column;

    public LowerCaseMapper(String column) {
        this.column = column;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
        var text = inputRecord.get(this.column);
        // maybe should throw exception if column null or not String
        var lower = text == null ? null : text.toString().toLowerCase();
        collector.collect(inputRecord.copy().set(this.column, lower));
    }
}
