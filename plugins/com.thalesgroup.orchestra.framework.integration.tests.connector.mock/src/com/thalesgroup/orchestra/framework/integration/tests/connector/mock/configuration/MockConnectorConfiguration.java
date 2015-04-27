/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests.connector.mock.configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.ice.jni.registry.RegStringValue;
import com.ice.jni.registry.Registry;
import com.ice.jni.registry.RegistryKey;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.integration.tests.connector.mock.MockConnectorActivator;
import com.thalesgroup.orchestra.framework.integration.tests.helpers.IntegrationTestsHelper;
import com.thalesgroup.orchestra.framework.integration.tests.initializer.AbstractConnectorConfiguration;

/**
 * @author S0024585
 */
public class MockConnectorConfiguration extends AbstractConnectorConfiguration {
  private File _connectorIconDestFile;
  private File _connectorInstallationDestFile;
  private File _mockArtifactsDescriptionFile;
  private File _mockAssociationsFile;
  
  @SuppressWarnings("nls")
  @Override
  public IStatus installConfDir() {
    try {
      URL mockAssociationResourceURL =
          FileHelper.getFileFullUrl(MockConnectorActivator.getInstance().getPluginId() + "/resources/ConfDir/Mock_Association.xml");
      _mockAssociationsFile = new File(IntegrationTestsHelper.__integrationTestsBaseConfDirPath + "/Framework/Associations/Mock_Association.xml");
      org.apache.commons.io.FileUtils.copyURLToFile(mockAssociationResourceURL, _mockAssociationsFile);

      URL mockArtefactDescriptionURL =
          FileHelper.getFileFullUrl(MockConnectorActivator.getInstance().getPluginId() + "/resources/ConfDir/artifacts_description_Mock.xml");
      _mockArtifactsDescriptionFile = new File(IntegrationTestsHelper.__integrationTestsBaseConfDirPath + "/Framework/Config/artifacts_description_Mock.xml");
      org.apache.commons.io.FileUtils.copyURLToFile(mockArtefactDescriptionURL, _mockArtifactsDescriptionFile);
    } catch (IOException ioException_p) {
      return new Status(IStatus.ERROR, MockConnectorActivator.getInstance().getPluginId(), "Error when installing confdir !", ioException_p);
    }

    return Status.OK_STATUS;
  }

  /**
   * @param connectorInstallationDirectory_p can be null.
   * @return
   * @throws IOException
   */
  @SuppressWarnings("nls")
  @Override
  public IStatus installConnector() {
    try {
      // Copy .connector file.
      URL connectorInstallationFileURL = FileHelper.getFileFullUrl(MockConnectorActivator.getInstance().getPluginId() + "/connectors/Mock.connector");
      _connectorInstallationDestFile = new File(IntegrationTestsHelper.__integrationTestsBaseInstallationPath + "/orcConfig/Mock.connector");
      org.apache.commons.io.FileUtils.copyURLToFile(connectorInstallationFileURL, _connectorInstallationDestFile);
      // Copy icon file.
      URL connectorIconFileURL = FileHelper.getFileFullUrl(MockConnectorActivator.getInstance().getPluginId() + "/resources/images/Mock.gif");
      _connectorIconDestFile = new File(IntegrationTestsHelper.__integrationTestsBaseInstallationPath + "/images/Mock.gif");
      org.apache.commons.io.FileUtils.copyURLToFile(connectorIconFileURL, _connectorIconDestFile);
      // Fill registry.
      RegistryKey productsKey = Registry.HKEY_LOCAL_MACHINE.createSubKey(_registryKey, ICommonConstants.EMPTY_STRING);
      RegistryKey connectorKey = productsKey.createSubKey("MockConnector", ICommonConstants.EMPTY_STRING);
      // configConnector.
      RegStringValue configConnectorValue = new RegStringValue(connectorKey, "configConnector", _connectorInstallationDestFile.getAbsolutePath());
      connectorKey.setValue(configConnectorValue);
      // iconPath. 
      RegStringValue iconPathValue = new RegStringValue(connectorKey, "iconPath", _connectorIconDestFile.getAbsolutePath());
      connectorKey.setValue(iconPathValue);
      // Category
      final RegStringValue categoryValue = new RegStringValue(connectorKey, "Category", "Mock Connector");
      connectorKey.setValue(categoryValue);
      // Build Number
      final RegStringValue buildNumberValue = new RegStringValue(connectorKey, "BuildNumber", "0001");
      connectorKey.setValue(buildNumberValue);
      // Product Version
      final RegStringValue productVersionValue = new RegStringValue(connectorKey, "ProductVersion", "1.0.0");
      connectorKey.setValue(productVersionValue);
      // Build Version
      final RegStringValue buildVersionValue = new RegStringValue(connectorKey, "BuildVersion", "1.0.0.0001");
      connectorKey.setValue(buildVersionValue);
    } catch (Exception exception_p) {
      return new Status(IStatus.ERROR, 
    		  			MockConnectorActivator.getInstance().getPluginId(), 
    		  			"An error occurred during Mock connector installation !",
    		  			exception_p);
    }
    return Status.OK_STATUS;
  }

  @Override
  public IStatus uninstallConfDir() {
    _mockAssociationsFile.delete();
    _mockArtifactsDescriptionFile.delete();
    return Status.OK_STATUS;
  }

  @SuppressWarnings("nls")
  @Override
  public IStatus uninstallConnector() {
    _connectorInstallationDestFile.delete();
    _connectorIconDestFile.delete();
    try {
      // Remove Mock connector's key in registry.
      RegistryKey productsKey = Registry.HKEY_LOCAL_MACHINE.openSubKey(_registryKey);
      productsKey.deleteSubKey("MockConnector");
    } catch (Exception exception_p) {
      return new Status(IStatus.ERROR, MockConnectorActivator.getInstance().getPluginId(), 
    		  			"An error occurred during Mock connector uninstallation !",
    		  			exception_p);
    }
    return Status.OK_STATUS;
  }
}
