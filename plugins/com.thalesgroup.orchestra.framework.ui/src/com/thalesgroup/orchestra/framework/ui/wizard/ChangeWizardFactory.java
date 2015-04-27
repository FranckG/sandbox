/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.wizard;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.wizard.Wizard;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.EditCategoryWizard;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.EditContextWizard;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.EditOverridingVariableWizard;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.EditVariableWizard;
import com.thalesgroup.orchestra.framework.ui.viewer.component.AbstractNode;

/**
 * A Change Wizard factory.
 * @author t0076261
 */
public class ChangeWizardFactory {
  /**
   * Adapt specified object to underlying element, if any.
   * @param object_p
   * @return
   */
  private static Object adaptToElement(Object object_p) {
    Object result = object_p;
    if (result instanceof AbstractNode<?>) {
      result = ((AbstractNode<?>) result).getValue();
    }
    return result;
  }

  /**
   * Create change wizard for specified model object and specified edition context.
   * @param object_p
   * @param context_p
   * @return
   */
  public static Wizard createChangeWizardFor(Object object_p, final Context context_p) {
    Object object = adaptToElement(object_p);
    Class<?> wizardType = getWizardTypeFor(object, context_p);
    if (EditCategoryWizard.class.equals(wizardType)) {
      return new EditCategoryWizard((Category) object);
    } else if (EditContextWizard.class.equals(wizardType)) {
      return new EditContextWizard((Context) object);
    } else if (EditVariableWizard.class.equals(wizardType)) {
      return new EditVariableWizard(getVariableFor(object), context_p);
    } else if (EditOverridingVariableWizard.class.equals(wizardType)) {
      return new EditOverridingVariableWizard(getVariableFor(object), context_p);
    }
    return null;
  }

  /**
   * Get variable for specified object.
   * @param object_p Either a {@link VariableValue} or a {@link Variable} directly.
   * @return
   */
  protected static Variable getVariableFor(Object object_p) {
    Variable variable = null;
    if (object_p instanceof VariableValue) {
      variable = getVariableFor(((VariableValue) object_p).eContainer());
    } else if (object_p instanceof Variable) {
      variable = (Variable) object_p;
    } else if (object_p instanceof OverridingVariable) {
      variable = ((OverridingVariable) object_p).getOverriddenVariable();
    }
    // Should not be null at this step because of the getWizardTypeFor implementation.
    return variable;
  }

  /**
   * Get expected wizard type for specified element in specified context.
   * @param object_p
   * @param context_p
   * @return
   */
  public static Class<?> getWizardTypeFor(Object object_p, final Context context_p) {
    // Adapt to real object.
    Object object = adaptToElement(object_p);
    // Precondition.
    if (!(object instanceof EObject)) {
      return null;
    }
    return new ContextsSwitch<Class<?>>() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseCategory(com.thalesgroup.orchestra.framework.model.contexts.Category)
       */
      @Override
      public Class<?> caseCategory(Category category_p) {
        if (DataUtil.isUnmodifiable(category_p, context_p)) {
          return null;
        } else if (context_p != ModelUtil.getContext(category_p)) {
          return null;
        }
        return EditCategoryWizard.class;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseContext(com.thalesgroup.orchestra.framework.model.contexts.Context)
       */
      @Override
      public Class<?> caseContext(Context editedContext_p) {
        if (DataUtil.isUnmodifiable(editedContext_p, context_p)) {
          return null;
        }
        return EditContextWizard.class;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariable(com.thalesgroup.orchestra.framework.model.contexts.Variable)
       */
      @Override
      public Class<?> caseVariable(Variable variable_p) {
        // The variable is contained in edition context, edit it.
        if (context_p == ModelUtil.getContext(variable_p)) {
          return EditVariableWizard.class;
        }
        // It is neither a simple variable, or a super category one, thus it must be an overridden one.
        return EditOverridingVariableWizard.class;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariableValue(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
       */
      @Override
      public Class<?> caseVariableValue(VariableValue variableValue_p) {
        Variable variable = getVariableFor(variableValue_p);
        if (null != variable) {
          return caseVariable(variable);
        }
        return null;
      }
    }.doSwitch((EObject) object);
  }
}