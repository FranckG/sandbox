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

import com.sun.star.text.XTextDocument;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement;
import com.thalesgroup.themis.papeetedoc.api.publication.implementation.exceptions.PublicationHandlerException;
import com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext;
/**
 * <p>
 * Title : HandlerDoorsModuleOrView
 * </p>
 * <p>
 * Description : TODO
 * </p>
 */
public class HandlerDoorsModuleOrView extends HandlerDoorsText {
	protected static final String PUBLICATION_ATTRIBUTE_NAME_FOR_NODE_REFERENCES = "referencesToDoorsNodes";
	protected static final String NO_REFERENCES = "No references";

	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.api.publication.implementation.template_technology.velocity.GenericVelocityPublicationHandler#footerTemplateCall(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, java.io.File, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	protected void footerTemplateCall(IAssemblyElement iElement, XTextDocument iDocument, File iFile,
			IPublicationContext iContext) throws PublicationHandlerException {
		// Take into account the "DoorsNodeReferences" publication attrinute to determine if the template is to be called or not
		String doorsNodeReferences = iElement.getPublicationContextParameter(PUBLICATION_ATTRIBUTE_NAME_FOR_NODE_REFERENCES);
		if (!NO_REFERENCES.equals(doorsNodeReferences)) {
			super.footerTemplateCall(iElement, iDocument, iFile, iContext);
		}
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.publication.handlers.custom.thav.velocity.TextPublicationHandler#footerBeforeTemplateCall(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, java.io.File, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	protected void footerBeforeTemplateCall(IAssemblyElement iElement, XTextDocument iDocument, File iFile,
			IPublicationContext iContext) throws PublicationHandlerException {
		super.footerBeforeTemplateCall(iElement, iDocument, iFile, iContext);
		iContext.incHeadingLevel();
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.publicayion.handlers.custom.thav.velocity.TextPublicationHandler#footerAfterTemplateCall(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, java.io.File, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	protected void footerAfterTemplateCall(IAssemblyElement iElement, XTextDocument iDocument, File iFile,
			IPublicationContext iContext) throws PublicationHandlerException {
		super.footerAfterTemplateCall(iElement, iDocument, iFile, iContext);
		iContext.decHeadingLevel();
	}
}
