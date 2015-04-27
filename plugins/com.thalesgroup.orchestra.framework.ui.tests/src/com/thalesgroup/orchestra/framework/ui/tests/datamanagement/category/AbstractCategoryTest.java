/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.category;

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
public abstract class AbstractCategoryTest extends AbstractTest {
  protected final String _adminContextCategory1 = "AdminContextCategory1";
  protected final String _adminContextCategory2 = "AdminContextCategory2";
  protected String _adminContextName;
  protected final String _adminContextOrchestraSubCategory1 = "AdminContextOrchestraSubCategory1";
  protected final String _adminContextOrchestraSubCategory2 = "AdminContextOrchestraSubCategory2";

  protected final String _userContextCategory1 = "UserContextCategory1";
  protected final String _userContextCategory2 = "UserContextCategory2";
  protected final String _userContextName = "CategoryTestUserContext";

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
        _adminContextName = "CategoryTestAdminContext" + UUID.randomUUID().toString();
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
        // Add admin categories and subcategories.
        createCategory(null, _adminContextCategory1, adminContext);
        createCategory(null, _adminContextCategory2, adminContext);
        createCategory(DataUtil.__CATEGORY_ORCHESTRA, _adminContextOrchestraSubCategory1, adminContext);
        createCategory(DataUtil.__CATEGORY_ORCHESTRA, _adminContextOrchestraSubCategory2, adminContext);
        // Add user category.
        createCategory(null, _userContextCategory1, userContext);
        createCategory(null, _userContextCategory2, userContext);
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
