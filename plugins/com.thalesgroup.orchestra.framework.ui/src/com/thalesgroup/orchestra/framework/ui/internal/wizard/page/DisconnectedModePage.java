/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
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
 * The page of the disconnected mode wizard.
 * @author s0024585
 */
public class DisconnectedModePage extends AbstractFormsWizardPage {
  /**
   * The browse button.
   */
  protected Button _browseButton;
  /**
   * The location choice text widget.
   */
  protected Text _locationPath;
  /**
   * Artifacts' Names
   */
  List<String> _rootNames = null;
  /**
   * Artifacts' type
   */
  String _rootType = null;
  String _selectedPath = null;

  /**
   * Constructor
   */
  public DisconnectedModePage() {
    this("DisconnectedMode"); //$NON-NLS-1$
  }

  /**
   * Constructor
   * @param pageId_p
   */
  public DisconnectedModePage(String pageId_p) {
    super(pageId_p);
  }

  /**
   * Constructor
   * @param rootType_p
   * @param rootNames_p
   */
  public DisconnectedModePage(String rootType_p, List<String> rootNames_p) {
    this();
    _rootNames = rootNames_p;
    _rootType = rootType_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite chooseFileComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 4, false);
    GridData layoutData = (GridData) chooseFileComposite.getLayoutData();
    layoutData.grabExcessVerticalSpace = false;

    Label label1 = toolkit_p.createLabel(chooseFileComposite, MessageFormat.format(Messages.DisconnectedModePage_ToolInDisconnectedMode, _rootType), SWT.WRAP);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(label1, LayoutType.GRID_LAYOUT);
    GridData labelData = (GridData) label1.getLayoutData();
    labelData.horizontalSpan = 4;
    labelData.grabExcessVerticalSpace = false;

    Label label = toolkit_p.createLabel(chooseFileComposite, ICommonConstants.EMPTY_STRING, SWT.WRAP);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(label, LayoutType.GRID_LAYOUT);
    labelData = (GridData) label.getLayoutData();
    labelData.horizontalSpan = 4;
    labelData.grabExcessVerticalSpace = false;

    ListViewer listViewer = new ListViewer(chooseFileComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

    listViewer.setContentProvider(new IStructuredContentProvider() {
      @Override
      public void dispose() {
        // Nothing to do here.
      }

      @SuppressWarnings("unchecked")
      @Override
      public Object[] getElements(Object inputElement_p) {
        if (inputElement_p instanceof List<?>) {
          return ((List<Object>) inputElement_p).toArray();
        }
        return null;
      }

      @Override
      public void inputChanged(Viewer viewer_p, Object oldInput_p, Object newInput_p) {
        // Nothing to do here.
      }
    });

    listViewer.setLabelProvider(new LabelProvider());
    GridData listViewerData = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(listViewer.getControl(), LayoutType.GRID_LAYOUT);
    listViewerData.horizontalSpan = 4;
    listViewer.setInput(_rootNames);

    label = toolkit_p.createLabel(chooseFileComposite, ICommonConstants.EMPTY_STRING, SWT.WRAP);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(label, LayoutType.GRID_LAYOUT);
    labelData = (GridData) label.getLayoutData();
    labelData.horizontalSpan = 4;
    labelData.grabExcessVerticalSpace = false;

    label = toolkit_p.createLabel(chooseFileComposite, Messages.DisconnectedModePage_SelectGefFile, SWT.WRAP);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(label, LayoutType.GRID_LAYOUT);
    labelData = (GridData) label.getLayoutData();
    labelData.horizontalSpan = 4;
    labelData.grabExcessVerticalSpace = false;

    label = toolkit_p.createLabel(chooseFileComposite, ICommonConstants.EMPTY_STRING, SWT.WRAP);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(label, LayoutType.GRID_LAYOUT);
    labelData = (GridData) label.getLayoutData();
    labelData.horizontalSpan = 4;
    labelData.grabExcessVerticalSpace = false;

    _locationPath = toolkit_p.createText(chooseFileComposite, ICommonConstants.EMPTY_STRING, SWT.NONE);
    _locationPath.setBackground(chooseFileComposite.getDisplay().getSystemColor(SWT.COLOR_WHITE));

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
    data.horizontalSpan = 3;
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
    return Messages.DisconnectedModePage_SelectConnectorResponseFile;
  }

  /**
   * @return
   */
  public String getSelectedPath() {
    return _locationPath.getText();
  }

  /**
   * Handle file location choice.
   * @return
   */
  protected void handleLocation() {
    FileDialog dialog = new FileDialog(getShell());
    dialog.setText(Messages.DisconnectedModePage_ChooseTheFile);
    // Set filter value.
    dialog.setFilterExtensions(new String[] { "*.gef" }); //$NON-NLS-1$
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
