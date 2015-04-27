/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

import junit.framework.Assert;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
public class SWTBotTestHelper {
  private static boolean areNodesDisplayedFromTreeItem(SWTBotTreeItem[] treeItem_p, String... nodeNamesOfPath_p) {
    if (0 == nodeNamesOfPath_p.length) {
      return true;
    }
    ArrayList<String> remainingNodesName = new ArrayList<String>(Arrays.asList(nodeNamesOfPath_p));
    remainingNodesName.remove(0);
    for (SWTBotTreeItem treeItem : treeItem_p) {
      if (treeItem.getText().equals(nodeNamesOfPath_p[0])) {
        return areNodesDisplayedFromTreeItem(treeItem.getItems(), remainingNodesName.toArray(new String[0]));
      }
    }

    return false;
  }

  /**
   * Check the given node path exists in the given tree. WidgetNotFoundException is not thrown by this method so it can be used to check if a node is NOT
   * displayed.
   * @param swtBotTree_p The tree in which to check node(s) are displayed.
   * @param nodeNamesOfPath_p The path of nodes.
   * @return <code>true</code> if every nodes in the path have been found. <code>false</code> else.
   */
  public static boolean areNodesDisplayedInTree(SWTBotTree swtBotTree_p, String... nodeNamesOfPath_p) {
    return areNodesDisplayedFromTreeItem(swtBotTree_p.getAllItems(), nodeNamesOfPath_p);
  }

  /**
   * Check ODM is dirty by checking the "enabled" state of "Save" in "File" menu.
   */
  public static void assertOdmIsDirty() {
    SWTWorkbenchBot bot = AbstractTest.getBot();
    Assert.assertTrue(
        "Expecting ODM to be in dirty state.",
        bot.menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
            .menu(org.eclipse.ui.internal.WorkbenchMessages.SaveAction_text).isEnabled());
  }

  /**
   * Check ODM is not dirty by checking the "enabled" state of "Save" in "File" menu.
   */
  public static void assertOdmIsNotDirty() {
    SWTWorkbenchBot bot = AbstractTest.getBot();
    Assert.assertFalse(
        "Not expecting ODM to be in dirty state.",
        bot.menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File)
            .menu(org.eclipse.ui.internal.WorkbenchMessages.SaveAction_text).isEnabled());
  }
}
