/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests.connector.mockecf;

import java.util.concurrent.CountDownLatch;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.connector.AbstractConnector;
import com.thalesgroup.orchestra.framework.connector.Artifact;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector;
import com.thalesgroup.orchestra.framework.integration.tests.helpers.IntegrationTestsHelper;
import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;

/**
 * @author T0052089
 */
public class MockEcfConnector extends AbstractConnector implements IRemoteConnector {
  /**
   * Type of this ECF connector.
   */
  public static final String MOCK_ECF_CONNECTOR_TYPE = "MockEcf"; //$NON-NLS-1$
  /**
   * Latch used to signal the running application that it has to close.
   */
  private CountDownLatch _closeApplicationSignal;

  /**
   * @param closeApplicationSignal_p Latch on which the application is waiting on.
   */
  public MockEcfConnector(CountDownLatch closeApplicationSignal_p) {
    _closeApplicationSignal = closeApplicationSignal_p;
  }

  /**
   * Generate a command status.
   * @param context_p
   * @param mainStatusMessage_p
   * @return
   */
  protected CommandStatus allOk(CommandContext context_p, String mainStatusMessage_p) {
    CommandStatus result = new CommandStatus(IStatus.OK, mainStatusMessage_p, null, 0);
    for (Artifact artefact : context_p.getArtifacts()) {
      // Add an OK CommandStatus for each artifact.
      result.addChild(new CommandStatus(IStatus.OK, "", artefact.getUri(), 0)); //$NON-NLS-1$
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#closeApplication()
   */
  @Override
  public CommandStatus closeApplication() throws Exception {
    if (null != _closeApplicationSignal) {
      new Thread(new Runnable() {
        /**
         * @see java.lang.Runnable#run()
         */
        @SuppressWarnings("synthetic-access")
        public void run() {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException exception_p) {
            // Interrupted.
          }
          _closeApplicationSignal.countDown();
        }
      }).start();

    }
    return new CommandStatus(Status.OK_STATUS);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#create(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus create(CommandContext context_p) throws Exception {
    CommandStatus createStatus = allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getType(), ICommandConstants.CREATE));
    return createStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#documentaryExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus documentaryExport(CommandContext context_p) throws Exception {
    CommandStatus documentaryExportStatus =
        allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getType(), ICommandConstants.EXPORT_DOC));
    return documentaryExportStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#executeSpecificCommand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus executeSpecificCommand(CommandContext context_p) throws Exception {
    // Unlike other commands, the status message of the main CommandStatus is filled using the asked specific command.
    CommandStatus executeSpecificCommandStatus =
        allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getType(), context_p.getCommandType()));
    return executeSpecificCommandStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#expand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus expand(CommandContext context_p) throws Exception {
    CommandStatus expandStatus = allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getType(), ICommandConstants.EXPAND));
    return expandStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#getType()
   */
  @Override
  public String getType() throws Exception {
    return MOCK_ECF_CONNECTOR_TYPE;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#importArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus importArtifacts(CommandContext context_p) throws Exception {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#isHandlingArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus isHandlingArtifacts(CommandContext context_p) throws Exception {
    return new CommandStatus(Status.OK_STATUS);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#lmExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus lmExport(CommandContext context_p) throws Exception {
    CommandStatus lmExportStatus = allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getType(), ICommandConstants.EXPORT_LM));
    return lmExportStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#navigate(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus navigate(CommandContext context_p) throws Exception {
    CommandStatus navigateStatus = allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getType(), context_p.getCommandType()));
    return navigateStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#search(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus search(CommandContext context_p) throws Exception {
    CommandStatus searchStatus = allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getType(), ICommandConstants.SEARCH));
    return searchStatus;
  }
}
