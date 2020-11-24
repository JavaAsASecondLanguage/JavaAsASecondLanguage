package io.github.javaasasecondlanguage.homework04.ops.reducers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Returns distinct records.
 */
public class DistinctReducer implements Reducer {

    private final String inputColumn;
    private final Set<String> found;

    public DistinctReducer(String inputColumn) {
        this.inputColumn = inputColumn;
        found = new HashSet<>();
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        if (!found.contains(inputRecord.getString(inputColumn))) {
            collector.collect(inputRecord);
            found.add(inputRecord.getString(inputColumn));
        }
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        found.clear();
    }
}
