package com.thalesgroup.orchestra.papeete;

import static org.junit.Assert.fail;

import org.junit.Test;

@SuppressWarnings("nls")
public class PUCITestImport extends PUCITestCommon {

  @Test
  public final void testImportNominal() {
    fail("Not yet implemented"); // TODO
    // OrchestraURI uri1 = null;
    // OrchestraURI uri2 = null;
    // try {
    // uri1 = new OrchestraURI("Excel", "BurnDownChart_2010.09.28", "Sheet", "Data");
    // } catch (BadOrchestraURIException exception_p) {
    // uri1 = null;
    // }
    // try {
    // uri2 = new OrchestraURI("Excel", "BurnDownChart_2010.09.28", "Sheet", "ExtractedData");
    // } catch (BadOrchestraURIException exception_p) {
    // uri2 = null;
    // }
    //
    // assertTrue(uri1 != null);
    // assertTrue(uri2 != null);
    // String[] result = PUCI.export(new OrchestraURI[] { uri1, uri2 }, "ArtifactExplorer", "XML_DOC", ""); // Import("Notepad", "", "Test", "", "", "",
    // // "ArtifactExplorer", "XML_DOC", "", "");
    //
    // assertEquals("200", result[0]);
    // assertEquals("", result[2]);
    // File file = new File(result[1]);
    // assertTrue(file.isFile());
  }

  @Test
  public final void testImportUnknownProject() {
    fail("Not yet implemented"); // TODO
    // String[] result = PUCI.Import("Notepad", "", "TestInconnu", "", "", "", "ArtifactExplorer", "XML_DOC", "", "");
    // String[] expected = { "404", "\" \'TestInconnu.cpp\' not found in : - " + artefactDataDir.getAbsolutePath() + File.separator + "TestInconnu.cpp\"", "" };
    // assertArrayEquals(expected, result);
  }

  @Test
  public final void testImportWithAssociationCreationCanceled() {
    // TODO Automatically reply to IHM during execution
    // Find a way to remove Notepad association at runtime...

    // String[] result = PUCI.Import("Notepad", "", "Test", "", "", "",
    // "ArtifactExplorer", "XML_DOC", "", "");
    //				
    // assertEquals("1630", result[0]);
    // assertEquals("\"Association creation canceled by user.\"",
    // result[1]);
    // assertEquals("", result[2]);
  }

  @Test
  public final void testImportWithAssociationCreationNominal() {
    // TODO Automatically reply to IHM during execution
    // Find a way to remove Notepad association at runtime...

    // String[] result = PUCI.Import("Notepad", "", "Test", "", "", "",
    // "ArtifactExplorer", "XML_DOC", "", "");
    //		
    // assertEquals("200", result[0]);
    // assertEquals("", result[2]);
    // try {
    // URI uri = new URI(result[1]);
    // File file = new File(uri);
    // assertTrue(file.isFile());
    // } catch (URISyntaxException e) {
    // fail(e.getMessage());
    // }
  }

  @Test
  public final void testImportWithParam() {
    fail("Not yet implemented"); // TODO
  }
}