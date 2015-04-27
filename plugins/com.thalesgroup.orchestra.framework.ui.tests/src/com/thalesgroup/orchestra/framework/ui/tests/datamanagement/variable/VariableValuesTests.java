/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.variable;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;

import junit.framework.Assert;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class VariableValuesTests extends AbstractVariableTest {
  @Test
  public void canChangeValueOfContextOwnedMonoVariable() throws Exception {
    final String newAdminCategoryVariableValue = "NewAdminVariableValue";
    initializeTest();
    changeModeTo(true);
    final SWTBotTree contextsTree = getContextsTree();
    final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
    // Select the admin category.
    expandNodeWithCondition(contextsTree, adminContextDisplayedName);
    contextsTree.getTreeItem(adminContextDisplayedName).getNode(_adminCategory).select();
    final SWTBotTree variablesTree = getVariablesTree();
    // Open edit wizard using the contextual menu.
    variablesTree.getTreeItem(_adminCategoryVariable).doubleClick();
    SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.EditVariableWizard_Wizard_Title);
    shell.activate();
    // Give a new value to the variable.
    SWTBotText textInGroup =
        shell.bot().textInGroup(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.AbstractEditVariablePage_Label_Title_Value);
    textInGroup.setFocus();
    textInGroup.setText(newAdminCategoryVariableValue);
    SWTBotButton button = shell.bot().button(IDialogConstants.FINISH_LABEL);
    button.setFocus();
    button.click();
    // Check the new value is displayed in the tree.
    Assert.assertEquals("Expecting to have the new variable value displayed in the variables tree.", newAdminCategoryVariableValue,
        variablesTree.getTreeItem(_adminCategoryVariable).cell(1));
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }
}