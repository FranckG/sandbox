/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.ui;

import java.util.ArrayList;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsActivator;
import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetsToComplete;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author S0035580
 */
public class DoorsEnvironmentCompleteBaselineSetsWizard extends AbstractFormsWizard {

  /**
   * BaselinesSets URIs to be completed.
   */
  public BaselineSetsToComplete _baselineSetsToComplete;
  /**
   * resulting completed BaselinesSets URIs.
   */
  public ArrayList<OrchestraURI> _completedBaselineSets;
  /**
   * Doors Environment make baseline page.
   */
  protected final DoorsEnvironmentCompleteBaselineSetsWizardPage _doorsEnvironmentCompleteBaselineSetsWizardPage;

  /**
   * Description of Orchestra baseline
   */
  private String _orchestraBaselineDescription;

  /**
   * Name of Orchestra baseline
   */
  private String _orchestraBaselineName;

  /**
   * Constructor.
   */
  public DoorsEnvironmentCompleteBaselineSetsWizard(String wizardTitle_p, BaselineSetsToComplete baselinesToComplete_p, String orchestraBaselineName_p,
      String orchestraBaselineDesc_p) {
    _doorsEnvironmentCompleteBaselineSetsWizardPage = new DoorsEnvironmentCompleteBaselineSetsWizardPage(baselinesToComplete_p);
    _baselineSetsToComplete = baselinesToComplete_p;
    _orchestraBaselineName = orchestraBaselineName_p;
    _orchestraBaselineDescription = orchestraBaselineDesc_p;
    setWindowTitle(wizardTitle_p);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    addPage(_doorsEnvironmentCompleteBaselineSetsWizardPage);
  }

  public String getOrchestraBaselineDesc() {

    return _orchestraBaselineDescription;
  }

  public String getOrchestraBaselineName() {

    return _orchestraBaselineName;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performCancel()
   */
  @Override
  public boolean performCancel() {
    // Make sure resulting structure is nullified.
    _completedBaselineSets = null;
    // And free wizard.
    return true;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    _completedBaselineSets = _baselineSetsToComplete.getCompletedBaselineSets();
    // And free wizard.
    return true;
  }

  protected static class DoorsEnvironmentCompleteBaselineSetsWizardPage extends AbstractFormsWizardPage {

    protected BaselineSetsToComplete _baselinesToComplete;

    /**
     * Constructor.
     */
    public DoorsEnvironmentCompleteBaselineSetsWizardPage(BaselineSetsToComplete bstc) {
      super("DoorsEnvironmentCompleteBaselineSetsWizardPage"); //$NON-NLS-1$
      _baselinesToComplete = bstc;
      setPageComplete(false);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.ui.forms.widgets.FormToolkit)
     */
    @Override
    protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
      // Main component.
      Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, false);
      // Scrolled form used by MasterDetailsBlock.
      ScrolledForm container = toolkit_p.createScrolledForm(composite);
      GridData test = new GridData(GridData.FILL_BOTH);
      container.setLayoutData(test);
      // MasterDetailsBlock part with baselinesSets.
      BaselinesSetsBlock block = new BaselinesSetsBlock(_baselinesToComplete, this);
      block.createContent(new ManagedForm(toolkit_p, container));
      return composite;
    }

    /**
     * @return the Orchestra Baseline name.
     */
    public String getOrchestraBaselineDescription() {
      return ((DoorsEnvironmentCompleteBaselineSetsWizard) getWizard()).getOrchestraBaselineDesc();
    }

    /**
     * @return the Orchestra Baseline name.
     */
    public String getOrchestraBaselineName() {
      return ((DoorsEnvironmentCompleteBaselineSetsWizard) getWizard()).getOrchestraBaselineName();
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
     */
    @Override
    protected ImageDescriptor getPageImageDescriptor() {
      return DoorsActivator.getInstance().getImageDescriptor("doors_big.gif"); //$NON-NLS-1$
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageTitle()
     */
    @Override
    protected String getPageTitle() {
      return Messages.DoorsEnvironmentCompleteBaselineSetsToMakeBaseline_Page_Title;
    }

  }

}
