 /** 
  * <p>THALES Services - Engineering & Process Management</p>
  * <p>THALES Part Number 16 262 601</p>
  * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
  */

package com.thalesgroup.orchestra.framework.oe.ui.preferences;

import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <p>
 * Title : PreferenceHandler
 * </p>
 * <p>
 * Description : The class to parse the preference xml file
 * </p>
 * 
 * @author Orchestra Framework Tool Suite developer
 * @version 3.7.0
 */
public class PreferenceHandler extends DefaultHandler {
	private Map<String, Preference> _map;
	private String _section;
	private Preference preference;

	/**
	 * PreferenceHandler Constructor
	 */
	public PreferenceHandler() {
		_map = new HashMap<String, Preference>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
  public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("SECTION".equalsIgnoreCase(qName)) {//$NON-NLS-1$
			_section = attributes.getValue("name");//$NON-NLS-1$
		}
		if ("PREFERENCE".equalsIgnoreCase(qName)) {//$NON-NLS-1$
			String id = attributes.getValue("id");//$NON-NLS-1$
			String name = attributes.getValue("name");//$NON-NLS-1$
			String possibleValues = attributes.getValue("possibleValues");//$NON-NLS-1$
			String defaultValue = attributes.getValue("defaultValue");//$NON-NLS-1$
			preference = new Preference(id, name, possibleValues, defaultValue);
			preference.setSection(_section);
			_map.put(preference.getId(), preference);
		}
	}

	/**
	 * Returns the map of the preferences found in the preference file
	 * 
	 * @return map
	 */
	public Map<String, Preference> getPreferences() {
		return _map;
	}
}
