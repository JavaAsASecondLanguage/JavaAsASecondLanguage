package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

import java.util.function.Function;

/**
 * Drops records if they return true on predicate.
 */
public class FilterMapper implements Mapper {

    final private Function<Record, Boolean> predicate;

    public FilterMapper(Function<Record, Boolean> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
        if (predicate.apply(inputRecord)) {
            collector.collect(inputRecord.copy());
        }
    }
}
