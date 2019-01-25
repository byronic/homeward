package com.amalgamated_incorporated.jam.impl.model.prologue.a;

import java.util.List;
import java.util.Optional;

import com.amalgamated_incorporated.jam.api.model.ActionResult;
import com.amalgamated_incorporated.jam.api.model.DefaultActionResults;
import com.amalgamated_incorporated.jam.api.model.Interactive;
import com.amalgamated_incorporated.jam.api.model.Verbs;

public class Flowers implements Interactive {

  @Override
  public String name() {
    return "flowers";
  }

  @Override
  public String description() {
    return "I think these are... irises? Lilies? I don't really know flowers. Ew, there's a bug in this one. Smells nice though.";
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
    case USE:
      return new ActionResult.Builder("What for?").success(false).build();
    case TAKE:
      return new ActionResult.Builder(
          "I don't... think I'm allowed to pick the flowers. Does mom even like gardens? I never see her except working at home or at the lab. Or she's passed out after eating chili...")
              .success(false).build();
    case PUT:
      return new ActionResult.Builder("They're already planted.").success(false).build();
    case OPEN_CLOSE:
      return new ActionResult.Builder(
          "They're... already open. Latin 'aprilis.' Means to open like a flower... I think.").success(false).build();
    default:
      break;
    }
    return DefaultActionResults.defaultFail;
  }

}
