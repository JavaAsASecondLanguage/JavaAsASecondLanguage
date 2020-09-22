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
    private boolean isAlpha(char[] str) {
        for (var c : str) {
            if (!(c >= 'a' && c <= 'z')) {
                return false;
            }
        }
        return true;
    }

    public char[] compress(char[] str) {
        if (str == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        if (!isAlpha(str)) {
            throw new IllegalArgumentException("Non-alphabetic characters in string");
        }
        if (str.length < 2) {
            return str;
        }

        var compressed = new java.lang.StringBuilder();
        var currentChar = str[0];
        var count = 1;
        for (int i = 1; i < str.length; i++) {
            if (currentChar != str[i]) {
                compressed.append(currentChar);
                if (count > 1) {
                    compressed.append(count);
                }
                currentChar = str[i];
                count = 1;
            } else {
                count++;
            }
        }
        compressed.append(currentChar);
        if (count > 1) {
            compressed.append(count);
        }
        return compressed.toString().toCharArray();
    }
}
