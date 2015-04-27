/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.tests;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.tests.internal.model.AbstractModelTest;

import junit.framework.Assert;

/**
 * @author t0076261
 * @author s0011584
 */
@SuppressWarnings("nls")
public class DataUtilTest extends AbstractModelTest {
  public void testContributedCategoryContent() {
    Category contributedCategory = DataUtil.getCategory("\\Orchestra\\LinkManager\\Contributed category");
    assertNotNull("Expecting to get contributed category to LinkManager one", contributedCategory);
    AbstractVariable contributedVariable = DataUtil.getVariable("\\Orchestra\\LinkManager\\Contributed category\\Contributed variable");
    assertNotNull("Expecting to get variable from contributed category to LinkManager one", contributedVariable);
    assertEquals("Accessing contributed variable from contributed category or full path should lead to the same instance",
        DataUtil.getVariables(contributedCategory).iterator().next(), contributedVariable);
    assertEquals("Ensure contributed variable from contributed category has correct value", "Contribution to LinkManager category", contributedVariable
        .getValues().get(0).getValue());
  }

  public void testGetAVariableContainedInACategoryOfAnotherContext() {
    AbstractVariable variable = DataUtil.getVariable("\\Themis\\A380");
    Assert.assertNotNull("The variable exists, it should be retrieved.", variable);
  }

  public void testGetValuesShouldNotFailWithANullVariable() throws Exception {
    Context context = ModelHandlerActivator.getDefault().getDataHandler().getContext("/THAV/THAV.contexts"); //$NON-NLS-1$
    try {
      List<VariableValue> values = DataUtil.getValues((AbstractVariable) null, context);
      assertNotNull(values);
      assertEquals(0, values.size());
    } catch (NullPointerException exceptionP) {
      fail("GetValues should not fail with a null variable."); //$NON-NLS-1$
    }
  }

  public void testReadCategoriesAndVariables() {
    Context thavContext = ModelHandlerActivator.getDefault().getDataHandler().getContext("/THAV/THAV.contexts");
    Context daeContext = DataUtil.getParentContext("DAE", thavContext);
    // Find themis category from its name.
    Category themisCategory = DataUtil.getCategory("\\Themis", thavContext);
    Assert.assertNotNull("Unable to retrieve a category from its name.", themisCategory);
    Assert.assertEquals("Expecting the themis category.", "Themis", themisCategory.getName());
    // Resolve themis category variables at Thav level.
    Collection<AbstractVariable> variables = DataUtil.getVariables(themisCategory, thavContext);
    Assert.assertEquals("Ensure themis category contains 3 variables at Thav level", 3, variables.size());
    // Resolve themis category variables at DAE level.
    Collection<AbstractVariable> variables2 = DataUtil.getVariables(themisCategory, daeContext);
    Assert.assertEquals("Ensure themis category contains 2 variables at DAE level", 2, variables2.size());
    // Remove shared variables.
    variables.removeAll(variables2);
    Assert.assertEquals("Ensure themis category contains 1 extra variable at Thav level", 1, variables.size());
    Assert.assertEquals("Ensure themis category has a contributed foreing variable named A380", "A380", variables.iterator().next().getName());
  }

  public void testSubstituionWithCycleDetection() {
    Context context = ModelHandlerActivator.getDefault().getDataHandler().getContext("/Cycle/Cycle.contexts");
    IStatus validationResult = getValidationHelper().validateElement(context, context);
    assertFalse("Validation of a context with cycling references should fail", validationResult.isOK());
  }

  public void testSubstitutionWithCycleShouldReturnRawValue() {
    Context context = ModelHandlerActivator.getDefault().getDataHandler().getContext("/Cycle/Cycle.contexts");
    AbstractVariable variable = DataUtil.getVariable("\\Test\\A rely on B", context);
    assertNotNull("Expecting to be able to retrieve the variable.", variable);
    List<VariableValue> values = DataUtil.getValues(variable, context);
    assertEquals(1, values.size());
    VariableValue variableValue = values.get(0);
    assertEquals("Cycle ! expecting the raw value.", "${odm:\\Test\\B rely on C}", variableValue.getValue());
  }

  public void testSubstitutionWithoutCycleShouldBeSucessfull() {
    Context context = ModelHandlerActivator.getDefault().getDataHandler().getContext("/Cycle/Cycle.contexts");
    AbstractVariable variable = DataUtil.getVariable("\\Success\\A rely on B", context);
    assertNotNull("Expecting to be able to retrieve the variable.", variable);
    List<VariableValue> values = DataUtil.getValues(variable, context);
    assertEquals(1, values.size());
    VariableValue variableValue = values.get(0);
    assertEquals("No cycle, expecting the substituted value", "AAAbbbvalueOfCBBBaaa", variableValue.getValue());
  }

  public void testEnvironmentPersistence() {
    // Test root persistence, without overrides.
    Context environmentContext = ModelHandlerActivator.getDefault().getDataHandler().getContext("/Environment/Environment.contexts");
    List<VariableValue> values = DataUtil.getValues(DataUtil.getVariable("\\Test\\paths", environmentContext), environmentContext);
    assertTrue("\\Test\\paths environment variable should contain at least one value", 0 < values.size());
    Collection<Object> objectsByType = EcoreUtil.getObjectsByType(values, ContextsPackage.Literals.ENVIRONMENT_VARIABLE_VALUE);
    assertEquals("\\Test\\paths environment variable should contain only environment variable values", values.size(), objectsByType.size());
    EnvironmentVariableValue firstEnvironment = (EnvironmentVariableValue) values.get(0);
    assertEquals(firstEnvironment.getValues().get("folders"), AllTests._absoluteRootLocation);
    assertEquals(firstEnvironment.getValues().get("filters"), ".svn");
  }

  public void testExtendedEnvironmentPeristence() {
    // Test overridden persistence.
    Context extendedEnvironmentContext = ModelHandlerActivator.getDefault().getDataHandler().getContext("/ExtendedEnvironment/ExtendedEnvironment.contexts");
    List<VariableValue> values = DataUtil.getValues(DataUtil.getVariable("\\Test\\paths", extendedEnvironmentContext), extendedEnvironmentContext);
    assertTrue("\\Test\\paths environment variable should contain at least one value", 0 < values.size());
    Collection<Object> objectsByType = EcoreUtil.getObjectsByType(values, ContextsPackage.Literals.ENVIRONMENT_VARIABLE_VALUE);
    assertEquals("\\Test\\paths environment variable should contain only environment variable values", values.size(), objectsByType.size());
    EnvironmentVariableValue firstEnvironment = (EnvironmentVariableValue) values.get(0);
    assertEquals(firstEnvironment.getValues().get("folders"), AllTests._absoluteRootLocation);
    assertEquals(firstEnvironment.getValues().get("filters"), "data");
  }

  public void testSuperExtendedEnvironmentPeristence() {
    // Test overridden persistence.
    Context extendedEnvironmentContext =
        ModelHandlerActivator.getDefault().getDataHandler().getContext("/SuperExtendedEnvironment/SuperExtendedEnvironment.contexts");
    List<VariableValue> values = DataUtil.getValues(DataUtil.getVariable("\\Test\\paths", extendedEnvironmentContext), extendedEnvironmentContext);
    assertTrue("\\Test\\paths environment variable should contain at least one value", 0 < values.size());
    Collection<Object> objectsByType = EcoreUtil.getObjectsByType(values, ContextsPackage.Literals.ENVIRONMENT_VARIABLE_VALUE);
    assertEquals("\\Test\\paths environment variable should contain only environment variable values", values.size(), objectsByType.size());
    EnvironmentVariableValue firstEnvironment = (EnvironmentVariableValue) values.get(0);
    assertEquals(firstEnvironment.getValues().get("folders"), "c:\\tmp");
    assertEquals(firstEnvironment.getValues().get("filters"), "data");
    assertEquals(firstEnvironment.getValues().get("A contributed new value"), "New value");
  }
}