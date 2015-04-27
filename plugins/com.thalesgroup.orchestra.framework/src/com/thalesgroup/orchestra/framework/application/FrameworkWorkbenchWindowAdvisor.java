/**
 * <p>
 * Copyright (c) 2002-2008 : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Société : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Thales Part Number 16 262 601
 * </p>
 */
package com.thalesgroup.orchestra.framework.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener;
import com.thalesgroup.orchestra.framework.model.handler.data.RootElement;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.server.ServerManager;
import com.thalesgroup.orchestra.framework.ui.action.SetAsCurrentContextAction;

/**
 * <p>
 * Title : PapeeteWorkbenchWindowAdvisor
 * </p>
 * <p>
 * Description : Orchestra Framework UI WorkbenchWindow Advisor
 * </p>
 * @author Orchestra Framework Tool Suite developer
 * @version 3.8.0
 */
public class FrameworkWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor implements ICurrentContextChangeListener {
  /**
   * Contexts found in the admin contexts pool (null if no such pool specified).
   */
  protected List<RootContextsProject> _contextsInPool;
  /**
   * Initial context setting errors.
   */
  private IStatus _initialContextErrors;
  private ServerManager _manager;
  protected Menu menu;
  private Image trayImage;
  protected TrayItem trayItem;

  protected IWorkbenchWindow window;

  /**
   * FrameworkWorkbenchWindowAdvisor Constructor
   * @param configurer
   * @param manager_p
   * @param initialContextErrors_p
   */
  public FrameworkWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer, ServerManager manager_p, IStatus initialContextErrors_p,
      List<RootContextsProject> contextsInPool_p) {
    super(configurer);
    _manager = manager_p;
    _initialContextErrors = initialContextErrors_p;
    // Register as context listener.
    ModelHandlerActivator.getDefault().getDataHandler().addCurrentContextChangeListener(this);

    _contextsInPool = contextsInPool_p;
  }

  /**
   * Add common actions (Restore, Minimize and Shutdown Orchestra Framework) in the menu.
   * @param menu_p
   */
  protected void addCommonActions(final Menu menu_p) {
    new MenuItem(menu_p, SWT.SEPARATOR);
    MenuItem restoreItem = new MenuItem(menu_p, SWT.NONE);
    restoreItem.setText(Messages.PapeeteWorkbenchWindowAdvisor_Menu_Text_Restore);
    restoreItem.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event event) {
        restore();
      }
    });
    menu_p.setDefaultItem(restoreItem);
    if (!window.getShell().getMinimized()) {
      MenuItem minimizeItem = new MenuItem(menu_p, SWT.NONE);
      minimizeItem.setText(Messages.PapeeteWorkbenchWindowAdvisor_Menu_Text_Minimize);
      minimizeItem.addListener(SWT.Selection, new Listener() {
        public void handleEvent(Event event) {
          minimize();
        }
      });
    }
    MenuItem exitItem = new MenuItem(menu_p, SWT.NONE);
    exitItem.setText(Messages.PapeeteWorkbenchWindowAdvisor_Menu_Text_Shutdown);
    exitItem.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event event) {
        PlatformUI.getWorkbench().close();
      }
    });
  }

  /**
   * Create "Participate to" sub menu.
   * @param menu_p
   */
  protected void addParticipateToSubMenu(Menu menu_p) {
    if (null == _contextsInPool || _contextsInPool.isEmpty()) {
      // No contexts in pool -> the menu is not created.
      return;
    }
    List<RootContextsProject> adminContextsToJoin = new ArrayList<RootContextsProject>();
    for (RootContextsProject rootContextsProject : _contextsInPool) {
      // Project containing the context isn't already accessible or it doesn't already contain an user context for current user -> add it to the menu.
      if (!rootContextsProject._project.isAccessible() || null == rootContextsProject.getContext()) {
        adminContextsToJoin.add(rootContextsProject);
      }
    }
    // No context to participate to.
    if (adminContextsToJoin.isEmpty()) {
      return;
    }
    // For presentation purposes, sort admin contexts.
    Collections.sort(adminContextsToJoin, new Comparator<RootContextsProject>() {
      public int compare(RootContextsProject o1_p, RootContextsProject o2_p) {
        return o1_p.getAdministratedContext().getName().compareToIgnoreCase(o2_p.getAdministratedContext().getName());
      }
    });
    new MenuItem(menu_p, SWT.SEPARATOR);
    MenuItem joinSubMenuItem = new MenuItem(menu_p, SWT.CASCADE);
    joinSubMenuItem.setText(Messages.FrameworkWorkbenchWindowAdvisor_Menu_Text_JoinContext);
    Menu joinSubMenu = new Menu(menu_p);
    joinSubMenuItem.setMenu(joinSubMenu);
    // Fill the sub menu with admin context names.
    for (final RootContextsProject adminContextToJoin : adminContextsToJoin) {
      MenuItem ctxToJoinMenuItem = new MenuItem(joinSubMenu, SWT.PUSH);
      ctxToJoinMenuItem.setText(adminContextToJoin.getAdministratedContext().getName());
      ctxToJoinMenuItem.addSelectionListener(new SelectionAdapter() {
        /**
         * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        @Override
        public void widgetSelected(SelectionEvent e_p) {
          String administratorContextName = adminContextToJoin.getAdministratedContext().getName();
          DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
          IStatus participateStatus = dataHandler.participateInAProject(administratorContextName, adminContextToJoin);
          // Check user context has been created/loaded successfully.
          if (!participateStatus.isOK()) {
            DisplayHelper.displayErrorDialog(Messages.FrameworkWorkbenchWindowAdvisor_ErrorDialogTitle_ParticipateFailed, null, participateStatus);
            return;
          }
          // Get user context.
          Context userContext = dataHandler.getContext(adminContextToJoin.getContext());
          if (null == userContext) {
            IStatus errorStatus =
                new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(),
                    Messages.FrameworkWorkbenchWindowAdvisor_JoineContextCantBeSetAsCurrent);
            DisplayHelper.displayErrorDialog(Messages.FrameworkWorkbenchWindowAdvisor_ErrorDialogTitle_ParticipateFailed, null, errorStatus);
            return;
          }
          // Set it as current.
          new SetAsCurrentContextAction().setAsCurrentContext(userContext.getName());
        }
      });
    }
  }

  /**
   * Add all contexts in menu in order to select them as Current. In the menu, current context is selected, modified contexts are disabled.
   * @param menu_p
   */
  protected void addSetAsCurrentContextActions(final Menu menu_p) {
    RootElement rootElement = ModelHandlerActivator.getDefault().getDataHandler().getAllContexts();
    for (Context context : rootElement.getContexts()) {
      MenuItem item = new MenuItem(menu, SWT.RADIO);
      String name = context.getName();
      item.setText((null != name) ? name : ICommonConstants.EMPTY_STRING);
      item.addSelectionListener(new SetAsCurrentContextAction());
      // If this context is modified, unable to select it.
      item.setEnabled(!ModelHandlerActivator.getDefault().getEditingDomain().isModified(context.eResource()));
      // Current context is marked as selected.
      item.setSelection(ModelHandlerActivator.getDefault().getDataHandler().isCurrentContext(context));
    }
  }

  private void close() {
    window.getShell().setMinimized(true);
    window.getShell().setVisible(false);
  }

  private String consultCurrentContextName() {
    return ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext().getName();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener#contextChanged(com.thalesgroup.orchestra.framework.model.contexts.Context,
   *      org.eclipse.core.runtime.IStatus, org.eclipse.core.runtime.IProgressMonitor, boolean)
   */
  public IStatus contextChanged(final Context currentContext_p, final IStatus errorStatus_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p) {
    // Progress reporting.
    if (null != progressMonitor_p) {
      progressMonitor_p.setTaskName(Messages.FrameworkWorkbenchWindowAdvisor_SwitchContextMessage);
    }
    Runnable r = new Runnable() {
      public void run() {
        // Update tray image
        updateTrayImage(errorStatus_p);
        // Display a notification bubble (Tooltip is created each time to avoid persistent tooltips when context switches are too quick !).
        final ToolTip tip = new ToolTip(window.getShell(), SWT.BALLOON | SWT.ICON_INFORMATION);
        trayItem.setToolTip(tip);
        String name = currentContext_p.getName();
        tip.setMessage(Messages.PapeeteWorkbenchWindowAdvisor_PopUp_Message + name);
        tip.setVisible(false);
        tip.setVisible(true);
        // Update title bar.
        String productAndContext = generateProductAndContext(name);
        setToolTipTextAndWindowTitle(productAndContext);
      }
    };
    PlatformUI.getWorkbench().getDisplay().asyncExec(r);
    return Status.OK_STATUS;
  }

  /**
   * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#createActionBarAdvisor(org.eclipse.ui.application.IActionBarConfigurer)
   */
  @Override
  public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
    return new FrameworkApplicationActionBarAdvisor(configurer);
  }

  /** Minimize action for the system tray */
  private void createMinimize() {
    window.getShell().addShellListener(new ShellAdapter() {
      @Override
      public void shellIconified(ShellEvent e) {
        if (FrameworkConsoleView.isMinimizedInSysTrayOnly()) {
          minimize();
        }
      }
    });
    trayItem.addSelectionListener(new SelectionListener() {
      /**
       * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
       */
      public void widgetDefaultSelected(SelectionEvent e_p) {
        restore();
      }

      /**
       * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      public void widgetSelected(SelectionEvent e_p) {
        restore();
      }
    });
  }

  @Override
  public void dispose() {
    if (trayImage != null) {
      trayImage.dispose();
      trayItem.dispose();
    }
    // Unregister as context listener.
    ModelHandlerActivator.getDefault().getDataHandler().removeCurrentContextChangeListener(this);
  }

  protected String generateProductAndContext(String contextName) {
    StringBuilder text = new StringBuilder();
    text.append(Platform.getProduct().getName());
    if (contextName != null) {
      text.append(" - "); //$NON-NLS-1$
      text.append(contextName);
    }
    return text.toString();
  }

  /** Menu for the system tray */
  private void hookPopupMenu() {
    Listener listener = new Listener() {
      public void handleEvent(Event event) {
        menu = new Menu(window.getShell(), SWT.POP_UP);
        addSetAsCurrentContextActions(menu);
        addParticipateToSubMenu(menu);
        addCommonActions(menu);
        menu.setVisible(true);
      }
    };
    trayItem.addListener(SWT.MenuDetect, listener);
  }

  /** Initialize the Tray */
  private void initTrayItem(IWorkbenchWindow window_p) {
    Shell shell = window_p.getShell();
    final Tray tray = shell.getDisplay().getSystemTray();
    trayItem = new TrayItem(tray, SWT.NONE);
    updateTrayImage(_initialContextErrors);
    _initialContextErrors = null;
  }

  /** Minimize Action */
  protected void minimize() {
    window.getShell().setMinimized(true);
    if (FrameworkConsoleView.isMinimizedInSysTrayOnly()) {
      window.getShell().setVisible(false);
    } else {
      window.getShell().setVisible(true);
    }
  }

  @Override
  public void postWindowOpen() {
    super.postWindowOpen();
    window = getWindowConfigurer().getWindow();
    initTrayItem(window);
    String productAndContext = generateProductAndContext(consultCurrentContextName());
    setToolTipTextAndWindowTitle(productAndContext);
    if (trayItem != null) {
      createMinimize();
      // Create restore, minimize and exit action on the icon
      hookPopupMenu();
    }
    // Minimize the window if asked.
    if (ProjectActivator.getInstance().getCommandLineArgsHandler().minimizeAsked()) {
      minimize();
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener#preContextChange(com.thalesgroup.orchestra.framework.model.contexts.Context,
   *      org.eclipse.core.runtime.IProgressMonitor, boolean)
   */
  public void preContextChange(Context futurCurrentContext_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p) {
    // Nothing to do before current context change.
  }

  @Override
  public void preWindowOpen() {
    IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
    configurer.setInitialSize(new Point(800, 600));
    configurer.setShowCoolBar(true);
    configurer.setShowStatusLine(true);
    configurer.setShowProgressIndicator(true);
  }

  @Override
  public boolean preWindowShellClose() {
    if (_manager.isHTTPServerStarted()) {
      close();
      return false;
    }
    return super.preWindowShellClose();
  }

  /** Restore Action */
  protected void restore() {
    Shell shell = window.getShell();
    shell.setMinimized(false);
    shell.setVisible(true);
    shell.setActive();
    shell.forceFocus();
  }

  protected void setToolTipTextAndWindowTitle(String productAndContext) {
    trayItem.setToolTipText(productAndContext);
    getWindowConfigurer().setTitle(productAndContext);
  }

  protected void updateTrayImage(IStatus errorStatus_p) {
    // If an error occurs during init or context loading, display a warning tray icon.
    if (!_manager.isHTTPServerStarted() || (errorStatus_p != null && !errorStatus_p.isOK())) {
      trayImage = FrameworkActivator.getDefault().getImage("OrchestraFmk-16x16-warning.png"); //$NON-NLS-1$
    } else {
      trayImage = FrameworkActivator.getDefault().getImage("OrchestraFmk-16x16.png"); //$NON-NLS-1$
    }
    trayItem.setImage(trayImage);
  }
}