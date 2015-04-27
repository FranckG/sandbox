package com.thalesgroup.orchestra.papeete;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.puci.PUCI;

@SuppressWarnings("nls")
public class PUCITestEdit extends PUCITestCommon {

  @Test
  public final void testEditNominal() {
    fail("Not yet implemented"); // TODO
    // createTestFile(jTestDir, "Test.cpp");
    //
    // OrchestraURI uri = new OrchestraURI("Notepad", jTestDir.getName() + "/Test");
    // String[] result = PUCI.edit(uri, "Word", true, "");
    //
    // assertEquals("200", result[0]);
    // File f = new File(result[1]);
    // assertTrue(f.exists());
  }

  @Test
  public final void testEditPhysicalPath() {
    fail("Not yet implemented"); // TODO
    // File testFile = createTestFile(jTestDir, "Test.cpp");
    //
    // String[] result = PUCI.Edit("Notepad", "", testFile.getAbsolutePath(), "", "", "", "Word", "true", "");
    //
    // assertEquals("200", result[0]);
    // File f = new File(result[1]);
    // assertTrue(f.exists());
  }

  @Test
  public final void testEditUnknownProject() {
    fail("Not yet implemented"); // TODO
    // File testFile = new File(artefactDataDir.getAbsoluteFile() + File.separator + "TestUnknown.cpp");
    //
    // String[] result = PUCI.Edit("Notepad", "", "TestUnknown", "", "", "", "ArtifactExplorer", "true", "");
    // String[] expected = { "404", "\" \'TestUnknown.cpp\' not found in : - " + testFile.getAbsolutePath() + "\"", "" };
    // assertArrayEquals(expected, result);
  }

  @Test
  public final void testEditWithParam() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testEditWrongTool() {
    fail("Not yet implemented"); // TODO
    // File testFile = createTestFile(jTestDir, "Test.cpp");
    //
    // String[] result = PUCI.Edit("Word", "", jTestDir.getName() + "/Test", "", "", "", "Notepad", "true", "");
    // String[] expected = { "404", "\" \'" + jTestDir.getName() + "/Test.doc\' not found in : - " + testFile.getParent() + File.separator + "Test.doc\"", "" };
    // assertArrayEquals(expected, result);
  }

  @Test
  public final void testTooMuchURIs() {
    // File testFile = createTestFile(jTestDir, "Test.cpp");

    List<OrchestraURI> urisList = new ArrayList<OrchestraURI>(20000);

    for (int i = 0; i < 20000; i++) {
      OrchestraURI uri = new OrchestraURI("Text", "Test" + i);
      uri.addParameter("Param" + i, Integer.toString(i));
      urisList.add(uri);
    }

    try {
      PUCI.navigate(urisList);
    } catch (Exception exception_p) {
      fail("Exception raised:" + exception_p.getLocalizedMessage());
    }

    // String[] result = PUCI.Edit("Word", "", jTestDir.getName() + "/Test", "", "", "", "Notepad", "true", "");
    // String[] expected = { "404", "\" \'" + jTestDir.getName() + "/Test.doc\' not found in : - " + testFile.getParent() + File.separator + "Test.doc\"", "" };
    // assertArrayEquals(expected, result);
  }
}