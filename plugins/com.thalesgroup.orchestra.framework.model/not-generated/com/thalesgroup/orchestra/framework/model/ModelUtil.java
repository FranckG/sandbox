/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.OrchestraDoctorLocalConfigurationType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType;
import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;
import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ContributedElement;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.PendingElementsCategory;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch;
import com.thalesgroup.orchestra.framework.model.install.InstallHandler;
import com.thalesgroup.orchestra.framework.model.internal.ProcessExecutionHandler;

/**
 * @author t0076261
 */
public class ModelUtil {
  /**
   * Odm variable reference string prefix, as it is used in raw variable values.
   */
  private static String __referenceStringPrefix;
  /**
   * A separator used in values from Orchestra Doctor fetched contents.
   */
  private static final String ANOTHER_PATH_SEPARATOR = "/"; //$NON-NLS-1$
  /**
   * Installation category current node name.
   */
  private static final String INSTALLATION_CATEGORY_CURRENT_NAME = "Current"; //$NON-NLS-1$
  /**
   * Orchestra installation category name.
   */
  public static final String INSTALLATION_CATEGORY_NAME = "Orchestra installation"; //$NON-NLS-1$
  /**
   * Orchestra installation 'Products' category name.
   */
  public static final String INSTALLATION_CATEGORY_PRODUCTS_NAME = "Products"; //$NON-NLS-1$
  /**
   * Installation variable/category compatibilities name.
   */
  public static final String INSTALLATION_COMPATIBILITIES_NAME = "Compatibilities"; //$NON-NLS-1$
  /**
   * Connector configuration file full path variable name.
   */
  private static final String INSTALLATION_VAR_CONNECTOR_CONFIGURATION_NAME = "configConnector"; //$NON-NLS-1$
  /**
   * Connector icons folder full path variable name.
   */
  private static final String INSTALLATION_VAR_CONNECTOR_ICONS_NAME = "iconPath"; //$NON-NLS-1$
  /**
   * Installation variable 'ExeName' name.
   */
  public static final String INSTALLATION_VAR_EXE_NAME = "ExeName"; //$NON-NLS-1$
  /**
   * Installation variable 'ExeRelativePath' name.
   */
  public static final String INSTALLATION_VAR_EXE_RELATIVE_PATH_NAME = "ExeRelativePath"; //$NON-NLS-1$
  /**
   * Full executable path variable name.
   */
  private static final String INSTALLATION_VAR_FULL_EXECUTABLE_NAME = "Executable"; //$NON-NLS-1$
  /**
   * Installation variable FullVersion name.
   */
  public static final String INSTALLATION_VAR_FULL_VERSION_NAME = "BuildVersion"; //$NON-NLS-1$
  /**
   * Installation variable 'HomePath' name.
   */
  public static final String INSTALLATION_VAR_HOME_PATH = "InstallLocation"; //$NON-NLS-1$
  /**
   * Installation variable ProductVersion name.
   */
  public static final String INSTALLATION_VAR_PRODUCT_VERSION_NAME = "ProductVersion"; //$NON-NLS-1$
  private static final String SEPARATOR = ":"; //$NON-NLS-1$
  private static final String TYPE_ENV = "env"; //$NON-NLS-1$
  private static final String TYPE_VALUE = "value"; //$NON-NLS-1$
  /**
   * Variable reference display pattern.
   */
  private static final String VARIABLE_REFERENCE_MESSAGE_FORMAT_PATTERN = "$'{'{0}:{1}'}'"; //$NON-NLS-1$
  /**
   * Pattern instance (group(1) -> variable type (odm, env_var...), group(2) -> variable path).
   */
  public static final Pattern VARIABLE_REFERENCE_REGEX_PATTERN = Pattern.compile("\\$\\{([^:]+):([^\\}]+)\\}"); //$NON-NLS-1$
  /**
   * Variable reference type for environment variables.
   */
  public static final String VARIABLE_REFERENCE_TYPE_ENV_VAR = "env_var"; //$NON-NLS-1$
  /**
   * Variable reference type for ODM variables.
   */
  public static final String VARIABLE_REFERENCE_TYPE_ODM = "odm"; //$NON-NLS-1$

  /**
   * Add external contributions to specified context.
   * @param context_p A not <code>null</code> instance of default context for current application.
   */
  public static void addContributionsToContext(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return;
    }
    // Get contributions.
    IConfigurationElement[] configurationElements =
        ExtensionPointHelper.getConfigurationElements("com.thalesgroup.orchestra.framework.lib", "defaultContextContribution"); //$NON-NLS-1$ //$NON-NLS-2$
    // Temporary resource set.
    ResourceSet tempResourceSet = new ResourceSetImpl() {
      /**
       * @see org.eclipse.emf.ecore.resource.impl.ResourceSetImpl#demandLoad(org.eclipse.emf.ecore.resource.Resource)
       */
      @Override
      protected void demandLoad(Resource resource_p) throws IOException {
        // Disable contributions within this resource set.
        if (resource_p instanceof ContextsResourceImpl) {
          ((ContextsResourceImpl) resource_p).disableContributions();
        }
        super.demandLoad(resource_p);
      }
    };
    // Cycle through them.
    for (IConfigurationElement configurationElement : configurationElements) {
      String relativePath = configurationElement.getAttribute("relativePath"); //$NON-NLS-1$
      // Not a valid contribution.
      if ((null == relativePath) || relativePath.trim().isEmpty()) {
        continue;
      }
      String id = configurationElement.getContributor().getName();
      IPath path = new Path(id).append(relativePath);
      URI fileFullUri = FileHelper.getFileFullUri(path.toString());
      Resource resource = tempResourceSet.getResource(fileFullUri, true);
      Context contributingContext = null;
      try {
        contributingContext = ModelUtil.getContext(resource);
      } catch (Exception exception_p) {
        // Do nothing, wait for null condition.
      }
      // Not a valid contribution.
      if (null == contributingContext) {
        continue;
      }
      doAddContributionsToContext(contributingContext, context_p);
    }
  }

  /**
   * Add current versions transient categories for specified context.<br>
   * This is reflecting the various
   * @param context_p
   */
  public static void addCurrentVersionsCategories(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return;
    }
    // Get selected versions.
    List<InstallationCategory> selectedVersions = context_p.getSelectedVersions();
    // Cycle through them.
    for (InstallationCategory selectedVersion : selectedVersions) {
      createOrUpdateCurrentVersionFor(selectedVersion);
    }
  }

  /**
   * Add installation data to specified context.<br>
   * Those data are fetched by OrchestraDoctor component, and added to specified context.<br>
   * Note that compatibilities nodes are added to allow for semantic validation.
   * @param context_p
   */
  public static void addInstallationStructureToSpecifiedContext(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return;
    }
    // Precondition
    for (Category category : context_p.getTransientCategories()) {
      if (INSTALLATION_CATEGORY_NAME.equals(category.getName())) {
        return;
      }
    }
    // Launch Orchestra Doctor.
    String outputFilePath = getInstallationConfigurationFilePath();
    try {
      // Content not already fetched.
      if (!new File(outputFilePath).exists()) {
        IStatus orchestraDoctorPathStatus = getOrchestraDoctorExecutablePath();
        // Unable to get OrchestraDoctor path.
        if (!orchestraDoctorPathStatus.isOK()) {
          ModelActivator.getInstance().getLog().log(orchestraDoctorPathStatus);
          return;
        }
        String result = ProcessExecutionHandler.exec(new String[] { orchestraDoctorPathStatus.getMessage(), "lc", "-o", outputFilePath, "-nk" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        CommonActivator.getInstance().logMessage(result, IStatus.INFO, null);
      }
    } catch (Exception exception_p) {
      StringBuilder loggerMessage = new StringBuilder("ModelUtil.addInstallationVariablesToSpecifiedContext(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
      return;
    }
    // Read resulting file.
    InstallHandler handler = new InstallHandler();
    OrchestraDoctorLocalConfigurationType model = handler.loadModel(outputFilePath);
    if (null == model) {
      CommonActivator.getInstance().logMessage(
          MessageFormat.format(Messages.ModelUtil_FetchOrchestraInstallation_Error_UnableToReadODResultFile, outputFilePath), IStatus.ERROR, null);
      return;
    }
    // Create main installation category.
    InstallationCategory installationCategory = ContextsFactory.eINSTANCE.createInstallationCategory();
    installationCategory.setName(INSTALLATION_CATEGORY_NAME);
    context_p.getTransientCategories().add(installationCategory);
    // Cycle through loaded elements.
    for (ProductType product : model.getProduct()) {
      // Get container.
      Category productKindCategory = findOrCreateInstallationCategory(installationCategory, product.getKind());
      // Get product category.
      Category productCategory = findOrCreateInstallationCategory(productKindCategory, product.getName());
      // Get compatibility category.
      Category compatibilityCategory = findOrCreateInstallationCategory(productCategory, INSTALLATION_COMPATIBILITIES_NAME);
      // Cycle through versions.
      for (VersionType version : product.getVersion()) {
        boolean isReferenceable = false;
        // Get version category.
        InstallationCategory versionCategory = null;
        if (INSTALLATION_CATEGORY_PRODUCTS_NAME.equals(productKindCategory.getName())) {
          // For an orchestra product (falling in the Products category), ignore version, as there should be only one version available.
          versionCategory = (InstallationCategory) productCategory;
        } else {
          versionCategory = (InstallationCategory) findOrCreateInstallationCategory(productCategory, version.getValue());
        }
        // Set values.
        { // Paths.
          String installationPath = resolveInstallationValue(INSTALLATION_VAR_HOME_PATH, version.getInstallLocation());
          if (null != installationPath) {
            createInstallationVariable(versionCategory, INSTALLATION_VAR_HOME_PATH, installationPath);
            // Then try and create executable full path.
            String executableName = resolveInstallationValue(INSTALLATION_VAR_EXE_NAME, version.getExecName());
            String exeRelativePath = resolveInstallationValue(INSTALLATION_VAR_EXE_RELATIVE_PATH_NAME, version.getExecRelativePath());
            if ((null != executableName) && (null != exeRelativePath)) {
              createInstallationVariable(versionCategory, INSTALLATION_VAR_FULL_EXECUTABLE_NAME, installationPath + exeRelativePath + executableName);
            }
            // Orchestra products can not be referenced so far.
            isReferenceable = (versionCategory != productCategory);
            // This is an Orchestra product.
            // Add its full version as a new variable, so as to retain it.
            if (versionCategory == productCategory) {
              // Add it as a FullVersion variable to the product category.
              createInstallationVariable(versionCategory, INSTALLATION_VAR_FULL_VERSION_NAME, version.getValue());
            }
          }
        }
        { // Connector attributes.
          createInstallationVariable(versionCategory, INSTALLATION_VAR_CONNECTOR_CONFIGURATION_NAME, version.getConfigConnector());
          createInstallationVariable(versionCategory, INSTALLATION_VAR_CONNECTOR_ICONS_NAME, version.getIconPath());
        }
        { // Parameters.
          for (ParameterType parameter : version.getParameter()) {
            createInstallationVariable(versionCategory, parameter.getName(), parameter.getValue());
          }
        }
        { // Compatibility data.
          if (!version.getCompatibility().isEmpty()) {
            // Create a root variable with name set to product compatibility declaration version.
            createInstallationVariable(compatibilityCategory, version.getValue(), ICommonConstants.EMPTY_STRING);
            // Cycle through compatibilities.
            for (CompatibilityType compatibility : version.getCompatibility()) {
              Category actualCompatibility = findOrCreateInstallationCategory(compatibilityCategory, compatibility.getTargetComponent());
              { // Compatibilities.
                createCompatibilityVariable(actualCompatibility, compatibility.getVersionRange());
              }
            }
          }
        }
        // Set ability to reference this version.
        versionCategory.setReferenceable(isReferenceable);
        if (!isReferenceable) {
          // Do not keep this version, as it is unlikely to be used.
          productCategory.getCategories().remove(versionCategory);
        }
      }
      // Remove compatibilities node, if it is empty.
      if (compatibilityCategory.getCategories().isEmpty()) {
        productCategory.getCategories().remove(compatibilityCategory);
      }
    }
  }

  /**
   * @param context_p
   * @return true if the category has been created, false otherwise
   */
  public static boolean addPendingElementsCategoryToSpecifiedContext(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return false;
    }

    final List<Category> orphanSuperCategoryCategories = getOrphanSuperCategoryCategories(context_p);
    final List<Variable> orphanSuperCategoryVariables = getOrphanSuperCategoryVariables(context_p);
    final Map<OverridingVariable, List<VariableValue>> orphanOverridingVariablesValues = getOrphanOverridingVariablesValues(context_p);
    final List<OverridingVariable> overridingVariablesFromFinalVariable = getOverridingVariablesFromFinalVariables(context_p);
    if (!orphanSuperCategoryCategories.isEmpty() || !orphanSuperCategoryVariables.isEmpty() || !orphanOverridingVariablesValues.isEmpty()
        || !overridingVariablesFromFinalVariable.isEmpty()) {
      // Create the transient "Pending Elements" category.
      PendingElementsCategory transientCategory = ContextsFactory.eINSTANCE.createPendingElementsCategory();
      transientCategory.setName(Messages.CATEGORY_NAME_PENDINGELEMENTS);
      context_p.getTransientCategories().add(transientCategory);

      // Add orphan superCategoryCategories (categories attached to an unloaded category from a super context).
      for (Category category : orphanSuperCategoryCategories) {
        category.setSuperCategory(null);
        transientCategory.getCategories().add(category);
      }

      // Add orphan superCategoryVariables (variables attached to an unloaded category from a super context).
      for (Variable variable : orphanSuperCategoryVariables) {
        variable.setSuperCategory(null);
        transientCategory.getVariables().add(variable);
      }

      // Add orphan overriding variables values (values attached to an unloaded overridden variable or to an unloaded overridden variable value).
      // 1. An overridden variable has been deleted,
      // 2. An overridden variable value has been deleted (e.g. : a value from a multiple valued variable is overridden in a child context and deleted in the
      // parent context), but the variable is still existing.
      for (Map.Entry<OverridingVariable, List<VariableValue>> orphanOverridingVariableValues : orphanOverridingVariablesValues.entrySet()) {
        // Create the pending variable.
        Variable pendingVariable = null;
        Variable overriddenVariable = orphanOverridingVariableValues.getKey().getOverriddenVariable();
        boolean isOverriddenVariableExisting = (null != overriddenVariable && !overriddenVariable.eIsProxy());

        // If overridden variable is still existing, use its type.
        if (isOverriddenVariableExisting) {
          if (ContextsPackage.Literals.ENVIRONMENT_VARIABLE.isInstance(overriddenVariable)) {
            pendingVariable = ContextsFactory.eINSTANCE.createEnvironmentVariable();
          } else if (ContextsPackage.Literals.FILE_VARIABLE.isInstance(overriddenVariable)) {
            pendingVariable = ContextsFactory.eINSTANCE.createFileVariable();
          } else if (ContextsPackage.Literals.FOLDER_VARIABLE.isInstance(overriddenVariable)) {
            pendingVariable = ContextsFactory.eINSTANCE.createFolderVariable();
          /* avoid NPE: */
            } else {
        	  pendingVariable = ContextsFactory.eINSTANCE.createVariable();
          }
        } else {
          pendingVariable = ContextsFactory.eINSTANCE.createVariable();
        }

        // If overridden variable is still existing, use its name.
        pendingVariable.setName(isOverriddenVariableExisting ? overriddenVariable.getName() : Messages.OVERRIDING_VARIABLE_VALUE_NAME);

        // Do a copy of orphan variable values to create pending variable values.
        for (VariableValue overridingVariableValue : orphanOverridingVariableValues.getValue()) {
          VariableValue variableValue = null;
          if (ContextsPackage.Literals.ENVIRONMENT_VARIABLE_VALUE.isInstance(overridingVariableValue)) {
            EnvironmentVariableValue ovEnvVariableValue = (EnvironmentVariableValue) overridingVariableValue;
            EnvironmentVariableValue pendingEnvVariableValue = ContextsFactory.eINSTANCE.createEnvironmentVariableValue();
            pendingEnvVariableValue.setEnvironmentId(ovEnvVariableValue.getEnvironmentId());
            pendingEnvVariableValue.getValues().addAll(ovEnvVariableValue.getValues());
            variableValue = pendingEnvVariableValue;
          } else {
            variableValue = ContextsFactory.eINSTANCE.createVariableValue();
            variableValue.setValue(overridingVariableValue.getValue());
          }
          pendingVariable.getValues().add(variableValue);
          // Remove the orphan value from the list of the overriding variable.
          orphanOverridingVariableValues.getKey().getValues().remove(overridingVariableValue);
        }

        // If overridden variable is still existing, use its multi valued state of parent.
        pendingVariable.setMultiValued(isOverriddenVariableExisting && overriddenVariable.isMultiValued() || pendingVariable.getValues().size() > 1);

        transientCategory.getVariables().add(pendingVariable);
        // If no more values in the overriding variable, get rid of it.
        if (orphanOverridingVariableValues.getKey().getValues().isEmpty()) {
          context_p.getOverridingVariables().remove(orphanOverridingVariableValues.getKey());
        }
      }

      // Add variable overriding a variable that is now final.
      for (OverridingVariable orphanOverriddingVariableFromFinalVariable : overridingVariablesFromFinalVariable) {
        Variable variable = ContextsFactory.eINSTANCE.createVariable();
        variable.setName(orphanOverriddingVariableFromFinalVariable.getOverriddenVariable().getName());
        variable.getValues().addAll(orphanOverriddingVariableFromFinalVariable.getValues());
        variable.setMultiValued(orphanOverriddingVariableFromFinalVariable.getOverriddenVariable().isMultiValued());
        transientCategory.getVariables().add(variable);
        context_p.getOverridingVariables().remove(orphanOverriddingVariableFromFinalVariable);
      }

      return true;
    }
    return false;
  }

  /**
   * Attach specified contributed element to pointed category directly.<br>
   * This is only applicable to {@link Variable} and {@link Category} so far, for default context creation, including contributions.
   * @param element_p
   * @param contributedContext_p
   */
  private static void attachElement(ContributedElement element_p, Context contributedContext_p) {
    // Make sure it does belong to contributed context.
    String fragment = getUriFragment(element_p.getSuperCategory(), contributedContext_p);
    if (null == fragment) {
      return;
    }
    Category realDestination = (Category) contributedContext_p.eResource().getEObject(fragment);
    if (null == realDestination) {
      return;
    }
    // Reset super category reference.
    element_p.setSuperCategory(null);
    // Add element to pointed category.
    EClass eClass = element_p.eClass();
    if (ContextsPackage.Literals.VARIABLE.isInstance(element_p)) {
      realDestination.getVariables().add((Variable) element_p);
    } else if (ContextsPackage.Literals.CATEGORY.equals(eClass)) {
      realDestination.getCategories().add((Category) element_p);
    }
  }

  /**
   * Convert specified {@link EnvironmentVariableValue} values to Java {@link Map} without impacting the underlying model element.
   * @param environmentVariableValue_p
   * @return
   */
  public static Map<String, String> convertEnvironmentVariableValues(EnvironmentVariableValue environmentVariableValue_p) {
    // Precondition.
    if (null == environmentVariableValue_p) {
      return Collections.emptyMap();
    }
    // Return a copy of the EnvironmentVariableValue internal map.
    return new HashMap<String, String>(environmentVariableValue_p.getValues().map());
  }

  /**
   * Convert/check specified Orchestra Doctor executable path.
   * @param doctorExecutablePath_p
   * @return <code>null</code> if path is invalid, the absolute path otherwise.
   */
  private static String convertOrchestraDoctorPath(String doctorExecutablePath_p) {
    // Preconditions.
    if ((null == doctorExecutablePath_p) || doctorExecutablePath_p.trim().isEmpty()) {
      return null;
    }
    // Remove useless characters.
    String doctorExecutablePath = doctorExecutablePath_p.replaceAll(System.getProperty("line.separator"), ICommonConstants.EMPTY_STRING); //$NON-NLS-1$
    // Check if file does exist.
    if (!new File(doctorExecutablePath).isFile()) {
      return null;
    }
    // Return path.
    return doctorExecutablePath;
  }

  /**
   * Convert value so that it is usable in resulting context.<br>
   * In the COTS case, this tries to ensure that HomePath\ExeRelativePath\ExeName leads to a correct path.<br>
   * Although there is admittedly no way to do so, a validation rule should also points non-usable COTS variables.
   * @param variableName_p
   * @param value_p
   * @param substituteEnvironmentVariables_p
   * @return
   */
  public static String convertValue(String variableName_p, String value_p, boolean substituteEnvironmentVariables_p) {
    // Unable to convert value.
    if ((null == variableName_p) || (null == value_p)) {
      return value_p;
    }
    // For specific variables, make sure a conversion is done.
    String convertedValue = value_p;
    String pointString = ICommonConstants.EMPTY_STRING + ICommonConstants.POINT_CHARACTER;
    if (INSTALLATION_VAR_EXE_NAME.equals(variableName_p)) {
      // Remove all preceding '\' , '/' and '.' characters.
      while (convertedValue.startsWith(pointString) || convertedValue.startsWith(ICommonConstants.PATH_SEPARATOR)
             || convertedValue.startsWith(ANOTHER_PATH_SEPARATOR)) {
        if (convertedValue.length() > 1) {
          convertedValue = convertedValue.substring(1, convertedValue.length());
        } else {
          convertedValue = ICommonConstants.EMPTY_STRING;
        }
      }
    } else if (INSTALLATION_VAR_EXE_RELATIVE_PATH_NAME.equals(variableName_p)) {
      // Remove all preceding '.' characters.
      while (convertedValue.startsWith(pointString)) {
        if (convertedValue.length() > 1) {
          convertedValue = convertedValue.substring(1, convertedValue.length());
        } else {
          convertedValue = ICommonConstants.EMPTY_STRING;
        }
      }
      // Replace all '/' with '\' characters.
      convertedValue = convertedValue.replace(ANOTHER_PATH_SEPARATOR, ICommonConstants.PATH_SEPARATOR);
      // Also make sure this contains a starting '\' and an ending one.
      if (!convertedValue.startsWith(ICommonConstants.PATH_SEPARATOR)) {
        convertedValue = ICommonConstants.PATH_SEPARATOR + convertedValue;
      }
      if (!convertedValue.endsWith(ICommonConstants.PATH_SEPARATOR)) {
        convertedValue += ICommonConstants.PATH_SEPARATOR;
      }
      // If conversion leads to multiple '\' characters only, reduce to just one '\' character.
      if (0 == convertedValue.replace(ICommonConstants.PATH_SEPARATOR, ICommonConstants.EMPTY_STRING).length()) {
        convertedValue = ICommonConstants.PATH_SEPARATOR;
      }
    } else if (INSTALLATION_VAR_HOME_PATH.equals(variableName_p)) {
      // Remove ending '\' or '/' if any.
      while (convertedValue.endsWith(ICommonConstants.PATH_SEPARATOR) || convertedValue.endsWith(ANOTHER_PATH_SEPARATOR)) {
        int convertedLength = convertedValue.length();
        if (convertedValue.length() > 1) {
          convertedLength -= 1;
          convertedValue = convertedValue.substring(0, convertedLength);
        } else {
          convertedValue = ICommonConstants.EMPTY_STRING;
        }
      }
    }
    // Add at least non-converted value.
    if (null == convertedValue) {
      convertedValue = value_p;
    }
    // Substitute environment variables, if needed.
    if (substituteEnvironmentVariables_p) {
      StringBuilder result = new StringBuilder();
      Pattern pattern = Pattern.compile("%[\\w]+%"); //$NON-NLS-1$
      Matcher matcher = pattern.matcher(convertedValue);
      while ((null != matcher) && matcher.find()) {
        int start = matcher.start();
        int end = matcher.end();
        // Add untouched start.
        result.append(convertedValue.substring(0, start));
        // Get matched environment variable name.
        String envVarName = matcher.group().replace(ICommonConstants.EMPTY_STRING + ICommonConstants.PERCENT_CHARACTER, ICommonConstants.EMPTY_STRING);
        // Add the proper eclipse syntax.
        result.append(MessageFormat.format(ModelUtil.VARIABLE_REFERENCE_MESSAGE_FORMAT_PATTERN, VARIABLE_REFERENCE_TYPE_ENV_VAR, envVarName));
        convertedValue = convertedValue.substring(end);
        matcher = pattern.matcher(convertedValue);
      }
      result.append(convertedValue);
      convertedValue = result.toString();
    }
    return convertedValue;
  }

  /**
   * Create compatibility variable for specified parameters.
   * @param category_p
   * @param ranges_p
   * @return
   */
  protected static Variable createCompatibilityVariable(Category category_p, List<VersionRangeType> ranges_p) {
    // Preconditions.
    if ((null == category_p) || (null == ranges_p) || ranges_p.isEmpty()) {
      return null;
    }
    // Create variable.
    Variable result = createInstallationVariable(category_p, INSTALLATION_COMPATIBILITIES_NAME, ICommonConstants.EMPTY_STRING);
    // This is indeed a multi-valued variable.
    result.setMultiValued(true);
    // Remove silly value.
    result.getValues().remove(0);
    // Add values.
    for (VersionRangeType versionRangeType : ranges_p) {
      VariableValue value = ContextsFactory.eINSTANCE.createVariableValue();
      value.setValue(versionRangeType.getRange());
      result.getValues().add(value);
    }
    return result;
  }

  /**
   * Create installation variable for specified parameters.
   * @param category_p
   * @param variableName_p
   * @param value_p
   * @return
   */
  protected static Variable createInstallationVariable(Category category_p, String variableName_p, String value_p) {
    // Precondition.
    if ((null == category_p) || (null == variableName_p) || (null == value_p)) {
      return null;
    }
    // Make sure it does not exist yet.
    for (AbstractVariable variable : category_p.getVariables()) {
      if (variableName_p.equals(variable.getName())) {
        return null;
      }
    }
    // Create variable.
    Variable result = null;
    if (INSTALLATION_VAR_HOME_PATH.equals(variableName_p)) {
      result = ContextsFactory.eINSTANCE.createFolderVariable();
    } else if (INSTALLATION_VAR_FULL_EXECUTABLE_NAME.equals(variableName_p)) {
      result = ContextsFactory.eINSTANCE.createFileVariable();
    } else {
      result = ContextsFactory.eINSTANCE.createVariable();
    }
    // Search for category to add variable to.
    Category parent = category_p;
    String variableName = variableName_p;
    IPath path = new Path(variableName_p);
    // In this case, there might be categories to create before adding the variable.
    if (path.segmentCount() > 1) {
      variableName = path.lastSegment();
      path = path.removeLastSegments(1);
      for (String segment : path.segments()) {
        parent = findOrCreateInstallationCategory(parent, segment);
      }
    }
    // Set variable values.
    result.setName(variableName);
    result.setFinal(true);
    result.setMultiValued(false);
    // Add variable value.
    VariableValue value = ContextsFactory.eINSTANCE.createVariableValue();
    value.setValue(value_p);
    result.getValues().add(value);
    // Add variable to category.
    parent.getVariables().add(result);
    return result;
  }

  /**
   * Create or update current versions structure for specified selected version of a product.
   * @param selectedVersion_p
   * @return <code>null</code> if current version could not be created. An instance of {@link InstallationCategory} that is a copy of version referenced by
   *         specified version otherwise and contributed to the targeted product category. This instance is transient, contained by specified version context
   *         directly.<br>
   *         There can be only one such installation category per component in the whole context hierarchy.
   */
  public static InstallationCategory createOrUpdateCurrentVersionFor(InstallationCategory selectedVersion_p) {
    // Precondition.
    if (null == selectedVersion_p) {
      return null;
    }
    // Get containing context.
    Context context = getContext(selectedVersion_p);
    // Get shared IDataUtil instance.
    IDataUtil dataUtil = ModelActivator.getInstance().getDataUtil();
    // Get selected version path.
    String versionPath = selectedVersion_p.getReferencePath();
    // Get associated targeted installation category.
    Category versionCategory = dataUtil.getCategory(versionPath, context);
    // Invalid argument, points to a not existing installation category.
    if (!(versionCategory instanceof InstallationCategory)) {
      return null;
    }
    // Try and get associated installation category.
    InstallationCategory currentVersion = getCurrentVersionForPath(versionPath, context, false);
    // There is already a current version, remove it.
    if (null != currentVersion) {
      // Get containing context...
      Context containingContext = getContext(currentVersion);
      // ...and remove current version.
      containingContext.getCurrentVersions().remove(currentVersion);
    }
    // Now create a new current version based on specified context.
    currentVersion = (InstallationCategory) dataUtil.makeFlatCopy(versionCategory, context, true);
    // Remind targeted parent category.
    currentVersion.setReferencePath(versionPath);
    // Use specific name.
    currentVersion.setName(INSTALLATION_CATEGORY_CURRENT_NAME);
    // Point to selected category.
    currentVersion.setSuperCategory(getCategory(versionCategory.eContainer()));
    // Resolved reference is set to version category, and is used along with the cross-referencer to deal with references faster.
    currentVersion.setResolvedReference(versionCategory);
    // Add it to current context.
    context.getCurrentVersions().add(currentVersion);
    return currentVersion;
  }

  /**
   * Create or update selected versions structure in specified context for specified selected version of a product.
   * @param versionToSelect_p
   * @param context_p
   * @return <code>null</code> if the selected version could not be created. An instance of {@link InstallationCategory} that references specified version
   *         otherwise. This instance is persisted directly as a child of specified context.
   */
  public static InstallationCategory createOrUpdateSelectedVersionFor(InstallationCategory versionToSelect_p, Context context_p) {
    // Precondition.
    if ((null == versionToSelect_p) || (null == context_p)) {
      return null;
    }
    // Version to select is to be a version category for a specific product.
    if (!isSelectableVersionCategory(versionToSelect_p)) {
      // Wrong argument.
      // This is not a version that is referenceable.
      return null;
    }
    // First, go up the product itself.
    EObject eContainer = versionToSelect_p.eContainer();
    // Get component path.
    String componentPath = getElementPath(eContainer);
    // Get selected version.
    InstallationCategory selectedVersion = getSelectedVersionFor(componentPath, context_p.getSelectedVersions());
    // Create a new selected version.
    if (null == selectedVersion) {
      selectedVersion = ContextsFactory.eINSTANCE.createInstallationCategory();
      context_p.getSelectedVersions().add(selectedVersion);
    }
    // Reference new version.
    selectedVersion.setReferencePath(getElementPath(versionToSelect_p));
    return selectedVersion;
  }

  /**
   * Create an overriding variable value for specified overriding variable.<br>
   * Note that this creation points to the targeted type and then returns a new {@link OverridingVariableValue} compatible with this type.
   * @param overridingVariable_p The overriding variable asking for a new overriding variable value. Should not be <code>null</code>.
   * @return <code>null</code> if targeted type could not be resolved, a new instance of {@link OverridingVariableValue} otherwise.
   */
  public static OverridingVariableValue createOverridingVariableValue(OverridingVariable overridingVariable_p) {
    // Precondition.
    if (null == overridingVariable_p) {
      return null;
    }
    // Get to targeted variable.
    AbstractVariable targetedVariable = overridingVariable_p.getOverriddenVariable();
    OverridingVariableValue result = null;
    if (targetedVariable instanceof EnvironmentVariable) {
      result = ContextsFactory.eINSTANCE.createOverridingEnvironmentVariableValue();
    } else {
      result = ContextsFactory.eINSTANCE.createOverridingVariableValue();
    }
    // Add result to specified overriding variable.
    overridingVariable_p.getValues().add(result);
    return result;
  }

  /**
   * Create an overriding variable value for specified overriding variable.<br>
   * Note that this creation points to the targeted type and then returns a new {@link OverridingVariableValue} compatible with this type.
   * @param overridingVariable_p The overriding variable asking for a new overriding variable value. Should not be <code>null</code>.
   * @param overriddenValue_p The overridden value. Should not be <code>null</code>.
   * @param parentValue_p The closest parent value, if needed to initialize current value. Can be <code>null</code> if that does not make sense.
   * @return <code>null</code> if targeted type could not be resolved, a new instance of {@link OverridingVariableValue} otherwise.
   */
  public static OverridingVariableValue createOverridingVariableValue(OverridingVariable overridingVariable_p, VariableValue overriddenValue_p,
      VariableValue parentValue_p) {
    // Precondition.
    if (null == overriddenValue_p) {
      return null;
    }
    OverridingVariableValue result = createOverridingVariableValue(overridingVariable_p);
    // Precondition.
    if (null == result) {
      return null;
    }
    // Set targeted value.
    VariableValue targetValue = overriddenValue_p;
    if (overriddenValue_p instanceof OverridingVariableValue) {
      targetValue = ((OverridingVariableValue) overriddenValue_p).getOverriddenValue();
    }
    result.setOverriddenValue(targetValue);
    // No initialization to perform, stop here.
    if (null == parentValue_p) {
      return result;
    }
    // Initialize result.
    initializeValue(result, parentValue_p);
    return result;
  }

  /**
   * Create a variable value for specified variable.<br>
   * Note that this creation does never return an {@link OverridingVariableValue} but instead points to the targeted type (in case of an
   * {@link OverridingVariable}) and then returns a new {@link VariableValue} compatible with this type.
   * @param variable_p
   * @return <code>null</code> if targeted type could not be resolved, a new instance of {@link VariableValue} otherwise.
   */
  public static VariableValue createVariableValue(AbstractVariable variable_p) {
    // Precondition.
    if (null == variable_p) {
      return null;
    }
    // Get to targeted variable.
    AbstractVariable targetedVariable = variable_p;
    if (variable_p instanceof OverridingVariable) {
      targetedVariable = ((OverridingVariable) variable_p).getOverriddenVariable();
    }
    if (targetedVariable instanceof EnvironmentVariable) {
      return ContextsFactory.eINSTANCE.createEnvironmentVariableValue();
    }
    return ContextsFactory.eINSTANCE.createVariableValue();
  }

  /**
   * Do add contributions from contributing to contributed context.<br>
   * So far, only categories and variables are valid contributions.<br>
   * Overriding variables are not kept in the process.
   * @param contributingContext_p
   * @param contributedContext_p
   */
  private static void doAddContributionsToContext(Context contributingContext_p, Context contributedContext_p) {
    // Move root categories.
    contributedContext_p.getCategories().addAll(contributingContext_p.getCategories());
    // Then attach contributed categories to their destination category.
    List<Category> clonedCategories = new ArrayList<Category>(contributingContext_p.getSuperCategoryCategories());
    for (Category category : clonedCategories) {
      attachElement(category, contributedContext_p);
    }
    // Finally attach contributed variables to their destination category.
    List<Variable> clonedVariables = new ArrayList<Variable>(contributingContext_p.getSuperCategoryVariables());
    for (Variable variable : clonedVariables) {
      attachElement(variable, contributedContext_p);
    }
  }

  /**
   * Extract the variable references from the given string.
   * @param value_p
   * @return an empty list if the given value is <code>null</code>, empty or if it contains no variable reference.
   */
  public static List<VariableReferenceType> extractVariableReferences(String value_p) {
    if (null == value_p || value_p.isEmpty()) {
      return Collections.emptyList();
    }
    Matcher variableReferenceMatcher = VARIABLE_REFERENCE_REGEX_PATTERN.matcher(value_p);
    List<VariableReferenceType> extractedReferences = new ArrayList<VariableReferenceType>();
    while (variableReferenceMatcher.find()) {
      extractedReferences
          .add(new VariableReferenceType(variableReferenceMatcher.group(), variableReferenceMatcher.group(1), variableReferenceMatcher.group(2)));
    }
    return extractedReferences;
  }

  /**
   * Fill specified {@link EnvironmentVariableValue} with specified attributes and environment ID.
   * @param value_p
   * @param attributes_p
   * @param environmentId_p
   * @param version_p The environment version.
   */
  public static void fillEnvironmentVariableValue(EnvironmentVariableValue value_p, Map<String, String> attributes_p, String environmentId_p, String version_p) {
    // Preconditions.
    if ((null == value_p) || (null == attributes_p)) {
      return;
    }
    // value_p.getValues().addAll(attributes.entrySet()); does raise an ArrayStoreException
    // replace with cycling through entry set.
    EMap<String, String> values = value_p.getValues();
    values.clear();
    // Set values first.
    for (Map.Entry<String, String> entry : attributes_p.entrySet()) {
      values.put(entry.getKey(), entry.getValue());
    }
    // Then set version.
    if (null != version_p) {
      values.put(ICommonConstants.ENVIRONMENT_KEY_VERSION, version_p);
    }
    // Bind type to the model.
    value_p.setEnvironmentId(environmentId_p);
  }

  /**
   * @param category_p
   * @param categoryName_p
   * @return
   */
  private static Category findCategoryByName(Category category_p, String categoryName_p) {
    for (Category category : category_p.getCategories()) {
      if (categoryName_p.equals(category.getName())) {
        return category;
      }
    }
    return null;
  }

  /**
   * Find or create named installation category among specified parent children ones.
   * @param parent_p
   * @param categoryName_p
   * @return
   */
  protected static Category findOrCreateInstallationCategory(Category parent_p, String categoryName_p) {
    // Preconditions.
    if ((null == parent_p) || (null == categoryName_p)) {
      return null;
    }
    // Find category.
    Category result = findCategoryByName(parent_p, categoryName_p);
    // Create it if needed.
    if (null == result) {
      result = ContextsFactory.eINSTANCE.createInstallationCategory();
      result.setName(categoryName_p);
      parent_p.getCategories().add(result);
    }
    return result;
  }

  /**
   * Get all selected versions from specified context up to its full hierarchy.
   * @param context_p
   * @return
   */
  public static Collection<InstallationCategory> getAllSelectedVersions(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return Collections.emptyList();
    }
    // Result.
    Collection<InstallationCategory> result = new ArrayList<InstallationCategory>(context_p.getSelectedVersions().size());
    Context currentContext = context_p;
    while ((null != currentContext) && (null != currentContext.eResource()) && !currentContext.eResource().isDefault()) {
      // Handle selected versions.
      List<InstallationCategory> selectedVersions = currentContext.getSelectedVersions();
      // Cycle through them.
      for (InstallationCategory selectedVersion : selectedVersions) {
        // Skip invalid content.
        if (null == selectedVersion.getReferencePath()) {
          continue;
        }
        // Make sure there is not already a version selected for this component.
        String componentPath = new Path(selectedVersion.getReferencePath()).removeLastSegments(1).toOSString();
        if (null == getSelectedVersionFor(componentPath, result)) {
          result.add(selectedVersion);
        }
      }
      // Up to parent one.
      currentContext = currentContext.getSuperContext();
    }
    return result;
  }

  /**
   * Get all level-1 categories, including parents ones.
   * @param context_p
   * @return A not <code>null</code> but possibly empty list of {@link Category}.
   */
  public static List<Category> getCategories(Context context_p) {
    List<Category> allCategories = new ArrayList<Category>(0);
    Context context = context_p;
    while (null != context) {
      // Add local categories.
      allCategories.addAll(context.getCategories());
      // And transient ones.
      allCategories.addAll(context.getTransientCategories());
      // Go up one step.
      context = context.getSuperContext();
    }
    return allCategories;
  }

  /**
   * Get logical direct containing category for specified element.<br>
   * In most cases, this is the physical direct container.<br>
   * For a {@link Category} this is the category itself.<br>
   * For an {@link OverridingVariable}, this is the overridden variable category.<br>
   * For a contributed variable, this is the super category, ie the category the variable is contributed to.<br>
   * Finally, for a {@link VariableValue}, this is the container's category.<br>
   * A {@link Context} does not have a category.
   * @param element_p
   * @return <code>null</code> if none could be found, or it does not make sense.
   */
  public static Category getCategory(Object element_p) {
    // Precondition.
    if (!(element_p instanceof EObject)) {
      return null;
    }
    return new ContextsSwitch<Category>() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseCategory(com.thalesgroup.orchestra.framework.model.contexts.Category)
       */
      @Override
      public Category caseCategory(Category object_p) {
        return object_p;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseOverridingVariable(com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable)
       */
      @Override
      public Category caseOverridingVariable(OverridingVariable object_p) {
        return getCategory(object_p.getOverriddenVariable());
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariable(com.thalesgroup.orchestra.framework.model.contexts.Variable)
       */
      @Override
      public Category caseVariable(Variable object_p) {
        if (object_p.eContainer() instanceof Context) {
          return getCategory(object_p.getSuperCategory());
        }
        return getCategory(object_p.eContainer());
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariableValue(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
       */
      @Override
      public Category caseVariableValue(VariableValue object_p) {
        return getCategory(object_p.eContainer());
      }
    }.doSwitch((EObject) element_p);
  }

  /**
   * Get compatibilities {@link InstallationCategory} for specified component category.
   * @param category_p
   * @return <code>null</code> if none could be found.
   */
  public static InstallationCategory getCompatibilitiesFor(Category category_p) {
    // Precondition.
    if (!(category_p instanceof InstallationCategory)) {
      return null;
    }
    // Cycle through children categories.
    for (Category category : category_p.getCategories()) {
      // Search for compatibilities one.
      if (INSTALLATION_COMPATIBILITIES_NAME.equals(category.getName())) {
        return (InstallationCategory) category;
      }
    }
    // Not found !
    return null;
  }

  /**
   * Get context for specified element.
   * @param element_p
   * @return <code>null</code> if this is not applicable.
   */
  public static Context getContext(Object element_p) {
    if (element_p instanceof ContextsResourceImpl) {
      EList<EObject> contents = ((Resource) element_p).getContents();
      if (contents.size() > 0) {
        Object firstElement = contents.get(0);
        if (ContextsPackage.Literals.CONTEXT.isInstance(firstElement)) {
          return (Context) firstElement;
        }
      }
    } else if (ContextsPackage.Literals.CONTEXT.isInstance(element_p)) {
      return (Context) element_p;
    } else if (ContextsPackage.Literals.MODEL_ELEMENT.isInstance(element_p)) {
      EObject rootContainer = EcoreUtil.getRootContainer((ModelElement) element_p);
      if (ContextsPackage.Literals.CONTEXT.isInstance(rootContainer)) {
        return (Context) rootContainer;
      }
    }
    return null;
  }

  /**
   * Get available current version for specified version path (ie a path to a category version contained in the category standing for the product) in specified
   * context hierarchy.
   * @param selectedVersionPath_p
   * @param context_p
   * @param isStrictVersion_p <code>true</code> to search for specified version only. <code>false</code> to search for any version on the product pointed by
   *          specified path.
   * @return <code>null</code> if parameters are invalid, or there is no current such version for specified product. A transient {@link InstallationCategory}
   *         standing for currently selected version otherwise.
   */
  public static InstallationCategory getCurrentVersionForPath(String selectedVersionPath_p, Context context_p, boolean isStrictVersion_p) {
    return getVersionForPath(selectedVersionPath_p, context_p, true, isStrictVersion_p);
  }

  /**
   * Get elder parent of specified context.<br>
   * The Default Context is excluded.<br>
   * So this is the eldest parent of specified context, direct child of Default Context.
   * @param context_p
   * @return <code>null</code> if specified context is <code>null</code>. Otherwise the parent context that qualifies (this can be specified context itself).
   */
  public static Context getElderParent(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return null;
    }
    Context result = context_p;
    while (!((null == result) || (null == result.getSuperContext()) || result.getSuperContext().eResource().isDefault())) {
      result = context_p.getSuperContext();
    }
    return result;
  }

  /**
   * Get specified element path.<br>
   * This path is composed of containing categories, and the element name (or id if none).<br>
   * For instance, variable "ThavConn" contained in category "Cat1" from context "Context1" has the following path : <b>\Cat1\ThavConn</b>.<br>
   * Note that the context has no influence in the variable path.<br>
   * A contributed variable "ProcessEM" from "Context2" to category "Cat1" has the following path : <b>\Cat1\ProcessEM</b>.<br>
   * For instance, if "Cat1" would be contained in "Cat0", the variable path would be : <b>\Cat0\Cat1\ProcessEM</b>.
   * @param element_p
   * @return Empty string if the element does not qualify to path reconstruction. A {@link String} representing that path otherwise.
   */
  public static String getElementPath(Object element_p) {
    if (element_p instanceof VariableValue) {
      VariableValue value = (VariableValue) element_p;
      return getElementPath(value.eContainer()) + ICommonConstants.PATH_SEPARATOR + value.getId();
    }
    // Go on.
    String path = ICommonConstants.EMPTY_STRING;
    Category category = getCategory(element_p);
    // Precondition.
    if (null == category) {
      return path;
    }
    // Initialize path.
    // Element must be different from its category.
    if (element_p != category) {
      String lastSegment = null;
      ModelElement element = (ModelElement) element_p;
      // Try and get name first.
      if (element instanceof NamedElement) {
        NamedElement namedElement = (NamedElement) element;
        lastSegment = namedElement.getName();
      }
      // Fall-back to ID.
      if (null == lastSegment) {
        lastSegment = element.getId();
      }
      path = ICommonConstants.PATH_SEPARATOR + lastSegment;
    }
    // Then up to reconstruct path.
    NamedElement currentElement = category;
    while (null != currentElement) {
      if (!(currentElement instanceof Context)) {
        // This is necessarily a category.
        Category currentCategory = (Category) currentElement;
        path = ICommonConstants.PATH_SEPARATOR + currentCategory.getName() + path;
        // If current element is a contributed category to another one, switch to the other one tree.
        if (null != currentCategory.getSuperCategory()) {
          currentElement = currentCategory.getSuperCategory();
        } else {
          // Parent is to be a NamedElement.
          currentElement = (NamedElement) currentElement.eContainer();
        }
      } else {
        // Stop here, context is not included in the path.
        currentElement = null;
      }
    }
    return path;
  }

  /**
   * Get specified context hierarchy, from farthest to closest parent, excluding default context.<br>
   * @param context_p
   * @return A not <code>null</code> but possibly empty list of {@link Context}.
   */
  public static List<Context> getHierarchy(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return Collections.emptyList();
    }
    ArrayList<Context> result = new ArrayList<Context>(0);
    Context currentContext = context_p.getSuperContext();
    while (!currentContext.eResource().isDefault()) {
      result.add(currentContext);
      currentContext = currentContext.getSuperContext();
    }
    return result;
  }

  /**
   * Get installation configuration file path to use.
   * @return
   */
  public static String getInstallationConfigurationFilePath() {
    return System.getProperty("java.io.tmpdir") + "OrcFmkFetch\\installation.configuration"; //$NON-NLS-1$ //$NON-NLS-2$
  }

  /**
   * Get specified element logical container.<br>
   * In most cases, this is the physical direct container.<br>
   * For an {@link AbstractVariable} this is the {@link Category} as returned by {@link #getCategory(Object)}.<br>
   * For a {@link ContributedElement} this is the {@link Category} that hosts the contribution.<br>
   * For a root category, this is the specified context.<br>
   * For an {@link OverridingVariableValue} this is the logical container of the overridden value.
   * @param element_p
   * @param context_p
   * @return
   */
  public static ModelElement getLogicalContainer(Object element_p, final Context context_p) {
    // Precondition.
    if (!(element_p instanceof EObject)) {
      return null;
    }
    // Do find container.
    ModelElement container = new ContextsSwitch<ModelElement>() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseAbstractVariable(com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable)
       */
      @Override
      public ModelElement caseAbstractVariable(AbstractVariable object_p) {
        return getCategory(object_p);
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseContributedElement(com.thalesgroup.orchestra.framework.model.contexts.ContributedElement)
       */
      @Override
      public ModelElement caseContributedElement(ContributedElement object_p) {
        return object_p.getSuperCategory();
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseOverridingVariableValue(com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue)
       */
      @Override
      public ModelElement caseOverridingVariableValue(OverridingVariableValue object_p) {
        return getLogicalContainer(object_p.getOverriddenValue(), context_p);
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariableValue(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
       */
      @Override
      public ModelElement caseVariableValue(VariableValue object_p) {
        if (object_p.eContainer() instanceof OverridingVariable) {
          return ((OverridingVariable) object_p.eContainer()).getOverriddenVariable();
        }
        return null;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#defaultCase(org.eclipse.emf.ecore.EObject)
       */
      @Override
      public ModelElement defaultCase(EObject object_p) {
        return (ModelElement) object_p.eContainer();
      }
    }.doSwitch((EObject) element_p);
    // Mute context to specified one.
    if (container instanceof Context) {
      container = context_p;
    }
    return container;
  }

  /**
   * Get Orchestra Doctor executable path.
   * @return
   */
  public static IStatus getOrchestraDoctorExecutablePath() {
    // Doctor path file relative path.
    String doctorPathRelativePath = "lib/OrchestraDoctor.path"; //$NON-NLS-1$
    String doctorExecutablePath = null;
    String pluginId = ModelActivator.getInstance().getPluginId();
    MultiStatus errorResult = new MultiStatus(pluginId, 0, Messages.ModelUtil_OrchestraDoctorFetch_Error_WrapUpMessage, null);
    // First stage : try and resolve the file as pushed by the Framework at startup.
    {
      // Get application data path.
      String appData = System.getenv("APPDATA"); //$NON-NLS-1$
      // Precondition.
      if ((null == appData) || appData.trim().isEmpty()) {
        return new Status(IStatus.ERROR, pluginId, Messages.ModelUtil_OrchestraDoctorFetch_Error_UnableToAccessOrchestraDoctorInstallationPath);
      }
      IPath path = new Path(appData).append("Orchestra/OrchestraFramework").append(doctorPathRelativePath); //$NON-NLS-1$
      try {
        doctorExecutablePath = new String(FileHelper.readFile(new FileInputStream(path.toFile())));
      } catch (Exception exception_p) {
        errorResult.add(new Status(IStatus.ERROR, pluginId, Messages.ModelUtil_OrchestraDoctorFetch_Error_UnableToAccessOrchestraDoctorInstallationPath,
            exception_p));
        doctorExecutablePath = null;
      }
      // Make sure path is valid.
      doctorExecutablePath = convertOrchestraDoctorPath(doctorExecutablePath);
    }
    // Second stage : try and resolve against platform installation path.
    if (null == doctorExecutablePath) {
      // Try and get platform installation path.
      URL platformInstallationURL = Platform.getInstallLocation().getURL();
      try {
        // Remove 'eclipse' last segment before adding OD path relative path.
        IPath path = new Path(new File(platformInstallationURL.getPath()).getAbsolutePath()).removeLastSegments(1).append(doctorPathRelativePath);
        doctorExecutablePath = new String(FileHelper.readFile(new FileInputStream(path.toFile())));
      } catch (Exception exception_p) {
        errorResult.add(new Status(IStatus.ERROR, pluginId, Messages.ModelUtil_OrchestraDoctorFetch_Error_UnableToAccessOrchestraDoctorInstallationPath,
            exception_p));
        doctorExecutablePath = null;
      }
      // Make sure path is valid.
      doctorExecutablePath = convertOrchestraDoctorPath(doctorExecutablePath);
    }
    // Third stage : try and resolve desperately against working directory.
    if (null == doctorExecutablePath) {
      try {
        String platformPath = new File(ICommonConstants.EMPTY_STRING).getAbsolutePath();
        // Remove 'eclipse' last segment before adding OD path relative path.
        IPath path = new Path(platformPath).removeLastSegments(1).append(doctorPathRelativePath);
        doctorExecutablePath = new String(FileHelper.readFile(new FileInputStream(path.toFile())));
      } catch (Exception exception_p) {
        errorResult.add(new Status(IStatus.ERROR, pluginId, Messages.ModelUtil_OrchestraDoctorFetch_Error_UnableToAccessOrchestraDoctorInstallationPath,
            exception_p));
        doctorExecutablePath = null;
      }
      // Make sure path is valid.
      doctorExecutablePath = convertOrchestraDoctorPath(doctorExecutablePath);
    }
    // Check if one the stage was successful.
    if (null == doctorExecutablePath) {
      return new Status(IStatus.ERROR, pluginId, Messages.ModelUtil_OrchestraDoctorFetch_Error_UnableToResolveOrchestraDoctorInstallationPath);
    }
    // Returning Orchestra Doctor path.
    return new Status(IStatus.OK, pluginId, doctorExecutablePath);
  }

  /**
   * Gets variable values (contained in overriding variables of the given context) filling one of the following conditions :<br>
   * 1. The variable overridden by the overriding variable containing the variable value is unloaded,<br>
   * 2. The variable overrides another variable value which is unloaded.
   * @param context_p the context where to find OverridingVariable/VariableValues.
   * @return a map containing the overriding variables with there orphan values.
   */
  public static Map<OverridingVariable, List<VariableValue>> getOrphanOverridingVariablesValues(Context context_p) {
    Map<OverridingVariable, List<VariableValue>> orphanVariableValues = new HashMap<OverridingVariable, List<VariableValue>>();

    // Go through overriding variables of the given context.
    for (OverridingVariable overridingVariable : context_p.getOverridingVariables()) {
      List<VariableValue> variableValues = new ArrayList<VariableValue>();

      // If the overridden variable is unloaded or doesn't exist anymore -> keep all values of this overriding variable (VariableValues (=contributed values) +
      // OverridingVariableValues).
      Variable overriddenVariable = overridingVariable.getOverriddenVariable();
      if (null == overriddenVariable || overriddenVariable.eIsProxy()) {
        variableValues.addAll(overridingVariable.getValues());
      } else {
        // Go through values of the overriding variable values.
        for (VariableValue variableValue : overridingVariable.getValues()) {
          if (variableValue instanceof OverridingVariableValue) {
            OverridingVariableValue overridingVariableValue = (OverridingVariableValue) variableValue;
            // Keep overriding variable values which have there overridden values unloaded or non existing.
            VariableValue overriddenVariableValue = overridingVariableValue.getOverriddenValue();
            if (null == overriddenVariableValue || overriddenVariableValue.eIsProxy()) {
              variableValues.add(overridingVariableValue);
            }
          }
        }
      }
      // This overriding variable contains some variable values -> keep them.
      if (!variableValues.isEmpty()) {
        orphanVariableValues.put(overridingVariable, variableValues);
      }
    }

    return orphanVariableValues;
  }

  /**
   * Returns all categories contributed to an unloaded or non existing parent category.
   * @param context_p the context where to find categories.
   * @return the list of orphan categories in this context.
   */
  public static List<Category> getOrphanSuperCategoryCategories(Context context_p) {
    List<Category> orphanSuperCategoryCategories = new ArrayList<Category>(0);

    // Go through super category categories.
    EList<Category> superCategoryCategories = context_p.getSuperCategoryCategories();
    for (Category category : superCategoryCategories) {
      Category containingSuperCategory = category.getSuperCategory();
      if (null == containingSuperCategory || containingSuperCategory.eIsProxy()) {
        orphanSuperCategoryCategories.add(category);
      }
    }

    return orphanSuperCategoryCategories;
  }

  /**
   * Returns all variables contributed to an unloaded parent category.
   * @param context_p the context where to find variables.
   * @return the list of orphan variables in this context.
   */
  public static List<Variable> getOrphanSuperCategoryVariables(Context context_p) {
    List<Variable> orphanSuperCategoryVariables = new ArrayList<Variable>(0);

    // Go through super category variables.
    EList<Variable> superCategoryVariables = context_p.getSuperCategoryVariables();
    for (Variable variable : superCategoryVariables) {
      Category containingSuperCategory = variable.getSuperCategory();
      if (null == containingSuperCategory || containingSuperCategory.eIsProxy()) {
        orphanSuperCategoryVariables.add(variable);
      }
    }

    return orphanSuperCategoryVariables;
  }

  /**
   * Return overriding variables which override a final variable in the given context.
   * @param context_p the context where to find overriding variables.
   * @return the list of variables overriding a final variable.
   */
  public static List<OverridingVariable> getOverridingVariablesFromFinalVariables(Context context_p) {
    List<OverridingVariable> orphanOverridingVariablesFromFinalVariables = new ArrayList<OverridingVariable>(0);

    EList<OverridingVariable> overridingVariables = context_p.getOverridingVariables();
    for (OverridingVariable overridingVariable : overridingVariables) {
      Variable overriddenVariable = overridingVariable.getOverriddenVariable();
      if (null != overriddenVariable && !overriddenVariable.eIsProxy() && overriddenVariable.isFinal()) {
        orphanOverridingVariablesFromFinalVariables.add(overridingVariable);
      }
    }

    return orphanOverridingVariablesFromFinalVariables;
  }

  /**
   * Get the pending category of each context, starting from the given context.
   * @param context_p
   * @return a not <code>null</code> but possibly empty list of pending categories.
   */
  public static List<Category> getPendingCategories(Context context_p) {
    List<Category> pendingCategories = new ArrayList<Category>(0);
    Context context = context_p;
    while (null != context) {
      Category pendingCategory = getPendingCategory(context);
      if (null != pendingCategory) {
        pendingCategories.add(pendingCategory);
      }
      // Go up one step.
      context = context.getSuperContext();
    }
    return pendingCategories;
  }

  /**
   * Get the category for pending elements for specified context.<br>
   * Note that this category is not persisted.
   * @param context_p
   * @return
   */
  public static Category getPendingCategory(Context context_p) {
    Category result = null;
    // Precondition.
    if (null == context_p) {
      return result;
    }
    // Search for pending elements category.
    for (Category category : context_p.getTransientCategories()) {
      if (Messages.CATEGORY_NAME_PENDINGELEMENTS.equals(category.getName())) {
        result = category;
        break;
      }
    }
    return result;
  }

  /**
   * Get reference string that points to specified variable.<br>
   * Should be used in a variable value.
   * @param variable_p
   * @return
   */
  public static String getReferenceString(AbstractVariable variable_p) {
    if (null == variable_p) {
      return ICommonConstants.EMPTY_STRING;
    }
    return MessageFormat.format(VARIABLE_REFERENCE_MESSAGE_FORMAT_PATTERN, VARIABLE_REFERENCE_TYPE_ODM, ModelUtil.getElementPath(variable_p));
  }

  /**
   * Get reference string prefix.<br>
   * See {@link #getReferenceString(AbstractVariable)} for definition of the reference string.
   * @return
   */
  public static String getReferenceStringPrefix() {
    // Lazy computation.
    if (null == __referenceStringPrefix) {
      __referenceStringPrefix = getReferenceString(ContextsFactory.eINSTANCE.createVariable()).replace("}", ICommonConstants.EMPTY_STRING); //$NON-NLS-1$
    }
    return __referenceStringPrefix;
  }

  /**
   * Get logical root category container for specified element.<br>
   * See {@link #getCategory(Object)} for definition of a logical category.<br>
   * The root category is typically contained by a context.<br>
   * It may be either a persisted category, or a transient one.
   * @param element_p
   * @return <code>null</code> if none could be found, or it does not make sense.
   */
  public static Category getRootCategory(Object element_p) {
    Category category = getCategory(element_p);
    while (null != category) {
      // Contained in a category, go on with current tree.
      if (category.eContainer() instanceof Category) {
        category = (Category) category.eContainer();
      } else if (null != category.getSuperCategory()) { // No parent category, but a super one, go on with super tree.
        category = category.getSuperCategory();
      } else {
        // This is the root category case : contained by a context and not contributed to another category.
        // Stop here.
        break;
      }
    }
    return category;
  }

  /**
   * Get selected version for specified component path among specified selected versions collection.
   * @param componentPath_p
   * @param selectedVersions_p
   * @return <code>null</code> if parameters are invalid, or none could be found.
   */
  protected static InstallationCategory getSelectedVersionFor(String componentPath_p, Collection<InstallationCategory> selectedVersions_p) {
    // Preconditions.
    if ((null == componentPath_p) || (null == selectedVersions_p)) {
      return null;
    }
    // Result.
    InstallationCategory selectedVersion = null;
    // Search for an already known selected version that points to this product.
    for (InstallationCategory installationCategory : selectedVersions_p) {
      String versionPath = installationCategory.getReferencePath();
      if ((null != versionPath) && versionPath.startsWith(componentPath_p)) {
        // Stop here, version already found.
        selectedVersion = installationCategory;
        break;
      }
    }
    return selectedVersion;
  }

  /**
   * Get available selected version for specified version path (ie a path to a category version contained in the category standing for the product) in specified
   * context hierarchy.
   * @param selectedVersionPath_p
   * @param context_p
   * @param isStrictVersion_p <code>true</code> to search for specified version only. <code>false</code> to search for any version on the product pointed by
   *          specified path.
   * @return <code>null</code> if parameters are invalid, or there is no selected such version for specified product. A persisted {@link InstallationCategory}
   *         standing for selected version otherwise.
   */
  public static InstallationCategory getSelectedVersionForPath(String selectedVersionPath_p, Context context_p, boolean isStrictVersion_p) {
    return getVersionForPath(selectedVersionPath_p, context_p, false, isStrictVersion_p);
  }

  /**
   * Get URI fragment for specified category within specified context (contributed one expected).
   * @param category_p
   * @param contributedContext_p
   * @return
   */
  private static String getUriFragment(Category category_p, Context contributedContext_p) {
    // Preconditions.
    if ((null == category_p) || (null == contributedContext_p)) {
      return null;
    }
    URI uri = EcoreUtil.getURI(category_p);
    return uri.fragment();
  }

  /**
   * Get variable for specified category and name, among directly contained 1-level (shallow) ones.
   * @param category_p
   * @param variableName_p
   * @return <code>null</code> if parameters are invalid, or such a variable could not be found.
   */
  public static Variable getVariable(Category category_p, String variableName_p) {
    // Preconditions.
    if ((null == category_p) || (null == variableName_p)) {
      return null;
    }
    // Cycle through directly contained variables.
    for (Variable variable : category_p.getVariables()) {
      // Found it.
      if (variableName_p.equals(variable.getName())) {
        return variable;
      }
    }
    // Not found.
    return null;
  }

  /**
   * Get available version for specified version path (ie a path to a category version contained in the category standing for the component) in specified
   * context hierarchy.
   * @param selectedVersionPath_p
   * @param context_p
   * @param searchForCurrentVersion_p <code>true</code> to search for current version. <code>false</code> to search for selected one.
   * @param isStrictVersion_p <code>true</code> to search for specified version only. <code>false</code> to search for any version of the component pointed by
   *          specified path.
   * @return <code>null</code> if parameters are invalid, or there is no such version for specified component. An {@link InstallationCategory} standing for
   *         chosen version otherwise. It can be either a current or selected version, depending on searchForCurrentVersion_p choice.
   */
  private static InstallationCategory getVersionForPath(String versionPath_p, Context context_p, boolean searchForCurrentVersion_p, boolean isStrictVersion_p) {
    // Precondition.
    if ((null == versionPath_p) || (null == context_p)) {
      return null;
    }
    // Component path, if comparison should be made against product.
    String componentPath = null;
    if (!isStrictVersion_p) {
      // Resolve component path first.
      int separatorLastIndex = versionPath_p.lastIndexOf('\\');
      // Invalid argument.
      if (0 > separatorLastIndex) {
        return null;
      }
      // Move up to parent category path.
      componentPath = versionPath_p.substring(0, separatorLastIndex);
    }
    // Cycle through context hierarchy.
    Context currentContext = context_p;
    // Stop at no context (should not happen) or default context levels.
    while ((null != currentContext) && !currentContext.eResource().isDefault()) {
      // Categories to search through.
      List<InstallationCategory> categories = searchForCurrentVersion_p ? currentContext.getCurrentVersions() : currentContext.getSelectedVersions();
      // Cycle through categories.
      for (InstallationCategory versionCategory : categories) {
        // There should be only one such category per hierarchy !
        String versionReferencePath = versionCategory.getReferencePath();
        if (null != versionReferencePath) {
          // Check against component category path, if search isn't strict.
          // Check against category version directly otherwise.
          IPath referencePath = new Path(versionReferencePath);
          referencePath = referencePath.removeLastSegments(1);
          boolean isAMatch = (null != componentPath) ? referencePath.toOSString().equals(componentPath) : versionPath_p.equals(versionReferencePath);
          if (isAMatch) {
            return versionCategory;
          }
        }
      }
      currentContext = currentContext.getSuperContext();
    }
    // None could be found.
    return null;
  }

  /**
   * Initialize destination value with source value contained values.<br>
   * Note that this is only applicable to environment variable values.
   * @param destinationValue_p
   * @param sourceValue_p
   */
  public static void initializeValue(VariableValue destinationValue_p, VariableValue sourceValue_p) {
    if ((destinationValue_p instanceof EnvironmentVariableValue) && (sourceValue_p instanceof EnvironmentVariableValue)) {
      EnvironmentVariableValue target = (EnvironmentVariableValue) destinationValue_p;
      EnvironmentVariableValue source = (EnvironmentVariableValue) sourceValue_p;
      // Use parent attributes...
      target.setEnvironmentId(source.getEnvironmentId());
      // ... and values.
      Iterator<Entry<String, String>> iterator = source.getValues().iterator();
      while (iterator.hasNext()) {
        Entry<String, String> entry = iterator.next();
        // Initialize with original values.
        target.getValues().put(entry.getKey(), entry.getValue());
      }
    }
  }

  /**
   * Returns whether the second object is contained by the first object in the specified context.
   * @param ancestorEObject the ancestor object.
   * @param eObject the object to test.
   * @param context_p
   * @return <code>true</code>if the first object is an ancestor of the second one (or if the first object == the second one), <code>false</code> else.
   */
  public static boolean isLogicalAncestor(EObject ancestorEObject, EObject eObject, Context context_p) {
    if (eObject != null) {
      if (eObject == ancestorEObject) {
        return true;
      }
      // Go through logical ancestors of eObject to find ancestorEObject.
      ModelElement container = getLogicalContainer(eObject, context_p);
      while (null != container) {
        if (container == ancestorEObject) {
          return true;
        }
        container = getLogicalContainer(container, context_p);
      }
    }
    return false;
  }

  /**
   * Is specified variable a mono-valued one ?
   * @param variable_p
   * @return <code>true</code> if so, or the specified variable is <code>null</code>. So potential <code>null</code> value has to be tested by caller too.
   */
  public static boolean isMonoValuedVariable(AbstractVariable variable_p) {
    // Precondition.
    if (null == variable_p) {
      return true;
    }
    AbstractVariable targetVariable = variable_p;
    if (variable_p instanceof OverridingVariable) {
      targetVariable = ((OverridingVariable) variable_p).getOverriddenVariable();
    }
    return !targetVariable.isMultiValued();
  }

  /**
   * Is specified {@link InstallationCategory} a selectable version for a given product ?
   * @param installationCategory_p
   * @return <code>true</code> if so, <code>false</code> if parameters are invalid, or category is not a selectable one.
   */
  public static boolean isSelectableVersionCategory(InstallationCategory installationCategory_p) {
    // Precondition.
    if (null == installationCategory_p) {
      return false;
    }
    // The current version of a product can not be selected as a persistent version.
    if (INSTALLATION_CATEGORY_CURRENT_NAME.equals(installationCategory_p.getName())) {
      return false;
    }
    return installationCategory_p.isReferenceable();
  }

  /**
   * Is specified model element a transient one ? <br>
   * An element is transient if its root category is transient.
   * @param modelElement_p
   * @return
   */
  public static boolean isTransientElement(Object modelElement_p) {
    // By default, an unknown/pending/... category is NOT transient.
    boolean result = false;
    // Precondition.
    if (null == modelElement_p) {
      return result;
    }
    Category rootCategory = getRootCategory(modelElement_p);
    EReference containmentFeature = (null != rootCategory) ? rootCategory.eContainmentFeature() : null;
    if (null != containmentFeature) {
      result = ContextsPackage.Literals.CONTEXT__TRANSIENT_CATEGORIES.equals(containmentFeature);
    }
    return result;
  }

  /**
   * This method resolve the value. This custom mechanism is set up to avoid duplication of data. The value can be defined in the environment, as a value or in
   * another windows registry key.<br />
   * The value can be one of:
   * <ul>
   * <li>
   * <dl>
   * <dt>env:String</dt>
   * <dd><strong>Environment variable</strong>. The <code>String</code> environment variable is returned with the help of {@link System#getenv(String)}.</dd>
   * </dl>
   * </li>
   * <li>
   * <dl>
   * <dt>value:String</dt>
   * <dd><strong>Simple string value</strong>. return <code>String</code>.</dd>
   * </dl>
   * </li>
   * <li>
   * <dl>
   * <dt>reg:String</dt>
   * <dd><strong>Registry Key</strong>. return the value of the <code>String</code> registry key.</dd>
   * </dl>
   * </li>
   * </ul>
   * @param variableName_p the variable name (that holds the value).
   * @param value_p the value to resolve.
   * @return the resolved value.
   * @see http://150.2.39.128/mediawiki/index.php/Automatic_population#How_OFVM_will_find_the_information_for_Orchestra_Tools_.3F
   */
  protected static String resolveInstallationValue(String variableName_p, String value_p) {
    // Preconditions.
    if (null == value_p) {
      return null;
    }
    if (!value_p.startsWith(TYPE_ENV + SEPARATOR) && !value_p.startsWith(TYPE_VALUE + SEPARATOR)) {
      return convertValue(variableName_p, value_p, false);
    }
    // Find the initial pattern.
    String toResolve = value_p.substring(value_p.indexOf(SEPARATOR) + 1);
    if (value_p.startsWith(TYPE_ENV)) {
      return convertValue(variableName_p, System.getenv(toResolve), false);
    } else if (value_p.startsWith(TYPE_VALUE)) {
      return convertValue(variableName_p, toResolve, false);
    }
    // Pattern not recognized.
    StringBuilder loggerMessage = new StringBuilder("ModelUtil.resolveRegistryValue(..) _ "); //$NON-NLS-1$
    loggerMessage.append("The pattern ["); //$NON-NLS-1$
    loggerMessage.append(toResolve);
    loggerMessage.append("] is not recognized."); //$NON-NLS-1$
    CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, null);
    return null;
  }

  /**
   * Add versions selection categories to specified context.<br>
   * If no such data already exist, then create them based on currently known platform.<br>
   * If new components are available on platform, it also adds version categories for them.
   * @param context_p
   */
  public static void selectVersionsForContext(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return;
    }
    // Get existing versions categories.
    List<InstallationCategory> selectedVersions = context_p.getSelectedVersions();
    // TODO Guillaume.
    // Maybe one should just update non-existing versions.
    // For now, clear all existing ones, and start it all-over.
    selectedVersions.clear();
    // Search for Orchestra installation one.
    List<Category> categories = getCategories(context_p);
    Category orchestraInstallation = null;
    for (Category category : categories) {
      if (INSTALLATION_CATEGORY_NAME.equals(category.getName())) {
        orchestraInstallation = category;
        break;
      }
    }
    // Precondition.
    if (null == orchestraInstallation) {
      return;
    }
    // Update versions recursively.
    updateSelectedVersions(orchestraInstallation, selectedVersions);
  }

  /**
   * Update versions for applicable categories in selected ones.
   * @param category_p The category to use as a search seed.
   * @param selectedVersions_p The resulting list of selected versions for a {@link Context}.
   */
  protected static void updateSelectedVersions(Category category_p, List<InstallationCategory> selectedVersions_p) {
    // Precondition.
    if ((null == category_p) || (null == selectedVersions_p)) {
      return;
    }
    // Cycle through contained categories.
    Collection<Category> categories = category_p.getCategories();
    for (Category category : categories) {
      // Found a category containing versions.
      if ((category instanceof InstallationCategory) && isSelectableVersionCategory((InstallationCategory) category)) {
        String parentElementPath = ModelUtil.getElementPath(category_p);        
        // Search for an installation category in selected versions that points to a child category of selected one.
        Category selectedVersion = null;
        for (InstallationCategory version : selectedVersions_p) {
          String referencePath = version.getReferencePath();
          if ((null != referencePath) && referencePath.startsWith(parentElementPath)) {
            selectedVersion = version;
          }
        }
        // Could not find any, create one !
        if (null == selectedVersion) {
          // Get first version.
          selectedVersion = category;
          // Create pointing version.
          InstallationCategory versionCategory = ContextsFactory.eINSTANCE.createInstallationCategory();
          versionCategory.setReferencePath(getElementPath(selectedVersion));
          // Add it to current version.
          selectedVersions_p.add(versionCategory);
        }
      }
      updateSelectedVersions(category, selectedVersions_p);
    }
  }

  /**
   * Class used to store variable reference components.
   * @author T0052089
   */
  public static class VariableReferenceType {
    /**
     * The variable reference String, as given by the user.
     */
    public final String _variableReference;
    /**
     * The variable reference path (\Category0\Variable0).
     */
    public final String _variableReferencePath;
    /**
     * The variable reference type (odm, env_var...).
     */
    public final String _variableReferencePrefix;

    /**
     * Constructor.
     * @param variableReference_p
     * @param variableReferencePrefix_p
     * @param variableReferencePath_p
     */
    public VariableReferenceType(String variableReference_p, String variableReferencePrefix_p, String variableReferencePath_p) {
      _variableReference = variableReference_p;
      _variableReferencePrefix = variableReferencePrefix_p;
      _variableReferencePath = variableReferencePath_p;
    }
  }
}