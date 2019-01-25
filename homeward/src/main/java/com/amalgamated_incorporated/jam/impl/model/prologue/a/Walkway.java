package com.amalgamated_incorporated.jam.impl.model.prologue.a;

import java.util.List;
import java.util.Optional;

import com.amalgamated_incorporated.jam.api.model.ActionResult;
import com.amalgamated_incorporated.jam.api.model.DefaultActionResults;
import com.amalgamated_incorporated.jam.api.model.Interactive;
import com.amalgamated_incorporated.jam.api.model.Verbs;

public class Walkway implements Interactive {

  @Override
  public String name() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String description() {
    return "It looks like green stuff has grown in between the stone tiles. It leads to the fat mailbox by the street. The sidewalk has a bunch of newspaper stains.";
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
    default:
      break;
    }
    return DefaultActionResults.defaultFail;
  }

}
