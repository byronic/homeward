package com.amalgamated_incorporated.jam.impl.model.prologue.a;

import java.util.List;
import java.util.Optional;

import com.amalgamated_incorporated.jam.api.model.ActionResult;
import com.amalgamated_incorporated.jam.api.model.DefaultActionResults;
import com.amalgamated_incorporated.jam.api.model.Interactive;
import com.amalgamated_incorporated.jam.api.model.Verbs;

public class Phone implements Interactive {

  @Override
  public String name() {
    return "flip-phone";
  }

  @Override
  public String description() {
    return "This is my mom's old work phone. She got to upgrade and I got a Nokia.";
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
    case PUT:
      return new ActionResult.Builder("It's bulging in my front pocket.").success(false).build();
    case THROW:
      return new ActionResult.Builder("I'm not mad enough to do that.").success(false).build();
    case USE: {
      if (interactives.isPresent()) {
        if (interactives.get().size() == 1) {
          return new ActionResult.Builder(
              "Man, my battery's dead. That game on it drains the battery SO FAST. I could charge it inside.")
                  .success(false).build();
        }
        for (Interactive i : interactives.get()) {
          switch (i.name()) {
          case "sun":
            return new ActionResult.Builder("It's not solar powered.").success(false).build();
          }
        }
      }
      return null;
    }
    case COMBINE: {
      if (interactives.isPresent()) {
        for (Interactive i : interactives.get()) {
          switch (i.name()) {
          case "house key":
            return new ActionResult.Builder("What if we had house keys, but for phones?").success(false).build();
          }
        }
      }
    }
      break;
    case TAKE:
      return new ActionResult.Builder("Oh yeah, it's right here.").success(false).build();
    default:
      break;
    }
    return DefaultActionResults.defaultFail;
  }

}
