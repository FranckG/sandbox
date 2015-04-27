/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.artefacts.internal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.thalesgroup.orchestra.framework.exchange.GefHandler;
import com.thalesgroup.orchestra.framework.gef.Description;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Property;
import com.thalesgroup.orchestra.framework.gef.TextualDescription;
import com.thalesgroup.orchestra.framework.lib.utils.uri.BadOrchestraURIException;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefactProperties;
import com.thalesgroup.orchestra.framework.oe.artefacts.description.ArtefactTypeDescription;
import com.thalesgroup.orchestra.framework.oe.artefacts.description.ArtefactsDescriptionLoader;
import com.thalesgroup.orchestra.framework.oe.artefacts.description.IArtefactTypeDescription;
import com.thalesgroup.orchestra.framework.oe.ui.constants.IImageConstants;

/**
 * This class represents any type of artefact based upon its URI.
 * @author S0024585
 */
public class Artefact implements IArtefact {

  private final static String PARAMETER_NAME_VALUE_SEPARATOR = "\247###\247"; //$NON-NLS-1$

  public final static String PARAMETER_PROPERTY = "Parameter"; //$NON-NLS-1$
  private final static String PARAMETERS_SEPARATOR = "#\247\247\247#"; //$NON-NLS-1$

  protected IArtefactTypeDescription _artefactTypeDescription;

  protected List<Artefact> _children = new ArrayList<Artefact>(0);

  protected String _description = ""; //$NON-NLS-1$

  protected String _fullName = ""; //$NON-NLS-1$

  protected String _label = ""; //$NON-NLS-1$

  protected Map<String, String> _parameters = new HashMap<String, String>(0);

  protected Map<String, String> _properties = new HashMap<String, String>(0);

  protected boolean _rootArtefact = true;

  protected OrchestraURI _uri;

  /**
   * Build an empty artefact.
   * @param isRootArtefact_p
   */
  protected Artefact(boolean isRootArtefact_p) {
    _rootArtefact = isRootArtefact_p;
  }

  /**
   * Build an artefact from a {@link DataInputStream}. Used in drag'n'drop.
   * @param in_p
   * @throws IOException
   */
  public Artefact(DataInputStream dataIn_p) throws IOException, BadOrchestraURIException {
    /**
     * Artifact serialization format is as follows: (String) papeeteUri (String) fragmentName (String) serviceParameters ... repeat for each child
     */
    String orchestraURI = dataIn_p.readUTF();
    String fragmentName = dataIn_p.readUTF();
    String parameters = dataIn_p.readUTF();
    String filePath = dataIn_p.readUTF();
    String relativePath = dataIn_p.readUTF();
    _uri = new OrchestraURI(orchestraURI);
    _properties.put(IArtefactProperties.FRAGMENT_NAME, fragmentName);
    if (filePath.length() > 0) {
      _properties.put(IArtefactProperties.ABSOLUTE_PATH, filePath);
    }
    if (relativePath.length() > 0) {
      _properties.put(IArtefactProperties.RELATIVE_PATH, relativePath);
    }
    if (parameters.length() > 0) {
      String parameter[] = parameters.split(PARAMETERS_SEPARATOR);
      for (int i = 0; i < parameter.length; i++) {
        StringTokenizer paramToken = new StringTokenizer(parameter[i], PARAMETER_NAME_VALUE_SEPARATOR, false);
        if (paramToken.countTokens() == 1) {
          String parameterName = paramToken.nextToken().trim();
          if (parameterName.length() > 0) {
            _parameters.put(parameterName, "");//$NON-NLS-1$
          }
        } else {
          String name = paramToken.nextToken().trim();
          if (name.length() > 0) {
            String value = paramToken.nextToken();
            _parameters.put(name, value);
          }
        }
      }
    }
  }

  /**
   * Build an artefact from a Gef element.
   * @param gefElement_p
   * @param isRootArtefact_p
   */
  public Artefact(Element gefElement_p, boolean isRootArtefact_p) {
    this(isRootArtefact_p);
    _label = gefElement_p.getLabel();
    _uri = new OrchestraURI(gefElement_p.getUri());
    _fullName = gefElement_p.getFullName();
    // Read properties
    EList<Properties> propertiesList = gefElement_p.getPROPERTIES();
    if (propertiesList != null) {
      for (Properties propertiesItem : propertiesList) {
        EList<Property> properties = propertiesItem.getPROPERTY();
        if (properties != null) {
          for (Property property : properties) {
            List<String> propertyValues = GefHandler.getValue(property);
            StringBuilder propertyValue = new StringBuilder();
            if (propertyValues != null) {
              for (String value : propertyValues) {
                propertyValue.append(value);
              }
            }
            String propertyName = property.getName();
            // Check particular case of "Parameter" properties
            // as there can be multiples parameters.
            if (propertyName.equals(PARAMETER_PROPERTY)) {
              _parameters.put(propertyValue.toString(), ""); //$NON-NLS-1$
            } else {
              _properties.put(propertyName, propertyValue.toString());
            }
          }
        }
      }
    }
    // Read description
    // Only TEXTUALDESCRIPTION type is taken into account.
    EList<Description> descriptions = gefElement_p.getDESCRIPTION();
    StringBuilder descriptionValue = new StringBuilder();
    if (descriptions != null) {
      for (Description description : descriptions) {
        EList<TextualDescription> textualDescriptions = description.getTEXTUALDESCRIPTION();
        if (textualDescriptions != null) {
          for (TextualDescription textualDescription : textualDescriptions) {
            List<String> values = GefHandler.getValue(textualDescription);
            StringBuilder descValue = new StringBuilder();
            if (values != null) {
              for (String value : values) {
                descValue.append(value);
              }
            }
            descriptionValue.append(descValue);
          }
        }
      }
    }
    _description = descriptionValue.toString();
  }

  /**
   * Build an artefact from its URI
   * @param uri_p
   * @param isRootArtefact_p
   */
  public Artefact(OrchestraURI uri_p, boolean isRootArtefact_p) {
    this(isRootArtefact_p);
    _uri = uri_p;
    _label = getName();
    _fullName = _label;
  }

  /**
   * Build an artefact from its URI, specifying its absolute path.
   * @param uri_p
   * @param absolutePath_p
   * @param relativePath_p
   */
  public Artefact(OrchestraURI uri_p, String absolutePath_p, String relativePath_p) {
    this(uri_p, true);
    _properties.put(IArtefactProperties.ABSOLUTE_PATH, absolutePath_p);
    _properties.put(IArtefactProperties.RELATIVE_PATH, relativePath_p);
  }

  /**
   * Add the artefact as a child.
   */
  public void addChild(Artefact child_p) {
    _children.add(child_p);
  }

  /**
   * Write the artefact in a {@link DataOutputStream} for drag'n'drop.
   * @param dataOut_p
   * @throws IOException
   */
  public void export(DataOutputStream dataOut_p) throws IOException {
    // Artifact serialization format is as follows: (String) papeeteUri (String) fragmentName (String) serviceParameters ... repeat for each child
    String papeeteUri = getUri().getUri();
    String fragmentName = getPropertyValue(IArtefactProperties.FRAGMENT_NAME);
    if (null == fragmentName) {
      fragmentName = getLabel();
    }
    String empty = "";//$NON-NLS-1$
    if ((null == fragmentName) || (empty.equals(fragmentName))) {
      fragmentName = getName();
    }
    String serviceParam = empty;
    if (!_parameters.isEmpty()) {
      StringBuffer serviceParamBuffer = new StringBuffer();
      for (String parameterKey : _parameters.keySet()) {
        serviceParamBuffer.append(parameterKey);
        serviceParamBuffer.append(PARAMETER_NAME_VALUE_SEPARATOR);
        serviceParamBuffer.append(_parameters.get(parameterKey));
        serviceParamBuffer.append(PARAMETERS_SEPARATOR);
      }
      serviceParam = serviceParamBuffer.toString();
    }
    String filePath = getPropertyValue(IArtefactProperties.ABSOLUTE_PATH);
    String relativePath = getPropertyValue(IArtefactProperties.RELATIVE_PATH);
    dataOut_p.writeUTF(papeeteUri);
    dataOut_p.writeUTF(fragmentName);
    dataOut_p.writeUTF(serviceParam);
    dataOut_p.writeUTF(filePath == null ? empty : filePath);
    dataOut_p.writeUTF(relativePath == null ? empty : relativePath);
  }

  /**
   * @return the children list
   */
  public List<Artefact> getChildren() {
    return _children;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#getDescription()
   */
  public String getDescription() {
    return _description;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#getFullName()
   */
  public String getFullName() {
    return _fullName;
  }

  /**
   * @return The {@link ArtefactTypeDescription} of the artefact.
   */
  public IArtefactTypeDescription getGenericArtifact() {
    if (null == _artefactTypeDescription) {
      String genericType = ""; //$NON-NLS-1$
      genericType = _uri.getRootType() + '.';
      String objectType = _uri.getObjectType();
      if (!((null == objectType) || objectType.trim().isEmpty())) {
        genericType += objectType;
      }
      _artefactTypeDescription = ArtefactsDescriptionLoader.getInstance().getArtefactTypeDescription(genericType);
      if (null == _artefactTypeDescription) {
        _artefactTypeDescription = new ArtefactTypeDescription(false, false, false, false, false, false, false, IImageConstants.DESC_ART_DEFAULT);
      }
    }
    return _artefactTypeDescription;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#getLabel()
   */
  public String getLabel() {
    return _label;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#getName()
   */
  public String getName() {
    String name = _uri.getObjectId();
    if ((name == null) || (name.isEmpty())) {
      name = _uri.getRootName();
    }
    return name;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#getParameters()
   */
  public Map<String, String> getParameters() {
    return _parameters;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#getProperties()
   */
  public Map<String, String> getProperties() {
    return _properties;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#getPropertyValue(java.lang.String)
   */
  public String getPropertyValue(String propertyName_p) {
    return _properties.get(propertyName_p);
  }

  /**
   * Return the RootPath of the artefact. The "RootPath" is the absolute path minus the relative path. <br>
   * For an environment artefact, the "RootPath" will be a String representation of the environment.
   * @return The "RootPath" of the artefact.
   */
  public String getRootPath() {
    String absolute = getPropertyValue(IArtefactProperties.ABSOLUTE_PATH);
    String result = null;
    // If an environment, the absolute path is null
    if (null != absolute) {
      Path absolutePath = new Path(absolute);
      String relative = getPropertyValue(IArtefactProperties.RELATIVE_PATH);
      Path relativePath = new Path(relative);
      IPath rootPath = absolutePath.removeLastSegments(relativePath.segmentCount());
      result = rootPath.toOSString();
    } else {
      // For an environment artefact return a toString description of the latter as rootPath
      result = getPropertyValue(IArtefactProperties.ENVIRONMENT);
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#getType()
   */
  public String getType() {
    String type = _uri.getObjectType();
    if ((type == null) || (type.isEmpty())) {
      type = _uri.getRootType();
    }
    return type;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#getUri()
   */
  public OrchestraURI getUri() {
    return _uri;
  }

  /**
   * @return the environmentId
   */
  public String getEnvironmentId() {
    return _properties.get(IArtefactProperties.ENVIRONMENT_ID);
  }

  /**
   * @return <code>true</code> if the artefact has children. <code>false</code> otherwise.
   */
  public boolean hasChildren() {
    return (_children.size() > 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#isRootArtefact()
   */
  public boolean isRootArtefact() {
    return _rootArtefact;
  }

  /**
   * @param children_p the children to set
   */
  public void setChildren(List<Artefact> children_p) {
    _children = children_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#setFullName(java.lang.String)
   */
  public void setFullName(String fullName_p) {
    _fullName = fullName_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#setLabel(java.lang.String)
   */
  public void setLabel(String label_p) {
    _label = label_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact#setUri(com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI)
   */
  public void setUri(OrchestraURI uri_p) {
    _uri = uri_p;
  }

  /**
   * @return the environmentId
   */
  public String setEnvironmentId(String environmentId_p) {
    return _properties.put(IArtefactProperties.ENVIRONMENT_ID, environmentId_p);
  }

  // Return the toString description of the Artefact URI for easier debug operations
  @Override
  public String toString() {
    return Messages.Artefact_ToString + _uri.toString();
  }

  /**
   * Update the current artefact with data from the target artefact. Condition: URIs of the two artefact must be strictly equals
   * @param targetArtefact_p
   */
  public void update(IArtefact targetArtefact_p) {
    assert (targetArtefact_p.getUri().equals(_uri));
    // Clean up a little
    _parameters.clear();
    _properties.clear();
    // Make sure the type description is reset because it might just happen that the configuration directories have changed, and should be taken into account
    // again.
    _artefactTypeDescription = null;
    // Update
    _fullName = targetArtefact_p.getFullName();
    _description = targetArtefact_p.getDescription();
    _label = targetArtefact_p.getLabel();
    _parameters = targetArtefact_p.getParameters();
    _properties = targetArtefact_p.getProperties();
  }
}
