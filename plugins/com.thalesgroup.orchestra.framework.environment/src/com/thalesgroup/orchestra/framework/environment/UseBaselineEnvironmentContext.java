/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;

import java.util.Map;

/**
 * An environment context that handles the Use Baseline attributes.<br>
 * In such a case, the specific attributes should be stored in the _useBaselineAttributes map.<br>
 * What's more, every environments are linked to a {@link UseBaselineEnvironmentContext}.<br>
 * Still, some depend on choices on another environment 'before' them.<br>
 * For instance, in a configuration management situation, if several environments are pointing to the same view, they will all behave the same way as starting
 * points (ie they will all fork to the same new view).<br>
 * So the first environment to decide what the "use baseline" attributes will be is called a master one.<br>
 * And there might be several other ones, that are slaved to this master.<br>
 * When an environment is a slave, according to the
 * {@link IEnvironmentUseBaselineHandler#isSameUseBaselineContext(UseBaselineEnvironmentContext, UseBaselineEnvironmentContext)} implementation, its attributes
 * and type are delegated to its master.
 * @author T0052089
 */
public class UseBaselineEnvironmentContext extends EnvironmentContext {
  /**
   * Master context containing the "use baseline" attributes for "same" environments.<br>
   * <code>null</code> if current UseBaselineContext is a master one.
   * @see IEnvironmentUseBaselineHandler#isSameUseBaselineContext(UseBaselineEnvironmentContext, UseBaselineEnvironmentContext)
   */
  public UseBaselineEnvironmentContext _masterContext;

  /**
   * Use baseline attributes.<br>
   * <code>null</code> if current UseBaselineContext is not a master one.
   */
  public Map<String, String> _useBaselineAttributes;

  /**
   * Use baseline type.<br>
   * <code>null</code> if current UseBaselineContext is not a master one.
   */
  public UseBaselineType _useBaselineType;

  /**
   * Return the "use baseline" attributes map of this UseBaselineContext.<br>
   * If this UseBaselineContext has a master then the master's attributes are returned.<br>
   * If it has no master (it is itself a master) its attributes are returned.
   * @return
   */
  public Map<String, String> getUseBaselineAttributes() {
    // Current UseBaselineContext is not a master one -> get attributes from master.
    if (null != _masterContext) {
      return _masterContext.getUseBaselineAttributes();
    }
    // Current UseBaselineContext is a master one.
    return _useBaselineAttributes;
  }

  /**
   * Return the use baseline type of this UseBaselineContext.<br>
   * If this UseBaselineContext has a master then the master's type is returned.<br>
   * If it has no master (it is itself a master) its type is returned.
   * @return
   */
  public UseBaselineType getUseBaselineType() {
    if (null != _masterContext) {
      return _masterContext.getUseBaselineType();
    }
    return _useBaselineType;
  }
}