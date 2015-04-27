package com.thalesgroup.orchestra.framework.application;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.thalesgroup.orchestra.framework.environments.actions.MakeBaselineAction;
import com.thalesgroup.orchestra.framework.environments.actions.UseBaselineAction;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.ui.action.AdministratorModeAction;
import com.thalesgroup.orchestra.framework.ui.action.AdministratorModeStatusLine;
import com.thalesgroup.orchestra.framework.ui.action.NewContextAction;
import com.thalesgroup.orchestra.framework.ui.action.SetModeAction;
import com.thalesgroup.orchestra.framework.ui.action.SetModeAdministratorAction;
import com.thalesgroup.orchestra.framework.ui.action.SetModeUserAction;

public class FrameworkApplicationActionBarAdvisor extends ActionBarAdvisor {
  private IWorkbenchAction _aboutAction;
  private AdministratorModeAction _administratorModeAction;
  private IWorkbenchAction _copyAction;
  private IWorkbenchAction _cutAction;
  private IWorkbenchAction _deleteAction;
  private IWorkbenchAction _exitAction;
  private AdministratorModeStatusLine _item;
  private IAction _makeBaselineAction;
  private IAction _newAction;
  private IWorkbenchAction _pasteAction;
  private IWorkbenchAction _preferencesAction;
  private IWorkbenchAction _redoAction;
  private IWorkbenchAction _resetPerspectiveAction;
  private IWorkbenchAction _saveAction;
  private SetModeAction _setModeAdministrationAction;
  private SetModeAction _setModeUserAction;
  private IWorkbenchAction _undoAction;
  private IAction _useBaselineAction;

  public FrameworkApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
    super(configurer);
  }

  /**
   * Create edit menu.
   * @return
   */
  protected MenuManager createEditMenu() {
    MenuManager editMenu = new MenuManager(Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_Edit, IWorkbenchActionConstants.M_EDIT);
    editMenu.add(_undoAction);
    editMenu.add(_redoAction);
    editMenu.add(new Separator());
    editMenu.add(_cutAction);
    editMenu.add(_copyAction);
    editMenu.add(_pasteAction);
    editMenu.add(new Separator());
    editMenu.add(_deleteAction);
    return editMenu;
  }

  /**
   * Create file menu.
   * @return
   */
  protected MenuManager createFileMenu() {
    MenuManager fileMenu = new MenuManager(Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_File, "OFFile"); //$NON-NLS-1$
    fileMenu.add(_newAction);
    fileMenu.add(new Separator());
    fileMenu.add(_saveAction);
    fileMenu.add(new Separator());
    fileMenu.add(_makeBaselineAction);
    fileMenu.add(_useBaselineAction);
    fileMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
    fileMenu.add(new Separator());
    fileMenu.add(_exitAction);
    return fileMenu;
  }

  /**
   * Create help menu.
   * @return
   */
  protected MenuManager createHelpMenu() {
    MenuManager helpMenu = new MenuManager(Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_Help, "OFHelp"); //$NON-NLS-1$
    helpMenu.add(_aboutAction);
    return helpMenu;
  }

  /**
   * Create mode menu.
   * @return
   */
  protected MenuManager createModeMenu() {
    MenuManager modeMenu = new MenuManager(Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_Mode, "OFMode"); //$NON-NLS-1$
    _setModeAdministrationAction = new SetModeAdministratorAction();
    ProjectActivator.getInstance().addAdministratorModeChangeListener(_setModeAdministrationAction);
    modeMenu.add(_setModeAdministrationAction);
    _setModeUserAction = new SetModeUserAction();
    ProjectActivator.getInstance().addAdministratorModeChangeListener(_setModeUserAction);
    modeMenu.add(_setModeUserAction);
    return modeMenu;
  }

  /**
   * Create a tool-bar manager
   * @param coolBarManager_p the parent cool-bar, if not <code>null</code> the returned tool-bar manager is automatically added in it.
   * @param minimumVisibleItems_p number of minimum visible items. To force all items to be visible, use <code>SWT.DEFAULT</code> value.
   * @return a not <code>null</code> {@link ToolBarManager}
   */
  private IToolBarManager createToolBarManager(IContributionManager coolBarManager_p, int minimumVisibleItems_p) {
    // Default tool-bar SWT.Trail is used to have text leading the icon into a ToolItem
    int TOOLBAR_STYLE = SWT.FLAT | SWT.WRAP | SWT.HORIZONTAL | SWT.TRAIL;
    ToolBarManager toolBarManager = new ToolBarManager(TOOLBAR_STYLE);
    ToolBarContributionItem item = new ToolBarContributionItem(toolBarManager);
    // if minimumVisibleItems_p == SWT.DEFAULT then all items are forced to be visible.
    if (SWT.DEFAULT != minimumVisibleItems_p) {
      item.setMinimumItemsToShow(minimumVisibleItems_p);
      item.setUseChevron(true);
    }
    if (null != coolBarManager_p) {
      coolBarManager_p.add(item);
    }
    return toolBarManager;
  }

  /**
   * Create window menu.
   * @return
   */
  protected MenuManager createWindowMenu() {
    MenuManager windowMenu = new MenuManager(Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_Window, IWorkbenchActionConstants.M_WINDOW);
    windowMenu.add(_resetPerspectiveAction);
    windowMenu.add(new Separator());
    windowMenu.add(_preferencesAction);
    return windowMenu;
  }

  @Override
  public void dispose() {
    ProjectActivator.getInstance().removeAdministratorModeListener(_item);
  }

  /**
   * @see org.eclipse.ui.application.ActionBarAdvisor#disposeAction(org.eclipse.jface.action.IAction)
   */
  @Override
  protected void disposeAction(IAction action_p) {
    // Dispose administrator mode action (if it was created).
    if (!ProjectActivator.getInstance().getCommandLineArgsHandler().userModeOnly() && _administratorModeAction.equals(action_p)) {
      ProjectActivator.getInstance().removeAdministratorModeListener(_administratorModeAction);
    }
    if (_setModeAdministrationAction.equals(action_p)) {
      ProjectActivator.getInstance().removeAdministratorModeListener(_setModeAdministrationAction);
    }
    if (_setModeUserAction.equals(action_p)) {
      ProjectActivator.getInstance().removeAdministratorModeListener(_setModeUserAction);
    }
  }

  @Override
  protected void fillCoolBar(ICoolBarManager coolBar_p) {
    IToolBarManager toolBarManager = createToolBarManager(coolBar_p, SWT.DEFAULT);
    // File
    toolBarManager.add(new ActionContributionItem(_newAction));
    toolBarManager.add(new ActionContributionItem(_saveAction));
    // Edit
    toolBarManager.add(new ActionContributionItem(_undoAction));
    toolBarManager.add(new ActionContributionItem(_redoAction));
    toolBarManager.add(new ActionContributionItem(_cutAction));
    toolBarManager.add(new ActionContributionItem(_copyAction));
    toolBarManager.add(new ActionContributionItem(_pasteAction));
    // Create change administrator mode contribution (if not in user only mode).
    if (!ProjectActivator.getInstance().getCommandLineArgsHandler().userModeOnly()) {
      IToolBarManager applicativeToolBarManager = createToolBarManager(coolBar_p, SWT.DEFAULT);
      applicativeToolBarManager.add(new ActionContributionItem(_administratorModeAction));
    }
  }

  @Override
  protected void fillMenuBar(IMenuManager menuBar) {
    menuBar.add(createFileMenu());
    menuBar.add(createEditMenu());
    // Create "Mode" menu (if not in user only mode).
    if (!ProjectActivator.getInstance().getCommandLineArgsHandler().userModeOnly()) {
      menuBar.add(createModeMenu());
    }
    menuBar.add(createWindowMenu());
    // Add a group marker indicating where action set menus will appear.
    menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
    menuBar.add(createHelpMenu());
  }

  /**
   * @see org.eclipse.ui.application.ActionBarAdvisor#fillStatusLine(org.eclipse.jface.action.IStatusLineManager)
   */
  @Override
  protected void fillStatusLine(IStatusLineManager statusLine_p) {
    super.fillStatusLine(statusLine_p);
    _item = new AdministratorModeStatusLine();
    ProjectActivator.getInstance().addAdministratorModeChangeListener(_item);
    statusLine_p.prependToGroup(StatusLineManager.BEGIN_GROUP, _item);
  }

  /**
   * @see org.eclipse.ui.application.ActionBarAdvisor#makeActions(org.eclipse.ui.IWorkbenchWindow)
   */
  @Override
  protected void makeActions(final IWorkbenchWindow window) {
    // File Menu
    // New action.
    _newAction = new NewContextAction();
    _newAction.setActionDefinitionId("org.eclipse.ui.newWizard"); //$NON-NLS-1$
    _newAction.setId("new"); //$NON-NLS-1$
    register(_newAction);
    // Save action.
    _saveAction = ActionFactory.SAVE.create(window);
    register(_saveAction);
    // Make Baseline action.
    _makeBaselineAction = new MakeBaselineAction();
    _makeBaselineAction.setId("makeBaseline"); //$NON-NLS-1$
    register(_makeBaselineAction);
    // Use baseline action.
    _useBaselineAction = new UseBaselineAction();
    _useBaselineAction.setId("useBaseline"); //$NON-NLS-1$
    register(_useBaselineAction);
    // Exit action.
    _exitAction = ActionFactory.QUIT.create(window);
    register(_exitAction);

    // Edit menu
    // Undo action.
    _undoAction = ActionFactory.UNDO.create(window);
    register(_undoAction);
    // Redo action.
    _redoAction = ActionFactory.REDO.create(window);
    register(_redoAction);
    // Cut action.
    _cutAction = ActionFactory.CUT.create(window);
    register(_cutAction);
    // Copy action.
    _copyAction = ActionFactory.COPY.create(window);
    register(_copyAction);
    // Paste action.
    _pasteAction = ActionFactory.PASTE.create(window);
    register(_pasteAction);
    // Delete action.
    _deleteAction = ActionFactory.DELETE.create(window);
    register(_deleteAction);

    // Mode menu
    // Create change administrator mode action (if not in user only mode).
    if (!ProjectActivator.getInstance().getCommandLineArgsHandler().userModeOnly()) {
      _administratorModeAction = new AdministratorModeAction();
      ProjectActivator.getInstance().addAdministratorModeChangeListener(_administratorModeAction);
      register(_administratorModeAction);
    }

    // Window Menu
    // Reset perspective action.
    _resetPerspectiveAction = ActionFactory.RESET_PERSPECTIVE.create(window);
    register(_resetPerspectiveAction);
    _preferencesAction = ActionFactory.PREFERENCES.create(window);
    register(_preferencesAction);

    // Help Menu
    // About action.
    _aboutAction = ActionFactory.ABOUT.create(window);
    register(_aboutAction);
  }
}