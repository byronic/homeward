package com.amalgamated_incorporated.messaging.impl;

import java.util.HashMap;
import java.util.Map;

import com.amalgamated_incorporated.messaging.api.Message;

public class DefaultMessage implements Message {
  private final Map<String, Object> map;

  private DefaultMessage(Map<String, Object> map) {
    this.map = map;
  }

  @Override
  public boolean has(String key) {
    return map.containsKey(key);
  }

  @Override
  public Object get(String key) {
    return map.get(key);
  }

  public static class Builder {
    private final Map<String, Object> builtMap = new HashMap<String, Object>();

    public Builder set(String key, Object value) {
      builtMap.put(key, value);
      return this;
    }

    public DefaultMessage build() {
      return new DefaultMessage(builtMap);
    }
  }

}
