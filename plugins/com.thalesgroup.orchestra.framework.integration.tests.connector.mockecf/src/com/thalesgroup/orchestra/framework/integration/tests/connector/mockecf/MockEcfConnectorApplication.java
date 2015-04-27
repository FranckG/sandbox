package com.thalesgroup.orchestra.framework.integration.tests.connector.mockecf;

import java.util.concurrent.CountDownLatch;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.thalesgroup.orchestra.framework.ecf.services.handler.ClientConnectorHandler;

/**
 * This class controls all aspects of the application's execution
 */
public class MockEcfConnectorApplication implements IApplication {
  private ClientConnectorHandler _clientConnectorHandler;
  private CountDownLatch _closeApplicationSignal;
  private MockEcfConnector _remoteConnectorImplementation;

  /*
   * (non-Javadoc)
   * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
   */
  public Object start(IApplicationContext context) throws Exception {
    _closeApplicationSignal = new CountDownLatch(1);
    _clientConnectorHandler = new ClientConnectorHandler();
    _remoteConnectorImplementation = new MockEcfConnector(_closeApplicationSignal);
    IStatus registerResult = _clientConnectorHandler.register(_remoteConnectorImplementation);
    System.out.println(registerResult);
    // Wait for closeApplication to be called.
    _closeApplicationSignal.await();
    IStatus unregisterResult = _clientConnectorHandler.unregister(_remoteConnectorImplementation);
    System.out.println(unregisterResult);
    return IApplication.EXIT_OK;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.equinox.app.IApplication#stop()
   */
  public void stop() {
    // Force close.
    _closeApplicationSignal.countDown();
  }
}
