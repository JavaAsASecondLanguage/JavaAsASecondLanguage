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
            throw new IllegalArgumentException();
        }

        if (str.length == 1) {
            return str;
        }

        int counter = 1;
        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < str.length; i++) {
            if ((int) str[i] < 97 || (int) str[i] > 122) {
                throw new IllegalArgumentException();
            }

            if (i < str.length - 1 && str[i] == str[i + 1]) {
                counter++;
            } else {
                ans.append(str[i]);
                if (counter > 1) {
                    ans.append(counter);
                    counter = 1;
                }
            }
        }

        char[] charans = new char[ans.length()];
        ans.getChars(0, ans.length(), charans, 0);
        return charans;
    }
}
