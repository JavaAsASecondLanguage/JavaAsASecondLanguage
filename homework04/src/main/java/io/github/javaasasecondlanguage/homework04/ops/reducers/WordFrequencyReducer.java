package io.github.javaasasecondlanguage.homework04.ops.reducers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.*;

/**
 * Calculate frequency of values in column for each group.
 */
public class WordFrequencyReducer implements Reducer {

    private final String termColumn;
    private final String outputColumn;
    private Map<String, Integer> counts;
    private int totalCount;

    public WordFrequencyReducer(String termColumn, String outputColumn) {
        this.termColumn = termColumn;
        this.outputColumn = outputColumn;
        counts = new HashMap<>();
        totalCount = 0;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        counts.compute(inputRecord.getString(termColumn), (word, count) -> {
            if (count == null) {
                return 1;
            }
            return count + 1;
        });
        ++totalCount;
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        List<String> words = new ArrayList<>(counts.keySet());
        words.sort(String::compareTo);
        for (String word : words) {
            Record newRecord = new Record(groupByEntries);
            newRecord.set(termColumn, word);
            newRecord.set(outputColumn, ((double)counts.get(word)) / totalCount);
            collector.collect(newRecord);
        }
        counts.clear();
        totalCount = 0;
    }

}
