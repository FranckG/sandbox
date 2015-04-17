/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment;

import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;

import com.thalesgroup.orchestra.doors.framework.environment.model.AbstractDoorsElement;
import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetDefinitionModel;
import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetModel;
import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetsToComplete;
import com.thalesgroup.orchestra.doors.framework.environment.model.DoorsProject;
import com.thalesgroup.orchestra.doors.framework.environment.ui.DoorsEnvironmentCompleteBaselineSetsWizard;
import com.thalesgroup.orchestra.doors.framework.environment.ui.Messages;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.connector.Artifact;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.AbstractEnvironment;
import com.thalesgroup.orchestra.framework.environment.BaselineContext;
import com.thalesgroup.orchestra.framework.environment.BaselineResult;
import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentConstants;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.exchange.GefHandler;
import com.thalesgroup.orchestra.framework.exchange.GefHandler.ValueType;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.GEF;
import com.thalesgroup.orchestra.framework.gef.GefFactory;
import com.thalesgroup.orchestra.framework.gef.GenericExportFormat;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Property;
import com.thalesgroup.orchestra.framework.gef.Version;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.puci.PUCI;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

import framework.orchestra.thalesgroup.com.VariableManagerAPI.VariableManagerAdapter;

import javax.xml.rpc.ServiceException;

/**
 * @author S0032874 <br>
 *         Doors Environment main class, extends {@link AbstractEnvironment}. Contains the definition of the attributes keys. Also contains two static tool
 *         methods
 */
public class DoorsEnvironment extends AbstractEnvironment {

  private static final String GET_ARTIFACTS_METADATA_INTERNAL_COMMAND = "GetArtifactsMetadataInternal"; //$NON-NLS-1$
  /**
   * Attribute key for the list of Doors baselineSet
   */
  public final static String ATTRIBUT_KEY_BASELINESET_LIST = "doors_baselineset_";//$NON-NLS-1$
  /**
   * Attribute key for the Doors database name
   */
  public final static String ATTRIBUT_KEY_DB_NAME = "doors_environment_db_name";//$NON-NLS-1$
  /**
   * Attribute key for the Doors database port
   */
  public final static String ATTRIBUT_KEY_PORT = "doors_environment_port";//$NON-NLS-1$
  /**
   * Attribute key for the Doors autologin value
   */
  public static final String ATTRIBUT_KEY_AUTOLOGIN_VALUE = "doors_environment_autologin_value"; //$NON-NLS-1$
  /**
   * Attribute key for the Doors parameters
   */
  public final static String ATTRIBUT_KEY_PARAMETERS = "doors_environment_parameters";//$NON-NLS-1$
  /**
   * Attribute key for the list of Doors projects
   */
  public final static String ATTRIBUT_KEY_PROJECT_LIST = "doors_projects";//$NON-NLS-1$

  public static final String COMMENT = "comment";//$NON-NLS-1$

  private static final String DATABASE = "database"; //$NON-NLS-1$

  private static final String DATABASE_ADDRESS_SEPARATOR = "@"; //$NON-NLS-1$

  private static final String DOORS = "Doors";//$NON-NLS-1$

  private static final String DOORS_EXECUTABLE = "DoorsExecutable"; //$NON-NLS-1$

  public static final String MAJOR = "major";//$NON-NLS-1$

  /**
   * if Baseline suffix is set to current, it means the parameters are missing in the baseline set.
   */
  public final static String MISSING_BASELINE_PARAMETERS = "current";//$NON-NLS-1$

  /**
   * projectID string value
   */
  private static final String PROJECT_ID = "projectID"; //$NON-NLS-1$

  /**
   * suffix string value
   */
  public static final String SUFFIX = "suffix";//$NON-NLS-1$

  /**
   * [SILVER S0.1 contribution] Segment attribute from ODM
   */
  public static final String ODM_PATH_SEGMENT_VALUE = "\\Orchestra\\Segment"; //$NON-NLS-1$

  /**
   * [SILVER S0.1 contribution] Segment Gold constant
   */
  public static final String GOLD = "Gold"; //$NON-NLS-1$

  /**
   * [SILVER S0.1 contribution] Segment Silver constant
   */
  public static final String SILVER = "Silver"; //$NON-NLS-1$

  /**
   * [SILVER S0.1 contribution] DOORS Executable path constant from ODM
   */
  public static final String ODM_DOORS_EXECUTABLE_PATH = "\\Orchestra installation\\COTS\\Doors\\Current\\Executable"; //$NON-NLS-1$

  /**
   * [SILVER S0.1 contribution] Silver cache project list
   */
  private List<String> _silverCacheProjectList = new ArrayList<String>();

  /**
   * Create the baselineSet elements for the GEF file
   * @param doorsProjectId_p The URI of the project
   * @return A list of {@link Element}
   */
  private List<Element> createBaselineSetElements(String doorsProjectId_p) {
    List<Element> elementList = new ArrayList<Element>();
    // Get the list of baselineSets associated to the given project and stored in the Doors Environment
    String baselineSetList = getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_BASELINESET_LIST + doorsProjectId_p);
    List<BaselineSetEnvironmentData> baselineSetEnvironmentDataList = deserializeBaselineSetList(baselineSetList);

    for (BaselineSetEnvironmentData baselineSet : baselineSetEnvironmentDataList) {
      Element element = createElement(baselineSet._baselineSetEncodedURI, baselineSet._baselineSetName);
      elementList.add(element);
    }
    return elementList;
  }

  /**
   * Create the baselineSetDefinition elements, for now the baselineSet elements are returned
   * @param doorsProjectId_p The URI of the project
   * @return A list of {@link Element}
   */
  private List<Element> createBsdElements(String doorsProjectId_p) {
    // for now just return the baselineSet
    List<Element> baselineSetList = createBaselineSetElements(doorsProjectId_p);
    return baselineSetList;
  }

  /**
   * Create a baselineSet {@link Element}
   * @param uri_p the URI of the element
   * @param name_p The name of the element
   * @return The created {@link Element}
   */
  private Element createElement(String uri_p, String name_p) {
    Element element = GefFactory.eINSTANCE.createElement();
    element.setType(ICommonConstants.EMPTY_STRING);
    element.setLabel(ICommonConstants.EMPTY_STRING);

    setElementProperty(element, "baselineSet", GefHandler.ValueType.CDATA, name_p);//$NON-NLS-1$
    element.setUri(uri_p);
    return element;
  }

  /**
   * Create a null context error response
   * @param commandName_p The used commandName
   * @return The command status
   */
  private CommandStatus createNullContextError(String commandName_p) {
    String message = MessageFormat.format(Messages.DoorsEnvironment_Null_Context_Given_To_Command, commandName_p);
    return new CommandStatus(IStatus.ERROR, message, null, 0);
  }

  /**
   * Create a project element with two mandatory properties and one optional
   * @param uri_p The URI of the project
   * @param database_p The database, mandatory
   * @param id_p The Id of the project, mandatory
   * @param baselineSet_p The baselineSet, optional, therefore null when absent
   * @param moduleElementList_p The list of modules, in Element format, can be empty if none
   * @return The created element, not null
   */
  private Element createProjectElement(String uri_p, String database_p, String id_p, String baselineSet_p, List<Element> moduleElementList_p) {
    Element element = GefFactory.eINSTANCE.createElement();
    element.setType(ICommonConstants.EMPTY_STRING);
    element.setLabel(ICommonConstants.EMPTY_STRING);

    setElementProperty(element, "database", GefHandler.ValueType.CDATA, database_p);//$NON-NLS-1$
    setElementProperty(element, "projectId", GefHandler.ValueType.CDATA, id_p);//$NON-NLS-1$
    if (null != baselineSet_p) {
      setElementProperty(element, "baselineSet", GefHandler.ValueType.CDATA, baselineSet_p);//$NON-NLS-1$
    }
    element.setUri(uri_p);
    // Add the module Elements
    element.getELEMENT().addAll(moduleElementList_p);
    return element;
  }

  /**
   * Create a GEF Element with on property
   * @param uri_p The URI of the element
   * @return The created element, not null
   */
  private Element createRootArtifactsElement(String uri_p) {
    Element element = GefFactory.eINSTANCE.createElement();
    element.setType(ICommonConstants.EMPTY_STRING);
    element.setLabel(ICommonConstants.EMPTY_STRING);

    Property environmentProperty = GefFactory.eINSTANCE.createProperty();
    environmentProperty.setName(IEnvironmentConstants.ELEMENT_PROPERTY_NAME_ENVIRONMENT);

    // Set environment string representation.
    GefHandler.addValue(environmentProperty, GefHandler.ValueType.CDATA, this.toString());

    Properties properties = GefFactory.eINSTANCE.createProperties();
    element.getPROPERTIES().add(properties);
    properties.getPROPERTY().add(environmentProperty);
    element.setUri(uri_p);
    return element;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getArtifactsMetadata(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus getArtifactsMetadata(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    if (null == context_p) {
      return createNullContextError("getArtifactsMetadata");//$NON-NLS-1$
    }

    String doorsProjectsEnvDescription = getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST);
    List<ProjectEnvironmentData> projectsEnvironmentData = deserializeProjectList(doorsProjectsEnvDescription);
    StatusHandler statusHandler = new StatusHandler();
    GefHandler gefHandler = new GefHandler();
    StatusDefinition statusDefinition = null;
    List<Element> elementList = new ArrayList<Element>();
    List<CommandStatus> resultList = new ArrayList<CommandStatus>();
    // Parse all artifacts, can be either a project or a module
    CommandStatus commandStatus = null;
    statusDefinition = getArtefactsMetadataFromDoors(context_p, statusHandler, commandStatus);

    for (Artifact artifact : context_p.getArtifacts()) {
      OrchestraURI uri = artifact.getUri();
      String decodedLogicalName = OrchestraURI.decode(uri.getRootName());
      String stringUri = uri.getUri();

      // Find Doors project with corresponding logical name.
      ProjectEnvironmentData currentDoorsProject = null;
      for (ProjectEnvironmentData projectEnvironmentData : projectsEnvironmentData) {
        if (decodedLogicalName.equals(projectEnvironmentData._doorsProjectLogicalName)) {
          currentDoorsProject = projectEnvironmentData;
          break;
        }
      }
      if (null != currentDoorsProject) {
        List<Element> bsdList = createBsdElements(currentDoorsProject._doorsProjectId);
        String database = getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_PORT) + "@" + getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_DB_NAME);//$NON-NLS-1$
        Element element = createProjectElement(stringUri, database, currentDoorsProject._doorsProjectId, null, bsdList);
        mergeGefFromConnectorAndEnvironment(statusHandler, gefHandler, statusDefinition, stringUri, element);
        elementList.add(element);
        com.thalesgroup.orchestra.framework.exchange.status.Status s = statusHandler.getStatusForUri(statusDefinition, stringUri);
        // prod00130554: verify if status s is null
        if (null != s)
          resultList.add(new CommandStatus(s.getSeverity().getValue(), s.getMessage(), uri, 0));
      }
    }

    // Create the GEF file
    GEF gef = gefHandler.createNewModel(context_p.getExportFilePath());

    GenericExportFormat artifactSet = GefFactory.eINSTANCE.createGenericExportFormat();
    for (Element element : elementList) {
      artifactSet.getELEMENT().add(element);
    }
    gef.getGENERICEXPORTFORMAT().add(artifactSet);

    gefHandler.saveModel(gef, true);
    gefHandler.clean();
    commandStatus = new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, null, 0);
    commandStatus.addChildren(resultList);

    return commandStatus;
  }

  private static void mergeGefFromConnectorAndEnvironment(StatusHandler statusHandler, GefHandler gefHandler, StatusDefinition statusDefinition,
      String stringUri, Element element) {
    com.thalesgroup.orchestra.framework.exchange.status.Status status = statusHandler.getStatusForUri(statusDefinition, stringUri);
    if (status != null) {
      GEF gefFromConnector = gefHandler.loadModel(statusHandler.getExportModelAbsolutePath(status));
      if (null != gefFromConnector) {
        // Retrieve the data from the GEF file
        for (GenericExportFormat databaseContent : gefFromConnector.getGENERICEXPORTFORMAT()) {
          for (Element elementInGefDoors : databaseContent.getELEMENT()) {
            // Get every project
            if (elementInGefDoors.getUri().equals(stringUri)) {
              Version version = elementInGefDoors.getVERSION().get(0);
              if (null != version) {
                element.getVERSION().clear();
                element.getVERSION().add(version);
              }
              Properties properties = element.getPROPERTIES().get(0);
              if (null != properties) {
                properties.getPROPERTY().addAll(elementInGefDoors.getPROPERTIES().get(0).getPROPERTY());
              }
            }
          }
        }
      }
    }
  }

  private static StatusDefinition getArtefactsMetadataFromDoors(CommandContext context_p, StatusHandler statusHandler, CommandStatus commandStatus) {
    StatusDefinition statusDefinition = null;
    List<OrchestraURI> listOfUrisToRetrieveInformationAtConnector = new ArrayList<OrchestraURI>();
    for (Artifact artifact : context_p.getArtifacts()) {
      OrchestraURI uri = artifact.getUri();
      String objectType = uri.getObjectType();
      if (null != objectType && objectType.length() > 0) {
        listOfUrisToRetrieveInformationAtConnector.add(uri);
      }
    }
    if (listOfUrisToRetrieveInformationAtConnector != null && listOfUrisToRetrieveInformationAtConnector.size() > 0) {
      Map<String, String> commandResult = null;
      try {
        commandResult = PUCI.executeSpecificCommand(GET_ARTIFACTS_METADATA_INTERNAL_COMMAND, listOfUrisToRetrieveInformationAtConnector);
      } catch (Exception e) {
        commandStatus = new CommandStatus(IStatus.ERROR, "An error occurs during Connector Doors interrogation.", null, 0); //$NON-NLS-1$
        return null;
      }
      // Manage the result
      if ((null == commandResult) || (commandResult.isEmpty())) {
        return null;
      }
      String responseFilePath = commandResult.get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH);
      if (null == responseFilePath) {
        return null;
      }
      // Get the status definition from response file.
      statusDefinition = statusHandler.loadModel(responseFilePath);
      // Unload the definition
      statusHandler.unloadModel(statusDefinition);
      if ((null == statusDefinition)) {
        return null;
      }
    }
    return statusDefinition;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironment#getEnvironmentHandler()
   */
  @Override
  public DoorsEnvironmentHandler getEnvironmentHandler() {
    return (DoorsEnvironmentHandler) super.getEnvironmentHandler();
  }

  /**
   * Return the Root artifacts of a Doors Environment, i.e. the projects, with one element per project
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getRootArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus getRootArtifacts(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    if (null == context_p) {
      return createNullContextError("getRootArtifacts"); //$NON-NLS-1$
    }
    // Get handler
    DoorsEnvironmentHandler handler = getEnvironmentHandler();
    // Orchestra segment value
    String orchestraSegment = null;

    try {
      // Retrieve orchestra segment value (Gold, Silver, Bronze)
      orchestraSegment = VariableManagerAdapter.getInstance().getSubstitutedValue(ODM_PATH_SEGMENT_VALUE);
    } catch (RemoteException exception_p) {
      return new CommandStatus(IStatus.ERROR, "DoorsEnvironment.getRootArtifacts(..) _ " + exception_p.getMessage(), null, 202); //$NON-NLS-1$
    } catch (ServiceException exception_p) {
      return new CommandStatus(IStatus.ERROR, "DoorsEnvironment.getRootArtifacts(..) _ " + exception_p.getMessage(), null, 303); //$NON-NLS-1$
    }

    if ((null == orchestraSegment)) {
      // Assuming descending compatibility, if segment name doesn't exist, the solution is Gold.
      orchestraSegment = GOLD;
    }
    // In case of Silver, we have to call getProject method because it is not possible to choose the projects at Silver usage for a Doors database.
    if (orchestraSegment.equals(SILVER)) {
      // In case of ExpandArtifactSet we have to get the cache instead of call getProject method
      if (context_p.getCommandType().equals("ExpandArtifactSet")) { //$NON-NLS-1$
        GefHandler gefHandler = new GefHandler();
        GEF gef = gefHandler.createNewModel(context_p.getExportFilePath());
        GenericExportFormat artifactSet = GefFactory.eINSTANCE.createGenericExportFormat();
        if (!_silverCacheProjectList.isEmpty()) {
          for (String project : _silverCacheProjectList) {
            OrchestraURI uri = new OrchestraURI(DOORS, project);
            Element element = createRootArtifactsElement(uri.getUri());
            artifactSet.getELEMENT().add(element);
          }
        }
        gef.getGENERICEXPORTFORMAT().add(artifactSet);
        gefHandler.saveModel(gef, true);
        gefHandler.clean();
      } else {
        // Refresh cache list of projects
        _silverCacheProjectList.clear();
        // Get Doors attributes to build minimal URI to call GetProject method.
        final Map<String, String> attributes = getAttributes();
        // Construct the command to send to the Doors connector to retrieve the projects and the modules
        OrchestraURI uri = new OrchestraURI(DoorsEnvironmentHandler.URI_PREFIX);
        uri.addParameter(DATABASE,
            buildDoorsDatataseFullAddress(OrchestraURI.decode(attributes.get(ATTRIBUT_KEY_DB_NAME)), OrchestraURI.decode(attributes.get(ATTRIBUT_KEY_PORT))));

        String doorsExecutablePath = null;
        try {
          doorsExecutablePath = VariableManagerAdapter.getInstance().getSubstitutedValue(ODM_DOORS_EXECUTABLE_PATH);
        } catch (RemoteException exception_p) {
          return new CommandStatus(IStatus.ERROR, "DoorsEnvironment.getRootArtifacts(..) _ " + exception_p.getMessage(), null, 505); //$NON-NLS-1$
        } catch (ServiceException exception_p) {
          return new CommandStatus(IStatus.ERROR, "DoorsEnvironment.getRootArtifacts(..) _ " + exception_p.getMessage(), null, 606); //$NON-NLS-1$
        }

        if (null == doorsExecutablePath) {
          return new CommandStatus(IStatus.ERROR,
              "Missing Doors executable path variable in ODM context at \\Orchestra installation\\COTS\\Doors\\Current\\Executable address", null, 0); //$NON-NLS-1$
        }

        uri.addParameter(DOORS_EXECUTABLE, doorsExecutablePath);
        uri.addParameter(DoorsEnvironmentHandler.ATTRIBUT_KEY_AUTOLOGIN_VALUE, "false"); //$NON-NLS-1$
        uri.addParameter(DoorsEnvironmentHandler.ATTRIBUTE_KEY_PARAMETERS, ""); //$NON-NLS-1$

        StatusDefinition statusDefinition = handler.executeDoorsSpecificCommand(uri, DoorsEnvironmentHandler.GET_PROJECTS_COMMAND);
        if ((null == statusDefinition)) {
          GefHandler gefHandler = new GefHandler();
          GEF gef = gefHandler.createNewModel(context_p.getExportFilePath());
          GenericExportFormat artifactSet = GefFactory.eINSTANCE.createGenericExportFormat();
          gef.getGENERICEXPORTFORMAT().add(artifactSet);
          gefHandler.saveModel(gef, true);
          gefHandler.clean();
          return new CommandStatus(IStatus.ERROR, "Doors specific command doesn't return a StatusDefinition", null, 0); //$NON-NLS-1$
        }

        StatusHandler handlerStatus = new StatusHandler();
        com.thalesgroup.orchestra.framework.exchange.status.Status commandStatus = handlerStatus.getStatusForUri(statusDefinition, uri.getUri());
        if (null == commandStatus) {
          GefHandler gefHandler = new GefHandler();
          GEF gef = gefHandler.createNewModel(context_p.getExportFilePath());
          GenericExportFormat artifactSet = GefFactory.eINSTANCE.createGenericExportFormat();
          gef.getGENERICEXPORTFORMAT().add(artifactSet);
          gefHandler.saveModel(gef, true);
          gefHandler.clean();
          return new CommandStatus(IStatus.ERROR, "Unable to retrieve status from Doors specific command", null, 0); //$NON-NLS-1$
        }

        String gefFilePath = commandStatus.getExportFilePath();
        if (null == gefFilePath) {
          GefHandler gefHandler = new GefHandler();
          GEF gef = gefHandler.createNewModel(context_p.getExportFilePath());
          GenericExportFormat artifactSet = GefFactory.eINSTANCE.createGenericExportFormat();
          gef.getGENERICEXPORTFORMAT().add(artifactSet);
          gefHandler.saveModel(gef, true);
          gefHandler.clean();
          return new CommandStatus(IStatus.ERROR, "Unable to get the Gef file path", null, 0); //$NON-NLS-1$
        }

        GefHandler gefHandler = new GefHandler();
        GEF inputGef = gefHandler.loadModel(gefFilePath);
        GEF outputGef = gefHandler.createNewModel(context_p.getExportFilePath());
        GenericExportFormat artifactSet = GefFactory.eINSTANCE.createGenericExportFormat();

        // Retrieve the data from the GEF file
        for (GenericExportFormat databaseContent : inputGef.getGENERICEXPORTFORMAT()) {
          for (Element environment : databaseContent.getELEMENT()) {
            // Get every project
            for (Element project : environment.getELEMENT()) {
              if ("Project".equals(project.getType())) { //$NON-NLS-1$
                // String projectId = new OrchestraURI(project.getUri()).getObjectId();
                String name = project.getFullName();
                _silverCacheProjectList.add(name);
                uri = new OrchestraURI(DOORS, name);
                Element element = createRootArtifactsElement(uri.getUri());
                artifactSet.getELEMENT().add(element);
              }
            }
          }
        }
        outputGef.getGENERICEXPORTFORMAT().add(artifactSet);
        gefHandler.saveModel(outputGef, true);
        gefHandler.clean();

        // Unload the model
        gefHandler.unloadModel(inputGef);
      }
    } else {
      GefHandler gefHandler = new GefHandler();
      GEF gef = gefHandler.createNewModel(context_p.getExportFilePath());

      GenericExportFormat artifactSet = GefFactory.eINSTANCE.createGenericExportFormat();
      List<String> projectLogicalNames = getProjectNames(getAttributes().get(ATTRIBUT_KEY_PROJECT_LIST), true);

      // Create an element for each project
      for (String name : projectLogicalNames) {
        OrchestraURI uri = new OrchestraURI(DOORS, name);
        Element element = createRootArtifactsElement(uri.getUri());
        artifactSet.getELEMENT().add(element);
      }

      gef.getGENERICEXPORTFORMAT().add(artifactSet);

      gefHandler.saveModel(gef, true);
      gefHandler.clean();
    }

    return new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, null, 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironment#handleInitialAttributes(java.util.Map)
   */
  @Override
  protected Map<String, String> handleInitialAttributes(EnvironmentContext context_p, IProgressMonitor progressMonitor_p) {
    // Map<String, String> attributes_p is deprecated, must use context_p parameter.
    Map<String, String> attributes = context_p._attributes;
    // Get handler.
    IVariablesHandler variablesHandler = getEnvironmentHandler().getVariablesHandler();
    // Generate a new map with substituted values.
    Map<String, String> result = new HashMap<String, String>();
    // Encoded substitution of the Doors database name.
    String substituedDoorsDbName = OrchestraURI.encode(variablesHandler.getSubstitutedValue(OrchestraURI.decode(attributes.get(ATTRIBUT_KEY_DB_NAME))));
    result.put(ATTRIBUT_KEY_DB_NAME, substituedDoorsDbName);
    // Encoded substitution of the Doors database port.
    String subsitutedDoorsDbPort = OrchestraURI.encode(variablesHandler.getSubstitutedValue(OrchestraURI.decode(attributes.get(ATTRIBUT_KEY_PORT))));
    result.put(ATTRIBUT_KEY_PORT, subsitutedDoorsDbPort);
    // Doors parameters
    String subsitutedParameters = OrchestraURI.encode(variablesHandler.getSubstitutedValue(OrchestraURI.decode(attributes.get(ATTRIBUT_KEY_PARAMETERS))));
    result.put(ATTRIBUT_KEY_PARAMETERS, subsitutedParameters);
    // Doors AutoLogin option
    String substitutedAutoLoginOption =
        OrchestraURI.encode(variablesHandler.getSubstitutedValue(OrchestraURI.decode(attributes.get(ATTRIBUT_KEY_AUTOLOGIN_VALUE))));
    if (null != substitutedAutoLoginOption && !substitutedAutoLoginOption.isEmpty()) {
      result.put(ATTRIBUT_KEY_AUTOLOGIN_VALUE, substitutedAutoLoginOption);
    } else {
      result.put(ATTRIBUT_KEY_AUTOLOGIN_VALUE, "false"); //$NON-NLS-1$
    }

    // Encoded substitution of the projects list.
    String substituedProjectsList = variablesHandler.getSubstitutedValue(OrchestraURI.decode(attributes.get(ATTRIBUT_KEY_PROJECT_LIST)));
    List<DoorsProject> substituedProjectsListToEncode = DoorsEnvironment.deserializeSubstitutedProjectList(substituedProjectsList);
    String encodedSubstituedProjectsList = DoorsEnvironment.serializeDoorsProject(substituedProjectsListToEncode);
    result.put(ATTRIBUT_KEY_PROJECT_LIST, encodedSubstituedProjectsList);
    // Get the list of the projects and get substituted baselinesets data if
    // any.
    List<ProjectEnvironmentData> unsubstitutedProjectsData = deserializeProjectList(attributes.get(ATTRIBUT_KEY_PROJECT_LIST));
    for (ProjectEnvironmentData unsubstitutedProjectData : unsubstitutedProjectsData) {
      // Only the ID of the project is needed to get the key of the
      // baselineset.
      String substitutedDoorsProjectId = OrchestraURI.encode(variablesHandler.getSubstitutedValue(unsubstitutedProjectData._doorsProjectId));
      String substitutedBaselineSetData =
          DoorsEnvironment.encodeBaselineSetValues(variablesHandler.getSubstitutedValue(OrchestraURI.decode(attributes
              .get(ATTRIBUT_KEY_BASELINESET_LIST + unsubstitutedProjectData._doorsProjectId))));
      if (null != substitutedBaselineSetData) {
        result.put(ATTRIBUT_KEY_BASELINESET_LIST + substitutedDoorsProjectId, substitutedBaselineSetData);
      }
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironment#isBaselineCompliant()
   */
  @Override
  public boolean isBaselineCompliant() {
    // Get DoorsEnvironnementHandler.
    DoorsEnvironmentHandler doorsEnvironmentHandler = getEnvironmentHandler();
    // Get Doors attributes to get all the BaselinesSets.
    Map<String, String> resultBaselines = doorsEnvironmentHandler.getBaselinesSets(getAttributes());
    // If there is no baseline in the environment, it is not compliant with
    // MakeBaseline.
    if (resultBaselines.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#isHandlingArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus isHandlingArtifacts(CommandContext context_p) {
    if (null == context_p) {
      return createNullContextError("isHandlingArtifacts"); //$NON-NLS-1$
    }
    // True if any artifact status is an Error

    boolean hasError = false;
    // True if any artifact status is a Warning
    boolean hasWarning = false;

    // Orchestra segment value
    String orchestraSegment = null;

    try {
      orchestraSegment = VariableManagerAdapter.getInstance().getSubstitutedValue(ODM_PATH_SEGMENT_VALUE);
    } catch (RemoteException exception_p) {
      return new CommandStatus(IStatus.ERROR, "DoorsEnvironment.isHandlingArtifacts(..) _ " + exception_p.getMessage(), null, 202); //$NON-NLS-1$
    } catch (ServiceException exception_p) {
      return new CommandStatus(IStatus.ERROR, "DoorsEnvironment.isHandlingArtifacts(..) _ " + exception_p.getMessage(), null, 303); //$NON-NLS-1$
    }

    if ((null == orchestraSegment)) {
      return new CommandStatus(IStatus.ERROR, "Missing Segment variable in ODM context at \\Orchestra\\Segment address", null, 0); //$NON-NLS-1$
    }

    List<CommandStatus> resultList = new ArrayList<CommandStatus>();
    String projectAttribute = getAttributes().get(ATTRIBUT_KEY_PROJECT_LIST);
    List<String> projectList = getProjectNames(projectAttribute, true);
    if (projectList.isEmpty() && (!SILVER.equals(orchestraSegment))) {
      return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.DoorsEnvironment_No_Project_Message, this), null, 0);
    }
    for (Artifact artifact : context_p.getArtifacts()) {
      OrchestraURI uri = artifact.getUri();
      String type = uri.getRootType();
      // Check the URI rootType
      if (!DOORS.equals(type)) {
        String errorMessage = MessageFormat.format(Messages.DoorsEnvironment_RootType_Not_Handled_Message, type, uri.getUri());
        resultList.add(new CommandStatus(IStatus.ERROR, errorMessage, uri, 0));
        hasError = true;
        continue;
      }
      // In case of Doors environment the rootName is a project name
      String name = uri.getRootName();
      if (projectList.contains(name)) {
        resultList.add(new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, uri, 0));
      } else {
        if (SILVER.equals(orchestraSegment)) {
          resultList.add(new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, uri, 0));
        } else {
          String errorMessage = MessageFormat.format(Messages.DoorsEnvironment_Project_Not_Handled_Message, name, uri.getUri(), toString());
          resultList.add(new CommandStatus(IStatus.WARNING, errorMessage, uri, 0));
          hasWarning = true;
        }
      }
    }

    // Create global CommandStatus result
    int globalSeverity = IStatus.OK;
    String message = ICommonConstants.EMPTY_STRING;
    // Check Warning fist because Error overrides Warning
    if (hasWarning) {
      globalSeverity = IStatus.WARNING;
      message = MessageFormat.format(Messages.DoorsEnvironment_Warning_URI_Not_Handled_Message, toString());
    } else if (hasError) {
      globalSeverity = IStatus.ERROR;
      message = ICommonConstants.EMPTY_STRING;
    }
    CommandStatus result = new CommandStatus(globalSeverity, message, null, 0);
    // Add sub commandStatus of each artifacts
    result.addChildren(resultList);
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#makeBaseline(com.thalesgroup.orchestra.framework.environment.BaselineContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public BaselineResult makeBaseline(BaselineContext baselineContext_p, IProgressMonitor monitor_p) {
    final String orchestraBaselineName = baselineContext_p.getBaselineName();
    final String orchestraBaselineDesc = baselineContext_p.getBaselineDescription();
    final String DoorsEnvId = baselineContext_p.getEnvironmentId();
    // Overall result.
    final BaselineResult result = new BaselineResult();
    // Total number of operations. Nothing to call with progressMonitor in
    // this method.
    // SubMonitor monitor = SubMonitor.convert(monitor_p, 0);

    // Get DoorsEnvironnementHandler.
    DoorsEnvironmentHandler doorsEnvironmentHandler = getEnvironmentHandler();
    // Precondition on handler
    if (null == doorsEnvironmentHandler) {
      IStatus resultStatus = new Status(IStatus.ERROR, DoorsEnvId, Messages.DoorsEnvironment_handler_unreachable);
      result.setStatus(new CommandStatus(resultStatus));
      return result;
    }
    // Get Doors attributes to build Doors database full address and get all
    // the BaselinesSets.
    final Map<String, String> attributes = getAttributes();
    // Get BaselinesSets attributes.
    Map<String, String> resultBaselines = doorsEnvironmentHandler.getBaselinesSets(attributes);

    // List of all baselineSets to be sent to the connector to validate the
    // baseline.
    final List<OrchestraURI> uriList = new ArrayList<OrchestraURI>();
    // List of baselineSets to be completed by the the user with wizard (if
    // any).
    final ArrayList<OrchestraURI> baselinesToComplete = new ArrayList<OrchestraURI>();
    // Validate list of baselineSets result.
    final BaselineSetsToComplete[] completedBaselinesSets = new BaselineSetsToComplete[] { null };

    for (String mapKey : resultBaselines.keySet()) {
      OrchestraURI uri = new OrchestraURI(mapKey);
      uri.addParameter(DATABASE,
          buildDoorsDatataseFullAddress(OrchestraURI.decode(attributes.get(ATTRIBUT_KEY_DB_NAME)), OrchestraURI.decode(attributes.get(ATTRIBUT_KEY_PORT))));
      uri.addParameter(DOORS_EXECUTABLE, doorsEnvironmentHandler.getVariablesHandler().getSubstitutedValue(DoorsEnvironmentHandler.ODM_DOORS_EXECUTABLE_PATH));
      // Detect selected baselines set to "current". Must be completed.
      if (DoorsEnvironment.MISSING_BASELINE_PARAMETERS.equals(resultBaselines.get(mapKey))) {
        baselinesToComplete.add(uri);
      } else {
        uri.addParameter(MAJOR, "");//$NON-NLS-1$ 
        uri.addParameter(SUFFIX, "");//$NON-NLS-1$ 
        uri.addParameter(COMMENT, "");//$NON-NLS-1$ 
        uriList.add(uri);
      }
    }
    // Only if there is BaselinesSets to complete, ask the user.
    if (!baselinesToComplete.isEmpty()) {
      // Get the list of project.
      List<ProjectEnvironmentData> projectsEnvironmentData = deserializeProjectList(getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST));
      final BaselineSetsToComplete baselineSetsToComplete = new BaselineSetsToComplete(baselinesToComplete, projectsEnvironmentData);

      // Display to user.
      DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
        /**
         * @see java.lang.Runnable#run()
         */
        public void run() {
          // Create wizard.
          DoorsEnvironmentCompleteBaselineSetsWizard completeBaselineSetsWizard =
              new DoorsEnvironmentCompleteBaselineSetsWizard(Messages.DoorsEnvironmentCompleteBaselineSetsToMakeBaseline_Window_Title, baselineSetsToComplete,
                  orchestraBaselineName, orchestraBaselineDesc);
          WizardDialog doorsEnvironmentCompleteBaselineSetsWizardDialog = new WizardDialog(getDisplay().getActiveShell(), completeBaselineSetsWizard);
          if (Window.OK == doorsEnvironmentCompleteBaselineSetsWizardDialog.open()) {
            if (null != completeBaselineSetsWizard._baselineSetsToComplete) {
              completedBaselinesSets[0] = completeBaselineSetsWizard._baselineSetsToComplete;
              uriList.addAll(completedBaselinesSets[0].getCompletedBaselineSets());
              // This part complete all uris with extra parameters from the environment to give to the connector.
              for (int i = 0; i < uriList.size(); i++) {
                OrchestraURI uriToFinalise = uriList.get(i);
                // Verify Parameters attributes is not null
                if (null != attributes.get(ATTRIBUT_KEY_PARAMETERS))
                  uriToFinalise.addParameter(ATTRIBUT_KEY_PARAMETERS, attributes.get(ATTRIBUT_KEY_PARAMETERS));
                else
                  uriToFinalise.addParameter(ATTRIBUT_KEY_PARAMETERS, ICommonConstants.EMPTY_STRING);
                // Verify Autologin attribute is not null and not empty
                if (null != attributes.get(ATTRIBUT_KEY_AUTOLOGIN_VALUE) && ICommonConstants.EMPTY_STRING != attributes.get(ATTRIBUT_KEY_AUTOLOGIN_VALUE))
                  uriToFinalise.addParameter(DoorsEnvironmentHandler.ATTRIBUT_KEY_AUTOLOGIN_VALUE, attributes.get(ATTRIBUT_KEY_AUTOLOGIN_VALUE));
                else
                  uriToFinalise.addParameter(DoorsEnvironmentHandler.ATTRIBUT_KEY_AUTOLOGIN_VALUE, Boolean.FALSE.toString());
                uriList.set(i, uriToFinalise);
              }
            } else {
              result.setStatus(new CommandStatus(new Status(IStatus.WARNING, DoorsEnvId, Messages.DoorsEnvironmentOperationCancelledByUser)));
            }
          } else {
            result.setStatus(new CommandStatus(new Status(IStatus.WARNING, DoorsEnvId, Messages.DoorsEnvironmentOperationCancelledByUser)));
          }
        }
      }, false);
    }
    // There is baselineSets and the user didn't cancel.
    if (null == result.getStatus()) {
      Map<String, String> resultAttributes = new HashMap<String, String>();
      // Those attributes don't change with baseline.
      resultAttributes.put(ATTRIBUT_KEY_DB_NAME, attributes.get(ATTRIBUT_KEY_DB_NAME));
      resultAttributes.put(ATTRIBUT_KEY_PORT, attributes.get(ATTRIBUT_KEY_PORT));
      resultAttributes.put(ATTRIBUT_KEY_PROJECT_LIST, attributes.get(ATTRIBUT_KEY_PROJECT_LIST));
      // Get the list of the project
      List<ProjectEnvironmentData> projectEnvironmentDataList = deserializeProjectList(attributes.get(ATTRIBUT_KEY_PROJECT_LIST));
      // Initialize baselineSets attributes in map
      for (ProjectEnvironmentData projectEnvironmentData : projectEnvironmentDataList) {
        // Only the ID of the project is needed to get the key of the baselineset.
        String doorsProjectId = projectEnvironmentData._doorsProjectId;
        resultAttributes.put(ATTRIBUT_KEY_BASELINESET_LIST + doorsProjectId, ICommonConstants.EMPTY_STRING);
      }
      // Send command to the connector.
      StatusDefinition statusDefinition = doorsEnvironmentHandler.executeDoorsSpecificCommand(uriList, "makeBaseline"); //$NON-NLS-1$
      if (null == statusDefinition)
        return null;
      com.thalesgroup.orchestra.framework.exchange.status.Status status = statusDefinition.getStatus();
      EList<com.thalesgroup.orchestra.framework.exchange.status.Status> statusEList = status.getStatus();
      if (statusEList.isEmpty()) {
        return null;
      }
      // Parsing return status.
      if (null != status) {
        CommandStatus subResult = new CommandStatus(Status.OK_STATUS);
        com.thalesgroup.orchestra.framework.exchange.status.Status doorsEnvStatus = statusEList.get(0);
        EList<com.thalesgroup.orchestra.framework.exchange.status.Status> subStatus = doorsEnvStatus.getStatus();
        for (com.thalesgroup.orchestra.framework.exchange.status.Status baselineStatus : subStatus) {
          if (SeverityType.ERROR == baselineStatus.getSeverity()) {
            subResult.addChild(new CommandStatus(new Status(IStatus.ERROR, DoorsEnvId, baselineStatus.getMessage())));
            result.setStatus(subResult);
            return result;
          }
          if ((0 == baselineStatus.getCode()) && (SeverityType.OK == baselineStatus.getSeverity())) {
            String baselineStatusUri = baselineStatus.getUri();
            OrchestraURI uribaseline = new OrchestraURI(baselineStatusUri);
            String projectId = uribaseline.getParameters().get(PROJECT_ID);
            String baselineName = uribaseline.getObjectId().split("!")[2]; //$NON-NLS-1$
            StringBuilder doorsBaselinesString = new StringBuilder();
            doorsBaselinesString.append(resultAttributes.get(ATTRIBUT_KEY_BASELINESET_LIST + projectId));
            if (0 != doorsBaselinesString.length()) {
              doorsBaselinesString.append(ICommonConstants.COMMA_CHARACTER);
              doorsBaselinesString.append(OrchestraURI.encode(uribaseline.getUri()));
              doorsBaselinesString.append(ICommonConstants.SEMI_COLON_CHARACTER);
              doorsBaselinesString.append(OrchestraURI.encode(baselineName));
              resultAttributes.put(ATTRIBUT_KEY_BASELINESET_LIST + projectId, doorsBaselinesString.toString());
            } else {
              doorsBaselinesString.append(OrchestraURI.encode(uribaseline.getUri()));
              doorsBaselinesString.append(ICommonConstants.SEMI_COLON_CHARACTER);
              doorsBaselinesString.append(OrchestraURI.encode(baselineName));
              resultAttributes.put(ATTRIBUT_KEY_BASELINESET_LIST + projectId, doorsBaselinesString.toString());
            }
            subResult.addChild(new CommandStatus(baselineStatus.getSeverity().getValue(), baselineStatus.getMessage(), null, baselineStatus.getCode()));
          }
        }
        result.setStatus(subResult);
        result.setReferencingAttributes(resultAttributes);
      }
    }
    return result;
  }

  /**
   * Create and set a property to a GEF element
   * @param element_p The element to which the property will be added
   * @param name_p The name of the property
   * @param type_p The type of the property
   * @param value_p The value of the property
   */
  private void setElementProperty(Element element_p, String name_p, ValueType type_p, String value_p) {
    if (element_p.getPROPERTIES().isEmpty()) {
      Properties properties = GefFactory.eINSTANCE.createProperties();
      element_p.getPROPERTIES().add(properties);
    }
    Property property = GefFactory.eINSTANCE.createProperty();
    property.setName(name_p);
    GefHandler.addValue(property, type_p, value_p);
    element_p.getPROPERTIES().get(0).getPROPERTY().add(property);
  }

  public static String buildDoorsDatataseFullAddress(String server_p, String port_p) {
    StringBuilder builder = new StringBuilder();
    if (server_p.isEmpty() || port_p.isEmpty()) {
      return builder.append(DATABASE_ADDRESS_SEPARATOR).toString();
    }
    builder.append(port_p);
    builder.append(DATABASE_ADDRESS_SEPARATOR);
    builder.append(server_p);
    return builder.toString();
  }

  public static List<BaselineSetEnvironmentData> deserializeBaselineSetList(String blsList_p) {
    if (null == blsList_p) {
      return Collections.emptyList();
    }
    List<BaselineSetEnvironmentData> baselineSetEnvironmentDataList = new ArrayList<BaselineSetEnvironmentData>();

    List<String> baselineSetsAttributsList = splitAttributList(blsList_p);
    for (String baselineSetAttributs : baselineSetsAttributsList) {
      List<String> baselineSetAttributsList = splitAttribut(baselineSetAttributs);
      if (2 == baselineSetAttributsList.size()) {
        String baselineSetEncodedURI = OrchestraURI.decode(baselineSetAttributsList.get(0));
        String decodedBaselineSetName = OrchestraURI.decode(baselineSetAttributsList.get(1));
        baselineSetEnvironmentDataList.add(new BaselineSetEnvironmentData(baselineSetEncodedURI, decodedBaselineSetName));
      }
    }
    return baselineSetEnvironmentDataList;
  }

  public static List<ProjectEnvironmentData> deserializeProjectList(String projectList_p) {
    if (null == projectList_p) {
      return Collections.emptyList();
    }
    List<ProjectEnvironmentData> projectEnvironmentDataList = new ArrayList<ProjectEnvironmentData>();

    List<String> doorsProjectsAttributsList = splitAttributList(projectList_p);
    for (String doorsProjectsAttributs : doorsProjectsAttributsList) {
      List<String> doorsProjectAttributs = splitAttribut(doorsProjectsAttributs);
      if (3 == doorsProjectAttributs.size()) {
        String decodedDoorsProjectId = OrchestraURI.decode(doorsProjectAttributs.get(0));
        String decodedDoorsProjectName = OrchestraURI.decode(doorsProjectAttributs.get(1));
        String decodedDoorsProjectLogicalName = OrchestraURI.decode(doorsProjectAttributs.get(2));
        projectEnvironmentDataList.add(new ProjectEnvironmentData(decodedDoorsProjectId, decodedDoorsProjectName, decodedDoorsProjectLogicalName));
      }
    }

    return projectEnvironmentDataList;
  }

  public static List<DoorsProject> deserializeSubstitutedProjectList(String projectList_p) {
    if (null == projectList_p) {
      return Collections.emptyList();
    }
    List<DoorsProject> projectEnvironmentDataList = new ArrayList<DoorsProject>();

    List<String> doorsProjectsAttributsList = splitAttributList(projectList_p);
    for (String doorsProjectsAttributs : doorsProjectsAttributsList) {
      List<String> doorsProjectAttributs = splitAttribut(doorsProjectsAttributs);
      if (3 == doorsProjectAttributs.size()) {
        String decodedDoorsProjectId = doorsProjectAttributs.get(0);
        String decodedDoorsProjectName = doorsProjectAttributs.get(1);
        String decodedDoorsProjectLogicalName = doorsProjectAttributs.get(2);
        projectEnvironmentDataList.add(new DoorsProject(null, decodedDoorsProjectName, false, decodedDoorsProjectId, decodedDoorsProjectLogicalName));
      }
    }

    return projectEnvironmentDataList;
  }

  /**
   * Encode baselineSets values from their string representation to a compressed encoded String.
   * @param rawValues_p
   * @return <code>null</code> if provided raw values can not be processed, the compressed String otherwise.
   */
  public static String encodeBaselineSetValues(String baselineSet_p) {
    // Preconditions.
    if ((null == baselineSet_p) || baselineSet_p.isEmpty()) {
      return null;
    }
    StringBuilder builder = new StringBuilder();
    List<String> baselineSetsAttributsList = splitAttributList(baselineSet_p);
    for (String baselineSetAttributs : baselineSetsAttributsList) {
      List<String> baselineSetValues = splitAttribut(baselineSetAttributs);
      for (String baselineSetValue : baselineSetValues) {
        builder.append(OrchestraURI.encode(baselineSetValue)).append(';');
      }
      // Remove last ';' (no separator on last value)
      builder.deleteCharAt(builder.length() - 1);
      // Each baselineSet is separated by a comma.
      builder.append(',');
    }
    String resultingString = builder.toString();

    // Remove trailing ';' before returning the result.
    return resultingString.substring(0, resultingString.length() - 1);
  }

  public static String generateBaselineSetEncodedURI(BaselineSetModel baselineSet_p) {
    String separator = "!"; //$NON-NLS-1$
    if (baselineSet_p.getParent().getParent() instanceof DoorsProject) {
      String doorsProjectId = baselineSet_p.getDoorsProjectId();
      String baselineSetDefinitionDoorsName = baselineSet_p.getParent().getDoorsName();
      String baselineSetDoorsName = baselineSet_p.getDoorsName();
      String baselinePath = doorsProjectId + separator + baselineSetDefinitionDoorsName + separator + baselineSetDoorsName;
      return OrchestraURI.encode(DoorsEnvironmentHandler.BASELINE_SET_URI_PREFIX + OrchestraURI.encode(baselinePath));
    }
    String blsdPath = ICommonConstants.EMPTY_STRING;
    AbstractDoorsElement doorsFolder = baselineSet_p.getParent().getParent();
    while (null != doorsFolder) {
      blsdPath = "/" + doorsFolder.getDoorsName() + blsdPath; //$NON-NLS-1$
      doorsFolder = doorsFolder.getParent();
    }
    String baselineSetDefinitionDoorsName = baselineSet_p.getParent().getDoorsName();
    String baselineSetDoorsName = baselineSet_p.getDoorsName();
    String baselinePath = separator + baselineSetDefinitionDoorsName + separator + baselineSetDoorsName;
    return OrchestraURI.encode(DoorsEnvironmentHandler.BASELINE_SET_URI_PREFIX + OrchestraURI.encode(blsdPath) + OrchestraURI.encode(baselinePath));

  }

  /**
   * Get a list of an attribute models name
   * @param attribute_p The attribute to use
   * @param islogicalName_p True if the logical name must be returned, else false if the real name must be returned
   * @return A list of names, not null but may be empty
   */
  public static List<String> getProjectNames(String attribute_p, boolean isLogicalName_p) {
    List<String> result = new ArrayList<String>();

    List<ProjectEnvironmentData> projectEnvironmentDataList = deserializeProjectList(attribute_p);
    // For each project get its name
    for (ProjectEnvironmentData model : projectEnvironmentDataList) {
      if (isLogicalName_p) {
        result.add(model._doorsProjectLogicalName);
      } else {
        result.add(model._doorsProjectName);
      }
    }
    return result;
  }

  /**
   * Put the {@link BaselineSetModel} of a {@link BaselineSetDefinitionModel} in the string data map
   * @param selectedBaselineSets_p The list of {@link BaselineSetModel} to convert to string
   * @param fullMap_p The map
   */
  public static Map<String, String> serializeBaselineSetList(Collection<BaselineSetModel> selectedBaselineSets_p) {
    Map<String, String> baselineSetsStrings = new HashMap<String, String>();
    // Parse each baselineSet
    for (BaselineSetModel baselineSet : selectedBaselineSets_p) {
      StringBuilder updatedBaselineSetData = new StringBuilder();
      String doorsProjectId = baselineSet.getDoorsProjectId();
      String mapKey = DoorsEnvironment.ATTRIBUT_KEY_BASELINESET_LIST + doorsProjectId;
      // Get existing data.
      String initialData = baselineSetsStrings.get(mapKey);
      if ((null != initialData) && !initialData.isEmpty()) {
        updatedBaselineSetData.append(initialData + ICommonConstants.COMMA_CHARACTER);
      }

      if (baselineSet.isVirtual()) {
        String baselineSetURI = generateBaselineSetEncodedURI(baselineSet);
        updatedBaselineSetData.append(baselineSetURI).append(ICommonConstants.SEMI_COLON_STRING).append(OrchestraURI.encode(baselineSet.getDoorsName()));
      } else {
        updatedBaselineSetData.append(OrchestraURI.encode(baselineSet.getDoorsURI())).append(ICommonConstants.SEMI_COLON_STRING)
            .append(OrchestraURI.encode(baselineSet.getDoorsName()));
      }
      baselineSetsStrings.put(mapKey, updatedBaselineSetData.toString());
    }
    return baselineSetsStrings;
  }

  /**
   * Create a Map<String, String> from the data model composed of {@link ProjectModel} <br>
   * Only the selected project, folder and baselineSet will be saved, the other will be discarded <br>
   * - The projects will be stored in one string whom key will be {@link DoorsEnvironment#ATTRIBUT_KEY_PROJECT_LIST} <br>
   * - The folders will be stored in one string whom keys will be {@link DoorsEnvironment#ATTRIBUT_KEY_FOLDER_LIST}_projectURI <br>
   * - The baselineSets will be stored in one string whom keys will be {@link DoorsEnvironment#ATTRIBUT_KEY_BASELINESET_LIST}_projectURI <br>
   * @return The map describing the selected elements from the model
   */
  public static String serializeDoorsProject(List<DoorsProject> selectedDoorsProjects_p) {
    StringBuilder doorsProjectsString = new StringBuilder();
    // Save selected projects.
    for (DoorsProject model : selectedDoorsProjects_p) {
      // if not the first data, add a comma to separate from the next data
      if (0 != doorsProjectsString.length()) {
        doorsProjectsString.append(ICommonConstants.COMMA_CHARACTER);
      }
      String encodedDoorsProjectId = OrchestraURI.encode(model.getDoorsProjectId());
      String encodedDoorsName = OrchestraURI.encode(model.getDoorsName());
      String encodedLogicalName = OrchestraURI.encode(model.getLogicalName());
      doorsProjectsString.append(encodedDoorsProjectId);
      doorsProjectsString.append(ICommonConstants.SEMI_COLON_STRING);
      doorsProjectsString.append(encodedDoorsName);
      doorsProjectsString.append(ICommonConstants.SEMI_COLON_STRING);
      doorsProjectsString.append(encodedLogicalName);
    }
    return doorsProjectsString.toString();
  }

  /**
   * Static tool method to separate the attribute variables from a Doors Environment attribute; the separator is: , <br>
   * The size of the list must be no less than three: id;name;surname
   * @param attribut_p The attribute to separate
   * @return The list of variables, or an empty list if the given string is incoherent with a module or project structure
   */
  public static List<String> splitAttribut(String attribut_p) {
    if ((null == attribut_p) || attribut_p.isEmpty()) {
      return Collections.emptyList();
    }
    String[] values = attribut_p.split(ICommonConstants.SEMI_COLON_STRING);
    return Arrays.asList(values);
  }

  /**
   * Static tool method to separate the attributes from a Doors Environment attribute: from a String to a list of String; the separator is: ,
   * @param attributList_p The attribute list to split
   * @return The list of attributes, or an empty list if there is no value
   */
  public static List<String> splitAttributList(String attributList_p) {
    if ((null == attributList_p) || attributList_p.isEmpty()) {
      return Collections.emptyList();
    }
    String[] values = attributList_p.split(String.valueOf(ICommonConstants.COMMA_CHARACTER));
    return Arrays.asList(values);
  }

  public static class BaselineSetEnvironmentData {
    public String _baselineSetEncodedURI;
    public String _baselineSetName;

    public BaselineSetEnvironmentData(String baselineSetEncodedURI_p, String baselineSetName_p) {
      _baselineSetEncodedURI = baselineSetEncodedURI_p;
      _baselineSetName = baselineSetName_p;
    }
  }

  public static class ProjectEnvironmentData {
    public final String _doorsProjectId;
    public final String _doorsProjectLogicalName;
    public final String _doorsProjectName;

    public ProjectEnvironmentData(String doorsProjectId_p, String doorsProjectName_p, String doorsProjectLogicalName_p) {
      _doorsProjectId = doorsProjectId_p;
      _doorsProjectName = doorsProjectName_p;
      _doorsProjectLogicalName = doorsProjectLogicalName_p;
    }
  }
}