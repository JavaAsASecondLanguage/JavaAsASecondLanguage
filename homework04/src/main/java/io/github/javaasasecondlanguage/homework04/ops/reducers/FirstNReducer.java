package io.github.javaasasecondlanguage.homework04.ops.reducers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.HashMap;
import java.util.Map;

/**
 * Returns at most maxAmount records per group.
 */
public class FirstNReducer implements Reducer {
    private int maxAmount;
    private int currAmount = 0;

    public FirstNReducer(int maxAmount) {

        this.maxAmount = maxAmount;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        if (this.currAmount < this.maxAmount) {
            collector.collect(inputRecord);
        }

        this.currAmount += 1;
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        this.currAmount = 0;
    }
}
