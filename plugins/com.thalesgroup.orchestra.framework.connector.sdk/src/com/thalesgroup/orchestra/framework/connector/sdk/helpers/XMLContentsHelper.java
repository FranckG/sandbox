package com.thalesgroup.orchestra.framework.connector.sdk.helpers;

import java.io.StringReader;
import java.io.StringWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import com.thalesgroup.orchestra.framework.connector.sdk.operation.TypeDefinition;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
public class XMLContentsHelper {
  /**
   * Artefact description file location in connector projects.
   */
  public static final String FILES_LOCATION_ARTEFACT_DESCRIPTION = "/configuration directory/Framework/Config/";
  /**
   * Connector association file location in connector projects.
   */
  public static final String FILES_LOCATION_CONNECTOR_ASSOCIATIONS = "/configuration directory/Framework/Associations/";
  /**
   * Connector declaration file location in connector projects.
   */
  public static final String FILES_LOCATION_CONNECTOR_DECLARATION = "/deployment/connector/declaration/";

  /**
   * Generate content for artefacts desciption files.
   * @param templateArtefactsDescriptionContent_p
   * @param typeDefininitions
   * @return
   * @throws Exception
   */
  public static String createArtefactDescriptionFileContent(String templateArtefactsDescriptionContent_p, TypeDefinition[] typeDefininitions) throws Exception {
    Document xmlDocument = loadDomDocumentFromString(templateArtefactsDescriptionContent_p);
    // Create needed <artifact> elements (there is already one in the template document).
    Node firstArtifactNode = xmlDocument.getElementsByTagName("artifact").item(0);
    for (int i = 1; i < typeDefininitions.length; ++i) {
      xmlDocument.getDocumentElement().appendChild(firstArtifactNode.cloneNode(true));
    }
    // Fill <artifact> elements with type definitions.
    for (int i = 0; i < typeDefininitions.length; ++i) {
      Element artifact = (Element) xmlDocument.getElementsByTagName("artifact").item(i);
      artifact.setAttribute("tool", typeDefininitions[i].getTypeName());

      Element objectTypeElement = (Element) artifact.getElementsByTagName("objectType").item(0);
      objectTypeElement.setAttribute("iconRelativePath", typeDefininitions[i].getTypeName() + ".gif");
    }
    return toFormattedXmlString(xmlDocument);
  }

  /**
   * @param templateConnectorDeclararionContent_p
   * @param connectorId_p
   * @param typeDefininitions
   * @return
   * @throws Exception
   */
  public static String createConnectorDeclarationFileContent(String templateConnectorDeclararionContent_p, String connectorId_p,
      TypeDefinition[] typeDefininitions) throws Exception {
    Document xmlDocument = loadDomDocumentFromString(templateConnectorDeclararionContent_p);
    Element connectorElement = (Element) xmlDocument.getElementsByTagName("connector").item(0);
    connectorElement.setAttribute("connectorId", connectorId_p);

    // Create needed <type> elements (there is already one in the template document).
    Node firstType = connectorElement.getElementsByTagName("type").item(0);
    Node unsupportedServiceNode = connectorElement.getElementsByTagName("unsupportedService").item(0);
    for (int i = 1; i < typeDefininitions.length; ++i) {
      connectorElement.insertBefore(firstType.cloneNode(true), unsupportedServiceNode);
    }
    // Fill <type> elements with type definitions.
    for (int i = 0; i < typeDefininitions.length; ++i) {
      Element artifact = (Element) connectorElement.getElementsByTagName("type").item(i);
      artifact.setAttribute("name", typeDefininitions[i].getTypeName());
    }
    return toFormattedXmlString(xmlDocument);
  }

  /**
   * Generate content for type associations files.
   * @param sourceAssociationFileContent_p
   * @param typeDefininitions
   * @return
   * @throws Exception
   */
  public static String createTypeAssociationFileContent(String sourceAssociationFileContent_p, TypeDefinition[] typeDefininitions) throws Exception {
    Document xmlDocument = loadDomDocumentFromString(sourceAssociationFileContent_p);
    // Create needed <association> elements (there is already one in the template document).
    Node firstAssociationNode = xmlDocument.getElementsByTagName("association").item(0);
    for (int i = 1; i < typeDefininitions.length; ++i) {
      xmlDocument.getDocumentElement().appendChild(firstAssociationNode.cloneNode(true));
    }
    // Fill <association> elements with type definitions.
    for (int i = 0; i < typeDefininitions.length; ++i) {
      Element artifact1 = (Element) xmlDocument.getElementsByTagName("artifact1").item(i);
      Element artifact2 = (Element) xmlDocument.getElementsByTagName("artifact2").item(i);

      artifact1.setAttribute("tool", typeDefininitions[i].getTypeName());
      artifact1.getFirstChild().setNodeValue(typeDefininitions[i].getLogicalName());
      artifact2.getFirstChild().setNodeValue(typeDefininitions[i].getPhysicalName());
    }
    return toFormattedXmlString(xmlDocument);
  }

  /**
   * Generate artefacts description file path.
   * @param projectName_p
   * @param fileNameSuffix_p
   * @return
   */
  public static String generateArtefactDescriptionFilePath(String projectName_p, String fileNameSuffix_p) {
    return projectName_p + FILES_LOCATION_ARTEFACT_DESCRIPTION + "artifacts_description_" + fileNameSuffix_p + ".xml";
  }

  /**
   * Generate connector association file path.
   * @param projectName_p
   * @param fileNameCustomPart_p
   * @return
   */
  public static String generateConnectorAssociationFilePath(String projectName_p, String fileNameCustomPart_p) {
    return projectName_p + FILES_LOCATION_CONNECTOR_ASSOCIATIONS + fileNameCustomPart_p + "ConnectorAssociation.xml";

  }

  /**
   * Generate connector declaration file path.
   * @param projectName_p
   * @param fileNameSuffix_p
   * @return
   */
  public static String generateConnectorDeclarationFilePath(String projectName_p, String fileNameSuffix_p) {
    return projectName_p + FILES_LOCATION_CONNECTOR_DECLARATION + fileNameSuffix_p + ".connector";
  }

  /**
   * Load xml content from String argument in a DOM document.
   * @param sourceXmlContent_p
   * @return
   * @throws Exception
   */
  public static Document loadDomDocumentFromString(String sourceXmlContent_p) throws Exception {
    // Get DOM document builder.
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    // dbFactory.setNamespaceAware(true);
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    // Parse given xml data.
    return dBuilder.parse(new InputSource(new StringReader(sourceXmlContent_p)));
  }

  /**
   * Generate a formatted String representation of he given DOM document.
   * @param sourceXmlDocument_p
   * @return
   * @throws Exception
   */
  public static String toFormattedXmlString(Document sourceXmlDocument_p) throws Exception {
    DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
    DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
    LSSerializer writer = impl.createLSSerializer();
    // Ask writer to format generated xml file.
    writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
    LSOutput lsOutput = impl.createLSOutput();
    StringWriter stringWriter = new StringWriter();
    lsOutput.setCharacterStream(stringWriter);
    writer.write(sourceXmlDocument_p, lsOutput);
    return stringWriter.toString();
  }
}
