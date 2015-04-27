/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.tests;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EMap;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.FolderVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingEnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.migration.AbstractMigration;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 * @author s0011584
 */
public class AllTests extends TestSuite {
  /**
   * Projects absolute root location.
   */
  public static String _absoluteRootLocation;

  public AllTests() {
    super("Test for com.thalesgroup.orchestra.framework.tests"); //$NON-NLS-1$
    addTestSuite(DataModelTest.class);
    addTestSuite(ModelUtilTest.class);
    addTestSuite(DataUtilTest.class);
    addTestSuite(WorkspaceManagementTest.class);
  }

  /**
   * @see junit.framework.TestSuite#run(junit.framework.TestResult)
   */
  @Override
  public void run(TestResult result_p) {
    try {
      setup();
    } catch (Throwable throwable_p) {
      CommonActivator.getInstance().logMessage("Setup for com.thalesgroup.orchestra.framework.tests failed", IStatus.ERROR, throwable_p); //$NON-NLS-1$
    }
    try {
      super.run(result_p);
    } finally {
      tearDown();
    }
  }

  @SuppressWarnings("nls")
  protected void setup() {
    _absoluteRootLocation = System.getProperty("java.io.tmpdir") + "modelTest";
    // Make sure root folder exists.
    File file = new File(_absoluteRootLocation);
    if (!file.exists()) {
      file.mkdirs();
    } else {
      try {
        FileUtils.deleteDirectory(file);
      } catch (IOException exception_p) {
        Assert.assertFalse(true);
      }
    }
    // Get projects.
    List<RootContextsProject> contexts = ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(_absoluteRootLocation);
    // Create data dynamically.
    if (contexts.isEmpty()) {
      final RootContextsProject[] daeProject = new RootContextsProject[] { null };
      // Use the migration process to create DAE project.
      new AbstractMigration() {
        /**
         * @see com.thalesgroup.orchestra.framework.model.migration.AbstractMigration#doMigrate()
         */
        @Override
        protected IStatus doMigrate() {
          // Create DAE context.
          Map<String, Object> daeMap = createNewContext("DAE", null, _absoluteRootLocation, false, null);
          Context daeContext = (Context) daeMap.get(RESULT_KEY_CONTEXT);
          daeProject[0] = (RootContextsProject) daeMap.get(RESULT_KEY_PROJECT);
          Category themisCategory = ContextsFactory.eINSTANCE.createCategory();
          themisCategory.setName("Themis");
          daeContext.getCategories().add(themisCategory);
          FolderVariable processEm = ContextsFactory.eINSTANCE.createFolderVariable();
          processEm.setName("ProcessEM");
          processEm.setDescription("The process directory");
          VariableValue value = ContextsFactory.eINSTANCE.createVariableValue();
          value.setValue("P:\\pEM\\");
          processEm.getValues().add(value);
          themisCategory.getVariables().add(processEm);
          Variable thavConnector = ContextsFactory.eINSTANCE.createVariable();
          thavConnector.setName("ThavConnector");
          thavConnector.setDescription(ICommonConstants.EMPTY_STRING);
          value = ContextsFactory.eINSTANCE.createVariableValue();
          value.setValue("false");
          thavConnector.getValues().add(value);
          themisCategory.getVariables().add(thavConnector);
          return Status.OK_STATUS;
        }
      }.migrate();
      // Use another migration process to create THAV (because of dependency on completed DAE project).
      new AbstractMigration() {
        /**
         * @see com.thalesgroup.orchestra.framework.model.migration.AbstractMigration#doMigrate()
         */
        @Override
        protected IStatus doMigrate() {
          // Create THAV context.
          Map<String, Object> thavMap = createNewContext("THAV", daeProject[0], _absoluteRootLocation, false, null);
          Context thavContext = (Context) thavMap.get(RESULT_KEY_CONTEXT);
          Category pitoCategory = ContextsFactory.eINSTANCE.createCategory();
          pitoCategory.setName("Pito");
          thavContext.getCategories().add(pitoCategory);
          Variable envUnix = ContextsFactory.eINSTANCE.createVariable();
          envUnix.setName("Env Unix");
          VariableValue value = ContextsFactory.eINSTANCE.createVariableValue();
          value.setValue("true");
          envUnix.getValues().add(value);
          pitoCategory.getVariables().add(envUnix);
          Category foreignCategory = DataUtil.getCategory("\\Themis", thavContext.getSuperContext());
          Variable a380 = ContextsFactory.eINSTANCE.createVariable();
          a380.setName("A380");
          a380.setSuperCategory(foreignCategory);
          value = ContextsFactory.eINSTANCE.createVariableValue();
          value.setValue("The bigger plane in earth");
          a380.getValues().add(value);
          thavContext.getSuperCategoryVariables().add(a380);
          // Add a contribution to LinkManager category.
          {
            Category linkManagerCategory = DataUtil.getCategory("\\Orchestra\\LinkManager", thavContext);
            Category newContributedCategory = ContextsFactory.eINSTANCE.createCategory();
            newContributedCategory.setName("Contributed category");
            newContributedCategory.setSuperCategory(linkManagerCategory);
            thavContext.getSuperCategoryCategories().add(newContributedCategory);
            // Add it a variable.
            Variable contributedVariable = ContextsFactory.eINSTANCE.createVariable();
            contributedVariable.setName("Contributed variable");
            newContributedCategory.getVariables().add(contributedVariable);
            VariableValue contributedVariableValue = ContextsFactory.eINSTANCE.createVariableValue();
            contributedVariableValue.setValue("Contribution to LinkManager category");
            contributedVariable.getValues().add(contributedVariableValue);
          }
          return Status.OK_STATUS;
        }
      }.migrate();
      // Use another migration process to create Cycle project.
      new AbstractMigration() {
        /**
         * @see com.thalesgroup.orchestra.framework.model.migration.AbstractMigration#doMigrate()
         */
        @Override
        protected IStatus doMigrate() {
          // Create Cycle context.
          Map<String, Object> cycleMap = createNewContext("Cycle", null, _absoluteRootLocation, false, null);
          Context cycleContext = (Context) cycleMap.get(RESULT_KEY_CONTEXT);
          Category testCategory = ContextsFactory.eINSTANCE.createCategory();
          testCategory.setName("Test");
          cycleContext.getCategories().add(testCategory);
          Variable A = ContextsFactory.eINSTANCE.createVariable();
          A.setName("A rely on B");
          A.setDescription(ICommonConstants.EMPTY_STRING);
          testCategory.getVariables().add(A);
          VariableValue aValue = ContextsFactory.eINSTANCE.createVariableValue();
          A.getValues().add(aValue);
          aValue.setValue("${odm:\\Test\\B rely on C}");
          Variable B = ContextsFactory.eINSTANCE.createVariable();
          B.setName("B rely on C");
          B.setDescription(ICommonConstants.EMPTY_STRING);
          testCategory.getVariables().add(B);
          VariableValue bValue = ContextsFactory.eINSTANCE.createVariableValue();
          B.getValues().add(bValue);
          bValue.setValue("${odm:\\Test\\C rely on A}");
          Variable C = ContextsFactory.eINSTANCE.createVariable();
          C.setName("C rely on A");
          C.setDescription(ICommonConstants.EMPTY_STRING);
          testCategory.getVariables().add(C);
          VariableValue cValue = ContextsFactory.eINSTANCE.createVariableValue();
          C.getValues().add(cValue);
          cValue.setValue("${odm:\\Test\\A rely on B}");
          Category successCategory = ContextsFactory.eINSTANCE.createCategory();
          successCategory.setName("Success");
          cycleContext.getCategories().add(successCategory);
          A = ContextsFactory.eINSTANCE.createVariable();
          A.setName("A rely on B");
          A.setDescription(ICommonConstants.EMPTY_STRING);
          aValue = ContextsFactory.eINSTANCE.createVariableValue();
          aValue.setValue("AAA${odm:\\Success\\B rely on C}aaa");
          A.getValues().add(aValue);
          successCategory.getVariables().add(A);
          B = ContextsFactory.eINSTANCE.createVariable();
          B.setName("B rely on C");
          B.setDescription(ICommonConstants.EMPTY_STRING);
          bValue = ContextsFactory.eINSTANCE.createVariableValue();
          bValue.setValue("bbb${odm:\\Success\\C}BBB");
          B.getValues().add(bValue);
          successCategory.getVariables().add(B);
          C = ContextsFactory.eINSTANCE.createVariable();
          C.setName("C");
          C.setDescription(ICommonConstants.EMPTY_STRING);
          cValue = ContextsFactory.eINSTANCE.createVariableValue();
          cValue.setValue("valueOfC");
          C.getValues().add(cValue);
          successCategory.getVariables().add(C);
          return Status.OK_STATUS;
        }
      }.migrate();
      final RootContextsProject[] environmentProject = new RootContextsProject[] { null };
      // Use another migration process to create Environment persistence project.
      new AbstractMigration() {
        /**
         * @see com.thalesgroup.orchestra.framework.model.migration.AbstractMigration#doMigrate()
         */
        @Override
        protected IStatus doMigrate() {
          // Create Environment context.
          Map<String, Object> environmentMap = createNewContext("Environment", null, _absoluteRootLocation, false, null);
          environmentProject[0] = (RootContextsProject) environmentMap.get(RESULT_KEY_PROJECT);
          Context environmentContext = (Context) environmentMap.get(RESULT_KEY_CONTEXT);
          createCategory(null, "Test", environmentContext);
          EnvironmentVariable paths =
              (EnvironmentVariable) createVariable("\\Test", "paths", Collections.singletonList("First environment"), VariableType.ENVIRONMENT_VARIABLE, false,
                  environmentContext);
          EnvironmentVariableValue firstEnvironment = (EnvironmentVariableValue) paths.getValues().get(0);
          firstEnvironment.getValues().put("folders", _absoluteRootLocation);
          firstEnvironment.getValues().put("filters", ".svn");
          return Status.OK_STATUS;
        }
      }.migrate();
      // Use another migration process to create Extended environment persistence project.
      new AbstractMigration() {
        /**
         * @see com.thalesgroup.orchestra.framework.model.migration.AbstractMigration#doMigrate()
         */
        @Override
        protected IStatus doMigrate() {
          // Create ExtendedEnvironment context.
          Map<String, Object> environmentMap = createNewContext("ExtendedEnvironment", environmentProject[0], _absoluteRootLocation, false, null);
          environmentProject[0] = (RootContextsProject) environmentMap.get(RESULT_KEY_PROJECT);
          Context extendedEnvironmentContext = (Context) environmentMap.get(RESULT_KEY_CONTEXT);
          AbstractVariable variable = createVariable("\\Test", "paths", Collections.singletonList("NA1"), null, true, extendedEnvironmentContext);
          OverridingEnvironmentVariableValue firstEnvironment = (OverridingEnvironmentVariableValue) variable.getValues().get(0);
          EMap<String, String> values = firstEnvironment.getValues();
          values.put("filters", "data");
          return Status.OK_STATUS;
        }
      }.migrate();
      // Use another migration process to create Super extended environment persistence project.
      new AbstractMigration() {
        /**
         * @see com.thalesgroup.orchestra.framework.model.migration.AbstractMigration#doMigrate()
         */
        @Override
        protected IStatus doMigrate() {
          // Create SuperExtendedEnvironment context.
          Map<String, Object> environmentMap = createNewContext("SuperExtendedEnvironment", environmentProject[0], _absoluteRootLocation, false, null);
          Context superExtendedEnvironmentContext = (Context) environmentMap.get(RESULT_KEY_CONTEXT);
          AbstractVariable variable = createVariable("\\Test", "paths", Collections.singletonList("NA2"), null, true, superExtendedEnvironmentContext);
          OverridingEnvironmentVariableValue firstEnvironment = (OverridingEnvironmentVariableValue) variable.getValues().get(0);
          EMap<String, String> values = firstEnvironment.getValues();
          values.put("folders", "c:\\tmp");
          values.put("A contributed new value", "New value");
          return Status.OK_STATUS;
        }
      }.migrate();
      // Then get them again.
      contexts = ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(_absoluteRootLocation);
    }
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    // Import and load resulting contexts.
    for (RootContextsProject rootContextsProject : contexts) {
      // Import project in workspace.
      ProjectHelper.importExistingProject(rootContextsProject._project, rootContextsProject._description);
      // Load context.
      dataHandler.getContext(rootContextsProject.getContext());
    }
    // Get THAV context.
    Context context = dataHandler.getContext("/THAV/THAV.contexts");
    Assert.assertNotNull("Expecting a non null context for /THAV/THAV.contexts.", context);
    // Set it as current one.
    dataHandler.setCurrentContext(context, false);
  }

  @SuppressWarnings("nls")
  protected void tearDown() {
    // Clean editing domain.
    ModelHandlerActivator.getDefault().cleanEditingDomain();
    // Delete projects on disk.
    ProjectHelper.deleteProject("DAE", false);
    ProjectHelper.deleteProject("THAV", false);
    ProjectHelper.deleteProject("Cycle", false);
    ProjectHelper.deleteProject("Environment", false);
    ProjectHelper.deleteProject("ExtendedEnvironment", false);
    ProjectHelper.deleteProject("SuperExtendedEnvironment", false);
    // Remove temporary directory.
    File file = new File(_absoluteRootLocation);
    if (file.exists()) {
      try {
        FileUtils.deleteDirectory(file);
      } catch (IOException exception_p) {
        // Too bad !
      }
    }
  }

  public static Test suite() {
    return new AllTests();
  }
}