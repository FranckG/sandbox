/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thalesgroup.orchestra.framework.environment.EnvironmentAttributes;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry.EnvironmentDescriptor;
import com.thalesgroup.orchestra.framework.environment.validation.EnvironmentRuleRegistry.ValidationType;

/**
 * Abstract environment rule class.<br>
 * Provide with input data.<br>
 * Also provides with resulting structure handling methods.
 * @author T0076261
 */
public abstract class AbstractEnvironmentRule {
  /**
   * All applicable contexts for this rule.<br>
   * Not all contexts are to be taken into account.<br>
   * This is up to the rule to select relevant values within contexts.
   */
  private List<EnvironmentRuleContext> _contexts;
  /**
   * Resulting errors.
   */
  private Map<EnvironmentAttributes, List<String>> _result;
  /**
   * The type of validation the rule is registered to.<br>
   * Note that it can not belong to several different types for the moment.
   */
  protected ValidationType _validationType;

  /**
   * Constructor.
   */
  public AbstractEnvironmentRule() {
    _contexts = new ArrayList<EnvironmentRuleContext>(0);
  }

  /**
   * Add a new failure message to specified context.
   * @param context_p A not <code>null</code> map of environment raw attributes as contained by one of the calling contexts..
   * @param failureMessage_p The failure details message. Must be formatted.
   */
  protected void addFailure(EnvironmentAttributes attributes_p, String failureMessage_p) {
    // Precondition.
    if ((null == attributes_p) || (null == failureMessage_p)) {
      return;
    }
    // Create resulting list if needed.
    List<String> failureMessages = _result.get(attributes_p);
    if (null == failureMessages) {
      failureMessages = new ArrayList<String>(1);
      _result.put(attributes_p, failureMessages);
    }
    // Then record failure.
    failureMessages.add(failureMessage_p);
  }

  /**
   * Do apply validation to contained contexts.
   */
  protected abstract void doValidate();

  /**
   * Get contexts to use.
   * @return
   */
  protected List<EnvironmentRuleContext> getContexts() {
    return _contexts;
  }

  /**
   * Get validation contexts for specified environment category defined in the {@link EnvironmentDescriptor} from the {@link EnvironmentRuleContext}.
   * @param environmentCategory_p A not <code>null</code> environment category
   * @return A not <code>null</code>, but possibly empty, collections of contexts
   */
  protected Collection<EnvironmentRuleContext> getContextsByCategory(String environmentCategory_p) {
    // Precondition
    if (null == environmentCategory_p) {
      return Collections.emptyList();
    }
    List<EnvironmentRuleContext> result = new ArrayList<EnvironmentRuleContext>();
    // Cycle through contexts
    for (EnvironmentRuleContext environmentRuleContext : _contexts) {
      // Filter category in CategoryDescriptor
      if (environmentCategory_p.equals(environmentRuleContext._environmentDescriptor.getCategory())) {
        result.add(environmentRuleContext);
      }
    }
    return result;
  }

  /**
   * Get validation contexts for specified environment ID.
   * @param environmentId_p A not <code>null</code> environment ID.
   * @return A not <code>null</code>, but possibly empty, collection of contexts.
   */
  protected Collection<EnvironmentRuleContext> getContextsFor(String environmentId_p) {
    // Precondition.
    if (null == environmentId_p) {
      return Collections.emptyList();
    }
    List<EnvironmentRuleContext> result = new ArrayList<EnvironmentRuleContext>(0);
    // Cycle through contexts.
    for (EnvironmentRuleContext environmentRuleContext : _contexts) {
      // Filter by environment ID.
      if (environmentId_p.equals(environmentRuleContext._environmentId)) {
        result.add(environmentRuleContext);
      }
    }
    return result;
  }

  /**
   * Set contexts to use.
   * @param contexts_p
   */
  protected void setContexts(Collection<EnvironmentRuleContext> contexts_p) {
    _contexts.clear();
    _contexts.addAll(contexts_p);
  }

  /**
   * Validate.
   * @param contexts_p The list of contexts applicable to this validation.
   * @return A not <code>null</code> (but possibly empty) map of failing environments (represented by raw attributes) along with failure messages.<br>
   *         A single environment can be linked to several failure messages, as it may violate several constraints at the same time.
   */
  @SuppressWarnings("finally")
  public Map<EnvironmentAttributes, List<String>> validate(Collection<EnvironmentRuleContext> contexts_p) {
    // Initialize validation result.
    if (null == _result) {
      _result = new HashMap<EnvironmentAttributes, List<String>>(0);
    } else {
      _result.clear();
    }
    // Precondition.
    if (null == contexts_p) {
      return _result;
    }
    // Set contexts.
    setContexts(contexts_p);
    // Do validate.
    try {
      doValidate();
    } finally {
      return _result;
    }
  }
}