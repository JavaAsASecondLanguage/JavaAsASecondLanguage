package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.max;

public class IntervalsMerger {
    /**
     * Given array of intervals,
     * merge overlapping intervals and sort them by start in ascending order
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

        if (intervals.length <= 1) {
            return intervals;
        }

        List<int[]> inputList = Arrays.asList(intervals);

        // Sort the list of arrays
        Collections.sort(inputList, new Comparator<int[]>() {
            public int compare(final int[] o1, final int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });

        // init result list
        List<int[]> result = new ArrayList<int[]>();
        result.add(inputList.get(0));

        for (int i = 1; i < inputList.size(); i++) {
            if (result.get(result.size() - 1)[1] >= inputList.get(i)[0]) {
                result.get(result.size() - 1)[1] = max(
                        inputList.get(i)[1],
                        result.get(result.size() - 1)[1]);
            } else {
                result.add(inputList.get(i));
            }
        }

        int[][] resultInt = new int[0][0];
        return result.toArray(resultInt);
    }
}
