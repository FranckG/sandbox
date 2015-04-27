/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.remote.services.internal.context;

import org.eclipse.core.runtime.Assert;

import com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListener;
import com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListenerProxy;
import com.thalesgroup.orchestra.framework.remote.services.context.serialization.SerializationHelper;

/**
 * Remote listener proxy implementation.<br>
 * Wraps an {@link IRemoteContextChangeListener} implementation so as to handle the serialization issue.
 * @author t0076261
 */
public class RemoteContextChangeListenerProxy implements IRemoteContextChangeListenerProxy {
  /**
   * Serial UID.
   */
  private static final long serialVersionUID = 7421020891352942441L;
  /**
   * Real listener implementation.
   */
  private IRemoteContextChangeListener _listener;
  /**
   * Serialization helper.
   */
  private SerializationHelper _serializationHelper;

  /**
   * Constructor.
   * @param listener_p The listener implementation. Must be not <code>null</code>.
   */
  public RemoteContextChangeListenerProxy(IRemoteContextChangeListener listener_p) {
    Assert.isNotNull(listener_p);
    _listener = listener_p;
    _serializationHelper = new SerializationHelper();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListenerProxy#getName()
   */
  public String getName() {
    return _listener.getName();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListenerProxy#postContextChange(java.lang.String)
   */
  public String postContextChange(String contextName_p) {
    return _serializationHelper.serializeStatus(_listener.postContextChange(contextName_p));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListenerProxy#preContextChange(java.lang.String)
   */
  public String preContextChange(String contextName_p) {
    return _serializationHelper.serializeStatus(_listener.preContextChange(contextName_p));
  }
}