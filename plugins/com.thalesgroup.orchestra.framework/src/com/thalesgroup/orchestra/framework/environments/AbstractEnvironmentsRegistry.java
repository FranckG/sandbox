/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environments;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.AbstractEnvironment;
import com.thalesgroup.orchestra.framework.environment.BaselineContext;
import com.thalesgroup.orchestra.framework.environment.BaselineContext.BaselineEnvironmentAttributes;
import com.thalesgroup.orchestra.framework.environment.BaselineResult;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.IEnvironment;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry.EnvironmentDescriptor;
import com.thalesgroup.orchestra.framework.environments.EnvironmentsHub.MakeBaselineContext;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingEnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.validation.baseline.MakeBaselineValidationContext;
import com.thalesgroup.orchestra.framework.ui.dialog.UseBaselineDialog.UseBaselineProperties;

/**
 * An abstract implementation of an environments registry.
 * @author t0076261
 */
public abstract class AbstractEnvironmentsRegistry extends AbstractEnvironment {
  /**
   * In use environments.<br>
   * Computed at context switch time.
   */
  protected Map<String, IEnvironment> _environments;

  /**
   * Get environment properties for specified runtime ID.
   * @param environmentId_p
   * @return <code>null</code> if no environment could be found for specified runtime ID.
   */
  public Map<String, String> getEnvironmentProperties(String environmentId_p) {
    // Precondition.
    if (null == environmentId_p) {
      return null;
    }
    IEnvironment environment = _environments.get(environmentId_p);
    // Can't find any environment with specified ID.
    if (null == environment) {
      return null;
    }
    return environment.getAttributes();
  }

  /**
   * Get source variable for environments fetching.
   * @param currentContext_p
   * @return A not <code>null</code> environment variable.
   */
  protected abstract EnvironmentVariable getEnvironmentsSourceVariable(Context currentContext_p);

  /**
   * Handle switch of context to specified one.
   * @param currentContext_p
   * @param progressMonitor_p
   * @param allowUserInteractions_p
   */
  protected IStatus handleContextSwitched(Context currentContext_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p) {
    // Initialize environments structure.
    if (null == _environments) {
      _environments = new LinkedHashMap<String, IEnvironment>(0);
    } else {
      // Clean local list.
      _environments.clear();
    }
    // Get environments.
    Couple<Map<String, IEnvironment>, IStatus> environments = getContextEnvironments(currentContext_p, progressMonitor_p, allowUserInteractions_p);
    _environments = environments.getKey();

    return environments.getValue();
  }

  /**
   * @param currentContext_p
   * @param progressMonitor_p
   * @param allowUserInteractions_p
   * @return
   */
  public Couple<Map<String, IEnvironment>, IStatus> getContextEnvironments(Context currentContext_p, IProgressMonitor progressMonitor_p,
      boolean allowUserInteractions_p) {

    Map<String, IEnvironment> environments = new LinkedHashMap<String, IEnvironment>(0);

    MultiStatus result = new MultiStatus(ModelHandlerActivator.getDefault().getPluginId(), 0, "Get context environments resulting status", null); //$NON-NLS-1$

    // Get environments.
    EnvironmentVariable sourceVariable = getEnvironmentsSourceVariable(currentContext_p);
    List<VariableValue> rawValues = DataUtil.getRawValues(sourceVariable, currentContext_p);
    for (VariableValue rawVariableValue : rawValues) {
      // Get environment.
      EnvironmentVariableValue environmentVariableValue = (EnvironmentVariableValue) rawVariableValue;
      String declarationId = environmentVariableValue.getEnvironmentId();
      // Raw value ID should be stable enough.
      String runtimeId = environmentVariableValue.getId();
      Couple<IStatus, IEnvironment> environmentCouple = EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironment(declarationId, runtimeId);
      if (!environmentCouple.getKey().isOK()) {
        continue;
      }
      // Get environment and initialize it.
      IEnvironment environment = environmentCouple.getValue();
      // Substitute value before setting attributes.
      EnvironmentVariableValue substitutedValue = (EnvironmentVariableValue) DataUtil.getSubstitutedValue(environmentVariableValue, currentContext_p);
      EnvironmentContext environmentContext = new EnvironmentContext();
      environmentContext._attributes = ModelUtil.convertEnvironmentVariableValues(substitutedValue);
      environmentContext._allowUserInteractions = allowUserInteractions_p;
      IStatus status = environment.setAttributes(environmentContext, progressMonitor_p);
      result.add(status);
      environments.put(runtimeId, environment);
    }

    return new Couple<Map<String, IEnvironment>, IStatus>(environments, result);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#makeBaseline(com.thalesgroup.orchestra.framework.environment.BaselineContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public BaselineResult makeBaseline(BaselineContext baselineContext_p, IProgressMonitor monitor_p) {
    // Baseline context is indeed the main baseline context.
    MakeBaselineContext mainBaselineContext = (MakeBaselineContext) baselineContext_p;
    Set<Entry<String, IEnvironment>> environments = _environments.entrySet();
    SubMonitor monitor = SubMonitor.convert(monitor_p, 10 * environments.size());
    // Overall statuses
    CommandStatus baselineResultOverAllEnvironments = new CommandStatus(ICommonConstants.EMPTY_STRING, null, 0);
    // Cycle through environments.
    for (Entry<String, IEnvironment> entry : environments) {
      IEnvironment environment = entry.getValue();
      // Precondition.
      if (!environment.isBaselineCompliant()) {
        continue;
      }
      IEnvironmentHandler environmentHandler =
          EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentHandler(environment.getDeclarationId()).getValue();
      if (null != environmentHandler) {
        monitor.setTaskName(MessageFormat.format(Messages.AbstractEnvironmentsRegistry_ProgressText_PropagatingBaseline,
            environmentHandler.toString(environment.getAttributes())));
      }
      BaselineContext baselineContext = mainBaselineContext.getContextFor(environment);
      BaselineResult baselineResult = environment.makeBaseline(baselineContext, monitor.newChild(10));
      // Error case.
      if ((null == baselineResult) || !baselineResult.getStatus().isOK()) {
        // Create result, if none is provided.
        if (null == baselineResult) {
          baselineResult = new BaselineResult();
          baselineResultOverAllEnvironments.addChild(new CommandStatus(IStatus.ERROR, MessageFormat.format(
              Messages.AbstractEnvironmentsRegistry_MakeBaseline_BaselineResultIsMissing, environment.getDeclarationId()), null, 0));
          baselineResult.setStatus(new CommandStatus(baselineResultOverAllEnvironments));
        }
        return baselineResult;
      }
      for (CommandStatus substatus : baselineResult.getStatus().getChildren()) {
        baselineResultOverAllEnvironments.addChild(substatus);
      }
      // Retain result.
      mainBaselineContext.addResultFor(environment, baselineResult);
    }
    // OK result.
    BaselineResult baselineResult = new BaselineResult();
    baselineResult.setStatus(new CommandStatus(baselineResultOverAllEnvironments));
    return baselineResult;
  }

  /**
   * Merge overriding variables values using the new values ones to override destination existing ones.<br>
   * This is only applicable to the Baseline Context.
   * @param destinationVariable_p
   * @param baselinesVariable_p
   */
  protected void mergeOverridingVariablesForBaselineContext(OverridingVariable destinationVariable_p, OverridingVariable baselinesVariable_p) {
    // Precondition.
    if ((null == destinationVariable_p) || (null == baselinesVariable_p)) {
      return;
    }
    // Cycle through overriding baselines values.
    // Clone them first as an addition to destination variable will mutate the original list (EMF behavior).
    List<VariableValue> baselinesVariableValues = new ArrayList<VariableValue>(baselinesVariable_p.getValues());
    for (VariableValue value : baselinesVariableValues) {
      OverridingVariableValue newOverridingValue = (OverridingVariableValue) value;
      // Cycle through existing values.
      boolean valuesReplaced = false;
      for (VariableValue existingValue : destinationVariable_p.getValues()) {
        String existingPath = null;
        // In case of an overriding variable value, jump to overridden value.
        if (existingValue instanceof OverridingVariableValue) {
          existingPath = ModelUtil.getElementPath(((OverridingVariableValue) existingValue).getOverriddenValue());
        } else {
          // Use regular value.
          existingPath = ModelUtil.getElementPath(existingValue);
        }
        // Compare paths.
        String newPath = ModelUtil.getElementPath(newOverridingValue.getOverriddenValue());
        // Found a match.
        if ((null != existingPath) && existingPath.equals(newPath)) {
          // Replace value with new one.
          ModelUtil.initializeValue(existingValue, newOverridingValue);
          valuesReplaced = true;
          break;
        }
      }
      // Add value.
      if (!valuesReplaced) {
        destinationVariable_p.getValues().add(value);
      }
    }
  }

  /**
   * Populate specified baseline context with baseline references as returned by specified make baseline context results.
   * @param currentContext_p
   * @param baselineContext_p
   * @param makeBaselineContext_p
   */
  public void populateBaselineContext(Context baselineContext_p, MakeBaselineContext makeBaselineContext_p) {
    // Get targeted environment variable.
    // In current context.
    EnvironmentVariable environmentVariable = getEnvironmentsSourceVariable(baselineContext_p);
    // Prepare overriding one for specified baseline context.
    OverridingVariable overridingVariable = ContextsFactory.eINSTANCE.createOverridingVariable();
    overridingVariable.setOverriddenVariable(environmentVariable);
    // Cycle through variable values.
    List<VariableValue> values = DataUtil.getRawValues(environmentVariable, baselineContext_p);
    for (VariableValue variableValue : values) {
      // Get environment.
      EnvironmentVariableValue environmentVariableValue = (EnvironmentVariableValue) variableValue;
      String runtimeId = environmentVariableValue.getId();
      // Get make baseline resulting attributes.
      IEnvironment environment = _environments.get(runtimeId);
      BaselineEnvironmentAttributes baselinedAttributes = makeBaselineContext_p.getBaselinedAttributesFor(environment);
      Map<String, String> baselineAttributes = null;
      if (null != baselinedAttributes) {
        // Use make baseline result.
        baselineAttributes = baselinedAttributes._baselineResult.getReferencingAttributes();
        if (null == baselineAttributes) {
          continue;
        }
        // Override environment with referencing attributes.
        OverridingEnvironmentVariableValue overridingVariableValue =
            (OverridingEnvironmentVariableValue) ModelUtil.createOverridingVariableValue(overridingVariable, variableValue, null);
        // Get version.
        String version = null;
        Couple<IStatus, EnvironmentDescriptor> descriptor =
            EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentDescriptor(environment.getDeclarationId());
        if (descriptor.getKey().isOK()) {
          version = descriptor.getValue().getVersion();
        }
        ModelUtil.fillEnvironmentVariableValue(overridingVariableValue, baselineAttributes, environment.getDeclarationId(), version);
        // Retain overriding variable value.
        overridingVariable.getValues().add(overridingVariableValue);
      }
    }
    // Precondition.
    // Make sure there is something to add.
    if (overridingVariable.getValues().isEmpty()) {
      return;
    }
    // Check whether there is already an overriding variable for targeted variable within baseline context.
    OverridingVariable currentOverridingVariable = DataUtil.getOverridingVariable(environmentVariable, baselineContext_p);
    if (!DataUtil.isExternalElement(currentOverridingVariable, baselineContext_p)) {
      // If so, it has to be merged with new one.
      mergeOverridingVariablesForBaselineContext(currentOverridingVariable, overridingVariable);
    } else {
      // Add new overriding variable to baseline context.
      baselineContext_p.getOverridingVariables().add(overridingVariable);
    }
  }

  /**
   * Populate baseline reference context with specified contents.
   * @param baselineReferenceContext_p
   * @return Status
   */
  public IStatus populateBaselineReferenceContext(UseBaselineProperties properties_p) {
    Context baselineReferenceContext = properties_p._createdUseBaselineContext;

    // Environment registry.
    EnvironmentRegistry environmentRegistry = EnvironmentActivator.getInstance().getEnvironmentRegistry();
    // Get targeted environment variable.
    EnvironmentVariable environmentVariable = getEnvironmentsSourceVariable(baselineReferenceContext);
    // Prepare overriding one for specified baseline reference context.
    OverridingVariable overridingVariable = ContextsFactory.eINSTANCE.createOverridingVariable();
    overridingVariable.setOverriddenVariable(environmentVariable);
    // Cycle through variable values.
    List<VariableValue> values = DataUtil.getRawValues(environmentVariable, baselineReferenceContext);
    for (VariableValue variableValue : values) {
      // Get environment.
      EnvironmentVariableValue environmentVariableValue = (EnvironmentVariableValue) variableValue;
      String declarationId = environmentVariableValue.getEnvironmentId();
      // Get baseline reference resulting attributes.
      Couple<IStatus, IEnvironmentHandler> couple = environmentRegistry.getEnvironmentHandler(declarationId);
      // Skip this environment, it is not known right now.
      if (!couple.getKey().isOK()) {
        continue;
      }
      IEnvironmentHandler handler = couple.getValue();
      Map<String, String> environmentAttributes;

      UseBaselineEnvironmentContext referenceEnvironmentContext = properties_p._envValueToUseBaselineContext.get(environmentVariableValue);

      // Skip this environment, it is not compliant with baseline usage.
      if (!handler.isUseBaselineCompliant(referenceEnvironmentContext)) {
        continue;
      }
      // Allow errors displaying to user.
      referenceEnvironmentContext._allowUserInteractions = true;
      // Get reference attributes.
      Couple<Map<String, String>, IStatus> referenceAttributes = handler.getBaselineReferenceAttributes(referenceEnvironmentContext, new NullProgressMonitor());
      if (null == referenceAttributes) {
        continue;
      }

      IStatus errorStatus = referenceAttributes.getValue();
      if (!errorStatus.isOK()) {
        return errorStatus;
      }

      environmentAttributes = referenceAttributes.getKey();
      // Skip this environment, it is not implementing (at least not properly) the get baseline reference attributes method.
      if ((null == environmentAttributes) || environmentAttributes.isEmpty()) {
        continue;
      }
      // Override environment with referencing attributes.
      OverridingEnvironmentVariableValue overridingVariableValue =
          (OverridingEnvironmentVariableValue) ModelUtil.createOverridingVariableValue(overridingVariable, variableValue, null);
      // Get version.
      String version = null;
      Couple<IStatus, EnvironmentDescriptor> descriptor = environmentRegistry.getEnvironmentDescriptor(declarationId);
      if (descriptor.getKey().isOK()) {
        version = descriptor.getValue().getVersion();
      }
      // Write attributes to model.
      ModelUtil.fillEnvironmentVariableValue(overridingVariableValue, environmentAttributes, declarationId, version);
      // Retain overriding variable value.
      overridingVariable.getValues().add(overridingVariableValue);
    }
    // Then add new overriding variable value to baseline reference context.
    if (!overridingVariable.getValues().isEmpty()) { // Do not add if it does not contain any data.
      baselineReferenceContext.getOverridingVariables().add(overridingVariable);
    }
    return Status.OK_STATUS;
  }

  /**
   * Populate baseline starting point context with specified contents.
   * @param properties_p
   * @return Status
   */
  public IStatus populateBaselineStartingPointContext(UseBaselineProperties properties_p, IProgressMonitor progressMonitor_p) {
    // Environment registry.
    EnvironmentRegistry environmentRegistry = EnvironmentActivator.getInstance().getEnvironmentRegistry();
    // Get targeted environment variable.
    Context startingPointContext = properties_p._createdUseBaselineContext;
    EnvironmentVariable environmentVariable = getEnvironmentsSourceVariable(startingPointContext);
    // Prepare overriding one for specified baseline reference context.
    OverridingVariable overridingVariable = ContextsFactory.eINSTANCE.createOverridingVariable();
    overridingVariable.setOverriddenVariable(environmentVariable);
    // Progress monitoring.
    SubMonitor monitor = SubMonitor.convert(progressMonitor_p, properties_p._envValueToUseBaselineContext.size());
    // Cycle through resulting structure.
    for (Entry<EnvironmentVariableValue, UseBaselineEnvironmentContext> entry : properties_p._envValueToUseBaselineContext.entrySet()) {
      EnvironmentVariableValue environmentVariableValue = entry.getKey();
      // Get containing variable.
      ModelElement container = ModelUtil.getLogicalContainer(environmentVariableValue, startingPointContext);
      // This environment variable does not point to the expected variable, skip it.
      if (container != environmentVariable) {
        continue;
      }
      // Try and get environment handler.
      String declarationId = environmentVariableValue.getEnvironmentId();
      Couple<IStatus, IEnvironmentHandler> environmentHandlerCouple = environmentRegistry.getEnvironmentHandler(declarationId);
      // Can't get the handler, skip this value.
      if (!environmentHandlerCouple.getKey().isOK()) {
        continue;
      }
      // Get attributes.
      IEnvironmentHandler handler = environmentHandlerCouple.getValue();
      UseBaselineEnvironmentContext startingPointEnvironmentContext = entry.getValue();
      // Allow errors displaying to user.
      startingPointEnvironmentContext._allowUserInteractions = true;
      Couple<Map<String, String>, IStatus> baselineAttributes = null;
      switch (startingPointEnvironmentContext.getUseBaselineType()) {
        case LIVE_ENVIRONMENT:
          baselineAttributes = handler.getBaselineStartingPointAttributes(startingPointEnvironmentContext, monitor.newChild(1));
        break;
        case REFERENCE_ENVIRONMENT:
          baselineAttributes = handler.getBaselineReferenceAttributes(startingPointEnvironmentContext, monitor.newChild(1));
        break;
        default:
        break;
      }
      if (null == baselineAttributes) {
        continue;
      }

      IStatus errorStatus = baselineAttributes.getValue();
      if (!errorStatus.isOK()) {
        return errorStatus;
      }

      Map<String, String> environmentAttributes = baselineAttributes.getKey();
      // Invalid arguments, skip this value.
      if (null == environmentAttributes || environmentAttributes.isEmpty()) {
        continue;
      }
      // Override environment with new attributes.
      OverridingEnvironmentVariableValue overridingVariableValue =
          (OverridingEnvironmentVariableValue) ModelUtil.createOverridingVariableValue(overridingVariable, environmentVariableValue, null);
      // Get version.
      String version = null;
      Couple<IStatus, EnvironmentDescriptor> descriptor = environmentRegistry.getEnvironmentDescriptor(declarationId);
      if (descriptor.getKey().isOK()) {
        version = descriptor.getValue().getVersion();
      }
      // Write attributes to model.
      ModelUtil.fillEnvironmentVariableValue(overridingVariableValue, environmentAttributes, declarationId, version);
      // Retain overriding variable value.
      overridingVariable.getValues().add(overridingVariableValue);
    }
    // Then add new overriding variable value to baseline reference context
    // only if the overriding variable contains values
    if (!overridingVariable.getValues().isEmpty()) {
      startingPointContext.getOverridingVariables().add(overridingVariable);
    }
    return Status.OK_STATUS;
  }

  /**
   * Retain Make Baseline non-compliant environments in specified validation context for currently in-use environments.
   * @param validationContext_p
   */
  public void retainMakeBaselineNonCompliantEnvironments(MakeBaselineValidationContext validationContext_p) {
    // Environment registry.
    EnvironmentRegistry registry = EnvironmentActivator.getInstance().getEnvironmentRegistry();
    // Cycle through environments.
    for (Entry<String, IEnvironment> entry : _environments.entrySet()) {
      IEnvironment environment = entry.getValue();
      // Test compliance to Make Baseline process.
      if (!environment.isBaselineCompliant()) {
        // Not compliant.
        // Try and resolve string representation.
        String declarationId = environment.getDeclarationId();
        // Find the handler.
        Couple<IStatus, EnvironmentDescriptor> environmentDescriptor = registry.getEnvironmentDescriptor(declarationId);
        String environmentString = environmentDescriptor.getValue().getHandler().toString(environment.getAttributes());
        // Retain environment.
        validationContext_p.addNonCompliantEnvironment(environmentString);
      }
    }
  }

  public IEnvironment getEnvironment(String environmentId_p) {
    return _environments.get(environmentId_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironment#toString()
   */
  @Override
  public String toString() {
    ArrayList<String> result = new ArrayList<String>(0);
    for (Entry<String, IEnvironment> entry : _environments.entrySet()) {
      result.add(entry.getValue().toString());
    }
    return result.toString();
  }
}