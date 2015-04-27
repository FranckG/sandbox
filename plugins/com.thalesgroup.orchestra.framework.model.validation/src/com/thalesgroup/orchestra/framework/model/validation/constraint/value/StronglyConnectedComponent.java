/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.value;

import java.util.ArrayList;
import java.util.List;

public class StronglyConnectedComponent {
  public List<Vertex> list;

  public StronglyConnectedComponent() {
    this.list = new ArrayList<Vertex>();
  }

  public StronglyConnectedComponent(ArrayList<Vertex> collection_p) {
    this.list = new ArrayList<Vertex>(collection_p);
  }

  public boolean isCycle() {
    return this.list.size() > 1;
  }

  public void add(Vertex vertex_p) {
    this.list.add(vertex_p);
  }
}