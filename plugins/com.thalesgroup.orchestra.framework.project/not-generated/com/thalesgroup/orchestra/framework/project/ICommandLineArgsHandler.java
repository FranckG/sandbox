/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;

/**
 * Interface for a command line arguments handler.
 * @author T0052089
 */
public interface ICommandLineArgsHandler {
  /**
   * Returns the directory path of the admin contexts pool.<br>
   * @return the directory path if the location is specified and existing, {@link ICommonConstants#EMPTY_STRING} otherwise.
   */
  public String getAdminContextsPoolLocation();

  /**
   * Admin context from the pool to auto join at startup.
   * @return the name of the admin context if specified, {@link ICommonConstants#EMPTY_STRING} otherwise.
   */
  public String getAdminContextToAutoJoin();

  /**
   * Get baselines repository absolute path on FS.<br>
   * Path can be a UNC path if required.<br>
   * Default value is {@link ICommonConstants#EMPTY_STRING}.<br>
   * If none is specified, baseline capability will not be accessible.
   * @return
   */
  public String getBaselinesRepositoryPath();

  /**
   * Should the main window be minimized ?
   * @return
   */
  public boolean minimizeAsked();

  /**
   * Should OF work in user mode only ?
   * @return
   */
  public boolean userModeOnly();
}