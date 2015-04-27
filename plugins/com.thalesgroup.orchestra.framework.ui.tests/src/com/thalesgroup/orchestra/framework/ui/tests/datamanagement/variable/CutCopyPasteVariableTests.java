/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.variable;

import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.ui.tests.SWTBotTestHelper;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class CutCopyPasteVariableTests extends AbstractVariableTest {
  @Test
  public void copyToTheSameContextOwnedCategory() throws Exception {
    initializeTest();
    // Admin mode.
    changeModeTo(true);
    final SWTBotTree contextsTree = getContextsTree();
    final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
    // Select the AdminContext category.
    contextsTree.expandNode(adminContextDisplayedName);
    contextsTree.getTreeItem(adminContextDisplayedName).getNode(_adminCategory).select();
    final SWTBotTree variablesTree = getVariablesTree();
    // Select variables of each type present under admin category.
    variablesTree.select(_adminCategoryVariable, _adminCategoryFileVariable, _adminCategoryFolderVariable);
    // ODM should be not dirty before a copy operation.
    SWTBotTestHelper.assertOdmIsNotDirty();
    // Copy the selected variables.
    variablesTree.contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_copy).click();
    // ODM should be not dirty after a copy operation.
    SWTBotTestHelper.assertOdmIsNotDirty();
    // Paste the selected variables.
    variablesTree.contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_paste).click();
    // ODM should be dirty after a paste operation.
    SWTBotTestHelper.assertOdmIsDirty();
    // Check old variables are always present.
    assertTrue("Old variable " + _adminCategoryVariable + " must be present in the variable tree.",
        SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, _adminCategoryVariable));
    assertTrue("Old variable " + _adminCategoryFileVariable + " must be present in the variable tree.",
        SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, _adminCategoryFileVariable));
    assertTrue("Old variable " + _adminCategoryFolderVariable + " must be present in the variable tree.",
        SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, _adminCategoryFolderVariable));
    // Check new variables (copied variables) are present.
    String copiedAdminCategoryVariableName =
        MessageFormat.format(com.thalesgroup.orchestra.framework.model.handler.command.Messages.PasteFromClipboardCommand_First_CopyOf, _adminCategoryVariable);
    assertTrue("Copied variable " + copiedAdminCategoryVariableName + " must be present in the variable tree.",
        SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, copiedAdminCategoryVariableName));
    String copiedAdminCategoryFileVariableName =
        MessageFormat.format(com.thalesgroup.orchestra.framework.model.handler.command.Messages.PasteFromClipboardCommand_First_CopyOf, _adminCategoryVariable);
    assertTrue("Copied variable " + copiedAdminCategoryFileVariableName + " must be present in the variable tree.",
        SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, copiedAdminCategoryFileVariableName));
    String copiedAdminCategoryFolderVariableName =
        MessageFormat.format(com.thalesgroup.orchestra.framework.model.handler.command.Messages.PasteFromClipboardCommand_First_CopyOf, _adminCategoryVariable);
    assertTrue("Copied variable " + copiedAdminCategoryFolderVariableName + " must be present in the variable tree.",
        SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, copiedAdminCategoryFolderVariableName));
    // Save context.
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }
}
