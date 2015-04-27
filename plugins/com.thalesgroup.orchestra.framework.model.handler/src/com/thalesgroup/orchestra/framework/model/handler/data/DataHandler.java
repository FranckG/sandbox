/**
 * Copyright (c) THALES, 2009. All rights reserved. 
 */
package com.thalesgroup.orchestra.framework.model.handler.data;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.ui.dialogs.DiagnosticDialog;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectFactory;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl.AdditionalSaveOperation;
import com.thalesgroup.orchestra.framework.model.handler.IValidationHandler;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.InitializeCurrentVersionsCommand;
import com.thalesgroup.orchestra.framework.model.handler.helper.NotificationHelper;
import com.thalesgroup.orchestra.framework.model.handler.internal.command.SwitchContextHelper;
import com.thalesgroup.orchestra.framework.model.handler.pref.IPreferencesConstants;
import com.thalesgroup.orchestra.framework.project.CaseUnsensitiveURIHandlerImpl;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.ProjectFactory;
import com.thalesgroup.orchestra.framework.project.ProjectHandler;
import com.thalesgroup.orchestra.framework.project.ProjectHandler.IFileLocator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.project.nature.ProjectNature;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * Orchestra framework model entry point.
 * @author t0076261
 */
public class DataHandler {
  /**
   * Current context preference key.
   */
  private static final String CURRENT_CONTEXT_PREFERENCE_KEY = "currentContext"; //$NON-NLS-1$
  /**
   * No script activation constant.
   */
  public static final int SCRIPTS_NONE = 0;
  /**
   * Context post-load script activation constant.
   */
  public static final int SCRIPTS_POSTLOAD = 1 << 3;
  /**
   * Context pre-load script activation constant.
   */
  public static final int SCRIPTS_PRELOAD = 1 << 2;
  /**
   * Context pre-unload script activation constant.
   */
  public static final int SCRIPTS_PREUNLOAD = 1 << 1;
  /**
   * Context currently in use by the framework and the external applications.
   */
  private Context _currentContext;
  /**
   * Listeners of the current context changes.
   */
  private List<ICurrentContextChangeListener> _currentContextChangeListeners;
  /**
   * Current context model bag.
   */
  private ContextsResourceSet _resourceSet;
  /**
   * Switch context helper.
   */
  protected SwitchContextHelper _switchContextHelper;

  /**
   * Constructor.
   */
  public DataHandler() {
    // Create switch helper.
    _switchContextHelper = new SwitchContextHelper();
    // Initialize current context resource set.
    _resourceSet = new ContextsResourceSet();
    // Add an URI handler able to correct URIs case regarding real files path.
    _resourceSet.getURIConverter().getURIHandlers().add(0, new CaseUnsensitiveURIHandlerImpl());
    // Initialize context change listeners collection.
    _currentContextChangeListeners = new ArrayList<ICurrentContextChangeListener>(0);
    // Register as listener to model changes for impacts on projects descriptions.
    ModelHandlerActivator.getDefault().getEditingDomain().getNotificationsListener().addAdapter(new HandlerAdapter());
  }

  /**
   * Add the supplied listener to the list of registered listeners.<br />
   * Register to this listener to be notified after the current context change has been applied in the Orchestra Framework.
   * @param listener_p
   */
  public void addCurrentContextChangeListener(ICurrentContextChangeListener listener_p) {
    _currentContextChangeListeners.add(listener_p);
  }

  /**
   * Add the supplied listener to the list of registered listeners.<br />
   * Register to this listener to be notified after the current context preference has changed, before the current context change has been applied in the
   * Orchestra Framework.
   * @param listener_p
   */
  public void addCurrentContextPreferenceChangeListener(IPreferenceChangeListener listener_p) {
    IEclipsePreferences node = new InstanceScope().getNode(ModelHandlerActivator.getDefault().getPluginId());
    node.addPreferenceChangeListener(listener_p);
  }

  /**
   * Specified context has been saved, handle it.<br>
   * If the saved context is :
   * <ul>
   * <li>the current context, re-apply it (by switching to it again),</li>
   * <li>an admin context parent of the current user context, re-apply the user context.</li>
   * </ul>
   * @param context_p
   */
  public void contextSaved(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return;
    }
    // Make sure platform is not closing.
    final boolean[] isClosing = new boolean[] { false };
    DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        isClosing[0] = PlatformUI.getWorkbench().isClosing();
      }
    }, false);
    // Do nothing in such a case.
    if (isClosing[0]) {
      return;
    }
    // Current context is saved ? -> Set it as current again.
    if (isCurrentContext(context_p)) {
      setCurrentContext(context_p);
      return;
    }
    // Saved occurred in admin mode. Is saved admin context parent of the CURRENT user context ?
    if (ProjectActivator.getInstance().isAdministrator()) {
      Context userContext = getUserContext(context_p);
      if (null != userContext && isCurrentContext(userContext)) {
        // Set it as current again.
        setCurrentContext(userContext);
      }
    }
  }

  /**
   * Calls {@link DataHandler#createNewContextCopy(String, String, Context, ResourceSet)} with the editing domain resource set.
   * @param newContextCopyName_p
   * @param projectAbsoluteLocation_p
   * @param sourceContext_p
   * @return
   */
  public IStatus createNewContextCopy(String newContextCopyName_p, String projectAbsoluteLocation_p, Context sourceContext_p) {
    return createNewContextCopy(newContextCopyName_p, projectAbsoluteLocation_p, sourceContext_p, ModelHandlerActivator.getDefault().getEditingDomain()
        .getResourceSet());
  }

  /**
   * Create a copy of the given source context.
   * @param newContextCopyName_p name of the project and the context copy.
   * @param projectAbsoluteLocation_p location of the new project. Can be <code>null</code> if the project should be created directly in the workspace.
   * @param sourceContext_p the context to copy.
   * @param resourceSet_p The owner of the newly created context. Can not be <code>null</code> !
   * @return An <code>ERROR</code> status if parameters are invalid, directory already exists or something wrong happened. In this situation, the already
   *         created project is entirely deleted, which might lead to listeners get events from project creation then deletion. An <code>OK</code> status if it
   *         was successfully loaded or created.
   */
  public IStatus createNewContextCopy(String newContextCopyName_p, String projectAbsoluteLocation_p, Context sourceContext_p, ResourceSet resourceSet_p) {
    //
    // Preconditions.
    //
    if (null == newContextCopyName_p) {
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), Messages.DataHandler_Create_NoName_Error);
    }
    if (null == resourceSet_p) {
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), Messages.DataHandler_Create_NoResource_Error);
    }
    // Check project doesn't already exist on the FileSystem.
    final String projectName = ProjectHelper.generateContextProjectName(newContextCopyName_p);
    // if no project location, find in working directory, i.e. workspace.
    final String projectDestDirectory = (projectAbsoluteLocation_p != null) ? projectAbsoluteLocation_p : Platform.getLocation().toString();
    if (new File(projectDestDirectory, projectName).exists()) {
      // A directory already exists -> stop here.
      return new Status(IStatus.WARNING, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
          Messages.DataHandler_Create_DirectoryAlreadyExists_Error, projectName));
    }

    IProject destinationProject = null;
    try {
      // Create destination project.
      destinationProject = ProjectHelper.createAnEmptyContextsProject(projectName, projectAbsoluteLocation_p, ProjectNature.CONTEXT_PROJECT_NATURE_ID);
      final String destinationProjectLocation =
          projectAbsoluteLocation_p != null ? projectAbsoluteLocation_p : destinationProject.getLocation().removeLastSegments(1).toString();

      // Copy source context .contexts files.
      final URI sourceContextURI = sourceContext_p.eResource().getURI();
      RootContextsProject sourceRootContextsProject = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(sourceContextURI);
      final String contextCopyFileName = FileHelper.generateContextFileName(newContextCopyName_p);
      ProjectActivator.getInstance().getProjectHandler().copyHierarchy(sourceRootContextsProject, new IFileLocator() {
        public String getContextAbsolutePath(ContextReference contextReference_p) {
          URI copyContextUri = null;
          if (contextReference_p.getUri().equals(sourceContextURI.toString())) {
            // Generate destination location for copied context.
            // => give the name of the new context to the .context file.
            copyContextUri = FileHelper.getFileFullUri(projectName + IPath.SEPARATOR + contextCopyFileName);
          } else {
            // Generates destination location for "super" contexts (i.e. for the hierarchy).
            // => .contexts files are copied to the directory of the new context.
            copyContextUri = FileHelper.getFileFullUri(projectName + IPath.SEPARATOR + new Path(contextReference_p.getUri()).lastSegment());
          }
          return FileHelper.getAbsolutePath(destinationProjectLocation, copyContextUri);
        }
      });
      // Refresh project.
      ProjectHelper.refreshProject(destinationProject, null);

      // Load context from copied files (in a new ResourceSet, to avoid notifications).
      String contextCopyPath = destinationProject.getFullPath().append(contextCopyFileName).toString();
      Context contextCopy = getContext(FileHelper.getFileFullUri(contextCopyPath), new ResourceSetImpl());
      contextCopy.setName(newContextCopyName_p);
      // Clean IDs.
      contextCopy.setId(null);
      TreeIterator<EObject> contextElementsIt = contextCopy.eAllContents();
      while (contextElementsIt.hasNext()) {
        EObject element = contextElementsIt.next();
        if (element instanceof ModelElement) {
          ((ModelElement) element).setId(null);
        }
      }
      // Save context copy (IDs will be regenerated).
      contextCopy.eResource().save(null);
      contextCopy.eResource().unload();

      // Create the "description.contextsproject" content from the copied context.
      ContextsProject contextDescription = (ContextsProject) EcoreUtil.copy(sourceRootContextsProject._contextsProject);
      URI resourceUri = ProjectActivator.getInstance().getProjectHandler().getContextsProjectDescriptionUri(destinationProject);
      Resource contextDescriptionResource = ProjectActivator.getInstance().getEditingDomain().getResourceSet().createResource(resourceUri);
      contextDescriptionResource.getContents().add(contextDescription);

      // Add hierarchy to description.
      URI contextCopyUri = FileHelper.getFileFullUri(contextCopyPath);
      populateContextsDescription(destinationProject, contextCopy, contextCopyUri);

      // Reload resource and notify the resource change
      Resource resource = resourceSet_p.getResource(contextCopyUri, true);
      NotificationHelper.fireNonDirtyingNotification(Notification.ADD, resource, null, null, resource.getContents().get(0));

      ProjectHelper.refreshProject(destinationProject, null);
    } catch (Exception exception_p) {
      if (null != destinationProject) {
        ProjectHelper.deleteProject(destinationProject.getName(), true);
      }
      String unableToCreateErrorMessage =
          MessageFormat.format("Unable to create new context {0} to copy {1} !", newContextCopyName_p, sourceContext_p.getName()); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(unableToCreateErrorMessage, IStatus.ERROR, exception_p);
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), unableToCreateErrorMessage);
    }
    return Status.OK_STATUS;
  }

  /**
   * Create new context structure, including hosting project, for specified parameters.<br>
   * Strictly equivalent to calling {@link #createNewContextStructure(String, String, RootContextsProject, ResourceSet)} with {@link ResourceSet} set to
   * {@link ContextsEditingDomain}'s one.
   * @param newContextName_p The new context name. Must not be <code>null</code>.
   * @param projectAbsoluteLocation_p The project hosting location. The project is contained within its own computed directory under specified location. Can be
   *          <code>null</code> if the project should be created in the workspace directly.
   * @param parentProject_p The parent project to initialize from. Basically, this means being a child of the parent project context (one active context per
   *          project at administrator level). Can be <code>null</code> if context is to inherit from default context directly.
   * @return An <code>ERROR</code> status if parameters are invalid, directory already exists or something wrong happened. In this situation, the already
   *         created project is entirely deleted, which might lead to listeners get events from project creation then deletion. An <code>OK</code> status if it
   *         was successfully loaded or created.
   */
  public IStatus createNewContextStructure(String newContextName_p, String projectAbsoluteLocation_p, RootContextsProject parentProject_p) {
    return createNewContextStructure(newContextName_p, projectAbsoluteLocation_p, parentProject_p, ModelHandlerActivator.getDefault().getEditingDomain()
        .getResourceSet(), true);
  }

  /**
   * Create new context structure, including hosting project, for specified parameters.
   * @param newContextName_p The new context name. Must not be <code>null</code>.
   * @param projectAbsoluteLocation_p The project hosting location. The project is contained within its own computed directory under specified location. Can be
   *          <code>null</code> if the project should be created in the workspace directly.
   * @param parentProject_p The parent project to initialize from. Basically, this means being a child of the parent project context (one active context per
   *          project at administrator level). Can be <code>null</code> if context is to inherit from default context directly.
   * @param resourceSet_p The owner of the newly created context. Can not be <code>null</code> !
   * @param shouldAddCurrentUserAsAdministrator_p <code>true</code> to add current user as the administrator of new created context structure,
   *          <code>false</code> to leave administrators list empty.
   * @return An <code>ERROR</code> status if parameters are invalid, directory already exists or something wrong happened. In this situation, the already
   *         created project is entirely deleted, which might lead to listeners get events from project creation then deletion. An <code>OK</code> status if it
   *         was successfully loaded or created.
   */
  public IStatus createNewContextStructure(String newContextName_p, String projectAbsoluteLocation_p, RootContextsProject parentProject_p,
      ResourceSet resourceSet_p, boolean shouldAddCurrentUserAsAdministrator_p) {
    // Precondition.
    if (null == newContextName_p) {
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), Messages.DataHandler_Create_NoName_Error);
    }
    if (null == resourceSet_p) {
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), Messages.DataHandler_Create_NoResource_Error);
    }
    final String projectName = ProjectHelper.generateContextProjectName(newContextName_p);
    final String projectLocation[] = new String[] { projectAbsoluteLocation_p };
    // Check project existence (if no project location, find in working directory, i.e. workspace).
    final String projectDestDirectory = (projectLocation[0] != null) ? projectLocation[0] : Platform.getLocation().toString();
    final String projectContentDestination = projectDestDirectory + IPath.SEPARATOR + projectName;
    final File projectContentDestinationFile = new File(projectContentDestination);

    if (projectContentDestinationFile.exists()) {
      return new Status(IStatus.WARNING, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
          Messages.DataHandler_Create_DirectoryAlreadyExists_Error, projectName));
    }
    URI parentProjectUri = null;
    if (null != parentProject_p) {
      String path = parentProject_p.getLocation();
      parentProjectUri = URI.createFileURI(path);
    }
    IProject project = null;
    try {
      // Create project.
      project = ProjectFactory.createNewProject(projectName, projectLocation[0], parentProjectUri, shouldAddCurrentUserAsAdministrator_p);
      // Copy hierarchy from parent project to new one.
      if (null != parentProject_p) {
        if (null == projectLocation[0]) {
          projectLocation[0] = project.getLocation().removeLastSegments(1).toString();
        }
        // Copy hierarchy.
        ProjectActivator.getInstance().getProjectHandler().copyHierarchy(parentProject_p, new IFileLocator() {
          public String getContextAbsolutePath(ContextReference contextReference_p) {
            // Generates destination location for "super" contexts (ie for the hierarchy).
            // => .contexts files are copied to the directory of the new context.
            URI copyContextUri = FileHelper.getFileFullUri(projectName + IPath.SEPARATOR + new Path(contextReference_p.getUri()).lastSegment());
            return FileHelper.getAbsolutePath(projectLocation[0], copyContextUri);
          }
        });
        // Refresh project.
        ProjectHelper.refreshProject(project, null);
      }
      // Load parent context.
      Context parentContext = getDefaultContext();
      if (null != parentProject_p) {
        // Get parent context URI.
        URI parentUri = URI.createURI(parentProject_p.getAdministratedContext().getUri());
        // Remove URI sub-type, and add project name as leading segment.
        if (null != parentUri) {
          IPath parentPath = new Path(parentUri.toPlatformString(true));
          parentPath = new Path(project.getName()).append(parentPath.removeFirstSegments(1));
          // Load from copy that has been done earlier.
          parentContext = getContext(parentPath.toString(), resourceSet_p);
        }
      }
      // Create resulting context.
      Context context = ContextsFactory.eINSTANCE.createContext();
      context.setName(newContextName_p);
      context.setSuperContext(parentContext);
      String pathName = project.getFullPath().append(FileHelper.generateContextFileName(newContextName_p)).toString();
      URI uri = FileHelper.getFileFullUri(pathName);
      Resource resource = resourceSet_p.createResource(uri);
      // Add hierarchy to description.
      populateContextsDescription(project, context, uri);
      // Save context.
      // This is very important that it be the last step !
      // Otherwise sent events won't be handled correctly.
      resource.getContents().add(context);
      // Add versions automatically, if context inherits from Default One.
      if ((null != parentContext.eResource()) && parentContext.eResource().isDefault()) {
        InitializeCurrentVersionsCommand versionsCommand = new InitializeCurrentVersionsCommand(Collections.singletonList(context));
        if (versionsCommand.canExecute()) {
          versionsCommand.execute();
        }
      }
      // Save resource.
      resource.save(null);
      // And refresh project.
      ProjectHelper.refreshProject(project, null);
    } catch (Exception exception_p) {
      if (null != project) {
        ProjectHelper.deleteProject(project.getName(), true);
      }
      String unableToCreateErrorMessage = MessageFormat.format("Unable to create new context {0} !", newContextName_p); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(unableToCreateErrorMessage, IStatus.ERROR, exception_p);
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), unableToCreateErrorMessage);
    }
    return Status.OK_STATUS;
  }

  /**
   * Set current context.
   * @param context_p the current context to set.
   * @param notify_p true to notify listeners, false otherwise.
   * @param scriptsActivation_p A combination of {@value #SCRIPTS_PREUNLOAD}, {@value #SCRIPTS_PRELOAD}, {@value #SCRIPTS_POSTLOAD} values enabling the various
   *          scripts execution. {@value #SCRIPTS_NONE} to disable all scripts.
   * @param allowUserInteractions_p <code>true</code> to allow user interactions while switching and display dialogs, <code>false</code> not to present user
   *          interactions.<br>
   *          In both cases, the code is run synchronously, but in the second one (ie <code>false</code>) an invalid context is still applied, whereas this is
   *          not possible from UI behavior.
   * @param previousContext_p The context to force as previous one.<br>
   *          If none is provided (ie value is <code>null</code>) current one will be used as previous one.
   * @param progressMonitor_p
   */
  public void doSetCurrentContext(Context context_p, boolean notify_p, int scriptsActivation_p, boolean allowUserInteractions_p, Context previousContext_p,
      IProgressMonitor progressMonitor_p) {
    // Handle progression.
    SubMonitor mainMonitor = null;
    if (null != progressMonitor_p) {
      mainMonitor = SubMonitor.convert(progressMonitor_p, 100);
    }
    // Default context flag.
    ContextsResourceImpl resource = context_p.eResource();
    boolean isDefaultContext = (null != resource) && resource.isDefault();
    // Confirm switch, if needed.
    {
      // Progression handling.
      if (null != mainMonitor) {
        SubMonitor confirmSwitchMonitor = mainMonitor.newChild(5);
        confirmSwitchMonitor.setTaskName(Messages.DataHandler_SetContext_ProgressTask_CheckRegisteredApplications);
      }
      // Precondition.
      if (allowUserInteractions_p && !_switchContextHelper.confirmSwitch(context_p.getName())) {
        return;
      }
    }
    MultiStatus result = new MultiStatus(ModelHandlerActivator.getDefault().getPluginId(), 0, "Set current context resulting status", null); //$NON-NLS-1$
    IStatus errorStatus = null;
    Context previousContext = null;
    Context currentContext = null;
    try {
      // Keep curentContext -> useful if a reload is asked.
      {
        try {
          previousContext = (null != previousContext_p) ? previousContext_p : getCurrentContext();
        } catch (InvalidContextException ice_p) {
          previousContext = null;
        }
      }
      // Execute pre-unload script.
      {
        errorStatus = testAndExecuteScript(SCRIPTS_PREUNLOAD, scriptsActivation_p, (null != mainMonitor) ? mainMonitor.newChild(5) : null);
        if ((null != errorStatus) && !errorStatus.isOK()) {
          result.add(errorStatus);
        }
      }
      // Notify pre-change.
      {
        // Progression handling.
        SubMonitor preContextChangeMonitor = null;
        if (null != mainMonitor) {
          preContextChangeMonitor = mainMonitor.newChild(5);
          preContextChangeMonitor.setTaskName(Messages.DataHandler_SetContext_ProgressTask_PreContextChangeListeners);
        }
        if (notify_p) {
          // Create a sub-monitor that gives each listener a one slot progress.
          SubMonitor realMonitor = null;
          if (null != preContextChangeMonitor) {
            realMonitor = preContextChangeMonitor.newChild(_currentContextChangeListeners.size());
          }
          for (ICurrentContextChangeListener listeners : Collections.unmodifiableCollection(_currentContextChangeListeners)) {
            listeners.preContextChange(context_p, (null != realMonitor) ? realMonitor.newChild(1) : null, allowUserInteractions_p);
          }
        }
      }
      
      // Set new context.
      String errorMessage  = Messages.DataHandler_ContextSettingError_Title;
      synchronized (this) {
    	saveCurrentContextPreference(context_p);
    	         
        // Load new context to current context resource set.
        _resourceSet.clean();
        try {
          _currentContext = getContext(resource.getURI(), _resourceSet);
          currentContext = _currentContext;
        } catch (InvalidContextException exception_p) {
          currentContext = null;
          errorMessage = exception_p.getMessage();
        }
      }
      
      if (null == currentContext) {
       	//MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Error", "message"); 
    	final Status status = new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), errorMessage);
    	boolean proceed = handleCurrentContextSetErrors(context_p, notify_p, scriptsActivation_p, allowUserInteractions_p, status, previousContext);
        // Stop here.
        if (!proceed) {
          return;
        }
      }

      // Execute pre-load script.
      {
        errorStatus = testAndExecuteScript(SCRIPTS_PRELOAD, scriptsActivation_p, (null != mainMonitor) ? mainMonitor.newChild(5) : null);
        if ((null != errorStatus) && !errorStatus.isOK()) {
          result.add(errorStatus);
          boolean proceed = handleCurrentContextSetErrors(getCurrentContext(), notify_p, scriptsActivation_p, allowUserInteractions_p, errorStatus, previousContext);
          // Stop here.
          if (!proceed) {
            return;
          }
        }
      }
      // Validate context.
      {
        // Progression handling.
        SubMonitor validateMonitor = null;
        if (null != mainMonitor) {
          validateMonitor = mainMonitor.newChild(10);
          validateMonitor.setTaskName(Messages.DataHandler_SetContext_ProgressTask_ValidateContext);
        }
        // Do not validate default context for it is invalid.
        if (!isDefaultContext) {
          IValidationHandler validationHandler = ModelHandlerActivator.getDefault().getValidationHandler();
          if (null != validationHandler) {
            errorStatus = validationHandler.validateElement(currentContext, currentContext);
          }
          if ((null != errorStatus) && !errorStatus.isOK()) {
            result.add(errorStatus);
            boolean displayErrors = errorLevelActivated(errorStatus.getSeverity());
            boolean proceed =
                handleCurrentContextSetErrors(currentContext, notify_p, scriptsActivation_p, allowUserInteractions_p && displayErrors, errorStatus,
                    previousContext);
            // Stop here.
            if (!proceed) {
              return;
            }
          }

        }
      }
      // Execute post-load script.
      {
        errorStatus = testAndExecuteScript(SCRIPTS_POSTLOAD, scriptsActivation_p, (null != mainMonitor) ? mainMonitor.newChild(5) : null);
        if ((null != errorStatus) && !errorStatus.isOK()) {
          result.add(errorStatus);
          boolean proceed = handleCurrentContextSetErrors(currentContext, notify_p, scriptsActivation_p, allowUserInteractions_p, errorStatus, previousContext);
          // Stop here.
          if (!proceed) {
            return;
          }
        }
      }
      // Notify change.
      {
        // Progression handling.
        SubMonitor contextChangeMonitor = null;
        if (null != mainMonitor) {
          contextChangeMonitor = mainMonitor.newChild(65);
          contextChangeMonitor.setTaskName(Messages.DataHandler_SetContext_ProgressTask_ContextChangeListeners);
        }
        if (notify_p) {
          // Create a sub-monitor that gives each listener a 10 slots progress.
          SubMonitor realMonitor = null;
          if (null != contextChangeMonitor) {
            realMonitor = contextChangeMonitor.newChild(_currentContextChangeListeners.size() * 10);
          }
          for (ICurrentContextChangeListener listeners : Collections.unmodifiableCollection(_currentContextChangeListeners)) {
            errorStatus = listeners.contextChanged(currentContext, result, (null != realMonitor) ? realMonitor.newChild(10) : null, allowUserInteractions_p);
            if ((null != errorStatus) && !errorStatus.isOK()) {
              result.add(errorStatus);
              boolean proceed =
                  handleCurrentContextSetErrors(currentContext, notify_p, scriptsActivation_p, allowUserInteractions_p, errorStatus, previousContext);
              // Stop here.
              if (!proceed) {
                return;
              }
            }
          }
          // Notify remote listeners.
          notifyRemoteListeners(context_p.getName(), allowUserInteractions_p);
        }
      }
    } catch (Exception exception_p) {
      if (null == errorStatus) {
        errorStatus = new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), null, exception_p);
      }
      handleCurrentContextSetErrors(currentContext, notify_p, scriptsActivation_p, allowUserInteractions_p, errorStatus, previousContext);
      CommonActivator.getInstance().getLog().log(errorStatus);
    }
  }

  /**
   * Is specified validation error level activated as a displayable one ?
   * @param validationSeverity_p
   * @return
   */
  protected boolean errorLevelActivated(int validationSeverity_p) {
    IPreferenceStore preferenceStore = ModelHandlerActivator.getDefault().getPreferenceStore();
    String validationLevel = preferenceStore.getString(IPreferencesConstants.VALIDATION_LEVEL);
    if (IPreferencesConstants.VALIDATION_LEVEL_INFO.equals(validationLevel)) {
      return true;
    } else if (IPreferencesConstants.VALIDATION_LEVEL_WARNING.equals(validationLevel)) {
      return (IStatus.ERROR == validationSeverity_p) || (IStatus.WARNING == validationSeverity_p);
    } else if (IPreferencesConstants.VALIDATION_LEVEL_ERROR.equals(validationLevel)) {
      return (IStatus.ERROR == validationSeverity_p);
    }
    return false;
  }

  /**
   * Get the list of parent contexts.
   * @param context_p the context to get context hierarchy of.
   * @return the list of parent contexts. Can not be <code>null</code> but possibly empty.
   */
  public List<ContextReference> extractContextReferences(Context context_p, URI contextUri_p) {
    List<ContextReference> contextReferences = new ArrayList<ContextReference>(0);
    Context context = context_p;
    String uri = (null != contextUri_p) ? contextUri_p.toString() : null;
    // Squeeze install context anyway.
    while ((null != context) && (null != uri) && !context.getId().equals(getDefaultContext().getId())) {
      ContextReference contextReference = ContextsProjectFactory.eINSTANCE.createContextReference();
      contextReference.setId(context.getId());
      contextReference.setUri(uri);
      contextReference.setName(context.getName());
      contextReferences.add(contextReference);
      context = context.getSuperContext();
      if (null != context) {
        uri = context.eResource().getURI().toString();
      }
    }
    return contextReferences;
  }

  /**
   * Get all contexts, both in workspace and deployed ones.
   * @return
   */
  public RootElement getAllContexts() {
    RootElement result = getAllContextsInWorkspace();
    // Add install context.
    RootContextsProject container = new RootContextsProject();
    Context context = getContext(ICommonConstants.DEFAULT_CONTEXTS_PATH);
    // Create the descriptor of the install context.
    ContextsProject contextsProject = ContextsProjectFactory.eINSTANCE.createContextsProject();
    ContextReference contextReference = ContextsProjectFactory.eINSTANCE.createContextReference();
    contextReference.setId(context.getId());
    contextReference.setName(context.getName());
    contextReference.setUri(context.eResource().getURI().toString());
    contextsProject.getContextReferences().add(contextReference);
    container._contextsProject = contextsProject;
    result._projects.add(container);
    return result;
  }

  /**
   * Get all contexts in workspace.
   * @return
   */
  public RootElement getAllContextsInWorkspace() {
    RootElement result = new RootElement();
    result._projects.addAll(ProjectActivator.getInstance().getProjectHandler().getAllProjectsInWorkspace());
    return result;
  }

  /**
   * Get context from specified context reference.<br>
   * This does only apply to context already imported in the workspace.
   * @param contextReference_p
   * @return <code>null</code> if reference is invalid, or context could not be loaded.
   */
  public Context getContext(ContextReference contextReference_p) {
    // Precondition.
    if (null == contextReference_p) {
      return null;
    }
    String contextUri = contextReference_p.getUri();
    // Precondition.
    if (null == contextUri) {
      return null;
    }
    return getContext(URI.createURI(contextUri));
  }

  /**
   * Context loading from its pathname.
   * @param pathName_p the pathname as defined by {@see FileHelper#getFileFullUri(String)}
   * @return <code>null</code> if there is any problem to load specified pathname.
   */
  public Context getContext(String pathName_p) throws InvalidContextException {
    return getContext(FileHelper.getFileFullUri(pathName_p));
  }

  /**
   * Context loading from its pathname, in specified resource set.
   * @param pathName_p the pathname as defined by {@see FileHelper#getFileFullUri(String)}
   * @return <code>null</code> if there is any problem to load specified pathname.
   */
  public Context getContext(String pathName_p, ResourceSet resourceSet_p) throws InvalidContextException {
    return getContext(FileHelper.getFileFullUri(pathName_p), resourceSet_p);
  }

  /**
   * Context loading from its {@link URI}.<br />
   * Also fill in the transient category with values from the windows registry.
   * @param contextUri_p The full {@link URI} as defined by {@link FileHelper#getFileFullUri(String)}
   * @return <code>null</code> if there is any problem to load specified pathname.
   * @throws InvalidContextException
   */
  public Context getContext(URI contextUri_p) throws InvalidContextException {
    return getContext(contextUri_p, null);
  }

  /**
   * Context loading from its {@link URI}.<br />
   * Also fill in the transient category with values from the windows registry.
   * @param contextUri_p The full {@link URI} as defined by {@link FileHelper#getFileFullUri(String)}
   * @param resourceSet_p The resource set to use. <code>null</code> if shared {@link ContextsEditingDomain} is to be used directly.
   * @return <code>null</code> if there is any problem to load specified pathname.
   * @throws InvalidContextException
   */
  public Context getContext(URI contextUri_p, ResourceSet resourceSet_p) throws InvalidContextException {
    Context result = null;
    // Make sure case is correct one.
    URI contextUri = FileHelper.correctURICase(contextUri_p);
    // Choose resource set.
    ContextsEditingDomain editingDomain = ModelHandlerActivator.getDefault().getEditingDomain();
    final ResourceSet resourceSet = (null == resourceSet_p) ? editingDomain.getResourceSet() : resourceSet_p;
    // Make sure modifications are monitored.
    boolean failed = false;
    String failureMessage = null;
    List<Resource> beforeGetResources = new ArrayList<Resource>(resourceSet.getResources());
    try {
      Resource resource = resourceSet.getResource(contextUri, true);
      result = ModelUtil.getContext(resource);
    } catch (Exception exception_p) {
      failed = true;
      failureMessage = "Cannot read the context denoted by " + contextUri; //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(failureMessage, IStatus.ERROR, null);
    }
    // End monitoring.
    List<Resource> afterGetResources = new ArrayList<Resource>(resourceSet.getResources());
    // Test result.
    if (null != result) {
      // TODO Guillaume.
      // Invoke model validation instead.
      // But make sure this is invoked at load time only !
      // Do not throw an exception for default context (which does not have a super context).
      Context superContext = result.getSuperContext();
      failed = !result.eResource().isDefault() && ((null == superContext) || superContext.eIsProxy());
      failureMessage = "Context " + result.getName() + " has no super context defined !"; //$NON-NLS-1$ //$NON-NLS-2$
    }
    // If there was an error, revert resource set back to previous state.
    if (failed) {
      // Undo modifications on resource set.
      afterGetResources.removeAll(beforeGetResources);
      for (Resource resource : afterGetResources) {
        if (null == resourceSet_p) {
          editingDomain.removeResource(resource);
        } else {
          resourceSet_p.getResources().remove(resource);
        }
      }
      throw new InvalidContextException(failureMessage);
    }
    return result;
  }

  /**
   * Get current context.<br />
   * If no context has ever been set in preferences, the <em>default</em> context is returned.<br/>
   * If the OF is working in user mode only and an admin context has to be set current, set DefaultContext as current one instead.
   * @return the current context. Can not be <code>null</code>.
   */
  public synchronized Context getCurrentContext() {
    // Precondition.
    if (null != _currentContext) {
      return _currentContext;
    }
    // No current context set : get its URI from preferences.
    final String prefCurrentContextURI =
        Platform.getPreferencesService().getString(ModelHandlerActivator.getDefault().getPluginId(), CURRENT_CONTEXT_PREFERENCE_KEY,
            ICommonConstants.DEFAULT_CONTEXTS_PATH, null);
    final URI prefCurrentContextFileFullURI = FileHelper.getFileFullUri(prefCurrentContextURI);
    final URI defaultContextFileFullURI = FileHelper.getFileFullUri(ICommonConstants.DEFAULT_CONTEXTS_PATH);
    URI currentContextFileFullURI = prefCurrentContextFileFullURI;
    // OF is started in user only mode, an admin context mustn't be set as current -> set DefautContext as current instead.
    if (!defaultContextFileFullURI.equals(prefCurrentContextFileFullURI) && ProjectActivator.getInstance().getCommandLineArgsHandler().userModeOnly()) {
      RootContextsProject prefRootContextsProject = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(prefCurrentContextFileFullURI);
      if (null != prefRootContextsProject) {
        ContextReference adminContextReference = prefRootContextsProject.getAdministratedContext();
        // Preferences contain an admin context URI, change it by DefaultContext URI.
        // URI comparison is done ignoring case since OF has to be case unsensitive with resources names.
        if (null != adminContextReference && adminContextReference.getUri().equalsIgnoreCase(prefCurrentContextURI.toString())) {
          currentContextFileFullURI = defaultContextFileFullURI;
        }
      }
    }
    // Load it in local model bag.
    _currentContext = getContext(currentContextFileFullURI, _resourceSet);
    return _currentContext;
  }

  /**
   * Get resource set used to handle current context.<br>
   * This one should be kept as lightweight and clean as possible.
   * @return
   */
  public ContextsResourceSet getCurrentContextResourceSet() {
    return _resourceSet;
  }

  /**
   * Get default context instance.
   * @return
   */
  public Context getDefaultContext() {
    return getContext(ICommonConstants.DEFAULT_CONTEXTS_PATH);
  }

  /**
   * Get user context, belonging to the current user, for which the given admin context is the parent.
   * @param context_p the possibly parent admin context.
   * @return <code>null</code> if the given context is null, unloaded or not an admin context or if it is an admin context which doesn't have a child user
   *         context for current user, the user context otherwise.
   */
  public Context getUserContext(Context context_p) {
    // Preconditions (a null or unloaded context can't be used).
    // (An unloaded context can be given as an argument when multiple contexts deletion are done).
    if (null == context_p || null == context_p.eResource()) {
      return null;
    }
    // Get the RootContextProject of the given context.
    URI contextURI = context_p.eResource().getURI();
    RootContextsProject rootContextProject = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(contextURI);
    // Is the given context the admin context ?
    URI adminContextURI = URI.createURI(rootContextProject.getAdministratedContext().getUri());
    if (!contextURI.equals(adminContextURI)) {
      // Given context is not the admin context.
      return null;
    }
    String currentUserContextURI = rootContextProject.getUserContextUri(ProjectActivator.getInstance().getCurrentUserId());
    ResourceSet resourceSet = ModelHandlerActivator.getDefault().getEditingDomain().getResourceSet();
    // Does child user context exist (load it if needed) ?
    if (null != currentUserContextURI && null != resourceSet.getResource(URI.createURI(currentUserContextURI), true)) {
      Context userContext = getContext(URI.createURI(currentUserContextURI));
      return userContext;
    }
    return null;
  }

  /**
   * Current context set has raised an error, handle it.
   * @param context_p The applied context.
   * @param notify_p Should listeners be notified of context set events ?
   * @param scriptsActivation_p A combination of {@value #SCRIPTS_PREUNLOAD}, {@value #SCRIPTS_PRELOAD}, {@value #SCRIPTS_POSTLOAD} values enabling the various
   *          scripts execution. {@value #SCRIPTS_NONE} to disable all scripts.
   * @param allowUserInteractions_p <code>true</code> to allow user interactions while switching and display dialogs, <code>false</code> not to present user
   *          interactions.<br>
   *          In both cases, the code is run synchronously, but in the second one (ie <code>false</code>) an invalid context is still applied, whereas this is
   *          not possible from UI behavior.
   * @param errorStatus_p The error(s) raised while setting new context.
   * @param previousContext_p The context applied before trying to set new one.
   * @return <code>true</code> if caller should continue, <code>false</code> if caller should stop its current operation.
   */
  private boolean handleCurrentContextSetErrors(final Context context_p, final boolean notify_p, final int scriptsActivation_p,
      boolean allowUserInteractions_p, final IStatus errorStatus_p, final Context previousContext_p) {
    if (null == context_p) {
      return false;
    }

    // transform the given status to a simple multistatus. To remove the EMF head status of each inner status
    final IStatus multiErrorStatus =
        new MultiStatus(errorStatus_p.getPlugin(), errorStatus_p.getCode(), errorStatus_p.getMessage(), errorStatus_p.getException());
    if (errorStatus_p.isMultiStatus()) {
      for (IStatus childStatus : errorStatus_p.getChildren()) {
        if (childStatus.isMultiStatus()) {
          ((MultiStatus) multiErrorStatus).addAll(childStatus);
        } else {
          ((MultiStatus) multiErrorStatus).add(childStatus);
        }
      }
    } else {
      // In case the error status is not a multi status
      ((MultiStatus) multiErrorStatus).add(errorStatus_p);
    }
    final ButtonCode[] result = new ButtonCode[] { ButtonCode.NONE };
    // Display error to user.
    if (allowUserInteractions_p && (null != errorStatus_p)) {
      DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
        /**
         * @see java.lang.Runnable#run()
         */
        public void run() {

          ContextSettingErrorDialog dialog =
              new ContextSettingErrorDialog(getDisplay().getActiveShell(), context_p.getName(), multiErrorStatus,
                  !((null == previousContext_p) || previousContext_p.getId().equals(context_p.getId())));
          result[0] = dialog.openDialog();
        }
      }, false);
    }
    // Deal with returned code.
    switch (result[0]) {
      case RETRY:
        // Try and apply the same context.
        doSetCurrentContext(context_p, notify_p, scriptsActivation_p, allowUserInteractions_p, previousContext_p, null);
        return false;
      case RELOAD:
        // Try and apply previous context, if any.
        // There should not be this button if the previous context is not available.
        doSetCurrentContext(previousContext_p, notify_p, scriptsActivation_p, allowUserInteractions_p, previousContext_p, null);
        return false;
      case QUIT:
        // Try and apply default context.
        doSetCurrentContext(getDefaultContext(), false, scriptsActivation_p, false, null, null);
        // Quit platform, either by closing the workbench or exiting the VM.
        if (PlatformUI.isWorkbenchRunning()) {
          DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
            /**
             * @see java.lang.Runnable#run()
             */
            public void run() {
              PlatformUI.getWorkbench().close();
            }
          }, true);
        } else {
          System.exit(-1);
        }
        return false;
      case CONTINUE:
        // Try and apply default context.
        // do not allow user interaction here. The user has decided to apply the default context. He has already been warned previous time that listener are
        // registered to the context
        doSetCurrentContext(getDefaultContext(), notify_p, scriptsActivation_p, false, previousContext_p, null);
        return false;
      case NONE:
      default:
        return true;
    }
  }

  /**
   * Import specified context projects into workspace.
   * @param projects_p The projects to import. Should not be <code>null</code> or empty. Note that it is expected that those projects do not already exist in
   *          the workspace.
   * @param copyProjectsToWorkspace_p <code>true</code> to make a copy of the projects into the workspace, <code>false</code> to link to original locations.
   * @param checkAdministrator_p Check that user id belong to administrators of the context
   * @return <code>true</code> if all projects were dealt with. Note that this does not necessarily mean that there are imported. <code>false</code> if
   *         parameters are not valid.
   */
  public IStatus importContexts(Collection<RootContextsProject> projects_p, boolean copyProjectsToWorkspace_p, boolean checkAdministrators_p) {
    // Precondition.
    if ((null == projects_p) || projects_p.isEmpty()) {
      return new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), ICommonConstants.EMPTY_STRING);
    }
    // Go go go.
    MultiStatus status = new MultiStatus(ModelHandlerActivator.getDefault().getPluginId(), 0, Messages.DataHandler_Import_Problems, null);
    for (RootContextsProject project : projects_p) {
      IProject realProject = project._project;
      // Skip this one, it is already in the workspace.
      if (realProject.isAccessible()) {
        continue;
      }
      IProjectDescription description = project._description;
      // Import project.
      boolean shouldCopy = copyProjectsToWorkspace_p;
      // Do not copy a project that already lies in the workspace, simply import it.
      if (shouldCopy) {
        if (ProjectHelper.isPhysicallyInWorkspace(description.getLocationURI())) {
          shouldCopy = false;
        }
      }
      // Import or copy the project.
      IStatus importStatus = Status.OK_STATUS;
      if (!shouldCopy) {
        importStatus = ProjectHelper.importExistingProject(realProject, description);
      } else {
        importStatus = ProjectHelper.copyProjectAndImportToWorkspace(realProject, description);
      }
      // Open imported context.
      if (importStatus.isOK()) {
        try {
          // Try and load context.
          final Context context = getContext(project.getContext(checkAdministrators_p));
          if (null == context) {
            // Log.
            StringBuilder message = new StringBuilder("DataHandler.importContexts()\n"); //$NON-NLS-1$
            message.append("Unable to load contexts for project ").append(realProject.getName()); //$NON-NLS-1$
            CommonActivator.getInstance().logMessage(message.toString(), IStatus.ERROR, null);
            importStatus =
                new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
                    Messages.DataHandler_Import_ContextsNotLoaded_Error, project.getAdministratedContext().getName()));
          } else {
            // Make as if the context was just added for most notifications are not taken into account while it's loading.
            NotificationHelper.fireNonDirtyingNotification(Notification.ADD, context.eResource(), null, null, context);
          }
        } catch (InvalidContextException ice_p) {
          // Remove project from workspace.
          ProjectHelper.deleteProject(realProject.getName(), false);
          // Log.
          StringBuilder message = new StringBuilder("DataHandler.importContexts()\n"); //$NON-NLS-1$
          message.append("Unable to load contexts for project ").append(realProject.getName()); //$NON-NLS-1$
          CommonActivator.getInstance().logMessage(message.toString(), IStatus.ERROR, ice_p);
          importStatus =
              new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
                  Messages.DataHandler_Import_ContextsNotLoaded_Error, project.getAdministratedContext().getName()));
        }
      }
      // Keep import result for this project.
      status.add(importStatus);
    }
    if (!status.isOK()) {
      return status;
    }
    return Status.OK_STATUS;
  }

  /**
   * Import specified context projects into workspace.
   * @param projects_p The projects to import. Should not be <code>null</code> or empty. Note that it is expected that those projects do not already exist in
   *          the workspace.
   * @param copyProjectsToWorkspace_p <code>true</code> to make a copy of the projects into the workspace, <code>false</code> to link to original locations.
   * @return <code>true</code> if all projects were dealt with. Note that this does not necessarily mean that there are imported. <code>false</code> if
   *         parameters are not valid.
   */
  public IStatus importContexts(Collection<RootContextsProject> projects_p, boolean copyProjectsToWorkspace_p) {
    return importContexts(projects_p, copyProjectsToWorkspace_p, true);
  }

  /**
   * Is specified script (via its constant) is activated within scripts activation hint ?
   * @param scriptActivationConstant_p The script activation constant to be tested. See {@value #SCRIPTS_NONE} to {@value #SCRIPTS_PREUNLOAD}.
   * @param scriptsActivation_p The scripts activation hint.
   * @return <code>true</code> if the script is activated, <code>false</code> otherwise.
   */
  protected boolean isActivated(int scriptActivationConstant_p, int scriptsActivation_p) {
    return (scriptActivationConstant_p & scriptsActivation_p) == scriptActivationConstant_p;
  }

  /**
   * Is specified context the current one ?
   * @param context_p
   * @return <code>true</code> if so, <code>false</code> otherwise.
   */
  public boolean isCurrentContext(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return false;
    }
    Context currentContext = getCurrentContext();
    return currentContext.getId().equals(context_p.getId());
  }

  /**
   * Notify remote context change listeners asynchronously.
   * @param newContextName_p
   */
  protected void notifyRemoteListeners(final String newContextName_p, final boolean allowUserInteractions_p) {
    new Thread(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        // Do invoke remote listeners, and get results.
        Map<String, IStatus> postContextChangeStatuses = _switchContextHelper.getPostContextChangeStatuses(newContextName_p, allowUserInteractions_p);
        // Log them.
        ILog log = CommonActivator.getInstance().getLog();
        for (Entry<String, IStatus> entry : postContextChangeStatuses.entrySet()) {
          MultiStatus status =
              new MultiStatus(ModelHandlerActivator.getDefault().getPluginId(), 0, MessageFormat.format(
                  Messages.DataHandler_RemoteListeners_PostContextChange_WrapupMessage, entry.getKey()), null);
          status.add(entry.getValue());
          log.log(status);
        }
      }
    }).start();
  }

  /**
   * Participate in specified project for specified user.<br>
   * If the participation already exists, then it is automatically imported, and the new context name is ignored.<br>
   * This is strictly equivalent to calling {@link #participateInAProject(String, RootContextsProject, ResourceSet, String)} with current user, and
   * {@link ResourceSet} set to {@link ContextsEditingDomain}'s one.
   * @param newContextName_p The new participation name. Can not be <code>null</code>.
   * @param project_p The project being participated in. Can not be <code>null</code>.
   * @return an <code>ERROR</code> status if no participation was found or could be created, an <code>OK</code> status if it was successfully loaded or created.
   */
  public IStatus participateInAProject(String newContextName_p, RootContextsProject project_p) {
    return participateInAProject(newContextName_p, project_p, null, null);
  }

  /**
   * Participate in specified project for specified user.<br>
   * If the participation already exists, then it is automatically imported, and the new context name is ignored.
   * @param newContextName_p The new participation name. Can only be <code>null</code> when the participation already exists.
   * @param project_p The project being participated in. Can not be <code>null</code>.
   * @param resourceSet_p The owner of the context participation. Can be <code>null</code> if shared {@link ContextsEditingDomain} is to be used.
   * @param userId_p The user that is participating. Can be <code>null</code>, the current user is then used instead.
   * @return an <code>ERROR</code> status if no participation was found or could be created, an <code>OK</code> status if it was successfully loaded or created.
   */
  public IStatus participateInAProject(String newContextName_p, RootContextsProject project_p, ResourceSet resourceSet_p, String userId_p) {
    // Precondition.
    if (null == project_p) {
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), Messages.DataHandler_Participate_NoParent_Error);
    }
    // Is project to participate to loaded ?
    IProject project = ProjectHelper.getProject(project_p._project.getName());
    boolean isProjectLoaded = (null != project) && project.exists();
    IStatus importProjectStatus = Status.OK_STATUS;
    if (!isProjectLoaded) {
      importProjectStatus = ProjectHelper.importExistingProject(project_p._project, project_p._description);
    }
    if (importProjectStatus.isOK()) {
      URI parentUri = URI.createURI(project_p.getAdministratedContext().getUri());
      IPath parentPath = new Path(parentUri.toPlatformString(true));
      String userId = userId_p;
      if (null == userId) {
        userId = ProjectActivator.getInstance().getCurrentUserId();
      }
      IPath userPath = parentPath.removeLastSegments(1).append(RootContextsProject.getUserContextPath(userId));
      // Try to load user context.
      URI resourceURI = FileHelper.getFileFullUri(userPath.toString());
      Context context = null;
      try {
        context = getContext(resourceURI, resourceSet_p);
      } catch (Exception exception_p) {
        // Most likely, context does not exist.
      }
      // User context does not exist.
      if (null == context) {
        // In this case, the project name is required.
        if (null == newContextName_p) {
          return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), Messages.DataHandler_Participate_NoName_Error);
        }
        ResourceSet resourceSet = (null == resourceSet_p) ? ModelHandlerActivator.getDefault().getEditingDomain().getResourceSet() : resourceSet_p;
        Resource resource = resourceSet.createResource(resourceURI);
        context = ContextsFactory.eINSTANCE.createContext();
        context.setName(newContextName_p);
        context.setSuperContext(getContext(parentUri, resourceSet_p));
        try {
          // Create blank resource so as to fulfill future events needs.
          resource.save(null);
          // Add context to resource.
          // At this time, the resource must exist (even empty) on the disk.
          resource.getContents().add(context);
          // Save with full contents.
          resource.save(null);
        } catch (IOException exception_p) {
          StringBuilder loggerMessage = new StringBuilder("Error while saving user context."); //$NON-NLS-1$
          CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
          return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), Messages.DataHandler_Participate_Save_Error, exception_p);
        }
      } else {
        // Make as if the context was just added for most notifications are not taken into account while it's loading.
        NotificationHelper.fireNonDirtyingNotification(Notification.ADD, context.eResource(), null, null, context);
      }
      ProjectHelper.refreshProject(project_p._project, null);
      return Status.OK_STATUS;
    }
    return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), Messages.DataHandler_Participate_Import_Error);
  }

  /**
   * Populate the context description after project creation.
   * @param project_p the project where the description exists.
   * @param context_p the context of this project.
   * @return the populated model element, or <code>null</code> if parameters are invalid.
   */
  public ContextsProject populateContextsDescription(IProject project_p, Context context_p) throws IOException {
    // Precondition.
    if ((null == project_p) || (null == context_p)) {
      return null;
    }
    // Get URI from context resource, if any.
    URI contextURI = null;
    ContextsResourceImpl resource = context_p.eResource();
    if (null != resource) {
      contextURI = resource.getURI();
    }
    return populateContextsDescription(project_p, context_p, contextURI);
  }

  /**
   * Populate the context description after project creation.
   * @param project_p the project where the description exists.
   * @param context_p the context of this project.
   * @param contextUri_p the future context resource URI, if context is not yet attached to a resource.
   * @return the populated model element, or <code>null</code> if parameters are invalid.
   */
  public ContextsProject populateContextsDescription(IProject project_p, Context context_p, URI contextUri_p) throws IOException {
    // Precondition.
    if ((null == project_p) || (null == context_p) || (null == contextUri_p)) {
      return null;
    }
    List<ContextReference> contextReferences = extractContextReferences(context_p, contextUri_p);
    // Add project references informations.
    ContextsProject contextsProject = ProjectActivator.getInstance().getProjectHandler().getContextsProject(project_p);
    // First of all clear existing references.
    EList<ContextReference> existingReferences = contextsProject.getContextReferences();
    existingReferences.clear();
    // Then add new ones.
    existingReferences.addAll(contextReferences);
    // And save description.
    ProjectActivator.getInstance().getEditingDomain().saveResource(contextsProject.eResource());
    return contextsProject;
  }

  /**
   * Remove the supplied listener to the listener list.
   * @param listener_p
   */
  public void removeCurrentContextChangeListener(ICurrentContextChangeListener listener_p) {
    _currentContextChangeListeners.add(listener_p);
  }

  /**
   * Remove the supplied listener to the listener list.
   * @param listener_p
   */
  public void removeCurrentContextPreferenceChangeListener(IPreferenceChangeListener listener_p) {
    IEclipsePreferences node = new InstanceScope().getNode(ModelHandlerActivator.getDefault().getPluginId());
    node.removePreferenceChangeListener(listener_p);
  }

  /**
   * Save specified context as current context preference tag.<br>
   * Does only apply to preferences mechanism.
   * @param context_p A not <code>null</code> context to declare as current one.
   */
  public void saveCurrentContextPreference(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return;
    }
    // Save preferences.
    URI uri = context_p.eResource().getURI();
    IEclipsePreferences node = new InstanceScope().getNode(ModelHandlerActivator.getDefault().getPluginId());
    node.put(CURRENT_CONTEXT_PREFERENCE_KEY, uri.toPlatformString(true));
    // Flush right now for further preference key readings.
    try {
      node.flush();
    } catch (BackingStoreException exception_p) {
      StringBuilder loggerMessage = new StringBuilder("DataHandler.saveCurrentContextPreference(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
    }
  }

  /**
   * Set current context. Notify all listeners of the switch.
   * @param context_p the current context to set.
   */
  public void setCurrentContext(Context context_p) {
    setCurrentContext(context_p, true);
  }

  /**
   * Set current context. Notify all listeners of the switch.
   * @param context_p the current context to set.
   * @param allowUserInteractions_p <code>true</code> to allow user interactions while switching and display dialogs, <code>false</code> not to present user
   *          interactions.<br>
   *          In both cases, the code is run synchronously, but in the second one (ie <code>false</code>) an invalid context is still applied, whereas this is
   *          not possible from UI behavior.
   */
  public void setCurrentContext(Context context_p, boolean allowUserInteractions_p) {
    setCurrentContext(context_p, true, SCRIPTS_PREUNLOAD | SCRIPTS_PRELOAD | SCRIPTS_POSTLOAD, allowUserInteractions_p, null);
  }

  /**
   * Set current context.
   * @param context_p the current context to set.
   * @param notify_p true to notify listeners, false otherwise.
   * @param scriptsActivation_p A combination of {@value #SCRIPTS_PREUNLOAD}, {@value #SCRIPTS_PRELOAD}, {@value #SCRIPTS_POSTLOAD} values enabling the various
   *          scripts execution. {@value #SCRIPTS_NONE} to disable all scripts.
   * @param allowUserInteractions_p <code>true</code> to allow user interactions while switching and display dialogs, <code>false</code> not to present user
   *          interactions.<br>
   *          In both cases, the code is run synchronously, but in the second one (ie <code>false</code>) an invalid context is still applied, whereas this is
   *          not possible from UI behavior.
   * @param previousContext_p The context to force as previous one.<br>
   *          If none is provided (ie value is <code>null</code>) current one will be used as previous one.
   */
  public void setCurrentContext(final Context context_p, final boolean notify_p, final int scriptsActivation_p, final boolean allowUserInteractions_p,
      final Context previousContext_p) {
    // Display a progress monitor, if it makes sense
    if (DisplayHelper.hasAnAccessibleDisplay()) {
      DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
        public void run() {
          ProgressMonitorDialog dialog = new ProgressMonitorDialog(getDisplay().getActiveShell());
          try {
            dialog.run(allowUserInteractions_p, false, new IRunnableWithProgress() {
              public void run(IProgressMonitor monitor_p) throws InvocationTargetException, InterruptedException {
                doSetCurrentContext(context_p, notify_p, scriptsActivation_p, allowUserInteractions_p, previousContext_p, monitor_p);
              }
            });
            } catch (InvalidContextException exception_p) {
             DisplayHelper.displayErrorDialog("Cannot read the context", exception_p.getMessage(), Status.OK_STATUS);
          } catch (Exception exception_p) {
            StringBuilder loggerMessage = new StringBuilder("DataHandler.setCurrentContext(..) _ "); //$NON-NLS-1$
            CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
          }
        }
      }, false);
    } else {
      // Execute code without any display, synchronously.
      // This is most probably a headless case.
      // No user interaction is allowed, and no progress monitoring either.
      doSetCurrentContext(context_p, notify_p, scriptsActivation_p, false, previousContext_p, null);
    }
  }

  /**
   * Test specified script (via its constant) within scripts activation hint, and execute it if it is activated.
   * @param scriptConstant_p The script constant to be tested. See {@value #SCRIPTS_NONE} to {@value #SCRIPTS_PREUNLOAD}.
   * @param scriptsActivation_p The scripts activation hint.
   * @param progressMonitor_p
   * @return <code>null</code> if script was executed successfully, or there was no script to execute for this constant, an {@link IStatus} describing the error
   *         otherwise.
   */
  @SuppressWarnings("boxing")
  protected IStatus testAndExecuteScript(int scriptConstant_p, int scriptsActivation_p, IProgressMonitor progressMonitor_p) {
    // Precondition.
    // Script is to be activated.
    if (!isActivated(scriptConstant_p, scriptsActivation_p)) {
      return null;
    }
    String variableName = null;
    switch (scriptConstant_p) {
      case SCRIPTS_POSTLOAD:
        variableName = "PostLoad"; //$NON-NLS-1$
      break;
      case SCRIPTS_PRELOAD:
        variableName = "PreLoad"; //$NON-NLS-1$
      break;
      case SCRIPTS_PREUNLOAD:
        variableName = "PreUnload"; //$NON-NLS-1$
      break;
      default:
      break;
    }
    // No script to execute.
    if (null == variableName) {
      return null;
    }
    // Progress handling.
    if (null != progressMonitor_p) {
      progressMonitor_p.setTaskName(MessageFormat.format(Messages.DataHandler_SetContext_ProgressTask_ScriptExecution, variableName));
    }
    variableName = "\\Orchestra\\Scripts\\" + variableName; //$NON-NLS-1$
    List<VariableValue> values = DataUtil.getValues(variableName);
    // Still no script to execute.
    if ((null == values) || values.isEmpty()) {
      return null;
    }
    String scriptAbsolutePath = values.get(0).getValue();
    // Still no script to execute.
    if (null == scriptAbsolutePath) {
      return null;
    }
    File scriptFile = new File(scriptAbsolutePath);
    // Still no script to execute.
    if (!scriptFile.exists()) {
      return null;
    }
    String argumentsName = variableName + " arguments"; //$NON-NLS-1$
    // Executable command line.
    String scriptExecutable = MessageFormat.format("\"{0}\"", scriptFile.getAbsolutePath()); //$NON-NLS-1$
    // Set arguments.
    List<VariableValue> arguments = DataUtil.getValues(argumentsName);
    if (null != arguments) {
      for (VariableValue argument : arguments) {
        scriptExecutable += MessageFormat.format(" \"{0}\"", argument.getValue()); //$NON-NLS-1$
      }
    }
    // Execute script.
    try {
      String commandLine = MessageFormat.format("cmd /c \"{0}\"", scriptExecutable); //$NON-NLS-1$
      Process process = Runtime.getRuntime().exec(commandLine, null, scriptFile.getParentFile());
      int returnCode = process.waitFor();
      if (0 != returnCode) {
        return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.DataHandler_ScriptExecutionMessage_ExitOnError, scriptAbsolutePath, returnCode));
      }
    } catch (Exception exception_p) {
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
          Messages.DataHandler_ScriptExecutionMessage_ExceptionWhileExecutingScript, scriptAbsolutePath), exception_p);
    }
    return null;
  }

  /**
   * @author t0076261
   */
  protected enum ButtonCode {
    CONTINUE(3), NONE(-1), QUIT(2), RELOAD(1), RETRY(10);
    protected int _id;

    ButtonCode(int id_p) {
      _id = id_p;
    }

    protected static ButtonCode getCode(int id_p) {
      for (ButtonCode buttonCode : values()) {
        if (buttonCode._id == id_p) {
          return buttonCode;
        }
      }
      return ButtonCode.NONE;
    }
  }

  /**
   * Context setting error dialog.
   * @author t0076261
   */
  protected class ContextSettingErrorDialog extends DiagnosticDialog {
    /**
     * Is Reload option enabled ?
     */
    private boolean _reloadEnabled;

    /**
     * Constructor.
     * @param parentShell_p
     * @param contextName_p
     * @param reloadEnabled_p
     */
    public ContextSettingErrorDialog(Shell parentShell_p, String contextName_p, IStatus status_p, boolean reloadEnabled_p) {

      super(parentShell_p, Messages.DataHandler_ContextSettingError_Title, MessageFormat
          .format(Messages.DataHandler_ContextSettingError_Message, contextName_p), BasicDiagnostic.toDiagnostic(status_p), IStatus.OK | IStatus.INFO
                                                                                                                            | IStatus.WARNING | IStatus.ERROR);
      _reloadEnabled = reloadEnabled_p;
    }

    /**
     * @see org.eclipse.jface.dialogs.ErrorDialog#buttonPressed(int)
     */
    @Override
    protected void buttonPressed(int id_p) {
      if (IDialogConstants.DETAILS_ID == id_p) {
        super.buttonPressed(id_p);
      } else {
        setReturnCode(id_p);
        close();
      }
    }

    /**
     * @see org.eclipse.jface.dialogs.ErrorDialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent_p) {
      createDetailsButton(parent_p);
      createButton(parent_p, ButtonCode.CONTINUE._id, Messages.DataHandler_ContextSettingDialog_ButtonLabel_Continue, true);
      createButton(parent_p, ButtonCode.RETRY._id, Messages.DataHandler_ContextSettingDialog_ButtonLabel_Retry, false);
      if (_reloadEnabled) {
        createButton(parent_p, ButtonCode.RELOAD._id, Messages.DataHandler_ContextSettingDialog_ButtonLabel_Reload, false);
      }
      createButton(parent_p, ButtonCode.QUIT._id, Messages.DataHandler_ContextSettingDialog_ButtonLabel_Quit, false);
    }

    /**
     * Open dialog and return chosen button code.
     * @return
     */
    public ButtonCode openDialog() {
      return ButtonCode.getCode(super.open());
    }
  }

  /**
   * An {@link Adapter} dedicated to project and contexts updates.
   * @author t0076261
   */
  protected class HandlerAdapter extends AdapterImpl {

    /**
     * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
     */
    @Override
    public void notifyChanged(Notification notification_p) {
      Object notifier = notification_p.getNotifier();
      int notificationType = notification_p.getEventType();
      switch (notificationType) {
        case Notification.SET:
          if (notifier instanceof Context) {
            if (ContextsPackage.Literals.NAMED_ELEMENT__NAME.equals(notification_p.getFeature())) {
              final Context context = ModelUtil.getContext(notifier);
              ContextsResourceImpl resource = context.eResource();
              ProjectActivator projectActivator = ProjectActivator.getInstance();
              // Make sure description is updated at save time, if this is an administrator context.
              if (!projectActivator.isAdministrator()) {
                // Stop here.
                return;
              }
              final RootContextsProject project = projectActivator.getProjectHandler().getProjectFromContextUri(resource.getURI());
              // Nothing can be done !
              if (null == project) {
                return;
              }
              // Add code to be called when the .context file will be saved.
              resource.addAdditionalSaveOperation(new PopulateContextsDescriptionSaveOperation(project._project, context));
            }
          }
        break;
        case ContextsResourceImpl.NOTIFICATION_TYPE_CONTEXT_SYNCHRONIZED:
          // Switch context if this is the current one.
          contextSaved((Context) notifier);
        break;
        default:
        break;
      }
    }
  }

  /**
   * Invalid context exception.
   * @author t0076261
   */
  public class InvalidContextException extends RuntimeException {
    private static final long serialVersionUID = -5182509392944291653L;

    /**
     * Constructor.
     * @param message_p
     */
    public InvalidContextException(String message_p) {
      super(message_p);
    }
  }

  /**
   * This class defines an additional save operation to populate the description.contextsproject file when a ContextResource is saved.
   * @author T0052089
   */
  protected class PopulateContextsDescriptionSaveOperation implements AdditionalSaveOperation {
    /**
     * The context linked to the description.contextsproject file to populate.
     */
    private Context _context;

    /**
     * The project containing the description.contextsproject file to populate.
     */
    private IProject _project;

    /**
     * @param project_p
     * @param context_p
     */
    public PopulateContextsDescriptionSaveOperation(IProject project_p, Context context_p) {
      _project = project_p;
      _context = context_p;
    }

    /**
     * Method generated by Eclipse but customized.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (!(obj instanceof PopulateContextsDescriptionSaveOperation)) {
        return false;
      }
      PopulateContextsDescriptionSaveOperation other = (PopulateContextsDescriptionSaveOperation) obj;
      if (_context == null) {
        if (other._context != null) {
          return false;
        }
      } else {
        if (other._context == null) {
          return false;
        }
        if (_context.getId() == null) {
          if (other._context.getId() != null) {
            return false;
          }
          // Contexts are compared using their Ids.
        } else if (!_context.getId().equals(other._context.getId())) {
          return false;
        }
      }
      if (_project == null) {
        if (other._project != null) {
          return false;
        }
      } else if (!_project.equals(other._project)) {
        return false;
      }
      return true;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void execute() throws IOException {
      // Update contexts description file.
      populateContextsDescription(_project, _context);
    }

    /**
     * Method generated by Eclipse but customized.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = (prime * result) + (((_context == null) || (_context.getId() == null)) ? 0 : _context.getId().hashCode());
      result = (prime * result) + ((_project == null) ? 0 : _project.hashCode());
      return result;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl.AdditionalSaveOperation#isExecutable()
     */
    public IStatus isExecutable() {
      // Check if the context description file is writable.
      URL fileFullUrl = FileHelper.getFileFullUrl(ProjectActivator.getInstance().getProjectHandler().getContextsProjectDescriptionUri(_project));
      File contextDescriptionFile = new File(fileFullUrl.getFile());
      if (!contextDescriptionFile.canWrite()) {
        return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), Messages.DataHandler_PopulateContextDescription_NotExecutable_Error);
      }
      return Status.OK_STATUS;
    }
  }
}