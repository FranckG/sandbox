/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.ui;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * A variables handler that provides UI and non-UI services on variables.<br>
 * The variables handling capability is indeed provided by the Framework.<br>
 * But this would lead to a cycling dependency with the environment bundle.<br>
 * This interface allows for an injection of content from the Framework to this bundle.
 * @author t0076261
 */
public interface IVariablesHandler {
  /**
   * Create variables browsing button in specified composite.
   * @param toolkit_p
   * @param buttonComposite_p
   * @param referencingValueHandler_p
   * @return A not <code>null</code> {@link Button}.
   */
  public Button createVariablesButton(final FormToolkit toolkit_p, Composite buttonComposite_p, IReferencingValueHandler referencingValueHandler_p);

  /**
   * Get substituted variable value from raw specified one.
   * @param rawValue_p
   * @return <code>null</code> if the raw value is <code>null</code>, itself if it can not be substituted. The substituted value otherwise.
   */
  public String getSubstitutedValue(String rawValue_p);

  /**
   * Get currently in-use variable type (ie the type of the variable being edited through this handler).
   * @return
   */
  public VariableType getVariableType();

  /**
   * Returns <code>true</code> if currently edited variable is read-only, <code>false</code> otherwise.
   * @return
   */
  public boolean isVariableReadOnly();

  /**
   * The referencing value handler.
   * @author t0076261
   */
  public interface IReferencingValueHandler {
    /**
     * Handle specified referencing value.<br>
     * The referenced value is the value selected in the dialog opened by the variables button contribution when ok is pressed.
     * @param referencingValue_p
     */
    public void handleSelectedValue(String referencingValue_p);
  }

  /**
   * Existing variables where environments are used.
   * @author t0076261
   */
  public enum VariableType {
    Artefacts, ConfigurationDirectories, Unspecified;
  }
}