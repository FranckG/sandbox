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

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.thalesgroup.orchestra.framework.lib.constants.IFrameworkConstants;

/**
 * FilesManager is an association files manager automaton. It uses for maintain consistency between physical xml files and memory representation.
 * @author Themis developer
 * @version 3.1.0
 */
final class FilesManager extends AssociationSourceObservable {
  /**
   * Default size of files map
   */
  private static final int _DEF_FILE_NB = 64;

  /**
   * Association File Table
   */
  private HashMap<String, AssociationFileManager> _associationFileManagers;

  /**
   * XML Association File Current Directory
   */
  private File _xmlAssociationCurrDir = null;

  /**
   * Constructor of FilesManager
   * @param iAssociationXMLDirectoryPath
   */
  FilesManager(String configurationDirectoryPath_p) {
    super();
    // Associations are located under {ConfDir}\Framework\Associations.
    IPath xmlAssociationDirPath =
        Path.fromOSString(configurationDirectoryPath_p).append(IFrameworkConstants.FRAMEWORK_CONFDIR_NAME)
            .append(IFrameworkConstants.FRAMEWORK_ASSOCIATIONS_DIR_NAME);

    _xmlAssociationCurrDir = xmlAssociationDirPath.toFile();
    _associationFileManagers = new HashMap<String, AssociationFileManager>(_DEF_FILE_NB);
  }

  /**
   * Method getXMLPathInFS. Get a list of xml files in a directory.
   * @param iDirName
   * @return List of files' pathname as String
   */
  @SuppressWarnings("synthetic-access")
  private List<String> getXMLPathInFS(File xmlAssociationsCurrDir_p) {
    List<String> retval = new ArrayList<String>();
    // get xml file in the directory
    if (xmlAssociationsCurrDir_p.isDirectory()) {
      FilenameFilter XMLFilenameFilter = new XMLFileFilter();
      File[] xmlFileList = xmlAssociationsCurrDir_p.listFiles(XMLFilenameFilter);
      // for each file, add its path
      for (File xmlFile : xmlFileList) {
        retval.add(xmlFile.getAbsolutePath());
      }
    }
    return retval;
  }

  /**
   * Method upToDate.<br>
   * Validate time consistency with physical files.<br>
   * synchronized Method
   */
  @Override
  protected void upToDate() {
    synchronized (this) {
      List<String> xmlAssociationFiles = getXMLPathInFS(_xmlAssociationCurrDir);
      Collection<Association> associationsToAdd = new ArrayList<Association>();
      Collection<Association> associationsToRemove = new ArrayList<Association>();
      int size = xmlAssociationFiles.size();
      AssociationFileManager afm = null;
      // parcours du tableau représentant les fichiers d'associations présents sur le filesystem
      // Alimentation de deux collections d'association une à ajouter, l'autre à supprimer.
      for (int i = 0; i < size; i++) {
        String filePathName = xmlAssociationFiles.get(i);
        if (_associationFileManagers.containsKey(filePathName)) {
          afm = _associationFileManagers.get(filePathName);
          if (!afm.isUpToDate()) {
            associationsToRemove.addAll(afm.getAssociations());
            associationsToAdd.addAll(afm.getUpToDateAssociations());
          }
        } else {
          // création de l'AssociationFileManager
          AssociationFileManager newAfm = new AssociationFileManager(filePathName);
          _associationFileManagers.put(filePathName, newAfm);
          associationsToAdd.addAll(newAfm.getUpToDateAssociations());
        }
      }
      // recherche des fichiers d'associations supprimés
      // et Alimentation de la collection d'asociations à supprimer
      Collection<String> keySet = _associationFileManagers.keySet();
      Stack<String> stack = new Stack<String>();
      stack.addAll(keySet);
      for (int i = 0; i < size; i++) {
        String filePathName = xmlAssociationFiles.get(i);
        stack.remove(filePathName);
      }
      for (String fpnToRemove : stack) {
        afm = _associationFileManagers.get(fpnToRemove);
        associationsToRemove.addAll(afm.getAssociations());
        _associationFileManagers.remove(fpnToRemove);
      }
      // Synchronisation des objets qui observent(TableManager)
      associationsRemoved(associationsToRemove);
      associationsAdded(associationsToAdd);
    }
  }

  /**
   * <p>
   * Titre : XMLFilenameFilter
   * </p>
   * <p>
   * Description : XMLFilenameFilter filters directory for XML files.
   * @author Themis developer
   * @version 2.2.2
   */
  private static class XMLFileFilter implements FilenameFilter {
    /**
     * Method accept.
     * @param iDir
     * @param iName
     * @return boolean
     * @pre iDir!=null && iName != null
     * @post $none
     * @see java.io.FilenameFilter#accept(File, String)
     */
    public boolean accept(File iDir, String iName) {
      File fichierToTest = new File(iDir, iName);
      return fichierToTest.isFile()
             && (iName.endsWith(XMLTags._TRANSCRIPTION_EXTENSION_XML) || iName.endsWith(XMLTags._TRANSCRIPTION_EXTENSION_XML.toLowerCase()));
    }
  }
}
