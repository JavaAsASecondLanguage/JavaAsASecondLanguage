package io.github.javaasasecondlanguage.homework04.ops.reducers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Returns at most maxAmount records per group.
 */
public class FirstNReducer implements Reducer {

    private final int maxAmount;
    private final List<Record> records;

    public FirstNReducer(int maxAmount) {
        this.maxAmount = maxAmount;
        records = new ArrayList<>(maxAmount);
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        if (records.size() < maxAmount) {
            records.add(inputRecord);
        }
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        for (Record record : records) {
            collector.collect(record);
        }
        records.clear();
    }
}
