/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.baseline;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.validation.ValidationContext;

/**
 * A {@link ValidationContext} dedicated to the Make Baseline process.<br>
 * Allows to retain variables that are using (Operating System) environment variables.
 * @author t0076261
 */
public class MakeBaselineValidationContext extends ValidationContext {
  /**
   * Pattern instance matching (OS) environment variables (group(1) -> variable name).
   */
  private static final Pattern ENV_VARIABLE_REFERENCE_PATTERN = Pattern.compile("\\$\\{env_var:([^\\}]+)\\}"); //$NON-NLS-1$
  /**
   * The string representations of currently in-use environments not compliant with Make Baseline process.
   */
  private Collection<String> _nonCompliantEnvironments;
  /**
   * Variable path to used (OS) environment variable names.
   */
  private Map<String, Collection<String>> _variablePathToEnvVarName;

  /**
   * Constructor.
   * @param askingContext_p
   */
  public MakeBaselineValidationContext(Context askingContext_p) {
    super(askingContext_p);
    _variablePathToEnvVarName = new HashMap<String, Collection<String>>(0);
    _nonCompliantEnvironments = new HashSet<String>(0);
  }

  /**
   * Extract and retain (OS) environment variables names as referenced by specified variable in specified value.
   * @param variablePath_p
   * @param variableRawValue_p
   */
  public void addEnvironmentVariableNamesFor(String variablePath_p, String variableRawValue_p) {
    // Precondition.
    if ((null == variablePath_p) || (null == variableRawValue_p)) {
      return;
    }
    // Extract (OS) environment variables names.
    Collection<String> envVariableNames = extractEnvironmentVariablesNamesFrom(variableRawValue_p);
    // Retain them, if any.
    if (!envVariableNames.isEmpty()) {
      // Get existing collection.
      Collection<String> envVarReferences = _variablePathToEnvVarName.get(variablePath_p);
      // Create it if needed.
      if (null == envVarReferences) {
        envVarReferences = new HashSet<String>(0);
        _variablePathToEnvVarName.put(variablePath_p, envVarReferences);
      }
      // Then retain all new values.
      envVarReferences.addAll(envVariableNames);
    }
  }

  /**
   * Add specified non-compliant environment to current validation context.
   * @param environmentStringRepresentation_p
   */
  public void addNonCompliantEnvironment(String environmentStringRepresentation_p) {
    _nonCompliantEnvironments.add(environmentStringRepresentation_p);
  }

  /**
   * Get all (OS) environment variable names as referenced by specified variable raw value.
   * @param variableRawValue_p
   * @return
   */
  protected Collection<String> extractEnvironmentVariablesNamesFrom(String variableRawValue_p) {
    // Precondition.
    if (null == variableRawValue_p) {
      return Collections.emptyList();
    }
    Collection<String> result = new HashSet<String>(0);
    // Cycle on environment variables.
    Matcher envVariableReferenceMatcher = ENV_VARIABLE_REFERENCE_PATTERN.matcher(variableRawValue_p);
    while (envVariableReferenceMatcher.find()) {
      result.add(envVariableReferenceMatcher.group(1));
    }
    return result;
  }

  /**
   * Get environment variable uses for asking context.<br>
   * The key is the ODM variable path within the context.<br>
   * The value is a collection of (OS) environment variables names within this ODM variable.
   * @return
   */
  public Map<String, Collection<String>> getEnvironmentVariablesReferences() {
    return _variablePathToEnvVarName;
  }

  /**
   * Get collection of non-compliant environments, as string representations.
   * @return
   */
  public Collection<String> getNonCompliantEnvironments() {
    return _nonCompliantEnvironments;
  }
}