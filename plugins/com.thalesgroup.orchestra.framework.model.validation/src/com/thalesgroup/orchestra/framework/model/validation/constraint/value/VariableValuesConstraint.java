/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.value;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.FileVariable;
import com.thalesgroup.orchestra.framework.model.contexts.FolderVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.validation.baseline.MakeBaselineValidationContext;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;
import com.thalesgroup.orchestra.framework.model.validation.constraint.Messages;

/**
 * Variable values constraint.<br>
 * A file variable value should point to existing file(s). A folder variable value should point to existing folder(s).
 * @author t0076261
 */
public class VariableValuesConstraint extends AbstractConstraint<Variable> {
  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(final Variable target_p, final IValidationContext context_p) {
    final Context askingContext = getAskingContext();
    final List<VariableValue> values = DataUtil.getRawValues(target_p, askingContext);
    // Special case for Make Baseline process.
    {
      // Check that this is indeed the case.
      if (getValidationContext() instanceof MakeBaselineValidationContext) {
        // Make sure variable does not come from DefaultContext too.
        // Variables from default context are making use of (OS) environment variables for temporary places, so it's ok not to warn about them.
        Context variableContext = ModelUtil.getContext(target_p);
        ContextsResourceImpl variableResource = (null != variableContext) ? variableContext.eResource() : null;
        if ((null != variableResource) && !variableResource.isDefault()) {
          // Get MakeBaselineValidationContext.
          MakeBaselineValidationContext makeBaselineValidationContext = (MakeBaselineValidationContext) getValidationContext();
          // Before validating check variable values against usage of OS env. variables.
          // Such variables are unlikely to be persisted by the context, and as such prone to error when the baseline will have to be restored.
          // A warning to the user is expected to enforce user approval about proceeding with the make baseline process.
          String variablePath = ModelUtil.getElementPath(target_p);
          for (VariableValue variableValue : values) {
            makeBaselineValidationContext.addEnvironmentVariableNamesFor(variablePath, variableValue.getValue());
          }
        }
      }
    }
    new ContextsSwitch<Object>() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseFileVariable(com.thalesgroup.orchestra.framework.model.contexts.FileVariable)
       */
      @SuppressWarnings("synthetic-access")
      @Override
      public Object caseFileVariable(FileVariable object_p) {
        // The variable values must point to existing files.
        for (VariableValue variableValue : values) {
          VariableValue substitutedValue = DataUtil.getSubstitutedValue(variableValue, askingContext);
          String value = (null != substitutedValue) ? substitutedValue.getValue() : null;
          // Ignore value if variable is not mandatory and value is null or empty.
          if (!object_p.isMandatory() && ((null == value) || value.trim().isEmpty())) {
            continue;
          }
          File file = FileHelper.isValidAbsolutePath(value);
          if ((null == file) || !file.isFile()) {
            addStatus(createFailureStatusWithDescription(variableValue, context_p, getFullPath(target_p), value, Messages.VariableValuesConstraint_File_Type,
                variableValue.getValue()));
          }
        }
        return null;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseFolderVariable(com.thalesgroup.orchestra.framework.model.contexts.FolderVariable)
       */
      @SuppressWarnings("synthetic-access")
      @Override
      public Object caseFolderVariable(FolderVariable object_p) {
        // The variable values must point to existing folders.
        for (VariableValue variableValue : values) {
          VariableValue substitutedValue = DataUtil.getSubstitutedValue(variableValue, askingContext);
          String value = (null != substitutedValue) ? substitutedValue.getValue() : null;
          // Ignore value if variable is not mandatory and value is null or empty.
          if (!object_p.isMandatory() && ((null == value) || value.trim().isEmpty())) {
            continue;
          }
          File file = FileHelper.isValidAbsolutePath(value);
          if ((null == file) || !file.isDirectory()) {
            addStatus(createFailureStatusWithDescription(variableValue, context_p, getFullPath(target_p), value, Messages.VariableValuesConstraint_Folder_Type,
                variableValue.getValue()));
          }
        }
        return null;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#defaultCase(org.eclipse.emf.ecore.EObject)
       */
      @Override
      public Object defaultCase(EObject object_p) {
        return null;
      }
    }.doSwitch(target_p);
    // Make sure null is returned.
    return null;
  }
}