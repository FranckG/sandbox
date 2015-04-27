/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;
import com.thalesgroup.orchestra.framework.ui.tests.SWTBotTestHelper;

import junit.framework.Assert;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class CreateNewUserContextTests extends AbstractContextTest {
  /**
   * Initialization : create 2 admin contexts (with the same name) and import the first. <br>
   * Test : check that it is not possible to create a user context linked to the second admin context (since the 2 admin contexts have the same name).
   */
  @Test
  public void adminContextsNameCollision() throws Exception {
    /*
     * Initialization.
     */
    // Create two admin contexts with the same name and import one of them.
    final String adminContextsName = "AdminContextForCollisionTest";
    final String firstAdminContextProjectName = "FirstAdminContextProject";
    final String secondAdminContextProjectName = "SecondAdminContextProject";
    final String firstContextProjectLocation = createContextProject(null, firstAdminContextProjectName, adminContextsName, null, false);
    final String secondContextProjectLocation = createContextProject(null, secondAdminContextProjectName, adminContextsName, null, false);
    // Wait until admin context is created.
    getBot().waitUntil(getConditionFactory().createContextIsCreatedCondition(firstContextProjectLocation, 1), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    // Import first project.
    importContexts(firstContextProjectLocation);
    /*
     * UI test.
     */
    initializeTest();
    // Switch to user mode.
    changeModeTo(false);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Check "Join a Context..." is enabled in the contextual menu.
    Assert.assertTrue(contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.view.Messages.NewContextAction_Label_User).isEnabled());
    // Use "Join a Context..." in "File" menu.
    getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
        .menu(com.thalesgroup.orchestra.framework.ui.view.Messages.NewContextAction_Label_User).click();
    // Fill the new context wizard.
    SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.ui.wizard.Messages.ParticipateContextWizard_Dialog_Title_NewParticipation);
    shell.activate();
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.ImportContextsPage_Label_Text_Location).setText(
        secondContextProjectLocation);
    getBot().button(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.ImportContextsPage_Button_Text_Refresh).click();
    // Get context node (under its parent directory node), try to check it and verify it is not checked.
    SWTBotTreeItem contextNode =
        getBot().tree().getTreeItem(new Path(secondContextProjectLocation).removeLastSegments(1).toString()).getNode(adminContextsName);
    contextNode.check();
    Assert.assertFalse("Node corresponding to the context with the same name mustn't be checked.", contextNode.isChecked());
    Assert.assertFalse("Finish button is expected to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Cancel context creation.
    getBot().button(IDialogConstants.CANCEL_LABEL).click();
    /*
     * Clean up.
     */
    // Delete imported context.
    deleteAdminContext(adminContextsName);
    // Delete non imported context.
    FileUtils.deleteDirectory(new File(secondContextProjectLocation));
  }

  /**
   * Initialization : create an admin context and import it.<br>
   * Test steps : <br>
   * 1- Create a context as an user using the created admin context as a parent, <br>
   * 2- Check the user context is displayed in the contexts viewer in user mode, <br>
   * 3- Check the user context is loaded in the workspace, <br>
   * 4- Check the user context is not displayed in the contexts viewer in admin mode.
   * @throws Exception
   */
  @Test
  public void canCreateUserContext() throws Exception {
    /*
     * Initialization.
     */
    // Create an admin context and import it.
    final String adminContextName = "AdminContextToBeJoined";
    final String contextProjectLocation = createContextProject(null, null, adminContextName, null, false);
    // Wait until admin context is created.
    getBot().waitUntil(getConditionFactory().createContextIsCreatedCondition(contextProjectLocation, 1), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    importContexts(contextProjectLocation);
    final String userContextName = "Participation to " + adminContextName;
    /*
     * UI test.
     */
    initializeTest();
    // Switch to user mode.
    changeModeTo(false);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Check "Join a Context..." is enabled in the contextual menu.
    Assert.assertTrue(contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.view.Messages.NewContextAction_Label_User).isEnabled());
    // Use "Join a Context..." in "File" menu.
    getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
        .menu(com.thalesgroup.orchestra.framework.ui.view.Messages.NewContextAction_Label_User).click();
    // Fill the new context wizard.
    SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.ui.wizard.Messages.ParticipateContextWizard_Dialog_Title_NewParticipation);
    shell.activate();
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.ImportContextsPage_Label_Text_Location).setText(
        contextProjectLocation);
    getBot().button(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.ImportContextsPage_Button_Text_Refresh).click();
    // Get directory node, try to check it and verify it is not checked.
    SWTBotTreeItem directoryNode = getBot().tree().getTreeItem(new Path(contextProjectLocation).removeLastSegments(1).toString());
    directoryNode.check();
    Assert.assertFalse("Directory node mustn't be checked.", directoryNode.isChecked());
    // Get context node (under its parent directory node) and check it.
    SWTBotTreeItem contextNode = directoryNode.getNode(adminContextName);
    contextNode.check();
    Assert.assertFalse("Finish button is expected to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Fill user context name.
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.ParticipateContextWizard_Name_Label_Text).setText(userContextName);
    getBot().button(IDialogConstants.FINISH_LABEL).click();
    // Check the newly created user context is displayed in the contexts viewer.
    Assert.assertTrue("Expecting to find the new user context in the contexts viewer (in user mode).",
        SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, getParticipationLabelInTree(userContextName, adminContextName)));
    // Search for newly created participation.
    Context context = null;
    Couple<RootContextsProject, Context> foundCouple = findContextForName(userContextName, false);
    if (null != foundCouple) {
      context = foundCouple.getValue();
    }
    Assert.assertNotNull("Expecting to find context '" + userContextName + "' loaded in the workspace.", context);
    // Check the newly created user context isn't displayed in the admin contexts viewer.
    changeModeTo(true);
    Assert.assertFalse("Not expecting to find the new user context in the contexts viewer (in admin mode)",
        SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, getParticipationLabelInTree(userContextName, adminContextName)));
    /*
     * Clean up.
     */
    deleteAdminContext(adminContextName);
  }

  /**
   * Create a couple Admin/User contexts and import them.<br>
   * Tests: <br>
   * 1- Try to create a new admin context called "CollisionTestAdminContext1" => Forbidden. <br>
   * 2- Try to create a new user context called "Participation to CollisionTestAdminContext1" => Forbidden.
   * @throws Exception
   */
  @Test
  public void userContextsNameCollisions() throws Exception {
    /*
     * Initialization.
     */
    // Create an admin context and import it.
    final String existingProjectName = "ProjectForCollisionTest";
    final String existingAdminContextName = "AdminContextForCollisionTest";
    final String existingUserContextName = "UserContextForCollisionTest";
    final String existingContextProjectLocation = createContextProject(null, existingProjectName, existingAdminContextName, existingUserContextName, false);
    final String adminContextName = "AdminContextToParticipateTo";
    final String contextProjectLocation = createContextProject(null, null, adminContextName, null, false);
    // Wait until projects are created.
    getBot().waitUntil(getConditionFactory().createContextIsCreatedCondition(existingContextProjectLocation, 1), 
    				   AbstractTest.CONDITION_TIMEOUT,
    				   AbstractTest.CONDITION_INTERVAL);
    getBot().waitUntil(getConditionFactory().createContextIsCreatedCondition(contextProjectLocation, 1), 
    				   AbstractTest.CONDITION_TIMEOUT,
    				   AbstractTest.CONDITION_INTERVAL);
    importContexts(existingContextProjectLocation);
    importContexts(contextProjectLocation);
    final String userContextNonExistingName = "UserContextOtherName";
    /*
     * UI tests.
     */
    initializeTest();
    // Switch to user mode.
    changeModeTo(false);
    // Try to create a new user context.
    getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
        	.menu(com.thalesgroup.orchestra.framework.ui.view.Messages.NewContextAction_Label_User)
        	.click();
    SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.ui.wizard.Messages.ParticipateContextWizard_Dialog_Title_NewParticipation);
    shell.activate();
    // Load parent context selection list.
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.ImportContextsPage_Label_Text_Location)
    		.setText(contextProjectLocation);
    getBot().button(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.ImportContextsPage_Button_Text_Refresh).click();
    // Check parent context node (under its parent directory node).
    getBot().tree().getTreeItem(new Path(contextProjectLocation).removeLastSegments(1).toString()).getNode(adminContextName).check();
    // Give the name of the existing project => OK.
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.MainNewContextWizardPage_0).setText(existingProjectName);
    sleep(1);
    Assert.assertTrue("Finish button is expected to be enabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Give the name of the existing admin context => OK.
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.MainNewContextWizardPage_0).setText(existingAdminContextName);
    sleep(1);
    Assert.assertTrue("Finish button is expected to be enabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Give the name of the existing user context => NOK.
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.MainNewContextWizardPage_0).setText(existingUserContextName);
    sleep(1);
    Assert.assertFalse("Finish button is expected to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Give the name of the parent admin context => OK.
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.MainNewContextWizardPage_0).setText(adminContextName);
    sleep(1);
    Assert.assertTrue("Finish button is expected to be enabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Give a non used name => OK.
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.MainNewContextWizardPage_0).setText(userContextNonExistingName);
    sleep(1);
    Assert.assertTrue("Finish button is expected to be enabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Cancel context creation.
    getBot().button(IDialogConstants.CANCEL_LABEL).click();
    /*
     * Clean up.
     */
    deleteAdminContext(existingAdminContextName);
    deleteAdminContext(adminContextName);
  }
}
