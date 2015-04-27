/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.category;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
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
public class CreateCategoryTests extends AbstractCategoryTest {
  /**
   * Tests: <br>
   * 1- Check new category menu item isn't enabled under "Orchestra installation" in Admin mode, <br>
   * 2- Check new category menu item isn't enabled under "Orchestra installation" in User mode.
   * @throws Exception
   */
  @Test
  public void canNotCreateCategoryUnderOrchestraInstallation() throws Exception {
    initializeTest();
    // Admin mode.
    {
      changeModeTo(true);
      final SWTBotTree contextsTree = getContextsTree();
      final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
      // Expand context node.
      expandNodeWithCondition(contextsTree, adminContextDisplayedName);
      // Create new category using contextual menu.
      contextsTree.getTreeItem(adminContextDisplayedName).getNode(ModelUtil.INSTALLATION_CATEGORY_NAME).select();
      // Check new category action is not available.
      Assert.assertFalse("Expecting to have new category action disabled.", contextsTree.contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(
          ContextsPackage.Literals.CATEGORY.getName()).isEnabled());
    }
    // User mode.
    {
      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Expand context node.
      expandNodeWithCondition(contextsTree, userContextDisplayedName);
      // Create new category using contextual menu.
      contextsTree.getTreeItem(userContextDisplayedName).getNode(ModelUtil.INSTALLATION_CATEGORY_NAME).select();
      // Check new category action is not available.
      Assert.assertFalse("Expecting to have new category action disabled.", contextsTree.contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(
          ContextsPackage.Literals.CATEGORY.getName()).isEnabled());
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  /**
   * Tests: <br>
   * 1- Create a category under the admin context, <br>
   * 2- Create a category under the user context.
   * @throws Exception
   */
  @Test
  public void createCategoryUnderContext() throws Exception {
    initializeTest();
    // Admin mode.
    {
      changeModeTo(true);
      final SWTBotTree contextsTree = getContextsTree();
      final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
      // Expand context node.
      expandNodeWithCondition(contextsTree, adminContextDisplayedName);
      int nbCategoriesBeforeCreation = contextsTree.getTreeItem(adminContextDisplayedName).getItems().length;
      // Create new category using contextual menu.
      contextsTree.select(adminContextDisplayedName);
      contextsTree.contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.CATEGORY.getName()).click();
      String name = "Category0";
      getBot().waitUntil(getConditionFactory().createCategoryRenamedCondition(findContextForName(_adminContextName, false).getValue(), name),
          AbstractTest.CONDITION_TIMEOUT, AbstractTest.CONDITION_INTERVAL);
      int nbCategoriesAfterCreation = contextsTree.getTreeItem(adminContextDisplayedName).getItems().length;
      Assert.assertEquals("Expecting to have one more category under test context.", nbCategoriesBeforeCreation + 1, nbCategoriesAfterCreation);
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    // User mode.
    {
      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Expand context node.
      expandNodeWithCondition(contextsTree, userContextDisplayedName);
      int nbCategoriesBeforeCreation = contextsTree.getTreeItem(userContextDisplayedName).getItems().length;
      // Create new category using contextual menu.
      contextsTree.select(userContextDisplayedName);
      contextsTree.contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.CATEGORY.getName()).click();
      String name = "Category0";
      getBot().waitUntil(getConditionFactory().createCategoryRenamedCondition(findContextForName(_userContextName, false).getValue(), name),
          AbstractTest.CONDITION_TIMEOUT, AbstractTest.CONDITION_INTERVAL);
      int nbCategoriesAfterCreation = contextsTree.getTreeItem(userContextDisplayedName).getItems().length;
      Assert.assertEquals("Expecting to have one more category under test context.", nbCategoriesBeforeCreation + 1, nbCategoriesAfterCreation);
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  @Test
  public void createCategoryUnderContextOwnedCategory() throws Exception {
    initializeTest();
    // Admin mode.
    {
      changeModeTo(true);
      final SWTBotTree contextsTree = getContextsTree();
      final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
      // Expand context node.
      expandNodeWithCondition(contextsTree, adminContextDisplayedName);
      SWTBotTreeItem category1Node = contextsTree.getTreeItem(adminContextDisplayedName).getNode(_adminContextCategory1);
      category1Node.select();
      // Create new category using contextual menu
      contextsTree.contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.CATEGORY.getName()).click();
      String name = "Category0";
      String path = _adminContextCategory1 + ICommonConstants.PATH_SEPARATOR + name;
      getBot().waitUntil(getConditionFactory().createCategoryRenamedCondition(findContextForName(_adminContextName, false).getValue(), path),
          AbstractTest.CONDITION_TIMEOUT, AbstractTest.CONDITION_INTERVAL);
      // Expand to render the new category accessible
      expandNodeWithCondition(contextsTree, adminContextDisplayedName, _adminContextCategory1);

      Assert.assertTrue("Expecting to find a category name: " + name + " in category: " + _adminContextCategory1, category1Node.getNode(name).isVisible());
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    // User mode.
    {
      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Expand context node.
      expandNodeWithCondition(contextsTree, userContextDisplayedName);
      SWTBotTreeItem category1Node = contextsTree.getTreeItem(userContextDisplayedName).getNode(_adminContextCategory1);

      // Create new category using contextual menu.
      category1Node.select();
      contextsTree.contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.CATEGORY.getName()).click();
      String name = "Category0";
      String path = _adminContextCategory1 + ICommonConstants.PATH_SEPARATOR + name;
      getBot().waitUntil(getConditionFactory().createCategoryRenamedCondition(findContextForName(_userContextName, false).getValue(), path),
          AbstractTest.CONDITION_TIMEOUT, AbstractTest.CONDITION_INTERVAL);
      // Expand to render the new category accessible
      expandNodeWithCondition(contextsTree, userContextDisplayedName, _adminContextCategory1);
      Assert.assertTrue("Expecting to find a category name: " + name + " in category: " + _adminContextCategory1, category1Node.getNode(name).isVisible());
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  /**
   * Tests: <br>
   * 1- Create a category under Orchestra in the admin context, <br>
   * 2- Create a category under Orchestra in the user context.
   * @throws Exception
   */
  @Test
  public void createCategoryUnderOrchestra() throws Exception {
    initializeTest();
    // Admin mode.
    {
      changeModeTo(true);
      final SWTBotTree contextsTree = getContextsTree();
      final String adminContextDisplayedName = getAdministratorContextLabelInTree(_adminContextName);
      // Expand context node.
      expandNodeWithCondition(contextsTree, adminContextDisplayedName, DataUtil.__CATEGORY_ORCHESTRA);
      int nbCategoriesBeforeCreation = contextsTree.getTreeItem(adminContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getItems().length;
      // Create new category using contextual menu.
      contextsTree.getTreeItem(adminContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
      contextsTree.contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.CATEGORY.getName()).click();
      String name = "Category0";
      String path = DataUtil.__CATEGORY_ORCHESTRA + ICommonConstants.PATH_SEPARATOR + name;
      getBot().waitUntil(getConditionFactory().createCategoryRenamedCondition(findContextForName(_adminContextName, false).getValue(), path),
          AbstractTest.CONDITION_TIMEOUT, AbstractTest.CONDITION_INTERVAL);
      int nbCategoriesAfterCreation = contextsTree.getTreeItem(adminContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getItems().length;
      Assert.assertEquals("Expecting to have one more category under Orchestra.", nbCategoriesBeforeCreation + 1, nbCategoriesAfterCreation);
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
    // User mode.
    {
      changeModeTo(false);
      final SWTBotTree contextsTree = getContextsTree();
      final String userContextDisplayedName = getParticipationLabelInTree(_userContextName, _adminContextName);
      // Expand context node.
      expandNodeWithCondition(contextsTree, userContextDisplayedName, DataUtil.__CATEGORY_ORCHESTRA);
      int nbCategoriesBeforeCreation = contextsTree.getTreeItem(userContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getItems().length;
      // Create new category using contextual menu.
      contextsTree.getTreeItem(userContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
      contextsTree.contextMenu(Messages.ContextsViewer_NewMenu_Text).menu(ContextsPackage.Literals.CATEGORY.getName()).click();
      String name = "Category0";
      String path = DataUtil.__CATEGORY_ORCHESTRA + ICommonConstants.PATH_SEPARATOR + name;
      getBot().waitUntil(getConditionFactory().createCategoryRenamedCondition(findContextForName(_userContextName, false).getValue(), path),
          AbstractTest.CONDITION_TIMEOUT, AbstractTest.CONDITION_INTERVAL);
      int nbCategoriesAfterCreation = contextsTree.getTreeItem(userContextDisplayedName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getItems().length;
      Assert.assertEquals("Expecting to have one more category under Orchestra.", nbCategoriesBeforeCreation + 1, nbCategoriesAfterCreation);
    }
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }
}
