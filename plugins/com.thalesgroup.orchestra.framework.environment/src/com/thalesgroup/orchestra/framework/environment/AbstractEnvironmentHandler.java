/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;

/**
 * @author t0076261
 */
public abstract class AbstractEnvironmentHandler implements IEnvironmentHandler {
  /**
   * Attributes map.
   */
  protected Map<String, String> _attributes;
  /**
   * Handler declaration ID.
   */
  protected String _declarationId;
  /**
   * Unique use baseline handler implementation to use.
   */
  protected IEnvironmentUseBaselineHandler _useBaselineHandler;

  /**
   * Constructors.
   */
  public AbstractEnvironmentHandler() {
    _attributes = new HashMap<String, String>(0);
  }

  /**
   * Create {@link IEnvironmentUseBaselineHandler} implementation to use.<br>
   * It is highly recommended that it extends {@link DefaultEnvironmentUseBaselineHandler} class.
   * @return
   */
  protected IEnvironmentUseBaselineHandler createEnvironmentUseBaselineHandler() {
    return new DefaultEnvironmentUseBaselineHandler();
  }

  /**
   * Do get next page from specified one.
   * @param currentPage_p
   * @return Default implementation does return <code>null</code>.
   */
  protected AbstractEnvironmentPage doGetNextPage(AbstractEnvironmentPage currentPage_p) {
    return null;
  }

  /**
   * Do initialize handler for specified (initial) attributes.<br>
   * Note that the internal map of attributes is already initialized when this method is invoked.<br>
   * This method is provided as an opportunity to update an internal state of the handler.<br>
   * Its implementation <b>must not modify</b> the internal map of attributes.
   * @param context_p Contains the raw attributes as they are currently known by context being edited.
   * @return <code>null</code> if initialization is successful. An error status otherwise, with details.<br>
   *         Default implementation returns <code>null</code>.
   */
  protected IStatus doInitialize(EnvironmentContext context_p) {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getAttributes()
   */
  public final Map<String, String> getAttributes() {
    return new HashMap<String, String>(_attributes);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getBaselineReferenceAttributes(EnvironmentContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public Couple<Map<String, String>, IStatus> getBaselineReferenceAttributes(UseBaselineEnvironmentContext context_p, IProgressMonitor progressMonitor_p) {
    // Default implementation does not change attributes when using a baseline as a reference.
    return new Couple<Map<String, String>, IStatus>(context_p._attributes, Status.OK_STATUS);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getBaselineStartingPointAttributes(com.thalesgroup.orchestra.framework.environment.EnvironmentContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public Couple<Map<String, String>, IStatus> getBaselineStartingPointAttributes(UseBaselineEnvironmentContext startingPointContext_p,
      IProgressMonitor progressMonitor_p) {
    // Default implementation does not change attributes when using a baseline as a starting point.
    return new Couple<Map<String, String>, IStatus>(startingPointContext_p._attributes, Status.OK_STATUS);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getDeclarationId()
   */
  public String getDeclarationId() {
    return _declarationId;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getEnvironmentUseBaselineHandler()
   */
  public final IEnvironmentUseBaselineHandler getEnvironmentUseBaselineHandler() {
    if (null == _useBaselineHandler) {
      _useBaselineHandler = createEnvironmentUseBaselineHandler();
    }
    return _useBaselineHandler;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getNextPage(com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage)
   */
  public final AbstractEnvironmentPage getNextPage(AbstractEnvironmentPage currentPage_p) {
    if (null == currentPage_p) {
      return null;
    }
    AbstractEnvironmentPage result = doGetNextPage(currentPage_p);
    if (null != result) {
      result.setToolkit(currentPage_p.getToolkit());
      result.setWizard(currentPage_p.getWizard());
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#getVariablesHandler()
   */
  public IVariablesHandler getVariablesHandler() {
    return EnvironmentActivator.getInstance().getVariablesHandler();
  }

  /**
   * Get environment version for current attributes.
   * @return <code>null</code> if version is not specified in current attributes.
   */
  protected String getVersion() {
    return _attributes.get(ICommonConstants.ENVIRONMENT_KEY_VERSION);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#initialize(com.thalesgroup.orchestra.framework.environment.EnvironmentContext)
   */
  public final IStatus initialize(EnvironmentContext context_p) {
    _attributes.clear();
    // Precondition.
    if ((null == context_p) || (null == context_p._attributes)) {
      return new Status(IStatus.OK, EnvironmentActivator.getInstance().getPluginId(), Messages.AbstractEnvironmentHandler_InitializeHandler_Reset_Successful);
    }
    // Replace parameters with specified ones.
    _attributes.putAll(context_p._attributes);
    IStatus result = doInitialize(context_p);
    if (null == result) {
      return new Status(IStatus.OK, EnvironmentActivator.getInstance().getPluginId(),
          Messages.AbstractEnvironmentHandler_InitializeHandler_Initialize_Successful);
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#isUseBaselineCompliant(com.thalesgroup.orchestra.framework.environment.EnvironmentContext)
   */
  public boolean isUseBaselineCompliant(EnvironmentContext environmentContext_p) {
    // Default implementation returns true, accordingly to the AbstractEnvironment#isBaselineCompliant() default implementation.
    // It is highly recommended to stick to this value, unless the environment is really not compatible with the whole baseline process, or should not be used
    // as a baseline reference or starting point.
    return true;
  }

  /**
   * Set declaration ID to specified one.<br>
   * This is intended to be used by the registry alone.
   * @param declarationId_p
   */
  public void setDeclarationId(String declarationId_p) {
    _declarationId = declarationId_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler#updateAttributes(java.util.Map)
   */
  public final IStatus updateAttributes(Map<String, String> updatedAttributes_p) {
    if (null == updatedAttributes_p) {
      return new Status(IStatus.ERROR, EnvironmentActivator.getInstance().getPluginId(),
          Messages.AbstractEnvironmentHandler_UpdateAttributes_Error_AttributesCantBeNull);
    }
    mergeMap(updatedAttributes_p, _attributes);
    return new Status(IStatus.OK, EnvironmentActivator.getInstance().getPluginId(), Messages.AbstractEnvironmentHandler_UpdateAttributes_Successful);
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