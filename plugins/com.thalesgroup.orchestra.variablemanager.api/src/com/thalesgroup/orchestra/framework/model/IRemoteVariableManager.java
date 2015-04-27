package com.thalesgroup.orchestra.framework.model;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Variable Manager services.
 */
public interface IRemoteVariableManager {
  /**
   * Get all context names.<br>
   * Only contexts currently available in the Framework workspace are considered.
   * @return A {@link List} of context names. Possibly empty but not <code>null</code>.
   */
  List<String> getAllContextNames() throws RemoteException;

  /**
   * Get all variables from current Framework context.
   * @return A {@link List} of {@link IVariable}. Possibly empty but not <code>null</code>.
   */
  List<IVariable> getAllVariables() throws RemoteException;

  /**
   * Reserved.<br>
   * Default implementation is strictly equivalent to {@link #getAllVariables()}.
   */
  List<IVariable> getAllVariablesLocalized(String locale) throws RemoteException;

  /**
   * Get shallow (level 1) sub-categories for specified category.<br>
   * See {@link #getCategorySeparator()} for the definition of a category path.
   * @return A {@link List} of category names. Possibly empty but not <code>null</code>.
   */
  List<String> getCategories(String categoryPath) throws RemoteException;

  /**
   * Get the String separator used to construct a category path.<br>
   * Default implementation is "\".<br>
   * Examples :<br>
   * <ul>
   * <li> <code>\</code> : refers to root categories in current context.
   * <li> <code>\Orchestra</code> : refers to category <code>Orchestra</code> in current context.
   * <li> <code>\Orchestra installation\COTS\Java\Current</code> : refers to category <code>Current</code> for specified parent path in current context.
   * </ul>
   */
  String getCategorySeparator() throws RemoteException;

  /**
   * Get current context name.
   * @return A not <code>null</code> context name.
   */
  String getCurrentContextName() throws RemoteException;

  /**
   * Get specified variable substituted value.<br>
   * See {@link #getVariable(String)} for the variable path definition.<br>
   * A substituted value is a value that is resolved against the current context values.<br>
   * For example, a raw value of a variable can be <code>john${odm:\MyCategory\FOO}doe</code>. Assuming a variable <code>\MyCategory\FOO</code> with value
   * <code>bar</code> is defined, then the substituted value will be: <code>johnbardoe</code>
   * @return <code>null</code> if specified path does not point to a variable in current context.
   */
  String getSubstitutedValue(String variablePath) throws RemoteException;

  /**
   * Get specified variable.<br>
   * The variable path is made of the containing category path (see {@link #getCategorySeparator()}) followed by the category separator, then the variable name.
   * Examples :
   * <ul>
   * <li> <code>\Orchestra\ArtefactPath</code> : refers to the variable <code>ArtefactPath</code> in category <code>\Orchestra</code> in current context.
   * <li> <code>\Orchestra installation\COTS\Java\Current\ExeName</code> : refers to variable <code>ExeName</code> for category
   * <code>\Orchestra installation\COTS\Java\Current</code> in current context.
   * </ul>
   * @return <code>null</code> if specified path does not point to a variable in current context.
   */
  IVariable getVariable(String variablePath) throws RemoteException;

  /**
   * Get the standard orchestra variable :
   * 
   * <pre>
   * \Orchestra\ArteFactPath
   * </pre>
   */
  IVariable getVariableArtefactPath() throws RemoteException;

  /**
   * Get the variable path of the standard orchestra variable :
   * 
   * <pre>
   * \Orchestra\ConfigurationDirectory
   * </pre>
   */
  IVariable getVariableConfigurationDirectory() throws RemoteException;

  /**
   * Reserved.<br>
   * Default implementation is strictly equivalent to {@link #getVariable(String)}.
   */
  IVariable getVariableLocalized(String variablePath, String locale) throws RemoteException;

  /**
   * Returns the list of all variables stored under this category path and descendants.<br />
   * see {@link #getCategorySeparator()} for more info on <code>categoryPath</code>.
   */
  List<IVariable> getVariables(String categoryPath) throws RemoteException;

  /**
   * Get the variable path of the standard orchestra variable :
   * 
   * <pre>
   * \Orchestra\SharedDirectory
   * </pre>
   */
  IVariable getVariableSharedDirectory() throws RemoteException;

  /**
   * Reserved.<br>
   * Default implementation is strictly equivalent to {@link #getVariables(String)}.
   */
  List<IVariable> getVariablesLocalized(String categoryPath, String locale) throws RemoteException;

  /**
   * Get the standard orchestra variable :
   * 
   * <pre>
   * \Orchestra\TemporaryDirectory
   * </pre>
   */
  IVariable getVariableTemporaryDirectory() throws RemoteException;

  /**
   * Register specified tool as variable consumer that is sensitive to context switching.<br>
   * The tool instance id should be unique per tool and instance of this tool.
   */
  void registerAsVariableConsumer(String toolInstanceId) throws RemoteException;

  /**
   * Remove the specified tool from the list of registered consumers.
   */
  void unregisterAsVariableConsumer(String toolInstanceId) throws RemoteException;
}