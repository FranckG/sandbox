/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.validation;

import java.util.ArrayList;
import java.util.List;

import com.thalesgroup.orchestra.framework.environment.EnvironmentAttributes;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry.EnvironmentDescriptor;

/**
 * A context that references environment values that should be validated.<br>
 * There is such a context per environment ID.<br>
 * It does contain all raw and expanded attributes of all environments currently in use (for this ID) and the descriptor for this environment.
 * @author T0076261
 */
public class EnvironmentRuleContext {
  /**
   * Environment attributes that should be validated.
   */
  public List<EnvironmentAttributes> _attributes;
  /**
   * Targeted environment descriptor.
   */
  public EnvironmentDescriptor _environmentDescriptor;
  /**
   * Environment declaration ID.
   */
  public String _environmentId;

  /**
   * Constructor.
   */
  public EnvironmentRuleContext() {
    _attributes = new ArrayList<EnvironmentAttributes>(0);
  }

  /**
   * Add new attributes.
   * @param attributes_p A not <code>null</code> {@link EnvironmentAttributes} object containing not <code>null</code> maps.
   */
  public void addAttributes(EnvironmentAttributes attributes_p) {
    // Precondition.
    if (null == attributes_p) {
      return;
    }
    // Set environment ID.
    attributes_p._environmentId = _environmentId;
    // Then add attributes.
    _attributes.add(attributes_p);
  }
}