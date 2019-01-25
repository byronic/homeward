package com.amalgamated_incorporated.jam.impl.model.prologue.a;

import java.util.List;
import java.util.Optional;

import com.amalgamated_incorporated.jam.api.model.ActionResult;
import com.amalgamated_incorporated.jam.api.model.DefaultActionResults;
import com.amalgamated_incorporated.jam.api.model.Interactive;
import com.amalgamated_incorporated.jam.api.model.Verbs;

public class Sun implements Interactive {

  @Override
  public String name() {
    // TODO Auto-generated method stub
    return "sun";
  }

  @Override
  public String description() {
    return "The sun is just a streak of orange now. Still muggy. Lots of bugs out.";
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
      return new ActionResult.Builder(description()).build();
    case USE: {
      return null;
    }
    case OPEN_CLOSE:
      return DefaultActionResults.defaultFail;
    case GO:
      return new ActionResult.Builder("Might as well be...").success(false).build();
    default:
      break;
    }
    return DefaultActionResults.defaultFail;
  }

}
