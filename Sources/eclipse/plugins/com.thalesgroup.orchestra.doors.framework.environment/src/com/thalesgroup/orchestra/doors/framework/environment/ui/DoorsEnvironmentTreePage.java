/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsActivator;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment.BaselineSetEnvironmentData;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment.ProjectEnvironmentData;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironmentHandler;
import com.thalesgroup.orchestra.doors.framework.environment.model.AbstractDoorsContainer;
import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetDefinitionModel;
import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetModel;
import com.thalesgroup.orchestra.doors.framework.environment.model.DoorsFolder;
import com.thalesgroup.orchestra.doors.framework.environment.model.DoorsProject;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * TODO add combo in page to hide/display only virtual model or both <br>
 * This page displays a checkbox tree which allows to select a project, its folders, its baselineSetDefinition and its baselineSet
 * @author S0032874
 */

public class DoorsEnvironmentTreePage extends AbstractEnvironmentPage {
  protected final List<DoorsProject> _doorsProjectList;

  protected final List<String> _initialProjectsIdWithBaselineSet;

  protected final List<BaselineSetModel> _initialSelectedBaselineSets;

  protected final List<DoorsProject> _initialSelectedProjects;

  /**
   * The {@link ProjectTreePanel}
   */
  private ProjectTreePanel _projectTreePanel;

  /**
   * Constructor; Will access the handler to retrieve the project attribute and initialize the page
   * @param handler_p The DoorsEnvironmentHandler containing the attributes of the environment
   */
  public DoorsEnvironmentTreePage(DoorsEnvironmentHandler handler_p) {
    super("DoorsEnvironmentProjectAndModulePage", handler_p); //$NON-NLS-1$
    _doorsProjectList = new ArrayList<DoorsProject>();
    _initialSelectedProjects = new ArrayList<DoorsProject>();
    _initialSelectedBaselineSets = new ArrayList<BaselineSetModel>();
    _initialProjectsIdWithBaselineSet = new ArrayList<String>();
  }

  public void applyBaseLineSetSelection(DoorsProject doorsProject_p, String baselines_p, List<BaselineSetModel> selectedBaseLineSet_p) {
    List<BaselineSetEnvironmentData> baselineSetsEnvData = DoorsEnvironment.deserializeBaselineSetList(baselines_p);

    for (BaselineSetEnvironmentData baselineSetEnvData : baselineSetsEnvData) {
      BaselineSetURIContent content = parseBaselineSetURI(baselineSetEnvData._baselineSetEncodedURI);
      selectOrCreateAndSelect(doorsProject_p, Arrays.copyOfRange(content._path, 1, content._path.length), content._blsdName, content._blsName,
          selectedBaseLineSet_p);
    }

  }

  /**
   * Determines when the next button is available, i.e. when a value has been entered for the port and for the name
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#doCanFlipToNextPage()
   */
  @Override
  protected boolean doCanFlipToNextPage() {
    Map<String, String> values = getUpdatedValues();
    // Can go to next page when at least one project has been selected
    if (values.containsKey(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST)) {
      String value = values.get(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST);
      // If value neither null nor empty
      if ((null != value) && !ICommonConstants.EMPTY_STRING.equals(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, true);

    initializeDoorsProjectsLists();
    _projectTreePanel = new ProjectTreePanel(composite, toolkit_p, _doorsProjectList, _initialSelectedProjects, _initialSelectedBaselineSets, this);
    return composite;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#forceUpdate()
   */
  @Override
  public void forceUpdate() {
    super.forceUpdate();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return DoorsActivator.getInstance().getImageDescriptor("doors_big.gif"); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.DoorsEnvironment_Project_And_Module_Page_Title;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#getUpdatedValues()
   */
  @Override
  protected Map<String, String> getUpdatedValues() {
    Map<String, String> updatedMap = new HashMap<String, String>();
    // "Clean" map.
    for (String initialProjectIdWithBaselineSet : _initialProjectsIdWithBaselineSet) {
      updatedMap.put(DoorsEnvironment.ATTRIBUT_KEY_BASELINESET_LIST + initialProjectIdWithBaselineSet, null);
    }
    // Save selected projects.
    updatedMap.put(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST, DoorsEnvironment.serializeDoorsProject(_projectTreePanel.getSelectedDoorsProjects()));
    // Save selected baselineSets.
    updatedMap.putAll(DoorsEnvironment.serializeBaselineSetList(_projectTreePanel.getSelectedBaselineSets()));

    return updatedMap;
  }

  @Override
  public IVariablesHandler getVariablesHandler() {
    return _handler.getVariablesHandler();
  }

  /**
   * Create the map containing the {@link ProjectModel} by merging the data from Doors and the ones from the Doors Environment
   * @return The data map
   */
  protected void initializeDoorsProjectsLists() {
    DoorsEnvironmentHandler doorsHandler = (DoorsEnvironmentHandler) _handler;
    // Get all Doors projects
    Map<String, DoorsProject> doorsProjectsMap = doorsHandler.getDoorsProjectMap();
    _doorsProjectList.addAll(doorsProjectsMap.values());
    // Get all Environment projects
    List<ProjectEnvironmentData> projectsEnvironmentData = doorsHandler.getDoorsEnvironmentProjectList();
    // Parse all projects from the environment
    for (ProjectEnvironmentData projectEnvironmentData : projectsEnvironmentData) {
      String doorsProjectId = projectEnvironmentData._doorsProjectId;
      String doorsProjectName = projectEnvironmentData._doorsProjectName;
      String doorsProjectLogicalName = projectEnvironmentData._doorsProjectLogicalName;
      DoorsProject currentDoorsProject = doorsProjectsMap.get(doorsProjectId);
      // A corresponding project was found in Doors
      if (null != currentDoorsProject) {
        currentDoorsProject.setLogicalName(doorsProjectLogicalName);
      } else {
        // Project is not present in Doors db -> it's a virtual project.
        currentDoorsProject = new DoorsProject(null, doorsProjectName, true, doorsProjectId, doorsProjectLogicalName);
        _doorsProjectList.add(currentDoorsProject);
      }
      _initialSelectedProjects.add(currentDoorsProject);
      String baselinesForProject = doorsHandler.getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_BASELINESET_LIST + doorsProjectId);
      if (null != baselinesForProject) {
        _initialProjectsIdWithBaselineSet.add(doorsProjectId);
      }
      applyBaseLineSetSelection(currentDoorsProject, baselinesForProject, _initialSelectedBaselineSets);
    }

  }

  public void selectOrCreateAndSelect(AbstractDoorsContainer doorsContainer_p, String[] path_p, String blsdName_p, String blsName_p,
      List<BaselineSetModel> selectedBaseLineSet_p) {
    if (path_p.length == 0) {
      // Find BLSD.
      BaselineSetDefinitionModel baseLineSetDefinition = doorsContainer_p.getBaseLineSetDefinitionForName(blsdName_p);
      if (null == baseLineSetDefinition) {
        baseLineSetDefinition = new BaselineSetDefinitionModel(doorsContainer_p, null, blsdName_p, true);
        doorsContainer_p.addBaselineSetDefinition(baseLineSetDefinition);
      }
      // Find BLS.
      BaselineSetModel baseLineSet = baseLineSetDefinition.getBaseLineSetForName(blsName_p);
      if (null == baseLineSet) {
        baseLineSet = new BaselineSetModel(baseLineSetDefinition, null, blsName_p, true);
        baseLineSetDefinition.addBaselineSet(baseLineSet);
      }
      selectedBaseLineSet_p.add(baseLineSet);
    } else {
      // Find path.
      DoorsFolder folder = doorsContainer_p.getFolderForName(path_p[0]);
      if (null == folder) {
        folder = new DoorsFolder(doorsContainer_p, null, path_p[0], true);
        doorsContainer_p.getFoldersList().add(folder);
      }
      selectOrCreateAndSelect(folder, Arrays.copyOfRange(path_p, 1, path_p.length), blsdName_p, blsName_p, selectedBaseLineSet_p);
    }
  }

  public static BaselineSetURIContent parseBaselineSetURI(String uri_p) {
    // Use an OrchestraURI instead...
    int indexOfParams = uri_p.indexOf("?"); //$NON-NLS-1$
    String uri = uri_p;
    if (-1 != indexOfParams) {
      uri = uri_p.substring(0, indexOfParams);
    }
    String pathBlsdBls = uri.replace(DoorsEnvironmentHandler.BASELINE_SET_URI_PREFIX, ""); //$NON-NLS-1$
    String decodedPathBlsdBls = OrchestraURI.decode(pathBlsdBls);
    String[] splittedDecodedPathBlsdBls = decodedPathBlsdBls.split("!"); //$NON-NLS-1$
    String[] splittedDecodedPath = splittedDecodedPathBlsdBls[0].split("/"); //$NON-NLS-1$
    if ((splittedDecodedPath.length > 0) && splittedDecodedPath[0].isEmpty()) {
      // Remove first '/'.
      splittedDecodedPath = Arrays.copyOfRange(splittedDecodedPath, 1, splittedDecodedPath.length);
    }

    return new BaselineSetURIContent(splittedDecodedPath, splittedDecodedPathBlsdBls[1], splittedDecodedPathBlsdBls[2]);
  }

  public static class BaselineSetURIContent {
    public final String _blsdName;
    public final String _blsName;
    public final String[] _path;

    public BaselineSetURIContent(String[] path_p, String blsdName_p, String blsName_p) {
      _path = path_p;
      _blsdName = blsdName_p;
      _blsName = blsName_p;
    }
  }
}
