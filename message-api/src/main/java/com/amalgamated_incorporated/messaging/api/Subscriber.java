package com.amalgamated_incorporated.messaging.api;

public interface Subscriber {
  public void receive(String topic, Message message);
}
