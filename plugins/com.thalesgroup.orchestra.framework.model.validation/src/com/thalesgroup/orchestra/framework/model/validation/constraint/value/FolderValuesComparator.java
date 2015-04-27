/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.value;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.FolderVariable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * {@link FolderVariable} {@link VariableValue} comparator.<br>
 * Compare two values searching for one value possibly containing the other.
 * @author t0076261
 */
public class FolderValuesComparator {
  /**
   * Compare specified folder values.<br>
   * Check whether :
   * <ul>
   * <li>The source value is containing the destination value, {@link ContainmentStatus#CONTAINING} is then returned.</li>
   * <li>The source value is contained in the destination value, {@link ContainmentStatus#CONTAINED} is then returned.</li>
   * <li>There is no hierarchical link between the twos, {@link ContainmentStatus#NONE} is then returned.</li>
   * </ul>
   * @param sourceValue_p
   * @param sourceAsRawValue_p <code>true</code> if source value is a raw one (that will be substituted), <code>false</code> if it is already substituted.
   * @param destinationValue_p
   * @param destinationAsRawValue_p <code>true</code> if destination value is a raw one (that will be substituted), <code>false</code> if it is already
   *          substituted.
   * @param context_p <code>null</code> if both values are already substituted, a substitution context otherwise.
   * @return {@link ContainmentStatus#ERROR} is returned in case parameters are not valid. See description for all others cases.
   */
  public ContainmentStatus compareValues(VariableValue sourceValue_p, boolean sourceAsRawValue_p, VariableValue destinationValue_p,
      boolean destinationAsRawValue_p, Context context_p) {
    // Precondition.
    if (sourceValue_p == destinationValue_p) {
      return ContainmentStatus.NONE;
    }
    // Get associated paths.
    IPath sourcePath = null;
    IPath destinationPath = null;
    VariableValue sourceValue = sourceValue_p;
    if (sourceAsRawValue_p) {
      sourceValue = DataUtil.getSubstitutedValue(sourceValue_p, context_p);
    }
    VariableValue destinationValue = destinationValue_p;
    if (destinationAsRawValue_p) {
      destinationValue = DataUtil.getSubstitutedValue(destinationValue_p, context_p);
    }
    // Make it case insensitive.
    if ((null != sourceValue) && (null != sourceValue.getValue())) {
      sourcePath = new Path(sourceValue.getValue().toLowerCase());
    }
    if ((null != destinationValue) && (null != destinationValue.getValue())) {
      destinationPath = new Path(destinationValue.getValue().toLowerCase());
    }
    // Precondition.
    if ((null == sourcePath) || (null == destinationPath)) {
      return ContainmentStatus.ERROR;
    }
    // Test prefixing.
    if (sourcePath.isPrefixOf(destinationPath)) {
      return ContainmentStatus.CONTAINING;
    } else if (destinationPath.isPrefixOf(sourcePath)) {
      return ContainmentStatus.CONTAINED;
    }
    return ContainmentStatus.NONE;
  }

  /**
   * Containment status.
   * @author t0076261
   */
  public static enum ContainmentStatus {
    CONTAINED, CONTAINING, ERROR, NONE;
  }
}