/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;

import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentUseBaselineViewer;

/**
 * Default implementation for EnvironmentUseBaselineHandler.
 */
public class DefaultEnvironmentUseBaselineHandler implements IEnvironmentUseBaselineHandler {

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentUseBaselineHandler#getReferenceViewer(com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext)
   */
  public AbstractEnvironmentUseBaselineViewer getReferenceViewer(UseBaselineEnvironmentContext referenceContext_p) {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentUseBaselineHandler#getStartingPointViewer(com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext)
   */
  public AbstractEnvironmentUseBaselineViewer getStartingPointViewer(UseBaselineEnvironmentContext startingPointContext_p) {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentUseBaselineHandler#isReferenceContextValid(com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext)
   */
  public boolean isReferenceContextValid(UseBaselineEnvironmentContext referenceContext_p) {
    return true;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentUseBaselineHandler#isStartingPointContextValid(com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext)
   */
  public boolean isStartingPointContextValid(UseBaselineEnvironmentContext envCtx_p) {
    return true;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentUseBaselineHandler#isSameUseBaselineContext(com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext,
   *      com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext)
   */
  public boolean isSameUseBaselineContext(UseBaselineEnvironmentContext startingPointContext1_p, UseBaselineEnvironmentContext startingPointContext2_p) {
    return false;
  }
}