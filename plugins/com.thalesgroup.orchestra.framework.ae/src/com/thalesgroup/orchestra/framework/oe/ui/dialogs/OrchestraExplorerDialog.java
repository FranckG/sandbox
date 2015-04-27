/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.dialogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.exchange.GefHandler;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.GEF;
import com.thalesgroup.orchestra.framework.gef.GefFactory;
import com.thalesgroup.orchestra.framework.gef.GenericExportFormat;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Property;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.constants.IImageConstants;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.DragableStringNode;
import com.thalesgroup.orchestra.framework.oe.ui.viewer.OrchestraExplorerViewer;
import com.thalesgroup.orchestra.framework.puci.PUCI;

/**
 * @author s0040806
 */
public class OrchestraExplorerDialog extends ApplicationWindow {

  private OrchestraExplorerViewer _viewer;
  /**
   * Selected artefact and the computed label
   */
  private List<Couple<Artefact, String>> _selection;
  /**
   * Selected URI in folded mode
   */
  private List<Couple<OrchestraURI, String>> _foldedSelection;

  private String _title;
  private String _message;
  private List<String> _filteredTypes;
  private List<String> _selectedURIs;
  private List<SelectableType> _selectableTypes;
  private boolean _isMultiSelectable;

  private Button _okButton;

  private OrchestraExplorerState _orchestraExplorerState;
  private IJobChangeListener _jobListener;

  /**
   * @param parentShell_p
   */
  public OrchestraExplorerDialog(String title_p, String message_p, List<String> filteredTypes_p, List<String> selectedURIs_p, boolean isMultiSelectable_p,
      List<String> selectableTypes_p) {
    super((Shell) null);
    // addMenuBar();
    addStatusLine();
    _title = title_p;
    _message = message_p;
    _filteredTypes = filteredTypes_p;
    _selectedURIs = selectedURIs_p;
    _isMultiSelectable = isMultiSelectable_p;
    _orchestraExplorerState = new OrchestraExplorerState();
    buildSelectableTypes(selectableTypes_p);
  }

  /**
   * Build map of selectable types
   * @param selectableTypes_p
   */
  private void buildSelectableTypes(List<String> selectableTypes_p) {
    if (null != selectableTypes_p) {
      _selectableTypes = new ArrayList<SelectableType>();
      for (String type : selectableTypes_p) {
        String selectable[] = type.split("\\.");
        String rootType = selectable[0];
        String objectType = null;
        if (2 == selectable.length) {
          objectType = selectable[1];
        }
        SelectableType selectableType = new SelectableType(rootType, objectType);
        _selectableTypes.add(selectableType);
      }
    }
  }

  /**
   * Check whether a given Orchestra URI is selectable
   * @param uri_p
   * @return
   */
  protected boolean isSelectableType(OrchestraURI uri_p) {
    if (null == _selectableTypes) {
      return true;
    }
    String rootType = uri_p.getRootType();
    String objectType = uri_p.getObjectType();

    for (SelectableType type : _selectableTypes) {
      if (type._rootType.equals(rootType) && (null == type._objectType || type._objectType.equals(objectType))) {
        return true;
      }
    }
    return false;
  }

  /**
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents(final Composite parent_p) {
    Composite composite = new Composite(parent_p, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    composite.setLayout(layout);
    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
    composite.setLayoutData(gridData);

    final MenuManager menuManager = new MenuManager();
    menuManager.createContextMenu(getShell());

    if (null != _message) {
      Label label = new Label(composite, SWT.WRAP);
      label.setText(_message);
    }

    final ToolBar toolbar = new ToolBar(composite, SWT.FLAT);
    toolbar.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false));
    ToolBarManager toolbarManager = new ToolBarManager(toolbar);

    StatusLineManager statusManager = getStatusLineManager();
    final IProgressMonitor monitor = statusManager.getProgressMonitor();

    _jobListener = new IJobChangeListener() {
      public void sleeping(IJobChangeEvent event_p) {
        // TODO Auto-generated method stub

      }

      public void scheduled(IJobChangeEvent event_p) {
        // TODO Auto-generated method stub

      }

      public void running(final IJobChangeEvent event_p) {
        toolbar.getDisplay().asyncExec(new Runnable() {
          public void run() {
            monitor.beginTask(event_p.getJob().getName(), IProgressMonitor.UNKNOWN);
          }
        });
      }

      public void done(IJobChangeEvent event_p) {
        toolbar.getDisplay().asyncExec(new Runnable() {
          public void run() {
            monitor.done();
          }
        });
      }

      public void awake(IJobChangeEvent event_p) {
        // TODO Auto-generated method stub

      }

      public void aboutToRun(IJobChangeEvent event_p) {
        // TODO Auto-generated method stub

      }
    };
    // Register update job of the root artefact bag before
    // updating the artefact bag
    RootArtefactsBag.getInstance().addUpdateJobListener(_jobListener);

    _viewer = new OrchestraExplorerViewer(_orchestraExplorerState, menuManager, toolbarManager, _filteredTypes, _selectedURIs, _isMultiSelectable, true);
    _viewer.createControl(composite);

    ISelectionChangedListener selectionListener = new ISelectionChangedListener() {

      @SuppressWarnings("synthetic-access")
      public void selectionChanged(SelectionChangedEvent event_p) {
        TreeSelection selection = (TreeSelection) event_p.getSelection();
        TreePath[] paths = selection.getPaths();

        boolean selectable = 0 == paths.length ? false : true;
        // Folded mode
        if (_orchestraExplorerState.isFolded()) {
          for (TreePath path : paths) {
            Object last = path.getLastSegment();
            // Cannot select non dragable artefact nodes
            if (!(last instanceof DragableStringNode)) {
              selectable = false;
              break;
            }
          }
        } else {
          // Unfolded mode
          for (TreePath path : paths) {
            Object last = path.getLastSegment();

            // Cannot select non artefact nodes
            if (!(last instanceof ArtefactNode)) {
              selectable = false;
              break;
            }
            // Check selection against selectable types
            ArtefactNode node = (ArtefactNode) last;
            Artefact artefact = node.getValue();
            OrchestraURI uri = artefact.getUri();
            if (!isSelectableType(uri)) {
              selectable = false;
              break;
            }
          }
        }
        if (selectable) {
          _okButton.setEnabled(true);
        } else {
          _okButton.setEnabled(false);
        }
      }
    };

    _viewer.addSelectionChangedListener(selectionListener);

    menuManager.updateAll(true);
    // Add preferences drop down menu at the end of the toolbar
    PreferencesAction preferences = new PreferencesAction(menuManager.getMenu());

    toolbarManager.add(preferences);
    toolbarManager.update(true);

    Composite buttonComposite = new Composite(composite, SWT.NONE);
    GridLayout buttonLayout = new GridLayout(2, true);
    buttonLayout.horizontalSpacing = 15;
    buttonComposite.setLayout(buttonLayout);
    GridData buttonData = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER);
    buttonComposite.setLayoutData(buttonData);
    _okButton = new Button(buttonComposite, SWT.PUSH);
    GridData okData = new GridData();
    okData.widthHint = 90;
    _okButton.setLayoutData(okData);
    _okButton.setText("Ok");
    _okButton.setEnabled(false);
    _okButton.addSelectionListener(new SelectionAdapter() {
      @SuppressWarnings("synthetic-access")
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        setReturnCode(OK);
        close();
      }
    });

    Button cancelButton = new Button(buttonComposite, SWT.PUSH);
    GridData cancelData = new GridData();
    cancelData.widthHint = 90;
    cancelButton.setLayoutData(cancelData);
    cancelButton.setText("Cancel");
    cancelButton.addSelectionListener(new SelectionAdapter() {
      @SuppressWarnings("synthetic-access")
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        setReturnCode(CANCEL);
        close();
      }
    });

    // Subscribe artefact jobs triggered throught the toolbar
    _viewer.subscribeGetRootArtefactsAction(_jobListener);
    _viewer.subscribeNavigateArtefactAction(_jobListener);
    _viewer.subscribeExpandArtefactAction(_jobListener);

    return parent_p;
  }

  @Override
  protected Point getInitialSize() {
    return new Point(500, 540);
  }

  /**
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell(Shell shell_p) {
    super.configureShell(shell_p);
    shell_p.setText(_title);
    shell_p.setImage(OrchestraExplorerActivator.getDefault().getImageDescriptor(IImageConstants.DESC_ARTEFACT_EXPLORER).createImage());

    Rectangle screenSize = shell_p.getDisplay().getPrimaryMonitor().getBounds();
    shell_p.setLocation((screenSize.width - shell_p.getBounds().width) / 2, (screenSize.height - shell_p.getBounds().height) / 2);
  }

  /**
   * Build map of selected artefacts
   */
  private void buildSelection() {
    TreeSelection selection = (TreeSelection) _viewer.getSelection();
    TreePath[] paths = selection.getPaths();

    // In folded mode, build artefact sets URIs
    if (_orchestraExplorerState.isFolded()) {
      _foldedSelection = new ArrayList<Couple<OrchestraURI, String>>();
      for (TreePath path : paths) {
        String logicalPath;
        String label;
        int nbSegments = path.getSegmentCount();
        if (1 == nbSegments) {
          logicalPath = ((DragableStringNode) path.getFirstSegment()).getLabel();
          label = logicalPath;
        } else {
          StringBuilder labelBuilder = new StringBuilder();
          StringBuilder pathBuilder = new StringBuilder();
          // Skip first segment because it is not used in the path
          int i = 1;
          // Add first label segment
          DragableStringNode node = (DragableStringNode) path.getSegment(i);
          pathBuilder.append(node.getLabel());
          labelBuilder.append(node.getLabel());

          // Add extra label segments if any
          i++;
          for (; i < nbSegments; i++) {
            pathBuilder.append("/");
            labelBuilder.append(".");
            node = (DragableStringNode) path.getSegment(i);
            pathBuilder.append(node.getLabel());
            labelBuilder.append(node.getLabel());
          }
          logicalPath = pathBuilder.toString();
          label = labelBuilder.toString();
        }
        try {
          OrchestraURI uri = PUCI.createArtifactSetUri(logicalPath, null);
          _foldedSelection.add(new Couple<OrchestraURI, String>(uri, label));
        } catch (Exception exception_p) {
        }
      }
    } else {
      _selection = new ArrayList<Couple<Artefact, String>>();
      for (TreePath path : paths) {
        StringBuilder builder = new StringBuilder();
        // Build complete label selection
        Object first = path.getFirstSegment();
        int i;
        // No grouping
        if (first instanceof ArtefactNode) {
          i = 0;
        } else {
          // Group by type or environment : skip first segment
          i = 1;
        }
        // Add first label segment
        ArtefactNode node = (ArtefactNode) path.getSegment(i);
        builder.append(node.getLabel());

        // Add extra label segments if any
        int nbSegments = path.getSegmentCount();
        i++;
        for (; i < nbSegments; i++) {
          builder.append(".");
          node = (ArtefactNode) path.getSegment(i);
          builder.append(node.getLabel());
        }
        Artefact artefact = node.getValue();
        _selection.add(new Couple<Artefact, String>(artefact, builder.toString()));
      }
    }
  }

  /**
   * Build GEF from selected artefact sets
   * @param filename_p GEF filename
   */
  protected void buildGEFFoldedSelection(String filename_p) {
    // Write the GEF file for found artifacts
    GefHandler handler = new GefHandler();
    GEF gef = handler.createNewModel(filename_p);

    GenericExportFormat artifactSet = GefFactory.eINSTANCE.createGenericExportFormat();
    for (Couple<OrchestraURI, String> couple : _foldedSelection) {
      Element pa = GefFactory.eINSTANCE.createElement();
      pa.setUri(couple.getKey().getUri());
      pa.setType(""); //$NON-NLS-1$
      pa.setLabel(couple.getValue());
      pa.setFullName("");
      artifactSet.getELEMENT().add(pa);
    }
    gef.getGENERICEXPORTFORMAT().add(artifactSet);

    handler.saveModel(gef, true);
    handler.clean();
  }

  /**
   * Build GEF from selected artefacts
   * @param filename_p GEF filename
   */
  public void buildGEFSelection(String filename_p) {
    // Write the GEF file for found artifacts
    GefHandler handler = new GefHandler();
    GEF gef = handler.createNewModel(filename_p);

    GenericExportFormat artifactSet = GefFactory.eINSTANCE.createGenericExportFormat();
    for (Couple<Artefact, String> couple : _selection) {
      Artefact artefact = couple.getKey();
      Element pa = GefFactory.eINSTANCE.createElement();
      pa.setUri(artefact.getUri().getUri());
      pa.setType(artefact.getType()); //$NON-NLS-1$
      pa.setLabel(couple.getValue());
      pa.setFullName(artefact.getFullName());

      Properties properties = GefFactory.eINSTANCE.createProperties();
      pa.getPROPERTIES().add(properties);

      // Properties
      for (Entry<String, String> entry : artefact.getProperties().entrySet()) {
        Property property = GefFactory.eINSTANCE.createProperty();
        property.setName(entry.getKey());
        property.setType("string");
        GefHandler.addValue(property, GefHandler.ValueType.TEXT, entry.getValue());
        properties.getPROPERTY().add(property);
      }
      // Parameters
      for (Entry<String, String> entry : artefact.getParameters().entrySet()) {
        Property property = GefFactory.eINSTANCE.createProperty();
        property.setName(Artefact.PARAMETER_PROPERTY);
        property.setType("string");
        GefHandler.addValue(property, GefHandler.ValueType.TEXT, entry.getKey());
        properties.getPROPERTY().add(property);
      }
      artifactSet.getELEMENT().add(pa);
    }

    gef.getGENERICEXPORTFORMAT().add(artifactSet);

    handler.saveModel(gef, true);
    handler.clean();
  }

  /**
   * Build GEF from selected artefacts
   * @param filename_p GEF filename
   */
  public void buildGEF(String filename_p) {
    if (null != _foldedSelection) {
      buildGEFFoldedSelection(filename_p);
    } else {
      buildGEFSelection(filename_p);
    }
  }

  /**
   * @see org.eclipse.jface.dialogs.Dialog#close()
   */
  @Override
  public boolean close() {
    if (OK == getReturnCode()) {
      // Build selection before disposing dialog
      buildSelection();
    }
    // Unsubscribe artefact jobs
    RootArtefactsBag.getInstance().removeUpdateJobListener(_jobListener);
    _viewer.unsubscribeGetRootArtefactsAction(_jobListener);
    _viewer.unsubscribeNavigateArtefactAction(_jobListener);
    _viewer.unsubscribeExpandArtefactAction(_jobListener);

    _viewer.dispose();
    return super.close();
  }

  @Override
  protected void setShellStyle(int newShellStyle) {
    super.setShellStyle(SWT.SHELL_TRIM | SWT.ON_TOP);
  }

  class SelectableType {
    public String _rootType;
    public String _objectType;

    public SelectableType(String rootType_p, String objectType_p) {
      _rootType = rootType_p;
      _objectType = objectType_p;
    }
  }

  /**
   * Action for artefact tree layout preferences
   * @author s0040806
   */
  public class PreferencesAction extends Action implements IMenuCreator {
    private Menu _menu;

    public PreferencesAction(Menu menu_p) {
      _menu = menu_p;
      setImageDescriptor(OrchestraExplorerActivator.getDefault().getImageDescriptor(IImageConstants.DESC_PREFERENCES));
      setMenuCreator(this);
    }

    public void dispose() {
      //
    }

    public Menu getMenu(Menu parent) {
      return null;
    }

    public Menu getMenu(Control parent) {
      return _menu;
    }
  }
}
