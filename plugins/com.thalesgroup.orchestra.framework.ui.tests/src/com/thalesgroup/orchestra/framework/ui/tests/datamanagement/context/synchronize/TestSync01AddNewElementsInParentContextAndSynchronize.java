/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.synchronize;

import org.junit.BeforeClass;
import org.junit.Test;

import com.thalesgroup.orchestra.framework.ui.tests.datamanagement.context.SynchronizeContextTests;

/**
 * @author t0122040
 */
public class TestSync01AddNewElementsInParentContextAndSynchronize extends SynchronizeContextTests {

  @BeforeClass
  public static void init() throws Exception {
    createTestsContexts();
  }

  @SuppressWarnings("all")
  @Test
  public void test() throws Exception {
    testAddNewElementsInParentContextAndSynchronize();
  }

}
