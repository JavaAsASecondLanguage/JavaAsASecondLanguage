package io.github.javaasasecondlanguage.homework01.compressor;

import java.util.Arrays;

public class StringCompressor {
  /**
   * Given an array of characters, compress it using the following algorithm:
   *
   * <p>Begin with an empty string s. For each group of consecutive repeating characters in chars:
   * If the group's length is 1, append the character to s. Otherwise append the character followed
   * by the group's length. Return a compressed string.
   *
   * <p>Follow up: Could you solve it using only O(1) extra space?
   *
   * <p>Examples: a -> a aa -> a2 aaa -> a3 aaabb -> a3b2 "" -> "" null -> Illegal argument 234 sdf
   * -> Illegal argument
   *
   * @param str nullable array of chars to compress str may contain illegal characters
   * @return a compressed array
   * @throws IllegalArgumentException if str is null
   * @throws IllegalArgumentException if any char is not in range 'a'..'z'
   */
  public char[] compress(char[] str) {
    if (str == null) {
      throw new IllegalArgumentException("str is null");
    }
    if (str.length == 0) {
      return str;
    }
    if (str[0] < 'a' || str[0] > 'z') {
      throw new IllegalArgumentException(
          "Got " + str[0] + " on position " + 0 + "only lower-case latin chars accepted.");
    }
    char prev = str[0];
    int len = 1;
    int newPos = 0;
    int lenlen = 1;
    int prevLen = 1;
    for (int i = 1; i < str.length; ++i) {
      if (str[i] == prev) {
        ++len;
        if (len / 10 >= prevLen) {
          ++lenlen;
          prevLen *= 10;
        }
      } else {
        if (str[i] < 'a' || str[i] > 'z') {
          throw new IllegalArgumentException(
              "Got " + str[i] + " on position " + i + "only lower-case latin chars accepted.");
        }
        str[newPos] = prev;
        if (len != 1) {
          for (var j = lenlen; j > 0; --j) {
            str[newPos + j] = Integer.toString(len % 10).charAt(0);
            len /= 10;
          }
          newPos += lenlen;
        }
        ++newPos;
        len = lenlen = prevLen = 1;
        prev = str[i];
      }
    }
    str[newPos] = prev;
    if (len != 1) {
      for (var j = lenlen; j > 0; --j) {
        str[newPos + j] = Integer.toString(len % 10).charAt(0);
        len /= 10;
      }
      newPos += lenlen;
    }
    ++newPos;
    return Arrays.copyOfRange(str, 0, newPos);
  }
}
