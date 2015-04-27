/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.creation.ui.environment;

import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;

import com.thalesgroup.orchestra.framework.ae.creation.ui.Messages;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author s0040806
 */
public abstract class AbstractEnvironmentArtefactCreationPage extends AbstractFormsWizardPage {

  protected Map<String, String> _environmentAttributes;
  protected String _fileExtension;
  protected String _association;
  protected String _rootType;

  private IEnvironmentArtefactCreationHandler _handler;

  public AbstractEnvironmentArtefactCreationPage(String pageId_p, IEnvironmentArtefactCreationHandler handler_p) {
    this(pageId_p);
    _handler = handler_p;
    _environmentAttributes = _handler.getEnvironmentAttributes();
    _fileExtension = handler_p.getFileExtension();
    _association = handler_p.getAssociation();
    _rootType = _handler.getRootType();
  }

  /**
   * @param pageName_p
   */
  protected AbstractEnvironmentArtefactCreationPage(String pageName_p) {
    super(pageName_p);
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
    return doCanFlipToNextPage() && null != _handler.getLastPage();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.ArtifactCreationWizard_Title;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return null;
  }

  /**
   * Is current page state enough to process to next page (or to finish the wizard) ?
   * @return <code>true</code> if so, <code>false</code> to wait for new inputs.
   */
  protected abstract boolean doCanFlipToNextPage();
}
