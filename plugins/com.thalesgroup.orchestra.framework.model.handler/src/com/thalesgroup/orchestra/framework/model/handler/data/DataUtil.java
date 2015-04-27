/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;

/**
 * Provides convenient static methods for working with the data model.
 * @author t0076261
 */
public class DataUtil {
  /**
   * Artifact Path variable name
   */
  public static final String __ARTEFACTPATH_VARIABLE_NAME = "\\Orchestra\\ArtefactPath"; //$NON-NLS-1$
  /**
   * Orchestra category name
   */
  public static final String __CATEGORY_ORCHESTRA = "Orchestra"; //$NON-NLS-1$
  /**
   * Configuration Directories variable name
   */
  public static final String __CONFIGURATIONDIRECTORIES_VARIABLE_NAME = "\\Orchestra\\ConfigurationDirectories"; //$NON-NLS-1$
  /**
   * Configuration Directory variable name
   */
  public static final String __CONFIGURATIONDIRECTORY_VARIABLE_NAME = "\\Orchestra\\ConfigurationDirectory"; //$NON-NLS-1$
  /**
   * Shared Directory variable name.
   */
  public static final String __SHAREDDIRECTORY_VARIABLE_NAME = "\\Orchestra\\SharedDirectory"; //$NON-NLS-1$
  /**
   * Substitution context in use.
   */
  private static Context __substitutionContext;
  /**
   * Temporary Directory variable name.
   */
  public static final String __TEMPORARYDIRECTORY_VARIABLE_NAME = "\\Orchestra\\TemporaryDirectory"; //$NON-NLS-1$

  /**
   * Collect recursively all categories of this category.
   */
  private static Collection<Category> collectCategories(Category category_p, Context context_p) {
    Collection<Category> collected = new ArrayList<Category>(0);
    Collection<Category> categories = DataUtil.getCategories(category_p, context_p);
    for (Category category : categories) {
      collected.add(category);
      collected.addAll(collectCategories(category, context_p));
    }
    return collected;
  }

  /**
   * Flush specified {@link ModelElement}s ID attributes, including all sub-trees elements IDs too.
   * @param elements_p
   * @return Specified (modified) elements list.<br>
   *         For convenience only.
   */
  private static <T extends ModelElement> List<T> flushElementsIDs(List<T> elements_p) {
    // Precondition.
    if ((null == elements_p) || elements_p.isEmpty()) {
      return elements_p;
    }
    // Cycle through elements.
    for (T element : elements_p) {
      // Reset ID for element itself.
      element.setId(null);
      // And for all its contents.
      TreeIterator<EObject> allContents = element.eAllContents();
      while (allContents.hasNext()) {
        EObject eObject = allContents.next();
        if (eObject instanceof ModelElement) {
          ((ModelElement) eObject).setId(null);
        }
      }
    }
    // Return input list.
    return elements_p;
  }

  /**
   * Get shallow (level-1) categories for specified category in current context.<br>
   * This includes all contained and contributed categories.<br>
   * This does not search recursively.
   * @param category_p
   * @return an empty collection if specified category is <code>null</code>. In all other cases, at least a clone of directly contained categories (including
   *         the <code>null</code> context case). This collection is augmented with contributed categories at specified context level, if any.
   */
  public static Collection<Category> getCategories(Category category_p) {
    return getCategories(category_p, ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext());
  }

  /**
   * Get shallow (level-1) categories for specified category in specified context.<br>
   * This includes all contained and contributed categories.<br>
   * This does not search recursively.
   * @param category_p
   * @param context_p
   * @return an empty collection if specified category is <code>null</code>. In all other cases, at least a clone of directly contained categories (including
   *         the <code>null</code> context case). This collection is augmented with contributed categories at specified context level, if any.
   */
  public static Collection<Category> getCategories(Category category_p, Context context_p) {
    // Precondition.
    // No category provided, return empty list.
    if (null == category_p) {
      return Collections.emptyList();
    }
    // Resulting collection.
    // Add at least all contained variables.
    Collection<Category> result = new ArrayList<Category>(category_p.getCategories());
    // Precondition.
    // No context provided, return a clone of contained variables.
    if (null == context_p) {
      return result;
    }
    // Add all contributed categories.
    result.addAll(getContributedElements(category_p, context_p, Category.class));
    return result;
  }

  /**
   * Get all categories for specified context. This is a deep search.<br>
   * For example, if the context contains the categories <code>/A</code> and <code>/A/B</code>, both of them will be retrieved by this method.<br />
   * See {@link ModelUtil#getCategories(Context)} for level-1 categories only.
   * @param context_p the context to retrieve all categories of.
   * @return the list of all categories of this context.
   */
  public static Collection<Category> getCategories(Context context_p) {
    List<Category> categories = new ArrayList<Category>(0);
    List<Category> topLevelCategories = ModelUtil.getCategories(context_p);
    for (Category category : topLevelCategories) {
      categories.add(category);
      categories.addAll(collectCategories(category, context_p));
    }
    return categories;
  }

  /**
   * Recursively search for the category denoted by its path in the specified parent category.
   * @param parentCategory_p the parent category to look into.
   * @param categories_p the list of categories name (path) of the category to look for.
   * @param context_p the holding context.
   * @return the category found or <code>null</code> if the path does not leads to existing category.
   */
  private static Category getCategory(Category parentCategory_p, String[] categories_p, Context context_p) {
    // Base case.
    if (0 == categories_p.length) {
      return parentCategory_p;
    }
    // Recursion step.
    for (Category category : getCategories(parentCategory_p, context_p)) {
      if (categories_p[0].equals(category.getName())) {
        return getCategory(category, (String[]) ArrayUtils.subarray(categories_p, 1, categories_p.length), context_p);
      }
    }
    return null;
  }

  /**
   * Get category for specified path, for current context.<br>
   * This is strictly equivalent to calling {@link #getCategory(String, Context)} with context set to current one.
   * @param categoryPathName_p
   * @return
   */
  public static Category getCategory(String categoryPathName_p) {
    return getCategory(categoryPathName_p, ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext());
  }

  /**
   * Find a category from its path name in the specified context.<br />
   * It cascades all super contexts until the context is found.<br />
   * return <code>null</code> if the specified context is not found.
   * @param categoryPathName_p the absolute path name of the category to look for.
   * @param context_p the context from which to start searching
   * @return the category that have been found or <code>null</code> if the category does not exists.
   */
  public static Category getCategory(String categoryPathName_p, Context context_p) {
    String[] categories = StringUtils.split(categoryPathName_p, ICommonConstants.PATH_SEPARATOR);
    if (categories.length > 0) {
      // Find the first category in its containing context.
      String firstCategoryName = categories[0];
      Category firstCategory = null;
      for (Category category : ModelUtil.getCategories(context_p)) {
        if (firstCategoryName.equals(category.getName())) {
          firstCategory = category;
          break;
        }
      }
      // Recursively find the rest of the path contained in the first category.
      if (null != firstCategory) {
        return getCategory(firstCategory, (String[]) ArrayUtils.subarray(categories, 1, categories.length), context_p);
      }
    }
    return null;
  }

  /**
   * Get contributed elements for specified category within specified context.
   * @param <T> The type of the contributed elements. Currently either {@link Variable} or {@link Category}.
   * @param category_p
   * @param context_p
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> List<T> getContributedElements(Category category_p, Context context_p, Class<T> type_p) {
    // Precondition.
    if ((null == category_p) || (null == context_p)) {
      return Collections.emptyList();
    }
    ArrayList<Object> result = new ArrayList<Object>(0);
    // Get cross referencer.
    ECrossReferenceAdapter crossReferencer = DataUtil.getCrossReferencer(context_p);
    // Precondition.
    if (null == crossReferencer) {
      return Collections.emptyList();
    }
    // Compute inverse references.
    Collection<Setting> inverseReferences = crossReferencer.getInverseReferences(category_p);
    for (Setting setting : inverseReferences) {
      // Test reference against super category reference.
      EStructuralFeature structuralFeature = setting.getEStructuralFeature();
      if (ContextsPackage.Literals.CONTRIBUTED_ELEMENT__SUPER_CATEGORY.equals(structuralFeature)) {
        Object currentSelection = setting.getEObject();
        try {
          if (null != type_p.cast(currentSelection)) {
            // Get containing context.
            Context rootContext = ModelUtil.getContext(currentSelection);
            // Containing context is a parent of specified one.
            if (0 <= getLevel(context_p, rootContext)) {
              result.add(currentSelection);
            }
          }
        } catch (ClassCastException classCastException_p) {
          // Object is not of expected type.
          // This is not an issue, just skip it.
        }
      }
    }
    return (List<T>) result;
  }

  /**
   * Get cross referencer for specified context.
   * @param context_p
   * @return <code>null</code> if specified context is not associated to a cross referencer.
   */
  public static ECrossReferenceAdapter getCrossReferencer(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return null;
    }
    ContextsResourceImpl resource = context_p.eResource();
    // Precondition.
    if (null == resource) {
      return null;
    }
    // Get cross referencer from resource set.
    ResourceSet resourceSet = resource.getResourceSet();
    if (resourceSet instanceof ContextsResourceSet) {
      return ((ContextsResourceSet) resourceSet).getCrossReferencer();
    }
    return null;
  }

  /**
   * Get the number of levels separating specified child context from parent one.
   * @param childContext_p
   * @param parentContext_p
   * @return A positive integer being the number of levels between parent context and its child, <code>-1</code> if <code>childContext_p</code> is not a child
   *         of <code>parentContext_p</code>. <code>0</code> means both child and parent context are equal.
   */
  public static int getLevel(Context childContext_p, Context parentContext_p) {
    int result = -1;
    int currentPos = 0;
    Context currentContext = childContext_p;
    while (null != currentContext) {
      if (currentContext == parentContext_p) {
        result = currentPos;
        break;
      }
      currentContext = currentContext.getSuperContext();
      currentPos++;
    }
    return result;
  }

  /**
   * Get overriding variable for specified variable in specified context.
   * @param variable_p
   * @param context_p
   * @return <code>null</code> if no overriding variable could be found for specified context.
   */
  public static OverridingVariable getOverridingVariable(AbstractVariable variable_p, Context context_p) {
    // Precondition.
    if ((null == variable_p) || (null == context_p)) {
      return null;
    }
    OverridingVariable result = null;
    // Get cross referencer.
    ECrossReferenceAdapter crossReferencer = DataUtil.getCrossReferencer(context_p);
    if (null == crossReferencer) {
      return null;
    }
    // Compute inverse references.
    Collection<Setting> inverseReferences = crossReferencer.getInverseReferences(variable_p);
    int lowestLevel = Integer.MAX_VALUE;
    // Cycle through them.
    for (Setting setting : inverseReferences) {
      // Test reference against overridden reference.
      EStructuralFeature structuralFeature = setting.getEStructuralFeature();
      if (ContextsPackage.Literals.OVERRIDING_VARIABLE__OVERRIDDEN_VARIABLE.equals(structuralFeature)) {
        EObject currentSelection = setting.getEObject();
        // Test against containing context.
        Context rootContext = ModelUtil.getContext(currentSelection);
        int level = getLevel(context_p, rootContext);
        if (0 == level) {
          // Can't find a better match than current one.
          result = (OverridingVariable) currentSelection;
          break;
        } else if ((level > 0) && (level < lowestLevel)) {
          // Found a new closest candidate.
          lowestLevel = level;
          result = (OverridingVariable) currentSelection;
        }
      }
    }
    return result;
  }

  /**
   * Get all overriding variables for specified variable, from farthest one to closest one.
   * @param variable_p
   * @param context_p
   * @return A {@link List} of {@link OverridingVariable}, possibly empty, but not <code>null</code>.
   */
  public static List<OverridingVariable> getOverridingVariables(AbstractVariable variable_p, Context context_p) {
    List<OverridingVariable> result = new ArrayList<OverridingVariable>(0);
    // Precondition.
    if ((null == variable_p) || (null == context_p)) {
      return result;
    }
    // Start from closest context.
    Context currentContext = context_p;
    while (null != currentContext) {
      // Get overriding variable at current context.
      OverridingVariable overridingVariable = getOverridingVariable(variable_p, currentContext);
      if (null != overridingVariable) {
        if (!result.contains(overridingVariable)) {
          // Add as farthest one.
          result.add(0, overridingVariable);
        }
      }
      // Next context is a further one.
      currentContext = currentContext.getSuperContext();
    }
    return result;
  }

  /**
   * Get all overriding variables for specified category in specified context.<br>
   * That is get all variables for specified category, and then search for an overriding one for each at specified context level.<br>
   * Note that returned {@link OverridingVariable}s belong to specified context only (not a parent one).
   * @param category_p
   * @param context_p
   * @return
   */
  public static Collection<OverridingVariable> getOverridingVariables(Category category_p, Context context_p) {
    // Resulting collection.
    Collection<OverridingVariable> result = Collections.emptyList();
    // Get all variables.
    Collection<AbstractVariable> variables = getVariables(category_p, context_p);
    // Precondition.
    if (variables.isEmpty()) {
      return result;
    }
    // Initialize result.
    result = new ArrayList<OverridingVariable>(0);
    // Cycle through variables.
    for (AbstractVariable abstractVariable : variables) {
      // Search for an overriding one.
      OverridingVariable overridingVariable = getOverridingVariable(abstractVariable, context_p);
      // Add it to result.
      if ((null != overridingVariable) && (context_p == ModelUtil.getContext(overridingVariable))) {
        result.add(overridingVariable);
      }
    }
    return result;
  }

  /**
   * Get parent context of specified one, according to specified name.
   * @param contextName_p
   * @param childContext_p
   * @return <code>null</code> if parameters are <code>null</code> or no such context could be found.
   */
  public static Context getParentContext(String contextName_p, Context childContext_p) {
    // Precondition.
    if ((null == contextName_p) || (null == childContext_p)) {
      return null;
    }
    Context result = null;
    Context currentContext = childContext_p;
    // Cycle through parent contexts.
    while (null != currentContext) {
      // Found one !
      if (contextName_p.equals(currentContext.getName())) {
        result = currentContext;
        break;
      }
      currentContext = currentContext.getSuperContext();
    }
    return result;
  }

  /**
   * Get all participations in specified {@link Context}.
   * @param context_p
   * @return A not <code>null</code> but possibly empty list of {@link Context}.
   */
  public static List<Context> getParticipations(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return Collections.emptyList();
    }
    ArrayList<Context> result = new ArrayList<Context>(0);
    // Get cross referencer.
    ECrossReferenceAdapter crossReferencer = DataUtil.getCrossReferencer(context_p);
    // Compute inverse references.
    Collection<Setting> inverseReferences = crossReferencer.getInverseReferences(context_p);
    // Cycle through them.
    for (Setting setting : inverseReferences) {
      // Test reference against overridden reference.
      EStructuralFeature structuralFeature = setting.getEStructuralFeature();
      if (ContextsPackage.Literals.CONTEXT__SUPER_CONTEXT.equals(structuralFeature)) {
        EObject currentSelection = setting.getEObject();
        // Test against containing context.
        Context rootContext = ModelUtil.getContext(currentSelection);
        result.add(rootContext);
      }
    }
    return result;
  }

  /**
   * Get current raw values for specified variable.<br />
   * The values are a merge of all overridden ones up to specified context.<br />
   * @param variable_p
   * @param context_p
   * @return <code>null</code> if specified parameters are not valid. Resolved values (overridden one) otherwise.
   */
  public static synchronized List<VariableValue> getRawValues(AbstractVariable variable_p, Context context_p) {
    // Precondition.
    if ((null == variable_p) || (null == context_p)) {
      return null;
    }
    return getRawValues(variable_p, getOverridingVariables(variable_p, context_p));
  }

  /**
   * Get current raw values for specified variable.<br>
   * The values are a merge of all overridden ones up to specified ones.<br>
   * This is not intended to be used by users outside the core itself.<br>
   * Instead use {@link #getRawValues(AbstractVariable, Context)} and specify a (boundary) context.
   * @param variable_p
   * @param context_p
   * @return <code>null</code> if specified parameters are not valid. Resolved values (overridden one) otherwise.
   */
  public static synchronized List<VariableValue> getRawValues(AbstractVariable variable_p, List<OverridingVariable> overridingVariables_p) {
    // Precondition.
    if ((null == variable_p) || (null == overridingVariables_p)) {
      return null;
    }
    // Return local values only.
    if (overridingVariables_p.isEmpty()) {
      return variable_p.getValues();
    }
    List<VariableValue> unsubstitutedValues = new ArrayList<VariableValue>(0);
    // First of all, add all root values.
    unsubstitutedValues.addAll(variable_p.getValues());
    // Retain overriding values.
    // Indeed the list of overriding variables might contain several overridden values for a unique value.
    // In this case, only the closest value should be retained.
    // Map of (unsubstituted raw value, unsubstituted overriding value).
    Map<VariableValue, List<OverridingVariableValue>> overridingValues = new HashMap<VariableValue, List<OverridingVariableValue>>(0);
    // Add new contributed values and retain overriding ones.
    for (OverridingVariable overridingVariable : overridingVariables_p) {
      List<VariableValue> overridingVariableValues = overridingVariable.getValues();
      for (VariableValue variableValue : overridingVariableValues) {
        if (ContextsPackage.Literals.OVERRIDING_VARIABLE_VALUE.isInstance(variableValue)) {
          OverridingVariableValue overridingValue = (OverridingVariableValue) variableValue;
          VariableValue overriddenValue = overridingValue.getOverriddenValue();
          List<OverridingVariableValue> overridingValuesForAValue = overridingValues.get(overriddenValue);
          if (null == overridingValuesForAValue) {
            overridingValuesForAValue = new ArrayList<OverridingVariableValue>(1);
            overridingValues.put(overriddenValue, overridingValuesForAValue);
          }
          overridingValuesForAValue.add(overridingValue);
        } else {
          unsubstitutedValues.add(variableValue);
        }
      }
    }
    // Replace values with their overriding counterparts.
    ArrayList<VariableValue> clonedUnsubstitutedValues = new ArrayList<VariableValue>(unsubstitutedValues);
    for (VariableValue variableValue : clonedUnsubstitutedValues) {
      List<OverridingVariableValue> allOverridingValues = overridingValues.get(variableValue);
      // Skip value, nothing to override.
      if ((null == allOverridingValues) || allOverridingValues.isEmpty()) {
        continue;
      }
      // Get closest value.
      OverridingVariableValue overridingValue = allOverridingValues.get(allOverridingValues.size() - 1);
      // Replace value with overriding counterpart.
      if (null != overridingValue) {
        // Replace all occurrences of the variable value with its overriding one.
        Collections.replaceAll(unsubstitutedValues, variableValue, overridingValue);
      }
    }
    return unsubstitutedValues;
  }

  /**
   * Get substituted value from specified string.
   * @param value_p
   * @param context_p required for further substitution.
   * @return the substituted string or if an error occurred (e.g. : unresolved variable), the String given in value_p.
   */
  public static synchronized String getSubstitutedValue(String value_p, Context context_p) {
    try {
      __substitutionContext = context_p;
      return (null != value_p) ? VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(value_p) : ICommonConstants.EMPTY_STRING;
    } catch (Exception exception_p) {
      return value_p;
    } finally {
      // Unset context.
      __substitutionContext = null;
    }
  }

  /**
   * Get substituted value from specified variable value.
   * @param rawValue_p
   * @param context_p
   * @return <code>null</code> if specified parameters are not valid. Resolved value otherwise.
   */
  public static synchronized VariableValue getSubstitutedValue(VariableValue rawValue_p, Context context_p) {
    // This is either a standard variable value or an environment one.
    VariableValue result = (VariableValue) ContextsFactory.eINSTANCE.create(rawValue_p.eClass());
    // Substitute value.
    String value = getSubstitutedValue(rawValue_p.getValue(), context_p);
    result.setValue(value);
    // In environment case, map values are to be substituted too.
    if (rawValue_p instanceof EnvironmentVariableValue) {
      EnvironmentVariableValue rawEnvironmentVariableValue = (EnvironmentVariableValue) rawValue_p;
      EnvironmentVariableValue resultingEnvironmentVariableValue = (EnvironmentVariableValue) result;
      // Set attributes...
      resultingEnvironmentVariableValue.setEnvironmentId(rawEnvironmentVariableValue.getEnvironmentId());
      // ... then values.
      Iterator<Entry<String, String>> iterator = rawEnvironmentVariableValue.getValues().iterator();
      while (iterator.hasNext()) {
        Entry<String, String> entry = iterator.next();
        value = getSubstitutedValue(entry.getValue(), context_p);
        resultingEnvironmentVariableValue.getValues().put(entry.getKey(), value);
      }
    }
    return result;
  }

  /**
   * Get substitution context in use.
   * @return
   */
  public static Context getSubstitutionContext() {
    return __substitutionContext;
  }

  /**
   * Get first variable value from its path.
   * @param variablePathName_p
   * @return <code>null</code> if variable does not exist, or has no value, or first value is <code>null</code>.
   */
  public static VariableValue getValue(String variablePathName_p) {
    return getValue(variablePathName_p, ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext());
  }

  /**
   * Get first variable value from its path.
   * @param variablePathName_p
   * @param context_p
   * @return <code>null</code> if variable does not exist, or has no value, or first value is <code>null</code>.
   */
  public static VariableValue getValue(String variablePathName_p, Context context_p) {
    List<VariableValue> values = getValues(variablePathName_p, context_p);
    if ((null != values) && !values.isEmpty()) {
      return values.get(0);
    }
    return null;
  }

  /**
   * Get current values for specified variable.<br />
   * The values are a merge of all overridden ones up to the current context.<br />
   * This is strictly equivalent to calling {@link #getValues(AbstractVariable, Context)} with Context set to current one.
   * @param variable_p
   * @return
   */
  public static List<VariableValue> getValues(AbstractVariable variable_p) {
    return getValues(variable_p, ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext());
  }

  /**
   * Get current substituted values for specified variable.<br />
   * The values are a merge of all overridden ones up to specified context.<br />
   * @param variable_p
   * @param context_p
   * @return <code>null</code> if specified parameters are not valid. Resolved values (overridden and/or substituted one) otherwise.
   */
  public static synchronized List<VariableValue> getValues(AbstractVariable variable_p, Context context_p) {
    List<VariableValue> unsubstitutedValues = getRawValues(variable_p, context_p);
    // Substitution.
    List<VariableValue> values = new ArrayList<VariableValue>(0);
    if (null != unsubstitutedValues) {
      for (VariableValue variableValue : unsubstitutedValues) {
        VariableValue substitutedVariableValue = getSubstitutedValue(variableValue, context_p);
        if (null != substitutedVariableValue) {
          values.add(substitutedVariableValue);
        }
      }
    }
    // If no substitution could be done, return raw values.
    if (values.isEmpty() && (null != unsubstitutedValues)) {
      values.addAll(unsubstitutedValues);
    }
    return values;
  }

  /**
   * Get variable values from its path.
   * @param variablePath_p
   * @return
   */
  public static List<VariableValue> getValues(String variablePath_p) {
    return getValues(variablePath_p, ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext());
  }

  /**
   * Get variable values from its path for specified context.
   * @param variablePath_p
   * @param context_p
   * @return
   */
  public static List<VariableValue> getValues(String variablePath_p, Context context_p) {
    return getValues(getVariable(variablePath_p, context_p), context_p);
  }

  /**
   * Retrieve the variable from its name in the current context.
   * @param variablePathName_p the path name of the variable to retrieve
   * @return <code>null</code> if no variable exists for this path name.
   */
  public static AbstractVariable getVariable(String variablePathName_p) {
    return getVariable(variablePathName_p, ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext());
  }

  /**
   * Retrieve the variable from its path name in the current context.
   * @param variablePathName_p the path name of the variable to retrieve.
   * @param context_p the context from which this variable should be retrieved.
   * @return <code>null</code> if no variable exists for this path name.
   */
  public static AbstractVariable getVariable(String variablePathName_p, Context context_p) {
    String variableName = StringUtils.substringAfterLast(variablePathName_p, ICommonConstants.PATH_SEPARATOR);
    if (null != variableName) {
      // Get the category which contains the variable.
      String categoryPath = StringUtils.substringBeforeLast(variablePathName_p, ICommonConstants.PATH_SEPARATOR);
      Category category = getCategory(categoryPath, context_p);
      Collection<AbstractVariable> variables = getVariables(category, context_p);
      for (AbstractVariable abstractVariable : variables) {
        if (variableName.equals(abstractVariable.getName())) {
          return abstractVariable;
        }
      }
    }
    return null;
  }

  /**
   * see {@link #getVariables(Category, Context)}<br />
   * Strictly equivalent to Context set to current one.
   * @param category_p
   * @return
   */
  public static Collection<AbstractVariable> getVariables(Category category_p) {
    return getVariables(category_p, ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext());
  }

  /**
   * Get all variables for specified category in specified context.<br>
   * This includes variables directly contained by the category.<br>
   * And also all variables contributed by foreign contexts that are parents (including itself) of specified one.<br>
   * Note that none of the returned variables are either resolved or substituted.<br>
   * To do so, a call to {@link #getValues(AbstractVariable, Context)} must be explicitly done on every returned variable.
   * @param category_p
   * @param context_p
   * @return an empty collection if specified category is <code>null</code>. In all other cases, at least a clone of directly contained variables (including the
   *         <code>null</code> context case). This collection is augmented with contributed variables at specified context level, if any.
   */
  public static Collection<AbstractVariable> getVariables(Category category_p, Context context_p) {
    // Precondition.
    // No category provided, return empty list.
    if (null == category_p) {
      return Collections.emptyList();
    }
    // Resulting collection.
    // Add at least all contained variables.
    Collection<AbstractVariable> result = new ArrayList<AbstractVariable>(category_p.getVariables());
    // Precondition.
    // No context provided, return a clone of contained variables.
    if (null == context_p) {
      return result;
    }
    // Add all contributed variables.
    result.addAll(getContributedElements(category_p, context_p, AbstractVariable.class));
    return result;
  }

  /**
   * Get all variables for specified context.<br>
   * This is a deep search, so that all (contributed or local) categories are involved.<br>
   * Note that none of the returned variables are either resolved or substituted.<br>
   * To do so, a call to {@link #getValues(AbstractVariable, Context)} must be explicitly done on every returned variable.<br>
   * Also, please note that returned variables are never overriding variables (OverridingVariable).<br>
   * In order to do so, you will have to call {@link #getOverridingVariables(AbstractVariable, Context)} on every return variable.
   * @param context_p
   * @return A not <code>null</code>, but possibly empty, collection of {@link AbstractVariable}.
   */
  public static Collection<AbstractVariable> getVariables(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return Collections.emptyList();
    }
    Collection<AbstractVariable> result = new ArrayList<AbstractVariable>(0);
    for (Category category : getCategories(context_p)) {
      result.addAll(getVariables(category, context_p));
    }
    return result;
  }

  /**
   * Is specified model element NOT directly contained in current context ?
   * @param category_p
   * @return
   */
  public static boolean isExternalElement(ModelElement modelElement_p) {
    return isExternalElement(modelElement_p, ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext());
  }

  /**
   * Is specified model element NOT directly contained in specified context ?
   * @param category_p
   * @param context_p
   * @return
   */
  public static boolean isExternalElement(ModelElement modelElement_p, Context context_p) {
    // Precondition.
    if ((null == modelElement_p) || (null == context_p)) {
      return true;
    }
    return (context_p != ModelUtil.getContext(modelElement_p));
  }

  /**
   * Is specified default context installation category currently a selected version in specified context hierarchy.
   * @param installationCategory_p
   * @param context_p
   * @return <code>true</code> if so, <code>false</code> if parameters are invalid, or specified category it not referenced as a version in specified context
   *         hierarchy.
   */
  public static boolean isSelectedVersion(InstallationCategory installationCategory_p, Context context_p) {
    // Precondition.
    if ((null == installationCategory_p) || (null == context_p)) {
      return false;
    }
    // Get cross referencer.
    ECrossReferenceAdapter crossReferencer = DataUtil.getCrossReferencer(context_p);
    // Precondition.
    if (null == crossReferencer) {
      return false;
    }
    // Compute inverse references.
    Collection<Setting> inverseReferences = crossReferencer.getInverseReferences(installationCategory_p);
    for (Setting setting : inverseReferences) {
      // Test reference against referencing element resolved reference.
      EStructuralFeature structuralFeature = setting.getEStructuralFeature();
      if (ContextsPackage.Literals.REFERENCING_ELEMENT__RESOLVED_REFERENCE.equals(structuralFeature)) {
        Object currentSelection = setting.getEObject();
        // Get containing context.
        Context rootContext = ModelUtil.getContext(currentSelection);
        // Containing context is a parent of specified one.
        if (0 <= getLevel(context_p, rootContext)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Is specified model element NOT modifiable within specified context ?
   * @param object_p
   * @param context_p
   * @return <code>true</code> if the object can NOT be modified, <code>false</code> if it is modifiable, or unknown.
   */
  public static boolean isUnmodifiable(EObject object_p, final Context context_p) {
    // Precondition.
    if ((null == object_p) || (null == context_p)) {
      return false;
    }
    // Switch on the object type.
    Boolean locked = new ContextsSwitch<Boolean>() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseCategory(com.thalesgroup.orchestra.framework.model.contexts.Category)
       */
      @SuppressWarnings("boxing")
      @Override
      public Boolean caseCategory(Category category_p) {
        // Transient categories are not modifiable.
        return caseContext(ModelUtil.getContext(category_p)) || ModelUtil.isTransientElement(category_p);
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseContext(com.thalesgroup.orchestra.framework.model.contexts.Context)
       */
      @SuppressWarnings("boxing")
      @Override
      public Boolean caseContext(Context currentContext_p) {
        // Precondition.
        if (context_p == currentContext_p) {
          // If the object is contained by a resource that is in read-only mode, and it is asked for its containing context, then it is locked.
          AdapterFactoryEditingDomain editingDomain = ModelHandlerActivator.getDefault().getEditingDomain();
          if (editingDomain.isReadOnly(currentContext_p.eResource())) {
            return true;
          }
        }
        // Test against being in a reference baseline project.
        ContextsResourceImpl resource = context_p.eResource();
        // Precondition.
        if (null == resource) {
          return false;
        }
        RootContextsProject contextsProject = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(resource.getURI());
        if ((null != contextsProject) && contextsProject.isBaseline()) {
          return true;
        }
        return false;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseOverridingVariable(com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable)
       */
      @SuppressWarnings("boxing")
      @Override
      public Boolean caseOverridingVariable(OverridingVariable overridingVariable_p) {
        // This is not perhaps not enough (e.g. if the overriden variable is final).
        return isUnmodifiable(overridingVariable_p.eContainer(), context_p);
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariable(com.thalesgroup.orchestra.framework.model.contexts.Variable)
       */
      @SuppressWarnings("boxing")
      @Override
      public Boolean caseVariable(Variable variable_p) {
        Category category = ModelUtil.getCategory(variable_p);
        Context variableContext = ModelUtil.getContext(variable_p);
        boolean result = variable_p.isFinal() && (context_p != variableContext);
        if (null != category) {
          return result || caseContext(variableContext) || ModelUtil.isTransientElement(variable_p);
        }
        return result;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariableValue(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
       */
      @SuppressWarnings("boxing")
      @Override
      public Boolean caseVariableValue(VariableValue variableValue_p) {
        return isUnmodifiable(variableValue_p.eContainer(), context_p);
      }
    }.doSwitch(object_p);
    return (null != locked) ? locked.booleanValue() : false;
  }

  /**
   * Is given variable fully filled in given context ? To be fully filled, a variable must contain at least one variable value and all of its variable values
   * must be filled (non empty substituted value for "classical" variables, non empty values map for environment variables).
   * @param variable_p
   * @param context_p
   * @return
   */
  public static boolean isVariableFilled(AbstractVariable variable_p, Context context_p) {
    List<VariableValue> values = DataUtil.getValues(variable_p, context_p);
    if (values.isEmpty()) {
      // The variable contains no variable value.
      return false;
    }
    for (VariableValue variableValue : values) {
      // For an environment variable value, check if map of values is empty,
      // for a "classical" variable value, check if the value field is empty.
      if (ContextsPackage.Literals.ENVIRONMENT_VARIABLE_VALUE.isInstance(variableValue)) {
        if (((EnvironmentVariableValue) variableValue).getValues().isEmpty()) {
          return false;
        }
      } else {
        if (null == variableValue.getValue() || ICommonConstants.EMPTY_STRING.equals(variableValue.getValue().trim())) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Copy specified source context owned contents into specified target context.<br>
   * This is copying only elements contributed (directly owned) by source context.<br>
   * This includes :
   * <ul>
   * <li>Categories
   * <li>Overriding variables
   * <li>Selected versions
   * <li>Contributed categories and variables
   * </ul>
   * Elements in target context have their IDs set to <code>null</code> as a result, thus ensuring uniqueness at persistence time.
   * @param sourceContext_p
   * @param targetContext_p
   * @param shouldFlushIds_p <code>true</code> to flush IDs in the destination context, <code>false</code> to keep the same IDs as the source one.
   * @return <code>true</code> if copy was successfully conducted, <code>false</code> if parameters are invalid.
   */
  public static boolean makeCopyOfOwnedContents(Context sourceContext_p, Context targetContext_p, boolean shouldFlushIds_p) {
    // Precondition.
    if ((null == sourceContext_p) || (null == targetContext_p)) {
      return false;
    }
    // Copy of source context.
    Context copy = (Context) EcoreUtil.copy(sourceContext_p);
    // Retain only relevant data.
    // Categories.
    {
      List<Category> categories = copy.getCategories();
      if (shouldFlushIds_p) {
        categories = flushElementsIDs(categories);
      }
      targetContext_p.getCategories().addAll(categories);
    }
    // Overriding variables.
    {
      List<OverridingVariable> overridingVariables = copy.getOverridingVariables();
      if (shouldFlushIds_p) {
        overridingVariables = flushElementsIDs(overridingVariables);
      }
      // Update references before adding variables to target context.
      for (OverridingVariable overridingVariable : overridingVariables) {
        // Set overridden variable to correct context.
        String overriddenVariablePath = ModelUtil.getElementPath(overridingVariable.getOverriddenVariable());
        AbstractVariable targetVariable = DataUtil.getVariable(overriddenVariablePath, targetContext_p);
        // Skip this variable.
        if (null == targetVariable) {
          continue;
        }
        overridingVariable.setOverriddenVariable((Variable) targetVariable);
        // Set overridden values to correct context.
        for (VariableValue variableValue : overridingVariable.getValues()) {
          // Skip non-overriding variable value.
          if (!(variableValue instanceof OverridingVariableValue)) {
            continue;
          }
          OverridingVariableValue overridingVariableValue = (OverridingVariableValue) variableValue;
          String overridingVariableValuePath = ModelUtil.getElementPath(overridingVariableValue.getOverriddenValue());
          // Skip this value.
          if (null == overriddenVariablePath) {
            continue;
          }
          List<VariableValue> rawValues = DataUtil.getRawValues(targetVariable, targetContext_p);
          for (VariableValue targetValue : rawValues) {
            // Found a match.
            if (overridingVariableValuePath.equals(ModelUtil.getElementPath(targetValue))) {
              // Stop here.
              overridingVariableValue.setOverriddenValue(targetValue);
              break;
            }
          }
        }
      }
      // Add variables.
      targetContext_p.getOverridingVariables().addAll(overridingVariables);
    }
    // Selected versions.
    {
      List<InstallationCategory> selectedVersions = copy.getSelectedVersions();
      if (shouldFlushIds_p) {
        selectedVersions = flushElementsIDs(selectedVersions);
      }
      targetContext_p.getSelectedVersions().addAll(selectedVersions);
    }
    // Contributed categories.
    {
      List<Category> superCategoryCategories = copy.getSuperCategoryCategories();
      if (shouldFlushIds_p) {
        superCategoryCategories = flushElementsIDs(superCategoryCategories);
      }
      // Update references before adding to target context.
      for (Category category : superCategoryCategories) {
        String superCategoryPath = ModelUtil.getElementPath(category.getSuperCategory());
        Category superCategory = DataUtil.getCategory(superCategoryPath, targetContext_p);
        if (null != superCategory) {
          category.setSuperCategory(superCategory);
        }
      }
      targetContext_p.getSuperCategoryCategories().addAll(superCategoryCategories);
    }
    // Contributed variables.
    {
      List<Variable> superCategoryVariables = copy.getSuperCategoryVariables();
      if (shouldFlushIds_p) {
        superCategoryVariables = flushElementsIDs(superCategoryVariables);
      }
      // Update references before adding to target context.
      for (Variable variable : superCategoryVariables) {
        String superCategoryPath = ModelUtil.getElementPath(variable.getSuperCategory());
        Category superCategory = DataUtil.getCategory(superCategoryPath, targetContext_p);
        if (null != superCategory) {
          variable.setSuperCategory(superCategory);
        }
      }
      targetContext_p.getSuperCategoryVariables().addAll(superCategoryVariables);
    }
    return true;
  }

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
  public static EObject makeFlatCopy(EObject element_p, final Context context_p, final boolean flushIds_p) {
    EObject result = element_p;
    // Precondition.
    if ((null == element_p) || (null == context_p)) {
      return result;
    }
    // Switch
    result = new ContextsSwitch<ModelElement>() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseAbstractVariable(com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable)
       */
      @Override
      public ModelElement caseAbstractVariable(AbstractVariable object_p) {
        // Precondition.
        if (null == object_p) {
          return null;
        }
        AbstractVariable copy = (AbstractVariable) ContextsFactory.eINSTANCE.create(object_p.eClass());
        // Copy attributes.
        for (EAttribute attribute : copy.eClass().getEAllAttributes()) {
          copy.eSet(attribute, object_p.eGet(attribute));
        }
        // Copy values.
        List<VariableValue> rawValues = DataUtil.getRawValues(object_p, context_p);
        for (VariableValue variableValue : rawValues) {
          copy.getValues().add((VariableValue) makeFlatCopy(variableValue, context_p, flushIds_p));
        }
        // IDs flush.
        if (flushIds_p) {
          copy.setId(null);
        }
        return copy;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseCategory(com.thalesgroup.orchestra.framework.model.contexts.Category)
       */
      @Override
      public ModelElement caseCategory(Category object_p) {
        Category copy = (Category) ContextsFactory.eINSTANCE.create(object_p.eClass());
        copy.setName(object_p.getName());
        // Add children categories (recursively).
        for (Category childCategory : DataUtil.getCategories(object_p, context_p)) {
          copy.getCategories().add((Category) caseCategory(childCategory));
        }
        // Add variables.
        Collection<AbstractVariable> variables = DataUtil.getVariables(object_p, context_p);
        for (AbstractVariable abstractVariable : variables) {
          // Create copy instance.
          AbstractVariable avCopy = (AbstractVariable) caseAbstractVariable(abstractVariable);
          // Assuming this is to be a variable.
          copy.getVariables().add((Variable) avCopy);
        }
        // IDs flush.
        if (flushIds_p) {
          copy.setId(null);
        }
        return copy;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseEnvironmentVariableValue(com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue)
       */
      @Override
      public ModelElement caseEnvironmentVariableValue(EnvironmentVariableValue object_p) {
        // EnvironmentVariableValue is inheriting from VariableValue -> copy VariableValue fields.
        ModelElement environmentVariableValueCopy = caseVariableValue(object_p);
        // Check VariableValue copy result.
        if (null == environmentVariableValueCopy || !(environmentVariableValueCopy instanceof EnvironmentVariableValue)) {
          return null;
        }
        // Copy specific fields.
        EnvironmentVariableValue copy = (EnvironmentVariableValue) environmentVariableValueCopy;
        // Environment Id.
        copy.setEnvironmentId(object_p.getEnvironmentId());
        // Environment map values.
        for (Map.Entry<String, String> value : object_p.getValues()) {
          copy.getValues().put(value.getKey(), value.getValue());
        }
        return copy;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseOverridingVariable(com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable)
       */
      @Override
      public ModelElement caseOverridingVariable(OverridingVariable object_p) {
        return caseAbstractVariable(object_p.getOverriddenVariable());
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariableValue(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
       */
      @Override
      public ModelElement caseVariableValue(VariableValue object_p) {
        // Precondition.
        if (null == object_p) {
          return null;
        }
        // Create copy (VariableValue or EnvironmentVariableValue). It's a flat copy, a classical Variable is created (not an Overriding one).
        VariableValue copy = ModelUtil.createVariableValue((AbstractVariable) object_p.eContainer());
        // Copy value.
        copy.setValue(object_p.getValue());
        // IDs flush.
        if (!flushIds_p) {
          // Don't flush -> keep Id.
          copy.setId(object_p.getId());
        }
        return copy;
      }
    }.doSwitch(element_p);
    if (null == result) {
      result = element_p;
    }
    return result;
  }
}