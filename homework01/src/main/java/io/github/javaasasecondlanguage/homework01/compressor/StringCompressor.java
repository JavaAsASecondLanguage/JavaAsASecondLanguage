package io.github.javaasasecondlanguage.homework01.compressor;

import java.util.Arrays;

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
            throw new IllegalArgumentException();
        }
        for (char c : str) {
            if (c < 'a' || c > 'z') {
                throw new IllegalArgumentException();
            }
        }
        int anchor = 0;
        int write = 0;
        for (int read = 0; read < str.length; read++) {
            if (read + 1 == str.length || str[read + 1] != str[read]) {
                str[write++] = str[anchor];
                if (read > anchor) {
                    for (char c : ("" + (read - anchor + 1)).toCharArray()) {
                        str[write++] = c;
                    }
                }
                anchor = read + 1;
            }
        }
        return Arrays.copyOf(str, write);
    }
}
