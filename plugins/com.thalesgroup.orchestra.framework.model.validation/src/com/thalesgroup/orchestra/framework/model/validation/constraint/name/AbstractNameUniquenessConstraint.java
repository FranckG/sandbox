/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.name;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;

/**
 * An abstract constraint that deals with name uniqueness.
 * @author t0076261
 */
public abstract class AbstractNameUniquenessConstraint<T extends ModelElement> extends AbstractConstraint<T> {
  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(T target_p, IValidationContext context_p) {
    // Get involved children.
    Collection<NamedElement> children = getChildren(target_p);
    // Precondition.
    if ((null == children) || children.isEmpty()) {
      return null;
    }
    // Create name to elements structure.
    Map<String, Collection<NamedElement>> nameToElement = new HashMap<String, Collection<NamedElement>>(0);
    for (NamedElement namedElement : children) {
      String name = namedElement.getName();
      Collection<NamedElement> elements = nameToElement.get(name);
      if (null == elements) {
        elements = new HashSet<NamedElement>(1);
        nameToElement.put(name, elements);
      }
      elements.add(namedElement);
    }
    // Create errors, if any.
    for (Entry<String, Collection<NamedElement>> entry : nameToElement.entrySet()) {
      Collection<NamedElement> elements = entry.getValue();
      if (elements.size() > 1) {
        addStatus(context_p.createFailureStatus(getErrorMessageArguments(target_p, elements)));
      }
    }
    return null;
  }

  /**
   * Get children for specified target, that should be implied in the name uniqueness check.
   * @param target_p
   * @return A possibly <code>null</code> or empty collection of {@link NamedElement}, if no element is to be checked.
   */
  protected abstract Collection<NamedElement> getChildren(T target_p);

  /**
   * Get error message arguments for specified target and named elements with duplicated names.
   * @param target_p The logical container of the elements with the same name.
   * @param elements_p A not <code>null</code>, not empty collection of {@link NamedElement}s with the same name.
   * @return
   */
  protected Object[] getErrorMessageArguments(T target_p, Collection<NamedElement> elements_p) {
    return new Object[] { getFullPath(target_p), elements_p.iterator().next().getName() };
  }
}