/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.thalesgroup.orchestra.framework.connector.sdk.SdkWizardsActivator;
import com.thalesgroup.orchestra.framework.connector.sdk.operation.ConnectorProjectParameters;
import com.thalesgroup.orchestra.framework.connector.sdk.operation.NewConnectorCreationOperation;
import com.thalesgroup.orchestra.framework.connector.sdk.wizards.pages.ConnectorAssociationsPage;
import com.thalesgroup.orchestra.framework.connector.sdk.wizards.pages.ConnectorStructurePage;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;

/**
 * New connector wizard (OF SDK).
 * @author t0076261
 */
public class NewConnectorWizard extends AbstractFormsWizard implements INewWizard, IExecutableExtension {
  /**
   * org.eclipse.ui.newWizards
   */
  private IConfigurationElement _configurationElement;

  protected ConnectorAssociationsPage _connectorPageTwo;

  protected ConnectorStructurePage _connectorStructurePage;

  /**
   * Project creation parameters.
   */
  private ConnectorProjectParameters _projectParameters;

  protected IWorkbench _workbench;

  public NewConnectorWizard() {
    setNeedsProgressMonitor(true);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    _projectParameters = new ConnectorProjectParameters();
    _connectorStructurePage = new ConnectorStructurePage(_projectParameters);
    addPage(_connectorStructurePage);
    _connectorPageTwo = new ConnectorAssociationsPage(_projectParameters);
    addPage(_connectorPageTwo);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
   */
  @Override
  public String getWindowTitle() {
    return Messages.NewConnectorWizard_Title;
  }

  /**
   * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
   */
  public void init(IWorkbench workbench_p, IStructuredSelection selection_p) {
    _workbench = workbench_p;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    _connectorStructurePage.updateData();
    _connectorPageTwo.updateData();
    try {
      // Connector project creation.
      NewConnectorCreationOperation newConnectorCreationOperation = new NewConnectorCreationOperation(_projectParameters);
      getContainer().run(true, false, newConnectorCreationOperation);
      // Change to preferred perspective (plug-in development).
      BasicNewProjectResourceWizard.updatePerspective(_configurationElement);
      return true;
    } catch (InvocationTargetException exception_p) {
      IStatus status =
          new Status(IStatus.ERROR, SdkWizardsActivator.getDefault().getPluginId(), Messages.NewConnectorWizard_ConnectorCreationFailed_Message,
              exception_p.getTargetException());
      StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
    } catch (InterruptedException exception_p) {
      // Should not occur if "cancelable" parameter of run() is false.
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String,
   * java.lang.Object)
   */
  public void setInitializationData(IConfigurationElement configurationElement_p, String propertyName, Object data) throws CoreException {
    _configurationElement = configurationElement_p;
  }

}