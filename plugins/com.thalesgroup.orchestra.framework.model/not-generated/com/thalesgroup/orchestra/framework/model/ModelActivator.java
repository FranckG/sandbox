/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model;

import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.common.activator.AbstractActivator;
import com.thalesgroup.orchestra.framework.model.migration.IMigrationHandler;

/**
 * Framework model bundle activator.
 * @author t0076261
 */
public class ModelActivator extends AbstractActivator {
  /**
   * Shared instance.
   */
  private static ModelActivator __instance;
  /**
   * {@link IDataUtil} unique instance.
   */
  private IDataUtil _dataUtil;
  /**
   * Migration handler unique instance.
   */
  private IMigrationHandler _migrationHandler;

  /**
   * Get unique {@link IDataUtil} instance.
   * @return A not <code>null</code> implementation of {@link IDataUtil}.
   */
  public IDataUtil getDataUtil() {
    return _dataUtil;
  }

  /**
   * Get unique migration handler instance.
   * @return A not <code>null</code> implementation of {@link IMigrationHandler}.
   */
  public IMigrationHandler getMigrationHandler() {
    return _migrationHandler;
  }

  /**
   * Set unique {@link IDataUtil} to use.
   * @param dataUtil_p A not <code>null</code> implementation of {@link IDataUtil}.
   */
  public void setDataUtil(IDataUtil dataUtil_p) {
    _dataUtil = dataUtil_p;
  }

  /**
   * Set unique migration handler to use.
   * @param migrationHandler_p A not <code>null</code> implementation of {@link IMigrationHandler}.
   */
  public void setMigrationHandler(IMigrationHandler migrationHandler_p) {
    _migrationHandler = migrationHandler_p;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context_p) throws Exception {
    super.start(context_p);
    __instance = this;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context_p) throws Exception {
    super.stop(context_p);
    __instance = null;
  }

  /**
   * Get shared instance.
   * @return
   */
  public static ModelActivator getInstance() {
    return __instance;
  }
}