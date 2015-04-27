/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.dialog;

import java.util.Map;

import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.ui.dialog.ChooseBaselineDialog.BaselineSelectionType;
import com.thalesgroup.orchestra.framework.ui.wizard.NewUseBaselineContextWizard;
import com.thalesgroup.orchestra.framework.ui.wizard.NewUseBaselineContextWizard.UseBaselineContextsModel;

/**
 * Use Baseline dialog.
 * @author T0052089
 */
public class UseBaselineDialog extends WizardDialog {
  /**
   * Constructor.
   * @param parentShell_p
   * @param newStartingPointContextWizard_p
   */
  private UseBaselineDialog(Shell parentShell_p, NewUseBaselineContextWizard newStartingPointContextWizard_p) {
    super(parentShell_p, newStartingPointContextWizard_p);
  }

  /**
   * @param baselineRootContextsProject_p
   * @param baselineName_p
   * @return
   */
  public static UseBaselineProperties getUseBaselineParameters(RootContextsProject baselineRootContextsProject_p, String baselineName_p,
      BaselineSelectionType baselineSelection_p) {
    // Create wizard.
    final NewUseBaselineContextWizard newStartingPointContextWizard =
        new NewUseBaselineContextWizard(baselineRootContextsProject_p, baselineName_p, baselineSelection_p);
    final int[] wizardResult = new int[1];
    // Display dialog.
    DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        // Create wizard dialog.
        WizardDialog newStartingPointContextWizardDialog = new WizardDialog(getDisplay().getActiveShell(), newStartingPointContextWizard);
        wizardResult[0] = newStartingPointContextWizardDialog.open();
      }
    }, false);
    // Wizard was correctly finished.
    if (Window.OK == wizardResult[0]) {
      // Get model content.
      UseBaselineContextsModel model = newStartingPointContextWizard.getUseBaselineContextsModel();
      // Create result structure.
      UseBaselineProperties result = new UseBaselineProperties();
      result._createdUseBaselineContext = model.getContext();
      result._envValueToUseBaselineContext = model.getEnvValueToUseBaselineContext();
      return result;
    }
    return null;
  }

  /**
   * Result structure for this dialog.
   * @author T0052089
   */
  public static class UseBaselineProperties {
    public Context _createdUseBaselineContext;
    public Map<EnvironmentVariableValue, UseBaselineEnvironmentContext> _envValueToUseBaselineContext;
  }
}
