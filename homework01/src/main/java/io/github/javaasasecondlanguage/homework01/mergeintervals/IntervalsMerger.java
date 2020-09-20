package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.ArrayList;
import java.util.Arrays;

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
    public int[][] merge(int[][] intervals) throws IllegalAccessException {
        if(intervals == null) {
            throw new IllegalArgumentException();
        }

        if(intervals.length == 0 || intervals.length == 1) {
            return intervals;
        }

        ArrayList<int[]> intervalsList = new ArrayList<int[]>(Arrays.asList(intervals));
        ArrayList<int[]> resultList = new ArrayList<int[]>();

        intervalsList.sort(this::comparePair);

        int leftNumber = intervalsList.get(0)[0], rightNumber = intervalsList.get(0)[1];

        for(int i = 1; i < intervalsList.size(); i++) {
            if(intervalsList.get(i)[0] > rightNumber) {
                resultList.add(new int[]{leftNumber, rightNumber});
                leftNumber = intervalsList.get(i)[0];
                rightNumber = intervalsList.get(i)[1];
            }
            else {
                rightNumber = Math.max(intervalsList.get(i)[1], rightNumber);
            }
        }

        resultList.add(new int[]{leftNumber, rightNumber});
        int[][] temp = new int[resultList.size()][2];
        return resultList.toArray(temp);
    }

    public int comparePair (int[] left, int[] right) {
        int result = Integer.compare(left[0], right[0]);
        if(result == 0) {
            result = Integer.compare(left[1], right[1]);
        }

        return result;
    }
}
