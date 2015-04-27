/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.helper;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EValidator;

import com.thalesgroup.orchestra.framework.common.CommonActivator;

/**
 * {@link IMarker} various helpers implementations.
 * @author t0076261
 */
public class MarkerHelper {
  /**
   * Delete all markers of specified types in the workspace.
   * @param types_p A not <code>null</code> array of types to delete.
   */
  public static void deleteMarkers(String[] types_p) {
    // Precondition.
    if (null == types_p) {
      return;
    }
    // Cycles through types.
    for (String type : types_p) {
      try {
        // Do delete markers.
        ResourcesPlugin.getWorkspace().getRoot().deleteMarkers(type, false, IResource.DEPTH_INFINITE);
      } catch (CoreException exception_p) {
        StringBuilder loggerMessage = new StringBuilder("MarkerHelper.deleteMarkers(..) _ "); //$NON-NLS-1$
        loggerMessage.append("Failed to delete markers for type ").append(type); //$NON-NLS-1$
        CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
      }
    }
  }

  /**
   * Delete all markers of specified types in the resource specified by its path.
   * @param types_p A not <code>null</code> array of types to delete.
   * @param uri_p The not <code>null</code> {@link IPath} of the resource.
   */
  public static void deleteMarkers(String[] types_p, URI uri_p) {
    // Precondition.
    if (null == types_p) {
      return;
    }
    if (null == uri_p) {
      return;
    }
    // Get the resource on which markers must be deleted.
    IPath path = new Path(uri_p.path()).removeFirstSegments(1);
    IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
    // Cycles through types.
    for (String type : types_p) {
      try {
        // Do delete markers.
        resource.deleteMarkers(type, false, IResource.DEPTH_INFINITE);
      } catch (CoreException exception_p) {
        StringBuilder loggerMessage = new StringBuilder("MarkerHelper.deleteMarkers(..) _ "); //$NON-NLS-1$
        loggerMessage.append("Failed to delete markers for type ").append(type); //$NON-NLS-1$
        CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
      }
    }
  }

  /**
   * Delete all validation markers in the workspace.
   */
  public static void deleteValidationMarkers() {
    deleteMarkers(new String[] { EValidator.MARKER });
  }

  /**
   * Delete all validation markers in the resource specified by its path.
   * @param uri_p The not <code>null</code> {@link IPath} of the resource.
   */
  public static void deleteValidationMarkers(URI uri_p) {
    deleteMarkers(new String[] { EValidator.MARKER }, uri_p);
  }

  /**
   * Get all (in-depth) markers for specified marker type in workspace.
   * @param type_p
   * @return A not <code>null</code> but possibly empty array of {@link IMarker}.
   */
  public static IMarker[] getAllMarkersForType(String type_p) {
    return getAllMarkersForType(type_p, ResourcesPlugin.getWorkspace().getRoot());
  }

  /**
   * Get all (in-depth) markers for specified marker type within specified resource sub-tree.
   * @param type_p
   * @param resource_p
   * @return A not <code>null</code> but possibly empty array of {@link IMarker}.
   */
  public static IMarker[] getAllMarkersForType(String type_p, IResource resource_p) {
    IMarker[] result = null;
    // Precondition.
    if ((null == type_p) || (null == resource_p)) {
      return result;
    }
    // Get markers.
    try {
      result = resource_p.findMarkers(type_p, false, IResource.DEPTH_INFINITE);
    } catch (CoreException exception_p) {
      StringBuilder loggerMessage = new StringBuilder("MarkerHelper.getAllMarkersForType(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
    }
    // Do not return a null value.
    if (null == result) {
      result = new IMarker[0];
    }
    return result;
  }

  /**
   * Get all validation markers in workspace.<br>
   * Strictly equivalent to calling {@link #getAllMarkersForType(String)} with type set to {@link EValidator#MARKER}.
   * @return A not <code>null</code> but possibly empty array of {@link IMarker}.
   */
  public static IMarker[] getAllValidationMarkers() {
    return getAllMarkersForType(EValidator.MARKER);
  }

  /**
   * Get all validation markers within specified resource sub-tree.<br>
   * Strictly equivalent to calling {@link #getAllMarkersForType(String, IResource)} with type set to {@link EValidator#MARKER}.
   * @return A not <code>null</code> but possibly empty array of {@link IMarker}.
   */
  public static IMarker[] getAllValidationMarkers(IResource resource_p) {
    return getAllMarkersForType(EValidator.MARKER, resource_p);
  }
}