package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

import java.util.function.Function;

/**
 * Calculates a new value from record using specified lambda. Then saves it into the outputColumn.
 */
public class AddColumnMapper implements Mapper {

    private final String outputColumn;
    private final Function<Record, ?> lambda;

    public AddColumnMapper(String outputColumn, Function<Record, ?> lambda) {
        this.outputColumn = outputColumn;
        this.lambda = lambda;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
        Record newRecord = new Record(inputRecord.getData());
        newRecord.set(outputColumn, lambda.apply(inputRecord));
        collector.collect(newRecord);
    }
}
