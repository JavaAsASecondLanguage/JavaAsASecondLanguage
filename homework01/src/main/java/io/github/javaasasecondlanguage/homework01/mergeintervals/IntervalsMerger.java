package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class IntervalsMerger {
    /**
     * Given array of intervals, merge overlapping intervals and sort them by start in ascending order
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

        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });

        ArrayList<int[]> l = new ArrayList<>();

        for (var interval : intervals) {
            if (l.isEmpty()) {
                l.add(interval);
            } else {
                if (interval[0] <= l.get(l.size() - 1)[1]) {
                    l.set(l.size()-1, new int[]{l.get(l.size() - 1)[0],
                            Math.max(l.get(l.size() - 1)[1], interval[1])});
                } else {
                    l.add(interval);
                }
            }
        }
        return l.toArray(new int[0][0]);
    }
}
