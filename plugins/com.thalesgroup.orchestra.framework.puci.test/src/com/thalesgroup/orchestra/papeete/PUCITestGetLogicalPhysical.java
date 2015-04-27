package com.thalesgroup.orchestra.papeete;

import static org.junit.Assert.fail;

import org.junit.Test;

@SuppressWarnings("nls")
public class PUCITestGetLogicalPhysical extends PUCITestCommon {

  @Test
  public final void testGetLogicalProjectNameFromPhysicalPath() {
    fail("Not yet implemented"); // TODO
    // File testFile = createTestFile(jTestDir, "Test.cpp");
    //
    // String[] expected = { "200", testFile.getParentFile().getName() + "/Test#null#Notepad#null", "" };
    // Map<String, String> result = PUCINextGeneration.getLogicalName(testFile.getAbsolutePath());
    // // assertArrayEquals(expected, result);
  }

  @Test
  public final void testGetPhysicalPathFromLogicalProjectName() {
    fail("Not yet implemented"); // TODO
    // File testFile = createTestFile(jTestDir, "Test.cpp");
    // OrchestraURI uri = new OrchestraURI("Notepad", testFile.getParentFile().getName() + "/Test");
    // String[] expected = { "200", testFile.getAbsolutePath(), "" };
    // Map<String, String> result = PUCINextGeneration.getPhysicalPath(uri);// ("Notepad", testFile.getParentFile().getName() + "/Test");
    // // assertArrayEquals(expected, result);
  }

}
