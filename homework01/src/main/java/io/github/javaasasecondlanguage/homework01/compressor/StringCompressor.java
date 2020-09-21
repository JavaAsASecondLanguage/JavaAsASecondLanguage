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
    public char[] compress(char[] str) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("argument is null");
        }

        var previousChar = '1';
        var currentCharCounter = 1;
        var result = new StringBuilder();

        for (char ch : str) {
            if (!Character.isLetter(ch)) {
                throw new IllegalArgumentException(
                        "char %s is is not in range 'a'..'z'".formatted(ch)
                );
            }

            if (previousChar == ch) {
                currentCharCounter++;
            } else {
                if (currentCharCounter > 1) {
                    result.append(currentCharCounter);
                }
                currentCharCounter = 1;
                result.append(ch);
            }

            previousChar = ch;
        }

        if (currentCharCounter > 1) {
            result.append(currentCharCounter);
        }


        return result.toString().toCharArray();
    }
}
