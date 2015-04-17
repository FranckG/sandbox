package com.thalesgroup.orchestra.doors.framework.environment.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsActivator;

/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */

/**
 * Data model of a Doors Baseline Set Definition
 * @author S0032874
 */
public class BaselineSetDefinitionModel extends AbstractDoorsElement {

  private final List<BaselineSetModel> _baselineSets;

  /**
   * Constructor
   * @param doorsId_p The Doors URI
   * @param name_p The Doors name
   */
  public BaselineSetDefinitionModel(AbstractDoorsContainer parent_p, String doorsURI_p, String doorsName_p, boolean isVirtual_p) {
    super(parent_p, doorsURI_p, doorsName_p, isVirtual_p);
    _baselineSets = new ArrayList<BaselineSetModel>();
  }

  /**
   * Add a {@link BaselineSetModel} to the BaselineSetDefinitionModel <br>
   * Only use this method to add a new {@link BaselineSetModel}
   * @param baselineSetModel_p The {@link BaselineSetModel} to add
   */
  public void addBaselineSet(BaselineSetModel baselineSetModel_p) {
    // Set the father ID
    _baselineSets.add(baselineSetModel_p);
  }

  public BaselineSetModel getBaseLineSetForName(String baseLineSetName_p) {
    for (BaselineSetModel baseLineSet : _baselineSets) {
      if (baseLineSet._doorsName.equals(baseLineSetName_p)) {
        return baseLineSet;
      }
    }
    return null;
  }

  /**
   * Do not use to add a new {@link BaselineSetModel}
   * @return the baselineSetsMap
   */
  public List<BaselineSetModel> getBaselineSets() {
    return _baselineSets;
  }

  /**
   * @see IDoorsTreeElement#getChildren()
   */
  @Override
  public Object[] getChildren() {
    if (hasChildren()) {
      return _baselineSets.toArray();
    }
    return new Object[0];
  }

  /**
   * @see com.thalesgroup.orchestra.doors.framework.environment.model.DefaultModel#getImage()
   */
  @Override
  public Image getImage() {
    return DoorsActivator.getInstance().getImage("doors_baselinesetdef.gif"); //$NON-NLS-1$
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
    return !_baselineSets.isEmpty();
  }
}
