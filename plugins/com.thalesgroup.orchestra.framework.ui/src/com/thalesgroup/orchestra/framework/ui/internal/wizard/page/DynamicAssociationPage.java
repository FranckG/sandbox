/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;
import com.thalesgroup.orchestra.framework.ui.wizard.Messages;

/**
 * The page of the dynamic association wizard.
 * @author s0024585
 */
public class DynamicAssociationPage extends AbstractFormsWizardPage {
  /**
   * The browse button.
   */
  protected Button _browseButton;
  /**
   * The combo box for configuration directory choice
   */
  protected Combo _comboConfDir;
  /**
   * The configuration directories
   */
  String _confDirs[];
  /**
   * The location choice text widget.
   */
  protected Text _locationPath;
  /**
   * Root name of the artifact
   */
  String _rootName = null;
  /**
   * Root type of the artifact
   */
  String _rootType = null;
  /**
   * The selected file path
   */
  String _selectedPath = null;

  /**
   * @param pageId_p
   */
  public DynamicAssociationPage(String pageId_p) {
    super(pageId_p);
  }

  /**
   * @param rootType_p
   * @param rootName_p
   */
  public DynamicAssociationPage(String rootType_p, String rootName_p, List<String> confDirs_p) {
    this("DynamicAssociationCreation"); //$NON-NLS-1$
    _rootType = rootType_p;
    _rootName = rootName_p;
    _confDirs = confDirs_p.toArray(new String[confDirs_p.size()]);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite chooseFileComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 3, false);
    GridData layoutData = (GridData) chooseFileComposite.getLayoutData();
    layoutData.grabExcessVerticalSpace = false;

    Label label = toolkit_p.createLabel(chooseFileComposite, MessageFormat.format(Messages.DynamicAssociationPage_NoFileFound, _rootType, _rootName), SWT.NONE);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(label, LayoutType.GRID_LAYOUT);
    GridData labelData = (GridData) label.getLayoutData();
    labelData.horizontalSpan = 3;
    labelData.grabExcessVerticalSpace = false;

    Label label2 = toolkit_p.createLabel(chooseFileComposite, ICommonConstants.EMPTY_STRING, SWT.NONE);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(label2, LayoutType.GRID_LAYOUT);
    labelData = (GridData) label2.getLayoutData();
    labelData.horizontalSpan = 3;
    labelData.grabExcessVerticalSpace = false;

    Label label3 = toolkit_p.createLabel(chooseFileComposite, Messages.DynamicAssociationPage_SelectTheFile, SWT.NONE);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(label3, LayoutType.GRID_LAYOUT);
    labelData = (GridData) label3.getLayoutData();
    labelData.horizontalSpan = 3;
    labelData.grabExcessVerticalSpace = false;

    Label label21 = toolkit_p.createLabel(chooseFileComposite, ICommonConstants.EMPTY_STRING, SWT.NONE);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(label21, LayoutType.GRID_LAYOUT);
    labelData = (GridData) label21.getLayoutData();
    labelData.horizontalSpan = 3;
    labelData.grabExcessVerticalSpace = false;

    _locationPath =
        (Text) FormHelper.createLabelAndText(toolkit_p, chooseFileComposite, Messages.DynamicAssociationPage_ArtifactFileLabel, ICommonConstants.EMPTY_STRING,
            SWT.NONE).get(Text.class);

    _locationPath.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e_p) {
        setSelectedPath(_locationPath.getText());
        setPageComplete(isPageComplete());
      }
    });

    GridData data = new GridData();
    data.horizontalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = false;
    _locationPath.setLayoutData(data);
    _browseButton = new Button(chooseFileComposite, SWT.PUSH);
    setButtonLayoutData(_browseButton);
    _browseButton.setText(Messages.NewContextWizard_ProjectLocation_BrowseButton_Text);
    _browseButton.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        handleLocation();
        setPageComplete(isPageComplete());
      }
    });

    Label label22 = toolkit_p.createLabel(chooseFileComposite, ICommonConstants.EMPTY_STRING, SWT.NONE);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(label22, LayoutType.GRID_LAYOUT);
    labelData = (GridData) label22.getLayoutData();
    labelData.horizontalSpan = 3;
    labelData.grabExcessVerticalSpace = false;

    Label labelConfDir = toolkit_p.createLabel(chooseFileComposite, Messages.DynamicAssociationPage_ConfigurationDirectoryChoice, SWT.NONE);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(labelConfDir, LayoutType.GRID_LAYOUT);
    labelData = (GridData) labelConfDir.getLayoutData();
    labelData.horizontalSpan = 3;
    labelData.grabExcessVerticalSpace = false;

    _comboConfDir = new Combo(chooseFileComposite, SWT.READ_ONLY);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(_comboConfDir, LayoutType.GRID_LAYOUT);
    data = (GridData) _comboConfDir.getLayoutData();
    data.horizontalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = false;
    data.horizontalSpan = 3;
    _comboConfDir.setLayoutData(data);
    // toolkit_p.adapt(comboConfDir, true, true);
    _comboConfDir.setItems(_confDirs);
    _comboConfDir.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        setPageComplete(isPageComplete());
      }
    });

    return chooseFileComposite;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.DynamicAssociationPage_SelectPhysicalArtifactPath;
  }

  /**
   * @return the selected configuration directory
   */
  public String getSelectedConfDir() {
    int index = _comboConfDir.getSelectionIndex();

    if (index == -1) {
      return null;
    }
    return _comboConfDir.getItem(index);
  }

  /**
   * @return the path of the selected file
   */
  public String getSelectedPath() {
    return _locationPath.getText();
  }

  /**
   * Handle project location choice.
   * @return
   */
  protected void handleLocation() {
    FileDialog dialog = new FileDialog(getShell());
    dialog.setText(Messages.DynamicAssociationPage_ChooseTheFile);
    // Set filter value.
    String selectedLocation = dialog.open();
    if (null != selectedLocation) {
      if (null != _locationPath) {
        _locationPath.setText(selectedLocation);
      } else {
        // This is already handled by the listener on _locationPath in the preceding case.
        setSelectedPath(selectedLocation);
      }
    } else {
      setSelectedPath(selectedLocation);
    }
  }

  /**
   * @param selectedLocation_p
   */
  void setSelectedPath(String selectedLocation_p) {
    _selectedPath = selectedLocation_p;
  }

}
