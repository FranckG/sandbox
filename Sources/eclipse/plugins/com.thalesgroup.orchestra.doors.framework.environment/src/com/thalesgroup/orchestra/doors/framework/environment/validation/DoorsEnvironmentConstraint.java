/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.validation;

import java.io.IOError;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironmentHandler;
import com.thalesgroup.orchestra.doors.framework.environment.ui.Messages;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentAttributes;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.validation.AbstractEnvironmentRule;
import com.thalesgroup.orchestra.framework.environment.validation.EnvironmentRuleContext;

import framework.orchestra.thalesgroup.com.VariableManagerAPI.VariableManagerAdapter;

import javax.xml.rpc.ServiceException;

/**
 * @author S0032874 <br>
 *         Constraint for Doors Environment to ensure that no environment is created with a project logical which is already used by another environment. It
 *         also ensures that the same project is not present in two different environments but with different logical names. <br>
 *         This last check does not apply if the environments are from distinct Doors database.
 */
public class DoorsEnvironmentConstraint extends AbstractEnvironmentRule {

  private static final String CONNECTORDOORS_INSTALLLOCATION = "${odm:\\Orchestra installation\\Products\\ConnectorDOORS\\InstallLocation}";

  /**
   * DOORS Environment unique ID
   */
  private final String _doorsEnvironmentId = "com.thalesgroup.orchestra.doors.framework.environment"; //$NON-NLS-1$

  /**
   * List of Doors arguments allowed
   */
  private static final Map<String, String> DOORS_ALLOWED_ARGUMENTS = new HashMap<String, String>() {
    private static final long serialVersionUID = 1L;
    {
      put("-a", "Addins");
      put("-addins", "Addins");
      put("-A", "AttributeAddins");
      put("-attributeaddins", "AttributeAddins");
      put("-k", "Caching");
      put("-caching", "Caching");
      put("-certName", "CertName");
      put("-o", "DefOpenMode");
      put("-defopenmode", "DefOpenMode");
      put("-O", "DefOpenLinkMode");
      put("-defopenlinkmode", "DefOpenLinkMode");
      put("-L", "LayoutAddins");
      put("-layoutaddins", "LayoutAddins");
      put("-f", "LocalData");
      put("-localdata", "LocalData");
      put("-l", "LogFile");
      put("-logfile", "LogFile");
    }
  };

  /**
   * List of allowed values for defopenmode and defopenlinkmode options
   */
  private static final List<String> DOORS_DEFOPENLMODE = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
      add("READ_ONLY");
      add("READ_WRITE");
      add("READ_WRITE_SHARED");
      add("r");
      add("w");
      add("s");
    }
  };

  /**
   * Add the conflicting projects to the conflict map
   * @param commonProjects_p The conflicting project names
   * @param conflictMap_p The conflicts map
   * @param firstEnv_p The first environment
   * @param secondEnv_p The second environment
   */
  private void addCommonProjectConflicts(List<String> commonProjects_p, Map<EnvironmentAttributes, Map<EnvironmentAttributes, List<String>>> conflictMap_p,
      EnvironmentAttributes firstEnv_p, EnvironmentAttributes secondEnv_p) {
    if (!commonProjects_p.isEmpty()) {
      // Set the conflict map
      // First environment
      // Get the first environment conflict map
      Map<EnvironmentAttributes, List<String>> firstCommonProjectMap = conflictMap_p.get(firstEnv_p);
      // If this environment has no previous conflict map, create it
      if (null == firstCommonProjectMap) {
        firstCommonProjectMap = new HashMap<EnvironmentAttributes, List<String>>();
        conflictMap_p.put(firstEnv_p, firstCommonProjectMap);
      }
      // Store the conflicting environment and the list of common projects
      List<String> firstCommonList = new ArrayList<String>();
      firstCommonList.addAll(commonProjects_p);
      firstCommonProjectMap.put(secondEnv_p, firstCommonList);
      // Second environment
      // Get the second environment conflict map
      Map<EnvironmentAttributes, List<String>> secondCommonProjectMap = conflictMap_p.get(secondEnv_p);
      // If this environment has no previous conflict map, create it
      if (null == secondCommonProjectMap) {
        secondCommonProjectMap = new HashMap<EnvironmentAttributes, List<String>>();
        conflictMap_p.put(secondEnv_p, secondCommonProjectMap);
      }
      // Store the conflicting environment and the list of common projects
      List<String> secondCommonList = new ArrayList<String>();
      secondCommonList.addAll(commonProjects_p);
      secondCommonProjectMap.put(firstEnv_p, secondCommonList);
    }
  }

  /**
   * Check the unicity of the project names in a Doors Environment
   * @param projectList_p The list of projects to test
   * @param env_p The tested environment
   */
  private void checkEnvironmentProjectsUnicity(List<String> projectList_p, EnvironmentAttributes env_p) {
    int size = projectList_p.size();
    for (int index = 0; index < size; index++) {
      String project = projectList_p.get(index);
      for (int i = index + 1; i < size; i++) {
        // If another project has the same name
        if (project.equals(projectList_p.get(i))) {
          addFailure(env_p, MessageFormat.format(Messages.DoorsEnvironment_Validation_Message_Same_Logical_Name_In_Environment, project));
        }
      }
    }
  }

  /**
   * Display the conflicting projects
   * @param conflictMap_p The map of environment and the related conflicting projects name
   * @param errorMessage_p The error message to display
   */
  private void displayCommonProjectsError(Map<EnvironmentAttributes, Map<EnvironmentAttributes, List<String>>> conflictMap_p, String errorMessage_p) {
    // For each environment
    for (EnvironmentAttributes envKey : conflictMap_p.keySet()) {
      // Environment to which the error is linked
      // Not used for now
      // String firstString = getEnvironmentStringRepresentation(envKey);
      Map<EnvironmentAttributes, List<String>> conflictEnv = conflictMap_p.get(envKey);
      if (null == conflictEnv) {
        continue;
      }
      // For each conflicting environment, add an error
      for (Map.Entry<EnvironmentAttributes, List<String>> entry : conflictEnv.entrySet()) {
        EnvironmentAttributes env = entry.getKey();
        // Conflicting environment
        String secondString = getEnvironmentStringRepresentation(env);
        // List of common projects
        String commonProjects = entry.getValue().toString();
        // Add error
        addFailure(envKey, MessageFormat.format(errorMessage_p, commonProjects, secondString));
      }
    }
  }

  /**
   * Check all Doors arguments
   * @param doorsLaunchArgsValue_p The arguments string
   * @param env_p The Doors environment
   */
  private void checkArguments(String doorsLaunchArgsValue_p, EnvironmentAttributes env_p) {
    if (doorsLaunchArgsValue_p.length() == 0) {
      return;
    }
    List<String> illegalOptionsForDoors = new ArrayList<String>();
    Map<String, String> optionArguments = parseArgumentString(doorsLaunchArgsValue_p, illegalOptionsForDoors);

    if (!illegalOptionsForDoors.isEmpty()) {
      for (String illegalOption : illegalOptionsForDoors) {
        addFailure(env_p, MessageFormat.format(Messages.DoorsArgumentIsNotSupported, illegalOption));
      }
    }
    for (Entry<String, String> entry : optionArguments.entrySet()) {
      String value = entry.getValue();
      switch (entry.getKey()) {
        case "Addins":
          checkAddinsArgument(value, env_p);
        break;
        case "AttributeAddins":
          checkAttributeAddinsArgument(value, env_p);
        break;
        case "CertName":
          checkCertNameArgument(value, env_p);
        break;
        case "DefOpenMode":
          checkDefOpenModeArgument(value, env_p);
        break;
        case "DefOpenLinkMode":
          checkDefOpenModeArgument(value, env_p);
        break;
        case "LocalData":
          checkLocalDataArgument(value, env_p);
        break;
        case "LogFile":
          checkLogFileArgument(value, env_p);
        break;
        default:
        break;
      }
    }
  }

  /**
   * Check is the path of the log file is a valid path
   * @param logfilePath_p The log file path
   * @param env_p The Doors environment
   */
  private void checkLogFileArgument(String logfilePath_p, EnvironmentAttributes env_p) {
    isFilenameValid(logfilePath_p, false, env_p);
  }

  /**
   * Check is the file is a valid file
   * @param file_p The file path
   * @param isDirectory_p true, if verifies that file_p is e valid directory, false otherwise
   * @param env_p The Doors environment
   * @return true if the file path is valid, otherwise false.
   */
  private boolean isFilenameValid(String file_p, boolean isDirectory_p, EnvironmentAttributes env_p) {
    if (null == file_p || file_p.isEmpty()) {
      return false;
    }
    Path path = Paths.get(file_p);
    try {
      path.toAbsolutePath();
    } catch (IOError e) {
      if (isDirectory_p) {
        addFailure(env_p, MessageFormat.format(Messages.DoorsArgumentDirectortIsNotValid, file_p));
      } else {
        addFailure(env_p, MessageFormat.format(Messages.DoorsArgumentPathIsNotValidForLogFile, file_p));
      }
      return false;
    }
    if (isDirectory_p) {
      if (!Files.exists(path)) {
        addFailure(env_p, MessageFormat.format(Messages.DoorsArgumentDirectoryIsNotExist, file_p));
        return false;
      }
      if (!Files.isDirectory(path) || path.getParent() == null) {
        addFailure(env_p, MessageFormat.format(Messages.DoorsArgumentDirectortIsNotValid, file_p));
        return false;
      }
    }
    return true;
  }

  /**
   * Check if the argument for local data option is valid
   * @param value_p The argument for local data option
   * @param env_p The Doors environment
   */
  private void checkLocalDataArgument(String value_p, EnvironmentAttributes env_p) {
    isFilenameValid(value_p, true, env_p);
  }

  /**
   * Check if the argument of the defopenmode or defopenlinkmode option is valid
   * @param value_p The value of the argument of the defopenmode/defopenlinkmode option
   * @param env_p The Doors environment
   */
  private void checkDefOpenModeArgument(String value_p, EnvironmentAttributes env_p) {
    if (!DOORS_DEFOPENLMODE.contains(value_p)) {
      addFailure(env_p, Messages.DoorsArgumentDefOpenModeIncorrect);
    }
  }

  /**
   * Check if the option certName don't have argument
   * @param value_p The argument
   * @param env_p The Doors environment
   */
  private void checkCertNameArgument(String value_p, EnvironmentAttributes env_p) {
    if (null == value_p || value_p.isEmpty()) {
      addFailure(env_p, MessageFormat.format(Messages.DoorsArgumentEmpty, "-certName"));
    }
  }

  /**
   * @param value
   * @param env_p The Doors environment
   */
  private void checkAttributeAddinsArgument(String value_p, EnvironmentAttributes env_p) {
    if (null == splitAndCheckPathArgument(value_p, env_p)) {
      addFailure(env_p, MessageFormat.format(Messages.DoorsArgumentEmpty, "-attributeaddins"));
    }
  }

  /**
   * @param value
   * @param env_p The Doors environment
   */
  private void checkAddinsArgument(String value_p, EnvironmentAttributes env_p) {
    List<String> listOfValidPath = splitAndCheckPathArgument(value_p, env_p);
    if (null == listOfValidPath) {
      addFailure(env_p, MessageFormat.format(Messages.DoorsArgumentEmpty, "-addins"));
      return;
    }
    // Get DoorsEnvironnementHandler.
    Couple<IStatus, IEnvironmentHandler> environmentHandlerCouple =
        EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentHandler(_doorsEnvironmentId);
    DoorsEnvironmentHandler doorsEnvironmentHandler = null;
    // Precondition on handler.
    if (environmentHandlerCouple.getKey().isOK() || (environmentHandlerCouple.getValue() instanceof DoorsEnvironmentHandler)) {
      doorsEnvironmentHandler = (DoorsEnvironmentHandler) environmentHandlerCouple.getValue();
    }
    if (null != doorsEnvironmentHandler) {
      String pathToConnectorDoors = doorsEnvironmentHandler.getVariablesHandler().getSubstitutedValue(CONNECTORDOORS_INSTALLLOCATION) + "\\Doors";
      if (!listOfValidPath.contains(pathToConnectorDoors)) {
        addFailure(env_p, MessageFormat.format(Messages.DoorsArgumentAddinsMustContainsThePathToDoorsConnectorInstallation, pathToConnectorDoors));
      }
    }
  }

  /**
   * Split and verifying that all path are valid and are directory.
   * @param value_p The string of paths separated by ";"
   * @param env_p The Doors environment
   * @return the list of valid path
   */
  private List<String> splitAndCheckPathArgument(String value_p, EnvironmentAttributes env_p) {
    if (null == value_p) {
      return null;
    }
    String[] tmp = value_p.replaceAll("\"", "").split(";");
    List<String> result = new ArrayList<String>();
    for (String path : tmp) {
      if (isFilenameValid(path, true, env_p)) {
        result.add(path);
      }
    }
    return result;
  }

  /**
   * Transform the Doors launch arguments to a map by arguments
   * @param doorsLaunchArgsValue_p
   * @param illegalOptionsForDoors_p
   * @return A map with Key = option and Value = arguments for the option.
   */
  private Map<String, String> parseArgumentString(String doorsLaunchArgsValue_p, List<String> illegalOptionsForDoors_p) {
    if (null == doorsLaunchArgsValue_p || doorsLaunchArgsValue_p.length() == 0) {
      return null;
    }
    Map<String, String> optionArguments = new HashMap<String, String>();
    String[] tmp = doorsLaunchArgsValue_p.split("-");
    String option;
    String arguments;
    for (int i = 0; i < tmp.length; i++) {
      if (tmp[i].length() == 0) {
        continue;
      }
      tmp[i] = "-" + tmp[i];
      option = ICommonConstants.EMPTY_STRING;
      arguments = ICommonConstants.EMPTY_STRING;
      String[] tmp2 = tmp[i].split(" ", 2);
      if (tmp2[0].length() > 1) {
        option = getOptionName(tmp2[0]);
        if (null != option) {
          if (tmp2.length > 1 && tmp2[0].length() > 0) {
            arguments = tmp2[1].trim();
          }
          if (optionArguments.containsKey(option)) {
            illegalOptionsForDoors_p.add(tmp[i]);
          } else {
            optionArguments.put(option, arguments);
          }
        } else {
          illegalOptionsForDoors_p.add(tmp[i]);
        }
      } else {
        illegalOptionsForDoors_p.add(tmp[i]);
      }
    }
    return optionArguments;
  }

  /**
   * Get the name of the option parameter
   * @param option_p
   * @return The name of the option parameter, null if the option is not exists or is not managed by Orchestra
   */
  private String getOptionName(String option_p) {
    if (DOORS_ALLOWED_ARGUMENTS.containsKey(option_p)) {
      return DOORS_ALLOWED_ARGUMENTS.get(option_p);
    }
    return null;
  }

  /**
   * Test that two environments do not have the same project name An error is returned for each environment that has a conflict, i.e. a common project will
   * generate two errors, one for each environment
   * @see com.thalesgroup.orchestra.framework.environment.validation.AbstractEnvironmentRule#doValidate()
   */
  @Override
  protected void doValidate() {
    // Get all DoorsEnvironmentContexts
    Collection<EnvironmentRuleContext> contexts = getContextsFor(_doorsEnvironmentId);
    for (EnvironmentRuleContext envRulecontext : contexts) {
      // Retrieve all the DoorsEnvironments
      List<EnvironmentAttributes> environmentList = envRulecontext._attributes;

      // Orchestra segment value
      String orchestraSegment = null;
      try {
        // Retrieve orchestra segment value (Gold, Silver, Bronze)
        orchestraSegment = VariableManagerAdapter.getInstance().getSubstitutedValue(DoorsEnvironment.ODM_PATH_SEGMENT_VALUE);
      } catch (RemoteException exception_p) {

      } catch (ServiceException exception_p) {

      }

      if ((null == orchestraSegment)) {
        // Assuming descending compatibility, if segment name doesn't exist, the solution is Gold.
        orchestraSegment = DoorsEnvironment.GOLD;
      }

      int size = environmentList.size();

      if (orchestraSegment.equals(DoorsEnvironment.SILVER) && size > 1) {
        addFailure(environmentList.get(0), "Only one Doors environment is supported in Silver segment");
      }

      // Logical name Conflict map: Map<this_Environment,
      // Map<Environment_with_project_in_conflict,
      // List<Common_projects_two_both_environment>>
      Map<EnvironmentAttributes, Map<EnvironmentAttributes, List<String>>> envLogicalNameConflictMap =
          new HashMap<EnvironmentAttributes, Map<EnvironmentAttributes, List<String>>>();
      // Real name Conflict map: Map<this_Environment,
      // Map<Environment_with_project_in_conflict,
      // List<Common_projects_two_both_environment>>
      Map<EnvironmentAttributes, Map<EnvironmentAttributes, List<String>>> envRealNameConflictMap =
          new HashMap<EnvironmentAttributes, Map<EnvironmentAttributes, List<String>>>();

      // Find all the environments which have conflicts
      // Parse each value in the ArtifactPath variable
      for (int i = 0; i < size; i++) {
        EnvironmentAttributes firstEnvValue = environmentList.get(i);
        // Get the arguments attribute
        String doorsArguments = getDoorsArguments(firstEnvValue);
        // Check Arguments line for Doors
        checkArguments(doorsArguments, firstEnvValue);
        // Get the list of project logical names from this environment
        List<String> firstProjectLogicalNameList = getDoorsProjectList(firstEnvValue, true);
        // Get the list of project real names from this environment
        List<String> firstProjectRealNameList = getDoorsProjectList(firstEnvValue, false);
        // First test the logical names between each other in the
        // environment
        checkEnvironmentProjectsUnicity(firstProjectLogicalNameList, firstEnvValue);
        // Parse each value in the ArtifactPath variable which is
        // "after" the first value
        for (int j = i + 1; j < size; j++) {
          EnvironmentAttributes secondEnvValue = environmentList.get(j);
          // If the two environment share the same DOORS database else
          // no check is required
          // Get the list of project logical names from this
          // environment
          List<String> secondProjectLogicalNameList = getDoorsProjectList(secondEnvValue, true);
          // Check if the two environments have projects with the same
          // logical names in common
          List<String> commonLogicalProjects = getCommonValues(firstProjectLogicalNameList, secondProjectLogicalNameList);
          // Get the list of project real names from this environment
          List<String> secondProjectRealNameList = getDoorsProjectList(secondEnvValue, false);
          // Check if the two environments have projects with the same
          // real names in common
          List<String> commonRealProjects = getCommonValues(firstProjectRealNameList, secondProjectRealNameList);

          addCommonProjectConflicts(commonLogicalProjects, envLogicalNameConflictMap, firstEnvValue, secondEnvValue);
          // Do not check for real name if not coming from the same
          // database
          if (isSameDatabase(firstEnvValue, secondEnvValue)) {
            addCommonProjectConflicts(commonRealProjects, envRealNameConflictMap, firstEnvValue, secondEnvValue);
          }
        }
      }
      displayCommonProjectsError(envLogicalNameConflictMap, Messages.DoorsEnvironment_Validation_Message_Same_Logical_Name_In_Two_Environment);
      displayCommonProjectsError(envRealNameConflictMap, Messages.DoorsEnvironment_Validation_Message_Same_Real_Name_In_Two_Environment);
    }
  }

  /**
   * Get the arguments attribute of an environment
   * @param env_p The {@link EnvironmentRuleContext.EnvironmentAttributes} to search
   * @return The arguments string for Doors, may be empty but not null
   */
  private String getDoorsArguments(EnvironmentAttributes env_p) {
    String result = ICommonConstants.EMPTY_STRING;
    try {
      if (null != env_p._expandedAttributes) {
        result = env_p._expandedAttributes.get(DoorsEnvironmentHandler.ATTRIBUTE_KEY_PARAMETERS).get(0);
      }
    } catch (Exception e) {
      // DO NOTHING
    }
    return result;
  }

  /**
   * Check if the two lists have values in common
   * @param firstList_p The first list
   * @param secondList_p The second list
   * @return A list of common values, not null but may be empty if no common value
   */
  private List<String> getCommonValues(List<String> firstList_p, List<String> secondList_p) {
    List<String> result = new ArrayList<String>();
    // For each values in the first list, check if it is also present in the
    // second list
    for (String name : firstList_p) {
      if (secondList_p.contains(name)) {
        result.add(name);
      }
    }
    return result;
  }

  /**
   * Get the list of project name (root artifact) of an environment
   * @param env_p The {@link EnvironmentRuleContext.EnvironmentAttributes} to search
   * @param isLogicalName_p True if the logical names must be returned, false if the real names must be returned
   * @return The list of project name, may be empty but not null
   */
  private List<String> getDoorsProjectList(EnvironmentAttributes env_p, boolean isLogicalName_p) {
    List<String> result = new ArrayList<String>();
    if (null == env_p._attributes) {
      return result;
    }
    String projectAttribute = env_p._attributes.get(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST);
    if (null == projectAttribute) {
      return result;
    }
    return DoorsEnvironment.getProjectNames(projectAttribute, isLogicalName_p);
  }

  /**
   * Retrieve the String representation of an environment using its handler
   * @param env_p The {@link EnvironmentRuleContext.EnvironmentAttributes}
   * @return The String representation of the environment, not null but may be an empty String
   */
  @SuppressWarnings("nls")
  protected String getEnvironmentStringRepresentation(EnvironmentAttributes env_p) {
    if (null == env_p) {
      return ICommonConstants.EMPTY_STRING;
    }

    String path = env_p._expandedAttributes.get("Doors_database").get(0);
    // Set the path of the database
    StringBuilder sb = new StringBuilder(path);
    sb.append(" P: ");
    for (Entry<String, List<String>> entry : env_p._expandedAttributes.entrySet()) {
      String key = entry.getKey();
      List<String> valueList = entry.getValue();
      // If this is a project: Project: 'SurName' BaselineSet:
      // 'baselineSet' (if any)
      if (key.contains("Project_")) {
        sb.append(valueList.get(2)).append(", ");
      }
    }
    return sb.toString();
  }

  /**
   * Check if the two given environment are from the same database name and use the same port
   * @param firstEnv_p The first environment to check
   * @param secondEnv_p The second environment to check
   * @return True if the two environments have the same database name and port, else false
   */
  private boolean isSameDatabase(EnvironmentAttributes firstEnv_p, EnvironmentAttributes secondEnv_p) {
    String firstDatabase = firstEnv_p._attributes.get(DoorsEnvironment.ATTRIBUT_KEY_DB_NAME);
    String firstPort = firstEnv_p._attributes.get(DoorsEnvironment.ATTRIBUT_KEY_PORT);
    String secondDatabse = secondEnv_p._attributes.get(DoorsEnvironment.ATTRIBUT_KEY_DB_NAME);
    String secondPort = secondEnv_p._attributes.get(DoorsEnvironment.ATTRIBUT_KEY_PORT);
    // Check null value to avoid a NPE in the next check
    if (null == firstDatabase || null == firstPort) {
      return false;
    }
    // Check if both environment have the same database name and port
    if (firstDatabase.equals(secondDatabse) && firstPort.equals(secondPort)) {
      return true;
    }
    return false;
  }

}
