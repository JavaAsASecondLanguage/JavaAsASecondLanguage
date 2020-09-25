package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.ArrayList;
import java.util.Arrays;

public class IntervalsMerger {
    /**
     * Given array of intervals, merge overlapping intervals and sort by start in ascending order
     * Interval is defined as [start, end] where start < end
     *
     * <p>Examples: [[1,3][2,4][5,6]] -> [[1,4][5,6]] [[1,2][2,3]] -> [[1,3]] [[1,4][2,3]] -> [[1,4]]
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
        if (intervals.length < 2) {
            return intervals;
        }
        Arrays.sort(
                intervals,
                (t1, t2) -> {
                    int result = Integer.compare(t1[0], t2[0]);
                    if (result == 0) {
                        return Integer.compare(t1[1], t2[1]);
                    }
                    return result;
                });
        ArrayList<int[]> result = new ArrayList<>();
        result.add(intervals[0]);
        int current_res_interval = 0;
        for (int i = 1; i < intervals.length; ++i) {
            if (intervals[i][0] <= result.get(current_res_interval)[1]) {
                if (intervals[i][1] > result.get(current_res_interval)[1]) {
                    result.get(current_res_interval)[1] = intervals[i][1];
                }
            } else {
                result.add(intervals[i]);
            }
        }
        int[][] temp = new int[result.size()][2];
        return result.toArray(temp);
    }
}
