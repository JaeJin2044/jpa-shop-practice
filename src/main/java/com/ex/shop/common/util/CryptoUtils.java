package com.ex.shop.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@SuppressWarnings("unused")
public class CryptoUtils {

  /**
   * 암복호화에 사용될 시크릿 키
   */
  private static final byte[] SECRET_KEY = { 11, 51, 117, 70, 49, 55, 107,
    90, 87, 78, 19, 125, 67, 74, 121, 101, 43, 18, 77, 48, 65, 38, 5,
    102 };

  private static final SecretKeySpec KEYSPEC = new SecretKeySpec(SECRET_KEY,
    "DESede");
  public static final String TRANSFORMATION = "DESede/ECB/PKCS5Padding";

  /**
   * 전달된 문자열을 DES로 암호화하여 반환.
   *
   * @param str 암호화할 문자열
   * @return String 암호화된 문자열
   */
  public static String encodeStr(String str) {
    try {
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(1, KEYSPEC);
      byte[] plainText = str.getBytes(StandardCharsets.UTF_8);
      byte[] cipherText = cipher.doFinal(plainText);
      return Base64.getEncoder().encodeToString(cipherText);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 암호화된 문자열을 복호화하여 반환.
   *
   * @param str 암호화된 문자열
   * @return String 복호화된 문자열
   */
  public static String decodeStr(String str) {
    try {
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(2, KEYSPEC);
      byte[] base64bytes = Base64.getDecoder().decode(str);
      byte[] decryptedText = cipher.doFinal(base64bytes);
      return new String(decryptedText, StandardCharsets.UTF_8).trim();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 전달된 문자열을 SHA-256으로 암호화하여 반환.
   *
   * @param str 암호화할 문자열
   * @return String 암호화된 문자열
   */
  public static String encryptSha256(String str) {
    try {
      MessageDigest sh = MessageDigest.getInstance("SHA-256");
      sh.update(str.getBytes(StandardCharsets.UTF_8));
      byte[] byteData = sh.digest();
      StringBuilder sb = new StringBuilder();
      for (byte byteDatum : byteData) {
        sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16)
          .substring(1));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 평문과 암호문을 비교하여 일치하는지 여부 반환.
   *
   * @param raw    평문
   * @param encode 암호문
   * @return boolean 일치 여부
   */
  public static boolean matchesSha256(String raw, String encode) {
    return Objects.requireNonNull(encryptSha256(raw)).matches(encode);
  }

  /**
   * FREET에서 SPACENET_ENCRYPT 처리하기 위한 암호.
   *
   * @param str 원문
   * @return String 결과
   */
  public static String rpadWildCard(String str) {
    if (str == null)
      str = "";

    StringBuffer newStr = new StringBuffer(str);
    int zeroLen = 8 - (str.getBytes().length % 8);

    if (zeroLen == 8)
      zeroLen = 0;

    for (int i = 1; i <= zeroLen; i++)
      newStr.append("$");

    return newStr.toString();
  }

}
