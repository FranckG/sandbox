/**
 * <p>
 * Copyright (c) 2002-2008 : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Société : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Thales Part Number 46-475-668
 * </p>
 */
package com.thalesgroup.orchestra.mozart.automatic_assembly.doors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.thalesgroup.themis.papeetedoc.api.automatic_assembly.implementation.ProgrammaticAssemblyDefault;
import com.thalesgroup.themis.papeetedoc.api.automatic_assembly.implementation.exceptions.AutomaticAssemblyException;
import com.thalesgroup.themis.papeetedoc.api.automatic_assembly.interfaces.IAutomaticAssemblyContext;
import com.thalesgroup.themis.papeetedoc.api.exceptions.APIException;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IExtractedFragment;
import com.thalesgroup.themis.papeetedoc.api.publication.utils.LogUtils;
import com.thalesgroup.themis.papeetedoc.api.publication.utils.StringUtils;
import com.thalesgroup.themis.papeetedoc.api.utils.ElementUtils;
/**
 * <p>
 * Title : ProgrammaticAssemblyAssociatedRequirements
 * </p>
 * <p>
 * Description : Create the assembly elements of the associated requirements of the parent of this container. The parent
 * shall be an assembly fragment.
 * </p>
 * @author Mozart Developer
 * @version 1.0 - Orchestra V4.4 / Mozart V3.10.5
 */
public class ProgrammaticAssemblyAssociatedRequirements extends ProgrammaticAssemblyDefault {
	public static String _LINK_TYPES_PROPERTY = "linkTypes";
	public static String _ASSOCIATED_FRAGMENT_TYPE_STARTS_WITH_PROPERTY = "associatedFragmentTypeStartsWith";
	public static String _SORT_ASSOCIATED_REQUIREMENTS_PROPERTY = "sortAssociatedRequirements";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.thalesgroup.themis.papeetedoc.api.automatic_assembly.interfaces.IProgrammaticAssembly#doAutomaticAssembly(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement,
	 *      com.thalesgroup.themis.papeetedoc.api.automatic_assembly.interfaces.IAutomaticAssemblyContext)
	 */
	public List<IAssemblyElement> doAutomaticAssembly(IAssemblyElement container,
			IAutomaticAssemblyContext automaticAssemblyContext) throws AutomaticAssemblyException {
		//
		IElement parent = container.getParent();
		if (parent == null) {
			String message = "The automatic assembly container '" + container.getInternalFullName() + "' has no parent !";
			LogUtils.write(LogUtils.ERROR_MESSAGE, LogUtils.DEST_CONSOLE_AND_SESSION_LOG_AND_PROJECT_LOG, message);
			return new ArrayList<IAssemblyElement>(0);
		}
		IExtractedFragment fragment = ((IAssemblyElement) parent).getExtractedFragment();
		if (fragment == null) {
			String message = "The parent of the automatic assembly container '" + container.getInternalFullName()
					+ "' is not an assembly fragment:\n'" + parent.getInternalFullName() + "'";
			LogUtils.write(LogUtils.ERROR_MESSAGE, LogUtils.DEST_CONSOLE_AND_SESSION_LOG_AND_PROJECT_LOG, message);
			return new ArrayList<IAssemblyElement>(0);
		}
		//
		// Retrieve the assembly properties
		String linkTypes = container.getPropertyValue(_LINK_TYPES_PROPERTY);
		if (linkTypes == null) {
			linkTypes = "";
		}
		String associatedFragmentTypeStartsWith = container.getPropertyValue(_ASSOCIATED_FRAGMENT_TYPE_STARTS_WITH_PROPERTY);
		if (associatedFragmentTypeStartsWith == null) {
			associatedFragmentTypeStartsWith = "";
		}
		boolean sortAssociatedRequirements = false;
		try {
			sortAssociatedRequirements = Boolean.parseBoolean(container
					.getPropertyValue(_SORT_ASSOCIATED_REQUIREMENTS_PROPERTY));
		} catch (Exception e) {}
		//
		// Search the linked objects
		List<IExtractedFragment> satisfiedObjects = getSatisfiedObjects(fragment, associatedFragmentTypeStartsWith,
				linkTypes, sortAssociatedRequirements);
		//
		// Retrieve the publication contexts property
		// (typeId1=publicationContextId1, typeId2=publicationContextId2, ...)
		String publicationContextsProperty = container.getPropertyValue(_PUBLICATION_CONTEXTS_PROPERTY);
		if (publicationContextsProperty == null) {
			publicationContextsProperty = "";
		}
		Map<String, String> typeId2PublicationContextIds = buildType2PublicationContext(publicationContextsProperty,
				container);
		//
		// Retrieve the automatic assembly containers property
		// (myContainerId1, myContainerId2, ...)
		String automaticAssemblyContainerIdsProperty = container
				.getPropertyValue(_AUTOMATIC_ASSEMBLY_CONTAINER_IDs_PROPERTY);
		if (automaticAssemblyContainerIdsProperty == null) {
			automaticAssemblyContainerIdsProperty = "";
		}
		List<String> automaticAssemblyContainerIds = StringUtils.buildStringListFromString(
				automaticAssemblyContainerIdsProperty, ",");
		automaticAssemblyContainerIds = ElementUtils.trim(automaticAssemblyContainerIds);
		//
		// Create the assembly nodes
		List<IAssemblyElement> assemblySatisfiedObjects = createAssemblyElements(satisfiedObjects, false,
				typeId2PublicationContextIds, automaticAssemblyContainerIds, automaticAssemblyContext);
		//		
		return assemblySatisfiedObjects;
	}

	/**
	 * @param startWithType filter on objects that satisfies this (e.g. "Doors" means all types starting by "Doors" so all
	 *            Doors types)
	 * @param linkTypes list of types separated by a "," (e.g. "DoorsSM,satisfies")
	 * @param sortByLabel if true sort by label
	 * @return the list of IExtractedFragment elements corresponding to objects (types starting with the given startWithType)
	 *         that are satisfied by this
	 * @throws AutomaticAssemblyException
	 */
	public List<IExtractedFragment> getSatisfiedObjects(IExtractedFragment fragment, String startWithType, String linkTypes,
			boolean sortByLabel) throws AutomaticAssemblyException {
		List<IExtractedFragment> filteredLinkTargetFragments = new ArrayList<IExtractedFragment>();
		try {
			List<String> linkTypesList = StringUtils.buildStringListFromString(linkTypes, ",");
			linkTypesList = ElementUtils.trim(linkTypesList);
			List<IExtractedFragment> linkTargetFragments = fragment.getLinkTargetFragments(linkTypesList, null);
			for (IExtractedFragment linkTargetFragment : linkTargetFragments) {
				String linkTargetFragmentType = linkTargetFragment.getType();
				if (linkTargetFragmentType != null && linkTargetFragmentType.startsWith(startWithType)) {
					filteredLinkTargetFragments.add(linkTargetFragment);
				}
			}
		} catch (APIException e) {
			throw new AutomaticAssemblyException(e);
		}
		if (sortByLabel) {
			Collections.sort(filteredLinkTargetFragments, new Comparator<IExtractedFragment>() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see java.util.Comparator#compare(java.lang.Object,
				 *      java.lang.Object)
				 */
				public int compare(IExtractedFragment arg0, IExtractedFragment arg1) {
					IExtractedFragment e0 = (IExtractedFragment) arg0;
					IExtractedFragment e1 = (IExtractedFragment) arg1;
					String name0 = e0.getName();
					String name1 = e1.getName();
					return name0.compareToIgnoreCase(name1);
				}
			});
		}
		return filteredLinkTargetFragments;
	}
}
