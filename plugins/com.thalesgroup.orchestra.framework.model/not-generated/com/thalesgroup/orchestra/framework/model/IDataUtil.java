/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model;

import org.eclipse.emf.ecore.EObject;

import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;

/**
 * @author t0076261
 */
public interface IDataUtil {
  /**
   * Find a category from its path name in the specified context.<br />
   * It cascades all super contexts until the context is found.<br />
   * return <code>null</code> if the specified context is not found.
   * @param categoryPathName_p the absolute path name of the category to look for.
   * @param context_p the context from which to start searching
   * @return the category that have been found or <code>null</code> if the category does not exists.
   */
  public Category getCategory(String categoryPathName_p, Context context_p);

  /**
   * Make a flat copy of specified element for specified context.<br>
   * If element is a contributed or overridden variable, return a new variable that contains a copy of specified variable content (unsubstituted).<br>
   * If element is a category, return a new category with all contributed/overridden variables dealt with as described above.<br>
   * If element is not eligible (a context, or an already flat category or variable), return the object itself.
   * @param element_p
   * @param context_p The edition context.
   * @param flushIds_p <code>true</code> to flush IDs, <code>false</code> to leave them untouched in the copy.<br>
   *          Does not apply if no copy is required.<br>
   *          Does not apply to {@link VariableValue} either (IDs are then always flushed).
   * @return The flat copy, or specified element if no copy could be done (or was necessary).
   */
  public EObject makeFlatCopy(EObject element_p, final Context context_p, final boolean flushIds_p);
}