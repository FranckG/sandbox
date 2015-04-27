/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.creation.ui.connector;

import java.util.Map;

import com.thalesgroup.orchestra.framework.ae.creation.ui.AbstractArtefactCreationHandler;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author s0040806
 */
public abstract class AbstractConnectorArtefactCreationHandler extends AbstractArtefactCreationHandler implements IConnectorArtefactCreationHandler {

  public void initialize(Map<String, String> parameters_p) {
    setParameters(parameters_p);
  }

  /**
   * Do get next page from specified one.
   * @param currentPage_p
   * @return Default implementation does return <code>null</code>.
   */
  protected AbstractConnectorArtefactCreationPage doGetNextPage(AbstractConnectorArtefactCreationPage currentPage_p) {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getNextPage(com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage)
   */
  public final AbstractFormsWizardPage getNextPage(AbstractConnectorArtefactCreationPage currentPage_p) {
    if (null == currentPage_p) {
      return null;
    }
    AbstractFormsWizardPage nextPage = doGetNextPage(currentPage_p);
    if (null != nextPage) {
      nextPage.setToolkit(currentPage_p.getToolkit());
      nextPage.setWizard(currentPage_p.getWizard());
    }
    return nextPage;
  }
}
