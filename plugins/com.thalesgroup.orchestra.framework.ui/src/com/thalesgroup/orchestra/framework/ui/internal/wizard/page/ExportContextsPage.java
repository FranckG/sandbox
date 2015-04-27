/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.RootElement;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.ProjectHandler;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * @author t0076261
 */
public class ExportContextsPage extends AbstractContextsSelectionWithLocationPage {
  /**
   * Contexts to select in export selection tree, can be null.
   */
  private List<Context> _contextsToSelect;

  /**
   * Constructor.
   * @param context_p contexts to select in this wizard (can be null).
   */
  public ExportContextsPage(List<Context> contexts_p) {
    super("ExportContexts"); //$NON-NLS-1$
    _contextsToSelect = contexts_p;
  }

  @Override
  protected Composite createContextsSelectionPart(Composite parent_p, FormToolkit toolkit_p) {
    Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, false);
    Label title = new Label(composite, SWT.NONE);
    title.setText(Messages.ExportContextsPage_ContextsSelectionPart_Title);
    super.createContextsSelectionPart(composite, toolkit_p);
    return composite;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite parent = new Composite(parent_p, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.marginWidth = 0;
    parent.setLayout(layout);
    parent.setLayoutData(new GridData(GridData.FILL_BOTH));
    // Create parent part.
    super.doCreateControl(parent, toolkit_p);
    // Create external directory choice.
    createChooseLocationPart(parent, toolkit_p);

    // Preselect some contexts (if needed).
    if (null != _contextsToSelect) {
      // Find RootContextsProjects to preselect.
      List<RootContextsProject> rootContextsProjectsToSelect = new ArrayList<RootContextsProject>();
      for (Context contextToSelect : _contextsToSelect) {
        URI contextToSelectUri = contextToSelect.eResource().getURI();
        RootContextsProject rootContextsProjectToSelect = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(contextToSelectUri);
        rootContextsProjectsToSelect.add(rootContextsProjectToSelect);
      }
      setCheckedProjects(rootContextsProjectsToSelect);
    }

    return parent;
  }

  /**
   * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
   */
  @Override
  public boolean doIsPageComplete() {
    return validateSelection() && validateLocation();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#getInvalidLocationErrorMessage()
   */
  @Override
  protected String getInvalidLocationErrorMessage() {
    return Messages.ExportContextsPage_ErrorMessage_InvalidDestinationDirectory;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#getLocationDialogMessage()
   */
  @Override
  protected String getLocationDialogMessage() {
    return Messages.ExportContextsPage_Dialog_Message;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#getLocationLabelText()
   */
  @Override
  protected String getLocationLabelText() {
    return Messages.ExportContextsPage_Label_Text_Location;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.ExportContextsPage_Page_Title;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#getPromptForContextLocationMessage()
   */
  @Override
  protected String getPromptForContextLocationMessage() {
    return Messages.ExportContextsPage_Message_PromptForLocationPath;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#getPromptForContextSelectionMessage()
   */
  @Override
  protected String getPromptForContextSelectionMessage() {
    return Messages.ExportContextsPage_Message_PromptForContextSelection;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionPage#refreshViewer()
   */
  @Override
  public void refreshViewer() {
    // Get all contexts loaded in workspace.
    RootElement root = ModelHandlerActivator.getDefault().getDataHandler().getAllContextsInWorkspace();
    // Filter for current user.
    root.filterForAdministrator(ProjectActivator.getInstance().getCurrentUserId());
    // Fill the tree.
    for (RootContextsProject rootContextsProject : root.getProjects()) {
      addNewElement(rootContextsProject, false);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#isValidLocation(java.lang.String)
   */
  @SuppressWarnings("boxing")
  @Override
  protected boolean validateLocation() {
    boolean validLocation = super.validateLocation();
    if (validLocation) {
      // Check existing projects against checked ones for selected location.
      // Make sure there is something to test first.
      Collection<RootContextsProject> checkedProjects = getCheckedProjects();
      if ((null == checkedProjects) || checkedProjects.isEmpty()) {
        return false;
      }
      // Get projects for selected location.
      ProjectHandler projectHandler = ProjectActivator.getInstance().getProjectHandler();
      // Make sure there is no project at selected location and hierarchy.
      IPath currentLocation = new Path(getLocation());
      // Go for hierarchy.
      while (true) {
        RootContextsProject locationProject = projectHandler.getContextFromLocation(currentLocation.toString(), null);
        if (null != locationProject) {
          setMessage(MessageFormat.format(Messages.ExportContextsPage_ErrorMessage_HierachyIsAlreadyAProject, locationProject._project.getName()), ERROR);
          return false;
        }
        // Stop here.
        if (0 == currentLocation.segmentCount()) {
          break;
        }
        // Remove last segment, up to root path (0 segment included since the test is done before this call).
        currentLocation = currentLocation.removeLastSegments(1);
      }
      IPath locationPath = new Path(getLocation());
      // Cycle through selected projects.
      StringBuilder errorMessage = new StringBuilder();
      for (RootContextsProject checkedProject : checkedProjects) {
        // Make sure there is no project already existing at future location.
        String checkedDirectory = new Path(checkedProject.getLocation()).lastSegment();
        String resultingLocation = locationPath.append(checkedDirectory).toString();
        RootContextsProject locationProject = projectHandler.getContextFromLocation(resultingLocation, null);
        if (checkedProject.equals(locationProject)) {
          errorMessage.append((errorMessage.length() > 0) ? '\n' : ICommonConstants.EMPTY_STRING).append(
              MessageFormat.format(Messages.ExportContextsPage_ErrorMessage_SameLocation, locationProject._project.getName()));
        }
      }
      // Errors ?
      if (errorMessage.length() > 0) {
        setMessage(errorMessage.toString(), ERROR);
        return false;
      }
    }
    return validLocation;
  }
}