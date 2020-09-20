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
        // check str is null
        if (str == null) {
            throw new IllegalArgumentException("Illegal argument: " + str);
        }

        // check str is empty
        if (str.length == 0) {
            return str;
        }

        int nb = 1;
        //char[] res = new char[]{};
        StringBuilder res = new StringBuilder();
        if ((str[0] >= 'a') & (str[0] <= 'z')) {
            res.append(str[0]);
        } else {
            throw new IllegalArgumentException("Illegal argument: " + str[0]);
        }
        for (int i = 1; i < str.length; i++) {
            if ((str[i] >= 'a') & (str[i] <= 'z')) {
                if (str[i] == str[i - 1]) {
                    nb++;
                } else {
                    if (nb > 1) {
                        res.append(nb);
                    }
                    res.append(str[i]);
                    nb = 1;
                }
            } else {
                throw new IllegalArgumentException("Illegal argument: " + str[0]);
            }
        }
        if (nb > 1) {
            res.append(nb);
        }

        String temp = res.toString();
        char[] reschars = temp.toCharArray();
        return reschars;
    }
}
