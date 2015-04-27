/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.value;

import java.util.List;
import java.util.Stack;

public final class TarjanCycleDetect {
  private static StronglyConnectedComponentList _stronglyConnectedComponents;
  private static Stack<Vertex> _stack;
  private static int _index;

  public static StronglyConnectedComponentList detectCycle(List<Vertex> g_p) {
    _stronglyConnectedComponents = new StronglyConnectedComponentList();
    _index = 0;
    _stack = new Stack<Vertex>();
    for (Vertex v : g_p) {
      if (v.Index < 0) {
        strongConnect(v);
      }
    }
    return _stronglyConnectedComponents;
  }

  /**
   * @param v_p
   */
  private static void strongConnect(Vertex v_p) {
    v_p.Index = _index;
    v_p.LowLink = _index;
    _index++;
    _stack.push(v_p);

    for (Vertex w : v_p.Dependencies) {
      if (w.Index < 0) {
        strongConnect(w);
        v_p.LowLink = Math.min(v_p.LowLink, w.LowLink);
      } else if (_stack.contains(w)) {
        v_p.LowLink = Math.min(v_p.LowLink, w.Index);
      }
    }
    if (v_p.LowLink == v_p.Index) {
      StronglyConnectedComponent scc = new StronglyConnectedComponent();
      Vertex w;
      do {
        w = _stack.pop();
        scc.add(w);
      } while (v_p != w);
      _stronglyConnectedComponents.add(scc);
    }
  }
}