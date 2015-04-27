/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentAttributes;
import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry.EnvironmentDescriptor;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractVariablesHandler;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.environment.validation.ConfigurationDirectoriesRuleContext;
import com.thalesgroup.orchestra.framework.environment.validation.EnvironmentRuleContext;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.validation.baseline.MakeBaselineValidationContext;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;

/**
 * A constraint specific to environment variables.<br>
 * Does not do any check by itself, but instead, delegates to contributed rules so as to collect all specific errors relative to such variables.
 * @author T0076261
 */
public class EnvironmentVariableConstraint extends AbstractConstraint<EnvironmentVariable> {
  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(EnvironmentVariable target_p, IValidationContext context_p) {
    // Get element path.
    String elementPath = ModelUtil.getElementPath(target_p);
    // Preconditions.
    // Validation rules applying on \Orchestra\ArtefactPath variable or \Orchestra\ConfigurationDirectories variable.
    boolean isArtefactPathValidation = DataUtil.__ARTEFACTPATH_VARIABLE_NAME.equals(elementPath);
    final boolean isConfigurationDirectoriesValidation = DataUtil.__CONFIGURATIONDIRECTORIES_VARIABLE_NAME.equals(elementPath);
    if (!isArtefactPathValidation && !isConfigurationDirectoriesValidation) {
      // Nothing to validate so far.
      return null;
    }
    // Prepare validation structure for contributed rules.
    // Get raw values.
    List<VariableValue> rawValues = DataUtil.getRawValues(target_p, getAskingContext());
    // Prepare environment rule contexts.
    Map<String, EnvironmentRuleContext> environmentIdToContext = new HashMap<String, EnvironmentRuleContext>(0);
    Map<EnvironmentAttributes, EnvironmentVariableValue> attributesToValue = new HashMap<EnvironmentAttributes, EnvironmentVariableValue>(0);
    // Variables handler.
    IVariablesHandler handler = new AbstractVariablesHandler() {
      /**
       * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#getSubstitutedValue(java.lang.String)
       */
      @SuppressWarnings("synthetic-access")
      @Override
      public String getSubstitutedValue(String rawValue_p) {
        // Precondition.
        if (null == rawValue_p) {
          return null;
        }
        return DataUtil.getSubstitutedValue(rawValue_p, getAskingContext());
      }

      /**
       * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractVariablesHandler#getVariableType()
       */
      @Override
      public VariableType getVariableType() {
        return (isConfigurationDirectoriesValidation ? VariableType.ConfigurationDirectories : VariableType.Artefacts);
      }
    };
    // Environment activator.
    EnvironmentActivator environmentActivator = EnvironmentActivator.getInstance();
    // Set variables handler.
    boolean handlerSet = environmentActivator.setVariablesHandlerIfNone(handler);
    try {
      EnvironmentRegistry environmentRegistry = EnvironmentActivator.getInstance().getEnvironmentRegistry();
      for (VariableValue variableValue : rawValues) {
        // Skip unexpected content.
        if (!(variableValue instanceof EnvironmentVariableValue)) {
          continue;
        }
        EnvironmentVariableValue environmentVariableValue = (EnvironmentVariableValue) variableValue;
        String environmentId = environmentVariableValue.getEnvironmentId();
        Couple<IStatus, EnvironmentDescriptor> environmentCouple = environmentRegistry.getEnvironmentDescriptor(environmentId);
        // Environment is not supported.
        if (!environmentCouple.getKey().isOK()) {
          continue;
        }
        // Prepare environment rule context.
        EnvironmentRuleContext context = environmentIdToContext.get(environmentId);
        if (null == context) {
          if (isConfigurationDirectoriesValidation) {
            // Configuration directories validation does require a specific additional information.
            ConfigurationDirectoriesRuleContext configurationDirectoriesRuleContext = new ConfigurationDirectoriesRuleContext();
            configurationDirectoriesRuleContext._configurationDirectoryAbsolutePath =
                DataUtil.getValues(DataUtil.getVariable(DataUtil.__CONFIGURATIONDIRECTORY_VARIABLE_NAME, getAskingContext()), getAskingContext()).get(0)
                    .getValue();
            context = configurationDirectoriesRuleContext;
          } else {
            // Default case (applicable to ArtefactPath).
            context = new EnvironmentRuleContext();
          }
          context._environmentId = environmentId;
          // Get to descriptor.
          context._environmentDescriptor = environmentCouple.getValue();
          // Retain context.
          environmentIdToContext.put(environmentId, context);
        }
        // Prepare context.
        EnvironmentContext envContext = new EnvironmentContext();
        envContext._allowUserInteractions = false; // No user interaction allowed while validating.
        // Get raw environment attributes.
        envContext._attributes = ModelUtil.convertEnvironmentVariableValues(environmentVariableValue);
        // Get handler and expanded attributes.
        IEnvironmentHandler environmentHandler = context._environmentDescriptor.getHandler();
        envContext._expandedAttributes = environmentHandler.getExpandedAttributes(envContext);
        // Retain attributes.
        context.addAttributes(envContext);
        // Retain environment variable value.
        attributesToValue.put(envContext, environmentVariableValue);
        // Special case for Make Baseline process.
        {
          // Check that this is indeed the case.
          if (getValidationContext() instanceof MakeBaselineValidationContext) {
            // Get MakeBaselineValidationContext.
            MakeBaselineValidationContext makeBaselineValidationContext = (MakeBaselineValidationContext) getValidationContext();
            // Before validating check environment against usage of OS env. variables.
            // Such variables are unlikely to be persisted by the context, and as such prone to error when the baseline will have to be restored.
            // A warning to the user is expected to enforce user approval about proceeding with the make baseline process.
            Map<String, List<String>> uncompressedAttributes = environmentHandler.getUncompressedAttributes(envContext);
            // Cycle through environment raw values.
            for (List<String> envRawValues : uncompressedAttributes.values()) {
              for (String rawValue : envRawValues) {
                makeBaselineValidationContext.addEnvironmentVariableNamesFor(elementPath, rawValue);
              }
            }
          }
        }
      }
      // Do validate.
      Map<EnvironmentAttributes, List<String>> errors = environmentRegistry.getRuleRegistry().validate(environmentIdToContext.values(), getValidationType());
      // Extract errors.
      for (Entry<EnvironmentAttributes, List<String>> error : errors.entrySet()) {
        // Environment attributes.
        EnvironmentAttributes environmentAttributes = error.getKey();
        // And context.
        EnvironmentRuleContext environmentRuleContext = environmentIdToContext.get(environmentAttributes._environmentId);
        // Try and resolve environment String representation.
        String environmentString = ICommonConstants.EMPTY_STRING;
        if (null != environmentRuleContext) {
          environmentString = environmentRuleContext._environmentDescriptor.getHandler().toString(environmentAttributes._attributes);
        }
        // Get associated model element.
        EnvironmentVariableValue environmentVariableValue = attributesToValue.get(environmentAttributes);
        // Register all failure messages to the same element.
        for (String failureMessage : error.getValue()) {
          addStatus(createFailureStatusWithDescription(environmentVariableValue, context_p, elementPath, failureMessage, environmentString));
        }
      }
    } finally {
      // Unset variables handler, if needed.
      if (handlerSet) {
        environmentActivator.setVariablesHandler(null);
      }
    }
    // So far only the two above variables should be of type EnvironmentVariable.
    return null;
  }
}