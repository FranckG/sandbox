/**
 * <p>Copyright (c) 2002-2010 :  Thales Research & Technology</p>
 * <p>Société : Thales Research & Technology</p>
 * <p>Thales Part Number 46-475-668</p>
 */
package com.thalesgroup.orchestra.mozart.generation.doors.handlers;
import java.io.File;
import java.util.List;
import com.sun.star.text.XTextDocument;
import com.thalesgroup.orchestra.mozart.generation.generic.utils.UtilsElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IDescription;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IExtractedFragment;
import com.thalesgroup.themis.papeetedoc.api.publication.implementation.exceptions.PublicationHandlerException;
import com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext;
import com.thalesgroup.themis.papeetedoc.api.publication.utils.OpenOfficeDocumentUtils;
/**
 * <p>
 * Title : TextPublicationHandler
 * </p>
 * <p>
 * Description : Text Publication Handler by means of Velocity - PFS-001
 * Reference: "PUBLICATION FORMATS SPECIFICATION for PAPEETEDOC", Issue 02, Thav.
 * </p>
 * 
 * @author Orchestra developer
 * @version 4.0.0
 */
public class HandlerDoorsText extends HandlerDoors {
	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.publication.handlers.custom.velocity.VelocityDefaultPublicationHandler#headerBeforeTemplateCall(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, java.io.File, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	protected void headerBeforeTemplateCall(IAssemblyElement iElement, XTextDocument iDocument, File iTemplateFile,
			IPublicationContext iContext) throws PublicationHandlerException {
		super.headerBeforeTemplateCall(iElement, iDocument, iTemplateFile, iContext);
		//
		boolean isPageBreak = UtilsElement.isPageBreakBefore(iElement);
		if (!isPageBreak) {
			OpenOfficeDocumentUtils.setPDocInternalCarriageReturn(iDocument);
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
	 * @see com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationHandler#providePublishableHeader(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	public void providePublishableHeader(IAssemblyElement iElement, XTextDocument iDocument, IPublicationContext iContext)
			throws PublicationHandlerException {
		if (hasNodeText(iElement)) {
			super.providePublishableHeader(iElement, iDocument, iContext);
		}
	}
	/**
	 * @param iElement
	 * @return true if the element has node text false else
	 */
	private boolean hasNodeText(IAssemblyElement iElement) {
		boolean hasNodeText = false;
		IExtractedFragment extractedFragment = iElement.getExtractedFragment();
		List<IDescription> desc = extractedFragment.getDescriptions();
		if (desc.size() > 0) {
			hasNodeText = true;
		}
		return hasNodeText;
	}
}