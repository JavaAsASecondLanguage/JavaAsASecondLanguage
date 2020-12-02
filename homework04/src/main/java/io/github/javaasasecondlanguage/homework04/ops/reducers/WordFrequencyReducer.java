package io.github.javaasasecondlanguage.homework04.ops.reducers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Calculate frequency of values in column for each group.
 */
public class WordFrequencyReducer implements Reducer {

    private final String termColumn;
    private final String outputColumn;
    private Map<String, Long> termToCount = new LinkedHashMap<>();
    private long totalCount = 0;

    public WordFrequencyReducer(String termColumn, String outputColumn) {
        this.termColumn = termColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        termToCount.merge(inputRecord.getString(termColumn), 1L, Long::sum);
        totalCount++;
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        termToCount.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(entry -> mapStatToRecord(entry.getKey(), entry.getValue(), groupByEntries))
            .forEach(collector::collect);
        termToCount = new HashMap<>();
        totalCount = 0;
    }

    private Record mapStatToRecord(String word, Long count, Map<String, Object> groupByEntries) {
        var record = new Record(groupByEntries);
        record.set(termColumn, word);
        record.set(outputColumn, (double) count / totalCount);
        return record;
    }
}
