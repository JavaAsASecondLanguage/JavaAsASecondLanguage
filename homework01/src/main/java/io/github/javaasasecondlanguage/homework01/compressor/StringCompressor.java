package io.github.javaasasecondlanguage.homework01.compressor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class StringCompressor {
    private final Set<Character> validChars = "abcdefghijklmnopqrstuvwxyz".chars()
            .mapToObj(e -> (char) e).collect(Collectors.toSet());
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
            throw new IllegalArgumentException("null");
        }

        if (str.length == 0) {
            return str;
        }

        int l = 1;
        int r = 1;

        char prev = str[0];
        int counter = 1;

        while (r < str.length) {
            if (!validChars.contains(str[r])) {
                throw new IllegalArgumentException("Invalid char: " + str[r]);
            }

            if (prev == str[r]) {
                counter += 1;
            } else {
                if (counter > 1) {
                    for (char ch : Integer.toString(counter).toCharArray()) {
                        str[l] = ch;
                        l += 1;
                    }
                }

                str[l] = str[r];
                l += 1;

                counter = 1;
                prev = str[r];
            }

            r += 1;
        }

        if (counter > 1) {
            for (char ch : Integer.toString(counter).toCharArray()) {
                str[l] = ch;
                l += 1;
            }
        }

        return Arrays.copyOf(str, l);
    }
}
