/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;
import com.thalesgroup.orchestra.framework.ui.tests.SWTBotTestHelper;

import junit.framework.Assert;

/**
 * Contexts admin/user creation tests.
 * @author t0076261
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class CreateNewAdminContextTests extends AbstractContextTest {
  /**
   * Initialization : create a project with a couple admin/user contexts and import it.<br>
   * Test steps: <br>
   * 1- Check an admin context can't be created with an existing project name, <br>
   * 2- Check an admin context can't be created with an existing admin context name, <br>
   * 3- Check an admin context can be created with an existing user context name, <br>
   * 3- Check an admin context can be created with a different name. <br>
   * @throws Exception
   */
  @Test
  public void adminContextsNameCollisions() throws Exception {
    /*
     * Initialization.
     */
    // Create an admin context and import it.
    final String existingProjectName = "ProjectForCollisionTest";
    final String existingAdminContextName = "AdminContextForCollisionTest";
    final String existingUserContextName = "UserContextForCollisionTest";
    final String existingContextProjectLocation = createContextProject(null, existingProjectName, existingAdminContextName, existingUserContextName, false);
    // Wait until admin context is created.
    getBot().waitUntil(getConditionFactory().createContextIsCreatedCondition(existingContextProjectLocation, 1), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    importContexts(existingContextProjectLocation);
    final String adminContextNonExistingName = "AdminContextOtherName";
    /*
     * UI tests.
     */
    initializeTest();
    // Switch to admin mode.
    changeModeTo(true);
    // Try to create a new admin context.
    getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
        .menu(com.thalesgroup.orchestra.framework.ui.view.Messages.NewContextAction_Label_Admin).click();
    SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.ui.wizard.Messages.NewContextWizard_1);
    shell.activate();
    // Give the name of the existing project => NOK.
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.MainNewContextWizardPage_0).setText(existingProjectName);
    sleep(1);
    Assert.assertFalse("Next button is expected to be disabled.", getBot().button(IDialogConstants.NEXT_LABEL).isEnabled());
    Assert.assertFalse("Finish button is expected to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Give the name of the existing admin context => NOK.
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.MainNewContextWizardPage_0).setText(existingAdminContextName);
    sleep(1);
    Assert.assertFalse("Next button is expected to be disabled.", getBot().button(IDialogConstants.NEXT_LABEL).isEnabled());
    Assert.assertFalse("Finish button is expected to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Give the name of the existing user context => OK.
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.MainNewContextWizardPage_0).setText(existingUserContextName);
    sleep(1);
    Assert.assertTrue("Next button is expected to be enabled.", getBot().button(IDialogConstants.NEXT_LABEL).isEnabled());
    Assert.assertTrue("Finish button is expected to be enabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Give a non used name => OK.
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.MainNewContextWizardPage_0).setText(adminContextNonExistingName);
    sleep(1);
    Assert.assertTrue("Next button is expected to be enabled.", getBot().button(IDialogConstants.NEXT_LABEL).isEnabled());
    Assert.assertTrue("Finish button is expected to be enabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Cancel context creation.
    getBot().button(IDialogConstants.CANCEL_LABEL).click();
    /*
     * Clean up.
     */
    deleteAdminContext(existingAdminContextName);
  }

  /**
   * Test steps : <br>
   * 1- Create a context as an administrator, <br>
   * 2- Check the admin context is displayed in the contexts viewer in admin mode, <br>
   * 3- Check the admin context is loaded in the workspace, <br>
   * 4- Check the admin context is not displayed in the contexts viewer in user mode.
   * @throws Exception
   */
  @Test
  public void canCreateAdminContext() throws Exception {
    /*
     * UI tests.
     */
    initializeTest();
    // Admin context to create name.
    String adminContextName = "AdministratorTestContext";
    // Switch to admin mode.
    changeModeTo(true);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Check "New Context..." is enabled in the contextual menu.
    Assert.assertTrue(contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.view.Messages.NewContextAction_Label_Admin).isEnabled());
    // Use "New Context..." in "File" menu.
    getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
        .menu(com.thalesgroup.orchestra.framework.ui.view.Messages.NewContextAction_Label_Admin).click();
    // Fill the new context wizard.
    SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.ui.wizard.Messages.NewContextWizard_1);
    shell.activate();
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.MainNewContextWizardPage_0).setText(adminContextName);
    sleep(1);
    getBot().button(IDialogConstants.FINISH_LABEL).click();
    // Check the newly created admin context is displayed in the contexts viewer (in admin mode).
    Assert.assertTrue("Expecting to find the new admin context in the contexts viewer (in admin mode).",
        SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, getAdministratorContextLabelInTree(adminContextName)));
    // Search for newly created project.
    RootContextsProject project = null;
    Couple<RootContextsProject, Context> foundCouple = findContextForName(adminContextName, false);
    if (null != foundCouple) {
      project = foundCouple.getKey();
    }
    Assert.assertNotNull("Expecting to find context '" + adminContextName + "' loaded in the workspace.", project);
    // Check the newly created admin context isn't displayed in the user contexts viewer.
    changeModeTo(false);
    Assert.assertFalse("Not expecting to find the new admin context in the contexts viewer (in user mode)",
        SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, getAdministratorContextLabelInTree(adminContextName)));
    /*
     * Clean up.
     */
    deleteAdminContext(adminContextName);
  }
}