/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ecf.tests;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.PlatformUI;

import com.thalesgroup.orchestra.framework.connector.AbstractConnector;
import com.thalesgroup.orchestra.framework.connector.Artifact;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector;

/**
 * @author t0076261
 */
@SuppressWarnings("nls")
public class EcfTestConnector extends AbstractConnector implements IRemoteConnector {
  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#closeApplication()
   */
  public CommandStatus closeApplication() throws Exception {
    PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException exception_p) {
          // TOO BAD !
        }
        StartupHandler.getInstance()._handler.unregister(EcfTestConnector.this);
        PlatformUI.getWorkbench().close();
      }
    });
    return new CommandStatus(IStatus.OK, "Closing application...", null, 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#create(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus create(CommandContext context_p) throws Exception {
    return new CommandStatus(IStatus.ERROR, "", null, 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#documentaryExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus documentaryExport(CommandContext context_p) throws Exception {
    return new CommandStatus(IStatus.ERROR, "", null, 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#executeSpecificCommand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus executeSpecificCommand(CommandContext context_p) throws Exception {
    return new CommandStatus(IStatus.ERROR, "", null, 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#expand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus expand(CommandContext context_p) throws Exception {
    return new CommandStatus(IStatus.ERROR, "", null, 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#getType()
   */
  public String getType() throws Exception {
    return "EcfTest"; //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#importArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus importArtifacts(CommandContext context_p) throws Exception {
    CommandStatus resultingStatus = new CommandStatus(IStatus.OK, "", null, 0);
    for (Artifact artifact : context_p.getArtifacts()) {
      resultingStatus.addChild(new CommandStatus(IStatus.OK, "Import ok", artifact.getUri(), 0));
    }
    EcfTestActivator.getInstance().getLog().log(resultingStatus);
    return resultingStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#isHandlingArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus isHandlingArtifacts(CommandContext context_p) throws Exception {
    return new CommandStatus(IStatus.ERROR, "", null, 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#isHappyToBeHere()
   */
  public boolean isHappyToBeHere() throws Exception {
    return true;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#lmExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus lmExport(CommandContext context_p) throws Exception {
    return new CommandStatus(IStatus.ERROR, "", null, 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#navigate(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus navigate(CommandContext context_p) throws Exception {
    CommandStatus resultingStatus = new CommandStatus(IStatus.OK, "", null, 0);
    for (Artifact artifact : context_p.getArtifacts()) {
      resultingStatus.addChild(new CommandStatus(IStatus.OK, "Navigation ok", artifact.getUri(), 0));
    }
    return resultingStatus;
  }
}