package com.amalgamated_incorporated.jam.impl.model.prologue.a;

import com.amalgamated_incorporated.jam.api.model.Interactive;
import com.amalgamated_incorporated.jam.api.model.Scene;
import com.amalgamated_incorporated.jam.impl.controller.MessageTopic;
import com.amalgamated_incorporated.messaging.api.Message;
import com.amalgamated_incorporated.messaging.api.MessageController;
import com.amalgamated_incorporated.messaging.api.Subscriber;

public class PrologueA implements Scene, Subscriber {
  public final static String ID = "com.amalgamated_incorporated.jam.impl.model.prologue.a.PrologueA";
  private String description;
  private final MessageController messageSystem;
  private boolean houseTriggerProcessed = false;
  private boolean gardenTriggerProcessed = false;

  private final House house;
  private Lights lights = new Lights();
  private Sun sun = new Sun();
  private final Door door;
  private Walkway walkway = new Walkway();
  private final Garden garden;
  private Flowers flowers = new Flowers();

  public PrologueA(MessageController messageSystem) {
    this.messageSystem = messageSystem;
    this.description = setDescription();
    this.door = new Door(this.messageSystem);
    this.messageSystem.subscribe(MessageTopic.LOOK_TRIGGER, this);
    this.house = new House(this.messageSystem);
    this.garden = new Garden(this.messageSystem);
  }

  @Override
  public String getId() {
    return ID;
  }

  @Override
  public String getName() {
    return "Prologue - Outdoors";
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public Interactive getClickedObject(String link) {
    // TODO Auto-generated method stub
    switch (link) {
    case "newHouse":
      return house;
    case "sun":
      return sun;
    case "lights":
      return lights;
    case "door":
      return door;
    case "garden":
      return garden;
    case "walkway":
      return walkway;
    case "flowers":
      return flowers;
    }
    return null;
  }

  @Override
  public void receive(String topic, Message message) {
    // only topic this subscribes to is LOOK triggers
    switch (message.get(MessageTopic.LOOK_TRIGGER).toString()) {
    case House.ID: {
      this.houseTriggerProcessed = true;
      this.updateDescription();
    }
      break;
    case Garden.ID: {
      this.gardenTriggerProcessed = true;
      this.updateDescription();
    }
      break;
    }
  }

  private void updateDescription() {
    description = setDescription();
  }

  private String setDescription() {
    String d = "I'm outside the <a href='newHouse'>new house</a>.<br />";
    d += "Mom won't be home 'til 9PM.<br />";
    d += "I think she said this was a &quot;southern colonial revival&quot; or something. ";
    d += "The columns out front are pretty cool.&nbsp;&nbsp;Our last house was just a little bungalow tucked between apartments under a bunch of phone lines.<br />";
    d += "Can't believe we live here now.<br />";
    d += "Mom asked that I write in the historic guestbook in the foyer.&nbsp;&nbsp;Apparently every family that has ever lived here writes in all the renovations they do and every major thing that happened.&nbsp;&nbsp;Makes it feel like a hotel, like we're just passing through.";
    d += "<br />Wellp, <a href='sun'>sun</a>'s getting low.&nbsp;&nbsp;I can see the <a href='lights'>lights</a> on the first floor.&nbsp;&nbsp;I don't wanna stay out here.&nbsp;&nbsp;There's mosquitoes.<br />";
    if (houseTriggerProcessed) {
      d += "<br />The <a href='door'>door</a> is shut. A <a href='garden'>garden</a> is to the right of the <a href='walkway'>walkway</a>.<br />";
    }
    if (gardenTriggerProcessed) {
      d += "I recognize some of these <a href='flowers'>flowers</a>; looks really fresh.<br />";
    }

    return d;
  }

}
