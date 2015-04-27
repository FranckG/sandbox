/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.matchers.AnyOf;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.matchers.WidgetOfType;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.hamcrest.Matcher;
import org.junit.Assert;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.DeleteContextServiceCommand;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.migration.AbstractMigration;
import com.thalesgroup.orchestra.framework.model.migration.AbstractMigration.VariableType;
import com.thalesgroup.orchestra.framework.project.CaseUnsensitiveResourceSetImpl;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.swtbotcondition.CategoryRenamedCondition;
import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.swtbotcondition.ConditionFactory;
import com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer;

/**
 * Abstract test class.
 * @author t0076261
 */
@SuppressWarnings("nls")
public abstract class AbstractTest {
  /**
   * Bot reference.
   */
  private static SWTWorkbenchBot __bot;

  public static final long CONDITION_INTERVAL = 500;

  public static final long CONDITION_TIMEOUT = 20000;
  /**
   * Variables view ID.
   */
  protected static final String VIEW_ID_VARIABLES = "com.thalesgroup.orchestra.framework.ui.view.variables";
  /**
   * Remove temporary folder.
   */
  static {
    String tmpFolderPath = AbstractTest.getSharedTemporaryFolder();
    try {
      FileUtils.cleanDirectory(new File(tmpFolderPath));
    } catch (IOException exception_p) {
      System.err.println("An error occured when deleting UI auto tests temporary data."); //$NON-NLS-1$
    }
  }
  /**
   * Condition factory.
   */
  private ConditionFactory _conditionFactory;

  /**
   * Change administration mode.
   * @param administrator_p <code>true</code> to change to administrator, <code>false</code> to change to user.
   */
  protected void changeModeTo(boolean administrator_p) throws Exception {
    String menuName =
        com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_Mode.replace("&", ICommonConstants.EMPTY_STRING);
    String subMenu =
        administrator_p ? com.thalesgroup.orchestra.framework.ui.action.Messages.SetModeAdministratorAction_Action_Text
                       : com.thalesgroup.orchestra.framework.ui.action.Messages.SetModeUserAction_Action_Text0;

    getBot().menu(menuName).menu(subMenu).click();
  }

  /**
   * Retrieve a CheckBox based on its text.
   * @param buttonText_p The text of the button to find
   * @return The SWTBotCheckBox associated to the given text
   */
  @SuppressWarnings("unchecked")
  protected SWTBotCheckBox checkBoxWithText(final String buttonText_p) {
    Matcher<? extends Widget> checkBoxMatcher =
        WidgetMatcherFactory.allOf(WidgetMatcherFactory.widgetOfType(Button.class), WidgetMatcherFactory.withText(buttonText_p),
            WidgetMatcherFactory.withStyle(SWT.CHECK, "SWT.CHECK"));
    return new SWTBotCheckBox((Button) getBot().widget(checkBoxMatcher), checkBoxMatcher);
  }

  /**
   * Create a category in a given parent category
   * @param parentCategoryPath_p The path of the parent category
   * @param categoryName_p The name of the category to create
   * @param context_p The context
   * @return The created category, else null
   */
  protected Category createCategory(final String parentCategoryPath_p, final String categoryName_p, final Context context_p) {
    final Category[] tab = new Category[1];
    AbstractMigration migration = new AbstractMigration() {

      @Override
      protected IStatus doMigrate() {
        Category category = createCategory(parentCategoryPath_p, categoryName_p, context_p);
        if (null != category) {
          tab[0] = category;
          return Status.OK_STATUS;
        }
        return Status.CANCEL_STATUS;
      }
    };
    IStatus result = migration.migrate();
    if (Status.OK_STATUS == result) {
      return tab[0];
    }
    return null;
  }

  /**
   * Create a project and its participation for specified context name.<br>
   * The name will match the administrator context name.<br>
   * The user participation context is computed from this one.
   * @param contextName_p
   * @param createParticipation_p <code>true</code> to create additional participation, as a user. <code>false</code> to create context as administrator only.
   * @return The user participation name, if creation of participation was asked, the specified context name otherwise.
   * @throws Exception
   */
  public String createContext(String contextName_p, boolean createParticipation_p) throws Exception {
    // Create administrator context.
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    dataHandler.createNewContextStructure(contextName_p, null, null);
    // Create participation.
    if (createParticipation_p) {
      String participationName = "Participation to " + contextName_p;
      Couple<RootContextsProject, Context> couple = findContextForName(contextName_p, false);
      dataHandler.participateInAProject(participationName, couple.getKey());
      return participationName;
    }
    return contextName_p;
  }

  /**
   * Create a variable in a given category, returns null if the creation failed
   * @param variableName_p The variable name
   * @param variableValues_p The list of variable values, may be empty
   * @param variableDescription_p The variable description
   * @param isMandatory_p <code>true</code> if the variable is mandatory, else <code>false</code>
   * @param isFinal_p <code>true</code> if the variable is final , else <code>false</code>
   * @param parentCategoryPath_p The path of the category containing the variable to create
   * @param context_p The context
   * @param variableType_p The type of the variable: Variable, File or Folder
   * @param keepExistingValues_p <code>true</code> keep existing values if the variable already exists, else <code>false</code>
   * @return The created variable, else null
   */
  protected Variable createVariable(final String variableName_p, final List<String> variableValues_p, final String variableDescription_p,
      final boolean isMandatory_p, final boolean isFinal_p, final String parentCategoryPath_p, final Context context_p, final VariableType variableType_p,
      final boolean keepExistingValues_p) {
    final AbstractVariable[] tab = new AbstractVariable[1];
    AbstractMigration migration = new AbstractMigration() {

      @Override
      protected IStatus doMigrate() {
        AbstractVariable variable = createVariable(parentCategoryPath_p, variableName_p, variableValues_p, variableType_p, keepExistingValues_p, context_p);
        if (null != variable) {
          tab[0] = variable;
          return Status.OK_STATUS;
        }
        return Status.CANCEL_STATUS;
      }
    };
    IStatus result = migration.migrate();
    if (Status.OK_STATUS == result) {
      Variable variable = (Variable) tab[0];
      variable.setFinal(isFinal_p);
      variable.setMandatory(isMandatory_p);
      variable.setDescription(variableDescription_p);

      return variable;
    }
    return null;
  }

  /**
   * Delete the given admin context.
   * @param adminContextName_p name of the admin context to delete.
   * @throws Exception
   */
  protected void deleteAdminContext(String adminContextName_p) throws Exception {
    deleteAdminContexts(Collections.singletonList(adminContextName_p));
  }

  /**
   * Delete the given admin contexts.
   * @param adminContextNames_p names of the admin context to delete.
   * @throws Exception
   */
  protected void deleteAdminContexts(Collection<String> adminContextNames_p) throws Exception {
    // Switch to admin mode to select the admin context.
    changeModeTo(true);
    // Get context and context location for each given adminContextNames.
    ArrayList<Context> contextsToDelete = new ArrayList<Context>(adminContextNames_p.size());
    ArrayList<String> contextProjectLocations = new ArrayList<String>(adminContextNames_p.size());
    for (String contextName : adminContextNames_p) {
      Couple<RootContextsProject, Context> adminContextCouple = findContextForName(contextName, false);
      Assert.assertNotNull("Context '" + contextName + "' can't be found.", adminContextCouple);
      boolean isCurrentContext = ModelHandlerActivator.getDefault().getDataHandler().isCurrentContext(adminContextCouple.getValue());
      Assert.assertFalse("Context '" + contextName + "' is the current context, it can't be deleted.", isCurrentContext);
      contextsToDelete.add(adminContextCouple.getValue());
      contextProjectLocations.add(adminContextCouple.getKey().getLocation());
    }
    // Fully delete the contexts.
    // Sleep 1 second to avoid concurrent accesses to resources.
    sleep(1);
    DeleteContextServiceCommand deleteCommand = new DeleteContextServiceCommand(contextsToDelete, true);
    boolean canExecuteDelete = deleteCommand.canExecute();
    Assert.assertTrue("Expecting to be able to delete contexts created in before().", canExecuteDelete);
    if (canExecuteDelete) {
      deleteCommand.execute();
    }
    // Sometimes, when tests are run under Jenkins, context deletion fails (project's directory isn't deleted) -> redo a deletion to be sure.
    for (String contextProjectLocation : contextProjectLocations) {
      final File file = new File(contextProjectLocation);
      if (null != file && file.exists()) {
        ICondition deletionTask = new DefaultCondition() {
          public String getFailureMessage() {
            return "Deletion failed for " + file.getAbsolutePath();
          }

          public boolean test() throws Exception {
            FileUtils.forceDelete(file);
            return !file.exists();
          }
        };
        getBot().waitUntil(deletionTask, 10000, 1000);
      }
      // getBot().waitUntil(getConditionFactory().createFileNoLongerExistsCondition(contextProjectLocation), CONDITION_TIMEOUT, CONDITION_INTERVAL);
      Assert.assertFalse("Folder " + contextProjectLocation + " is expected to be deleted.", new File(contextProjectLocation).exists());
    }
    // Check contexts have been truly deleted from disk.
    // for (String contextProjectLocation : contextProjectLocations) {
    // getBot().waitUntil(getConditionFactory().createFileNoLongerExistsCondition(contextProjectLocation), CONDITION_TIMEOUT, CONDITION_INTERVAL);
    // Assert.assertFalse("Folder " + contextProjectLocation + " is expected to be deleted.", new File(contextProjectLocation).exists());
    // }
  }

  /**
   * Expand a node but with a {@link CategoryRenamedCondition} on the result to ensure the success of the operation
   * @param tree_p The tree containing the node to expand
   * @param nodes_p The list of nodes, from the first (context) to the last (the one to expand)
   * @return The expanded node, may be null if the expand has failed
   */
  protected SWTBotTreeItem expandNodeWithCondition(SWTBotTree tree_p, String... nodes_p) {
    // Expand the element
    SWTBotTreeItem result = tree_p.expandNode(nodes_p);
    // Wait until the element has been correctly expanded
    getBot().waitUntil(getConditionFactory().createNodeIsExpandedCondition(result), CONDITION_TIMEOUT, CONDITION_INTERVAL);
    return result;
  }

  /**
   * Find context in workspace for specified context name.
   * @param contextName_p Either an administrator context name or a user participation name.
   * @param loadOnDemand_p Load the asked context if not already loaded.
   * @return <code>null</code> if no context could be found.
   * @throws Exception
   */
  protected Couple<RootContextsProject, Context> findContextForName(String contextName_p, boolean loadOnDemand_p) throws Exception {
    return findContextForName(contextName_p, null, loadOnDemand_p);
  }

  /**
   * For context within specified location subtree for specified name.
   * @param contextName_p Either an administrator context name or a user participation name.
   * @param loadOnDemand_p Load the asked context in the resource set if it is not already loaded (set it to true if a location is specified).
   * @param location_p A remote location (that is outside the workspace, workspace included), or <code>null</code> to point to the workspace.
   * @return <code>null</code> if no context could be found.
   * @throws Exception
   */
  protected Couple<RootContextsProject, Context> findContextForName(String contextName_p, String location_p, boolean loadOnDemand_p) throws Exception {
    Assert.assertNotNull("Can not look for a null context !", contextName_p);
    Collection<RootContextsProject> projects = null;
    if (null == location_p) {
      projects = ProjectActivator.getInstance().getProjectHandler().getAllProjectsInWorkspace();
    } else {
      projects = ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(location_p);
    }
    ResourceSet resourceSet = null;
    if (null == location_p) {
      resourceSet = ModelHandlerActivator.getDefault().getEditingDomain().getResourceSet();
    } else {
      resourceSet = new CaseUnsensitiveResourceSetImpl();
    }
    // Search for newly created project.
    for (RootContextsProject rootContextsProject : projects) {
      ContextReference contextReference = rootContextsProject.getContext();
      // Skip this one.
      if (null == contextReference) {
        continue;
      }
      // Load context to get its name (needed for participations).
      String name = null;
      Resource resource = resourceSet.getResource(URI.createURI(contextReference.getUri()), loadOnDemand_p);
      Context context = ModelUtil.getContext(resource);
      if (null != context) {
        name = context.getName();
      }
      // Test names.
      if (contextName_p.equals(name)) {
        return new Couple<RootContextsProject, Context>(rootContextsProject, context);
      }
    }
    return null;
  }

  /**
   * Get administrator context label in {@link ContextsViewer} tree from its name.
   * @param administratorContextName_p A not <code>null</code> context name.
   * @return <code>null</code> if parameter is not valid.
   */
  protected String getAdministratorContextLabelInTree(String administratorContextName_p) {
    if (null == administratorContextName_p) {
      return null;
    }
    return administratorContextName_p + " [Default Context]";
  }

  /**
   * Get condition factory.
   * @return
   */
  protected ConditionFactory getConditionFactory() {
    if (_conditionFactory == null) {
      _conditionFactory = new ConditionFactory();
    }
    return _conditionFactory;
  }

  /**
   * Get the context display name as seen in orchestra
   * @param contextName_p
   * @param contextParentName_p May be null if no parent context
   * @return The context display name
   */
  protected String getContextLabelInDeleteConfirmationTree(String contextName_p, String contextParentName_p) {
    if (null == contextName_p) {
      return null;
    }
    if ((null == contextParentName_p) || contextParentName_p.isEmpty()) {
      return contextName_p + " [Default Context]";
    }
    return contextName_p + " [" + contextParentName_p + "]";
  }

  /**
   * Get the context tree
   * @return
   */
  public SWTBotTree getContextsTree() {
    SWTBotView variablesViewPart = getBot().viewById(VIEW_ID_VARIABLES);
    return variablesViewPart.bot().treeWithLabel(com.thalesgroup.orchestra.framework.ui.viewer.Messages.ContextsViewer_SectionTitle);
  }

  /**
   * Get current administrator context label in {@link ContextsViewer} tree from its name.
   * @param administratorContextName_p A not <code>null</code> context name.
   * @return <code>null</code> if parameter is not valid.
   */
  protected String getCurrentAdministratorContextLabelInTree(String administratorContextName_p) {
    if (null == administratorContextName_p) {
      return null;
    }
    String administratorContextLabelInTree = getAdministratorContextLabelInTree(administratorContextName_p);
    return administratorContextLabelInTree + " (Current)";
  }

  /**
   * Get current participation label in {@link ContextsViewer} tree from its name and its parent administrator context name.
   * @param participationName_p A not <code>null</code> participation name.
   * @param administratorContextName_p <code>null</code> for DefaultContext.
   * @return <code>null</code> if parameters are not valid.
   */
  protected String getCurrentParticipationLabelInTree(String participationName_p, String administratorContextName_p) {
    if (null == participationName_p) {
      return null;
    }
    String participationLabelInTree = getParticipationLabelInTree(participationName_p, administratorContextName_p);
    return participationLabelInTree + " (Current)";
  }

  /**
   * Get new dedicated temporary folder for test to write data.<br>
   * Returned path is always created so that it is a valid folder at call time.
   * @return
   */
  protected String getNewDedicatedTemporaryFolder() {
    String rootPath = getSharedTemporaryFolder();
    String result = rootPath;
    File resultingFolder = new File(result);
    int i = 0;
    while (resultingFolder.exists()) {
      result = rootPath + File.separator + getClass().getSimpleName() + ++i;
      resultingFolder = new File(result);
      if (!resultingFolder.exists()) {
        resultingFolder.mkdirs();
        break;
      }
    }
    return result;
  }

  /**
   * Get participation label in {@link ContextsViewer} tree from its name and its parent administrator context name.
   * @param participationName_p A not <code>null</code> participation name.
   * @param administratorContextName_p <code>null</code> for DefaultContext.
   * @return <code>null</code> if parameters are not valid.
   */
  protected String getParticipationLabelInTree(String participationName_p, String administratorContextName_p) {
    if (null == participationName_p) {
      return null;
    }
    return participationName_p + " [participation in " + ((null == administratorContextName_p) ? "Default Context" : administratorContextName_p) + "]";
  }

  /**
   * Get the parent directory of a context from its name (load it if needed).
   * @param contextName_p name of the context
   * @return the parent directory of the context (i.e : without the directory of the context).
   * @throws Exception
   */
  protected String getProjectParentDirectoryFromContextName(String contextName_p) throws Exception {
    Couple<RootContextsProject, Context> foundCouple = findContextForName(contextName_p, true);
    if (null == foundCouple) {
      return null;
    }
    RootContextsProject rootContextsProject = foundCouple.getKey();
    return new Path(rootContextsProject.getLocation()).removeLastSegments(1).toString();
  }

  public SWTBotTree getVariablesTree() {
    SWTBotView variablesViewPart = getBot().viewById(VIEW_ID_VARIABLES);
    return variablesViewPart.bot().treeWithLabel(com.thalesgroup.orchestra.framework.ui.viewer.Messages.VariablesViewer_SectionTitle);
  }

  /**
   * Import all contexts at specified location into the Framework workspace (as references).
   * @param projectsRootLocation_p
   * @return
   */
  protected static IStatus importContexts(String projectsRootLocation_p) {
    // Normally, one would have to import it through the AbstractMigration implementation.
    // But this relies on accessing the machine installation properly, which is not possible in the continuous build process.
    return ModelHandlerActivator.getDefault().getDataHandler()
        .importContexts(ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(projectsRootLocation_p), false);
  }

  /**
   * Initialize test.<br>
   * Will call getBot() which will clean the temporary test folder on first call (singleton instantiation) <br>
   * Default implementation makes sure variables view is having the focus.
   * @throws Exception
   */
  protected void initializeTest() throws Exception {
    final SWTBotView viewById = getBot().viewById(VIEW_ID_VARIABLES);
    viewById.setFocus();
  }

  /**
   * Get Odm current mode.
   * @return <code>true</code> if ODM is in Admin mode, <code>false</code> if ODM is in User mode.
   */
  protected boolean isOdmInAdministratorMode() {
    return getBot().menu(com.thalesgroup.orchestra.framework.application.Messages.PapeeteApplicationActionBarAdvisor_Menu_Text_Mode)
        .menu(com.thalesgroup.orchestra.framework.ui.action.Messages.SetModeAdministratorAction_Action_Text).isChecked();
  }

  /**
   * Retrieve a CheckBox based on its label
   * @param label_p The label to search
   * @return The SWTBotCheckBox associated to the label, else null
   */
  @SuppressWarnings("unchecked")
  protected SWTBotRadio RadioWithLabel(final String label_p) {
    if ((null == label_p) || ICommonConstants.EMPTY_STRING.equals(label_p)) {
      return null;
    }
    Matcher<? extends Button> matcher = AnyOf.anyOf(WidgetOfType.widgetOfType(Button.class));
    final List<? extends Button> buttons = getBot().widgets(matcher);
    for (Button button : buttons) {
      SWTBotRadio swtBotRadio = null;
      try {
        swtBotRadio = new SWTBotRadio(button);
      } catch (Exception exception_p) {
        // Silent one.
        // The only way to know if it is a radio is to try to do a new.
        continue;
      }
      if (label_p.equals(swtBotRadio.getText())) {
        return swtBotRadio;
      }
    }
    return null;
  }

  /**
   * Set DefaultContext as current context in ODM.
   */
  public void setDefaultContextAsCurrent() {
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    dataHandler.setCurrentContext(dataHandler.getDefaultContext());
  }

  /**
   * Set the value of a SWTBotTableItem
   * @param item_p The Table item to set
   * @param value_p The value to set
   */
  protected void setTableItemValue(SWTBotTable table_p, SWTBotTableItem item_p, String value_p) {
    SWTBot bot = new SWTBot(table_p.widget);
    item_p.select().click();
    SWTBotText text = bot.text();
    text.setText(value_p);
  }

  /**
   * Sleep for specified seconds.
   * @param timeToSleep_p
   */
  public void sleep(float timeToSleep_p) {
    getBot().sleep((long) (1000 * timeToSleep_p));
  }

  /**
   * Get Bot.
   * @return
   */
  public static SWTWorkbenchBot getBot() {
    if (null == __bot) {
      __bot = new SWTWorkbenchBot();
    }
    return __bot;
  }

  /**
   * Get shared root temporary folder for tests.<br>
   * This ensures that returned path always exists.
   * @return
   */
  protected static String getSharedTemporaryFolder() {
    String folderPath = System.getProperty("java.io.tmpdir") + "SwtBotUiTests";
    File folder = new File(folderPath);
    if (!folder.exists()) {
      folder.mkdirs();
    }
    return folderPath;
  }

  /**
   * Get new dedicated temporary folder for test to write data.<br>
   * Returned path is always created so that it is a valid folder at call time.
   * @return
   */
  protected static String getStaticNewDedicatedTemporaryFolder(Class localClass) {
    String rootPath = getSharedTemporaryFolder();
    String result = rootPath;
    File resultingFolder = new File(result);
    int i = 0;
    while (resultingFolder.exists()) {
      result = rootPath + File.separator + localClass.getSimpleName() + ++i;
      resultingFolder = new File(result);
      if (!resultingFolder.exists()) {
        resultingFolder.mkdirs();
        break;
      }
    }
    return result;
  }
}