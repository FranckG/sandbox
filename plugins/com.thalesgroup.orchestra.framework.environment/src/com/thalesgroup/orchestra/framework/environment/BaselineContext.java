/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A baseline context is made of information about baselines already successfully created in current baseline creation for a given environment type.
 * @author t0076261
 */
public class BaselineContext {
  /**
   * Attributes of environments that have already responded to the {@link IEnvironment#makeBaseline(BaselineContext)} command, for current declaration ID.
   */
  private Collection<BaselineEnvironmentAttributes> _attributes;
  /**
   * Baseline description, as chosen by user.
   */
  private String _baselineDescription;
  /**
   * Baseline name, as chosen by user.
   */
  private String _baselineName;
  /**
   * Environment declaration ID.
   */
  private String _environmentId;

  /**
   * Constructor.
   * @param baselineName_p The baseline name to use as a root name.
   * @param baselineDescription_p The baseline description to use.
   * @param environmentId_p The environment declaration ID.
   */
  public BaselineContext(String baselineName_p, String baselineDescription_p, String environmentId_p) {
    _attributes = new ArrayList<BaselineEnvironmentAttributes>(0);
    _baselineName = baselineName_p;
    _baselineDescription = baselineDescription_p;
    _environmentId = environmentId_p;
  }

  /**
   * Add new attributes.
   * @param runtimeId_p The environment runtime ID.
   * @param attributes_p A not <code>null</code> {@link BaselineEnvironmentAttributes} object containing not <code>null</code> maps.
   */
  public void addAttributes(String runtimeId_p, BaselineEnvironmentAttributes attributes_p) {
    // Precondition.
    if (null == attributes_p) {
      return;
    }
    // Set environment declaration ID.
    attributes_p._environmentId = _environmentId;
    // Set environment runtime ID.
    attributes_p._runtimeId = runtimeId_p;
    // Then add attributes.
    _attributes.add(attributes_p);
  }

  /**
   * Get {@link EnvironmentAttributes} for environments having already responded to {@link IEnvironment#makeBaseline(BaselineContext)} command within current
   * (ODM) context.
   * @return the attributes
   */
  public Collection<BaselineEnvironmentAttributes> getAttributes() {
    return Collections.unmodifiableCollection(_attributes);
  }

  /**
   * Get attributes for specified environment runtime ID.<br>
   * Note that this method is not intended to be used by {@link BaselineContext} fillers (i.e. environments within a make baseline process) but rather by the
   * centralized make baseline process once all environment baselines have been set.<br>
   * In particular, this method is not safe against concurrent modifications (such as the {@link #addAttributes(String, BaselineEnvironmentAttributes)} could
   * trigger).
   * @param runtimeId_p
   * @return
   */
  public BaselineEnvironmentAttributes getAttributesFor(String runtimeId_p) {
    // Precondition.
    if (null == runtimeId_p) {
      return null;
    }
    // Cycle through existing attributes.
    for (BaselineEnvironmentAttributes attributes : _attributes) {
      // Check against runtime ID.
      if (runtimeId_p.equals(attributes._runtimeId)) {
        return attributes;
      }
    }
    // None could be found.
    return null;
  }

  /**
   * Get baseline description, as chosen by user.<br>
   * Might be <code>null</code> if no description was chosen by user at creation time.
   * @return
   */
  public String getBaselineDescription() {
    return _baselineDescription;
  }

  /**
   * Get baseline name, as chosen by user.<br>
   * Might not be applicable to environment as is.
   * @return A not <code>null</code> baseline name.
   */
  public String getBaselineName() {
    return _baselineName;
  }

  /**
   * Get environment declaration ID.<br>
   * All {@link EnvironmentAttributes} within this context are from the same environment class.
   * @return
   */
  public String getEnvironmentId() {
    return _environmentId;
  }

  /**
   * Baseline {@link EnvironmentAttributes} is a specialized implementation that also retains the {@link BaselineResult}.<br>
   * Note that there is no expanded attributes map available in this case.<br>
   * Also, raw attributes are indeed resolved attributes, as injected (at context switch time) by the environment implementation.
   * @author t0076261
   */
  public class BaselineEnvironmentAttributes extends EnvironmentAttributes {
    /**
     * {@link BaselineResult} reference.
     */
    public BaselineResult _baselineResult;
  }
}