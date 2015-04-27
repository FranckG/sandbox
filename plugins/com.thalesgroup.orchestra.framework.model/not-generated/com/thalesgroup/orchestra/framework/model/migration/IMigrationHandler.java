/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.migration;

import org.eclipse.emf.ecore.resource.ResourceSet;

import com.thalesgroup.orchestra.framework.model.contexts.Context;

/**
 * Model migration handler.
 * @author t0076261
 */
public interface IMigrationHandler {
  /**
   * Do activate migration support.
   */
  public void activateMigrationSupport(ResourceSet resourceSet_p);

  /**
   * Is current handler able (and willing) to migrate specified context ?
   * @param context_p
   * @return <code>true</code> if so, <code>false</code> not to migrate specified context.
   */
  public boolean isHandlingMigrationFor(Context context_p);

  /**
   * Migrate specified context.
   * @param context_p A not <code>null</code> {@link Context} model element to migrate.
   */
  public void migrateContext(Context context_p);
}