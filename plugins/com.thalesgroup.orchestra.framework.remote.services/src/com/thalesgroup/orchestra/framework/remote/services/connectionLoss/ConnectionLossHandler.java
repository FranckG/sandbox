/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.remote.services.connectionLoss;

import org.eclipse.ecf.core.IContainerListener;
import org.eclipse.ecf.core.events.IContainerEjectedEvent;
import org.eclipse.ecf.core.events.IContainerEvent;

import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;
import com.thalesgroup.orchestra.framework.lib.helper.ConnectionHelper;

/**
 * Handle connection losses and reconnection events
 * @author s0040806
 */
public class ConnectionLossHandler implements IContainerListener {

  private boolean notified = false;

  private IConnectionLossListener _connectionLossListener;
  private IReconnectionListener _reconnectionListener;

  private Thread _reconnectionThread;

  // Ping frequency in ms
  private int PING_FREQ = 5000;

  /**
   * @see org.eclipse.ecf.core.IContainerListener#handleEvent(org.eclipse.ecf.core.events.IContainerEvent)
   */
  public void handleEvent(IContainerEvent event_p) {
    if (event_p instanceof IContainerEjectedEvent) {
      if (null != _connectionLossListener) {
        if (!notified) {
          // Avoid concurrent notifications first in case the callback calls the connection handler
          notified = true;
          // The run the callback
          _connectionLossListener.handleConnectionLoss();
          // Start the reconnection thread if necessary
          if (null != _reconnectionListener) {
            startReconnectionThread();
          }
        }
      }
    }
  }

  public void startReconnectionThread() {
    _reconnectionThread = new Thread("ConnectionHandler Reconnection Thread") {
      /**
       * @see java.lang.Thread#run()
       */
      @SuppressWarnings("synthetic-access")
      @Override
      public void run() {
        try {
          while (!ConnectionHelper.ping()) {
            Thread.sleep(PING_FREQ);
            // Reload server configuration parameter in order to update the port number to ping
            ServerConfParam.getInstance().readFile();
          }
          _reconnectionListener.handleReconnection();
          resetNotifications();
        } catch (InterruptedException exception_p) {
          // Nothing to do
        }
      }
    };
    _reconnectionThread.start();
  }

  public void setConnectionLossListener(IConnectionLossListener listener_p) {
    _connectionLossListener = listener_p;
  }

  public void unsetConnectionLossListener() {
    _connectionLossListener = null;
  }

  public void resetNotifications() {
    notified = false;
  }

  public void setReconnectionListener(IReconnectionListener listener_p) {
    _reconnectionListener = listener_p;
  }

  public void unsetReconnectionListener() {
    _reconnectionListener = null;
  }
}
