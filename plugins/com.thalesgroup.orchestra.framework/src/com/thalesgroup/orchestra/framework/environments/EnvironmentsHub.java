/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environments;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.ui.dialogs.DiagnosticDialog;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.AbstractEnvironment;
import com.thalesgroup.orchestra.framework.environment.BaselineContext;
import com.thalesgroup.orchestra.framework.environment.BaselineResult;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.IEnvironment;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractVariablesHandler;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler.VariableType;
import com.thalesgroup.orchestra.framework.environment.validation.EnvironmentRuleRegistry.ValidationType;
import com.thalesgroup.orchestra.framework.environments.logger.OrchestraEnvironmentsHubMakeBaselineLogger;
import com.thalesgroup.orchestra.framework.model.baseline.Baseline;
import com.thalesgroup.orchestra.framework.model.baseline.BaselineRepositoryHandler;
import com.thalesgroup.orchestra.framework.model.baseline.BaselineRepositoryHandler.LockState;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.DeleteContextServiceCommand;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsResourceSet;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener;
import com.thalesgroup.orchestra.framework.model.handler.data.RootElement;
import com.thalesgroup.orchestra.framework.model.validation.AbstractValidationHandler;
import com.thalesgroup.orchestra.framework.model.validation.ValidationContext;
import com.thalesgroup.orchestra.framework.model.validation.ValidationHelper;
import com.thalesgroup.orchestra.framework.model.validation.baseline.MakeBaselineValidationContext;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.ProjectHandler;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.project.nature.BaselineNature;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.ui.dialog.ChooseBaselineDialog;
import com.thalesgroup.orchestra.framework.ui.dialog.ChooseBaselineDialog.BaselineSelection;
import com.thalesgroup.orchestra.framework.ui.dialog.ChooseBaselineDialog.BaselineSelectionType;
import com.thalesgroup.orchestra.framework.ui.dialog.MakeBaselineDialog;
import com.thalesgroup.orchestra.framework.ui.dialog.MakeBaselineDialog.BaselineProperties;
import com.thalesgroup.orchestra.framework.ui.dialog.UseBaselineDialog;
import com.thalesgroup.orchestra.framework.ui.dialog.UseBaselineDialog.UseBaselineProperties;

/**
 * A centralized environments hub.<br>
 * Does use the {@link EnvironmentRegistry} as a pool of descriptors.<br>
 * Maintain its own list of active environments, based on current context.
 * @author t0076261
 */
public class EnvironmentsHub extends AbstractEnvironment implements ICurrentContextChangeListener {
  /**
   * Registry dedicated to artifacts environments.
   */
  protected ArtifactEnvironmentRegistry _artifactEnvironmentRegistry;
  /**
   * Registry dedicated to configuration directories environments.
   */
  protected ConfDirEnvironmentRegistry _confDirEnvironmentRegistry;
  /**
   * Local semaphore for make baseline process.<br>
   * Note that interprocess semaphore is obtained through the repository handler.<br>
   * The local one only prevents current process action from being executed several times.
   */
  private volatile boolean _isAlreadyMakingABaseline;

  /**
   * Constructor.
   */
  public EnvironmentsHub() {
    _artifactEnvironmentRegistry = new ArtifactEnvironmentRegistry();
    _confDirEnvironmentRegistry = new ConfDirEnvironmentRegistry();
    _isAlreadyMakingABaseline = false;
  }

  /**
   * Ask user for baseline name.
   * @param result The result, if any. There is no need to fill result as long as everything is {@link IStatus#OK}.<br>
   *          If the user cancels, a result with severity set to {@link IStatus#WARNING} is expected.<br>
   *          If an error occurs, a status with severity set to {@link IStatus#ERROR} is expected.
   * @param currentContext
   * @param baselineRepositoryHandler_p
   * @param monitor_p
   * @return A baseline properties where the baselineSuffix attribute stands for the new baseline full name, if no error occurred. <code>null</code> otherwise.
   */
  protected BaselineProperties askUserForBaselineProperties(BaselineResult result, Context currentContext,
      final BaselineRepositoryHandler baselineRepositoryHandler_p, IProgressMonitor monitor_p) {
    monitor_p.subTask(Messages.EnvironmentsRegistry_ProgressText_AskingForBaselineName);
    // Ask user for baseline name.
    final String baselineNamePrefix =
        new StringBuilder("ORC_BL_").append(ProjectHelper.generateContextProjectName(currentContext.getName())).append('_').toString(); //$NON-NLS-1$
    final List<Baseline> baselineReferences = baselineRepositoryHandler_p.getAllBaselineReferences();
    BaselineProperties properties = MakeBaselineDialog.chooseBaselineProperties(baselineNamePrefix, new IValidator() {
      /**
       * @see org.eclipse.core.databinding.validation.IValidator#validate(java.lang.Object)
       */
      public IStatus validate(Object value_p) {
        // Status plug-in ID.
        String pluginId = FrameworkActivator.getDefault().getPluginId();
        // Get new name.
        String newName = value_p.toString();
        if ((null == newName) || newName.trim().isEmpty()) {
          return new Status(IStatus.ERROR, pluginId, Messages.EnvironmentsRegistry_ValidationError_NameEmpty);
        }
        // Validate against existing names.
        String chosenBaselineName = getBaselineName(baselineNamePrefix, newName);
        for (Baseline baseline : baselineReferences) {
          if (chosenBaselineName.equalsIgnoreCase(baseline.getName())) {
            return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.EnvironmentsRegistry_ValidationError_NameAlreadyExists,
                chosenBaselineName, baselineRepositoryHandler_p.getRootFolder().getAbsolutePath()));
          }
        }
        return Status.OK_STATUS;
      }
    });
    if (null == properties) {
      // Cancelled by user. No need to retain the previous ok children statuses. No log because of the cancel.
      result.setStatus(new CommandStatus(IStatus.OK, "", null, 0));
      return null;
    }
    // Release memory.
    baselineReferences.clear();
    baselineRepositoryHandler_p.clean();
    // Resulting baseline name.
    properties._baselineSuffix = getBaselineName(baselineNamePrefix, properties._baselineSuffix);
    return properties;
  }

  /**
   * Ask user for baseline path.
   * @param currentContext
   * @param baselineRepositoryHandler_p
   * @param monitor_p
   * @return The selected baseline full path on FileSystem, if no error occurred. <code>null</code> otherwise.
   */
  protected BaselineSelection askUserForBaselineSelection(BaselineRepositoryHandler baselineRepositoryHandler_p, IProgressMonitor monitor_p) {
    monitor_p.subTask(Messages.EnvironmentsRegistry_BaselineSelection_TaskText);
    List<Baseline> references = baselineRepositoryHandler_p.getAllBaselineReferences();
    // Precondition.
    if ((null == references) || references.isEmpty()) {
      return null;
    }
    Map<String, Baseline> baselineReferences = new HashMap<String, Baseline>(references.size());
    for (Baseline baseline : references) {
      baselineReferences.put(baseline.getName(), baseline);
    }
    return ChooseBaselineDialog.chooseBaseline(baselineReferences);
  }

  /**
   * Check all baseline process preconditions, before proceeding.
   * @param baselineRepositoryHandlers_p
   * @param monitor_p
   * @param forceLock_p <code>true</code> to force lock for current process. <code>false</code> otherwise.<br>
   *          Note that lock should be freed by caller.
   * @return The result, if any. There is no need to fill result as long as everything is {@link IStatus#OK}.<br>
   *         If the process is cancelled, a result with severity set to {@link IStatus#WARNING} is expected.<br>
   *         If an error occurs, a status with severity set to {@link IStatus#ERROR} is expected.
   */
  protected CommandStatus checkBaselineProcessConditions(BaselineRepositoryHandler[] baselineRepositoryHandlers_p, IProgressMonitor monitor_p,
      boolean forceLock_p) {
    monitor_p.subTask(Messages.EnvironmentsRegistry_ProgressText_CheckingConditions);
    // Local semaphore.
    if (_isAlreadyMakingABaseline) {
      return new CommandStatus(IStatus.WARNING, Messages.EnvironmentsRegistry_PreConditionsError_MakeBaselineAlreadyRunning, null, 0);
    }
    // Set local semaphore.
    _isAlreadyMakingABaseline = forceLock_p;
    // Try and lock repository first.
    String baselinesRepositoryPath = ProjectActivator.getInstance().getCommandLineArgsHandler().getBaselinesRepositoryPath();
    if (ICommonConstants.EMPTY_STRING.equals(baselinesRepositoryPath)) {
      return new CommandStatus(IStatus.ERROR, Messages.EnvironmentsRegistry_PreConditionsError_NoBaselineRepositorySet, null, 0);
    }
    // Create repository handler.
    final BaselineRepositoryHandler baselineRepositoryHandler = new BaselineRepositoryHandler(baselinesRepositoryPath);
    baselineRepositoryHandlers_p[0] = baselineRepositoryHandler;
    LockState lockState = baselineRepositoryHandler.lock();
    // Make sure lock is released, whatever might happen.
    if (LockState.OWNED != lockState) {
      String errorMessage = null;
      switch (lockState) {
        case ERROR:
          errorMessage = MessageFormat.format(Messages.EnvironmentsRegistry_PreConditionsError_LockFailed, baselinesRepositoryPath);
        break;
        case LOCKED_BY_OTHER:
          errorMessage = MessageFormat.format(Messages.EnvironmentsRegistry_PreConditionsError_LockAlreadyHoldByAnotherSession, baselinesRepositoryPath);
        break;
        case AVAILABLE:
          errorMessage = MessageFormat.format(Messages.EnvironmentsRegistry_PreConditionsError_LockFreeWhenItShouldBeOwned, baselinesRepositoryPath);
        break;
        default:
        break;
      }
      return new CommandStatus(IStatus.ERROR, errorMessage, null, 0);
    }
    CommandStatus status = new CommandStatus(ICommonConstants.EMPTY_STRING, null, 0);
    status.addChild(new CommandStatus(Messages.EnvironmentsHub_LogBaselineProcessConditionsOk, null, 0));
    return status;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener#contextChanged(com.thalesgroup.orchestra.framework.model.contexts.Context,
   *      org.eclipse.core.runtime.IStatus, org.eclipse.core.runtime.IProgressMonitor, boolean)
   */
  public IStatus contextChanged(final Context currentContext_p, IStatus errorStatus_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p) {
    // Clear environment registry.
    EnvironmentActivator.getInstance().getEnvironmentRegistry().clear();
    // Set variables handler for environments to use.
    AbstractVariablesHandler handler = new AbstractVariablesHandler() {
      /**
       * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#getSubstitutedValue(java.lang.String)
       */
      @Override
      public String getSubstitutedValue(String rawValue_p) {
        // Precondition.
        if (null == rawValue_p) {
          return null;
        }
        return DataUtil.getSubstitutedValue(rawValue_p, currentContext_p);
      }
    };
    {
      EnvironmentActivator.getInstance().setVariablesHandler(handler);
      MultiStatus result = new MultiStatus(ModelHandlerActivator.getDefault().getPluginId(), 0, Messages.EnvironmentsHub_SwitchContext_ErrorMessage, null); //$NON-NLS-1$
      try {
        SubMonitor localMonitor = null;
        // Progress reporting.
        if (null != progressMonitor_p) {
          progressMonitor_p.setTaskName(Messages.EnvironmentsHub_SwitchContextMessage);
          localMonitor = SubMonitor.convert(progressMonitor_p, 2);
        }

        // Delegate to artifacts environments first.
        // Set variable type of artefacts environments for the artefact metadata cache
        handler.setVariableType(VariableType.Artefacts);
        IStatus artefactEnvironmentStatus =
            _artifactEnvironmentRegistry.handleContextSwitched(currentContext_p, null != localMonitor ? localMonitor.newChild(1) : null,
                allowUserInteractions_p);
        result.add(artefactEnvironmentStatus);
        // Then to configuration directories environments.
        // Set variable type of confdir environments to be skipped by the artefact metadata cache
        handler.setVariableType(VariableType.ConfigurationDirectories);
        IStatus confDirEnvironmentStatus =
            _confDirEnvironmentRegistry
                .handleContextSwitched(currentContext_p, null != localMonitor ? localMonitor.newChild(1) : null, allowUserInteractions_p);
        result.add(confDirEnvironmentStatus);
      } finally {
        EnvironmentActivator.getInstance().setVariablesHandler(null);
      }
      return result;
    }
  }

  /**
   * Create baseline context at expected repository location.
   * @param result_p The result, if any. There is no need to fill result as long as everything is {@link IStatus#OK}.<br>
   *          If the process is cancelled, a result with severity set to {@link IStatus#WARNING} is expected.<br>
   *          If an error occurs, a status with severity set to {@link IStatus#ERROR} is expected.
   * @param baselineProperties_p User chosen properties.
   * @param currentContext_p
   * @param baselineRepositoryHandler_p
   * @param mainBaselineContext_p
   * @param monitor_p
   */
  protected void createBaseline(BaselineResult result_p, BaselineProperties baselineProperties_p, Context currentContext_p,
      BaselineRepositoryHandler baselineRepositoryHandler_p, MakeBaselineContext mainBaselineContext_p, IProgressMonitor monitor_p) {
    // If baseline catalog is not writable, return an error
    if (!baselineRepositoryHandler_p.isBaselineCatalogWritable()) {
      result_p.getStatus().addChild(
          new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.EnvironmentsRegistry_MBLProcess_Error_BaselineCatalogIsNotWritable,
              baselineRepositoryHandler_p.getBaselineCatalogFilename()), null, 0));
      return;
    }
    monitor_p.setTaskName(Messages.EnvironmentsRegistry_ProgressText_CreatingProject);
    // Get handlers.
    ProjectHandler projectHandler = ProjectActivator.getInstance().getProjectHandler();
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    // Make context copy.
    String baselineRootPath = baselineRepositoryHandler_p.getBaselineRootPath();
    // Keep everything in the current context resource set, as we need to be able to compare references in the process.
    // Make sure resource set is cleaned afterwards.
    ContextsResourceSet localResourceSet = dataHandler.getCurrentContextResourceSet();
    // Keep a pointer to already existing resources.
    List<Resource> beforeMakeBaselineResources = new ArrayList<Resource>(localResourceSet.getResources());
    // Create a new context project.
    RootContextsProject currentContextProject = projectHandler.getProjectFromContextUri(currentContext_p.eResource().getURI());
    // Note that current user is not added as administrator.
    IStatus contextCopyStatus =
        dataHandler.createNewContextStructure(baselineProperties_p._baselineSuffix, baselineRootPath, currentContextProject, localResourceSet, false);
    if (!contextCopyStatus.isOK()) {
      result_p.getStatus().addChild(new CommandStatus(contextCopyStatus));
      return;
    }
    // Load it.
    IPath baselinePath = new Path(baselineRootPath).append(ProjectHelper.generateContextProjectName(baselineProperties_p._baselineSuffix));
    RootContextsProject baselineContextProject =
        projectHandler.getContextFromLocation(baselinePath.toOSString(), ProjectActivator.getInstance().getEditingDomain().getResourceSet());
    if (null == baselineContextProject) {
      result_p.getStatus().addChild(
          new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.EnvironmentsRegistry_MBLProcess_Error_FailedToReachBaselineProject,
              baselineProperties_p._baselineSuffix), null, 0));
      return;
    }
    // The close in finally statement should remain in place so that the project is removed from workspace.
    boolean shouldDeleteProjectOnDisk = false;
    try {
      // Load baseline context.
      Context baselineContext = null;
      try {
        URI baselineContextUri = URI.createURI(baselineContextProject.getAdministratedContext().getUri());
        baselineContext = dataHandler.getContext(baselineContextUri, localResourceSet);
      } catch (Exception exception_p) {
        shouldDeleteProjectOnDisk = true;
        result_p.getStatus().addChild(
            new CommandStatus(new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
                Messages.EnvironmentsRegistry_MBLProcess_Error_FailedToReloadBaselineProject, baselineContextProject.getAdministratedContext().getName()),
                exception_p)));
        return;
      }
      // And populate it.
      IStatus resultStatus = populateBaselineContext(baselineContext, currentContext_p, mainBaselineContext_p, baselineProperties_p);
      if ((null != resultStatus) && !resultStatus.isOK()) {
        shouldDeleteProjectOnDisk = true;
        result_p.getStatus().addChild(new CommandStatus(resultStatus));
        return;
      }
      // Update project definition.
      resultStatus = updateBaselineProjectDefinition(baselineContextProject, mainBaselineContext_p);
      if ((null != resultStatus) && !resultStatus.isOK()) {
        shouldDeleteProjectOnDisk = true;
        result_p.getStatus().addChild(new CommandStatus(resultStatus));
        return;
      }
      // Save/update baseline catalog at specified location.
      baselineRepositoryHandler_p.addBaselineReference(baselineProperties_p._baselineSuffix, baselineProperties_p._baselineDescription,
          baselineContext.getId(), baselineContextProject.getLocation());
    } finally {
      // Clean resource set.
      // Keep a pointer to already existing resources.
      List<Resource> afterMakeBaselineResources = new ArrayList<Resource>(localResourceSet.getResources());
      // Undo modifications on resource set.
      afterMakeBaselineResources.removeAll(beforeMakeBaselineResources);
      for (Resource resource : afterMakeBaselineResources) {
        localResourceSet.getResources().remove(resource);
      }
      // Remove project from memory.
      ProjectActivator.getInstance().getEditingDomain().unloadProject(baselineContextProject);
      // Remove project from workspace.
      ProjectHelper.deleteProject(baselineContextProject._project.getName(), shouldDeleteProjectOnDisk);
    }
  }

  /**
   * Reference specified baseline at specified absolute location.
   * @param baselineRootContextsProject_p The {@link RootContextsProject} standing for the baseline project one.
   * @param baseline_p The baseline reference.
   * @param monitor_p
   * @return
   */
  protected CommandStatus createBaselineReference(RootContextsProject baselineRootContextsProject_p, Baseline baseline_p, IProgressMonitor monitor_p) {
    // Switch to user mode, if needed.
    DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        // Check current state.
        if (ProjectActivator.getInstance().isAdministrator()) {
          // Switch to user.
          ProjectActivator.getInstance().changeAdministratorMode();
        }
      }
    }, false);
    // First, make sure particiaption does not exist yet.
    String contextName = baseline_p.getName();
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    RootElement contextsInWorkspace = dataHandler.getAllContextsInWorkspace();
    for (RootContextsProject rootContextsProject : contextsInWorkspace.getProjects()) {
      // Participation already exists.
      if (baselineRootContextsProject_p.equals(rootContextsProject)
          && (null != rootContextsProject.getUserContextUri(ProjectActivator.getInstance().getCurrentUserId()))) {
        return new CommandStatus(IStatus.WARNING, MessageFormat.format(Messages.EnvironmentsHub_UseBaseline_AsReference_AlreadyExistsInWorkspace, contextName),
            null, 0);
      }
    }

    // Run baseline dialog box
    final UseBaselineProperties referenceProperties =
        UseBaselineDialog.getUseBaselineParameters(baselineRootContextsProject_p, baseline_p.getName(), BaselineSelectionType.REFERENCE);
    if (null == referenceProperties) {
      // Cancelled by user.
      return new CommandStatus(IStatus.OK, "", null, 0);
    }

    final Context participation = referenceProperties._createdUseBaselineContext;

    // Populate participation
    try {
      // Handler to use, if needed.
      IVariablesHandler handler = new AbstractVariablesHandler() {
        /**
         * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractVariablesHandler#getSubstitutedValue(java.lang.String)
         */
        @Override
        public String getSubstitutedValue(String rawValue_p) {
          // Precondition.
          if (null == rawValue_p) {
            return null;
          }
          return DataUtil.getSubstitutedValue(rawValue_p, participation);
        }
      };
      EnvironmentActivator.getInstance().setVariablesHandler(handler);
      // Do populate it.
      IStatus status = populateBaselineReferenceContext(referenceProperties, baseline_p);
      // On error, remove baseline participation
      if (!status.isOK()) {
        DeleteContextServiceCommand command = new DeleteContextServiceCommand(referenceProperties._createdUseBaselineContext, true);
        boolean canExecuteDelete = command.canExecute();
        if (canExecuteDelete) {
          command.execute();
        }
      }
      return new CommandStatus(status);
    } finally {
      EnvironmentActivator.getInstance().setVariablesHandler(null);
    }
  }

  /**
   * Create a new context from the specified baseline context.
   * @param baselineRootContextsProject_p
   * @param baseline_p
   * @param monitor_p
   * @return
   */
  protected CommandStatus createBaselineStartingPoint(RootContextsProject baselineRootContextsProject_p, Baseline baseline_p, IProgressMonitor monitor_p) {
    final UseBaselineProperties startingPointProperties =
        UseBaselineDialog.getUseBaselineParameters(baselineRootContextsProject_p, baseline_p.getName(), BaselineSelectionType.STARTING_POINT);
    if (null != startingPointProperties) {
      // Populate it.
      try {
        // Handler to use, if needed.
        IVariablesHandler handler = new AbstractVariablesHandler() {
          /**
           * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractVariablesHandler#getSubstitutedValue(java.lang.String)
           */
          @Override
          public String getSubstitutedValue(String rawValue_p) {
            // Precondition.
            if (null == rawValue_p) {
              return null;
            }
            return DataUtil.getSubstitutedValue(rawValue_p, startingPointProperties._createdUseBaselineContext);
          }
        };
        EnvironmentActivator.getInstance().setVariablesHandler(handler);
        // Do populate it.
        IStatus status = populateBaselineStartingPointContext(startingPointProperties, monitor_p);
        // On error, remove baseline context
        if (!status.isOK()) {
          // In starting point, remove context from the workspace and from disk
          DeleteContextServiceCommand command = new DeleteContextServiceCommand(startingPointProperties._createdUseBaselineContext, true, true);
          boolean canExecuteDelete = command.canExecute();
          if (canExecuteDelete) {
            command.execute();
          }
        }
        return new CommandStatus(status);
      } finally {
        EnvironmentActivator.getInstance().setVariablesHandler(null);
      }
    }
    // Cancelled by user.
    return new CommandStatus(IStatus.OK, "", null, 0);
  }

  /**
   * Get unique {@link ArtifactEnvironmentRegistry} instance.
   * @return
   */
  public ArtifactEnvironmentRegistry getArtifactEnvironmentRegistry() {
    return _artifactEnvironmentRegistry;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getArtifactsMetadata(com.thalesgroup.orchestra.framework.connector.CommandContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public CommandStatus getArtifactsMetadata(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    return _artifactEnvironmentRegistry.getArtifactsMetadata(context_p, progressMonitor_p);
  }

  /**
   * Get baseline name for specified parameters.
   * @param prefix_p
   * @param suffix_p
   * @return
   */
  protected String getBaselineName(String prefix_p, String suffix_p) {
    return prefix_p + suffix_p;
  }

  /**
   * Get unique {@link ConfDirEnvironmentRegistry} instance.
   * @return
   */
  public ConfDirEnvironmentRegistry getConfDirEnvironmentRegistry() {
    return _confDirEnvironmentRegistry;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getRootArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public CommandStatus getRootArtifacts(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    return _artifactEnvironmentRegistry.getRootArtifacts(context_p, progressMonitor_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#isHandlingArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus isHandlingArtifacts(CommandContext context_p) {
    return _artifactEnvironmentRegistry.isHandlingArtifacts(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#makeBaseline(com.thalesgroup.orchestra.framework.environment.BaselineContext)<br>
   *      Input {@link BaselineContext} is of no use.<br>
   *      Callers should use <code>null</code>, and rely on implementation.
   */
  public BaselineResult makeBaseline(BaselineContext baselineContext_p, IProgressMonitor progressMonitor_p) {
    // Overall result.
    BaselineResult result = new BaselineResult();
    // Log result.
    CommandStatus logResult;
    String baselineNameReminder = null;
    BaselineRepositoryHandler[] baselineRepositoryHandlers = new BaselineRepositoryHandler[] { null };
    // Get data handler.
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    // First Step : make sure current context is valid.
    final Context currentContext = dataHandler.getCurrentContext();
    if (currentContext.eResource().isDefault()) {
      result
          .setStatus(new CommandStatus(IStatus.ERROR, Messages.EnvironmentsRegistry_MakeBaseline_PreCondition_Error_CannotBeAppliedToDefaultContext, null, 0));
      return result;
    }
    // Total number of operations.
    SubMonitor monitor = SubMonitor.convert(progressMonitor_p, 100);
    try { // Make sure lock is released, whatever might happen.
      // Handler to use, if needed.
      IVariablesHandler handler = new AbstractVariablesHandler() {
        /**
         * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractVariablesHandler#getSubstitutedValue(java.lang.String)
         */
        @Override
        public String getSubstitutedValue(String rawValue_p) {
          // Precondition.
          if (null == rawValue_p) {
            return null;
          }
          return DataUtil.getSubstitutedValue(rawValue_p, currentContext);
        }
      };
      EnvironmentActivator.getInstance().setVariablesHandler(handler);

      // Check make baseline various conditions.
      result.setStatus(checkBaselineProcessConditions(baselineRepositoryHandlers, monitor.newChild(5), true));
      if ((null != result.getStatus()) && !result.getStatus().isOK()) {
        return result;
      }
      // Do validate.
      validateCurrentContextForMakeBaseline(result, currentContext, monitor.newChild(5));
      if ((null != result.getStatus()) && !result.getStatus().isOK()) {
        return result;
      }
      // Validation is ok, add child status to log.
      result.getStatus().addChild(new CommandStatus(IStatus.OK, Messages.EnvironmentsHub_LogContextValidationOk, null, 0));
      // Get baseline name.
      BaselineProperties baselineProperties = askUserForBaselineProperties(result, currentContext, baselineRepositoryHandlers[0], monitor.newChild(5));
      if ((null != result.getStatus()) && !result.getStatus().isOK()) {
        return result;
      }
      // /!\ Log possible at this point of the code. Nothing to log before.
      // Create in-use baseline context.
      MakeBaselineContext mainBaselineContext = new MakeBaselineContext(baselineProperties);
      // Make baseline on artifacts first.
      BaselineResult makeBaselineResult = _artifactEnvironmentRegistry.makeBaseline(mainBaselineContext, monitor.newChild(30));
      if (!makeBaselineResult.getStatus().isOK()) {
        CommandStatus errorStatus = null;
        if (makeBaselineResult.getStatus().isMultiStatus()) {
          // Add all sub-statuses from makeBaselineResult to the main status.
          for (CommandStatus subStatus : makeBaselineResult.getStatus().getChildren()) {
            result.getStatus().addChild(subStatus);
            // Find the error status that caused the makebaseline failure.
            if (subStatus.getSeverity() == 4)
              errorStatus = subStatus;
          }
        } else {
          result.getStatus().addChild(makeBaselineResult.getStatus());
          errorStatus = makeBaselineResult.getStatus();
        }
        logResult = logAllStatuses(baselineProperties, result.getStatus(), baselineRepositoryHandlers[0], false);
        String logPath;
        if (null != logResult && logResult.isOK())
          logPath = System.getProperty("logname") + ".log"; //$NON-NLS-1$ //$NON-NLS-2$
        else {
          // Error with logger, Makebaseline aborted.
          result.setStatus(logResult);
          return result;
        }
        // Return the error status.
        if (null != errorStatus)
          result.setStatus(new CommandStatus(IStatus.ERROR, errorStatus.getMessage()
                                                            + MessageFormat.format(Messages.EnvironmentsHub_LogMessageLocation, logPath), errorStatus.getUri(),
              errorStatus.getCode()));
        return result;
      }
      // Add all sub-statuses from makeBaselineResult to the main status.
      for (CommandStatus subStatus : makeBaselineResult.getStatus().getChildren()) {
        result.getStatus().addChild(subStatus);
      }
      // Then on configuration directories.
      makeBaselineResult = _confDirEnvironmentRegistry.makeBaseline(mainBaselineContext, monitor.newChild(30));
      /*
       * makeBaselineResult = new BaselineResult(); makeBaselineResult.setStatus(new CommandStatus(IStatus.OK, "", null, 0)); //$NON-NLS-1$
       * makeBaselineResult.getStatus().addChild(new CommandStatus(IStatus.ERROR, "NO NO NONONONONONONONO!!!", null, 0)); //$NON-NLS-1$
       */
      if (!makeBaselineResult.getStatus().isOK()) {
        CommandStatus errorStatus = null;
        // Add all sub-statuses from makeBaselineResult to the main status.
        for (CommandStatus subStatus : makeBaselineResult.getStatus().getChildren()) {
          result.getStatus().addChild(subStatus);
          // Find the error status that caused the makebaseline failure.
          if (subStatus.getSeverity() == 4)
            errorStatus = subStatus;
        }
        logResult = logAllStatuses(baselineProperties, result.getStatus(), baselineRepositoryHandlers[0], false);
        String logPath;
        if (null != logResult && logResult.isOK())
          logPath = System.getProperty("logname") + ".log"; //$NON-NLS-1$ //$NON-NLS-2$
        else {
          // Error with logger, Makebaseline aborted.
          result.setStatus(logResult);
          return result;
        }
        // Return the error status.
        if (null != errorStatus)
          result.setStatus(new CommandStatus(IStatus.ERROR, errorStatus.getMessage()
                                                            + MessageFormat.format(Messages.EnvironmentsHub_LogMessageLocation, logPath), errorStatus.getUri(),
              errorStatus.getCode()));
        return result;
      }
      for (CommandStatus subStatus : makeBaselineResult.getStatus().getChildren()) {
        result.getStatus().addChild(subStatus);
      }

      // Do create baseline (at ODM level).
      createBaseline(result, baselineProperties, currentContext, baselineRepositoryHandlers[0], mainBaselineContext, monitor.newChild(25));
      if ((null != result.getStatus()) && !result.getStatus().isOK()) {
        CommandStatus errorStatus = null;
        // Add all sub-statuses from makeBaselineResult to the main status.
        for (CommandStatus subStatus : makeBaselineResult.getStatus().getChildren()) {
          result.getStatus().addChild(subStatus);
          // Find the error status that caused the makebaseline failure.
          if (subStatus.getSeverity() == 4)
            errorStatus = subStatus;
        }
        logResult = logAllStatuses(baselineProperties, result.getStatus(), baselineRepositoryHandlers[0], false);
        String logPath;
        if (null != logResult && logResult.isOK())
          logPath = System.getProperty("logname") + ".log"; //$NON-NLS-1$ //$NON-NLS-2$
        else {
          // Error with logger, Makebaseline aborted.
          result.setStatus(logResult);
          return result;
        }
        // Return the error status.
        if (null != errorStatus)
          result.setStatus(new CommandStatus(IStatus.ERROR, errorStatus.getMessage()
                                                            + MessageFormat.format(Messages.EnvironmentsHub_LogMessageLocation, logPath), errorStatus.getUri(),
              errorStatus.getCode()));
        return result;
      }
      // Retain baseline name.
      baselineNameReminder = baselineProperties._baselineSuffix;

      // log overall statuses.
      logResult = logAllStatuses(baselineProperties, result.getStatus(), baselineRepositoryHandlers[0], true);
    } finally {
      if (null != baselineRepositoryHandlers[0]) {
        // Release locks.
        baselineRepositoryHandlers[0].unlock();
        // And free memory.
        baselineRepositoryHandlers[0].clean();
      }
      _isAlreadyMakingABaseline = false;
      // Release variables handler.
      EnvironmentActivator.getInstance().setVariablesHandler(null);
    }
    String logPath;
    if (null != logResult && logResult.isOK()) {
      logPath = System.getProperty("logname") + ".log"; //$NON-NLS-1$ //$NON-NLS-2$
      result.setStatus(new CommandStatus(IStatus.OK, MessageFormat.format(Messages.EnvironmentsHub_MakeBaseline_Successful_Message, baselineNameReminder)
                                                     + MessageFormat.format(Messages.EnvironmentsHub_LogMessageLocation, logPath), null, 0));
    } else
      result.setStatus(new CommandStatus(IStatus.WARNING, MessageFormat.format(Messages.EnvironmentsHub_MakeBaseline_Successful_Message, baselineNameReminder)
                                                          + Messages.BaselineLogFailed, null, 0));
    return result;
  }

  private CommandStatus logAllStatuses(BaselineProperties baselineProperties_p, CommandStatus makeBaselineStatus_p,
      BaselineRepositoryHandler baselineRepositoryHandler_p, boolean contextCreated_p) {
    // Overall result.
    CommandStatus result;
    // If baseline catalog is not writable, return an error
    if (!baselineRepositoryHandler_p.isBaselineCatalogWritable()) {
      result =
          new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.EnvironmentsRegistry_MBLProcess_Error_BaselineCatalogIsNotWritable,
              baselineRepositoryHandler_p.getBaselineCatalogFilename()), null, 0);
      return result;
    }
    try {
      // Check project existence (if no project location, find in working directory, i.e. workspace).
      final String projectDestDirectory =
          (baselineRepositoryHandler_p.getBaselineRootPath() != null) ? baselineRepositoryHandler_p.getBaselineRootPath() : Platform.getLocation().toString();
      // Create directory for makeBaseline process.
      final String baselineContentDestination =
          projectDestDirectory + "\\" + baselineProperties_p._baselineSuffix.replaceAll("\\W", ICommonConstants.EMPTY_STRING); //$NON-NLS-1$ //$NON-NLS-2$

      if (!contextCreated_p) {
        final File baselineContentDestinationFile = new File(baselineContentDestination);
        if (baselineContentDestinationFile.exists()) {
          result =
              new CommandStatus(new Status(IStatus.WARNING, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
                  Messages.EnvironmentsHub_Create_DirectoryAlreadyExists_Error, baselineProperties_p._baselineSuffix)));
          return result;
        }
        if (!baselineContentDestinationFile.mkdirs()) {
          result =
              new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.EnvironmentsHub_ErrorCreatingDirectory, baselineProperties_p._baselineSuffix),
                  null, 0);
          return result;
        }
      }
      // Initialize the logger.
      OrchestraEnvironmentsHubMakeBaselineLogger.initialize(baselineContentDestination);

      int severity = makeBaselineStatus_p.getSeverity();
      if (makeBaselineStatus_p.isMultiStatus()) {
        for (CommandStatus subStatus : makeBaselineStatus_p.getChildren()) {
          int subSeverity = subStatus.getSeverity();
          switch (subSeverity) {
            case 0:
              OrchestraEnvironmentsHubMakeBaselineLogger.log(baselineProperties_p._baselineSuffix, subStatus.getMessage(),
                  OrchestraEnvironmentsHubMakeBaselineLogger.Loglevel.INFO);
            break;
            case 2:
              OrchestraEnvironmentsHubMakeBaselineLogger.log(baselineProperties_p._baselineSuffix, subStatus.getMessage(),
                  OrchestraEnvironmentsHubMakeBaselineLogger.Loglevel.WARN);
            break;
            case 4:
              OrchestraEnvironmentsHubMakeBaselineLogger.log(baselineProperties_p._baselineSuffix, subStatus.getMessage(),
                  OrchestraEnvironmentsHubMakeBaselineLogger.Loglevel.ERROR);
            break;
            default:
              OrchestraEnvironmentsHubMakeBaselineLogger.log(baselineProperties_p._baselineSuffix, "Severity not exploitable", //$NON-NLS-1$
                  OrchestraEnvironmentsHubMakeBaselineLogger.Loglevel.ALL);
          }
        }
      }
      switch (severity) {
        case 0:
          OrchestraEnvironmentsHubMakeBaselineLogger.log(baselineProperties_p._baselineSuffix,
              MessageFormat.format(Messages.EnvironmentsHub_BaselineCreationOk, baselineProperties_p._baselineSuffix),
              OrchestraEnvironmentsHubMakeBaselineLogger.Loglevel.INFO);
        break;
        case 2:
          OrchestraEnvironmentsHubMakeBaselineLogger.log(baselineProperties_p._baselineSuffix,
              "Orchestra baseline not created. See the details for the whole process", OrchestraEnvironmentsHubMakeBaselineLogger.Loglevel.WARN); //$NON-NLS-1$
        break;
        case 4:
          OrchestraEnvironmentsHubMakeBaselineLogger.log(baselineProperties_p._baselineSuffix,
              "Orchestra baseline not created. See the details for the whole process", OrchestraEnvironmentsHubMakeBaselineLogger.Loglevel.ERROR); //$NON-NLS-1$
        break;
        default:
          OrchestraEnvironmentsHubMakeBaselineLogger.log(baselineProperties_p._baselineSuffix, "Severity not exploitable", //$NON-NLS-1$
              OrchestraEnvironmentsHubMakeBaselineLogger.Loglevel.ALL);
      }
      return new CommandStatus(ICommonConstants.EMPTY_STRING, null, 0);
    } catch (Exception e) {
      return new CommandStatus(IStatus.ERROR, e.getMessage(), null, 0);
    }
  }

  /**
   * Populate baseline context with expected contents.
   * @param baselineContext_p
   * @param currentContext_p
   * @param mainBaselineContext_p
   * @param baselineProperties_p
   * @return
   */
  protected IStatus populateBaselineContext(Context baselineContext_p, Context currentContext_p, MakeBaselineContext mainBaselineContext_p,
      BaselineProperties baselineProperties_p) {
    try {
      // Copy current context content into baseline one.
      // This is only applicable to user context, as administrator context is already inherited by the structure creation.
      if (!ProjectActivator.getInstance().isAdministrator()) {
        DataUtil.makeCopyOfOwnedContents(currentContext_p, baselineContext_p, false);
      }
      // Add referencing baseline contents.
      // Even if the make baseline result is identical to current environments values, create overriding environment variables so that the baseline context
      // remains coherent even if a synchronize is done (once as a starting point has been issued).
      _artifactEnvironmentRegistry.populateBaselineContext(baselineContext_p, mainBaselineContext_p);
      _confDirEnvironmentRegistry.populateBaselineContext(baselineContext_p, mainBaselineContext_p);
      // Set description, if any.
      if (null != baselineProperties_p._baselineDescription) {
        baselineContext_p.setDescription(baselineProperties_p._baselineDescription);
      }
      // Save resource.
      baselineContext_p.eResource().save(null);
      return Status.OK_STATUS;
    } catch (Exception exception_p) {
      return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
          Messages.EnvironmentsRegistry_MBLProcess_Error_FailedToPopulateBaselineProject, baselineContext_p.getName()), exception_p);
    }
  }

  /**
   * Populate baseline reference context with expected contents.
   * @param baselineReferenceContext_p
   * @param baseline_p
   * @return
   */
  protected IStatus populateBaselineReferenceContext(UseBaselineProperties referenceProperties_p, Baseline baseline_p) {
    Context baselineReferenceContext = referenceProperties_p._createdUseBaselineContext;
    try {
      // Add referencing baseline contents.
      IStatus errorStatus = _artifactEnvironmentRegistry.populateBaselineReferenceContext(referenceProperties_p);
      if (!errorStatus.isOK()) {
        return errorStatus;
      }
      errorStatus = _confDirEnvironmentRegistry.populateBaselineReferenceContext(referenceProperties_p);
      if (!errorStatus.isOK()) {
        return errorStatus;
      }
      // Add context description.
      baselineReferenceContext.setDescription(baseline_p.getDescription());
      // Save resource.
      baselineReferenceContext.eResource().save(null);
      return Status.OK_STATUS;
    } catch (Exception exception_p) {
      return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
          Messages.EnvironmentsHub_UseBaselineProcess_Error_FailedToPopulateBaselineReferenceContext, baselineReferenceContext.getName()), exception_p);
    }
  }

  /**
   * Populate baseline starting point context with expected contents.
   * @param startingPointProperties_p
   * @param monitor_p
   * @return Status
   */
  protected IStatus populateBaselineStartingPointContext(UseBaselineProperties startingPointProperties_p, IProgressMonitor monitor_p) {
    SubMonitor monitor = SubMonitor.convert(monitor_p, 2);
    Context createdStartingPointContext = startingPointProperties_p._createdUseBaselineContext;
    try {
      // Add referencing baseline contents.
      IStatus errorStatus = _artifactEnvironmentRegistry.populateBaselineStartingPointContext(startingPointProperties_p, monitor.newChild(1));
      if (!errorStatus.isOK()) {
        return errorStatus;
      }
      errorStatus = _confDirEnvironmentRegistry.populateBaselineStartingPointContext(startingPointProperties_p, monitor.newChild(1));
      if (!errorStatus.isOK()) {
        return errorStatus;
      }
      // Save resource.
      ContextsResourceImpl resource = createdStartingPointContext.eResource();
      resource.save(null);
      // In user mode, make sure a participation is created.
      ProjectActivator projectActivator = ProjectActivator.getInstance();
      if (!projectActivator.isAdministrator()) {
        // Create participation.
        RootContextsProject rootContextsProject = projectActivator.getProjectHandler().getProjectFromContextUri(resource.getURI());
        IStatus status = ModelHandlerActivator.getDefault().getDataHandler().participateInAProject(createdStartingPointContext.getName(), rootContextsProject);
        return status;
      }
      return Status.OK_STATUS;
    } catch (Exception exception_p) {
      return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
          Messages.EnvironmentsHub_BaselineStartingPoint_Error_CantPopulateBaseline, createdStartingPointContext.getName()), exception_p);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener#preContextChange(com.thalesgroup.orchestra.framework.model.contexts.Context,
   *      org.eclipse.core.runtime.IProgressMonitor, boolean)
   */
  public void preContextChange(Context futureContext_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p) {
    // Nothing to do.
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironment#transcript(com.thalesgroup.orchestra.framework.connector.CommandContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public CommandStatus transcript(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    return _artifactEnvironmentRegistry.transcript(context_p, progressMonitor_p);
  }

  /**
   * Update baseline project definition for baseline context.
   * @param baselineContextProject_p
   * @param mainBaselineContext_p
   * @return
   */
  protected IStatus updateBaselineProjectDefinition(RootContextsProject baselineContextProject_p, MakeBaselineContext mainBaselineContext_p) {
    String pluginId = FrameworkActivator.getDefault().getPluginId();
    // Modify nature so that it is identified as a baseline context.
    boolean natureAdded = ProjectHelper.addNatureToProject(baselineContextProject_p._project, BaselineNature.BASELINE_PROJECT_NATURE_ID);
    if (!natureAdded) {
      return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.EnvironmentsRegistry_MBLProcess_Error_FailedToUpdateBaselineProjectNature,
          mainBaselineContext_p.getBaselineName()));
    }
    // Modify context project description, so that there is no administrator declared for this context.
    baselineContextProject_p._contextsProject.getAdministrators().clear();
    // Save project description.
    try {
      ProjectActivator.getInstance().getEditingDomain().saveResource(baselineContextProject_p._contextsProject.eResource(), true);
    } catch (IOException exception_p) {
      return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.EnvironmentsRegistry_MBLProcess_Error_FailedToSaveModifiedBaselineProject,
          mainBaselineContext_p.getBaselineName()));
    }
    return Status.OK_STATUS;
  }

  /**
   * Use an existing baseline.
   * @param progressMonitor_p
   * @return
   */
  public CommandStatus useBaseline(IProgressMonitor progressMonitor_p) {
    // Result.
    CommandStatus result = new CommandStatus(Messages.EnvironmentsRegistry_BaselineUsage_WrapUp_Message, null, 0);
    // Handler.
    BaselineRepositoryHandler[] baselineRepositoryHandlers = new BaselineRepositoryHandler[] { null };
    // Total number of operations.
    SubMonitor monitor = SubMonitor.convert(progressMonitor_p, 100);
    try { // Make sure lock is released, whatever might happen.
      // Check make baseline various conditions.
      CommandStatus commandStatus = checkBaselineProcessConditions(baselineRepositoryHandlers, monitor.newChild(15), false);
      // Preconditions.
      if (!commandStatus.isOK()) {
        result.addChild(commandStatus);
        return result;
      }
      // Get baseline to reference.
      BaselineSelection baselineSelection = askUserForBaselineSelection(baselineRepositoryHandlers[0], monitor.newChild(15));
      if (null == baselineSelection) {
        result.addChild(new CommandStatus(IStatus.OK, "", null, 0));
        return result;
      }
      // Get baseline.
      Baseline baseline = baselineSelection._selectedBaseline;
      // Get project absolute path.
      IPath absolutePath = new Path(baselineRepositoryHandlers[0].getRootFolder().getAbsolutePath()).append(baseline.getProjectRelativePath());
      SubMonitor useBaselineMonitor = monitor.newChild(70);
      // Get baseline context from location.
      final RootContextsProject baselineRootContextsProject =
          ProjectActivator.getInstance().getProjectHandler().getContextFromLocation(absolutePath.toOSString(), null);
      // There should be a context.
      if (null == baselineRootContextsProject) {
        return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.EnvironmentsRegistry_BaselineReference_InvalidBaselineLocation, absolutePath),
            null, 0);
      }
      switch (baselineSelection._selectionType) {
        case STARTING_POINT:
          commandStatus = createBaselineStartingPoint(baselineRootContextsProject, baseline, useBaselineMonitor);
          if (!commandStatus.isOK()) {
            result.addChild(commandStatus);
          }
        break;
        case REFERENCE:
          commandStatus = createBaselineReference(baselineRootContextsProject, baseline, useBaselineMonitor);
          if (!commandStatus.isOK()) {
            result.addChild(commandStatus);
          }
          //$FALL-THROUGH$
        default:
        break;
      }
    } finally {
      if (null != baselineRepositoryHandlers[0]) {
        // Release locks.
        baselineRepositoryHandlers[0].unlock();
        // And free memory.
        baselineRepositoryHandlers[0].clean();
      }
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironment#useTranscription()
   */
  @Override
  public boolean useTranscription() {
    return true;
  }

  /**
   * Validate current context against Make Baseline specific constraints.<br>
   * Note that regular validation process is also applied in this case.
   * @param result The result, if any. There is no need to fill result as long as everything is {@link IStatus#OK}.<br>
   *          If the process is cancelled, a result with severity set to {@link IStatus#WARNING} is expected.<br>
   *          If an error occurs, a status with severity set to {@link IStatus#ERROR} is expected.
   * @param currentContext
   * @param monitor_p
   */
  protected void validateCurrentContextForMakeBaseline(BaselineResult result, Context currentContext, IProgressMonitor monitor_p) {
    monitor_p.subTask(Messages.EnvironmentsRegistry_ProgressText_ValidatingContext);
    // Create specific validation context and handler.
    MakeBaselineValidationContext validationContext = new MakeBaselineValidationContext(currentContext);
    MakeBaselineValidationHandler validationHandler = new MakeBaselineValidationHandler(validationContext);
    // Search for environments that do not support Make Baseline process.
    _artifactEnvironmentRegistry.retainMakeBaselineNonCompliantEnvironments(validationContext);
    _confDirEnvironmentRegistry.retainMakeBaselineNonCompliantEnvironments(validationContext);
    // Then validate context.
    ValidationHelper.getInstance().validateElement(currentContext, currentContext, ValidationType.MAKE_BASELINE_TYPE, validationHandler);
    // Check whether user cancelled make baseline for validation reasons.
    if (!validationHandler.shouldProceed()) {
      // Cancelled by user. (No need to retain previous OK children because no log at this time.
      result.setStatus(new CommandStatus(IStatus.CANCEL, "", null, 0));
      return;
    }
  }

  /**
   * The {@link BaselineContext} initiated by the Make Baseline process.<br>
   * It contains all baselined environments for current baselining process.
   * @author t0076261
   */
  protected class MakeBaselineContext extends BaselineContext {
    /**
     * Environment declaration ID to associated {@link BaselineContext}.
     */
    protected Map<String, BaselineContext> _declarationIdToBaselineContext;

    /**
     * Constructor.
     * @param properties_p
     */
    public MakeBaselineContext(BaselineProperties properties_p) {
      super(properties_p._baselineSuffix, properties_p._baselineDescription, null);
      _declarationIdToBaselineContext = new HashMap<String, BaselineContext>(0);
    }

    /**
     * Add result for specified environment.
     * @param environment_p
     * @param baselineResult_p
     */
    public void addResultFor(IEnvironment environment_p, BaselineResult baselineResult_p) {
      // Preconditions.
      if ((null == environment_p) || (null == baselineResult_p)) {
        return;
      }
      // Get baseline context.
      BaselineContext baselineContext = _declarationIdToBaselineContext.get(environment_p.getDeclarationId());
      // Create baseline environment attributes.
      BaselineEnvironmentAttributes baselineEnvironmentAttributes = baselineContext.new BaselineEnvironmentAttributes();
      // Fill it.
      {
        // Set result.
        baselineEnvironmentAttributes._baselineResult = baselineResult_p;
        // Set declaration ID.
        baselineEnvironmentAttributes._environmentId = environment_p.getDeclarationId();
        // Set runtime ID.
        baselineEnvironmentAttributes._runtimeId = environment_p.getRuntimeId();
        // Set raw attributes.
        // Raw attributes are indeed currently resolved ones in this case.
        baselineEnvironmentAttributes._attributes = environment_p.getAttributes();
        // Set expanded attributes.
        // Nothing to set in here.
        baselineEnvironmentAttributes._expandedAttributes = null;
      }
      baselineContext.addAttributes(environment_p.getRuntimeId(), baselineEnvironmentAttributes);
    }

    /**
     * Get attributes standing for a baseline creation for specified source environment.<br>
     * Note that this method is intended to be used by the centralized make baseline process once all environment baselines have been set.<br>
     * In particular, this method is not safe against concurrent modifications (such as the {@link #getAttributesFor(String)} could trigger).
     * @param environment_p
     * @return
     */
    public BaselineEnvironmentAttributes getBaselinedAttributesFor(IEnvironment environment_p) {
      // Precondition.
      if (null == environment_p) {
        return null;
      }
      // Get declaration ID.
      String declarationId = environment_p.getDeclarationId();
      // Get existing context, if any.
      BaselineContext baselineContext = _declarationIdToBaselineContext.get(declarationId);
      // Could not find any matching context.
      if (null == baselineContext) {
        return null;
      }
      return baselineContext.getAttributesFor(environment_p.getRuntimeId());
    }

    /**
     * Get {@link BaselineContext} that applies to specified environment.
     * @param environment_p
     * @return
     */
    public BaselineContext getContextFor(IEnvironment environment_p) {
      // Precondition.
      if (null == environment_p) {
        return null;
      }
      // Get declaration ID.
      String declarationId = environment_p.getDeclarationId();
      // Get existing context, if any.
      BaselineContext baselineContext = _declarationIdToBaselineContext.get(declarationId);
      // Create context if needed.
      if (null == baselineContext) {
        baselineContext = new BaselineContext(getBaselineName(), getBaselineDescription(), declarationId);
        _declarationIdToBaselineContext.put(declarationId, baselineContext);
      }
      return baselineContext;
    }
  }

  /**
   * A validation handler dedicated to the Make Baseline process.<br>
   * It makes sure no (Operating System) environment variable is used within the current context.<br>
   * If some are used, the user is asked to either cancel or proceed.
   * @author t0076261
   */
  protected class MakeBaselineValidationHandler extends AbstractValidationHandler {
    /**
     * Should make baseline process be proceeded ?
     */
    protected boolean _shouldProceed;
    /**
     * Validation context to use.
     */
    protected MakeBaselineValidationContext _validationContext;

    /**
     * Constructor.
     * @param validationContext_p
     */
    public MakeBaselineValidationHandler(MakeBaselineValidationContext validationContext_p) {
      _validationContext = validationContext_p;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.validation.AbstractValidationHandler#createValidationContext(com.thalesgroup.orchestra.framework.model.contexts.Context)
     */
    @Override
    public ValidationContext createValidationContext(Context askingContext_p) {
      return _validationContext;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.validation.AbstractValidationHandler#handleValidationResult(com.thalesgroup.orchestra.framework.model.validation.ValidationContext,
     *      org.eclipse.core.runtime.IStatus)
     */
    @Override
    public void handleValidationResult(ValidationContext validationContext_p, IStatus validationStatus_p) {
      // This is actually a MakeBaselineValidationContext.
      MakeBaselineValidationContext context = (MakeBaselineValidationContext) validationContext_p;
      // Get references to (OS) environment variables.
      Map<String, Collection<String>> environmentVariablesReferences = context.getEnvironmentVariablesReferences();
      // Get non-compliant environments.
      Collection<String> nonCompliantEnvironments = context.getNonCompliantEnvironments();
      // Nothing to look at, proceed with the Make Baseline process.
      if (environmentVariablesReferences.isEmpty() && nonCompliantEnvironments.isEmpty() && ((null == validationStatus_p) || validationStatus_p.isOK())) {
        _shouldProceed = true;
        return;
      }
      // Plug-in ID.
      String pluginId = FrameworkActivator.getDefault().getPluginId();
      // Create root status.
      final MultiStatus rootStatus = new MultiStatus(pluginId, 0, Messages.EnvironmentsRegistry_ValidationWarning_DialogMessage, null);
      // Non compliant environments case.
      if (!nonCompliantEnvironments.isEmpty()) {
        MultiStatus nonCompliantStatus = new MultiStatus(pluginId, 0, Messages.EnvironmentsRegistry_ValidationWarning_NotCompatibleEnvironments, null);
        for (String environmentAsString : nonCompliantEnvironments) {
          nonCompliantStatus.add(new Status(IStatus.WARNING, pluginId, environmentAsString));
        }
        rootStatus.add(nonCompliantStatus);
      }
      // Environment using OS env. variables case.
      if (!environmentVariablesReferences.isEmpty()) {
        MultiStatus envVarStatus = new MultiStatus(pluginId, 0, Messages.EnvironmentsRegistry_ValidationWarning_EnvironmentVariablesUsageWrapUp, null);
        // Cycle through OS environment variables references.
        for (Entry<String, Collection<String>> entry : environmentVariablesReferences.entrySet()) {
          // Create a multi-status for the variable.
          MultiStatus variableStatus =
              new MultiStatus(pluginId, 0, MessageFormat.format(Messages.EnvironmentsRegistry_ValidationWarning_EnvironmentVariableUsage, entry.getKey()), null);
          // Then add a sub-status per reference to an OS environment variable.
          for (String envVarName : entry.getValue()) {
            variableStatus.add(new Status(IStatus.WARNING, pluginId, envVarName));
          }
          // Add variable status to root one.
          envVarStatus.add(variableStatus);
        }
        // Add status to root one.
        rootStatus.add(envVarStatus);
      }
      // Regular validation case.
      if (null != validationStatus_p) {
        // transform the given status to a simple multistatus. To remove the EMF head status of each inner status
        if (validationStatus_p.isMultiStatus()) {
          for (IStatus childStatus : validationStatus_p.getChildren()) {
            if (childStatus.isMultiStatus()) {
              rootStatus.addAll(childStatus);
            } else {
              rootStatus.add(childStatus);
            }
          }
        }
      }
      // Display to user.
      DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
        /**
         * @see java.lang.Runnable#run()
         */
        public void run() {
          ValidationDialog dialog =
              new ValidationDialog(getDisplay().getActiveShell(), Messages.EnvironmentsRegistry_ValidationDialog_Title, null, rootStatus, IStatus.WARNING
                                                                                                                                          | IStatus.ERROR);
          // Proceed if user confirms to do so.
          _shouldProceed = (IDialogConstants.OK_ID == dialog.open());
        }
      }, false);
    }

    private class ValidationDialog extends DiagnosticDialog {
      private IStatus status;

      /**
       * @param parentShell_p
       * @param dialogTitle_p
       * @param message_p
       * @param diagnostic_p
       * @param severityMask_p
       */
      public ValidationDialog(Shell parentShell_p, String dialogTitle_p, String message_p, IStatus status_p, int severityMask_p) {
        super(parentShell_p, dialogTitle_p, message_p, BasicDiagnostic.toDiagnostic(status_p), severityMask_p);
        status = status_p;
      }

      @Override
      protected void createButtonsForButtonBar(Composite parent_p) {
        if (IStatus.ERROR != status.getSeverity()) {
          // Create "Continue" button.
          createButton(parent_p, IDialogConstants.OK_ID, Messages.EnvironmentsHub_ButtonLabel_Continue, false);
        }
        // Create CANCEL button.
        createButton(parent_p, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
        // Create details button.
        createDetailsButton(parent_p);
      }
    }

    /**
     * Should Make Baseline process be proceeded ?
     * @return
     */
    protected boolean shouldProceed() {
      return _shouldProceed;
    }
  }
}