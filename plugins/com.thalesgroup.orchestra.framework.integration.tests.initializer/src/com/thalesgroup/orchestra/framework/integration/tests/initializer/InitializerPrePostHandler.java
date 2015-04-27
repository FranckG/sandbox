/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests.initializer;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import com.ice.jni.registry.NoSuchKeyException;
import com.ice.jni.registry.RegStringValue;
import com.ice.jni.registry.Registry;
import com.ice.jni.registry.RegistryException;
import com.ice.jni.registry.RegistryKey;
import com.thalesgroup.orchestra.framework.integration.tests.application.handler.IPrePostTestHandler;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
public class InitializerPrePostHandler implements IPrePostTestHandler {

  /**
   * @see com.thalesgroup.orchestra.framework.integration.tests.application.handler.IPrePostTestHandler#postAction()
   */
  
  public void postAction() {
    try {
      RegistryKey orchestraFrameworkKey = Registry.HKEY_LOCAL_MACHINE.openSubKey("SOFTWARE\\Wow6432Node\\Thales\\EPM\\Orchestra\\Products\\OrchestraFramework");
      recursiveDelete(orchestraFrameworkKey);
      Registry.HKEY_LOCAL_MACHINE.openSubKey("SOFTWARE\\Wow6432Node\\Thales\\EPM\\Orchestra\\Products").deleteSubKey("OrchestraFramework");
    } catch (Exception exception_p) {
      System.out.println(exception_p);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.integration.tests.application.handler.IPrePostTestHandler#preAction()
   */
  public void preAction() {
    try {
      URL installLocationURL = Platform.getInstallLocation().getURL();
      File installLocation = new File(installLocationURL.getPath());
      IPath path = new Path(installLocation.getAbsolutePath());
      String installationDriveLetter = path.getDevice();

      RegistryKey productsKey = Registry.HKEY_LOCAL_MACHINE.createSubKey("SOFTWARE\\Wow6432Node\\Thales\\EPM\\Orchestra\\Products", "");

      // Populate OrchestraFramework node.
      RegistryKey orchestraFrameworkKey = productsKey.createSubKey("OrchestraFramework", "");

      RegStringValue artifactsValue = new RegStringValue(orchestraFrameworkKey, "ArtefactPath", installationDriveLetter + "\\ORCHESTRA_DATA\\Artifacts\\");
      orchestraFrameworkKey.setValue(artifactsValue);

      RegStringValue confdirValue =
          new RegStringValue(orchestraFrameworkKey, "ConfigurationDirectoryPath", installationDriveLetter + "\\ORCHESTRA_DATA\\ConfDir\\");
      orchestraFrameworkKey.setValue(confdirValue);

      RegStringValue installLocationValue = new RegStringValue(orchestraFrameworkKey, "InstallLocation", installationDriveLetter + "\\OrchestraFramework");
      orchestraFrameworkKey.setValue(installLocationValue);

      // Category
      RegStringValue categoryValue = new RegStringValue(orchestraFrameworkKey, "Category", "Data Management");
      orchestraFrameworkKey.setValue(categoryValue);
      
      // Build Number
      RegStringValue buildNumberValue = new RegStringValue(orchestraFrameworkKey, "BuildNumber", "0001");
      orchestraFrameworkKey.setValue(buildNumberValue);
      
      // Build Version
      RegStringValue buildVersionValue = new RegStringValue(orchestraFrameworkKey, "BuildVersion", "1.0.0.0001");
      orchestraFrameworkKey.setValue(buildVersionValue);
      
      // Product Version
      RegStringValue productVersionValue = new RegStringValue(orchestraFrameworkKey, "ProductVersion", "1.0.0");
      orchestraFrameworkKey.setValue(productVersionValue);
      
      // Populate ConnectorCommands node.
      RegistryKey connectorCommandsKey = orchestraFrameworkKey.createSubKey("ConnectorCommands", "");
      RegStringValue configConnectorCommandsValue = new RegStringValue(connectorCommandsKey, 
        		  		     										   "configConnector", 
        		  		     										   installationDriveLetter + "\\OrchestraFramework\\orcConfig\\FrameworkCommands.connector");
      connectorCommandsKey.setValue(configConnectorCommandsValue);
      
      // Category
      categoryValue = new RegStringValue(connectorCommandsKey, "Category", "Data Management");
      orchestraFrameworkKey.setValue(categoryValue);
      
      // Build Number
      buildNumberValue = new RegStringValue(connectorCommandsKey, "BuildNumber", "0001");
      orchestraFrameworkKey.setValue(buildNumberValue);
      
      // Build Version
      buildVersionValue = new RegStringValue(connectorCommandsKey, "BuildVersion", "1.0.0.0001");
      orchestraFrameworkKey.setValue(buildVersionValue);
      
      // Product Version
      productVersionValue = new RegStringValue(connectorCommandsKey, "ProductVersion", "1.0.0");
      orchestraFrameworkKey.setValue(productVersionValue);
      	
      
      // Populate ConnectorMigration node.
      RegistryKey connectorMigrationKey = orchestraFrameworkKey.createSubKey("ConnectorMigration", "");
      RegStringValue configMigrationConnectorValue =  new RegStringValue(connectorMigrationKey, 
    		  															 "configConnector", 
    		  															 installationDriveLetter + "\\OrchestraFramework" + "\\orcConfig\\Migration.connector");
      connectorMigrationKey.setValue(configMigrationConnectorValue);
      
      // Category
      categoryValue = new RegStringValue(connectorMigrationKey, "Category", "Data Management");
      orchestraFrameworkKey.setValue(categoryValue);
      
      // Build Number
      buildNumberValue = new RegStringValue(connectorMigrationKey, "BuildNumber", "0001");
      orchestraFrameworkKey.setValue(buildNumberValue);
      
      // Build Version
      buildVersionValue = new RegStringValue(connectorMigrationKey, "BuildVersion", "1.0.0.0001");
      orchestraFrameworkKey.setValue(buildVersionValue);
      
      // Product Version
      productVersionValue = new RegStringValue(connectorMigrationKey, "ProductVersion", "1.0.0");
      orchestraFrameworkKey.setValue(productVersionValue);
      
    } catch (Exception exception_p) {
      System.out.println("ERROR during initialization:"+exception_p);
    }
  }

  /**
   * Recursively deletes every keys under the given start key (start key IS NOT DELETED).
   * @param startKey_p the key from which to start deletion.
   * @throws NoSuchKeyException
   * @throws RegistryException
   */
  public static void recursiveDelete(RegistryKey startKey_p) throws NoSuchKeyException, RegistryException {
    // Go through sub keys of given start key.
    Enumeration<String> subKeyElements = startKey_p.keyElements();
    while (subKeyElements.hasMoreElements()) {
      String subKeyName = subKeyElements.nextElement();
      RegistryKey subKey = startKey_p.openSubKey(subKeyName);
      recursiveDelete(subKey);
      startKey_p.deleteSubKey(subKeyName);
      // For subKeyElements to be consistent after deletion, update it.
      subKeyElements = startKey_p.keyElements();
    }

  }
}