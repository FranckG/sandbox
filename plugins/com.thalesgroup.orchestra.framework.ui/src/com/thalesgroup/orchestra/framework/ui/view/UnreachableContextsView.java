/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.view;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;

import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.project.CaseUnsensitiveResourceSetImpl;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.ProjectHandler;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;

/**
 * Show unreachable projects in an Eclipse view prod00106752
 * @author s0040806
 */
public class UnreachableContextsView extends ViewPart {

  protected static final int PROJECT_NAME_COLUMN = 0;
  protected static final int LOCATION_COLUMN = 1;

  protected Table _table;

  List<Couple<String, URL>> _unreachableContexts;

  /**
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl(Composite parent_p) {
    FormToolkit toolkit = new FormToolkit(parent_p.getDisplay());
    _table = toolkit.createTable(parent_p, SWT.MULTI | SWT.FULL_SELECTION);
    _table.setHeaderVisible(true);
    GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
    _table.setLayoutData(data);

    String[] titles = { Messages.UnreachableContextsView_Label_ProjectName, Messages.UnreachableContextsView_Label_Location };
    for (int i = 0; i < titles.length; i++) {
      TableColumn column = new TableColumn(_table, SWT.NONE);
      column.setText(titles[i]);
    }

    // Get unreachable project list and sort it by project name
    _unreachableContexts = new ArrayList<Couple<String, URL>>();
    Map<URL, String> contexts = ProjectHelper.getUnreachablebleProjects();
    for (Entry<URL, String> entry : contexts.entrySet()) {
      _unreachableContexts.add(new Couple<String, URL>(entry.getValue(), entry.getKey()));
    }
    Collections.sort(_unreachableContexts, new Comparator<Couple<String, URL>>() {
      public int compare(Couple<String, URL> arg0_p, Couple<String, URL> arg1_p) {
        return arg0_p.getKey().compareTo(arg1_p.getKey());
      }
    });

    // Fill projects in the table
    try {
      for (Couple<String, URL> entry : _unreachableContexts) {
        TableItem item = new TableItem(_table, SWT.NONE);
        item.setText(PROJECT_NAME_COLUMN, entry.getKey());
        // Remove leading '/' from URL path
        item.setText(LOCATION_COLUMN, URLDecoder.decode(entry.getValue().getPath().substring(1), "UTF-8"));
      }
    } catch (UnsupportedEncodingException exception_p) {
      // TODO Auto-generated catch block
    }

    for (int i = 0; i < titles.length; i++) {
      _table.getColumn(i).pack();
    }

    // Build contextual menu
    Menu contextualMenu = new Menu(_table);
    final MenuItem reloadItem = new MenuItem(contextualMenu, SWT.NONE);
    reloadItem.setText(Messages.UnreachableContextsView_Label_ReloadContext);
    final MenuItem reloadAllItem = new MenuItem(contextualMenu, SWT.NONE);
    reloadAllItem.setText(Messages.UnreachableContextsView_Label_ReloadAllContexts);
    new MenuItem(contextualMenu, SWT.BAR);
    final MenuItem removeItem = new MenuItem(contextualMenu, SWT.NONE);
    removeItem.setText(Messages.UnreachableContextsView_Label_RemoveContext);

    MenuListener menuListener = new MenuListener() {
      // When contextual menu is shown
      public void menuShown(MenuEvent e_p) {
        int[] selections = _table.getSelectionIndices();
        if (0 == _table.getItemCount()) {
          reloadAllItem.setEnabled(false);
          reloadItem.setEnabled(false);
          removeItem.setEnabled(false);
        } else {
          reloadAllItem.setEnabled(true);
          if (0 == selections.length) {
            reloadItem.setEnabled(false);
            removeItem.setEnabled(false);
          } else {
            reloadItem.setEnabled(true);
            removeItem.setEnabled(true);
          }
        }
      }

      public void menuHidden(MenuEvent e_p) {
        // TODO Auto-generated method stub
      }
    };
    contextualMenu.addMenuListener(menuListener);

    SelectionListener selectionListener = new SelectionListener() {
      public void widgetSelected(SelectionEvent e_p) {
        int[] selections = _table.getSelectionIndices();
        Arrays.sort(selections);
        // Reload selected unreachable context
        if (e_p.widget == reloadItem) {
          MultiStatus displayStatus = null;
          for (int i = selections.length - 1; i >= 0; i--) {
            IStatus status = importContext(_table.getItem(selections[i]).getText(LOCATION_COLUMN));
            if (status.isOK()) {
              removeProject(selections[i]);
            } else {
              if (null == displayStatus) {
                displayStatus =
                    new MultiStatus(OrchestraFrameworkUiActivator.getDefault().getPluginId(), 0, MessageFormat.format(
                        Messages.UnreachableContextsView_ImportError_Reason, IDialogConstants.SHOW_DETAILS_LABEL), null);
              }
              displayStatus.add(status);
            }
          }
          if (null != displayStatus) {
            DisplayHelper.displayErrorDialog(Messages.UnreachableContextsView_ImportError_Title, Messages.UnreachableContextsView_ImportError_Message,
                displayStatus);
          }
        } else if (e_p.widget == reloadAllItem) {
          // Reload all unreachable contexts
          MultiStatus displayStatus = null;
          int nbProjects = _unreachableContexts.size();
          for (int i = nbProjects - 1; i >= 0; i--) {
            IStatus status = importContext(_table.getItem(i).getText(LOCATION_COLUMN));
            if (status.isOK()) {
              removeProject(i);
            } else {
              if (null == displayStatus) {
                displayStatus =
                    new MultiStatus(OrchestraFrameworkUiActivator.getDefault().getPluginId(), 0, MessageFormat.format(
                        Messages.UnreachableContextsView_ImportError_Reason, IDialogConstants.SHOW_DETAILS_LABEL), null);
              }
              displayStatus.add(status);
            }
          }

          if (null != displayStatus) {
            DisplayHelper.displayErrorDialog(Messages.UnreachableContextsView_ImportError_Title, Messages.UnreachableContextsView_ImportError_Message,
                displayStatus);
          }
        } else if (e_p.widget == removeItem) {
          // Remove projects from unreachable contexts list, in the reverse order of the selection
          for (int i = selections.length - 1; i >= 0; i--) {
            removeProject(selections[i]);
          }
        }
      }

      public void widgetDefaultSelected(SelectionEvent e_p) {
        // TODO Auto-generated method stub
      }
    };

    reloadItem.addSelectionListener(selectionListener);
    reloadAllItem.addSelectionListener(selectionListener);
    removeItem.addSelectionListener(selectionListener);

    _table.setMenu(contextualMenu);

  }

  /**
   * Import context into workspace
   * @param contextLocation_p Path to project
   * @return Error status
   */
  IStatus importContext(String contextLocation_p) {
    ResourceSet resourceSet = new CaseUnsensitiveResourceSetImpl();
    ProjectHandler projectHandler = ProjectActivator.getInstance().getProjectHandler();
    RootContextsProject currentContext = projectHandler.getContextFromLocation(contextLocation_p, resourceSet);
    if (null == currentContext) {
      return new Status(IStatus.ERROR, OrchestraFrameworkUiActivator.getDefault().getPluginId(), MessageFormat.format(
          Messages.UnreachableContextsView_ImportError_ContextStillUnreachable, contextLocation_p));
    }

    List<RootContextsProject> rootProjects = Collections.singletonList(currentContext);
    // Import project
    IStatus importContextsStatuses = ModelHandlerActivator.getDefault().getDataHandler().importContexts(rootProjects, false);
    return importContextsStatuses;
  }

  /**
   * Remove context from unreachable contexts list
   * @param selection_p Selection in the context list
   */
  void removeProject(int selection_p) {
    _table.remove(selection_p);
    ProjectHelper.removeProjectFromUnreachableProjects(_unreachableContexts.get(selection_p).getValue());
    _unreachableContexts.remove(selection_p);
  }

  /**
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    // TODO Auto-generated method stub

  }
}
