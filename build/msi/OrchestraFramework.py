from msigenlib.objects import *
import subprocess
import os

actions = [
  #--------------------#
  # PRE-BUILD COMMANDS #
  #--------------------#
  CleanFolder(r"${WORKSPACE}\package"),
  CleanFolder(r"${WORKSPACE}\dist"),
  CleanFolder(r"${WORKSPACE}\temp"),
    
  # framework product
  ExpandArchive(r"\\tc1\msideliver\OrchestraFramework\latest-v${ORCHESTRA_VERSION}\OrchestraFramework-*.zip", r"${WORKSPACE}\temp"),

  # File tree for the contributions
  ExpandArchive(r"${WORKSPACE}\Delivery\lib\contributionsFileTree.zip", r"${WORKSPACE}\package\shared"),  

  CopyFolder(r"${WORKSPACE}\temp\x86", r"${WORKSPACE}\package"),

  #framework product invariant part 32, 64 bits
  CopyFolder(r"${WORKSPACE}\temp", r"${WORKSPACE}\package", exclude="x86*"),
  # doc
  CopyFile(r"\\nas-ewd.orchestra\integ\Deliveries\delivery_Documentation\OrchestraFramework\v${ORCHESTRA_VERSION}\SUM_OrchestraFramework.pdf", r"${WORKSPACE}\package\doc"),
  # Protocol
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.Protocol\bin\Release\Orchestra Protocol.exe", r"${WORKSPACE}\package\lib"),
  # CRC32
  CopyFile(r"${WORKSPACE}\ConnectorCommon\CRC32\release\CRC.dll", r"${WORKSPACE}\package\lib"),
  # ManagedWinApi
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\ManagedWinApi\ManagedWinapi.dll", r"${WORKSPACE}\package\lib"),
  # Orchestra Framework Artefact
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.Artefact\bin\Release\Orchestra.Framework.Artefact.dll", r"${WORKSPACE}\package\lib"),
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.Artefact\bin\Release\policy.1.3.Orchestra.Framework.Artefact.dll", r"${WORKSPACE}\package\lib"),
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.Artefact\publisher 1.3.Artefact.config", r"${WORKSPACE}\package\lib"),
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.Artefact\bin\Release\policy.1.4.Orchestra.Framework.Artefact.dll", r"${WORKSPACE}\package\lib"),
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.Artefact\publisher 1.4.Artefact.config", r"${WORKSPACE}\package\lib"),
  # Orchestra Framework Utilities
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.Utilities\bin\Release\Orchestra.Framework.Utilities.dll", r"${WORKSPACE}\package\lib"),
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.Utilities\bin\Release\policy.1.3.Orchestra.Framework.Utilities.dll", r"${WORKSPACE}\package\lib"),
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.Utilities\publisher 1.3.Utilities.config", r"${WORKSPACE}\package\lib"),
  # Orchestra Framework VariableManager
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.VariableManager\bin\Release\Orchestra.Framework.VariableManager.dll", r"${WORKSPACE}\package\lib"),
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.VariableManager\bin\Release\Orchestra.Framework.VariableManager.XmlSerializers.dll", r"${WORKSPACE}\package\lib"),
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.VariableManager\bin\Release\policy.1.2.Orchestra.Framework.VariableManager.dll", r"${WORKSPACE}\package\lib"),
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.VariableManager\bin\Release\policy.1.3.Orchestra.Framework.VariableManager.dll", r"${WORKSPACE}\package\lib"),
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.VariableManager\publisher 1.2.VariableManager.config", r"${WORKSPACE}\package\lib"),
  CopyFile(r"${WORKSPACE}\ConnectorCommon\Orchestra.Framework\Orchestra.Framework.VariableManager\publisher 1.3.VariableManager.config", r"${WORKSPACE}\package\lib"),
  # dtd
  CopyFolder(r"${WORKSPACE}\Delivery\dtd", r"${WORKSPACE}\package\dtd"),
  # xsd
  CopyFolder(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework.exchange\model\xsd", r"${WORKSPACE}\package\xsd"),    
  # orcConfig
  CopyFile(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework.connector.image\connectors\Image.connector", r"${WORKSPACE}\package\orcConfig"),
  CopyFile(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework.connector.text\connectors\Text.connector", r"${WORKSPACE}\package\orcConfig"),
  CopyFile(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework.connector.migration\connectors\Migration.connector", r"${WORKSPACE}\package\orcConfig"),
  CopyFile(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework.connector.commands\connectors\FrameworkCommands.connector", r"${WORKSPACE}\package\orcConfig"),
  # resources
  CopyFolder(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework.connector.image\resources\images", r"${WORKSPACE}\package\resources\images"),
  CopyFolder(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework.connector.image\resources\types", r"${WORKSPACE}\package\resources\types"),
  CopyFolder(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework.connector.text\resources\images", r"${WORKSPACE}\package\resources\images"),
  CopyFolder(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework.connector.text\resources\types", r"${WORKSPACE}\package\resources\types"),
  CopyFile(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework.ae\icons\views\menu\fold_project.gif",r"${WORKSPACE}\package\resources\images"),
  # config.ini
  CopyFile(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework\config.ini", r"${WORKSPACE}\package\eclipse\configuration"),
  # MimeTypes.properties
  CopyFile(r"${WORKSPACE}\Delivery\lib\MimeTypes.properties", r"${WORKSPACE}\package\lib"),
  # OrchestraFrameworkCLI.exe
  CopyFile(r"${WORKSPACE}\Delivery\lib\OrchestraFrameworkCLI.exe", r"${WORKSPACE}\package\lib"),
  # extlocations
  CopyFolder(r"\\tc1\pde\extloc\shared\jetty-8.1.14\eclipse\plugins", r"${WORKSPACE}\package\shared\puci\eclipse\plugins", exclude="*.svn*"),
  CopyFolder(r"\\tc1\pde\extloc\shared\google\eclipse\plugins", r"${WORKSPACE}\package\shared\lib\eclipse\plugins", exclude="*.svn*"),
  CopyFile(r"\\tc1\pde\extloc\platform-3.8\emf-xsd-SDK-2.8.3\eclipse\plugins\org.eclipse.emf.common_2.8.0.v20130125-0546.jar", r"${WORKSPACE}\package\shared\puci\eclipse\plugins"),
  # links
  CopyFile(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework\links\*.link", r"${WORKSPACE}\package\eclipse\links"),
  # paths
  CopyFile(r"${WORKSPACE}\plugins\com.thalesgroup.orchestra.framework\paths\*.path", r"${WORKSPACE}\package\lib"),
  # move notifier
  CopyFile(r"${WORKSPACE}\package\shared\ae.notifier\eclipse\plugins\*.jar", r"${WORKSPACE}\package\eclipse\plugins"),
  CleanFolder(r"${WORKSPACE}\package\shared\ae.notifier"),

  #----------------------#
  # MANDATORY PROPERTIES #
  #----------------------#
  SetVersion("${VERSION}"),
  SetProperty("ProductName", "OrchestraFramework"),
  SetProperty("BuildNumber", "${TIMESTAMP}"),
  SetProperty("Category", "Data Management"),
  
  #-----------------#
  # FILES & FOLDERS #
  #-----------------#
  AddFiles(r"${WORKSPACE}\package", "APPDIR", exclude="lib", accept_folders=True),
  AddFile(r"${WORKSPACE}\package\lib\MimeTypes.properties", r"APPDIR\lib"),
  AddFile(r"${WORKSPACE}\package\lib\OrchestraDoctor.path", r"APPDIR\lib"),

  # Compute MD5 for framwork.exe. This value is put into registry DOD\layer4
  ComputeMd5(r"${WORKSPACE}\temp\x86\eclipse\framework.exe", r"MD5"),
  SetProperty("MD5", "${MD5}"),

  #-----------#
  # SHORTCUTS #
  #-----------#
  NewShortcut(name="Orchestra Framework", 
              dir="SHORTCUTDIR", 
              target=r"APPDIR\eclipse\Framework.exe"),
  NewShortcut(name="Orchestra Framework User Manual", 
              dir="SHORTCUTDIR", 
              target=r"APPDIR\doc\SUM_OrchestraFramework.pdf")
]