/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.data;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContributedElement;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.edit.INameProvider;

/**
 * Name provider.
 * @author t0076261
 */
public class NameProvider implements INameProvider {
  /**
   * @see com.thalesgroup.orchestra.framework.model.edit.INameProvider#computeUniqueName(com.thalesgroup.orchestra.framework.model.contexts.Context,
   *      com.thalesgroup.orchestra.framework.model.contexts.NamedElement, org.eclipse.emf.ecore.EObject)
   */
  public String computeUniqueName(Context editionContext_p, NamedElement element_p, EObject parent_p) {
    String resultingName = ICommonConstants.EMPTY_STRING;
    EObject container = parent_p;
    if (element_p instanceof ContributedElement) {
      Category superCategory = ((ContributedElement) element_p).getSuperCategory();
      if (null != superCategory) {
        container = superCategory;
      }
    }
    // Precondition.
    if (null == container) {
      return resultingName;
    }
    // Name is at least composed of the type name.
    String rootName = element_p.eClass().getName();
    // Get potential matches, that would collide with chosen name.
    List<NamedElement> elementsToCompareTo = new ArrayList<NamedElement>(0);
    if (container instanceof Category) {
      Category category = (Category) container;
      if (element_p instanceof Variable) {
        elementsToCompareTo.addAll(DataUtil.getVariables(category, editionContext_p));
      } else if (element_p instanceof Category) {
        elementsToCompareTo.addAll(DataUtil.getCategories(category, editionContext_p));
      }
    } else if (container instanceof Context) {
      elementsToCompareTo.addAll(ModelUtil.getCategories(editionContext_p));
    }
    // Try and find a new name.
    int suffix = 0;
    resultingName = null;
    while (null == resultingName) {
      resultingName = rootName + suffix;
      for (NamedElement element : elementsToCompareTo) {
        String currentName = element.getName();
        // Concurrent name found, try a new one !
        if (resultingName.equals(currentName)) {
          resultingName = null;
          break;
        }
      }
      suffix++;
    }
    return resultingName;
  }
}