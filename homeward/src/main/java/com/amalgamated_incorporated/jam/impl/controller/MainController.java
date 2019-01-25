package com.amalgamated_incorporated.jam.impl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.amalgamated_incorporated.jam.api.model.ActionResult;
import com.amalgamated_incorporated.jam.api.model.Interactive;
import com.amalgamated_incorporated.jam.api.model.Player;
import com.amalgamated_incorporated.jam.api.model.Scene;
import com.amalgamated_incorporated.jam.api.model.Verbs;
import com.amalgamated_incorporated.jam.api.view.GameView;
import com.amalgamated_incorporated.jam.impl.model.prologue.a.HouseKey;
import com.amalgamated_incorporated.jam.impl.model.prologue.a.Phone;
import com.amalgamated_incorporated.jam.impl.model.prologue.a.PrologueA;
import com.amalgamated_incorporated.jam.impl.model.prologue.b.PrologueB;
import com.amalgamated_incorporated.jam.impl.view.GameWindow;
import com.amalgamated_incorporated.jam.impl.view.HTML;
import com.amalgamated_incorporated.jam.impl.view.VerbButtonListener;
import com.amalgamated_incorporated.messaging.api.Message;
import com.amalgamated_incorporated.messaging.api.MessageController;
import com.amalgamated_incorporated.messaging.api.Subscriber;
import com.amalgamated_incorporated.messaging.impl.DefaultMessageController;

public class MainController implements Subscriber {
  /**
   * Game stuff! That I like, and isn't to do with graphx
   */
  private final static MessageController messageSystem = new DefaultMessageController();
  private Verbs activeVerb = Verbs.LOOK;
  private List<Interactive> activeObjects = new ArrayList<Interactive>();
  private Player player = new Player();
  // TODO: Make the Scenes a map of String ID -> Scene scene
  private static final Map<String, Scene> scenes;
  static {
    scenes = new HashMap<String, Scene>();
    scenes.put(PrologueA.ID, new PrologueA(MainController.messageSystem));
    scenes.put(PrologueB.ID, new PrologueB());
  }
  private static Scene currentScene = scenes.get(PrologueA.ID);

  private GameView window = new GameWindow(getListener(), (Verbs v) -> {
    return new VerbButtonListener(MainController.messageSystem, v);
  }, MainController.messageSystem);

  @Override
  public void receive(String topic, Message message) {
    switch (topic) {
    case MessageTopic.VERB_CLICK: {
      activeVerb = (Verbs) message.get(MessageTopic.VERB_CLICK);
      activeObjects = new ArrayList<Interactive>();
    }
      break;
    case MessageTopic.AREA_TRANSITION: {
      System.out.println("Received area transition message");
      MainController.currentScene = scenes.get(message.get(MessageTopic.AREA_TRANSITION));
    }
      break;
    }
  }

  public void init() {
    subscribeToTopics();
    window.start();
    initPlayerInventory();
    updateActiveScene();
    updateInventory();
  }

  private void subscribeToTopics() {
    messageSystem.subscribe(MessageTopic.VERB_CLICK, this);
    messageSystem.subscribe(MessageTopic.AREA_TRANSITION, this);
  }

  private void initPlayerInventory() {
    player.addToInventory(new HouseKey());
    player.addToInventory(new Phone());
  }

  private void updateActiveScene() {
    window.setTitle("Homeward 2 - " + currentScene.getName());
    window.setSceneDescription(HTML.htmlify(currentScene.getDescription()));
  }

  private void updateInventory() {
    String s = "";
    for (Interactive i : player.getInventory()) {
      s += HTML.linkify(i.name(), i.name()) + " ";
    }
    window.setInventoryDescription(HTML.htmlify(s));
  }

  private HyperlinkListener getListener() {
    return (HyperlinkEvent e) -> {
      if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
        Interactive clickedObject = currentScene.getClickedObject(e.getDescription());
        if (clickedObject == null) {
          // try inventory
          for (Interactive i : player.getInventory()) {
            if (e.getDescription().equals(i.name())) {
              clickedObject = i;
              break;
            }
          }
        }
        activeObjects.add(clickedObject);
        ActionResult a = null;
        if (activeVerb.equals(Verbs.COMBINE)) {
          System.out.println(activeObjects.size());
          if (activeObjects.size() == 2) {
            a = activeObjects.get(0).respond(Verbs.COMBINE, Optional.of(activeObjects));

            if (a.getSuccess().isPresent() && a.getSuccess().get()) {
              System.out.println("TODO: Not implemented!");
            }
            window.setCommandDescription(HTML.htmlify(HTML.boldify(a.getResultString())));
            activeObjects = new ArrayList<Interactive>();
            activeVerb = Verbs.LOOK;
          }
        } else if (activeVerb.equals(Verbs.USE)) {

          List<ActionResult> actionResults = activeObjects.stream().map((Interactive i) -> {
            return i.respond(Verbs.USE, Optional.of(activeObjects));
          }).collect(Collectors.toList());

          boolean foundNonNull = false;
          String resultStrings = "";
          List<Interactive> tempList = new ArrayList<Interactive>();

          for (ActionResult aa : actionResults) {
            if (aa != null) {
              foundNonNull = true;
              resultStrings += aa.getResultString() + " ";
              if (aa.getResultObjects().isPresent()) {
                tempList.addAll(aa.getResultObjects().get());
              }
            }
          }

          if (foundNonNull) {
            ActionResult.Builder builder = new ActionResult.Builder(resultStrings);
            if (!tempList.isEmpty()) {
              builder.resultObjects(tempList);
            }
            a = builder.build();
            window.setCommandDescription(HTML.htmlify(HTML.boldify(a.getResultString())));
            if (activeObjects.size() == 2 || (a.getSuccess().isPresent() && a.getSuccess().get())) {
              System.out.println("Triggered USE reset");
              activeObjects = new ArrayList<Interactive>();
              activeVerb = Verbs.LOOK;
            }
          } else {
            // all results null, I can't use X with Y!
            if (activeObjects.size() == 2) {
              window.setCommandDescription(HTML.htmlify(
                  String.format("I can't use %s with %s.", activeObjects.get(0).name(), clickedObject.name())));
              activeVerb = Verbs.LOOK;
            }
          }
        } else {
          System.out.println(activeVerb + ", " + clickedObject.description());

          a = clickedObject.respond(activeVerb, Optional.of(activeObjects));
          window.setCommandDescription(HTML.htmlify(HTML.boldify(a.getResultString())));
          activeVerb = Verbs.LOOK;

          activeObjects = new ArrayList<Interactive>();
        }
        // Update all pieces.
        updateActiveScene();
        updateInventory();
      }
    };
  }

}
