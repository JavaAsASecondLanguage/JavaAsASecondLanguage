package io.github.javaasasecondlanguage.homework01.compressor;

import java.util.Random;

/**
 * Sorter of integer intervals by their left margin
 * or by right margin in case of equal left margins.
 * Using Quick sort algorithm.
 */
public class IntervalsSorter {

    private static final Random RANDOM = new Random();

    private static int split(int[][] array, int offset, int length, int[] center) {
        int threshold = offset;
        for (int i = offset; i < offset + length; i++) {
            int compare = compare(array[i], center);
            if (compare <=  0) {
                swap(array, i, threshold);
                if (compare < 0) {
                    threshold++;
                }
            }
        }
        return threshold;
    }

    private static void swap(int[][] array, int pos1, int pos2) {
        int[] temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

    private static int compare(int[] interval1, int[] interval2) {
        long result = (long) interval1[0] - interval2[0];
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return +1;
        }
        result = (long) interval1[1] - interval2[1];
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return +1;
        }
        return 0;
    }

    private static void sort(int[][] array, int offset, int length) {
        if (length < 2) {
            return;
        }
        int[] median = array[offset + RANDOM.nextInt(length)];
        int leftThreshold = split(array, offset, length, median);
        int rightThreshold = leftThreshold;
        while ((rightThreshold < offset + length)
                && (compare(array[rightThreshold], median) == 0)) {
            rightThreshold++;
        }
        sort(array, offset, leftThreshold - offset);
        sort(array, rightThreshold, length - rightThreshold);
    }

    public static void sort(int[][] array) {
        if (array == null) {
            throw new IllegalArgumentException("null array");
        }
        sort(array, 0, array.length);
    }
}
