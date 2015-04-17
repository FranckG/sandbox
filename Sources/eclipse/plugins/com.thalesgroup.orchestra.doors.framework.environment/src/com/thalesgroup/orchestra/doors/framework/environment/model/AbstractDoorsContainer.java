package com.thalesgroup.orchestra.doors.framework.environment.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */

/**
 * Data model of a Doors project or folder
 * @author S0032874
 */
public abstract class AbstractDoorsContainer extends AbstractDoorsElement {

  private final List<BaselineSetDefinitionModel> _baselineSetDefinitions;

  private final List<DoorsFolder> _folders;

  /**
   * Constructor
   * @param doorsURI_p The Doors URI
   * @param doorsName_p The Doors name
   * @param isFolder_p True if the project is a folder, else false
   */
  public AbstractDoorsContainer(AbstractDoorsElement parent_p, String doorsURI_p, String doorsName_p, boolean isVirtual_p) {
    super(parent_p, doorsURI_p, doorsName_p, isVirtual_p);
    _folders = new ArrayList<DoorsFolder>();
    _baselineSetDefinitions = new ArrayList<BaselineSetDefinitionModel>();
  }

  /**
   * Add a {@link BaselineSetDefinitionModel} to the project or folder <br>
   * Only use this method to add a new {@link BaselineSetDefinitionModel}
   * @param model_p The {@link BaselineSetDefinitionModel} to add
   */
  public void addBaselineSetDefinition(BaselineSetDefinitionModel model_p) {
    _baselineSetDefinitions.add(model_p);
  }

  /**
   * Add a {@link ProjectModel} folder to the project or folder <br>
   * Only use this method to add a new {@Link ProjectModel}
   * @param folder_p The {@link ProjectModel} to add
   */
  public void addFolder(DoorsFolder folder_p) {
    _folders.add(folder_p);
  }

  public BaselineSetDefinitionModel getBaseLineSetDefinitionForName(String baseLineSetDefinitionName_p) {
    for (BaselineSetDefinitionModel baseLineSetDefinition : _baselineSetDefinitions) {
      if (baseLineSetDefinition._doorsName.equals(baseLineSetDefinitionName_p)) {
        return baseLineSetDefinition;
      }
    }
    return null;
  }

  public List<BaselineSetDefinitionModel> getBaselineSetDefinitions() {
    return _baselineSetDefinitions;
  }

  /**
   * @see IDoorsTreeElement#getChildren()
   */
  @Override
  public Object[] getChildren() {
    List<Object> tempList = new ArrayList<Object>(0);
    if (hasChildren()) {
      tempList.addAll(_folders);
      tempList.addAll(_baselineSetDefinitions);
    }
    return tempList.toArray();
  }

  public DoorsFolder getFolderForName(String folderDoorsName_p) {
    for (DoorsFolder doorsFolder : _folders) {
      if (doorsFolder._doorsName.equals(folderDoorsName_p)) {
        return doorsFolder;
      }
    }
    return null;
  }

  /**
   * Do not use to add a new folder
   * @return the foldersMap
   */
  public List<DoorsFolder> getFoldersList() {
    return _folders;
  }

  /**
   * @see com.thalesgroup.orchestra.doors.framework.environment.model.AbstractDoorsElement#getParent()
   */
  @Override
  public AbstractDoorsContainer getParent() {
    return (AbstractDoorsContainer) super.getParent();
  }

  /**
   * @see IDoorsTreeElement#hasChildren()
   */
  @Override
  public boolean hasChildren() {
    return !_baselineSetDefinitions.isEmpty() || !_folders.isEmpty();
  }

}
