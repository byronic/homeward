package com.amalgamated_incorporated.jam.impl.model.prologue.a;

import java.util.List;
import java.util.Optional;

import com.amalgamated_incorporated.jam.api.model.ActionResult;
import com.amalgamated_incorporated.jam.api.model.DefaultActionResults;
import com.amalgamated_incorporated.jam.api.model.Interactive;
import com.amalgamated_incorporated.jam.api.model.Verbs;

public class HouseKey implements Interactive {

  @Override
  public String name() {
    return "house key";
  }

  @Override
  public String description() {
    return "Looks like a newly made key.";
  }

  @Override
  public boolean respondsTo(String id) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public ActionResult respond(Verbs verb, Optional<List<Interactive>> interactives) {
    switch (verb) {
    case COMBINE: {
      if (interactives.isPresent()) {
        for (Interactive i : interactives.get()) {
          switch (i.name()) {
          case "flip-phone":
            return new ActionResult.Builder("What if we had house keys, but for phones?").success(false).build();
          }
        }
      }
    }
      break;
    case USE: {
      if (interactives.isPresent()) {
        for (Interactive i : interactives.get()) {
          switch (i.name()) {
          case "lights":
            return new ActionResult.Builder("Yeah that doesn't make sense.").success(false).build();
          case "sun":
            return new ActionResult.Builder("I can cover the whole sun with the key, but this hurts my eyes.")
                .success(false).build();
          }
        }
      }
      return null;
    }
    case TAKE:
      return new ActionResult.Builder("I've already got the key.").success(false).build();
    case THROW:
      return new ActionResult.Builder("No way, I don't have a spare.").success(false).build();
    case PUT:
      return new ActionResult.Builder("I put it in my pocket.").success(false).build();
    case LOOK:
      return new ActionResult.Builder(description()).build();
    default:
      break;
    }
    return DefaultActionResults.defaultFail;
  }

}
