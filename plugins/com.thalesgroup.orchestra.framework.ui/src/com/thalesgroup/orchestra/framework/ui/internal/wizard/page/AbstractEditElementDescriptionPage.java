/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages;

/**
 * Abstract wizard page useful to display a description field.
 * @author T0052089
 */
public abstract class AbstractEditElementDescriptionPage extends AbstractEditNamedElementPage {
  /**
   * Constructor.
   * @param pageName_p
   */
  protected AbstractEditElementDescriptionPage(String pageName_p) {
    super(pageName_p);
  }

  /**
   * Add edition for description.
   * @param toolkit_p
   * @param parent_p
   * @return
   */
  protected Control addDescriptionEdition(FormToolkit toolkit_p, Composite parent_p) {
    Text text =
        (Text) FormHelper.createLabelAndText(toolkit_p, parent_p, Messages.AbstractEditVariablePage_Label_Title_Description, ICommonConstants.EMPTY_STRING,
            SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL).get(Text.class);
    GridData data = new GridData();
    data.horizontalAlignment = SWT.FILL;
    data.horizontalSpan = getContainerColumnsCount();
    data.verticalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = true;
    // Give a preferred vertical size to avoid too small or too big field.
    PixelConverter converter = new PixelConverter(JFaceResources.getDialogFont());
    data.heightHint = converter.convertHeightInCharsToPixels(3);
    text.setLayoutData(data);
    _bindingContext.bindValue(SWTObservables.observeText(text, SWT.FocusOut), EMFObservables.observeValue(getNamedElement(), getDescriptionEAttribute()));
    return text;
  }

  /**
   * Return the EMF attribute representing the description field of the edited model element.
   * @return
   */
  protected abstract EAttribute getDescriptionEAttribute();
}
