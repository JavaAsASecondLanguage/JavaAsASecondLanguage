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
        if (str == null) {
            throw new IllegalArgumentException("Null argument!");
        }

        for (var i = 0; i < str.length; i++) {
            if (str[i] < 'a' || str[i] > 'z') {
                throw new IllegalArgumentException(String.format("Contains illegal char {0} at position {1}!", i, str[i]));
            }
        }

        if (str.length < 2) {
            return str;
        }

        var prevChar = str[0];
        int charCount = 1;
        String result = "";
        for (var i = 1; i < str.length; i++) {
            if (str[i] == prevChar) {
                charCount++;
                continue;
            }

            result = addCompressedChar(result, prevChar, charCount);
            prevChar = str[i];
            charCount = 1;
        }

        result = addCompressedChar(result, prevChar, charCount);

        return result.toCharArray();
    }

    private String addCompressedChar(String str, char c, int charCount) {
        str = str + c;
        if (charCount > 1) {
            str = str + String.valueOf(charCount);
        }

        return str;
    }
}

