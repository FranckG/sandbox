/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.variable;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.ui.viewer.Messages;

import junit.framework.Assert;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class CreateVariableTests extends AbstractVariableTest {
  /**
   * Test: <br>
   * 1- In user mode, using the contextual menus of the ContextsViewer and of the VariablesViewer, create one variable of each type in a category owned by the
   * admin context.
   * @throws Exception
   */
  @Test
  public void canCreateVariablesInAdminCategoryInUserMode() throws Exception {
    initializeTest();
    // User mode.
    {
      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Select the category owned by the user context.
      expandNodeWithCondition(contextsTree, userContextDisplayedName);
      contextsTree.getTreeItem(userContextDisplayedName).getNode(_adminCategory).select();
      // Create variables using contextual menu of ContextsViewer.
      createAllTypesOfVariableUsingContextualMenu(contextsTree);
      // Create variables using contextual menu of VariablesViewer.
      createAllTypesOfVariableUsingContextualMenu(getVariablesTree());
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  /**
   * Tests: <br>
   * 1- In admin mode, using the contextual menus of the ContextsViewer and of the VariablesViewer, create one variable of each type in a category owned by the
   * admin context, <br>
   * 2- In user mode, using the contextual menus of the ContextsViewer and of the VariablesViewer, create one variable of each type in a category owned by the
   * user context.
   * @throws Exception
   */
  @Test
  public void canCreateVariablesInContextOwnedCategory() throws Exception {
    initializeTest();
    // Admin mode.
    {
      changeModeTo(true);
      final SWTBotTree contextsTree = getContextsTree();
      final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
      // Select the category owned by the admin context.
      expandNodeWithCondition(contextsTree, adminContextDisplayedName);
      contextsTree.getTreeItem(adminContextDisplayedName).getNode(_adminCategory).select();
      // Create variables using contextual menu of ContextsViewer.
      createAllTypesOfVariableUsingContextualMenu(contextsTree);
      // Create variables using contextual menu of VariablesViewer.
      createAllTypesOfVariableUsingContextualMenu(getVariablesTree());
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
      // Create variables using contextual menu of ContextsViewer.
      createAllTypesOfVariableUsingContextualMenu(contextsTree);
      // Create variables using contextual menu of VariablesViewer.
      createAllTypesOfVariableUsingContextualMenu(getVariablesTree());
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  /**
   * Tests: <br>
   * 1- In admin mode, using the contextual menus of the ContextsViewer and of the VariablesViewer, create one variable of each type in the category
   * "Orchestra", <br>
   * 2- In user mode, using the contextual menus of the ContextsViewer and of the VariablesViewer, create one variable of each type in the category "Orchestra".
   * @throws Exception
   */
  @Test
  public void canCreateVariablesUnderOrchestra() throws Exception {
    initializeTest();
    // Admin mode.
    {
      changeModeTo(true);
      final SWTBotTree contextsTree = getContextsTree();
      final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
      // Select the "Orchestra" category.
      expandNodeWithCondition(contextsTree, adminContextDisplayedName);
      contextsTree.getTreeItem(adminContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
      // Create variables using contextual menu of ContextsViewer.
      createAllTypesOfVariableUsingContextualMenu(contextsTree);
      // Create variables using contextual menu of VariablesViewer.
      createAllTypesOfVariableUsingContextualMenu(getVariablesTree());
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    // User mode.
    {
      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Select the "Orchestra" category.
      expandNodeWithCondition(contextsTree, userContextDisplayedName);
      contextsTree.getTreeItem(userContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
      // Create variables using contextual menu of ContextsViewer.
      createAllTypesOfVariableUsingContextualMenu(contextsTree);
      // Create variables using contextual menu of VariablesViewer.
      createAllTypesOfVariableUsingContextualMenu(getVariablesTree());
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  /**
   * Tests: <br>
   * 1- In admin mode and in the contextual menus of the ContextsViewer and of the VariablesViewer, check all variable creation actions are not enabled, <br>
   * 2- In user mode and in the contextual menus of the ContextsViewer and of the VariablesViewer, check all variable creation actions are not enabled. <br>
   * @throws Exception
   */
  @Test
  public void canNotCreateVariablesUnderOrchestraInstallation() throws Exception {
    initializeTest();
    // Admin mode.
    {
      changeModeTo(true);
      final SWTBotTree contextsTree = getContextsTree();
      final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
      // Expand context node.
      expandNodeWithCondition(contextsTree, adminContextDisplayedName);
      // Try to create variables using contextual menu.
      contextsTree.getTreeItem(adminContextDisplayedName).getNode(ModelUtil.INSTALLATION_CATEGORY_NAME).select();
      // Variable creation actions must be disabled in ContextsViewer.
      checkCreationOfAllTypesOfVariableIsNotEnabled(contextsTree);
      // Variable creation actions must be disabled in VariablesViewer.
      checkCreationOfAllTypesOfVariableIsNotEnabled(getVariablesTree());
    }
    // User mode.
    {
      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Expand context node.
      expandNodeWithCondition(contextsTree, userContextDisplayedName);
      // Try to create variables using contextual menu.
      contextsTree.getTreeItem(userContextDisplayedName).getNode(ModelUtil.INSTALLATION_CATEGORY_NAME).select();
      // Variable creation actions must be disabled in ContextsViewer.
      checkCreationOfAllTypesOfVariableIsNotEnabled(contextsTree);
      // Variable creation actions must be disabled in VariablesViewer.
      checkCreationOfAllTypesOfVariableIsNotEnabled(getVariablesTree());
    }
  }

  private void checkCreationOfAllTypesOfVariableIsNotEnabled(SWTBotTree swtBotTreeForContextualMenu_p) {
    String[] variableTypeNames =
        { ContextsPackage.Literals.VARIABLE.getName(), ContextsPackage.Literals.FILE_VARIABLE.getName(), ContextsPackage.Literals.FOLDER_VARIABLE.getName() };
    // Check no new variable action is available.
    for (String variableTypeName : variableTypeNames) {
      Assert.assertFalse("Expecting to have new " + variableTypeName + " action disabled.",
          swtBotTreeForContextualMenu_p.contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(variableTypeName).isEnabled());
    }

  }

  private void createAllTypesOfVariableUsingContextualMenu(SWTBotTree swtBotTreeForContextualMenu_p) {
    final SWTBotTree variablesTree = getVariablesTree();
    String[] variableTypeNames =
        { ContextsPackage.Literals.VARIABLE.getName(), ContextsPackage.Literals.FILE_VARIABLE.getName(), ContextsPackage.Literals.FOLDER_VARIABLE.getName() };
    // Create a variable of each type.
    for (String variableTypeName : variableTypeNames) {
      int rowNumberBefore = variablesTree.rowCount();
      swtBotTreeForContextualMenu_p.contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(variableTypeName).click();
      Assert.assertEquals("Expecting to have one more element in the variables viewer.", rowNumberBefore + 1, variablesTree.rowCount());
    }
  }
}
