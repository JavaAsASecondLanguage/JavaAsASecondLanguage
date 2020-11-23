package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

/**
 * Does absolutely nothing useful.
 */
public class IdentityMapper implements Mapper {

    @Override
    public void apply(Record inputRecord, Collector collector) { collector.collect(inputRecord); }
}
