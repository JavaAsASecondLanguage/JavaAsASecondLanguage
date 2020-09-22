package io.github.javaasasecondlanguage.homework01.compressor;

import java.lang.StringBuilder;

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
    	
    	StringBuilder stringBuilder = new StringBuilder();
    	
    	if (str == null)
    		throw new IllegalArgumentException("str is null");
    	
    	if (str.length > 0) {

    		char c = checkCharacter(str[0]);
    		int charCounter = 1;

    		stringBuilder.append(c);
        	
        	for (int i = 1; i < str.length; i++) {

        		char new_c = checkCharacter(str[i]);
        		
        		if (c == new_c) {

        			charCounter++;
        			
        		} else {

        			if ( charCounter > 1) {
            			stringBuilder.append(charCounter);
        			}

        			c = new_c;
        			stringBuilder.append(c);
        			charCounter = 1;
        		}
        	}

			if ( charCounter > 1) {
    			stringBuilder.append(charCounter);
			}
    	}

    	return stringBuilder.toString().toCharArray();
    	
    }

    
	private char checkCharacter(char c) {
		
		if ( c >= 'a' && c <= 'z') {
			return c;
			
		} else {
			throw new IllegalArgumentException("char '" + c + "' is not in range 'a'..'z'");
			
		}
	}
    
}
