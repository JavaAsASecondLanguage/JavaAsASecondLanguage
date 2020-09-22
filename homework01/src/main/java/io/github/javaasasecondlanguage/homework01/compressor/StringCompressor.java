package io.github.javaasasecondlanguage.homework01.compressor;

import java.util.Arrays;

public class StringCompressor {
    /**
     * Given an array of characters, compress it using the following algorithm:
     * <p>
     * Begin with an empty string s.
     * For each group of consecutive repeating characters in chars:
     * If the group's length is 1, append the character to s.
     * Otherwise, append the character followed by the group's length.
     * Return a compressed string.
     * <p>
     * Follow up:
     * Could you solve it using only O(1) extra space?
     * <p>
     * Examples:
     * a -> a
     * aa -> a2
     * aaa -> a3
     * aaabb -> a3b2
     * "" -> ""
     * null -> Illegal argument
     * 234 sdf -> Illegal argument
     *
     * @param str nullable array of chars to compress
     *            str may contain illegal characters
     * @return a compressed array
     * @throws IllegalArgumentException if str is null
     * @throws IllegalArgumentException if any char is not in range 'a'..'z'
     */
    public char[] compress(char[] str) {
        checkNullityThrowException(str);
        if (str.length == 0) {
            return new char[]{};
        }
        byte count = 1;
        int writePosition = 1;
        checkLegalCharThrowExceptionIfFailed(str, 0);
        for (int i = 1; i < str.length; i++) {
            checkLegalCharThrowExceptionIfFailed(str, i);
            if (str[i] == str[i - 1]) {
                count++;
                if (i + 1 == str.length) {
                    str[writePosition++] = (char) (count + '0');
                }
            } else if (count > 1) {
                str[writePosition++] = (char) (count + '0');
                str[writePosition++] = str[i];
                count = 1;
            } else {
                str[writePosition++] = str[i];
            }
        }
        return Arrays.copyOfRange(str, 0, writePosition);
    }

    private void checkNullityThrowException(char[] str) {
        if (str == null) {
            throw new IllegalArgumentException("Passed illegal array!");
        }
    }

    private void checkLegalCharThrowExceptionIfFailed(char[] chr, int index) {
        if (index < chr.length && !checkLegalChars(chr[index])) {
            String errorMessage = String.format("Illegal character in array! [index = {0}]", index);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private boolean checkLegalChars(char str) {
        return str >= 'a' && str <= 'z';
    }
}
