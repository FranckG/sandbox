/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.swtbotcondition;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

import com.thalesgroup.orchestra.framework.model.contexts.Context;

/**
 * @author S0032874
 */
public class ConditionFactory {
  /**
   * Create a {@link CategoryRenamedCondition} on the renaming of a category
   * @param context_p The context carrying the category
   * @param categoryPathName_p The path to the category, separated by \\, with the new name at the end
   * @return The {@link CategoryRenamedCondition}, i.e. true if the name has changed, else false
   */
  public CategoryRenamedCondition createCategoryRenamedCondition(Context context_p, String categoryPathName_p) {
    return new CategoryRenamedCondition(context_p, categoryPathName_p);
  }

  /**
   * Create a {@link ContextIsCreatedCondition}.
   * @param location_p where to find contexts
   * @param numberOfContexts_p number of awaited contexts.
   * @return
   */
  public ContextIsCreatedCondition createContextIsCreatedCondition(String location_p, int numberOfContexts_p) {
    return new ContextIsCreatedCondition(location_p, numberOfContexts_p);
  }

  /**
   * Create a {@link FileNoLongerExistsCondition}.
   * @param filePath_p file to check existence
   * @return
   */
  public FileNoLongerExistsCondition createFileNoLongerExistsCondition(String filePath_p) {
    return new FileNoLongerExistsCondition(filePath_p);
  }

  /**
   * Create a {@link NodeIsExpandedCondition} on the expand of a node
   * @param node_p The node being expanded
   * @return The {@link NodeIsExpandedCondition}, i.e. true if the node is expanded else false
   */
  public NodeIsExpandedCondition createNodeIsExpandedCondition(SWTBotTreeItem node_p) {
    return new NodeIsExpandedCondition(node_p);
  }

  /**
   * Get Platform Shell is active one condition.
   * @return
   */
  public PlatformShellIsActiveCondition createPlatformShellIsActiveCondition() {
    return new PlatformShellIsActiveCondition();
  }

  /**
   * Create a {@link TreeItemTextCondition} on the value of the text of a {@link SWTBotTreeItem}
   * @param item_p The {@link SWTBotTreeItem}
   * @param textValue_p The text value to achieve
   * @return The {@link TreeItemTextCondition}, i.e. true if the name has changed, else false
   */
  public TreeItemTextCondition createTreeItemTextCondition(SWTBotTreeItem item_p, String textValue_p) {
    return new TreeItemTextCondition(item_p, textValue_p);
  }
}
