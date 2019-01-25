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

public class House implements Interactive {
  public final static String ID = "com.amalgamated_incorporated.jam.impl.model.prologue.a.House.LOOK_TRIGGER";
  private final MessageController messageSystem;
  private final static Message lookTrigger = new DefaultMessage.Builder().set(MessageTopic.LOOK_TRIGGER, House.ID)
      .build();

  public House(MessageController messageSystem) {
    this.messageSystem = messageSystem;
  }

  @Override
  public String name() {
    return "new house";
  }

  @Override
  public String description() {
    return "There's red brick behind these four white columns.<br />Probably some fancy architecture name for all the windows.";
  }

  @Override
  public boolean respondsTo(String id) {
    return false;
  }

  @Override
  public ActionResult respond(Verbs verb, Optional<List<Interactive>> interactives) {
    switch (verb) {
    case LOOK: {
      messageSystem.send(MessageTopic.LOOK_TRIGGER, House.lookTrigger);
      return new ActionResult.Builder(description()).build();
    }
    case USE: {
      if (interactives.isPresent()) {
        if (interactives.get().size() == 1) {
          return new ActionResult.Builder("How do I do that?").success(false).build();
        }
      }
      return null;
    }
    case GO: {
      return new ActionResult.Builder("I'm here.").success(false).build();
    }
    case OPEN_CLOSE: {
      return new ActionResult.Builder("I'd rather open the door than the house.").success(false).build();
    }
    default:
      break;
    }
    return DefaultActionResults.defaultFail;
  }

}
