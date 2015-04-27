/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.AbstractCommand.NonDirtying;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * Delete context.
 * @author t0076261
 */
public class DeleteContextCommand extends DeleteCommandWithConfirmation implements NonDirtying {
  /**
   * ContextToResource map used to delete resource after context deletion.
   */
  private Map<Context, Resource> _contextToResourceMap;
  /**
   * User choice to delete content on disk.
   */
  protected boolean _deleteContents;
  /**
   * Impacted resources.
   */
  private List<Resource> _impactedResources;

  /**
   * Constructor.
   * @param contexts_p list of contexts to delete.
   */
  public DeleteContextCommand(Collection<Context> contexts_p) {
    // Unfortunately a copy must be done to change the type of the list.
    super(new ArrayList<ModelElement>(contexts_p), null);
    // ContextToResource map initialization.
    _contextToResourceMap = new HashMap<Context, Resource>();
    for (Context context : contexts_p) {
      _contextToResourceMap.put(context, context.eResource());
    }
  }

  /**
   * @see org.eclipse.emf.common.command.CompoundCommand#canUndo()
   */
  @Override
  public boolean canUndo() {
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.DeleteCommandWithConfirmation#createCustomConfirmationArea(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite, java.util.List)
   */
  @Override
  protected Composite createCustomConfirmationArea(FormToolkit toolkit_p, Composite parent_p, List<Context> impactedContexts_p) {
    Composite parent = null;
    String buttonLabel;
    if (ProjectActivator.getInstance().isAdministrator()) {
      parent = super.createCustomConfirmationArea(toolkit_p, parent_p, impactedContexts_p);
      buttonLabel = Messages.DeleteContext_ConfirmationDialog_AdditionalWarning_Admin;
    } else {
      // Do not display impact in user mode.
      parent = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, false);
      buttonLabel = Messages.DeleteContext_ConfirmationDialog_AdditionalWarning_User;
    }
    Button deleteContentsButton = FormHelper.createButton(toolkit_p, parent_p, buttonLabel, SWT.CHECK);
    deleteContentsButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        _deleteContents = ((Button) e_p.getSource()).getSelection();
      }
    });
    _deleteContents = false;
    deleteContentsButton.setSelection(_deleteContents);
    return parent;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.DeleteCommand#doPrepare()
   */
  @Override
  protected void doPrepare() {
    // Add a remove command for each element to delete.
    for (Object contextToDelete : _elementsToDelete) {
      append(new RemoveCommand(ModelHandlerActivator.getDefault().getEditingDomain(), _contextToResourceMap.get(contextToDelete).getContents(),
          Collections.singletonList(contextToDelete)));
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.DeleteCommandWithConfirmation#getDialogTitle()
   */
  @Override
  protected String getDialogTitle() {
    if (ProjectActivator.getInstance().isAdministrator()) {
      return super.getDialogTitle();
    }
    return Messages.DeleteContext_Dialog_Title_UserMode;

  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.DeleteCommand#getEditionContext(java.lang.Object)
   */
  @Override
  public Context getEditionContext(Object object_p) {
    // Find edition context of the context to delete -> it's itself...
    return ModelUtil.getContext(object_p);
  }

  /**
   * Is Framework currently running in user-only mode ?
   * @return
   */
  protected boolean isInUserModeOnly() {
    return ProjectActivator.getInstance().getCommandLineArgsHandler().userModeOnly();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.DeleteCommand#postExecute()
   */
  @Override
  protected void postExecute() {
    boolean administrator = ProjectActivator.getInstance().isAdministrator();
    for (Object context : _elementsToDelete) {
      Resource contextResource = _contextToResourceMap.get(context);
      // Get resource URI.
      URI uri = contextResource.getURI();
      // Get associated root contexts project.
      RootContextsProject project = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(uri);
      // Remove resource from editing domain.
      ModelHandlerActivator.getDefault().getEditingDomain().removeResource(contextResource);
      // Remove file from workspace.
      if (uri.isPlatformResource()) {
        String relativePath = uri.toPlatformString(false);
        String projectName = new Path(relativePath).segment(0);
        if (administrator) {
          unloadStructure(projectName, _deleteContents, project);
        } else {
          // Delete in user mode.
          if (_deleteContents) {
            FileHelper.deleteFile(relativePath);
          }
          if (isInUserModeOnly() || !project.isAdministrator(ProjectActivator.getInstance().getCurrentUserId())) {
            // If OF is launched in user mode only, the project is always removed (it can't be seen as an administrator).
            // If user is not project administrator, the project is not visible as administrator, remove it.
            unloadStructure(projectName, false, project);
          }
          // If user is project administrator, the project is still visible as administrator, do not remove it.
        }
      }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.DeleteCommand#preExecute()
   */
  @Override
  protected void preExecute() {
    boolean administrator = ProjectActivator.getInstance().isAdministrator();
    _impactedResources = new ArrayList<Resource>(0);
    for (Object elementToDelete : _elementsToDelete) {
      Context context = (Context) elementToDelete;
      List<Context> hierarchy = ModelUtil.getHierarchy(context);
      // Add hierarchy to impacted resources.
      for (Context parent : hierarchy) {
        _impactedResources.add(parent.eResource());
      }
      List<Context> participations = Collections.emptyList();
      if (administrator) {
        participations = DataUtil.getParticipations(context);
      }
      // Add participations to impacted resources.
      for (Context participation : participations) {
        _impactedResources.add(participation.eResource());
      }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.DeleteCommand#preparePreConditions()
   */
  @Override
  protected boolean preparePreConditions() {
    // Selected contexts must be loaded (eResource not null).
    // Unloaded contexts are given to this command during multiple contexts deletions (every contexts are deleted in the model, then ContextsViewer deletes them
    // in the tree one by one in asyncExec, trying to preserve selection and then selecting unloaded contexts).
    if (_contextToResourceMap.values().contains(null)) {
      return false;
    }
    // Deletion of the current context or of a parent of the current user context is forbidden.
    ContextsEditingDomain contextsEditingDomain = ModelHandlerActivator.getDefault().getEditingDomain();
    Collection<Context> selectedContexts = EcoreUtil.getObjectsByType(_elementsToDelete, ContextsPackage.Literals.CONTEXT);
    if (contextsEditingDomain.containsCurrentContext(selectedContexts) || contextsEditingDomain.containsParentOfCurrentUserContext(selectedContexts)) {
      return false;
    }
    return true;
  }

  /**
   * Unload remaining structure for specified project.<br>
   * That is unload impacted resources, then delete project, optionally including contents (on file system).
   * @param projectName
   * @param impactedResources
   * @param deleteContents_p
   * @param project_p
   */
  protected void unloadStructure(String projectName_p, boolean deleteContents_p, RootContextsProject project_p) {
    // Remove all impacted resources.
    for (Resource resource : _impactedResources) {
      ModelHandlerActivator.getDefault().getEditingDomain().removeResource(resource);
    }
    // Remove contexts description resource.
    ProjectActivator.getInstance().getEditingDomain().getResourceSet().getResources().remove(project_p._contextsProject.eResource());
    // Delete project.
    ProjectHelper.deleteProject(projectName_p, deleteContents_p);
  }
}