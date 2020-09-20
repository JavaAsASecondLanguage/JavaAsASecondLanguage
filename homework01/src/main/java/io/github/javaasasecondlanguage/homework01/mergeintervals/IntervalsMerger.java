package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.Arrays;
import java.util.Comparator;

public class IntervalsMerger {

    public static int[][] append(int[][] arr, int[] elem) {
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = elem;
        return arr;
    }

    public static int[][] popFront(int[][] arr) {
        return Arrays.copyOfRange(arr, 1, arr.length);
    }


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
        // check intevals is null
        if (intervals == null) {
            throw new IllegalArgumentException();
        }

        //check intervals is empty - already merged
        if (intervals.length == 0) {
            return intervals;
        }

        int[][] merged = new int[][]{};

        // sort intervals by 1st element in tuple
        Arrays.sort(intervals, Comparator.comparing(o -> o[0]));

        // fill merged array
        int i = 0;
        merged = append(merged, intervals[0]);
        intervals = popFront(intervals);
        while (intervals.length > 0) {
            if (merged[i][1] >= intervals[0][1]) {
                intervals = popFront(intervals);
            } else {
                if (merged[i][1] >= intervals[0][0]) {
                    merged[i][1] = intervals[0][1];
                    intervals = popFront(intervals);
                } else {
                    merged = append(merged, intervals[0]);
                    intervals = popFront(intervals);
                    i++;
                }
            }
        }
        return merged;
    }
}
