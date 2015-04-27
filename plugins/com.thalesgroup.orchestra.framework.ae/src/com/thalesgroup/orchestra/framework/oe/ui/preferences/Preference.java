 /** 
  * <p>THALES Services - Engineering & Process Management</p>
  * <p>THALES Part Number 16 262 601</p>
  * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
  */

package com.thalesgroup.orchestra.framework.oe.ui.preferences;

/**
 * <p>
 * Title : Preference
 * </p>
 * <p>
 * Description : Class for the preference page properties
 * </p>
 * 
 * @author  Orchestra Framework Tool Suite developer
 * @version 3.7.0
 */
public class Preference {
	private String _id;
	private String _name;
	private String _possibleValues;
	private String _defaultValue;
	private String _section;

	/**
	 * Preference Constructor
	 */
	public Preference(String id, String name, String possibleValues,
			String defaultValue) {
		_id = id;
		_name = name;
		_possibleValues = possibleValues;
		_defaultValue = defaultValue;
	}

	/**
	 * @return
	 */
	public String getId() {
		return _id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		_id = id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * @return
	 */
	public String getPossibleValues() {
		return _possibleValues;
	}

	/**
	 * @param possibleValues
	 */
	public void setPossibleValues(String possibleValues) {
		_possibleValues = possibleValues;
	}

	/**
	 * @return
	 */
	public String getDefaultValue() {
		return _defaultValue;
	}

	/**
	 * @param defaultValue
	 */
	public void setDefaultValue(String defaultValue) {
		_defaultValue = defaultValue;
	}

	/**
	 * @return
	 */
	public String getSection() {
		return _section;
	}

	/**
	 * @param section
	 */
	public void setSection(String section) {
		_section = section;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
  public String toString() {
		StringBuilder label = new StringBuilder("section : "); //$NON-NLS-1$
		label.append(_section);
		label.append("\nname : ");//$NON-NLS-1$
		label.append(_name);
		label.append("\npossible values : ");//$NON-NLS-1$
		label.append(_possibleValues);
		label.append("\ndefault value : ");//$NON-NLS-1$
		label.append(_defaultValue);
		return label.toString();
	}
}
