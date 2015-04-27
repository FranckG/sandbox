/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;

import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentUseBaselineViewer;

/**
 * The environment "use baseline" handler is responsible for managing "use baseline" attributes and creating viewer for these use baseline attributes.
 */
public interface IEnvironmentUseBaselineHandler {
  /**
   * Create an EnvironmentUseBaselineViewer to allow the user to give starting point parameters. Starting point parameters have to be stored in the map given by
   * {@link UseBaselineEnvironmentContext#getUseBaselineAttributes()}.
   * @param startingPointContext_p
   * @return an EnvironmentUseBaselineViewer or <code>null</code> if "use baseline" parameters don't need to be configured for this environment.
   */
  public AbstractEnvironmentUseBaselineViewer getStartingPointViewer(UseBaselineEnvironmentContext startingPointContext_p);

  /**
   * Create an EnvironmentUseBaselineViewer to allow the user to give reference parameters. Reference parameters have to be stored in the map given by
   * {@link UseBaselineEnvironmentContext#getUseBaselineAttributes()}.
   * @param referenceContext_p
   * @return an EnvironmentUseBaselineViewer or <code>null</code> if "use baseline" parameters don't need to be configured for this environment.
   */
  public AbstractEnvironmentUseBaselineViewer getReferenceViewer(UseBaselineEnvironmentContext referenceContext_p);

  /**
   * If two "use baseline" contexts are equal, they share the same "use baseline" parameters (only one will be displayed for the user to configure it). <br>
   * @param useBaselineContext1_p
   * @param useBaselineContext2_p
   * @return <code>true</code> if "use baseline" context are equal, <code>false</code> else.
   */
  public boolean isSameUseBaselineContext(UseBaselineEnvironmentContext useBaselineContext1_p, UseBaselineEnvironmentContext useBaselineContext2_p);

  /**
   * Are starting point parameters valid ?<br>
   * This method is automatically invoked by the wizard when the {@link AbstractEnvironmentUseBaselineViewer} implementation asks for an update on the wizard
   * buttons.<br>
   * This is the place to decide whether or not the wizard can be completed.<br>
   * Note that even if the implementation returns <code>true</code>, the wizard might not be finished because of other implementations returning
   * <code>false</code>.
   * @param startingPointContext_p The environments attributes as edited by the {@link AbstractEnvironmentUseBaselineViewer} implementation (or baseline ones if
   *          no viewer is provided).
   * @return
   */
  public boolean isStartingPointContextValid(UseBaselineEnvironmentContext startingPointContext_p);

  /**
   * Are reference parameters valid ?<br>
   * This method is automatically invoked by the wizard when the {@link AbstractEnvironmentUseBaselineViewer} implementation asks for an update on the wizard
   * buttons.<br>
   * This is the place to decide whether or not the wizard can be completed.<br>
   * Note that even if the implementation returns <code>true</code>, the wizard might not be finished because of other implementations returning
   * <code>false</code>.
   * @param referenceContext_p The environments attributes as edited by the {@link AbstractEnvironmentUseBaselineViewer} implementation (or baseline ones if no
   *          viewer is provided).
   * @return
   */
  public boolean isReferenceContextValid(UseBaselineEnvironmentContext referenceContext_p);
}