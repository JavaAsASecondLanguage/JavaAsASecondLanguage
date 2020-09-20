package io.github.javaasasecondlanguage.homework01.mergeintervals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MergeIntervalsTest {
    IntervalsMerger intervalsMerger;

    @BeforeEach
    void setup() {
        intervalsMerger = new IntervalsMerger();
    }

    @Test
    void nullIsIllegalArgument() {
        assertThrows(
                IllegalArgumentException.class,
                () -> intervalsMerger.merge(null)
        );
    }

    @Test
    void emptyIntervalListIsAlreadyMerged() throws IllegalAccessException {
        var emptyIntervalArray = new int[][]{};
        var result = intervalsMerger.merge(emptyIntervalArray);
        assertArrayEquals(emptyIntervalArray, result);
    }

    @Test
    void sample1() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{1, 3},
                new int[]{2, 4},
                new int[]{5, 6}
        };
        var expected = new int[][]{
                new int[]{1, 4},
                new int[]{5, 6}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void sample2() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{1, 2},
                new int[]{2, 3}
        };
        var expected = new int[][]{
                new int[]{1, 3}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void sample3() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{1, 4},
                new int[]{2, 3}
        };
        var expected = new int[][]{
                new int[]{1, 4}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void sample4() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{5, 6},
                new int[]{1, 2}
        };
        var expected = new int[][]{
                new int[]{1, 2},
                new int[]{5, 6}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void sample5() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{1, 4},
                new int[]{2, 5}
        };
        var expected = new int[][]{
                new int[]{1, 5}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void sample6() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{1, 2},
                new int[]{2, 3}
        };
        var expected = new int[][]{
                new int[]{1, 3}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void sample7() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{2, 3},
                new int[]{1, 2}
        };
        var expected = new int[][]{
                new int[]{1, 3}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void sample8() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{5, 6},
                new int[]{1, 2}
        };
        var expected = new int[][]{
                new int[]{1, 2},
                new int[]{5, 6}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void sample9() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{3, 4},
                new int[]{5, 6},
                new int[]{1, 2}
        };
        var expected = new int[][]{
                new int[]{1, 2},
                new int[]{3, 4},
                new int[]{5, 6}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void sample10() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{1, 3},
                new int[]{2, 4},
                new int[]{1, 2}
        };
        var expected = new int[][]{
                new int[]{1, 4}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void sample11() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{5, 6},
                new int[]{1, 3},
                new int[]{2, 4}
        };
        var expected = new int[][]{
                new int[]{1, 4},
                new int[]{5, 6}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void sample12() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{2, 3},
                new int[]{3, 4},
                new int[]{1, 5}
        };
        var expected = new int[][]{
                new int[]{1, 5}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void sample13() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{Integer.MIN_VALUE, 0},
                new int[]{0, Integer.MAX_VALUE}
        };
        var expected = new int[][]{
                new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void duplicateIntervals1() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{1, 2},
                new int[]{1, 2}
        };
        var expected = new int[][]{
                new int[]{1, 2}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void duplicateIntervals2() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{1, 2},
                new int[]{3, 4},
                new int[]{1, 2}
        };
        var expected = new int[][]{
                new int[]{1, 2},
                new int[]{3, 4}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(expected, result);
    }

    @Test
    void singleIntervalIsSorted1() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{1, 3}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(intervals, result);
    }

    @Test
    void singleIntervalIsSorted2() throws IllegalAccessException {
        var intervals = new int[][]{
                new int[]{-3, 3}
        };
        var result = intervalsMerger.merge(intervals);
        assertArrayEquals(intervals, result);
    }
}