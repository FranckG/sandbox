/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests.helpers;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.debug.core.ILaunch;

import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;

/**
 * @author T0052089
 */
public class IntegrationTestsHelper {
  /**
   * Artifact path for integration tests.
   */
  public static final String __integrationTestsBaseArtefactPath = getTemporaryFolder("IntegrationTestsArtefactDirectory"); //$NON-NLS-1$
  /**
   * Configuration directory path for integration tests.
   */
  public static final String __integrationTestsBaseConfDirPath = getTemporaryFolder("IntegrationTestsConfigurationDirectory"); //$NON-NLS-1$
  /**
   * Integration tests connector installation root.
   */
  public static final String __integrationTestsBaseInstallationPath = getTemporaryFolder("IntegrationTestsInstallationDirectory"); //$NON-NLS-1$
  /**
   * Last launched ECF connector.
   */
  public static ILaunch __lastLaunchedEcfConnector;

  /**
   * Generate test connectors' main status string.
   * @param connectorClass_p
   * @param commandType_p
   * @return
   */
  public static String generateConnectorMainStatusMessage(String connectorType_p, String commandType_p) {
    return connectorType_p + " " + commandType_p; //$NON-NLS-1$
  }

  /**
   * Get temporary folder for tests.<br>
   * This ensures that returned path always exists.
   * @return
   */
  protected static String getTemporaryFolder(String prefix_p) {
    String folderPath = System.getProperty("java.io.tmpdir") + prefix_p; //$NON-NLS-1$
    File folder = new File(folderPath);
    if (!folder.exists()) {
      folder.mkdirs();
    }
    return folderPath;
  }

  /**
   * Check the framework response against given arguments.
   * @param frameworkResponse_p
   * @param expectedMainMessage_p
   * @param expectedMainSeverity_p
   * @param expectedUriToSeverity_p
   * @return
   */
  public static boolean isExpectedResponse(Map<String, String> frameworkResponse_p, String expectedMainMessage_p, SeverityType expectedMainSeverity_p,
      Map<String, SeverityType> expectedUriToSeverity_p) {
    String reponseFilePath = frameworkResponse_p.get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH);
    if (null == reponseFilePath) {
      return false;
    }
    StatusHandler statusHandler = new StatusHandler();
    try {
      StatusDefinition statusModel = statusHandler.loadModel(reponseFilePath);
      Status containerStatus = statusModel.getStatus();
      if (SeverityType.INFO != containerStatus.getSeverity())
        return false;

      // Cycle through expected URIs.
      for (Entry<String, SeverityType> entry : expectedUriToSeverity_p.entrySet()) {
        // Get status for expected URI.
        Status status = statusHandler.getStatusForUri(statusModel, entry.getKey());
        // Make sure status is found.
        if (null == status) {
          return false;
        }
        // Then test severity.
        SeverityType severityType = status.getSeverity();
        // Make sure there is a severity to test.
        if (null == severityType) {
          return false;
        }
        // Unexpected severity for specified artifact.
        if (!severityType.equals(entry.getValue())) {
          return false;
        }
      }
      return true;
    } finally {
      statusHandler.clean();
    }
  }
}