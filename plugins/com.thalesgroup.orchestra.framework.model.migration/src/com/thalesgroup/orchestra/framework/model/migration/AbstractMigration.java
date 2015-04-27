/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.migration;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.ResourceChange;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.model.ModelActivator;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceFactoryImpl;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsResourceSet;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.handler.data.SharedDataUtil;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.puci.PUCI;

/**
 * Migration helper.<br>
 * Allows for the creation of contexts within projects with migrated contents (from previous models).
 * @author t0076261
 */
public abstract class AbstractMigration {
  /**
   * Service result : context key.<br>
   * Returned element should be a {@link Context}.
   */
  protected static final String RESULT_KEY_CONTEXT = "Context"; //$NON-NLS-1$
  /**
   * Service result : project key.<br>
   * Returned element should be a {@link RootContextsProject}.
   */
  protected static final String RESULT_KEY_PROJECT = "Project"; //$NON-NLS-1$
  /**
   * Service result : status key.<br>
   * Returned element should be a {@link IStatus}.
   */
  protected static final String RESULT_KEY_STATUS = "Status"; //$NON-NLS-1$
  /**
   * Resource set.
   */
  private ResourceSet _resourceSet;
  /**
   * Status handler.
   */
  private StatusHandler _statusHandler;

  /**
   * Default constructor.
   */
  public AbstractMigration() {
    _statusHandler = new StatusHandler();
  }

  /**
   * Clean specified context.
   * @param context_p
   * @param contextName_p The new context name to set, if any. Can be <code>null</code>, in this case, existing name remains the context name.
   */
  protected final void cleanContext(Context context_p, String contextName_p) {
    if (null == context_p) {
      return;
    }
    // Set new name.
    if (null != contextName_p) {
      context_p.setName(contextName_p);
    }
    // Clean all existing values.
    context_p.getCategories().clear();
    context_p.getOverridingVariables().clear();
    context_p.getSuperCategoryCategories().clear();
    context_p.getSuperCategoryVariables().clear();
    context_p.getTransientCategories().clear();
  }

  /**
   * Convert severity from {@link SeverityType} to {@link IStatus} value.
   * @param severityType_p
   * @return
   */
  protected final int convertSeverity(SeverityType severityType_p) {
    switch (severityType_p) {
      case ERROR:
        return IStatus.ERROR;
      case WARNING:
        return IStatus.WARNING;
      case INFO:
        return IStatus.INFO;
      case OK:
        return IStatus.OK;
    }
    return IStatus.INFO;
  }

  /**
   * Convert status from {@link com.thalesgroup.orchestra.framework.exchange.status.Status} model to an {@link IStatus}.
   * @param status_p
   * @return A not <code>null</code> status, even if parameter is <code>null</code>.
   */
  protected final IStatus convertStatus(com.thalesgroup.orchestra.framework.exchange.status.Status status_p) {
    if (null == status_p) {
      return new Status(IStatus.ERROR, getPluginId(), Messages.AbstractMigration_FailedToConvertStatus);
    }
    EList<com.thalesgroup.orchestra.framework.exchange.status.Status> statuses = status_p.getStatus();
    if (statuses.isEmpty()) {
      return new Status(convertSeverity(status_p.getSeverity()), getPluginId(), status_p.getMessage());
    }
    MultiStatus result = new MultiStatus(getPluginId(), status_p.getCode(), status_p.getMessage(), null);
    for (com.thalesgroup.orchestra.framework.exchange.status.Status childStatus : statuses) {
      result.add(convertStatus(childStatus));
    }
    return result;
  }

  /**
   * @param containingCategoryPath_p
   * @param name_p
   * @param context_p
   * @return
   */
  protected final Category createCategory(String containingCategoryPath_p, String name_p, Context context_p) {
    // Preconditions.
    if ((null == name_p) || (null == context_p)) {
      return null;
    }
    // Get containing category, if any.
    Category containingCategory = null;
    if (null != containingCategoryPath_p) {
      containingCategory = DataUtil.getCategory(containingCategoryPath_p, context_p);
    }
    String categoryPath =
        ((null != containingCategoryPath_p) ? containingCategoryPath_p : ICommonConstants.EMPTY_STRING) + ICommonConstants.PATH_SEPARATOR + name_p;
    Category category = DataUtil.getCategory(categoryPath, context_p);
    // Category already exists.
    if (null != category) {
      // Category is already owned by specified context.
      if (context_p == ModelUtil.getContext(category)) {
        // And stop here.
        return category;
      }
    } else { // Category does not exist yet.
      category = ContextsFactory.eINSTANCE.createCategory();
      category.setName(name_p);
      if (null != containingCategory) {
        // Add to containing category.
        if (context_p == ModelUtil.getContext(containingCategory)) {
          containingCategory.getCategories().add(category);
        } else {
          category.setSuperCategory(containingCategory);
          context_p.getSuperCategoryCategories().add(category);
        }
      } else {
        // Add to context directly.
        context_p.getCategories().add(category);
      }
      return category;
    }
    return null;
  }

  /**
   * Strictly equivalent to {@link #createNewContext(String, RootContextsProject, String, boolean, String, boolean)} with the clean participation flag set to
   * <code>false</code>.<br>
   * Provided for compatibility reasons only.
   * @param contextName_p
   * @param parentProject_p
   * @param rootLocationAbsolutePath_p
   * @param participate_p
   * @param userId_p
   * @return
   */
  protected final Map<String, Object> createNewContext(String contextName_p, RootContextsProject parentProject_p, String rootLocationAbsolutePath_p,
      boolean participate_p, String userId_p) {
    return createNewContext(contextName_p, parentProject_p, rootLocationAbsolutePath_p, participate_p, userId_p, false);
  }

  /**
   * Create new context for specified parameters.<br>
   * Note that creating a participation when one already exists for specified user does return the existing participation.
   * @param contextName_p A not <code>null</code> context name.
   * @param parentProject_p The new context parent structure. Must be not <code>null</code> for a new participation.
   * @param rootLocationAbsolutePath_p The new structure root location absolute path. <code>null</code> for a new participation or if the new structure is to be
   *          created in the Framework workspace. <b>WARNING !</b> This requires that the Framework is launched first.
   * @param participate_p <code>true</code> to creation a participation. <code>false</code> to create a new context structure.
   * @param userId_p The user ID. Required for a participation. Can be <code>null</code> for a new context structure.
   * @param cleanParticipation_p <code>true</code> to remove all contents from the participation, and set its name to newly provided one, <code>false</code> to
   *          leave participation untouched (with potentially existing values).
   * @return A map containing values for the following keys : {@link #RESULT_KEY_CONTEXT} {@link #RESULT_KEY_PROJECT} {@link #RESULT_KEY_STATUS}. The context
   *         and project may be <code>null</code>, whereas the status is always provided.
   */
  protected final Map<String, Object> createNewContext(String contextName_p, RootContextsProject parentProject_p, String rootLocationAbsolutePath_p,
      boolean participate_p, String userId_p, boolean cleanParticipation_p) {
    Map<String, Object> result = new HashMap<String, Object>(0);
    // Precondition.
    if (null == contextName_p) {
      result.put(RESULT_KEY_STATUS, new Status(IStatus.ERROR, getPluginId(), Messages.AbstractMigration_FailedToCreateContext_NameRequired));
      return result;
    }
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    // Participation.
    if (participate_p) {
      // Precondition.
      if ((null == parentProject_p) || (null == userId_p)) {
        result.put(RESULT_KEY_STATUS, new Status(IStatus.ERROR, getPluginId(),
            Messages.AbstractMigration_FailedToCreateParticipation_HostingContextAndUserIdRequired));
        return result;
      }
      IStatus participationResult = dataHandler.participateInAProject(contextName_p, parentProject_p, _resourceSet, userId_p);
      result.put(RESULT_KEY_STATUS, participationResult);
      if (!participationResult.isOK()) {
        return result;
      }
      Context context = dataHandler.getContext(URI.createURI(parentProject_p.getUserContextUri(userId_p)), _resourceSet);
      // Clean context if needed.
      if (cleanParticipation_p) {
        cleanContext(context, contextName_p);
      }
      result.put(RESULT_KEY_CONTEXT, context);
      result.put(RESULT_KEY_PROJECT, parentProject_p);
      return result;
    }
    // Create all new structure.
    String rootLocation = rootLocationAbsolutePath_p;
    if (null == rootLocation) {
      rootLocation = getFrameworkWorkspaceLocation();
    }
    // There is a need for a root location here !
    if (null == rootLocation) {
      result.put(RESULT_KEY_STATUS, new Status(IStatus.ERROR, getPluginId(), Messages.AbstractMigration_FailedToCreateContext_LocationRequired));
      return result;
    }
    IStatus contextResult = dataHandler.createNewContextStructure(contextName_p, rootLocation, parentProject_p, _resourceSet, true);
    result.put(RESULT_KEY_STATUS, contextResult);
    if (!contextResult.isOK()) {
      return result;
    }
    String projectName = ProjectHelper.generateContextProjectName(contextName_p);
    RootContextsProject rootContextsProject = ProjectActivator.getInstance().getProjectHandler().getRootContextsProject(ProjectHelper.getProject(projectName));
    Context context = dataHandler.getContext(URI.createURI(rootContextsProject.getAdministratedContext().getUri()), _resourceSet);
    result.put(RESULT_KEY_CONTEXT, context);
    result.put(RESULT_KEY_PROJECT, rootContextsProject);
    return result;
  }

  /**
   * Get a new migration {@link OrchestraURI}.<br>
   * The URI is initialized with correct root type and root name.<br>
   * Parameters are still to be provided (if any).
   * @return
   */
  protected final OrchestraURI createOrchestraURI() {
    return new OrchestraURI("Migration", "Migration"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  /**
   * Create/update or override variable for specified parameters.
   * @param containingCategoryPath_p The category that logically contains the variable.
   * @param name_p The name of the variable.
   * @param values_p The values to add/replace in the variable. For {@link EnvironmentVariable}, the values won't be used, but are still required to initialize
   *          the variable with the correct expected number of environment values.
   * @param type_p The variable type, in case of a creation. Can be <code>null</code> in case of an update/override process.
   * @param keepExistingValues_p In case of an update/override process, should existing values be kept ? <code>true</code> if so, <code>false</code> to remove
   *          them. Note that it does not make any sense for mono-valued variables.
   * @param context_p The asking context (i.e. the one containing the value in the end).
   * @return <code>null</code> if the parameters are incorrect. Either a {@link Variable} or an {@link OverridingVariable} instance depending on the context
   *         containing the variable against specified one.
   */
  protected final AbstractVariable createVariable(String containingCategoryPath_p, String name_p, List<String> values_p, VariableType type_p,
      boolean keepExistingValues_p, Context context_p) {
    // Precondition.
    if ((null == containingCategoryPath_p) || (null == name_p) || (null == values_p) || values_p.isEmpty() || (null == context_p)) {
      return null;
    }
    // Get category.
    Category category = DataUtil.getCategory(containingCategoryPath_p, context_p);
    // Category does not exist, stop here.
    if (null == category) {
      return null;
    }
    // Get variable.
    AbstractVariable variable = DataUtil.getVariable(containingCategoryPath_p + ICommonConstants.PATH_SEPARATOR + name_p, context_p);
    int numberOfValues = values_p.size();
    // There is already an existing variable.
    // Replace its values with provided ones, and return existing value.
    if (null != variable) {
      if (!variable.isMultiValued()) {
        numberOfValues = 1;
      }
      // Is this variable contributed by specified context ?
      if (context_p == ModelUtil.getContext(variable)) {
        // If values should be kept, stop here.
        if (keepExistingValues_p) {
          return variable;
        }
        // Clean existing values.
        variable.getValues().clear();
        // Initialize expecting values.
        for (int i = 0; i < numberOfValues; i++) {
          VariableValue value = null;
          if (VariableType.ENVIRONMENT_VARIABLE.equals(type_p)) {
            value = ContextsFactory.eINSTANCE.createEnvironmentVariableValue();
          } else {
            value = ContextsFactory.eINSTANCE.createVariableValue();
          }
          value.setValue(ICommonConstants.EMPTY_STRING);
          variable.getValues().add(value);
        }
      } else {
        // The variable exists, but is contributed by a parent context.
        // Create/get corresponding overriding variable.
        variable = getOverridingVariable(variable, context_p, numberOfValues, !variable.isMultiValued() ? false : keepExistingValues_p);
      }
    } else {
      // Precondition.
      if (null == type_p) {
        return null;
      }
      // Create variable.
      Variable targetVariable = (Variable) ContextsFactory.eINSTANCE.create(type_p.getEClass());
      variable = targetVariable;
      // Add it to containing category.
      if (context_p == ModelUtil.getContext(category)) {
        category.getVariables().add(targetVariable);
      } else {
        targetVariable.setSuperCategory(category);
        context_p.getSuperCategoryVariables().add(targetVariable);
      }
      // Set its properties.
      targetVariable.setName(name_p);
      if (numberOfValues > 1) {
        targetVariable.setMultiValued(true);
      }
      // Initialize expecting values.
      for (int i = 0; i < numberOfValues; i++) {
        VariableValue value = null;
        if (VariableType.ENVIRONMENT_VARIABLE.equals(type_p)) {
          value = ContextsFactory.eINSTANCE.createEnvironmentVariableValue();
        } else {
          value = ContextsFactory.eINSTANCE.createVariableValue();
        }
        value.setValue(ICommonConstants.EMPTY_STRING);
        variable.getValues().add(value);
      }
    }
    // Fill in values.
    for (int i = 0; i < numberOfValues; i++) {
      VariableValue variableValue = variable.getValues().get(i);
      // Skip value setting for EnvironmentVariableValue.
      if (variableValue instanceof EnvironmentVariableValue) {
        continue;
      }
      variableValue.setValue(values_p.get(i));
    }
    return variable;
  }

  /**
   * Do migrate.
   * @return An {@link IStatus}, with {@link IStatus#OK} value if migration ended successfully, any other value if migration could not be achieved or failed.
   *         Must be not <code>null</code> !
   */
  protected abstract IStatus doMigrate();

  /**
   * @see java.lang.Object#finalize()
   */
  @Override
  protected final void finalize() throws Throwable {
    if (null != _statusHandler) {
      _statusHandler.clean();
    }
  }

  /**
   * Get contexts from Framework workspace that could be parents of a newly created context structure.<br>
   * They meet the condition that they are not directly hosted by the workspace, but only referenced.
   * @return A {@link Collection} of {@link Couple} of (Name of context, context project absolute location path). <code>null</code> if an error occurred.
   */
  public final Collection<Couple<String, String>> getContextsFromFrameworkWorkspace() {
    try {
      // Create a new migration URI.
      OrchestraURI uri = createOrchestraURI();
      // Invoke workspace location command.
      String statusAbsoluteLocation =
          PUCI.executeSpecificCommand("ParentsCandidates", Collections.singletonList(uri)).get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH); //$NON-NLS-1$
      // Get result.
      StatusDefinition statusDefinition = _statusHandler.loadModel(statusAbsoluteLocation);
      com.thalesgroup.orchestra.framework.exchange.status.Status status = _statusHandler.getStatusForUri(statusDefinition, uri.getUri());
      // An error occurred, stop here.
      if (!SeverityType.OK.equals(status.getSeverity())) {
        return null;
      }
      // Return parents candidates.
      Collection<Couple<String, String>> contextsNameAndLocation = new ArrayList<Couple<String, String>>(0);
      for (com.thalesgroup.orchestra.framework.exchange.status.Status child : status.getStatus()) {
        String contextName = child.getMessage();
        String contextLocation = child.getStatus().get(0).getMessage();
        contextsNameAndLocation.add(new Couple<String, String>(contextName, contextLocation));
      }
      return contextsNameAndLocation;
    } catch (Exception exception_p) {
      // Something went wrong, return null.
      return null;
    }
  }

  /**
   * Get OF workspace absolute location.
   * @return
   */
  private String getFrameworkWorkspaceLocation() {
    try {
      // Create a new migration URI.
      OrchestraURI uri = createOrchestraURI();
      // Invoke workspace location command.
      String statusAbsoluteLocation =
          PUCI.executeSpecificCommand("WorkspaceLocation", Collections.singletonList(uri)).get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH); //$NON-NLS-1$
      // Get result.
      StatusDefinition statusDefinition = _statusHandler.loadModel(statusAbsoluteLocation);
      com.thalesgroup.orchestra.framework.exchange.status.Status status = _statusHandler.getStatusForUri(statusDefinition, uri.getUri());
      // Return absolute location.
      return status.getMessage();
    } catch (Exception exception_p) {
      // Something went wrong, return null.
      return null;
    }
  }

  /**
   * Get overriding variable for specified mono-valued variable.<br>
   * This method should not be used with a multi-valued variable.
   * @param variable_p The variable to override.
   * @param context_p The asking context (i.e. the one containing the value in the end).
   * @return <code>null</code> if the specified variable is either <code>null</code>, or multi-valued, or can not be overridden.
   */
  protected final OverridingVariable getOverridingMonoValuedVariable(AbstractVariable variable_p, Context context_p) {
    // Precondition.
    if ((null == variable_p) || variable_p.isMultiValued() || variable_p.isFinal()) {
      return null;
    }
    return getOverridingVariable(variable_p, context_p, 1, false);
  }

  /**
   * Get overriding variable for specified variable.<br>
   * This method can be used with either mono or multi-valued variables.<br>
   * For mono-valued variable though, number of values must be set to <code>1</code> and keep inherited values flag to <code>false</code>. It is thus more
   * convenient to use {@link #getOverridingMonoValuedVariable(AbstractVariable, Context)} directly.
   * @param variable_p The variable to override.
   * @param context_p The asking context (i.e. the one containing the value in the end).
   * @param numberOfValues_p The number of values to insert in the overriding variable. Note that if this is less than inherited values, and keep inherited
   *          values flag is set to <code>false</code>, then the resulting value will contain empty overriding values.
   * @param keepInheritedValues_p <code>true</code> to add values to inherited ones, <code>false</code> to replace all existing values (at least with an empty
   *          one).
   * @return
   */
  protected final OverridingVariable getOverridingVariable(AbstractVariable variable_p, Context context_p, int numberOfValues_p, boolean keepInheritedValues_p) {
    // Precondition.
    if ((null == variable_p) || variable_p.isFinal() || (0 > numberOfValues_p)) {
      return null;
    }
    // Make sure this is not applied to a mono-valued variable with multiple values.
    if (!variable_p.isMultiValued() && (1 > numberOfValues_p)) {
      return null;
    }
    // Get overriding variable.
    OverridingVariable overridingVariable = DataUtil.getOverridingVariable(variable_p, context_p);
    // Either there is no overriding variable, or it does not belong to
    // specified context.
    if ((null == overridingVariable) || (context_p != ModelUtil.getContext(overridingVariable))) {
      // Create a new 'local' overriding variable.
      overridingVariable = ContextsFactory.eINSTANCE.createOverridingVariable();
      overridingVariable.setOverriddenVariable((Variable) variable_p);
      // Add newly create overriding variable to specified context.
      context_p.getOverridingVariables().add(overridingVariable);
    }
    // Clear existing values list.
    overridingVariable.getValues().clear();
    List<VariableValue> values = Collections.emptyList();
    // Override existed inherited values.
    if (!keepInheritedValues_p) {
      // Clean parents contributions.
      // Get compressed values, including all overriding values.
      values = DataUtil.getRawValues(variable_p, context_p);
      // Create new empty overriding values.
      for (VariableValue value : values) {
        VariableValue targetValue = value;
        // Jump to source value if needed.
        if (value instanceof OverridingVariableValue) {
          targetValue = ((OverridingVariableValue) value).getOverriddenValue();
        }
        // Create overriding variable value with correct content.
        ModelUtil.createOverridingVariableValue(overridingVariable, targetValue, value);
      }
    }
    // Add empty slots, if required.
    int delta = numberOfValues_p - values.size();
    if (delta > 0) {
      for (int i = 0; i < delta; i++) {
        VariableValue value = ModelUtil.createVariableValue(variable_p);
        value.setValue(ICommonConstants.EMPTY_STRING);
        overridingVariable.getValues().add(value);
      }
    }
    return overridingVariable;
  }

  /**
   * Get hosting bundle ID.
   * @return
   */
  protected final String getPluginId() {
    return getClass().getPackage().getName();
  }

  /**
   * Get {@link RootContextsProject} from project absolute location.
   * @param projectAbsoluteLocation_p
   * @return A map containing values for the following keys : {@link #RESULT_KEY_PROJECT} {@link #RESULT_KEY_STATUS}. The project may be <code>null</code> if it
   *         was not successfully loaded.
   */
  protected final Map<String, Object> getProject(String projectAbsoluteLocation_p) {
    Map<String, Object> result = new HashMap<String, Object>(0);
    if (null == projectAbsoluteLocation_p) {
      result.put(RESULT_KEY_STATUS, new Status(IStatus.ERROR, getClass().getPackage().getName(), Messages.AbstractMigration_ProjectNotLoaded_NullPath));
      return result;
    }
    RootContextsProject contextFromLocation = ProjectActivator.getInstance().getProjectHandler().getContextFromLocation(projectAbsoluteLocation_p, null);
    if (null == contextFromLocation) {
      result.put(
          RESULT_KEY_STATUS,
          new Status(IStatus.ERROR, getClass().getPackage().getName(), MessageFormat.format(
              Messages.AbstractMigration_ProjectNotLoaded_NoProjectAtSpecifiedLocation, projectAbsoluteLocation_p)));
      return result;
    }
    result.put(RESULT_KEY_PROJECT, contextFromLocation);
    result.put(RESULT_KEY_STATUS,
        new Status(IStatus.OK, getClass().getPackage().getName(), MessageFormat.format(Messages.AbstractMigration_ProjectLoaded, projectAbsoluteLocation_p)));
    return result;
  }

  /**
   * Import or reload specified context project to Framework workspace.<br>
   * It does not copy this project to the workspace, but rather reference it from the workspace.<br>
   * If the project already exists in the workspace, it reloads it.<br>
   * In this case, the context is re-applied if it was the current context.<br>
   * Note that whether this presents/reloads the entire project or a participation depends on the current Framework mode (either Administrator or User).
   * @param project_p The project to import/reload.
   * @return An {@link IStatus}.
   */
  public final IStatus importToFrameworkWorkspace(RootContextsProject project_p) {
    try {
      // Create a new migration URI.
      OrchestraURI uri = createOrchestraURI();
      uri.addParameter("ContextLocation", project_p.getLocation()); //$NON-NLS-1$
      // Invoke workspace location command.
      String statusAbsoluteLocation =
          PUCI.executeSpecificCommand("ImportContext", Collections.singletonList(uri)).get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH); //$NON-NLS-1$
      // Get result.
      StatusDefinition statusDefinition = _statusHandler.loadModel(statusAbsoluteLocation);
      com.thalesgroup.orchestra.framework.exchange.status.Status status = _statusHandler.getStatusForUri(statusDefinition, uri.getUri());
      // Return absolute location.
      return convertStatus(status);
    } catch (Exception exception_p) {
      // Something went wrong, return null.
      return new Status(IStatus.ERROR, getPluginId(), Messages.AbstractMigration_FailedToImportToFrameworkWorkspace, exception_p);
    }
  }

  /**
   * Is specified context name a valid one ?<br>
   * A valid context name is a name that is not already known to be used by the Framework.
   * @param contextName_p
   * @return
   */
  public final IStatus isValidContextName(String contextName_p) {
    try {
      // Create a new migration URI.
      OrchestraURI uri = createOrchestraURI();
      uri.addParameter("ContextName", contextName_p); //$NON-NLS-1$
      // Invoke workspace location command.
      String statusAbsoluteLocation =
          PUCI.executeSpecificCommand("ValidateContextName", Collections.singletonList(uri)).get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH); //$NON-NLS-1$
      // Get result.
      StatusDefinition statusDefinition = _statusHandler.loadModel(statusAbsoluteLocation);
      com.thalesgroup.orchestra.framework.exchange.status.Status status = _statusHandler.getStatusForUri(statusDefinition, uri.getUri());
      return convertStatus(status);
    } catch (Exception exception_p) {
      // Something went wrong, return null.
      return null;
    }
  }

  /**
   * Migration process.
   * @return An {@link IStatus}, with {@link IStatus#OK} value if migration ended successfully, any other value if migration could not be achieved or failed.
   */
  public final IStatus migrate() {
    // Create environment.
    if (null == _resourceSet) {
      _resourceSet = new ContextsResourceSet();
      if (!Platform.isRunning()) {
        _resourceSet.getPackageRegistry().put(ContextsPackage.eNS_URI, ContextsPackage.eINSTANCE);
        _resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("contexts", new ContextsResourceFactoryImpl()); //$NON-NLS-1$
      }
    }
    // Do migrate.
    IStatus result = null;
    try {
      // Make sure there is a shared data util instance.
      ModelActivator.getInstance().setDataUtil(new SharedDataUtil());

      // Begin recording changes on resource set
      ChangeRecorder recorder = new ChangeRecorder(_resourceSet);
      // Do something about migration.
      result = doMigrate();
      // Stop recording changes on resource set
      ChangeDescription endRecording = recorder.endRecording();
      // Save all modified resources.
      for (ResourceChange resourceChange : endRecording.getResourceChanges()) {
        try {
          resourceChange.getResource().save(null);
        } catch (Exception exception_p) {
          StringBuilder loggerMessage = new StringBuilder("AbstractMigration.migrate(..) _ "); //$NON-NLS-1$
          CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
        }
      }
      recorder.dispose();
    } finally {
      // Clean resources.
      for (Iterator<Resource> resources = _resourceSet.getResources().iterator(); resources.hasNext();) {
        Resource resource = resources.next();
        resource.unload();
        resources.remove();
      }
      // Clean workspace.
      Collection<RootContextsProject> projects = ProjectActivator.getInstance().getProjectHandler().getAllProjectsInWorkspace();
      for (RootContextsProject rootContextsProject : projects) {
        ProjectHelper.deleteProject(rootContextsProject._project.getName(), false);
      }
    }
    // Failed to migrate.
    if (null == result) {
      result = new Status(IStatus.ERROR, getPluginId(), Messages.AbstractMigration_FailedToMigrate);
    }
    return result;
  }

  /**
   * Existing variable types (for creation purposes).
   * @author t0076261
   */
  public enum VariableType {
    ENVIRONMENT_VARIABLE(ContextsPackage.Literals.ENVIRONMENT_VARIABLE), FILE_VARIABLE(ContextsPackage.Literals.FILE_VARIABLE), FOLDER_VARIABLE(
        ContextsPackage.Literals.FOLDER_VARIABLE), VARIABLE(ContextsPackage.Literals.VARIABLE);
    private EClass _modelClass;

    private VariableType(EClass modelClass_p) {
      _modelClass = modelClass_p;
    }

    public EClass getEClass() {
      return _modelClass;
    }
  }
}