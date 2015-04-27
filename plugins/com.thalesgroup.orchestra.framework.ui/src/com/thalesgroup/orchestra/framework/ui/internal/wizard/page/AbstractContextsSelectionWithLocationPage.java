/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.ui.wizard.Messages;

/**
 * A contexts selection page that also provides with the selection of an external location.<br>
 * This is up to the implementation to choose where to place the external location selection widget, along with its decoration.
 * @author t0076261
 */
public abstract class AbstractContextsSelectionWithLocationPage extends AbstractContextsSelectionPage {
  /**
   * The browse button.
   */
  protected Button _browseButton;
  /**
   * Location path label.
   */
  protected Label _locationLabel;
  /**
   * The location choice text widget.
   */
  protected Text _locationPath;

  /**
   * Constructor.
   * @param pageId_p
   */
  public AbstractContextsSelectionWithLocationPage(String pageId_p) {
    super(pageId_p);
  }

  /**
   * Create choose location (absolute path) part.
   * @param parent_p
   * @param toolkit_p
   */
  protected void createChooseLocationPart(Composite parent_p, FormToolkit toolkit_p) {
    Composite chooseLocationComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 3, false);
    GridData layoutData = (GridData) chooseLocationComposite.getLayoutData();
    layoutData.grabExcessVerticalSpace = false;
    _locationLabel = new Label(chooseLocationComposite, SWT.NONE);
    _locationLabel.setText(getLocationLabelText());
    _locationPath = new Text(chooseLocationComposite, SWT.BORDER);
    _locationPath.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e_p) {
        setPageComplete(isPageComplete());
      }
    });
    _locationPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    _browseButton = new Button(chooseLocationComposite, SWT.PUSH);
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
  }

  /**
   * Template method implementation of {@link #isPageComplete()}.<br>
   * Allows for subclasses to respond to super.isPageComplete() and still override its behavior at any level.
   * @return
   */
  protected abstract boolean doIsPageComplete();

  /**
   * Get invalid location error message.
   * @return
   */
  protected abstract String getInvalidLocationErrorMessage();

  /**
   * Get location.
   * @return
   */
  public String getLocation() {
    return _locationPath.getText();
  }

  /**
   * Get location choice dialog message.
   * @return
   */
  protected abstract String getLocationDialogMessage();

  /**
   * Get location choice label text.
   * @return
   */
  protected abstract String getLocationLabelText();

  /**
   * Returns the message to show (and its severity) when the page is complete. A <code>null</code> result can be returned (or a couple with a <code>null</code>
   * String) to specify that no message must be shown.
   * @return
   */
  protected Couple<String, Integer> getPageCompleteMessage() {
    return null;
  }

  /**
   * Get context location prompt message.
   * @return
   */
  protected abstract String getPromptForContextLocationMessage();

  /**
   * Get context selection prompt message.
   * @return
   */
  protected abstract String getPromptForContextSelectionMessage();

  /**
   * Handle project location choice.
   * @return
   */
  protected void handleLocation() {
    DirectoryDialog dialog = new DirectoryDialog(getShell());
    dialog.setMessage(getLocationDialogMessage());
    // Set filter value.
    if (validateLocation()) {
      dialog.setFilterPath(getLocation());
    }
    String selectedLocation = dialog.open();
    if (null != selectedLocation) {
      _locationPath.setText(selectedLocation);
    }
  }

  /**
   * If the page is complete, show the message returned by {@link #getPageCompleteMessage}.
   * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
   */
  @Override
  public boolean isPageComplete() {
    boolean isPageComplete = doIsPageComplete();
    if (isPageComplete) {
      // If the page is complete, show the message returned by getPageCompleteMessage.
      Couple<String, Integer> pageCompleteMessage = getPageCompleteMessage();
      if (null == pageCompleteMessage || null == pageCompleteMessage.getKey()) {
        // Returned message is null, clear the dialog page message.
        setMessage(null);
      } else {
        int messageSeverity = (null != pageCompleteMessage.getValue()) ? pageCompleteMessage.getValue().intValue() : NONE;
        setMessage(pageCompleteMessage.getKey(), messageSeverity);
      }
    }
    return isPageComplete;
  }

  /**
   * Do check if specified location can be used as an input for location browsing (it must be a non empty absolute path pointing to an existing directory).
   * @param locationAbsolutePath_p
   * @return
   */
  protected boolean isValidLocation(String locationAbsolutePath_p) {
    // Precondition.
    if (null == locationAbsolutePath_p || locationAbsolutePath_p.isEmpty()) {
      return false;
    }
    File file = new File(locationAbsolutePath_p);
    boolean result = file.isDirectory() && file.isAbsolute();
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionPage#setEnabled(boolean)
   */
  @Override
  public void setEnabled(boolean enabled_p) {
    super.setEnabled(enabled_p);
    _locationLabel.setEnabled(enabled_p);
    _locationPath.setEnabled(enabled_p);
    _browseButton.setEnabled(enabled_p);
  }

  /**
   * Is specified absolute location a valid one for new project creation ?
   * @param locationAbsolutePath_p
   * @return
   */
  protected boolean validateLocation() {
    String location = getLocation();
    if (null == location || location.isEmpty()) {
      setMessage(getPromptForContextLocationMessage());
      return false;
    }
    if (!isValidLocation(location)) {
      setMessage(getInvalidLocationErrorMessage(), ERROR);
      return false;
    }
    return true;
  }

  /**
   * Is context selection valid.
   * @return
   */
  protected boolean validateSelection() {
    // An empty selection is not valid.
    boolean isValidSelection = (0 != _viewer.getCheckedElements().length);
    if (!isValidSelection)
      setMessage(getPromptForContextSelectionMessage());
    return isValidSelection;
  }
}