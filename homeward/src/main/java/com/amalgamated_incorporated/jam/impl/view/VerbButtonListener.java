package com.amalgamated_incorporated.jam.impl.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.amalgamated_incorporated.jam.api.model.Verbs;
import com.amalgamated_incorporated.jam.impl.controller.MessageTopic;
import com.amalgamated_incorporated.messaging.api.Message;
import com.amalgamated_incorporated.messaging.api.MessageController;
import com.amalgamated_incorporated.messaging.impl.DefaultMessage;

public class VerbButtonListener implements ActionListener {
  private final MessageController messageSystem;
  private final Message clickMessage;

  public VerbButtonListener(MessageController messageSystem, Verbs verb) {
    this.messageSystem = messageSystem;
    this.clickMessage = new DefaultMessage.Builder().set(MessageTopic.VERB_CLICK, verb).build();
  }

  @Override
  public void actionPerformed(ActionEvent aEvent) {
    messageSystem.send(MessageTopic.VERB_CLICK, clickMessage);
  }
}
