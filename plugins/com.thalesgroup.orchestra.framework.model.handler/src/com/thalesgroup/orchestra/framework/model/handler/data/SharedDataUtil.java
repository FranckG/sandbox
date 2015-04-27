/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.data;

import org.eclipse.emf.ecore.EObject;

import com.thalesgroup.orchestra.framework.model.IDataUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;

/**
 * An implementation of {@link IDataUtil} based on {@link DataUtil} static implementations.<br>
 * Useful for dependency injection only.
 * @author t0076261
 */
public class SharedDataUtil implements IDataUtil {
  /**
   * @see com.thalesgroup.orchestra.framework.model.IDataUtil#getCategory(java.lang.String, com.thalesgroup.orchestra.framework.model.contexts.Context)
   */
  public Category getCategory(String categoryPathName_p, Context context_p) {
    return DataUtil.getCategory(categoryPathName_p, context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IDataUtil#makeFlatCopy(org.eclipse.emf.ecore.EObject,
   *      com.thalesgroup.orchestra.framework.model.contexts.Context, boolean)
   */
  public EObject makeFlatCopy(EObject element_p, Context context_p, boolean flushIds_p) {
    return DataUtil.makeFlatCopy(element_p, context_p, flushIds_p);
  }
}