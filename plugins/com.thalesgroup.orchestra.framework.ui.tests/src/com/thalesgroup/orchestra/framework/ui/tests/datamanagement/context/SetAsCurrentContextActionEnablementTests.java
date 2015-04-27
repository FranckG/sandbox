/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context;

import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;

import junit.framework.Assert;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class SetAsCurrentContextActionEnablementTests extends AbstractContextTest {
  /**
   * Check set as current context action enablement after context creation, modification, undo, redo and save.
   * @throws Exception
   */
  @Test
  public void checkSetAsCurrentContextActionEnablementOnContextCreationModificationAndSave() throws Exception {
    final String adminContextName1 = "AdminContextSetAsCurrentContextEnablementTests1";
    final String adminContextDisplayedName1 = getAdministratorContextLabelInTree(adminContextName1);
    final String adminContextName2 = "AdminContextSetAsCurrentContextEnablementTests2";
    final SWTBotTree contextsTree = getContextsTree();
    initializeTest();
    // Admin mode.
    changeModeTo(true);
    /*
     * Create the first context / check it can be set as current.
     */
    createAdminContextUsingNewContextWizard(adminContextName1);
    Assert.assertTrue("Set as current context should be enabled after context creation.", isSetAsCurrentContextActionEnabled(adminContextName1));
    /*
     * Create the first context / check both contexts can be set as current.
     */
    createAdminContextUsingNewContextWizard(adminContextName2);
    Assert.assertTrue("Set as current context should be enabled after context creation.", isSetAsCurrentContextActionEnabled(adminContextName1));
    Assert.assertTrue("Set as current context should be enabled after context creation.", isSetAsCurrentContextActionEnabled(adminContextName2));
    /*
     * Modify a context / check only the modified context can't be set as current.
     */
    contextsTree.getTreeItem(adminContextDisplayedName1).select();
    getContextsTree().contextMenu(com.thalesgroup.orchestra.framework.ui.viewer.Messages.ContextsViewer_NewMenu_Text)
        .menu(ContextsPackage.Literals.CATEGORY.getName()).click();
    Assert.assertFalse("Set as current context should not be enabled on the changed context.", isSetAsCurrentContextActionEnabled(adminContextName1));
    Assert.assertTrue("Set as current context should be enabled on the not changed context.", isSetAsCurrentContextActionEnabled(adminContextName2));
    /*
     * Undo the modification / every context can be set as current.
     */
    // To find the "Undo" menu item, use a regex since the name of this menu item is appended with the name of the command to undo.
    Matcher<MenuItem> undoMenuItemMatcher = WidgetMatcherFactory.withRegex("Undo");
    getBot().menu(getBot().activeShell(), undoMenuItemMatcher, 0).click();
    Assert.assertTrue("Set as current context should be enabled.", isSetAsCurrentContextActionEnabled(adminContextName1));
    Assert.assertTrue("Set as current context should be enabled.", isSetAsCurrentContextActionEnabled(adminContextName2));
    /*
     * Redo the modification / check only the modified context can't be set as current.
     */
    // To find the "Redo" menu item, use a regex since the name of this menu item is appended with the name of the command to redo.
    Matcher<MenuItem> redoMenuItemMatcher = WidgetMatcherFactory.withRegex("Redo");
    getBot().menu(getBot().activeShell(), redoMenuItemMatcher, 0).click();
    Assert.assertFalse("Set as current context should not be enabled.", isSetAsCurrentContextActionEnabled(adminContextName1));
    Assert.assertTrue("Set as current context should be enabled.", isSetAsCurrentContextActionEnabled(adminContextName2));
    /*
     * Save / every context can be set as current.
     */
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    Assert.assertTrue("Set as current context should be enabled after a save.", isSetAsCurrentContextActionEnabled(adminContextName1));
    Assert.assertTrue("Set as current context should be enabled after a save.", isSetAsCurrentContextActionEnabled(adminContextName2));
    /*
     * Clean up.
     */
    deleteAdminContext(adminContextName1);
    deleteAdminContext(adminContextName2);
  }
}
