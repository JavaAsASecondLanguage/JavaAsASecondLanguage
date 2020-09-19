package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class IntervalsMerger {
    /**
     * Given array of intervals, merge overlapping intervals and sort them by
     * start in ascending order
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
        if (null == intervals) {
            throw new IllegalArgumentException("intervals are null");
        }

        if (intervals.length <= 1) {
            return intervals;
        }

        Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));
        int left = intervals[0][0];
        int right = intervals[0][1];
        ArrayList<int[]> mergedIntervals = new ArrayList<int[]>();
        for (int i = 1; i < intervals.length; ++i) {
            int curLeft = intervals[i][0];
            int curRight = intervals[i][1];
            if (curLeft <= right) {
                right = Math.max(right, curRight);
                continue;
            }

            mergedIntervals.add(new int[]{left, right});
            left = curLeft;
            right = curRight;
        }

        mergedIntervals.add(new int[]{left, right});

        return mergedIntervals.toArray(new int[mergedIntervals.size()][]);
    }
}
