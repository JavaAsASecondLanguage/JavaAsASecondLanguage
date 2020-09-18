package io.github.javaasasecondlanguage.lecture01.practice2;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

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
    public static boolean isPalindrome(String str) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("Null string");
        }

        CharacterIterator beg = new StringCharacterIterator(str);
        CharacterIterator end = new StringCharacterIterator(str);
        end.last();
        beg.setIndex(beg.getBeginIndex());

        boolean isPalindrome = true;
        while (beg.getIndex() < end.getIndex()) {
            if (beg.current() != end.current()) {
                isPalindrome = false;
                break;
            }
            end.previous();
            beg.next();
        }
        return isPalindrome;
    }
}
