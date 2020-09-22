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

    @SuppressWarnings("checkstyle:Indentation")
    public char[] compress(char[] str) {
        final int numberOfLetters = 26;
//        check for null
        if (str == null) {
            throw new IllegalArgumentException();
        }

        StringBuilder result = new StringBuilder();

        int repeats = 1;
        char pastChar = '0';
        for (int i = 0; i < str.length; i++) {
            char c = str[i];
            int element = c - 'a';
            // check each element for is char in range 'a'..'z'
            if ((element < 0) || (element > (numberOfLetters - 1))) {
                throw new IllegalArgumentException();
            }

            if (c != pastChar) {
                if (repeats > 1) {
                    result.append(repeats);
                    repeats = 1;
                }
                result.append(c);
            } else {
                repeats += 1;
                if (i == str.length - 1) {
                    result.append(repeats);
                }
            }
            pastChar = c;
        }

//        throw new RuntimeException("Not implemented");

        return result.toString().toCharArray();
    }
}
