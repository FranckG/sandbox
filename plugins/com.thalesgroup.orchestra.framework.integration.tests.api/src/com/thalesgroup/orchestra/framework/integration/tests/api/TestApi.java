/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.model.migration.AbstractMigration;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.puci.PUCI;
import com.thalesgroup.orchestra.framework.puci.PUCI.OdmMode;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class TestApi {
  /**
   * Time between two pings.
   */
  public static final int PING_RETRY_INTERVAL = 1000;
  /**
   * Ping test duration.
   */
  public static final int PING_RETRY_DURATION = 30000;
  /**
   * Time to wait between an OK ping and the start of the test operation.
   */
  public static final int ORCHESTRA_FRAMEWORK_START_TIME = 10000;
  /**
   * Orchestra Framework ping address.
   */
  public static final String PING_ADDRESS = "http://localhost:{0}/ping";
  /**
   * Admin context name.
   */
  public static final String ADMIN_CONTEXT_NAME = "ApiAdminContextTest";
  /**
   * User context name.
   */
  public static final String USER_CONTEXT_NAME = "ApiUserContextTest";

  @After
  public void closeOrchestraFramework() throws Exception {
    // Close the Framework.
    Map<String, String> closeFrameworkResult =
        PUCI.executeSpecificCommand("CloseFramework", Collections.singletonList(new OrchestraURI("FrameworkCommands", "FC")));
    Status closeFrameworkStatus = getPUCIResultStatus(closeFrameworkResult);
    assertTrue(closeFrameworkStatus.getMessage(), SeverityType.OK.equals(closeFrameworkStatus.getSeverity()));
  }

  /**
   * Test steps:
   * <ol>
   * <li>Wait until the Framework is started (do pings),</li>
   * <li>Create an admin context,</li>
   * <li>Create an user context,</li>
   * <li>Import the context project,</li>
   * <li>Set the user context as current,</li>
   * <li>Re-create the user context,</li>
   * <li>Re-import the context project,</li>
   * <li>Re-set the user context as current.</li>
   * </ol>
   * @throws Exception
   */
  @Test
  public void switchToUserContextTest() throws Exception {
    /*
     * Test preparation.
     */
    // Wait until the Framework is started.
    long startTime = System.currentTimeMillis();
    boolean pingResult = false;
    while (System.currentTimeMillis() - startTime < PING_RETRY_DURATION && !pingResult) {
      System.out.print("Trying to ping the Orchestra Framework... ");
      pingResult = pingOrchestraFramework();
      if (pingResult) {
        System.out.println("OK");
      } else {
        System.out.println("NOK");
        Thread.sleep(PING_RETRY_INTERVAL);
      }
    }
    assertTrue("Unable to ping the Orchestra Framework (check it is started).", pingResult);
    // Wait until the Framework is fully operational.
    Thread.sleep(ORCHESTRA_FRAMEWORK_START_TIME);
    // Admin context creation.
    final RootContextsProject[] rootContextsProject = new RootContextsProject[1];
    AbstractMigration createAdminContextMigration = new AbstractMigration() {
      @Override
      protected IStatus doMigrate() {
        Map<String, Object> adminContextCreationResult = createNewContext(ADMIN_CONTEXT_NAME, null, null, false, null);
        IStatus adminContextCreationStatus = (IStatus) adminContextCreationResult.get(AbstractMigration.RESULT_KEY_STATUS);
        rootContextsProject[0] = (RootContextsProject) adminContextCreationResult.get(AbstractMigration.RESULT_KEY_PROJECT);
        return adminContextCreationStatus;
      }
    };
    IStatus adminContextCreationStatus = createAdminContextMigration.migrate();
    assertTrue(adminContextCreationStatus.getMessage(), adminContextCreationStatus.isOK());

    // Switch to user mode.
    Map<String, String> changeODMModeResult1 = PUCI.changeODMMode(OdmMode.USER);
    Status changeODMStatus1 = getPUCIResultStatus(changeODMModeResult1);
    assertTrue(changeODMStatus1.getMessage(), SeverityType.OK.equals(changeODMStatus1.getSeverity()));

    // User context creation.
    AbstractMigration createUserContextMigration = new AbstractMigration() {
      @Override
      protected IStatus doMigrate() {
        // Create User context.
        Map<String, Object> userContextCreationResult =
            createNewContext(USER_CONTEXT_NAME, rootContextsProject[0], null, true, ProjectActivator.getInstance().getCurrentUserId());
        return (IStatus) userContextCreationResult.get(AbstractMigration.RESULT_KEY_STATUS);
      }
    };

    /*
     * First step.
     */
    // User context creation.
    IStatus userContextCreationStatus1 = createUserContextMigration.migrate();
    assertTrue(userContextCreationStatus1.getMessage(), userContextCreationStatus1.isOK());
    // Context project import.
    IStatus contextimportStatus1 = createUserContextMigration.importToFrameworkWorkspace(rootContextsProject[0]);
    assertTrue(contextimportStatus1.getMessage(), contextimportStatus1.isOK());
    // "Set as current context" of the user context.
    Map<String, String> changeContextResult1 = PUCI.changeContext(USER_CONTEXT_NAME, false);
    Status changeContextStatus1 = getPUCIResultStatus(changeContextResult1);
    assertTrue(changeContextStatus1.getMessage(), SeverityType.OK.equals(changeContextStatus1.getSeverity()));

    /*
     * Second step.
     */
    // User context creation.
    IStatus userContextCreationStatus2 = createUserContextMigration.migrate();
    assertTrue(userContextCreationStatus2.getMessage(), userContextCreationStatus2.isOK());
    // Context project import.
    IStatus contextimportStatus2 = createUserContextMigration.importToFrameworkWorkspace(rootContextsProject[0]);
    assertTrue(contextimportStatus2.getMessage(), contextimportStatus2.isOK());
    // "Set as current context" of the user context.
    Map<String, String> changeContextResult2 = PUCI.changeContext(USER_CONTEXT_NAME, false);
    Status changeContextStatus2 = getPUCIResultStatus(changeContextResult2);
    assertTrue(changeContextStatus2.getMessage(), SeverityType.OK.equals(changeContextStatus2.getSeverity()));
  }

  /**
   * Extract the first status under "Orchestra Framework response container status" from the given status file.
   * @param puciResultMap_p
   * @return
   */
  public Status getPUCIResultStatus(Map<String, String> puciResultMap_p) {
    String statusFilePath = puciResultMap_p.get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH);
    assertNotNull("Status file path can't be null", statusFilePath);
    // Try and load the resulting status.
    StatusHandler statusHandler = new StatusHandler();
    StatusDefinition statusModel = statusHandler.loadModel(statusFilePath);
    Status frameworkStatus = (statusModel == null) ? null : statusModel.getStatus();
    SeverityType severity = (frameworkStatus == null) ? SeverityType.ERROR : frameworkStatus.getSeverity();
    // Check the framework status severity.
    assertEquals("Unexpected severity for main status (expected severy is INFO).", SeverityType.INFO, severity);
    // Main status is INFO as expected. So the framework status contains one sub-status: the PUCI operation status.
    Status operationStatus = frameworkStatus.getStatus().get(0);
    return operationStatus;
  }

  /**
   * Try to open a connection to the Orchestra Framework.
   * @return
   * @throws MalformedURLException
   */
  public boolean pingOrchestraFramework() throws MalformedURLException {
    // serverConfParam.xml must be read to have an up to date port value.
    IStatus readStatus = ServerConfParam.getInstance().readFile();
    if (!readStatus.isOK()) {
      return false;
    }
    int variableManagerPort = ServerConfParam.getInstance().getPort();
    String connectString = MessageFormat.format(PING_ADDRESS, String.valueOf(variableManagerPort));
    System.out.println("Connect string: "+connectString);
    URL url = new URL(connectString);
    InputStream openStream;
    try {
      openStream = url.openStream();
    } catch (IOException exception_p) {
      return false;
    }
    byte[] readFile = FileHelper.readFile(openStream);
    String actual = new String(readFile);
    System.out.println("User name: "+actual);
    return System.getProperty("user.name").equals(actual);
  }

}
