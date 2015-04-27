/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.project.ProjectFactory;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages;
import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;

import junit.framework.Assert;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class EditAdminContextTests extends AbstractContextTest {
  @Test
  public void testChangeContextAdministratorsOnReadOnlyAndNotReadOnlyContextDescription() throws Exception {
    /*
     * Initialization.
     */
    // ErrorDialog display must be allowed !
    final boolean oldAutomatedMode = ErrorDialog.AUTOMATED_MODE;
    ErrorDialog.AUTOMATED_MODE = false;

    final SWTBotView variablesViewPart = getBot().viewById(VIEW_ID_VARIABLES);
    final String adminContextName = "AdminContextForChangeContextAdministratorsTests";
    final String adminContextDisplayedName = getAdministratorContextLabelInTree(adminContextName);
    final SWTBotTree contextsTree = getContextsTree();

    // Create a context.
    createAdminContextUsingNewContextWizard(adminContextName);
    Assert.assertFalse("Variables view should not be dirty after context creation.", variablesViewPart.getReference().isDirty());
    // Context is created in the workspace directory, get its description.contextproject file.
    File createdContextDirectory = Platform.getLocation().append(adminContextName).toFile();
    Assert.assertTrue("Can't find created context directory.", createdContextDirectory.isDirectory());
    File contextDescriptionFile = new File(createdContextDirectory, ProjectFactory.DEFAULT_CONTEXTS_PROJECT_DESCRIPTION);
    Assert.assertTrue("Can't find created context description file.", contextDescriptionFile.isFile());
    long lastcontextDescriptionFileModificationTime = contextDescriptionFile.lastModified();
    /*
     * Change the administrators list with description.contextsproject file read only.
     */
    // Set context description file read only.
    contextDescriptionFile.setWritable(false);
    // Select the context node and edit it.
    final SWTBotTreeItem contextNode = contextsTree.getTreeItem(adminContextDisplayedName);
    contextNode.select();
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();

    // Close warning popup about context administrators not found in LDAP database
    // if any
    try {
      SWTBotShell ldapAccessErrorShell = getBot().shell(Messages.EditContextWizard_LdapAccessError_Title);
      ldapAccessErrorShell.bot().button(IDialogConstants.OK_LABEL).click();
      ldapAccessErrorShell.close();
    } catch (WidgetNotFoundException exception_p) {
      // Nothing to do
    }

    SWTBotShell editContextShell = getBot().shell(Messages.EditContextWizard_Wizard_Title);
    editContextShell.activate();
    // Add a new FakeAdmin.
    editContextShell.bot().button(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.EditContextPage_AdministratorsAdd_Button_Text).click();
    SWTBotShell newAdministratorShell = editContextShell.bot().shell("Add administrator"); // String is hard coded, "Add administrator" wizard is internal.
    newAdministratorShell.bot().textWithLabel("Login* :").setText("FakeAdmin"); // String is hard coded, wizard page comes from an external library.
    newAdministratorShell.bot().button(IDialogConstants.FINISH_LABEL).click();
    // Finish context edition wizard.
    editContextShell.bot().button(IDialogConstants.FINISH_LABEL).click();
    // A save error dialog must be displayed.
    SWTBotShell contextDescriptionSaveErrorShell = getBot().shell(Messages.EditContextWizard_ContextDescriptionSaveError_Title);
    Assert.assertTrue("Trying to save a read only context, a save error dialog must be displayed.", contextDescriptionSaveErrorShell.isActive());
    // Click on OK in the error dialog.
    contextDescriptionSaveErrorShell.bot().button(IDialogConstants.OK_LABEL).click();
    Assert.assertFalse("Variables view must not be dirty after a change in the administrators list.", variablesViewPart.getReference().isDirty());
    /*
     * Change the administrators list with description.contextsproject file writable.
     */
    // Set context description file writable.
    contextDescriptionFile.setWritable(true);
    // Finish context edition wizard.
    editContextShell.bot().button(IDialogConstants.FINISH_LABEL).click();
    // Check context description file have to be modified.
    Assert.assertFalse(ProjectFactory.DEFAULT_CONTEXTS_PROJECT_DESCRIPTION + " file must be modified after a change in the administors list.",
        lastcontextDescriptionFileModificationTime == contextDescriptionFile.lastModified());
    Assert.assertFalse("Variables view must not be dirty after a change in the administrators list.", variablesViewPart.getReference().isDirty());
    /*
     * Clean up.
     */
    deleteAdminContext(adminContextName);

    // Reset AUTOMATED_MODE to its old value.
    ErrorDialog.AUTOMATED_MODE = oldAutomatedMode;
  }

  @Test
  public void testChangeContextNameOnReadOnlyAndNotReadOnlyContextDescription() throws Exception {
    /*
     * Initialization.
     */
    // ErrorDialog display must be allowed !
    final boolean oldAutomatedMode = ErrorDialog.AUTOMATED_MODE;
    ErrorDialog.AUTOMATED_MODE = false;

    final SWTBotView variablesViewPart = getBot().viewById(VIEW_ID_VARIABLES);
    final String adminContextName = "AdminContextForChangeContextNameTests";
    final String adminContextDisplayedName = getAdministratorContextLabelInTree(adminContextName);
    final String adminContextNewName = "AdminContextForChangeContextNameTests NewName";
    final String adminContextDisplayedNewName = getAdministratorContextLabelInTree(adminContextNewName);
    final SWTBotTree contextsTree = getContextsTree();

    // Create a context.
    createAdminContextUsingNewContextWizard(adminContextName);
    Assert.assertFalse("Variables view should not be dirty after context creation.", variablesViewPart.getReference().isDirty());
    // Context is created in the workspace directory, get its description.contextproject file.
    File createdContextDirectory = Platform.getLocation().append(adminContextName).toFile();
    Assert.assertTrue("Can't find created context directory.", createdContextDirectory.isDirectory());
    File contextDescriptionFile = new File(createdContextDirectory, ProjectFactory.DEFAULT_CONTEXTS_PROJECT_DESCRIPTION);
    Assert.assertTrue("Can't find created context description file.", contextDescriptionFile.isFile());
    File contextFile = new File(createdContextDirectory, FileHelper.generateContextFileName(adminContextName));
    Assert.assertTrue("Can't find created context description file.", contextFile.isFile());
    /*
     * Change the context name.
     */
    // Select the context node and edit it.
    final SWTBotTreeItem contextNode = contextsTree.getTreeItem(adminContextDisplayedName);
    contextNode.select();
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();

    // Close warning popup about context administrators not found in LDAP database
    // if any
    try {
      SWTBotShell ldapAccessErrorShell = getBot().shell(Messages.EditContextWizard_LdapAccessError_Title);
      ldapAccessErrorShell.bot().button(IDialogConstants.OK_LABEL).click();
      ldapAccessErrorShell.close();
    } catch (WidgetNotFoundException exception_p) {
      // Nothing to do
    }

    SWTBotShell editContextShell = getBot().shell(Messages.EditContextWizard_Wizard_Title);
    editContextShell.activate();
    // Change context name.
    editContextShell.bot().textWithLabel(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.AbstractEditVariablePage_Label_Title_Name)
        .setText(adminContextNewName);
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(contextNode, adminContextDisplayedNewName), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    // Finish context edition wizard.
    editContextShell.bot().button(IDialogConstants.FINISH_LABEL).click();
    // Check new name isn't included in the context files.
    Assert.assertFalse("Context description file shouldn't contain context's new name.", FileHelper.readFile(contextDescriptionFile.getAbsolutePath())
        .contains(adminContextNewName));
    Assert.assertFalse("Context description file shouldn't contain context's new name.",
        FileHelper.readFile(contextFile.getAbsolutePath()).contains(adminContextNewName));

    long contextDescriptionFileLastModificationTime = contextDescriptionFile.lastModified();
    long contextFileLastModificationTime = contextFile.lastModified();
    /*
     * Try to save with description.contextsproject file not writable.
     */
    contextDescriptionFile.setWritable(false);
    contextFile.setWritable(true);
    // Call save action.
    getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
        .menu(WorkbenchMessages.SaveAction_text).click();
    // Check an error dialog is displayed and click the OK button.
    SWTBotShell contextSaveErrorShell = getBot().shell(com.thalesgroup.orchestra.framework.ui.view.Messages.VariablesView_ContextSaveError_Title);
    Assert.assertTrue("Trying to save a read only context, a save error dialog must be displayed.", contextSaveErrorShell.isActive());
    contextSaveErrorShell.bot().button(IDialogConstants.OK_LABEL).click();
    Assert.assertTrue("Variables view must stay dirty after a save on read only files.", variablesViewPart.getReference().isDirty());
    Assert.assertTrue("Context description file shouldn't be modified (it is read-only anyway).",
        contextDescriptionFile.lastModified() == contextDescriptionFileLastModificationTime);
    Assert.assertTrue("Context file shouldn't be modified.", contextFile.lastModified() == contextFileLastModificationTime);
    /*
     * Try to save with contexts file not writable.
     */
    contextDescriptionFile.setWritable(true);
    contextFile.setWritable(false);
    // Call save action.
    getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
        .menu(WorkbenchMessages.SaveAction_text).click();
    // Check an error dialog is displayed and click the OK button.
    contextSaveErrorShell = getBot().shell(com.thalesgroup.orchestra.framework.ui.view.Messages.VariablesView_ContextSaveError_Title);
    Assert.assertTrue("Trying to save a read only context, a save error dialog must be displayed.", contextSaveErrorShell.isActive());
    contextSaveErrorShell.bot().button(IDialogConstants.OK_LABEL).click();
    Assert.assertTrue("Variables view must stay dirty after a save on read only files.", variablesViewPart.getReference().isDirty());
    Assert.assertTrue("Context description file shouldn't be modified.", contextDescriptionFile.lastModified() == contextDescriptionFileLastModificationTime);
    Assert.assertTrue("Context file shouldn't be modified (it is read-only anyway).", contextFile.lastModified() == contextFileLastModificationTime);
    /*
     * Re-save with description.contextsproject file writable.
     */
    // Set context description file writable.
    contextDescriptionFile.setWritable(true);
    contextFile.setWritable(true);
    // Call save action.
    getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
        .menu(WorkbenchMessages.SaveAction_text).click();
    Assert.assertFalse("Variables view must not be dirty after a save on writable files.", variablesViewPart.getReference().isDirty());
    Assert.assertTrue("Context file should be modified.", contextFile.lastModified() != contextFileLastModificationTime);
    Assert.assertTrue("Context description file should be modified.", contextDescriptionFile.lastModified() != contextDescriptionFileLastModificationTime);
    /*
     * Clean up.
     */
    deleteAdminContext(adminContextNewName);

    // Reset AUTOMATED_MODE to its old value.
    ErrorDialog.AUTOMATED_MODE = oldAutomatedMode;
  }
}
