/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages;

/**
 * Abstract edit named element wizard page.
 * @author t0076261
 */
public abstract class AbstractEditNamedElementPage extends AbstractPageWithLiveValidation {
  /**
   * Binding context.
   */
  protected DataBindingContext _bindingContext;

  /**
   * Constructor.
   * @param pageName_p
   */
  protected AbstractEditNamedElementPage(String pageName_p) {
    super(pageName_p);
  }

  /**
   * Activate binding for name edition.
   * @param controlObservableValue_p
   * @param observableValue_p
   */
  protected void activateBindingForName(ISWTObservableValue controlObservableValue_p, IObservableValue observableValue_p,
      Couple<EObject, EStructuralFeature> elementAttribute_p) {
    _bindingContext.bindValue(controlObservableValue_p, observableValue_p);
  }

  /**
   * Add edition for name.
   * @param toolkit_p
   * @param parent_p
   * @return
   */
  protected Control addNameEdition(FormToolkit toolkit_p, Composite parent_p) {
    // Create text control.
    Text text = (Text) FormHelper.createLabelAndText(toolkit_p, parent_p, getNameLabel(), ICommonConstants.EMPTY_STRING, SWT.BORDER).get(Text.class);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(text, LayoutType.GRID_LAYOUT);
    GridData data = (GridData) text.getLayoutData();
    data.horizontalSpan = getContainerColumnsCount() - 1;
    data.grabExcessVerticalSpace = false;
    // Enable binding.
    ISWTObservableValue observeText = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(text, SWT.Modify));
    IObservableValue observeValue = EMFObservables.observeValue(getNamedElement(), ContextsPackage.Literals.NAMED_ELEMENT__NAME);
    // Create target structure.
    Couple<EObject, EStructuralFeature> elementAttribute =
        new Couple<EObject, EStructuralFeature>(getNamedElement(), ContextsPackage.Literals.NAMED_ELEMENT__NAME);
    // Enable live validation decoration.
    createDecoration(text, elementAttribute);
    // Activate binding.
    activateBindingForName(observeText, observeValue, elementAttribute);
    return text;
  }

  /**
   * @see org.eclipse.jface.dialogs.DialogPage#dispose()
   */
  @Override
  public void dispose() {
    try {
      super.dispose();
    } finally {
      if (null != _bindingContext) {
        _bindingContext.dispose();
        _bindingContext = null;
      }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, getContainerColumnsCount(), false);
    // Leave enough space to add decorations.
    GridLayout layout = (GridLayout) composite.getLayout();
    layout.horizontalSpacing = FieldDecorationRegistry.getDefault().getMaximumDecorationWidth();
    // Create binding context.
    _bindingContext = new DataBindingContext();
    /* Name */
    addNameEdition(toolkit_p, composite);
    return composite;
  }

  /**
   * Get parent composite grid layout columns count.
   * @return
   */
  protected int getContainerColumnsCount() {
    return 3;
  }

  /**
   * Get named element.
   * @return
   */
  protected abstract NamedElement getNamedElement();

  /**
   * @return
   */
  protected String getNameLabel() {
    return Messages.AbstractEditVariablePage_Label_Title_Name;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return null;
  }
}