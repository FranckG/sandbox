/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.creation.ui.connector;

import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;

import com.thalesgroup.orchestra.framework.ae.creation.ui.Messages;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author s0040806
 */
public abstract class AbstractConnectorArtefactCreationPage extends AbstractFormsWizardPage {

  protected IConnectorArtefactCreationHandler _handler;

  public AbstractConnectorArtefactCreationPage(String pageId_p, IConnectorArtefactCreationHandler handler_p) {
    super(pageId_p);
    _handler = handler_p;
  }

  /**
   * @param pageId_p
   */
  public AbstractConnectorArtefactCreationPage(String pageId_p) {
    super(pageId_p);
  }

  protected abstract Map<String, String> getUpdatedValues();

  public void forceUpdate() {
    Map<String, String> updatedValues = getUpdatedValues();
    if (null != updatedValues) {
      _handler.updateParameters(updatedValues);
    }
    setPageComplete(canFlipToNextPage());
  }

  @Override
  public IWizardPage getNextPage() {
    return _handler.getNextPage(this);
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
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.ArtifactCreationWizard_Title;
  }

}
