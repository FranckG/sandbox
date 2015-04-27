/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.category;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.ui.tests.SWTBotTestHelper;

import junit.framework.Assert;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class CutCopyPasteCategoryTests extends AbstractCategoryTest {
  @Test
  public void copyIntraContextFromContextToInheritedCategory() throws Exception {
    copyOrCutIntraContextFromContextToInheritedCategory(OperationType.COPY_PASTE);
  }

  @Test
  public void copyIntraContextFromInheritedCategoryToContext() throws Exception {
    copyOrCutIntraContextFromInheritedCategoryToContext(OperationType.COPY_PASTE);
  }

  /**
   * Select 2 categories belonging to the context and copy them under "Orchestra" category.
   * @param operation_p
   * @throws Exception
   */
  private void copyOrCutIntraContextFromContextToInheritedCategory(OperationType operation_p) throws Exception {
    final String adminContextDisplayName = getAdministratorContextLabelInTree(_adminContextName);
    initializeTest();
    // Admin mode.
    changeModeTo(true);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    expandNodeWithCondition(contextsTree, getAdministratorContextLabelInTree(_adminContextName));
    // Select the 2 child categories of the context.
    contextsTree.select(contextsTree.getTreeItem(adminContextDisplayName).getNode(_adminContextCategory1), contextsTree.getTreeItem(adminContextDisplayName)
        .getNode(_adminContextCategory2));
    // ODM should be not dirty before a copy/cut operation.
    SWTBotTestHelper.assertOdmIsNotDirty();
    // Copy or Cut elements.
    String operationMenuText =
        (OperationType.COPY_PASTE == operation_p) ? org.eclipse.ui.internal.WorkbenchMessages.Workbench_copy
                                                 : org.eclipse.ui.internal.WorkbenchMessages.Workbench_cut;
    contextsTree.contextMenu(operationMenuText).click();
    // ODM should be not dirty after a copy/cut operation.
    SWTBotTestHelper.assertOdmIsNotDirty();
    contextsTree.getTreeItem(adminContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).expand();
    // Paste.
    contextsTree.getTreeItem(adminContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
    contextsTree.contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_paste).click();
    // ODM should be dirty after a paste operation.
    SWTBotTestHelper.assertOdmIsDirty();
    if (OperationType.COPY_PASTE == operation_p) {
      // It's a copy, check the 2 categories are still present under the context.
      Assert.assertTrue("Expecting to still see the copied category.",
          SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, adminContextDisplayName, _adminContextCategory1));
      Assert.assertTrue("Expecting to still see the copied category.",
          SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, adminContextDisplayName, _adminContextCategory2));
    } else {
      // It's a cut, check the 2 categories are no longer present under the context.
      Assert.assertFalse("Not expecting to see the cut category.",
          SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, adminContextDisplayName, _adminContextCategory1));
      Assert.assertFalse("Not expecting to see the cut category.",
          SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, adminContextDisplayName, _adminContextCategory2));
    }
    // Check the 2 categories have been pasted.
    Assert.assertTrue("Expecting to have the category pasted.",
        SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, adminContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA, _adminContextCategory1));
    Assert.assertTrue("Expecting to have the category pasted.",
        SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, adminContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA, _adminContextCategory2));
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  /**
   * Select 2 categories belonging to "Orchestra" category and copy them under the context root.
   * @param operation_p
   * @throws Exception
   */
  private void copyOrCutIntraContextFromInheritedCategoryToContext(OperationType operation_p) throws Exception {
    final String adminContextDisplayName = getAdministratorContextLabelInTree(_adminContextName);
    initializeTest();
    // Admin mode.
    changeModeTo(true);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Expand context node.
    expandNodeWithCondition(contextsTree, getAdministratorContextLabelInTree(_adminContextName), DataUtil.__CATEGORY_ORCHESTRA);
    // Select the 2 child categories of Orchestra.
    contextsTree.select(contextsTree.getTreeItem(adminContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(_adminContextOrchestraSubCategory1),
        contextsTree.getTreeItem(adminContextDisplayName).getNode(DataUtil.__CATEGORY_ORCHESTRA).getNode(_adminContextOrchestraSubCategory2));
    // ODM should be not dirty before a copy/cut operation.
    SWTBotTestHelper.assertOdmIsNotDirty();
    // Copy or Cut elements.
    String operationMenuText =
        (OperationType.COPY_PASTE == operation_p) ? org.eclipse.ui.internal.WorkbenchMessages.Workbench_copy
                                                 : org.eclipse.ui.internal.WorkbenchMessages.Workbench_cut;
    contextsTree.contextMenu(operationMenuText).click();
    // ODM should be not dirty after a copy/cut operation.
    SWTBotTestHelper.assertOdmIsNotDirty();
    // Paste.
    contextsTree.getTreeItem(adminContextDisplayName).select();
    contextsTree.contextMenu(org.eclipse.ui.internal.WorkbenchMessages.Workbench_paste).click();
    // ODM should be dirty after a paste operation.
    SWTBotTestHelper.assertOdmIsDirty();
    if (OperationType.COPY_PASTE == operation_p) {
      // It's a copy, check the 2 categories are still present under Orchestra.
      Assert.assertTrue("Expecting to still see the copied category.",
          SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, adminContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA, _adminContextOrchestraSubCategory1));
      Assert.assertTrue("Expecting to still see the copied category.",
          SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, adminContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA, _adminContextOrchestraSubCategory2));
    } else {
      // It's a cut, check the 2 categories are no longer present under Orchestra.
      Assert.assertFalse("Not expecting to see the cut category.",
          SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, adminContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA, _adminContextOrchestraSubCategory1));
      Assert.assertFalse("Not expecting to see the cut category.",
          SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, adminContextDisplayName, DataUtil.__CATEGORY_ORCHESTRA, _adminContextOrchestraSubCategory2));
    }
    // Check the 2 categories have been copied.
    Assert.assertTrue("Expecting to have the category pasted.",
        SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, adminContextDisplayName, _adminContextOrchestraSubCategory1));
    Assert.assertTrue("Expecting to have the category pasted.",
        SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, adminContextDisplayName, _adminContextOrchestraSubCategory2));
    ModelHandlerActivator.getDefault().getEditingDomain().save();
  }

  @Test
  public void cutIntraContextFromContextToInheritedCategory() throws Exception {
    copyOrCutIntraContextFromContextToInheritedCategory(OperationType.CUT_PASTE);
  }

  @Test
  public void cutIntraContextFromInheritedCategoryToContext() throws Exception {
    copyOrCutIntraContextFromInheritedCategoryToContext(OperationType.CUT_PASTE);
  }

  public static enum OperationType {
    COPY_PASTE, CUT_PASTE;
  }
}
