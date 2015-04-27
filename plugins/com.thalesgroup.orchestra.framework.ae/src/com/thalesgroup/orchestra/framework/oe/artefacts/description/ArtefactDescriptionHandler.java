/**
 * <p>
 * Copyright (c) 2002-2007 : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Société : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Thales Part Number 16 262 601
 * </p>
 */
package com.thalesgroup.orchestra.framework.oe.artefacts.description;

import java.util.LinkedHashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <p>
 * Title : ArtifactsDescriptionHandler
 * </p>
 * <p>
 * Description : This class handles the ArtifactDescription XMLs file.
 * @author Orchestra Framework Tool Suite developer
 * @version 3.7.0
 */
public class ArtefactDescriptionHandler extends DefaultHandler {
  private static final String _ARTIFACT = "artifact"; //$NON-NLS-1$
  private static final String _ISDISPLAYABLE = "isDisplayable";//$NON-NLS-1$
  private static final String _ISEXPANDABLE = "isExpandable";//$NON-NLS-1$
  private static final String _ISEXTRACTABLE = "isExtractable";//$NON-NLS-1$
  private static final String _ISNAVIGABLE = "isNavigable";//$NON-NLS-1$
  private static final String _ISSETCREDENTIAL = "supportReinitializeLogin";//$NON-NLS-1$
  private static final String _ISTOOLATOMICALLYEXTRACTABLE = "isToolAtomicallyExtractable";//$NON-NLS-1$
  private static final String _ISTRACEABLE = "isTraceable";//$NON-NLS-1$
  private static final String _NAME = "name";//$NON-NLS-1$
  private static final String _NODEICONNAME = "iconRelativePath";//$NON-NLS-1$
  private static final String _TOOL = "tool";//$NON-NLS-1$
  private static final String _TYPE = "objectType";//$NON-NLS-1$
  private IArtefactTypeDescription _genericArtifact = null;
  private String _icon = null;
  private boolean _isDisplayable = true;
  private boolean _isExpandable = false;
  private boolean _isExtractable = false;
  private boolean _isNavigable = false;
  private boolean _isSetCredential = false;
  private boolean _isToolAtomicallyExtractable = false;
  private boolean _isTraceable = false;
  private StringBuilder _keyToolType = null;
  private Map<String, IArtefactTypeDescription> _mapToolTypeArtifact = new LinkedHashMap<String, IArtefactTypeDescription>();
  private String _tool = null;
  private String _type = null;

  /**
   * Returns the map of tool,version and type of the artifacts
   * @return map
   */
  public Map<String, IArtefactTypeDescription> getMap() {
    return _mapToolTypeArtifact;
  }

  /**
   * Set the attributes values to their default settings
   */
  private void setDefaultValues() {
    _isDisplayable = true;
    _isExpandable = false;
    _isExtractable = false;
    _isNavigable = false;
    _isSetCredential = false;
    _isToolAtomicallyExtractable = false;
    _isTraceable = false;
  }

  /**
   * Event received each time the parser finds a xml opening mark-up
   * @param nameSpaceURI URL of the naming space.
   * @param localName Local name of the mark-up.
   * @param rawName Mark-up name in version 1.0 <code>nameSpaceURI + ":" + localName</code>
   * @throws SAXException If the mark-up does not correspond to what is expected, such as a dis-respectful DTD.
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  @Override
  public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException {
    // Reset the values, else if an attribute is not found, the previous value will be used and not the default value (as intended)
    setDefaultValues();
    if (localName.equalsIgnoreCase(_ARTIFACT)) {
      for (int index = 0; index < attributs.getLength(); index++) {
        // Parse the list of attributes
        String witness = attributs.getLocalName(index);
        if (_TOOL.equalsIgnoreCase(witness)) {
          _tool = attributs.getValue(index);
        }
      }
    } else if (localName.equalsIgnoreCase(_TYPE)) {
      _isToolAtomicallyExtractable = true;
      // if not specified consider it is
      _isDisplayable = true;
      for (int index = 0; index < attributs.getLength(); index++) {
        // Parse the list of attributes
        String witness = attributs.getLocalName(index);
        if (_NAME.equalsIgnoreCase(witness)) {
          _type = attributs.getValue(index);
        }
        if (_ISEXPANDABLE.equalsIgnoreCase(witness)) {
          _isExpandable = Boolean.parseBoolean(attributs.getValue(index));
        } else if (_ISNAVIGABLE.equalsIgnoreCase(witness)) {
          _isNavigable = Boolean.parseBoolean(attributs.getValue(index));
        } else if (_ISSETCREDENTIAL.equalsIgnoreCase(witness)) {
          _isSetCredential = Boolean.parseBoolean(attributs.getValue(index));
        } else if (_ISEXTRACTABLE.equalsIgnoreCase(witness)) {
          _isExtractable = Boolean.parseBoolean(attributs.getValue(index));
        } else if (_ISTOOLATOMICALLYEXTRACTABLE.equalsIgnoreCase(witness)) {
          _isToolAtomicallyExtractable = Boolean.parseBoolean(attributs.getValue(index));
        } else if (_ISTRACEABLE.equalsIgnoreCase(witness)) {
          _isTraceable = Boolean.parseBoolean(attributs.getValue(index));
        } else if (_ISDISPLAYABLE.equalsIgnoreCase(witness)) {
          _isDisplayable = Boolean.parseBoolean(attributs.getValue(index));
        } else if (_NODEICONNAME.equalsIgnoreCase(witness)) {
          _icon = attributs.getValue(index);
        }
      }
      if (_isToolAtomicallyExtractable && !_isExtractable) {
        // if not extractable then cannot be tool atomically extractable
        _isToolAtomicallyExtractable = false;
      }
      _genericArtifact =
          new ArtefactTypeDescription(_isExpandable, _isExtractable, _isToolAtomicallyExtractable, _isTraceable, _isNavigable, _isDisplayable,
              _isSetCredential, _icon);
      _keyToolType = new StringBuilder();
      _keyToolType.append(_tool);
      _keyToolType.append(".");//$NON-NLS-1$
      _keyToolType.append(_type);
      _mapToolTypeArtifact.put(_keyToolType.toString(), _genericArtifact);
      _keyToolType.delete(0, _keyToolType.length());

    }
  }
}