/**
 * <p>Copyright (c) 2002-2005 :  Thales Research & Technology</p>
 * <p>Société : Thales Research & Technology</p>
 * <p>Thales Part Number 46-475-668</p>
 */
package com.thalesgroup.orchestra.mozart.generation.doors.handlers;

import com.sun.star.text.XTextDocument;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement;
import com.thalesgroup.themis.papeetedoc.api.publication.implementation.exceptions.PublicationHandlerException;
import com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext;

/**
 * <p>
 * Title : DoorsReqTabularPublicationHandler
 * </p>
 * <p>
 * Description : Doors Requirement Tabular Mode Publication Handler by means of Velocity - PFS-007
 * Reference: "PUBLICATION FORMATS SPECIFICATION for PAPEETEDOC", Issue 02, Thav.
 * </p>
 * 
 * @author Themis developer
 * @version 1.0.1
 */
public class HandlerDoorsReqTabular extends HandlerDoors {
	
	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.api.publication.implementation.template_technology.GenericTemplateTechnologyPublicationHandler#providePublishableHeader(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	public void providePublishableHeader(IAssemblyElement iElement, XTextDocument iDocument, IPublicationContext iContext)
	throws PublicationHandlerException {
		super.providePublishableHeader(iElement, iDocument, iContext);
		iContext.setChildrenMuteForTypeName(iElement.getId(),"Doors");
	}
	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.api.publication.implementation.PublicationHandlerAdapter#providePublishableFooter(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	public void providePublishableFooter(IAssemblyElement iElement, XTextDocument iDocument, IPublicationContext iContext)
	throws PublicationHandlerException {
		iContext.removeChildrenMuteForTypeName(iElement.getId(),"Doors");
		super.providePublishableFooter(iElement, iDocument, iContext);
	}
	
}