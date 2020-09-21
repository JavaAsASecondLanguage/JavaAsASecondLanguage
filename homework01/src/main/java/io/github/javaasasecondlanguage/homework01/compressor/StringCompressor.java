package io.github.javaasasecondlanguage.homework01.compressor;

import java.nio.CharBuffer;

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
            throw new IllegalArgumentException("str is null");
        }

        if (str.length == 0) {
            return new char[0];
        }

        int count = 0;
        char last = str[0];
        var sb = new StringBuilder();
        
        for (var ch : str) {
            if (ch < 'a' || ch > 'z') {
                throw new IllegalArgumentException("invalid char");
            }

            if (last == ch) {
                ++count;
            } else {
                addLast(sb, last, count);

                last = ch;
                count = 1;
            }
        }
        addLast(sb, last, count);

        return sb.toString().toCharArray();
    }

    static void addLast(StringBuilder buffer, char last, int count) {
        buffer.append(last);
        if (count > 1) {
            buffer.append(count);
        }
    }
}
