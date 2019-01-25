package com.amalgamated_incorporated.jam.impl.model.prologue.b;

import com.amalgamated_incorporated.jam.api.model.Interactive;
import com.amalgamated_incorporated.jam.api.model.Scene;

public class PrologueB implements Scene {
  public static final String ID = "com.amalgamated_incorporated.jam.impl.model.prologue.b.PrologueB";

  @Override
  public String getId() {
    return ID;
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return "Prologue - Foyer";
  }

  @Override
  public String getDescription() {
    String d = "Wow, that ceiling is <i>wayyyyyy</i> high. There's floor-length windows that face the courtyard in front of me, and hallways to the left and right. Paintings that once hung on the pale, low walls left their silhouettes behind.";
    d += "<br /><br />And, you've reached as far as we were able to implement as late entrants! Thanks for playing :)<br />";
    d += "`beauty queen breakdown` - programming, `@rebirf_alice` - lead design, and Ben - additional design and consultancy";
    return d;
  }

  @Override
  public Interactive getClickedObject(String link) {
    // TODO Auto-generated method stub
    return null;
  }

}
