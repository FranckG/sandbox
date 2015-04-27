/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.migration.test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.migration.AbstractMigration;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Model migration tests.
 * @author t0076261
 */
@SuppressWarnings("nls")
public class MigrationTest extends TestCase {
  /**
   * Projects absolute root location.
   */
  protected String _absoluteRootLocation;

  @Override
  public void setUp() {
    _absoluteRootLocation = System.getProperty("java.io.tmpdir") + "migrationTest";
    // Make sure root folder exists.
    File file = new File(_absoluteRootLocation);
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  /**
   * Clean migration process.
   */
  @Override
  public void tearDown() {
    File file = new File(_absoluteRootLocation);
    if (file.exists()) {
      try {
        FileUtils.deleteDirectory(file);
      } catch (IOException exception_p) {
        // Too bad !
      }
    }
    Assert.assertFalse(String.format("Expecting test directory %s to be deleted.", file.getAbsolutePath()), file.exists());
  }

  public void testMigrateSomeProjects() {
    // Go on with the simulated migration.
    new AbstractMigration() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.migration.AbstractMigration#doMigrate()
       */
      @Override
      protected IStatus doMigrate() {
        Map<String, Object> rootContextMap = createNewContext("MigrationRootContext", null, _absoluteRootLocation, false, null);
        for (int i = 0; i < 10; i++) {
          // Create context with all required artifacts.
          Map<String, Object> userContextMap =
              createNewContext("Context" + i, (RootContextsProject) rootContextMap.get(RESULT_KEY_PROJECT), _absoluteRootLocation, true, "User" + i);
          // Create test category.
          Category testCategory = ContextsFactory.eINSTANCE.createCategory();
          testCategory.setName("Category" + i);
          ((Context) userContextMap.get(RESULT_KEY_CONTEXT)).getCategories().add(testCategory);
          // Create test variable and value.
          Variable testVariable = ContextsFactory.eINSTANCE.createVariable();
          testVariable.setName("Variable" + i);
          testCategory.getVariables().add(testVariable);
          VariableValue testValue = ContextsFactory.eINSTANCE.createVariableValue();
          testValue.setValue("Value" + i);
          testVariable.getValues().add(testValue);
        }
        return Status.OK_STATUS;
      }
    }.migrate();
    // Test results.
    File rootDirectory = new File(_absoluteRootLocation + "/MigrationRootContext");
    Assert.assertTrue("Testing root project existence", rootDirectory.exists() && rootDirectory.isDirectory());
    for (int i = 0; i < 10; i++) {
      File file = new File(rootDirectory.getAbsolutePath() + "/users/User" + i + ICommonConstants.POINT_CHARACTER + ICommonConstants.FILE_EXTENSION_CONTEXTS);
      Assert.assertTrue("Testing participation " + i + " existence", file.exists() && file.isFile());
    }
  }
}