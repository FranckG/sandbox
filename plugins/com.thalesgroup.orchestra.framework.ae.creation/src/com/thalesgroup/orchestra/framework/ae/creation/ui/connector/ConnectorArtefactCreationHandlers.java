/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.creation.ui.connector;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.ae.creation.ArtefactCreationActivator;
import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;

/**
 * @author s0040806
 */
public class ConnectorArtefactCreationHandlers {
  // Artefact type to handler map
  private Map<String, IConnectorArtefactCreationHandler> _handlerMap = new HashMap<String, IConnectorArtefactCreationHandler>();
  private static ConnectorArtefactCreationHandlers __instance;

  public static ConnectorArtefactCreationHandlers getInstance() {
    if (null == __instance) {
      __instance = new ConnectorArtefactCreationHandlers();
    }
    return __instance;
  }

  private ConnectorArtefactCreationHandlers() {
    IConfigurationElement[] configurationElements =
        ExtensionPointHelper.getConfigurationElements("com.thalesgroup.orchestra.framework.ae.creation", "connectorArtefactCreation");

    // For all contributing connectors
    for (IConfigurationElement configurationElement : configurationElements) {
      IConnectorArtefactCreationHandler handler =
          (IConnectorArtefactCreationHandler) ExtensionPointHelper.createInstance(configurationElement, ExtensionPointHelper.ATT_CLASS);
      if (null == handler) {
        String cls = configurationElement.getAttribute(ExtensionPointHelper.ATT_CLASS);
        ArtefactCreationActivator.getDefault().logMessage("Could not instanciate class:" + cls, IStatus.WARNING, null);
      } else {
        for (IConfigurationElement artefactType : configurationElement.getChildren("rootType")) {
          String name = artefactType.getAttribute("name");
          _handlerMap.put(name, handler);
        }
      }
    }
  }

  public IConnectorArtefactCreationHandler getConnectorArtefactCreationHandler(String artefactType_p) {
    return _handlerMap.get(artefactType_p);
  }
}
