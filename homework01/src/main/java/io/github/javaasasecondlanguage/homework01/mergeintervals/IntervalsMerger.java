package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        if (intervals == null) {
            throw new IllegalArgumentException();
        }
        List<int[]> ans = new ArrayList<>();
        boolean added = false;

        for (int[] x : intervals) {
            if (ans.isEmpty()) {
                ans.add(x);
            } else {
                for (int i = 0; i < ans.size(); i++) {
                    if ((ans.get(i)[0] <= x[0] && ans.get(i)[1] >= x[0]
                            || ans.get(i)[0] <= x[1] && ans.get(i)[1] >= x[1])
                            || (ans.get(i)[0] >= x[0] && ans.get(i)[1] <= x[1])) {
                        ans.set(i, new int[]
                                {Math.min(ans.get(i)[0], x[0]), Math.max(ans.get(i)[1], x[1])});
                        added = true;
                    }
                }
                if (added == false) {
                    ans.add(x);
                }
            }
            added = false;
        }

        ans.sort(Comparator.comparing(a -> a[1]));

        int[][] arrayans = new int[ans.size()][2];
        arrayans = ans.toArray(arrayans);

        return arrayans;
    }
}
