/**
 * <p>
 * Copyright (c) 2002 : Thales Research & Technology
 * </p>
 * <p>
 * Société : Thales Research & Technology
 * </p>
 * <p>
 * Thales Part Number 16 262 601
 * </p>
 * Merged 17/09/03
 */
package com.thalesgroup.orchestra.framework.transcription;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.lib.base.BaseErrorHandler;
import com.thalesgroup.orchestra.framework.lib.base.UtilFunction;
import com.thalesgroup.orchestra.framework.lib.base.constants.PapeeteDTD;
import com.thalesgroup.orchestra.framework.variablemanager.server.model.VariableManager;

/**
 * This class parses a file and return a AssociationMap
 * @author Orchestra Framework developer
 * @version 3.1
 */
final class AssociationXMLHandler extends DefaultHandler {
  private boolean _artifact1 = false;

  private String _artifact1Content = null;

  private String _artifact1Tool = null;

  private boolean _artifact2 = false;

  private String _artifact2Content = null;

  private String _artifact2Tool = null;

  private List<Association> _associationMap = new ArrayList<Association>();

  private boolean _balanced = false;

  private StringBuffer _content = UtilFunction.GetDefaultStringBuffer();
  private String _relational_type = null;
  private AssociationArtifact _toolArtifact1 = null;
  private AssociationArtifact _toolArtifact2 = null;
  private boolean _valid = false;
  private String _xmlFileURL = null;

  /**
   * Method AssociationXMLHandler.
   * @param iXMLFileURL The File in format Url to parse
   */
  AssociationXMLHandler(String iXMLFileURL) {
    if (!UtilFunction.IsNull_ExistedFile(iXMLFileURL)) {
      _xmlFileURL = iXMLFileURL;
    }
  }

  /**
   * Method characters. Receive notification of character data inside an element into _content if element type is an artifact.
   * @see org.xml.sax.ContentHandler#characters(char[], int, int)
   * @param iCh The characters.
   * @param iStart The start position in the character array.
   * @param iLen The number of characters to use from the character array.
   */
  @Override
  public void characters(char[] iCh, int iStart, int iLen) {
    if (_artifact1) {
      _content.append(iCh, iStart, iLen);
    } else if (_artifact2) {
      _content.append(iCh, iStart, iLen);
    }
  }

  /**
   * Method endElement. With all value, create the association with artifact1 and artifact2
   * @see org.xml.sax.ContentHandler#endElement(String, String, String)
   * @param iUri unused
   * @param iLocalName XML element's type
   * @param iQName unused
   * @see org.xml.sax.ContentHandler#endElement(String, String, String)
   */
  @Override
  public void endElement(String iUri, String iLocalName, String iQName) {
    // Association
    if (iLocalName.equals(PapeeteDTD._ASSOCIATION_ASSOCIATION)) {
      Association myAssociation = new Association(_toolArtifact1, _toolArtifact2, _relational_type, _balanced, _valid);
      _associationMap.add(myAssociation);
      // re-initialization
      _relational_type = null;
      _balanced = false;
      _valid = false;
      _artifact1Tool = null;
      _artifact1Content = null;
      _artifact2Tool = null;
      _artifact2Content = null;
      _toolArtifact1 = null;
      _toolArtifact2 = null;
    } else if (iLocalName.equals(PapeeteDTD._ASSOCIATION_ARTIFACT1)) {
      _artifact1Content = new String(_content);
      _toolArtifact1 = new AssociationArtifact(_artifact1Tool, _artifact1Content);
      _artifact1 = false;
    } else if (iLocalName.equals(PapeeteDTD._ASSOCIATION_ARTIFACT2)) {
      _artifact2Content = new String(_content);
      _toolArtifact2 = new AssociationArtifact(_artifact2Tool, _artifact2Content);
      _artifact2 = false;
    }
    _content.setLength(0);
  }

  /**
   * Method error Wraps error into SAXException with Orchestra Framework error message.
   * @see org.xml.sax.ErrorHandler#error(SAXParseException)
   * @param iSAXParseException Exception to wrap
   * @throws SAXException result of wrapping operation
   */
  @Override
  public void error(SAXParseException iSAXParseException) throws SAXException {
    String errmsg = BaseErrorHandler.GetErrorMsg("SAX_Analysis_Error"); //$NON-NLS-1$
    throw new SAXException(errmsg);
  }

  /**
   * Method fatalError Wraps fatal error into SAXException with Orchestra Framework error message.
   * @see org.xml.sax.ErrorHandler#fatalError(SAXParseException)
   * @param iSAXParseException Exception to wrap
   * @throws SAXException result of wrapping operation
   */
  @Override
  public void fatalError(SAXParseException iSAXParseException) throws SAXException {
    String errmsg = BaseErrorHandler.GetErrorMsg("SAX_Fatal_Error"); //$NON-NLS-1$
    throw new SAXException(errmsg);
  }

  /**
   * Method parse. Parses the XML File into current instance.
   */
  private IStatus parse() {
    // Precondition.
    if (null == _xmlFileURL) {
      return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), ICommonConstants.EMPTY_STRING);
    }
    try {
      XMLReader _parser = XMLReaderFactory.createXMLReader();
      _parser.setEntityResolver(new EntityResolver() {
        public InputSource resolveEntity(String publicId, String systemId) {
          try {
            VariableManager.getInstance();
            // TODO Guillaume
            // TODO Eric
            // Replace with variable in context.
            return new InputSource(new FileInputStream(
                VariableManager.getValue("\\Orchestra installation\\Products\\OrchestraFramework\\InstallLocation") + "\\dtd\\associations.dtd")); //$NON-NLS-1$ //$NON-NLS-2$
          } catch (Exception exception_p) {
            return null;
          }
        }
      });
      _parser.setErrorHandler(this);
      _parser.setContentHandler(this);
      _parser.parse(new File(_xmlFileURL).toURI().toURL().toString());
      return Status.OK_STATUS;
    } catch (Exception exception_p) {
      return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), BaseErrorHandler.GetErrorMsg("Transcriptor_DTDError", _xmlFileURL)); //$NON-NLS-1$
    }
  }

  /**
   * Method startElement. Extract from XML file to current instance's values for each attribute. This method prepares association creation.
   * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
   * @param iUri unused
   * @param iLocalName XML element's type
   * @param iQName unused
   * @param iAttributes XML element's attributes specification
   * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
   */
  @Override
  public void startElement(String iUri, String iLocalName, String iQName, Attributes iAttributes) {
    // Creation d'une association
    if (iLocalName.equals(PapeeteDTD._ASSOCIATION_ASSOCIATION)) {
      _relational_type = iAttributes.getValue(PapeeteDTD._ASSOCIATION_RELATIONAL_TYPE);
      _balanced = UtilFunction.GetBooleanValueOfString(iAttributes.getValue(PapeeteDTD._ASSOCIATION_BALANCED));
      _valid = UtilFunction.GetBooleanValueOfString(iAttributes.getValue(PapeeteDTD._ASSOCIATION_VALID));
    } else if (iLocalName.equals(PapeeteDTD._ASSOCIATION_ARTIFACT1)) {
      _artifact1 = true;
      _artifact1Tool = iAttributes.getValue(PapeeteDTD._ASSOCIATION_TOOL);
    } else if (iLocalName.equals(PapeeteDTD._ASSOCIATION_ARTIFACT2)) {
      _artifact2 = true;
      _artifact2Tool = iAttributes.getValue(PapeeteDTD._ASSOCIATION_TOOL);
    }
    _content.setLength(0);
  }

  /**
   * Method unmarshall. This method clears the associationMap, parses the file and return the map which contains all associations of the file
   * @return Collection All associations in the file
   */
  Collection<Association> unmarshal() {
    _associationMap.clear();
    parse();
    return _associationMap;
  }

  /**
   * Method warning Wraps warning error into SAXException with Orchestra Framework error message.
   * @see org.xml.sax.ErrorHandler#warning(SAXParseException)
   * @param iSAXParseException Exception to wrap
   * @throws SAXException result of wrapping operation
   */
  @Override
  public void warning(SAXParseException iSAXParseException) throws SAXException {
    String errmsg = BaseErrorHandler.GetErrorMsg("SAX_Warning_Error"); //$NON-NLS-1$
    throw new SAXException(errmsg);
  }
}