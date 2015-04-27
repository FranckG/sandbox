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
 */
package com.thalesgroup.orchestra.framework.transcription;

import com.thalesgroup.orchestra.framework.lib.base.UtilFunction;
import com.thalesgroup.orchestra.framework.lib.base.utils.xml.XMLUtils;

/**
 * Papeete manipulates artifacts, which are "things" produced by a tool and used by another. Examples of artifacts are a word document, a set of UML models, a
 * requirement, a source code file, a picture… Artifacts can be associated to each other</p>
 * @author Themis developer
 * @version 3.1.0
 */
public final class AssociationArtifact {
  /**
   * Content of the artifact logical project name and its physical file system with a logical-to-physical relational type the full qualified name of an artifact
   * in a logical-to-logical association includes the project
   */
  private String _content = null;

  /**
   * Name of the tool managing the artifact.
   */
  private String _rootType = null;

  /**
   * Constructs an artifact with the given strings.
   * @param rootType_p Name of the tool managing the artifact.
   * @param content_p Content of the artifact.
   * @pre iTool!=null && iContent!=null
   */
  public AssociationArtifact(String rootType_p, String content_p) {
    _rootType = rootType_p;
    _content = content_p;
  }

  /**
   * Returns the Content of this artifact.
   * @return String
   */
  public final String getContent() {
    return _content;
  }

  /**
   * Returns the Name of the tool managing this artifact.
   * @return String
   */
  public final String getRootType() {
    return _rootType;
  }

  /**
   * Returns a XML string represented this artifact.
   * @return String
   */
  String getXMLTag() {
    StringBuffer balise = UtilFunction.GetDefaultStringBuffer();
    // Tag Tool
    balise.append(XMLTags._ASSOCIATION_TOOL);
    if (_rootType != null) {
      balise.append(XMLUtils.AddDoubleQuote(_rootType));
    }
    balise.append(XMLTags._TAGCLOSE);
    // Content of the artefact
    if (_content != null) {
      balise.append(XMLUtils.ConvertSpecialCharacterToXMLEntity(_content));
    }
    return balise.toString();
  }

  /**
   * returns XML Tag of this artifact(artifact1).
   * @return String
   */
  final String getXMLTagAsFirstActifact() {
    StringBuffer balise = UtilFunction.GetDefaultStringBuffer();
    balise.append(XMLTags._SPACE);
    balise.append(XMLTags._SPACE);
    balise.append(XMLTags._ASSOCIATION_ARTIFACT1_BEG);
    // Tag association
    balise.append(getXMLTag());
    balise.append(XMLTags._ASSOCIATION_ARTIFACT1_END);
    return balise.toString();
  }

  /**
   * returns XML Tag of this artifact(artifact2).
   * @return String
   */
  final String getXMLTagAsSecondActifact() {
    StringBuffer balise = UtilFunction.GetDefaultStringBuffer();
    balise.append(XMLTags._SPACE);
    balise.append(XMLTags._SPACE);
    // Tag association
    balise.append(XMLTags._ASSOCIATION_ARTIFACT2_BEG);
    balise.append(getXMLTag());
    balise.append(XMLTags._ASSOCIATION_ARTIFACT2_END);
    return balise.toString();
  }

  /**
   * Utility routine to check if _tool and _content fields in this instance are not equals to null or "null".
   * @return
   */
  final boolean isDefined() {
    boolean retval = true;
    if (UtilFunction.IsNull(_rootType) || UtilFunction.IsNull(_content)) {
      retval = false;
    }
    // type could be not defined
    return retval;
  }

  /**
   * Compares this artifact to the specified artifact
   * @param artifactToCompare_p
   * @return boolean
   */
  final boolean isEquals(AssociationArtifact artifactToCompare_p) {
    boolean retval = isRootTypeEqualsIgnore(artifactToCompare_p);
    if (retval) {
      retval = _content.equals(artifactToCompare_p._content);
    }
    return retval;
  }

  /**
   * Compares this artifact to another artifact, ignoring case considerations.
   * @param artifactToCompare_p
   * @return
   */
  final boolean isEqualsIgnoreCase(AssociationArtifact artifactToCompare_p) {
    boolean retval = isRootTypeEqualsIgnore(artifactToCompare_p);
    if (retval) {
      retval = _content.equalsIgnoreCase(artifactToCompare_p._content);
    }
    return retval;
  }

  /**
   * Utility routine to check if this artifact is equals to iArtifact parameter.
   * @param iArtifact Artifact to compare
   * @return true if the two artifacts are equals; false otherwise.
   */
  @SuppressWarnings("nls")
  final boolean isMatchingArtifact(AssociationArtifact iArtifact) {
    boolean retval = false;
    if (isDefined()) {
      // match tool
      retval = _rootType.equalsIgnoreCase(iArtifact._rootType);
      if ((_content.indexOf(StringMapping._METACHAR) == -1) && (iArtifact._content.indexOf(StringMapping._METACHAR) == -1)) {
        retval &= _content.equals(iArtifact._content);
      } else {
        // try to encode the dot when the logical name contains a dot (to not disturb the extension case)
        if (!iArtifact._rootType.equals("FILESYSTEM") && iArtifact.getContent().contains(".")) {
          String encodedDotContent = iArtifact._content.replaceAll("\\.", "%2E");
          retval &= StringMapping.IsContentMetaChar(encodedDotContent, _content.replaceAll("\\.", "%2E"));
        } else {
          retval &= StringMapping.IsContentMetaChar(iArtifact._content, _content);
        }
      }
    }
    return retval;
  }

  /**
   * Compares this artifact to another artifact except the _content and _toolid attributes, ignoring case considerations.
   * @param iArtifactToCompare
   * @return boolean
   */
  final boolean isRootTypeEqualsIgnore(AssociationArtifact iArtifactToCompare) {
    boolean retval = false;
    if (isDefined() && (iArtifactToCompare != null)) {
      retval = _rootType.equalsIgnoreCase(iArtifactToCompare._rootType);
    }
    return retval;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "AssociationArtifact [_content=" + _content + ", _rootType=" + _rootType + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  }
}
