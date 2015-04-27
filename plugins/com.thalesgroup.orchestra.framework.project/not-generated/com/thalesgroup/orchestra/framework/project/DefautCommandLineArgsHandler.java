/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;

/**
 * Default implementation.
 * @author T0052089
 */
public class DefautCommandLineArgsHandler implements ICommandLineArgsHandler {
  /**
   * @see com.thalesgroup.orchestra.framework.project.ICommandLineArgsHandler#getAdminContextsPoolLocation()
   */
  public String getAdminContextsPoolLocation() {
    return ICommonConstants.EMPTY_STRING;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.project.ICommandLineArgsHandler#getAdminContextToAutoJoin()
   */
  public String getAdminContextToAutoJoin() {
    return ICommonConstants.EMPTY_STRING;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.project.ICommandLineArgsHandler#getBaselinesRepositoryPath()
   */
  public String getBaselinesRepositoryPath() {
    return ICommonConstants.EMPTY_STRING;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.project.ICommandLineArgsHandler#minimizeAsked()
   */
  public boolean minimizeAsked() {
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.project.ICommandLineArgsHandler#userModeOnly()
   */
  public boolean userModeOnly() {
    return false;
  }
}
