/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.creation.ui.environment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.ae.creation.ArtefactCreationActivator;
import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;

/**
 * @author s0040806
 */
public class EnvironmentArtefactCreationHandlers {
  private Map<String, IEnvironmentArtefactCreationHandler> _handlerMap = new HashMap<String, IEnvironmentArtefactCreationHandler>();
  private Map<String, Set<String>> _excludedRootTypeMap = new HashMap<String, Set<String>>();
  private static EnvironmentArtefactCreationHandlers __instance;

  /**
   * 
   */
  public static EnvironmentArtefactCreationHandlers getInstance() {
    if (null == __instance) {
      __instance = new EnvironmentArtefactCreationHandlers();
    }
    return __instance;
  }

  private EnvironmentArtefactCreationHandlers() {
    IConfigurationElement[] creationConfigurationElements =
        ExtensionPointHelper.getConfigurationElements("com.thalesgroup.orchestra.framework.ae.creation", "environmentArtefactCreation");

    // Register all contributing environments handlers
    for (IConfigurationElement configurationElement : creationConfigurationElements) {
      String environmentType = configurationElement.getAttribute("environmentId");
      IEnvironmentArtefactCreationHandler handler =
          (IEnvironmentArtefactCreationHandler) ExtensionPointHelper.createInstance(configurationElement, ExtensionPointHelper.ATT_CLASS);
      if (null == handler) {
        String cls = configurationElement.getAttribute(ExtensionPointHelper.ATT_CLASS);
        ArtefactCreationActivator.getDefault().logMessage("Could not instanciate class: " + cls, IStatus.WARNING, null);
      } else {
        _handlerMap.put(environmentType, handler);
      }
    }

    // Build root type exclusion list
    IConfigurationElement[] configurationElements =
        ExtensionPointHelper.getConfigurationElements("com.thalesgroup.orchestra.framework.ae.creation", "environmentArtefactCreationFiltering");

    // For all contributing environments
    for (IConfigurationElement configurationElement : configurationElements) {
      String environmentType = configurationElement.getAttribute("environmentId");
      if (_handlerMap.containsKey(environmentType)) {
        // Read excluded root type list
        Set<String> set = new HashSet<String>();
        for (IConfigurationElement excludedArtefactType : configurationElement.getChildren("excludedRootType")) {
          String name = excludedArtefactType.getAttribute("name");
          set.add(name);
        }
        if (!set.isEmpty()) {
          _excludedRootTypeMap.put(environmentType, set);
        }
      }
    }
  }

  public IEnvironmentArtefactCreationHandler getEnvironmentArtefactCreationHandler(String environmentType_p) {
    return _handlerMap.get(environmentType_p);
  }

  public Set<String> getExcludedRootTypes(String environmentType_p) {
    return _excludedRootTypeMap.get(environmentType_p);
  }

  public Set<String> getAvailableEnvironmentTypes() {
    return _handlerMap.keySet();
  }
}
