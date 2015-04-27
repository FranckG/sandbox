/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;
import com.thalesgroup.orchestra.framework.ui.viewer.Messages;

import junit.framework.Assert;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class SaveActionTests extends AbstractTest {
  /**
   * Test steps:<br>
   * - Create a context and modify it,<br>
   * - Set context files read-only and do a save -> an error dialog is displayed and ODM stays dirty,<br>
   * - Set context files writable and do a save -> save is done and ODM is no longer dirty.
   * @throws Exception
   */
  @Test
  public void testSaveOnReadOnlyAndNotReadOnlyContext() throws Exception {
    /*
     * Initialization.
     */
    // ErrorDialog display must be allowed !
    final boolean oldAutomatedMode = ErrorDialog.AUTOMATED_MODE;
    ErrorDialog.AUTOMATED_MODE = false;

    final SWTBotView variablesViewPart = getBot().viewById(VIEW_ID_VARIABLES);
    final String adminContextName = "AdminContextForSaveTestsOnReadOnlyAndNotReadOnlyFiles";
    final String adminContextDisplayedName = getAdministratorContextLabelInTree(adminContextName);
    final SWTBotTree contextsTree = getContextsTree();
    // Admin mode.
    changeModeTo(true);
    // Create a context.
    createContext(adminContextName, false);
    // Context is created in the workspace directory.
    File createdContextDirectory = Platform.getLocation().append(adminContextName).toFile();
    Assert.assertTrue("Can't find created context directory.", createdContextDirectory.isDirectory());
    /*
     * Do a basic change on the created context to have a dirty view.
     */
    contextsTree.getTreeItem(adminContextDisplayedName).select();
    getContextsTree().contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.CATEGORY.getName()).click();
    Assert.assertTrue("Variables view should be dirty.", variablesViewPart.getReference().isDirty());
    /*
     * Save the context with read only files.
     */
    // Set all project's files read-only.
    for (File contextFile : createdContextDirectory.listFiles()) {
      boolean isSetReadOnly = contextFile.setWritable(false);
      Assert.assertTrue("Can't set read only context file.", isSetReadOnly);
    }
    // Call save action.
    getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
        .menu(WorkbenchMessages.SaveAction_text).click();
    // Check an error dialog is displayed and click the OK button.
    SWTBotShell contextSaveErrorShell = getBot().shell(com.thalesgroup.orchestra.framework.ui.view.Messages.VariablesView_ContextSaveError_Title);
    Assert.assertTrue("Trying to save a read only context, a save error dialog must be displayed.", contextSaveErrorShell.isActive());
    contextSaveErrorShell.bot().button(IDialogConstants.OK_LABEL).click();
    Assert.assertTrue("Variables view must stay dirty after a save on read only files.", variablesViewPart.getReference().isDirty());
    /*
     * Save the context with writable files.
     */
    // Set all project's files writable.
    for (File contextFile : createdContextDirectory.listFiles()) {
      boolean isSetWritable = contextFile.setWritable(true);
      Assert.assertTrue("Can't set writable context file.", isSetWritable);
    }
    // Call save action.
    getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
        .menu(WorkbenchMessages.SaveAction_text).click();
    Assert.assertFalse("Variables view must not be dirty after a save on writable files.", variablesViewPart.getReference().isDirty());

    /*
     * Clean up.
     */
    deleteAdminContext(adminContextName);

    // Reset AUTOMATED_MODE to its old value.
    ErrorDialog.AUTOMATED_MODE = oldAutomatedMode;
  }
}
