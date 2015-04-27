/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.migration;

import com.thalesgroup.orchestra.framework.model.migration.test.MigrationTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author s0011584
 *
 */
public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.thalesgroup.orchestra.framework.model.migration.test"); //$NON-NLS-1$
    //$JUnit-BEGIN$
    suite.addTestSuite(MigrationTest.class);
    //$JUnit-END$
    return suite;
  }

}
