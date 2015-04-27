/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common;

import com.thalesgroup.orchestra.framework.common.helper.ProjectHelperTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author s0011584
 */
@SuppressWarnings("nls")
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.thalesgroup.orchestra.framework.common");
    // $JUnit-BEGIN$
    suite.addTestSuite(ProjectHelperTest.class);
    // $JUnit-END$
    return suite;
  }

}
