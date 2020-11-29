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

    HashMap<String, Double> wordCounter = new HashMap<>();
    int totalCounter = 0;

    public WordFrequencyReducer(String termColumn, String outputColumn) {
        this.termColumn = termColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        String term = inputRecord.get(termColumn).toString();
        wordCounter.merge(term, 1.0, (x, y) -> x + y);
        totalCounter++;
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        SortedSet<String> keys = new TreeSet<>(wordCounter.keySet());
        for (String term: keys ) {
            collector.collect(new Record(groupByEntries)
                    .set(termColumn, term)
                    .set(termColumn, term)
                    .set(outputColumn, wordCounter.get(term) / totalCounter));
        }
        totalCounter = 0;
        wordCounter.clear();
    }

}
