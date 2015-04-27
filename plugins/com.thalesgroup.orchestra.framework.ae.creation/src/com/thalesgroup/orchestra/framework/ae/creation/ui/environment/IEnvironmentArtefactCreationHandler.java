/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.creation.ui.environment;

import java.util.Map;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author s0040806
 */
public interface IEnvironmentArtefactCreationHandler {

  public void initialize(EnvironmentInformation environmentInformation_p, String fileExtension_p, String association_p, String type_p,
      Map<String, String> parameters_p);

  public String getFileExtension();

  public String getAssociation();

  public String getRootType();

  public String getRootName();

  public AbstractFormsWizardPage getFirstPage();

  public AbstractFormsWizardPage getNextPage(AbstractEnvironmentArtefactCreationPage currentPage);

  public void setLastPage(AbstractFormsWizardPage page_p);

  public AbstractFormsWizardPage getLastPage();

  boolean arePagesComplete();

  public Map<String, String> getEnvironmentAttributes();

  public Map<String, String> getParameters();

  public void updateParameters(Map<String, String> updatedParameters_p);

  public IStatus preCreate();

  public IStatus postCreate();
}
