/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.value;

import java.util.ArrayList;
import java.util.List;

public class StronglyConnectedComponentList {
  private List<StronglyConnectedComponent> collection;

  public StronglyConnectedComponentList() {
    this.collection = new ArrayList<StronglyConnectedComponent>();
  }

  public StronglyConnectedComponentList(ArrayList<StronglyConnectedComponent> collection_p) {
    this.collection = new ArrayList<StronglyConnectedComponent>(collection_p);
  }

  public void add(StronglyConnectedComponent scc_p) {
    this.collection.add(scc_p);
  }

  public List<StronglyConnectedComponent> cycles() {
    List<StronglyConnectedComponent> result = new ArrayList<StronglyConnectedComponent>();
    for (StronglyConnectedComponent stronglyConnectedComponent : this.collection) {
      if (stronglyConnectedComponent.isCycle()) {
        result.add(stronglyConnectedComponent);
      }
    }
    return result;
  }

  public List<StronglyConnectedComponent> independentComponents() {
    List<StronglyConnectedComponent> result = new ArrayList<StronglyConnectedComponent>();
    for (StronglyConnectedComponent stronglyConnectedComponent : this.collection) {
      if (!stronglyConnectedComponent.isCycle()) {
        result.add(stronglyConnectedComponent);
      }
    }
    return result;
  }
}