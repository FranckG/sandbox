/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.environment.ui.AbstractVariablesHandler;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler.IReferencingValueHandler;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.handler.data.RootElement;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.Messages;
import com.thalesgroup.orchestra.framework.ui.view.ISharedActionsHandler;
import com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer;
import com.thalesgroup.orchestra.framework.ui.viewer.component.VariableNode;
import com.thalesgroup.orchestra.framework.ui.viewer.component.VariableValueNode;

/**
 * The base class to provide a customization to help user fill in variable values.
 * @author s0011584
 * @author t0076261
 */
public class DefaultValueCustomizer extends AbstractVariablesHandler implements IReferencingValueHandler, IAdaptable {
  /**
   * Context of edition.
   */
  protected Context _editionContext;
  /**
   * Value handler.
   */
  protected IValueHandler _valueHandler;
  /**
   * Variables picking button.
   */
  protected Button _variablesButton;

  /**
   * Constructor.
   * @param editionContext_p A not <code>null</code> instance of {@link Context}, which variable edition happens for.
   */
  public DefaultValueCustomizer(Context editionContext_p, IValueHandler handler_p) {
    _editionContext = editionContext_p;
    _valueHandler = handler_p;
  }

  /**
   * Create buttons part customization.
   * @param toolkit_p The toolkit to use.
   * @param parent_p The parent composite.
   * @param composite_p The composite in-use for values part. This is recommended to use this one instead of the parent one.
   * @return The composite containing the customization. <code>null</code> if no customization is done.
   */
  public Composite createButtonsPart(FormToolkit toolkit_p, Composite parent_p, Composite composite_p) {
    _variablesButton = createVariablesButton(toolkit_p, composite_p, this);
    return composite_p;
  }

  /**
   * Create values part customization.
   * @param toolkit_p The toolkit to use.
   * @param parent_p The parent composite.
   * @param composite_p The composite in-use for values part. This is recommended to use this one instead of the parent one.
   * @return The composite containing the customization. <code>null</code> if no customization is done.
   */
  public Composite createValuesPart(FormToolkit toolkit_p, Composite parent_p, Composite composite_p) {
    // Do nothing so far.
    return null;
  }

  /**
   * Add the variable browser action.
   */
  @Override
  public Button createVariablesButton(final FormToolkit toolkit_p, Composite buttonComposite_p, final IReferencingValueHandler referencingValueHandler_p) {
    Button button = FormHelper.createButton(toolkit_p, buttonComposite_p, Messages.AbstractValueEditionHandler_Button_Text_Variables, SWT.PUSH);
    button.setToolTipText(Messages.AbstractValueEditionHandler_Button_ToolTip_Variables);
    button.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        final Display display = PlatformUI.getWorkbench().getDisplay();
        final VariableBrowserViewer viewer = new VariableBrowserViewer();
        VariableBrowserDialog dialog =
            new VariableBrowserDialog(e_p.display.getActiveShell(), Messages.AbstractValueEditionHandler_Dialog_Title, null,
                Messages.AbstractValueEditionHandler_Dialog_Message, MessageDialog.NONE, new String[] { IDialogConstants.OK_LABEL,
                                                                                                       IDialogConstants.CANCEL_LABEL }, 0, display, viewer,
                toolkit_p);
        int returnCode = dialog.open();
        if (0 == returnCode) {
          String referencingValue = ModelUtil.getReferenceString(viewer.getSelectedVariable());
          referencingValueHandler_p.handleSelectedValue(referencingValue);
        }
      }
    });
    GridData data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(button, LayoutType.GRID_LAYOUT);
    data.grabExcessVerticalSpace = false;
    return button;
  }

  /**
   * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
   */
  public Object getAdapter(Class adapter_p) {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#getSubstitutedValue(java.lang.String)
   */
  @Override
  public String getSubstitutedValue(String rawValue_p) {
    // Precondition.
    if (null == rawValue_p) {
      return null;
    }
    return DataUtil.getSubstitutedValue(rawValue_p, _editionContext);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#getVariableType()
   */
  @Override
  public VariableType getVariableType() {
    String path = ModelUtil.getElementPath(_valueHandler.getVariable());
    if (DataUtil.__ARTEFACTPATH_VARIABLE_NAME.equals(path)) {
      return VariableType.Artefacts;
    } else if (DataUtil.__CONFIGURATIONDIRECTORIES_VARIABLE_NAME.equals(path)) {
      return VariableType.ConfigurationDirectories;
    } else {
      return VariableType.Unspecified;
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#handleSelectedValue(java.lang.String)
   */
  public void handleSelectedValue(String referencingValue_p) {
    _valueHandler.insertValue(referencingValue_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractVariablesHandler#isVariableReadOnly()
   */
  @Override
  public boolean isVariableReadOnly() {
    return DataUtil.isUnmodifiable(_valueHandler.getVariable(), _editionContext);
  }

  /**
   * Set customization enablement to specified one.
   * @param enabled_p <code>true</code> if customization should activate itself, <code>false</code> if it should be deactivated.
   */
  public void setEnabled(boolean enabled_p) {
    // Variables button must exist.
    if (null != _variablesButton) {
      // And a value must be accessible.
      if (null != _valueHandler.getValue()) {
        _variablesButton.setEnabled(enabled_p && !isVariableReadOnly());
      } else {
        _variablesButton.setEnabled(false);
      }
    }
  }

  /**
   * Variable browser dialog.
   */
  protected class VariableBrowserDialog extends MessageDialog implements ISelectionChangedListener {
    private Display _display;
    private FormToolkit _toolkit;
    private VariableBrowserViewer _viewer;

    /**
     * Constructor based on {@link MessageDialog#MessageDialog(Shell, String, Image, String, int, String[], int)}.
     */
    public VariableBrowserDialog(Shell parentShell_p, String dialogTitle_p, Image dialogTitleImage_p, String dialogMessage_p, int dialogImageType_p,
        String[] dialogButtonLabels_p, int defaultIndex_p, Display display_p, VariableBrowserViewer viewer_p, FormToolkit toolkit_p) {
      super(parentShell_p, dialogTitle_p, dialogTitleImage_p, dialogMessage_p, dialogImageType_p, dialogButtonLabels_p, defaultIndex_p);
      _display = display_p;
      _viewer = viewer_p;
      _toolkit = toolkit_p;
    }

    /**
     * @see org.eclipse.jface.dialogs.MessageDialog#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
    protected void configureShell(Shell shell_p) {
      super.configureShell(shell_p);
      int defaultDialogWidth = 550;
      int defaultDialogHeight = 650;
      Shell activeShell = _display.getActiveShell();
      if (null != activeShell) {
        // Center dialog on active parent shell.
        Rectangle bounds = activeShell.getBounds();
        shell_p.setBounds(bounds.x + (bounds.width - defaultDialogWidth) / 2, bounds.y + (bounds.height - defaultDialogHeight) / 2, defaultDialogWidth,
            defaultDialogHeight);
      }
    }

    /**
     * @see org.eclipse.jface.dialogs.IconAndMessageDialog#createContents(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createContents(Composite parent_p) {
      Control contents = super.createContents(parent_p);
      // Set the initial button enabled state.
      getButton(OK).setEnabled(false);
      return contents;
    }

    /**
     * @see org.eclipse.jface.dialogs.MessageDialog#createCustomArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createCustomArea(Composite parent_p) {
      _viewer.createViewer(_toolkit, parent_p, null, null);
      _viewer.getSelectionProvider().addSelectionChangedListener(this);
      return _viewer.getViewer().getControl();
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#isResizable()
     */
    @Override
    protected boolean isResizable() {
      return true;
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event_p) {
      // Set the button enabled state.
      Object selection = ((IStructuredSelection) event_p.getSelection()).getFirstElement();
      getButton(OK).setEnabled(selection instanceof AbstractVariable || selection instanceof VariableValue);
    }
  }

  /**
   * @author s0011584
   */
  protected class VariableBrowserViewer extends ContextsViewer {
    /**
     * Currently selected variable.
     */
    protected AbstractVariable _selectedVariable;

    /**
     * @see com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer#createContextualMenu(org.eclipse.ui.IWorkbenchPartSite,
     *      com.thalesgroup.orchestra.framework.ui.view.ISharedActionsHandler)
     */
    @Override
    protected void createContextualMenu(IWorkbenchPartSite site_p, ISharedActionsHandler sharedActionsHandler_p) {
      // Do not need any contextual menu.
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer#createViewerParentComposite(org.eclipse.ui.forms.widgets.FormToolkit,
     *      org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Composite createViewerParentComposite(FormToolkit toolkit_p, Composite parent_p) {
      // Do not create a section.
      return parent_p;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer#customizeViewer(org.eclipse.jface.viewers.TreeViewer,
     *      org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite, org.eclipse.ui.IWorkbenchPartSite)
     */
    @Override
    protected void customizeViewer(TreeViewer viewer_p, FormToolkit toolkit_p, Composite parent_p, IWorkbenchPartSite site_p) {
      // Use categorization to order categories before variables.
      viewer_p.setComparator(new ViewerComparator() {
        /**
         * @see org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
         */
        @SuppressWarnings("synthetic-access")
        @Override
        public int category(Object element_p) {
          Object element = _selectionProviderAdapter.getModelElement(element_p);
          if (element instanceof Category) {
            return -1;
          }
          return super.category(element);
        }
      });
      viewer_p.addFilter(new ViewerFilter() {
        /**
         * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
         */
        @SuppressWarnings("synthetic-access")
        @Override
        public boolean select(Viewer selectedViewer_p, Object parentElement_p, Object element_p) {
          Object element = _selectionProviderAdapter.getModelElement(element_p);
          if (element instanceof AbstractVariable) {
            AbstractVariable variable = (AbstractVariable) element;
            // Do not display multiple valued variables.
            if (variable.isMultiValued()) {
              return false;
            }
            // Nor current variable.
            return !_valueHandler.isHandling(variable);
          }
          return true;
        }
      });
      // Do not use the selection provider directly for tree nodes are required to select the variable.
      viewer_p.addSelectionChangedListener(new ISelectionChangedListener() {
        /**
         * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
         */
        public void selectionChanged(SelectionChangedEvent event_p) {
          Object firstElement = ((StructuredSelection) event_p.getSelection()).getFirstElement();
          if (firstElement instanceof VariableNode) {
            _selectedVariable = ((VariableNode) firstElement).getValue();
          } else if (firstElement instanceof VariableValueNode) {
            _selectedVariable = (AbstractVariable) ((VariableValueNode) firstElement).getParent().getValue();
          } else {
            _selectedVariable = null;
          }
        }
      });
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer#getInitialInput()
     */
    @Override
    protected RootElement getInitialInput() {
      RootElement result = new RootElement();
      // Do not display other contexts than the edition one.
      RootContextsProject project = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(_editionContext.eResource().getURI());
      if (null != project) {
        result.addProject(project);
      }
      return result;
    }

    /**
     * @return the selectedVariable
     */
    public AbstractVariable getSelectedVariable() {
      return _selectedVariable;
    }
  }
}