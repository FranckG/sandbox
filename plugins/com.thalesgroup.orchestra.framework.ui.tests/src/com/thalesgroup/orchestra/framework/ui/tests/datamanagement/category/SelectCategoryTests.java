/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.category;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * Contexts admin/user category selection tests.
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class SelectCategoryTests extends AbstractCategoryTest {
  /**
   * Tests: <br>
   * 1- In admin mode, select Orchestra category in the context viewer and check several variables are visible in the variables viewer. <br>
   * 2- In user mode, same as in admin mode.
   * @throws Exception
   */
  @Test
  public void selectCategory() throws Exception {
    initializeTest();

    Context adminContext = findContextForName(_adminContextName, false).getValue();
    String artefactPathVariableName = DataUtil.getVariable(DataUtil.__ARTEFACTPATH_VARIABLE_NAME, adminContext).getName();
    String configurationDirectoriesVariableName = DataUtil.getVariable(DataUtil.__CONFIGURATIONDIRECTORIES_VARIABLE_NAME, adminContext).getName();
    String configurationDirectoryVariableName = DataUtil.getVariable(DataUtil.__CONFIGURATIONDIRECTORY_VARIABLE_NAME, adminContext).getName();
    // Admin mode.
    {
      changeModeTo(true);
      // Find context viewer
      SWTBotTree contextsTree = getContextsTree();
      // Expand context node.
      expandNodeWithCondition(contextsTree, getAdministratorContextLabelInTree(_adminContextName));
      // Select Orchestra category.
      contextsTree.getTreeItem(getAdministratorContextLabelInTree(_adminContextName)).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
      SWTBotTree variablesTree = getVariablesTree();
      // Check several variables of Orchestra category are visible.
      Assert.assertTrue("Expecting to see " + artefactPathVariableName + " variable.", variablesTree.getTreeItem(artefactPathVariableName).isVisible());
      Assert.assertTrue("Expecting to see ConfigurationDirectories " + configurationDirectoriesVariableName + " variable.",
          variablesTree.getTreeItem(configurationDirectoriesVariableName).isVisible());
      Assert.assertTrue("Expecting to see ArtefactPath " + configurationDirectoryVariableName + " variable.",
          variablesTree.getTreeItem(configurationDirectoryVariableName).isVisible());

    }
    // User mode.
    {
      changeModeTo(false);
      // Find context viewer
      SWTBotTree contextsTree = getContextsTree();
      // Expand context node.
      expandNodeWithCondition(contextsTree, getParticipationLabelInTree(_userContextName, _adminContextName));
      // Select Orchestra category.
      contextsTree.getTreeItem(getParticipationLabelInTree(_userContextName, _adminContextName)).getNode(DataUtil.__CATEGORY_ORCHESTRA).select();
      SWTBotTree variablesTree = getVariablesTree();
      // Check several variables of Orchestra category are visible.
      Assert.assertTrue("Expecting to see " + artefactPathVariableName + " variable.", variablesTree.getTreeItem(artefactPathVariableName).isVisible());
      Assert.assertTrue("Expecting to see ConfigurationDirectories " + configurationDirectoriesVariableName + " variable.",
          variablesTree.getTreeItem(configurationDirectoriesVariableName).isVisible());
      Assert.assertTrue("Expecting to see ArtefactPath " + configurationDirectoryVariableName + " variable.",
          variablesTree.getTreeItem(configurationDirectoryVariableName).isVisible());

    }
  }
}
