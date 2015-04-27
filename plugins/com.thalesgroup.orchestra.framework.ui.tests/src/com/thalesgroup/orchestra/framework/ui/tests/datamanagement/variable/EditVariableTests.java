/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.variable;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages;
import com.thalesgroup.orchestra.framework.ui.tests.SWTBotTestHelper;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class EditVariableTests extends AbstractVariableTest {
  /**
   * In admin mode, check edit wizard doesn't allow to rename a variable with an existing variable name : <br>
   * 1- Inherited variable ArtefactPath, <br>
   * 2- File variable of the same category, <br>
   * 3- Folder variable in the same category.
   * @throws Exception
   */
  @Test
  public void canNotNameVariablesWithExistingNameInAdminMode() throws Exception {
    final Context adminContext = findContextForName(_adminContextName, false).getValue();
    final String artefactPathVariableName = DataUtil.getVariable(DataUtil.__ARTEFACTPATH_VARIABLE_NAME, adminContext).getName();

    initializeTest();
    changeModeTo(true);
    final SWTBotTree contextsTree = getContextsTree();
    final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
    // Select the "Orchestra" category.
    expandNodeWithCondition(contextsTree, adminContextDisplayedName);
    contextsTree.getTreeItem(adminContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
    final SWTBotTree variablesTree = getVariablesTree();
    variablesTree.select(_adminOrchestraVariable);
    // Open edit wizard using the contextual menu.
    variablesTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditVariableWizard_Wizard_Title);
    shell.activate();
    // Try with "ArtefactPath" variable.
    checkVariableNameCollisionOccurs(artefactPathVariableName);
    // Try with existing file variable.
    checkVariableNameCollisionOccurs(_adminOrchestraFileVariable);
    // Try with existing folder variable.
    checkVariableNameCollisionOccurs(_adminOrchestraFolderVariable);
    getBot().button(IDialogConstants.CANCEL_LABEL).click();
    // TO BE REMOVED !!!
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  /**
   * In user mode, check edit wizard doesn't allow to rename a variable with an existing variable name : <br>
   * 1- Inherited variable ArtefactPath, <br>
   * 2- Admin variable of the same category, <br>
   * 3- Admin file variable of the same category, <br>
   * 4- Admin folder variable of the same category, <br>
   * 5- User file variable of the same category, <br>
   * 6- User folder variable of the same category, <br>
   * @throws Exception
   */
  @Test
  public void canNotNameVariablesWithExistingNameInUserMode() throws Exception {
    final Context adminContext = findContextForName(_adminContextName, false).getValue();
    final String artefactPathVariableName = DataUtil.getVariable(DataUtil.__ARTEFACTPATH_VARIABLE_NAME, adminContext).getName();

    initializeTest();
    changeModeTo(false);
    final SWTBotTree contextsTree = getContextsTree();
    final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
    // Select the "Orchestra" category.
    expandNodeWithCondition(contextsTree, userContextDisplayedName);
    contextsTree.getTreeItem(userContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
    final SWTBotTree variablesTree = getVariablesTree();
    variablesTree.select(_userOrchestraVariable);
    // Open edit wizard using the contextual menu.
    variablesTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditVariableWizard_Wizard_Title);
    shell.activate();
    // Try with "ArtefactPath" variable.
    checkVariableNameCollisionOccurs(artefactPathVariableName);
    // Try with existing admin variable.
    checkVariableNameCollisionOccurs(_adminOrchestraVariable);
    // Try with existing admin file variable.
    checkVariableNameCollisionOccurs(_adminOrchestraFileVariable);
    // Try with existing admin folder variable.
    checkVariableNameCollisionOccurs(_adminOrchestraFolderVariable);
    // Try with existing user file variable.
    checkVariableNameCollisionOccurs(_userOrchestraFileVariable);
    // Try with existing user folder variable.
    checkVariableNameCollisionOccurs(_userOrchestraFolderVariable);
    getBot().button(IDialogConstants.CANCEL_LABEL).click();
    // TO BE REMOVED !!!
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  @Test
  public void canNotRenameAdminVariablesInUserMode() throws Exception {
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

      checkRenameOverridingVariableIsDisabled(_adminCategoryVariable);
      checkRenameOverridingVariableIsDisabled(_adminCategoryFileVariable);
      checkRenameOverridingVariableIsDisabled(_adminCategoryFolderVariable);
    }
  }

  @Test
  public void canNotRenameInheritedVariables() throws Exception {
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
      checkRenameOverridingVariableIsDisabled(artefactPathVariableName);
    }
    // User mode.
    {
      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Select the "Orchestra" category.
      expandNodeWithCondition(contextsTree, userContextDisplayedName);
      contextsTree.getTreeItem(userContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
      checkRenameOverridingVariableIsDisabled(artefactPathVariableName);
    }
  }

  @Test
  public void canRenameContextOwnedVariables() throws Exception {
    initializeTest();
    // Admin mode.
    {
      final String adminCategoryVariableNewName = "AdminVariableNewName";
      final String adminCategoryFileVariableNewName = "AdminFileVariableNewName";
      final String adminCategoryFolderVariableNewName = "AdminFolderVariableNewName";

      changeModeTo(true);
      final SWTBotTree contextsTree = getContextsTree();
      final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
      // Select the category owned by the admin context.
      expandNodeWithCondition(contextsTree, adminContextDisplayedName);
      contextsTree.getTreeItem(adminContextDisplayedName).getNode(_adminCategory).select();

      changeVariableName(_adminCategoryVariable, adminCategoryVariableNewName);
      changeVariableName(_adminCategoryFileVariable, adminCategoryFileVariableNewName);
      changeVariableName(_adminCategoryFolderVariable, adminCategoryFolderVariableNewName);
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    // User mode.
    {
      final String userCategoryVariableNewName = "UserVariableNewName";
      final String userCategoryFileVariableNewName = "UserFileVariableNewName";
      final String userCategoryFolderVariableNewName = "UserFolderVariableNewName";

      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Select the category owned by the admin context.
      expandNodeWithCondition(contextsTree, userContextDisplayedName);
      contextsTree.getTreeItem(userContextDisplayedName).getNode(_userCategory).select();

      changeVariableName(_userCategoryVariable, userCategoryVariableNewName);
      changeVariableName(_userCategoryFileVariable, userCategoryFileVariableNewName);
      changeVariableName(_userCategoryFolderVariable, userCategoryFolderVariableNewName);
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  private void changeVariableName(String variableOldName_p, String variableNewName_p) {
    final SWTBotTree variablesTree = getVariablesTree();
    variablesTree.select(variableOldName_p);
    // Open edit wizard using the contextual menu.
    variablesTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditVariableWizard_Wizard_Title);
    shell.activate();
    getBot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name).setText(variableNewName_p);
    // Wait for HMI update.
    sleep(1);
    getBot().button(IDialogConstants.FINISH_LABEL).click();
    Assert.assertTrue("Expecting the variable to have the new name.", SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, variableNewName_p));
  }

  private void checkRenameOverridingVariableIsDisabled(String variableName_p) {
    final SWTBotTree variablesTree = getVariablesTree();
    variablesTree.select(variableName_p);
    // Open edit wizard using the contextual menu.
    variablesTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditOverridingVariableWizard_Wizard_Title);
    shell.activate();
    Assert.assertTrue("Excepting the name field to be read only.",
        SWTUtils.hasStyle(getBot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name).widget, SWT.READ_ONLY));
    getBot().button(IDialogConstants.FINISH_LABEL).click();
  }

  private void checkVariableNameCollisionOccurs(String newVariableName_p) {
    getBot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name).setText(newVariableName_p);
    sleep(1);
    Assert.assertFalse("Expecting the Finish button to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
  }
}
