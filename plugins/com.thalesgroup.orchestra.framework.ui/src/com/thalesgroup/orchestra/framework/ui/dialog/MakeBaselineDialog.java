/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.dialog;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditElementDescriptionPage;

/**
 * Make Baseline choose name dialog.
 * @author t0076261
 */
public class MakeBaselineDialog extends WizardDialog {
  /**
   * Constructor.
   * @param parentShell_p
   * @param makeBaselineWizard_p
   */
  private MakeBaselineDialog(Shell parentShell_p, MakeBaselineWizard makeBaselineWizard_p) {
    super(parentShell_p, makeBaselineWizard_p);
  }

  /**
   * Get baseline name as chosen by the user.
   * @param prefix_p The baseline name prefix. Can't be <code>null</code>.
   * @param nameValidator_p A validator for chosen baseline name. Should not be <code>null</code> (name collision would be valid then).
   * @return
   */
  public static BaselineProperties chooseBaselineProperties(final String prefix_p, final IValidator nameValidator_p) {
    // Precondition.
    if (null == prefix_p) {
      return null;
    }
    final BaselineProperties[] result = new BaselineProperties[] { null };
    // Display dialog.
    DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
      /**
       * @see java.lang.Runnable#run()
       */
      @SuppressWarnings("synthetic-access")
      public void run() {
        // Create wizard.
        MakeBaselineWizard wizard = new MakeBaselineWizard();
        // Retain prefix.
        wizard._prefix = prefix_p;
        // And validator.
        wizard._nameValidator = nameValidator_p;
        // Choose name dialog.
        MakeBaselineDialog dialog = new MakeBaselineDialog(getDisplay().getActiveShell(), wizard);
        // Open dialog.
        dialog.open();
        // Retain result.
        result[0] = wizard._baselineProperties;
      }
    }, false);
    // Return result.
    return result[0];
  }

  /**
   * Baseline properties, resulting from the wizard invocation.
   * @author t0076261
   */
  public static class BaselineProperties {
    /**
     * Baseline description.
     */
    public String _baselineDescription;
    /**
     * Baseline name suffix.
     */
    public String _baselineSuffix;
  }

  /**
   * Make baseline wizard page.
   * @author t0076261
   */
  public static class MakeBaselinePage extends AbstractEditElementDescriptionPage {
    /**
     * Fake context, used to retain name and description.
     */
    private Context _context;

    /**
     * Constructor.
     */
    public MakeBaselinePage() {
      super("MakeBaselinePage"); //$NON-NLS-1$
      _context = ContextsFactory.eINSTANCE.createContext();
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditNamedElementPage#activateBindingForName(org.eclipse.jface.databinding.swt.ISWTObservableValue,
     *      org.eclipse.core.databinding.observable.value.IObservableValue, com.thalesgroup.orchestra.framework.common.util.Couple)
     */
    @Override
    protected void activateBindingForName(ISWTObservableValue controlObservableValue_p, IObservableValue observableValue_p,
        final Couple<EObject, EStructuralFeature> elementAttribute_p) {
      // Make sure there is something to validate.
      if (null != getWizard()._nameValidator) {
        // Create proxy validator that reports to LiveValidationHandler.
        IValidator proxyValidator = new IValidator() {
          /**
           * @see org.eclipse.core.databinding.validation.IValidator#validate(java.lang.Object)
           */
          @SuppressWarnings("synthetic-access")
          public IStatus validate(Object value_p) {
            IStatus result = getWizard()._nameValidator.validate(value_p);
            if (!result.isOK()) {
              _liveValidationHandler.handleValidationFailed(result, elementAttribute_p);
            } else {
              _liveValidationHandler.handleValidationSuccessful(elementAttribute_p);
            }
            return result;
          }
        };
        _bindingContext.bindValue(controlObservableValue_p, observableValue_p, new UpdateValueStrategy().setBeforeSetValidator(proxyValidator), null);
      } else {
        super.activateBindingForName(controlObservableValue_p, observableValue_p, elementAttribute_p);
      }
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditNamedElementPage#doCreateControl(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.ui.forms.widgets.FormToolkit)
     */
    @Override
    protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
      // Do create composite first.
      Composite composite = super.doCreateControl(parent_p, toolkit_p);
      // Then add description.
      addDescriptionEdition(toolkit_p, composite);
      return composite;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractPageWithLiveValidation#doDisposeLiveValidation()
     */
    @Override
    protected void doDisposeLiveValidation() {
      // Do nothing.
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractPageWithLiveValidation#doRegisterLiveValidation()
     */
    @Override
    protected void doRegisterLiveValidation() {
      // Do nothing.
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditNamedElementPage#getContainerColumnsCount()
     */
    @Override
    protected int getContainerColumnsCount() {
      // 2 columns should be enough (in this case).
      return 2;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditElementDescriptionPage#getDescriptionEAttribute()
     */
    @Override
    protected EAttribute getDescriptionEAttribute() {
      return ContextsPackage.Literals.CONTEXT__DESCRIPTION;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditNamedElementPage#getNamedElement()
     */
    @Override
    protected Context getNamedElement() {
      return _context;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditNamedElementPage#getNameLabel()
     */
    @Override
    protected String getNameLabel() {
      return getWizard()._prefix;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageTitle()
     */
    @Override
    protected String getPageTitle() {
      return Messages.MakeBaselineDialog_Page_Title;
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#getWizard()
     */
    @Override
    public MakeBaselineWizard getWizard() {
      return (MakeBaselineWizard) super.getWizard();
    }
  }

  /**
   * Make baseline wizard.
   * @author t0076261
   */
  public static class MakeBaselineWizard extends AbstractFormsWizard {
    /**
     * Baseline resulting properties.
     */
    protected BaselineProperties _baselineProperties;
    /**
     * Make baseline page.
     */
    protected MakeBaselinePage _makeBaselinePage;
    /**
     * Baseline name validator.
     */
    protected IValidator _nameValidator;
    /**
     * Baseline name prefix.
     */
    protected String _prefix;

    /**
     * Constructor.
     */
    public MakeBaselineWizard() {
      _makeBaselinePage = new MakeBaselinePage();
      addPage(_makeBaselinePage);
      setWindowTitle(Messages.MakeBaselineDialog_Window_Title);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performCancel()
     */
    @Override
    public boolean performCancel() {
      // Make sure resulting structure is nullified.
      _baselineProperties = null;
      // And free wizard.
      return true;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
      // Create resulting structure.
      _baselineProperties = new BaselineProperties();
      _baselineProperties._baselineDescription = _makeBaselinePage.getNamedElement().getDescription();
      _baselineProperties._baselineSuffix = _makeBaselinePage.getNamedElement().getName();
      // And free wizard.
      return true;
    }
  }
}