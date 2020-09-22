package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.Arrays;
import java.util.Comparator;

public class IntervalsMerger {
    /**
     * Given array of intervals, merge overlapping intervals and sort
     * them by start in ascending order
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
        checkArgumentThrowException(intervals);
        sortIntervals(intervals);
        int removedElements = mergeIntervals(intervals);
        return createFilteredArray(intervals, removedElements);
    }

    private void checkArgumentThrowException(int[][] intervals) {
        if (intervals == null) {
            throw new IllegalArgumentException("Passed null intervals!");
        }
    }

    private void sortIntervals(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] > o2[0]) {
                    return 1;
                } else if (o1[0] < o2[0]) {
                    return -1;
                } else if (o1[1] > o2[1]) {
                    return 1;
                } else if (o1[1] < o2[1]) {
                    return -1;
                }
                return 0;
            }
        });
    }

    private int mergeIntervals(int[][] intervals) {
        int removedElements = 0;
        for (int i = 0; i < intervals.length - 1; i++) {
            if (intervals[i][0] <= intervals[i + 1][0] && intervals[i][1] >= intervals[i + 1][0]) {
                intervals[i + 1][0] = Math.min(intervals[i][0], intervals[i + 1][0]);
                intervals[i + 1][1] = Math.max(intervals[i][1], intervals[i + 1][1]);
                intervals[i] = null;
                removedElements++;
            }
        }
        return removedElements;
    }

    private int[][] createFilteredArray(int[][] intervals, int removedElements) {
        int[][] newIntervals = new int[intervals.length - removedElements][];
        for (int i = 0, j = 0; i < intervals.length; ++i) {
            if (intervals[i] != null) {
                newIntervals[j++] = intervals[i];
            }
        }
        return newIntervals;
    }


}
