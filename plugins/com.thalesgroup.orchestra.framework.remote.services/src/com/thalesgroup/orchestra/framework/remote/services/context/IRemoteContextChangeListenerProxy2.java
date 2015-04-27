/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.remote.services.context;

import java.io.Serializable;

import org.eclipse.core.runtime.IStatus;

/**
 * This is a pure connection proxy.<br>
 * This is not intended to be implemented by the client side.<br>
 * Instead implement {@link IRemoteContextChangeListener} and register it.
 * @author t0076261
 */
public interface IRemoteContextChangeListenerProxy2 extends Serializable {
  /**
   * The name of the remote listener.
   * @return A not <code>null</code>, neither empty {@link String}.
   */
  String getName();

  /**
   * Named context has just been applied.<br>
   * The listener should try and adapt to the change.
   * @param contextName_p The name of the context about to be applied.
   * @param allowUserInteractions_p
   * @return A serialized form of an {@link IStatus}.
   */
  String postContextChange(String contextName_p, boolean allowUserInteractions_p);

  /**
   * Named context is about to be applied by the user.<br>
   * The listener is to tell whether it is able to handle such a change or not.
   * @param contextName_p The name of the context about to be applied.
   * @return A serialized form of an {@link IStatus}.
   */
  String preContextChange(String contextName_p);
}