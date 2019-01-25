package com.amalgamated_incorporated.messaging.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amalgamated_incorporated.messaging.api.Message;
import com.amalgamated_incorporated.messaging.api.MessageController;
import com.amalgamated_incorporated.messaging.api.Subscriber;

public class DefaultMessageController implements MessageController {
  private final static Logger log = LoggerFactory.getLogger(DefaultMessageController.class);

  private final Map<String, Set<Subscriber>> messageMap = new HashMap<>();

  @Override
  public void subscribe(String topic, Subscriber s) {
    if (messageMap.containsKey(topic)) {
      log.info(String.format("%s added to subscriber list for topic %s", s.toString(), topic));
      messageMap.get(topic).add(s);
    } else {
      log.info(String.format("%s is the first subscriber for topic %s", s.toString(), topic));
      messageMap.put(topic, Stream.of(s).collect(Collectors.toSet()));
    }
  }

  @Override
  public void unsubscribe(String topic, Subscriber s) {
    if (messageMap.containsKey(topic)) {
      log.info(String.format("%s unsubscribes from topic %s", s.toString(), topic));
      messageMap.get(topic).remove(s);
    } else {
      log.warn(String.format("%s tried to unsubscribe from topic %s, but wasn't subscribed", s.toString(), topic));
    }
  }

  @Override
  public void send(String topic, Message m) {
    if (messageMap.containsKey(topic)) {
      messageMap.get(topic).forEach((Subscriber s) -> {
        s.receive(topic, m);
      });
      log.info(String.format("Message %s sent to %d subscribers", m.toString(), messageMap.get(topic).size()));
    } else {
      log.warn(String.format("No subscribers to message %s", m.toString()));
    }
  }

}
