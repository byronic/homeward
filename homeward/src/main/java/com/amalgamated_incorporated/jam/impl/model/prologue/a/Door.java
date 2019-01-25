package com.amalgamated_incorporated.jam.impl.model.prologue.a;

import java.util.List;
import java.util.Optional;

import com.amalgamated_incorporated.jam.api.model.ActionResult;
import com.amalgamated_incorporated.jam.api.model.DefaultActionResults;
import com.amalgamated_incorporated.jam.api.model.Interactive;
import com.amalgamated_incorporated.jam.api.model.Verbs;
import com.amalgamated_incorporated.jam.impl.controller.MessageTopic;
import com.amalgamated_incorporated.jam.impl.model.prologue.b.PrologueB;
import com.amalgamated_incorporated.messaging.api.Message;
import com.amalgamated_incorporated.messaging.api.MessageController;
import com.amalgamated_incorporated.messaging.impl.DefaultMessage;

public class Door implements Interactive {
  // this message is used to link the door to scene PrologueB
  private final static Message changeScene = new DefaultMessage.Builder()
      .set(MessageTopic.AREA_TRANSITION, PrologueB.ID).build();
  private final MessageController messageSystem;

  public Door(MessageController messageSystem) {
    this.messageSystem = messageSystem;
  }

  private int tries = 0;
  private boolean locked = true;

  @Override
  public String name() {
    return "door";
  }

  @Override
  public String description() {
    return "It's a big red door with an iron knocker.&nbsp;&nbsp;Probably locked.";
  }

  @Override
  public boolean respondsTo(String id) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public ActionResult respond(Verbs verb, Optional<List<Interactive>> interactives) {
    // TODO Auto-generated method stub
    switch (verb) {
    case LOOK:
      return new ActionResult.Builder(description()).build();
    case GO: {
      if (locked) {
        return new ActionResult.Builder("Okay, I'm there.").success(false).build();
      } else {
        System.out.println("Sent area transition message");
        messageSystem.send(MessageTopic.AREA_TRANSITION, Door.changeScene);
        return new ActionResult.Builder("I went through the front door.").success(true).build();
      }
    }
    case USE: {
      if (interactives.isPresent()) {
        if (interactives.get().size() == 1) {
          if (locked) {
            return new ActionResult.Builder("It's locked.").success(false).build();
          } else {
            return new ActionResult.Builder("The door opens easily now.").success(true).build();
          }
        }
        for (Interactive i : interactives.get()) {
          System.out.println(i.name());
          if (i.name().equals("house key")) {
            System.out.println("House key used!");
            tries += 1;
            switch (tries) {
            case 1: {
              return new ActionResult.Builder("I turn the lock leftward, but... the door is still locked?!")
                  .success(false).build();
            }
            case 2: {
              locked = false;
              return new ActionResult.Builder("Oh, the lock is reversed. Now it's unlocked.").success(true).build();
            }
            }
          }
        }
      }
      return null;
    }
    default:
      break;
    }
    return DefaultActionResults.defaultFail;
  }

}
