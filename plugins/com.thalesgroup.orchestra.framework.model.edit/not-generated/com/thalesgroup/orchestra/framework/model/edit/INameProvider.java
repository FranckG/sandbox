/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.edit;

import org.eclipse.emf.ecore.EObject;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;

/**
 * Name provider.
 * @author t0076261
 */
public interface INameProvider {
  /**
   * Compute a new unique name for specified {@link NamedElement} in the specified {@link Context}.
   * @param editionContext_p
   * @param element_p
   * @param parent_p
   * @return
   */
  public String computeUniqueName(Context editionContext_p, NamedElement element_p, EObject parent_p);
}