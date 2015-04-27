/**
 * <p>Copyright (c) 2002-2008 : Thales Services - Engineering & Process Management</p>
 * <p>Société : Thales Services - Engineering & Process Management</p>
 * <p>Thales Part Number 16 262 601</p>
 */
package com.thalesgroup.orchestra.framework.oe.artefacts.description;

import org.eclipse.swt.graphics.Image;

import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;

/**
 * <p>
 * Title : ArtifactTypeDescription
 * </p>
 * <p>
 * Description : Class representing the Artifact Description object for the ArtifactsTree objects based upon the ArtifactDescription xml files
 * @author Orchestra Framework Tool Suite developer
 * @version 3.7.0
 */
public class ArtefactTypeDescription implements IArtefactTypeDescription {
  private final String _icon;
  private final boolean _isSetCredential;
  private final boolean _isDisplayable;
  private final boolean _isExpandable;
  private final boolean _isExtractable;
  private final boolean _isNavigable;
  private final boolean _isToolAtomicallyExtractable;
  private final boolean _isTraceable;

  /**
   * @param isExpandable
   * @param isExtractable
   * @param isToolAtomicallyExtractable
   * @param isTraceable
   * @param isNavigable
   * @param isDisplayable
   * @param isSetCredential
   * @param iconPath
   */
  public ArtefactTypeDescription(boolean isExpandable, boolean isExtractable, boolean isToolAtomicallyExtractable, boolean isTraceable, boolean isNavigable,
      boolean isDisplayable, boolean isSetCredential, String iconPath) {
    _isExpandable = isExpandable;
    _isExtractable = isExtractable;
    _isToolAtomicallyExtractable = isToolAtomicallyExtractable;
    _isTraceable = isTraceable;
    _isNavigable = isNavigable;
    _isDisplayable = isDisplayable;
    _isSetCredential = isSetCredential;
    _icon = iconPath;
  }

  /**
   * @return the path icon for an artifact (icon in the directory artifacts/)
   */
  public String getIcon() {
    return _icon;
  }

  /**
   * @return the image of the icon in the directory artifacts/
   */
  public Image getIconImage() {
    return OrchestraExplorerActivator.getDefault().getImage(_icon);
  }

  /**
   * @return the isCredentialReInitializable
   */
  public boolean isSetCredential() {
    return _isSetCredential;
  }

  /*
   * (non-Javadoc)
   * @see com.thalesgroup.themis.papeetedoc.aep.model. IArtifactTypeDescription#isDisplayable()
   */
  public boolean isDisplayable() {
    return _isDisplayable;
  }

  /**
   * @return true if the element is expandable else return false
   */
  public boolean isExpandable() {
    return _isExpandable;
  }

  /**
   * @return true if the element is extractable else return false
   */
  public boolean isExtractable() {
    return _isExtractable;
  }

  /**
   * @return true if the element is navigable else return false
   */
  public boolean isNavigable() {
    return _isNavigable;
  }

  /**
   * @return true if the element is tool atomically extractable else return false
   */
  public boolean isToolAtomicallyExtractable() {
    return _isToolAtomicallyExtractable;
  }

  /**
   * @return true if the element is traceable else return false
   */
  public boolean isTraceable() {
    return _isTraceable;
  }
}