
package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.ArrayList;
import java.util.List;

public class IntervalsMerger {
    /**
     * Given array of intervals, merge overlapping intervals and sort them
     * by start in ascending order
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

    public static class Interval {
        int start;
        int end;

        public Interval(int[] bounds) {
            this.start = bounds[0];
            this.end = bounds[1];
        }

        public int[] toArray() {
            return new int[]{this.start, this.end};
        }
    }

    public int[][] intervalsToArray(List<Interval> data) {
        int[][] arr = new int[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = data.get(i).toArray();
        }
        return arr;
    }

    public int[][] merge(int[][] intervals) {
        if (intervals == null) {
            throw new IllegalArgumentException();
        }
        if (intervals.length <= 1) {
            return intervals;
        }
        // 1. Get more comfortable data structure for further sorting
        List<Interval> sections = new ArrayList<>();
        for (int[] bounds : intervals) {
            sections.add(new Interval(bounds));
        }
        // 2. Sort intervals by left side
        sections.sort((o1, o2) -> {
            int intermediate = Integer.compare(o1.start, o2.start);
            if (intermediate != 0) {
                return intermediate;
            } else {
                return Integer.compare(o1.end, o2.end);
            }
        });
        // 3.Merge
        List<Interval> result = new ArrayList<>();
        Interval prev = sections.get(0);
        for (int i = 1; i < sections.size(); i++) {
            Interval current = sections.get(i);
            if (current.start <= prev.end) {
                prev.end = Math.max(current.end, prev.end);
            } else {
                result.add(prev);
                prev = current;
            }
        }
        result.add(prev);
        return intervalsToArray(result);
    }
}
