/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model;

import java.util.List;

import com.thalesgroup.orchestra.framework.model.contexts.Context;

/**
 * @author T0052089
 */
public interface ISelectedContextsProvider {
  public List<Context> getSelectedContexts();
}
