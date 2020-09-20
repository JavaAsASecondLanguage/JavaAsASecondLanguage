package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.Arrays;
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
            throw new IllegalArgumentException("intervals is null");
        }
        int[][] copiedIntervals = new int[intervals.length][];
        if (intervals.length == 0) {
            return copiedIntervals;
        }
        for (int i = 0; i < intervals.length; i++) {
            copiedIntervals[i] = new int[]{intervals[i][0], intervals[i][1]};
        }
        Arrays.sort(copiedIntervals, Comparator.comparing(x -> x[0]));
        int[][] res = new int[intervals.length][];
        int currentPos = 0;
        int currentRightBound = copiedIntervals[currentPos][1];
        res[currentPos] = new int[]{copiedIntervals[currentPos][0], copiedIntervals[currentPos][1]};
        for (var interval : copiedIntervals) {
            if (interval[0] <= currentRightBound) {
                currentRightBound = Math.max(currentRightBound, interval[1]);
                res[currentPos][1] = currentRightBound;
            } else {
                currentRightBound = interval[1];
                res[++currentPos] = new int[]{interval[0], interval[1]};
            }
        }
        return Arrays.copyOf(res, currentPos + 1);
    }
}
