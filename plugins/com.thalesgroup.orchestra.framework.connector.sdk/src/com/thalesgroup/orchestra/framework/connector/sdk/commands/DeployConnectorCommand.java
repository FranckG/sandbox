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
public class DeployConnectorCommand extends AbstractHandler {
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
    // Try to import connector data in HKCU.
    IStatus hkcuImportResult = importRegFileInRegistry(selectedProject, WindowsRegistryFileHelper.HKCU_HIVE_INITIALS);
    if (hkcuImportResult.isOK()) {
      return null;
    }
    // HKCU import failed !
    MultiStatus multiStatus =
        new MultiStatus(SdkWizardsActivator.getDefault().getPluginId(), 0, new IStatus[] { hkcuImportResult },
            Messages.DeployConnectorCommand_MultiStatusMessage_ImportFailed, null);
    DisplayHelper.displayErrorDialog(Messages.DeployConnectorCommand_ErrorDialogTitle_ImportFailed,
        Messages.DeployConnectorCommand_ErrorDialogMessage_ImportFailed, multiStatus);
    return null;
  }

  /**
   * @param connectorProject_p
   * @param hiveInitials_p
   * @return
   */
  public IStatus importRegFileInRegistry(IProject connectorProject_p, String hiveInitials_p) {
    IStatus importResult = null;
    IResource fileResource = WindowsRegistryFileHelper.getRegistryFileResource(connectorProject_p, hiveInitials_p);
    if (null == fileResource) {
      importResult =
          new Status(IStatus.ERROR, SdkWizardsActivator.getDefault().getPluginId(), MessageFormat.format(
              Messages.DeployConnectorCommand_ErrorStatusMessage_FileNotFound, hiveInitials_p));
    } else {
      // File is found, do the import.
      importResult = WindowsRegistryFileHelper.importRegFileInWindowsRegistry(fileResource.getLocation());
      if (importResult.isOK()) {
        MessageDialog.openInformation(Display.getCurrent().getActiveShell(), Messages.DeployConnectorCommand_InfoDialogTitle_ImportSuccessful,
            MessageFormat.format(Messages.DeployConnectorCommand_InfoDialogMessage_ImportSuccessful, fileResource.getFullPath()));
      }
    }
    return importResult;
  }
}
