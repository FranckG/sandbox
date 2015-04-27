/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.model.validation.LiveValidationContentAdapter;
import com.thalesgroup.orchestra.framework.model.validation.LiveValidationContentAdapter.ILiveValidationHandler;
import com.thalesgroup.orchestra.framework.root.ui.UIUtils;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;

/**
 * An {@link AbstractFormsWizardPage} that adds the live validation support and automatic association to error text and fields decorations.
 * @author t0076261
 */
public abstract class AbstractPageWithLiveValidation extends AbstractFormsWizardPage {
  /**
   * UI plug-in ID, useful to create statuses.
   */
  public static final String UI_PLUGIN_ID = OrchestraFrameworkUiActivator.getDefault().getPluginId();
  /**
   * Model elements attributes on error.
   */
  protected Collection<Couple<EObject, EStructuralFeature>> _elementsOnError;
  /**
   * Live validation handler.
   */
  protected ILiveValidationHandler _liveValidationHandler;
  /**
   * Model element to decorations map.
   */
  protected Map<Couple<EObject, EStructuralFeature>, ControlDecoration> _modelElementToDecoration;

  /**
   * Constructor.
   * @param pageId_p
   */
  public AbstractPageWithLiveValidation(String pageId_p) {
    super(pageId_p);
    // Decorations structure.
    _modelElementToDecoration = new HashMap<Couple<EObject, EStructuralFeature>, ControlDecoration>(0);
    // Erroneous model elements.
    _elementsOnError = new HashSet<Couple<EObject, EStructuralFeature>>(0);
    // Live validation handler.
    _liveValidationHandler = new LiveValidationHandler();
    // Register live validation mechanism.
    doRegisterLiveValidation();
  }

  /**
   * Create control decoration for specified control.
   * @param control_p control on which the decoration is added
   * @param elementAttribute_p model attribute linked to this decoration
   * @return
   */
  protected ControlDecoration createDecoration(Control control_p, Couple<EObject, EStructuralFeature> elementAttribute_p) {
    // Precondition.
    if ((null == control_p) || (null == elementAttribute_p)) {
      return null;
    }
    // Create decoration.
    ControlDecoration controlDecoration = new ControlDecoration(control_p, SWT.LEFT | SWT.TOP);
    // Add it.
    _modelElementToDecoration.put(elementAttribute_p, controlDecoration);
    // Return it.
    return controlDecoration;
  }

  /**
   * @see org.eclipse.jface.dialogs.DialogPage#dispose()
   */
  @Override
  public void dispose() {
    super.dispose();
    // Unregister handler.
    doDisposeLiveValidation();
    // Clear decorations.
    _modelElementToDecoration.clear();
  }

  /**
   * Do dispose live validation mechanism.
   */
  protected void doDisposeLiveValidation() {
    // Unregister handler.
    if (null != _liveValidationHandler) {
      LiveValidationContentAdapter.getInstance().removeLiveValidationHandler(_liveValidationHandler);
      _liveValidationHandler = null;
    }
  }

  /**
   * Called by isPageComplete. Should be overridden by sub classes.
   * @return an {@link IStatus}, with a severity and a message to be displayed in the dialog. If the returned severity is ERROR, isComplete() will return false.
   */
  protected IStatus doIsPageComplete() {
    if (!_elementsOnError.isEmpty()) {
      return new Status(IStatus.ERROR, UI_PLUGIN_ID, Messages.AbstractPageWithLiveValidation_ValidationFailed_HeaderMessage);
    }
    return Status.OK_STATUS;
  }

  /**
   * Do register live validation mechanism.
   */
  protected void doRegisterLiveValidation() {
    // Register handler.
    LiveValidationContentAdapter.getInstance().addLiveValidationHandler(_liveValidationHandler);
  }

  /**
   * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
   */
  @Override
  public boolean isPageComplete() {
    IStatus doIsPageCompleteStatus = doIsPageComplete();
    if (doIsPageCompleteStatus.isOK()) {
      // By default, if everything is OK -> no message.
      setMessage(null);
    } else {
      // Else display a message with the correct severity.
      setMessage(doIsPageCompleteStatus.getMessage(), UIUtils.statusSeverityToMessageProviderConstant(doIsPageCompleteStatus.getSeverity()));
    }
    return IStatus.ERROR != doIsPageCompleteStatus.getSeverity();
  }

  /**
   * Live validation handler that automatically change states of created decorations.
   * @author t0076261
   */
  protected final class LiveValidationHandler implements ILiveValidationHandler {
    /**
     * Do handle error status recursively (if needed).
     * @param validationStatus_p
     */
    protected void doHandleErrorStatus(IStatus validationStatus_p, Couple<EObject, EStructuralFeature> changedElement_p) {
      // Recursively deal with children.
      if (validationStatus_p.isMultiStatus()) {
        for (IStatus status : validationStatus_p.getChildren()) {
          doHandleErrorStatus(status, changedElement_p);
        }
      } else if (null != _modelElementToDecoration.get(changedElement_p)) { // Make sure this status will be useful.
        ControlDecoration decoration = _modelElementToDecoration.get(changedElement_p);
        // Set decoration text.
        decoration.setDescriptionText(validationStatus_p.getMessage());
        // Set decoration image.
        String decorationImageKey = null;
        switch (validationStatus_p.getSeverity()) {
          case IStatus.ERROR:
            decorationImageKey = FieldDecorationRegistry.DEC_ERROR;
          break;
          case IStatus.INFO:
            decorationImageKey = FieldDecorationRegistry.DEC_INFORMATION;
          break;
          case IStatus.WARNING:
            decorationImageKey = FieldDecorationRegistry.DEC_WARNING;
          break;
          default:
          break;
        }
        if (null != decorationImageKey) {
          decoration.setImage(FieldDecorationRegistry.getDefault().getFieldDecoration(decorationImageKey).getImage());
        }
        // Show decoration.
        decoration.show();
      }
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.validation.LiveValidationContentAdapter.ILiveValidationHandler#handleValidationFailed(org.eclipse.core.runtime.IStatus)
     */
    public void handleValidationFailed(IStatus validationStatus_p, Couple<EObject, EStructuralFeature> changedElement_p) {
      // Precondition.
      if (null == validationStatus_p) {
        return;
      }
      _elementsOnError.add(changedElement_p);
      // Page completion management.
      setPageComplete(isPageComplete());
      // Do handle status.
      doHandleErrorStatus(validationStatus_p, changedElement_p);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.validation.LiveValidationContentAdapter.ILiveValidationHandler#handleValidationSuccessful()
     */
    public void handleValidationSuccessful(Couple<EObject, EStructuralFeature> changedElement_p) {
      // Remove valid field from erroneous fields list.
      _elementsOnError.remove(changedElement_p);
      // Page completion management.
      setPageComplete(isPageComplete());
      // Find decoration linked to changed element and hide it.
      ControlDecoration decoration = _modelElementToDecoration.get(changedElement_p);
      if (decoration != null) {
        decoration.hide();
      }
    }
  }
}