package com.thalesgroup.orchestra.papeete;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;

public class PUCITestCommon {

  protected File artefactDataDir;
  protected File jTestDir;

  protected final File createTestFile(File parentDir, String fileName) {
    File testFile = new File(parentDir.getAbsoluteFile() + File.separator + fileName);
    if (!testFile.exists()) {
      try {
        testFile.createNewFile();
      } catch (IOException e) {
        fail(e.getMessage());
      }
    }
    return testFile;
  }

  private boolean deleteDir(File file) {
    return FileUtils.deleteQuietly(file);
  }

  @Before
  public void setUp() throws Exception {
    // String dataDirPath = null;
    // VariableManagerAdapter manager = VariableManagerAdapter.getInstance();
    // IVariable variable = manager.getVariable("\\Orchestra\\ArtefactPath");
    // if (variable != null) {
    // dataDirPath = variable.getValues().get(0);
    // }
    // artefactDataDir = new File(dataDirPath);
    // jTestDir = new File(artefactDataDir.getAbsolutePath() + File.separator
    // + "JTest");
    // if (!jTestDir.mkdir()) {
    // fail("Cannot create test directory " + jTestDir.getAbsolutePath());
    // }
  }

  @After
  public void tearDown() throws Exception {
    deleteDir(jTestDir);
  }
}
