package io.github.javaasasecondlanguage.homework01.mergeintervals;

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
        IntervalsSorter.sort(intervals);
        if (intervals.length == 0) {
            return intervals;
        }
        int newSize = 1;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[newSize - 1][1] >= intervals[i][0]
                    && intervals[newSize - 1][1] < intervals[i][1]) {
                intervals[newSize - 1][1] = intervals[i][1];
            } else if (intervals[newSize - 1][1] < intervals[i][0]) {
                intervals[newSize++] = intervals[i];
            }
        }
        int[][] mergedIntervals = new int[newSize][];
        for (int i = 0; i < newSize; i++) {
            mergedIntervals[i] = intervals[i].clone();
        }
        return mergedIntervals;
    }
}
