/**
 * <p>Copyright (c) 2002-2008 : Thales Services - Engineering & Process Management</p>
 * <p>Société : Thales Services - Engineering & Process Management</p>
 * <p>Thales Part Number 16 262 601</p>
 */
package com.thalesgroup.orchestra.framework.oe.artefacts.description;

import org.eclipse.swt.graphics.Image;

/**
 * <p>
 * Title : IArtifactTypeDescription
 * </p>
 * <p>
 * Description :
 * @author Orchestra Framework Tool Suite developer
 * @version 3.7.0
 */
public interface IArtefactTypeDescription {
  /**
   * @return the path of the associatedIcon
   */
  public String getIcon();

  /**
   * @return the image of the associatedIcon
   */
  public Image getIconImage();

  /**
   * @return true if the element has re-initializable credentials
   */
  public boolean isSetCredential();

  /**
   * @return true if the artifact is to be displayed
   */
  public boolean isDisplayable();

  /**
   * @return true if the artifact is expandable
   */
  public boolean isExpandable();

  /**
   * @return true if the artifact is extractable
   */
  public boolean isExtractable();

  /**
   * @return true if the artifact is navigable
   */
  public boolean isNavigable();

  /**
   * @return true if the element is tool atomically extractable else return false
   */
  public boolean isToolAtomicallyExtractable();

  /**
   * @return true if the element is traceable
   */
  public boolean isTraceable();
}