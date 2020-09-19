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
        if (null == str) {
            throw new IllegalArgumentException("str is null");
        }
        if (str.length == 0) {
            return str;
        }

        String compressedStr = "";
        int cnt = 1;
        char prevCh = str[0];
        for (int i = 1; i < str.length; ++i) {
            char ch = str[i];
            if (' ' == ch) {
                throw new IllegalArgumentException("` ` in string");
            }

            if (ch == prevCh) {
                ++cnt;
                continue;
            }

            if (cnt > 1) {
                compressedStr = compressedStr + prevCh + String.valueOf(cnt);
            } else {
                compressedStr = compressedStr + prevCh;
            }

            cnt = 1;
            prevCh = ch;
        }

        if (cnt > 1) {
            compressedStr = compressedStr + prevCh + String.valueOf(cnt);
        } else {
            compressedStr = compressedStr + prevCh;
        }

        return compressedStr.toCharArray();
    }
}
