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

import java.util.Collection;

import com.thalesgroup.orchestra.framework.lib.base.UtilFunction;
import com.thalesgroup.orchestra.framework.lib.base.utils.xml.XMLUtils;

/**
 * Artifact associations, or in short associations are used to specify that a relation exists between two Themis artifacts. This relation can be either
 * unbalanced (artifact A is associated to artifact B) or balanced (artifact A is associated to artifact B, and artifact B is associated backward to artifact
 * A). The semantics of this relation is up to the user. Examples of association uses can be :
 * <ul>
 * <li>Physical abstraction The logical artifact DOC_PROJECT is associated to the physical artifact Y:\REPOSITORY\A400\CDS\DOCS. If all references to the
 * project documentation specify DOC_PROJECT as the documentation root, the relocation of all the documentation on an other physical spot than will require only
 * a simple edition of the association, all logical references remaining unaffected.</li>
 * <li>Tracing Although Papeete association feature isn’t intended to replace specialized requirement traceability tools, it is possible to specify that an
 * artifact can be traced to another one. This can be used for simple traceability needs that users do not wish to implement with, say, DOORS or a database.</li>
 * <li>Tool translation Suppose that the target development environment contains two tools providing different but complementary visions of the same object. For
 * example, they can be a UML modeling tool for describing the object behavioral aspects, and a graphical design tool to describe the way it physically looks
 * like. Associations can be used to formalize the logical connection that exist between the two representation of the same object. They can then be used to
 * implement navigation from one tool to the other.</li>
 * </ul>
 * @author Themis developer
 * @version 3.1
 */
final public class Association {
  /**
   * Type of the association.
   */
  private String _relationType = null;

  private AssociationArtifact _sourceArtifact;

  /**
   * When false (unbalanced association), artifact 1 is associated to artifact 2. When true (balanced association), artifact 1 is associated to artifact 2 AND
   * artifact 2 is associated to artifact 1.
   */
  private boolean _symmetry = false;

  private AssociationArtifact _targetArtifact;

  /**
   * An association that is not valid is ignored by the association services.
   */
  private boolean _valid = false;

  /**
   * Constructor of Association
   * @param iSourceArtifact
   * @param iDestinationArtifact
   * @param iRelationType
   * @param iSymmetry
   * @param iValidity
   */
  public Association(AssociationArtifact iSourceArtifact, AssociationArtifact iDestinationArtifact, String iRelationType, boolean iSymmetry, boolean iValidity) {
    _sourceArtifact = iSourceArtifact;
    _targetArtifact = iDestinationArtifact;
    _relationType = iRelationType;
    _symmetry = iSymmetry;
    _valid = iValidity;
  }

  /**
   * @see java.lang.Object#clone()
   */
  @Override
  protected Object clone() throws CloneNotSupportedException {
    return new Association(getSourceArtifact(), getTargetArtifact(), _relationType, _symmetry, _valid);
  }

  /**
   * searches this association in a collection parameter
   * @param iCol a collection
   * @param iCaseSensitive
   * @return true if association is in the collection, false otherweise.
   */
  boolean exists(Collection<?> iCol) {
    return indexOf(iCol) >= 0;
  }

  /**
   * Method getRelationType.
   * @return String
   */
  final String getRelationType() {
    return _relationType;
  }

  /**
   * Method getSourceArtifact.
   * @return Artifact
   */
  public final AssociationArtifact getSourceArtifact() {
    return _sourceArtifact;
  }

  /**
   * Method getDestinationArtifact.
   * @return Artifact
   */
  public final AssociationArtifact getTargetArtifact() {
    return _targetArtifact;
  }

  /**
   * Method getXMLTag.
   * @return String as XML representation of the instance
   */
  final String getXMLTag() {
    StringBuffer balise = UtilFunction.GetDefaultStringBuffer();
    balise.append(XMLTags._SPACE);
    balise.append(XMLTags._ASSOCIATION_ASSOCIATION_BEG);
    // Relational Type
    balise.append(XMLTags._ASSOCIATION_RELATIONAL_TYPE);
    balise.append(XMLUtils.AddDoubleQuote(_relationType));
    balise.append(XMLTags._SPACE);
    // Tag Balanced
    balise.append(XMLTags._ASSOCIATION_BALANCED);
    balise.append(XMLUtils.AddDoubleQuote(UtilFunction.GetStringOfBooleanValue(_symmetry)));
    balise.append(XMLTags._SPACE);
    // Tag Valid
    balise.append(XMLTags._ASSOCIATION_VALID);
    balise.append(XMLUtils.AddDoubleQuote(UtilFunction.GetStringOfBooleanValue(_valid)));
    balise.append(XMLTags._SPACE);
    balise.append(XMLTags._TAGCLOSE);
    // First artifact
    balise.append(UtilFunction._LINE_SEPARATOR);
    balise.append(getSourceArtifact().getXMLTagAsFirstActifact());
    // Second artifact
    balise.append(UtilFunction._LINE_SEPARATOR);
    balise.append(getTargetArtifact().getXMLTagAsSecondActifact());
    // fin
    balise.append(UtilFunction._LINE_SEPARATOR);
    balise.append(XMLTags._SPACE);
    balise.append(XMLTags._ASSOCIATION_ASSOCIATION_END);
    balise.append(UtilFunction._LINE_SEPARATOR);
    return balise.toString();
  }

  /**
   * returns position of association in collection.
   * @param iCol
   * @return -1 otherwise.
   */
  private int indexOf(Collection<?> iCol) {
    int size = 0;
    int i = -1;
    boolean continues = true;
    int j = 0;
    if (iCol != null) {
      Object[] elementData = iCol.toArray();
      size = elementData.length;
      while (j < size && continues) {
        if (this.isEquals((Association) elementData[j])) {
          i = j;
          continues = false;
        }
        j++;
      }
    }
    return i;
  }

  /**
   * Method isAssoMatching. uses only by getErrotView
   * @param iSourceArtifact
   * @param iToolToTranscript
   * @return true if association matches to artifact and tool destination
   */
  final boolean isAssoMatching(Association iAsso) {
    // matching test only if the current association is valid
    if (_valid) {
      boolean res1 = iAsso.getSourceArtifact().isMatchingArtifact(getSourceArtifact());
      boolean res2 = iAsso.getTargetArtifact().isMatchingArtifact(getTargetArtifact());
      if (res2) {
        return true;
      }
      return res1;
    }
    return false;
  }

  /**
   * Method isAssoMatching. Verify if an association matches with an artifact and the target tool.
   * @param associationArtifact_p
   * @param iToolToTranscript
   * @return true if association matches to artifact and tool destination
   */
  final boolean isAssoMatching(AssociationArtifact associationArtifact_p, String iToolToTranscript) {
    boolean match = false;
    // matching test only if the current association is valid
    if (_valid) {
      // from source to destination
      if ("*".equalsIgnoreCase(iToolToTranscript)) { //$NON-NLS-1$
        if (_symmetry) {
          match = getTargetArtifact().isMatchingArtifact(associationArtifact_p);
        }
      } else {
        final boolean art0 = iToolToTranscript.equalsIgnoreCase(getSourceArtifact().getRootType());
        final boolean art1 = iToolToTranscript.equalsIgnoreCase(getTargetArtifact().getRootType());
        if (art0) {
          // 4 possibilités (3 cas 1 et un cas 3)
          if (art1 && _symmetry) {
            // cas 3
            boolean test1 = getTargetArtifact().isMatchingArtifact(associationArtifact_p);
            boolean test0 = getSourceArtifact().isMatchingArtifact(associationArtifact_p);
            match = test0 | test1;
          } else if (_symmetry && !art1) {
            // cas 5 =
            match = getTargetArtifact().isMatchingArtifact(associationArtifact_p);
          } else {
            // cas 1
            match = getSourceArtifact().isMatchingArtifact(associationArtifact_p);
          }
        } else if (art1) {
          match = getSourceArtifact().isMatchingArtifact(associationArtifact_p);
        } else {
          match = false;
        }
      }
    }
    return match;
  }

  /**
   * Is association balanced ?
   * @return
   */
  public boolean isBalanced() {
    return _symmetry;
  }

  /**
   * Utility routine to check if this association is correctly instanciated. Type of the association must be different of null and the two artifacts must be
   * defined too.
   * @return boolean
   */
  private final boolean isDefined() {
    boolean retval = false;
    if ((_relationType != null) && (getSourceArtifact() != null) && (getTargetArtifact() != null)) {
      retval = (getSourceArtifact().isDefined() && getTargetArtifact().isDefined());
    }
    return retval;
  }

  /**
   * Method isEquals.
   * @param iAssociationToCompare
   * @return boolean
   */
  private final boolean isEquals(Association iAssociationToCompare) {
    boolean retval = false;
    boolean test = false;
    if (iAssociationToCompare != null) {
      if ((iAssociationToCompare.isDefined()) && (isDefined())) {
        if ((_symmetry == iAssociationToCompare._symmetry) && (_valid == iAssociationToCompare._valid)) {
          if (_relationType.equalsIgnoreCase(iAssociationToCompare._relationType)) {
            retval = (getSourceArtifact().isEquals(iAssociationToCompare.getSourceArtifact()));
            retval &= (getTargetArtifact().isEquals(iAssociationToCompare.getTargetArtifact()));
            if (_symmetry) {
              test = (getSourceArtifact().isEquals(iAssociationToCompare.getTargetArtifact()));
              test &= (getTargetArtifact().isEquals(iAssociationToCompare.getSourceArtifact()));
              retval |= test;
            }
          }
        }
      }
    }
    return retval;
  }

  public boolean isEqualsByValues(Association iAsso) {
    boolean retval = false;
    if (iAsso != null) {
      retval = this.isEquals(iAsso);
    }
    return retval;
  }

  /**
   * Is association valid ?
   * @return
   */
  public boolean isValid() {
    return _valid;
  }

  /**
   * @param iBalanced
   */
  final void setBalanced(boolean iBalanced) {
    _symmetry = iBalanced;
  }

  /**
   * @param iRelationalType
   */
  final void setRelationType(String iRelationalType) {
    _relationType = iRelationalType;
  }
}
