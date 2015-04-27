/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.validation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentAttributes;

/**
 * Environment rule registry.<br>
 * Creates rules and handles their life-cycles.
 * @author t0076261
 */
public class EnvironmentRuleRegistry extends AbstractEnvironmentRule {
  /**
   * Contributed environment specific rules.<br>
   * So far, this is just a list, as there is no need (yet) for filtering.<br>
   * Still this is a default implementation that takes no advantage of required declaration structure.
   */
  private List<AbstractEnvironmentRule> _rules;

  /**
   * Constructor.
   */
  public EnvironmentRuleRegistry() {
    _rules = new ArrayList<AbstractEnvironmentRule>(0);
  }

  /**
   * Validate for specified contexts and validation type.
   * @param contexts_p
   * @param validationType_p
   * @return
   */
  public Map<EnvironmentAttributes, List<String>> validate(Collection<EnvironmentRuleContext> contexts_p, ValidationType validationType_p) {
    // Retain validation type.
    _validationType = validationType_p;
    try {
      return super.validate(contexts_p);
    } finally {
      // Release current validation type.
      _validationType = null;
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.validation.AbstractEnvironmentRule#doValidate()
   */
  @Override
  protected void doValidate() {
    // Cycle through rules.
    for (AbstractEnvironmentRule rule : getAllApplicableRules()) {
      // Precondition : make sure rule applies.
      if (!rule._validationType.isApplicableToType(_validationType)) {
        // Skip incompatible rule.
        continue;
      }
      // Validate.
      Map<EnvironmentAttributes, List<String>> errors = rule.validate(getContexts());
      // Add errors to resulting ones.
      for (Entry<EnvironmentAttributes, List<String>> error : errors.entrySet()) {
        EnvironmentAttributes environment = error.getKey();
        for (String failureMessage : error.getValue()) {
          addFailure(environment, failureMessage);
        }
      }
    }
  }

  /**
   * Get all applicable rules.
   * @return
   */
  public Collection<AbstractEnvironmentRule> getAllApplicableRules() {
    return new ArrayList<AbstractEnvironmentRule>(_rules);
  }

  /**
   * Initialize registry.
   * @return
   */
  public IStatus initialize() {
    String pluginId = EnvironmentActivator.getInstance().getPluginId();
    MultiStatus result = new MultiStatus(pluginId, 0, Messages.EnvironmentRuleRegistry_Initialization_WrapUp, null);
    // Create rules from extensions.
    IConfigurationElement[] configurationElements =
        ExtensionPointHelper.getConfigurationElements("com.thalesgroup.orchestra.framework.environment", "environmentValidationRule"); //$NON-NLS-1$ //$NON-NLS-2$
    // Cycle through extensions.
    for (IConfigurationElement configurationElement : configurationElements) {
      // Environment ID.
      String environmentId = configurationElement.getAttribute("environmentDeclarationId"); //$NON-NLS-1$
      // Get validation rules children.
      IConfigurationElement[] children = configurationElement.getChildren("validationRule"); //$NON-NLS-1$
      // Precondition.
      if (null == children) {
        continue;
      }
      for (IConfigurationElement childConfigurationElement : children) {
        // Make sure there is a class provided.
        if (null == childConfigurationElement.getAttribute(ExtensionPointHelper.ATT_CLASS)) {
          result.add(new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.EnvironmentRuleRegistry_Error_ClassNotDefined, environmentId)));
          continue;
        }
        // Try and instantiate it.
        AbstractEnvironmentRule validationRule =
            (AbstractEnvironmentRule) ExtensionPointHelper.createInstance(childConfigurationElement, ExtensionPointHelper.ATT_CLASS);
        if (null == validationRule) {
          result
              .add(new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.EnvironmentRuleRegistry_Error_ClassCannotBeInstantiated, environmentId)));
          continue;
        }
        // Try and get validation type.
        String validationScope = childConfigurationElement.getAttribute("ruleScope"); //$NON-NLS-1$
        if ((null == validationScope) || ICommonConstants.EMPTY_STRING.equals(validationScope.trim())) {
          validationRule._validationType = ValidationType.NOMINAL_TYPE;
        } else {
          validationRule._validationType = ValidationType.valueOf(validationScope);
        }
        // Keep a reference to the rule.
        _rules.add(validationRule);
        result.add(new Status(IStatus.OK, pluginId, MessageFormat.format(Messages.EnvironmentRuleRegistry_Ok_Rule_Loaded, environmentId)));
      }
    }
    return result;
  }

  /**
   * An enumeration that defines the available types of validations.
   * @author t0076261
   */
  public enum ValidationType {
    MAKE_BASELINE_TYPE, NOMINAL_TYPE, USE_BASELINE_TYPE;

    /**
     * Is current validation type to be retained in the scope of specified one ?
     * @param validationType_p
     * @return
     */
    public boolean isApplicableToType(ValidationType validationType_p) {
      // Precondition : NOMINAL_TYPE applies to anything.
      if (NOMINAL_TYPE == ValidationType.this) {
        return true;
      }
      // Only equal types
      return (validationType_p == ValidationType.this);
    }
  }
}