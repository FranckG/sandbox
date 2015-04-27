/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem;

import java.text.MessageFormat;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;

/**
 * @author T0052089
 */
public class FileSystemEnvironmentDefaultFiltersRegistry {
  /**
   * Default filters set (avoid duplicate elements).
   */
  private final Set<String> _defaultFilters;

  /**
   * Constructor.
   */
  public FileSystemEnvironmentDefaultFiltersRegistry() {
    // Use a TreeSet, filters will be sorted alphabetically.
    _defaultFilters = new TreeSet<String>();
  }

  /**
   * Get all default filters.
   * @return
   */
  public Set<String> getDefaultFilters() {
    return _defaultFilters;
  }

  /**
   * Initialize registry.
   * @return a status giving the initialization result.
   */
  public IStatus initialize() {
    String pluginId = EnvironmentActivator.getInstance().getPluginId();
    MultiStatus resultStatus = new MultiStatus(pluginId, 0, Messages.FileSystemEnvironmentDefaultFiltersRegistry_Initialization_WrapUp, null);
    // Load default filters from extensions.
    IConfigurationElement[] configurationElements =
        ExtensionPointHelper.getConfigurationElements("com.thalesgroup.orchestra.framework.environment", "fileSystemEnvironmentDefaultFilters"); //$NON-NLS-1$ //$NON-NLS-2$
    for (IConfigurationElement configurationElement : configurationElements) {
      String defaultFilterValue = configurationElement.getAttribute("value"); //$NON-NLS-1$
      // Check default filter content.
      if (null == defaultFilterValue || defaultFilterValue.isEmpty()) {
        String pluginIdentifier = configurationElement.getDeclaringExtension().getNamespaceIdentifier();
        resultStatus.add(new Status(IStatus.WARNING, pluginId, MessageFormat.format(
            Messages.FileSystemEnvironmentDefaultFiltersRegistry_Warning_FilterValueNotDefined, pluginIdentifier)));
        continue;
      }
      // Store filter's value.
      boolean addResult = _defaultFilters.add(defaultFilterValue);
      if (addResult) {
        resultStatus.add(new Status(IStatus.OK, pluginId, MessageFormat.format(Messages.FileSystemEnvironmentDefaultFiltersRegistry_OK_FilterLoaded,
            defaultFilterValue)));
      } else {
        resultStatus.add(new Status(IStatus.WARNING, pluginId, MessageFormat.format(
            Messages.FileSystemEnvironmentDefaultFiltersRegistry_Warning_FilterAlreadyLoaded, defaultFilterValue)));
      }
    }
    // No default filter loaded
    if (_defaultFilters.isEmpty()) {
      resultStatus.add(new Status(IStatus.OK, pluginId, Messages.FileSystemEnvironmentDefaultFiltersRegistry_OK_NoDefaultFilterFound));
    }
    return resultStatus;
  }
}
