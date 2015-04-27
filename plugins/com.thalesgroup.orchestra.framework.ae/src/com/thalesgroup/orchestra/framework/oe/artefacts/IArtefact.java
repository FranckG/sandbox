/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.artefacts;

import java.util.Map;

import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * This interface is the minimum an artefact provides.
 * @author S0024585
 */
public interface IArtefact {

  /**
   * @return the description of this artefact
   */
  public String getDescription();

  /**
   * @return the fullName
   */
  public String getFullName();

  /**
   * @return the label
   */
  public String getLabel();

  /**
   * @return The object Id of this artefact. Return the root name if the object Id is null or empty.
   */
  public String getName();

  /**
   * @return the parameters {@link Map} of this artefact
   */
  public Map<String, String> getParameters();

  /**
   * @return the properties {@link Map} of this artefact
   */
  public Map<String, String> getProperties();

  /**
   * @return The value of the property. <code>null</code> if this property is not defined.
   */
  public String getPropertyValue(String propertyName_p);

  /**
   * @return The object type of this artefact. Return the root type if the object type is null or empty.
   */
  public String getType();

  /**
   * @return the uri
   */
  public OrchestraURI getUri();

  /**
   * @return true if this artefact is a root artefact
   */
  public boolean isRootArtefact();

  /**
   * @param fullName_p the fullName to set
   */
  public void setFullName(String fullName_p);

  /**
   * @param label_p the label to set
   */
  public void setLabel(String label_p);

  /**
   * @param uri_p the uri to set
   */
  public void setUri(OrchestraURI uri_p);

}