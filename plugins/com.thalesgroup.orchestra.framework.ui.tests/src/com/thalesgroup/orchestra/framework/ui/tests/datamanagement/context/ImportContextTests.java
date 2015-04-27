/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages;
import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;
import com.thalesgroup.orchestra.framework.ui.tests.SWTBotTestHelper;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class ImportContextTests extends AbstractContextTest {
  /**
   * Initialization : create 2 admin contexts (different project name but same context name). <br>
   * Tests : check that it is not possible to import both of them.
   * @throws Exception
   */
  @Test
  public void canNotImportTwoAdminContextsWithTheSameName() throws Exception {
    /*
     * Initialization.
     */
    final String firstAdminProjectName = "FirstImportTestProject";
    final String secondAdminProjectName = "SecondImportTestProject";
    final String commonAdminContextName = "ImportTestAdminContext";
    final String firstContextProjectLocation = createContextProject(null, firstAdminProjectName, commonAdminContextName, null, false);
    final String secondContextProjectLocation = createContextProject(null, secondAdminProjectName, commonAdminContextName, null, false);
    Assert.assertEquals("Incorrect initialization : expecting the two created projects in the same parent directory.",
        (new Path(firstContextProjectLocation)).removeLastSegments(1), (new Path(secondContextProjectLocation)).removeLastSegments(1));
    final String contextParentDirectory = (new Path(secondContextProjectLocation)).removeLastSegments(1).removeTrailingSeparator().toString();
    /*
     * UI tests.
     */
    // Switch to admin mode.
    changeModeTo(true);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Open import wizard.
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.ImportContextsAction_Action_Text).click();
    SWTBotShell importWizardShell = getBot().shell(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.ImportContextsWizard_Wizard_Title);
    importWizardShell.activate();
    Assert.assertFalse("Expecting the Finish button to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Set parent directory of the two contexts as root directory.
    getBot().textWithLabel(Messages.ImportContextsPage_Label_Text_Location).setText(contextParentDirectory);
    getBot().button(Messages.ImportContextsPage_Button_Text_Refresh).click();
    Assert.assertFalse("Expecting the Finish button to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Wait until contexts tree is populated.
    getBot().waitUntil(Conditions.widgetIsEnabled(getBot().tree()));
    // Check the two contexts.
    getBot().tree().getTreeItem(contextParentDirectory).getNode(commonAdminContextName, 0).check();
    getBot().tree().getTreeItem(contextParentDirectory).getNode(commonAdminContextName, 1).check();
    Assert.assertFalse("Expecting the Finish button to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    getBot().button(IDialogConstants.CANCEL_LABEL).click();
    /*
     * Clean up.
     */
    // Project are not loaded in the workspace -> Delete them directly from the disk.
    FileUtils.deleteDirectory(new File(firstContextProjectLocation));
    FileUtils.deleteDirectory(new File(secondContextProjectLocation));

  }

  /**
   * Initialization : create a couple admin/user contexts and import it in ODM. <br>
   * Test : try to import the admin context and check it isn't displayed the import wizard contexts list.
   * @throws Exception
   */
  @Test
  public void canNotImportTwoTimesAdminContextTest() throws Exception {
    /*
     * Initialization.
     */
    final String adminContextName = "CategoryTestAdminContext";
    final String userContextName = "CategoryTestUserContext";
    String contextProjectLocation = createContextProject(null, null, adminContextName, userContextName, false);
    // Wait until admin context is created.
    getBot().waitUntil(getConditionFactory().createContextIsCreatedCondition(contextProjectLocation, 1), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    importContexts(contextProjectLocation);
    /*
     * UI tests.
     */
    initializeTest();
    // Switch to admin mode.
    changeModeTo(true);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Open import wizard.
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.ImportContextsAction_Action_Text).click();
    SWTBotShell importWizardShell = getBot().shell(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.ImportContextsWizard_Wizard_Title);
    importWizardShell.activate();
    Assert.assertFalse("Expecting the Finish button to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Set workspace as root directory.
    String workspaceAbsolutePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
    getBot().textWithLabel(Messages.ImportContextsPage_Label_Text_Location).setText(workspaceAbsolutePath);
    getBot().button(Messages.ImportContextsPage_Button_Text_Refresh).click();
    Assert.assertFalse("Expecting the Finish button to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // The already loaded context mustn't be present in the list.
    String contextPathInImportWizard = (new Path(contextProjectLocation)).removeLastSegments(1).removeTrailingSeparator().toString();
    Assert.assertFalse("Not expecting the context to be in the import list.",
        SWTBotTestHelper.areNodesDisplayedInTree(getBot().tree(), contextPathInImportWizard, adminContextName));
    getBot().button(IDialogConstants.CANCEL_LABEL).click();
    /*
     * Clean up.
     */
    deleteAdminContext(adminContextName);
  }

  /**
   * Initialization : create a couple admin/user contexts. <br>
   * Tests : import the admin context (containing an user context). Then check that: <br>
   * 1- The admin context is displayed in the contexts viewer in admin mode, <br>
   * 2- The user context is displayed in the contexts viewer in user mode.
   * @throws Exception
   */
  @Test
  public void importAdminContextTest() throws Exception {
    /*
     * Initialization.
     */
    final String adminContextName = "CategoryTestAdminContext";
    final String userContextName = "CategoryTestUserContext";
    createContextProject(null, null, adminContextName, userContextName, false);
    /*
     * UI tests.
     */
    initializeTest();
    // Set administrator mode.
    changeModeTo(true);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Open import wizard.
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.ImportContextsAction_Action_Text).click();
    SWTBotShell importWizardShell = getBot().shell(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.ImportContextsWizard_Wizard_Title);
    importWizardShell.activate();
    Assert.assertFalse("Expecting the Finish button to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Set workspace as root directory.
    String workspaceAbsolutePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
    getBot().textWithLabel(Messages.ImportContextsPage_Label_Text_Location).setText(workspaceAbsolutePath);
    getBot().button(Messages.ImportContextsPage_Button_Text_Refresh).click();
    Assert.assertFalse("Expecting the Finish button to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Select the context to import.
    getBot().tree().getTreeItem(workspaceAbsolutePath).getNode(adminContextName).check();
    getBot().button(IDialogConstants.FINISH_LABEL).click();
    // Check the admin context is imported.
    Assert.assertTrue("Expecting to have the imported context in the Contexts viewer.",
        contextsTree.getTreeItem(getAdministratorContextLabelInTree(adminContextName)).isVisible());
    // Check the participation is imported along with its parent.
    changeModeTo(false);
    Assert.assertTrue("Expecting to have the imported context in the Contexts viewer.",
        contextsTree.getTreeItem(getParticipationLabelInTree(userContextName, adminContextName)).isVisible());
    /*
     * Clean up.
     */
    deleteAdminContext(adminContextName);
  }
}
