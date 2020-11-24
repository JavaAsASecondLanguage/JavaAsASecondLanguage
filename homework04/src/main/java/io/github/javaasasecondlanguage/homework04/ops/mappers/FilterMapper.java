package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

import java.util.function.Function;

/**
 * Drops records if they return true on predicate.
 */
public class FilterMapper implements Mapper {
    private final Function<Record, Boolean> predicate;

    public FilterMapper(Function<Record, Boolean> predicate) {
//        throw new IllegalStateException("You must implement this");
        this.predicate = predicate;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
//        throw new IllegalStateException("You must implement this");
        if (predicate.apply(inputRecord)) {
            collector.collect(inputRecord.copy());
        }
    }
}
