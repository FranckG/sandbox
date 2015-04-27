/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.tests;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.tests.internal.model.AbstractModelTest;

import junit.framework.Assert;

/**
 * @author s0011584
 */
public class WorkspaceManagementTest extends AbstractModelTest {
  public void testOpenAContextResourceFromWorkspace() {
    Context context = ModelHandlerActivator.getDefault().getDataHandler().getContext("/THAV/THAV.contexts"); //$NON-NLS-1$
    Assert.assertNotNull(context);
    Assert.assertEquals("Ensure context name is THAV", "THAV", context.getName()); //$NON-NLS-1$ //$NON-NLS-2$
    Context superContext = context.getSuperContext();
    Assert.assertEquals("Ensure super context name is DAE", "DAE", superContext.getName()); //$NON-NLS-1$ //$NON-NLS-2$
    Assert.assertEquals("Ensure super super context name is the default Orchestra.", "Default Context", superContext.getSuperContext().getName()); //$NON-NLS-1$ //$NON-NLS-2$
  }
}