/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context;

import java.io.File;

import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;

import junit.framework.Assert;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class DeleteUserContextTests extends AbstractContextTest {
  /**
   * As a user, create participation then delete without then with removing contents on disk.
   * @throws Exception
   */
  @Test
  public void canDeleteAnExistingUserContext() throws Exception {
    // Create test structure.
    initializeTest();
    changeModeTo(true);
    String administratorContextName = "DeleteAsAUser";
    String participationName = createContext(administratorContextName, true);
    changeModeTo(false);
    RootContextsProject foundContext = findContextForName(participationName, false).getKey();
    String projectFullPath = foundContext.getLocation();
    String participationPath = projectFullPath + RootContextsProject.getUserContextPath();
    String participationUILabel = getParticipationLabelInTree(participationName, administratorContextName);
    // Delete without removing contents.
    {
      SWTBotTree tree = getBot().tree().select(participationUILabel);
      tree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
      getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.DeleteContext_Dialog_Title_UserMode).activate();
      getBot().button(IDialogConstants.YES_LABEL).click();
      // Check that participation is no longer accessible (unloaded).
      Assert.assertNull("Participation '" + participationName + "' is expected to be deleted !", findContextForName(participationName, false));
      // Check that context is still accessible (loaded).
      changeModeTo(true);
      Assert.assertNotNull("Context '" + administratorContextName + "' is expected to be accessible !", findContextForName(administratorContextName, false));
      // Check that participation still exists on disk.
      File participationFile = new File(participationPath);
      Assert.assertTrue("Delete participation '" + participationName + "' should still be available on file system", participationFile.exists());
      changeModeTo(false);
    }
    // There is no need to import project again.
    // Switching from user to administrator back to user magically forces participations to reappear.
    // Delete removing contents.
    {
      SWTBotTree tree = getBot().tree().select(participationUILabel);
      tree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
      getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.DeleteContext_Dialog_Title_UserMode).activate();
      getBot().checkBox().click();
      getBot().button(IDialogConstants.YES_LABEL).click();
      // Check that participation is no longer accessible (try to load it to be sure).
      Assert.assertNull("Participation '" + participationName + "' is expected to be deleted !", findContextForName(participationName, true));
      // Check that context is still accessible (loaded).
      changeModeTo(true);
      Assert.assertNotNull("Context '" + administratorContextName + "' is expected to be accessible !", findContextForName(administratorContextName, false));
      // Check that participation does no longer exist on disk.
      File participationFile = new File(participationPath);
      Assert.assertFalse("Deleted participation '" + participationName + "' shouldn't still be available on file system", participationFile.exists());
    }
    /**
     * Clean up.
     */
    deleteAdminContext(administratorContextName);
  }

  /**
   * Initialization : create a couple admin/user contexts and import it. <br>
   * Test steps: <br>
   * 1- Set the user context as current, <br>
   * 2- Try to delete it -> An error dialog must be displayed.
   * @throws Exception
   */
  @Test
  public void canNotDeleteCurrentUserContext() throws Exception {
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

}
