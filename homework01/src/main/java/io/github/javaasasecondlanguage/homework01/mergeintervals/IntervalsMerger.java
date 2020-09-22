package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.Comparator;

public class IntervalsMerger {
    /**
     * Given array of intervals, merge overlapping intervals
     * and sort them by start in ascending order
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
            throw new IllegalArgumentException();
        }

        if (intervals.length == 0) {
            return new int[0][2];
        }
        java.util.Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        int currentStart = intervals[0][0];
        int currentEnd = intervals[0][1];
        int[][] result = new int[intervals.length][2];
        int currentPosition = 0;
        for (int[] pair : intervals) {
            int start = pair[0];
            int end = pair[1];
            if (start <= currentEnd) {
                currentEnd = Math.max(currentEnd, end);
            } else {
                result[currentPosition][0] = currentStart;
                result[currentPosition][1] = currentEnd;
                currentStart = start;
                currentEnd = end;
                currentPosition++;
            }
        }
        result[currentPosition][0] = currentStart;
        result[currentPosition][1] = currentEnd;

        int[][] trueResult = new int[currentPosition + 1][2];
        int[] pair;
        for (int i = 0; i <= currentPosition; i++) {
            pair = result[i];
            trueResult[i][0] = pair[0];
            trueResult[i][1] = pair[1];
        }

        return trueResult;
    }
}
