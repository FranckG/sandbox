/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment.BaselineSetEnvironmentData;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment.ProjectEnvironmentData;
import com.thalesgroup.orchestra.doors.framework.environment.model.AbstractDoorsContainer;
import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetDefinitionModel;
import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetModel;
import com.thalesgroup.orchestra.doors.framework.environment.model.DoorsFolder;
import com.thalesgroup.orchestra.doors.framework.environment.model.DoorsProject;
import com.thalesgroup.orchestra.doors.framework.environment.ui.DoorsEnvironmentConnectionDataPage;
import com.thalesgroup.orchestra.doors.framework.environment.ui.DoorsEnvironmentProjectNamePage;
import com.thalesgroup.orchestra.doors.framework.environment.ui.DoorsEnvironmentTreePage;
import com.thalesgroup.orchestra.doors.framework.environment.ui.Messages;
import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.AbstractEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.exchange.GefHandler;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.GEF;
import com.thalesgroup.orchestra.framework.gef.GenericExportFormat;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Property;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.puci.PUCI;

/**
 * @author S0032874 <br>
 *         Doors environment handler, extends {@link AbstractEnvironmentHandler} . Provides wizards to create and/or edit a Doors environment. <br>
 *         The parameters of the environment are stored in an attribute map (the same as the one in DoorsEnvironment class) and are editable through two pages. <br>
 *         Necessary access to Doors database are done here.
 */
public class DoorsEnvironmentHandler extends AbstractEnvironmentHandler {
  /**
   * Doors database full address attribute key.
   */
  public static final String ATTRIBUTE_KEY_DOORS_DATABASE_FULL_ADRESS = "Doors_database";//$NON-NLS-1$

  /**
   * Project (partial) attribute key. Completion with project id in getExpandedAttributes() method.
   */
  private static final String ATTRIBUTE_KEY_PARTIAL_PROJECT = "Project_";//$NON-NLS-1$

  public static final String BASELINE_SET_URI_PREFIX = "orchestra://Doors/Environment/BaselineSet/"; //$NON-NLS-1$

  private static final String DATABASE = "database";//$NON-NLS-1$

  private static final String DOORS_EXECUTABLE_PATH = "DoorsExecutable"; //$NON-NLS-1$

  private static final String GEF_LABEL_BASELINE_SET = "BaselineSet";//$NON-NLS-1$

  private static final String GEF_LABEL_BASELINE_SET_DEFINITION = "BaselineSetDefinition";//$NON-NLS-1$

  private static final String GEF_LABEL_FOLDER = "Folder";//$NON-NLS-1$

  private static final String GEF_LABEL_PROJECT = "Project";//$NON-NLS-1$

  public static final String GET_PROJECTS_COMMAND = "getProjects";//$NON-NLS-1$

  public static final String ATTRIBUTE_KEY_PARAMETERS = "parameters";//$NON-NLS-1$

  public static final String ATTRIBUT_KEY_AUTOLOGIN_VALUE = "autologin"; //$NON-NLS-1$

  public static final String ODM_DOORS_EXECUTABLE_PATH = "${odm:\\Orchestra installation\\COTS\\Doors\\Current\\Executable}"; //$NON-NLS-1$

  /**
   * URI prefix for a Doors environment URI
   */
  public static final String URI_PREFIX = "orchestra://Doors/Environment";//$NON-NLS-1$

  private Map<String, DoorsProject> _doorsProjectMap = new HashMap<String, DoorsProject>();

  private GefHandler _gefHandler;

  private StatusHandler _statusHandler;
  // Boolean used to verify if the Doors database address is updated. Used to
  // disable "finish" button on wizard.
  private boolean _updatedAddress;

  /**
   * Send a request to Doors database to retrieve all the projects and their contents
   * @return Status return by the connector
   */
  public Status accessDoorsAndRetrieveProjectsWithStatus() {
    // Construct the command to send to the Doors connector to retrieve the
    // projects and the modules
    OrchestraURI uri = getMinimalistDoorsUriToExecuteSpecificCommand();
    if (null == uri) {
      return null;
    }
    StatusDefinition statusDefinition = executeDoorsSpecificCommand(uri, GET_PROJECTS_COMMAND);
    if ((null == statusDefinition)) {
      return null;
    }

    /*
     * Status status = statusDefinition.getStatus(); if ((null == status)) { return null; } EList<Status> statusList = status.getStatus(); if (null ==
     * statusList) { return null; } Status commandStatus = statusList.get(0); if (null == commandStatus) { return null; }
     */

    StatusHandler handlerStatus = new StatusHandler();
    Status commandStatus = handlerStatus.getStatusForUri(statusDefinition, uri.getUri());
    if (null == commandStatus) {
      return null;
    }

    String gefFilePath = commandStatus.getExportFilePath();
    if (null == gefFilePath) {
      return commandStatus;
    }

    _doorsProjectMap.clear();
    GEF gef = getGefHandler().loadModel(gefFilePath);
    // Retrieve the data from the GEF file
    for (GenericExportFormat databaseContent : gef.getGENERICEXPORTFORMAT()) {
      for (Element environment : databaseContent.getELEMENT()) {
        // Get every project
        for (Element project : environment.getELEMENT()) {
          if (GEF_LABEL_PROJECT.equals(project.getType())) {
            String projectId = new OrchestraURI(project.getUri()).getObjectId();
            String name = project.getFullName();
            DoorsProject projectModel = new DoorsProject(project.getUri(), name, false, projectId, name);
            for (Element projectChild : project.getELEMENT()) {
              String childType = projectChild.getType();
              if (GEF_LABEL_FOLDER.equals(childType)) {
                DoorsFolder folder = manageFolderElement(projectModel, projectChild);
                projectModel.addFolder(folder);
              } else if (GEF_LABEL_BASELINE_SET_DEFINITION.equals(childType)) {
                BaselineSetDefinitionModel bsd = manageBaselineSetDefinitionElement(projectModel, projectChild);
                projectModel.addBaselineSetDefinition(bsd);
              }
            }
            _doorsProjectMap.put(projectId, projectModel);
          }
        }
      }
    }
    // Unload the model
    getGefHandler().unloadModel(gef);
    // The retrieval of the Doors data was successful, return true
    return commandStatus;
  }

  /**
   * There are only two pages, DoorsEnvironmentProjectAndModulePage is following the first page
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironmentHandler#doGetNextPage(com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage)
   */
  @Override
  protected AbstractEnvironmentPage doGetNextPage(AbstractEnvironmentPage currentPage_p) {
    if (DoorsEnvironmentConnectionDataPage.class.equals(currentPage_p.getClass())) {
      return new DoorsEnvironmentTreePage(this);
    }
    if (DoorsEnvironmentTreePage.class.equals(currentPage_p.getClass())) {
      return new DoorsEnvironmentProjectNamePage(this);
    }
    return null;
  }

  /**
   * Execute a specificCommand and manage the result, i.e. the return status (from response file).
   * @param uriList The Orchestra URI list to send to Doors
   * @param command_p The command to execute
   * @return The return status of Specific command, else null if failed
   */
  protected StatusDefinition executeDoorsSpecificCommand(List<OrchestraURI> uriList, final String command_p) {
    Map<String, String> commandResult = null;
    try {
      commandResult = PUCI.executeSpecificCommand(command_p, uriList);
    } catch (Exception exception_p) {
      StringBuilder loggerMessage = new StringBuilder("DoorsEnvironmentHandler.executeDoorsSpecificCommand(").append(command_p).append(") _ "); //$NON-NLS-1$ //$NON-NLS-2$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
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
    StatusDefinition statusDefinition = getStatusHandler().loadModel(responseFilePath);
    // Unload the definition
    getStatusHandler().unloadModel(statusDefinition);
    // Return the statusDefinition
    return statusDefinition;
  }

  /**
   * Execute a specificCommand and manage the result, i.e. extract the GEF file path
   * @param uri_p The Orchestra URI to search in Doors
   * @param command_p The command to execute
   * @return The return status of Specific command, else null if failed
   */
  public StatusDefinition executeDoorsSpecificCommand(OrchestraURI uri_p, final String command_p) {
    List<OrchestraURI> uriList = Collections.singletonList(uri_p);
    StatusDefinition status = executeDoorsSpecificCommand(uriList, command_p);
    return status;
  }

  /**
   * Get a String format representation of a project or module
   * @param attribut_p The project or module to convert
   * @param prefix_p A prefix to differentiate a project from a module
   * @return The String representation of the given project or module, not null
   */
  private String getAttributToStringRepresentation(String attribut_p, String prefix_p) {
    // TODO correct this to new format
    StringBuilder sb = new StringBuilder(prefix_p);
    // The various parameters are separated by a semi-colon
    String[] values = attribut_p.split(ICommonConstants.SEMI_COLON_STRING);
    for (int i = 0; i < values.length; i++) {
      // Decode each value
      values[i] = OrchestraURI.decode(values[i]);
    }
    if (null != values) {
      sb.append(": ").append(values[2]); //$NON-NLS-1$
    }

    return sb.toString();
  }

  protected Map<String, String> getBaselinesSets(Map<String, String> attributes_p) {
    Map<String, String> result = new HashMap<String, String>();
    if (null != attributes_p) {
      List<String> projectList = DoorsEnvironment.splitAttributList(attributes_p.get(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST));
      for (String project : projectList) {
        List<String> data = DoorsEnvironment.splitAttribut(project);
        if (!data.isEmpty()) {
          String baseline = attributes_p.get(DoorsEnvironment.ATTRIBUT_KEY_BASELINESET_LIST + data.get(0));
          List<String> baselineDataList = DoorsEnvironment.splitAttributList(baseline);
          for (String baselineData : baselineDataList) {
            List<String> baselineDataSeparates = DoorsEnvironment.splitAttribut(baselineData);
            String baselineUri = OrchestraURI.decode(baselineDataSeparates.get(0));
            String baselineName = OrchestraURI.decode(baselineDataSeparates.get(1));
            result.put(baselineUri, baselineName);
          }
        }
      }
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironmentHandler#getBaselineStartingPointAttributes(com.thalesgroup.orchestra.framework.environment.EnvironmentContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public Couple<Map<String, String>, IStatus> getBaselineStartingPointAttributes(UseBaselineEnvironmentContext startingPointContext_p,
      IProgressMonitor progressMonitor_p) {
    Map<String, String> startingPointAttributes = new HashMap<String, String>();
    startingPointAttributes.putAll(startingPointContext_p._attributes);

    // Get the list of the projects and get substituted baselinesets data if
    // any.
    List<ProjectEnvironmentData> projectsData =
        DoorsEnvironment.deserializeProjectList(startingPointAttributes.get(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST));
    for (ProjectEnvironmentData data : projectsData) {
      // Only the ID of the project is needed to get the key of the
      // baselineset.
      String unsubstitutedBaselineSetData = startingPointAttributes.get(DoorsEnvironment.ATTRIBUT_KEY_BASELINESET_LIST + data._doorsProjectId);
      if (null != unsubstitutedBaselineSetData) {
        // Parse and modify baselineset to current.
        List<BaselineSetEnvironmentData> bsenvdata = DoorsEnvironment.deserializeBaselineSetList(unsubstitutedBaselineSetData);
        StringBuilder doorsBaselinesString = new StringBuilder();
        for (BaselineSetEnvironmentData datafff : bsenvdata) {
          String uri = datafff._baselineSetEncodedURI;
          OrchestraURI uribaseline = new OrchestraURI(uri);

          String[] objectID = uribaseline.getObjectId().split("!"); //$NON-NLS-1$
          StringBuilder newObjectID = new StringBuilder();
          newObjectID.append(objectID[0]);
          newObjectID.append("!"); //$NON-NLS-1$
          newObjectID.append(objectID[1]);
          newObjectID.append("!"); //$NON-NLS-1$
          newObjectID.append(DoorsEnvironment.MISSING_BASELINE_PARAMETERS);
          uribaseline.setObjectData(GEF_LABEL_BASELINE_SET, newObjectID.toString());

          doorsBaselinesString.append(uribaseline.getUri());
          doorsBaselinesString.append(ICommonConstants.SEMI_COLON_CHARACTER);
          doorsBaselinesString.append(DoorsEnvironment.MISSING_BASELINE_PARAMETERS);
          doorsBaselinesString.append(ICommonConstants.COMMA_CHARACTER);

        }
        // Remove last ','
        doorsBaselinesString.deleteCharAt(doorsBaselinesString.length() - 1);
        String substitutedBaselineSetData = doorsBaselinesString.toString();
        startingPointAttributes.put(DoorsEnvironment.ATTRIBUT_KEY_BASELINESET_LIST + data._doorsProjectId, substitutedBaselineSetData);
      }
    }

    return new Couple<Map<String, String>, IStatus>(startingPointAttributes, org.eclipse.core.runtime.Status.OK_STATUS);
  }

  /**
   * Get the Doors database full address: port@name
   * @return The full address, just "@" if both values are missing
   */
  protected String getDatabaseFullAddress() {
    String adr = getDataBasePort() + "@" + getDataBaseName(); //$NON-NLS-1$

    return getVariablesHandler().getSubstitutedValue(adr);
  }

  public String getOptionalParameters() {
    String parameters = getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_PARAMETERS);
    if (null == parameters) {
      // If no value, return an empty string
      parameters = ICommonConstants.EMPTY_STRING;
    }
    return OrchestraURI.decode(parameters);
  }

  public String getOptionalAutoLogin() {
    String autoLoginOption = getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_AUTOLOGIN_VALUE);
    if (null == autoLoginOption) {
      // If no value, return an empty string
      autoLoginOption = ICommonConstants.EMPTY_STRING;
    }
    return OrchestraURI.decode(autoLoginOption);
  }

  /**
   * Retrieve the name of the database
   * @return The name of the database, if none an empty string is returned
   */
  public String getDataBaseName() {
    String result = getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_DB_NAME);
    if (null == result) {
      // If no value, return an empty string
      result = ICommonConstants.EMPTY_STRING;
    }
    return OrchestraURI.decode(result);
  }

  /**
   * Retrieve the name of the database
   * @return The port of the database, if none an empty string is returned
   */
  public String getDataBasePort() {
    String result = getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_PORT);
    if (null == result) {
      // If no value, return an empty string
      result = ICommonConstants.EMPTY_STRING;
    }
    return OrchestraURI.decode(result);
  }

  /**
   * Retrieve the description property of an element from the GEF file
   * @param properties_p The properties list where the description is
   * @return The value of the description
   */
  private String getDescriptionProperty(List<Properties> properties_p) {
    String result = ICommonConstants.EMPTY_STRING;
    if (properties_p.isEmpty()) {
      return result;
    }
    Properties firstProperties = properties_p.get(0);
    for (Property property : firstProperties.getPROPERTY()) {
      String name = property.getName();
      // Find the right tag
      if ("description".equals(name)) { //$NON-NLS-1$
        FeatureMap mixed = property.getMixed();
        if (null == mixed) {
          return result;
        }
        Object value = mixed.getValue(0);
        if (null == value) {
          return result;
        }
        result = value.toString();
        return result;
      }
    }
    return ICommonConstants.EMPTY_STRING;
  }

  /**
   * Get decoded Doors database full address from specified attributes map. No substitution is performed, returned values are raw values.
   * @param attributes_p
   * @return
   */
  protected List<String> getDoorsDatabaseAdressValues(Map<String, String> attributes_p) {
    // Precondition.
    if (null == attributes_p) {
      return Collections.emptyList();
    }
    // Get values.
    String adress = attributes_p.get(DoorsEnvironment.ATTRIBUT_KEY_PORT) + "@" + attributes_p.get(DoorsEnvironment.ATTRIBUT_KEY_DB_NAME); //$NON-NLS-1$
    if ((null == adress) || adress.equals("@")) { //$NON-NLS-1$
      return Collections.emptyList();
    }
    // Using of public method in FileSystemEnvironmentHandler to decode
    // values.
    return FileSystemEnvironmentHandler.decodeValues(adress);
  }

  /**
   * Retrieve the list of projects in the Doors Environment in the following format: projectId;projectName;baselineSet The baselineSet parameter can be absent
   * @return The list of projects in a string or an empty list if no value
   */
  public List<ProjectEnvironmentData> getDoorsEnvironmentProjectList() {
    String projects = getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST);
    return DoorsEnvironment.deserializeProjectList(projects);
  }

  /**
   * @return the doorsProjectMap
   */
  public Map<String, DoorsProject> getDoorsProjectMap() {
    return _doorsProjectMap;
  }

  /**
   * The returned map contains the following keys and values: <br>
   * - Doors_database: Key to the database full address; the value is: port@name <br>
   * - Project_X: Key to a project, X being the project unique Id, the values are: projectId, name, baselineSet; baselineSet can be absent <br>
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getExpandedAttributes(java.util.Map)
   */
  @Override
  public Map<String, List<String>> getExpandedAttributes(EnvironmentContext context_p) {
    // Old public Map<String, List<String>>
    // getExpandedAttributes(Map<String, String> attributes_p)
    Map<String, List<String>> result = new HashMap<String, List<String>>();
    // Doors database full address has to be resolved (server or port could
    // contain variables).
    result.put(DoorsEnvironmentHandler.ATTRIBUTE_KEY_DOORS_DATABASE_FULL_ADRESS, getResolvedDatabaseFullAdress(context_p._attributes));
    // Doors parameters
    result.put(DoorsEnvironmentHandler.ATTRIBUTE_KEY_PARAMETERS, getResolvedParameters(context_p._attributes.get(DoorsEnvironment.ATTRIBUT_KEY_PARAMETERS)));
    // Doors autoLogin option
    String autologValue = context_p._attributes.get(DoorsEnvironment.ATTRIBUT_KEY_AUTOLOGIN_VALUE);
    if (null != autologValue && !autologValue.isEmpty()) {
      result.put(DoorsEnvironmentHandler.ATTRIBUT_KEY_AUTOLOGIN_VALUE,
          getResolvedAutoLoginOption(context_p._attributes.get(DoorsEnvironment.ATTRIBUT_KEY_AUTOLOGIN_VALUE)));
    } else {
      List<String> resultAutoLogin = new ArrayList<String>();
      resultAutoLogin.add("false");
      result.put(DoorsEnvironmentHandler.ATTRIBUT_KEY_AUTOLOGIN_VALUE, resultAutoLogin);
    }
    // Theoretically nothing to resolve with projects and baselineSets.
    if (null != context_p._attributes) {
      List<String> projectList = DoorsEnvironment.splitAttributList(context_p._attributes.get(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST));
      for (String project : projectList) {
        List<String> data = DoorsEnvironment.splitAttribut(project);
        if (!data.isEmpty()) {
          result.put(DoorsEnvironmentHandler.ATTRIBUTE_KEY_PARTIAL_PROJECT + data.get(0), getResolvedDoorsProject(project));
        }
      }
    }
    return result;
  }

  /**
   * @param string_p
   * @return
   */
  protected List<String> getResolvedAutoLoginOption(String autoLoginOption) {
    // Precondition.
    if (null == autoLoginOption) {
      return Collections.emptyList();
    }
    IVariablesHandler handler = getVariablesHandler();
    List<String> decodedautoLoginOptionValue = FileSystemEnvironmentHandler.decodeValues(autoLoginOption);
    if (null == handler) {
      return decodedautoLoginOptionValue;
    }
    // Try and substitute values.
    List<String> resolvedDoorsdecodedautoLoginOptionValue = new ArrayList<String>(decodedautoLoginOptionValue.size());
    for (String data : decodedautoLoginOptionValue) {
      resolvedDoorsdecodedautoLoginOptionValue.add(handler.getSubstitutedValue(data));
    }
    return resolvedDoorsdecodedautoLoginOptionValue;
  }

  protected List<String> getResolvedParameters(String parameters) {
    // Precondition.
    if (null == parameters) {
      return Collections.emptyList();
    }
    IVariablesHandler handler = getVariablesHandler();
    List<String> decodedParametersValue = FileSystemEnvironmentHandler.decodeValues(parameters);
    if (null == handler) {
      return decodedParametersValue;
    }
    // Try and substitute values.
    List<String> resolvedDoorsParameters = new ArrayList<String>(decodedParametersValue.size());
    for (String data : decodedParametersValue) {
      resolvedDoorsParameters.add(handler.getSubstitutedValue(data));
    }
    return resolvedDoorsParameters;
  }

  /**
   * The first page for Doors Environment handler is the DoorsEnvironmentConnectionPage where the Doors database address is given
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getFirstPage()
   */
  @Override
  public AbstractEnvironmentPage getFirstPage() {
    // The first page to return.
    return new DoorsEnvironmentConnectionDataPage(this);
  }

  /**
   * Get the GefHandler
   * @return The GEF handler, not null
   */
  public GefHandler getGefHandler() {
    if (null == _gefHandler) {
      _gefHandler = new GefHandler();
    }
    return _gefHandler;
  }

  /**
   * Get a minimalist uri to execute specific command. The minimalist uri need 2 arguments: databaseFullAddress & Doors executable path.
   * @Return minimalistDoorsUriToExecuteSpecificCommand
   */
  public OrchestraURI getMinimalistDoorsUriToExecuteSpecificCommand() {
    OrchestraURI minimalistDoorsUriToExecuteSpecificCommand = new OrchestraURI(URI_PREFIX);
    minimalistDoorsUriToExecuteSpecificCommand.addParameter(DATABASE, getDatabaseFullAddress());
    // Get handler.
    IVariablesHandler handler = getVariablesHandler();
    // Get Doors Executable path
    String doorsPath = handler.getSubstitutedValue(ODM_DOORS_EXECUTABLE_PATH);
    // Add Doors Executable path (needed when there is no current context
    // yet in Orchestra).
    if ((null == doorsPath) || ODM_DOORS_EXECUTABLE_PATH.equals(doorsPath)) {
      return null;
    }
    minimalistDoorsUriToExecuteSpecificCommand.addParameter(DOORS_EXECUTABLE_PATH, doorsPath);
    // Add autoLogin option
    if (!ICommonConstants.EMPTY_STRING.equals(getOptionalAutoLogin())) {
      String autoLoginOption = handler.getSubstitutedValue(getOptionalAutoLogin());
      minimalistDoorsUriToExecuteSpecificCommand.addParameter(ATTRIBUT_KEY_AUTOLOGIN_VALUE, autoLoginOption);
    } else
      // If autologin value doesn't exist (i.e. odler versions of Orchestra) it should be false.
      minimalistDoorsUriToExecuteSpecificCommand.addParameter(ATTRIBUT_KEY_AUTOLOGIN_VALUE, Boolean.FALSE.toString());
    // Add parameters if any
    if (!ICommonConstants.EMPTY_STRING.equals(getOptionalParameters())) {
      String doorsParameters = handler.getSubstitutedValue(getOptionalParameters());
      minimalistDoorsUriToExecuteSpecificCommand.addParameter(ATTRIBUTE_KEY_PARAMETERS, doorsParameters);
    }
    return minimalistDoorsUriToExecuteSpecificCommand;
  }

  /**
   * Get a minimalist uri to execute specific command with given port and database. The minimalist uri need 2 arguments: databaseFullAddress & Doors executable
   * path.
   * @Return minimalistDoorsUriToExecuteSpecificCommand
   */
  public OrchestraURI getMinimalistDoorsUriToExecuteSpecificCommand(String address_p) {
    OrchestraURI minimalistDoorsUriToExecuteSpecificCommand = new OrchestraURI(URI_PREFIX);
    minimalistDoorsUriToExecuteSpecificCommand.addParameter(DATABASE, address_p);
    // Get handler.
    IVariablesHandler handler = getVariablesHandler();
    // Get Doors Executable path
    String doorsPath = handler.getSubstitutedValue(ODM_DOORS_EXECUTABLE_PATH);
    // Add Doors Executable path (needed when there is no current context
    // yet in Orchestra).
    if ((null == doorsPath) || ODM_DOORS_EXECUTABLE_PATH.equals(doorsPath)) {
      return null;
    }
    minimalistDoorsUriToExecuteSpecificCommand.addParameter(DOORS_EXECUTABLE_PATH, doorsPath);
    // Add autoLogin option
    if (!ICommonConstants.EMPTY_STRING.equals(getOptionalAutoLogin())) {
      String autoLoginOption = handler.getSubstitutedValue(getOptionalAutoLogin());
      minimalistDoorsUriToExecuteSpecificCommand.addParameter(ATTRIBUT_KEY_AUTOLOGIN_VALUE, autoLoginOption);
    } else
      // If autologin value doesn't exist (i.e. odler versions of Orchestra) it should be false.
      minimalistDoorsUriToExecuteSpecificCommand.addParameter(ATTRIBUT_KEY_AUTOLOGIN_VALUE, Boolean.FALSE.toString());
    // Add parameters if any
    if (!ICommonConstants.EMPTY_STRING.equals(getOptionalParameters())) {
      String doorsParameters = handler.getSubstitutedValue(getOptionalParameters());
      minimalistDoorsUriToExecuteSpecificCommand.addParameter(ATTRIBUTE_KEY_PARAMETERS, doorsParameters);
    }
    return minimalistDoorsUriToExecuteSpecificCommand;
  }

  /**
   * Get decoded project from specified project parameter. No substitution is performed, returned values are raw values.
   * @param attributes_p
   * @return
   */
  protected List<String> getProjectValues(String project) {
    // Precondition.
    if (null == project) {
      return Collections.emptyList();
    }
    // Using of public method in FileSystemEnvironmentHandler to decode
    // values.
    return FileSystemEnvironmentHandler.decodeValues(project);
  }

  /**
   * Get decoded and substituted doors database full address from specified attributes map.
   * @param attributes_p
   * @return The provided raw attributes if no handler could be found, the resolved & substituted list otherwise.
   */
  protected List<String> getResolvedDatabaseFullAdress(Map<String, String> attributes_p) {
    // Get handler.
    IVariablesHandler handler = getVariablesHandler();
    // Get existing directories values.
    List<String> doorsDatabaseAdressValues = getDoorsDatabaseAdressValues(attributes_p);
    // Precondition : nothing can be done if a variables handler isn't
    // available.
    if (null == handler) {
      return doorsDatabaseAdressValues;
    }
    // Try and substitute values.
    List<String> resolvedDoorsDatabaseFullAdress = new ArrayList<String>(doorsDatabaseAdressValues.size());
    for (String fullAdress : doorsDatabaseAdressValues) {
      resolvedDoorsDatabaseFullAdress.add(handler.getSubstitutedValue(fullAdress));
    }
    return resolvedDoorsDatabaseFullAdress;
  }

  /**
   * Get decoded and substituted doors project data from specified list.
   * @param project
   * @return The provided raw attributes if no handler could be found, the resolved & substituted list otherwise.
   */
  protected List<String> getResolvedDoorsProject(String project) {
    // Get handler.
    IVariablesHandler handler = getVariablesHandler();
    // Decode values in project.
    List<String> decodedProject = getProjectValues(project);
    // Precondition : nothing can be done if a variables handler isn't
    // available.
    if (null == handler) {
      return decodedProject;
    }
    // Try and substitute values.
    List<String> resolvedDoorsProject = new ArrayList<String>(decodedProject.size());
    for (String data : decodedProject) {
      resolvedDoorsProject.add(handler.getSubstitutedValue(data));
    }
    return resolvedDoorsProject;
  }

  /**
   * Get the StatusHandler
   * @return The status handler, not null
   */
  private StatusHandler getStatusHandler() {
    if (null == _statusHandler) {
      _statusHandler = new StatusHandler();
    }
    return _statusHandler;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getUncompressedAttributes(java.util.Map)
   */
  public Map<String, List<String>> getUncompressedAttributes(EnvironmentContext context_p) {
    Map<String, List<String>> result = new HashMap<String, List<String>>();
    // Doors database full address has to be resolved (server or port could
    // contain variables).
    result.put(DoorsEnvironmentHandler.ATTRIBUTE_KEY_DOORS_DATABASE_FULL_ADRESS, getDoorsDatabaseAdressValues(context_p._attributes));
    // Theoretically nothing to resolve with projects and baselineSets.
    if (null != context_p._attributes) {
      List<String> projectList = DoorsEnvironment.splitAttributList(context_p._attributes.get(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST));
      for (String project : projectList) {
        List<String> data = DoorsEnvironment.splitAttribut(project);
        if (!data.isEmpty()) {
          result.put(DoorsEnvironmentHandler.ATTRIBUTE_KEY_PARTIAL_PROJECT + data.get(0), getProjectValues(project));
        }
      }
    }
    return result;
  }

  /**
   * The creation is complete when the port and the name of the database have been given and at least one project has been selected (with or without a
   * baselineSet)
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#isCreationComplete()
   */
  @Override
  public IStatus isCreationComplete() {
    // Creation complete when name, port and at least one project (with or
    // without baselineSet) has been selected.
    List<ProjectEnvironmentData> projectList = getDoorsEnvironmentProjectList();
    // If a project is present
    boolean hasProject = !projectList.isEmpty();

    // Check that the attributes contains a name and a port
    boolean complete = (!ICommonConstants.EMPTY_STRING.equals(getDataBaseName())) && (!ICommonConstants.EMPTY_STRING.equals(getDataBasePort())) && hasProject;
    // If the create is complete, return an OK status, else a CANCEL status
    if (complete && !_updatedAddress) {
      return org.eclipse.core.runtime.Status.OK_STATUS;
    }
    return org.eclipse.core.runtime.Status.CANCEL_STATUS;
  }

  /**
   * @return the updatedAddress
   */
  public boolean isUpdatedAddress() {
    return _updatedAddress;
  }

  /**
   * Parse a GEF {@link Element} to create a {@link BaselineSetDefinitionModel} it represents
   * @param element_p The element to read
   * @return The created baselineSetDefinition
   */
  private BaselineSetDefinitionModel manageBaselineSetDefinitionElement(AbstractDoorsContainer parent_p, Element element_p) {
    String bsdUri = new OrchestraURI(element_p.getUri()).getObjectId();
    String name = element_p.getFullName();
    String description = getDescriptionProperty(element_p.getPROPERTIES());
    BaselineSetDefinitionModel bsdModel = new BaselineSetDefinitionModel(parent_p, bsdUri, name, false);
    bsdModel.setDescription(description);
    for (Element projectChild : element_p.getELEMENT()) {
      if (GEF_LABEL_BASELINE_SET.equals(projectChild.getType())) {
        // Parse all baselineSet
        BaselineSetModel bsd = manageBaselineSetElement(bsdModel, projectChild);
        bsdModel.addBaselineSet(bsd);
      }
    }
    return bsdModel;
  }

  /**
   * Parse a GEF {@link Element} to create a {@link BaselineSetModel} it represents
   * @param element_p The element to read
   * @return The created baselineSet
   */
  private BaselineSetModel manageBaselineSetElement(BaselineSetDefinitionModel parent_p, Element element_p) {
    String baselineSetUri = element_p.getUri();
    String name = element_p.getFullName();
    String description = getDescriptionProperty(element_p.getPROPERTIES());
    BaselineSetModel baselineSetModel = new BaselineSetModel(parent_p, baselineSetUri, name, false);
    baselineSetModel.setDescription(description);
    return baselineSetModel;
  }

  /**
   * Parse a GEF {@link Element} to create the folder {@link ProjectModel} it represents
   * @param element_p The element to read
   * @return The created folder
   */
  private DoorsFolder manageFolderElement(AbstractDoorsContainer parent_p, Element element_p) {
    String folderUri = new OrchestraURI(element_p.getUri()).getObjectId();
    String name = element_p.getFullName();
    DoorsFolder folderModel = new DoorsFolder(parent_p, folderUri, name, false);
    for (Element projectChild : element_p.getELEMENT()) {
      String childType = projectChild.getType();
      if (GEF_LABEL_FOLDER.equals(childType)) {
        // Parse all sub-folders
        DoorsFolder folder = manageFolderElement(folderModel, projectChild);
        folderModel.addFolder(folder);
      } else if (GEF_LABEL_BASELINE_SET_DEFINITION.equals(childType)) {
        // Parse all baselineSetDefinitions
        BaselineSetDefinitionModel bsd = manageBaselineSetDefinitionElement(folderModel, projectChild);
        folderModel.addBaselineSetDefinition(bsd);
      }
    }

    // Consolidate the folder
    // for (Element projectChild : element_p.getELEMENT()) {
    // BaselineSetDefinitionModel bsd =
    // manageBaselineSetDefinitionElement(projectChild);
    // folderModel.addBaselineSetDefinition(bsd);
    // }
    return folderModel;
  }

  /**
   * @param updatedAddress the updatedAddress to set
   */
  public void setUpdatedAddress(boolean updatedAddress) {
    _updatedAddress = updatedAddress;
  }

  /**
   * String representation of a DoorsEnvironment: DOORS (Port@Name) : P ProjectName (baselineSet) M ModuleName (baseline)...
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#toString(java.util.Map)
   */
  @Override
  public String toString(Map<String, String> attributes_p) {
    // TODO change this
    // The returned ToString result will display the database address and
    // the name of each project, with their baselineSet if any, with each
    // associated module
    // with their baseline if any
    String port = null;
    String hostName = null;
    List<String> projectList = null;
    // Get handler.
    IVariablesHandler handler = getVariablesHandler();
    // Parse all the attributes values and display them
    for (String key : attributes_p.keySet()) {
      String value = attributes_p.get(key);
      if (DoorsEnvironment.ATTRIBUT_KEY_PORT.equals(key)) {
        // Retrieve the port value
        port = OrchestraURI.decode(value);
        if (null != handler) {
          port = handler.getSubstitutedValue(port);
        }
        // port = getVariablesHandler().getSubstitutedValue(port);
      } else if (DoorsEnvironment.ATTRIBUT_KEY_DB_NAME.equals(key)) {
        // Retrieve the host name value
        hostName = OrchestraURI.decode(value);
        if (null != handler) {
          hostName = handler.getSubstitutedValue(hostName);
        }
        // hostName =
        // getVariablesHandler().getSubstitutedValue(hostName);
      } else if (DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST.equals(key)) {
        // Retrieve the projects
        projectList = DoorsEnvironment.splitAttributList(attributes_p.get(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST));
      }
    }
    // Build the attributes list containing the projects and their related
    // modules
    List<String> attributesList = new ArrayList<String>();
    if(null != projectList){  
	    for (String project : projectList) {
	      String stringProject = getAttributToStringRepresentation(project, "P");//$NON-NLS-1$
	      stringProject = OrchestraURI.decode(stringProject);
	      if (null != handler) {
	        stringProject = handler.getSubstitutedValue(stringProject);
	      }
	      // stringProject =
	      // getVariablesHandler().getSubstitutedValue(stringProject);
	      attributesList.add(stringProject);
	    }
    }
    return MessageFormat.format(Messages.DoorsEnvironmentHandler_ToString_Pattern, port, hostName, attributesList);
  }

  /**
   * Retrieve the name of the database
   * @return The name of the database, if none an empty string is returned
   */
  public String getAutoLoginValue() {
    String result = getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_AUTOLOGIN_VALUE);
    if (null == result) {
      // If no value, return an empty string
      result = ICommonConstants.EMPTY_STRING;
    }
    return OrchestraURI.decode(result);
  }
}
