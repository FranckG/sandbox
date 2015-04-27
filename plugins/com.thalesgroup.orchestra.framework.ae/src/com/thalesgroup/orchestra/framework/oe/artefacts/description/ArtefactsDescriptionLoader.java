/**
 * <p>
 * Copyright (c) 2002-2008 : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Société : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Thales Part Number 16 262 601
 * </p>
 */
package com.thalesgroup.orchestra.framework.oe.artefacts.description;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.xerces.parsers.SAXParser;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.xml.sax.InputSource;

import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.variableloader.EnvironmentVariablesLoader;

/**
 * <p>
 * Title : ArtifactsDescriptionLoader
 * </p>
 * <p>
 * Description :This class utilizes the functionality of ArtifactDescriptionHandler in order to read the ArtifactDescription files.
 * @author Orchestra Framework Tool Suite developer
 * @version 3.7.0
 */
public class ArtefactsDescriptionLoader {
  private Map<Object, Object> _artefactsMap = new HashMap<Object, Object>();
  private final Map<String, String> _extractableFromTool = new HashMap<String, String>();
  private static ArtefactsDescriptionLoader _instance;

  /**
   * @param toolNameObjectType
   * @return The ArtifactTypeDescription associated get by ToolName.Tool.ObjectType
   */
  public IArtefactTypeDescription getArtefactTypeDescription(String toolNameObjectType) {
    return (IArtefactTypeDescription) _artefactsMap.get(toolNameObjectType);
  }

  /**
   * @return a String[] of extractable tools
   */
  public String[] getExtractableTools() {
    Set<String> keySet = _extractableFromTool.keySet();
    String[] result = keySet.toArray(new String[0]);
    Arrays.sort(result);
    return result;
  }

  /**
	 * 
	 */
  private void generateToolTypesForExtractorPage() {
    String[] extractableToolType = getExtractableToolType();
    int index = 0;
    String tool = null;
    String type = null;
    String tmp = null;
    for (int i = 0; i < extractableToolType.length; i++) {
      index = extractableToolType[i].indexOf('.');
      tool = extractableToolType[i].substring(0, index);
      tmp = extractableToolType[i].substring(index + 1);
      index = tmp.indexOf("."); //$NON-NLS-1$
      type = tmp.substring(index + 1);
      _extractableFromTool.put(tool, type);
    }
  }

  /**
   * @return A set of tool atomically extractable tooltype (Tool.Type)
   */
  private String[] getExtractableToolType() {
    ArrayList<String> extractableToolType = new ArrayList<String>();
    Set<Object> keySet = _artefactsMap.keySet();
    for (Iterator<Object> iter = keySet.iterator(); iter.hasNext();) {
      String element = (String) iter.next();
      if (((IArtefactTypeDescription) _artefactsMap.get(element)).isToolAtomicallyExtractable()) {
        extractableToolType.add(element);
      }
    }
    return extractableToolType.toArray(new String[0]);
  }

  /**
   * Initialization of artifacts description loader
   */
  public void init() {
    // Clear maps.
    // This is important as the configuration directories might have changed, and must be taken into account again.
    _artefactsMap.clear();
    _extractableFromTool.clear();
    // Get the PapeeteDoc config dir
    String papeeteDocConfig = EnvironmentVariablesLoader.getInstance().getConfig();
    File fileToInspect = null;
    if (null != papeeteDocConfig) {
      fileToInspect = new File(papeeteDocConfig);
    }
    if ((null == fileToInspect) || !fileToInspect.exists()) {
      StringBuilder loggerMessage = new StringBuilder(".init() _ "); //$NON-NLS-1$
      String message = null;
      if (null == fileToInspect) {
        message = Messages.ArtefactsDescriptionLoader_NoDirectoryProvided;
      } else {
        message = MessageFormat.format(Messages.ArtefactsDescriptionLoader_DirectoryDoesNotExist, fileToInspect.getAbsolutePath());
      }
      loggerMessage.append(message);
      OrchestraExplorerActivator.getDefault().logMessage(loggerMessage.toString(), IStatus.WARNING, null);
      return;
    }
    loadArtefactsDescription(papeeteDocConfig);
    generateToolTypesForExtractorPage();
    if (_artefactsMap.isEmpty()) {
      StringBuilder loggerMessage = new StringBuilder(".init() _ "); //$NON-NLS-1$
      loggerMessage.append(MessageFormat.format(Messages.ArtefactsDescriptionLoader_NoArtefactsDescriptionInFiles, fileToInspect.getAbsolutePath()));
      OrchestraExplorerActivator.getDefault().logMessage(loggerMessage.toString(), IStatus.WARNING, null);
    }
  }

  /**
   * Load recursively artifacts description
   * @param directoryPath
   */
  private void loadArtefactsDescription(String directoryPath) {
    loadCustomArtefactsDescription(directoryPath);
    File[] filesToInspect = new File(directoryPath).listFiles();
    if (null == filesToInspect)
      return;
    for (int i = 0; i < filesToInspect.length; i++) {
      if (filesToInspect[i].isDirectory()) {
        loadArtefactsDescription(filesToInspect[i].getAbsolutePath());
      }
    }
  }

  /**
   * Load artifacts description
   */
  private void loadCustomArtefactsDescription(String directoryToInspect) {
    File input = null;
    File[] filesToInspect = new File(directoryToInspect).listFiles();
    String fileName = null;
    if (null == filesToInspect)
      return;
    for (int i = 0; i < filesToInspect.length; i++) {
      fileName = filesToInspect[i].getName();
      if (!filesToInspect[i].isDirectory()) {
        IPath path = new Path(fileName);
        if (fileName.startsWith("artifacts_description") //$NON-NLS-1$
            && "xml".equalsIgnoreCase(path.getFileExtension())) { //$NON-NLS-1$
          input = filesToInspect[i];
          try {
            if (input.exists()) {
              InputSource inputSource = new InputSource(input.getAbsolutePath());
              ArtefactDescriptionHandler artifactDescHandler = new ArtefactDescriptionHandler();
              SAXParser parser = new SAXParser();
              parser.setFeature("http://xml.org/sax/features/validation", //$NON-NLS-1$
                  false);
              parser.setErrorHandler(artifactDescHandler);
              parser.setContentHandler(artifactDescHandler);
              parser.parse(inputSource);
              Map<String, IArtefactTypeDescription> _artifactsCustomMap = artifactDescHandler.getMap();
              _artefactsMap = mergeMap(_artefactsMap, _artifactsCustomMap);
            }
          } catch (Throwable ex) {
            OrchestraExplorerActivator.getDefault().logMessage(MessageFormat.format(Messages.ArtefactsDescriptionLoader_ParsingError, input.getAbsolutePath()),
                IStatus.ERROR, ex);
          }
        }
      }
    }
  }

  /**
   * This method merges the custom map and the common map.
   */
  private Map<Object, Object> mergeMap(Map<Object, Object> common, Map<?, ?> custom) {
    Map<Object, Object> mergedMap = new HashMap<Object, Object>();
    mergedMap.putAll(custom);
    Set<?> keySet = custom.keySet();
    for (Iterator<?> iter = keySet.iterator(); iter.hasNext();) {
      String element = (String) iter.next();
      String tool1 = element.substring(0, element.indexOf(".")); //$NON-NLS-1$
      // Effacement des objets de l'outil non spécifiés
      Set<Object> keySet2 = common.keySet();
      String[] keySetToDel = keySet2.toArray(new String[0]);
      for (int i = 0; i < keySetToDel.length; i++) {
        String element2 = keySetToDel[i];
        String tool2 = element2.substring(0, element2.indexOf(".")); //$NON-NLS-1$
        if (tool1.equalsIgnoreCase(tool2)) {
          common.remove(element2);
        }
      }
    }
    mergedMap.putAll(common);
    return mergedMap;
  }

  /**
   * this method return a instance of a artifacts description loader
   */
  public static ArtefactsDescriptionLoader getInstance() {
    if (_instance == null) {
      _instance = new ArtefactsDescriptionLoader();
      _instance.init();
    }

    return _instance;
  }
}