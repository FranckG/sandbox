/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.AbstractCommand.NonDirtying;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.handler.data.RootElement;
import com.thalesgroup.orchestra.framework.project.CaseUnsensitiveResourceSetImpl;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.ProjectHandler;
import com.thalesgroup.orchestra.framework.project.ProjectHandler.IFileLocator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * Synchronize command.<br>
 * This command can not be undone.<br>
 * Does change the selected context hierarchy, if updates are available.
 * @author t0076261
 */
public class SynchronizeCommand extends AbstractCommand implements NonDirtying {
  /**
   * Selected list of contexts to synchronise
   **/
  protected List<Context> _contextList;

  /**
   * Sorted list of contexts to synchronise
   */
  protected List<Context> _synchronizedContextList;

  /**
   * Project description maps for contexts, indexed by context id
   */
  protected Map<String, RootContextsProject> _rootProjectMap;

  /**
   * Temporary directory where the copy took place.
   */
  private String _temporaryDirectory;

  /**
   * Enable/Disable user interfaction
   */
  protected boolean _allowUserInteraction;

  private ProjectHandler _projectHandler;
  private ModelHandlerActivator _modelHandlerActivator;
  private String _pluginId;

  /**
   * Get all contexts from workspace
   * @return
   */
  protected static List<Context> getWorkspaceContextList() {
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    RootElement rootElement = dataHandler.getAllContextsInWorkspace();
    return rootElement.getContexts();
  }

  /**
   * Constructor.
   * @param context_p Context to synchronize against its hierarchy. Can not be <code>null</code>.
   */
  public SynchronizeCommand(List<Context> contextList_p) {
    // By default, allow user interaction
    this(contextList_p, true);
  }

  /**
   * @param contextList_p
   * @param allowUserInteraction_p
   */
  public SynchronizeCommand(List<Context> contextList_p, boolean allowUserInteraction_p) {
    _contextList = contextList_p;
    _allowUserInteraction = allowUserInteraction_p;

    _projectHandler = ProjectActivator.getInstance().getProjectHandler();
    _modelHandlerActivator = ModelHandlerActivator.getDefault();
    _pluginId = _modelHandlerActivator.getPluginId();
  }

  /**
   * Default constructor
   */
  protected SynchronizeCommand() {
  }

  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
   */
  @Override
  public boolean canUndo() {
    // Not yet, be patient !
    return false;
  }

  /**
   * Display specified error message as an error dialog with details.
   * @param error_p
   */
  protected void displayErrorMessage(final IStatus error_p) {
    DisplayHelper.displayErrorDialog(Messages.SynchronizeCommand_ErrorDialog_Title, null, error_p);
  }

  /**
   * Display specified information message.
   * @param message_p
   */
  protected void displayInformationMessage(final String message_p) {
    AbstractRunnableWithDisplay displayRunnable = new AbstractRunnableWithDisplay() {
      public void run() {
        Display display = getDisplay();
        MessageDialog dialog =
            new MessageDialog(display.getActiveShell(), Messages.SynchronizeCommand_Dialog_Title, null, message_p, MessageDialog.INFORMATION,
                new String[] { IDialogConstants.OK_LABEL }, IDialogConstants.OK_ID);
        dialog.open();
      }
    };
    DisplayHelper.displayRunnable(displayRunnable, false);
  }

  /**
   * Display question message, with OK and CANCEL buttons available.
   * @param message_p
   * @return <code>true</code> if OK was pressed, <code>false</code> otherwise (CANCEL or CLOSED).
   */
  protected boolean displayQuestionMessage(final String message_p) {
    final boolean[] result = new boolean[] { false };
    AbstractRunnableWithDisplay displayRunnable = new AbstractRunnableWithDisplay() {
      public void run() {
        MessageDialog dialog =
            new MessageDialog(getDisplay().getActiveShell(), Messages.SynchronizeCommand_Dialog_Title, null, message_p, MessageDialog.CONFIRM,
                new String[] { IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL }, IDialogConstants.OK_ID);
        result[0] = (IDialogConstants.OK_ID == dialog.open());
      }
    };
    DisplayHelper.displayRunnable(displayRunnable, false);
    return result[0];
  }

  /**
   * Synchronise a context
   * @param context_p Context
   * @return Error status
   */
  private IStatus synchronize(Context context_p) {
    // Go for it.
    final RootContextsProject rootProject = _rootProjectMap.get(context_p.getId());
    // Save context URI, for reloading purposes.
    URI contextURI = context_p.eResource().getURI();
    // First of all, make sure parent project still exists.
    String rawParentProjectAbsoluteLocation = rootProject._contextsProject.getParentProject();
    // Nothing to synchronize.
    if (null == rawParentProjectAbsoluteLocation) {
      IStatus errorStatus = new Status(IStatus.INFO, _pluginId, Messages.SynchronizeCommand_Message_NoSyncNeeded);
      if (_allowUserInteraction) {
        displayInformationMessage(Messages.SynchronizeCommand_Message_NoSyncNeeded);
      }
      return errorStatus;
    }
    // No longer exists.
    // Perform variable references substitution.
    String substitutedParentProjectAbsoluteLocation = DataUtil.getSubstitutedValue(rawParentProjectAbsoluteLocation, context_p);
    File parentProjectDirectory = new File(substitutedParentProjectAbsoluteLocation);
    if (!(parentProjectDirectory.exists() || parentProjectDirectory.isDirectory())) {
      IStatus errorStatus =
          new Status(IStatus.ERROR, _pluginId, MessageFormat.format(Messages.SynchronizeCommand_Message_ParentMissing,
              substitutedParentProjectAbsoluteLocation, rawParentProjectAbsoluteLocation));
      if (_allowUserInteraction) {
        displayErrorMessage(errorStatus);
      }
      // Stop here.
      return errorStatus;
    }
    // Get parent project description.
    RootContextsProject parentProject = _projectHandler.getContextFromLocation(substitutedParentProjectAbsoluteLocation, new CaseUnsensitiveResourceSetImpl());
    if (null == parentProject) {
      IStatus errorStatus =
          new Status(IStatus.ERROR, _pluginId, MessageFormat.format(Messages.SynchronizeCommand_Message_NoProjectAt, substitutedParentProjectAbsoluteLocation,
              rawParentProjectAbsoluteLocation));
      if (_allowUserInteraction) {
        displayErrorMessage(errorStatus);
      }
      // Stop here.
      return errorStatus;
    }
    // Make sure parent hierarchy is compatible with current context one.
    String parentContextId = context_p.getSuperContext().getId();
    if (!parentProject.getAdministratedContext().getId().equals(parentContextId)) {
      IStatus errorStatus = new Status(IStatus.ERROR, _pluginId, Messages.SynchronizeCommand_Message_ParentCorrupted);
      if (_allowUserInteraction) {
        displayErrorMessage(errorStatus);
      }
      // Stop here.
      return errorStatus;
    }
    //
    // Ensure that files to be overwritten in the synchronize operation are not read-only.
    //
    List<IFile> filesToBeOverwritten = new ArrayList<IFile>();
    // Add parent contexts files.
    List<ContextReference> contextReferences = parentProject._contextsProject.getContextReferences();
    for (ContextReference cr : contextReferences) {
      filesToBeOverwritten.add(rootProject._project.getFile(new Path(URI.createURI(cr.getUri()).lastSegment())));
    }
    // Add "description.contextsproject" file.
    URI contextsProjectDescriptionURI = ProjectActivator.getInstance().getProjectHandler().getContextsProjectDescriptionUri(rootProject._project);
    filesToBeOverwritten.add(rootProject._project.getFile(new Path(contextsProjectDescriptionURI.lastSegment())));
    // Check files read-only state, if some files are read-only, ask the user if he wants to set them writable.
    IStatus validateEditResult =
        ResourcesPlugin.getWorkspace().validateEdit(filesToBeOverwritten.toArray(new IFile[0]), PlatformUI.getWorkbench().getDisplay().getActiveShell());
    if (!validateEditResult.isOK()) {
      // Some files are still read-only -> can't perform synchronization.
      MultiStatus errorStatus = new MultiStatus(_pluginId, IStatus.ERROR, Messages.SynchronizeCommand_Message_CantSynchronizeWithReadOnlyFiles, null);
      errorStatus.add(validateEditResult);
      if (_allowUserInteraction) {
        displayErrorMessage(errorStatus);
      }
      return errorStatus;
    }

    // Make a copy.
    _temporaryDirectory = System.getenv("APPDATA") + "\\Orchestra\\OrchestraFramework\\temp\\projects\\"; //$NON-NLS-1$ //$NON-NLS-2$
    IStatus copySuccessful = ProjectHelper.copyProjectTo(rootProject._project, _temporaryDirectory, true, null);
    if (!copySuccessful.isOK()) {
      MultiStatus errorStatus = new MultiStatus(_pluginId, IStatus.ERROR, Messages.SynchronizeCommand_Message_CopyFailed, null);
      errorStatus.add(copySuccessful);
      if (_allowUserInteraction) {
        displayErrorMessage(errorStatus);
      }
      // Stop here, for the moment.
      return errorStatus;
    }
    // Unload models from editing domains. 1) Contexts 2) Description.
    // Unload contexts.
    List<Context> contextsToUnload = ModelUtil.getHierarchy(context_p);
    contextsToUnload.addAll(DataUtil.getParticipations(context_p));
    // Add self context.
    contextsToUnload.add(context_p);
    // Do unload contexts.
    for (Context context : contextsToUnload) {
      _modelHandlerActivator.getEditingDomain().removeResource(context.eResource());
    }
    // Copy hierarchy.
    MultiStatus status = new MultiStatus(_pluginId, IStatus.OK, "", null);
    final String projectLocation = rootProject._project.getLocation().removeLastSegments(1).toString();
    try {
      _projectHandler.copyHierarchy(parentProject, new IFileLocator() {
        public String getContextAbsolutePath(ContextReference contextReference_p) {
          URI copyContextUri =
              FileHelper.getFileFullUri(rootProject._description.getName() + IPath.SEPARATOR + new Path(contextReference_p.getUri()).lastSegment());
          return FileHelper.getAbsolutePath(projectLocation, copyContextUri);
        }
      });
    } catch (Exception exception_p) {
      MultiStatus errorStatus = new MultiStatus(_pluginId, IStatus.ERROR, Messages.SynchronizeCommand_Message_SyncFailed, null);
      errorStatus.add(new Status(IStatus.ERROR, _pluginId, null, exception_p));
      if (_allowUserInteraction) {
        displayErrorMessage(errorStatus);
      }
      status.add(errorStatus);
      // Do not stop here, restore hierarchy.
      IStatus restorationStatus = restoreHierarchy(rootProject);
      if (!restorationStatus.isOK()) {
        errorStatus = new MultiStatus(_pluginId, IStatus.ERROR, Messages.SynchronizeCommand_Message_RestorationFailed, null);
        errorStatus.add(restorationStatus);
        if (_allowUserInteraction) {
          displayErrorMessage(errorStatus);
        }
        status.add(errorStatus);
      }
      // TODO Guillaume
      // Should stop here ??
      // The context can no longer be loaded.
    }
    // Reload context.
    DataHandler dataHandler = _modelHandlerActivator.getDataHandler();
    Context reloadedContext = dataHandler.getContext(contextURI);
    // Update description, if needed.
    try {
      dataHandler.populateContextsDescription(rootProject._project, reloadedContext);
    } catch (IOException exception_p) {
      IStatus errorStatus = new Status(IStatus.ERROR, _pluginId, Messages.SynchronizeCommand_ErrorMessage_CouldNotPopulateContextDescriptionFile, exception_p);
      if (_allowUserInteraction) {
        displayErrorMessage(errorStatus);
      }
      status.add(errorStatus);
    }
    // Notify synchronization.
    reloadedContext.eResource().contextSynchronized();

    return status;
  }

  /**
   * @return
   */
  public IStatus batchExecute() {
    // Try and save contexts.
    try {
      ModelHandlerActivator.getDefault().getEditingDomain().notifyHandlerOnResourceSaved(false);
      if (!PlatformUI.getWorkbench().saveAllEditors(false)) {
        IStatus errorStatus =
            new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), Messages.SynchronizeCommand_SaveError_OperationCancelled);
        if (_allowUserInteraction) {
          displayErrorMessage(errorStatus);
        }
        return errorStatus;
      }
    } finally {
      ModelHandlerActivator.getDefault().getEditingDomain().notifyHandlerOnResourceSaved(true);
    }

    MultiStatus errorStatus = new MultiStatus(_pluginId, IStatus.OK, "", null);
    // Synchronise contexts one after one
    for (Context context : _synchronizedContextList) {
      IStatus status = synchronize(context);
      errorStatus.add(status);
    }

    return errorStatus;
  }

  /**
   * @see org.eclipse.emf.common.command.Command#execute()
   */
  public void execute() {
    // Make sure synchronisation can only happen one time at once
    synchronized (_modelHandlerActivator) {
      if (!_synchronizedContextList.isEmpty()) {
        // Ask for confirmation first.
        if (_allowUserInteraction) {
          if (!displayQuestionMessage(Messages.SynchronizeCommand_Message_Start)) {
            return;
          }
        }
        batchExecute();
      }
    }
  }

  /**
   * Get parent context from context list
   * @param context_p Context
   * @return Parent context, <code>null</code> if parent context is default context
   */
  private Context getParentContext(Context context_p, List<Context> contextList_p) {
    // Get parent from context project
    Context parentFromProject = context_p.getSuperContext();

    // Parent context is default context
    if (parentFromProject.eResource().isDefault()) {
      return null;
    }

    // Get context from the context list matching the same context Id
    Context parent = getContextById(parentFromProject.getId(), contextList_p);

    return parent;
  }

  /**
   * Check if a context list contains a given context
   * @param context_p
   * @param contextList_p
   * @return
   */
  private boolean containsContext(Context context_p, List<Context> contextList_p) {
    for (Context context : contextList_p) {
      if (context.getId().equals(context_p.getId())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get a context from a context list, by context Id
   * @param contextId_p
   * @param contextList_p
   * @return Context if found, <code>null</code>Q otherwise
   */
  private Context getContextById(String contextId_p, List<Context> contextList_p) {
    for (Context context : contextList_p) {
      if (context.getId().equals(contextId_p)) {
        return context;
      }
    }
    return null;
  }

  /**
   * Recurse over parent contexts and append them to synchronised contexts list
   * @param context_p Context to recurse into
   * @param synchronizedContexts_p Appended contexts Id list
   */
  private void appendSynchronizedContext(Context context_p, List<Context> synchronizedContexts_p) {
    if (!containsContext(context_p, synchronizedContexts_p)) {
      Context parent = getParentContext(context_p, _contextList);

      // If parent is not default context and belongs to the context list
      if (null != parent) {
        appendSynchronizedContext(parent, synchronizedContexts_p);
        synchronizedContexts_p.add(context_p);
      }
    }
  }

  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
   */
  @Override
  public boolean prepare() {
    // Precondition.
    if (null == _contextList) {
      return false;
    }

    ProjectActivator projectActivator = ProjectActivator.getInstance();
    // Make sure user is currently in administration mode.
    if (!projectActivator.isAdministrator()) {
      return false;
    }

    // Build sorted list of contexts to synchronise
    _synchronizedContextList = new ArrayList<Context>();
    if (1 == _contextList.size()) {
      // Synchronize single context
      _synchronizedContextList.add(_contextList.get(0));
    } else {
      // Synchronize all
      for (Context context : _contextList) {
        appendSynchronizedContext(context, _synchronizedContextList);
      }
    }

    _rootProjectMap = new HashMap<String, RootContextsProject>();
    for (Context context : _synchronizedContextList) {
      RootContextsProject rootProject = projectActivator.getProjectHandler().getProjectFromContextUri(context.eResource().getURI());
      // Can not access project data, stop here.
      if (null == rootProject) {
        return false;
      }
      // Make sure user is administrator for this context.
      if (!rootProject.isAdministrator(projectActivator.getCurrentUserId())) {
        return false;
      }

      _rootProjectMap.put(context.getId(), rootProject);
    }
    return true;
  }

  /**
   * @see org.eclipse.emf.common.command.Command#redo()
   */
  public void redo() {
    // Nothing to do yet.
  }

  /**
   * Restore context hierarchy from backup copy.
   * @return An {@link IStatus} embedding success or failure. In case of failure, the message is describing the error more precisely.
   */
  protected IStatus restoreHierarchy(final RootContextsProject rootProject_p) {
    // Get backup copy.
    String directory = _temporaryDirectory + rootProject_p._project.getName();
    List<RootContextsProject> contextsProjects = _projectHandler.getValidContextsFromLocation(directory);
    // There is no backup to be found.
    if (contextsProjects.isEmpty()) {
      // Stop here.
      return new Status(IStatus.ERROR, _pluginId, MessageFormat.format(Messages.SynchronizeCommand_Message_NoBackupAt, directory));
    }
    // Get backup project.
    RootContextsProject backupProject = contextsProjects.get(0);
    // Copy hierarchy.
    final String projectLocation = rootProject_p._project.getLocation().removeLastSegments(1).toString();
    try {
      _projectHandler.copyHierarchy(backupProject, new IFileLocator() {
        public String getContextAbsolutePath(ContextReference contextReference_p) {
          URI copyContextUri =
              FileHelper.getFileFullUri(rootProject_p._description.getName() + IPath.SEPARATOR + new Path(contextReference_p.getUri()).lastSegment());
          return FileHelper.getAbsolutePath(projectLocation, copyContextUri);
        }
      });
    } catch (Exception exception_p) {
      return new Status(IStatus.ERROR, _pluginId, null, exception_p);
    }
    return Status.OK_STATUS;
  }
}