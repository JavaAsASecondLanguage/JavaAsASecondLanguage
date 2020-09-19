package io.github.javaasasecondlanguage.homework01.mergeintervals;

import io.github.javaasasecondlanguage.homework01.compressor.IntervalsSorter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IntervalsSorterTest {

    private static Stream<Arguments> provideTestArguments() {
        return Stream.of(
                Arguments.of(new int[][]{{1, 2}, {2, 3}}, new int[][]{{1, 2}, {2, 3}}),
                Arguments.of(new int[][]{{1, 2}, {1, 4}}, new int[][]{{1, 2}, {1, 4}}),
                Arguments.of(new int[][]{{1, 4}, {1, 2}}, new int[][]{{1, 2}, {1, 4}}),
                Arguments.of(new int[][]{{1, 2}, {3, 5}}, new int[][]{{1, 2}, {3, 5}}),
                Arguments.of(
                        new int[][]{{1, 4}, {-1, 2}, {0, 7}},
                        new int[][]{{-1, 2}, {0, 7}, {1, 4}}),
                Arguments.of(
                        new int[][]{{1, 4}, {-1, 2}, {1, 3}},
                        new int[][]{{-1, 2}, {1, 3}, {1, 4}}),
                Arguments.of(
                        new int[][]{
                                {Integer.MIN_VALUE, 0},
                                {0, Integer.MAX_VALUE}},
                        new int[][]{
                                {Integer.MIN_VALUE, 0},
                                {0, Integer.MAX_VALUE}}),
                Arguments.of(
                        new int[][]{
                                {Integer.MIN_VALUE, 0},
                                {0, Integer.MAX_VALUE},
                                {Integer.MIN_VALUE, 0}},
                        new int[][]{
                                {Integer.MIN_VALUE, 0},
                                {Integer.MIN_VALUE, 0},
                                {0, Integer.MAX_VALUE}})
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestArguments")
    void sort(int[][] subject, int[][] result) {
        int[][] subjectCopy = subject.clone();
        IntervalsSorter.sort(subjectCopy);
        assertTrue(Arrays.deepEquals(subjectCopy, result),
                "Result is " + Arrays.deepToString(subjectCopy)
                        + ", but " + Arrays.deepToString(result) + " was expected");
    }

    @Test
    void nullIsIllegalArgument() {
        assertThrows(
                IllegalArgumentException.class,
                () -> IntervalsSorter.sort(null)
        );
    }
}