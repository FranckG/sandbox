package com.thalesgroup.orchestra.framework.tests;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.validation.ValidationHelper;
import com.thalesgroup.orchestra.framework.tests.internal.model.AbstractModelTest;

import junit.framework.Assert;

@SuppressWarnings("nls")
public class DataModelTest extends AbstractModelTest {
  public void testCheckConstraintOnUniquenessVariableNameInACategory() {
    ContextsFactory factory = ContextsFactory.eINSTANCE;
    Context context = ContextsFactory.eINSTANCE.createContext();
    context.setName("Uniqueness tests");
    Category category = factory.createCategory();
    category.setName("category");
    context.getCategories().add(category);
    Variable variable = factory.createVariable();
    variable.setName("junit variable");
    VariableValue value = ContextsFactory.eINSTANCE.createVariableValue();
    value.setValue("value");
    variable.getValues().add(value);
    variable.setFinal(true);
    variable.setMandatory(false);
    category.getVariables().add(variable);
    Variable variable2 = factory.createVariable();
    variable2.setName("junit variable");
    VariableValue value2 = ContextsFactory.eINSTANCE.createVariableValue();
    value2.setValue("value2");
    variable2.getValues().add(value2);
    variable2.setFinal(false);
    variable2.setMandatory(true);
    category.getVariables().add(variable2);
    ValidationHelper validationHelper = getValidationHelper();
    IStatus validation = validationHelper.validateElement(category, context);
    Assert.assertFalse(validation.isOK());

    variable2.setName("junit variable 2");
    validation = validationHelper.validateElement(category, context);
    Assert.assertTrue(validation.isOK());
  }
}