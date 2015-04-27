/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;


/**
 * An environment invocation context.<br>
 * Note that only _rawAttributes is mandatory in this implementation.<br>
 * Also note that it stands for the environment attributes at service invocation time.<br>
 * Whether these are raw/expanded/baseline/... attributes is not specified here.<br>
 * The service description should specify the nature of these attributes.
 * @author t0076261
 */
public class EnvironmentContext extends EnvironmentAttributes {
  /**
   * <code>true</code> if interactions with user through UI are allowed, <code>false</code> to execute the service silently, resorting to logs only in error
   * cases.
   */
  public boolean _allowUserInteractions;
}