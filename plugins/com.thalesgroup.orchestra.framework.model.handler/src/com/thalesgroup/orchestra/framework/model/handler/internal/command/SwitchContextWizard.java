/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.internal.command;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * Wizard to display preContextChange statuses to the user while switching context.
 * @author t0076261
 */
public class SwitchContextWizard extends AbstractFormsWizard {
  /**
   * Column index client name.
   */
  protected static final int COLUMN_INDEX_CLIENT_NAME = 0;
  /**
   * Column index status.
   */
  protected static final int COLUMN_INDEX_STATUS = 1;
  /**
   * Change statuses from PreContextChange events to remote clients.
   */
  protected Map<String, IStatus> _changeStatus;
  /**
   * New - about to be applied - context name.
   */
  protected String _newContextName;
  /**
   * Proceed with context switching ?
   */
  private boolean _proceedWithSwitching;

  /**
   * Constructor.
   * @param changeStatus_p The preContextChange statuses from remote clients.
   * @param newContextName_p The context name to be applied by current switching process.
   */
  public SwitchContextWizard(Map<String, IStatus> changeStatus_p, String newContextName_p) {
    setWindowTitle(MessageFormat.format(Messages.ConfirmSwitchContextCommand_SwitchContextTitle, newContextName_p));
    setForcePreviousAndNextButtons(false);
    _proceedWithSwitching = false;
    _changeStatus = changeStatus_p;
    _newContextName = newContextName_p;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    addPage(new RegisteredClientsPage());
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    _proceedWithSwitching = true;
    return _proceedWithSwitching;
  }

  /**
   * Should the switching process be resumed ?
   * @return
   */
  public boolean proceedWithSwitching() {
    return _proceedWithSwitching;
  }

  /**
   * A content provider dedicated to registered clients preContextChange statuses.
   * @author t0076261
   */
  protected class RegisteredClientsContentProvider extends TreeNodeContentProvider {
    /**
     * @see org.eclipse.jface.viewers.TreeNodeContentProvider#getElements(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object[] getElements(Object inputElement_p) {
      if (inputElement_p instanceof Map) {
        RootNode rootNode = new RootNode((Map<String, IStatus>) inputElement_p);
        return rootNode.getChildren();
      }
      return super.getElements(inputElement_p);
    }
  }

  /**
   * A label provider dedicated to registered clients preContextChange statuses.
   * @author t0076261
   */
  protected class RegisteredClientsLabelProvider extends CellLabelProvider {
    /**
     * @see org.eclipse.jface.viewers.CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
     */
    @Override
    public void update(ViewerCell cell_p) {
      // Get input data.
      StatusNode statusNode = (StatusNode) cell_p.getElement();
      int columnIndex = cell_p.getColumnIndex();
      // Deal with text.
      String text = null;
      switch (columnIndex) {
        case COLUMN_INDEX_CLIENT_NAME:
          text = statusNode.getName();
        break;
        case COLUMN_INDEX_STATUS:
          text = statusNode.getStatusMessage();
        break;
        default:
        break;
      }
      cell_p.setText(text);
      // Deal with image.
      Image image = null;
      switch (columnIndex) {
        case COLUMN_INDEX_CLIENT_NAME:
          image = statusNode.getImage();
        break;
        default:
        break;
      }
      cell_p.setImage(image);
      // Deal with background color.
      cell_p.setBackground(statusNode.getBackgroundColor());
    }
  }

  /**
   * Registered clients page.<br>
   * Displays registered clients along with their state (if available) when the user asked for a context switching.<br>
   * These states can be refreshed within this page.
   * @author t0076261
   */
  protected class RegisteredClientsPage extends AbstractFormsWizardPage {
    /**
     * Internal switch context helper.
     */
    protected SwitchContextHelper _switchContextHelper;

    /**
     * Constructor.
     */
    public RegisteredClientsPage() {
      super("RegisteredClientsPage"); //$NON-NLS-1$
      _switchContextHelper = new SwitchContextHelper();
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.ui.forms.widgets.FormToolkit)
     */
    @Override
    protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
      Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 2, false);
      // Introduction text.
      {
        StyledText styledText =
            FormHelper.createStyledText(toolkit_p, composite, Messages.ConfirmSwitchContextCommand_SwitchContextConfirmationMessage, SWT.READ_ONLY);
        GridData data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(styledText, LayoutType.GRID_LAYOUT);
        data.grabExcessHorizontalSpace = false;
        data.grabExcessVerticalSpace = false;
        data.horizontalSpan = 2;
      }
      // TreeViewer.
      Composite treeComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, composite, LayoutType.GRID_LAYOUT, 1, false);
      GridData treeData = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(treeComposite, LayoutType.GRID_LAYOUT);
      treeData.horizontalSpan = 1;
      final TreeViewer viewer = new TreeViewer(treeComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
      {
        // Create columns.
        Tree tree = viewer.getTree();
        tree.setLinesVisible(true);
        tree.setHeaderVisible(true);
        TableLayout layout = new TableLayout();
        tree.setLayout(layout);
        layout.addColumnData(new ColumnWeightData(33, 150, true));
        layout.addColumnData(new ColumnWeightData(66, 300, true));
        TreeViewerColumn viewerColumnName = new TreeViewerColumn(viewer, SWT.LEFT, COLUMN_INDEX_CLIENT_NAME);
        viewerColumnName.getColumn().setText(Messages.SwitchContextWizard_Column_Text_Name);
        TreeViewerColumn viewerColumnValue = new TreeViewerColumn(viewer, SWT.LEFT, COLUMN_INDEX_STATUS);
        viewerColumnValue.getColumn().setText(Messages.SwitchContextWizard_Column_Text_Status);
        // Set content provider.
        viewer.setContentProvider(new RegisteredClientsContentProvider());
        // Set label provider.
        CellLabelProvider labelProvider = new RegisteredClientsLabelProvider();
        viewerColumnName.setLabelProvider(labelProvider);
        viewerColumnValue.setLabelProvider(labelProvider);
        // Layout viewer.
        FormHelper.updateControlLayoutDataWithLayoutTypeData(viewer.getControl(), LayoutType.GRID_LAYOUT);
      }
      // Buttons.
      {
        // Refresh button.
        Button refreshButton = toolkit_p.createButton(composite, Messages.SwitchContextWizard_Button_Text_Refresh, SWT.PUSH);
        GridData data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(refreshButton, LayoutType.GRID_LAYOUT);
        data.grabExcessHorizontalSpace = false;
        data.grabExcessVerticalSpace = false;
        data.horizontalSpan = 1;
        data.verticalAlignment = SWT.BEGINNING;
        refreshButton.setLayoutData(data);
        refreshButton.addSelectionListener(new SelectionAdapter() {
          /**
           * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
           */
          @Override
          public void widgetSelected(SelectionEvent e_p) {
            // Refresh the viewer.
            viewer.setInput(_switchContextHelper.getPreContextChangeStatuses(_newContextName));
          }
        });
      }
      viewer.setInput(_changeStatus);
      return composite;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
     */
    @Override
    protected ImageDescriptor getPageImageDescriptor() {
      return ModelHandlerActivator.getDefault().getImageDescriptor("status/registered_clients_wiz_ban.png"); //$NON-NLS-1$
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageTitle()
     */
    @Override
    protected String getPageTitle() {
      return Messages.SwitchContextWizard_Page_Title_RegisteredClientsWrapup;
    }
  }

  /**
   * The tree root node.
   * @author t0076261
   */
  protected class RootNode extends TreeNode {
    /**
     * Constructor.
     * @param statuses_p
     */
    public RootNode(Map<String, IStatus> statuses_p) {
      super(statuses_p);
    }

    /**
     * @see org.eclipse.jface.viewers.TreeNode#getChildren()
     */
    @Override
    public TreeNode[] getChildren() {
      List<TreeNode> nodes = new ArrayList<TreeNode>(0);
      int position = 0;
      for (Entry<String, IStatus> entry : getValue().entrySet()) {
        StatusNode statusNode = new StatusNode(entry.getKey(), entry.getValue(), 0, position);
        statusNode.setParent(null);
        nodes.add(statusNode);
        position++;
      }
      return nodes.toArray(new TreeNode[nodes.size()]);
    }

    /**
     * @see org.eclipse.jface.viewers.TreeNode#getParent()
     */
    @Override
    public TreeNode getParent() {
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.TreeNode#getValue()
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, IStatus> getValue() {
      return (Map<String, IStatus>) super.getValue();
    }
  }

  /**
   * The tree intermediate node.
   * @author t0076261
   */
  protected class StatusNode extends TreeNode {
    /**
     * Node level.<br>
     * == 0 : root level.<br>
     * > 0 : sub level.
     */
    private int _level;
    /**
     * Client name, if this node does indeed stand for a client node.
     */
    private String _name;
    /**
     * Position in parent children collection.
     */
    private int _position;

    /**
     * Constructor.
     * @param value_p
     */
    public StatusNode(String name_p, IStatus status_p, int level_p, int position_p) {
      super(status_p);
      _name = name_p;
      _level = level_p;
      _position = position_p;
    }

    /**
     * Get background color.
     * @return
     */
    protected Color getBackgroundColor() {
      Display display = PlatformUI.getWorkbench().getDisplay();
      // Color comes from root status position.
      if (0 == _level) {
        // One status out of two is colored.
        if ((_position / 2) == (_position / 2.0f)) {
          return display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
        }
      } else if (null != getParent()) {
        return ((StatusNode) getParent()).getBackgroundColor();
      }
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.TreeNode#getChildren()
     */
    @Override
    public TreeNode[] getChildren() {
      List<TreeNode> nodes = new ArrayList<TreeNode>(0);
      int position = 0;
      for (IStatus status : getValue().getChildren()) {
        StatusNode statusNode = new StatusNode(null, status, _level + 1, position);
        statusNode.setParent(this);
        nodes.add(statusNode);
        position++;
      }
      return nodes.toArray(new TreeNode[nodes.size()]);
    }

    /**
     * Get image.
     * @return
     */
    protected Image getImage() {
      // Only applicable to root status.
      if (0 == _level) {
        switch (getValue().getSeverity()) {
          case IStatus.ERROR:
            return ModelHandlerActivator.getDefault().getImage("status/error.gif"); //$NON-NLS-1$
          case IStatus.WARNING:
            return ModelHandlerActivator.getDefault().getImage("status/warning.gif"); //$NON-NLS-1$
          case IStatus.OK:
            return ModelHandlerActivator.getDefault().getImage("status/ok.gif"); //$NON-NLS-1$
          default:
          break;
        }
      }
      return null;
    }

    /**
     * Get client name, if any.
     * @return
     */
    protected String getName() {
      return _name;
    }

    /**
     * Get formated message status.
     * @return
     */
    protected String getStatusMessage() {
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < _level; i++) {
        builder.append("  "); //$NON-NLS-1$
      }
      builder.append(getValue().getMessage());
      return builder.toString();
    }

    /**
     * @see org.eclipse.jface.viewers.TreeNode#getValue()
     */
    @Override
    public IStatus getValue() {
      return (IStatus) super.getValue();
    }

    /**
     * @see org.eclipse.jface.viewers.TreeNode#hasChildren()
     */
    @Override
    public boolean hasChildren() {
      return getValue().isMultiStatus();
    }
  }
}