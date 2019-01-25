package com.amalgamated_incorporated.jam.api.model;

/*
 * A Scene is like a Room of the game. 
 * Part of the Model.
 */
public interface Scene {
  /*
   * Scene name
   */
  String getName();

  /*
   * Scene's description (can change)
   */
  String getDescription();

  /**
   * Scene's ID, for message passing purposes (notably Go)
   * 
   */
  String getId();

  Interactive getClickedObject(String link);
}
