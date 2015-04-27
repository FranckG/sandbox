/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;

import junit.framework.Assert;

/**
 * As an administrator export a project to various locations, with or without errors expected.
 * @author t0076261
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class ExportContextTests extends AbstractContextTest {
  /**
   * As an administrator, export a context to an external empty folder.
   * @throws Exception
   */
  @Test
  public void canExportToAnEmptyFolder() throws Exception {
    final String adminContext = "ExportAsAnAdministrator";
    // Create test structure.
    initializeTest();
    changeModeTo(true);
    String exportToFolder = getNewDedicatedTemporaryFolder();
    String administratorContextName = createContext(adminContext, false);
    {
      SWTBotTree tree = getBot().tree().select(getAdministratorContextLabelInTree(administratorContextName));
      tree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.ExportContextsAction_Action_Text).click();
      getBot().shell(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.ExportContextsWizard_Wizard_Title).activate();
      // Get context node (under its directory node).
      String contextParentDirectory = getProjectParentDirectoryFromContextName(administratorContextName);
      getBot().tree().getTreeItem(contextParentDirectory).getNode(administratorContextName).check();
      getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.ExportContextsPage_Label_Text_Location).setText(
          exportToFolder);
      getBot().button(IDialogConstants.FINISH_LABEL).click();
      Assert.assertNotNull("Expecting to find exported context '" + administratorContextName + "' at test location " + exportToFolder,
          findContextForName(administratorContextName, exportToFolder, true));
    }
    // Clean test structure.
    FileUtils.deleteDirectory(new File(exportToFolder));
    /*
     * Clean up.
     */
    deleteAdminContext(adminContext);
  }

  /**
   * As an administrator, export a context to its current location.<br>
   * It should fail.
   * @throws Exception
   */
  @Test
  public void canNotExportToItsLocation() throws Exception {
    final String adminContext = "ExportAsAnAdministratorToItsLocation";
    // Create test structure.
    initializeTest();
    changeModeTo(true);
    String administratorContextName = createContext(adminContext, false);
    RootContextsProject project = findContextForName(administratorContextName, false).getKey();
    String exportToFolder = project.getLocation();
    {
      SWTBotTree tree = getBot().tree().select(getAdministratorContextLabelInTree(administratorContextName));
      tree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.ExportContextsAction_Action_Text).click();
      SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.ExportContextsWizard_Wizard_Title);
      shell.activate();
      // Check context node (under its parent directory).
      String contextParentDirectory = getProjectParentDirectoryFromContextName(administratorContextName);
      getBot().tree().getTreeItem(contextParentDirectory).getNode(administratorContextName).check();
      getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.ExportContextsPage_Label_Text_Location).setText(
          exportToFolder);
      Assert.assertFalse(getBot().button(IDialogConstants.FINISH_LABEL).isEnabled());
      shell.close();
    }
    deleteAdminContext(adminContext);
  }

  /**
   * As an administrator, export a context to a new location.<br>
   * Then try and export it again to this location.<br>
   * It should be feasible, but a warning popup should say that the content will be overridden.
   * @throws Exception
   */
  @Test
  public void warnExportToAnAlreadyExistingExportLocation() throws Exception {
    final String adminContext = "ExportTwiceAsAnAdministrator";
    // Create test structure.
    initializeTest();
    changeModeTo(true);
    String exportToFolder = getNewDedicatedTemporaryFolder();
    String administratorContextName = createContext(adminContext, false);
    // First export.
    {
      RootContextsProject project = findContextForName(administratorContextName, false).getKey();
      IStatus status = ProjectActivator.getInstance().getProjectHandler().exportProjectTo(project._project, exportToFolder, true);
      Assert.assertTrue("First export of '" + administratorContextName + "' at test location " + exportToFolder + " should be ok.", status.isOK());
    }
    // Second one.
    {
      SWTBotTree tree = getBot().tree().select(getAdministratorContextLabelInTree(administratorContextName));
      tree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.ExportContextsAction_Action_Text).click();
      getBot().shell(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.ExportContextsWizard_Wizard_Title).activate();
      String contextParentDirectory = getProjectParentDirectoryFromContextName(administratorContextName);
      getBot().tree().getTreeItem(contextParentDirectory).getNode(administratorContextName).check();
      getBot().textWithLabel(com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages.ExportContextsPage_Label_Text_Location).setText(
          exportToFolder);
      getBot().button(IDialogConstants.FINISH_LABEL).click();
      SWTBotShell shell = getBot().shell(com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages.ExportContextsWizard_Dialog_Confirm_Title);
      Assert.assertTrue("Should warn the user of overriding behavior if export proceeds", shell.isOpen() && shell.isVisible());
      shell.activate();
      getBot().button(IDialogConstants.OK_LABEL).click();
    }
    // Clean test structure.
    FileUtils.deleteDirectory(new File(exportToFolder));
    deleteAdminContext(adminContext);
  }
}