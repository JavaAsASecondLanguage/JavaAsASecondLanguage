package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class IntervalsMerger {
    /**
     * Given array of intervals,
     * merge overlapping intervals and sort them by start in ascending order
     * Interval is defined as [start, end] where start < end
     * <p>
     * Examples:
     * [[1,3][2,4][5,6]] -> [[1,4][5,6]]
     * [[1,2][2,3]] -> [[1,3]]
     * [[1,4][2,3]] -> [[1,4]]
     * [[5,6][1,2]] -> [[1,2][5,6]]
     *
     * @param intervals is a nullable array of pairs [start, end]
     * @return merged intervals
     * @throws IllegalArgumentException if intervals is null
     */
    public int[][] merge(int[][] intervals) {
        if (intervals == null) {
            throw new IllegalArgumentException("intervals is null");
        }

        return Arrays.stream(intervals)
                .sorted(Comparator.comparingInt(a -> a[0]))
                .reduce(
                        new ArrayList<int[]>(),
                        (acc, interval) -> {
                            if (acc.size() == 0) {
                                acc.add(interval);
                                return acc;
                            }

                            var last = acc.get(acc.size() - 1);
                            var endOfLastInterval = last[1];
                            var start = interval[0];
                            var end = interval[1];

                            if (endOfLastInterval >= start) {
                                last[1] = Math.max(end, endOfLastInterval);
                            } else {
                                acc.add(interval);
                            }

                            return acc;
                        },
                        (oldAcc, acc) -> acc
                )
                .toArray(int[][]::new);
    }
}
