/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.services;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.connector.helper.SerializationHelper;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.puci.PUCI;

/**
 * A bridge to Framework services dedicated to connectors.
 * @author t0076261
 */
public class ConnectorFrameworkServices {
  /**
   * Artifacts metadata URI parameter : environment ID.
   */
  public static String URI_PARAMETER_METADATA_ENVIRONMENT_ID = "environmentId"; //$NON-NLS-1$

  /**
   * Get artifacts metadata for specified URIs.<br>
   * These URIs may not be absolute, and contain the following parameter :<br>
   * <ul>
   * <li><code>environmentId</code>=The environment runtime Id, as provided by an {@link Artifact} in considered connector {@link CommandContext}</li>
   * </ul>
   * @param URIs_p
   * @return
   */
  public static CommandResult getArtifactsMetadata(List<OrchestraURI> URIs_p) {
    // Precondition.
    if ((null == URIs_p) || URIs_p.isEmpty()) {
      return null;
    }
    CommandResult result = new ConnectorFrameworkServices().new CommandResult();
    // Do execute the command.
    Map<String, String> resultingMap = null;
    try {
      resultingMap = PUCI.executeSpecificCommand(ICommandConstants.GET_ARTIFACTS_METADATA, URIs_p);
    } catch (Exception exception_p) {
      CommandStatus status =
          new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.ConnectorFrameworkServices_MetaData_Error,
              exception_p.toString()), null, 0);
      for (OrchestraURI orchestraURI : URIs_p) {
        status.addChild(new CommandStatus(IStatus.ERROR, "", orchestraURI, 0)); //$NON-NLS-1$
      }
      result._status = status;
      return result;
    }
    // Status handler.
    String responseFilePath = resultingMap.get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH);
    StatusHandler statusHandler = new StatusHandler();
    try {
      StatusDefinition statusDefinition = statusHandler.loadModel(responseFilePath);
      Status status = statusDefinition.getStatus().getStatus().get(0);
      result._gefAbsolutePath = status.getExportFilePath();
      result._status = new SerializationHelper().buildStatus(status);
      return result;
    } finally {
      statusHandler.clean();
    }
  }

  /**
   * A command result.
   * @author t0076261
   */
  public class CommandResult {
    /**
     * Resulting Gef model absolute path.
     */
    public String _gefAbsolutePath;
    /**
     * Resulting status.
     */
    public CommandStatus _status;
  }
}