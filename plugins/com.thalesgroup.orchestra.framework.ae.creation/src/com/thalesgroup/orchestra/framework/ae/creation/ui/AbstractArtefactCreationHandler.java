/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.creation.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author s0040806
 */
public class AbstractArtefactCreationHandler {

  protected Map<String, String> _parameters;

  protected void setParameters(Map<String, String> parameters_p) {
    _parameters = parameters_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ae.creation.ui.connector.IConnectorArtefactCreationHandler#getFirstPage()
   */
  public AbstractFormsWizardPage getFirstPage() {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ae.creation.ui.environment.IEnvironmentArtefactCreationHandler#arePagesComplete()
   */
  public boolean arePagesComplete() {
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ae.creation.ui.environment.IEnvironmentArtefactCreationHandler#getParameters()
   */
  public Map<String, String> getParameters() {
    return new HashMap<String, String>(_parameters);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ae.creation.ui.environment.IEnvironmentArtefactCreationHandler#updateParameters(java.util.Map)
   */
  public void updateParameters(Map<String, String> updatedParameters_p) {
    if (null != updatedParameters_p) {
      mergeMap(updatedParameters_p, _parameters);
    }
  }

  /**
   * This method merges all entries from the source map into the destination map.<br>
   * Entries with a <code>null</code> value (in the source map) are removed from the destination map.
   * @param sourceMap_p A not <code>null</code> source map, with values to merge to destination one (including values to remove).
   * @param destinationMap_p A not <code>null</code>, but possibly empty, destination map that hosts the merge result.<br>
   *          If the map is not empty, then its resulting content depends on the result of the merge operation, that overrides whatever the destination map
   *          might already contains and collides with the source one.
   */
  public static void mergeMap(Map<String, String> sourceMap_p, Map<String, String> destinationMap_p) {
    // Precondition.
    if ((null == sourceMap_p) || (null == destinationMap_p)) {
      return;
    }
    // Replace parameters with specified ones.
    destinationMap_p.putAll(sourceMap_p);
    // Cycle through entries so as to remove keys that have no longer any value.
    Iterator<Entry<String, String>> iterator = destinationMap_p.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> entry = iterator.next();
      // Get rid of this entry.
      if (null == entry.getValue()) {
        iterator.remove();
      }
    }
  }
}
