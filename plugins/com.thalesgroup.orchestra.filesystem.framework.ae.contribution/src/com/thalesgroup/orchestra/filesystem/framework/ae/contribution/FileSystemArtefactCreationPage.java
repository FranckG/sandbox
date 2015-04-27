/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.filesystem.framework.ae.contribution;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.ae.creation.ui.environment.AbstractEnvironmentArtefactCreationHandler;
import com.thalesgroup.orchestra.framework.ae.creation.ui.environment.AbstractEnvironmentArtefactCreationPage;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * @author s0040806
 */
public class FileSystemArtefactCreationPage extends AbstractEnvironmentArtefactCreationPage {
  protected static String FILTERED_ATTRIBUTE = "filtered";
  protected static String DIRECTORIES_ATTRIBUTE = "directories";

  protected Combo _directoriesCombo;
  protected Text _rootNameText;

  protected String _selectedDirectory;
  protected String _rootName;

  protected List<String> _directories = new ArrayList<String>();
  protected Text _artefactFileFullPathText;

  private String _fullPath;

  int WIDTH = 90;

  protected List<PathMatcher> _filterMatchers;

  protected FileSystemArtefactCreationHandler _handler;

  public FileSystemArtefactCreationPage(FileSystemArtefactCreationHandler handler_p) {
    this(Messages.FileSystemArtefactCreationPage_Page_Name, handler_p);
  }

  public FileSystemArtefactCreationPage(String pageId_p, FileSystemArtefactCreationHandler handler_p) {
    super(pageId_p, handler_p);
    setDescription(Messages.FileSystemArtefactCreationPage_Page_Description);
    _handler = handler_p;

    if (_environmentAttributes.containsKey(FILTERED_ATTRIBUTE)) {
      _filterMatchers = new ArrayList<PathMatcher>();
      String filters = _environmentAttributes.get(FILTERED_ATTRIBUTE);
      for (String filter : filters.split(";")) {
        _filterMatchers.add(FileSystems.getDefault().getPathMatcher("glob:" + OrchestraURI.decode(filter))); //$NON-NLS-1$
      }
    }

    String directories = _environmentAttributes.get(DIRECTORIES_ATTRIBUTE);
    for (String directory : directories.split(";")) {
      _directories.add(OrchestraURI.decode(directory));
    }
  }

  /**
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    // Initialization of the container
    Composite container = new Composite(parent_p, SWT.NULL);
    final GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 1;
    container.setLayout(gridLayout);
    setControl(container);
    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

    Composite containerForEntries = new Composite(container, SWT.NULL);
    final GridLayout gridLayoutForEntries = new GridLayout();
    gridLayoutForEntries.numColumns = 2;
    containerForEntries.setLayout(gridLayoutForEntries);
    containerForEntries.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

    Label directoriesLabel = new Label(containerForEntries, SWT.NONE);
    directoriesLabel.setText(Messages.FileSystemArtefactCreationPage_Directory_Label);
    _directoriesCombo = new Combo(containerForEntries, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
    for (String directory : _directories) {
      _directoriesCombo.add(directory);
    }
    _directoriesCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

    _directoriesCombo.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e_p) {
        _selectedDirectory = _directoriesCombo.getText();
        updateFullPath(true);
      }
    });

    Label logicalNameLabel = new Label(containerForEntries, SWT.NONE);
    logicalNameLabel.setText(Messages.FileSystemArtefactCreationPage_LogicalName_Label);

    Composite rootNameComposite = new Composite(containerForEntries, SWT.NONE);
    final GridLayout rootNameLayout = new GridLayout();
    rootNameLayout.numColumns = 2;
    rootNameComposite.setLayout(gridLayoutForEntries);
    rootNameComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

    Label associationLabel = new Label(rootNameComposite, SWT.NONE);
    associationLabel.setText(_association.replace("*", ""));

    _rootNameText = new Text(rootNameComposite, SWT.BORDER);
    _rootNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    _rootNameText.addVerifyListener(new VerifyListener() {
      @SuppressWarnings("boxing")
      @Override
      public void verifyText(VerifyEvent e_p) {
        String unauthorizedCharacters = "<>:*\"|"; //$NON-NLS-1$
        setMessage(null);
        if (e_p.character == '\\') {
          e_p.text = "/"; //$NON-NLS-1$
          e_p.doit = true;
          setMessage(Messages.FileSystemArtefactCreationPage_BackslashReplacedWithSlash, WARNING);
        } else if (unauthorizedCharacters.indexOf(e_p.character) > -1) {
          setErrorMessage(MessageFormat.format(Messages.FileSystemArtefactCreationPage_NotAuthorizedCharacter, e_p.character));
          e_p.doit = false;
        }
      }
    });
    _rootNameText.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e_p) {
        _rootName = _rootNameText.getText();
        updateFullPath(true);
      }
    });

    ISWTObservableValue observeName = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(_rootNameText, SWT.Modify));
    observeName.addValueChangeListener(createValueChangeListener());

    Group groupPhysicalPath = new Group(container, SWT.SHADOW_ETCHED_IN);
    groupPhysicalPath.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
    groupPhysicalPath.setLayout(new GridLayout(1, false));
    groupPhysicalPath.setText(Messages.FileSystemArtefactCreationPage_PhysicalPath_Label);
    GridData groupDataPhysicalPath = new GridData(GridData.FILL_HORIZONTAL);
    groupPhysicalPath.setLayoutData(groupDataPhysicalPath);
    _artefactFileFullPathText = new Text(groupPhysicalPath, SWT.NONE);
    _artefactFileFullPathText.setEditable(false);
    _artefactFileFullPathText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    // If there is only one directory, select it by default
    if (1 == _directoriesCombo.getItemCount()) {
      _directoriesCombo.select(0);
      _directoriesCombo.setEnabled(false);
      _selectedDirectory = _directoriesCombo.getText();
      updateFullPath(false);
    }

    forceUpdate();

    return container;
  }

  /**
   * Check whether a directory matches directory filters
   * @param directory_p
   * @return
   */
  protected boolean matchesDirectory(java.nio.file.Path directory_p) {
    if (null != _filterMatchers) {
      // Match on relative directory
      if (!directory_p.toString().equals("")) { //$NON-NLS-1$
        for (PathMatcher matcher : _filterMatchers) {
          if (matcher.matches(directory_p)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Data binding listener
   * @return
   */
  private IValueChangeListener createValueChangeListener() {
    return new IValueChangeListener() {
      public void handleValueChange(ValueChangeEvent event_p) {
        setMessage(null);
        if (_rootNameText.getText().equals("")) {
          setErrorMessage(Messages.FileSystemArtefactCreationPage_NameCannotBeEmpty);
          _rootName = null;
          forceUpdate();
        } else {
          java.nio.file.Path parent = Paths.get(_rootNameText.getText()).getParent();
          // Check that artefact is not in a filtered directory
          if (null != parent && matchesDirectory(parent)) {
            setErrorMessage(Messages.FileSystemArtefactCreationPage_ExcludedDirectory);
            _rootName = null;
            forceUpdate();
          } else {
            // Check that built URI is not available
            @SuppressWarnings("synthetic-access")
            OrchestraURI newUri = new OrchestraURI(_rootType, _rootNameText.getText());
            // Check that new
            if (_handler.orchestraUriExists(newUri)) {
              setErrorMessage(MessageFormat.format(Messages.FileSystemArtefactCreationPage_ArtefactUriAlreadyExists, newUri.getUri()));
              _rootName = null;
              forceUpdate();
            }
          }
        }
      }
    };
  }

  /**
   * @param check True if the name must be checked
   */
  protected void updateFullPath(boolean check) {
    String logicalName = getRootName();

    IPath name = new Path(logicalName);

    IPath fullPath = new Path(_selectedDirectory);
    if (_fileExtension != null) {
      String physicalPart = _fileExtension.replace("*", name.toString()); //$NON-NLS-1$
      fullPath = fullPath.append(physicalPart);
    } else {
      fullPath = fullPath.append(name);
    }

    if (logicalName.startsWith("../") || logicalName.contains("/../")) {
    	setErrorMessage(Messages.FileSystemArtefactCreationPage_BadLogicalName);
    	_rootName = null;
    	forceUpdate();
    } else {
    	_fullPath = fullPath.toOSString();
    	_artefactFileFullPathText.setText(_fullPath);

    	if (getArtefactFullPath().length() > WIDTH) {
    		this.getControl().pack();
    		this.getControl().getParent().getShell().pack();
    	}
    	if (check) {
    		IWorkspace workspace = ResourcesPlugin.getWorkspace();
    		IStatus status = Status.OK_STATUS;
    		for (String pathSegment : fullPath.segments()) {
    			status = workspace.validateName(pathSegment, IResource.FILE);
    			if (!status.isOK()) {
    				break;
    			}
    		}

    		if (status.isOK()) {
    			java.nio.file.Path file = Paths.get(_fullPath);
    			if (Files.exists(file)) {
    				setErrorMessage(Messages.FileSystemArtefactCreationPage_FileAlreadyExists);
    				_rootName = null;
    			} else {
    				setErrorMessage(null);
    			}
    		} else {
    			setErrorMessage(status.getMessage());
    			_rootName = null;
    		}
    		forceUpdate();
    	}
    }
  }

  /**
   * Remove leading slashes as well as duplicate slashes from root name
   * @param rootName_p
   * @return relativized root name
   */
  private String relativizeRootName(String rootName_p) {
    if (null != rootName_p) {
      IPath name = new Path(rootName_p);
      return name.makeRelative().toString();
    }
    return null;
  }

  @Override
  public Map<String, String> getUpdatedValues() {
    Map<String, String> parameters = new HashMap<String, String>();
    // Compute association on root name first
    String rootName = null == _rootName ? _rootName : _association.replace("*", _rootName);
    parameters.put(AbstractEnvironmentArtefactCreationHandler.ROOT_NAME_ATTRIBUTE, relativizeRootName(rootName));
    parameters.put(FileSystemArtefactCreationHandler.PHYSICAL_PATH_PARAMETER, _fullPath);
    return parameters;
  }

  /**
   * @return
   */
  protected String getArtefactFullPath() {
    return _artefactFileFullPathText.getText();
  }

  /**
   * @return
   */
  protected String getRootName() {
    return _rootNameText.getText();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.wizard.creation.environment.AbstractEnvironmentArtefactCreationPage#doCanFlipToNextPage()
   */
  @Override
  protected boolean doCanFlipToNextPage() {
    return null != _rootName;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.wizard.creation.environment.AbstractEnvironmentArtefactCreationPage#getPageTitle()
   */
  protected String getPageTitle() {
    return Messages.FileSystemArtefactCreationPage_Page_Title;
  }

}
