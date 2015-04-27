/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.variable;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.migration.AbstractMigration;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;

/**
 * This class is used to ease the writing of JUnit test classes on Categories.<br>
 * It initializes a project with an admin context and an user context, each of them is filled with 2 categories.
 * @author T0052089
 */
@SuppressWarnings("nls")
public abstract class AbstractVariableTest extends AbstractTest {
  protected final String _adminCategory = "AdminCategory";
  protected final String _adminCategoryFileVariable = "AdminFileVariable";
  protected final String _adminCategoryFileVariableValue;
  protected final String _adminCategoryFolderVariable = "AdminFolderVariable";
  protected final String _adminCategoryFolderVariableValue = getSharedTemporaryFolder();
  protected final String _adminCategoryVariable = "AdminVariable";
  protected final String _adminCategoryVariableValue = "AdminVariableValue";
  protected String _adminContextName;
  protected final String _adminOrchestraFileVariable = "AdminOrchestraFileVariable";
  protected final String _adminOrchestraFileVariableValue;
  protected final String _adminOrchestraFolderVariable = "AdminOrchestraFolderVariable";
  protected final String _adminOrchestraFolderVariableValue = getSharedTemporaryFolder();
  protected final String _adminOrchestraVariable = "AdminOrchestraVariable";
  protected final String _adminOrchestraVariableValue = "AdminOrchestraVariableValue";

  protected final String _userCategory = "UserCategory";
  protected final String _userCategoryFileVariable = "UserFileVariable";
  protected final String _userCategoryFileVariableValue;
  protected final String _userCategoryFolderVariable = "UserFolderVariable";
  protected final String _userCategoryFolderVariableValue = getSharedTemporaryFolder();
  protected final String _userCategoryVariable = "UserVariable";
  protected final String _userCategoryVariableValue = "UserVariableValue";
  protected final String _userContextName = "VariableTestUserContext";
  protected final String _userOrchestraFileVariable = "UserOrchestraFileVariable";
  protected final String _userOrchestraFileVariableValue;
  protected final String _userOrchestraFolderVariable = "UserOrchestraFolderVariable";
  protected final String _userOrchestraFolderVariableValue = getSharedTemporaryFolder();
  protected final String _userOrchestraVariable = "UserOrchestraVariable";
  protected final String _userOrchestraVariableValue = "UserOrchestraVariableValue";

  public AbstractVariableTest() {
    String localAdminCategoryFileVariableValue = null;
    String localAdminOrchestraFileVariableValue = null;
    String localUserCategoryFileVariableValue = null;
    String localUserOrchestraFileVariableValue = null;
    try {
      localAdminCategoryFileVariableValue = File.createTempFile("FileVariableTarget", "", new File(getSharedTemporaryFolder())).toString();
      localAdminOrchestraFileVariableValue = File.createTempFile("FileVariableTarget", "", new File(getSharedTemporaryFolder())).toString();
      localUserCategoryFileVariableValue = File.createTempFile("FileVariableTarget", "", new File(getSharedTemporaryFolder())).toString();
      localUserOrchestraFileVariableValue = File.createTempFile("FileVariableTarget", "", new File(getSharedTemporaryFolder())).toString();
    } catch (IOException exception_p) {
      Assert.fail("Can't create temp files to give a value to FileVariables.");
    }
    _adminCategoryFileVariableValue = localAdminCategoryFileVariableValue;
    _adminOrchestraFileVariableValue = localAdminOrchestraFileVariableValue;
    _userCategoryFileVariableValue = localUserCategoryFileVariableValue;
    _userOrchestraFileVariableValue = localUserOrchestraFileVariableValue;
  }

  /**
   * Data initialization : create a couple admin/user context and add 2 categories to each of them.
   */
  @Before
  public void createContexts() {
    // Manage project path.
    final IPath[] projectPath = new IPath[1];
    // No location path given -> put the project in the workspace.
    projectPath[0] = ResourcesPlugin.getWorkspace().getRoot().getLocation();
    if (!projectPath[0].isValidPath(projectPath[0].toString())) {
      return;
    }
    final String projectLocation[] = new String[1];
    AbstractMigration abstractMigration = new AbstractMigration() {
      @Override
      protected IStatus doMigrate() {
        // Create admin context.
        _adminContextName = "VariableTestAdminContext" + UUID.randomUUID().toString();
        Map<String, Object> mapAdmin = createNewContext(_adminContextName, null, projectPath[0].toOSString(), false, null);
        // Check creation result.
        IStatus adminContextCreationResult = (IStatus) mapAdmin.get(RESULT_KEY_STATUS);
        Assert.assertTrue(adminContextCreationResult.toString(), adminContextCreationResult.isOK());
        RootContextsProject rootContextsProject = (RootContextsProject) mapAdmin.get(RESULT_KEY_PROJECT);
        Context adminContext = (Context) mapAdmin.get(RESULT_KEY_CONTEXT);
        // Create user context.
        Map<String, Object> mapUser = createNewContext(_userContextName, rootContextsProject, null, true, ProjectActivator.getInstance().getCurrentUserId());
        // Check creation result.
        IStatus userContextCreationResult = (IStatus) mapUser.get(RESULT_KEY_STATUS);
        Assert.assertTrue(userContextCreationResult.toString(), userContextCreationResult.isOK());
        Context userContext = (Context) mapUser.get(RESULT_KEY_CONTEXT);
        // Add admin category and it variables.
        createCategory(null, _adminCategory, adminContext);
        createVariable(_adminCategory, _adminCategoryVariable, Collections.singletonList(_adminCategoryVariableValue), VariableType.VARIABLE, false,
            adminContext);
        createVariable(_adminCategory, _adminCategoryFileVariable, Collections.singletonList(_adminCategoryFileVariableValue), VariableType.FILE_VARIABLE,
            false, adminContext);
        createVariable(_adminCategory, _adminCategoryFolderVariable, Collections.singletonList(_adminCategoryFolderVariableValue),
            VariableType.FOLDER_VARIABLE, false, adminContext);
        // Add variables to Orchestra.
        createVariable(DataUtil.__CATEGORY_ORCHESTRA, _adminOrchestraVariable, Collections.singletonList(_adminOrchestraVariableValue), VariableType.VARIABLE,
            false, adminContext);
        createVariable(DataUtil.__CATEGORY_ORCHESTRA, _adminOrchestraFileVariable, Collections.singletonList(_adminOrchestraFileVariableValue),
            VariableType.FILE_VARIABLE, false, adminContext);
        createVariable(DataUtil.__CATEGORY_ORCHESTRA, _adminOrchestraFolderVariable, Collections.singletonList(_adminOrchestraFolderVariableValue),
            VariableType.FOLDER_VARIABLE, false, adminContext);
        // Add user category.
        createCategory(null, _userCategory, userContext);
        createVariable(_userCategory, _userCategoryVariable, Collections.singletonList(_userCategoryVariableValue), VariableType.VARIABLE, false, userContext);
        createVariable(_userCategory, _userCategoryFileVariable, Collections.singletonList(_userCategoryFileVariableValue), VariableType.FILE_VARIABLE, false,
            userContext);
        createVariable(_userCategory, _userCategoryFolderVariable, Collections.singletonList(_userCategoryFolderVariableValue), VariableType.FOLDER_VARIABLE,
            false, userContext);
        // Add variables to Orchestra.
        createVariable(DataUtil.__CATEGORY_ORCHESTRA, _userOrchestraVariable, Collections.singletonList(_userOrchestraVariableValue), VariableType.VARIABLE,
            false, userContext);
        createVariable(DataUtil.__CATEGORY_ORCHESTRA, _userOrchestraFileVariable, Collections.singletonList(_userOrchestraFileVariableValue),
            VariableType.FILE_VARIABLE, false, userContext);
        createVariable(DataUtil.__CATEGORY_ORCHESTRA, _userOrchestraFolderVariable, Collections.singletonList(_userOrchestraFolderVariableValue),
            VariableType.FOLDER_VARIABLE, false, userContext);
        projectLocation[0] = rootContextsProject.getLocation();
        return Status.OK_STATUS;
      }
    };
    abstractMigration.migrate();
    // Wait until admin context is created.
    getBot().waitUntil(getConditionFactory().createContextIsCreatedCondition(projectLocation[0], 1));
    // Load created contexts (using project) in ODM.
    IStatus importProjectStatus = importContexts(projectLocation[0]);
    Assert.assertTrue(importProjectStatus.toString(), importProjectStatus.isOK());
  }

  /**
   * Delete project created in before().
   * @throws Exception
   */
  @After
  public void deleteContexts() throws Exception {
    deleteAdminContext(_adminContextName);
  }
}
