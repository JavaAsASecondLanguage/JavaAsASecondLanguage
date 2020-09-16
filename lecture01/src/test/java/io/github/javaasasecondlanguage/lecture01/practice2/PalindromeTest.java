package io.github.javaasasecondlanguage.lecture01.practice2;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PalindromeTest {
    @Test
    void nullIsAnIlligalArgument() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Palindrome.isPalindrome(null)
        );
    }

    @Test
    void emptyStringIsAPalindrome() {
        assertTrue(Palindrome.isPalindrome(""));
    }

    @Test
    void singleLetterIsAPalindrome() {
        for (var c = 'a'; c < 'z'; c++) {
            assertTrue(Palindrome.isPalindrome(String.valueOf(c)));
        }
    }

    @Test
    void registerMatters() {
        assertFalse(Palindrome.isPalindrome("abBA"));
    }


    @Test
    void oddStringPalindrome() {
        assertTrue(Palindrome.isPalindrome("aBa"));
    }

    @Test
    void evenStringPalindrome() {
        assertTrue(Palindrome.isPalindrome("abccba"));
    }

    @Test
    void oddStringIsNotPalindrome() {
        assertFalse(Palindrome.isPalindrome("aBc"));
    }

    @Test
    void evenStringIsNotPalindrome() {
        assertFalse(Palindrome.isPalindrome("abcDba"));
    }

    @Test
    void randomStingIsAPalindrome() {
        String randomString = ("Aiaia rever devil stink trap pip steels peep dog "
                + "sleep time mined Kassak denim emit peels god peep sleets pip part "
                + "knits lived rever Aiaia"
        ).toLowerCase();
        assertTrue(Palindrome.isPalindrome(randomString));
    }
}