package com.ex.shop.common.util;

import java.util.Random;

public class RandomUtils {

  /**
   * 랜덤 문자열 생성.
   * @param size 길이
   * @param flag 문자열 종류 ('n' 숫자, 's' 영문소문자, 'm' 혼합)
   * @return String 랜덤 문자열
   */
  public static String createRandomString(int size, String flag) {
    Random rand = new Random();
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < size; i++) {
      if ("n".equals(flag)) {
        buf.append(rand.nextInt(10));
      } else if ("s".equals(flag)) {
        buf.append((char) (rand.nextInt(26) + 97));
      } else {
        if (rand.nextBoolean()) {
          buf.append((char) (rand.nextInt(26) + 97));
        } else {
          buf.append(rand.nextInt(10));
        }
      }
    }
    return buf.toString();
  }
}
