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

import java.util.Stack;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.lib.base.UtilFunction;

/**
 * <p>
 * Title : StringMapping
 * </p>
 * <p>
 * Description : This class delivers a string-mapping service. The string content can be contain a metacharacter *
 * </p>
 * @author Papeete Tool Suite developer
 * @version 3.3.x
 */
class StringMapping {
  /**
   * Meta char used in content to describe abstract element
   */
  static final String _METACHAR = "*"; //$NON-NLS-1$

  /**
   * Private Constructor
   */
  private StringMapping() {
    // Just to avoid any use of constructor.
  }

  /**
   * Method ExtractMetaContent.
   * @param iContentToExtract to extract
   * @param iRegCnt defined the regular content
   * @return String
   * @pre iRegCnt != null
   */
  private final static String ExtractMetaContent(String iCnt, Stack<String> iRegCnt) {
    String retval = null;
    iRegCnt.trimToSize();
    String[] array = null;
    // TODO Guillaume
    // TODO Eric
    // Replace with value extracted from context.
    // ConfigServer.getInstance().GetCasensitive();
    boolean isCaseSensitive = false;
    boolean isStartedWith = false;
    int prefixe = 0;
    int suffixe = 0;
    if (iRegCnt != null && iRegCnt.size() != 0) {
      // pas de cas 0 car test avant
      switch (iRegCnt.size()) {
      // cas 0 : où il y a 0 meta char
        case 0:
          System.err.println("d'après commentaire on ne devrait pas en avoir"); //$NON-NLS-1$
        break;
        // cas 1 : où il y a 1 meta char (*)
        case 1:
          retval = iCnt;
        break;
        // cas 2 : où il y a un meta char et :
        // - un préfixe dans le nom logique ou
        // - un suffixe dans le nom logique ou
        // - une extension dans le nom physique
        case 2:
          array = iRegCnt.toArray(new String[0]);
          // nom logique : prefixe_*
          if (array[0] == null) {
            // prefix_*
            prefixe = array[1].length();
            if (prefixe > 0) {
              if (isCaseSensitive) {
                isStartedWith = iCnt.startsWith(array[1]);
              } else {
                isStartedWith = iCnt.toLowerCase().startsWith(array[1].toLowerCase());
              }
              if (isStartedWith) {
                retval = iCnt.substring(prefixe);
              }
            }
          }
          // nom logique : *_suffixe
          // nom physique : *.extension
          else {
            String extension = UtilFunction.getExtensionFile(iCnt);
            if (extension == null || (isCaseSensitive ? extension.equals(array[0]) : extension.equalsIgnoreCase(array[0]))) {
              suffixe = iCnt.toLowerCase().lastIndexOf(array[0].toLowerCase());
              if (suffixe > 0) {
                retval = iCnt.substring(0, suffixe);
              }
            }
            // nom physique : */.extension
            else if (iCnt.endsWith(array[0])) {
              suffixe = iCnt.toLowerCase().lastIndexOf(array[0].toLowerCase());
              if (suffixe > 0) {
                retval = iCnt.substring(0, suffixe);
              }
            }
          }
        break;
        // cas 3 : où il y a 1 meta char avec préfixe et suffixe (logique) ou extension (physique)
        case 3:
          // nom logique : Text_*_doc
          // nom physique : Word/*.doc
          array = iRegCnt.toArray(new String[0]);
          if (isCaseSensitive) {
            isStartedWith = iCnt.startsWith(array[2]);
          } else {
            isStartedWith = iCnt.toLowerCase().startsWith(array[2].toLowerCase());
          }
          if (isStartedWith) {
            // suppression du préfixe
            String prefixeString = iCnt.substring(array[2].length());
            // vérification du suffixe
            String extension = UtilFunction.getExtensionFile(prefixeString);
            if (extension == null || (isCaseSensitive ? extension.equals(array[0]) : extension.equalsIgnoreCase(array[0]))) {
              suffixe = prefixeString.toLowerCase().lastIndexOf(array[0].toLowerCase());
              if (suffixe > 0) {
                retval = prefixeString.substring(0, suffixe);
              }
            }
            // nom physique : */.extension
            else if (prefixeString.endsWith(array[0])) {
              suffixe = prefixeString.toLowerCase().lastIndexOf(array[0].toLowerCase());
              if (suffixe > 0) {
                retval = prefixeString.substring(0, suffixe);
              }
            }
          }
        break;
        // cas default : où il y a plusieurs meta char avec préfixe et suffixe (logique) ou extension (physique)
        default:
          // Ne marche que pour les cas suivants :
          // - Models/*/*.rpy
          // - */*.rpy
          array = iRegCnt.toArray(new String[0]);
          String extension = UtilFunction.getExtensionFile(iCnt);
          if (extension == null || (isCaseSensitive ? extension.equals(array[0]) : extension.equalsIgnoreCase(array[0]))) {
            suffixe = iCnt.toLowerCase().lastIndexOf(array[0].toLowerCase());
            if (suffixe > 0) {
              if (array[array.length - 1] == null) {
                // pas de prefixe
                String content = iCnt.substring(0, suffixe);
                String[] metachar = content.split("/"); //$NON-NLS-1$
                if (metachar.length == 2 && metachar[0].equals(metachar[1])) {
                  retval = metachar[0];
                }
              } else {
                if (isCaseSensitive ? iCnt.startsWith(array[array.length - 1]) : iCnt.toLowerCase().startsWith(array[array.length - 1].toLowerCase())) {
                  String content = iCnt.substring(array[array.length - 1].length(), suffixe);
                  // */* pattern
                  String[] metachar = content.split("/"); //$NON-NLS-1$
                  if (metachar.length == 2 && metachar[0].equals(metachar[1])) {
                    retval = metachar[0];
                  }
                }
              }
            }
          }
        break;
      }
    }
    return retval;
  }

  /**
   * It extracts the meta char of a string in a stack
   * @param iMsgToFormat
   * @param iCurrIndex
   * @param iToken
   * @return Stack
   */
  private final static Stack<String> ExtractMetaStringToStack(String iMsgToFormat, String iToken, int iI) {
    int i = iI;
    Stack<String> resultTokenizeMsg = null;
    final int iNewCurrIndex = iMsgToFormat.indexOf(iToken);
    if (iNewCurrIndex == -1) {
      resultTokenizeMsg = new Stack<String>();
      if (i != 0) {
        resultTokenizeMsg.push(iMsgToFormat);
      }
    } else {
      i++;
      String subs = iMsgToFormat.substring(iNewCurrIndex + iToken.length());
      if (subs.length() > 0) {
        resultTokenizeMsg = ExtractMetaStringToStack(subs, iToken, i);
      } else {
        resultTokenizeMsg = new Stack<String>();
      }
      resultTokenizeMsg.push(null);
      if (iNewCurrIndex > 0) {
        resultTokenizeMsg.push(iMsgToFormat.substring(0, iNewCurrIndex));
      }
    }
    return resultTokenizeMsg;
  }

  /**
   * Method GetContentMetaChar. Extract the value of the metachar defined in pattern string
   * @param iContentToTranscript
   * @param iContentSourcePattern
   * @return value of metachar
   */
  private static String GetContentMetaChar(String iContentToTranscript, String iContentSourcePattern) {
    Stack<String> pile = ExtractMetaStringToStack(iContentSourcePattern, _METACHAR, 0);
    String retval = ExtractMetaContent(iContentToTranscript, pile);
    return retval;
  }

  /**
   * Method GetTranscribeContent. Transform a content to another by pattern string
   * @param iContentToTranscript
   * @param iContentSourcePattern
   * @param iContentDestinationPattern
   * @return String
   */
  static final String GetTranscribeContent(String iContentToTranscript, String iContentSourcePattern, String iContentDestinationPattern) {
    String retval = ICommonConstants.EMPTY_STRING;
    final int posStarArtefact1Name = iContentSourcePattern.indexOf(_METACHAR);
    if (posStarArtefact1Name == -1) {
      // No MetaChar detected !
      retval = iContentDestinationPattern;
    } else { // almost one MetaChar: nbMetaChar
      String metachar = GetContentMetaChar(iContentToTranscript, iContentSourcePattern);
      if (metachar != null) {
        if (iContentDestinationPattern.indexOf(_METACHAR) == -1) {
          // *->constant CR 260
          retval = iContentDestinationPattern;
        } else {
          retval = TranscriptMetaChar(iContentDestinationPattern, metachar);
        }
      }
    }
    return retval;
  }

  /**
   * It tests content with a pattern string
   * @param iContentToTranscript
   * @param iContentSourcePattern
   * @return true if iContentToTranscript match to iContentSourcePattern
   */
  static final boolean IsContentMetaChar(String iContentToTranscript, String iContentSourcePattern) {
    boolean result = false;
    String retval = GetContentMetaChar(iContentToTranscript, iContentSourcePattern);
    if (retval != null) {
      result = true;
    }
    return result;
  }

  /**
   * It get string designed by a pattern string from value of the metachar
   * @param iContentDestinationPattern
   * @param iMetachar
   * @return String
   */
  private static String TranscriptMetaChar(String iContentDestinationPattern, String iMetachar) {
    StringBuffer retval = UtilFunction.GetDefaultStringBuffer();
    Stack<?> pile = ExtractMetaStringToStack(iContentDestinationPattern, _METACHAR, 0);
    String tmp = null;
    while (!pile.empty()) {
      tmp = (String) pile.pop();
      if (UtilFunction.IsNull(tmp)) {
        retval.append(iMetachar);
      } else {
        retval.append(tmp);
      }
    }
    return retval.toString();
  }
}
