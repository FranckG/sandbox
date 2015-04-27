/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard;

import com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractChangeWizard;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.EditCategoryPage;

/**
 * Category edition wizard.
 * @author t0076261
 */
public class EditCategoryWizard extends AbstractChangeWizard<Category> {
  /**
   * Constructor.
   * @param category_p
   */
  public EditCategoryWizard(Category category_p) {
    setObject(category_p);
    setNeedsProgressMonitor(false);
    setWindowTitle(Messages.EditCategoryWizard_Wizard_Title);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    addPage(new EditCategoryPage(getObject()));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractChangeWizard#getCommandLabel()
   */
  @Override
  protected String getCommandLabel() {
    return Messages.EditCategoryWizard_Command_Label;
  }
}