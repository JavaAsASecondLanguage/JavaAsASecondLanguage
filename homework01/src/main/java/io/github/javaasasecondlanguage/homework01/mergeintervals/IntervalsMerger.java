package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class IntervalsMerger {
    /**
     * Given array of intervals, merge overlapping intervals and
     * sort them by start in ascending order.
     * Interval is defined as [start, end] where start < end.
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
            throw new IllegalArgumentException("Input argument is null!");
        }
        if (intervals.length <= 1) {
            return intervals;
        }
        var merged = new ArrayList<int[]>();
        Arrays.sort(intervals, Comparator.comparingInt(x -> x[0]));
        var currentLeft = intervals[0][0];
        var currentRight = intervals[0][1];
        for (var i = 1; i < intervals.length; i++) {
            var left = intervals[i][0];
            var right = intervals[i][1];
            if (left <= currentRight) {
                currentRight = Math.max(currentRight, right);
            } else {
                merged.add(new int[]{currentLeft, currentRight});
                currentLeft = left;
                currentRight = right;
            }
        }
        merged.add(new int[]{currentLeft, currentRight});
        return merged.toArray(new int[merged.size()][]);
    }
}
