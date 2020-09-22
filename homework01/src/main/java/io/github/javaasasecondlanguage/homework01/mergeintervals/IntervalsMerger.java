package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class IntervalsMerger {
    /**
     * Given array of intervals, merge overlapping intervals and sort them by start
     * in ascending order. Interval is defined as [start, end] where start < end
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
            throw new IllegalArgumentException("Argument cannot be null");
        }
        if (intervals.length < 2) {
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[0]));
        var stack = new Stack<int[]>();
        stack.push(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            var top = stack.peek();
            if (top[1] < intervals[i][0]) {
                stack.push(intervals[i]);
            } else if (top[1] < intervals[i][1]) {
                top[1] = intervals[i][1];
                stack.pop();
                stack.push(top);
            }
        }
        return stack.toArray(new int[0][0]);
    }
}