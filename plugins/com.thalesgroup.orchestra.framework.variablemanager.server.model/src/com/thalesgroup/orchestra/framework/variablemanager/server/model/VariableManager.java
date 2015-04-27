/**
 * <p>
 * Copyright (c) 2002-2009 : Thales - Engineering & Process Management
 * </p>
 * <p>
 * Société : Thales - Engineering & Process Management
 * </p>
 * <p>
 * Thales Part Number 16 262 601
 * </p>
 */
package com.thalesgroup.orchestra.framework.variablemanager.server.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.model.IRemoteVariableManager;
import com.thalesgroup.orchestra.framework.model.IVariable;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;

/**
 * <p>
 * Title : VariableManager
 * </p>
 * <p>
 * Description : This class is to manage the variable manager server side
 * </p>
 * @author Orchestra developer
 * @version 4.0.0
 */
public class VariableManager extends Observable implements IRemoteVariableManager {
  private static VariableManager _instance = null;
  private static final String VARIABLE_ARTEFACT_PATH = "ArtefactPath"; //$NON-NLS-1$
  private static final String VARIABLE_SHARED_DIRECTORY = "SharedDirectory"; //$NON-NLS-1$
  private static final String VARIABLE_TEMPORARY_DIRECTORY = "TemporaryDirectory"; //$NON-NLS-1$
  public static final String VARNAME_ARTEFACT_PATH = new StringBuilder().append(ICommonConstants.PATH_SEPARATOR).append(DataUtil.__CATEGORY_ORCHESTRA)
      .append(ICommonConstants.PATH_SEPARATOR).append(VARIABLE_ARTEFACT_PATH).toString();
  public static final String VARNAME_CONFIGURATION_DIRECTORY = DataUtil.__CONFIGURATIONDIRECTORY_VARIABLE_NAME;
  public static final String VARNAME_SHARED_DIRECTORY = new StringBuilder().append(ICommonConstants.PATH_SEPARATOR).append(DataUtil.__CATEGORY_ORCHESTRA)
      .append(ICommonConstants.PATH_SEPARATOR).append(VARIABLE_SHARED_DIRECTORY).toString();
  public static final String VARNAME_TEMPORARY_DIRECTORY = new StringBuilder().append(ICommonConstants.PATH_SEPARATOR).append(DataUtil.__CATEGORY_ORCHESTRA)
      .append(ICommonConstants.PATH_SEPARATOR).append(VARIABLE_TEMPORARY_DIRECTORY).toString();

  private VariableManager() {
    // No public constructor
  }

  /**
   * Convert a collection of {@link AbstractVariable} into a list of {@link IVariable}.
   * @param variables_p
   * @return
   */
  private List<IVariable> abstractVariableToIVariable(Collection<AbstractVariable> variables_p) {
    List<IVariable> variables = new ArrayList<IVariable>(variables_p.size());
    for (AbstractVariable abstractVariable : variables_p) {
      if (abstractVariable instanceof EnvironmentVariable) {
        variables.add(new com.thalesgroup.orchestra.framework.variablemanager.server.model.EnvironmentVariable(abstractVariable));
      } else {
        variables.add(new Variable(abstractVariable));
      }
    }
    return variables;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getAllContextNames()
   */
  public List<String> getAllContextNames() {
    List<String> names = new ArrayList<String>();
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    for (RootContextsProject project : dataHandler.getAllContexts().getProjects()) {
      ContextReference context = project.getContext();
      if (null != context) {
        String contextName = context.getName();
        if (null == contextName) {
          try {
            contextName = dataHandler.getContext(context).getName();
          } catch (Exception exception_p) {
            continue;
          }
        }
        if (null != contextName) {
          names.add(contextName);
        }
      }
    }
    return names;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getAllVariables()
   */
  public List<IVariable> getAllVariables() {
    List<IVariable> result = new ArrayList<IVariable>(0);
    Collection<AbstractVariable> variables = DataUtil.getVariables(getCurrentContext());
    result.addAll(abstractVariableToIVariable(variables));
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getAllVariablesLocalized(java.lang.String)
   */
  public List<IVariable> getAllVariablesLocalized(String locale) {
    return getAllVariables();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getCategories(java.lang.String)
   */
  public List<String> getCategories(String path) {
    Collection<Category> categories;
    if (ICommonConstants.PATH_SEPARATOR.equals(path)) {
      categories = ModelUtil.getCategories(getCurrentContext());
    } else {
      Category category = DataUtil.getCategory(path, getCurrentContext());
      if (null == category) {
        return new ArrayList<String>(0);
      }
      categories = DataUtil.getCategories(category, getCurrentContext());
    }
    List<String> categoryNames = new ArrayList<String>(categories.size());
    for (Category cat : categories) {
      categoryNames.add(cat.getName());
    }
    for (String c : categoryNames) {
      System.out.println(c);
    }
    // Sort with case insensivite and lower case first
    Collections.sort(categoryNames, new Comparator<String>() {
      public int compare(String arg0_p, String arg1_p) {
        // Compare case insensitive first
        int result = arg0_p.compareToIgnoreCase(arg1_p);
        // If equals
        if (0 == result) {
          // lower case first
          result = arg0_p.compareTo(arg1_p);
          if (0 != result) {
            return -result;
          }
        }
        return result;
      }

    });
    return categoryNames;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getCategorySeparator()
   */
  public String getCategorySeparator() {
    return ICommonConstants.PATH_SEPARATOR;
  }

  protected Context getCurrentContext() {
    return ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getCurrentContextName()
   */
  public String getCurrentContextName() {
    Context context = getCurrentContext();
    String name = context.getName();
    return name;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getSubstitutedValue(java.lang.String)
   */
  public String getSubstitutedValue(String variablePath_p) {
    return getValue(variablePath_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariable(java.lang.String)
   */
  public IVariable getVariable(String variablePath_p) {
    AbstractVariable variable = DataUtil.getVariable(variablePath_p);
    // Precondition.
    if (null == variable) {
      return null;
    }
    return abstractVariableToIVariable(Collections.singletonList(variable)).get(0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariableArtefactPath()
   */
  public IVariable getVariableArtefactPath() {
    return getVariable(VARNAME_ARTEFACT_PATH);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariableConfigurationDirectory()
   */
  public IVariable getVariableConfigurationDirectory() {
    return getVariable(VARNAME_CONFIGURATION_DIRECTORY);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariableLocalized(java.lang.String, java.lang.String)
   */
  public IVariable getVariableLocalized(String variablePath, String locale) {
    return getVariable(variablePath);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariables(java.lang.String)
   */
  public List<IVariable> getVariables(String categoryPath) {
    Category category = DataUtil.getCategory(categoryPath, getCurrentContext());
    Collection<AbstractVariable> variables = DataUtil.getVariables(category);
    return abstractVariableToIVariable(variables);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariableSharedDirectory()
   */
  public IVariable getVariableSharedDirectory() {
    return getVariable(VARNAME_SHARED_DIRECTORY);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariablesLocalized(java.lang.String, java.lang.String)
   */
  public List<IVariable> getVariablesLocalized(String categoryPath, String locale) {
    return getVariables(categoryPath);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariableTemporaryDirectory()
   */
  public IVariable getVariableTemporaryDirectory() {
    return getVariable(VARNAME_TEMPORARY_DIRECTORY);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#registerAsVariableConsumer(java.lang.String)
   */
  public void registerAsVariableConsumer(String toolInstanceId) {
    ModelHandlerActivator.getDefault().registerConsumer(toolInstanceId);
  }

  public void setCurrentContext(Context context_p) {
    ModelHandlerActivator.getDefault().getDataHandler().setCurrentContext(context_p);
  }

  /**
   * Set the context found in the repository provided in parameter as current
   * @param name
   * @param repositoryPath
   */
  public void setCurrentContextFromItsName(String name) {
    for (Context editedContext : ModelHandlerActivator.getDefault().getDataHandler().getAllContexts().getContexts()) {
      if (name.equals(editedContext.getName())) {
        setCurrentContext(editedContext);
        break;
      }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#unregisterAsVariableConsumer(java.lang.String)
   */
  public void unregisterAsVariableConsumer(String toolInstanceId) {
    ModelHandlerActivator.getDefault().unregisterConsumer(toolInstanceId);
  }

  public static String getFilename(String key) throws FileNotFoundException {
    String value = getValue(key);
    if (value == null) {
      throw new FileNotFoundException(key + " is not set."); //$NON-NLS-1$
    }
    return value;
  }

  /**
   * Get the instance of the server Variable Manager
   * @return the instance of Variable Manager
   */
  public static VariableManager getInstance() {
    if (null == _instance) {
      _instance = new VariableManager();
    }
    return _instance;
  }

  public static String getSharedDirectory() {
    IVariable variable = getInstance().getVariableSharedDirectory();
    String directory = getValue(variable);
    return directory;
  }

  private static String getValue(IVariable variable) {
    String value = null;
    List<String> values = getValues(variable);
    if (values != null) {
      value = values.get(0);
    }
    return value;
  }

  /**
   * Get the value of the variable from its submitted key.<br />
   * If the key does not denote an existing variable, returns <code>null</code>.
   * @param key the variable key to get value of.
   * @return the value of the variable denoted by the submitted key or <code>null</code> if the key does not denote an existing variable.
   */
  public static String getValue(String key) {
    final IVariable variable = getInstance().getVariable(key);
    String value = getValue(variable);
    return value;
  }

  private static List<String> getValues(IVariable variable) {
    List<String> values = null;
    if (variable != null) {
      values = variable.getValues();
    }
    return values;
  }
}