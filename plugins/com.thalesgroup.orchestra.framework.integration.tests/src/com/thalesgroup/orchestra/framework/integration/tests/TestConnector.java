/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.integration.tests.helpers.IntegrationTestsHelper;
import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.migration.AbstractMigration;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.puci.PUCI;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.swtbotcondition.ContextIsCreatedCondition;

import junit.framework.Assert;

/**
 * @author S0024585
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class TestConnector {
  @Test
  public void navigateToArtefactsWithEcfConnector() throws Exception {
    final String typeName = "MockEcf";
    // Create existing artefact URI and file.
    final String existingMockEcfArtefactLogicalName = "ExistingMockEcfArtefactToNavigateTo";
    final OrchestraURI existingMockArtefactURI = new OrchestraURI(typeName, existingMockEcfArtefactLogicalName);
    org.apache.commons.io.FileUtils.touch(new File(IntegrationTestsHelper.__integrationTestsBaseArtefactPath, existingMockEcfArtefactLogicalName + ".mockecf"));
    // Create non existing artefact URI only.
    final OrchestraURI nonExistingMockEcfArtefactURI = new OrchestraURI(typeName, "InexistingMockEcfArtefactToNavigateTo");

    // Call navigate.
    Map<String, String> navigateResult = PUCI.navigate(Arrays.asList(existingMockArtefactURI, nonExistingMockEcfArtefactURI));

    System.out.println("Existing Mock Artefact URI: "+existingMockArtefactURI.getAbsoluteUri().toString());
    System.out.println("Non existing Mock Artefact URI: "+nonExistingMockEcfArtefactURI.getAbsoluteUri().toString());
    
    // Check result.
    final String expectedMainMessage = IntegrationTestsHelper.generateConnectorMainStatusMessage(typeName, ICommandConstants.NAVIGATE);
    final SeverityType expectedMainSeverity = SeverityType.WARNING;
    final Map<String, SeverityType> expectedArtefactSeverities = new HashMap<String, SeverityType>();
    expectedArtefactSeverities.put(existingMockArtefactURI.getAbsoluteUri(), SeverityType.OK);
    expectedArtefactSeverities.put(nonExistingMockEcfArtefactURI.getAbsoluteUri(), SeverityType.ERROR);
    
    System.out.println("Navigate result:");
    System.out.println(navigateResult.toString());
    
    System.out.println("Expected main messages:");
    System.out.println(expectedMainMessage);
    
    System.out.println("Expected main severity:");
    System.out.println(expectedMainSeverity.toString());
    
    System.out.println("Expected artefact severities:");
    System.out.println(expectedArtefactSeverities.toString());
    
    Assert.assertTrue("Not expected navigate result.",
        IntegrationTestsHelper.isExpectedResponse(navigateResult, expectedMainMessage, expectedMainSeverity, expectedArtefactSeverities));

    // Check ECF connector isn't closed after a navigate.
    Assert.assertFalse("ECF connector mustn't be closed after a navigate.", IntegrationTestsHelper.__lastLaunchedEcfConnector.isTerminated());
    // Close ECF connector.
    IntegrationTestsHelper.__lastLaunchedEcfConnector.terminate();
  }

  @Test
  public void navigateToArtefactWithConnector() throws Exception {
    final String typeName = "Mock";
    // Create existing artefact URI and file.
    final String existingMockArtefactLogicalName = "ExistingMockArtefactToNavigateTo";
    final OrchestraURI existingMockArtefactURI = new OrchestraURI(typeName, existingMockArtefactLogicalName);
    org.apache.commons.io.FileUtils.touch(new File(IntegrationTestsHelper.__integrationTestsBaseArtefactPath, existingMockArtefactLogicalName + ".mock"));
    // Create non existing artefact URI only.
    final OrchestraURI nonExistingMockArtefactURI = new OrchestraURI(typeName, "InexistingArtefactToNavigateTo");

    // Call navigate.
    Map<String, String> navigateResult = PUCI.navigate(Arrays.asList(existingMockArtefactURI, nonExistingMockArtefactURI));

    // Check result.
    final String expectedMainMessage = IntegrationTestsHelper.generateConnectorMainStatusMessage(typeName, ICommandConstants.NAVIGATE);
    final SeverityType expectedMainSeverity = SeverityType.WARNING;
    final Map<String, SeverityType> expectedArtefactSeverities = new HashMap<String, SeverityType>();
    expectedArtefactSeverities.put(existingMockArtefactURI.getAbsoluteUri(), SeverityType.OK);
    expectedArtefactSeverities.put(nonExistingMockArtefactURI.getAbsoluteUri(), SeverityType.ERROR);
    Assert.assertTrue("Not expected navigate result.",
        IntegrationTestsHelper.isExpectedResponse(navigateResult, expectedMainMessage, expectedMainSeverity, expectedArtefactSeverities));
  }

  @BeforeClass
  public static void beforeClass() throws Exception {
    final String contextName = "IntegrationTestContext";
    final String rootContextProjectPath[] = new String[1];
    AbstractMigration migration = new AbstractMigration() {
      @Override
      protected IStatus doMigrate() {
        Map<String, Object> createNewContextResult = createNewContext(contextName, null, null, false, null, false);
        Context createdContext = (Context) createNewContextResult.get(RESULT_KEY_CONTEXT);
        rootContextProjectPath[0] = ((RootContextsProject) createNewContextResult.get(RESULT_KEY_PROJECT)).getLocation();
        
        // Add artifact paths to new context.
        updateEnvironmentVariableWithFileSystemEnvironment("Orchestra",
        												   "ArtefactPath", 
        												   IntegrationTestsHelper.__integrationTestsBaseArtefactPath,
        												   createdContext);
        
        // Add configuration directories to new context.
        updateEnvironmentVariableWithFileSystemEnvironment("Orchestra", 
        												   "ConfigurationDirectories", 
        												   IntegrationTestsHelper.__integrationTestsBaseConfDirPath,
        												   createdContext);
        return Status.OK_STATUS;
      }

      /**
       * Update specified environment variable with FileSystem based environment containing specified new absolute path.
       * @param category_p The variable container name, including all parents hierarchy.
       * @param variableName_p The environment variable name.
       * @param newPath_p The new absolute path to add.
       * @param context_p The target context.
       */
      public void updateEnvironmentVariableWithFileSystemEnvironment(String category_p, String variableName_p, String newPath_p, Context context_p) {
        // Get environment variable.
        // Note that this is necessarily an existing one, so the result is an overriding variable.
        // Provided values are useless, as an overriding environment value will be provided (keepValues = false). Still it can't be empty.
        OverridingVariable variable = (OverridingVariable) createVariable(category_p, variableName_p, Collections.singletonList("NA"), null, false, context_p);
        // Get resulting environment variable value.
        EnvironmentVariableValue environmentVariableValue = (EnvironmentVariableValue) variable.getValues().get(0);
        // Set environment ID.
        environmentVariableValue.setEnvironmentId("com.thalesgroup.orchestra.framework.environment.filesystem");
        // Set new 'Directories' values.
        environmentVariableValue.getValues().put("directories", OrchestraURI.encode(newPath_p));
      }
    };
    migration.migrate();
    // Wait until admin context is created.
    IntegrationTestSuite.getBot().waitUntil(new ContextIsCreatedCondition(rootContextProjectPath[0], 1));
    migration.importToFrameworkWorkspace(ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(rootContextProjectPath[0]).get(0));
    PUCI.changeContext(contextName);
  }

}