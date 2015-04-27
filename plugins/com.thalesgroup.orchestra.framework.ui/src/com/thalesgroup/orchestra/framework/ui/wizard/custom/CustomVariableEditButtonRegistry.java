/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.wizard.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;

import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;
import com.thalesgroup.orchestra.framework.root.ui.custom.ICustomMonoValueVariableEditButton;
import com.thalesgroup.orchestra.framework.root.ui.custom.ICustomMultipleValuesVariableEditButton;

/**
 * @author s0040806
 */
public class CustomVariableEditButtonRegistry {

  /**
   * Variable path to button list table
   */
  private HashMap<String, List<ICustomMonoValueVariableEditButton>> _monoVariableMap;
  private HashMap<String, List<ICustomMultipleValuesVariableEditButton>> _multiVariableMap;

  private static CustomVariableEditButtonRegistry __instance;

  private CustomVariableEditButtonRegistry() {
    _monoVariableMap = new HashMap<String, List<ICustomMonoValueVariableEditButton>>();
    _multiVariableMap = new HashMap<String, List<ICustomMultipleValuesVariableEditButton>>();
  }

  public static CustomVariableEditButtonRegistry getInstance() {
    if (null == __instance) {
      __instance = new CustomVariableEditButtonRegistry();
    }
    return __instance;
  }

  /**
   * Initialise variable edit button registry
   */
  public void initialize() {
    IConfigurationElement[] configurationElements =
        ExtensionPointHelper.getConfigurationElements("com.thalesgroup.orchestra.framework.root.ui", "customVariableEditButton");

    // For all variables
    for (IConfigurationElement configurationElement : configurationElements) {
      String variablePath = configurationElement.getAttribute("path");

      String variableType = configurationElement.getName();
      if (variableType.equals("monoValueVariable")) {
        // Mono value variables
        List<ICustomMonoValueVariableEditButton> list = _monoVariableMap.get(variablePath);
        if (null == list) {
          list = new ArrayList<ICustomMonoValueVariableEditButton>();
          _monoVariableMap.put(variablePath, list);
        }
        for (IConfigurationElement child : configurationElement.getChildren()) {
          ICustomMonoValueVariableEditButton button =
              (ICustomMonoValueVariableEditButton) ExtensionPointHelper.createInstance(child, ExtensionPointHelper.ATT_CLASS);
          list.add(button);
        }
      } else {
        // Multiple values variables
        List<ICustomMultipleValuesVariableEditButton> list = _multiVariableMap.get(variablePath);
        if (null == list) {
          list = new ArrayList<ICustomMultipleValuesVariableEditButton>();
          _multiVariableMap.put(variablePath, list);
        }
        for (IConfigurationElement child : configurationElement.getChildren()) {
          ICustomMultipleValuesVariableEditButton button =
              (ICustomMultipleValuesVariableEditButton) ExtensionPointHelper.createInstance(child, ExtensionPointHelper.ATT_CLASS);
          list.add(button);
        }
      }
    }
  }

  /**
   * Get list of custom buttons for mono value variable
   * @param variablePath_p Variable path
   * @return List of custom buttons
   */
  public List<ICustomMonoValueVariableEditButton> getButtonListForMonoValueVariable(String variablePath_p) {
    return _monoVariableMap.get(variablePath_p);
  }

  /**
   * Get list of custom buttons for multiple values variable
   * @param variablePath_p Variable path
   * @return List of custom buttons
   */
  public List<ICustomMultipleValuesVariableEditButton> getButtonListForMultipleValuesVariable(String variablePath_p) {
    return _multiVariableMap.get(variablePath_p);
  }
}
