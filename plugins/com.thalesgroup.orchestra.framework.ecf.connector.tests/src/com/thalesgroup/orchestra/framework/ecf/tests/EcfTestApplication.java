/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ecf.tests;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector;
import com.thalesgroup.orchestra.framework.ecf.services.handler.ClientConnectorHandler;

/**
 * @author t0076261
 */
public class EcfTestApplication implements IApplication {
  /**
   * Shared instance.
   */
  public static EcfTestApplication __instance;
  /**
   * Latch dialog.
   */
  public ErrorDialog _dialog;
  /**
   * In-use display.
   */
  public Display _display;
  /**
   * Connector handler.
   */
  public ClientConnectorHandler _handler;

  /**
   * Default constructor.
   */
  public EcfTestApplication() {
    __instance = this;
  }

  /**
   * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
   */
  @SuppressWarnings("nls")
  public Object start(IApplicationContext context_p) throws Exception {
    final IRemoteConnector[] connector = new IRemoteConnector[] { null };
    _display = new Display();
    Shell shell = new Shell(_display);
    _dialog =
        new ErrorDialog(shell, "Ecf test connector application", "Ecf test application is currently running", new Status(IStatus.ERROR, "EcfTest",
            "Running ecf test application..."), IStatus.OK | IStatus.INFO | IStatus.CANCEL | IStatus.WARNING | IStatus.ERROR);
    new Thread(new Runnable() {
      public void run() {
        try {
          _handler = new ClientConnectorHandler();
          connector[0] = new EcfTestConnector();
          _handler.register(connector[0]);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }).start();
    _dialog.open();
    _handler.unregister(connector[0]);
    return null;
  }

  /**
   * @see org.eclipse.equinox.app.IApplication#stop()
   */
  public void stop() {
    // Nothing to do.
  }
}