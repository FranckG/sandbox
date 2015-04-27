/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests.initializer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.integration.tests.application.handler.IPrePostTestHandler;

/**
 * @author T0052089
 */
public abstract class AbstractConnectorConfiguration implements IPrePostTestHandler {
  public abstract IStatus installConfDir();
  public abstract IStatus installConnector();

  protected String _registryKey = "SOFTWARE\\Wow6432Node\\Thales\\EPM\\Orchestra\\Products\\OrchestraFramework";
  
  public void postAction() {
    //uninstallConnector();
    uninstallConfDir();
  }

  public void preAction() {
    IStatus status = installConnector();
    if (status != Status.OK_STATUS) {
      System.out.println("Cannot install connector: "+status.getMessage()+". "+status.getException().getMessage());
    }
    
    status = installConfDir();
    if (status != Status.OK_STATUS) {
      System.out.println("Cannot install configuration diretctory: "+status.getMessage()+". "+status.getException().getMessage());
    }
  }

  public abstract IStatus uninstallConfDir();

  public abstract IStatus uninstallConnector();
}
