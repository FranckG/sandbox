/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.activator;

import org.eclipse.core.runtime.Plugin;

/**
 * A default activator that resolved its plug-in ID by code (instead of using a constant).
 * @author t0076261
 */
public class AbstractActivator extends Plugin {
  /**
   * Get plug-in ID (according to the manifest definition).
   * @return
   */
  public String getPluginId() {
    return getBundle().getSymbolicName();
  }
}