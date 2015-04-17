/**
 * <p>
 * Copyright (c) 2002-2005 : Thales Research & Technology
 * </p>
 * <p>
 * Société : Thales Research & Technology
 * </p>
 * <p>
 * Thales Part Number 46-475-668
 * </p>
 */
package com.thalesgroup.orchestra.mozart.generation.doors.handlers;
import java.io.File;

import org.apache.velocity.VelocityContext;

import com.sun.star.text.XTextDocument;
import com.thalesgroup.orchestra.mozart.generation.doors.velocity.VelocityElementDoors;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement;
import com.thalesgroup.themis.papeetedoc.api.publication.implementation.exceptions.PublicationHandlerException;
import com.thalesgroup.themis.papeetedoc.api.publication.implementation.template_technology.velocity.GenericVelocityPublicationHandler;
import com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext;
/**
 * <p>
 * Title : DoorsPublicationHandler
 * </p>
 * <p>
 * Description : Root class for Std Velocity template processings for Doors Element.
 * <p>
 * Specificities:
 * </p>
 * <p>
 * - the properties to communicate with the Velocity template:
 * </p>
 * <p>
 * - the velocity context contains a velocityTrtDoorsElement instance
 * </p>
 * </p>
 * @author Themis developer
 * @version 1.0.1
 */
public class HandlerDoors extends GenericVelocityPublicationHandler {
	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.publication.handlers.custom.velocity.VelocityDefaultPublicationHandler#headerBeforeTemplateCall(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, java.io.File, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	protected void headerBeforeTemplateCall(IAssemblyElement iElement, XTextDocument iDocument, File iTemplateFile,
			IPublicationContext iContext) throws PublicationHandlerException {
	// FIXME : add implementation
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.publication.handlers.custom.velocity.GenericVelocityPublicationHandler#getVelocityContext(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	protected VelocityContext getVelocityContext(IAssemblyElement iElement, IPublicationContext iContext) {
		VelocityContext velocityContext = super.getVelocityContext(iElement, iContext);
		VelocityElementDoors velocityDoorsElement = new VelocityElementDoors(iElement, iContext);
		velocityContext.put("velocityDoorsElement", velocityDoorsElement);
		return velocityContext;
	}
}