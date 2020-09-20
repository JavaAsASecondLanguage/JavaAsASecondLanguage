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
            throw new IllegalArgumentException("str is null");
        }
        if (str.length == 0) {
            return new char[0];
        }
        StringBuilder compressedString = new StringBuilder();
        char previousCharacter = str[0];
        int characterCount = 0;
        for (var character : str) {
            if (!(character >= 'a' && character <= 'z')) {
                throw new IllegalArgumentException(String.format(
                    "char '%c' is not in range 'a'..'z'", character));
            }
            if (character != previousCharacter) {
                populateWithCharacter(compressedString, previousCharacter, characterCount);
                characterCount = 1;
                previousCharacter = character;
            } else {
                characterCount++;
            }
        }
        populateWithCharacter(compressedString, previousCharacter, characterCount);
        return compressedString.toString().toCharArray();
    }

    private void populateWithCharacter(StringBuilder compressedString, char character, int count) {
        compressedString.append(character);
        if (count > 1) {
            compressedString.append(count);
        }
    }
}
