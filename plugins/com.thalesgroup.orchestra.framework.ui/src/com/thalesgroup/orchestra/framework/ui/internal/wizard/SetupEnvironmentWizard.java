/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironment;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry.EnvironmentDescriptor;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.FilteredTree;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.PatternFilter;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler;

/**
 * Wizard for environment modification/creation.
 * @author t0076261
 */
public class SetupEnvironmentWizard extends AbstractFormsWizard {
  /**
   * In-use environment handler.
   */
  protected IEnvironmentHandler _handler;
  /**
   * Collection of already initialized handlers, within this wizard.
   */
  protected Set<IEnvironmentHandler> _initializedHandlers;
  /**
   * Handled value.
   */
  protected EnvironmentVariableValue _value;
  /**
   * Value handler.
   */
  protected IValueHandler _valueHandler;

  /**
   * Constructor.
   * @param valueHandler_p
   */
  public SetupEnvironmentWizard(IValueHandler valueHandler_p) {
    setWindowTitle(Messages.SetupEnvironmentWizard_Wizard_Title);
    setForcePreviousAndNextButtons(true);
    initialize(valueHandler_p);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    EnvironmentTypeSelectionPage environmentTypeSelectionPage = new EnvironmentTypeSelectionPage();
    if (null == _handler) {
      addPage(environmentTypeSelectionPage);
    } else {
      AbstractEnvironmentPage firstPage = _handler.getFirstPage();
      // The value is an overridden one or is not modifiable, the environment type can't be changed.
      if (!((_value instanceof OverridingVariableValue) || DataUtil.isUnmodifiable(_value, _valueHandler.getEditionContext()))) {
        preparePage(environmentTypeSelectionPage);
        firstPage.setPreviousPage(environmentTypeSelectionPage);
      }
      addPage(firstPage);
    }

  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#canFinish()
   */
  @Override
  public boolean canFinish() {
    // Setup wizard can't be finished if the edited element is unmodifiable.
    if (DataUtil.isUnmodifiable(_valueHandler.getVariable(), _valueHandler.getEditionContext())) {
      return false;
    }
    return !(getContainer().getCurrentPage() instanceof EnvironmentTypeSelectionPage) && (null != _handler) && _handler.isCreationComplete().isOK();
  }

  /**
   * Compute environment descriptor string representation. By default returns the environment's label (as specified in environmentDeclaration extension point).
   * If no label is specified in the extension point, returns the environment's ID.
   * @param descriptor_p
   * @return
   */
  protected String computeEnvironmentDescriptorStringRepresentation(EnvironmentDescriptor descriptor_p) {
    String label = descriptor_p.getLabel();
    if (null == label) {
      label = descriptor_p.getDeclarationId();
    }
    return label;
  }

  /**
   * Initialize wizard.
   * @param valueHandler_p
   */
  protected void initialize(IValueHandler valueHandler_p) {
    Assert.isNotNull(valueHandler_p);
    _valueHandler = valueHandler_p;
    _value = (EnvironmentVariableValue) valueHandler_p.getVariableValue();
    _initializedHandlers = new HashSet<IEnvironmentHandler>(0);
    // Stop here.
    if (null == _value) {
      return;
    }
    String environmentId = _value.getEnvironmentId();
    // Stop here.
    if (null == environmentId) {
      return;
    }
    Couple<IStatus, IEnvironmentHandler> environmentHandlerCouple =
        EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentHandler(environmentId);
    // Stop here, again.
    if (!environmentHandlerCouple.getKey().isOK()) {
      return;
    }
    // Get handler.
    _handler = environmentHandlerCouple.getValue();
    // Initialize with existing values.
    initializeHandler();
  }

  /**
   * Initialize handler.
   */
  protected void initializeHandler() {
    // Preconditions.
    if ((null == _handler) || (null == _value) || _initializedHandlers.contains(_handler)) {
      return;
    }
    // Tag handler as already initialized.
    _initializedHandlers.add(_handler);
    // Initial attributes.
    // If it remains null, then no initial value is injected.
    Map<String, String> initialAttributes = null;
    // Check against model.
    if (_handler.getDeclarationId().equals(_value.getEnvironmentId())) {
      initialAttributes = ModelUtil.convertEnvironmentVariableValues(_value);
    }
    // Initialize handler.
    EnvironmentContext context = new EnvironmentContext();
    context._allowUserInteractions = true;
    context._attributes = initialAttributes;
    _handler.initialize(context);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    // Precondition.
    // A handler has to be selected before even being able to inject values in the model.
    if (null == _handler) {
      return false;
    }
    // Get version.
    String version = null;
    Couple<IStatus, EnvironmentDescriptor> descriptor =
        EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentDescriptor(_handler.getDeclarationId());
    if (descriptor.getKey().isOK()) {
      version = descriptor.getValue().getVersion();
    }
    // Bind values to the model.
    ModelUtil.fillEnvironmentVariableValue(_value, _handler.getAttributes(), _handler.getDeclarationId(), version);
    return true;
  }

  /**
   * Make sure specified page is having the correct set of attributes, required to effectively display it.
   * @param page_p
   */
  protected void preparePage(AbstractFormsWizardPage page_p) {
    // Precondition.
    if (null == page_p) {
      return;
    }
    page_p.setToolkit(getToolkit());
    page_p.setWizard(this);
  }

  /**
   * Environment type content provider.
   * @author t0076261
   */
  protected class EnvironmentTypeContentProvider implements ITreeContentProvider {
    /**
     * Category to descriptors.
     */
    private Map<String, Collection<EnvironmentDescriptor>> _categoryToDescriptors;
    /**
     * Comparator of descriptors' string representations.
     */
    private final Comparator<EnvironmentDescriptor> _environmentDescriptorComparator = new Comparator<EnvironmentDescriptor>() {
      @Override
      public int compare(EnvironmentDescriptor environmentDescriptor1_p, EnvironmentDescriptor environmentDescriptor2_p) {
        String environmentDescriptor1StringRepresentation = computeEnvironmentDescriptorStringRepresentation(environmentDescriptor1_p);
        String environmentDescriptor2StringRepresentation = computeEnvironmentDescriptorStringRepresentation(environmentDescriptor2_p);
        return environmentDescriptor1StringRepresentation.compareTo(environmentDescriptor2StringRepresentation);
      }
    };

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose() {
      if (null != _categoryToDescriptors) {
        _categoryToDescriptors.clear();
        _categoryToDescriptors = null;
      }
    }

    /**
     * Returns a list of categories allowed to be displayed in the environment type selection tree.<br>
     * <code>null</code> means all category are allowed.
     * @return
     */
    public Collection<String> getAllowedCategories() {
      // When ConfigurationDirectories variable is being edited, only environments from "File System" category are allowed.
      if (DataUtil.__CONFIGURATIONDIRECTORIES_VARIABLE_NAME.equals(ModelUtil.getElementPath(_valueHandler.getVariable()))) {
        Couple<IStatus, EnvironmentDescriptor> fileSystemEnvironmentDescriptor =
            EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentDescriptor(FileSystemEnvironment.FILE_SYSTEM_ENVIRONMENT_ID);
        if (!fileSystemEnvironmentDescriptor.getKey().isOK()) {
          return null;
        }
        return Collections.singletonList(fileSystemEnvironmentDescriptor.getValue().getCategory());
      }
      // For other environment variables every categories are allowed.
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    @Override
    public Object[] getChildren(Object parentElement_p) {
      if (parentElement_p instanceof String) {
        return _categoryToDescriptors.get(parentElement_p).toArray();
      }
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object inputElement_p) {
      if (inputElement_p instanceof EnvironmentRegistry) {
        // Fill first level of the tree.
        Collection<Object> result = new ArrayList<Object>(0);
        // Add categories.
        Set<String> categories = _categoryToDescriptors.keySet();
        if (!categories.isEmpty()) {
          result.addAll(categories);
          // Remove category with empty name (if any).
          result.remove(ICommonConstants.EMPTY_STRING);
        }
        // Then add environments belonging to the category with an empty name.
        Collection<EnvironmentDescriptor> descriptors = _categoryToDescriptors.get(ICommonConstants.EMPTY_STRING);
        if (null != descriptors) {
          result.addAll(descriptors);
        }
        return result.toArray();
      }
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    @Override
    public Object getParent(Object element_p) {
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    @Override
    public boolean hasChildren(Object element_p) {
      return (element_p instanceof String);
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    @Override
    public void inputChanged(Viewer viewer_p, Object oldInput_p, Object newInput_p) {
      if (newInput_p instanceof EnvironmentRegistry) {
        // Prepare structure.
        // Structure is sorted by category name.
        _categoryToDescriptors = new TreeMap<String, Collection<EnvironmentDescriptor>>();
        Collection<EnvironmentDescriptor> descriptors = ((EnvironmentRegistry) newInput_p).getDescriptors();
        Collection<String> allowedCategories = getAllowedCategories();

        // Cycle through descriptors.
        for (EnvironmentDescriptor environmentDescriptor : descriptors) {
          // Get category.
          String category = environmentDescriptor.getCategory();
          if (null == category) {
            // Default category.
            category = ICommonConstants.EMPTY_STRING;
          }
          if (null != allowedCategories && !allowedCategories.contains(category)) {
            // A list of allowed categories is given and current category isn't amongst them -> skip this descriptor.
            continue;
          }
          // Get descriptors for selected category.
          Collection<EnvironmentDescriptor> childrenDescriptors = _categoryToDescriptors.get(category);
          if (null == childrenDescriptors) {
            // Make sure descriptor is uniquely bound.
            // Descriptor list is sorted by descriptors' string representations.
            childrenDescriptors = new TreeSet<EnvironmentRegistry.EnvironmentDescriptor>(_environmentDescriptorComparator);
            _categoryToDescriptors.put(category, childrenDescriptors);
          }
          // Add descriptor.
          childrenDescriptors.add(environmentDescriptor);
        }
      }
    }
  }

  /**
   * Environment type label provider.
   * @author t0076261
   */
  protected class EnvironmentTypeLabelProvider extends BaseLabelProvider implements ILabelProvider {
    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element_p) {
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element_p) {
      if (element_p instanceof EnvironmentDescriptor) {
        EnvironmentDescriptor descriptor = (EnvironmentDescriptor) element_p;
        return computeEnvironmentDescriptorStringRepresentation(descriptor);
      }
      return element_p.toString();
    }
  }

  /**
   * Environment type selection page.
   * @author t0076261
   */
  protected class EnvironmentTypeSelectionPage extends AbstractFormsWizardPage {
    /**
     * Viewer.
     */
    protected TreeViewer _viewer;

    /**
     * Constructor
     * @param pageId_p
     */
    public EnvironmentTypeSelectionPage() {
      super("EnvironmentTypeSelectionPage"); //$NON-NLS-1$
    }

    /**
     * Add environment type selection part.
     * @param composite_p
     * @param toolkit_p
     */
    protected void addTypeSelection(Composite composite_p, FormToolkit toolkit_p) {
      _viewer = new FilteredTree(composite_p, SWT.BORDER | SWT.SINGLE, new PatternFilter()).getViewer();
      FormHelper.updateControlLayoutDataWithLayoutTypeData(_viewer.getControl(), LayoutType.GRID_LAYOUT);
      _viewer.setContentProvider(new EnvironmentTypeContentProvider());
      _viewer.setLabelProvider(new EnvironmentTypeLabelProvider());
      _viewer.setInput(EnvironmentActivator.getInstance().getEnvironmentRegistry());
      _viewer.addSelectionChangedListener(new ISelectionChangedListener() {
        @Override
        public void selectionChanged(SelectionChangedEvent event_p) {
          boolean canFlipToNextPage = canFlipToNextPage();
          if (canFlipToNextPage) {
            _handler = getSelectedDescriptor().getHandler();
            initializeHandler();
          }
          setPageComplete(canFlipToNextPage);
        }
      });
      _viewer.addDoubleClickListener(new IDoubleClickListener() {
        /**
         * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
         */
        @Override
        public void doubleClick(DoubleClickEvent event_p) {
          // Allow to double click on an environment type.
          IWizardPage nextPage = getNextPage();
          if (null != nextPage) {
            getWizard().getContainer().showPage(nextPage);
          }
        }
      });
      // Select correct descriptor, if any.
      if (null != _handler) {
        Couple<IStatus, EnvironmentDescriptor> couple =
            EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentDescriptor(_handler.getDeclarationId());
        if (couple.getKey().isOK()) {
          _viewer.reveal(couple.getValue());
          _viewer.setSelection(new StructuredSelection(couple.getValue()));
        }
      }
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
     */
    @Override
    public boolean canFlipToNextPage() {
      return (null != getSelectedDescriptor());
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractPageWithLiveValidation#dispose()
     */
    @Override
    public void dispose() {
      super.dispose();
      if (null != _viewer) {
        _viewer.getContentProvider().dispose();
        _viewer.getLabelProvider().dispose();
        _viewer = null;
      }
    }

    /**
     * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.ui.forms.widgets.FormToolkit)
     */
    @Override
    protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
      Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, false);
      // Environment type selection.
      addTypeSelection(composite, toolkit_p);
      return composite;
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
     */
    @Override
    public IWizardPage getNextPage() {
      if (canFlipToNextPage()) {
        EnvironmentDescriptor descriptor = (EnvironmentDescriptor) ((IStructuredSelection) _viewer.getSelection()).getFirstElement();
        IEnvironmentHandler handler = descriptor.getHandler();
        initializeHandler();
        AbstractEnvironmentPage firstPage = handler.getFirstPage();
        if (null != firstPage) {
          preparePage(firstPage);
          return firstPage;
        }
      }
      return null;
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
      return Messages.SetupEnvironmentWizard_EnvironmentTypePage_Title;
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#getPreviousPage()
     */
    @Override
    public IWizardPage getPreviousPage() {
      return null;
    }

    /**
     * Get currently selected environment descriptor.
     * @return <code>null</code> if current selection is not an {@link EnvironmentDescriptor}.
     */
    protected EnvironmentDescriptor getSelectedDescriptor() {
      ISelection selection = _viewer.getSelection();
      // Precondition.
      if (selection.isEmpty()) {
        return null;
      }
      Object firstElement = ((IStructuredSelection) selection).getFirstElement();
      if (firstElement instanceof EnvironmentDescriptor) {
        return (EnvironmentDescriptor) firstElement;
      }
      return null;
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
    public boolean isPageComplete() {
      // One can never stop at this page.
      return false;
    }
  }
}