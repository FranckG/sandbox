/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.wizard;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler.InvalidContextException;
import com.thalesgroup.orchestra.framework.project.CaseUnsensitiveResourceSetImpl;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractNewContextPage;

/**
 * Wizard to participate to a context as a user.
 * @author s0011584
 */
public class ParticipateContextWizard extends AbstractFormsWizard {
  /**
   * The participate project WizardPage.
   */
  private ParticipateProjectWizardPage _participateProjectWizardPage;

  /**
   * Constructor.
   */
  public ParticipateContextWizard() {
    setNeedsProgressMonitor(true);
  }

  @Override
  public void addPages() {
    _participateProjectWizardPage = new ParticipateProjectWizardPage();
    _participateProjectWizardPage.setTitle(Messages.ParticipateContextWizard_Title);
    _participateProjectWizardPage.setDescription(Messages.ParticipateContextWizard_Description);
    addPage(_participateProjectWizardPage);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
   */
  @Override
  public String getWindowTitle() {
    return Messages.ParticipateContextWizard_Dialog_Title_NewParticipation;
  }

  @Override
  public boolean performFinish() {
    IStatus result =
        ModelHandlerActivator
            .getDefault()
            .getDataHandler()
            .participateInAProject(
                _participateProjectWizardPage.getNewContextName() == null ? ICommonConstants.EMPTY_STRING : _participateProjectWizardPage.getNewContextName(),
                _participateProjectWizardPage.getSelectedParent());
    if (!result.isOK()) {
      _participateProjectWizardPage.setMessage(result.getMessage(), IMessageProvider.ERROR);
    }
    return result.isOK();
  }

  /**
   * @author s0011584
   */
  public class ParticipateProjectWizardPage extends AbstractNewContextPage {
    /**
     * List of RootContextsProject having an user context currently loaded in the Framework.
     */
    protected List<RootContextsProject> _contextWithAlreadyLoadedParticipation;
    /**
     * List of RootContextsProject absolute paths currently loaded in the Framework.
     */
    protected List<String> _managedProjectsAbsolutePaths;
    /**
     * List of user context name currently loaded in the Framework.
     */
    protected List<String> _managedUserContextsNames;

    /**
     * Constructor.
     * @param pageId_p
     */
    protected ParticipateProjectWizardPage() {
      super(ParticipateProjectWizardPage.class.getName());
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.ImportContextsPage#accept(com.thalesgroup.orchestra.framework.project.RootContextsProject)
     */
    @Override
    protected boolean accept(RootContextsProject rootContextsProject_p) {
      synchronized (_initManagedElementsThread) {
        // RootContextsProject with already loaded participation are not displayed.
        // Also ignore baseline ones.
        return !(_contextWithAlreadyLoadedParticipation.contains(rootContextsProject_p) || rootContextsProject_p.isBaseline());
      }
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractNewContextPage#createParentSelectionButton(org.eclipse.swt.widgets.Composite,
     *      int)
     */
    @Override
    protected void createParentSelectionButton(Composite parent_p, int numColumns_p) {
      // Do nothing, no need to make parent selection optional.
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.ImportContextsPage#doCreateControl(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.ui.forms.widgets.FormToolkit)
     */
    @Override
    protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
      Composite parent = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 2, false);
      // Parent selection.
      Composite group = createParentSelection(parent);
      // Create name control.
      createNameSelection(parent, Messages.ParticipateContextWizard_Name_Label_Text);
      // Create parent part.
      super.doCreateControl(group, toolkit_p);
      return parent;
    }

    /**
     * Inspired by {@link WizardNewProjectCreationPage#validatePage()}.
     */
    @Override
    public boolean doIsPageComplete() {
      if (!super.doIsPageComplete()) {
        return false;
      }
      // If selected admin context already contains an user context, no need to validate name.
      if (!isParticipationAlreadyExisting() && !isContextNameValid()) {
        return false;
      }
      _nameTextDecoration.hide();
      return true;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.ImportContextsPage#initializeManagedElementsLists()
     */
    @Override
    protected void initializeManagedElementsLists() {
      super.initializeManagedElementsLists();
      // Populate a list of project absolute paths for already loaded contexts.
      _managedProjectsAbsolutePaths = new ArrayList<String>();
      _managedUserContextsNames = new ArrayList<String>();
      Collection<RootContextsProject> loadedProjects = ProjectActivator.getInstance().getProjectHandler().getAllProjectsInWorkspace();
      for (RootContextsProject rootContextsProject : loadedProjects) {
        _managedProjectsAbsolutePaths.add(rootContextsProject.getLocation());
        ContextReference userContextReference = rootContextsProject.getContext();
        if (null != userContextReference) {
          DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
          String currentUserContextName = dataHandler.getContext(userContextReference).getName();
          _managedUserContextsNames.add(currentUserContextName);
        }
      }
      // Find RootContextsProjects with an already loaded participation.
      _contextWithAlreadyLoadedParticipation = new ArrayList<RootContextsProject>();
      String currentUserId = ProjectActivator.getInstance().getCurrentUserId();
      ResourceSet resourceSet = ModelHandlerActivator.getDefault().getEditingDomain().getResourceSet();
      for (RootContextsProject projectInWorkspace : _projectsInWorkspace) {
        String userContextUri = projectInWorkspace.getUserContextUri(currentUserId);
        // Try and get resource (participation) from resource set, but do not load it !
        if ((null != userContextUri) && (null != resourceSet.getResource(URI.createURI(userContextUri), false))) {
          // Participation exists, keep it in the list.
          _contextWithAlreadyLoadedParticipation.add(projectInWorkspace);
        }
      }
    }

    /**
     * Unlike in the super method, already loaded contexts are not in conflict.
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.ImportContextsPage#isInConflict(com.thalesgroup.orchestra.framework.project.RootContextsProject)
     */
    @Override
    protected boolean isInConflict(RootContextsProject rootContextProject_p) {

      // If the root context project already contains an user context check this name isn't already used.
      URI userContextAbsoluteURI = URI.createFileURI(rootContextProject_p.getLocation() + RootContextsProject.getUserContextPath());
      ResourceSet resourceSet = new CaseUnsensitiveResourceSetImpl();
      try {
        // Try to load the user context in a temporary resource set.
        Context userContext = ModelHandlerActivator.getDefault().getDataHandler().getContext(userContextAbsoluteURI, resourceSet);
        synchronized (_initManagedElementsThread) {
          if (_managedUserContextsNames.contains(userContext.getName())) {
            return true;
          }
        }
      } catch (InvalidContextException ex) {
        // No user context associated to current user in this project.
      }
      synchronized (_initManagedElementsThread) {
        // Already loaded admin contexts are NOT in conflict, it's possible to add an user context to them.
        if (_managedProjectsAbsolutePaths.contains(rootContextProject_p.getLocation())) {
          return false;
        }
      }
      return super.isInConflict(rootContextProject_p);
    }

    /**
     * @return <code>true</code> if a participation already exists for the user and the selected parent.
     */
    private boolean isParticipationAlreadyExisting() {
      // Precondition
      if (null == _selectedParent) {
        return false;
      }
      String userContextPath = RootContextsProject.getUserContextPath();
      String userContextFilepath = new File(_selectedParent._description.getLocationURI()).getAbsolutePath() + userContextPath;
      return new File(userContextFilepath).exists();
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractNewContextPage#postHandleCheckEvent(org.eclipse.jface.viewers.CheckStateChangedEvent)
     */
    @Override
    protected void postHandleCheckEvent(CheckStateChangedEvent event_p) {
      // Disable context name text if a participation already exists.
      _nameText.setEnabled(!isParticipationAlreadyExisting());
    }
  }
}
