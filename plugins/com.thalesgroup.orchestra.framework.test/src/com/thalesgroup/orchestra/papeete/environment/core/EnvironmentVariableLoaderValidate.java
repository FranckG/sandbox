/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.papeete.environment.core;

import java.io.File;
import java.net.URISyntaxException;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.lib.base.utils.FileUtils;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author s0011584
 * @deprecated should be a {@link FileUtils} test.
 */
public class EnvironmentVariableLoaderValidate extends TestCase {
  private String _root = "com.thalesgroup.orchestra.framework.test/EnvironmentVariableLoaderTest/";

  public void testIsFileNameValid() throws URISyntaxException {
    File parent = new File(FileHelper.getFileFullUrl(_root).toURI());
    // Nominal cases.
    Assert.assertTrue("Should accept word characters for folder.", FileUtils.isFilenameValidToBeCreated(parent.getAbsolutePath() + File.separator + "valid")); //$NON-NLS-1$ //$NON-NLS-2$
    Assert.assertFalse("Tested directory should not have been created.", new File(parent.getAbsolutePath() + File.separator + "valid").exists()); //$NON-NLS-1$ //$NON-NLS-2$
    Assert.assertTrue("Should accept word characters for file.", FileUtils.isFilenameValidToBeCreated(parent.getAbsolutePath() + File.separator + "valid.txt")); //$NON-NLS-1$ //$NON-NLS-2$
    Assert.assertFalse("Tested file should not have been created.", new File(parent.getAbsolutePath() + File.separator + "valid.txt").exists()); //$NON-NLS-1$ //$NON-NLS-2$
    // Illegal characters.
    if (System.getProperty("os.name").contains("indows")) { //$NON-NLS-1$//$NON-NLS-2$
      Assert.assertFalse(
          "Should not accept illegal win32 characters.", FileUtils.isFilenameValidToBeCreated(parent.getAbsolutePath() + File.separator + "\\/:*?\"<>|")); //$NON-NLS-1$ //$NON-NLS-2$
      Assert.assertFalse("Tested file should not have been created.", new File(parent.getAbsolutePath() + File.separator + "\\/:*?\"<>|").exists()); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }

  // Test remove since changing read/write permission on directories need ACL to work now
  //
  // public void testIsFileNameValidInAReadOnlyFolder() throws URISyntaxException {
  // File parent = new File(FileHelper.getFileFullUrl(_root).toURI());
  //    parent = new File(parent, "readOnlyFolder"); //$NON-NLS-1$
  // parent.setReadOnly();
  // // Nominal cases.
  //    Assert.assertFalse("Expecting to not being able to create anything in the read-only folder.", //$NON-NLS-1$
  // parent.canWrite());
  // Assert.assertFalse(
  //        "Should not accept a folder in a read-only folder.", FileUtils.isFilenameValidToBeCreated(parent.getAbsolutePath() + File.separator + "valid")); //$NON-NLS-1$ //$NON-NLS-2$
  //    Assert.assertFalse("Tested directory should not have been created.", new File(parent.getAbsolutePath() + File.separator + "valid").exists()); //$NON-NLS-1$ //$NON-NLS-2$
  // Assert.assertFalse(
  //        "Should not accept a file in a read-only folder.", FileUtils.isFilenameValidToBeCreated(parent.getAbsolutePath() + File.separator + "valid.txt")); //$NON-NLS-1$ //$NON-NLS-2$
  //    Assert.assertFalse("Tested file should not have been created.", new File(parent.getAbsolutePath() + File.separator + "valid.txt").exists()); //$NON-NLS-1$ //$NON-NLS-2$
  // // Illegal characters.
  //    if (System.getProperty("os.name").contains("indows")) { //$NON-NLS-1$//$NON-NLS-2$
  // Assert.assertFalse(
  //          "Should not accept anyhting in read-only folder.", FileUtils.isFilenameValidToBeCreated(parent.getAbsolutePath() + File.separator + "\\/:*?\"<>|")); //$NON-NLS-1$ //$NON-NLS-2$
  //      Assert.assertFalse("Tested file should not have been created.", new File(parent.getAbsolutePath() + File.separator + "\\/:*?\"<>|").exists()); //$NON-NLS-1$ //$NON-NLS-2$
  // }
  // }

  public void testValidateWithAnExistingDirectoryWithAccents() throws URISyntaxException {
    String directoryWithAccents = new File(FileHelper.getFileFullUrl(_root + "directoryWithAccentsÈ‡Ë˘ÔÓ").toURI()).getAbsolutePath(); //$NON-NLS-1$
    IStatus validationResult = FileUtils.validate("withAccents", directoryWithAccents, null); //$NON-NLS-1$
    Assert.assertNotNull(validationResult.getMessage());
    Assert.assertEquals(0, validationResult.getChildren().length);
  }

  public void testValidateWithANominalExistingDirectory() throws URISyntaxException {
    String nominalDirectory = new File(FileHelper.getFileFullUrl(_root + "nominalDirectory").toURI()).getAbsolutePath(); //$NON-NLS-1$
    IStatus validationResult = FileUtils.validate("nominalDirectory", nominalDirectory, null); //$NON-NLS-1$
    Assert.assertNotNull(validationResult.getMessage());
    Assert.assertEquals(0, validationResult.getChildren().length);
  }

  public void testValidateWithANonExistingDirectory() throws URISyntaxException {
    File parent = new File(FileHelper.getFileFullUrl(_root).toURI());
    File file = new File(parent, "doesNotExists"); //$NON-NLS-1$
    Assert.assertFalse(file.exists());
    String notExistingDirectory = file.getAbsolutePath();
    IStatus validationResult = FileUtils.validate("doesNotExists", notExistingDirectory, null); //$NON-NLS-1$
    Assert.assertFalse(validationResult.isOK());
    Assert.assertEquals(1, validationResult.getChildren().length);
    Assert.assertEquals("- doesNotExists ('" + notExistingDirectory + "') does not exist.\n", validationResult.getChildren()[0].getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
  }
}