/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author s0011584
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.thalesgroup.orchestra.framework.project"); //$NON-NLS-1$
    //$JUnit-BEGIN$
    suite.addTestSuite(ExportProjectTest.class);
    suite.addTestSuite(ImportProjectTest.class);
    suite.addTestSuite(NewProjectTest.class);
    suite.addTestSuite(ParticipateInContextTest.class);
    suite.addTestSuite(ProjectBrowserTest.class);
    //$JUnit-END$
    return suite;
  }

}
