package io.github.javaasasecondlanguage.homework04.ops.reducers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Counts sum of values in a specified column for each group and returns a single record with a sum.
 */
public class SumReducer implements Reducer {

    private final String inputColumn;
    private final String outputColumn;
    private int curSum = 0;

    public SumReducer(String inputColumn, String outputColumn) {
        this.inputColumn = inputColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        int delta = Integer.valueOf(inputRecord.get(this.inputColumn).toString());
        this.curSum += delta;
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        Record outputRecord = new Record(groupByEntries);
        outputRecord.set(this.outputColumn, this.curSum);
        collector.collect(outputRecord);

        this.curSum = 0;
    }

}
