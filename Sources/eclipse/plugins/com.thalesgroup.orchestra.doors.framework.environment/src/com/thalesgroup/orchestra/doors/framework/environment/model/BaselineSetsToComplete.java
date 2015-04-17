/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment.ProjectEnvironmentData;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * @author S0035580
 */
public class BaselineSetsToComplete {

  /**
   * List of BaselineSets name to display to the user.
   */
  public ArrayList<String> _baselineSetsNames;

  /**
   * List of input baselineSets to complete.
   */
  private ArrayList<OrchestraURI> _baselineSetsToComplete;

  /**
   * List of completed baselineSets to return.
   */
  private ArrayList<OrchestraURI> _completedBaselineSets;

  private Map<String, String> _projectMap;

  private List<ProjectEnvironmentData> _projectsEnvironmentData;

  private Map<OrchestraURI, BaselineSet> _RefStruct;

  /**
   * Constructor
   * @param baselineSetsToComplete_p
   */
  public BaselineSetsToComplete(ArrayList<OrchestraURI> baselineSetsToComplete_p, List<ProjectEnvironmentData> projectsEnvironmentData_p) {
    _baselineSetsToComplete = baselineSetsToComplete_p;
    _projectsEnvironmentData = projectsEnvironmentData_p;
    _projectMap = initializeProjectMap();
    _RefStruct = initializeRefStruct();
  }

  /**
   * @return the baselineSetsToComplete
   */
  public ArrayList<OrchestraURI> getBaselineSetsToComplete() {
    return _baselineSetsToComplete;
  }

  public ArrayList<BaselineSet> getBaselinesPathsNamesToDisplay() {
    ArrayList<BaselineSet> baselineSets = new ArrayList<BaselineSet>();
    for (OrchestraURI uri : _RefStruct.keySet()) {
      baselineSets.add(_RefStruct.get(uri));
    }
    return baselineSets;
  }

  /**
   * @return the completedBaselineSets
   */
  public ArrayList<OrchestraURI> getCompletedBaselineSets() {
    _completedBaselineSets = new ArrayList<OrchestraURI>();
    for (OrchestraURI baselineUri : _RefStruct.keySet()) {
      OrchestraURI completedbaselineSet = new OrchestraURI(baselineUri.getUri());
      completedbaselineSet.addParameter(DoorsEnvironment.MAJOR, getVersion(_RefStruct.get(baselineUri).getVersion()));
      completedbaselineSet.addParameter(DoorsEnvironment.SUFFIX, _RefStruct.get(baselineUri).getSuffix());
      completedbaselineSet.addParameter(DoorsEnvironment.COMMENT, _RefStruct.get(baselineUri).getDescription());
      _completedBaselineSets.add(completedbaselineSet);
    }

    return _completedBaselineSets;
  }

  private String getVersion(int version) {
    if (1 == version) {
      return "true"; //$NON-NLS-1$
    }
    return "false"; //$NON-NLS-1$
  }

  /**
   * @return projectsMap
   */
  private Map<String, String> initializeProjectMap() {
    Map<String, String> projectsMap = new HashMap<String, String>();
    // Parse all projects from the calling environment
    for (ProjectEnvironmentData projectEnvironmentData : _projectsEnvironmentData) {
      projectsMap.put(projectEnvironmentData._doorsProjectId, projectEnvironmentData._doorsProjectName);
    }
    return projectsMap;
  }

  /**
   * format: OrchestraURI (key) - BaselineSet (value). BaselineSet object contains the triplet version-suffix-comment to implement.
   * @return the reference structure of BaselineSets to complete.
   */
  private Map<OrchestraURI, BaselineSet> initializeRefStruct() {
    Map<OrchestraURI, BaselineSet> structResult = new HashMap<OrchestraURI, BaselineSet>();

    for (OrchestraURI bsUri : _baselineSetsToComplete) {
      String objectId = bsUri.getObjectId();
      if (null != objectId) {
        String[] values = objectId.split("!", -1); //$NON-NLS-1$
        String projectName = _projectMap.get(values[0]);
        String nameToDisplay;
        if (null != projectName) {
          nameToDisplay = projectName + " - " + values[1]; //$NON-NLS-1$
        } else {
          nameToDisplay = values[0] + " - " + values[1]; //$NON-NLS-1$
        }
        structResult.put(bsUri, new BaselineSet(nameToDisplay, 0, null, null));
      }
    }
    return structResult;
  }

  public boolean isValid() {
    for (OrchestraURI uri : _RefStruct.keySet()) {
      if (null != _RefStruct.get(uri).getSuffix()) {

        if (_RefStruct.get(uri).getSuffix().matches(".*!.*")) { //$NON-NLS-1$
          return false;
        }
      }

      if (0 == _RefStruct.get(uri).getVersion()) {
        return false;
      }
    }
    return true;
  }

  public class BaselineSet {
    private String _description;
    private String _name;
    private String _suffix;
    private int _version; // version format: 0: null, 1: major, 2:minor

    public BaselineSet(String name_p, int version_p, String suffix_p, String description_p) {
      setName(name_p);
      setVersion(version_p);
      setSuffix(suffix_p);
      setDescription(description_p);
    }

    /**
     * @return the description
     */
    public String getDescription() {
      return _description;
    }

    /**
     * @return the name
     */
    public String getName() {
      return _name;
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
      return _suffix;
    }

    /**
     * @return the version
     */
    public int getVersion() {
      return _version;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
      _description = description;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
      _name = name;
    }

    /**
     * @param suffix the suffix to set
     */
    public void setSuffix(String suffix) {
      _suffix = suffix;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(int version) {
      _version = version;
    }

  }
}
