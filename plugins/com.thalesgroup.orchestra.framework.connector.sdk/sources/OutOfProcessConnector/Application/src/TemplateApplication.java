/**
 * Copyright (c) THALES, 2011-2012. All rights reserved.
 */
package {0}.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector;
import com.thalesgroup.orchestra.framework.ecf.services.handler.ClientConnectorHandler;
import {0}.impl.{1}RemoteConnector;

/**
 * An application that handles remote connector declaration to the Framework.
 * @author TBD
 */
public class {1}Application implements IApplication '{
  /**
   * Application main awaiting semaphore.
   */
  private CountDownLatch _countDownLatch;
  /**
   * The connector handler reference.
   */
  protected ClientConnectorHandler _handler;
  /**
   * A remote connector implementations.
   */
  protected List<IRemoteConnector> _remoteConnectors;
  /**
   * Shared instance.
   */
  public static '{1}'Application __instance;

  /**
   * Constructor.
   */
  public '{1}'Application() {
    __instance = this;
  }
  
  /**
   * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
   */
  public Object start(IApplicationContext context_p) throws Exception {
    _countDownLatch = new CountDownLatch(1);
    _handler = new ClientConnectorHandler();
    _remoteConnectors = new ArrayList<IRemoteConnector>();
    // Register connectors.
    '{2}'
    // TODO Call application specific code here.
    // ...
    // ...
    // Wait for the end of application.
    _countDownLatch.await();
    return IApplication.EXIT_OK;
  }

  /**
   * Register a connector for the given type.
   * @param connectorType_p artefact type associated to this connector.
   */
  public IStatus registerConnectorForType(String connectorType_p) {
    'IRemoteConnector remoteConnector = new {1}RemoteConnector(connectorType_p);'
    IStatus status = _handler.register(remoteConnector);
    if (status.isOK()) {
      _remoteConnectors.add(remoteConnector);
    }
    return status;
  }
  
  /**
   * @see org.eclipse.equinox.app.IApplication#stop()
   */
  public void stop() {
    try {
      // TODO Call application specific code here.
      // ...
      // ...
    } finally {
      // Unregister connectors.
      for (IRemoteConnector remoteConnector : _remoteConnectors) {
        IStatus status = _handler.unregister(remoteConnector);
        if (!status.isOK()) {
          // TODO Log or display status to user.
        }
      }
      _remoteConnectors.clear();
      _handler = null;
      _countDownLatch.countDown();
    }
  }
}'