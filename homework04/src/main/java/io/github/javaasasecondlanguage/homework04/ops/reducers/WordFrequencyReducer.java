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
    private int wordsProcessed = 0;
    private HashMap<String, Integer> wordCounts = new HashMap<>();
    private final String termColumn;
    private final String outputColumn;

    public WordFrequencyReducer(String termColumn, String outputColumn) {
        this.termColumn = termColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        String curWord = inputRecord.getString(this.termColumn);
        if (this.wordCounts.containsKey(curWord)) {
            this.wordCounts.put(curWord, this.wordCounts.get(curWord) + 1);
        } else {
            this.wordCounts.put(curWord, 1);
        }
        this.wordsProcessed++;
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        for (String word : this.wordCounts.keySet().stream().sorted().collect(Collectors.toList())) {
            Record outputRecord = new Record(groupByEntries);
            outputRecord.set(this.termColumn, word);
            outputRecord.set(this.outputColumn, Double.valueOf(this.wordCounts.get(word)) /
                    Double.valueOf(this.wordsProcessed));
            collector.collect(outputRecord);
        }

        this.wordsProcessed = 0;
        this.wordCounts = new HashMap<>();
    }

}
