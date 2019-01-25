package com.amalgamated_incorporated.messaging.api;

public interface MessageController {
  public void subscribe(String topic, Subscriber s);

  public void unsubscribe(String topic, Subscriber s);

  public void send(String topic, Message m);
}
