package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class IntervalsMerger {
    private static class PairsComparator implements Comparator<int[]> {
        @Override
        public int compare(int[] p1, int[] p2) {
            if (p1[0] > p2[0]) {
                return 1;
            } else if (p1[0] < p2[0]) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Given array of intervals, merge overlapping intervals and
     * sort them by start in ascending order
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
    public int[][] merge(int[][] intervals) throws IllegalArgumentException {
        if (intervals == null) {
            throw new IllegalArgumentException();
        }

        Arrays.sort(intervals, new PairsComparator());

        ArrayList<int[]> returnIntervals = new ArrayList<int[]>();
        for (int i = 0; i < intervals.length; i++) {
            if (returnIntervals.size() == 0) {
                returnIntervals.add(intervals[i]);
            } else if (intervals[i][0] <= returnIntervals.get(returnIntervals.size() - 1)[1]) {
                if (returnIntervals.get(returnIntervals.size() - 1)[1] < intervals[i][1]) {
                    returnIntervals.get(returnIntervals.size() - 1)[1] = intervals[i][1];
                }
            } else {
                returnIntervals.add(intervals[i]);
            }
        }

        int[][] returnIntervalsArray = new int[returnIntervals.size()][2];
        returnIntervals.toArray(returnIntervalsArray);
        return returnIntervalsArray;
    }
}
