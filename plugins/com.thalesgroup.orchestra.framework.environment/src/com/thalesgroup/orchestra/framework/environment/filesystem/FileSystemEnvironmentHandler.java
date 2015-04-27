/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem;

import java.nio.file.FileSystems;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.environment.AbstractEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.filesystem.ui.DirectoriesPickingPage;
import com.thalesgroup.orchestra.framework.environment.filesystem.ui.FiltersPage;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler.VariableType;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * File System environment handler.<br>
 * Does take care of the UI creation, along with attributes values.
 * @author t0076261
 */
public class FileSystemEnvironmentHandler extends AbstractEnvironmentHandler {
  /**
   * Filtered directories attribute key.
   */
  public static final String ATTRIBUTE_KEY_FILTERS = "filtered"; //$NON-NLS-1$
  /**
   * Input directories attribute key.
   */
  public static final String ATTRIBUTE_KEY_INPUT_DIRECTORIES = "directories"; //$NON-NLS-1$

  /**
   * By default, do not allow relative directories
   */
  protected boolean _allowRelativeDirectories = false;

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironmentHandler#doGetNextPage(com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage)
   */
  @Override
  protected AbstractEnvironmentPage doGetNextPage(AbstractEnvironmentPage currentPage_p) {
    if (currentPage_p instanceof DirectoriesPickingPage) {
      // Are filters needed for currently edited variable ?
      boolean areFiltersNeeded = VariableType.Artefacts.equals(getVariablesHandler().getVariableType());
      if (areFiltersNeeded) {
        return new FiltersPage(this);
      }
    }
    return null;
  }

  /**
   * Get directories list.
   * @return
   */
  public List<String> getDirectoriesValues() {
    return getDirectoriesValues(getAttributes());
  }

  /**
   * Get decoded directories list from specified attributes map. No substitution is performed, returned values are raw values.
   * @param attributes_p
   * @return
   */
  protected List<String> getDirectoriesValues(Map<String, String> attributes_p) {
    // Precondition.
    if (null == attributes_p) {
      return Collections.emptyList();
    }
    // Get values.
    String directories = attributes_p.get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES);
    if (null == directories) {
      return Collections.emptyList();
    }
    return FileSystemEnvironmentHandler.decodeValues(directories);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getExpandedAttributes(com.thalesgroup.orchestra.framework.environment.EnvironmentContext)
   */
  public Map<String, List<String>> getExpandedAttributes(EnvironmentContext context_p) {
    Map<String, List<String>> result = new HashMap<String, List<String>>(2);
    // Directories list has to be resolved (directory paths could contain variables).
    result.put(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES, getResolvedDirectoriesList(context_p._attributes));
    // In filters nothing to resolve -> use the list without transformation.
    result.put(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_FILTERS, getResolvedFiltersList(context_p._attributes));
    return result;
  }

  /**
   * Get filters list.
   * @return
   */
  public List<String> getFiltersValues() {
    return getFiltersValues(getAttributes());
  }

  /**
   * Get filters list.
   * @param attributes_p
   * @return
   */
  public List<String> getFiltersValues(Map<String, String> attributes_p) {
    // Precondition.
    if (null == attributes_p) {
      return Collections.emptyList();
    }
    // Get values.
    String values = attributes_p.get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_FILTERS);
    if (null == values) {
      return Collections.emptyList();
    }
    return FileSystemEnvironmentHandler.decodeValues(values);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getFirstPage()
   */
  @Override
  public AbstractEnvironmentPage getFirstPage() {
    return new DirectoriesPickingPage(this);
  }

  /**
   * Get decoded and substituted directories list from specified attributes map.
   * @param attributes_p
   * @return The provided raw attributes if no handler could be found, the resolved & substituted list otherwise.
   */
  protected List<String> getResolvedDirectoriesList(Map<String, String> attributes_p) {
    // Get handler.
    IVariablesHandler handler = getVariablesHandler();
    // Get existing directories values.
    List<String> directoriesValues = getDirectoriesValues(attributes_p);
    // Precondition : nothing can be done if a variables handler isn't available.
    if (null == handler) {
      return directoriesValues;
    }
    // Try and substitute values.
    List<String> resolvedDirectoriesList = new ArrayList<String>(directoriesValues.size());
    for (String directory : directoriesValues) {
      resolvedDirectoriesList.add(handler.getSubstitutedValue(directory));
    }
    return resolvedDirectoriesList;
  }

  /**
   * Get decoded and substituted filters list from specified attributes map.
   * @param attributes_p
   * @return The provided raw attributes if no handler could be found, the resolved & substituted list otherwise.
   */
  protected List<String> getResolvedFiltersList(Map<String, String> attributes_p) {
    // Get handler.
    IVariablesHandler handler = getVariablesHandler();
    // Get existing directories values.
    List<String> filterValues = getFiltersValues(attributes_p);
    // Preconditions.
    if (null == handler) {
      return filterValues;
    }
    // Try and substitute values.
    List<String> resolvedFiltersList = new ArrayList<String>(filterValues.size());
    for (String filter : filterValues) {
      resolvedFiltersList.add(handler.getSubstitutedValue(filter));
    }
    return resolvedFiltersList;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getUncompressedAttributes(com.thalesgroup.orchestra.framework.environment.EnvironmentContext)
   */
  public Map<String, List<String>> getUncompressedAttributes(EnvironmentContext context_p) {
    Map<String, List<String>> result = new HashMap<String, List<String>>(2);
    // Directories list has to be resolved (directory paths could contain variables).
    result.put(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES, getDirectoriesValues(context_p._attributes));
    // In filters nothing to resolve -> use the list without transformation.
    result.put(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_FILTERS, getFiltersValues(context_p._attributes));
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#isCreationComplete()
   */
  @Override
  public IStatus isCreationComplete() {
    // Make sure list of provided directories is valid.
    if (!validateDirectories(getDirectoriesValues()).isOK()) {
      return Status.CANCEL_STATUS;
    }
    // Make sure list of provided filters is valid.
    if (!validateFilters(getFiltersValues()).isOK()) {
      return Status.CANCEL_STATUS;
    }
    return Status.OK_STATUS;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironmentHandler#isUseBaselineCompliant(com.thalesgroup.orchestra.framework.environment.EnvironmentContext)
   */
  @Override
  public boolean isUseBaselineCompliant(EnvironmentContext environmentContext_p) {
    // File system is not compliant with the make baseline process.
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#toString(java.util.Map)
   */
  @Override
  public String toString(Map<String, String> attributes_p) {
    if (null == attributes_p) {
      return Messages.FileSystemEnvironmentHandler_ToString_Default;
    }
    // Resolve directories.
    List<String> resolvedDirectoriesList = getResolvedDirectoriesList(attributes_p);
    // String representation.
    return MessageFormat.format(Messages.FileSystemEnvironmentHandler_ToString_Pattern, resolvedDirectoriesList);
  }

  /**
   * Is given directory list valid in FileSystemEnvironment ?
   * @param directoryPaths_p
   * @return <code>Status.CANCEL_STATUS</code> if given list is empty or contains an incorrect directory, <code>Status.OK_STATUS</code> else.
   */
  public IStatus validateDirectories(List<String> directoryPaths_p) {
    if (directoryPaths_p.isEmpty()) {
      return Status.CANCEL_STATUS;
    }
    for (String directoryPath : directoryPaths_p) {
      IStatus directoryValidationStatus = validateDirectory(directoryPath);
      if (!directoryValidationStatus.isOK()) {
        return Status.CANCEL_STATUS;
      }
    }
    return Status.OK_STATUS;
  }

  /**
   * Is specified directory well-formed in FileSystemEnvironment ?
   * @param directoryPath_p
   * @return
   */
  public IStatus validateDirectory(String directoryPath_p) {
    String pluginId = EnvironmentActivator.getInstance().getPluginId();
    // Precondition.
    if ((null == directoryPath_p) || directoryPath_p.isEmpty()) {
      return new Status(IStatus.ERROR, pluginId, Messages.FileSystemEnvironmentHandler_DirectoryValidation_Error_NoContent);
    }
    IVariablesHandler variablesHandler = getVariablesHandler();
    // A non absolute path is erroneous (a value containing reference(s) to variable is considered as OK).
    if (((null == variablesHandler) || directoryPath_p.equals(variablesHandler.getSubstitutedValue(directoryPath_p)))
        && (!_allowRelativeDirectories && !Path.fromOSString(directoryPath_p).isAbsolute())) {
      return new Status(IStatus.ERROR, pluginId, Messages.FileSystemEnvironmentHandler_DirectoryValidation_Error_NonAbsolutePath);
    }
    return Status.OK_STATUS;
  }

  /**
   * Is specified filter a well-formed one ?
   * @param filter_p
   * @return
   */
  public IStatus validateFilter(String filter_p) {
    String pluginId = EnvironmentActivator.getInstance().getPluginId();
    MultiStatus result = new MultiStatus(pluginId, 0, Messages.FileSystemEnvironmentHandler_FilterValidation_WrapUpMessage, null);
    // Precondition.
    if ((null == filter_p) || filter_p.isEmpty()) {
      result.add(new Status(IStatus.ERROR, pluginId, Messages.FileSystemEnvironmentHandler_FilterValidation_Error_NoContent));
      return result;
    }

    try {
      FileSystems.getDefault().getPathMatcher("glob:" + filter_p); //$NON-NLS-1$
    } catch (Exception exception_p) {
      String message = MessageFormat.format(Messages.FileSystemEnvironmentHandler_FilterValidation_Error_MalformedFilter, filter_p);
      result.add(new Status(IStatus.ERROR, pluginId, message));
    }

    return result;
  }

  /**
   * Is given filter list valid ?
   * @param filters_p
   * @return <code>Status.OK_STATUS</code> if all filters are valid, <code>Status.CANCEL_STATUS</code> else.
   */
  public IStatus validateFilters(List<String> filters_p) {
    for (String filter : filters_p) {
      IStatus filterValidationStatus = validateFilter(filter);
      if (!filterValidationStatus.isOK()) {
        return Status.CANCEL_STATUS;
      }
    }
    return Status.OK_STATUS;
  }

  /**
   * Decode values from compressed encoded String representation to a list of decoded String.
   * @param encodedValues_p
   * @return A not <code>null</code> but possibly empty list of decoded values.
   */
  public static List<String> decodeValues(String encodedValues_p) {
    // Precondition.
    if (null == encodedValues_p || encodedValues_p.isEmpty()) {
      return Collections.emptyList();
    }
    // Split values but don't remove trailing space (argument -1) as it can be an empty path.
    String[] values = encodedValues_p.split(";", -1); //$NON-NLS-1$
    if ((null == values) || (0 == values.length)) {
      return Collections.emptyList();
    }
    // Decode each value.
    List<String> result = new ArrayList<String>(values.length);
    for (String value : values) {
      result.add(OrchestraURI.decode(value));
    }
    return result;
  }

  /**
   * Encode values from their raw representation to a compressed encoded String.
   * @param rawValues_p
   * @return <code>null</code> if provided raw values can not be processed, the compressed String otherwise.
   */
  public static String encodeValues(Collection<String> rawValues_p) {
    // Preconditions.
    if ((null == rawValues_p) || rawValues_p.isEmpty()) {
      return null;
    }
    // Cycle through raw values.
    StringBuilder builder = new StringBuilder();
    for (String value : rawValues_p) {
      // Encode value.
      builder.append(OrchestraURI.encode(value)).append(';');
    }
    String resultingString = builder.toString();
    // Remove trailing ';' before returning the result.
    return resultingString.substring(0, resultingString.length() - 1);
  }
}