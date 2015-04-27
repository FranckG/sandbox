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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.thalesgroup.orchestra.framework.lib.base.UtilFunction;

/**
 * This class manages associations in memory
 * @author Themis developer
 * @version 2.2.2
 */
final class AssociationTableManager extends AssociationTypeManager implements Observer {
  /**
   * Constructor for AssociationTableManager.
   */
  AssociationTableManager() {
    // Just use the super constructor.
    super();
  }

  /**
   * updates AssociationTypeManager HashMap sorted by type of association
   * @param iObservable this is the caller
   * @param iArg The iArg[0] is to know if there is an addition or a removing The iArg[1] is a collection of associations to action
   * @see java.util.Observer#update(Observable, Object)
   * @pre iArg != null
   */
  @SuppressWarnings("unchecked")
  public void update(Observable iObservable, Object iArg) {
    Integer stratege = (Integer) ((Object[]) iArg)[0];
    Collection<Association> arguments = (Collection<Association>) ((Object[]) iArg)[1];
    if (!arguments.isEmpty()) {
      if (AssociationSourceObservable._ADDING.equals(stratege)) {
        addAssociations(arguments);
      } else if (AssociationSourceObservable._REMOVING.equals(stratege)) {
        removeAssociations(arguments);
      }
    }
  }

  /**
   * searchs all associations which corresponding of all parameters of the method and return an arrayList
   * @param iArtifactToSeek The artifact to search
   * @param iToolToTranscript The tool to transcript
   * @param iRelationType The relationType
   * @return List The list of all associations or empty list;
   */
  List<Association> getAssociations(AssociationArtifact iArtifactToSeek, String iToolToTranscript, String iRelationType) {
    List<Association> retval = new ArrayList<Association>();
    if (iArtifactToSeek != null && (!UtilFunction.IsNull(iToolToTranscript)) && (!UtilFunction.IsNull(iRelationType))) { // get the correct map
      Collection<Association> assoSet = _relationTypeToAssociations.get(iRelationType.toUpperCase());
      // may be null
      if (assoSet != null) {
        Iterator<Association> iterator = assoSet.iterator();
        Association crt_asso = null; // current_association
        while (iterator.hasNext()) {
          crt_asso = iterator.next();
          if (crt_asso.isAssoMatching(iArtifactToSeek, iToolToTranscript)) {
            // on ajoute si elle n'existe pas déjà dans la collection
            if (!crt_asso.exists(retval)) {
              retval.add(crt_asso);
            }
          }
        }
      }
    }
    return retval;
  }

  /**
   * Return returns a collection of associations can be wrong.
   * @param iType
   * @return AssociationTableManager The associationTableManager of error
   */
  Map<String, Collection<Association>> getErrorSet(String iType) {
    Map<String, Collection<Association>> result = new HashMap<String, Collection<Association>>(0);
    Collection<Association> associations = _relationTypeToAssociations.get(iType);
    if (null != associations) {
      for (Association source : associations) {
        for (Association target : associations) {
          if (!target.isEqualsByValues(source) && target.isAssoMatching(source)) {
            String sourceType = source.getSourceArtifact().getRootType();
            String targetType = target.getSourceArtifact().getRootType();
            // Add source association to its typed collection.
            Collection<Association> sourceTypeCollection = result.get(sourceType);
            if (null == sourceTypeCollection) {
              sourceTypeCollection = new HashSet<Association>(1);
              result.put(sourceType, sourceTypeCollection);
            }
            sourceTypeCollection.add(source);
            // Add target association to its typed collection.
            Collection<Association> targetTypeCollection = result.get(targetType);
            if (null == targetTypeCollection) {
              targetTypeCollection = new HashSet<Association>(1);
              result.put(targetType, targetTypeCollection);
            }
            targetTypeCollection.add(target);
          }
        }
      }
    }
    return result;
  }
}