/**
 * <p>
 * Copyright (c) 2002-2010 : Thales Research & Technology
 * </p>
 * <p>
 * Société : Thales Research & Technology
 * </p>
 * <p>
 * Thales Part Number 46-475-668
 * </p>
 */
package com.thalesgroup.orchestra.mozart.generation.doors.velocity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.thalesgroup.orchestra.mozart.generation.generic.utils.UtilsElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IExtractedFragment;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.ILinkedElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IProperty;
import com.thalesgroup.themis.papeetedoc.api.publication.implementation.template_technology.velocity.VelocityElement;
import com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext;
import com.thalesgroup.themis.papeetedoc.api.publication.utils.StringUtils;
/**
 * <p>
 * Title : VelocityStdDoorsElement
 * </p>
 * <p>
 * Description : Velocity for Standard Element. Specific to Velocity templates related to Standard publication.
 * </p>
 * @author Orchestra developer
 * @version 4.0.0
 */
public class VelocityElementDoors extends VelocityElement {
	protected static final String PUBLICATION_ATTRIBUTE_NAME_FOR_NODE_REFERENCES = "referencesToDoorsNodes";
	protected static final String NOT_SORTED = "References not sorted";
	protected static final String SORTED_BY_NODE_TYPE = "References sorted by node type";
	protected static final String SORTED_ALPHABETICALLY = "References sorted alphabetically";

	/**
	 * VelocityStdDoorsElement Constructor
	 * @param iAssemblyElement
	 */
	public VelocityElementDoors(IAssemblyElement iAssemblyElement, IPublicationContext iContext) {
		super(iAssemblyElement, iContext);
	}

	/**
	 * Get the node user id (IE PUID)
	 * @return The user id formatted - encoded
	 */
	public String getNodeUserId() {
		String id = _extractedFragment.getPropertyValue("IE PUID");
		if (id == null) {
			id = "";
		}
		id = getStringWithoutHook(id);
		return getOpenOfficeLabel(id, true);
	}

	/**
	 * Get the node title (label in assembler)
	 * @return The title formatted - String to be inserted inside a Velocity template
	 */
	public String getNodeTitle() {
		String title = _assemblyElement.getName().trim();
		if (title == null) {
			title = "";
		}
		return getOpenOfficeText(title);
	}

	/**
	 * Get the node title (label in assembler) if the title is different from the id
	 * @return The title formatted - String to be inserted inside a Velocity template
	 */
	public String getNodeTitleIfDifferentFromNodeId() {
		String title = getNodeTitle();
		String id = getNodeUserId();
		if (id.equals(title)) {
			title = getOpenOfficeText("");
		}
		return title;
	}

	/**
	 * Get the node title (label in assembler) if the title is different from the id and not start with the id If the node
	 * title starts with the node id, then substring
	 * @return The title formatted - String to be inserted inside a Velocity template
	 */
	public String getNodeTitleIfDifferentFromNodeIdAndNotStartWithNodeId() {
		String title = getNodeTitleIfDifferentFromNodeId();
		String id = getNodeUserId();
		if (title.startsWith(id)) {
			title = title.replaceFirst(id, "").trim();
		}
		return title;
	}

	/**
	 * Get the section title
	 * @return The section title
	 */
	public String getSectionTitle() {
		return getNodeTitle();
		//		return getNodeUserIdAndTitle();
	}

	/**
	 * Get the IE PUID + Label in assembler
	 * @return The section title
	 */
	public String getNodeUserIdAndTitle() {
		StringBuffer sectionTitle = new StringBuffer();
		String userId = getNodeUserId();
		String title = getNodeTitle();
		if (!userId.equals("")) {
			String label = UtilsElement.getNameFromFullName(userId);
			sectionTitle.append(label);
			if (label.trim().equals(title.trim())) {
				return sectionTitle.toString();
			}
			if (!title.equals("")) {
				sectionTitle.append(" : ");
			}
		}
		if (!title.equals("")) {
			sectionTitle.append(title);
		}
		return sectionTitle.toString();
	}

	/**
	 * Get OpenOffice formatted textual and file descriptions such as : <text:p>...</text:p>
	 * @param iStyle the style to apply for textual description
	 * @return The textual and/or file description(s) - String to be inserted inside a Velocity template
	 */
	public String getNodeText(String iStyle) {
		return getExtractedFragmentDescriptions(iStyle);
	}

	/**
	 * Get OpenOffice formatted textual and file descriptions such as : <text:p>...</text:p>
	 * @param iStyle the style to apply for textual description
	 * @return The textual and/or file description(s) - String to be inserted inside a OpenOffice template
	 */
	public String getNodeText() {
		return getExtractedFragmentDescriptions();
	}

	/**
	 * Get OpenOffice formatted textual and file descriptions such as : <text:p>...</text:p> for the node and his children
	 * (except Doors tables/rows)
	 * @param iStyle the style to apply for textual description
	 * @return The textual and/or file description(s) - String to be inserted inside a OpenOffice template
	 */
	public String getNodeTextWithChildren(String iStyle) {
		// If "PublishOnlyChildrenDescriptions" exists and is true, then return only the node text of children not of this if at least one Doors child
		String parameterPublishOnlyChildrenDescriptions = _assemblyElement
				.getPublicationContextParameter("PublishOnlyChildrenDescriptions");
		if (parameterPublishOnlyChildrenDescriptions != null
				&& StringUtils.getBooleanValueOfString(parameterPublishOnlyChildrenDescriptions)) {
			boolean atLeastOneDoorsElementNotTable = false;
			List<IElement> children = getAssemblyElement().getChildren(true, false, false);
			for (IElement child : children) {
				if (isDoorsElement((IAssemblyElement) child) && !isDoorsTableElement((IAssemblyElement) child)) {
					atLeastOneDoorsElementNotTable = true;
					break;
				}
			}
			if (atLeastOneDoorsElementNotTable) {
				return getChildrenNodeText(iStyle);
			}
		}
		StringBuffer childrenNodeText = new StringBuffer(getNodeText(iStyle));
		List<VelocityElementDoors> children = getAllVelocityTrtDoorsElementChildren();
		for (Iterator<VelocityElementDoors> iter = children.iterator(); iter.hasNext();) {
			VelocityElementDoors velocityChild = iter.next();
			if (!isDoorsTableElement(velocityChild.getAssemblyElement())) {
				childrenNodeText.append(velocityChild.getNodeText(iStyle));
			}
		}
		return childrenNodeText.toString();
	}

	public String getNodeTextWithChildren() {
		// If "PublishOnlyChildrenDescriptions" exists and is true, then return only the node text of children not of this if at least one Doors child
		String parameterPublishOnlyChildrenDescriptions = _assemblyElement
				.getPublicationContextParameter("PublishOnlyChildrenDescriptions");
		if (parameterPublishOnlyChildrenDescriptions != null
				&& StringUtils.getBooleanValueOfString(parameterPublishOnlyChildrenDescriptions)) {
			boolean atLeastOneDoorsElementNotTable = false;
			List<IElement> children = getAssemblyElement().getChildren(true, false, false);
			for (IElement child : children) {
				if (isDoorsElement((IAssemblyElement) child) && !isDoorsTableElement((IAssemblyElement) child)) {
					atLeastOneDoorsElementNotTable = true;
					break;
				}
			}
			if (atLeastOneDoorsElementNotTable) {
				return getChildrenNodeText();
			}
		}
		StringBuffer childrenNodeText = new StringBuffer(getNodeText());
		List<VelocityElementDoors> children = getAllVelocityTrtDoorsElementChildren();
		for (Iterator<VelocityElementDoors> iter = children.iterator(); iter.hasNext();) {
			VelocityElementDoors velocityChild = iter.next();
			if (!isDoorsTableElement(velocityChild.getAssemblyElement())) {
				childrenNodeText.append(velocityChild.getNodeText());
			}
		}
		return childrenNodeText.toString();
	}

	/**
	 * Get OpenOffice formatted textual and file descriptions such as : <text:p>...</text:p> for the children only (except
	 * Doors tables/rows)
	 * @param iStyle the style to apply for textual description
	 * @return The textual and/or file description(s) - String to be inserted inside a OpenOffice template
	 */
	public String getChildrenNodeText(String iStyle) {
		StringBuffer childrenNodeText = new StringBuffer();
		List<VelocityElementDoors> children = getAllVelocityTrtDoorsElementChildren();
		for (Iterator<VelocityElementDoors> iter = children.iterator(); iter.hasNext();) {
			VelocityElementDoors velocityChild = iter.next();
			if (!isDoorsTableElement(velocityChild.getAssemblyElement())) {
				childrenNodeText.append(velocityChild.getNodeText(iStyle));
			}
		}
		return childrenNodeText.toString();
	}

	public String getChildrenNodeText() {
		StringBuffer childrenNodeText = new StringBuffer();
		List<VelocityElementDoors> children = getAllVelocityTrtDoorsElementChildren();
		for (Iterator<VelocityElementDoors> iter = children.iterator(); iter.hasNext();) {
			VelocityElementDoors velocityChild = iter.next();
			if (!isDoorsTableElement(velocityChild.getAssemblyElement())) {
				childrenNodeText.append(velocityChild.getNodeText());
			}
		}
		return childrenNodeText.toString();
	}

	/**
	 * Get OpenOffice bookmark and formatted textual and file descriptions such as : <text:p>...</text:p>
	 * @param iStyle the style to apply for textual description
	 * @return The textual and/or file description(s) - String to be inserted inside a Velocity template
	 */
	public String getBookmarkElementAndNodeText(String iStyle) {
		StringBuffer bmkAndNodeText = new StringBuffer("<text:p text:style-name=\"" + iStyle + "\">");
		bmkAndNodeText.append(getOpenOfficeElementBookMark());
		String desc = getExtractedFragmentDescriptions(iStyle);
		int index = desc.indexOf(">");
		if (index > -1) {
			bmkAndNodeText.append(desc.substring(index + 1));
		}
		else {
			bmkAndNodeText.append("</text:p>");
		}
		return bmkAndNodeText.toString();
	}

	/**
	 * Get the node attributes list
	 * @return the List of IProperty elements without element with nature is defined by value filterNature
	 */
	public List<IProperty> getNodeAttrListFilterNature(String filterNature) {
		List<IProperty> l = new ArrayList<IProperty>();
		List<IProperty> properties = _extractedFragment.getProperties();
		for (Iterator<IProperty> iter = properties.iterator(); iter.hasNext();) {
			IProperty property = iter.next();
			if (filterNature == null || (!filterNature.equals(property.getNature()))) {
				l.add(property);
			}
		}
		return l;
	}

	/**
	 * Get the node attributes list
	 * @return the List of IProperty elements
	 */
	public List<IProperty> getAllNodeAttrList() {
		return getNodeAttrListFilterNature(null);
	}

	/**
	 * Get the list of properties that are not of nature "Description" nor "PapeeteDocInternal" and that can be published
	 * (take into account inherited attributes "publishAttributesWithNoValue" and "attributesNotToPublish"). Do not take into
	 * account attributes for which the value is equal to the node label (because attribute already published in the title)
	 * @return the List of IProperty elements without element with nature "PapeeteDocInternal"
	 */
	public List<IProperty> getNodeAttrList() {
		List<IProperty> l = getAllNodeAttrList();
		List<IProperty> lFiltered = new ArrayList<IProperty>();
		boolean publishAttributesWithNoValue = isPublishAttributesWithNoValue();
		List<String> attributesNotToPublish = getAttributesNotToPublish();
		String assemblyName = _assemblyElement.getName();
		for (Iterator<IProperty> iter = l.iterator(); iter.hasNext();) {
			IProperty property = iter.next();
			if (!"Description".equals(property.getNature()) && !"PapeeteDocInternal".equals(property.getNature())) {
				// inherited publication attribute
				// "publishAttributesWithNoValue" is set to true (or not found)
				// and not empty value ?
				String value = property.getValue();
				if (publishAttributesWithNoValue || !"".equals(value)) {
					// is the attribute to be published ?
					if (!attributesNotToPublish.contains(property.getName())) {
						// if the property value is equal to the node label => do not take it into account      
						if (value != null && !value.trim().equals(assemblyName.trim())) {
							lFiltered.add(property);
						}
					}
				}
			}
		}
		return lFiltered;
	}

	// True if the publication attribute
	// "publishAttributesWithNoValue" is equal to "true" or not found
	// (if the parent is a view or a module, it prevails)
	private boolean isPublishAttributesWithNoValue() {
		String publishAttributesWithNoValue = _assemblyElement.getPublicationAttributeValue("publishAttributesWithNoValue");
		IAssemblyElement parent = _assemblyElement.getParent();
		String parentType = parent.getDisplayableType();
		while (isDoorsElement(parent)
				&& !("Doors Module".equalsIgnoreCase(parentType) || "Doors View".equalsIgnoreCase(parentType))) {
			parent = parent.getParent();
			parentType = parent.getDisplayableType();
		}
		if (!isDoorsElement(parent)) {
			return publishAttributesWithNoValue == null || "true".equalsIgnoreCase(publishAttributesWithNoValue);
		}
		else {
			publishAttributesWithNoValue = parent.getPublicationAttributeValue("publishAttributesWithNoValue");
			return publishAttributesWithNoValue == null || "true".equalsIgnoreCase(publishAttributesWithNoValue);
		}
	}

	// Return the list of attribute names that are not to publish for the element
	// (if the parent is a view or a module, attributes of the view/module are also taken into account)
	private List<String> getAttributesNotToPublish() {
		List<String> lAttributes = new ArrayList<String>();
		String attributes = "";
		// attributes defined at view/module level
		IAssemblyElement parent = _assemblyElement.getParent();
		String parentType = parent.getDisplayableType();
		while (isDoorsElement(parent)
				&& !("Doors Module".equalsIgnoreCase(parentType) || "Doors View".equalsIgnoreCase(parentType))) {
			parent = parent.getParent();
			parentType = parent.getDisplayableType();
		}
		if (isDoorsElement(parent)) {
			attributes = parent.getPublicationAttributeValue("attributesNotToPublish");
			if (attributes != null && !"".equals(attributes)) {
				lAttributes = StringUtils.buildStringListFromString(attributes, ",");
			}
		}
		// attributes defined at node level
		List<String> lAttributes2 = new ArrayList<String>();
		attributes = _assemblyElement.getPublicationAttributeValue("attributesNotToPublish");
		if (attributes != null && !"".equals(attributes)) {
			lAttributes2 = StringUtils.buildStringListFromString(attributes, ",");
		}
		// Merge of the 2 lists removing duplicates
		for (Iterator<String> iterator = lAttributes2.iterator(); iterator.hasNext();) {
			String attribute = iterator.next();
			if (!lAttributes.contains(attribute)) {
				lAttributes.add(attribute);
			}
		}
		//
		return lAttributes;
	}

	/**
	 * Get a node property name
	 * @param property The property
	 * @return The property name - String to be inserted inside a Velocity template - encoded
	 */
	public String getNodePropertyName(IProperty property) {
		return StringUtils.getEncodedString(property.getName());
	}

	/**
	 * Get a node property value
	 * @param property The property
	 * @param style The style to apply (for other lines if any)
	 * @return The property value - String to be inserted inside a Velocity template - encoded
	 */
	public String getNodePropertyValue(IProperty property, String style) {
		if (style == null) {
			return getOpenOfficeText(property.getValue());
		}
		else {
			return getOpenOfficeText(property.getValue(), style);
		}
	}

	/**
	 * Get a node property value
	 * @param property The property
	 * @return The property value - String to be inserted inside a Velocity template - encoded
	 */
	public String getNodePropertyValue(IProperty property) {
		return getNodePropertyValue(property, null);
	}

	/**
	 * Get a node property type
	 * @param property The property
	 * @return The property type - String to be inserted inside a Velocity template - encoded
	 */
	public String getNodePropertyType(IProperty property) {
		return getOpenOfficeText(property.getType());
	}

	/**
	 * Get a node property value
	 * @param name The property name
	 * @return The property value - String to be inserted inside a Velocity template
	 */
	public String getNodePropertyValue(String name) {
		String propertyValue = getExtractedFragmentPropertyValue(name);
		if (propertyValue == null) {
			propertyValue = "";
		}
		return propertyValue;
	}

	/**
	 * This method gets the nature of the property
	 * @param property The property name
	 * @return The Nature of the property
	 */
	public String getNodePropertyNature(String name) {
		List<IProperty> l = getAllNodeAttrList();
		String propertyNature = "";
		for (Iterator<IProperty> iter = l.iterator(); iter.hasNext();) {
			IProperty element = iter.next();
			if (element.getName().equalsIgnoreCase(name)) {
				propertyNature = element.getNature();
				return propertyNature;
			}
		}
		return propertyNature;
	}

	/**
	 * Get the LINKname list for the given link type (only 'out' are considered)
	 * @param linkType ('out' only)
	 * @return the OpenOffice String made of the list of link names separated by a ";" - String to be inserted inside a
	 *         Velocity template - encoded
	 */
	public String getDisplayableLinkNames(String linkType) {
		List<String> list = getLinkNames(linkType);
		return StringUtils.buildStringFromStringList(list, "; ", false);
	}

	/**
	 * Get the sorted LINKname list for the given link type (only 'out' are considered)
	 * @param linkType ('out' only)
	 * @return the OpenOffice String made of the list of sorted link names separated by a ";" - String to be inserted inside
	 *         a Velocity template - encoded
	 */
	public String getDisplayableSortedLinkNames(String linkType) {
		List<String> list = getLinkNames(linkType);
		Collections.sort(list);
		return StringUtils.buildStringFromStringList(list, "; ", false);
	}

	/**
	 * Get the LINKname list for the given link type (only 'out' are considered)
	 * @param linkType
	 * @return The list of all LINKname for the given linkType - encoded
	 */
	public List<String> getLinkNames(String linkType) {
		List<String> list = new ArrayList<String>();
		List<ILinkedElement> linkedElements = _extractedFragment.getLinkedElements();
		for (Iterator<ILinkedElement> iter = linkedElements.iterator(); iter.hasNext();) {
			ILinkedElement linkedElement = iter.next();
			if (linkType != null && linkType.equals(linkedElement.getLinkType())
					&& "out".equals(linkedElement.getLinkDirection())) {
				String label = linkedElement.getLabel();
				list.add((label == null) ? "?" : StringUtils.getEncodedString(label));
			}
		}
		return list;
	}

	/**
	 * Get all 'out' link type
	 * @return A set of all 'out' link type - encoded
	 */
	public Set<String> getLinkTypes() {
		Set<String> linkTypeSet = new HashSet<String>();
		List<ILinkedElement> linkedElements = _extractedFragment.getLinkedElements();
		for (Iterator<ILinkedElement> iter = linkedElements.iterator(); iter.hasNext();) {
			ILinkedElement linkedElement = iter.next();
			if ("out".equals(linkedElement.getLinkDirection())) {
				linkTypeSet.add(StringUtils.getEncodedString(linkedElement.getLinkType()));
			}
		}
		return linkTypeSet;
	}

	/**
	 * Get the displayable type (ex: "Doorssatisfies" => "satisfies")
	 * @return the displayable type
	 */
	public String getDisplayableLinkType(String iLinkType) {
		String linkType = iLinkType;
		if (linkType != null && linkType.startsWith("Doors")) {
			linkType = linkType.substring(5);
		}
		return linkType;
	}

	/**
	 * Return the part of a string without hook [MyProject] => MyProject
	 * @param stringToExtract
	 * @return the part of a string without hook
	 */
	public String getStringWithoutHook(String stringToExtract) {
		String result = null;
		if (stringToExtract == null) {
			return result;
		}
		int posBegin = stringToExtract.indexOf("[");
		int posEnd = stringToExtract.lastIndexOf("]");
		if (posBegin > -1 && posEnd > -1) {
			result = stringToExtract.substring(posBegin + 1, posEnd);
		}
		else {
			result = stringToExtract;
		}
		return result;
	}

	/**
	 * Some properties may be not to be published (publication attribute "matrixColumnsNotToPublish")
	 * @return the list of properties ({@link IProperty} object) associated to this element and Column type.
	 */
	public List<IProperty> getPropertyTypeColumnList() {
		List<IProperty> resultsList = null;
		List<String> columnsNotToPublish = getColumnsNotToPublish();
		List<IProperty> extractedList = _extractedFragment.getPropertiesOfType("Column");
		if (extractedList != null && extractedList.size() != 0) {
			resultsList = new ArrayList<IProperty>();
			for (Iterator<IProperty> iter = extractedList.iterator(); iter.hasNext();) {
				IProperty property = iter.next();
				if (!columnsNotToPublish.contains(property.getName())) {
					resultsList.add(property);
				}
			}
		}
		return resultsList;
	}

	// Return the list of column names that are not to publish (deduced from
	// publication attribute "matrixColumnsNotToPublish")
	private List<String> getColumnsNotToPublish() {
		List<String> lColumns = null;
		String columns = _assemblyElement.getPublicationAttributeValue("matrixColumnsNotToPublish");
		if (columns != null) {
			lColumns = StringUtils.buildStringListFromString(columns, ",");
		}
		else {
			lColumns = new ArrayList<String>();
		}
		return lColumns;
	}

	/**
	 * @return the list of {@link VelocityElementDoors} elements which are the children and sub children of the element.
	 */
	public List<VelocityElementDoors> getAllVelocityTrtDoorsElementChildren() {
		return getAllVelocityTrtDoorsElementByType(null);
	}

	/**
	 * @return the list of {@link VelocityElementDoors} elements which are the children and sub children of the element,
	 *         whose associated {@link IExtractFragment} parent match it's parent associated {@link IExtractFragment}.
	 */
	public List<VelocityElementDoors> getAllVelocityTrtDoorsElementByType(String type) {
		final List<VelocityElementDoors> list = new ArrayList<VelocityElementDoors>();
		final List<IElement> ielementList = _assemblyElement.getChildren();
		for (IElement ielement : ielementList) {
			if (ielement instanceof IAssemblyElement) {
				final IAssemblyElement iAssemblyElement = (IAssemblyElement) ielement;
				if (isDoorsElement(iAssemblyElement) && isTrueChildOfFragment(iAssemblyElement)) {
					final VelocityElementDoors velocityElt = new VelocityElementDoors(iAssemblyElement, _publicationContext);
					if ((type != null && velocityElt.getType().equals(type)) || type == null) {
						list.add(velocityElt);
					}
					final List<VelocityElementDoors> childList = velocityElt.getAllVelocityTrtDoorsElementByType(type);
					if (childList != null && childList.size() != 0) {
						list.addAll(childList);
					}
				}
			}
		}
		final String doorsNodeReferencesSortedByValue = _assemblyElement
				.getPublicationContextParameter(PUBLICATION_ATTRIBUTE_NAME_FOR_NODE_REFERENCES);
		Comparator<VelocityElementDoors> comparator = null;
		if (SORTED_BY_NODE_TYPE.equals(doorsNodeReferencesSortedByValue)) {
			comparator = new ByNodeTypeComparator();
		}
		else if (SORTED_ALPHABETICALLY.equals(doorsNodeReferencesSortedByValue)) {
			comparator = new ByNodeNameComparator();
		}
		if (comparator != null) {
			Collections.sort(list, comparator);
		}
		return list;
	}

	/**
	 * @return true if the given element associated fragment is child of this element associated fragment
	 */
	private boolean isTrueChildOfFragment(IAssemblyElement iAssemblyElement) {
		final IExtractedFragment fragment = _assemblyElement.getExtractedFragment();
		if (fragment != null) {
			final IExtractedFragment iExtractedFragment = iAssemblyElement.getExtractedFragment();
			IExtractedFragment iExtractedFragmentParent = null;
			if (iExtractedFragment != null) {
				iExtractedFragmentParent = iExtractedFragment.getParent();
			}
			if (iExtractedFragmentParent != null && fragment.getPapeeteURI() != null
					&& fragment.getPapeeteURI().equals(iExtractedFragmentParent.getPapeeteURI())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return a String use to define if the element is visible or not
	 */
	public String getVisiblePropertyValue() {
		return getAssemblyElementPropertyValue(IAssemblyElement.ASSEMBLY_ATTRIBUTE_VISIBLE);
	}

	/**
	 * @return a String use to define if the type of the element
	 */
	@Override
	public String getType() {
		return getAssemblyElement().getType();
	}

	/**
	 * @param iAssemblyElement
	 * @return true if iAssemblyElement is a Doors element, otherwise false - based on the type (shall starts by "Doors")
	 */
	protected boolean isDoorsElement(IAssemblyElement iAssemblyElement) {
		boolean isDoorsElement = false;
		String type = iAssemblyElement.getType();
		isDoorsElement = (type != null && type.startsWith("Doors"));
		return isDoorsElement;
	}

	/**
	 * @param iAssemblyElement
	 * @return true if iAssemblyElement is a Doors Table element, otherwise false - based on the type (="DoorsTable" or
	 *         "DoorsRow")
	 */
	protected boolean isDoorsTableElement(IAssemblyElement iAssemblyElement) {
		boolean isDoorsTableElement = false;
		String type = iAssemblyElement.getType();
		isDoorsTableElement = (type != null && ("DoorsTable".equals(type) || "DoorsRow".equals(type)));
		return isDoorsTableElement;
	}

	/**
	 * Parses the string argument as a signed decimal integer
	 * @return the integer value represented by the argument in decimal.
	 */
	public int getStringToIntValue(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * Give real width for column in table which - real width is tableRealWidth - totalColumnsWidth is the total width of all
	 * columns in Doors Unit - columnWidth is the column in Doors Unit
	 * @return the integer value represented by the argument in decimal.
	 */
	public String getColumnWidth(String tableRealWidth, String totalColumnsWidth, String columnWidth) {
		try {
			double A = Double.parseDouble(tableRealWidth);
			double B = Double.parseDouble(totalColumnsWidth);
			double C = Double.parseDouble(columnWidth);
			return Double.toString(A * C / B);
		} catch (NumberFormatException e) {
			return "0";
		}
	}
	/**
	 * Comparator class used to compare 2 types of 2 VelocityStdDoorsElement elements.
	 */
	class ByNodeTypeComparator implements Comparator<VelocityElementDoors> {
		/*
		 * (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(VelocityElementDoors velocityElement0, VelocityElementDoors velocityElement1) {
			String nodeType0 = velocityElement0.getDisplayableType();
			String nodeType1 = velocityElement1.getDisplayableType();
			return nodeType0.compareToIgnoreCase(nodeType1);
		}
	}
	/**
	 * Comparator class used to compare 2 assembly names of 2 VelocityStdDoorsElement elements.
	 */
	class ByNodeNameComparator implements Comparator<VelocityElementDoors> {
		/*
		 * (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(VelocityElementDoors velocityElement0, VelocityElementDoors velocityElement1) {
			String nodeLabel0 = velocityElement0.getNodeUserIdAndTitle();
			String nodeLabel1 = velocityElement1.getNodeUserIdAndTitle();
			return nodeLabel0.compareToIgnoreCase(nodeLabel1);
		}
	}
}
