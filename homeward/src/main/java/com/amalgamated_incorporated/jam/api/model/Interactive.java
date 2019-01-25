package com.amalgamated_incorporated.jam.api.model;

import java.util.List;
import java.util.Optional;

/**
 * Any interactive object. Can be a door, an item, &c. Part of the Model.
 */
public interface Interactive {

  String name();

  String description();

  boolean respondsTo(String id);

  ActionResult respond(Verbs verb, Optional<List<Interactive>> interactives);
}
