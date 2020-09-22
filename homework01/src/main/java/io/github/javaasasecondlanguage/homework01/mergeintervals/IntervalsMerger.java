package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public int[][] merge(int[][] intervals) {

    	if (intervals == null)
    		throw new IllegalArgumentException("intervals is null");

        List<int[]> listOfPairs = new ArrayList<>();
        
        if ( intervals.length > 0) {
        	
        	Arrays.sort(intervals, (p1, p2) -> p1[0] >= p2[0] ? 1 : -1);        	

            var last = testPair(intervals[0]);
        	listOfPairs.add(last);
        	
            for (int i = 1 ; i < intervals.length; i++) {
            	var current = testPair(intervals[i]);
            	if (last[0] == current[0] || last[1] >= current[0]){
            		if  (last[1] < current[1])
                		last[1] = current[1];
            	} else {
            		last = testPair(intervals[i]);
            		listOfPairs.add(last);
            	}
            }
            
        }
    	
        int[][] out = new int[listOfPairs.size()][2];
        for (int i = 0; i < listOfPairs.size(); i++) {
        	out[i][0] = listOfPairs.get(i)[0];
        	out[i][1] = listOfPairs.get(i)[1];
        }
        
    	return out;
    }
    
    public int[] testPair(int[] pair) {
    	
    	if (pair == null || pair[0] > pair[1])
    		throw new IllegalArgumentException("intervals is null or wrong pair");
    	
    	return pair;
    }
}
