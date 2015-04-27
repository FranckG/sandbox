/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.variable;

import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.ui.tests.SWTBotTestHelper;

import junit.framework.Assert;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class DeleteVariableTests extends AbstractVariableTest {
  /**
   * Tests: <br>
   * 1- In admin mode, delete all admin variables, <br>
   * 2- In user mode, delete all user variables.
   * @throws Exception
   */
  @Test
  public void canDeleteVariablesInContextOwnedCategory() throws Exception {
    initializeTest();
    // Admin mode.
    {
      changeModeTo(true);
      final SWTBotTree contextsTree = getContextsTree();
      final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
      // Select the category owned by the admin context.
      expandNodeWithCondition(contextsTree, adminContextDisplayedName);
      contextsTree.getTreeItem(adminContextDisplayedName).getNode(_adminCategory).select();
      deleteAllVariableTypes(adminContextDisplayedName, _adminCategoryVariable, _adminCategoryFileVariable, _adminCategoryFolderVariable);
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    // User mode.
    {
      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Select the category owned by the user context.
      expandNodeWithCondition(contextsTree, userContextDisplayedName);
      contextsTree.getTreeItem(userContextDisplayedName).getNode(_userCategory).select();
      deleteAllVariableTypes(getContextLabelInDeleteConfirmationTree(_userContextName, _adminContextName), _userCategoryVariable, _userCategoryFileVariable,
          _userCategoryFolderVariable);
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  public void canDeleteVariablesInInheritedCategory() throws Exception {
    initializeTest();
    // Admin mode.
    {
      changeModeTo(true);
      final SWTBotTree contextsTree = getContextsTree();
      final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
      // Select the category owned by the admin context.
      expandNodeWithCondition(contextsTree, adminContextDisplayedName);
      contextsTree.getTreeItem(adminContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
      deleteAllVariableTypes(adminContextDisplayedName, _adminOrchestraVariable, _adminOrchestraFileVariable, _adminOrchestraFolderVariable);
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    // User mode.
    {
      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Select the category owned by the user context.
      expandNodeWithCondition(contextsTree, userContextDisplayedName);
      contextsTree.getTreeItem(userContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
      deleteAllVariableTypes(getContextLabelInDeleteConfirmationTree(_userContextName, _adminContextName), _userOrchestraVariable, _userOrchestraFileVariable,
          _userOrchestraFolderVariable);
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  /**
   * Test: <br>
   * 1- In user mode, check admin variables can't be deleted.
   * @throws Exception
   */
  @Test
  public void canNotDeleteAdminVariablesInUserMode() throws Exception {
    initializeTest();
    // User mode.
    {
      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Select the "Orchestra" category.
      expandNodeWithCondition(contextsTree, userContextDisplayedName);
      contextsTree.getTreeItem(userContextDisplayedName).getNode(_adminCategory).select();
      final SWTBotTree variablesTree = getVariablesTree();
      variablesTree.select(_adminCategoryVariable);
      Assert.assertFalse("Expecting to have a disabled delete action.", variablesTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item"))
          .isEnabled());

      variablesTree.select(_adminCategoryFileVariable);
      Assert.assertFalse("Expecting to have a disabled delete action.", variablesTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item"))
          .isEnabled());

      variablesTree.select(_adminCategoryFolderVariable);
      Assert.assertFalse("Expecting to have a disabled delete action.", variablesTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item"))
          .isEnabled());
    }
  }

  /**
   * Tests: <br>
   * 1- In admin mode, check "ArtefactPath" variable under "Orchestra" can't be deleted, <br>
   * 2- In user mode, same checks.
   * @throws Exception
   */
  @Test
  public void canNotDeleteInheritedVariables() throws Exception {
    final Context adminContext = findContextForName(_adminContextName, false).getValue();
    final String artefactPathVariableName = DataUtil.getVariable(DataUtil.__ARTEFACTPATH_VARIABLE_NAME, adminContext).getName();

    initializeTest();
    // Admin mode.
    {
      changeModeTo(true);
      final SWTBotTree contextsTree = getContextsTree();
      final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
      // Select the "Orchestra" category.
      expandNodeWithCondition(contextsTree, adminContextDisplayedName);
      contextsTree.getTreeItem(adminContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
      final SWTBotTree variablesTree = getVariablesTree();
      variablesTree.select(artefactPathVariableName);
      Assert.assertFalse("Expecting to have a disabled delete action.", variablesTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item"))
          .isEnabled());
    }
    // User mode.
    {
      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Select the "Orchestra" category.
      expandNodeWithCondition(contextsTree, userContextDisplayedName);
      contextsTree.getTreeItem(userContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
      final SWTBotTree variablesTree = getVariablesTree();
      variablesTree.select(artefactPathVariableName);
      Assert.assertFalse("Expecting to have a disabled delete action.", variablesTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item"))
          .isEnabled());
    }
  }

  private void deleteAllVariableTypes(String contextDisplayedName_p, String... variableNames_p) {
    final SWTBotTree variablesTree = getVariablesTree();
    for (String variableName : variableNames_p) {
      int rowNumberBefore = variablesTree.rowCount();
      // Call delete action in the contextual menu.
      variablesTree.select(variableName);
      variablesTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
      getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.DeleteCommandWithConfirmation_ConfirmationDialog_Title).activate();
      // Check delete confirmation window contains the context name.
      Assert.assertTrue("Expecting to have context \"" + contextDisplayedName_p + "\"" + " displayed in the delete confirmation window.",
          SWTBotTestHelper.areNodesDisplayedInTree(getBot().tree(), contextDisplayedName_p));
      getBot().button(IDialogConstants.YES_LABEL).click();
      // Check there is one less variable.
      Assert.assertEquals("Expecting to have one less element in the variables viewer.", rowNumberBefore - 1, variablesTree.rowCount());
    }
  }
}
