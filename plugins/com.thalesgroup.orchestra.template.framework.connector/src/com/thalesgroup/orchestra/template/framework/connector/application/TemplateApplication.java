/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.template.framework.connector.application;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector;
import com.thalesgroup.orchestra.framework.ecf.services.handler.ClientConnectorHandler;
import com.thalesgroup.orchestra.template.framework.connector.impl.TemplateRemoteConnector;

/**
 * An application that handles remote connector declaration to the Framework.
 * @author t0076261
 */
public class TemplateApplication implements IApplication {
  /**
   * The connector handler reference.
   */
  protected ClientConnectorHandler _handler;
  /**
   * A remote connector implementation.
   */
  protected IRemoteConnector _remoteConnector;

  /**
   * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
   */
  public Object start(IApplicationContext context_p) throws Exception {
    _handler = new ClientConnectorHandler();
    _remoteConnector = new TemplateRemoteConnector();
    IStatus status = _handler.register(_remoteConnector);
    if (!status.isOK()) {
      // TODO Log or display status to user.
      _remoteConnector = null;
    }
    // TODO Call application specific code here.
    // ...
    // ...
    return null;
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
      // Unregister connector.
      if (null != _remoteConnector) {
        IStatus status = _handler.unregister(_remoteConnector);
        if (!status.isOK()) {
          // TODO Log or display status to user.
        }
        _handler = null;
        _remoteConnector = null;
      }
    }
  }
}