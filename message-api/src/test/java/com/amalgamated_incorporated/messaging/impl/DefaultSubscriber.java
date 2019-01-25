package com.amalgamated_incorporated.messaging.impl;

import com.amalgamated_incorporated.messaging.api.Message;
import com.amalgamated_incorporated.messaging.api.MessageController;
import com.amalgamated_incorporated.messaging.api.Subscriber;

public class DefaultSubscriber implements Subscriber {
  public final static String EXAMPLE_KEY = "EXAMPLE_KEY";
  public final static String EXAMPLE_CONTENTS = "EXAMPLE_CONTENTS";
  public final static String EXAMPLE_INT = "EXAMPLE_INT";
  public final static String EXAMPLE_TOPIC = "EXAMPLE_TOPIC";

  private boolean receivedMessage = false;
  private String messageContents = "";
  private int receivedInt;
  private final MessageController controller;

  public DefaultSubscriber(MessageController controller) {
    this.controller = controller;
  }

  @Override
  public void receive(String topic, Message message) {
    if (message.has(EXAMPLE_KEY)) {
      // I got my message!
      receivedMessage = true;
      messageContents = (String) message.get(EXAMPLE_CONTENTS);
      receivedInt = (int) message.get(EXAMPLE_INT);
    }
  }

  public boolean hasReceivedMessage() {
    return receivedMessage;
  }

  public int getReceivedInt() {
    return receivedInt;
  }

  public String getMessageContents() {
    return messageContents;
  }

  public void subscribe() {
    controller.subscribe(EXAMPLE_TOPIC, this);
  }

  public void unsubscribe() {
    controller.unsubscribe(EXAMPLE_TOPIC, this);
  }

}
