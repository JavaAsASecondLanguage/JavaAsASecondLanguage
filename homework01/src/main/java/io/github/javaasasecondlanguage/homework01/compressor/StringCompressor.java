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
    public char[] compress(char[] str) throws IllegalArgumentException {

        if(str == null) {
            throw new IllegalArgumentException();
        }

        if(str.length == 0) {
            return str;
        }

        StringBuilder resultString = new StringBuilder();
        int countCurrentLetter = 1;
        char currentLetter = str[0];

        for(int i = 1; i < str.length; i++) {
            if(str[i] < 'a' || str[i] > 'z') {
                throw new IllegalArgumentException();
            }

            if(currentLetter == str[i]) {
                countCurrentLetter++;
            }
            else {
                resultString.append(currentLetter);
                if(countCurrentLetter != 1) {
                    resultString.append(countCurrentLetter);
                    countCurrentLetter = 1;
                }
                currentLetter = str[i];
            }
        }

        resultString.append(currentLetter);
        if(countCurrentLetter != 1) {
            resultString.append(countCurrentLetter);
        }
        return resultString.toString().toCharArray();

    }

}

