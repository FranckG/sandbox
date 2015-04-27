/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.util.Collection;

import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;
import com.thalesgroup.orchestra.framework.ui.viewer.VariableUsagesViewer;

/**
 * @author s0040806
 */
public class RenameVariablePage extends AbstractFormsWizardPage {

  protected Variable _variable;
  protected Context _context;

  protected VariableUsagesViewer _viewer;

  protected String _variableName;
  private Collection<AbstractVariable> _categoryVariables;

  /**
   * @param pageId_p
   */
  public RenameVariablePage(Variable variable_p, Context context_p) {
    super("RenameVariablePage"); //$NON-NLS-1$
    _variable = variable_p;
    _context = context_p;

    // Get all variables from the same category
    _categoryVariables = DataUtil.getVariables(_variable.getSuperCategory(), _context);

    setDescription(Messages.RenameVariablePage_Page_Description);
  }

  /**
   * Check if a variable does already exist in the category
   * @param name_p Variable name
   * @return
   */
  protected boolean existsVariable(String name_p) {
    for (AbstractVariable variable : _categoryVariables) {
      if (name_p.equals(variable.getName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite parent = new Composite(parent_p, SWT.NONE);
    GridLayout layout = new GridLayout(1, false);
    layout.marginWidth = 10;
    parent.setLayout(layout);
    GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
    parent.setLayoutData(data);

    Label contextLabel = new Label(parent, SWT.NONE);
    contextLabel.setText(Messages.RenameVariablePage_Context_Label + " " + _context.getName());
    Label variablePathLabel = new Label(parent, SWT.NONE);
    variablePathLabel.setText(Messages.RenameVariablePage_VariableLocation_Label + " "
                              + ModelUtil.getElementPath(ModelUtil.getCategory(_variable)).substring(1));

    Composite variableComposite = new Composite(parent, SWT.NONE);
    GridLayout variableLayout = new GridLayout(2, false);
    variableLayout.marginWidth = 0;
    variableLayout.horizontalSpacing = 5;
    variableComposite.setLayout(variableLayout);
    GridData variableData = new GridData(SWT.FILL, SWT.FILL, true, false);
    variableComposite.setLayoutData(variableData);

    final Text nameText =
        (Text) FormHelper.createLabelAndText(toolkit_p, variableComposite, Messages.RenameVariablePage_VariableName_Label, ICommonConstants.EMPTY_STRING,
            SWT.BORDER).get(Text.class);
    _variableName = _variable.getName();
    nameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
    nameText.setText(_variableName);
    nameText.selectAll();

    Group group = new Group(parent, SWT.NONE);
    group.setText(Messages.RenameVariablePage_ImpactedVariables_Label);
    GridLayout groupLayout = new GridLayout(1, true);
    layout.marginWidth = 0;
    group.setLayout(groupLayout);
    GridData groupData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
    group.setLayoutData(groupData);

    _viewer = new VariableUsagesViewer(_variable, _context);
    _viewer.createViewer(toolkit_p, group, null, null);

    final ControlDecoration nameTextDecorator =
        createDecoration(nameText, Messages.RenameVariablePage_ErrorMessage_VariableDoesExist, FieldDecorationRegistry.DEC_ERROR);
    nameTextDecorator.hide();

    ISWTObservableValue observeText = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(nameText, SWT.Modify));
    observeText.addValueChangeListener(new IValueChangeListener() {
      public void handleValueChange(ValueChangeEvent event_p) {
        if (existsVariable(nameText.getText())) {
          nameTextDecorator.show();
          _variableName = null;
        } else {
          _variableName = nameText.getText();
          nameTextDecorator.hide();
        }
        updateButtons();
      }
    });

    return parent;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.RenameVariablePage_Page_Title;
  }

  /**
   * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
   */
  @Override
  public boolean isPageComplete() {
    return null != _variableName && !_variableName.equals(_variable.getName());
  }

  public String getVariableName() {
    return _variableName;
  }

  protected void updateButtons() {
    setPageComplete(isPageComplete());
  }

}
