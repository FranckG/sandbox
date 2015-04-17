/**
 * <p>
 * Copyright (c) 2002-2010 : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Société : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Thales Part Number 46-475-668
 * </p>
 */
package com.thalesgroup.orchestra.mozart.generation.doors.handlers;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.sun.star.text.XTextDocument;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IElement;
import com.thalesgroup.themis.papeetedoc.api.publication.implementation.exceptions.PublicationHandlerException;
import com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext;
import com.thalesgroup.themis.papeetedoc.api.publication.utils.LogUtils;
import com.thalesgroup.themis.papeetedoc.api.publication.utils.OpenOfficeDocumentUtils;
/**
 * <p>
 * Title : DoorsHierarchicalObjectPublicationHandler
 * </p>
 * <p>
 * Description : Enable to process a hierarchy of Doors objects (whatever their real type in Doors i.e. object, node,
 * requirement, ...).<br>
 * Generation of a heading for non terminal node (a node which contains children that are not Doors tables). Some
 * specificities:<br>
 * - Non terminal node and there is a label (not empty) => heading generated<br>
 * - Non terminal node and there is no label (label empty): <br>
 * - if publication attribute "headingIfNoLabelForNonTerminalElement" is set to false (default) => no heading generated +
 * warning<br>
 * - if publication attribute "headingIfNoLabelForNonTerminalElement" is set to true => heading generated <br>
 * - Terminal node and there is no label (label empty) => no heading generated <br>
 * - Terminal node and there is a label (not empty)<br>
 * - if publication attribute "headingIfLabelForTerminalElement" is set to false (default) => no heading generated <br>
 * - if publication attribute "headingIfLabelForTerminalElement" is set to true => heading generated <br>
 * </p>
 * @author Orchestra developer
 * @version 4.0.0
 */
public class HandlerDoorsHierarchicalObject extends HandlerDoors {
	@Override
	protected VelocityContext getVelocityContext(IAssemblyElement iElement, IPublicationContext iContext) {
		VelocityContext velocityContext = super.getVelocityContext(iElement, iContext);
		velocityContext.put("headingToGenerate", generateHeading(iElement, 2));
		return velocityContext;
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.publication.handlers.custom.velocity.VelocityDefaultPublicationHandler#headerBeforeTemplateCall(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, java.io.File, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	protected void headerBeforeTemplateCall(IAssemblyElement iElement, XTextDocument iDocument, File iTemplateFile,
			IPublicationContext iContext) throws PublicationHandlerException {
		super.headerBeforeTemplateCall(iElement, iDocument, iTemplateFile, iContext);
		//
		if (generateHeading(iElement, 1)) {
			iContext.incHeadingLevel();
		}
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.api.publication.implementation.template_technology.GenericTemplateTechnologyPublicationHandler#headerAfterTemplateCall(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, java.io.File, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	protected void headerAfterTemplateCall(IAssemblyElement iElement, XTextDocument iDocument, File iTemplateFile,
			IPublicationContext iContext) throws PublicationHandlerException {
		super.headerAfterTemplateCall(iElement, iDocument, iTemplateFile, iContext);
		//
		OpenOfficeDocumentUtils.setPDocInternalCarriageReturn(iDocument);
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.publication.handlers.custom.GenericTemplateTechnologyPublicationHandler#providePublishableFooter(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	public void providePublishableFooter(IAssemblyElement iElement, XTextDocument iDocument, IPublicationContext iContext)
			throws PublicationHandlerException {
		super.providePublishableFooter(iElement, iDocument, iContext);
		//
		if (generateHeading(iElement, 3)) {
			iContext.decHeadingLevel();
		}
	}

	// A terminal Doors element if there are either no children or only Doors tables as children
	protected boolean isTerminalDoorsElement(IAssemblyElement iElement) {
		List<IElement> children = iElement.getChildren();
		for (IElement child : children) {
			if (!"DoorsTable".equals(child.getType())) {
				return false;
			}
		}
		return true;
	}

	// Return the list of Doors children that are not Doors table 
	protected List<IElement> getDoorsChildren(IAssemblyElement iElement) {
		List<IElement> doorsChildren = new ArrayList<IElement>();
		List<IElement> children = iElement.getChildren();
		if (children != null) {
			for (Iterator<IElement> iter = children.iterator(); iter.hasNext();) {
				IElement element = (IElement) iter.next();
				String type = element.getType();
				if (type.startsWith("Doors") && !"DoorsTable".equals(type)) {
					doorsChildren.add(element);
				}
			}
		}
		return doorsChildren;
	}

	// See algorithm above
	protected boolean generateHeading(IAssemblyElement iElement, int callerNumber) {
		boolean headingToGenerate = false;
		boolean terminalDoorsElement = isTerminalDoorsElement(iElement);
		String label = iElement.getName().trim();
		boolean hasLabel = !"".equals(label);
		//
		if (terminalDoorsElement) {
			// Terminal element
			if (hasLabel) {
				headingToGenerate = headingIfLabelForTerminalElement(iElement);
			}
			else {
				headingToGenerate = false;
			}
		}
		else {
			// Non terminal element
			if (hasLabel) {
				headingToGenerate = true;
			}
			else {
				headingToGenerate = headingIfNoLabelForNonTerminalElement(iElement);
				//
			}
		}
		//
		return headingToGenerate;
	}

	//
	protected boolean headingIfNoLabelForNonTerminalElement(IAssemblyElement iElement) {
		boolean result = false;
		String value = iElement.getInheritedPublicationAttributeValue("headingIfNoLabelForNonTerminalElement");
		if (value != null) {
			result = value.equalsIgnoreCase("true");
		}
		return result;
	}

	//
	protected boolean headingIfLabelForTerminalElement(IAssemblyElement iElement) {
		boolean result = false;
		String value = iElement.getInheritedPublicationAttributeValue("headingIfLabelForTerminalElement");
		if (value != null) {
			result = value.equalsIgnoreCase("true");
		}
		return result;
	}
}
