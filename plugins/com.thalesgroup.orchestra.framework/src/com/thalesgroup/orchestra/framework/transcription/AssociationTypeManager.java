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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.thalesgroup.orchestra.framework.lib.base.UtilFunction;

/**
 * This class is the super class for AssociationTableManager and AssociationFileManager for the managing of associationMap
 * @author Themis developer
 * @version 2.5.0
 */
public class AssociationTypeManager {
  /**
   * Associations.
   */
  protected final HashMap<String, Set<Association>> _relationTypeToAssociations;
  /**
   * Erroneous associations.
   */
  protected final HashMap<String, Set<Association>> _relationTypeToErroneousAssociations;

  /**
   * Constructor for AssociationTypeManager.
   * @see java.lang.Object#Object()
   */
  AssociationTypeManager() {
    _relationTypeToAssociations = new HashMap<String, Set<Association>>();
    _relationTypeToErroneousAssociations = new HashMap<String, Set<Association>>();
  }

  /**
   * This method add a collection of associations.
   * @param iAssociationsToAdd The collection of associations.
   * @throws PapeeteException
   * @pre iAssociationsToAdd != null
   */
  final void addAssociations(Collection<Association> iAssociationsToAdd) {
    for (Association association : iAssociationsToAdd) {
      if (isAssociationCorrect(association)) {
        addAssociationTo(association, _relationTypeToAssociations);
      } else {
        addAssociationTo(association, _relationTypeToErroneousAssociations);
      }
    }
  }

  private void addAssociationTo(Association association_p, HashMap<String, Set<Association>> destinationHashMap_p) {
    // Type of association must be stored in upper case in the HashMap.
    String relationType = association_p.getRelationType().toUpperCase();
    Set<Association> associationSet = destinationHashMap_p.get(relationType);
    // Association set not found (it doesn't exist for this relation type), create it.
    if (null == associationSet) {
      associationSet = new HashSet<Association>();
      destinationHashMap_p.put(relationType, associationSet);
    }
    // Add the association.
    associationSet.add(association_p);
  }

  /**
   * This method returns the array of associations
   * @return AssociationMap[]
   */
  final protected Object[] getAssociationCols() {
    return _relationTypeToAssociations.values().toArray();
  }

  public Set<Association> getAssociationsFromRelationType(String relationType_p) {
    return _relationTypeToAssociations.get(relationType_p);
  }

  public Set<Association> getErroneousAssociationsFromRelationType(String relationType_p) {
    return _relationTypeToErroneousAssociations.get(relationType_p);
  }

  /**
   * Returns the number of associations of a specified type
   * @param relationType_p type of association
   * @return size of the collection
   */
  final int getSize(String relationType_p) {
    String relationType = relationType_p;
    if (relationType != null) {
      relationType = relationType_p.toUpperCase();
    }
    int result = 0;
    if (_relationTypeToAssociations.containsKey(relationType)) {
      result = _relationTypeToAssociations.get(relationType).size();
    }
    return result;
  }

  /**
   * Method getTypeList. This method returns a list of all types existed in all associationMaps
   * @return String[] The list of all types
   */
  final String[] getTypes() {
    return (String[]) _relationTypeToAssociations.keySet().toArray();
  }

  /**
   * Method getXMLTag. This method returns a XML String representation of all associations of a predefined type
   * @param relationType_p The type to get all associations
   * @return String A XML representation of all associations with the type in parameter
   */
  final String getXMLTag(String relationType_p) {
    String relationType = relationType_p;
    if (relationType != null) {
      relationType = relationType_p.toUpperCase();
    }
    Collection<Association> col = _relationTypeToAssociations.get(relationType);
    StringBuffer retval = UtilFunction.GetDefaultStringBuffer();
    if (col != null) {
      Iterator<Association> iterator = col.iterator();
      Association temp = null;
      while (iterator.hasNext()) {
        temp = iterator.next();
        retval.append(temp.getXMLTag());
        retval.append(UtilFunction._LINE_SEPARATOR);
      }
    }
    return retval.toString();
  }

  public boolean isAssociationCorrect(Association association_p) {
    AssociationArtifact mockAssociationArtifact = new AssociationArtifact("FILESYSTEM", "test"); //$NON-NLS-1$//$NON-NLS-2$
    try {
      association_p.getSourceArtifact().isMatchingArtifact(mockAssociationArtifact);
      association_p.getTargetArtifact().isMatchingArtifact(mockAssociationArtifact);
      return true;
    } catch (NullPointerException ex) {
      return false;
    }
  }

  /**
   * This method removes the collection of associations
   * @param iAssociationsToRemove The collection of associations to remove
   * @throws PapeeteException
   * @pre iAssociationsToRemove != null
   */
  final void removeAssociations(Collection<Association> iAssociationsToRemove) {
    for (Association association : iAssociationsToRemove) {
      Collection<Association> associations = _relationTypeToAssociations.get(association.getRelationType().toUpperCase());
      if (null != associations) {
        // Remove anyway (event if the set doesn't contain the element).
        associations.remove(association);
      }
      Collection<Association> erroneousAssociations = _relationTypeToErroneousAssociations.get(association.getRelationType().toUpperCase());
      if (null != erroneousAssociations) {
        // Remove anyway (event if the set doesn't contain the element).
        erroneousAssociations.remove(association);
      }
    }
  }
}
