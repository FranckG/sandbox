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
/**
 * <p>Titre : XMLTags</p>
 * <p>Description : All XML constants for Association xml files
 * @author Themis developer
 * @version 3.1
 */
@SuppressWarnings("nls")
final class XMLTags {
	/**
	 * tag artifact1 begin
	 */
	public static final String _ASSOCIATION_ARTIFACT1_BEG = "<artifact1 "; 
	/**
	 * tag artifact1 end
	 */
	public static final String _ASSOCIATION_ARTIFACT1_END = "</artifact1>"; 
	/**
	 * tag artifact2 begin
	 */
	public static final String _ASSOCIATION_ARTIFACT2_BEG = "<artifact2 "; 
	/**
	 * tag artifact2 end
	 */
	public static final String _ASSOCIATION_ARTIFACT2_END = "</artifact2>"; 
	// Association
	/**
	 * tag association begin
	 */
	public static final String _ASSOCIATION_ASSOCIATION_BEG = "<association "; 
	/**
	 * tag association end
	 */
	public static final String _ASSOCIATION_ASSOCIATION_END = "</association>"; 
	/**
	 * attribut balanced concat with '='
	 */
	public static final String _ASSOCIATION_BALANCED = "balanced="; 
	/**
	 * attribut relational_type concat with '='
	 */
	public static final String _ASSOCIATION_RELATIONAL_TYPE = "relational_type="; 
	/**
	 * attribut tool concat with '='
	 */
	public static final String _ASSOCIATION_TOOL = "tool="; 
	/**
	 * attribut type concat with '='
	 */
	public static final String _ASSOCIATION_TYPE = "type="; 
	/**
	 * attribut valid concat with '='
	 */
	public static final String _ASSOCIATION_VALID = "valid="; 
	/**
	   * This is the reference of the parser 
	   * (org.apache.xerces.parsers.SAXParser)
	   */
	public static final String _CLASSNAME = "org.apache.xerces.parsers.SAXParser";
	/**
	 * attribut id concat with '='
	 */
	public static final String _ID = "id=";
	/**
	 * String quote
	 */
	public static final String _QUOTE = "\"";
	/**
	 * String space
	 */
	public static final String _SPACE = " ";
	/**
	 * String tabulation
	 */
	public static final String _TAB = "\t";
	/**
	 * tag association_list begin
	 */
	public static final String _TAGASSOLIST = "  <association_list>";
	/**
	* Tag for close 
	*/
	public static final String _TAGCLOSE = ">";
	/**
	 * tag association_list end
	 */
	public static final String _TAGENDASSOLIST = "  </association_list>";
	// Dtd
	/**
	 * Beginning of a XML file
	 */
	public static final String _TAGXML = "<?xml version = \"1.0\" encoding=\"ISO-8859-1\" ?>";
	/**
	* Extension for all Transcription File : .xml
	*/
	public static final String _TRANSCRIPTION_EXTENSION_XML = ".XML";
	/**
	 * This is the reference of the validation property for SAXParser
	 * "http://xml.org/sax/features/validation" 
	 */
	public static final String _VALIDATIONPROPERTY = "http://xml.org/sax/features/validation";
	/**
	* Constructor for PapeeteXML.
	*/
	private XMLTags() {
	  // Just to avoid any constructor calls
	}
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Create the 3 XML tag : version, dtd, association_list
	 * 
	 * @param dtdPath
	 *            The path of the dtd reference of the file
	 * @return the tag The 3 tags formatted in XML
	 */
	public static String CreateBeginTag(String iDtdPath) {
		StringBuffer path = new StringBuffer(_TAGXML);
		path.append(UtilFunction._LINE_SEPARATOR);
		path.append(iDtdPath);
		path.append(UtilFunction._LINE_SEPARATOR);
		path.append(_TAGASSOLIST);
		path.append(UtilFunction._LINE_SEPARATOR);
		return path.toString();
	}
	/**
	 * Method createEndTag. This method returns the end tag association_list of
	 * a document
	 * 
	 * @return String end tag association_list
	 */
	public static String CreateEndTag() {
		return _TAGENDASSOLIST;
	}
}