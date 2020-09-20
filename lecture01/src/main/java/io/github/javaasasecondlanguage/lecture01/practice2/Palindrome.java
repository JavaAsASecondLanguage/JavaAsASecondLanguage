package io.github.javaasasecondlanguage.lecture01.practice2;

public class Palindrome {
    /**
     * @param str - Nullable string
     * @return True if str is a palindrome
     * 1. Empty string is a palindrome.
     * 2. "aba" is a palindrome.
     * 3. "ab" is not a palindrome.
     * 4. "abA" is not a palindrome.
     * @throws IllegalArgumentException if str is `null`
     */
    public static boolean isPalindrome(String str) {
        if (str == null) {
            throw new IllegalArgumentException("str is null");
        }
        //throw new RuntimeException("Not implemented");
        int i1 = 0;
        int i2 = str.length() - 1;
        while (i2 > i1) {
            if (str.charAt(i1) != str.charAt(i2)) {
                return false;
            }
            ++i1;
            --i2;
        }
        return true;

/*
        if (str == null) {
            throw new IllegalArgumentException();
        }
        // use reverse string

 */
    }
}
