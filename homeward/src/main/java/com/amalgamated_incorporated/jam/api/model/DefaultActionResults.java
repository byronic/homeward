package com.amalgamated_incorporated.jam.api.model;

public class DefaultActionResults {
  public static final ActionResult defaultFail = new ActionResult.Builder("That makes no sense.").build();
  public static final ActionResult defaultLategameFail = new ActionResult.Builder("Whatever.").build();
}
