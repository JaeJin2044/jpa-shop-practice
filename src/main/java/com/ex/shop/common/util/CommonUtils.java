package com.ex.shop.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommonUtils {

  public static <K, V> Map<K, V> mapOf(Object... args) {
    Map<K, V> map = new LinkedHashMap<>();

    int size = args.length / 2;

    for (int i = 0; i < size; i++) {
      int kIdx = i * 2;
      int vIdx = kIdx + 1;

      K key = (K) args[kIdx];
      V value = (V) args[vIdx];
      map.put(key, value);
    }
    return map;
  }

  public static String encode(String str) {
    try {
      return URLEncoder.encode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      return str;
    }
  }

  public static String decode(String str) {
    try {
      return URLDecoder.decode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      return str;
    }
  }

}
