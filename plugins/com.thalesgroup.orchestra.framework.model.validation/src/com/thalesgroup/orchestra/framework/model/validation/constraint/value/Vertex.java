/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.value;

import java.util.ArrayList;
import java.util.List;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;

class Vertex {
  public Vertex() {
    this.Index = -1;
    this.Dependencies = new ArrayList<Vertex>();
  }

  public Vertex(AbstractVariable value) {
    this();
    this.Value = value;
  }

  public Vertex(List<Vertex> dependencies) {
    this();
    this.Dependencies.addAll(dependencies);
  }

  public Vertex(AbstractVariable value, List<Vertex> dependencies) {
    this(dependencies);
    this.Value = value;
  }

  public int Index;

  public int LowLink;

  public AbstractVariable Value;

  public List<Vertex> Dependencies;
}