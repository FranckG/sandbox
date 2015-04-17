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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.StringTokenizer;

import com.thalesgroup.orchestra.mozart.generation.doors.velocity.VelocityElementDoorsMatrix;
import com.thalesgroup.orchestra.mozart.generation.generic.handlers.table.HandlerGenericTable;
import com.thalesgroup.orchestra.mozart.generation.generic.utils.UtilsElement;
import com.thalesgroup.orchestra.mozart.generation.generic.velocity.table.VelocityElementGenericTable;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IExtractedFragment;
import com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext;
import com.thalesgroup.themis.papeetedoc.api.publication.utils.LogUtils;
/**
 * <p>
 * Title : DoorsMatrixPublicationHandler
 * </p>
 * <p>
 * Description : Doors Requirement Matrix Mode Publication Handler Reference:
 * "PUBLICATION FORMATS SPECIFICATION for PAPEETEDOC", Issue 02, Thav.
 * </p>
 * @author Orchestra developer
 * @version 4.0.0
 */
public class HandlerDoorsMatrix extends HandlerGenericTable {
	public static final String _CAPTION = "caption";
	public static final String _FULL_LABEL = "Full Label";
	public static final String _MODULE_VIEW = "Module And View";
	public static final String _VIEW = "View Only";

	@Override
	protected VelocityElementGenericTable createVelocityElement(IAssemblyElement iElement, IPublicationContext iContext) {
		return new VelocityElementDoorsMatrix(iElement, iContext);
	}

	// By default the title is the name of the element
	@Override
	protected String getTitle(IAssemblyElement iElement) {
		String caption = iElement.getPropertyValue(_CAPTION);
		if (caption != null) {
			// Type
			String doorsType = iElement.getDisplayableType();
			int index = doorsType.indexOf(" ");
			String type = doorsType;
			if (index != -1) {
				type = doorsType.substring(index + 1);
			}
			// Project
			if (caption.equals(_MODULE_VIEW)) {
				return "(" + getModule(iElement, type) + ") " + getView(iElement);
			}
			else if (caption.equals(_VIEW)) {
				return getView(iElement);
			}
			else {
				return iElement.getName();
			}
		}
		return iElement.getName();
	}

	/**
	 * @return
	 */
	private String getView(IAssemblyElement iElement) {
		String view = "";
		IExtractedFragment extractedFragment = iElement.getExtractedFragment();
		if (iElement.getExtractedFragment() != null) {
			view = extractedFragment.getName();
			int index = view.indexOf(") ");
			if (index != -1) {
				view = view.substring(index + 2);
			}
		}
		return view;
	}

	/**
	 * @param iElement
	 * @param type
	 * @return
	 */
	private String getModule(IAssemblyElement iElement, String type) {
		String module = "";
		IExtractedFragment extractedFragment = iElement.getExtractedFragment();
		String projectName = "/";
		if (extractedFragment != null) {
			String orchestraUri = extractedFragment.getPapeeteURI();
			// orchestra uri must be in the format : orchestra://ToolName/ProjectName/ObjectType/ObjectId
			String[] orchestraUriFields = orchestraUri.split("/");
			String encodedProjectName = orchestraUriFields[3].trim();
			try {
				projectName = URLDecoder.decode(encodedProjectName, UtilsElement.DEFAULT_ENCODING);
			} catch (UnsupportedEncodingException e) {
				LogUtils.write(LogUtils.ERROR_MESSAGE, LogUtils.DEST_CONSOLE_AND_SESSION_LOG_AND_PROJECT_LOG,
						"Decoded ProjectName '" + encodedProjectName + "' failed.");
			}
			// Title
			StringBuffer moduleBuffer = new StringBuffer();
			String label = extractedFragment.getName();
			int index = label.indexOf(projectName);
			if (index != -1) {
				label = label.substring(index + projectName.length());
			}
			StringTokenizer token = new StringTokenizer(label, " ");
			String endType = type + ")";
			while (token.hasMoreElements()) {
				String object = (String) token.nextElement();
				if (object.equals(endType)) {
					break;
				}
				moduleBuffer.append(object);
				moduleBuffer.append(" ");
			}
			if (moduleBuffer.length() > 0) {
				module = moduleBuffer.toString().trim();
			}
		}
		return module;
	}

	/* (non-Javadoc)
	 * @see com.thalesgroup.orchestra.mozart.generation.generic.handlers.table.HandlerGenericTable#removeParenthesisInTitle()
	 */
	@Override
	protected boolean removeParenthesisInTitle() {
		return false;
	}
}