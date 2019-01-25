package com.amalgamated_incorporated.jam.api.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
  private List<Interactive> inventory = new ArrayList<Interactive>();

  public void addToInventory(Interactive toAdd) {
    inventory.add(toAdd);
  }

  public List<Interactive> getInventory() {
    return inventory;
  }

  public void removeFromInventory(Interactive toRemove) {
    inventory.remove(toRemove);
  }
}
