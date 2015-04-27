/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.internal.migration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.mapping.ecore2xml.Ecore2XMLPackage;
import org.eclipse.emf.mapping.ecore2xml.Ecore2XMLRegistry;
import org.eclipse.emf.mapping.ecore2xml.impl.Ecore2XMLRegistryImpl;
import org.eclipse.emf.mapping.ecore2xml.util.Ecore2XMLExtendedMetaData;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironment;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingEnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.migration.IMigrationHandler;

/**
 * A migration handler dedicated to environments migration from Orchestra 5.0 (4.0.x) to 5.1 (4.1.x) contexts models.
 * @author t0076261
 */
public class EnvironmentMigrationHandler implements IMigrationHandler {
  /**
   * @see com.thalesgroup.orchestra.framework.model.migration.IMigrationHandler#activateMigrationSupport(org.eclipse.emf.ecore.resource.ResourceSet)
   */
  public void activateMigrationSupport(ResourceSet resourceSet_p) {
    // Register the new schema against the old name-space URI
    String oldNamespaceURI = "http://com.thalesgroup.orchestra/framework/4_0_18/contexts"; //$NON-NLS-1$
    EPackage.Registry ePackageRegistry = new EPackageRegistryImpl(EPackage.Registry.INSTANCE);
    ePackageRegistry.put(oldNamespaceURI, ContextsPackage.eINSTANCE);
    // Register the new schema against the customer.ecore file referenced by the mapping
    ePackageRegistry.put("platform:/plugin/com.thalesgroup.orchestra.framework.model/model/contexts.ecore", ContextsPackage.eINSTANCE); //$NON-NLS-1$
    // Set package registry.
    resourceSet_p.setPackageRegistry(ePackageRegistry);
    // register the mapping with the ecore2xml registry.
    Ecore2XMLRegistry ecore2xmlRegistry = new Ecore2XMLRegistryImpl(Ecore2XMLRegistry.INSTANCE);
    ecore2xmlRegistry.put(
        oldNamespaceURI,
        EcoreUtil.getObjectByType(
            resourceSet_p.getResource(
                URI.createURI("platform:/plugin/com.thalesgroup.orchestra.framework.model/migration/5.0.x/contexts_2_contexts.ecore2xml"), //$NON-NLS-1$
                true).getContents(), Ecore2XMLPackage.Literals.XML_MAP));
    // Specify an ecore2xml extended metadata as a load option.
    resourceSet_p.getLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, new Ecore2XMLExtendedMetaData(ePackageRegistry, ecore2xmlRegistry));
  }

  /**
   * Add FileSystem environment contribution to specified variable with specified values, contributed by specified context.
   * @param targetVariable_p
   * @param directories_p
   * @param context_p
   */
  protected void addFileSystemEnvironmentForVariable(Variable targetVariable_p, ListOfValuesWithNoDuplicate directories_p, Context context_p) {
    // Precondition.
    if ((null == targetVariable_p) || (null == directories_p) || directories_p.isEmpty() || (null == context_p)) {
      return;
    }
    // Create hosting structure.
    OverridingVariable variable = ContextsFactory.eINSTANCE.createOverridingVariable();
    // Set targeted variable.
    variable.setOverriddenVariable(targetVariable_p);
    // Attach it to context.
    context_p.getOverridingVariables().add(variable);
    // Create environment value.
    EnvironmentVariableValue environmentVariableValue = null;
    ListOfValuesWithNoDuplicate values = new ListOfValuesWithNoDuplicate();
    // Deal with existing parent values.
    OverridingVariable parentVariable = getClosestOverridingVariable(targetVariable_p, context_p);
    if (null != parentVariable) {
      // Get values.
      List<VariableValue> parentValues = parentVariable.getValues();
      // Add them, if any.
      if (!parentValues.isEmpty()) {
        // Get parent first FS environment available.
        EnvironmentVariableValue selectedValue = null;
        for (VariableValue parentValue : parentValues) {
          EnvironmentVariableValue parentEnvironmentVariableValue = (EnvironmentVariableValue) parentValue;
          if (FileSystemEnvironment.FILE_SYSTEM_ENVIRONMENT_ID.equals(parentEnvironmentVariableValue.getEnvironmentId())) {
            selectedValue = parentEnvironmentVariableValue;
            break;
          }
        }
        // Do override this value, if any.
        if (null != selectedValue) {
          values.addAll(FileSystemEnvironmentHandler.decodeValues(selectedValue.getValues().get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES)));
          // Create overriding variable value.
          OverridingEnvironmentVariableValue overridingEnvironmentVariableValue = ContextsFactory.eINSTANCE.createOverridingEnvironmentVariableValue();
          overridingEnvironmentVariableValue.setOverriddenValue(selectedValue);
          environmentVariableValue = overridingEnvironmentVariableValue;
        }
      }
    }
    // Make sure a dedicated FS environment value is created.
    if (null == environmentVariableValue) {
      environmentVariableValue = ContextsFactory.eINSTANCE.createEnvironmentVariableValue();
    }
    // Add already contributed values.
    values.addAll(directories_p.getValues());
    variable.getValues().add(environmentVariableValue);
    // Fill it.
    environmentVariableValue.setEnvironmentId(FileSystemEnvironment.FILE_SYSTEM_ENVIRONMENT_ID);
    environmentVariableValue.getValues().put(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES,
        FileSystemEnvironmentHandler.encodeValues(values.getValues()));
  }

  /**
   * Get and remove contributed values that are using a deprecated type for environments variables.
   * @param targetVariable_p
   * @param context_p
   * @return
   */
  protected List<String> getAndCleanContributedValues(AbstractVariable targetVariable_p, Context context_p) {
    // Precondition.
    if ((null == targetVariable_p) || (null == context_p)) {
      return Collections.emptyList();
    }
    // Resulting values.
    List<String> result = new ArrayList<String>(0);
    // Find overriding variable that applies to specified target one.
    OverridingVariable overridingVariable = null;
    for (OverridingVariable ov : context_p.getOverridingVariables()) {
      Variable overriddenVariable = ov.getOverriddenVariable();
      // Reference is lost, stop here.
      if (overriddenVariable.eIsProxy()) {
        continue;
      }
      // Found unique candidate, stop here.
      if (targetVariable_p == overriddenVariable) {
        overridingVariable = ov;
        break;
      }
    }
    // Nothing to do.
    if (null == overridingVariable) {
      return Collections.emptyList();
    }
    // Get values.
    for (VariableValue variableValue : overridingVariable.getValues()) {
      // Skip environment values.
      if (variableValue instanceof EnvironmentVariableValue) {
        continue;
      }
      result.add(variableValue.getValue());
    }
    // Remove it from context.
    context_p.getOverridingVariables().remove(overridingVariable);
    return result;
  }

  /**
   * Get and remove orphan values that used to override a deprecated type for environments variables.
   * @param targetVariable_p
   * @param context_p
   * @return
   */
  protected List<String> getAndCleanOrphanValues(AbstractVariable targetVariable_p, Context context_p) {
    // Precondition.
    if ((null == targetVariable_p) || (null == context_p)) {
      return Collections.emptyList();
    }
    // Resulting values.
    List<String> result = new ArrayList<String>(0);
    // Get orphan values.
    Map<OverridingVariable, List<VariableValue>> orphanVariableValues = ModelUtil.getOrphanOverridingVariablesValues(context_p);
    // Cycle through them.
    for (Map.Entry<OverridingVariable, List<VariableValue>> orphanOverridingVariableValues : orphanVariableValues.entrySet()) {
      OverridingVariable overridingVariable = orphanOverridingVariableValues.getKey();
      // Get overridden variable.
      Variable overriddenVariable = overridingVariable.getOverriddenVariable();
      // Reference is lost, stop here.
      if (overriddenVariable.eIsProxy()) {
        continue;
      }
      // Only specified variable is targeted.
      if (targetVariable_p != overriddenVariable) {
        continue;
      }
      // Cycle through values.
      for (VariableValue overridingVariableValue : orphanOverridingVariableValues.getValue()) {
        // Retain value.
        result.add(overridingVariableValue.getValue());
        // Remove from overriding variable.
        overridingVariable.getValues().remove(overridingVariableValue);
      }
    }
    return result;
  }

  /**
   * Get specified context hierarchy closest overriding variable for specified one.<br>
   * The result does not belong to specified context, but to one of its parent instead.<br>
   * Closest means the closest parent from specified one containing an overriding variable targeting specified one.
   * @param variable_p
   * @param context_p
   * @return <code>null</code> if parameters are invalid, or no overriding variable could be found in the hierarchy.
   */
  protected OverridingVariable getClosestOverridingVariable(AbstractVariable variable_p, Context context_p) {
    // Precondition.
    if ((null == variable_p) || (null == context_p)) {
      return null;
    }
    // Start at specified context level.
    Context parentContext = context_p.getSuperContext();
    // Resulting variable.
    OverridingVariable result = null;
    // Cycle through hierarchy.
    while ((null == result) && (null != parentContext)) {
      // Get overriding variable at current level.
      result = getOverridingVariable(variable_p, parentContext);
      // Go up to parent context.
      parentContext = parentContext.getSuperContext();
    }
    return result;
  }

  /**
   * Get overriding variable for specified variable in specified context.
   * @param variable_p
   * @param context_p
   * @return <code>null</code> if parameters are invalid, or none could be found.
   */
  protected OverridingVariable getOverridingVariable(AbstractVariable variable_p, Context context_p) {
    // Precondition.
    if ((null == variable_p) || (null == context_p)) {
      return null;
    }
    OverridingVariable result = null;
    // Cycle through overriding variables.
    for (OverridingVariable overridingVariable : context_p.getOverridingVariables()) {
      // Overridden variable is specified one.
      if (variable_p == overridingVariable.getOverriddenVariable()) {
        result = overridingVariable;
        break;
      }
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.migration.IMigrationHandler#isHandlingMigrationFor(com.thalesgroup.orchestra.framework.model.contexts.Context)
   */
  public boolean isHandlingMigrationFor(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return false;
    }
    // Get context URI.
    URI uri = context_p.eResource().getURI();
    // Try and load it as a String.
    String fileContent = FileHelper.readFile(uri);
    return (null != fileContent) && fileContent.contains("xmlns:contexts=\"http://com.thalesgroup.orchestra/framework/4_0_18/contexts\""); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.migration.IMigrationHandler#migrateContext(com.thalesgroup.orchestra.framework.model.contexts.Context)
   */
  public void migrateContext(Context context_p) {
    // ArtefactPath variable.
    AbstractVariable artefactPathVariable = DataUtil.getVariable(DataUtil.__ARTEFACTPATH_VARIABLE_NAME, context_p);
    // ConfigurationDirectories variable.
    AbstractVariable configurationDirectoriesVariable = DataUtil.getVariable(DataUtil.__CONFIGURATIONDIRECTORIES_VARIABLE_NAME, context_p);
    // Values to retain.
    Map<AbstractVariable, ListOfValuesWithNoDuplicate> variableToDirectories = new HashMap<AbstractVariable, ListOfValuesWithNoDuplicate>(0);
    variableToDirectories.put(artefactPathVariable, new ListOfValuesWithNoDuplicate());
    variableToDirectories.put(configurationDirectoriesVariable, new ListOfValuesWithNoDuplicate());
    // Analyze orphan variable values first.
    variableToDirectories.get(artefactPathVariable).addAll(getAndCleanOrphanValues(artefactPathVariable, context_p));
    variableToDirectories.get(configurationDirectoriesVariable).addAll(getAndCleanOrphanValues(configurationDirectoriesVariable, context_p));
    // Then collect and clean contributed types that are no longer supported.
    variableToDirectories.get(artefactPathVariable).addAll(getAndCleanContributedValues(artefactPathVariable, context_p));
    variableToDirectories.get(configurationDirectoriesVariable).addAll(getAndCleanContributedValues(configurationDirectoriesVariable, context_p));
    // Finally create expected environments.
    {
      addFileSystemEnvironmentForVariable((Variable) artefactPathVariable, variableToDirectories.get(artefactPathVariable), context_p);
      addFileSystemEnvironmentForVariable((Variable) configurationDirectoriesVariable, variableToDirectories.get(configurationDirectoriesVariable), context_p);
    }
  }

  /**
   * A structure backed by a {@link List} of {@link String}.<br>
   * It does not allow for duplicates in values.<br>
   * This is important to keep it as a list structure, as the original order is required.
   * @author t0076261
   */
  protected class ListOfValuesWithNoDuplicate {
    /**
     * The backing list.
     */
    private List<String> _values = new ArrayList<String>(0);

    /**
     * Add a new value to the list.
     * @param value_p
     */
    protected void add(String value_p) {
      // Precondition.
      if (null == value_p) {
        return;
      }
      if (!_values.contains(value_p)) {
        _values.add(value_p);
      }
    }

    /**
     * Add a list of values to the list.
     * @param values_p
     */
    protected void addAll(List<String> values_p) {
      // Precondition.
      if ((null == values_p) || values_p.isEmpty()) {
        return;
      }
      for (String value : values_p) {
        add(value);
      }
    }

    /**
     * Get backing list.
     * @return
     */
    protected List<String> getValues() {
      return _values;
    }

    /**
     * Is there any value in the list ?
     * @return
     */
    protected boolean isEmpty() {
      return _values.isEmpty();
    }
  }
}