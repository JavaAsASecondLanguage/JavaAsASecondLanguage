package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.*;

public class IntervalsMerger {
    public class IntervalComparator implements Comparator<int[]> {
        @Override
        public int compare(int[] o1, int[] o2) {
            if (o1[0] < o2[0]) {
                return -1;
            } else if (o1[0] > o2[0]) {
                return 1;
            } else {
                return 0;
            }
        }
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
        if (intervals == null) {
            throw new IllegalArgumentException();
        }

        List<int[]> res = new ArrayList<int[]>(Arrays.asList(intervals));
        Collections.sort(res, new IntervalComparator());

        int i = 0;

        while (i < res.size() - 1) {
            if (overlaps(res.get(i), res.get(i + 1))) {
                res.set(i, mergeCouple(res.get(i), res.get(i + 1)));
                res.remove(i + 1);
            } else {
                i += 1;
            }
        }

        return res.toArray(new int[][]{});
    }

    private boolean overlaps(int[] interval1, int[] interval2) {
        var one = interval1[1] >= interval2[0] && interval1[1] <= interval2[1];
        var two = interval2[1] >= interval1[0] && interval2[1] <= interval1[1];

        return one || two;
    }

    private int[] mergeCouple(int[] interval1, int[] interval2) {
        return new int[] {
            Math.min(interval1[0], interval2[0]),
            Math.max(interval1[1], interval2[1])
        };
    }
}
