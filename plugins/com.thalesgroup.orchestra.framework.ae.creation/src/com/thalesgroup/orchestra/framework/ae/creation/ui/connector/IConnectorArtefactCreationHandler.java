/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.creation.ui.connector;

import java.util.Map;

import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author s0040806
 */
public interface IConnectorArtefactCreationHandler {

  public void initialize(Map<String, String> parameters_p);

  public AbstractFormsWizardPage getFirstPage();

  public AbstractFormsWizardPage getNextPage(AbstractConnectorArtefactCreationPage currentPage_p);

  boolean arePagesComplete();

  public Map<String, String> getParameters();

  public void updateParameters(Map<String, String> updatedParameters_p);
}
