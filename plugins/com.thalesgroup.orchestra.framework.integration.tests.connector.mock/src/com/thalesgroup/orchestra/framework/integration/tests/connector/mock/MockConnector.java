/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests.connector.mock;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.connector.AbstractConnector;
import com.thalesgroup.orchestra.framework.connector.Artifact;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.integration.tests.helpers.IntegrationTestsHelper;
import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;

/**
 * @author S0024585
 */
public class MockConnector extends AbstractConnector {
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
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#create(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus create(CommandContext context_p) throws Exception {
    CommandStatus createStatus = allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getConnectorType(), ICommandConstants.CREATE));
    return createStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#documentaryExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus documentaryExport(CommandContext context_p) throws Exception {
    CommandStatus documentaryExportStatus =
        allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getConnectorType(), ICommandConstants.EXPORT_DOC));
    return documentaryExportStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#executeSpecificCommand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus executeSpecificCommand(CommandContext context_p) throws Exception {
    // Unlike other commands, the status message of the main CommandStatus is filled using the asked specific command.
    CommandStatus executeSpecificCommandStatus =
        allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getConnectorType(), context_p.getCommandType()));
    return executeSpecificCommandStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#expand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus expand(CommandContext context_p) throws Exception {
    CommandStatus expandStatus = allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getConnectorType(), ICommandConstants.EXPAND));
    return expandStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.AbstractConnector#lmExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus lmExport(CommandContext context_p) throws Exception {
    CommandStatus lmExportStatus = allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getConnectorType(), ICommandConstants.EXPORT_LM));
    return lmExportStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#navigate(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus navigate(CommandContext context_p) throws Exception {
    CommandStatus navigateStatus = allOk(context_p, IntegrationTestsHelper.generateConnectorMainStatusMessage(getConnectorType(), ICommandConstants.NAVIGATE));
    return navigateStatus;
  }

}
