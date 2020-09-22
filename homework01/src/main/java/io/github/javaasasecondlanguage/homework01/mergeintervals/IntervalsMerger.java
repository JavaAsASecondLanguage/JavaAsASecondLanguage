package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.*;

public class IntervalsMerger {
    /**
     * Given array of intervals, merges overlapping intervals and sort by start
     * in ascending order
     * Interval is defined as [start,end] where start < end
     *
     * Examples:
     * [[1,3][2,4][5,6]] -> [[1,4][5,6]]
     * [[1,2][2,3]] -> [[1,3]]
     * [[1,4][2,3]] -> [[1,4]]
     * [[5,6][1,2]] -> [[1,2][5,6]]
     *
     * @param intervals nullable array of pairs [start, end]
     * @return merged intervals
     * @throws IllegalArgumentException if intervals is null
     */
    public int[][] merge(int[][] intervals) {
        // check if intervals array equals null
        if (intervals == null) {
            throw new IllegalArgumentException();
        }
        Collections.sort(Arrays.asList(intervals), new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0], b[0]);
            }
        });

        LinkedList<int[]> merged = new LinkedList<>();
        for (int[] interval : intervals) {

            if (merged.isEmpty() || merged.getLast()[1] < interval[0]) {
                merged.add(interval);
            } else {
                merged.getLast()[1] = Math.max(merged.getLast()[1], interval[1]);
            }
        }

        return merged.toArray(new int[merged.size()][]);
    }

}
