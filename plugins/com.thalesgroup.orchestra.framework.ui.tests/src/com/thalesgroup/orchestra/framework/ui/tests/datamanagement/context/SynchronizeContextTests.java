/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.FileVariable;
import com.thalesgroup.orchestra.framework.model.contexts.FolderVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.migration.AbstractMigration;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages;
import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;
import com.thalesgroup.orchestra.framework.ui.tests.SWTBotTestHelper;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.swtbotcondition.ContextIsCreatedCondition;

import junit.framework.Assert;

/**
 * Synchronization tests between an inherited context and its parent
 * @author S0032874
 */
@RunWith(SWTBotJunit4ClassRunner.class)
@SuppressWarnings("nls")
public class SynchronizeContextTests extends AbstractContextTest {

  protected final static String __inheritedContextName = "SynchronizationTestsInheritedContext";

  protected static AbstractMigration __migration;

  protected final static String __parentContextName = "SynchronizationTestsParentContext";

  protected static String __testsDirectory;

  protected static final String CATEGORY_0 = "Category0";

  protected static final String COPIED_CATEGORY = "Copied_category";

  protected static final String COPIED_VARIABLE = "Copied_variable";

  protected static final String COPY_OF_VARIABLE_FINAL = "Copy of variableFinal";

  protected static final String DESCRIPTION = "description";

  protected static final String EMPTY_STRING = "";

  protected static final String FILE_VARIABLE_1 = "file_variable_1";

  protected static final String FOLDER_VARIABLE_1 = "folder_variable_1";

  protected static final String INHERITED_CATEGORY_VALUE = "Inherited_value_in_category";

  protected static final String INHERITED_VARIABLE_VALUE = "Inherited_value_in_variable";

  protected static final String MODIFIED_VALUE = "value1_modified";

  protected static final String MULTI_VALUE_1 = "multiValue1";

  protected static final String MULTI_VALUE_2 = "multiValue2";

  protected static final String MULTI_VALUE_3 = "multiValue3";

  protected static final String MULTI_VARIABLE = "multiVariable";

  protected static final String MULTI_VARIABLE_FINAL = "multiVariableFinal";

  protected static final String MULTI_VARIABLE_MANDATORY = "multiVariableMandatory";

  protected static final String NEW_CATEGORY = "new_category";

  protected static final String NEW_CATEGORY_NAME = "New_category_name";

  protected static final String NEW_MULTIPLE_VARIABLE = "new_multiple_variable";

  protected static final String NEW_PARENT_CATEGORY = "New_parent_category";

  protected static final String NEW_PARENT_VARIABLE = "New_parent_Variable";

  protected static final String NEW_VARIABLE_NAME = "New_variable_name";

  protected static final String OVERRIDE_VARIABLE_VALUE = "OverridingVariableValue";

  protected static final String PARENT_VARIABLE_VALUE = "Parent_variable_value";

  protected static final String ROOT_CATEGORY_FIVE = "rootCategoryFive";

  protected static final String ROOT_CATEGORY_FOUR = "rootCategoryFour";

  protected static final String ROOT_CATEGORY_ONE = "rootCategoryOne";

  protected static final String ROOT_CATEGORY_SIX = "rootCategorySix";

  protected static final String ROOT_CATEGORY_THREE = "rootCategoryThree";

  protected static final String ROOT_CATEGORY_TWO = "rootCategoryTwo";

  protected static final String VALUE_1 = "value1";

  protected static final String VARIABLE = "variable";

  protected static final String VARIABLE_0 = "Variable0";

  protected static final String VARIABLE_2 = "Variable_2";

  protected static final String VARIABLE_FINAL = "variableFinal";

  protected static final String VARIABLE_MANDATORY = "variableMandatory";

  protected static final String VARIABLE_NO_DESCRIPTION = "variableNoDescription";

  protected static final String VARIABLE_NO_VALUE = "variableNoValue";

  protected String _inheritedContextDisplayName = null;

  protected String _pendingElements = "Pending Elements (" + __inheritedContextName + ")";

  /**
   * Create and activate a shell on the inherited variable edit viewer
   * @param tree_p
   * @param variable_p
   * @return The shell
   */
  protected SWTBotShell activateEditOverrideVariableShell(SWTBotTree tree_p, String variable_p) {
    tree_p.select(variable_p);
    tree_p.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditOverridingVariableWizard_Wizard_Title);
    shell.activate();
    return shell;
  }

  /**
   * Create and activate a shell on the variable edit viewer
   * @param tree_p
   * @param variable_p
   * @return The shell
   */
  protected SWTBotShell activateEditVariableShell(SWTBotTree tree_p, String variable_p) {
    tree_p.select(variable_p);
    tree_p.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditVariableWizard_Wizard_Title);
    shell.activate();
    return shell;
  }

  /**
   * Retrieve the rootCategoryOne variable tree, <br>
   * Also allows to "click" on the rootCategoryOne tree element
   * @return The VariableTree
   */
  protected SWTBotTree getRootCategoryOneTree() {
    final SWTBotTree contextsTree = getContextsTree();
    contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_ONE).select();
    return getVariablesTree();
  }

  @Before
  public void preTestInitialization() throws Exception {
    if (null == _inheritedContextDisplayName) {
      _inheritedContextDisplayName = getContextLabelInDeleteConfirmationTree(__inheritedContextName, __parentContextName);
    }
    initializeTest();
    changeModeTo(true);
  }

  /**
   * Save the parent context and then synchronize the inherited context
   */
  protected void saveParentContextAndSynchronizeInheritedContext() {
    // XXX Saving done without passing by the HMI, acceptable ? (there is no intention of testing the Save functionality)
    ModelHandlerActivator.getDefault().getEditingDomain().save();

    // Synchronize the inherited context with the parent context
    SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    contextsTree.getTreeItem(_inheritedContextDisplayName).select();
    contextsTree.getTreeItem(_inheritedContextDisplayName).contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.SynchronizeAction_Action_Text)
        .click();
    SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.SynchronizeCommand_Dialog_Title);
    shell.bot().button(IDialogConstants.OK_LABEL).click();
    shell.close();
  }

  /**
   * Check that a new category and a new variable added in the parent context are in the inherited context after synchronization: <br>
   * 1 The new category is present in the inherited context after synchronization, <br>
   * 2 The new variable is present in the new category in the inherited context after synchronization, <br>
   * @throws Exception
   */
  @SuppressWarnings("all")
  public void testAddNewElementsInParentContextAndSynchronize() throws Exception {
    SWTBotTree contextsTree = getContextsTree();
    String parentName = getContextLabelInDeleteConfirmationTree(__parentContextName, null);
    expandNodeWithCondition(contextsTree, parentName, DataUtil.__CATEGORY_ORCHESTRA);
    // Create new category and new variable in the parent context
    SWTBotTreeItem orchestraNode = contextsTree.getTreeItem(parentName).getNode(DataUtil.__CATEGORY_ORCHESTRA);
    orchestraNode.select();
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.viewer.Messages.ContextsViewer_NewMenu_Text)
        .menu(ContextsPackage.Literals.CATEGORY.getName()).click();
    SWTBotTreeItem node = orchestraNode.getNode(CATEGORY_0).select();
    node.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditCategoryWizard_Wizard_Title);
    shell.activate();
    SWTBotText categoryNameText = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name);
    categoryNameText.setText(NEW_PARENT_CATEGORY);
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(node, NEW_PARENT_CATEGORY), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    shell.bot().button(IDialogConstants.FINISH_LABEL).click();
    shell.close();

    node.contextMenu(com.thalesgroup.orchestra.framework.ui.viewer.Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.VARIABLE.getName())
        .click();
    node.select();
    SWTBotTree variablesTree = getVariablesTree();
    String name = variablesTree.select(0).cell(0, 0);
    shell = activateEditVariableShell(variablesTree, name);
    SWTBotText textValue = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name);
    textValue.setFocus();
    textValue.setText(NEW_PARENT_VARIABLE);
    textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textValue.setFocus();
    textValue.setText(PARENT_VARIABLE_VALUE);
    SWTBotButton button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    shell.close();

    saveParentContextAndSynchronizeInheritedContext();

    contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    orchestraNode = contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA);

    // Test that the new category in the parent context is also in the inherited context
    String assertMessage = "Expecting to find the category: " + NEW_PARENT_CATEGORY;
    Assert.assertTrue(assertMessage, orchestraNode.getNodes().contains(NEW_PARENT_CATEGORY));

    // Test that the new variable in the parent context is also in the inherited context
    orchestraNode.getNode(NEW_PARENT_CATEGORY).select();
    variablesTree = getVariablesTree();
    assertMessage = "Expecting to find variable: " + NEW_PARENT_VARIABLE;
    Assert.assertTrue(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, NEW_PARENT_VARIABLE));

  }

  /**
   * Check that if the name of a variable or of a category is changed in the parent context, the inherited variable and category name is changed after
   * synchronization: <br>
   * 1 The category in the inherited context has the new name, <br>
   * 2 There are no category in the inherited context with the old name, <br>
   * 3 The variable in the inherited context has the new name, <br>
   * 4 There are no variable in the inherited context with the old name, <br>
   * @throws Exception
   */

  public void testChangeElementsNameInParentContextAndSynchronize() throws Exception {
    SWTBotTree contextsTree = getContextsTree();
    String parentName = getContextLabelInDeleteConfirmationTree(__parentContextName, null);
    expandNodeWithCondition(contextsTree, parentName, DataUtil.__CATEGORY_ORCHESTRA);
    // Change the name of an existing category and an existing variable
    SWTBotTreeItem rootTwo = contextsTree.getTreeItem(parentName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_TWO).select();
    rootTwo.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditCategoryWizard_Wizard_Title);
    shell.activate();
    SWTBotText categoryNameText = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name);
    categoryNameText.setText(NEW_CATEGORY_NAME);
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(rootTwo, NEW_CATEGORY_NAME), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    shell.bot().button(IDialogConstants.FINISH_LABEL).click();
    shell.close();

    contextsTree.getTreeItem(parentName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_ONE).select();
    SWTBotTree variablesTree = getVariablesTree();
    shell = activateEditVariableShell(variablesTree, VARIABLE);
    SWTBotText text = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name);
    text.setFocus();
    text.setText(NEW_VARIABLE_NAME);
    SWTBotButton button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    shell.close();

    saveParentContextAndSynchronizeInheritedContext();

    contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    SWTBotTreeItem orchestraNode = contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA);

    // Test that the category in the parent context is also in the inherited context with the right new name
    String assertMessage = "Expecting to find a category with the name: " + NEW_CATEGORY_NAME;
    Assert.assertTrue(assertMessage, orchestraNode.getNodes().contains(NEW_CATEGORY_NAME));
    // Test that there is no category with the old name in the inherited context
    assertMessage = "Expecting not to find a category with the name: " + ROOT_CATEGORY_TWO;
    Assert.assertFalse(assertMessage, orchestraNode.getNodes().contains(ROOT_CATEGORY_TWO));

    // Test that the variable in the parent context is also in the inherited context with the right new name
    orchestraNode.getNode(ROOT_CATEGORY_ONE).select();
    variablesTree = getVariablesTree();
    assertMessage = "Expecting to find a variable with the name: " + NEW_VARIABLE_NAME;
    Assert.assertTrue(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, NEW_VARIABLE_NAME));
    // Test that there is no variable with the old name in the category from the inherited context
    assertMessage = "Expecting not to find a variable with the name: " + VARIABLE;
    Assert.assertFalse(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, VARIABLE));

  }

  /**
   * Check that a create category in an inherited context can be edited : <br>
   * 1 Create a category in an inherited context, <br>
   * 2 Edit a created category in an inherited context, <br>
   * 3 Delete a created category in an inherited context, <br>
   * @throws Exception
   */

  public void testEditExpandInheritedCategory() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    // Test that a category can be created
    final SWTBotTreeItem orchestraNode = contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA);
    orchestraNode.select();
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.viewer.Messages.ContextsViewer_NewMenu_Text)
        .menu(ContextsPackage.Literals.CATEGORY.getName()).click();
    String assertMessage = "Expecting to find category: " + CATEGORY_0;
    Assert.assertTrue(assertMessage, orchestraNode.getNode(CATEGORY_0).isVisible());

    // Test that a created category can be edited
    SWTBotTreeItem node = orchestraNode.getNode(CATEGORY_0).select();
    node.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditCategoryWizard_Wizard_Title);
    shell.activate();
    SWTBotText categoryNameText = getBot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name);
    categoryNameText.setText(NEW_CATEGORY);
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(node, NEW_CATEGORY), AbstractTest.CONDITION_TIMEOUT, AbstractTest.CONDITION_INTERVAL);
    SWTBotButton button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    assertMessage = "Expecting the category name to have been change to: " + NEW_CATEGORY;
    Assert.assertTrue(assertMessage, orchestraNode.getNode(NEW_CATEGORY).isVisible());
    shell.close();

    // Test that a created category can be deleted
    node = orchestraNode.getNode(NEW_CATEGORY);
    node.select();
    node.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
    getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.DeleteCommandWithConfirmation_ConfirmationDialog_Title).activate();
    getBot().button(IDialogConstants.YES_LABEL).click();
    assertMessage = "Expecting the category: " + NEW_CATEGORY + " to have been deleted";
    Assert.assertFalse(assertMessage, orchestraNode.getNodes().contains(NEW_CATEGORY));

  }

  /**
   * Check that a created file variable from an inherited context can be edited : <br>
   * 1 Create a file variable in an inherited context, <br>
   * 2 Edit a created file variable in an inherited context, <br>
   * 3 Delete a created file variable in an inherited context, <br>
   * @throws Exception
   */

  public void testEditExpandInheritedFileVariable() throws Exception {
    testVariableCRUD("FileVariable0", VALUE_1, ContextsPackage.Literals.FILE_VARIABLE.getName());
  }

  /**
   * Check that a created folder variable from an inherited context can be edited : <br>
   * 1 Create a folder variable in an inherited context, <br>
   * 2 Edit a created folder variable in an inherited context, <br>
   * 3 Delete a created folder variable in an inherited context, <br>
   * @throws Exception
   */

  public void testEditExpandInheritedFolderVariable() throws Exception {
    testVariableCRUD("FolderVariable0", VALUE_1, ContextsPackage.Literals.FOLDER_VARIABLE.getName());
  }

  /**
   * Check that a created variable from an inherited context can be edited : <br>
   * 1 Create a variable in an inherited context, <br>
   * 2 Edit a created variable in an inherited context, <br>
   * 3 Delete a created variable in an inherited context, <br>
   * @throws Exception
   */

  public void testEditExpandInheritedVariable() throws Exception {
    testVariableCRUD(VARIABLE_0, VALUE_1, ContextsPackage.Literals.VARIABLE.getName());
  }

  /**
   * Check that if a variable value from the parent context is different from the value in the inherited context, different choices are made during
   * synchronization: <br>
   * 1 If the parent variable is not final, the parent value is lost and the inherited value is chosen for the inherited variable after synchronization, <br>
   * 2 If the parent variable has become final, the parent value is kept for the inherited value and the old inherited value is stored in the "Pending Elements"
   * category, <br>
   * 3 The old inherited value is stored in the "Pending Elements" category
   * @throws Exception
   */

  public void testEditFinalVariableInParentContextAndSynchronize() throws Exception {
    SWTBotTree contextsTree = getContextsTree();
    String parentName = getContextLabelInDeleteConfirmationTree(__parentContextName, null);
    expandNodeWithCondition(contextsTree, parentName, DataUtil.__CATEGORY_ORCHESTRA);

    // Edit the parent context, set variable to final
    contextsTree.getTreeItem(parentName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_FOUR).select();
    SWTBotTree variablesTree = getVariablesTree();
    SWTBotShell shell = activateEditVariableShell(variablesTree, VARIABLE_FINAL);
    SWTBotCheckBox box = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Final);
    box.select();
    SWTBotButton finishButton = shell.bot().button(IDialogConstants.FINISH_LABEL);
    finishButton.setFocus();
    finishButton.click();
    shell.close();

    // Modify the values in the inherited context
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_FOUR).select();
    variablesTree = getVariablesTree();
    shell = activateEditOverrideVariableShell(variablesTree, VARIABLE_FINAL);
    SWTBotText textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textValue.setFocus();
    textValue.setText(MODIFIED_VALUE);
    finishButton = shell.bot().button(IDialogConstants.FINISH_LABEL);
    finishButton.setFocus();
    finishButton.click();
    shell.close();
    shell = activateEditOverrideVariableShell(variablesTree, VARIABLE);
    textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textValue.setFocus();
    textValue.setText(MODIFIED_VALUE);
    finishButton = shell.bot().button(IDialogConstants.FINISH_LABEL);
    finishButton.setFocus();
    finishButton.click();
    shell.close();

    saveParentContextAndSynchronizeInheritedContext();

    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_FOUR).select();
    variablesTree = getVariablesTree();

    // Test that the value of the inherited variable which is not final has not changed
    String assertMessage = "Expecting the variable: " + VARIABLE + " to have the value: " + MODIFIED_VALUE;
    Assert.assertEquals(assertMessage, MODIFIED_VALUE, variablesTree.getTreeItem(VARIABLE).cell(1));
    // Test that the value of the inherited variable which has become final has changed to the value of the parent context
    assertMessage = "Expecting the variable: " + VARIABLE_FINAL + " to have the value: " + VALUE_1;
    Assert.assertEquals(assertMessage, VALUE_1, variablesTree.getTreeItem(VARIABLE_FINAL).cell(1));
    // Test that the old value of the now final variable is stored in the "pending" category;
    contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(_pendingElements).select();
    variablesTree = getVariablesTree();
    assertMessage = "Expecting the variable: " + VARIABLE_FINAL + " to have the value: " + MODIFIED_VALUE;
    Assert.assertEquals(assertMessage, MODIFIED_VALUE, variablesTree.getTreeItem(VARIABLE_FINAL).cell(1));

  }

  /**
   * Check that if a variable with the same name is created in the same category in both the parent and inherited context, the inherited one is not lost upon
   * synchronization: <br>
   * 1 The variable created in the parent context is present in the inherited context with the right value, <br>
   * 2 The variable created in the inherited context is still present and has the right value, <br>
   * @throws Exception
   */

  public void testEditParentAndInheritedContextAndSynchronize() throws Exception {
    // Edit the variable value in the parent context
    SWTBotTree contextsTree = getContextsTree();
    String parentName = getContextLabelInDeleteConfirmationTree(__parentContextName, null);
    expandNodeWithCondition(contextsTree, parentName, DataUtil.__CATEGORY_ORCHESTRA);
    SWTBotTreeItem node = contextsTree.getTreeItem(parentName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_FOUR).select();
    node.contextMenu(com.thalesgroup.orchestra.framework.ui.viewer.Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.VARIABLE.getName())
        .click();
    node.select();
    SWTBotTree variablesTree = getVariablesTree();
    String name = variablesTree.select(0).cell(0, 0);
    SWTBotShell shell = activateEditVariableShell(variablesTree, name);
    SWTBotText textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textValue.setFocus();
    textValue.setText(PARENT_VARIABLE_VALUE);
    SWTBotButton button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    shell.close();

    // Edit the variable value in the inherited context
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    node = contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_FOUR).select();
    node.contextMenu(com.thalesgroup.orchestra.framework.ui.viewer.Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.VARIABLE.getName())
        .click();
    node.select();
    variablesTree = getVariablesTree();
    name = variablesTree.select(0).cell(0, 0);
    shell = activateEditVariableShell(variablesTree, name);
    textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textValue.setFocus();
    textValue.setText(VALUE_1);
    button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    shell.close();

    saveParentContextAndSynchronizeInheritedContext();

    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    node = contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_FOUR).select();
    variablesTree = getVariablesTree();

    // Test that the value of the parent context variable is present
    String assertMessage = "Expecting the first variable: " + VARIABLE_0 + " to have the value: " + PARENT_VARIABLE_VALUE;
    Assert.assertEquals(assertMessage, PARENT_VARIABLE_VALUE, variablesTree.cell(0, 1));
    // Test that the value of the inherited context variable is present
    assertMessage = "Expecting the second variable: " + VARIABLE_0 + " to have the value: " + VALUE_1;
    Assert.assertEquals(assertMessage, VALUE_1, variablesTree.cell(1, 1));

  }

  /**
   * Check that if a a parent context is modified, the modification are retrieved in the inherited context when the later is synchronized: <br>
   * 1 A variable name is changed in the parent context, the name in the inherited context is also changed, <br>
   * 2 A variable value is changed in the parent context, the value in the inherited context is also changed, <br>
   * 3 A variable is given a value in the parent context, the variable now has a value in the inherited context, <br>
   * 4 A value is added to a multiple variable in the parent context, the variable in the inherited context also has a new value, <br>
   * @throws Exception
   */

  public void testExpandElementsInParentContextAndSynchronize() throws Exception {
    SWTBotTree contextsTree = getContextsTree();
    String parentName = getContextLabelInDeleteConfirmationTree(__parentContextName, null);
    expandNodeWithCondition(contextsTree, parentName, DataUtil.__CATEGORY_ORCHESTRA);

    // Edit parent context: new variable, new value and new multiple value
    SWTBotTreeItem node = contextsTree.getTreeItem(parentName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_THREE).select();
    node.contextMenu(com.thalesgroup.orchestra.framework.ui.viewer.Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.VARIABLE.getName())
        .click();
    node.select();
    SWTBotTree variablesTree = getVariablesTree();
    String name = variablesTree.select(0).cell(0, 0);
    SWTBotShell shell = activateEditVariableShell(variablesTree, name);
    SWTBotText textValue = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name);
    textValue.setFocus();
    textValue.setText(NEW_PARENT_VARIABLE);
    textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textValue.setFocus();
    textValue.setText(PARENT_VARIABLE_VALUE);
    SWTBotButton button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    shell.close();

    shell = activateEditVariableShell(variablesTree, VARIABLE_NO_VALUE);
    textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textValue.setFocus();
    textValue.setText(VALUE_1);
    button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    shell.close();

    shell = activateEditVariableShell(variablesTree, MULTI_VARIABLE);
    shell.bot().button(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.MultipleValuesEditionHandler_Button_Add_Text).click();
    SWTBotTable table = shell.bot().tableInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    SWTBotTableItem item = table.getTableItem(2);
    setTableItemValue(table, item, MULTI_VALUE_3);
    button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();

    saveParentContextAndSynchronizeInheritedContext();

    contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_THREE).select();
    variablesTree = getVariablesTree();

    // Test that the new variable is still present in the inherited context
    String assertMessage = "Expecting to find variable: " + NEW_PARENT_VARIABLE;
    Assert.assertTrue(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, NEW_PARENT_VARIABLE));
    // Test that the value of the new variable is correct in the inherited context
    assertMessage = "Expecting the variable: " + NEW_PARENT_VARIABLE + " to have the value: " + PARENT_VARIABLE_VALUE;
    Assert.assertEquals(assertMessage, PARENT_VARIABLE_VALUE, variablesTree.getTreeItem(NEW_PARENT_VARIABLE).cell(1));

    // Test that the variable with previously no value know has a value in the inherited context
    assertMessage = "Expecting the variable: " + VARIABLE_NO_VALUE + " to have the value: " + VALUE_1;
    Assert.assertEquals(assertMessage, VALUE_1, variablesTree.getTreeItem(VARIABLE_NO_VALUE).cell(1));

    String value = variablesTree.getTreeItem(MULTI_VARIABLE).cell(2, 1);
    assertMessage = "Expecting the value of " + MULTI_VARIABLE + " to be: " + MULTI_VALUE_3;
    Assert.assertEquals(assertMessage, MULTI_VALUE_3, value);
    shell.close();
  }

  /**
   * Check that a created variable in an inherited context can have its structure changed : <br>
   * 1 Create a multiple variable with two values, <br>
   * 2 Set a created variable final, <br>
   * 3 Set a created variable mandatory, <br>
   * @throws Exception
   */

  public void testExpandInheritedStructure() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    // Test if a multiple variable can be added
    SWTBotTree variablesTree = getRootCategoryOneTree();
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.viewer.Messages.ContextsViewer_NewMenu_Text)
        .menu(ContextsPackage.Literals.VARIABLE.getName()).click();
    String assertMessage = "Expecting to find variable: " + VARIABLE_0;
    Assert.assertTrue(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, VARIABLE_0));

    SWTBotShell shell = activateEditVariableShell(variablesTree, VARIABLE_0);
    SWTBotText textValue = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name);
    textValue.setFocus();
    textValue.setText(NEW_MULTIPLE_VARIABLE);
    SWTBotRadio radio = RadioWithLabel(Messages.AbstractEditVariablePage_Button_Title_MultipleValues);
    radio.setFocus();
    radio.click();
    shell.bot().button(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.MultipleValuesEditionHandler_Button_Add_Text).click();
    SWTBotTable table = shell.bot().tableInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    final SWTBotTableItem item1 = table.getTableItem(0);
    setTableItemValue(table, item1, MULTI_VALUE_1);
    final SWTBotTableItem item2 = table.getTableItem(1);
    setTableItemValue(table, item2, MULTI_VALUE_2);
    SWTBotButton button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    shell.close();
    variablesTree = getRootCategoryOneTree();
    String value = variablesTree.getTreeItem(NEW_MULTIPLE_VARIABLE).cell(0, 1);
    assertMessage = "Expecting the value of " + NEW_MULTIPLE_VARIABLE + " to be: ";
    Assert.assertEquals(assertMessage + MULTI_VALUE_1, MULTI_VALUE_1, value);
    value = variablesTree.getTreeItem(NEW_MULTIPLE_VARIABLE).cell(1, 1);
    assertMessage = "Expecting the value of " + NEW_MULTIPLE_VARIABLE + " to be: ";
    Assert.assertEquals(assertMessage + MULTI_VALUE_2, MULTI_VALUE_2, value);
    // Set Variable_0 for next tests
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.viewer.Messages.ContextsViewer_NewMenu_Text)
        .menu(ContextsPackage.Literals.VARIABLE.getName()).click();
    shell = activateEditVariableShell(variablesTree, VARIABLE_0);
    SWTBotCheckBox checkFinal = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Final);
    checkFinal.select();
    SWTBotCheckBox checkMandatory = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Mandatory);
    checkMandatory.select();
    button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    shell.close();

    // Test that the variable is final
    shell = activateEditVariableShell(variablesTree, VARIABLE_0);
    boolean isFinal = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Final).isChecked();
    assertMessage = "Expecting the Final checkBox to be checked";
    Assert.assertEquals(assertMessage, true, isFinal);

    // Test that the variable is mandatory
    boolean mandatory = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Mandatory).isChecked();
    assertMessage = "Expecting the Mandatory checkBox to be checked";
    Assert.assertEquals(assertMessage, true, mandatory);
    shell.close();
  }

  /**
   * Check that a not final inherited variable can be expanded : <br>
   * 1 Missing value from an inherited variable can be set, <br>
   * 2 New value can be added to an inherited multiple variable, <br>
   * @throws Exception
   */

  public void testExpandInheritedVariable() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    SWTBotTree variablesTree = getRootCategoryOneTree();

    // Test if a missing value can be added to an inherited variable
    SWTBotShell shell = activateEditOverrideVariableShell(variablesTree, VARIABLE_NO_VALUE);
    SWTBotText textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textValue.setFocus();
    textValue.setText(VALUE_1);
    SWTBotButton button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    String value = variablesTree.getTreeItem(VARIABLE_NO_VALUE).cell(1);
    String assertMessage = "Expecting the value of " + VARIABLE_NO_VALUE + " to be: " + VALUE_1;
    Assert.assertEquals(assertMessage, VALUE_1, value);
    shell.close();

    // Test if a new value can be added to a multiple variable
    shell = activateEditOverrideVariableShell(variablesTree, MULTI_VARIABLE);
    shell.bot().button(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.MultipleValuesEditionHandler_Button_Add_Text).click();
    SWTBotTable table = shell.bot().tableInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    final SWTBotTableItem item = table.getTableItem(2);
    setTableItemValue(table, item, MULTI_VALUE_3);
    button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    value = variablesTree.getTreeItem(MULTI_VARIABLE).cell(2, 1);
    assertMessage = "Expecting the value of " + MULTI_VARIABLE + " to be: " + MULTI_VALUE_3;
    Assert.assertEquals(assertMessage, MULTI_VALUE_3, value);
    shell.close();
  }

  /**
   * Check that a copied inherited category in the inherited context can be edited: <br>
   * 1 The inherited category can not be cut, <br>
   * 2 The inherited category can be copied and then pasted, <br>
   * 3 The copied category name can be edited, <br>
   * 4 All the variables from the original category have been copied in the copy, <br>
   * @throws Exception
   */

  public void TestInheritedCopiedCategoryIsEditable() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    SWTBotTreeItem orchestraNode = contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA);

    // Test that an inherited category can not be cut
    boolean isCut = orchestraNode.getNode(ROOT_CATEGORY_SIX).contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_cut).isEnabled();
    String assertMessage = "Expecting the Cut menu item to be disabled";
    Assert.assertFalse(assertMessage, isCut);
    String copy = "Copy of " + ROOT_CATEGORY_SIX;
    // If Category six copy has already happened in a previous test, remove it
    List<String> nodes = orchestraNode.getNodes();
    if (nodes.contains(copy)) {
      orchestraNode.getNode(copy).contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
      // Check if the category was well removed
      String message = "Expecting the category: " + copy + " to have been removed, current test will fail because of context instability";
      nodes = orchestraNode.getNodes();
      Assert.assertFalse(message, nodes.contains(copy));
    }

    // Test that an inherited category can be copied
    orchestraNode.getNode(ROOT_CATEGORY_SIX).contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_copy).click();
    orchestraNode.select();
    orchestraNode.contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_paste).click();
    assertMessage = "Expecting to find the category named: " + copy;
    Assert.assertTrue(assertMessage, orchestraNode.getNodes().contains(copy));

    // Test that a created category can be edited
    SWTBotTreeItem node = orchestraNode.getNode(copy).select();
    node.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(Messages.EditCategoryWizard_Wizard_Title);
    shell.activate();
    SWTBotText categoryNameText = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name);
    categoryNameText.setText(COPIED_CATEGORY);
    getBot().waitUntil(getConditionFactory().createTreeItemTextCondition(node, COPIED_CATEGORY), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    getBot().button(IDialogConstants.FINISH_LABEL).click();
    assertMessage = "Expecting the category name to have been change to: " + COPIED_CATEGORY;
    Assert.assertTrue(assertMessage, orchestraNode.getNode(COPIED_CATEGORY).isVisible());
    shell.close();

    // Test that all variables have been copied
    orchestraNode.getNode(COPIED_CATEGORY).select();
    SWTBotTree variablesTree = getVariablesTree();
    boolean allPresent =
        true && SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, MULTI_VARIABLE)
            && SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, MULTI_VARIABLE_MANDATORY)
            && SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, MULTI_VARIABLE_FINAL)
            && SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, VARIABLE) && SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, VARIABLE_FINAL)
            && SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, VARIABLE_MANDATORY)
            && SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, VARIABLE_NO_VALUE);
    assertMessage = "Expecting to find seven variables in the category: " + COPIED_CATEGORY;
    Assert.assertTrue(assertMessage, allPresent);

  }

  /**
   * Check that a variable from a copied category is completely editable: <br>
   * 1 The name can be edited, <br>
   * 2 The value can be edited, <br>
   * 3 The description can be edited, <br>
   * 4 The multiple value can be chosen, <br>
   * 5 The final checkBox can be edited, <br>
   * 6 The mandatory checkBox can be edited, <br>
   * @throws Exception
   */

  public void testInheritedCopiedCategoryVariableIsEditable() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    // Create the copied category
    String copy = "Copy of " + ROOT_CATEGORY_SIX;
    SWTBotTreeItem orchestraNode = contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA);
    // If Category six copy has already happened in a previous test, remove it
    List<String> nodes = orchestraNode.getNodes();
    if (nodes.contains(copy)) {
      orchestraNode.getNode(copy).contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
      // Check if the category was well removed
      String message = "Expecting the category: " + copy + " to have been removed, current test will fail because of context instability";
      nodes = orchestraNode.getNodes();
      Assert.assertFalse(message, nodes.contains(copy));
    }
    orchestraNode.getNode(ROOT_CATEGORY_SIX).contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_copy).click();
    orchestraNode.select();
    orchestraNode.contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_paste).click();
    orchestraNode.getNode(copy).select();
    SWTBotTree variablesTree = getVariablesTree();
    SWTBotShell shell = activateEditVariableShell(variablesTree, VARIABLE);

    // Test that the name can be edited
    SWTBotText textValue = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name);
    String assertMessage = "Expecting the multiple checkBox to be editable";
    Assert.assertTrue(assertMessage, textValue.isEnabled());

    // Test that the value can be edited
    textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    assertMessage = "Expecting the multiple checkBox to be editable";
    Assert.assertTrue(assertMessage, textValue.isEnabled());

    // Test that the description can be edited
    textValue = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Description);
    assertMessage = "Expecting the multiple checkBox to be editable";
    Assert.assertTrue(assertMessage, textValue.isEnabled());

    // Test that the multiple values checkBox can be edited
    boolean isMultiple = RadioWithLabel(Messages.AbstractEditVariablePage_Button_Title_MultipleValues).isEnabled();
    assertMessage = "Expecting the multiple checkBox to be editable";
    Assert.assertTrue(assertMessage, isMultiple);

    // Test that the final checkBox can be edited
    boolean isFinal = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Final).isEnabled();
    assertMessage = "Expecting the Final checkBox to be editable";
    Assert.assertTrue(assertMessage, isFinal);

    // Test that the mandatory checkBox can be edited
    boolean mandatory = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Mandatory).isEnabled();
    assertMessage = "Expecting the Mandatory checkBox to be editable";
    Assert.assertTrue(assertMessage, mandatory);
    shell.close();

  }

  /**
   * Check that a copied inherited variable in the inherited context is completly editable: <br>
   * 1 The variable can switch to multiple value, <br>
   * 2 The final checkBox can be edited, <br>
   * 3 The mandatory checkBox can be edited, <br>
   * 4 The description field can be edited, <br>
   * 5 The name can be changed, <br>
   * 6 The value can be changed, <br>
   * @throws Exception
   */

  public void TestInheritedCopiedVariableIsEditable() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    // Create the copied inherited variable
    SWTBotTree variablesTree = getRootCategoryOneTree();
    variablesTree.select(VARIABLE_FINAL);
    variablesTree.contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_copy).click();
    variablesTree.contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_paste).click();
    // Set the name, value and description for latter testing
    variablesTree = getRootCategoryOneTree();
    String name = variablesTree.select(0).cell(0, 0);
    SWTBotShell shell = activateEditVariableShell(variablesTree, name);
    SWTBotText textValue = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name);
    textValue.setFocus();
    textValue.setText(COPIED_VARIABLE);
    textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textValue.setFocus();
    textValue.setText(VALUE_1);
    textValue = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Description);
    textValue.setFocus();
    textValue.setText(DESCRIPTION);

    // Test that the multiple values checkBox can be edited
    boolean isMultiple = RadioWithLabel(Messages.AbstractEditVariablePage_Button_Title_MultipleValues).isEnabled();
    String assertMessage = "Expecting the multiple checkBox to be editable";
    Assert.assertTrue(assertMessage, isMultiple);

    // Test that the final checkBox can be edited
    boolean isFinal = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Final).isEnabled();
    assertMessage = "Expecting the Final checkBox to be editable";
    Assert.assertTrue(assertMessage, isFinal);

    // Test that the mandatory checkBox can be edited
    boolean mandatory = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Mandatory).isEnabled();
    assertMessage = "Expecting the Mandatory checkBox to be editable";
    Assert.assertTrue(assertMessage, mandatory);

    // Test if the description has been changed
    String text = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Description).getText();
    assertMessage = "Expection the description to be: " + DESCRIPTION;
    Assert.assertEquals(assertMessage, DESCRIPTION, text);

    SWTBotButton button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    shell.close();
    variablesTree = getRootCategoryOneTree().select(COPIED_VARIABLE);

    // Test that the name has been changed
    assertMessage = "Expecting the variable name to be: " + COPIED_VARIABLE;
    Assert.assertEquals(assertMessage, COPIED_VARIABLE, variablesTree.cell(0, 0));

    // Test that the value has been changed
    assertMessage = "Expecting the variable value to be: " + VALUE_1;
    Assert.assertEquals(assertMessage, VALUE_1, variablesTree.cell(0, 1));

  }

  /**
   * Check that an inherited variable can be copied in the inherited context: <br>
   * 1 The inherited variable can not be cut, <br>
   * 2 The inherited variable can be copied, <br>
   * 3 The inherited variable can be pasted in the inherited context, <br>
   * @throws Exception
   */

  public void TestInheritedCopyVariableIsEditable() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    SWTBotTree variablesTree = getRootCategoryOneTree();

    // Test that an inherited variable can not be cut
    variablesTree.select(VARIABLE_FINAL);
    boolean isCut = variablesTree.contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_cut).isEnabled();
    String assertMessage = "Expecting the Cut menu item to be disabled";
    Assert.assertFalse(assertMessage, isCut);

    // Test that an inherited variable can be copied
    SWTBotMenu copy = variablesTree.contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_copy);
    assertMessage = "Expecting the Copy menu item to be enabled";
    Assert.assertTrue(assertMessage, copy.isEnabled());

    // Test that the variable has been copied
    copy.click();
    variablesTree.contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_paste).click();
    variablesTree = getRootCategoryOneTree();
    assertMessage = "Expecting to find variable: " + COPY_OF_VARIABLE_FINAL;
    Assert.assertTrue(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, COPY_OF_VARIABLE_FINAL));

  }

  /**
   * Check that an inherited file variable can not be changed in the inherited context: <br>
   * 1 The file variable is still present in the inherited context, <br>
   * 2 The file variable name can not be changed in the inherited context, <br>
   * 3 The file variable can not be deleted in the inherited context, <br>
   * @throws Exception
   */

  public void testInheritedFileVariable() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    SWTBotTree variablesTree = getRootCategoryOneTree();

    // Test if the inherited file variable is still present in the inherited context
    String assertMessage = "Expecting to find file variable: " + FILE_VARIABLE_1;
    Assert.assertTrue(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, FILE_VARIABLE_1));

    // Test if the inherited file variable name can not be changed
    SWTBotShell shell = activateEditOverrideVariableShell(variablesTree, FILE_VARIABLE_1);
    boolean isNotReadable = SWTUtils.hasStyle(getBot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name).widget, SWT.READ_ONLY);
    assertMessage = "Excepting the Name field to be read only.";
    Assert.assertTrue(assertMessage, isNotReadable);
    shell.close();

    // Test id the inherited file variable can not be deleted
    variablesTree.select(FILE_VARIABLE_1);
    SWTBotMenu menu = variablesTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item"));
    assertMessage = "Expecting the Delete menu item to be inactive";
    Assert.assertFalse(assertMessage, menu.isEnabled());

  }

  /**
   * Check that inherited final values can not be edited : <br>
   * 1 Inherited final value can not be edited, <br>
   * 2 Inherited value from a final multiple variable can not be edited, <br>
   * 3 No value can be added to an inherited final multiple variable, <br>
   * @throws Exception
   */

  public void testInheritedFinalCoherence() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    SWTBotTree variablesTree = getRootCategoryOneTree();

    // Test if an inherited final value can not be edited
    SWTBotShell shell = activateEditOverrideVariableShell(variablesTree, VARIABLE_FINAL);
    boolean isReadable = SWTUtils.hasStyle(shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value).widget, SWT.READ_ONLY);
    String assertMessage = "Expecting the Value field to be not editable";
    Assert.assertTrue(assertMessage, isReadable);
    shell.close();

    // Test if an inherited final multiple value can not be edited
    shell = activateEditOverrideVariableShell(variablesTree, MULTI_VARIABLE_FINAL);
    // Select a value and check its edition text field.
    SWTBotTable multiValuesTable = shell.bot().tableInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    multiValuesTable.getTableItem(MULTI_VALUE_1).select().click();
    SWTBotText valueTextField = new SWTBot(multiValuesTable.widget).text();
    Assert.assertTrue("Expecting the text field to be enabled but read-only !",
        valueTextField.isEnabled() && SWTUtils.hasStyle(valueTextField.widget, SWT.READ_ONLY));

    // Test if a multiple value can not be added
    boolean isEnabled =
        shell.bot().button(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.MultipleValuesEditionHandler_Button_Add_Text).isEnabled();
    assertMessage = "Expecting the Button Add to be disabled";
    Assert.assertFalse(assertMessage, isEnabled);
    shell.close();
  }

  /**
   * Check that an inherited folder variable can not be changed in the inherited context: <br>
   * 1 The folder variable is still present in the inherited context, <br>
   * 2 The folder variable name can not be changed in the inherited context, <br>
   * 3 The folder variable can not be deleted in the inherited context, <br>
   * @throws Exception
   */

  public void testInheritedFolderVariable() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    SWTBotTree variablesTree = getRootCategoryOneTree();
    // Test if the inherited folder variable is still present in the inherited context
    String assertMessage = "Expecting to find folder variable: " + FOLDER_VARIABLE_1;
    Assert.assertTrue(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, FOLDER_VARIABLE_1));

    // Test if the inherited folder variable name can not be changed
    SWTBotShell shell = activateEditOverrideVariableShell(variablesTree, FOLDER_VARIABLE_1);
    boolean isNotReadable = SWTUtils.hasStyle(getBot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name).widget, SWT.READ_ONLY);
    assertMessage = "Excepting the Name field to be read only.";
    Assert.assertTrue(assertMessage, isNotReadable);
    shell.close();

    // Test id the inherited file variable can not be deleted
    variablesTree.select(FOLDER_VARIABLE_1);
    SWTBotMenu menu = variablesTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item"));
    assertMessage = "Expecting the Delete menu item to be inactive";
    Assert.assertFalse(assertMessage, menu.isEnabled());

  }

  /**
   * Check that the inherited variable structure can not be changed : <br>
   * 1 Inherited variable name can not be changed, <br>
   * 2 Inherited variable description can not be changed, <br>
   * 3 Inherited category can not be changed, <br>
   * @throws Exception
   */

  public void testInheritedStructureDataIsNotEditable() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    SWTBotTree variablesTree = getRootCategoryOneTree();

    // Test if variable name is not editable
    SWTBotShell shell = activateEditOverrideVariableShell(variablesTree, VARIABLE);
    boolean isNotReadable = SWTUtils.hasStyle(getBot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Name).widget, SWT.READ_ONLY);
    String assertMessage = "Excepting the Name field to be read only.";
    Assert.assertTrue(assertMessage, isNotReadable);

    // Test if the description is not editable
    isNotReadable = SWTUtils.hasStyle(getBot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Description).widget, SWT.READ_ONLY);
    assertMessage = "Excepting the Description field to be read only.";
    Assert.assertTrue(assertMessage, isNotReadable);
    shell.close();

    // Test if an inherited category can not be edited
    final SWTBotTreeItem orchestraNode = contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA);
    SWTBotTreeItem node = orchestraNode.getNode(ROOT_CATEGORY_ONE);
    node.select();
    boolean isEnabled = node.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).isEnabled();
    assertMessage = "Expecting the category not to be editable";
    Assert.assertFalse(assertMessage, isEnabled);
    shell.close();
  }

  /**
   * Check that the inherited elements can not be deleted : <br>
   * 1 Inherited category can not be deleted, <br>
   * 2 Inherited variable can not be deleted, <br>
   * 3 Inherited value from a multiple variable can not be deleted, <br>
   * @throws Exception
   */

  public void testInheritedStructureIsFinal() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    SWTBotTree variablesTree = getRootCategoryOneTree();

    // Test if a category can not be deleted
    SWTBotMenu menu = contextsTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item"));
    String assertMessage = "Expecting the Delete menu item to be disabled";
    Assert.assertFalse(assertMessage, menu.isEnabled());

    // Test if a variable can not be deleted
    variablesTree.select(VARIABLE);
    menu = variablesTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item"));
    Assert.assertFalse(assertMessage, menu.isEnabled());

    // Test if a multiple from a multiple variable can not be deleted
    SWTBotShell shell = activateEditOverrideVariableShell(variablesTree, MULTI_VARIABLE);
    shell.bot().tableInGroup(Messages.AbstractEditVariablePage_Label_Title_Value).select(0);
    boolean isEnabled =
        shell.bot().button(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.MultipleValuesEditionHandler_Button_Remove_Text).isEnabled();
    assertMessage = "Expecting the Button Remove to be disabled";
    Assert.assertFalse(assertMessage, isEnabled);
    shell.close();
  }

  /**
   * Check that the categories and variables of a context are still present in the inherited context: <br>
   * 1 Category is present in the inherited context, <br>
   * 2 Variable is present in the inherited context, <br>
   * 3 Multiple value variable is present in the inherited context, <br>
   * @throws Exception
   */

  public void testInheritedVariableAndCategoryPresence() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    String contextDisplyName = getContextLabelInDeleteConfirmationTree(__inheritedContextName, __parentContextName);
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    // Test if category is present
    String assertMessage = "Expecting to find category: " + ROOT_CATEGORY_SIX;
    Assert.assertTrue(assertMessage,
        SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, contextDisplyName, DataUtil.__CATEGORY_ORCHESTRA, ROOT_CATEGORY_SIX));

    contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_SIX).select();
    SWTBotTree variablesTree = getVariablesTree();
    // Test if variable is present
    assertMessage = "Expecting to find variable: " + VARIABLE;
    Assert.assertTrue(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, VARIABLE));

    // Test if multiple value variable is present
    assertMessage = "Expecting to find variable: " + MULTI_VARIABLE;
    Assert.assertTrue(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, MULTI_VARIABLE));
  }

  /**
   * Check that the structure of a variable is still the same in the inherited context : <br>
   * 1 Variable is still final in the inherited context, <br>
   * 2 Variable is still not final in the inherited context, <br>
   * 3 Variable is still mandatory in the inherited context, <br>
   * 4 Variable is sill not mandatory in the inherited context, <br>
   * @throws Exception
   */

  public void testInheritedVariableCoherence() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_SIX).select();
    SWTBotTree variablesTree = getVariablesTree();

    // Test if variable is still final
    SWTBotShell shell = activateEditOverrideVariableShell(variablesTree, VARIABLE_FINAL);
    // Classic methods to access SwtBot object will not work, alternate access through widgets list
    SWTBotCheckBox checkBox = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Final);
    String assertMessage = "Expecting the Final checkBox to be checked";
    Assert.assertEquals(assertMessage, true, checkBox.isChecked());
    shell.close();

    // Test if variable is still not final
    shell = activateEditOverrideVariableShell(variablesTree, VARIABLE);
    checkBox = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Final);
    assertMessage = "Expecting the Final checkBox to be not checked";
    Assert.assertEquals(assertMessage, false, checkBox.isChecked());
    shell.close();

    // Test if variable is still mandatory
    shell = activateEditOverrideVariableShell(variablesTree, VARIABLE_MANDATORY);
    checkBox = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Mandatory);
    assertMessage = "Expecting the Mandatory checkBox to be checked";
    Assert.assertEquals(assertMessage, true, checkBox.isChecked());
    shell.close();

    // Test if variable is still not mandatory
    shell = activateEditOverrideVariableShell(variablesTree, VARIABLE);
    checkBox = checkBoxWithText(Messages.AbstractEditVariablePage_Button_Title_Mandatory);
    assertMessage = "Expecting the Mandatory checkBox to be not checked";
    Assert.assertEquals(assertMessage, false, checkBox.isChecked());
    shell.close();
  }

  /**
   * Check that values of a variable is the same in the inherited context: <br>
   * 1 Variable value is the same in the inherited context, <br>
   * 2 Variable description is the same in the inherited context, <br>
   * 3 Variable empty value is still empty in the inherited context, <br>
   * 4 Variable empty description is still empty in the inherited context, <br>
   * 5 Number of values for a multiple variable is the same in the inherited context, <br>
   * 6 Each value of a multiple variable is the same in the inherited context, <br>
   * @throws Exception
   */

  public void testInheritedVariableDataPresence() throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);

    contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_SIX).select();
    SWTBotTree variablesTree = getVariablesTree();
    SWTBotShell shell = activateEditOverrideVariableShell(variablesTree, VARIABLE);

    // Test if variable value is correct
    SWTBotText textInGroup = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textInGroup.setFocus();
    String value = textInGroup.getText();
    String assertMessage = "Expecting to find value: " + VALUE_1;
    Assert.assertEquals(assertMessage, VALUE_1, value);

    // Test if description is correct
    String desc = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Description).getText();
    assertMessage = "Expecting to find description: " + DESCRIPTION;
    Assert.assertEquals(assertMessage, DESCRIPTION, desc);
    shell.close();

    shell = activateEditOverrideVariableShell(variablesTree, VARIABLE_NO_DESCRIPTION);
    // Test if variable value is correct, i.e. there is none
    textInGroup = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textInGroup.setFocus();
    value = textInGroup.getText();
    assertMessage = "Expecting to find value: " + EMPTY_STRING;
    Assert.assertEquals(assertMessage, EMPTY_STRING, value);

    // Test if description is correct, i.e. there is none
    desc = shell.bot().textWithLabel(Messages.AbstractEditVariablePage_Label_Title_Description).getText();
    assertMessage = "Expecting to find description: " + EMPTY_STRING;
    Assert.assertEquals(assertMessage, EMPTY_STRING, desc);
    shell.close();

    shell = activateEditOverrideVariableShell(variablesTree, MULTI_VARIABLE);
    SWTBotTable table = shell.bot().tableInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);

    // Test if number of multiple value is correct
    int rowNumber = 2;
    assertMessage = "Expecting to find the following number of multiple value: " + rowNumber;
    Assert.assertEquals(assertMessage, rowNumber, table.rowCount());

    // Test if values of multiple value is correct
    String multiValue1 = table.getTableItem(MULTI_VALUE_1).getText();
    assertMessage = "Expecting to find value: " + MULTI_VALUE_1;
    Assert.assertEquals(assertMessage, MULTI_VALUE_1, multiValue1);

    String multiValue2 = table.getTableItem(MULTI_VALUE_2).getText();
    assertMessage = "Expecting to find value: " + MULTI_VALUE_2;
    Assert.assertEquals(assertMessage, MULTI_VALUE_2, multiValue2);
    shell.close();

  }

  /**
   * Check if when a variable is edited in an inherited context, its value is saved when this variable is deleted in the parent context and the inherited
   * context is resynchronized. <br>
   * 1 A deleted category in the parent context is deleted from the inherited context after synchronization. <br>
   * 2 A deleted variable in the parent context is deleted from the inherited context after synchronization. <br>
   * 3 The value of the deleted variable and the value of the variable in the deleted category are stored in the Pending Elements category
   * @throws Exception
   */

  public void testRemoveElementsInParentContextAndSynchronize() throws Exception {
    // Edit the variable value in the inherited context
    SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_FIVE).select();
    SWTBotTree variablesTree = getVariablesTree();
    SWTBotShell shell = activateEditOverrideVariableShell(variablesTree, VARIABLE_2);
    SWTBotText textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textValue.setFocus();
    textValue.setText(INHERITED_CATEGORY_VALUE);
    SWTBotButton button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    shell.close();
    contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_ONE).select();
    variablesTree = getVariablesTree();
    shell = activateEditOverrideVariableShell(variablesTree, VARIABLE_2);
    textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textValue.setFocus();
    textValue.setText(INHERITED_VARIABLE_VALUE);
    button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    shell.close();

    // Delete the category in the parent context
    String parentName = getContextLabelInDeleteConfirmationTree(__parentContextName, null);
    expandNodeWithCondition(contextsTree, parentName, DataUtil.__CATEGORY_ORCHESTRA);
    SWTBotTreeItem node = contextsTree.getTreeItem(parentName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_FIVE).select();
    node.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
    getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.DeleteCommandWithConfirmation_ConfirmationDialog_Title).activate();
    getBot().button(IDialogConstants.YES_LABEL).click();
    // Delete the variable in the parent context
    contextsTree.getTreeItem(parentName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(ROOT_CATEGORY_ONE).select();
    variablesTree = getVariablesTree();
    variablesTree.select(VARIABLE_2);
    variablesTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
    getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.DeleteCommandWithConfirmation_ConfirmationDialog_Title).activate();
    getBot().button(IDialogConstants.YES_LABEL).click();
    shell.close();

    saveParentContextAndSynchronizeInheritedContext();

    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    node = contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA);
    // Test that the category has been deleted in the inherited context
    String assertMessage = "Expecting the category: " + ROOT_CATEGORY_FIVE + " to have been deleted";
    Assert.assertFalse(assertMessage, node.getNodes().contains(ROOT_CATEGORY_FIVE));
    // Test that the variable has been deleted in the inherited context
    variablesTree = getRootCategoryOneTree();
    assertMessage = "Expecting variable: " + VARIABLE_2 + " to have been deleted";
    Assert.assertFalse(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, VARIABLE_2));
    // Test that the old values of the deleted variables are stored in the "pending" category;
    contextsTree.getTreeItem(_inheritedContextDisplayName).getNode(_pendingElements).select();
    variablesTree = getVariablesTree();
    // Test if both values are present
    String firstValue = variablesTree.cell(0, 1);
    String secondValue = variablesTree.cell(1, 1);
    boolean first =
        ((INHERITED_CATEGORY_VALUE.equals(firstValue)) && (!INHERITED_CATEGORY_VALUE.equals(secondValue)))
            || ((!INHERITED_CATEGORY_VALUE.equals(firstValue)) && (INHERITED_CATEGORY_VALUE.equals(secondValue)));
    assertMessage = "Expecting a variable: " + OVERRIDE_VARIABLE_VALUE + " to have the value: " + INHERITED_CATEGORY_VALUE;
    Assert.assertTrue(assertMessage, first);
    boolean second =
        ((INHERITED_VARIABLE_VALUE.equals(firstValue)) && (!INHERITED_VARIABLE_VALUE.equals(secondValue)))
            || ((!INHERITED_VARIABLE_VALUE.equals(firstValue)) && (INHERITED_VARIABLE_VALUE.equals(secondValue)));
    assertMessage = "Expecting the second variable: " + OVERRIDE_VARIABLE_VALUE + " to have the value: " + INHERITED_VARIABLE_VALUE;
    Assert.assertTrue(assertMessage, second);

  }

  /**
   * Test the create, update and delete of a variable
   * @param variableName_p
   * @param variableValue_p
   * @param variableType_p
   * @throws Exception
   */
  protected void testVariableCRUD(String variableName_p, String variableValue_p, String variableType_p) throws Exception {
    final SWTBotTree contextsTree = getContextsTree();
    expandNodeWithCondition(contextsTree, _inheritedContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA);
    SWTBotTree variablesTree = getRootCategoryOneTree();

    // Test if a variable can be created
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.viewer.Messages.ContextsViewer_NewMenu_Text).menu(variableType_p).click();
    String assertMessage = "Expecting to find " + variableType_p + ": " + variableName_p;
    Assert.assertTrue(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, variableName_p));

    // Test if a created variable can be edited
    SWTBotShell shell = activateEditVariableShell(variablesTree, variableName_p);
    SWTBotText textValue = shell.bot().textInGroup(Messages.AbstractEditVariablePage_Label_Title_Value);
    textValue.setFocus();
    textValue.setText(variableValue_p);
    SWTBotButton button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    String value = variablesTree.getTreeItem(variableName_p).cell(1);
    assertMessage = "Expecting the value of " + variableName_p + " to be: " + variableValue_p;
    Assert.assertEquals(assertMessage, variableValue_p, value);
    shell.close();

    // Test if a created variable can be deleted
    variablesTree = getRootCategoryOneTree();
    variablesTree.select(variableName_p);
    variablesTree.contextMenu(EMFEditUIPlugin.INSTANCE.getString("_UI_Delete_menu_item")).click();
    shell =
        getBot().shell(com.thalesgroup.orchestra.framework.model.handler.command.Messages.DeleteCommandWithConfirmation_ConfirmationDialog_Title).activate();
    getBot().button(IDialogConstants.YES_LABEL).click();
    shell.close();
    assertMessage = "Expecting variable: " + variableName_p + " to have been deleted";
    Assert.assertFalse(assertMessage, SWTBotTestHelper.areNodesDisplayedInTree(variablesTree, variableName_p));
  }

  /**
   * Create a parent context and then an inherited context
   * @throws Exception
   */
  public static void createTestsContexts() throws Exception {
    // Initialize the bot, which will purge the temporary test folders
    __migration = new AbstractMigration() {
      @SuppressWarnings("synthetic-access")
      @Override
      protected IStatus doMigrate() {
        // Create parent context
        __testsDirectory = getStaticNewDedicatedTemporaryFolder(SynchronizeContextTests.class);
        Map<String, Object> parentContextMap = createNewContext(__parentContextName, null, __testsDirectory, false, null, false);
        Context parentContext = (Context) parentContextMap.get(RESULT_KEY_CONTEXT);
        // Create categories
        Category rootCategoryOne = createCategory(DataUtil.__CATEGORY_ORCHESTRA, ROOT_CATEGORY_ONE, parentContext);
        createCategory(DataUtil.__CATEGORY_ORCHESTRA, ROOT_CATEGORY_TWO, parentContext);
        Category rootCategoryThree = createCategory(DataUtil.__CATEGORY_ORCHESTRA, ROOT_CATEGORY_THREE, parentContext);
        Category rootCategoryFour = createCategory(DataUtil.__CATEGORY_ORCHESTRA, ROOT_CATEGORY_FOUR, parentContext);
        Category rootCategoryFive = createCategory(DataUtil.__CATEGORY_ORCHESTRA, ROOT_CATEGORY_FIVE, parentContext);
        Category rootCategorySix = createCategory(DataUtil.__CATEGORY_ORCHESTRA, ROOT_CATEGORY_SIX, parentContext);
        // Create Variables
        List<String> valuesList = new ArrayList<String>();
        valuesList.add(VALUE_1);
        Variable simpleVariable1 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryOne), VARIABLE, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(simpleVariable1, DESCRIPTION, false, false);
        Variable simpleVariable2 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryOne), VARIABLE_MANDATORY, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(simpleVariable2, DESCRIPTION, false, true);
        Variable simpleVariable3 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryOne), VARIABLE_FINAL, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(simpleVariable3, DESCRIPTION, true, false);
        valuesList.clear();
        valuesList.add(EMPTY_STRING);
        Variable simpleVariable4 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryOne), VARIABLE_NO_DESCRIPTION, valuesList, VariableType.VARIABLE, false,
                parentContext);
        setVariable(simpleVariable4, EMPTY_STRING, false, false);
        Variable simpleVariable5 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryOne), VARIABLE_NO_VALUE, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(simpleVariable5, DESCRIPTION, false, false);
        valuesList.clear();
        valuesList.add(MULTI_VALUE_1);
        valuesList.add(MULTI_VALUE_2);
        Variable multiVariable1 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryOne), MULTI_VARIABLE, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(multiVariable1, DESCRIPTION, false, false);
        Variable multiVariable2 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryOne), MULTI_VARIABLE_FINAL, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(multiVariable2, DESCRIPTION, true, false);
        Variable multiVariable3 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryOne), MULTI_VARIABLE_MANDATORY, valuesList, VariableType.VARIABLE, false,
                parentContext);
        setVariable(multiVariable3, DESCRIPTION, false, true);
        Variable multiVariable4 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryThree), MULTI_VARIABLE, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(multiVariable4, DESCRIPTION, false, false);
        valuesList.clear();
        valuesList.add(EMPTY_STRING);
        FolderVariable folderVariable1 =
            (FolderVariable) createVariable(ModelUtil.getElementPath(rootCategoryOne), FOLDER_VARIABLE_1, valuesList, VariableType.FOLDER_VARIABLE, false,
                parentContext);
        setVariable(folderVariable1, DESCRIPTION, false, false);
        FileVariable fileVariable1 =
            (FileVariable) createVariable(ModelUtil.getElementPath(rootCategoryOne), FILE_VARIABLE_1, valuesList, VariableType.FILE_VARIABLE, false,
                parentContext);
        setVariable(fileVariable1, DESCRIPTION, false, false);
        valuesList.clear();
        valuesList.add(VALUE_1);
        Variable simpleVariable7 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryFour), VARIABLE, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(simpleVariable7, DESCRIPTION, false, false);
        Variable simpleVariable8 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryFour), VARIABLE_FINAL, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(simpleVariable8, DESCRIPTION, false, false);
        Variable simpleVariable9 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryFive), VARIABLE_2, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(simpleVariable9, DESCRIPTION, false, false);
        Variable simpleVariable10 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryThree), VARIABLE_NO_VALUE, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(simpleVariable10, DESCRIPTION, false, false);
        Variable simpleVariable11 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryOne), VARIABLE_2, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(simpleVariable11, DESCRIPTION, false, false);
        valuesList.clear();
        valuesList.add(MULTI_VALUE_1);
        valuesList.add(MULTI_VALUE_2);
        Variable multiVariable5 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryOne), MULTI_VARIABLE_FINAL, valuesList, VariableType.VARIABLE, false, parentContext);
        Variable multiVariable6 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryOne), MULTI_VARIABLE_FINAL + "_2", valuesList, VariableType.VARIABLE, false,
                parentContext);
        Variable multiVariable7 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategoryOne), MULTI_VARIABLE_FINAL + "_3", valuesList, VariableType.VARIABLE, false,
                parentContext);
        setVariable(multiVariable5, DESCRIPTION, true, false);
        setVariable(multiVariable6, DESCRIPTION, true, false);
        setVariable(multiVariable7, DESCRIPTION, true, false);
        // Fill rootCategorySix for copy test
        Variable multiVariable8 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategorySix), MULTI_VARIABLE, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(multiVariable8, DESCRIPTION, false, false);
        Variable multiVariable9 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategorySix), MULTI_VARIABLE_MANDATORY, valuesList, VariableType.VARIABLE, false,
                parentContext);
        setVariable(multiVariable9, DESCRIPTION, false, true);
        Variable multiVariable10 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategorySix), MULTI_VARIABLE_FINAL, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(multiVariable10, DESCRIPTION, true, false);
        valuesList.clear();
        valuesList.add(VALUE_1);
        Variable variableCopy1 =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategorySix), VARIABLE, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(variableCopy1, DESCRIPTION, false, false);
        Variable variableCopyFinal =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategorySix), VARIABLE_FINAL, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(variableCopyFinal, DESCRIPTION, true, false);
        Variable variableCopyMandatory =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategorySix), VARIABLE_MANDATORY, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(variableCopyMandatory, DESCRIPTION, false, true);
        valuesList.clear();
        valuesList.add(EMPTY_STRING);
        Variable variableCopyEmptyFields =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategorySix), VARIABLE_NO_DESCRIPTION, valuesList, VariableType.VARIABLE, false,
                parentContext);
        setVariable(variableCopyEmptyFields, EMPTY_STRING, false, false);
        Variable variableNoValue =
            (Variable) createVariable(ModelUtil.getElementPath(rootCategorySix), VARIABLE_NO_VALUE, valuesList, VariableType.VARIABLE, false, parentContext);
        setVariable(variableNoValue, DESCRIPTION, false, false);

        RootContextsProject parentProject = (RootContextsProject) parentContextMap.get(RESULT_KEY_PROJECT);
        try {
          parentContext.eResource().save(null);
        } catch (IOException exception_p) {
          return Status.CANCEL_STATUS;
        }

        // Create child context
        createNewContext(__inheritedContextName, parentProject, __testsDirectory, false, null, false);
        return Status.OK_STATUS;
      }
    };
    __migration.migrate();
    loadTestsContexts();
  }

  /**
   * Load the two contexts required for the tests
   */
  protected static void loadTestsContexts() {
    // Wait until projects are created.
    getBot().waitUntil(new ContextIsCreatedCondition(__testsDirectory, 2), AbstractTest.CONDITION_TIMEOUT, AbstractTest.CONDITION_INTERVAL);
    // Import them.
    importContexts(__testsDirectory);
  }

  /**
   * Set the data of a variable which are not set by the createVariable method
   * @param variable_p The variable to set
   * @param description_p The description to set
   * @param isFinal_p True if the variable is final, else false
   * @param isMandatory_p True if the variable is mandatory, else false
   */
  protected static void setVariable(Variable variable_p, String description_p, boolean isFinal_p, boolean isMandatory_p) {
    variable_p.setDescription(description_p);
    variable_p.setFinal(isFinal_p);
    variable_p.setMandatory(isMandatory_p);
  }
}
