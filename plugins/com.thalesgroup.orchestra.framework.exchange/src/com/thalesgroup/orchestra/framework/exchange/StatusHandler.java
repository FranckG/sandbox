/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.exchange;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.exchange.status.StatusFactory;
import com.thalesgroup.orchestra.framework.exchange.status.StatusPackage;
import com.thalesgroup.orchestra.framework.exchange.status.util.StatusResourceFactoryImpl;

/**
 * Status model format handler.<br>
 * Allows for reading/writing of files contents.
 * @author t0076261
 */
public class StatusHandler extends AbstractModelHandler<StatusDefinition> {
  /**
   * @see com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler#createRootElement()
   */
  @Override
  protected StatusDefinition createRootElement() {
    DocumentRoot root = StatusFactory.eINSTANCE.createDocumentRoot();
    root.setStatusDefinition(StatusFactory.eINSTANCE.createStatusDefinition());
    return root.getStatusDefinition();
  }

  /**
   * Get export model (GEF) absolute path for specified status, if any.<br>
   * The retained export path is either contained by specified status, or one of its parents.
   * @param status_p
   * @return <code>null</code> if no model is exported or specified within this status range, or specified status is <code>null</code>.
   */
  public String getExportModelAbsolutePath(Status status_p) {
    Status currentStatus = status_p;
    // Cycle through statuses.
    while (null != currentStatus) {
      String exportFilePath = currentStatus.getExportFilePath();
      // Found candidate.
      if ((null != exportFilePath) && !exportFilePath.trim().isEmpty()) {
        return exportFilePath;
      }
      // Up to parent.
      EObject container = currentStatus.eContainer();
      if (container instanceof Status) {
        // Parent selected.
        currentStatus = (Status) container;
      } else {
        // Parent is likely status definition.
        // Stop here.
        currentStatus = null;
      }
    }
    // No export path found.
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler#getFileExtension()
   */
  @Override
  public String getFileExtension() {
    return "status"; //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler#getRootElement(java.lang.Object)
   */
  @Override
  protected StatusDefinition getRootElement(Object element_p) {
    if (element_p instanceof DocumentRoot) {
      return ((DocumentRoot) element_p).getStatusDefinition();
    } else if (element_p instanceof StatusDefinition) {
      return (StatusDefinition) element_p;
    }
    return null;
  }

  /**
   * Get status for specified URI, child of specified root.
   * @param rootStatus_p
   * @param uri_p
   * @return <code>null</code> if no such URI could be found within specified tree, or parameters are invalid.
   */
  protected Status getStatusForUri(Status rootStatus_p, String uri_p) {
    if (uri_p.equals(rootStatus_p.getUri())) {
      return rootStatus_p;
    }
    for (Status childStatus : rootStatus_p.getStatus()) {
      Status result = getStatusForUri(childStatus, uri_p);
      if (null != result) {
        return result;
      }
    }
    return null;
  }

  /**
   * Get status for specified URI and status definition.
   * @param definition_p
   * @param uri_p
   * @return <code>null</code> if no such URI could be found within specified tree, or parameters are invalid.
   */
  public Status getStatusForUri(StatusDefinition definition_p, String uri_p) {
    // Precondition.
    if ((null == definition_p) || (null == uri_p)) {
      return null;
    }
    return getStatusForUri(definition_p.getStatus(), uri_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler#initialize(org.eclipse.emf.ecore.resource.ResourceSet)
   */
  @Override
  protected void initialize(ResourceSet resourceSet_p) {
    if (!Platform.isRunning()) {
      resourceSet_p.getPackageRegistry().put(StatusPackage.eNS_URI, StatusPackage.eINSTANCE);
      resourceSet_p.getResourceFactoryRegistry().getExtensionToFactoryMap().put("status", new StatusResourceFactoryImpl()); //$NON-NLS-1$
    }
  }
}