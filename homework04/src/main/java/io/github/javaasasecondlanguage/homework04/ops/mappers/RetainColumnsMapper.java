package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

import java.util.Collection;

/**
 * Keeps only specified columns.
 */
public class RetainColumnsMapper implements Mapper {

    Collection<String> retainedColumns;

    public RetainColumnsMapper(Collection<String> retainedColumns) {
        this.retainedColumns = retainedColumns;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
       collector.collect(inputRecord.copyColumns(retainedColumns));
    }
}
