/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.themis.papeete.server.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.config.ConfDirHelper;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author s0011584
 */
public class ConfDirHelperTest extends TestCase {
  private List<String> _allSourceConfDir;
  private File _tempDirectory;

  private void setTempDir() {
    String tempDir = System.getProperty("java.io.tmpdir"); //$NON-NLS-1$
    _tempDirectory = new File(tempDir + UUID.randomUUID().toString());
    _tempDirectory.deleteOnExit();
  }

  @Override
  protected void setUp() throws Exception {
    setTempDir();
    setVariableValuesWithTestConfdirs();
  }

  @SuppressWarnings("nls")
  private void setVariableValuesWithTestConfdirs() throws URISyntaxException {
    String confDir1 = new File(FileHelper.getFileFullUrl("com.thalesgroup.orchestra.framework.test/confdirs/source1").toURI()).getAbsolutePath();
    String confDir2 = new File(FileHelper.getFileFullUrl("com.thalesgroup.orchestra.framework.test/confdirs/source2").toURI()).getAbsolutePath();
    String confDir3 = new File(FileHelper.getFileFullUrl("com.thalesgroup.orchestra.framework.test/confdirs/source3").toURI()).getAbsolutePath();
    _allSourceConfDir = Arrays.asList(confDir1, confDir2, confDir3);
  }

  /**
   * Test method for
   * {@link com.thalesgroup.orchestra.framework.config.ConfDirHelper#mergeConfDir(java.util.List, com.thalesgroup.orchestra.framework.model.contexts.VariableValue)}
   * .
   */
  @SuppressWarnings("nls")
  public void testMergeConfDirInANotExistingDirectoryShouldCreateTheDirectory() {
    Assert.assertFalse("We need a unique temporary directory that does not exists yet.", _tempDirectory.exists());
    VariableValue destinationConfDir = ContextsFactory.eINSTANCE.createVariableValue();
    destinationConfDir.setValue(_tempDirectory.getAbsolutePath());
    ConfDirHelper.mergeConfDir(_allSourceConfDir, destinationConfDir);
    Assert.assertTrue("The destination directory should have been created.", _tempDirectory.exists());
  }

  @SuppressWarnings("nls")
  public void testMergeConfDirWithSucess() throws FileNotFoundException {
    VariableValue destinationConfDir = ContextsFactory.eINSTANCE.createVariableValue();
    destinationConfDir.setValue(_tempDirectory.getAbsolutePath());
    ConfDirHelper.mergeConfDir(_allSourceConfDir, destinationConfDir);
    // file.txt
    File fileTxt = new File(_tempDirectory, "file.txt");
    Assert.assertTrue("file.txt should exists.", fileTxt.exists());
    Assert.assertEquals("file.txt should be the same as source 1", FileHelper.readFile("com.thalesgroup.orchestra.framework.test/confdirs/source1/file.txt"),
        new String(FileHelper.readFile(new FileInputStream(fileTxt))));
    // conflict.txt
    File conflictTxt = new File(_tempDirectory, "conflict.txt");
    Assert.assertTrue("conflict.txt should exists.", conflictTxt.exists());
    Assert.assertEquals("conflict.txt should be the same as source 3.",
        FileHelper.readFile("com.thalesgroup.orchestra.framework.test/confdirs/source3/conflict.txt"),
        new String(FileHelper.readFile(new FileInputStream(conflictTxt))));
    // rep
    File rep = new File(_tempDirectory, "rep");
    Assert.assertTrue("rep should exists.", rep.exists());
    // rep/file.txt
    File fileInRep = new File(_tempDirectory, "rep/file.txt");
    Assert.assertTrue("rep/file.txt should exists.", fileInRep.exists());
    Assert.assertEquals("rep/file.txt should be the same as source 3.",
        FileHelper.readFile("com.thalesgroup.orchestra.framework.test/confdirs/source3/rep/file.txt"),
        new String(FileHelper.readFile(new FileInputStream(fileInRep))));
  }
}
