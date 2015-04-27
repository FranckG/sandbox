/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.test;

import com.thalesgroup.orchestra.papeete.environment.core.EnvironmentVariableLoaderValidate;
import com.thalesgroup.themis.papeete.server.config.ConfDirHelperTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author s0011584
 */
public class AllTests {
  public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.thalesgroup.orchestra.framework.test"); //$NON-NLS-1$
    // $JUnit-BEGIN$
    suite.addTestSuite(ConfDirHelperTest.class);
    suite.addTestSuite(EnvironmentVariableLoaderValidate.class);
    // $JUnit-END$
    return suite;
  }
}