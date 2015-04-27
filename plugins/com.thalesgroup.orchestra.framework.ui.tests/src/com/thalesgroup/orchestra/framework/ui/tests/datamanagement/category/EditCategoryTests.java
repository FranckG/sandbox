/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.category;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages;
import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class EditCategoryTests extends AbstractCategoryTest {
  /**
   * In admin mode, check edit wizard doesn't allow to rename a category with an existing category name : <br>
   * 1- Category in the same context, <br>
   * 2- Inherited category (Orchestra), <br>
   * 3- Transient category (Orchestra Installation).
   * @throws Exception
   */
  @Test
  public void canNotNameCategoryWithExistingNameInAdminMode() throws Exception {
    initializeTest();
    // Admin mode.
    changeModeTo(true);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    final String adminContext = getAdministratorContextLabelInTree(_adminContextName);
    expandNodeWithCondition(contextsTree, adminContext);
    // Select CategoryToRename.
    contextsTree.getTreeItem(adminContext).getNode(_adminContextCategory1).select();
    // Open edit wizard using the contextual menu.
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditCategoryWizard_Wizard_Title);
    shell.activate();
    // Try with an existing category name.
    SWTBotText categoryNameText = getBot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name);
    categoryNameText.setText(_adminContextCategory2);
    // Get the item whom name will change
    final SWTBotTreeItem item0 = contextsTree.getTreeItem(adminContext).getNode(_adminContextCategory1);
    // Condition on the first element i.e. the category whom name will change
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(item0, _adminContextCategory2), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    Assert.assertFalse("Expecting the Finish button to be disabled.", shell.bot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Try with Orchestra.
    categoryNameText.setText(DataUtil.__CATEGORY_ORCHESTRA);
    // Condition on the first element i.e. the category whom name will change
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(item0, DataUtil.__CATEGORY_ORCHESTRA), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    Assert.assertFalse("Expecting the Finish button to be disabled.", shell.bot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Try with Orchestra Installation.
    categoryNameText.setText(ModelUtil.INSTALLATION_CATEGORY_NAME);
    // Condition on the first element i.e. the category whom name will change
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(item0, ModelUtil.INSTALLATION_CATEGORY_NAME), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    Assert.assertFalse("Expecting the Finish button to be disabled.", shell.bot().button(IDialogConstants.FINISH_LABEL).isEnabled());

    // Close edit wizard.
    getBot().button(IDialogConstants.CANCEL_LABEL).click();
    // Save is needed even if nothing has been modified.
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  /**
   * In user mode, check edit wizard doesn't allow to rename a category with an existing category name : <br>
   * 1- Category in the same context (user), <br>
   * 2- Category in the admin context, <br>
   * 3- Inherited category (Orchestra), <br>
   * 4- Transient category (Orchestra Installation).
   * @throws Exception
   */
  @Test
  public void canNotNameCategoryWithExistingNameInUserMode() throws Exception {
    initializeTest();
    // Switch to user mode.
    changeModeTo(false);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    final String participationContext = getParticipationLabelInTree(_userContextName, _adminContextName);
    expandNodeWithCondition(contextsTree, participationContext);
    // Select CategoryToRename.
    contextsTree.getTreeItem(participationContext).getNode(_userContextCategory1).select();
    // Open edit wizard using the contextual menu.
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditCategoryWizard_Wizard_Title);
    shell.activate();
    // Get the item whom name will change
    final SWTBotTreeItem item0 = contextsTree.getTreeItem(participationContext).getNode(_userContextCategory1);
    // Try with an existing category name in user context.
    SWTBotText categoryNameText = getBot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name);
    categoryNameText.setText(_userContextCategory2);
    // Condition on the first element i.e. the category whom name will change
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(item0, _userContextCategory2), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    Assert.assertFalse("Expecting the Finish button to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Try with an existing category name in admin context.
    categoryNameText.setText(_adminContextCategory1);
    // Condition on the first element i.e. the category whom name will change
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(item0, _adminContextCategory1), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    Assert.assertFalse("Expecting the Finish button to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Try with Orchestra.
    categoryNameText.setText(DataUtil.__CATEGORY_ORCHESTRA);
    // Condition on the first element i.e. the category whom name will change
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(item0, DataUtil.__CATEGORY_ORCHESTRA), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    Assert.assertFalse("Expecting the Finish button to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
    // Try with Orchestra Installation.
    categoryNameText.setText(ModelUtil.INSTALLATION_CATEGORY_NAME);
    // Condition on the first element i.e. the category whom name will change
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(item0, ModelUtil.INSTALLATION_CATEGORY_NAME), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    Assert.assertFalse("Expecting the Finish button to be disabled.", getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());

    // Close edit wizard.
    getBot().button(IDialogConstants.CANCEL_LABEL).click();
    // Save is needed even if nothing has been modified.
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  /**
   * Check the "edit" menu item isn't enabled in user mode on an admin category.
   * @throws Exception
   */
  @Test
  public void canNotRenameAdminCategoryInUserMode() throws Exception {
    initializeTest();
    // Switch to user mode.
    changeModeTo(false);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    expandNodeWithCondition(contextsTree, getParticipationLabelInTree(_userContextName, _adminContextName));
    contextsTree.getTreeItem(getParticipationLabelInTree(_userContextName, _adminContextName)).getNode(_adminContextCategory1).select();
    Assert.assertFalse("Expecting to have a disabled edit menu item.", contextsTree.contextMenu(
        com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).isEnabled());

  }

  /**
   * Check that, in admin mode, the "edit" menu item isn't enabled for the inherited category "Orchestra".
   * @throws Exception
   */
  @Test
  public void canNotRenameInheritedCategoryInAdminMode() throws Exception {
    initializeTest();
    // Admin mode.
    changeModeTo(true);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    expandNodeWithCondition(contextsTree, getAdministratorContextLabelInTree(_adminContextName));
    contextsTree.getTreeItem(getAdministratorContextLabelInTree(_adminContextName)).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
    Assert.assertFalse("Expecting to have a disabled edit menu item.", contextsTree.contextMenu(
        com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).isEnabled());
  }

  /**
   * Check that, in user mode, the "edit" menu item isn't enabled for the inherited category "Orchestra".
   * @throws Exception
   */
  @Test
  public void canNotRenameInheritedCategoryInUserMode() throws Exception {
    initializeTest();
    // Switch to user mode.
    changeModeTo(false);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    expandNodeWithCondition(contextsTree, getParticipationLabelInTree(_userContextName, _adminContextName));
    contextsTree.getTreeItem(getParticipationLabelInTree(_userContextName, _adminContextName)).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
    Assert.assertFalse("Expecting to have a disabled edit menu item.", contextsTree.contextMenu(
        com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).isEnabled());
  }

  /**
   * In admin mode, rename the admin category using the edit wizard and check the new name is taken into account.
   * @throws Exception
   */
  @Test
  public void canRenameAdminCategory() throws Exception {
    final String adminCategoryNewName = "AdminCategoryNewName";
    initializeTest();
    // Admin mode.
    changeModeTo(true);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    expandNodeWithCondition(contextsTree, getAdministratorContextLabelInTree(_adminContextName));
    // Select CategoryToRename.
    final SWTBotTreeItem editNode = contextsTree.getTreeItem(getAdministratorContextLabelInTree(_adminContextName)).getNode(_adminContextCategory1);
    editNode.select();
    // Open edit wizard using the contextual menu.
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditCategoryWizard_Wizard_Title);
    shell.activate();
    getBot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name).setText(adminCategoryNewName);
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(editNode, adminCategoryNewName), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    getBot().button(IDialogConstants.FINISH_LABEL).click();
    Assert.assertTrue("Expecting the category to have the new name.", contextsTree.getTreeItem(getAdministratorContextLabelInTree(_adminContextName)).getNode(
        adminCategoryNewName).isVisible());
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  /**
   * In user mode, rename the user category using the edit wizard and check the new name is taken into account.
   * @throws Exception
   */
  @Test
  public void canRenameUserCategory() throws Exception {
    final String userCategoryNewName = "UserCategoryNewName";
    initializeTest();
    // User mode.
    changeModeTo(false);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    expandNodeWithCondition(contextsTree, getParticipationLabelInTree(_userContextName, _adminContextName));
    final SWTBotTreeItem editNode = contextsTree.getTreeItem(getParticipationLabelInTree(_userContextName, _adminContextName)).getNode(_userContextCategory1);
    editNode.select();
    // Open edit wizard using the contextual menu.
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditCategoryWizard_Wizard_Title);
    shell.activate();
    getBot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name).setText(userCategoryNewName);
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(editNode, userCategoryNewName), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    getBot().button(IDialogConstants.FINISH_LABEL).click();
    Assert.assertTrue("Expecting the category to have the new name.", contextsTree
        .getTreeItem(getParticipationLabelInTree(_userContextName, _adminContextName)).getNode(userCategoryNewName).isVisible());
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }
}
