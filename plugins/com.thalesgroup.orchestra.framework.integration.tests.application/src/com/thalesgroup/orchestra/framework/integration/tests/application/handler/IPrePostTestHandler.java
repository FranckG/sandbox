/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests.application.handler;

/**
 * @author S0024585
 */
public interface IPrePostTestHandler {

  /**
   * After framework stop
   */
  public void postAction();

  /**
   * Before framework start
   */
  public void preAction();

}
