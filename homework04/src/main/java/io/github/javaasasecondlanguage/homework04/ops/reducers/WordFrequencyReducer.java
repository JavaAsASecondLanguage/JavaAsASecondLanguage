package io.github.javaasasecondlanguage.homework04.ops.reducers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Calculate frequency of values in column for each group.
 */
public class WordFrequencyReducer implements Reducer {

    private final String termColumn;
    private final String outputColumn;
    private Double termCounter;

    private final Map<String, Integer> frequencyCounter = new HashMap<String, Integer>() {
        @Override
        public Integer get(Object key) {
            if(!containsKey(key))
                return 0;
            return super.get(key);
        }
    };

    public WordFrequencyReducer(String termColumn, String outputColumn) {
        this.termColumn = termColumn;
        this.outputColumn = outputColumn;
        this.termCounter = 0.0;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        frequencyCounter.compute(inputRecord.getString(termColumn),
                (k, v) -> v == null ? 1 : v + 1);
        termCounter += 1.0;
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        var record = new Record(groupByEntries);
        frequencyCounter.entrySet().stream()
                .map(kv -> {
                    return record
                            .copy()
                            .setAll(Map.of(termColumn, kv.getKey(),
                                    outputColumn, kv.getValue() / termCounter));
                })
                .sorted(Comparator.comparing(rec -> rec.getString(termColumn)))
                .forEach(rec -> collector.collect(rec));

        frequencyCounter.clear();
        termCounter = 0.0;
    }

}
