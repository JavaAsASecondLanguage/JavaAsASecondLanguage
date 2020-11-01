package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.Collector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Mapper;

import java.util.function.Function;

/**
 * Drops records if they return true on predicate.
 */
public class FilterMapper implements Mapper {

    private final Function<Record, Boolean> predicate;

    public FilterMapper(Function<Record, Boolean> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
        var isAcceptable = predicate.apply(inputRecord);
        if (isAcceptable) {
            collector.collect(inputRecord);
        }
    }
}
