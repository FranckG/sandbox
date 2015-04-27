/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Orchestra Framework UI test suite.
 * @author t0076261
 */
@RunWith(Suite.class)
@SuiteClasses({ TestConnector.class })
public class IntegrationTestSuite {
  /**
   * Bot reference.
   */
  private static SWTWorkbenchBot __bot;

  /**
   * Get bot.
   * @return
   */
  public static SWTWorkbenchBot getBot() {
    if (null == __bot) {
      __bot = new SWTWorkbenchBot();
    }
    return __bot;
  }
}