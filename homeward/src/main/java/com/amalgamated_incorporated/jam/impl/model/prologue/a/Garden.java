package com.amalgamated_incorporated.jam.impl.model.prologue.a;

import java.util.List;
import java.util.Optional;

import com.amalgamated_incorporated.jam.api.model.ActionResult;
import com.amalgamated_incorporated.jam.api.model.DefaultActionResults;
import com.amalgamated_incorporated.jam.api.model.Interactive;
import com.amalgamated_incorporated.jam.api.model.Verbs;
import com.amalgamated_incorporated.jam.impl.controller.MessageTopic;
import com.amalgamated_incorporated.messaging.api.Message;
import com.amalgamated_incorporated.messaging.api.MessageController;
import com.amalgamated_incorporated.messaging.impl.DefaultMessage;

public class Garden implements Interactive {
  public final static String ID = "com.amalgamated_incorporated.jam.impl.model.prologue.a.Garden";
  private final static Message lookTrigger = new DefaultMessage.Builder().set(MessageTopic.LOOK_TRIGGER, Garden.ID)
      .build();
  private final MessageController messageSystem;

  public Garden(MessageController messageSystem) {
    this.messageSystem = messageSystem;
  }

  @Override
  public String name() {
    return "garden";
  }

  @Override
  public String description() {
    return "I looked at the garden.";
  }

  @Override
  public boolean respondsTo(String id) {
    return false;
  }

  @Override
  public ActionResult respond(Verbs verb, Optional<List<Interactive>> interactives) {
    switch (verb) {
    case LOOK: {
      messageSystem.send(MessageTopic.LOOK_TRIGGER, Garden.lookTrigger);
      return new ActionResult.Builder(description()).build();
    }
    default:
      break;
    }
    return DefaultActionResults.defaultFail;
  }

}
