/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.commands;

import java.text.MessageFormat;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

import com.thalesgroup.orchestra.framework.connector.sdk.SdkWizardsActivator;
import com.thalesgroup.orchestra.framework.connector.sdk.helpers.WindowsRegistryFileHelper;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * @author T0052089
 */
public class UnDeployConnectorCommand extends AbstractHandler {
  private final String pluginId = SdkWizardsActivator.getDefault().getPluginId();

  /**
   * Create a new status with the same content as the given status but with the given severity. If given status is a multi-status, it is returned untouched.
   * @param status_p model status.
   * @param newSeverity_p
   * @return
   */
  public IStatus changeSeverity(IStatus status_p, int newSeverity_p) {
    if (status_p.isMultiStatus()) {
      return status_p;
    }
    return new Status(newSeverity_p, status_p.getPlugin(), status_p.getMessage(), status_p.getException());
  }

  /**
   * Delete the registry key linked to this connector project.
   * @param connectorProject_p the connector project.
   * @param hiveInitials_p the hive from which to delete the key.
   * @return
   */
  public IStatus deleteConnectorRegistryKey(IProject connectorProject_p, String hiveInitials_p) {
    // Find .reg file in the given project for the given hive.
    IResource fileResource = WindowsRegistryFileHelper.getRegistryFileResource(connectorProject_p, hiveInitials_p);
    if (null == fileResource) {
      return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.UnDeployConnectorCommand_ErrorStatusMessage_FileNotFound, hiveInitials_p));
    }
    // File is found, get the key from it.
    String registryKeyToDelete = WindowsRegistryFileHelper.getFirstRegistryKeyFromRegFile(fileResource);
    if (null == registryKeyToDelete) {
      return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.UnDeployConnectorCommand_ErrorStatusMessage_CantDeleteRegistryKey,
          hiveInitials_p));
    }
    // Delete the key.
    return WindowsRegistryFileHelper.deleteKeyInWindowsRegistry(registryKeyToDelete);
  }

  /**
   * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
   */
  public Object execute(ExecutionEvent event_p) throws ExecutionException {
    ISelection currentSelection = HandlerUtil.getCurrentSelection(event_p);
    // There is only one selected element (see plugin.xml - org.eclipse.core.expressions.definitions).
    IProject selectedProject = ConnectorCommandHelper.getFirstSelectedProject(currentSelection);
    if (null == selectedProject) {
      return null;
    }

    IStatus hkcuDeleteResult = deleteConnectorRegistryKey(selectedProject, WindowsRegistryFileHelper.HKCU_HIVE_INITIALS);

    if (hkcuDeleteResult.isOK()) {
      MessageDialog.openInformation(Display.getCurrent().getActiveShell(), Messages.UnDeployConnectorCommand_InfoDialogTitle_DeleteSuccessful,
          hkcuDeleteResult.getMessage());
    } else {
      IStatus[] childStatuses = { hkcuDeleteResult };
      MultiStatus multiStatus = new MultiStatus(pluginId, 0, childStatuses, Messages.UnDeployConnectorCommand_MultiStatusMessage_SelectDetails, null);
      DisplayHelper.displayErrorDialog(Messages.UnDeployConnectorCommand_ErrorDialogTitle_DeleteFailed,
          Messages.UnDeployConnectorCommand_ErrorDialogMessage_DeleteFailed, multiStatus);
    }
    return null;
  }
}
