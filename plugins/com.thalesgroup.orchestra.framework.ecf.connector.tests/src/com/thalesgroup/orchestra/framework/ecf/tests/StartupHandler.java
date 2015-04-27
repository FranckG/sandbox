/**
 * 
 */
package com.thalesgroup.orchestra.framework.ecf.tests;

import org.eclipse.ui.IStartup;

import com.thalesgroup.orchestra.framework.ecf.services.handler.ClientConnectorHandler;

/**
 * @author t0076261
 */
public class StartupHandler implements IStartup {
  /**
   * Shared instance.
   */
  private static StartupHandler __instance;
  /**
   * Connector handler.
   */
  public ClientConnectorHandler _handler;

  /**
   * Constructor.
   */
  public StartupHandler() {
    __instance = this;
  }

  /**
   * @see org.eclipse.ui.IStartup#earlyStartup()
   */
  public void earlyStartup() {
    try {
      _handler = new ClientConnectorHandler();
      _handler.register(new EcfTestConnector());
      new ClientConnectorHandler().register(new EcfTestConnector());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Get shared instance.
   * @return
   */
  public static StartupHandler getInstance() {
    return __instance;
  }
}