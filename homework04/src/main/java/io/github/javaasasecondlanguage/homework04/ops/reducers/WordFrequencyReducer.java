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
    private final Map<String, Integer> dict = new HashMap<>();

    public WordFrequencyReducer(String termColumn, String outputColumn) {
        this.termColumn = termColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        final String term = inputRecord.getString(termColumn);
        dict.put(term, dict.getOrDefault(term, 0) + 1);
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        final double total = dict.values().stream().reduce(0, Integer::sum);
        dict.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(item -> {
            Record rec = new Record(groupByEntries);
            rec.set(termColumn, item.getKey());
            rec.set(outputColumn, item.getValue() / total);
            collector.collect(rec);
        });
        dict.clear();
    }

}
