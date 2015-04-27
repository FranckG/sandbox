/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.ui;

import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext;

/**
 * This class defines a viewer to show the environment "use baseline" attributes.
 * @author T0052089
 */
public abstract class AbstractEnvironmentUseBaselineViewer {
  /**
   * The starting point context to display/to fill with parameters (see {@link UseBaselineEnvironmentContext#_useBaselineAttributes}).
   */
  protected final UseBaselineEnvironmentContext _useBaselineContext;

  /**
   * @param useBaselineContext_p
   */
  public AbstractEnvironmentUseBaselineViewer(UseBaselineEnvironmentContext useBaselineContext_p) {
    _useBaselineContext = useBaselineContext_p;
  }

  /**
   * Create controls for this EnvironmentUseBaselineViewer.<br>
   * Parent composite has no layout set, implementors have to set one.
   * @param parent_p The not <code>null</code> parent composite (layout to be set by implementation).
   * @param toolkit_p The {@link FormToolkit} implementation to use.
   * @param wizardContainer_p A way to update the containing (Starting Point) wizard button states.
   */
  public abstract void createControl(Composite parent_p, FormToolkit toolkit_p, IWizardContainer wizardContainer_p);

  /**
   * Enable/Disable the viewer.<br>
   * Whenever the view has to be disabled, every component of the viewer should be disabled
   * @param enabled_p true if to be enabled, false otherwise
   */
  public abstract void setEnabled(boolean enabled_p);

  /**
   * Dispose the viewer owned resources.<br>
   * Default implementation does nothing.
   */
  public void dispose() {
    // Default implementation does nothing.
  }
}