/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.category;

import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.ui.tests.SWTBotTestHelper;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class DeleteCategoryTests extends AbstractCategoryTest {
  @Test
  public void canDeleteAdminCategoryInAdminMode() throws Exception {
    final String adminContextDisplayName = getAdministratorContextLabelInTree(_adminContextName);
    initializeTest();
    // Admin mode.
    changeModeTo(true);
    // Find context viewer.
    final SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    expandNodeWithCondition(contextsTree, adminContextDisplayName);
    // Select category to delete.
    contextsTree.getTreeItem(adminContextDisplayName).select();
    contextsTree.getTreeItem(adminContextDisplayName).getNode(_adminContextCategory1).select();
    // Click on delete.
    contextsTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
    SWTBotShell shell =
        getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.DeleteCommandWithConfirmation_ConfirmationDialog_Title);
    shell.activate();
    Assert.assertTrue("Expecting to have the admin context name in the confirmation list.", getBot().tree().getTreeItem(adminContextDisplayName).isVisible());
    getBot().button(IDialogConstants.YES_LABEL).click();
    Assert.assertFalse("Expecting the categroy to be deleted.", contextsTree.getTreeItem(adminContextDisplayName).getNodes().contains(_adminContextCategory1));
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  @Test
  public void canDeleteUserCategoryInUserMode() throws Exception {
    final String userContextDisplayName = getParticipationLabelInTree(_userContextName, _adminContextName);
    initializeTest();
    // User mode.
    changeModeTo(false);
    // Find context viewer.
    final SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    expandNodeWithCondition(contextsTree, userContextDisplayName);
    // Select CategoryToRename.
    contextsTree.getTreeItem(userContextDisplayName).select();
    contextsTree.getTreeItem(userContextDisplayName).getNode(_userContextCategory1).select();
    // Open edit wizard using the contextual menu.
    contextsTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
    SWTBotShell shell =
        getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.DeleteCommandWithConfirmation_ConfirmationDialog_Title);
    shell.activate();
    SWTBotTree deleteConfirmationTree = getBot().tree();
    Assert.assertTrue("Expecting to have the user context name in the confirmation list.",
        SWTBotTestHelper.areNodesDisplayedInTree(deleteConfirmationTree, getContextLabelInDeleteConfirmationTree(_userContextName, _adminContextName)));
    getBot().button(IDialogConstants.YES_LABEL).click();
    Assert.assertFalse("Expecting the categroy to be deleted.", contextsTree.getTreeItem(userContextDisplayName).getNodes().contains(_userContextCategory1));
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  @Test
  public void canNotDeleteAdminCategoryInUserMode() throws Exception {
    initializeTest();
    // Switch to user mode.
    changeModeTo(false);
    // Find context viewer.
    final SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    expandNodeWithCondition(contextsTree, getParticipationLabelInTree(_userContextName, _adminContextName));
    Assert.assertFalse("Expecting to have a disabled edit menu item.", contextsTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item"))
        .isEnabled());
  }

  @Test
  public void canNotDeleteInheritedCategoryInAdminMode() throws Exception {
    initializeTest();
    // Switch to admin mode.
    changeModeTo(true);
    // Find context viewer.
    final SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    expandNodeWithCondition(contextsTree, getAdministratorContextLabelInTree(_adminContextName));
    Assert.assertFalse("Expecting to have a disabled edit menu item.", contextsTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item"))
        .isEnabled());

  }

  @Test
  public void canNotDeleteInheritedCategoryInUserMode() throws Exception {
    initializeTest();
    // Switch to user mode.
    changeModeTo(false);
    // Find context viewer.
    final SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    expandNodeWithCondition(contextsTree, getParticipationLabelInTree(_userContextName, _adminContextName));
    Assert.assertFalse("Expecting to have a disabled edit menu item.", contextsTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item"))
        .isEnabled());

  }
}
