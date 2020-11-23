package io.github.javaasasecondlanguage.homework04.ops.reducers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.Map;

/**
 * Counts records in each group and returns a single record with a count.
 *
 * Note: it is already implemented, use it as reducer example.
 */
public class CountReducer implements Reducer {

    private final String outputColumn;
    int currentCount = 0;

    public CountReducer(String outputColumn) {
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        currentCount++;
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        collector.collect(new Record(groupByEntries).set(outputColumn, currentCount));
        currentCount = 0;
    }
}
