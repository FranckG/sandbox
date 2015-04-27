/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import com.thalesgroup.orchestra.framework.model.contexts.Category;

/**
 * Edit category wizard page.
 * @author t0076261
 */
public class EditCategoryPage extends AbstractEditNamedElementPage {
  /**
   * Edited category.
   */
  protected Category _category;

  /**
   * Constructor.
   * @param category_p
   */
  public EditCategoryPage(Category category_p) {
    super("EditCategory"); //$NON-NLS-1$
    _category = category_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditNamedElementPage#getNamedElement()
   */
  @Override
  protected Category getNamedElement() {
    return _category;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.EditCategoryPage_Page_Title;
  }
}