/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ecf.services.launcher;

import org.eclipse.core.runtime.IStatus;

/**
 * An application launcher.<br>
 * See launcher extension point definition.
 * @author t0076261
 */
public interface ILauncher {
  /**
   * Launch application for specified context.<br>
   * @param applicationContext_p
   * @return
   */
  public IStatus launchApplication(IApplicationContext applicationContext_p);

  /**
   * The application launching context.
   * @author t0076261
   */
  public interface IApplicationContext {
    /**
     * Targeted type.
     * @return
     */
    public String getType();

    /**
     * Launch arguments
     * @return
     */
    public String getLaunchArguments();
  }
}