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
import java.util.Iterator;
import java.util.List;

import com.sun.star.text.XTextDocument;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IProperty;
import com.thalesgroup.themis.papeetedoc.api.publication.implementation.exceptions.PublicationHandlerException;
import com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext;
import com.thalesgroup.themis.papeetedoc.api.publication.utils.OpenOfficeDocumentUtils;
/**
 * <p>
 * Title : NumberedSectionWithLEROrLFIPublicationHandler
 * </p>
 * <p>
 * Description : Numbered Section With LER Or LFI Publication Handler by means of Velocity
 * Reference: "PUBLICATION FORMATS SPECIFICATION for PAPEETEDOC", Issue 02, Thav.
 * </p>
 * 
 * @author Themis developer
 * @version 1.0.1
 */
public class HandlerDoorsNumberedSection extends HandlerDoors {
	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.publication.handlers.custom.velocity.VelocityDefaultPublicationHandler#headerBeforeTemplateCall(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, java.io.File, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	protected void headerBeforeTemplateCall(IAssemblyElement iElement, XTextDocument iDocument, File iTemplateFile,
			IPublicationContext iContext) throws PublicationHandlerException {
		super.headerBeforeTemplateCall(iElement, iDocument, iTemplateFile, iContext);
		//
		List<IProperty> properties = iElement.getPropertiesOfNature(IAssemblyElement.NATURE_PUBLICATION_ATTRIBUTE);
		for (Iterator<IProperty> iter = properties.iterator(); iter.hasNext();) {
			IProperty property = (IProperty) iter.next();
			String name = property.getName();
			if (name != null && name.equalsIgnoreCase("title") && property.getValue().equalsIgnoreCase("Numbered")) {
				iContext.incHeadingLevel();
			}
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
		List<IProperty> properties = iElement.getPropertiesOfNature(IAssemblyElement.NATURE_PUBLICATION_ATTRIBUTE);
		for (Iterator<IProperty> iter = properties.iterator(); iter.hasNext();) {
			IProperty property = (IProperty) iter.next();
			String name = property.getName();
			if (name != null && name.equalsIgnoreCase("title") && property.getValue().equalsIgnoreCase("Numbered")) {
				iContext.decHeadingLevel();
			}
		}
	}
}