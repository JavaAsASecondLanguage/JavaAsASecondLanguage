package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.TreeMap;

public class IntervalsMerger {
    /**
     * Given array of intervals, merge overlapping intervals 
     * and sort them by start in ascending order.
     * Interval is defined as [start, end] where start < end.
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
            throw new IllegalArgumentException("intervals is null");
        }
        
        var treeMap = new TreeMap<Integer, Integer>();

        for (var currentInterval : intervals) {
            Integer currentStart = currentInterval[0];
            Integer currentEnd = currentInterval[1];

            var floorInterval = treeMap.floorEntry(currentStart);
            if (floorInterval == null) {
                // current is most to the left
                treeMap.put(currentStart, currentEnd);

            } else {
                Integer floorStart = floorInterval.getKey();
                Integer floorEnd = floorInterval.getValue();
                if (floorEnd < currentStart)  {
                    // floor not overlaps, just insert current
                    treeMap.put(currentStart, currentEnd);

                } else {
                    // floor overlaps, just update ends
                    currentStart = floorStart;
                    currentEnd = Integer.max(floorEnd, currentEnd);
                    treeMap.replace(currentStart, currentEnd);

                }
            }
            // merge overlaped to the right
            mergeOverlapped(treeMap, currentStart, currentEnd);

        }
        return populateIntervals(treeMap);
    }
    
    static void mergeOverlapped(TreeMap<Integer, Integer> treeMap, Integer start, Integer end) {
        var nextKey = treeMap.higherKey(start);

        while (nextKey != null && nextKey <= end) {
            var nextValue = treeMap.get(nextKey);

            treeMap.remove(nextKey);
            treeMap.replace(start, Integer.max(nextValue, end));
            
            nextKey = treeMap.higherKey(nextValue);
        }
    }

    static int[][] populateIntervals(TreeMap<Integer, Integer> treeMap) {
        var res = new int[treeMap.keySet().size()][];
        var i = 0;
        for (var entry : treeMap.entrySet()) {
            res[i++] = new int[] { entry.getKey(), entry.getValue() };
        }
        return res;
    }
}
