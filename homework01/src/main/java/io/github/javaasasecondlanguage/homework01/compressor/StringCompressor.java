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
     *
     * Follow up:
     * Could you solve it using only O(1) extra space?
     *
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

        // check that input is not null
        if (str == null) {
            throw new IllegalArgumentException();
        }

        // check that there are no illegal characters
        for (char c : str) {
            if (c < 'a' || c > 'z') {
                throw new IllegalArgumentException();
            }
        }

        if (str.length <= 1) {
            return str;
        }

        StringBuilder result = new StringBuilder();

        char previousCharacter = str[0];
        int count = 1;
        for (int i = 1; i < str.length; i++) {

            if (str[i] == previousCharacter) {
                count++;
            } else {
                result = addCompressedBlock(result, previousCharacter, count);
                previousCharacter = str[i];
                count = 1;
            }
        }
        // append compressed block one more time
        result = addCompressedBlock(result, previousCharacter, count);

        return result.toString().toCharArray();
    }

    private StringBuilder addCompressedBlock(StringBuilder current, char ch, int count) {
        if (count == 1) {
            return current.append(ch);
        } else {
            return current.append(ch).append(Integer.toString(count));
        }
    }

}
