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
 * merged 17/09/03
 */
package com.thalesgroup.orchestra.framework.transcription;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thalesgroup.orchestra.framework.lib.base.UtilFunction;

/**
 * manages associations from one file : It can be load associations from a specified file and stores them in a list. It can serialize associations in a file.
 * @author Themis developer
 * @version 3.1
 */
final class AssociationFileManager {
  /**
   * _associationFilepathname is the name of the file for this instance of AssocationFileManager
   */
  private String _associationFilepathname = null;

  /**
   * Associations in memory
   */
  private List<Association> _assoSet = new ArrayList<Association>();

  /**
   * 
   */
  private long _lastModified = -1;

  /**
   * This is the constructor of the AssociationFileManager.
   * @param iAssociationFilepathname The pathName xml file association.
   */
  protected AssociationFileManager(String iAssociationFilepathname) {
    _associationFilepathname = iAssociationFilepathname;
    _lastModified = -1;
  }

  /**
   * add an association in memory
   * @param iAssociationToAdd
   */
  protected void addAssociation(Association iAssociationToAdd) {
    _assoSet.add(iAssociationToAdd);
  }

  /**
   * returns a collection in memory
   * @return a collection of associations loaded
   */
  protected Collection<Association> getAssociations() {
    return _assoSet;
  }

  /**
   * This method parses the xml file and stores them in a list updates the value of last modified attribute.
   * @return a collection of associations corresponding to an association file pathname
   */
  protected Collection<Association> getUpToDateAssociations() {
    if (!isUpToDate() && UtilFunction.IsExistedFile(_associationFilepathname)) {
      _assoSet.clear();
      AssociationXMLHandler assoXMLHandler = new AssociationXMLHandler(_associationFilepathname);
      _assoSet = (List<Association>) assoXMLHandler.unmarshal();
      File fileAssociation = new File(_associationFilepathname);
      _lastModified = fileAssociation.lastModified();
    }
    return _assoSet;
  }

  /**
   * Method isUpToDate. This method tests if the value _lastModified is different with the method file.lastModified().
   * @return boolean true if no change else false.
   */
  protected boolean isUpToDate() {
    boolean retval = false;
    if (UtilFunction.IsExistedFile(_associationFilepathname)) {
      retval = _lastModified == new File(_associationFilepathname).lastModified();
    }
    return retval;
  }
}