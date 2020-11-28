package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Keeps only specified columns.
 */
public class RetainColumnsMapper implements Mapper {
    private final LinkedList<String> retainedColumns;

    public RetainColumnsMapper(Collection<String> retainedColumns) {
        this.retainedColumns = new LinkedList(retainedColumns);
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
        Record outputRecord = inputRecord.copyColumns(this.retainedColumns);
        collector.collect(outputRecord);
    }
}
