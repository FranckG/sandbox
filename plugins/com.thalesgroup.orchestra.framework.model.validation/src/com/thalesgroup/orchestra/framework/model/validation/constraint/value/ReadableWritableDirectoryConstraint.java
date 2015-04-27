/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.value;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.FolderVariable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;
import com.thalesgroup.orchestra.framework.model.validation.constraint.Messages;

/**
 * Verify that ConfigurationDirectory, SharedDirectory and TemporaryDirectory are filled with a path to a readable and writable directory.
 * @author T0052089
 */
public class ReadableWritableDirectoryConstraint extends AbstractConstraint<FolderVariable> {
  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(FolderVariable target_p, IValidationContext context_p) {
    // This validation rule applies only on \Orchestra\ConfigurationDirectory, \Orchestra\SharedDirectory and \Orchestra\TemporaryDirectory.
    String elementPath = ModelUtil.getElementPath(target_p);
    if (!DataUtil.__CONFIGURATIONDIRECTORY_VARIABLE_NAME.equals(elementPath) && !DataUtil.__SHAREDDIRECTORY_VARIABLE_NAME.equals(elementPath)
        && !DataUtil.__TEMPORARYDIRECTORY_VARIABLE_NAME.equals(elementPath)) {
      return null;
    }
    // Get asking context.
    Context askingContext = getAskingContext();
    VariableValue directoryValue = DataUtil.getValues(target_p, askingContext).get(0);
    String fileName = directoryValue.getValue();
    File file = FileHelper.isValidAbsolutePath(fileName);
    // Check if the file is complete i.e. not a path like: '\temp\folder' with no driver
    boolean isNotExisting = false;
    if (null == file) {
      file = new File(fileName);
      isNotExisting = true;
    }
    String absoluteName = file.getAbsoluteFile().toString();
    if (!file.toString().equals(absoluteName)) {
      String message = MessageFormat.format(Messages.ReadableWritableDirectoryConstraint_Incoherent_Folder_Path, absoluteName);
      addStatus(createFailureStatusWithDescription(target_p, context_p, getFullPath(target_p), fileName, message));
    }

    // If the folder does not exist
    if (isNotExisting) {
      // The folder is null, therefore it does not exist or the given path is incoherent
      // As folder file does not exist it will be created, but a check must be done on the carrying folder to insure it has read/writ rights
      Path path = new Path(fileName);
      // Remove the non-existing to folder and only keep the father
      path = (Path) path.removeLastSegments(1);
      file = path.toFile();
    }

    if (!FileHelper.isDirectoryReadable(file) || !FileHelper.isDirectoryWritable(file)) {
      String message = Messages.ReadableWritableDirectoryConstraint_Folder_No_Rights;
      // If the folder does not exist, its parent folder was tested, therefore a different error message is required
      if (isNotExisting) {
        message = MessageFormat.format(Messages.ReadableWritableDirectoryConstraint_Parent_Folder_No_Rigths, file.toString());
      }
      addStatus(createFailureStatusWithDescription(target_p, context_p, getFullPath(target_p), fileName, message));
    }
    return null;
  }
}
