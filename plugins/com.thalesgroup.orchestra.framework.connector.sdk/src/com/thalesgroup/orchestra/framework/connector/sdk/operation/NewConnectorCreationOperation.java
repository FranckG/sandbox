/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.operation;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.connector.sdk.ConnectorProjectNature;
import com.thalesgroup.orchestra.framework.connector.sdk.SdkWizardsActivator;
import com.thalesgroup.orchestra.framework.connector.sdk.helpers.WindowsRegistryFileHelper;
import com.thalesgroup.orchestra.framework.connector.sdk.helpers.XMLContentsHelper;

/**
 * @author T0052089
 */
public class NewConnectorCreationOperation extends WorkspaceModifyOperation {
  /**
   * Connector ID for remote connector (ECF).
   */
  public static final String ECF_DEFAULT_CONNECTOR_ID = "Ecf_DefaultConnector"; //$NON-NLS-1$
  /**
   * Template icon location.
   */
  public static final String FILES_LOCATION_ICONS = "/deployment/connector/resources/images/"; //$NON-NLS-1$
  /**
   * SDK plug-in ID.
   */
  public static final String SDK_PLUGIN_ID = SdkWizardsActivator.getDefault().getPluginId();
  /**
   * Template source locations for each type of connector.
   */
  public static final String SOURCES_LOCATION_IN_PROCESS_CONNECTOR = "/sources/InProcessConnector/"; //$NON-NLS-1$
  public static final String SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_APPLICATION = "/sources/OutOfProcessConnector/Application/"; //$NON-NLS-1$
  public static final String SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_SIMPLE = "/sources/OutOfProcessConnector/Simple/"; //$NON-NLS-1$
  /**
   * Remote connector application registration type.
   */
  public static final String REGISTER_ECF_CONNECTOR_JAVA_CODE = "registerConnectorForType(\"{0}\");\n"; //$NON-NLS-1$
  /**
   * Given parameters to create the connector project.
   */
  private final ConnectorProjectParameters _connectorProjectParameters;
  /**
   * Code formatter used to format Java sources.
   */
  private final CodeFormatter _codeFormatter;
  /**
   * Created connector project.
   */
  private IProject _createdConnectorProject;

  /**
   * Constructor.
   * @param connectorProjectParameters_p
   */
  @SuppressWarnings("unchecked")
  public NewConnectorCreationOperation(ConnectorProjectParameters connectorProjectParameters_p) {
    _connectorProjectParameters = connectorProjectParameters_p;
    // Initialize code formatter (format java sources in version 1.5 (mainly for generics)).
    Hashtable codeFormatterOptions = JavaCore.getOptions();
    codeFormatterOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);
    codeFormatterOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
    codeFormatterOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_5);
    _codeFormatter = ToolFactory.createCodeFormatter(codeFormatterOptions);
  }

  /**
   * Create artefact description file (artifacts_description ... .xml).
   * @throws Exception
   */
  protected void createArtefactDescriptionFile() throws Exception {
    // Load source file content.
    String sourceFileContent = FileHelper.readFile(XMLContentsHelper.generateArtefactDescriptionFilePath(SDK_PLUGIN_ID, "Template")); //$NON-NLS-1$
    String resultString = XMLContentsHelper.createArtefactDescriptionFileContent(sourceFileContent, _connectorProjectParameters._typeDefinitions);
    // Write new file.
    String destinationFilePath =
        XMLContentsHelper.generateArtefactDescriptionFilePath(_createdConnectorProject.getName(), _connectorProjectParameters._connectorName);
    FileHelper.writeFile(destinationFilePath, true, resultString);
  }

  /**
   * Create association file (...ConnectorAssociationFile.xml).
   * @throws Exception
   */
  protected void createConnectorAssociationFile() throws Exception {
    // Precondition : no association defined -> no need to create this file.
    if (0 == _connectorProjectParameters._typeDefinitions.length) {
      return;
    }
    // Load source file content.
    String sourceFileContent = FileHelper.readFile(XMLContentsHelper.generateConnectorAssociationFilePath(SDK_PLUGIN_ID, "Template")); //$NON-NLS-1$

    String resultString = XMLContentsHelper.createTypeAssociationFileContent(sourceFileContent, _connectorProjectParameters._typeDefinitions);

    // Write new file.
    String destinationFilePath =
        XMLContentsHelper.generateConnectorAssociationFilePath(_createdConnectorProject.getName(), _connectorProjectParameters._connectorName);
    FileHelper.writeFile(destinationFilePath, true, resultString);
  }

  /**
   * Create connector declaration file (.connector file).
   * @throws Exception
   */
  protected void createConnectorDeclarationFile() throws Exception {
    // Load source file content.
    String sourceFileContent = FileHelper.readFile(XMLContentsHelper.generateConnectorDeclarationFilePath(SDK_PLUGIN_ID, "Template")); //$NON-NLS-1$

    // If it's an out of process connector, set connector id to "Ecf_DefaultConnector".
    String connectorId = _connectorProjectParameters._connectorId;
    if (_connectorProjectParameters._isOutOfProcess) {
      connectorId = ECF_DEFAULT_CONNECTOR_ID;
    }

    String resultString = XMLContentsHelper.createConnectorDeclarationFileContent(sourceFileContent, connectorId, _connectorProjectParameters._typeDefinitions);

    // Write new file.
    String destinationFilePath =
        XMLContentsHelper.generateConnectorDeclarationFilePath(_createdConnectorProject.getName(), _connectorProjectParameters._connectorName);
    FileHelper.writeFile(destinationFilePath, true, resultString);
  }

  /**
   * Create new connector project.
   * @throws CoreException
   */
  protected void createConnectorProject() throws CoreException {
    _createdConnectorProject = ResourcesPlugin.getWorkspace().getRoot().getProject(_connectorProjectParameters._connectorId);
    IPath locationPath = new Path(_connectorProjectParameters._connectorProjectLocationPath);

    IProjectDescription desc = _createdConnectorProject.getWorkspace().newProjectDescription(_createdConnectorProject.getName());
    if (Platform.getLocation().equals(locationPath)) {
      locationPath = null;
    }
    desc.setLocation(locationPath);
    _createdConnectorProject.create(desc, null);
    _createdConnectorProject.open(null);

    // Add "PluginNature" and "javanature" to project's description.
    ArrayList<String> projectDescriptionNatureIds = new ArrayList<String>(Arrays.asList(desc.getNatureIds()));
    projectDescriptionNatureIds.add("org.eclipse.pde.PluginNature"); //$NON-NLS-1$
    projectDescriptionNatureIds.add(JavaCore.NATURE_ID);
    projectDescriptionNatureIds.add(ConnectorProjectNature.CONNECTOR_PROJECT_NATURE_ID);
    desc.setNatureIds(projectDescriptionNatureIds.toArray(new String[0]));
    _createdConnectorProject.setDescription(desc, null);
  }

  /**
   * Create in-process connector sources.
   */
  protected void createInProcessConnectorSources() {
    // Create base plugin files.
    createSourceFileInNewProject(SOURCES_LOCATION_IN_PROCESS_CONNECTOR + "classpath", "/.classpath", false); //$NON-NLS-1$ //$NON-NLS-2$
    createSourceFileInNewProject(SOURCES_LOCATION_IN_PROCESS_CONNECTOR + "build.properties", "/build.properties", false); //$NON-NLS-1$ //$NON-NLS-2$
    createSourceFileInNewProject(SOURCES_LOCATION_IN_PROCESS_CONNECTOR + "META-INF/MANIFEST.MF", "/META-INF/MANIFEST.MF", false); //$NON-NLS-1$ //$NON-NLS-2$
    createSourceFileInNewProject(SOURCES_LOCATION_IN_PROCESS_CONNECTOR + "plugin.xml", "/plugin.xml", false); //$NON-NLS-1$ //$NON-NLS-2$

    // Create java file under its package.
    String connectorBasePackagePath = _connectorProjectParameters._connectorId.replace(".", "/"); //$NON-NLS-1$ //$NON-NLS-2$
    String connectorSrcDestinationPath =
        MessageFormat.format("/src/{0}/impl/{1}Connector.java", connectorBasePackagePath, _connectorProjectParameters._connectorName); //$NON-NLS-1$
    createSourceFileInNewProject(SOURCES_LOCATION_IN_PROCESS_CONNECTOR + "src/TemplateConnector.java", connectorSrcDestinationPath, true); //$NON-NLS-1$

  }

  /**
   * Create out of process (ECF) connector sources (with application and product).
   */
  protected void createOutOfProcessConnectorApplicationSources() {
    // Create base plugin files.
    createSourceFileInNewProject(SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_APPLICATION + "classpath", "/.classpath", false); //$NON-NLS-1$ //$NON-NLS-2$
    createSourceFileInNewProject(SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_APPLICATION + "build.properties", "/build.properties", false); //$NON-NLS-1$ //$NON-NLS-2$
    createSourceFileInNewProject(SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_APPLICATION + "META-INF/MANIFEST.MF", "/META-INF/MANIFEST.MF", false); //$NON-NLS-1$ //$NON-NLS-2$
    createSourceFileInNewProject(SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_APPLICATION + "plugin.xml", "/plugin.xml", false); //$NON-NLS-1$ //$NON-NLS-2$
    createSourceFileInNewProject(SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_APPLICATION + "RemoteConnector.product", //$NON-NLS-1$
        "/" + _connectorProjectParameters._connectorName + "RemoteConnector.product", false); //$NON-NLS-1$ //$NON-NLS-2$

    // Create java files under their package.
    String connectorBasePackagePath = _connectorProjectParameters._connectorId.replace(".", "/"); //$NON-NLS-1$ //$NON-NLS-2$
    String connectorSrcDestinationPath =
        MessageFormat.format("/src/{0}/impl/{1}RemoteConnector.java", connectorBasePackagePath, _connectorProjectParameters._connectorName); //$NON-NLS-1$
    createSourceFileInNewProject(SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_APPLICATION + "src/TemplateRemoteConnector.java", connectorSrcDestinationPath, true); //$NON-NLS-1$

    // Generate application customized code.
    String applicationCustomizedCode = generateCustomizedCodeForOutOfProcessConnectorApplication();
    String applicationSrcDestinationPath =
        MessageFormat.format("/src/{0}/application/{1}Application.java", connectorBasePackagePath, _connectorProjectParameters._connectorName); //$NON-NLS-1$
    createSourceFileInNewProject(
        SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_APPLICATION + "src/TemplateApplication.java", applicationSrcDestinationPath, true, applicationCustomizedCode); //$NON-NLS-1$

  }

  /**
   * Create out of process (ECF) connector sources.
   */
  protected void createOutOfProcessConnectorSimpleSources() {
    // Create base plugin files.
    createSourceFileInNewProject(SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_SIMPLE + "classpath", "/.classpath", false); //$NON-NLS-1$ //$NON-NLS-2$
    createSourceFileInNewProject(SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_SIMPLE + "build.properties", "/build.properties", false); //$NON-NLS-1$ //$NON-NLS-2$
    createSourceFileInNewProject(SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_SIMPLE + "META-INF/MANIFEST.MF", "/META-INF/MANIFEST.MF", false); //$NON-NLS-1$ //$NON-NLS-2$

    // Create java file under its package.
    String connectorBasePackagePath = _connectorProjectParameters._connectorId.replace(".", "/"); //$NON-NLS-1$ //$NON-NLS-2$
    String destinationPath =
        MessageFormat.format("/src/{0}/impl/{1}RemoteConnector.java", connectorBasePackagePath, _connectorProjectParameters._connectorName); //$NON-NLS-1$
    createSourceFileInNewProject(SOURCES_LOCATION_OUT_OF_PROCESS_CONNECTOR_SIMPLE + "src/TemplateRemoteConnector.java", destinationPath, true); //$NON-NLS-1$
  }

  /**
   * The following operations are done:
   * <ol>
   * <li>Template source file is read from the SDK plug-in,</li>
   * <li>Substitution is done on template content ({0} is replaced by connectorID, {1} is replaced by connectorName, for other {x}, values are taken in
   * messageFormatArguments_p)</li>
   * <li>If asked, a Java formatting is done,</li>
   * <li>File content is written in the created project.</li>
   * </ol>
   * @param sourceFilePath
   * @param destinationFilePath
   * @param doJavaFormat_p
   * @param messageFormatArguments_p
   */
  protected void createSourceFileInNewProject(String sourceFilePath, String destinationFilePath, boolean doJavaFormat_p, Object... messageFormatArguments_p) {
    // Load source file content.
    String sourceFileContent = FileHelper.readFile(SDK_PLUGIN_ID + sourceFilePath);
    // Arguments management for MessageFormat.format call.
    Object[] messageFormatArguments = new Object[2 + messageFormatArguments_p.length];
    messageFormatArguments[0] = _connectorProjectParameters._connectorId;
    messageFormatArguments[1] = _connectorProjectParameters._connectorName;
    System.arraycopy(messageFormatArguments_p, 0, messageFormatArguments, 2, messageFormatArguments_p.length);
    // Substitution.
    String fileContent = MessageFormat.format(sourceFileContent, messageFormatArguments);
    // Format file content is needed.
    if (doJavaFormat_p) {
      try {
        fileContent = javaSourceFormat(fileContent);
      } catch (Exception exception_p) {
        // A problem occurred, log it. File content will stay unformatted.
        IStatus formatProblem = new Status(IStatus.WARNING, SDK_PLUGIN_ID, Messages.NewConnectorCreationOperation_Warning_FormatJavaSources, exception_p);
        SdkWizardsActivator.getDefault().getLog().log(formatProblem);
      }
    }
    // Write new file.
    FileHelper.writeFile(_createdConnectorProject.getName() + destinationFilePath, true, fileContent);
  }

  /**
   * Format given java source code (formatter configuration of Eclipse preferences is used).
   * @param javaUnformattedContent_p content to format.
   * @return formatted java content.
   * @throws Exception if an error occurred.
   */
  protected String javaSourceFormat(String javaUnformattedContent_p) throws Exception {
    // Format java code.
    TextEdit formatResult = _codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT, javaUnformattedContent_p, 0, javaUnformattedContent_p.length(), 0, null);
    if (null == formatResult) {
      // Stop formatting if an error occurred.
      throw new Exception(Messages.NewConnectorCreationOperation_ExceptionMessage_CantFormat);
    }
    // Create a text document with original content.
    IDocument document = new Document(javaUnformattedContent_p);
    // Apply format results to the document.
    formatResult.apply(document, TextEdit.NONE);
    // Return document's content (formatted content).
    return document.get();
  }

  /**
   * Create type icon files in the new connector project. One icon file is created for each type (with the same content).
   */
  protected void createTypeIconFiles() {
    for (TypeDefinition typeDefinition : _connectorProjectParameters._typeDefinitions) {
      String sourceFileRelativePath = SDK_PLUGIN_ID + FILES_LOCATION_ICONS + "template.gif"; //$NON-NLS-1$
      String targetFileRelativePath = _createdConnectorProject.getName() + FILES_LOCATION_ICONS + typeDefinition.getTypeName() + ".gif"; //$NON-NLS-1$
      FileHelper.copyFile(sourceFileRelativePath, targetFileRelativePath);
    }
  }

  /**
   * Create .reg files in the new connector project. Two files are created, one for HKLM, one for HKCU.
   * @throws UnsupportedEncodingException
   */
  protected void createWindowsRegistryFiles() throws UnsupportedEncodingException {
    // Load HKLM template file.
    byte[] hklmTemplateFileByteContent = FileHelper.readRawFile(WindowsRegistryFileHelper.HKLM_TEMPLATE_FILE_PATH);
    String hklmTemplateFileContent = new String(hklmTemplateFileByteContent, WindowsRegistryFileHelper.REG_FILES_CHARSET_NAME);
    // Do substitution (HKLM file is filled with a fake installation path).
    String hklmResultFileContent = MessageFormat.format(hklmTemplateFileContent, "path to connector installation", _connectorProjectParameters._connectorName); //$NON-NLS-1$
    // Write new file in the created project.
    FileHelper.writeFile(WindowsRegistryFileHelper.generateHklmFilePath(_createdConnectorProject.getName(), _connectorProjectParameters._connectorName), true,
        hklmResultFileContent.getBytes(WindowsRegistryFileHelper.REG_FILES_CHARSET_NAME));

    // Get "connector" folder absolute url location.
    URL connectorFolderUrl = FileHelper.getFileFullUrl(_createdConnectorProject.getName() + "/deployment/connector"); //$NON-NLS-1$
    // Get "connector" folder absolute path (\ have to be doubled in reg files).
    String connectorFolderAbsolutePath = new File(connectorFolderUrl.getFile()).getAbsolutePath().replaceAll("\\\\", "\\\\\\\\"); //$NON-NLS-1$ //$NON-NLS-2$

    // Load HKCU template file.
    byte[] hkcuTemplateFileByteContent = FileHelper.readRawFile(WindowsRegistryFileHelper.HKCU_TEMPLATE_FILE_PATH);
    String hkcuTemplateFileContent = new String(hkcuTemplateFileByteContent, WindowsRegistryFileHelper.REG_FILES_CHARSET_NAME);
    // Do substitution (HKCU file is filled with a path to the connector project).
    String hkcuResultFileContent = MessageFormat.format(hkcuTemplateFileContent, connectorFolderAbsolutePath, _connectorProjectParameters._connectorName);
    // Write new file in the created project.
    FileHelper.writeFile(WindowsRegistryFileHelper.generateHkcuFilePath(_createdConnectorProject.getName(), _connectorProjectParameters._connectorName), true,
        hkcuResultFileContent.getBytes(WindowsRegistryFileHelper.REG_FILES_CHARSET_NAME));
  }

  /**
   * Generate Java code to register ECF connectors for each artefact type.
   * @return
   */
  protected String generateCustomizedCodeForOutOfProcessConnectorApplication() {
    StringBuilder replacementString = new StringBuilder();
    for (TypeDefinition typeDefinition : _connectorProjectParameters._typeDefinitions) {
      replacementString.append(MessageFormat.format(REGISTER_ECF_CONNECTOR_JAVA_CODE, typeDefinition.getTypeName()));
    }
    return replacementString.toString();
  }

  /**
   * @see org.eclipse.ui.actions.WorkspaceModifyOperation#execute(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected void execute(IProgressMonitor monitor_p) throws CoreException, InvocationTargetException, InterruptedException {
    monitor_p.beginTask(Messages.NewConnectorCreationOperation_TaskName_CreatingProject, 4);
    monitor_p.subTask(Messages.NewConnectorCreationOperation_SubTask_CreatingPlugin);
    createConnectorProject();
    monitor_p.worked(1);

    monitor_p.subTask(Messages.NewConnectorCreationOperation_SubTask_AddingSources);
    if (!_connectorProjectParameters._isOutOfProcess) {
      // Simple in-process connector.
      createInProcessConnectorSources();
    } else if (!_connectorProjectParameters._isProduct) {
      // Simple remote connector.
      createOutOfProcessConnectorSimpleSources();
    } else {
      // Remote connector with product.
      createOutOfProcessConnectorApplicationSources();
    }
    monitor_p.worked(1);

    monitor_p.subTask(Messages.NewConnectorCreationOperation_SubTask_AddingConfDeploymentFiles);
    try {
      createConnectorDeclarationFile();
      createConnectorAssociationFile();
      createArtefactDescriptionFile();
      createWindowsRegistryFiles();
      createTypeIconFiles();
    } catch (CoreException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new InvocationTargetException(ex);
    } finally {
      monitor_p.worked(1);

      monitor_p.subTask(Messages.NewConnectorCreationOperation_SubTask_RefreshingProject);
      ProjectHelper.refreshProject(_createdConnectorProject, null);
      monitor_p.worked(1);
    }
  }
}
