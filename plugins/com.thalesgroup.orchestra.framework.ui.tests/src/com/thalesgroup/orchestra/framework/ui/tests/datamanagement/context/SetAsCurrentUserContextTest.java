/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thalesgroup.orchestra.framework.ui.tests.AbstractTest;
import com.thalesgroup.orchestra.framework.ui.tests.SWTBotTestHelper;

import junit.framework.Assert;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
@RunWith(SWTBotJunit4ClassRunner.class)
public class SetAsCurrentUserContextTest extends AbstractContextTest {
  /**
   * Initialization : create an admin context and import it. <br>
   * Test steps: <br>
   * 1- Set the admin context as current, <br>
   * 2- Check the current context is displayed in the tree with "(Current)" at the end, <br>
   * 3- Check that window's title ends with the current context name, <br>
   * 4- Check that the tooltip of the ODM tray item show the current context name.
   * @throws Exception
   */
  @Test
  public void canSetAsCurrentUserContext() throws Exception {
    /*
     * Initialization.
     */
    // Create an admin context and import it.
    final String adminContextName = "AdminContext";
    final String userContextName = "UserContextToSetAsCurrent";
    final String contextProjectLocation = createContextProject(getSharedTemporaryFolder(), null, adminContextName, userContextName, false);
    // Wait until admin context is created.
    getBot().waitUntil(getConditionFactory().createContextIsCreatedCondition(contextProjectLocation, 1), AbstractTest.CONDITION_TIMEOUT,
        AbstractTest.CONDITION_INTERVAL);
    importContexts(contextProjectLocation);
    /*
     * UI tests.
     */
    initializeTest();
    changeModeTo(false);
    // Find context viewer.
    SWTBotTree contextsTree = getContextsTree();
    // Set user context as current one.
    contextsTree.select(getParticipationLabelInTree(userContextName, adminContextName));
    contextsTree.contextMenu(com.thalesgroup.orchestra.framework.ui.action.Messages.SetAsCurrentContextAction_setAsCurrentContext).click();
    // Wait for progress shell to disappear.
    getBot().waitUntil(getConditionFactory().createPlatformShellIsActiveCondition(), CONDITION_TIMEOUT, CONDITION_INTERVAL);
    sleep(1);
    // Check current context display in contexts tree.
    Assert.assertTrue("Expecting to find \"(Current)\" at the end of the current context name.",
        SWTBotTestHelper.areNodesDisplayedInTree(contextsTree, getCurrentParticipationLabelInTree(userContextName, adminContextName)));
    // Check shell's title.
    Assert.assertTrue("Expecting to find current context name at the end of the main window's title.",
        getBot().activeShell().getText().endsWith(userContextName));
    // Check tray item tooltip.
    Assert.assertTrue("Expecting to find the current context name at the end of the systray tooltip.",
        getBot().trayItem().getToolTipText().endsWith(userContextName));
    /*
     * Clean up.
     */
    // DON'T FORGET TO SET ANOTHER CONTEXT AS CURRENT TO DELETE THE CREATED CONTEXT.
    setDefaultContextAsCurrent();
    deleteAdminContext(adminContextName);
  }
}
