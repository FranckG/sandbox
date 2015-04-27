/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model;

import java.util.List;

/**
 * Environment variable extra properties.<br>
 * In this case, the root {@link IVariable#getValues()} does no longer make sense (and is empty).<br>
 * Clients are urged to use {@link #getEnvironmentValues()} instead.
 * @author t0076261
 */
public interface IEnvironmentVariable extends IVariable {
  /**
   * Get environments.
   * @return
   */
  List<IEnvironment> getEnvironments();

  /**
   * An environment as stored in an {@link IEnvironmentVariable}.
   * @author t0076261
   */
  public interface IEnvironment {
    /**
     * Get environment category.
     * @return
     */
    String getCategory();

    /**
     * Get environment id.
     * @return
     */
    String getId();

    /**
     * Get environment values.
     * @return
     */
    List<IEnvironmentValue> getValues();
  }

  /**
   * An environment value.
   * @author t0076261
   */
  public interface IEnvironmentValue {
    /**
     * The value key, as defined by the handling environment.
     * @return
     */
    String getKey();

    /**
     * The values associated to the key.<br>
     * These are substituted values, as returned by the handling environment.
     * @return
     */
    List<String> getValues();
  }
}