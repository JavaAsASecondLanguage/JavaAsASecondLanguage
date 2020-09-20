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

        if (str == null) {
            throw new IllegalArgumentException();
        }

        ArrayList<Character> compressedArr = new ArrayList<>();
        int counter = 0;
        for (char c : str) {
            if (!Character.isLowerCase(c) || !Character.isLetter(c)) {
                throw new IllegalArgumentException();
            }

            if (!compressedArr.isEmpty() && compressedArr.get(compressedArr.size() - 1).equals(c)) {
                ++counter;
            } else {
                if (counter > 1) {
                    while (counter > 0) {
                        compressedArr.add((char) (counter % 10 + '0'));
                        counter /= 10;
                    }
                }
                compressedArr.add(c);
                counter = 1;
            }
        }
        if (counter > 1) {
            while (counter > 0) {
                compressedArr.add((char)(counter % 10 + '0'));
                counter /= 10;
            }
        }
        char[] res = new char[compressedArr.size()];
        for (int i = 0; i < compressedArr.size(); i++) {
            res[i] = compressedArr.get(i);
        }
        return res;
    }
}
