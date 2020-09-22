package io.github.javaasasecondlanguage.homework01.compressor;

public class StringCompressor {
    /**
     * Given an array of characters, compress it using the following algorithm:
     *
     * Begin with an empty string s.
     * For each group of consecutive repeating characters in chars:
     * If the group's length is 1, append the character to s.
     * Otherwise, append the character followed by the group's length.
     * Return a compressed string.
     * </p>
     * Follow up:
     * Could you solve it using only O(1) extra space?
     * </p>
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
        if (str == null) {
            throw new IllegalArgumentException("String was null");
        }
        for (char c : str) {
            if ((c < 'a') || (c > 'z')) {
                throw new IllegalArgumentException("String contains illegal character: " + c);
            }
        }
        char[] result = new char[str.length];
        int newLength = str.length;
        if (str.length == 0) {
            return new char[] {};
        }

        char currentChar = str[0];
        int currentCount = 0;
        int currentPosition = 0;
        for (char c : str) {
            if (c == currentChar) {
                currentCount += 1;
            } else {
                result[currentPosition] = currentChar;
                currentPosition += 1;
                if (currentCount > 1) {
                    result[currentPosition] = (char) (currentCount + '0');
                    currentPosition += 1;
                    if (currentCount > 2) {
                        newLength -= currentCount - 2;
                    }
                }
                currentChar = c;
                currentCount = 1;
            }
        }
        result[currentPosition] = currentChar;
        currentPosition += 1;
        if (currentCount > 1) {
            result[currentPosition] = (char) (currentCount + '0');
            if (currentCount > 2) {
                newLength -= currentCount - 2;
            }
        }

        char[] result2 = new char[newLength];
        if (newLength >= 0) {
            System.arraycopy(result, 0, result2, 0, newLength);
        }

        return result2;
    }
}
