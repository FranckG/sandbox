/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.ui;

import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;

import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * An abstract implementation that does take care of navigation through pages based on {@link IEnvironmentHandler} capabilities.
 * @author t0076261
 */
public abstract class AbstractEnvironmentPage extends AbstractFormsWizardPage {
  /**
   * The environment handler reference.
   */
  protected final IEnvironmentHandler _handler;
  /**
   * Is current edited environment read-only ? If so it should be displayed as read-only !
   */
  protected final boolean _isCurrentEnvironmentReadOnly;

  /**
   * Constructor.
   * @param pageId_p
   * @param handler_p The environment handler responsible for currently edited environment.
   */
  public AbstractEnvironmentPage(String pageId_p, IEnvironmentHandler handler_p) {
    super(pageId_p);
    Assert.isNotNull(handler_p);
    _handler = handler_p;
    _isCurrentEnvironmentReadOnly = _handler.getVariablesHandler().isVariableReadOnly();
  }

  /**
   * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
   */
  @Override
  public boolean canFlipToNextPage() {
    return doCanFlipToNextPage();
  }

  /**
   * Is current page state enough to process to next page (or to finish the wizard) ?
   * @return <code>true</code> if so, <code>false</code> to wait for new inputs.
   */
  protected abstract boolean doCanFlipToNextPage();

  /**
   * Force page completion update.<br>
   * This involves the readiness to flip to next page.
   */
  protected void forceUpdate() {
    Map<String, String> updatedValues = getUpdatedValues();
    if (null != updatedValues) {
      _handler.updateAttributes(updatedValues);
    }
    setPageComplete(canFlipToNextPage());
  }

  /**
   * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
   */
  @Override
  public IWizardPage getNextPage() {
    return _handler.getNextPage(this);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return EnvironmentActivator.getInstance().getImageDescriptor("environments/generic_wiz_ban.gif"); //$NON-NLS-1$
  }

  /**
   * Get values as updated by the page.<br>
   * The map does represent deltas against initial values.
   * @return A not <code>null</code> but possibly empty map of updated values.
   */
  protected abstract Map<String, String> getUpdatedValues();

  /**
   * Get current {@link IVariablesHandler} accessible implementation.
   * @return
   */
  public IVariablesHandler getVariablesHandler() {
    return _handler.getVariablesHandler();
  }
}