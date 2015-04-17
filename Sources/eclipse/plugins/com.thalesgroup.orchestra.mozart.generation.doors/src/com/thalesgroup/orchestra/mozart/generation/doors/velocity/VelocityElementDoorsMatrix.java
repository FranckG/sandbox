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
package com.thalesgroup.orchestra.mozart.generation.doors.velocity;
import com.thalesgroup.orchestra.mozart.generation.generic.velocity.table.VelocityElementTable;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IAssemblyElement;
import com.thalesgroup.themis.papeetedoc.api.model.interfaces.IElement;
import com.thalesgroup.themis.papeetedoc.api.publication.interfaces.IPublicationContext;
/**
 * <p>
 * Title : VelocityStdDoorsMatrixElement
 * </p>
 * <p>
 * Description : Velocity for Table publication. The goal of this class is to publish a table with a set of rows.
 * <p>
 * Here are the possible publication attributes:
 * The same publication attributes as VelocityStdTableElement except that 'hideColumns' is replaced by 'matrixColumnsNotToPublish'
 * Be aware that due to "Description" nature of some columns, some publication attributes may not work fine. See VelocityStdTableElement class description.
 * </p>
 * @author Themis developer
 * @version 1.0.3
 */
public class VelocityElementDoorsMatrix extends VelocityElementTable {
	// 'matrixColumnsNotToPublish' property: correspond to '_HIDE_COLUMNS', list of columns not to publish, for example: col1, col5, col2
	public static final String _MATRIX_COLUMNS_NOT_TO_PUBLISH = "matrixColumnsNotToPublish";
	// Doors element type prefix
	public static final String _DOORS_TYPE_PREFIX = "Doors";
	/**
	 * Constructor
	 */
	public VelocityElementDoorsMatrix(IElement iElement, IPublicationContext iContext) {
		super(iElement, iContext);
	}
	/**
	 * Redefined because the property for matrix is not HIDE_COLUMNS but _MATRIX_COLUMNS_NOT_TO_PUBLISH (in previous versions).
	 * So to ensure compatibility.
	 * @return the value of the property _MATRIX_COLUMNS_NOT_TO_PUBLISH - if no value, then return the property of _HIDE_COLUMNS
	 * @override
	 */
	public String getHideColumnsProperty() {
		String s = super.getHideColumnsProperty();
		String matrixColumns = _assemblyElement.getPropertyValue(_MATRIX_COLUMNS_NOT_TO_PUBLISH);
		if (matrixColumns != null && ! "".equals(matrixColumns)) {
			s =  s.concat(matrixColumns);
		}
		return s;
	}
	/**
	 * @param child
	 * @return true if the child is a Doors type element
	 */
	protected boolean isAllowedChild(IAssemblyElement child) {
		String type = child.getType();
		return (type != null && type.startsWith(_DOORS_TYPE_PREFIX));
	}
}