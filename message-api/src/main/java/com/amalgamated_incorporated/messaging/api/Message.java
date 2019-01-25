package com.amalgamated_incorporated.messaging.api;

public interface Message {
  public boolean has(String key);

  public Object get(String key);
}
