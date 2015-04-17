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
package com.thalesgroup.orchestra.mozart.generation.doors.handlers;
import java.io.File;
import java.util.List;
import java.util.Set;

import com.sun.star.text.XTextDocument;
import com.thalesgroup.orchestra.mozart.generation.doors.velocity.VelocityElementDoors;
import com.thalesgroup.orchestra.mozart.generation.generic.utils.UtilsElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IExtractedFragment;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IProperty;
import com.thalesgroup.themis.papeetedoc.api.publication.implementation.exceptions.PublicationHandlerException;
import com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext;
import com.thalesgroup.themis.papeetedoc.api.publication.utils.OpenOfficeDocumentUtils;
import com.thalesgroup.themis.papeetedoc.api.publication.utils.PostPublicationUtils;
/**
 * <p>
 * Title : SectionWithLEROrLFIPublicationHandler
 * </p>
 * <p>
 * Description : Section With LER Or LFI Publication Handler by means of Velocity - PFS-005/PFS-006 Reference:
 * "PUBLICATION FORMATS SPECIFICATION for PAPEETEDOC", Issue 02, Thav.
 * </p>
 * @author Orchestra developer
 * @version 4.0.0
 */
public class HandlerDoorsSection extends HandlerDoors {
	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.api.publication.implementation.template_technology.GenericTemplateTechnologyPublicationHandler#providePublishableHeader(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	@Override
	public void providePublishableHeader(IAssemblyElement iElement, XTextDocument iDocument, IPublicationContext iContext)
			throws PublicationHandlerException {
		boolean muteCurrentButNotChildren = isSameTypeAsParent(iElement); // true for example for a sub-requirement child of a requirement - do not publish it but publish its children (Doors tables, UC, ...)
		if (muteCurrentButNotChildren) {
			return;
		}
		iContext.setChildrenMuteForTypeName(iElement.getId(), iElement.getType());
		//
		final boolean isPageBreak = UtilsElement.isPageBreakBefore(iElement);
		if (!isPageBreak) {
			// To avoid the last array of the preceding element (if any) and the array of the title of this to be together...
			final IAssemblyElement precedingHeaderElement = (IAssemblyElement) iContext
					.getObjectProperty(IPublicationContext.PRECEDING_HEADER_ELEMENT_OBJECT_PROPERTY);
			if (precedingHeaderElement != null
					&& "DoorsNumberedSectionProvider".equals(precedingHeaderElement.getPublicationContextID())) {
				final VelocityElementDoors velocityDoorsElement = new VelocityElementDoors(precedingHeaderElement, iContext);
				final List<IProperty> nodeAttrList = velocityDoorsElement.getNodeAttrList();
				final Set<String> linkTypesList = velocityDoorsElement.getLinkTypes();
				if (!nodeAttrList.isEmpty() || !linkTypesList.isEmpty()) {
					OpenOfficeDocumentUtils.setStyleCarriageReturn(iDocument, "Text (user)"); // insert blank line
				}
			}
		}
		//
		boolean isParagraphJointly = UtilsElement.isKeepWithNext(iElement);
		if (isParagraphJointly) {
			PostPublicationUtils.bookmarkJoinLines(iDocument, true);
		}
		else {
			OpenOfficeDocumentUtils.generateTextWithStyle("", "PDocInternal", iDocument);
		}
		super.providePublishableHeader(iElement, iDocument, iContext);
		//		List<String> doorsTableExceptions = new ArrayList<String> ();
		//		doorsTableExceptions.add("DoorsTable");
		//		iContext.setChildrenMuteForTypeName(iElement.getId(), "Doors", doorsTableExceptions);
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.themis.papeetedoc.api.publication.implementation.PublicationHandlerAdapter#providePublishableFooter(com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement, com.sun.star.text.XTextDocument, com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext)
	 */
	@Override
	public void providePublishableFooter(IAssemblyElement iElement, XTextDocument iDocument, IPublicationContext iContext)
			throws PublicationHandlerException {
		iContext.removeChildrenMuteForTypeName(iElement.getId(), iElement.getType());
		boolean muteCurrentButNotChildren = isSameTypeAsParent(iElement);
		if (muteCurrentButNotChildren) {
			return;
		}
		final boolean isParagraphJointly = UtilsElement.isKeepWithNext(iElement);
		//		iContext.removeChildrenMuteForTypeName(iElement.getId(),"Doors");
		//super.providePublishableFooter(iElement, iDocument, iContext);
		//		
		//OpenOfficeDocumentUtils.setHeadingCarriageReturn(iDocument);	
		final String templateParameterName = getFooterTemplateParameterName(iElement, iContext);
		final File fTemplateFile = getTemplateFile(iElement, templateParameterName, iContext);
		if (fTemplateFile != null) {
			// Do not forget to manage the 'keep with next' option in the template
			super.providePublishableFooter(iElement, iDocument, iContext);
		}
		else {
			if (isParagraphJointly) {
				PostPublicationUtils.bookmarkJoinLines(iDocument, false);
				OpenOfficeDocumentUtils.setCarriageReturn(iDocument);
			}
			generateEndByCode(iDocument);
		}
	}

	// return true if same type as parent
	// and if the corresponding fragment parent is the same as the assembly parent corresponding fragment.
	private boolean isSameTypeAsParent(IAssemblyElement iElement) {
		boolean sameTypeAsParent = false;
		final IElement parent = iElement.getParent();
		if (parent instanceof IAssemblyElement) {
			final IAssemblyElement assemblyParent = (IAssemblyElement) parent;
			IExtractedFragment fragmentParent = assemblyParent.getExtractedFragment();
			IExtractedFragment fragmentElement = iElement.getExtractedFragment();
			if (fragmentElement != null) {
				IExtractedFragment fragmentElementParent = fragmentElement.getParent();
				if (fragmentElementParent != null) {
					String uri = fragmentElementParent.getPapeeteURI();
					if (uri != null && fragmentParent != null) {
						if (uri.equals(fragmentParent.getPapeeteURI())) {
							String parentType = fragmentParent.getType();
							String type = fragmentElement.getType();
							if (type != null) {
								sameTypeAsParent = type.equals(parentType);
							}
						}
					}
				}
			}
		}
		return sameTypeAsParent;
	}

	/**
	 * In case you want to publish it by template, subclass then redefine this method. Be aware that it will be less
	 * efficient.
	 * @param iDocument
	 * @throws PublicationHandlerException
	 */
	protected void generateEndByCode(XTextDocument iDocument) throws PublicationHandlerException {
		OpenOfficeDocumentUtils.generateTextWithStyle("END", "reqEnd", iDocument);
	}
}