/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;
import com.thalesgroup.orchestra.framework.ui.viewer.Messages;

import junit.framework.Assert;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class DirtyStateTests extends AbstractTest {
  /**
   * Test main steps : <br>
   * rename a category : ODM should be dirty.<br>
   * Undo the modification : ODM should be not dirty.<br>
   * Redo the modification : ODM should be dirty.
   * @throws Exception
   */
  @Test
  public void checkViewDirtyAndUndirtyStatesOnUndoAndRedo() throws Exception {
    final SWTBotView variablesViewPart = getBot().viewById(VIEW_ID_VARIABLES);
    final String adminContextName = "AdminContextDirtyStatesTestsOnUndoAndRedo";
    final String adminContextDisplayedName = getAdministratorContextLabelInTree(adminContextName);
    final SWTBotTree contextsTree = getContextsTree();
    initializeTest();
    // Admin mode.
    changeModeTo(true);
    // Create a context.
    createContext(adminContextName, false);
    /*
     * Create a new category directly under the created context.
     */
    contextsTree.getTreeItem(adminContextDisplayedName).select();
    getContextsTree().contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.CATEGORY.getName()).click();
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    /*
     * Edit the newly created category to rename it.
     */
    // Expand context node.
    expandNodeWithCondition(contextsTree, adminContextDisplayedName);
    // Select the created category.
    final SWTBotTreeItem editNode = contextsTree.getTreeItem(adminContextDisplayedName).getNode("Category0");
    editNode.select();
    // Open edit wizard using the contextual menu.
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.EditCategoryWizard_Wizard_Title);
    shell.activate();
    // Change the category name in the text field -> ODM should not be dirty.
    SWTBotText categoryNameText =
        shell.bot().textWithLabel(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.AbstractEditVariablePage_Label_Title_Name);
    categoryNameText.setText("Category0NewName");
    Assert.assertFalse("Variables view should not be dirty.", variablesViewPart.getReference().isDirty());
    sleep(1);
    // Validate changes -> ODM should be dirty.
    shell.bot().button(IDialogConstants.FINISH_LABEL).click();
    Assert.assertTrue("Variables view should not be dirty.", variablesViewPart.getReference().isDirty());
    /*
     * Undo name change -> ODM should not be dirty.
     */
    // To find the "Undo" menu item, use a regex since the name of this menu item is appended with the name of the command to undo.
    Matcher<MenuItem> undoMenuItemMatcher = WidgetMatcherFactory.withRegex("Undo");
    getBot().menu(getBot().activeShell(), undoMenuItemMatcher, 0).click();
    Assert.assertFalse("Variables view should not be dirty.", variablesViewPart.getReference().isDirty());
    /*
     * Redo name change -> ODM should be dirty.
     */
    // To find the "Redo" menu item, use a regex since the name of this menu item is appended with the name of the command to redo.
    Matcher<MenuItem> redoMenuItemMatcher = WidgetMatcherFactory.withRegex("Redo");
    getBot().menu(getBot().activeShell(), redoMenuItemMatcher, 0).click();
    Assert.assertTrue("Variables view should be dirty.", variablesViewPart.getReference().isDirty());
    // Save to return to the undirty state.
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    /*
     * Clean up.
     */
    deleteAdminContext(adminContextName);
  }

  /**
   * Check view is not dirty after context creation, dirty after modifications and not dirty after a save operation.
   * @throws Exception
   */
  @Test
  public void checkViewDirtyAndUndirtyStatesOnContextCreationAndModification() throws Exception {
    final SWTBotView variablesViewPart = getBot().viewById(VIEW_ID_VARIABLES);
    final String adminContextName = "AdminContextForDirtyStatesTestsOnContextCreationModification";
    final String adminContextDisplayedName = getAdministratorContextLabelInTree(adminContextName);
    final SWTBotTree contextsTree = getContextsTree();
    initializeTest();
    // Admin mode.
    changeModeTo(true);
    // Variables view should not be dirty.
    Assert.assertFalse("Nothing has been done, Variables view should not be dirty.", variablesViewPart.getReference().isDirty());
    /*
     * Create a new context.
     */
    // Use "New Context..." in "File" menu.
    getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
        .menu(com.thalesgroup.orchestra.framework.ui.view.Messages.NewContextAction_Label_Admin).click();
    // Fill the new context wizard.
    SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.ui.wizard.Messages.NewContextWizard_1);
    shell.activate();
    getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.wizard.Messages.MainNewContextWizardPage_0).setText(adminContextName);
    sleep(1);
    getBot().button(IDialogConstants.FINISH_LABEL).click();
    // Variables view should not be dirty after context creation.
    Assert.assertFalse("Variables view should not be dirty after context creation.", variablesViewPart.getReference().isDirty());
    /*
     * Check dirty/undirty state on category creation.
     */
    // Create a new category directly under the created context.
    contextsTree.getTreeItem(adminContextDisplayedName).select();
    getContextsTree().contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.CATEGORY.getName()).click();
    // Variables view should be dirty after category creation.
    Assert.assertTrue("Variables view should be dirty after category creation.", variablesViewPart.getReference().isDirty());
    // Save.
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    // Variables view should not be dirty after a save operation.
    Assert.assertFalse("Variables view should not be dirty after a save operation.", variablesViewPart.getReference().isDirty());
    /*
     * Check dirty/undirty state on variable creation.
     */
    // Create a variable to set the view dirty.
    expandNodeWithCondition(contextsTree, adminContextDisplayedName);
    contextsTree.getTreeItem(adminContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
    getContextsTree().contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.VARIABLE.getName()).click();
    // Variables view should be dirty after variable creation.
    Assert.assertTrue("Variables view should be dirty after category creation.", variablesViewPart.getReference().isDirty());
    // Save.
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    // Variables view should not be dirty after a save operation.
    Assert.assertFalse("Variables view should not be dirty after a save operation.", variablesViewPart.getReference().isDirty());
    /*
     * Clean up.
     */
    deleteAdminContext(adminContextName);
  }

  /**
   * This test begins to rename a category but cancel the change -> ODM should not be dirty.
   * @throws Exception
   */
  @Test
  public void checkViewIsNotDirtiedOnCancelledModifications() throws Exception {
    final SWTBotView variablesViewPart = getBot().viewById(VIEW_ID_VARIABLES);
    final String adminContextName = "AdminContextDirtyStatesTestsOnCancelledModifications";
    final String adminContextDisplayedName = getAdministratorContextLabelInTree(adminContextName);
    final SWTBotTree contextsTree = getContextsTree();
    initializeTest();
    // Admin mode.
    changeModeTo(true);
    // Create a context.
    createContext(adminContextName, false);
    /*
     * Create a new category directly under the created context.
     */
    contextsTree.getTreeItem(adminContextDisplayedName).select();
    getContextsTree().contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.CATEGORY.getName()).click();
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    /*
     * Edit the created category to rename it but cancel this modification.
     */
    // Expand context node.
    expandNodeWithCondition(contextsTree, adminContextDisplayedName);
    // Select the created category.
    final SWTBotTreeItem editNode = contextsTree.getTreeItem(adminContextDisplayedName).getNode("Category0");
    editNode.select();
    // Open edit wizard using the contextual menu.
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.EditElementAction_Action_Text).click();
    SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.EditCategoryWizard_Wizard_Title);
    shell.activate();
    // ODM view should not be dirty.
    Assert.assertFalse("Variables view should not be dirty.", variablesViewPart.getReference().isDirty());
    // Change the category name.
    SWTBotText categoryNameText =
        shell.bot().textWithLabel(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.AbstractEditVariablePage_Label_Title_Name);
    categoryNameText.setText("Category0NewName");
    // ODM view should not be dirty.
    Assert.assertFalse("Variables view should not be dirty.", variablesViewPart.getReference().isDirty());
    // Cancel changes.
    getBot().button(IDialogConstants.CANCEL_LABEL).click();
    // ODM view should not be dirty after a cancellation.
    Assert.assertFalse("Variables view should not be dirty.", variablesViewPart.getReference().isDirty());
    /*
     * Clean up.
     */
    deleteAdminContext(adminContextName);
  }
}
