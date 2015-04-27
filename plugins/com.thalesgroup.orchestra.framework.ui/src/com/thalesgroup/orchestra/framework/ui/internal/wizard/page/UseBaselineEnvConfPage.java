/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentUseBaselineHandler;
import com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.UseBaselineType;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.DeleteContextServiceCommand;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;
import com.thalesgroup.orchestra.framework.ui.dialog.ChooseBaselineDialog.BaselineSelectionType;
import com.thalesgroup.orchestra.framework.ui.view.EnvUseBaselineBlock;
import com.thalesgroup.orchestra.framework.ui.wizard.NewUseBaselineContextWizard;
import com.thalesgroup.orchestra.framework.ui.wizard.NewUseBaselineContextWizard.UseBaselineContextsModel;

/**
 * @author T0052089
 */
public class UseBaselineEnvConfPage extends AbstractFormsWizardPage {
  /**
   * Baseline context, from the Baselines Repository.
   */
  protected RootContextsProject _baselineContext;

  /**
   * Created starting point context (a child of the baseline context).
   */
  protected Context _createdUseBaselineContext;

  /**
   * Starting point master/details block.
   */
  protected EnvUseBaselineBlock _envUseBaselineBlock;

  /**
   * The wizard containing this page.
   */
  protected NewUseBaselineContextWizard _newUseBaselineContextWizard;

  /**
   * Starting point contexts model.
   */
  protected UseBaselineContextsModel _useBaselineContextsModel;

  protected String _baselineName;

  protected BaselineSelectionType _baselineSelection;

  /**
   * Constructor.
   */
  public UseBaselineEnvConfPage(NewUseBaselineContextWizard newStartingPointContextWizard_p, RootContextsProject baselineContext_p, String baselineName_p,
      BaselineSelectionType baselineSelection_p) {
    super(UseBaselineEnvConfPage.class.getName());
    _newUseBaselineContextWizard = newStartingPointContextWizard_p;
    _baselineContext = baselineContext_p;
    _baselineName = baselineName_p;
    _baselineSelection = baselineSelection_p;
  }

  /**
   * Create the starting point context (a child of the baselined context, with name and location given in previous pages).
   */
  protected IStatus createProvisionalContext() {
    IStatus status;
    // Create starting point context
    if (BaselineSelectionType.STARTING_POINT == _baselineSelection) {
      // Get starting point context name.
      String newContextName = _newUseBaselineContextWizard.getNewContextParametersPage().getNewContextName();
      // Get starting point context location (external or in-workspace location).
      String projectLocation = _newUseBaselineContextWizard.isUseProjectLocation() ? _newUseBaselineContextWizard.getNewProjectLocation() : null;
      // Create the starting point context.
      DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
      status = dataHandler.createNewContextStructure(newContextName, projectLocation, _baselineContext);
      if (!status.isOK()) {
        // An error occurred when creating context, stop here.
        return status;
      }
      // Retrieve created context.
      String newContextProjectName = ProjectHelper.generateContextProjectName(newContextName);
      IProject newContextProject = ResourcesPlugin.getWorkspace().getRoot().getProject(newContextProjectName);
      RootContextsProject createdRootContextProject = ProjectActivator.getInstance().getProjectHandler().getRootContextsProject(newContextProject);
      _createdUseBaselineContext = dataHandler.getContext(createdRootContextProject.getAdministratedContext());
    } else {
      // Create participation to context
      DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
      status = dataHandler.participateInAProject(_baselineName, _baselineContext);
      _createdUseBaselineContext = dataHandler.getContext(_baselineContext.getContext());
      if (!status.isOK()) {
        // Remove participation from disk.
        DeleteContextServiceCommand command = new DeleteContextServiceCommand(_createdUseBaselineContext, true);
        if (command.canExecute()) {
          command.execute();
        }
        return new CommandStatus(status);
      }
    }
    // Fill internal model.
    _useBaselineContextsModel = new UseBaselineContextsModel(_createdUseBaselineContext);
    _envUseBaselineBlock.setInput(_useBaselineContextsModel);
    return status;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite pageComposite = new Composite(parent_p, SWT.NONE);
    pageComposite.setLayout(new FillLayout());
    _envUseBaselineBlock = new EnvUseBaselineBlock(getToolkit(), getContainer(), _baselineSelection);
    _envUseBaselineBlock.createContent(new ManagedForm(pageComposite));
    return pageComposite;
  }

  /**
   * @return the createdStartingPointContext
   */
  public Context getCreatedUseBaselineContext() {
    return _createdUseBaselineContext;
  }

  /**
   * @return the envValuesStartingPointContexts
   */
  public UseBaselineContextsModel getEnvValuesUseBaselineContexts() {
    return _useBaselineContextsModel;
  }

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
    return null;
  }

  /**
   * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
   */
  @Override
  public boolean isPageComplete() {
    if (null == _useBaselineContextsModel) {
      return false;
    }
    // For each StartingPointContext, ask its handler if it is valid.
    for (UseBaselineEnvironmentContext useBaselineContext : _useBaselineContextsModel.getEnvValueToUseBaselineContext().values()) {
      // Get corresponding handler.
      Couple<IStatus, IEnvironmentHandler> envHandlerCouple =
          EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentHandler(useBaselineContext._environmentId);
      if (!envHandlerCouple.getKey().isOK()) {
        // No handler found -> starting point context is considered as non valid.
        return false;
      }

      IEnvironmentUseBaselineHandler useBaselineHandler = envHandlerCouple.getValue().getEnvironmentUseBaselineHandler();
      // Starting point
      if (UseBaselineType.LIVE_ENVIRONMENT.equals(useBaselineContext.getUseBaselineType())) {
        if (!useBaselineHandler.isStartingPointContextValid(useBaselineContext)) {
          return false;
        }
      } else {
        // Reference
        if (!useBaselineHandler.isReferenceContextValid(useBaselineContext)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Called when the containing wizard is canceled to delete the possibly created context.
   */
  public void performCancel() {
    deleteProvisionalContext();
  }

  /**
   * Delete the starting point context created when this page was displayed.
   */
  protected void deleteProvisionalContext() {
    if (null != _createdUseBaselineContext) {
      // Delete created project.
      DeleteContextServiceCommand command;
      if (BaselineSelectionType.STARTING_POINT == _baselineSelection) {
        command = new DeleteContextServiceCommand(_createdUseBaselineContext, true, true);
      } else {
        command = new DeleteContextServiceCommand(_createdUseBaselineContext, true);
      }
      if (command.canExecute()) {
        command.execute();
      }
      _createdUseBaselineContext = null;
      _envUseBaselineBlock.setInput(null);
    }
  }

  /**
   * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
   */
  @Override
  public void setVisible(boolean visible_p) {
    if (visible_p) {
      // Current page is going to be displayed, create the starting point context.
      IStatus createContextStatus = createProvisionalContext();
      if (!createContextStatus.isOK()) {
        // Creation failed, show an error dialog.
        DisplayHelper.displayErrorDialog(Messages.StartingPointEnvConfPage_ContextCreationFailed_DialogTitle,
            Messages.StartingPointEnvConfPage_ContextCreationFailed_DialogMessage, createContextStatus);
      }
    } else {
      // Leaving back to location page ? -> Remove provisional context.
      if (getContainer().getCurrentPage() == _newUseBaselineContextWizard.getNewContextWizardLocationPage()) {
        deleteProvisionalContext();
      }
    }
    super.setVisible(visible_p);
  }

}
