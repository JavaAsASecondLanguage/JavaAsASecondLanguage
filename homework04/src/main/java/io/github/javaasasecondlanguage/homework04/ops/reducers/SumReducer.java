package io.github.javaasasecondlanguage.homework04.ops.reducers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.Map;

/**
 * Counts sum of values in a specified column for each group and returns a single record with a sum.
 */
public class SumReducer implements Reducer {
    private String inputColumn;
    private String outputColumn;
    private Double sum = 0.0;

    public SumReducer(String inputColumn, String outputColumn) {
        this.inputColumn = inputColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        this.sum += inputRecord.getDouble(this.inputColumn);
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        var newRecord = new Record(groupByEntries);
        newRecord.set(this.outputColumn, this.sum);
        collector.collect(newRecord);
        this.sum = 0.0;
    }
}
