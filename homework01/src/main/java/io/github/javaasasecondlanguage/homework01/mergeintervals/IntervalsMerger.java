package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.ArrayList;
import java.util.*;


class Interval
{
    private int start;
    private int end;

    Interval() {
        start = 0;
        end = 0;
    }

    Interval(int s, int e)
    {
        start = s;
        end = e;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

class IntervalComparator implements Comparator<Interval>
{
    public int compare(Interval i1, Interval i2)
    {
        return i1.getStart() / 2 - i2.getStart() / 2;
    }
}

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
        if (intervals == null) {
            throw new IllegalArgumentException();
        }

        ArrayList<Interval> x = new ArrayList<>();


        for (int i=0; i < intervals.length; i++) {
            x.add(new Interval(intervals[i][0], intervals[i][1]));
        }

        if(x.size() == 0 || x.size() == 1)
            return intervals;

        Collections.sort(x, new IntervalComparator());

        Interval first = x.get(0);
        int start = first.getStart();
        int end = first.getEnd();

        ArrayList<Interval> result = new ArrayList<Interval>();

        for (int i = 1; i < x.size(); i++) {
            Interval current = x.get(i);
            if (current.getStart() <= end) {
                end = Math.max(current.getEnd(), end);
            } else {
                result.add(new Interval(start, end));
                start = current.getStart();
                end = current.getEnd();
            }
        }
        result.add(new Interval(start, end));

        int j = 0;
        var resultArray = new int[result.size()][2];

        for (Interval i : result) {
            resultArray[j][0] = i.getStart();
            resultArray[j][1] = i.getEnd();
            j++;
        }
        return resultArray;
    }
}
