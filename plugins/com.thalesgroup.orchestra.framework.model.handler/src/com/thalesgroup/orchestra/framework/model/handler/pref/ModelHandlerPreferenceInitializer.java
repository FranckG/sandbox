/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.pref;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;

/**
 * Model handler preferences initializer.
 * @author t0076261
 */
public class ModelHandlerPreferenceInitializer extends AbstractPreferenceInitializer {
  /**
   * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
   */
  @Override
  public void initializeDefaultPreferences() {
    IPreferenceStore preferenceStore = ModelHandlerActivator.getDefault().getPreferenceStore();
    // Validation level, error by default.
    preferenceStore.setDefault(IPreferencesConstants.VALIDATION_LEVEL, IPreferencesConstants.VALIDATION_LEVEL_ERROR);
  }
}