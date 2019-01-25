package com.amalgamated_incorporated.jam.impl.model.prologue.a;

import java.util.List;
import java.util.Optional;

import com.amalgamated_incorporated.jam.api.model.ActionResult;
import com.amalgamated_incorporated.jam.api.model.DefaultActionResults;
import com.amalgamated_incorporated.jam.api.model.Interactive;
import com.amalgamated_incorporated.jam.api.model.Verbs;

public class Lights implements Interactive {

  @Override
  public String name() {
    // TODO Auto-generated method stub
    return "lights";
  }

  @Override
  public String description() {
    // TODO Auto-generated method stub
    return "I can see lights on inside.";
  }

  @Override
  public boolean respondsTo(String id) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public ActionResult respond(Verbs verb, Optional<List<Interactive>> interactives) {
    switch (verb) {
    case LOOK:
      return new ActionResult.Builder(description()).success(true).build();
    case USE: {
      return null;
    }
    case OPEN_CLOSE:
      return new ActionResult.Builder("Huh?").build();
    case GO:
      return new ActionResult.Builder("Those are inside. I need to go in first.").success(false).build();
    default:
      break;

    }
    return DefaultActionResults.defaultFail;
  }

}
