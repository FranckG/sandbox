/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.ui;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.AbstractFormPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetsToComplete;
import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetsToComplete.BaselineSet;
import com.thalesgroup.orchestra.doors.framework.environment.ui.DoorsEnvironmentCompleteBaselineSetsWizard.DoorsEnvironmentCompleteBaselineSetsWizardPage;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;

/**
 * Baselines details page.
 * @author S0035580
 */
public class BaselinesDetailsPage extends AbstractFormPart implements IDetailsPage {
  private BaselineSetsToComplete _baselineSetsToComplete;

  /**
   * Initial input - the selected BaselineSet object in the list from master selection.
   */
  protected Object _initialInput;

  private DoorsEnvironmentCompleteBaselineSetsWizardPage _wizardPage;

  private IManagedForm mform;

  /**
   * @param initialInput_p The initial input (here the selected BaselineSet object in the list).
   * @param bstc_p The list of baselinetSet to complete.
   * @param wizardpage_p the wizard page.
   */
  public BaselinesDetailsPage(Object initialInput_p, BaselineSetsToComplete bstc_p, DoorsEnvironmentCompleteBaselineSetsWizardPage wizardpage_p) {
    _initialInput = initialInput_p;
    _baselineSetsToComplete = bstc_p;
    _wizardPage = wizardpage_p;
  }

  /**
   * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
   */
  public void createContents(Composite parent_p) {
    GridLayout layout = new GridLayout(1, false);
    parent_p.setLayout(layout);

    FormToolkit toolkit = mform.getToolkit();
    Section s1 = toolkit.createSection(parent_p, ExpandableComposite.TITLE_BAR);
    s1.setText(Messages.DoorsEnvironment_completeBaselineSets_details);
    s1.setLayoutData(new GridData(GridData.FILL_BOTH));

    Composite client = toolkit.createComposite(s1, SWT.WRAP);
    client.setLayout(new GridLayout(2, false));
    client.setLayoutData(new GridData(GridData.FILL_BOTH));

    Label version = new Label(client, SWT.NONE);
    version.setText(Messages.DoorsEnvironment_completeBaselineSets_version);
    version.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, false));

    Composite myButtonsRadio = new Composite(client, SWT.NONE);
    myButtonsRadio.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    myButtonsRadio.setLayout(new FillLayout(SWT.HORIZONTAL));

    Button major = new Button(myButtonsRadio, SWT.RADIO);
    major.setText(Messages.DoorsEnvironment_completeBaselineSets_major);
    major.setSelection(getMajorVersion());
    major.addSelectionListener(new SelectionListener() {

      public void widgetDefaultSelected(SelectionEvent e_p) {
        // Nothing to do.
      }

      @SuppressWarnings("synthetic-access")
      public void widgetSelected(SelectionEvent e_p) {
        ((BaselineSet) _initialInput).setVersion(1);
        update();
      }
    });
    Button minor = new Button(myButtonsRadio, SWT.RADIO);
    minor.setText(Messages.DoorsEnvironment_completeBaselineSets_minor);
    minor.setSelection(getMinorVersion());
    minor.addSelectionListener(new SelectionListener() {
      public void widgetDefaultSelected(SelectionEvent e_p) {
        // Nothing to do.
      }

      @SuppressWarnings("synthetic-access")
      public void widgetSelected(SelectionEvent e_p) {
        ((BaselineSet) _initialInput).setVersion(2);
        update();
      }
    });

    Label mysecondLabel = new Label(client, SWT.NONE);
    mysecondLabel.setText(Messages.DoorsEnvironment_completeBaselineSets_suffix);

    final Text suffix = new Text(client, SWT.SINGLE | SWT.BORDER);
    suffix.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    String suffixString = getSuffix();
    if (null != suffixString) {
      suffix.setText(suffixString);
      updateErrorMessage();
    }
    suffix.addModifyListener(new ModifyListener() {
      @SuppressWarnings("synthetic-access")
      public void modifyText(ModifyEvent e_p) {
        ((BaselineSet) _initialInput).setSuffix(suffix.getText());
        update();
        updateErrorMessage();
      }
    });

    Label mythirdLabel = new Label(client, SWT.FILL);
    mythirdLabel.setText(Messages.DoorsEnvironment_completeBaselineSets_description);

    final Text description = new Text(client, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP);
    description.setLayoutData(new GridData(GridData.FILL_BOTH));
    String descriptionString = getDescription();
    if (null != descriptionString) {
      description.setText(descriptionString);
    }
    description.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e_p) {
        ((BaselineSet) _initialInput).setDescription(description.getText());
      }
    });

    s1.setClient(client);
  }

  /**
   * @return the description of baseline.
   */
  private String getDescription() {
    if (null == ((BaselineSet) _initialInput).getDescription()) {
      if (null != _wizardPage.getOrchestraBaselineDescription()) {
        ((BaselineSet) _initialInput).setDescription(_wizardPage.getOrchestraBaselineDescription());
        return _wizardPage.getOrchestraBaselineDescription();
      }
      return ICommonConstants.EMPTY_STRING;
    }
    return ((BaselineSet) _initialInput).getDescription();
  }

  /**
   * @return true if baseline version is major.
   */
  private boolean getMajorVersion() {
    if (1 == ((BaselineSet) _initialInput).getVersion()) {
      return true;
    }
    return false;
  }

  /**
   * @return true if baseline version is minor.
   */
  private boolean getMinorVersion() {
    if (2 == ((BaselineSet) _initialInput).getVersion()) {
      return true;
    }
    return false;
  }

  /**
   * @return the suffix of the baseline.
   */
  private String getSuffix() {
    if (null == ((BaselineSet) _initialInput).getSuffix()) {
      ((BaselineSet) _initialInput).setSuffix(_wizardPage.getOrchestraBaselineName());
      return _wizardPage.getOrchestraBaselineName();
    }
    return ((BaselineSet) _initialInput).getSuffix();
  }

  @Override
  public void initialize(IManagedForm form) {
    this.mform = form;
  }

  /**
   * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
   */
  public void selectionChanged(IFormPart part_p, ISelection selection_p) {
    // Not much to do here yet.
  }

  private void update() {
    // Validate basalineSet and enable wizard dialog finish button.
    if (_baselineSetsToComplete.isValid()) {
      _wizardPage.setPageComplete(true);
    } else {
      _wizardPage.setPageComplete(false);
    }
  }

  /**
   * 
   */
  protected void updateErrorMessage() {
    String suffix = getSuffix();
    if (suffix.matches(".*!.*")) { //$NON-NLS-1$
      _wizardPage.setErrorMessage(Messages.DoorsEnvironment_completeBaselineSets_error_message_illegal_character);
    } else {
      _wizardPage.setErrorMessage(null);
    }

  }

}
