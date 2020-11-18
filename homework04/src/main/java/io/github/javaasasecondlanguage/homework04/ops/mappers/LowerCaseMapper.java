package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

/**
 * Shifts selected column to lowercase.
 */
public class LowerCaseMapper implements Mapper {
    private String column;

    public LowerCaseMapper(String column) {
        this.column = column;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
        var newRecord = inputRecord.copy();
        var lowerCased = newRecord.getString(this.column).toLowerCase();
        newRecord.set(this.column, lowerCased);
        collector.collect(newRecord);
    }
}
