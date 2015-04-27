/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context;

import java.io.File;
import java.util.Collections;

import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;

import junit.framework.Assert;

/**
 * As an administrator, delete project without then with removing contents on disk.<br>
 * As a user, delete participation without then with removing contents on disk.
 * @author t0076261
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class DeleteAdminContextTests extends AbstractContextTest {
  /**
   * As an administrator, create context then delete without then with removing contents on disk.
   * @throws Exception
   */
  @Test
  public void canDeleteAnExistingAdminContext() throws Exception {
    // Create test structure.
    initializeTest();
    changeModeTo(true);
    String administratorContextName = "DeleteAsAnAdministrator";
    String participationName = createContext(administratorContextName, true);
    String projectFullPath = findContextForName(administratorContextName, false).getKey().getLocation();
    // Delete without removing contents.
    {
      SWTBotTree tree = getBot().tree().select(getAdministratorContextLabelInTree(administratorContextName));
      tree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
      getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.DeleteCommandWithConfirmation_ConfirmationDialog_Title).activate();
      getBot().button(IDialogConstants.YES_LABEL).click();
      // Check that project is no longer accessible (unloaded).
      Assert.assertNull("Context '" + administratorContextName + "' is expected to be deleted !", findContextForName(administratorContextName, false));
      // Check that participation is no longer accessible (unloaded).
      changeModeTo(false);
      Assert.assertNull("Participation '" + participationName + "' is expected to be deleted !", findContextForName(participationName, false));
      // Check that project still exists on disk.
      File projectDirectory = new File(projectFullPath);
      Assert.assertTrue("Deleted context '" + administratorContextName + "' should still be available on file system !", projectDirectory.exists());
      changeModeTo(true);
    }
    // Import project again.
    {
      RootContextsProject contextFromLocation = ProjectActivator.getInstance().getProjectHandler().getContextFromLocation(projectFullPath, null);
      boolean imported = ModelHandlerActivator.getDefault().getDataHandler().importContexts(Collections.singletonList(contextFromLocation), false).isOK();
      Assert.assertTrue("Should be able to import project for context '" + administratorContextName + "' !", imported);
      sleep(1);
    }
    // Delete removing contents.
    {
      SWTBotTree tree = getBot().tree().select(getAdministratorContextLabelInTree(administratorContextName));
      tree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
      getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.DeleteCommandWithConfirmation_ConfirmationDialog_Title).activate();
      getBot().checkBox().click(); // com.thalesgroup.orchestra.framework.model.handler.command.Messages.DeleteContext_ConfirmationDialog_AdditionalWarning_Admin
      getBot().button(IDialogConstants.YES_LABEL).click();
      // Check that project is no longer accessible (try to load it to be sure).
      Assert.assertNull("Context '" + administratorContextName + "' is expected to be deleted !", findContextForName(administratorContextName, true));
      // Check that participation is no longer accessible (try to load it to be sure).
      changeModeTo(false);
      Assert.assertNull("Participation '" + participationName + "' is expected to be deleted !", findContextForName(participationName, true));
      // Check that project does no longer exist on disk.
      File projectDirectory = new File(projectFullPath);
      Assert.assertFalse("Deleted context '" + administratorContextName + "' should be deleted on file system !", projectDirectory.exists());
    }
  }

  /**
   * Initialization : create an admin context and import it. <br>
   * Test steps: <br>
   * 1- Set the admin context as current, <br>
   * 2- Try to delete it -> An error dialog must be displayed.
   * @throws Exception
   */
  @Test
  public void canNotDeleteCurrentAdminContext() throws Exception {
    /*
     * Initialization.
     */
    // Create an admin context and import it.
    final String adminContextName = "AdminContextDeletionTest";
    final String contextProjectLocation = createContextProject(getSharedTemporaryFolder(), null, adminContextName, null, false);
    // Wait until admin context is created.
    getBot().waitUntil(getConditionFactory().createContextIsCreatedCondition(contextProjectLocation, 1), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    importContexts(contextProjectLocation);
    /*
     * UI tests.
     */
    initializeTest();
    changeModeTo(true);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Set admin context as current one.
    contextsTree.select(getAdministratorContextLabelInTree(adminContextName));
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.SetAsCurrentContextAction_setAsCurrentContext).click();
    // Wait for progress shell to disappear.
    getBot().waitUntil(getConditionFactory().createPlatformShellIsActiveCondition(), CONDITION_TIMEOUT, CONDITION_INTERVAL);
    // Try to delete it.
    contextsTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
    // Expecting to have an error dialog.
    SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.UnableToDeleteContextCommand_ErrorDialog_Title);
    shell.activate();
    shell.bot().button(IDialogConstants.OK_LABEL).click();
    /*
     * Clean up.
     */
    // DON'T FORGET TO SET ANOTHER CONTEXT AS CURRENT TO DELETE THE CREATED CONTEXT.
    setDefaultContextAsCurrent();
    deleteAdminContext(adminContextName);
  }

  /**
   * Initialization : create a couple admin/user contexts and import it. <br>
   * Test steps: <br>
   * 1- Set the user context as current, <br>
   * 2- Try to delete the admin context -> An error dialog must be displayed.
   * @throws Exception
   */
  @Test
  public void canNotDeleteParentOfCurrentUserContext() throws Exception {
    /*
     * Initialization.
     */
    // Create a couple admin/user context and import it.
    final String adminContextName = "AdminContextDeletionTest";
    final String userContextName = "UserContextDeletionTest";
    final String contextProjectLocation = createContextProject(getSharedTemporaryFolder(), null, adminContextName, userContextName, false);
    // Wait until admin context is created.
    getBot().waitUntil(getConditionFactory().createContextIsCreatedCondition(contextProjectLocation, 1), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    importContexts(contextProjectLocation);
    /*
     * UI tests.
     */
    initializeTest();
    changeModeTo(false);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Set user context as current one.
    contextsTree.select(getParticipationLabelInTree(userContextName, adminContextName));
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.SetAsCurrentContextAction_setAsCurrentContext).click();
    // Wait for progress shell to disappear.
    getBot().waitUntil(getConditionFactory().createPlatformShellIsActiveCondition(), CONDITION_TIMEOUT, CONDITION_INTERVAL);
    // Try to delete the parent admin context.
    changeModeTo(true);
    contextsTree.select(getAdministratorContextLabelInTree(adminContextName));
    contextsTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
    // Expecting to have an error dialog.
    SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.UnableToDeleteContextCommand_ErrorDialog_Title);
    shell.activate();
    shell.bot().button(IDialogConstants.OK_LABEL).click();
    /*
     * Clean up.
     */
    // DON'T FORGET TO SET ANOTHER CONTEXT AS CURRENT TO DELETE THE CREATED CONTEXT.
    setDefaultContextAsCurrent();
    deleteAdminContext(adminContextName);
  }

}