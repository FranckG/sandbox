/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;

import java.util.List;
import java.util.Map;

/**
 * Environment attributes.<br>
 * Stands for an instantiation of an environment within an ODM context.<br>
 * These are the environment attributes currently defined/in use.
 * @author t0076261
 */
public class EnvironmentAttributes {
  /**
   * Environment declaration ID.<br>
   * Might be <code>null</code>.
   */
  public String _environmentId;
  /**
   * Environment runtime ID.<br>
   * Might be <code>null</code>.
   */
  public String _runtimeId;
  /**
   * Expanded environment attributes.<br>
   * See {@link IEnvironmentHandler#getExpandedAttributes(Map)} for details.<br>
   * Might be <code>null</code>.
   */
  public Map<String, List<String>> _expandedAttributes;
  /**
   * Environment attributes.<br>
   * Most likely raw ones, but might depend on caller (which should specify what they stand for).<br>
   * Must not be <code>null</code>.
   */
  public Map<String, String> _attributes;
}