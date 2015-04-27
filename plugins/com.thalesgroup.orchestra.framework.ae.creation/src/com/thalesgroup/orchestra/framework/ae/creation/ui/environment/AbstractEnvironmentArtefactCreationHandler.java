/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.creation.ui.environment;

import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.ae.creation.ArtefactCreationActivator;
import com.thalesgroup.orchestra.framework.ae.creation.IOrchestraExplorerServices;
import com.thalesgroup.orchestra.framework.ae.creation.ui.AbstractArtefactCreationHandler;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author s0040806
 */
public abstract class AbstractEnvironmentArtefactCreationHandler extends AbstractArtefactCreationHandler implements IEnvironmentArtefactCreationHandler {

  public static final String ROOT_TYPE_ATTRIBUTE = "rootType";
  public static final String ROOT_NAME_ATTRIBUTE = "rootName";

  private AbstractFormsWizardPage _lastPage;

  protected Map<String, String> _environmentAttributes;

  protected String _fileExtension;

  protected String _association;

  protected String _rootType;

  protected IOrchestraExplorerServices _services;

  protected EnvironmentInformation _environmentInformation;

  public final void initialize(EnvironmentInformation environmentInformation_p, String fileExtension_p, String association_p, String rootType_p,
      Map<String, String> parameters_p) {
    setParameters(parameters_p);
    _environmentInformation = environmentInformation_p;
    _environmentAttributes = environmentInformation_p.getAttributes();
    _fileExtension = fileExtension_p;
    _association = association_p;
    _parameters.put(ROOT_TYPE_ATTRIBUTE, rootType_p);
    _rootType = rootType_p;
    _services = ArtefactCreationActivator.getDefault().getOrchestraExplorerServices();
  }

  public String getFileExtension() {
    return _fileExtension;
  }

  public String getAssociation() {
    return _association;
  }

  public String getRootType() {
    return _rootType;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ae.creation.ui.environment.IEnvironmentArtefactCreationHandler#getEnvironmentAttributes()
   */
  public Map<String, String> getEnvironmentAttributes() {
    return _environmentAttributes;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ae.creation.ui.environment.IEnvironmentArtefactCreationHandler#setLastPage(org.eclipse.jface.wizard.WizardPage)
   */
  public final void setLastPage(AbstractFormsWizardPage lastPage_p) {
    _lastPage = lastPage_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ae.creation.ui.environment.IEnvironmentArtefactCreationHandler#getLastPage()
   */
  public AbstractFormsWizardPage getLastPage() {
    return _lastPage;
  }

  /**
   * Do get next page from specified one.
   * @param currentPage_p
   * @return Default implementation does return <code>null</code>.
   */
  protected AbstractEnvironmentArtefactCreationPage doGetNextPage(AbstractEnvironmentArtefactCreationPage currentPage_p) {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getNextPage(com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage)
   */
  public final AbstractFormsWizardPage getNextPage(AbstractEnvironmentArtefactCreationPage currentPage_p) {
    if (null == currentPage_p) {
      return null;
    }
    AbstractFormsWizardPage nextPage = doGetNextPage(currentPage_p);
    if (null == nextPage) {
      nextPage = getLastPage();
      nextPage.setPreviousPage(currentPage_p);
    }
    if (null != nextPage) {
      nextPage.setToolkit(currentPage_p.getToolkit());
      nextPage.setWizard(currentPage_p.getWizard());
    }
    return nextPage;
  }

  public String getRootName() {
    return getParameters().get(ROOT_NAME_ATTRIBUTE);
  }

  /**
   * Check if an Orchestra URI does already exist
   * @return True if the Orchestra URI is already used, otherwise false
   */
  public final boolean orchestraUriExists(OrchestraURI uri_p) {
    return _services.orchestraUriExists(uri_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ae.creation.ui.environment.IEnvironmentArtefactCreationHandler#preCreate()
   */
  public IStatus preCreate() {
    return Status.OK_STATUS;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ae.creation.ui.environment.IEnvironmentArtefactCreationHandler#postCreate()
   */
  public IStatus postCreate() {
    return Status.OK_STATUS;
  }

}
