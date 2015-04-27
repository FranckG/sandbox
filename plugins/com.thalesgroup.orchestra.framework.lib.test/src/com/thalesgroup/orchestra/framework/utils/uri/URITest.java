/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.utils.uri;

import java.util.TreeMap;

import com.thalesgroup.orchestra.framework.lib.utils.uri.BadOrchestraURIException;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

import junit.framework.TestCase;

/**
 * @author s0024585
 */
public class URITest extends TestCase {

  @SuppressWarnings("nls")
  public void testCompleteURIFromString() {
    OrchestraURI uri = null;
    String uriString = "orchestra://Ccucm/Doors%2FConnector%20+or%2fODM/Baseline/Virtual_Latest_Baseline_DoorsConnector?Promotion+level=RELEASED";
    try {
      uri = new OrchestraURI(uriString);
    } catch (BadOrchestraURIException exception) {
      System.out.println(exception.getMessage());
      uri = null;
    }

    assertNotNull(uri);
    assertEquals("Ccucm", uri.getRootType());
    assertEquals("Doors/Connector +or/ODM", uri.getRootName());
    assertEquals("Baseline", uri.getObjectType());
    assertEquals("Virtual_Latest_Baseline_DoorsConnector", uri.getObjectId());
    TreeMap<String, String> map = new TreeMap<String, String>();
    map.put("Promotion+level", "RELEASED");
    assertEquals(map, uri.getParameters());
    assertEquals(uriString, uri.getUri());
  }

  @SuppressWarnings("nls")
  public void testEquality() {
    OrchestraURI uri1;
    try {
      uri1 = new OrchestraURI("Type de mon root à moi", "Root Name/Name du fichier", "Vue Doors", "ObjectId");
      uri1.addParameter("test d'es?pace", "c:/path/d=e/fichier");
      uri1.addParameter("test&decaractèresmoisis", "=en&lk");
    } catch (BadOrchestraURIException exception_p) {
      uri1 = null;
    }

    OrchestraURI uri2;
    try {
      uri2 = new OrchestraURI("Type de mon root à moi", "Root Name/Name du fichier", "Vue Doors", "ObjectId");
      uri2.addParameter("test d'es?pace", "c:/path/d=e/fichier");
      uri2.addParameter("test&decaractèresmoisis", "=en&lk");
    } catch (BadOrchestraURIException exception_p) {
      uri2 = null;
    }

    assertNotNull(uri1);
    assertNotNull(uri2);
    assertTrue(uri1.equals(uri2));

    OrchestraURI uri3;
    try {
      uri3 = new OrchestraURI("Type de mon root à moi", "Root Name/Name du fichier", "Vue Doors", "ObjectId changé");
    } catch (BadOrchestraURIException exception_p) {
      uri3 = null;
    }
    uri3.addParameter("test d'es?pace", "c:/path/d=e/fichier");
    uri3.addParameter("test&decaractèresmoisis", "=en&lk");

    assertNotNull(uri3);
    assertFalse(uri1.equals(uri3));
  }

  @SuppressWarnings("nls")
  public void testExceptions() {
    try {
      @SuppressWarnings("unused")
      OrchestraURI uriKO =
          new OrchestraURI("orchestra://Ccucm/Doors%2FConnector%20+or%2fODM/Baseline/Toto/tutu/Virtual_Latest_Baseline_DoorsConnector?Promotion+level=RELEASED");
      assertFalse("No exception raised!", true);
    } catch (BadOrchestraURIException e) {
      assertEquals("The uri form is not conform to the one expected (too many segments).", e.getMessage());
    }

    try {
      @SuppressWarnings("unused")
      OrchestraURI uriKO =
          new OrchestraURI("orchestra:///Doors%2FConnector%20+or%2fODM/Baseline/Virtual_Latest_Baseline_DoorsConnector?Promotion+level=RELEASED");
      assertFalse("No exception raised!", true);
    } catch (BadOrchestraURIException e) {
      assertEquals("Root Type is missing.", e.getMessage());
    }

    try {
      @SuppressWarnings("unused")
      OrchestraURI uriKO =
          new OrchestraURI("MyProtocol:///Doors%2FConnector%20+or%2fODM/Baseline/Virtual_Latest_Baseline_DoorsConnector?Promotion+level=RELEASED");
      assertFalse("No exception raised!", true);
    } catch (BadOrchestraURIException e) {
      assertEquals("Wrong scheme value: it must be 'orchestra'. Instead, it's 'MyProtocol'.", e.getMessage());
    }

    try {
      @SuppressWarnings("unused")
      OrchestraURI uriKO = new OrchestraURI("RootType", "RootName", "", "objectId");
      assertFalse("No exception raised!", true);
    } catch (BadOrchestraURIException e) {
      assertEquals("Inconsistency: object type and object id should both be empty or not", e.getMessage());
    }
  }

  @SuppressWarnings("nls")
  public void testNewFullURI() {
    OrchestraURI uri = null;

    try {
      uri = new OrchestraURI("RootType", "Root Name/Name du fichier", "ObjectType", "ObjectId");
    } catch (BadOrchestraURIException exception_p) {
      uri = null;
    }

    assertNotNull(uri);
    assertEquals("orchestra://RootType/Root%20Name%2FName%20du%20fichier/ObjectType/ObjectId", uri.getUri());
    assertNull(uri.getParameters());
  }

  @SuppressWarnings("nls")
  public void testRootURIWithParameter() {
    OrchestraURI uri = null;
    uri = new OrchestraURI("RootType", "RootName");
    uri.addParameter("test d'es?pace", "c:/path/d=e/fichier");
    uri.addParameter("test&decaractèresmoisis", "=en&lk");

    assertNotNull(uri);
    assertEquals("orchestra://RootType/RootName?test%20d%27es%3Fpace=c%3A%2Fpath%2Fd%3De%2Ffichier&test%26decaract%C3%A8resmoisis=%3Den%26lk", uri.getUri());
  }

  @SuppressWarnings("nls")
  public void testRootWithParameterFromString() {
    OrchestraURI uri = null;
    String uriString = "orchestra://Ccucm/DoorsConnector%20f%2for+ODM?Promotion+level=RELEASED";
    try {
      uri = new OrchestraURI(uriString);
    } catch (BadOrchestraURIException exception) {
      System.out.println(exception.getMessage());
      uri = null;
    }

    assertNotNull(uri);
    assertEquals("Ccucm", uri.getRootType());
    assertEquals("DoorsConnector f/or+ODM", uri.getRootName());
    assertNull(uri.getObjectType());
    assertNull(uri.getObjectId());
    TreeMap<String, String> map = new TreeMap<String, String>();
    map.put("Promotion+level", "RELEASED");
    assertEquals(map, uri.getParameters());
    assertEquals(uriString, uri.getUri());
  }
}
