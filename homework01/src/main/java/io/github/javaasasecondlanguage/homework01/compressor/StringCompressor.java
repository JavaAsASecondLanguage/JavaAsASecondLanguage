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
    public static char charValidated(char s) {
        if ((s >= 'a') && (s <= 'z')) {
            return s;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static char[] compress(char[] str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        if (str.length == 0) {
            return str;
        }
        String s = "";
        int curCounter = 1;
        char prev = charValidated(str[0]);
        for(int i = 1; i < str.length; i++) {
            char cur = charValidated(str[i]);
            if (cur == prev) {
                curCounter += 1;
            } else {
                s += prev;
                s += (curCounter == 1) ? "" : curCounter;
                prev = cur;
                curCounter = 1;
            }
        }
        s += prev;
        s += (curCounter == 1) ? "" : curCounter;
        return s.toCharArray();
    }
}
