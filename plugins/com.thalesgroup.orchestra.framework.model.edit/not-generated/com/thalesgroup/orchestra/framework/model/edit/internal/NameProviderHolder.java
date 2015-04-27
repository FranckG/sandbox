/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.edit.internal;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecore.EObject;

import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.edit.INameProvider;

/**
 * Name provider holder.
 * @author t0076261
 */
public class NameProviderHolder {
  /**
   * Unique instance.
   */
  private static NameProviderHolder __instance;
  /**
   * Name provider implementation.
   */
  private INameProvider _nameProvider;

  /**
   * Constructor.<br>
   * Read the platform searching for the implementation.
   */
  private NameProviderHolder() {
    IConfigurationElement[] configurationElements =
        ExtensionPointHelper.getConfigurationElements("com.thalesgroup.orchestra.framework.model.edit", "elementNameProvider"); //$NON-NLS-1$ //$NON-NLS-2$
    if ((null == configurationElements) || (0 == configurationElements.length)) {
      _nameProvider = new DefautNameProvider();
    } else {
      _nameProvider = (INameProvider) ExtensionPointHelper.createInstance(configurationElements[0], ExtensionPointHelper.ATT_CLASS);
    }
  }

  /**
   * Get name provider implementation.
   * @return
   */
  public INameProvider getNameProvider() {
    return _nameProvider;
  }

  /**
   * Get unique instance.
   * @return
   */
  public static NameProviderHolder getInstance() {
    if (null == __instance) {
      __instance = new NameProviderHolder();
    }
    return __instance;
  }

  /**
   * Default name provider.<br>
   * Create a new name based on both the element meta-class and a random double value.
   * @author t0076261
   */
  protected class DefautNameProvider implements INameProvider {
    /**
     * @see com.thalesgroup.orchestra.framework.model.edit.INameProvider#computeUniqueName(com.thalesgroup.orchestra.framework.model.contexts.Context,
     *      com.thalesgroup.orchestra.framework.model.contexts.NamedElement, org.eclipse.emf.ecore.EObject)
     */
    public String computeUniqueName(Context editionContext_p, NamedElement element_p, EObject parent_p) {
      return element_p.eClass().getName() + Math.random();
    }
  }
}