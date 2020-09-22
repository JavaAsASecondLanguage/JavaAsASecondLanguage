package io.github.javaasasecondlanguage.homework01.compressor;

public class StringCompressor {
    /**
     * Given an array of characters, compress it using the following algorithm:
     * <p>
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
            throw new IllegalArgumentException();
        }
        for (char c : str) {
            if (!(c >= 'a' && c <= 'z')) {
                throw new IllegalArgumentException();
            }
        }
        if (str.length == 0) {
            return new char[]{};
        }
        char prev = '-';
        int index = 1;
        String result = "";
        for (char c : str) {
            if (prev == c) {
                index++;
            } else {
                if (index != 1) {
                    result = result + prev + String.valueOf(index);
                } else {
                    if (prev != '-') {
                        result = result + prev;
                    }
                }
                index = 1;

            }
            prev = c;
        }
        if (index != 1) {
            result = result + prev + String.valueOf(index);
        } else {
            result = result + prev;
        }
        return result.toCharArray();
    }
}
