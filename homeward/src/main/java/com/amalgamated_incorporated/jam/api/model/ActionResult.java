package com.amalgamated_incorporated.jam.api.model;

import java.util.List;
import java.util.Optional;

public class ActionResult {
  // every result needs a result string, to put into the game window!
  private final String resultString;
  // success matters for GET, PUT, USE, COMBINE
  private final Optional<Boolean> success;
  // Resultant objects matter if the Interactive can create other Interactives
  private final Optional<List<Interactive>> resultObjects;

  private ActionResult(String resultString, Optional<Boolean> success, Optional<List<Interactive>> resultObjects) {
    this.success = success;
    this.resultObjects = resultObjects;
    this.resultString = resultString;
  }

  public static class Builder {
    private String resultString;
    private Optional<Boolean> success;
    private Optional<List<Interactive>> resultObjects;

    public Builder(String resultString) {
      this.resultString = resultString;
      success = Optional.empty();
      resultObjects = Optional.empty();
    }

    public Builder success(boolean s) {
      this.success = Optional.of(s);
      return this;
    }

    public Builder resultObjects(List<Interactive> resultObjects) {
      this.resultObjects = Optional.of(resultObjects);
      return this;
    }

    public ActionResult build() {
      return new ActionResult(this.resultString, this.success, this.resultObjects);
    }
  }

  public String getResultString() {
    return resultString;
  }

  public Optional<List<Interactive>> getResultObjects() {
    return resultObjects;
  }

  public Optional<Boolean> getSuccess() {
    return success;
  }
}
