package io.github.javaasasecondlanguage.homework04.ops.reducers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Calculate frequency of values in column for each group.
 */
public class WordFrequencyReducer implements Reducer {
    private String termColumn;
    private String outputColumn;
    private final Map<String, Integer> wordsCounter = new HashMap<>();
    private Integer counter = 0;

    public WordFrequencyReducer(String termColumn, String outputColumn) {
        this.termColumn = termColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        var value = inputRecord.getString(this.termColumn);

        if (wordsCounter.containsKey(value)) {
            wordsCounter.put(value, wordsCounter.get(value) + 1);
        } else {
            wordsCounter.put(value, 1);
        }

        counter += 1;
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        var items = wordsCounter.entrySet()
                .stream()
                .sorted((p1, p2) -> p1.getKey().compareTo(p2.getKey()))
                .collect(Collectors.toList());

        for (var pair : items) {
            var newRecord = new Record(groupByEntries);
            newRecord.set(this.termColumn, pair.getKey());
            newRecord.set(this.outputColumn, 1.0 * pair.getValue() / counter);
            collector.collect(newRecord);
        }

        wordsCounter.clear();
        counter = 0;
    }
}
