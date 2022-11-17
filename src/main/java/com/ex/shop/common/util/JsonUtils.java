package com.ex.shop.common.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonUtils {

  public static Object toStr(Map<String, Object> map) {
    try {
      return getObjectMapper().writeValueAsString(map);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public static Map<String, Object> toMap(String jsonStr) {
    try {
      return getObjectMapper().readValue(jsonStr, LinkedHashMap.class);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  private static ObjectMapper getObjectMapper() {
    return ApplicationContextUtils.getBean(ObjectMapper.class);
  }

}
