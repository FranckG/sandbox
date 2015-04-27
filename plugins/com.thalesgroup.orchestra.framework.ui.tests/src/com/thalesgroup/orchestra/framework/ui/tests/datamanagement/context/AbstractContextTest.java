/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Assert;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.migration.AbstractMigration;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
public abstract class AbstractContextTest extends AbstractTest {
  protected AbstractMigration _migrationHelper;

  /**
   * Create a new context using the new context wizard. <br>
   * ODM must be in Admin mode.
   * @param newContextName_p the new context name.
   */
  public void createAdminContextUsingNewContextWizard(String newContextName_p) {
    // Precondition.
    Assert.assertTrue("ODM must be in admin mode to create a new admin context using the new context wizard.", isOdmInAdministratorMode());
    // Use "New Context..." in "File" menu.
    getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
        .menu(com.thalesgroup.orchestra.framework.ui.view.Messages.NewContextAction_Label_Admin).click();
    // Fill the new context wizard.
    SWTBotShell newContextWizardShell = getBot().shell(com.thalesgroup.orchestra.framework.ui.wizard.Messages.NewContextWizard_1);
    newContextWizardShell.activate();
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.MainNewContextWizardPage_0).setText(newContextName_p);
    sleep(1);
    getBot().button(IDialogConstants.FINISH_LABEL).click();
  }

  /**
   * Create a project, an admin context and potentially an user context.
   * @param locationPath_p the location where to put the new project. If it is null or empty, the project is created in the workspace root directory. If it is a
   *          relative path, it is resolved from the workspace root directory. If it is an absolute path, it is directly used.
   * @param projectName_p Name of the project to create (can be <code>null</code> or empty. In this case, adminContextName_p is used).
   * @param adminContextName_p Name of the admin context to create (can't be null nor empty).
   * @param userContextName_p Name of the user context to create (if null or empty, the user context is not created).
   * @param importToWorkspace_p Should the project be imported to the Framework workspace ?
   * @return the location of the new project.
   */
  public String createContextProject(final String locationPath_p, 
		  							 final String projectName_p, 
		  							 final String adminContextName_p, 
		  							 final String userContextName_p,
		  							 boolean importToWorkspace_p) {
    // Preconditions.
    if (null == adminContextName_p || adminContextName_p.isEmpty()) {
      return null;
    }
    // Manage project path.
    final IPath[] projectPath = new IPath[1];
    if (null == locationPath_p || locationPath_p.isEmpty()) {
      // No location path given -> put the project in the workspace.
      projectPath[0] = ResourcesPlugin.getWorkspace().getRoot().getLocation();
    } else {
      IPath locationPath = new Path(locationPath_p);
      if (!locationPath.isAbsolute()) {
        // A relative path given -> put the project in the workspace + relative path.
        projectPath[0] = ResourcesPlugin.getWorkspace().getRoot().getLocation().append(locationPath);
      } else {
        // An absolute path given -> put the project in the given path.
        projectPath[0] = locationPath;
      }
    }
    if (!projectPath[0].isValidPath(projectPath[0].toString())) {
      return null;
    }
    final String contextsProjectLocation[] = new String[1];
    _migrationHelper = new AbstractMigration() {
      @Override
      protected IStatus doMigrate() {
        // Create admin context.
        Map<String, Object> mapAdmin = null;
        if (null != projectName_p && !projectName_p.isEmpty()) {
          mapAdmin = createNewContext(projectName_p, null, projectPath[0].toOSString(), false, null);
        } else {
          mapAdmin = createNewContext(adminContextName_p, null, projectPath[0].toOSString(), false, null);
        }
        Context adminContext = (Context) mapAdmin.get(RESULT_KEY_CONTEXT);
        adminContext.setName(adminContextName_p);
        // Create env fs artefact
        AbstractVariable artefactVariable = createVariable("Orchestra", "ArtefactPath", Collections.singletonList(""), null, false, adminContext);
        EnvironmentVariableValue value = (EnvironmentVariableValue) artefactVariable.getValues().get(0);
        value.setEnvironmentId("com.thalesgroup.orchestra.framework.ui.tests.environmentmock");
        value.getValues().put("ARTEFACTS", "ARTEFACTS");
        // Create env fs confdir
        AbstractVariable confdirVariable = createVariable("Orchestra", "ConfigurationDirectories", Collections.singletonList(""), null, false, adminContext);
        value = (EnvironmentVariableValue) confdirVariable.getValues().get(0);
        value.setEnvironmentId("com.thalesgroup.orchestra.framework.ui.tests.environmentmock");
        value.getValues().put("CONFDIRS", "CONFDIRS");

        IProject adminProject = ((RootContextsProject) mapAdmin.get(RESULT_KEY_PROJECT))._project;
        try {
          ModelHandlerActivator.getDefault().getDataHandler().populateContextsDescription(adminProject, adminContext);
        } catch (IOException exception_p) {
          return Status.CANCEL_STATUS;
        }

        RootContextsProject rootContextsProject = (RootContextsProject) mapAdmin.get(RESULT_KEY_PROJECT);
        contextsProjectLocation[0] = rootContextsProject.getLocation();
        // Create user context.
        if (null != userContextName_p && !userContextName_p.isEmpty()) {
          createNewContext(userContextName_p, rootContextsProject, null, true, ProjectActivator.getInstance().getCurrentUserId());
        }
        return Status.OK_STATUS;
      }
    };
    _migrationHelper.migrate();
    // Import project ?
    if (importToWorkspace_p) {
      IStatus status = importContexts(contextsProjectLocation[0]);
      Assert.assertTrue(status.toString(), status.isOK());
    }
    return contextsProjectLocation[0];
  }

  /**
   * Check if the set as current context action is enabled for given context. <br>
   * ODM must be in Admin mode.
   * @return
   */
  public boolean isSetAsCurrentContextActionEnabled(String contextName) {
    // Precondition.
    Assert.assertTrue("ODM must be in admin mode to check current context action enablement.", isOdmInAdministratorMode());
    final SWTBotTree contextsTree = getContextsTree();
    // Get context name as displayed in the contexts tree.
    final String contextDisplayedName = getAdministratorContextLabelInTree(contextName);
    // Select context node.
    contextsTree.select(contextDisplayedName);
    // Is set as current context action enabled ?
    return contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.SetAsCurrentContextAction_setAsCurrentContext).isEnabled();
  }
}
