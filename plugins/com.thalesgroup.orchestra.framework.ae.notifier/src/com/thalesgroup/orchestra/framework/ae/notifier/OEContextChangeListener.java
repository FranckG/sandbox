/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.notifier;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListener;

/**
 * @author S0032874
 */
public class OEContextChangeListener implements IRemoteContextChangeListener {

  private final String listenerName = "OEContextChangeListener"; //$NON-NLS-1$

  /**
   * @see com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListener#getName()
   */
  public String getName() {
    return listenerName;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListener#postContextChange(java.lang.String)
   */
  public IStatus postContextChange(String contextName_p) {
    // Set the clearSurArtefacts flag to true as a change context happened
    OrchestraExplorerActivator.getDefault().setClearSubArtefact(true);
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListener#preContextChange(java.lang.String)
   */
  public IStatus preContextChange(String contextName_p) {
    // Nothing to do
    return null;
  }

}
